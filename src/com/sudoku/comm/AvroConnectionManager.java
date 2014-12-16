package com.sudoku.comm;

import com.sudoku.comm.generated.Message;
import com.sudoku.comm.generated.NodeExplorer;
import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * Class representing a connection to a remote user
 * @author Murat Cansiz
 * @see com.sudoku.comm.ConnectionManager
 */
public class AvroConnectionManager extends ConnectionManager {
  private static final Long CONNECTION_TIME_OUT = 1000L;

  private NettyTransceiver client;
  private NodeExplorer explorer;

  /**
   * Class constructor
   * @param ip ip of the connection
   */
  public AvroConnectionManager(String ip) {
    super(ip);
    client = null;
  }

  /**
   * Opens a distant connection
   * @throws OfflineUserException
   */
  public void openConnection() throws OfflineUserException {
    if (client == null) {
      try {
        client =
            new NettyTransceiver(new InetSocketAddress(ipAddress, NODEPORT),
                CONNECTION_TIME_OUT);
        explorer = (NodeExplorer)
            SpecificRequestor.getClient(NodeExplorer.class, client);
        isConnected = true;
      } catch(IOException exc) {
        throw new OfflineUserException();
      }
    }
  }

  /**
   * Gets the list of distant connected ips
   * @param newConnectedIps connected ips to send to the distant connection
   * @return a list of connected ips
   * @throws ConnectionClosedException
   * @throws OfflineUserException
   */
  public List<String> getConnectedIps(List<String> newConnectedIps)
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

  /**
   * Publishes the current list of connected ips to the distant connection
   * @param ips list of ips to be published
   * @throws OfflineUserException
   * @throws ConnectionClosedException
   */
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

  /**
   * Disconnects this peer to the distant peer
   * @throws OfflineUserException
   * @throws ConnectionClosedException
   */
  public void disconnect() throws OfflineUserException,
     ConnectionClosedException {
    super.disconnect();
    try {
      explorer.disconnect(CommunicationManager.getInstance().getLocalIp());
    } catch (AvroRemoteException exc) {
      throw new OfflineUserException();
    }
  }

  /**
   * Closes connection with the distant peer
   * @throws OfflineUserException
   */
  public void closeConnection() throws OfflineUserException {
    if (client != null) {
      client.close();
      client = null;
    }
    isConnected = false;
  }
}
