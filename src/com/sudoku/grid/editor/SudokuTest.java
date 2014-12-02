/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

import com.sudoku.data.model.Grid;
import com.sudoku.grid.ihm_grid_player.IhmGridPlayer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author mecton08
 */
public class SudokuTest extends Application {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {

    IhmGridEditorRandomlyFilled ihm_test;
    IhmGridEditorManuallyFilled ihm_test2;
    IhmGridPlayer ihm_test3;

    ihm_test = new IhmGridEditorRandomlyFilled();
    ihm_test2 = new IhmGridEditorManuallyFilled();
    ihm_test3 = new IhmGridPlayer(new Grid());

    Scene scene = new Scene(ihm_test, 800, 1000);
    //Scene scene = new Scene(ihm_test2, 800, 1000);
    //Scene scene = new Scene(ihm_test3, 800, 1000);

    primaryStage.setTitle("Sudoku Editor");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

}
