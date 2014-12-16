package com.sudoku.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.Iterator;
import java.util.Collection;

/**
 * Class handling node discovery as a task
 * @author Karim El Aktaa
 */
public class DiscoverNodesTimerTask extends TimerTask {
  private final String localIp;
  private final CommunicationManager communicationManager;
  private static final Logger LOGGER = LoggerFactory.getLogger(DiscoverNodesTimerTask.class);

  /**
   * Class constructor
   */
  public DiscoverNodesTimerTask() {
    this.communicationManager = CommunicationManager.getInstance();
    this.localIp = communicationManager.getLocalIp();
  }

  /**
   * Updates ips in the communication manager
   * @param ipToUpdate ip to be updated
   * @param iterator iterator
   */
  public void updateIpsInCommunicationManager(String ipToUpdate,
                                              Iterator<String> iterator) {
    communicationManager.syncIps(ipToUpdate, iterator);
  }

  /**
   * Method launching node discovery
   */
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

        /* update ipsConnected & ipsToConfirm */
        updateIpsInCommunicationManager(currentIpToConfirm, itr);

        /* DEBUG */
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
        /* END DEBUG */

        currentConnectionManager.closeConnection();
      } catch (ConnectionManager.OfflineUserException exc) {
        LOGGER.debug("Offline user: " + currentIpToConfirm + "\n ", exc);
      } catch (ConnectionManager.ConnectionClosedException exc) {
        LOGGER.debug("Closed connection: " + currentIpToConfirm + "\n ", exc);
      }

      /* Publishing known nodes */
      if (!ipsToPublish.isEmpty()) {
        Collection<ConnectionManager> connectedManagers = ipsConnected.values();
        for (ConnectionManager cm : connectedManagers) {
          try {
            cm.openConnection();
            cm.publishIps(ipsToPublish);
            cm.closeConnection();
          } catch (ConnectionManager.OfflineUserException exc) {
            LOGGER.debug("Offline user", exc);
          } catch (ConnectionManager.ConnectionClosedException exc) {
            LOGGER.debug("Closed connection", exc);
          }
        }
      }
    }
  }

  /**
   * Method to run the task in a separate thread
   */
  public void run() {
    launchDiscovery();
  }
}
