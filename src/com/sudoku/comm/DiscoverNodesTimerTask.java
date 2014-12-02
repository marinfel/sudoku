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
import java.util.TimerTask;
import java.util.Iterator;
import java.util.Timer;

public class DiscoverNodesTimerTask extends TimerTask {
  private final String localIp;
  private final CommunicationManager communicationManager;

  public DiscoverNodesTimerTask() {
    this.communicationManager = CommunicationManager.getInstance();
    this.localIp = communicationManager.getLocalIp();
  }

  public void updateIpsInCommunicationManager(String ipToUpdate, Iterator<String> iterator) {
    communicationManager.syncIps(ipToUpdate, iterator);
  }

  public void launchDiscovery() {
    HashMap<String, ConnectionManager> ipsToConfirm =  communicationManager.getIpsToConfirm();
    Iterator<String> itr = ipsToConfirm.keySet().iterator();
    HashMap<String, ConnectionManager> ipsConnected =  communicationManager.getIpsConnected();

    while (itr.hasNext()) {

              
              //DEBUG MURAT  
              //      
              ArrayList<String> ipsConnectedTMP = new ArrayList<String>(ipsConnected.keySet());
              Iterator<String> itrDebug = ipsConnectedTMP.iterator();

                System.out.println("ipsConnectedTMP");

          while (itrDebug.hasNext()) {
            System.out.println(itrDebug.next());
          }
                          System.out.println("/ipsConnectedTMP");


      ArrayList<String> ipToShare = new ArrayList<String>(ipsConnected.keySet());
      ipToShare.add(localIp);

      String currentIpToConfirm = itr.next();
      System.out.println("[BEGIN TimerTask] Connecting to :" + currentIpToConfirm + "\n ");
      AvroConnectionManager currentConnectionManager = new AvroConnectionManager(currentIpToConfirm);
      try {
          currentConnectionManager.openConnection();
          ipsToConfirm.put(currentIpToConfirm, currentConnectionManager);

          List<String> ipFromRemoteNode = currentConnectionManager.getConnectedIps(ipToShare);
          
          //update ipsConnected & ipsToConfirm
          updateIpsInCommunicationManager(currentIpToConfirm, itr);

          //DEBUG
          Iterator<String> itrRemote = ipFromRemoteNode.iterator();
          System.out.println("[TimerTask] ip From Remote Node (from " + currentIpToConfirm + ") : ");
          while (itrRemote.hasNext()) {
            String addr = itrRemote.next();
            System.out.println(addr);
          }
          System.out.println("[END TimerTask]");
          System.out.println("*********************");
          // END DEBUG
          
          currentConnectionManager.closeConnection();  
      }
        catch (ConnectionManager.OfflineUserException exc) {System.out.print("Offline user: " + currentIpToConfirm + "\n ");}
        catch (ConnectionManager.ConnectionClosedException exc) {System.out.print("Closed connection: " + currentIpToConfirm + "\n ");}   
    }
  }

  public void run() {
    launchDiscovery();
  }
}