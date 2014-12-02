/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

import com.sudoku.data.manager.GridManager;
import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.Grid;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines;

/**
 * @author mecton08
 */
public class IhmGridEditorManuallyFilled extends IhmGridEditor {

  public IhmGridEditorManuallyFilled() {
    super(IhmGridLines.ALL_EDITABLE, new Grid("", UserManager.getInstance().getLoggedUser()));
  }

}
