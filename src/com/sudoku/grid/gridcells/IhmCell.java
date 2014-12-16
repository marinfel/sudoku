package com.sudoku.grid.gridcells;

import javafx.scene.layout.GridPane;

/**
 * this is an abstract class
 *
 * @author Yi FENG / Marc-Antoine MARTI
 */
public abstract class IhmCell extends GridPane {

  protected int x = -1;
  protected int y = -1;

  /**
   * @param value
   * @return
   */
  protected boolean checkValue(int value) {
    //check the value
    return value > 0 && value < 10;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public abstract int getValue();

  public abstract void setValue(int value);
}
