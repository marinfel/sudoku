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

public class UserManager { // This is the manager for users.
  /*
  * Attributs
  */
  private static volatile UserManager instance = null;
  private User loggedUser;
  private List<User> users;

  private UserManager() {
    this.loggedUser = null;
    this.users = new ArrayList<User>(); //Will need to load and deserialise
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

  public List<User> getAllUsers() {
    return this.users;
  }

  public boolean addUser(User u) {
    return this.users.add(u);
  }

  public boolean removeUser(User u) {
    return users.remove(u);
  }

  public User authenticate(String pseudo, String password)
      throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
    for (User u : this.users) { //For all users
      if (u.getPseudo() == pseudo) //For all correspoding pseudos
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

  public User createUser() {
    return null;
  }

  public List<User> getConnectedUsers() {
    return null; // Appeler com
  }

}

       
