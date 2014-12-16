package com.sudoku.data.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author jonathan
 */

public class Comment {
  /**
   * Champs de la classe Comment
   * *
   */
  private final User author;
  private final String pseudo;
  private final String userSalt; // We use the salt as an UUID to identify the poster
  private String comment;
  private Integer grade; // Number of half stars. If some comment is graded 3 stars and a half, grade will be 7.

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

  public Double getGrade() { //Return the number of Stars (and not half stars)
    return grade/2.0;
  }

  public void setGrade(Double grade) {
    Double halfStarGrade = grade * 2; //Set this number in halfstars
    this.grade = halfStarGrade.intValue();
  }
  public String getUserSalt(){
      return this.userSalt;
  }

}
