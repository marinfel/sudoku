/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author xuncao
 */

package com.sudoku.data.model;

public class Cell {

  protected byte x;
  protected byte y;
  //For deserialisation use only
   private byte value;

  public Cell(){
      
  }
  protected Cell(byte x, byte y) throws IllegalArgumentException {

    if (x < 0 || x >= 9 || y < 0 || y >= 9) {
      throw new IllegalArgumentException(Cell.Errors.Cell_illegal_position);
    }

    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return "X ";
  }

  public static class Errors {
    public static final String Cell_illegal_position = "La position de la cellule devrait etre comprise entre 0 et 8";
    public static final String Cell_illegal_value = "Le contenu de la cellule devrait etre compris entre 1 et 9";
  }
  
  // for deserialisatio use only
    public byte getX(){
      return x;
      
    }
    public void setX( byte X){
        x=X;
    }
    public byte getY(){
          return y;

      }
    public void setY( byte Y){
        y=Y;
    }
    public byte getValue(){
          return value;

      }
    public void setValue( byte Value){
        value=Value;
    }
}
