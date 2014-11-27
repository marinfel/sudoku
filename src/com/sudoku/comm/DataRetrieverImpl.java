package com.sudoku.comm;

import com.sudoku.comm.generated.DataRetriever;
import com.sudoku.comm.generated.Grid;
import com.sudoku.comm.generated.User;
import com.sudoku.data.manager.GridManager;
import com.sudoku.data.manager.UserManager;
import org.apache.avro.AvroRemoteException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 26/11/14.
 */
public class DataRetrieverImpl implements DataRetriever {
  @Override
  public List<Grid> getGrids() throws AvroRemoteException {
    List<com.sudoku.data.model.Grid> availableGrids =
        GridManager.getInstance().getAvailableGrids();
    ArrayList<Grid> grids = new ArrayList<Grid>();
    for (com.sudoku.data.model.Grid grid : availableGrids) {
      //grids.add(Grid.newBuilder()
      //  .set)
    }
    return grids;
  }

  @Override
  public User getProfile() throws AvroRemoteException {
    com.sudoku.data.model.User currentUser =
        UserManager.getInstance().getLoggedUser();
    return User.newBuilder()
        .setBirthDate(currentUser.getBirthDate().toString())
        .setCreateDate(currentUser.getCreateDate().toString())
        .setIpAddress(currentUser.getIpAddress())
        .setProfilePicturePath(currentUser.getProfilePicturePath())
        .setPseudo(currentUser.getPseudo())
        .setUpdateDate(currentUser.getUpdateDate().toString())
        .build();
  }
}
