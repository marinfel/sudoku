package com.sudoku.comm;

import com.sudoku.comm.generated.Message;
import com.sudoku.comm.generated.NodeExplorer;
import org.apache.avro.AvroRemoteException;

import java.util.ArrayList;

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
        .setListIps(new ArrayList<>(commManager.getConnectedIps())  )
        .setLogin(commManager.getLogin())
        .setUuid(commManager.getUuid())
        .build();
  }

  @Override
  public Void publishIpsToConfirm(Message sentMessage)
      throws AvroRemoteException {
    commManager.addIpToConfirm(sentMessage.getListIps());
    return null;
  }

  @Override
  public Void disconnect(String ip) throws AvroRemoteException {
    commManager.removeConnectedIp(ip);
    return null;
  }
}
