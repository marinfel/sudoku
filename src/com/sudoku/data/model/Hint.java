//File writed by Florian Trois


package com.sudoku.data.model;

public class Hint {
	private byte value;

	public Hint(byte value) {
		super();
		this.setValue(value);
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}
	
	
}
