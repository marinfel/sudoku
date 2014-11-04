//File writed by Florian Trois

package com.sudoku.data.model;

public class FixedCell extends Cell{
    private int value;

    public int getValue() {
            return value;
    }

    public void setValue(int value) {
            this.value = value;
    }

    public FixedCell(int x, int y){
        super(x, y);
    }

    public FixedCell(int x, int y, int value) {
            super(x, y);
            this.value = value;
    }
}
