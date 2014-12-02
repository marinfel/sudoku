package com.sudoku.data.model;

import java.sql.Timestamp;
import java.util.*;

public class Grid {
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
  private String createSalt; // Salt of the creator used as an UUID
  private Date createDate;
  private Date updateDate;

  private Grid() {
  }

  public Grid(String t, User u) {
    id = UUID.randomUUID();
    title = t;
    description = "";
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
    createPseudo = u.getPseudo();
    createSalt = u.getSalt();
    Calendar cal = new GregorianCalendar();
    createDate = cal.getTime();
    updateDate = createDate;

  }

  public static Grid buildFromAvroGrid(com.sudoku.comm.generated.Grid grid) {
    Grid resultGrid = new Grid();
    resultGrid.id = UUID.fromString(grid.getId());
    resultGrid.title = grid.getTitle();
    resultGrid.description = grid.getDescription();
    resultGrid.difficulty = grid.getDifficulty();
    resultGrid.published = grid.getPublished();
    resultGrid.createDate = Timestamp.valueOf(grid.getCreateDate());
    resultGrid.updateDate = Timestamp.valueOf(grid.getUpdateDate());
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
        if (row.get(j) != null) {
          resultGrid.grid[i][j] = new FixedCell(i, j, row.get(j).byteValue());
        } else {
          resultGrid.grid[i][j] = new EmptyCell(i, j);
        }
      }
    }
    return resultGrid;
  }

  private void updateDate(){
      Calendar cal = new GregorianCalendar();
      updateDate = cal.getTime();
  }
  
  public void setEmptyCell(byte x, byte y) throws IllegalArgumentException {
    if (x < 0 || x > 9 || y < 0 || y > 9) {
      throw new IllegalArgumentException(Cell.Errors.Cell_illegal_position);
    }
    grid[x][y] = new EmptyCell(x, y);
    this.updateDate();
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
    this.updateDate();
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
    if (grid.length != 9 || grid[0].length != 9) {
      throw new IllegalArgumentException(Grid.errors.Grid_invalid_grid_array);
    }
    this.updateDate();
    this.grid = grid;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String titre) {
    this.title = titre;
    this.updateDate();
  }

  public int getMeanGrades() { //Give the mean, or 0 if there is no grades
    int i = 0, result = 0;
    for (Comment comment : comments) {
      result += comment.getGrade();
      i++;
    }
    if (i != 0)
      return result / i;
    else
      return 0;
  }

  public UUID getId() {
    return id;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
    this.updateDate();
  }

  public int getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(int difficulty) {
    this.difficulty = difficulty;
    this.updateDate();
  }

  public boolean isPublished() {
    return published;
  }

  public void setPublished(boolean published) {
    this.published = published;
    this.updateDate();
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void addComments(Comment c) {
    this.comments.add(c);
    this.updateDate();
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
    this.updateDate();
  }

  public User getCreateUser() {
    return createUser;
  }


  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
    this.updateDate();
  }

  public Date getUpdateDate() {
    return updateDate;
  }


  public double getAverageGrade() {
    double averageGrade = 0.0;
    for (Comment comment : comments) {
      averageGrade += comment.getGrade();
    }
    return averageGrade / comments.size();
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
    this.updateDate();
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
    this.updateDate();
  }

  public void addComment(Comment comment) {
    if (comment != null && comment.getComment() != null &&
        !comment.getComment().isEmpty()) {
      comments.add(comment);
    }
    this.updateDate();
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
    this.updateDate();
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
}
