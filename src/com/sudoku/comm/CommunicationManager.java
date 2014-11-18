package com.sudoku.comm;

import com.sudoku.data.model.*;

import java.util.ArrayList;

public final class CommunicationManager {
  
  private static volatile CommunicationManager instance = null;

  private CommunicationManager() {
    super();
  }

  public final static CommunicationManager getInstance() {
    if (CommunicationManager.instance == null) {
      synchronized(CommunicationManager.class) {
        if (CommunicationManager.instance == null){
          CommunicationManager.instance = new CommunicationManager();
        }
      }
    }
    return CommunicationManager.instance;
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