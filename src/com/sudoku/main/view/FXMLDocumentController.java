/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sudoku.main.view;

import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.User;
import java.net.URL;
import java.util.ResourceBundle;

import com.sudoku.data.sample.DataSample;
import com.sudoku.grid.editor.IhmGridEditorRandomlyFilled;
import com.sudoku.grid.ihm_grid_preview.IhmGridPreview;
import com.sudoku.main.manager.ListGridManager;
import javafx.scene.control.ScrollPane;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JScrollPane;

/**
 *
 * @author JULIANC
 */
public class FXMLDocumentController implements Initializable, ControlledScreen {

    //Data
    public DataSample instance;
    public UserManager instUserM;
    
    public List<User> ListUsers = new LinkedList<>();

    @FXML   private Label userName;
    @FXML   private ImageView avatar;
    @FXML   private Button connexion;

    // Partie Julian
    
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
    @FXML   private AnchorPane grid1;

    public static final ObservableList groups = FXCollections.observableArrayList();
    public static ObservableList users  = FXCollections.observableArrayList();
    
    public ObservableList getUsersToShow(List<User> ListUsers){
        ObservableList ObsList  = FXCollections.observableArrayList();
        Iterator<User> i = ListUsers.iterator();
        while(i.hasNext())
            ObsList.add(i.next().getPseudo());
        return ObsList;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
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

        System.out.println("test data");
        instUserM = UserManager.getInstance();
        instance = new DataSample();
        instance.exec();        
        System.out.println("test data"+instance.a.getPseudo());
        userName.setText("Utilisateur : "+instUserM.getLoggedUser());
        
        //Ajouter des éléments aux listes groupes et utilisateurs
        groups.addAll("Global", "Amis", "Camarades");
        //groups.addAll(UserManager.getInstance().getConnectedUsers());
        ListUsers = instance.getUserList();
        users = getUsersToShow(ListUsers);
        //users.addAll("julian", "user2", "user3");
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
            User a = ListUsers.get(listUsers.getSelectionModel().getSelectedIndex());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            birthD.setText("Date de Naissance: "+df.format(a.getBirthDate()));
            paneUser.setVisible(true);
            paneGroup.setVisible(false);
          }
        });
    
    
    
    // Liste Grille
      /*ListGridManager myGridManager=new ListGridManager();
      
      for(int i = 0; i<myGridManager.AllGrid().size();i++)
      {
          grid1.getChildren().add(myGridManager.AllGrid().get(i));
      }*/
    ListGridManager gridList = new ListGridManager(instance);
    ScrollPane  scpane = new ScrollPane();
    scpane.setContent(gridList.getGridThumbnailContainer());
    scpane.setPrefSize(800, 800);
    grid1.getChildren().add(scpane);
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

