package com.sudoku.comm;

import com.sudoku.comm.generated.Message;
import com.sudoku.comm.generated.NodeExplorer;
import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.User;
import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

// Class representing a connection to a remote user
public class AvroConnectionManager extends ConnectionManager {
  private NettyTransceiver client;
  private NodeExplorer explorer;

  public AvroConnectionManager(String ip) {
    super(ip);
  }


  public void openConnection()
     throws OfflineUserException {
    System.out.println("CM - connect START");
    try {
      client = new NettyTransceiver(new InetSocketAddress(ipAddress, NODE_PORT));
      explorer = (NodeExplorer)
          SpecificRequestor.getClient(NodeExplorer.class, client);
    }
    catch(IOException exc) {throw new OfflineUserException();}
    System.out.println("CM - connect STOP");
    isConnected = true;
  }

  public List<String> getConnectedIps(ArrayList<String> newConnectedIps)
     throws ConnectionClosedException, OfflineUserException {
    System.out.println("CM - getConnectedIps START");
    super.getConnectedIps(newConnectedIps);
    User localUser = UserManager.getInstance().getLoggedUser();
    //Message userCredentials = new Message("wut", localUser.getPseudo(), newConnectedIps);
    Message userCredentials = new Message("wut", "toto", newConnectedIps);
    List<String> res;


    try {
      Message receivedMessage = explorer.discoverNode(userCredentials);
      res = receivedMessage.getListIps();
    }
    catch (AvroRemoteException exc) {throw new OfflineUserException();}
    System.out.println("CM - getConnectedIps STOP");
    return res;
  }

  public void closeConnection() throws OfflineUserException {
    System.out.println("CM - CLOSE CONNECTION");
    client.close();
    client = null;
    isConnected = false;
  }

  /*public ArrayList<Grid> getGrids() throws ConnectionClosedException {

  }
  
  public User getProfile() throws ConnectionClosedException {

  }

  public void pushComment(Comment c, Grid g) throws ConnectionClosedException {

  }

  public void publishComment(Comment c, Grid g) throws ConnectionClosedException {

  }
  */
}
