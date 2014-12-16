/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.Grid;
import com.sudoku.grid.gridcells.IhmGridLines;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

/**
 * @author Celine To and Mehdi Kane Instantiate an empty grid that has to be
 * field by the user
 */
public class IhmGridEditorManuallyFilled extends IhmGridEditor {

  public IhmGridEditorManuallyFilled() {
    //IhmGridEditor's constructor
    super(IhmGridLines.ALL_EDITABLE, new Grid("", UserManager.getInstance().getLoggedUser()), 500);

    /*
     Handler that cancels the grid creation process and displays the editing
     choice screen
     */
    cancelBtn.setOnAction(
      new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          resetIhmEditor(new IhmGridLines(new Grid("", UserManager.getInstance().getLoggedUser()),
              500, IhmGridLines.ALL_EDITABLE));
        }
      });
  }

}
