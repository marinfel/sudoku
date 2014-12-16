package com.sudoku.data.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Classe Tag
 *
 * @author jonathan
 */
public class Tag {
  /**
   * Champs de la classe Tag
   */
  private String name;

  /**
   * Méthodes de la classe Tag
   */
  public Tag() {
    name = "";
  }

  public Tag(String name) {
    this.name = name;
  }

  public Tag(Tag tag) {
    this.name = tag.getName();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object other){
    if(other == null) return false;
    if(other == this) return true;
    if(!(other instanceof Tag)) return false;
    return ((Tag)other).name.equals(this.name);
  }
}
