package com.sudoku.comm;

import com.sudoku.comm.generated.Message;
import com.sudoku.comm.generated.NodeExplorer;
import org.apache.avro.AvroRemoteException;

import java.util.ArrayList;

/**
 * Implementation of the Avro-generated NodeExplorer interface
 * @author Benjamin Fradet
 * @see NodeExplorer
 */
public class NodeExplorerImpl implements NodeExplorer {
  private CommunicationManager commManager;

  /**
   * Class constructor
   */
  public NodeExplorerImpl() {
    this.commManager = CommunicationManager.getInstance();
  }

  /**
   * Discovers a node by adding the ips contained in the message to the
   * connected ips
   * @param sentMessage message sent by the requester
   * @return a message
   * @throws AvroRemoteException
   */
  @Override
  public Message discoverNode(Message sentMessage) throws AvroRemoteException {
    commManager.addIpToConfirm(sentMessage.getListIps());
    return Message.newBuilder()
        .setListIps(new ArrayList<>(commManager.getConnectedIps()))
        .setLogin(commManager.getLogin())
        .setUuid(commManager.getUuid())
        .build();
  }

  /**
   * Adds the ips known by the requester the ips to confirm
   * @param sentMessage message sent by the requester
   * @return nothing
   * @throws AvroRemoteException
   */
  @Override
  public Void publishIpsToConfirm(Message sentMessage)
      throws AvroRemoteException {
    commManager.addIpToConfirm(sentMessage.getListIps());
    return null;
  }

  /**
   * Disconnects the message's sender by removing its ip from the list of
   * connected ips
   * @param ip the ip to remove
   * @return nothing
   * @throws AvroRemoteException
   */
  @Override
  public Void disconnect(String ip) throws AvroRemoteException {
    commManager.removeConnectedIp(ip);
    return null;
  }
}
