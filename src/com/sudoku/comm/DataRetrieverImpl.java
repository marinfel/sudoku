package com.sudoku.comm;

import com.sudoku.comm.generated.DataRetriever;
import com.sudoku.comm.generated.Grid;
import com.sudoku.comm.generated.User;
import org.apache.avro.AvroRemoteException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 26/11/14.
 */
public class DataRetrieverImpl implements DataRetriever {
  private CommunicationManager commManager;

  public DataRetrieverImpl() {
    this.commManager = CommunicationManager.getInstance();
  }

  @Override
  public List<Grid> getGrids(String ip) throws AvroRemoteException {
    return new ArrayList<Grid>();
  }

  @Override
  public User getProfile(String ip) throws AvroRemoteException {
    return User.newBuilder()
        .setBirthDate("birthdate")
        .setCreateDate("createdate")
        .setId(0)
        .setIpAddress(commManager.getLocalIp())
        .setProfilePicturePath("/path")
        .setPseudo("pseudo")
        .setSalt("salt")
        .setUpdateDate("updateDate")
        .build();
  }
}
