/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.tests;

import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.Grid;
import com.sudoku.grid.editor.IhmGridEditorManuallyFilled;
import com.sudoku.grid.editor.IhmGridEditorRandomlyFilled;
import com.sudoku.grid.editor.IhmGridView;
import com.sudoku.grid.ihm_grid_player.IhmGridPlayer;
import com.sudoku.grid.ihm_popups.IhmPopupsList;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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

    IhmGridView ihm_test;

    //ihm_test = new IhmGridEditorRandomlyFilled();
    ihm_test = new IhmGridEditorManuallyFilled();
    //ihm_test = new IhmGridPlayer(new Grid("", UserManager.getInstance().getLoggedUser()));

    Scene scene = new Scene(ihm_test, 800, 1000);
    //Scene scene = new Scene(ihm_test2, 800, 1000);
    //Scene scene = new Scene(ihm_test3, 800, 1000);

    primaryStage.setTitle("Sudoku Editor");
    primaryStage.setScene(scene);
    primaryStage.show();

    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

      @Override
      public void handle(WindowEvent t) {
        if (t.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
          IhmPopupsList.getInstance().finalize();
        }
      }

    });
  }

}
