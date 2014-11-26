package com.sudoku.data.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *  
 * @author jonathan
 */
import com.sudoku.data.model.User;

public class Comment {
  /** Champs de la classe Comment
   * *
   */
  private User author;
  private String pseudo;
  private String comment;
  private Integer grade;
  /**Méthode de la classe  Comment
   *
   */

  public Comment(String comment,Integer grade, User u){
      this.comment=comment;
      this.grade=grade;
      this.author=u;
      this.pseudo=u.getPseudo();
  }
  
  public User getAuthor(){
      return author;
  }
  public String getPseudo() { return pseudo; }
  public String getComment(){
      return comment;
  }
  public void setComment(String comment){
      this.comment=comment;
  }
  public void setGrade(Integer grade){
      this.grade=grade;
  }
  public Integer getGrade(){
      return grade;
  }
}

