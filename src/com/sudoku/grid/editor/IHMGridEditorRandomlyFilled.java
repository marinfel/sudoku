/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

import static javafx.geometry.Pos.CENTER_LEFT;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author mecton08
 */
public class IHMGridEditorRandomlyFilled extends IHMGridEditor {

  private Button deleteCells;
  private TextField deleteCellsField;

  public IHMGridEditorRandomlyFilled(String title) {
    super(title);

    deleteCells = new Button("Delete Cells");
    deleteCellsField = new TextField("0");

    VBox leftLayout = (VBox) super.getBorder().getLeft();
    leftLayout.getChildren().addAll(deleteCellsField, deleteCells);

  }

}
