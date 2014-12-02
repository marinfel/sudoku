package com.sudoku.comm;

import com.sudoku.comm.generated.Message;
import com.sudoku.comm.generated.NodeExplorer;
import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.User;
import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;

import java.io.IOException;
import java.lang.Long;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
// Class representing a connection to a remote user
public class AvroConnectionManager extends ConnectionManager {
  private final static Long CONNECTION_TIME_OUT = 1000L;

  private NettyTransceiver client;
  private NodeExplorer explorer;

  public AvroConnectionManager(String ip) {
    super(ip);
  }

  public void openConnection() throws OfflineUserException {
    try {
      client = new NettyTransceiver(new InetSocketAddress(ipAddress, NODE_PORT),
          CONNECTION_TIME_OUT);
      explorer = (NodeExplorer)
          SpecificRequestor.getClient(NodeExplorer.class, client);
    } catch(IOException exc) {
      throw new OfflineUserException();
    }
    isConnected = true;
  }

  public List<String> getConnectedIps(ArrayList<String> newConnectedIps)
     throws ConnectionClosedException, OfflineUserException {
    super.getConnectedIps(newConnectedIps);
    CommunicationManager cm = CommunicationManager.getInstance();
    Message userCredentials =
        new Message(cm.getUuid(), cm.getLogin(), newConnectedIps);
    List<String> res;

    try {
      Message receivedMessage = explorer.discoverNode(userCredentials);
      res = receivedMessage.getListIps();
    } catch (AvroRemoteException exc) {
      throw new OfflineUserException();
    }
    return res;
  }

  public void publishIps(List<String> ips)
     throws OfflineUserException, ConnectionClosedException {
    super.publishIps(ips);
    try {
      CommunicationManager cm = CommunicationManager.getInstance();
      Message publishMessage = new Message(cm.getUuid(), cm.getLogin(), ips);
      explorer.publishIpsToConfirm(publishMessage);
    } catch (AvroRemoteException exc) {
      throw new OfflineUserException();
    }
  }

  public void closeConnection() throws OfflineUserException {
     try {
      explorer.disconnect(CommunicationManager.getInstance().getLocalIp());
    } catch (AvroRemoteException exc) {
       throw new OfflineUserException();
     }
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
