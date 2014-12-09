package com.sudoku.data.manager;

/**
 *
 * @author clesaege
 */
import com.sudoku.data.model.User;
import org.springframework.security.crypto.codec.Base64;

import java.io.UnsupportedEncodingException;
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

public final class UserManager { // This is the manager for users.
  /*
   * Attributs
   */

  private static volatile UserManager instance = null;
  private User loggedUser; // The user actualy logged on this machine
  private List<User> localUsers; // Local Users
  private List<User> distantUsers; // Users connected but not on this machine

  private UserManager() {
    this.loggedUser = null;
    this.localUsers = new ArrayList<>(); // Will need to load and deserialise
    this.distantUsers = new ArrayList<>(); //
  }

  public void save() { // Serialize and save localUsers, we do not keep track of distantUsers
    // TO BE COMPLETED   
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
    throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
    for (User u : this.localUsers) { //For all users
      if (u.getPseudo().equals(pseudo)) //For all correspoding pseudos
      {
        String toBeHashed = password + u.getSalt();
        if (new String(Base64.encode(
          mDigest.digest(toBeHashed.getBytes("UTF-8"))))
          .equals(u.getPassword())) { //If the hash of pwd+salt is good
          loggedUser = u; // If the password is correct, log the user
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
    return null; // Appeler com
  }

  public boolean exportLoggedUserToFile(String path) {
        // TO BE COMPLETED
    // Serialize this.loggedUser and write in a file.
    return true;
  }

  public User importUser(String path) {
        // TO BE COMPLETED
    // Derialize from a file and add to local users
    return null;
  }


}
