package com.sudoku.comm;

import com.sudoku.data.model.*;

import java.util.ArrayList;
import java.util.Arrays;

public final class CommunicationManager {
  
  private static volatile CommunicationManager instance = null;
  private static ArrayList<String> connectedIps =
      new ArrayList<>(Arrays.asList(
          "127.0.0.1"
      ));
  public static final String UUID = "uuid";
  public static final String LOGIN = "login";

  private CommunicationManager() {
    super();
  }

  public final static CommunicationManager getInstance() {
    if (instance == null) {
      synchronized(CommunicationManager.class) {
        if (instance == null){
          instance = new CommunicationManager();
        }
      }
    }
    return instance;
  }

  public static ArrayList<String> getConnectedIps() {
    return connectedIps;
  }

  public static void setConnectedIps(ArrayList<String> connectedIps) {
    CommunicationManager.connectedIps = connectedIps;
  }

  public ArrayList<Grid> getAllGrids() {
    return null;
  }

  public ArrayList<Grid> getAllGrids(User user) {
    return null;
  }

  public ArrayList<User> getAllProfiles() {
    return null;
  }

  public void pushCommentAndSync(Comment newComment, Grid gridToSync) { }

  /* Useless now, grade = comment !
  public void pushGradeAndSync(Comment newComment, Grid gridToSync) {
    return null;
  }
  */
}