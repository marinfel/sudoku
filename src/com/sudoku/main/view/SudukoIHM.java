/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.main.view;

import com.sudoku.grid.popups.IhmPopupsList;
import com.sudoku.data.manager.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.stage.WindowEvent;

/**
 * @author MOURAD
 */
public class SudukoIHM extends Application {

  public static String loginID = "login";
  public static String loginFile = "Login.fxml";
  public static String registerID = "Register";
  public static String registerFile = "Register.fxml";
  public static String programID = "program";
  public static String programFile = "FXMLDocument.fxml";
  public static String fillGridID = "FillAndCreate";
  public static String fillGridFile = "FillAndCreate.fxml";
  public static String fromFullGridID = "FromFullGrid";
  public static String fromFullGridFile = "FromFullGrid.fxml";
  public static String gridPlayerGameID = "GridPlayerGame";
  public static String gridPlayerGame = "GridPlayerGame.fxml";

  private ImageView avatar;
  private Image image;

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

//        image = new Image(SudukoIHM.class.getResourceAsStream("veng.png"));
//        avatar = new ImageView();
//        avatar.setImage(image);
    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    ScreensController mainContainer = new ScreensController();
    mainContainer.loadScreen(SudukoIHM.loginID, SudukoIHM.loginFile);
    mainContainer.loadScreen(SudukoIHM.registerID, SudukoIHM.registerFile);
    mainContainer.loadScreen(SudukoIHM.programID, SudukoIHM.programFile);
    mainContainer.loadScreen(SudukoIHM.fillGridID, SudukoIHM.fillGridFile);
    mainContainer.loadScreen(SudukoIHM.fromFullGridID, SudukoIHM.fromFullGridFile);
    mainContainer.loadScreen(SudukoIHM.fillGridID, SudukoIHM.fillGridFile);
    mainContainer.loadScreen(SudukoIHM.gridPlayerGameID, SudukoIHM.gridPlayerGame);
    mainContainer.setScreen(SudukoIHM.loginID);
    Group root = new Group();
    root.getChildren().addAll(mainContainer);
    Scene scene = new Scene(root);
    primaryStage.setTitle("Sudoku LO23");
    primaryStage.setResizable(true);
    primaryStage.sizeToScene();
    primaryStage.setScene(scene);
    primaryStage.setHeight(primaryScreenBounds.getHeight() * 0.8);
    primaryStage.setWidth(primaryScreenBounds.getWidth() * 0.8);
    primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {

      @Override
      public void handle(WindowEvent t) {
        System.out.println("By by by");
        if (IhmPopupsList.getInstance() != null) {
          IhmPopupsList.getInstance().killAllTimers();
        }
        if (DataManager.getInstance() != null) {
          DataManager.getInstance().saveToJson();
        }
      }

    });
    primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent t) {
        DataManager datamng = DataManager.getInstance();
        UserManager usrManag = UserManager.getInstance();
        GridManager grdMang = GridManager.getInstance();
        //DataManager.buildFromJson();
        System.out.println("Biiiiiiiiiiiiiiiinggggg");
      }
    });
    primaryStage.show();
  }
}
