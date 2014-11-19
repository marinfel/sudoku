package com.sudoku.grid.ihm_grid_cells;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * @class IHM_CellView -- this is a class to afficher the chiffer in a cell
 * @author Yi FENG
 * @version 0.0.1
 */
public class IhmCellView extends IhmCell {

    protected Label valueView;
    protected boolean hiden;
    protected boolean hidable;

    protected int valueFontSize = 20;

    /**
     * @explain: this is constraction function of class IHM_ClleView
     */
    public IhmCellView(int side) {
        this.setMaxSize(side, side);

        // When mouse click the cell, we hide the label
        setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if (t.getEventType() == MouseEvent.MOUSE_CLICKED && hidable) {
                    setHiden(!hiden);
                    t.consume();
                }
            }

        });

        // Init hiden attribut at false
        hiden = false;
        hidable = false;

        /*init valueView attribut and add it to the layout*/
        valueView = new Label();
        valueView.setMinSize(side, side);
        valueView.setMaxSize(side, side);
        valueView.setStyle("-fx-font-size: " + (0.7 * (double) side) + "px;");
        valueView.setAlignment(Pos.CENTER);
        valueView.setManaged(true);// Use to fix/unfix it on the layout
        setConstraints(valueView, 0, 0);
        getChildren().add(valueView);
    }

    /**
     * @explain: this is a function for setting coordinate of the value
     * displayed
     * @param value type int, this is a value need to be displayed
     */
    @Override
    public final void setValue(int value) {
        //first check the value
        if (checkValue(value)) { //set the value
            valueView.setText(String.valueOf(value));
        }
    }

    @Override
    public int getValue() {
        try {
            return Integer.parseInt(valueView.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Use to enable/disable automatic hide when user click on the Cell
     *
     * @param hidable
     */
    public void setHidable(boolean hidable) {
        this.hidable = hidable;
    }

    /**
     * Use to hide or show the cell
     *
     * @param hidden
     */
    public void setHiden(boolean hidden) {
        this.hiden = hidden;
        valueView.setVisible(!hidden);// Use to show/hide it
    }

    public boolean isHiden() {
        return hiden;
    }
}
