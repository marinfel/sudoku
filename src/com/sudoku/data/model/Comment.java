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
  private User author;
  private String pseudo;
  private String comment;
  private Integer grade;

  /**
   * Méthode de la classe  Comment
   */

  public Comment(String comment, Integer grade, User u) {
    this.comment = comment;
    this.grade = grade;
    this.author = u;
    this.pseudo = u.getPseudo();
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

  public Integer getGrade() {
    return grade;
  }

  public void setGrade(Integer grade) {
    this.grade = grade;
  }
}

