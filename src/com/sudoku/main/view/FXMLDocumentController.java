/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sudoku.main.view;

import com.sudoku.data.manager.AccessManager;
import com.sudoku.data.manager.DataManager;
import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.AccessAction;
import com.sudoku.data.model.ContactCategory;
import com.sudoku.data.model.Tag;
import com.sudoku.data.model.User;
import com.sudoku.data.sample.DataSample;
import com.sudoku.grid.gridcells.IhmGridLinesCompleted;
import com.sudoku.grid.player.IhmGridPlayer;
import com.sudoku.main.manager.ListGridManager;
import com.sudoku.main.manager.UserCategoryManager;
import com.sudoku.main.manager.RefreshGridPlayer;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import javafx.stage.Screen;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author MOURAD
 */
public class FXMLDocumentController implements Initializable, ControlledScreen {

  public static ObservableList groups = FXCollections.observableArrayList();
  public static ObservableList users = FXCollections.observableArrayList();
  public static ObservableList users_n = FXCollections.observableArrayList();
  
  public Stage dialogStage = new Stage();
  public AccessManager accesMan = AccessManager.getInstance();
          
  //Data
  //public DataSample instance;
  ListGridManager gridList;
  ListGridManager currentList;
  ListGridManager distantList;
  ListGridManager finishedList;

  ScrollPane  scpane ;
  ScrollPane distantPane;
  ScrollPane currentPane;
  ScrollPane finishedPane;
  int ResearchMode;
  
  public User loggedUser;
  public UserManager userManag;
  public UserCategoryManager userCategoryManag = UserCategoryManager.getInstance();            ;
  public List<User> listUsers = new LinkedList<>();
  public List<User> listCategoryUsers = new LinkedList<>();
  public List<User> listConnectedUsers = new LinkedList<>();
  public List<ContactCategory> userCategories = new LinkedList<>();
  public HashMap<String,List<User>> categoryAndUsers;
  public HashMap<String,ObservableList> observableData;
  
  // Partie JulianC
  ScreensController myController;
  @FXML
  private TitledPane mainContainer;
  @FXML
  private Label userName;
  @FXML
  private ImageView avatar;
  @FXML
  private ImageView avatarContact;
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
  private TextField userHome;
  @FXML
  private TextField birthDateHome;
  @FXML
  private TextField creatDateHome;
  @FXML
  private TextField updateDateHome;
  @FXML
  private TextField nameHome;
  @FXML
  private PasswordField pass1Home;
  @FXML
  private PasswordField pass2Home;
  @FXML
  private PasswordField pass3Home;
  @FXML
  private TextField ipAddressHome;
  @FXML
  private TextField picturePathHome;
  @FXML
  private Label textInfHome;
  
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
  @FXML
  private AnchorPane ContentContainer;
  @FXML
  private Tab ListGrille;
  @FXML
  private TabPane TabP;
  @FXML
  private Tab Jouer;
  @FXML
  private AnchorPane GridPlayer;
  @FXML
  private ScrollPane GridPlayerContainer;
  @FXML
  public ScrollPane GridPreviewShow;
  @FXML
  private ChoiceBox ResearchType;
  @FXML
  private TextField ResearchInput;
  @FXML
  private CheckBox chBox1;
  @FXML
  private CheckBox chBox2;
  @FXML
  private CheckBox chBox3;
  @FXML
  private TextField newIPAddress;

  @Override
  public void initialize(URL url, ResourceBundle rb) 
  {
          /*instance = new DataSample();
          instance.exec();*/
          
          userManag = UserManager.getInstance();
          
          dialogStage.initModality(Modality.WINDOW_MODAL);
          dialogStage.setTitle("Message");
          dialogStage.setHeight(100);
          dialogStage.setWidth(200);
          dialogStage.setResizable(false);
          ResearchMode=0;
          
          //System.out.println("test data" + instance.a.getPseudo());
          Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
          mainContainer.setPrefHeight(primaryScreenBounds.getHeight());
          mainContainer.setPrefWidth(primaryScreenBounds.getWidth());
          ContentContainer.setPrefHeight(primaryScreenBounds.getHeight()*0.8);
          ContentContainer.setPrefWidth(primaryScreenBounds.getWidth()*0.8);
          
          assert ResearchInput != null : "fx:id=\"ResearchInput\" was not injected: check your FXML file 'FXMLDocument.fxml'.";  
          assert ResearchType != null : "fx:id=\"ResearchType\" was not injected: check your FXML file 'FXMLDocument.fxml'.";  
          assert GridPreviewShow != null : "fx:id=\"GridPreviewShow\" was not injected: check your FXML file 'FXMLDocument.fxml'.";  
          assert GridPlayerContainer != null : "fx:id=\"GridPlayerContainer\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert Jouer != null : "fx:id=\"Jouer\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert GridPlayer != null : "fx:id=\"GridPlayer\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert TabP != null : "fx:id=\"TabP\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert ListGrille != null : "fx:id=\"ListGrille\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
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
          assert mainContainer != null : "fx:id=\"mainContainer\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert ContentContainer != null : "fx:id=\"ContentContainer\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert userHome != null : "fx:id=\"userHome\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert birthDateHome != null : "fx:id=\"birthDateHome\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert creatDateHome != null : "fx:id=\"creatDateHome\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert updateDateHome != null : "fx:id=\"updateDateHome\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert nameHome != null : "fx:id=\"nameHome\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert pass1Home != null : "fx:id=\"pass1Home\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert pass2Home != null : "fx:id=\"pass2Home\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert pass3Home != null : "fx:id=\"pass3Home\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert ipAddressHome != null : "fx:id=\"ipAddressHome\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert picturePathHome != null : "fx:id=\"picturePathHome\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert textInfHome != null : "fx:id=\"textInfHome\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert chBox1 != null : "fx:id=\"chBox1\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert chBox2 != null : "fx:id=\"chBox2\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert chBox3 != null : "fx:id=\"chBox3\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
          assert newIPAddress != null : "fx:id=\"newIPAddress\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
                     
          //Ajouter des éléments aux listes groupes et utilisateurss
          //groups.addAll("Utilisateurs connectés", "Amis", "Camarades");
          //loggedUser = userManag.getLoggedUser();
    
        //ListUsers = UserManager.getInstance().getConnectedUsers();
        //listUsers = instance.getUserList();    
        //categoryAndUsers = userCategoryManag.getUsersCategories(userCategories,listUsers);  // J'obtiens les utilisateurs de chaque catégorie
        //observableData = userCategoryManag.changeToObservableData(categoryAndUsers); //Changer au format Observable (pour afficher dans la listView)
        //showConnectedUsers2(categoryAndUsers,"Famille"); //Les afficher
        //users.addAll("julian", "user2", "user3");
        //users.addAll(UserManager.getInstance().getConnectedUsers());
        //users.addAll(userCategoryManag.getUsersToShow(listUsers));
        //    users = userCategoryManag.getUsersToShow(listUsers);
        //    
        //    listGroups.setItems(groups);
        //    listUsersView.setItems(users);        
        //Charger données utilisateur
        //loggedUser = null;
          
          
          
          //Research methods
          ResearchType.setItems(FXCollections.observableArrayList("Tag", "Note"));
          ResearchType.setTooltip(new Tooltip("Selectionez le type de la recherche"));
          ResearchType.getSelectionModel().selectedIndexProperty().addListener(
                  new ChangeListener<Number>(){
                      public void changed(ObservableValue ov, Number value, Number new_value)
                      {
                          if(new_value.intValue() == 0)
                          {
                              ResearchMode = 0;
                              ResearchInput.setPromptText("Tapez un tag");
                              
                          }
                          else  if(new_value.intValue() == 1)
                          {
                              ResearchMode = 1;
                              ResearchInput.setPromptText("Tapez une note");
                              
                          }
                      }
                  }
          
          );

            //Méthode Bouton "Aller aux grilles"
          goToGrids.setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent e) {
                  dialogStage.setScene(new Scene(VBoxBuilder.create()
                          .children(new Text("Grilles chargées"))
                          .alignment(Pos.CENTER).padding(new Insets(15)).build()));
                  dialogStage.show();
//                Dialogs dialog = null;
//                dialog.title("Message");
//                dialog.masthead("Message d'information");
//                dialog.message("Vous allez aux grilles de l'utilisateur: "+user.getText());
//                dialog.showInformation();
      }
    });

    //Méthode ListView Groupes -> OnClick
    listGroups.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<String>() {
          public void changed(ObservableValue<? extends String> ov,
                              String old_val, String new_val) {
            showCategoryRights(new_val);            
            showUsersCategory(new_val);
            nombUsers.setText(listUsersView.getItems().size() + " utilisateurs connectés");                                    
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
                          name.setText(new_val);                          
                          User a = listUsers.get(listUsersView.getSelectionModel().getSelectedIndex());
                          DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                          birthD.setText("Date de Naissance: "+df.format(a.getBirthDate()));
                          paneUser.setVisible(true);
                          paneGroup.setVisible(false);
                      }
                  });
          
          
          TabP.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
              @Override public void changed(ObservableValue<? extends Tab> tab, Tab oldTab, Tab newTab) {
                  if(newTab.getText().equalsIgnoreCase("Liste Grilles"))
                  {
                      refreshMyGrids();
                  }
                  else if(newTab.getText().equalsIgnoreCase("Jouer"))
                  {
                      refreshGridPlayer();
                  }
              }
          });
  }

    
  @Override
  public void setScreenParents(ScreensController screenParent) {
    myController = screenParent;
   
        myController.addEventHandler(WindowEvent.WINDOW_SHOWING ,new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent e) {
                userManag = UserManager.getInstance();
                loggedUser = userManag.getLoggedUser();
                if(loggedUser != null){
                    getDataUser(); //Charger donnés de l'utilisateur connecté
                    listUsers = userManag.getConnectedUsers(); //liste de tous les utilisateurs connectés                    
                    if(!listUsers.isEmpty()){ //Si la liste n'est pas vide -> afficher les utilisateurs
                        users = userCategoryManag.getUsersToShow(listUsers); //Convertir en ObservableList
                        listUsersView.setItems(users);
                    }                                        
                    // A FAIRE DANS LA PARTIE CREATION COMPTE **
                    loggedUser.createContactCategory("Global");  // Créer catégorie "Global" par défaut à tous les utilisateurs
                    ContactCategory cat = loggedUser.getContactCategory("Global"); //Obtenir catégorie Global
                    HashSet<AccessAction> allowedAct = new HashSet<>(); //Liste des droits "Allowed Actions"
                    allowedAct.add(AccessAction.display); //Ajouter les 3 droits à la catégorie "Global"
                    allowedAct.add(AccessAction.play);
                    allowedAct.add(AccessAction.comment);
                    accesMan.setAccessRulesForGroup(cat, allowedAct); //Associer la liste de droits à la catégorie
                    //refreshGroups(); //Actualiser listView de catégories
                    // ****
                    userCategories = loggedUser.getContactCategories(); //Obtenir liste de catégories de l'utilisateur connecté
                    groups = userCategoryManag.getCategoriesToShow(userCategories); //Convertir en ObservableList                    
                    listGroups.setItems(groups);
                    
                    listCategoryUsers = userCategories.get(0).getContacts(); //Liste d'utilisateurs de la catégorie choisie ("Global" au début)
                    if(!listCategoryUsers.isEmpty()){ //Si la liste n'est pas vide -> afficher les utilisateurs
                        users = userCategoryManag.getUsersToShow(listCategoryUsers);//Convertir en ObservableList
                        listUsersView.setItems(users);
                    }
                }
                else{
                    System.out.println("User is null");
                }
            }
      });
  }

  @FXML
  private void goToLogin(ActionEvent event) {
    userManag.logoff();
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
  
  @FXML
  private void usersRefresh(ActionEvent event) {
    listUsers = userManag.getConnectedUsers();
    users = userCategoryManag.getUsersToShow(listUsers);
    listUsersView.getItems().clear();
    listUsersView.setItems(users);
  }
  
  @FXML
  private void addIPAddress(ActionEvent event) {
    if(userManag.addKnownIpAddress(newIPAddress.getText())){
        newIPAddress.setText(null);
        textInfHome.setText("Adresse IP ajoutée");
    }else 
        textInfHome.setText("Adresse IP non valide");
  }
  
  @FXML
  private void modifyUserInformation(ActionEvent event) throws ParseException, NoSuchAlgorithmException, UnsupportedEncodingException {
    textInfHome.setText(null);
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date dateB = formatter.parse(birthDateHome.getText());
    //loggedUser = userManag.getLoggedUser(); Modifier l'utilisateur connecté
    loggedUser.setBirthDate(dateB);
    loggedUser.setProfilePicturePath(picturePathHome.getText());
    System.out.println("PW: "+loggedUser.getPassword()); //Le mot de passe avec salt ??
    if(!picturePathHome.getText().isEmpty())
        avatar.setImage(new Image(new File(picturePathHome.getText()).toURI().toString()));    
    textInfHome.setText("Enregistré correctement");
    if(!pass2Home.getText().isEmpty() || !pass3Home.getText().isEmpty()){
        if(!pass1Home.getText().isEmpty()){
            if (loggedUser.checkPassword(pass1Home.getText())){
                if(pass2Home.getText().equals(pass3Home.getText())){                    
                    loggedUser.modifyPassword(pass2Home.getText());
                    System.out.println("Guardar contraseña");
                    textInfHome.setText("Enregistré correctement");
                    pass1Home.setText(null);
                    pass2Home.setText(null);
                    pass3Home.setText(null);
                }else
                    textInfHome.setText("Les deux mots de passe ne correspondent pas");
            }else
                textInfHome.setText("Mot de passe incorrect");            
        }else
            textInfHome.setText("Entrez le mot de passe actuel");        
    }    
  }
  
  @FXML
  private void selectPicturePath(ActionEvent event) {
    FileChooser chooser = new FileChooser();
    chooser.setTitle("Choisir Image");
    File file = chooser.showOpenDialog(new Stage());
    picturePathHome.setText(file.toString());
    avatar.setImage(new Image(new File(file.toString()).toURI().toString()));
  }
  
  @FXML
  private void createNewGroup(ActionEvent event) {
      loggedUser.createContactCategory(nameGroup.getText());
      ContactCategory cat = loggedUser.getContactCategory(nameGroup.getText());
      HashSet<AccessAction> allowedAct = new HashSet<>();
      if(chBox1.isSelected())
          allowedAct.add(AccessAction.display);
      if(chBox2.isSelected())
          allowedAct.add(AccessAction.play);
      if(chBox3.isSelected())
          allowedAct.add(AccessAction.comment);
      accesMan.setAccessRulesForGroup(cat, allowedAct);
      groups.add(cat.getName());
//      refreshGroups();    
  }
  
  @FXML
  private void editGroup(ActionEvent event) {
      //loggedUser.createContactCategory(nameGroup.getText());
      Iterator<ContactCategory> it = userCategories.iterator(); 
      while (it.hasNext())
         System.out.println("CONT CAT: "+it.next().getName());      
  }
  
  @FXML
  private void deleteGroup(ActionEvent event) {      
      if(!nameGroup.getText().equals("Global")){
        loggedUser.removeContactCategory(nameGroup.getText());
        groups.remove(nameGroup.getText());
      }
  }
    
  private void LetSearchGrid(ActionEvent event) {
      if(ResearchMode == 0)
      {
          List<Tag> tags = new ArrayList<Tag>();
          String[] result = ResearchInput.getText().split(" ");
          for(int i=0; i<result.length;i++)
          {
              tags.add(new Tag(result[i]));
          }
          gridList = new ListGridManager(GridPreviewShow,myController);
          scpane = new ScrollPane();
          scpane.setContent(gridList.getGridThumbnailContainer(true,0,tags,0));
          scpane.setPrefSize(800, 400);
          myGrid.getChildren().add(scpane);
      }
      else
      {
          int NoteValue = Integer.valueOf(ResearchInput.getText());
          gridList = new ListGridManager(GridPreviewShow,myController);
          scpane = new ScrollPane();
          scpane.setContent(gridList.getGridThumbnailContainer(true,1,null,NoteValue));
          scpane.setPrefSize(800, 400);
          myGrid.getChildren().add(scpane);
      }
  }
  
  @FXML
  private void saveDatas(ActionEvent event){
      DataManager.getInstance().saveToJson();
  }
  
  @FXML
  private void refreshGrids(ActionEvent event){
      refreshMyGrids();
  }
  
  private void getDataUser() {
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);     
    //avatar.setImage(new Image(new File("pictures/grid/yellowStar.png").toURI().toString()));
    avatar.setImage(new Image(new File(loggedUser.getProfilePicturePath()).toURI().toString()));
    userHome.setText(loggedUser.getPseudo());
    nameHome.setText(loggedUser.getPseudo()); // Mettre le nom quand la fonction sera créée
    ipAddressHome.setText(loggedUser.getIpAddress());        
    birthDateHome.setText(df.format(loggedUser.getBirthDate()));
    creatDateHome.setText(df.format(loggedUser.getCreateDate()));
    updateDateHome.setText(df.format(loggedUser.getUpdateDate()));
    picturePathHome.setText(loggedUser.getProfilePicturePath());
    userCategories = loggedUser.getContactCategories(); // J'obtiens les categories de l'utilisateur connecté
    userName.setText(loggedUser.getPseudo());
  }
  
  private void refreshMyGrids(){
    // My Grids
    gridList = new ListGridManager(GridPreviewShow,myController);
    scpane = new ScrollPane();
    scpane.setContent(gridList.getGridThumbnailContainer(false,0,null,0));
    scpane.setPrefSize(800, 400);
    myGrid.getChildren().add(scpane);
    
    //Current Grids
    currentList = new ListGridManager(GridPreviewShow,myController);
    currentPane= new ScrollPane();
    currentPane.setContent(currentList.getCurrentGridThumbnailContainer());
    currentPane.setPrefSize(800,400);
    currentGrid.getChildren().add(currentPane);
    
    //Finished Grids
    finishedList = new ListGridManager(GridPreviewShow,myController);
    finishedPane = new ScrollPane();
    finishedPane.setContent(finishedList.getFinishedGridThumbnailContainer());
    finishedPane.setPrefSize(800, 400);
    finishedGrid.getChildren().add(finishedPane);
    
    //Distant Grids
    distantList = new ListGridManager(GridPreviewShow,myController);
    distantPane = new ScrollPane();
    distantPane.setContent(distantList.getDistantGridThumbnailContainer());
    distantPane.setPrefSize(800, 400);
    distanteGrid.getChildren().add(distantPane);
  }
  
  private void refreshGridPlayer(){
      myController.setScreen(SudukoIHM.gridPlayerGameID);
  }
    
  
//    private void showConnectedUsers(HashMap<String,ObservableList> observableData, String cat) {
//        String categorie = cat;
//        groups.clear();
//        listGroups.setItems(groups);
//        users.clear();
//        ObservableList userL = observableData.get(categorie);
//        listUsersView.setItems(userL);
//        for (Map.Entry e : observableData.entrySet())
//            groups.add(e.getKey());            
//        listGroups.setItems(groups);
//    }
//    
//    private void showConnectedUsers2(HashMap<String,List<User>> observableData, String cat) {
//        String categorie = cat;
//        ObservableList groups_show = FXCollections.observableArrayList();
//        ObservableList users_show = FXCollections.observableArrayList();
//        List<User> lll;
//        lll = observableData.get(categorie);
//        listConnectedUsers = observableData.get(categorie);
//        Iterator<User> it = lll.iterator(); 
//        while (it.hasNext())
//            users_show.add(it.next().getPseudo());
//        for (Map.Entry e : observableData.entrySet())
//            groups_show.add(e.getKey());            
//        listUsersView.setItems(users_show);
//        listGroups.setItems(groups_show);
//    }
    
    private void showCategoryRights(String nomCat) {
        ContactCategory cat = loggedUser.getContactCategory(nomCat);
        HashSet<AccessAction> allowedAct = accesMan.getAccessRulesForGroup(cat);
        if(allowedAct.contains(AccessAction.display))
            chBox1.setSelected(true);
        else
            chBox1.setSelected(false);
        if(allowedAct.contains(AccessAction.play))
            chBox2.setSelected(true);
        else
            chBox2.setSelected(false);
        if(allowedAct.contains(AccessAction.comment))
            chBox3.setSelected(true);
        else
            chBox3.setSelected(false);
    }
    
    private void showUsersCategory(String nomCat) {
        ContactCategory cat = loggedUser.getContactCategory(nomCat);
        listCategoryUsers = cat.getContacts();//Liste d'utilisateurs de la catégorie choisie
        if(!listCategoryUsers.isEmpty()){//Si la liste n'est pas vide -> afficher les utilisateurs
            users.removeAll(users);
            users.addAll(userCategoryManag.getUsersToShow(listCategoryUsers));//Convertir en ObservableList
        }
    }
}

