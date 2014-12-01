package com.sudoku.comm;

import com.sudoku.comm.generated.Message;
import com.sudoku.comm.generated.NodeExplorer;
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

  public AvroConnectionManager(User u) {
    super(u);
  }


  public void openConnection()
     throws OfflineUserException {
    System.out.println("CM - connect START");
    client = new NettyTransceiver(new InetSocketAddress(user.getIpAdress(), NODE_PORT));
    explorer = (NodeExplorer)
        SpecificRequestor.getClient(NodeExplorer.class, client);
    System.out.println("CM - connect STOP");
  }

  public List<String> getConnectedIps(ArrayList<String> newConnectedIps)
     throws OfflineUserException {
    System.out.println("CM - getConnectedIps START");
    Message userCredentials = new Message(, login, newConnectedIps);
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
