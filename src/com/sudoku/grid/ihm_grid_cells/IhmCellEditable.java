/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.ihm_grid_cells;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Marc-Antoine
 */
public class IhmCellEditable extends IhmCell {

    protected final static int addlesMaxNumbers = 3;

    protected int addlesFontSize = 10;
    protected int valueFontSize = 20;

    protected double addlesSizeX;
    protected double addlesSizeY;
    protected double valueSizeX;
    protected double valueSizeY;

    protected HBox addlesLayout = new HBox();
    protected ArrayList<TextField> addlesEditList = new ArrayList<TextField>();
    protected Hyperlink addlesAddButton = new Hyperlink();
    protected TextField valueEdit = new TextField();

    /**
     * IHM_CellEditable constructor
     *
     * @param width taken by the node
     * @param height taken by the node
     * @param addlesFontSize defines the font size in addles TextField
     * @param valueFontSize defines the font size in value's TextField
     */
    public IhmCellEditable(double width, double height, int addlesFontSize, int valueFontSize) {
        setMaxSize(width, height);

        this.addlesFontSize = addlesFontSize;
        this.valueFontSize = valueFontSize;

        valueSizeX = getMaxWidth();
        valueSizeY = getMaxHeight() * (1 - 1 / addlesMaxNumbers);
        addlesSizeX = getMaxWidth() / addlesMaxNumbers;
        addlesSizeY = getMaxHeight() / addlesMaxNumbers;

        // When mouse enters the cell, we show the add button
        setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if (addlesEditList.size() < addlesMaxNumbers) {
                    setAddlesAddButtonVisible(true);
                }
            }

        });

        // When mouse exits the cell, we hide the add button
        setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                setAddlesAddButtonVisible(false);
            }

        });

        /*init the addles layout attribut and add it to the top of the layout*/
        this.add(addlesLayout, 0, 0);

        //Create a space and add it in the addles layout to fix the height when there is no addles
        Rectangle space = new Rectangle();
        space.setWidth(0);
        space.setHeight(addlesSizeY + 1);
        space.setStroke(Color.TRANSPARENT);
        space.setFill(Color.TRANSPARENT);
        addlesLayout.getChildren().add(space);

        /*init the addles add-button and add it on the layout next to the addles list*/
        addlesAddButton.setText("+");
        addlesAddButton.setStyle("-fx-font-size: " + addlesFontSize + "pt;");
        addlesAddButton.setAlignment(Pos.CENTER);
        addlesAddButton.setMaxSize(addlesSizeX, addlesSizeY);
        addlesAddButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                // Handle the action event from addles add-button and TextFields
                System.out.println("Event handled: [" + t.getClass().toString() + "] " + t.getEventType());

                // Check if the handler has already been consumed
                if (t.isConsumed()) {
                    return; //then quit the handle function
                }
                // Check if we handled the event from the addles add-button
                if (t.getSource() == addlesAddButton) {
                    addlesAddButtonHandler(t);
                } // Otherwise
                else {
                    return; //Quit the function without consume the event
                }
                // Consume the event to not handle it once more
                t.consume();
            }

        });
        addlesLayout.getChildren().add(addlesAddButton);
        setAddlesAddButtonVisible(false);

        /*init valueEdit attribut and add it to the bottom of the layout*/
        valueEdit.setMaxSize(valueSizeX, valueSizeY);
        valueEdit.setAlignment(Pos.CENTER);
        valueEdit.setStyle("-fx-font-size: " + valueFontSize + "pt;");
        valueEdit.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                // Handle any change on the textField
                textFieldHandler(valueEdit, t, t1);
            }

        });
        this.add(valueEdit, 0, 1);
    }

    /**
     * Override method of IhmCell used to set the main value
     *
     * <p>
 This method will check wether the value is correct or not see boolean
 IhmCell.checkValue(int value)</p>
     *
     * @param value is an integer which has to be between [1,9]
     * @return Nothing
     * @throws Nothing
     */
    @Override
    public void setValue(int value) {
        //first check the value
        if (checkValue(value)) //set the value
        {
            valueEdit.setText(String.valueOf(value));
        }
    }

    /**
     * Set the addles add-button visibility
     *
     * @param visible will define whether the button is visible or not
     * @return Nothing
     * @throws Nothing
     */
    private void setAddlesAddButtonVisible(boolean visible) {
        addlesAddButton.setVisible(visible);// Use to show/hide it
        addlesAddButton.setManaged(visible);// Use to fix/unfix it on the layout
    }

    /**
     * Is called when the user clicks on the addles add-button.
     *
     * <p>
     * It will add a new addle if there is enough room</p>
     *
     * @param t an ActionEvent
     * @return Nothing
     * @throws Nothing
     */
    protected void addlesAddButtonHandler(ActionEvent t) {
        System.out.println("addlesAddButtonHandler(): " + addlesEditList.size() + " ?<? " + addlesMaxNumbers);
        // Check if there is enough rooms on the top of the  layout
        if (addlesEditList.size() < addlesMaxNumbers) {
            // Add a new addles to the list and to the addlesLayout
            final TextField addle = new TextField();
            addle.setMaxSize(addlesSizeX, addlesSizeY);
            addle.setAlignment(Pos.CENTER);
            addle.setStyle("-fx-font-size: " + addlesFontSize + "pt;");
            addle.textProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    // Handle any change on the textField
                    textFieldHandler(addle, t, t1);

                    // Delete it if the user cleared it
                    if (t1.isEmpty() && !t.isEmpty()) {
                        deleteAddle(addle);
                    }
                }
            });

            addle.setOnKeyTyped(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    // Doesn't Work on mac, to test on Windows

                    // Handles a key typed
                    System.out.println("onKeyTyped(" + t.getCode().toString() + ")");
                    if (t.isConsumed()) {
                        return;
                    }

                    //Handle a cell-suppress request
                    if (t.getCode() == KeyCode.DELETE) //delete cell
                    {
                        deleteAddle(addle);
                    } else if (t.getCode() == KeyCode.BACK_SPACE && addle.getText().isEmpty()) {
                        if (deleteAddle(addle)) {
                            t.consume();
                        }
                    }
                }

            });

            addlesEditList.add(addle);
            //Add it to the before-the-last position (which is the addles add-button)
            addlesLayout.getChildren().add(addlesLayout.getChildren().size() - 1, addle);

            // Hide the addles add-button if there is no more rooms after adding the new one
            if (addlesEditList.size() == addlesMaxNumbers) {
                setAddlesAddButtonVisible(false);
            }

            // Set Focus on the addles created
            addle.requestFocus();
        }
    }

    /**
     * Called when the user change a TextField content
     *
     * <p>
     * Will check what thre user enter in the TextField and apply or not a
     * correction on it</p>
     *
     * @param tf a TextField that trigged the event
     * @param t a string which was in the TextField before the event
     * @param t1 a string which is what is in the TextField
     * @return Nothing
     * @throws Nothing
     */
    protected void textFieldHandler(TextField tf, String t, String t1) {
        //Try to parse the input
        String text = t1;

        // Limit the size of TextField
        if (text.length() > 1) {
            tf.setText(t1.substring(t1.length() - 1)); //récupère le dernier caractère
            return;
        }

        int val = 0;
        try {
            val = Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            System.err.println("textFieldHandler(\"" + t1 + "\"): " + ex.getMessage());
        }

        // Check if the input value is correct
        if (!checkValue(val)) {
            tf.clear(); //if not then clear it
        }
    }

    /**
     * Use to delete an addle and set the focus on another addle
     *
     * <p>
     * Delete the addle and pass the focus to the previous or to the next addle
     * or to the add-button</p>
     *
     * @param addle is the addle to delete
     * @return Nothing
     * @throws Nothing (TODO: throw a NullPointerException)
     */
    private boolean deleteAddle(TextField addle) {
        int index = addlesEditList.indexOf(addle);

        //delete the addle and check if it succeeds
        if (index >= 0 && addlesLayout.getChildren().remove(addle) && (addlesEditList.remove(index) == addle)) {
            // Hide the addles add-button if there is no more rooms after adding the new one
            if (addlesEditList.size() < addlesMaxNumbers) {
                setAddlesAddButtonVisible(true);
            }

            // Set focus on the previous addle if possible or the next addle if possible otherwise on the button
            if (addlesEditList.size() > 0) {
                if (index - 1 >= 0 && index - 1 < addlesEditList.size()) // Focus on the previous addle
                {
                    addlesEditList.get(index - 1).requestFocus();
                } else if (index < addlesEditList.size()) // Focus on the next addle
                {
                    addlesEditList.get(index).requestFocus();
                }
            } else //Focus on the button
            {
                addlesAddButton.requestFocus();
            }
        } else {
            return false;
        }
        return true;
    }
}
