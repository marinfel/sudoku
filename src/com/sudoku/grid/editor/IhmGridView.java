/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

import com.sudoku.data.model.Grid;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines.Flags;
import com.sudoku.grid.ihm_popups.IhmPopupsList;
import com.sudoku.grid.manager.IhmGridLayout;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author mecton08
 */
public abstract class IhmGridView extends IhmGridLayout {

  protected final BorderPane border;

  public IhmGridView(Flags flagStatus, Grid gr) {
    super(flagStatus, gr);
    border = new BorderPane();
    getChildren().add(border);
    HBox topHBox = new HBox();
    VBox leftVBox = new VBox();
    VBox bottomHBox = new VBox();
    VBox rightVBox = new VBox();
    VBox centerVBox = new VBox();

    // center a faire
    border.setTop(topHBox);
    border.setLeft(leftVBox);
    border.setBottom(bottomHBox);
    border.setRight(rightVBox);
    border.setCenter(centerVBox);

    //VBox centerLayout = (VBox)border.getCenter();
    //centerLayout.getChildren().add(sudokuGrid);
    centerVBox.getChildren().add(gridLines);

    IhmPopupsList.init(150.0, 500.0, 10);
    final IhmPopupsList popupList = IhmPopupsList.getInstance();
    rightVBox.getChildren().add(popupList);
    popupList.addPopup("test", "texte de test", 10);
    //rightVBox.getChildren().add(new Label("Ceci est un test"));

    /* A IMPLEMENTER POUR LE BON FONCTIONNEMENT DE L'APPLICATION
     primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){

     @Override
     public void handle(WindowEvent t) {
     if(t.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST){
     popupList.finalize();
     }
     }

     });
     */
    //border.setCenter();
    //
  }

  //protected textItemInput textInput;
  //protected textItemView textView;
  protected enum mode {

    Play,
    Edit
  }

}
