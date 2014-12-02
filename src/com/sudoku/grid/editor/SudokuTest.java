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

    //Grid g1 = generateRandomGrid();
    //Grid g2 = new Grid();
//    for (int i = 0; i < 9; i++) {
//      for (int j = 0; j < 9; j++) {
//        g2.setFixedCell((byte) i, (byte) j, (byte) 8);
//      }
//    }
    ihm_test = new IhmGridEditorRandomlyFilled("test randomly filled", IhmGridLines.ALL_VIEW.add(IhmGridLines.FIXED_HIDABLE),
            generateRandomGrid());
    //ihm_test = new IhmGridEditorRandomlyFilled("test randomly filled", IhmGridLines.ALL_VIEW,
    //        generateRandomGrid());
    ihm_test2 = new IhmGridEditorManuallyFilled("test manually filled", IhmGridLines.ALL_EDITABLE,
            new Grid());

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
