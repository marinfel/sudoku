package com.sudoku.grid.ihm_grid_cells;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 * @class IHM_CellView -- this is a class to afficher the chiffer in a cell
 * @author Yi FENG
 * @version 0.0.1
 */
public class IhmCellView extends IhmCell {

    protected Label valueView;
    protected boolean hidden;

    protected int valueFontSize = 20;

    /**
     * @explain: this is constraction function of class IHM_ClleView
     */
    public IhmCellView(int value, int valueFontSize, int width, int height) {
        this.setMaxSize(width, height);

        // Init hidden attribut at false
        hidden = false;

        /*init valueView attribut and add it to the layout*/
        valueView = new Label();
        valueView.setStyle("-fx-font-size: " + valueFontSize + "pt;");
        valueView.setAlignment(Pos.CENTER);
        setConstraints(valueView, 0, 0);
        getChildren().add(valueView);
        System.out.println("IHM_CellView()");

        setValue(value);
    }

    /**
     * @explain: this is a function for setting coordinate of the value
     * displayed
     * @param value type int, this is a value need to be displayed
     */
    @Override
    public void setValue(int value) {
        //first check the value
        if (checkValue(value)) { //set the value
            valueView.setText(String.valueOf(value));
        }
    }
}
