package com.sudoku.comm;

import com.sudoku.comm.generated.Message;
import com.sudoku.comm.generated.NodeExplorer;
import com.sudoku.data.model.Comment;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.User;
import com.sudoku.util.CollectionUtil;
import com.sudoku.comm.ConnectionManager;

import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.Iterator;
import java.util.Timer;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class DiscoverNodesTimerTask extends TimerTask {
  private final String localIp;
  private final CommunicationManager communicationManager;
  private static final Logger LOGGER = LoggerFactory.getLogger(DiscoverNodesTimerTask.class);

  public DiscoverNodesTimerTask() {
    this.communicationManager = CommunicationManager.getInstance();
    this.localIp = communicationManager.getLocalIp();
  }

  public void updateIpsInCommunicationManager(String ipToUpdate,
                                              Iterator<String> iterator) {
    communicationManager.syncIps(ipToUpdate, iterator);
  }

  public void launchDiscovery() {
    Map<String, ConnectionManager> ipsToConfirm =
        communicationManager.getIpsToConfirm();
    Iterator<String> itr = ipsToConfirm.keySet().iterator();
    Map<String, ConnectionManager> ipsConnected =
        communicationManager.getIpsConnected();

    List<String> ipsToPublish = new ArrayList<>();
    while (itr.hasNext()) {
      List<String> ipsConnectedTMP =
          new ArrayList<>(ipsConnected.keySet());
      Iterator<String> itrDebug = ipsConnectedTMP.iterator();

      LOGGER.debug("ipsConnectedTMP");

      while (itrDebug.hasNext()) {
        LOGGER.debug(itrDebug.next());
      }
      LOGGER.debug("/ipsConnectedTMP");

      List<String> ipToShare = new ArrayList<>(ipsConnected.keySet());
      ipToShare.add(localIp);

      String currentIpToConfirm = itr.next();
      LOGGER.debug("[BEGIN TimerTask] Connecting to :" +
          currentIpToConfirm + "\n ");
      AvroConnectionManager currentConnectionManager =
          new AvroConnectionManager(currentIpToConfirm);
      try {
        currentConnectionManager.openConnection();
        ipsToConfirm.put(currentIpToConfirm, currentConnectionManager);

        List<String> ipFromRemoteNode =
            currentConnectionManager.getConnectedIps(ipToShare);
        for (String ipToP : ipFromRemoteNode) {
          ipsToPublish.add(ipToP);
        }

        //update ipsConnected & ipsToConfirm
        updateIpsInCommunicationManager(currentIpToConfirm, itr);

        //DEBUG
        Iterator<String> itrRemote = ipFromRemoteNode.iterator();
        LOGGER.debug("[TimerTask] ip From Remote Node (from " +
            currentIpToConfirm + ") : ");
        while (itrRemote.hasNext()) {
          String addr = itrRemote.next();
          LOGGER.debug(addr);
        }
        communicationManager.addIpToConfirm(ipFromRemoteNode);
        LOGGER.debug("[END TimerTask]");
        LOGGER.debug("*********************");
        // END DEBUG

        currentConnectionManager.closeConnection();
      } catch (ConnectionManager.OfflineUserException exc) {
        LOGGER.debug("Offline user: " + currentIpToConfirm + "\n ");
      } catch (ConnectionManager.ConnectionClosedException exc) {
        LOGGER.debug("Closed connection: " + currentIpToConfirm + "\n ");
      }

      // Publishing known nodes
      if (ipsToPublish.size() != 0) {
        Collection<ConnectionManager> connectedManagers = ipsConnected.values();
        for (ConnectionManager cm : connectedManagers) {
          try {
            cm.openConnection();
            cm.publishIps(ipsToPublish);
            cm.closeConnection();
          } catch (ConnectionManager.OfflineUserException exc) {
            LOGGER.debug("Offline user");
          } catch (ConnectionManager.ConnectionClosedException exc) {
            LOGGER.debug("Closed connection");
          }
        }
      }
    }
  }

  public void run() {
    launchDiscovery();
  }
}
