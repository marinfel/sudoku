/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sudoku.main.view;

import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.ContactCategory;
import com.sudoku.data.model.User;
import com.sudoku.data.sample.DataSample;
import com.sudoku.main.manager.ListGridManager;
import com.sudoku.main.manager.UserCategoryManager;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

/**
 * @author MOURAD
 */
public class FXMLDocumentController implements Initializable, ControlledScreen {

  public static ObservableList groups = FXCollections.observableArrayList();
  public static ObservableList users = FXCollections.observableArrayList();
  //Data
  public DataSample instance;
  ListGridManager gridList;
  ScrollPane  scpane ;
  
  public User loggedUser;
  public UserManager userManag;
  public UserCategoryManager userCategoryManag;
  public List<User> listUsers = new LinkedList<>();
  public List<ContactCategory> userCategories = new LinkedList<>();
  public HashMap<String,List<User>> categoryAndUsers;
  public HashMap<String,ObservableList> observableData;
  
  // Partie JulianC
  ScreensController myController;
  @FXML
  private Label userName;
  @FXML
  private ImageView avatar;
  @FXML
  private Button connexion;
  @FXML
  private StackPane panes;
  @FXML
  private Pane paneUser;
  @FXML
  private Pane paneGroup;
  @FXML
  private Button fillGrid;
  @FXML
  private Button fromFullGrid;
  @FXML
  private ListView listGroups;
  @FXML
  private ListView listUsersView;
  @FXML
  private Label user;
  @FXML
  private Label name;
  @FXML
  private Label birthD;
  @FXML
  private Label nombUsers;
  @FXML
  private Button goToGrids;
  @FXML
  private Button delFromGroup;
  @FXML
  private Button newGroup;
  @FXML
  private TextField nameGroup;
  @FXML
  private Button delGroup;
  @FXML
  private AnchorPane currentGrid;
  @FXML
  private AnchorPane distanteGrid;
  @FXML
  private AnchorPane finishedGrid;
  @FXML
  private AnchorPane myGrid;
  @FXML
  private TitledPane MesGrilles;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    System.out.println("test data");
    instance = new DataSample();
    instance.exec();
    
    userManag = UserManager.getInstance();
    userCategoryManag = UserCategoryManager.getInstance();
    
    System.out.println("test data" + instance.a.getPseudo());
    userName.setText("Utilisateur : "+userManag.getLoggedUser());

    assert panes != null : "fx:id=\"panes\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert fillGrid != null : "fx:id=\"fillGrid\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert fromFullGrid != null : "fx:id=\"fromFullGrid\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert paneUser != null : "fx:id=\"paneUser\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert paneGroup != null : "fx:id=\"paneGroup\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert listGroups != null : "fx:id=\"listGroups\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert listUsersView != null : "fx:id=\"listUsersView\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert user != null : "fx:id=\"user\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert birthD != null : "fx:id=\"birthD\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert goToGrids != null : "fx:id=\"goToGrids\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert delFromGroup != null : "fx:id=\"delFromGroup\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert newGroup != null : "fx:id=\"newGroup\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert nameGroup != null : "fx:id=\"nameGroup\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert delGroup != null : "fx:id=\"delGroup\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert currentGrid != null : "fx:id=\"currentGrid\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert distanteGrid != null : "fx:id=\"distanteGrid\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert finishedGrid != null : "fx:id=\"finishedGrid\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert myGrid != null : "fx:id=\"myGrid\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
    assert MesGrilles != null : "fx:id=\"MesGrilles\" was not injected: check your FXML file 'FXMLDocument.fxml'.";


    //Ajouter des éléments aux listes groupes et utilisateurs
    groups.addAll("Utilisateurs connectés", "Amis", "Camarades");
    loggedUser = userManag.getLoggedUser();
    userCategories = loggedUser.getContactCategories(); // J'obtiens les categories de l'utilisateur connecté
    //ListUsers = UserManager.getInstance().getConnectedUsers();
    listUsers = instance.getUserList();
    users = userCategoryManag.getUsersToShow(listUsers);
    categoryAndUsers = userCategoryManag.getUsersCategories(userCategories,listUsers);  // J'obtiens les utilisateurs de chaque catégorie
    observableData = userCategoryManag.changeToObservableData(categoryAndUsers); //Changer au format Observable (pour afficher dans la listView)
    showConnectedUsers(observableData,1); //Les afficher
    //users.addAll("julian", "user2", "user3");
    //users.addAll(UserManager.getInstance().getConnectedUsers());
    
    //Méthode Bouton "Aller aux grilles"
    goToGrids.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
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
            nombUsers.setText(listUsersView.getItems().size() + " utilisateurs connectés");
            showConnectedUsers(observableData, listGroups.getSelectionModel().getSelectedIndex());
            nameGroup.setText(new_val);
            paneGroup.setVisible(true);
            paneUser.setVisible(false);
          }
        });

    //Méthode ListView Utilisateurs -> OnClick
    listUsersView.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<String>() {
          public void changed(ObservableValue<? extends String> ov,
                              String old_val, String new_val) {
            user.setText(new_val);
            User a = listUsers.get(listUsersView.getSelectionModel().getSelectedIndex());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            birthD.setText("Date de Naissance: "+df.format(a.getBirthDate()));
            paneUser.setVisible(true);
            paneGroup.setVisible(false);
          }
        });
   
    
    EventHandler<MouseEvent> mousehandler = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent mouseEvent) {
            System.out.println("hi");
            refreshMyGrids();
        }
    };
    
    MesGrilles.setOnMouseClicked(mousehandler);
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
  
  private void refreshMyGrids(){
    // My Grids
    gridList=new ListGridManager(instance);
    scpane = new ScrollPane();
    scpane.setContent(gridList.getGridThumbnailContainer());
    scpane.setPrefSize(800, 800);
    myGrid.getChildren().add(scpane);
  }
    
    private void showConnectedUsers(HashMap<String,ObservableList> observableData, int index) {
        groups.clear();
        users.clear();        
        Iterator it = observableData.entrySet().iterator();
        int i = 1;
        for (Map.Entry e : observableData.entrySet()) {
            System.out.println("GR: "+e.getKey());
            groups.add(e.getKey());
            e = (Map.Entry)it.next();
            if(i == index){
                System.out.println("Index: "+i);
                users = (ObservableList)e.getValue();
            }
            i++;
        }
        listGroups.setItems(groups);
        listUsersView.setItems(users);
    }
}

