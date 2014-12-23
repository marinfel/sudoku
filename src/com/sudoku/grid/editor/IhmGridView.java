/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

import com.sudoku.data.model.Grid;
import com.sudoku.grid.gridcells.IhmGridLines.Flags;
import com.sudoku.grid.gridcells.IhmGridLinesCompleted;
import com.sudoku.grid.popups.IhmPopupsList;
import com.sudoku.grid.manager.IhmGridLayout;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

/**
 * @author Mehdi Kane and Celine To Mother class of IhmGridEditor and
 * IhmGridPlayer, Generates pop-up list
 */
public abstract class IhmGridView extends IhmGridLayout {

  public IhmGridView(Flags flagStatus, Grid gr, int size) {
    super(flagStatus, gr, size);

    IhmPopupsList.init(150.0, 500.0, 10);
    final IhmPopupsList popupList = IhmPopupsList.getInstance();

    ((VBox) border.getRight()).getChildren().add(popupList);
    popupList.addPopup("test", "texte de test", 10);
    
  }
}
