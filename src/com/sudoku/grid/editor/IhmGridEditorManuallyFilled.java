/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

import com.sudoku.data.manager.GridManager;
import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.Grid;
import com.sudoku.grid.gridcells.IhmGridLines;

/**
 * @author Celine To and Mehdi Kane Instantiate an empty grid that has to be
 * field by the user
 */
public class IhmGridEditorManuallyFilled extends IhmGridEditor {

  public IhmGridEditorManuallyFilled() {
    //IhmGridEditor's constructor
    super(IhmGridLines.ALL_EDITABLE, new Grid("", UserManager.getInstance().getLoggedUser()), 500);
  }

}
