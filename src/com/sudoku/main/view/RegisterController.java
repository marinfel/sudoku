/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sudoku.main.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author MOURAD
 */
public class RegisterController implements Initializable, ControlledScreen {

  // Partie JulianC
  ScreensController myController;

  @FXML
  private TextField user;
  @FXML
  private PasswordField passwd;
  @FXML
  private TextField nom;
  @FXML
  private TextField prenom;
  @FXML
  private Button login;
  @FXML
  private Button register;


  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  private void clean() {
    user.setText(null);
    passwd.setText(null);
    nom.setText(null);
    prenom.setText(null);
  }

  @Override
  public void setScreenParents(ScreensController screenParent) {
    myController = screenParent;
  }

  @FXML
  private void goToProgram(ActionEvent event) {
    clean();
    myController.setScreen(SudukoIHM.programID);
  }

  @FXML
  private void goToLogin(ActionEvent event) {
    myController.setScreen(SudukoIHM.loginID);
  }

}
