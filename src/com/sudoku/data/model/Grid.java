package com.sudoku.data.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Grid {
	private int id;
	private String description;
	private int difficulty;
	private boolean published;
	private List<Comment> comments;
	private List<Tag> tags;
	private User createUser;
	private Timestamp createDate;
	private Timestamp updateDate;
	

	public Grid(){
		comments = new ArrayList<Comment>();
		tags = new ArrayList<Tag>();
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
}
