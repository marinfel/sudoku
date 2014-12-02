/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

import com.sudoku.data.model.Grid;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines.Flags;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

/**
 *
 * @author mecton08
 */
public class IhmGridEditorManuallyFilled extends IhmGridEditor {

  public IhmGridEditorManuallyFilled(String title, Flags flagStatus, Grid gr) {
    super(title, flagStatus, gr);
  }

}
