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
  private HashMap<String, ConnectionManager> ipsToConfirm;
  private HashMap<String, ConnectionManager> ipsConnected;

  public DiscoverNodesTimerTask(ArrayList<String> ipsCurrentSession, HashMap<String, ConnectionManager> ipsToConfirm,  HashMap<String, ConnectionManager> ipsConnected) {
    this.ipsToConfirm = ipsToConfirm;
    this.ipsConnected = ipsConnected;
    this.localIp = CommunicationManager.getInstance().getLocalIp();
  }

  public void updateIpsConnectedAndIpsToConfirm(String ipToUpdate, Iterator<String> iterator) {
    ConnectionManager tmpCM = ipsToConfirm.get(ipToUpdate);
    ipsConnected.put(ipToUpdate, tmpCM);
    //ipsToConfirm.remove(ipToUpdate);
    iterator.remove();
    //ipsCurrentSession.add(ipToUpdate);
  }

  public void launchDiscovery() {
    Iterator<String> itr = ipsToConfirm.keySet().iterator();

    while (itr.hasNext()) {
      //ArrayList<String> ipToShare = (ArrayList<String>) ipsConnected.keySet();
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
          updateIpsConnectedAndIpsToConfirm(currentIpToConfirm, itr);

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