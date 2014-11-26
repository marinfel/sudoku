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

import java.util.List;

public class CellGuess {

  private Cell cell;
  private int guessedValue;
  private List<Hint> hints;
  private String createDate;
  private String updateDate;

  public CellGuess() {
  }

  /**
   * @return the cell
   */
  public Cell getCell() {
    return cell;
  }

  /**
   * @param cell the Cell to set
   */
  public void setCell(Cell cell) {
    this.cell = cell;
  }

  /**
   * @return the guessedValue
   */
  public int getGuessedValue() {
    return guessedValue;
  }

  /**
   * @param value the guessedValue to set
   */
  public void setGuessedValue(int value) {
    this.guessedValue = value;
  }

  /**
   * @return the hints
   */
  public List<Hint> getHints() {
    return hints;
  }

  /**
   * @add the hints
   */
  public void addHint(Hint hint) {
    this.hints.add(hint);
  }

  /**
   * @remove the hints
   */
  public void removeHint(Hint hint) {
    this.hints.remove(hint);
  }

}
