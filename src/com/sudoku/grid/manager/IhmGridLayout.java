/*
 * To change this license header, choose License Headers in Project 
 * Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.manager;

import com.sudoku.data.model.Grid;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines.Flags;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
/**
 *
 * @author Mélie
 */
public abstract class IhmGridLayout extends StackPane{

  protected Label title;
  protected final int sceneHeight = 200;
  protected final int sceneLength = 200;
  protected final int sizeTitle = 25;
  protected Grid grid;
  protected IhmGridLines gridLines;

  //Grid grille;

  public IhmGridLayout() {}

  public IhmGridLayout(String ttl, Flags flagStatus, Grid gr) {
    grid = gr;
    gridLines = new IhmGridLines(grid, 500, flagStatus);
    title = new Label(ttl);
    title.setFont(Font.font ("Verdana", sizeTitle));
    getChildren().add(title);
    StackPane.setAlignment(title, Pos.TOP_CENTER);
    grid = new Grid();
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title.getText();
  }
}
