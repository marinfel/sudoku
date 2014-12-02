package com.sudoku.comm;

import com.sudoku.comm.generated.Message;
import com.sudoku.comm.generated.NodeExplorer;
import com.sudoku.util.CollectionUtil;
import org.apache.avro.AvroRemoteException;

/**
 * Created by ben on 19/11/14.
 */
public class NodeExplorerImpl implements NodeExplorer {
  private CommunicationManager commManager;

  public NodeExplorerImpl() {
    this.commManager = CommunicationManager.getInstance();
  }

  @Override
  public Message discoverNode(Message sentMessage) throws AvroRemoteException {
    commManager.addIpToConfirm(sentMessage.getListIps());
    return Message.newBuilder()
        .setListIps(commManager.getConnectedIps())
        .setLogin(commManager.getLogin())
        .setUuid(commManager.getUuid())
        .build();
  }

  @Override
  public Void publishIpsToConfirm(Message sentMessage) throws AvroRemoteException {
    commManager.addIpToConfirm(sentMessage.getListIps());
    return null;
  }
  
  @Override
  public Void disconnect(String ip) throws AvroRemoteException {
    commManager.getConnectedIps().remove(ip);
    return null;
  }
}
