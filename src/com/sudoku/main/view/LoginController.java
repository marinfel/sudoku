/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sudoku.main.view;

import com.sudoku.comm.CommunicationManager;
import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

/**
 * @author MOURAD
 */
public class LoginController implements Initializable, ControlledScreen {

  ScreensController myController;
  @FXML
  private ImageView avatar;
  private Image image;
  @FXML
  private TextField user;
  @FXML
  private PasswordField passwd;
  @FXML
  private Button login;
  @FXML
  private Button register;
  @FXML
  private Label userName;

  private UserManager userManag;
  @Override
  public void initialize(URL url, ResourceBundle rb) {
//        image = new Image("sudoku.jpg");
//        avatar = new ImageView();
//        avatar.setImage(image);
     //public User authenticate(String pseudo, String password)
     
  }

  @Override
  public void setScreenParents(ScreensController screenParent) {
    myController = screenParent;
  }
  @FXML
  private void goToProgram(ActionEvent event) {
    if(UserManager.getInstance().getLoggedUser() != null)
    {
        myController.setScreen(SudukoIHM.programID);
    }
  }

  @FXML
  private void goToRegister(ActionEvent event) {
    myController.setScreen(SudukoIHM.registerID);
  }
  
  @FXML
  private void authenticate(ActionEvent event){
    userManag = UserManager.getInstance();
    System.out.println("in authenticate "+userManag.getLocalUsers().size());
    try{
      User authUser = userManag.authenticate(user.getText(), passwd.getText());
      if(authUser == null){
        userName.setText("Echec de connexion : vérifiez vos identifiants.");
      }
      else{
        userName.setText("Veuillez patienté "+userManag.getLoggedUser());
        System.out.println("Veuillez patienté "+authUser.getPseudo());
        goToProgram(event);
      }
    }
    catch(NoSuchAlgorithmException e){
      userName.setText("Erreur lors de l'exécution : problème d'encryptage");
      return;
    }
    catch(UnsupportedEncodingException e){
      userName.setText("Erreur lors de l'exécution : problème d'encodage");
      return;
    }
    catch(IOException e){
      userName.setText("Erreur lors de l'exécution : problème de connexion");
    }
  }
}
