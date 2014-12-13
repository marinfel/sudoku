/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

import com.sudoku.grid.gridcells.IhmCell;
import com.sudoku.grid.gridcells.IhmCellView;
import com.sudoku.grid.gridcells.IhmGridLines;
import com.sudoku.grid.popups.IhmPopupsList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Collections;
import java.util.LinkedList;

import static com.sudoku.data.factory.GridFactory.generateRandomGrid;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

/**
 * @author Celine To and Mehdi Kane Instantiates a completely filled grid, the
 * user can choose to hide cells by clicking on a cell or by typing a number of
 * cells to be randomly removed
 */
public class IhmGridEditorRandomlyFilled extends IhmGridEditor {

  private Button deleteCells;
  private TextField deleteCellsField;

  /**
   * Constructor changes the padding of the borderPane and inserts controls in
   * the leftPane to remove cells.
   */
  public IhmGridEditorRandomlyFilled() {
    //IhmGridEditor's constructor
    super(IhmGridLines.ALL_VIEW.add(IhmGridLines.FIXED_HIDABLE), generateRandomGrid(), 500);

    // Button with the number of cases that the user wants to hide randomly
    deleteCells = new Button("Delete Cells");
    deleteCellsField = new TextField("0");

    border.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));

    VBox leftLayout = (VBox) border.getLeft();
    leftLayout.getChildren().addAll(deleteCellsField, deleteCells);
    leftLayout.setAlignment(Pos.TOP_CENTER);

    //Handler of the delete button
    deleteCells.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        deleteCells();
      }
    });

    //Handler of the TextField to delete cells
    deleteCellsField.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        deleteCells();
      }
    });

  }

  /**
   * Randomly hides a number of cells given in the deleteCellsField
   */
  public void deleteCells() {
    IhmCell[][] cells = gridLines.getCells();
    int number = Integer.parseInt(deleteCellsField.getText());
    int nbNotHiddenCells = 0;

    //Counts the number of visible cells
    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells[i].length; j++) {
        if (!((IhmCellView) cells[i][j]).isHidden()) {
          nbNotHiddenCells++;
        }
      }
    }

    //Checks if the number of cells to delete is higher than the # of visible cells
    if ((nbNotHiddenCells - number) < 0) {
      String title = new String("Not Enough Filled Cells");
      String text = new String(
        "You can't hide that many cells");
      IhmPopupsList.getInstance().addPopup(title, text, 10);
    } else if (number < 0) { //Checks if the number of cells to delete is positive
      String title = new String("Negative Value");
      String text = new String(
        "Enter a positive number");
      IhmPopupsList.getInstance().addPopup(title, text, 10);
    } else { //Hides the desired number of cells
      LinkedList<IhmCellView> notHiddenCells = new LinkedList<IhmCellView>();
      //Makes a list of the visible cells
      for (int i = 0; i < cells.length; i++) {
        for (int j = 0; j < cells[i].length; j++) {
          IhmCellView tmp = (IhmCellView) cells[i][j];
          if (!tmp.isHidden()) {
            notHiddenCells.add(tmp);
          }
        }
      }
      Collections.shuffle(notHiddenCells); //Shuffles the visible cells
      for (int i = 0; i < number; i++) {
        (notHiddenCells.poll()).setHidden(true); //Hide the cells
      }
    }
  }

}
