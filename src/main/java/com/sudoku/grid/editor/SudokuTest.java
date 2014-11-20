/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

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
        ihm_test = new IhmGridEditorRandomlyFilled("test", IhmGridLines.ALL_VIEW, new Grid());
        
        Scene scene = new Scene(ihm_test, 800, 1000);
        
        primaryStage.setTitle("Hello World!");
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
