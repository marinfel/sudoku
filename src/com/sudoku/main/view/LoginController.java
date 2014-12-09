/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sudoku.main.view;


import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.User;
import com.sudoku.data.sample.DataSample;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author JULIANC
 */
public class LoginController implements Initializable, ControlledScreen {
            
    @FXML   private ImageView avatar;
    private Image image;
    public DataSample instance;
    public List<User> ListUsers = new LinkedList<>();
        
    // Partie JulianC
    ScreensController myController;    
    public UserManager instUserM;
    
    @FXML   private TextField user;
    @FXML   private PasswordField passwd;
    @FXML   private Button login;
    @FXML   private Button register;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = new DataSample();
        instance.exec();        
        ListUsers = instance.getUserList();
        
//        image = new Image("sudoku.jpg");
//        avatar = new ImageView();
//        avatar.setImage(image);
    }    

    @Override
    public void setScreenParents(ScreensController screenParent) {
        myController = screenParent;
    }
    
    @FXML
    private void goToProgram(ActionEvent event) {
        myController.setScreen(SudukoIHM.programID);
        /*try {
            instUserM.authenticate(user.getText(), passwd.getText());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            System.out.println("Erreur loggin");
        }*/               
    }   
    
    @FXML
    private void goToRegister(ActionEvent event) {
        myController.setScreen(SudukoIHM.registerID);
    }   
    
    @FXML
    private void chooseFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        //fileText.setText(""+file);        
    }       
}
