package com.sudoku.grid.ihm_grid_cells;

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
     * TODO
     */
    protected boolean checkValue(int value) {
        //check the value
        return (value > 0 && value < 10);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void setValue(int value);

    public abstract int getValue();
}
