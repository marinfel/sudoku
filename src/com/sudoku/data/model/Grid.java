package com.sudoku.data.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Grid {
    private int id;
    private String title;
    private String description;
    private int difficulty;
    private boolean published;
    private List<Comment> comments;
    private List<Tag> tags;
    private Cell[][] grid;
    private User createUser;
    private Timestamp createDate;
    private Timestamp updateDate;
    

    public Grid(){
            comments = new ArrayList<>();
            tags = new ArrayList<>();

            grid = new Cell[9][9];
            for(byte i = 0; i < 9; i++){
                for(byte j = 0; j < 9; j++){
                    grid[i][j] = new EmptyCell(i, j);
                }
            }
    }
    
        public void setEmptyCell(byte x, byte y) throws IllegalArgumentException{
            if(x < 0 || x > 9 || y < 0 || y > 9){
                throw new IllegalArgumentException(Cell.Errors.Cell_illegal_position);
            }
            
            grid[x][y] = new EmptyCell(x, y);
        }
        
        public void setFixedCell(byte x, byte y, byte value) throws IllegalArgumentException {
            if(value < 1 || value > 9){
                throw new IllegalArgumentException(Cell.Errors.Cell_illegal_value);
            }
            
            if(x < 0 || x > 9 || y < 0 || y > 9){
                throw new IllegalArgumentException(Cell.Errors.Cell_illegal_position);
            }
            
            if(grid[x][y] instanceof FixedCell){
                ((FixedCell) grid[x][y]).setValue(value);
            }
            else{
                grid[x][y] = new FixedCell(x, y, value);
            }           
        }
        
        public Cell getCell(int x, int y) throws IllegalArgumentException {
            if(x < 0 || x >= 9 || y < 0 || y >= 9){
                throw new IllegalArgumentException(Cell.Errors.Cell_illegal_position);
            }
            return grid[x][y];
        } 
        
    public String getTitle(){
        return title;
    }
    
    public void setTitle(String titre){
        this.title=titre;
    }
        
    public int getMeanGrades(){
        int i=0,result=0;
        for(Comment comment:comments){
            result+=comment.getGrade();
            i++;
        }
        
        return result/i;
    }
    public int getId() {
        return id;
    }
    
    
    public void setId(int id) {
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
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    public User getCreateUser() {
        return createUser;
    }
    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }
    public Timestamp getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    public Timestamp getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }
    
    public double getAverageGrade(){
        double averageGrade = 0.0;
        for(Comment comment : comments){
            averageGrade += comment.getGrade();
        }
        return averageGrade / comments.size();
    }
    
    public void addTag(Tag tag){
        if(tag != null && tag.getName() != null && !tag.getName().isEmpty())
        {
            for(Tag t : tags){
                if(t.getName().equals(tag.getName())){
                    return;
                }
            }
            tags.add(tag);
        }
    }
    
    public void removeTag(Tag tag){
        if(tag != null && tag.getName() != null && !tag.getName().isEmpty())
        {
            for(Tag t : tags){
                if(t.getName().equals(tag.getName())){
                    tags.remove(t);
                    return;
                }
            }
        }
    }
    
    public void addComment(Comment comment){
        if(comment != null && comment.getComment() != null && !comment.getComment().isEmpty())
        {
            comments.add(comment);
        }
    }
    
    public void removeComment(Comment comment){
        if(comment != null && comment.getComment() != null && !comment.getComment().isEmpty())
        {
            for(Comment c : comments){
                if(c.getComment().equals(comment.getComment()) && c.getGrade() == comment.getGrade()){
                    comments.remove(c);
                    return;
                }
            }
        }        
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                sb.append(grid[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
}
