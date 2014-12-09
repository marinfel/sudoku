/*
 * To change this license header, choose License Headers in Project 
 * Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.manager;

import com.sudoku.data.model.Grid;
import com.sudoku.data.manager.UserManager;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines.Flags;
import com.sudoku.grid.ihm_grid_preview.StarView;
import com.sudoku.grid.ihm_grid_preview.StarsBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import java.util.Vector;

/**
 * @author Mélie
 */
public abstract class IhmGridLayout extends StackPane {

  protected static final int MAX_NUMBER_OF_STARS = 5;
  protected final int sceneHeight = 200;
  protected final int sceneLength = 200;
  protected final int sizeTitle = 25;
  protected Label title;
  protected Grid grid;
  protected IhmGridLines gridLines;

  //Grid grille;
  public IhmGridLayout() {
  }

  public IhmGridLayout(Flags flagStatus, Grid gr) {
    grid = gr;
    gridLines = new IhmGridLines(grid, 500, flagStatus);
    title = new Label("");
    title.setFont(Font.font("Verdana", sizeTitle));
    getChildren().add(title);
    StackPane.setAlignment(title, Pos.TOP_CENTER);
    grid = new Grid("", UserManager.getInstance().getLoggedUser());
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title.getText();
  }

  public StarsBox getStarsBox() {
    return new StarsBox(MAX_NUMBER_OF_STARS);
  }
}
