/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

import static com.sudoku.data.factory.GridFactory.generateRandomGrid;
import com.sudoku.data.model.Grid;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author mecton08
 */
public class SudokuTest extends Application {

  @Override
  public void start(Stage primaryStage) {

    IhmGridEditorRandomlyFilled ihm_test;
    IhmGridEditorManuallyFilled ihm_test2;

    ihm_test = new IhmGridEditorRandomlyFilled();
    ihm_test2 = new IhmGridEditorManuallyFilled();

    Scene scene = new Scene(ihm_test, 800, 1000);
    //Scene scene = new Scene(ihm_test2, 800, 1000);

    primaryStage.setTitle("Sudoku Editor");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

}
