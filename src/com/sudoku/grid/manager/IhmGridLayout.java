/*
 * To change this license header, choose License Headers in Project 
 * Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.manager;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import com.sudoku.data.model.Grid;
/**
 *
 * @author Mélie
 */
public abstract class IhmGridLayout extends StackPane{

  protected Label title;
  protected final int sceneHeight = 200;
  protected final int sceneLength = 200;
  protected Grid grid;

  //Grid grille;

  public IhmGridLayout(String ttl) {
    title = new Label(ttl);
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
