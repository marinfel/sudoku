/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.tests;

import com.sudoku.data.model.Comment;
import static com.sudoku.data.factory.GridFactory.generateRandomGrid;
import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.Tag;
import com.sudoku.data.model.User;
import com.sudoku.grid.editor.IhmGridEditorManuallyFilled;
import com.sudoku.grid.editor.IhmGridEditorRandomlyFilled;
import com.sudoku.grid.manager.IhmGridLayout;
import com.sudoku.grid.preview.IhmGridDetailledPreview;
import com.sudoku.grid.player.IhmGridPlayer;
import com.sudoku.grid.popups.IhmPopupsList;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author Mehdi KANE, Céline TO, This is a test class for grid ihm (editor,
 * player and preview)
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

    IhmGridLayout ihm_test1;
    IhmGridLayout ihm_test2;
    IhmGridLayout ihm_test3;
    IhmGridLayout ihm_test4;
    IhmGridLayout ihm_test5;

    ihm_test1 = new IhmGridEditorRandomlyFilled();
    ihm_test2 = new IhmGridEditorManuallyFilled();
    ihm_test3 = new IhmGridPlayer(generateRandomGrid(20));
    //ihm_test4 = new IhmGridPreview(2, new Grid("", UserManager.getInstance().getLoggedUser()), 100);

    Grid g = generateRandomGrid();
    ArrayList<Tag> tagsList = new ArrayList<Tag>();
    tagsList.add(new Tag("tag1"));
    tagsList.add(new Tag("tag2"));
    g.setTags(tagsList);
    List<Comment> commentsList = new ArrayList<Comment>();
    Comment c1 = new Comment();
    c1.setComment("Comment1");
    Comment c2 = new Comment();
    c2.setComment("Comment2");
    commentsList.add(c1);
    commentsList.add(c2);
    g.setComments(commentsList);
    User user = UserManager.getInstance().getLoggedUser();
    g.setCreateUser(user);
    g.setTitle("test title");
    ihm_test5 = new IhmGridDetailledPreview(g, 200);

    //Scene scene = new Scene(ihm_test1 800, 1000);
    //Scene scene = new Scene(ihm_test2, 800, 1000);
    Scene scene = new Scene(ihm_test3, 800, 1000);
    //Scene scene = new Scene(ihm_test4, 800, 1000);
    //Scene scene = new Scene(ihm_test5, 300, 300);

    primaryStage.setTitle("Sudoku preview");
    primaryStage.setScene(scene);
    primaryStage.show();

    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

      @Override
      public void handle(WindowEvent t) {
//        if (t.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
//          IhmPopupsList.getInstance().killAllTimers();
//        }
      }

    });
  }

}
