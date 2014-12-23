package com.sudoku.data.model;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.sudoku.comm.CommunicationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Grid {
  private static final Logger LOGGER = LoggerFactory.getLogger(User.class);
  private UUID id;
  private String title;
  private String description;
  private int difficulty;
  private boolean published;
  private List<Comment> comments;
  private List<Tag> tags;
  private Cell[][] grid;
  private User createUser;
  private String createPseudo;
  private String createSalt; // Salt of the creator used as a UUID
  private Date createDate;
  private Date updateDate;

  public Grid() {}

  public Grid(String t, User u) {
    id = UUID.randomUUID();
    title = t;
    description = "";
    createPseudo = "";
    createSalt = "" ;
    difficulty = 0;
    published = false;
    comments = new ArrayList<>();
    tags = new ArrayList<>();

    grid = new Cell[9][9];
    for (byte i = 0; i < 9; i++) {
      for (byte j = 0; j < 9; j++) {
        grid[i][j] = new EmptyCell(i, j);
      }
    }
    
    createUser = u;
    if(createUser != null){
        createPseudo = u.getPseudo();
        createSalt = u.getSalt();
    }
    
    Calendar cal = new GregorianCalendar();
    createDate = cal.getTime();
    updateDate = createDate;
  }

  public static Grid buildFromAvroGrid(com.sudoku.comm.generated.Grid grid) {
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    Grid resultGrid = new Grid();
    resultGrid.id = UUID.fromString(grid.getId());
    resultGrid.title = grid.getTitle();
    resultGrid.description = grid.getDescription();
    resultGrid.difficulty = grid.getDifficulty();
    resultGrid.published = grid.getPublished();
    try {
      resultGrid.createDate = df.parse(grid.getCreateDate());
      resultGrid.updateDate = df.parse(grid.getUpdateDate());
    } catch (ParseException ex) {
      LOGGER.error(ex.toString());
    }
    resultGrid.createUser = User.buildFromAvroUser(grid.getCreateUser());
    for (com.sudoku.comm.generated.Comment comment : grid.getComments()) {
      resultGrid.comments.add(Comment.buildFromAvroComment(comment));
    }
    for (String tag : grid.getTags()) {
      resultGrid.tags.add(new Tag(tag));
    }
    List<List<Integer>> matrix = grid.getMatrix();
    for (byte i = 0; i < matrix.size(); i++) {
      List<Integer> row = matrix.get(i);
      for (byte j = 0; j < row.size(); j++) {
        if (row.get(j) != -1) {
          resultGrid.grid[i][j] = new FixedCell(i, j, row.get(j).byteValue());
        } else {
          resultGrid.grid[i][j] = new EmptyCell(i, j);
        }
      }
    }
    return resultGrid;
  }

  public void setEmptyCell(byte x, byte y) throws IllegalArgumentException {
    if (x < 0 || x > 9 || y < 0 || y > 9) {
      throw new IllegalArgumentException(Cell.Errors.Cell_illegal_position);
    }

    grid[x][y] = new EmptyCell(x, y);
  }

  public void setFixedCell(byte x, byte y, byte value)
      throws IllegalArgumentException {
    if (value < 1 || value > 9) {
      throw new IllegalArgumentException(Cell.Errors.Cell_illegal_value);
    }

    if (x < 0 || x > 9 || y < 0 || y > 9) {
      throw new IllegalArgumentException(Cell.Errors.Cell_illegal_position);
    }

    if (grid[x][y] instanceof FixedCell) {
      ((FixedCell) grid[x][y]).setValue(value);
    } else {
      grid[x][y] = new FixedCell(x, y, value);
    }
  }

  public Cell getCell(int x, int y) throws IllegalArgumentException {
    if (x < 0 || x >= 9 || y < 0 || y >= 9) {
      throw new IllegalArgumentException(Cell.Errors.Cell_illegal_position);
    }
    return grid[x][y];
  }

  public Cell[][] getGrid() {
    return this.grid;
  }

  public void setGrid(Cell[][] grid) throws IllegalArgumentException {
    if (grid.length != 9 || grid[0].length != 9) { //Check if the grid is of the right size
      throw new IllegalArgumentException(Grid.errors.Grid_invalid_grid_array);
    }

    this.grid = grid;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String titre) {
    this.title = titre;
  }
@JsonIgnore
  public double getMeanGrades() { // Give the mean expressed in half stars
    int i = 0;
    double result=0;
    for (Comment comment : comments) {
      result += comment.getGrade();
      i++;
    }
    if (i != 0)
      return result / i;
    else
      return 0;
  }
@JsonIgnore
  public double getMeanStarGrades() { //Give the mean expressed in stars
    int i = 0;
    double result=0;
    for (Comment comment : comments) {
      result += comment.getGrade();
      i++;
    }
    if (i != 0)
      return result / (2 * i);
    else
      return 0;
  }
    
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(int difficulty) {
    this.difficulty = difficulty;
  }

  public boolean isPublished() {
    return published;
  }

  public void setPublished(boolean published) {
    this.published = published;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public boolean hasTag(Tag tag){ return this.tags.contains(tag); }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public User getCreateUser() {
    return createUser;
  }

  public void setCreateUser(User createUser) {
    this.createUser = createUser;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Timestamp updateDate) {
    this.updateDate = updateDate;
  }


  public void addTag(Tag tag) {
    if (tag != null && tag.getName() != null && !tag.getName().isEmpty()) {
      for (Tag t : tags) {
        if (t.getName().equals(tag.getName())) {
          return;
        }
      }
      tags.add(tag);
    }
  }

  public void removeTag(Tag tag) {
    if (tag != null && tag.getName() != null && !tag.getName().isEmpty()) {
      for (Tag t : tags) {
        if (t.getName().equals(tag.getName())) {
          tags.remove(t);
          return;
        }
      }
    }
  }

  public void addComment(Comment comment) {
    if (comment != null && comment.getComment() != null &&
        !comment.getComment().isEmpty()) {
      comments.add(comment);
      try {
		CommunicationManager.getInstance().pushComment(comment, getId());
	} catch (IOException e) {
		e.printStackTrace();
	}
    }
  }

  public void removeComment(Comment comment) {
    if (comment != null && comment.getComment() != null &&
        !comment.getComment().isEmpty()) {
      for (Comment c : comments) {
        if (c.getComment().equals(comment.getComment()) &&
            c.getGrade().equals(comment.getGrade())) {
          comments.remove(c);
          return;
        }
      }
    }
  }
  
  // Use getMeanGrade instead
  @Deprecated
  @JsonIgnore
  public double getAverageGrade(){
    if(this.comments.size() == 0){return 0;}

    double total = 0;
    for(Comment c : this.comments){
      total += c.getGrade();
    }
    return total / this.comments.size();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        sb.append(grid[i][j]);
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  public class errors {
    public static final String Grid_invalid_grid_array =
        "The grid must be a 9x9 cell array";
  }
  // for deserialisation use only
  public String getCreatePseudo(){
      return createPseudo;
  }
  public String getCreateSalt(){
      return createSalt;
  }
  
  public boolean equals(Object other){
	    if(other == null) return false;
	    if(other == this) return true;
	    if(!(other instanceof Grid)) return false;
	    
	    Grid o = (Grid)other;
	    return o.getId().equals(getId());
	  }
}
