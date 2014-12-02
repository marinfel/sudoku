/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sudoku.main.view;

import com.sudoku.data.manager.UserManager;
import java.net.URL;
import java.util.ResourceBundle;

import com.sudoku.data.sample.DataSample;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author JULIANC
 */
public class FXMLDocumentController implements Initializable, ControlledScreen {

    //Data
    public DataSample instance;
    public UserManager instUserM;

    @FXML   private Label userName;
    @FXML   private ImageView avatar;
    @FXML   private Button connexion;

    // Partie JulianC
    ScreensController myController;
    @FXML   private StackPane panes;
    @FXML   private Pane paneUser;
    @FXML   private Pane paneGroup;
    @FXML   private Button fillGrid;
    @FXML   private Button fromFullGrid;
    @FXML   private ListView listGroups;
    @FXML   private ListView listUsers;
    @FXML   private Label user;
    @FXML   private Label name;
    @FXML   private Label birthD;
    @FXML   private Label nombUsers;
    @FXML   private Button goToGrids;
    @FXML   private Button delFromGroup;
    @FXML   private Button newGroup;
    @FXML   private TextField nameGroup;
    @FXML   private Button delGroup;

    public static final ObservableList groups = FXCollections.observableArrayList();
    public static final ObservableList users  = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("test data");
        instUserM = UserManager.getInstance();
        instance = new DataSample();
        System.out.println("test data"+instance.a.getPseudo());
        userName.setText("Utilisateur : "+instUserM.getLoggedUser());

        assert panes != null : "fx:id=\"panes\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert fillGrid != null : "fx:id=\"fillGrid\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert fromFullGrid != null : "fx:id=\"fromFullGrid\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert paneUser != null : "fx:id=\"paneUser\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert paneGroup != null : "fx:id=\"paneGroup\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert listGroups != null : "fx:id=\"listGroups\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert listUsers != null : "fx:id=\"listUsers\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert user != null : "fx:id=\"user\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert birthD != null : "fx:id=\"birthD\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert goToGrids != null : "fx:id=\"goToGrids\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert delFromGroup != null : "fx:id=\"delFromGroup\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert newGroup != null : "fx:id=\"newGroup\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert nameGroup != null : "fx:id=\"nameGroup\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert delGroup != null : "fx:id=\"delGroup\" was not injected: check your FXML file 'FXMLDocument.fxml'.";

        //Ajouter des éléments aux listes groupes et utilisateurs
        groups.addAll("Global", "Amis", "Camarades");
        //groups.addAll(UserManager.getInstance().getConnectedUsers());
        users.addAll("julian", "user2", "user3");
        //users.addAll(UserManager.getInstance().getConnectedUsers());
        listGroups.setItems(groups);
        listUsers.setItems(users);
        //Méthode Bouton "Aller aux grilles"
        goToGrids.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e){
//                Dialogs dialog = null;
//                dialog.title("Message");
//                dialog.masthead("Message d'information");
//                dialog.message("Vous allez aux grilles de l'utilisateur: "+user.getText());
//                dialog.showInformation();
      }
    });

    //Méthode Bouton "Supprimer du groupe"
    delFromGroup.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
//                dialog.owner(null);
//                dialog.title("Confirm Dialog");
//                dialog.message("Voulez-vous supprimer l'utilisateur '"+user.getText()+"' du groupe '"+nameGroup.getText()+"'?");
//                Action response = dialog.showConfirm();
//                if (response == Dialog.ACTION_YES) {
//                    dialog.title("Message");
//                    dialog.message("L'utilisateur '"+user.getText()+"' sera supprimé");
//                    dialog.showInformation();
//                } else {
//                    // ... utilisateur choisit NO, CANCEL, ou ferme le dialog
//                }
      }
    });

    //Méthode Bouton "Nouveau Groupe"
    newGroup.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
//                dialog.title("Message");
//                dialog.masthead("Message d'information");
//                dialog.message("Créer un nouveau groupe");
//                dialog.showInformation();
      }
    });

    //Méthode Bouton "Supprimer Groupe"
    delGroup.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
//                dialog.owner(null);
//                dialog.title("Confirm Dialog");
//                dialog.message("Voulez-vous supprimer le groupe '"+nameGroup.getText()+"'?");
//                Action response = dialog.showConfirm();
//                if (response == Dialog.ACTION_YES) {
//                    dialog.title("Message");
//                    dialog.message("Le groupe '"+nameGroup.getText()+"' sera supprimé");
//                    dialog.showInformation();
//                } else {
//                    // ... utilisateur choisit NO, CANCEL, ou ferme le dialog
//                }
      }
    });

    //Méthode ListView Groupes -> OnClick
    listGroups.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<String>() {
          public void changed(ObservableValue<? extends String> ov,
                              String old_val, String new_val) {
            nombUsers.setText(listUsers.getItems().size() + " utilisateurs connectés");
            nameGroup.setText(new_val);
            paneGroup.setVisible(true);
            paneUser.setVisible(false);
          }
        });

    //Méthode ListView Groupes -> OnClick
    listGroups.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<String>() {
          public void changed(ObservableValue<? extends String> ov,
                              String old_val, String new_val) {
            nombUsers.setText(listUsers.getItems().size() + " utilisateurs connectés");
            nameGroup.setText(new_val);
            paneGroup.setVisible(true);
            paneUser.setVisible(false);
          }
        });

    //Méthode ListView Utilisateurs -> OnClick
    listUsers.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<String>() {
          public void changed(ObservableValue<? extends String> ov,
                              String old_val, String new_val) {
            user.setText(new_val);
            paneUser.setVisible(true);
            paneGroup.setVisible(false);
          }
        });
  }

  @Override
  public void setScreenParents(ScreensController screenParent) {
    myController = screenParent;
  }

  @FXML
  private void goToLogin(ActionEvent event) {
    myController.setScreen(SudukoIHM.loginID);
  }

  @FXML
  private void goToFillGrid(ActionEvent event) {
    myController.setScreen(SudukoIHM.fillGridID);
  }

  @FXML
  private void goToFromFullGrid(ActionEvent event) {
    myController.setScreen(SudukoIHM.fromFullGridID);
  }
}

