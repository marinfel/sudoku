//File writed by Florian Trois

package com.sudoku.data.model;

import java.sql.Timestamp;
import java.util.List;


public class PlayedGrid {
  List<CellGuess> guessedCells;
  Timestamp startDate;
  Timestamp updateDate;
  private Grid grid;
  private User player;


  public PlayedGrid(Grid grid) {
    super();
    this.grid = grid;
  }

  public PlayedGrid(Grid grid, User player) {
    super();
    this.grid = grid;
    this.player = player;
  }

  public Grid getGrid() {
    return grid;
  }

  public void setGrid(Grid grid) {
    this.grid = grid;
  }

  public boolean isComplete() {
    return false;
  }

  public int nbRemainingCells() {
    return 0;
  }

  public List<CellGuess> getGuessedCells() {
    return null;
  }

  public User getPlayer() {
    return player;
  }

  public void setPlayer(User player) {
    this.player = player;
  }
}
