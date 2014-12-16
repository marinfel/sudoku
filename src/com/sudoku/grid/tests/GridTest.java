/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.tests;

import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.User;
import com.sudoku.grid.editor.IhmGridEditorManuallyFilled;
import com.sudoku.grid.gridcells.IhmCellEditable;
import com.sudoku.grid.gridcells.IhmGridLines;
import com.sudoku.grid.gridcells.IhmGridLinesCompleted;
import com.sudoku.grid.player.IhmGridPlayer;
import com.sudoku.grid.popups.IhmPopupsList;
import com.sudoku.grid.preview.IhmGridPreview;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Marc-Antoine
 */
public class GridTest extends Application {

  @Override
  public void start(Stage primaryStage) {
    HBox root = new HBox();
    Scene scene = new Scene(root, 700, 500);

    primaryStage.setTitle("Sudoku Tests");
    primaryStage.setScene(scene);

    /*
     IhmCellEditable ce = new IhmCellEditable(50, 50, 10, 20);
     root.getChildren().add(ce);
     //*/
    /*
     TextField ce = new TextField("1");
     ce.setMaxSize(50, 50);
     ce.setAlignment(Pos.CENTER);
     ce.setStyle("-fx-font-size: 20pt;");
     root.getChildren().add(ce);
     //*/
    //*
    Grid g = new Grid("blabla", UserManager.getInstance().getLoggedUser());
    /*for (byte x = 0; x < 9; x++) {
     for (byte y = 0; y < 9; y++) {
     if (Math.random() > 0.5) {
     g.setFixedCell(x, y, (byte) (((x + y) % 9) + 1));
     } else {
     g.setEmptyCell(x, y);
     }
     }
     }*/

    /* Sudoku de test:
     |4 9|31 |
     |37 |4 5| 9
     |8 5|   |4
     | 9 |   |8 5
     |   |592|
     |1 3|   | 7
     |  6|   |3 7
     | 1 |2 3| 84
     |   | 81|9 6
     Solution:
     | 6 |  8|752
     |  1| 2 |6 8
     | 2 |679| 31
     |6 2|137| 4
     |748|   |163
     | 5 |846|2 9
     |28 |954| 1
     |9 7| 6 |5
     |534|7  | 2
     */
    g.setFixedCell((byte) 0, (byte) 0, (byte) 4);
    g.setFixedCell((byte) 0, (byte) 1, (byte) 3);
    g.setFixedCell((byte) 0, (byte) 2, (byte) 8);
    g.setFixedCell((byte) 0, (byte) 5, (byte) 1);
    g.setFixedCell((byte) 1, (byte) 1, (byte) 7);
    g.setFixedCell((byte) 1, (byte) 3, (byte) 9);
    g.setFixedCell((byte) 1, (byte) 7, (byte) 1);
    g.setFixedCell((byte) 2, (byte) 0, (byte) 9);
    g.setFixedCell((byte) 2, (byte) 2, (byte) 5);
    g.setFixedCell((byte) 2, (byte) 5, (byte) 3);
    g.setFixedCell((byte) 2, (byte) 6, (byte) 6);
    g.setFixedCell((byte) 3, (byte) 0, (byte) 3);
    g.setFixedCell((byte) 3, (byte) 1, (byte) 4);
    g.setFixedCell((byte) 3, (byte) 4, (byte) 5);
    g.setFixedCell((byte) 3, (byte) 7, (byte) 2);
    g.setFixedCell((byte) 4, (byte) 0, (byte) 1);
    g.setFixedCell((byte) 4, (byte) 4, (byte) 9);
    g.setFixedCell((byte) 4, (byte) 8, (byte) 8);
    g.setFixedCell((byte) 5, (byte) 1, (byte) 5);
    g.setFixedCell((byte) 5, (byte) 4, (byte) 2);
    g.setFixedCell((byte) 5, (byte) 7, (byte) 3);
    g.setFixedCell((byte) 5, (byte) 8, (byte) 1);
    g.setFixedCell((byte) 6, (byte) 2, (byte) 4);
    g.setFixedCell((byte) 6, (byte) 3, (byte) 8);
    g.setFixedCell((byte) 6, (byte) 6, (byte) 3);
    g.setFixedCell((byte) 6, (byte) 8, (byte) 9);
    g.setFixedCell((byte) 7, (byte) 1, (byte) 9);
    g.setFixedCell((byte) 7, (byte) 5, (byte) 7);
    g.setFixedCell((byte) 7, (byte) 7, (byte) 8);
    g.setFixedCell((byte) 8, (byte) 3, (byte) 5);
    g.setFixedCell((byte) 8, (byte) 6, (byte) 7);
    g.setFixedCell((byte) 8, (byte) 7, (byte) 4);
    g.setFixedCell((byte) 8, (byte) 8, (byte) 6);

    IhmGridLines glines = new IhmGridLines(g, 500, IhmGridLines.ALL_VIEW);
    glines.addEventHandler(IhmGridLinesCompleted.GRID_COMPLETED, new EventHandler<IhmGridLinesCompleted>() {

      @Override
      public void handle(IhmGridLinesCompleted t) {
        //Grid completed
        System.out.println("\n########## Grid Completed! ##########");
      }

    });
    root.getChildren().add(glines);

    //*
    IhmPopupsList.init(200, 700, 6);
    IhmPopupsList plist = IhmPopupsList.getInstance();
    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

      @Override
      public void handle(WindowEvent t) {
        if (t.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
          IhmPopupsList.getInstance().killAllTimers();
        }
      }

    });
    root.getChildren().add(plist);
    //*/

    /*
     IhmGridPreview ce = new IhmGridPreview("Test Preview", 5);
     root.getChildren().add(ce);
     //*/
    /*
     IhmGridEditorManuallyFilled ce = new IhmGridEditorManuallyFilled();
     root.getChildren().add(ce);
     //*/
    primaryStage.show();
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}
