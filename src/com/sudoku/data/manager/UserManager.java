package com.sudoku.data.manager;

/**
 *
 * @author clesaege
 */

import com.sudoku.data.model.User;
import org.springframework.security.crypto.codec.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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
  
  public void logoff()
  {
      this.loggedUser=null;
  }
  

  public User createUser() {
    return null;
  }

  public List<User> getConnectedUsers() {
    return null; // Appeler com
  }

}

       
