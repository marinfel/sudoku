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
import com.sudoku.data.model.User;
import com.sudoku.data.manager.UserManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import java.util.regex.*;
/**
 * @author MOURAD
 */
public class RegisterController implements Initializable, ControlledScreen {

  // Partie JulianC
  ScreensController myController;
  private UserManager userManag;
  @FXML
  private Label userName;
  @FXML
  private TextField birthDate;
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
//public User createUser(String pseudo, String brutPassword, Date birthDate, String profilePicturePath)

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
      userManag = UserManager.getInstance();
  }

  private void clean() {
    user.setText(null);
    passwd.setText(null);
    birthDate.setText(null);
  }

  @Override
  public void setScreenParents(ScreensController screenParent) {
    myController = screenParent;
  }
  @FXML
  private void createUserAndLog(ActionEvent event) throws IOException{
    System.out.println("in createUser");
    if(!user.getText().isEmpty() && !passwd.getText().isEmpty()&& !birthDate.getText().isEmpty()){
        Pattern datePattern = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
        Matcher matcher = datePattern.matcher(birthDate.getText());
        if(!matcher.matches())
        {
            userName.setText("Format de date invalide. Entrez la date de naissance au format DD/MM/AAAA.");
            return;
        }
        try{
            String[] dateComp = birthDate.getText().split("/");
            //if(userManag.getLocalUsers() && userManag.getDistantUsers())
            Date dob = new Date(Integer.parseInt(dateComp[2])-1900, 
                Integer.parseInt(dateComp[1])-1, Integer.parseInt(dateComp[0]));
            User temp = new User(user.getText(), passwd.getText(), dob, "");
            if(!userManag.getDistantUsers().contains(temp) && !userManag.getLocalUsers().contains(temp)){
                User newUser;           
                newUser = UserManager.getInstance().createUser(user.getText(),
                    passwd.getText(), dob, "");
                UserManager.getInstance().authenticate(newUser.getPseudo(), newUser.getPassword());
            userName.setText("");
            goToLogin(event);
            }
            else{
                userName.setText("L'utilisateur existe déjà !");
            }
        }    
        catch(NoSuchAlgorithmException e){
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, e);
           return; 
        }
        catch(UnsupportedEncodingException e){
           Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, e);
           return; 
        } catch (UnknownHostException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    else{
        userName.setText("Veuillez entrer des informations valides.");
        return;
    }
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
