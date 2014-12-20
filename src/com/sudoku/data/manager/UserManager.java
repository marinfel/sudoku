package com.sudoku.data.manager;

/**
 *
 * @author clesaege
 */
import com.sudoku.comm.CommunicationManager;
import com.sudoku.data.model.ExportUser;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.PlayedGrid;
import com.sudoku.data.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.security.crypto.codec.Base64;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public final class UserManager { // This is the manager for users.
  /*
   * Attributs
   */

  private static volatile UserManager instance = null;
  @JsonIgnore
  private User loggedUser; // The user actualy logged on this machine

  private List<User> localUsers; // Local Users
  private List<User> distantUsers; // Users connected but not on this machine

  private ArrayList<String> knownIpAdresses = new ArrayList<>();

  @JsonIgnore
  private File jsonFile;
  @JsonIgnore
  private String jsonFilePath;
    
  private UserManager() {
    this.loggedUser = null;
    this.localUsers = new ArrayList<>(); // Will need to load and deserialise
    this.distantUsers = new ArrayList<>(); //
    jsonFilePath = System.getProperty("user.home").concat("\\LO23Sudoku\\Backup\\");
 
  }


  public final static UserManager getInstance() {
    if (instance == null) {
      synchronized (UserManager.class) {
        if (instance == null) {
          instance = new UserManager();
        }
      }
    }
    return instance;
  }

  public ArrayList<String> getKnownIpAdresses(){
    return this.knownIpAdresses;
  }

  public void setKnownIpAdresses(ArrayList<String> knownIpAdresses){
    this.knownIpAdresses = knownIpAdresses;
  }

  public void addKnownIpAddress(String ipAddress){
    this.knownIpAdresses.add(ipAddress);
  }

  public boolean hasKnownIpAddress(String ipAddress){
    return this.knownIpAdresses.contains(ipAddress);
  }

  public User getLoggedUser() {
    return this.loggedUser;
  }

  public List<User> getLocalUsers() {
    return this.localUsers;
  }

  public List<User> getDistantUsers() {
    return this.distantUsers;
  }

  public boolean addLocalUser(User u) {
    return this.localUsers.add(u);
  }

  public boolean addDistantUser(User u) {
    return this.distantUsers.add(u);
  }

  public boolean removeLocalUser(User u) {
    return localUsers.remove(u);
  }

  public boolean removeDistantUser(User u) {
    return distantUsers.remove(u);
  }

  public User authenticate(String pseudo, String password)
     throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
    MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
    for (User u : this.localUsers) { //For all users
      if (u.getPseudo().equals(pseudo)) //For all correspoding pseudos
      {
        String toBeHashed = password + u.getSalt();
        if (new String(Base64.encode(
           mDigest.digest(toBeHashed.getBytes("UTF-8"))))
           .equals(u.getPassword())) { //If the hash of pwd+salt is good
          loggedUser = u; // If the password is correct, log the user

          CommunicationManager cm = CommunicationManager.getInstance();
          cm.init(u.getSalt(), u.getPseudo(), this.knownIpAdresses);

          return u; // and return the identified user
        }
      }
    }
    return null; // If we didn't manage to authenticate
  }

  public void logoff() {
    this.loggedUser = null;
  }

  public User createUser(String pseudo, String brutPassword, Date birthDate, String profilePicturePath) { // Create a local user and add it to the manager
    User u = null;
    try {
      u = new User(pseudo, brutPassword, birthDate, profilePicturePath);
    } catch (NoSuchAlgorithmException ex) {
      Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
    } catch (UnsupportedEncodingException ex) {
      Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
    } catch (UnknownHostException ex) {
      Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    if (u != null) {
      this.addLocalUser(u);
    }
    return u;
  }

  public List<User> getConnectedUsers() {
    CommunicationManager tmp = CommunicationManager.getInstance();
    try {
      this.distantUsers=tmp.getAllProfiles();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return distantUsers;
    // Met à jour la liste des distants users. Appel la com pour trouver les utilisateurs connectés
  }

  public boolean exportLoggedUserToFile(String path) {
    // Serialize this.loggedUser and write in a file.
    ObjectMapper mapper = new ObjectMapper();
    mapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
    try {
      jsonFile = new File(path);
      ExportUser user = new ExportUser();
      user.setUser(getLoggedUser());
      user.setAvailableGrids(GridManager.getInstance().getUserGrids(getLoggedUser()));
      user.setPlayedGrids(GridManager.getInstance().getUserPlayedGrids(getLoggedUser()));
      mapper.writeValue(jsonFile, user);
      System.out.println(mapper.writeValueAsString(user));
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
    return true;
  }

  public boolean importUser(String path) {
    // Derialize from a file and add to local users
    ObjectMapper mapper= new ObjectMapper();
    try {
      File jsonFile = new File(path);
      ExportUser user = mapper.readValue(jsonFile, ExportUser.class);
      for(User u : localUsers){
        if(u.equals(user.getUser())){
          System.err.println("utilisateur déjà présent");
          return false;
        }
      }
      addLocalUser(user.getUser());
      for(Grid g : user.getAvailableGrids()){
        GridManager.getInstance().addGrid(g);
      }
      for(PlayedGrid p : user.getPlayedGrids()){
        GridManager.getInstance().addPlayedGrid(p);
      }
    }catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
    return true;
  }
  public void SaveToJson(){

  ObjectMapper mapper = new ObjectMapper();
       //Pour sérializer les champs publics comme privés
       mapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
       //pour ne pas planter sur une valeur null
       //mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
       //mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
        
        try {
	        System.out.println(jsonFilePath.concat("backupUserManager.json"));
                jsonFile = new File(jsonFilePath.concat("backupUserManager.json"));
           
                mapper.writeValue(jsonFile, this);
                    //Pour logger le processus de sauvegarde
	        System.out.println(mapper.writeValueAsString(this));
        } catch (JsonGenerationException ex) {
                   ex.printStackTrace();
 
	} catch (JsonMappingException ex) {
	 
	            ex.printStackTrace();
        }   catch (FileNotFoundException e){
            try {
          
                File tmpFile=new File(jsonFilePath);

                tmpFile.mkdirs();
                jsonFile = new File(jsonFilePath.concat("backupUserManager.json"));
                mapper.writeValue(jsonFile, this);
                //Pour logger le processus de sauvegarde
                System.out.println(mapper.writeValueAsString(this));
            } catch (IOException ex) {
                Logger.getLogger(GridManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }catch (IOException ex) {
	 
	            ex.printStackTrace();
        }

    }
  
  public static UserManager BuildFromJson(){
  ObjectMapper mapper= new ObjectMapper();
        // To avoid any undeclared property error
        //mapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIE‌​S , false);
            
        try {
	 
               String jsonFilePath = System.getProperty("user.home").concat("\\LO23Sudoku\\Backup\\");
 
               File jsonFile = new File(jsonFilePath.concat("backupUserManager.json"));
	 
                //DataManager.instance = mapper.readValue(jsonFile, DataManager.class);
                UserManager.instance = mapper.readValue(jsonFile, UserManager.class);
        }catch (JsonGenerationException ex) {
         
	 
	            ex.printStackTrace();
 
	} catch (JsonMappingException ex) {
	 
	            ex.printStackTrace();
	 
	} catch (IOException ex) {
	 
	            ex.printStackTrace();
	 
	}
        return UserManager.getInstance();
  
  }
}
