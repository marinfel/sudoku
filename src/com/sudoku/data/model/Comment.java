package com.sudoku.data.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author jonathan
 */

public final class Comment {
  /**
   * Champs de la classe Comment
   * *
   */
  private final User author;
  private final String pseudo;
  private final String userSalt; // We use the salt as an UUID to identify the poster
  private String comment;
  private Integer grade; // Number of half stars. If some comment is graded 3 stars and a half, grade will be 7.
  private Date creationDate;
  /**
   * Méthode de la classe  Comment
     * @param comment
     * @param grade
     * @param u
   */
  
  // Nécessaire pour la désérialisation
     public Comment(){
    this.comment = "";
    this.grade = 0;
    this.author = null;
    this.pseudo = null;
    this.userSalt = null;
    }
  public Comment(String comment, Double grade, User u) {
    this.comment = comment;
    this.setGrade(grade);
    this.author = u;
    this.pseudo = u.getPseudo();
    this.userSalt = u.getSalt();
  }
 public Comment(String comment, Integer grade, User u) {
    this.comment = comment;
    this.grade=grade;
    this.author = u;
    this.pseudo = u.getPseudo();
    this.userSalt = u.getSalt();
  }

  public static Comment buildFromAvroComment(
      com.sudoku.comm.generated.Comment comment) {
    return new Comment(comment.getComment(), comment.getGrade(),
        User.buildFromAvroUser(comment.getAuthor()));
  }

  public User getAuthor() {
    return author;
  }

  public String getPseudo() {
    return pseudo;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Double getStarGrade() { //Return the number of Stars (and not half stars)
    return this.grade/2.0;
  }
  public Integer getGrade() { //Return the number of half stars
    return this.grade;
  }

 public void setGrade(Integer grade){
      this.grade=grade;
  }
  
  public void setGrade(Double grade) {
    Double halfStarGrade = grade * 2; //Set this number in halfstars
    this.grade = halfStarGrade.intValue();
  }
  public String getUserSalt(){
      return this.userSalt;
  }
  public void setCreationDateNow(){
      Calendar cal = new GregorianCalendar();
      this.creationDate=cal.getTime();
  }
}
