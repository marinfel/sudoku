/*
 * To change this license header, choose License Headers in Project 
 * Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.manager;

import com.sudoku.data.model.Grid;
import com.sudoku.data.manager.UserManager;
import com.sudoku.grid.gridcells.IhmGridLines;
import com.sudoku.grid.gridcells.IhmGridLines.Flags;
import com.sudoku.grid.gridcells.IhmGridLinesCompleted;
import com.sudoku.grid.preview.StarsBox;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * @author Mehdi Kane, Celine To, Laetitia Leichtnam, Amelie Groud Mother class
 * of Grid View Common IHM : border pane including grid, title field
 */
public abstract class IhmGridLayout extends StackPane{

  protected static final int MAX_NUMBER_OF_STARS = 5;

  protected final int sizeTitle = 25;
  protected Label title;
  protected Grid grid;
  protected IhmGridLines gridLines;
  protected final BorderPane border;

  //Grid grille;
  public IhmGridLayout(Flags flagStatus, Grid gr, int size) {
    grid = gr;

    gridLines = new IhmGridLines(grid, size, flagStatus);
    
    
    title = new Label("");
    title.setFont(Font.font("Verdana", sizeTitle));
    getChildren().add(title);
    StackPane.setAlignment(title, Pos.TOP_CENTER);
    grid = new Grid("", UserManager.getInstance().getLoggedUser());

    border = new BorderPane();
    getChildren().add(border);
    HBox topHBox = new HBox();
    VBox leftVBox = new VBox();
    HBox bottomHBox = new HBox();
    VBox rightVBox = new VBox();
    VBox centerVBox = new VBox();

    border.setTop(topHBox);
    border.setLeft(leftVBox);
    border.setBottom(bottomHBox);
    border.setRight(rightVBox);
    border.setCenter(centerVBox);

    centerVBox.getChildren().add(gridLines);

  }

  public String getTitle() {
    return title.getText();
  }

  public StarsBox getStarsBox() {
    return new StarsBox(MAX_NUMBER_OF_STARS);
  }

}
