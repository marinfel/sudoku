/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sudoku.main.view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
    ScreensController mainContainer = new ScreensController();
    mainContainer.loadScreen(SudukoIHM.loginID, SudukoIHM.loginFile);
    mainContainer.loadScreen(SudukoIHM.registerID, SudukoIHM.registerFile);
    mainContainer.loadScreen(SudukoIHM.programID, SudukoIHM.programFile);
    mainContainer.loadScreen(SudukoIHM.fillGridID, SudukoIHM.fillGridFile);
    mainContainer.loadScreen(SudukoIHM.fromFullGridID, SudukoIHM.fromFullGridFile);
    mainContainer.setScreen(SudukoIHM.loginID);
    Group root = new Group();
    root.getChildren().addAll(mainContainer);
    Scene scene = new Scene(root);
    primaryStage.setTitle("Sudoku LO23");
    primaryStage.setResizable(false);
    primaryStage.sizeToScene();
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
