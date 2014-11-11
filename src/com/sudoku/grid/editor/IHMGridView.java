/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

import com.sudoku.data.model.Grid;
import com.sudoku.grid.manager.IhmGridLayout;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines;
import static com.sudoku.grid.ihm_grid_cells.IhmGridLines.CellStatus.FIT_GRID;
import com.sudoku.grid.ihm_popups.IhmPopupsList;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author mecton08
 */
public abstract class IHMGridView extends IhmGridLayout {
    //protected textItemInput textInput;
  //protected textItemView textView;

  protected enum mode {

    Play,
    Edit
  }

  protected final BorderPane border;

  public IHMGridView(String title) {
    super(title);
    border = new BorderPane();
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

    IhmGridLines sudokuGrid = new IhmGridLines(getGrid(), 500, FIT_GRID);
        //VBox centerLayout = (VBox)border.getCenter();
    //centerLayout.getChildren().add(sudokuGrid);

    centerVBox.getChildren().add(sudokuGrid);

    final IhmPopupsList popupList = IhmPopupsList.getInstance(100, 500, 2);
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
    rightVBox.getChildren().add(popupList);

        //border.setCenter();
    // 
  }

  public BorderPane getBorder() {
    return border;
  }

}
