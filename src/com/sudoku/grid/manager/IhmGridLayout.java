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
import com.sudoku.grid.ihm_popups.IhmPopupsList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import java.util.Vector;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * @author Mélie
 */
public abstract class IhmGridLayout extends StackPane {

  //protected int sceneHeight=200;
  //protected int sceneLength=200;
  protected final int sizeTitle = 25;
  protected Label title;
  protected Grid grid;
  protected IhmGridLines gridLines;
  protected final BorderPane border;

  //Grid grille;
  public IhmGridLayout(Flags flagStatus, Grid gr) {
    grid = gr;
	//sceneHeight=size;
	//sceneLength=size;
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

    // center a faire
    border.setTop(topHBox);
    border.setLeft(leftVBox);
    border.setBottom(bottomHBox);
    border.setRight(rightVBox);
    border.setCenter(centerVBox);

    //VBox centerLayout = (VBox)border.getCenter();
    //centerLayout.getChildren().add(sudokuGrid);
    centerVBox.getChildren().add(gridLines);

  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title.getText();
  }

  public HBox getStarsBox(int numberOfStars) {
    int i;
    if (numberOfStars < 0) {
      numberOfStars = 0;
    }
    if (numberOfStars > 5) {
      numberOfStars = 5;
    }
    Vector<StarView> grades = new Vector();
    int num = (int) Math.floor(numberOfStars);
    for (i = 0; i <= num; i++) {
      grades.add(new StarView(1));
    }
    if (Math.round(numberOfStars * 2) % 2 != 0) {
      grades.add(new StarView(2));
    }
    for (i = grades.size(); i < 6; i++) {
      grades.add(new StarView(3));
    }
    HBox box = new HBox();
    for (i = 1; i < 6; i++) {
      box.getChildren().add(grades.get(i).getStar());
    }
    return box;
  }
}
