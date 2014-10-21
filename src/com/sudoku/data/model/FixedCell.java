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

	public FixedCell(int value) {
		super();
		this.value = value;
	}
	
}
