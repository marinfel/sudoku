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
import java.util.HashMap;
import java.util.Runnable;

public class DiscoverNodesTimerTask extends TimerTask {
  private final String targetIp;
  private HashMap<String, ConnectionManager> ipToConfirm;
  private HashMap<String, ConnectionManager> ipConnected;

  public DiscoverNodesTimerTask(ArrayList<String> listCurrentSessionIp, HashMap<String, ConnectionManager> ipToConfirm,  HashMap<String, ConnectionManager> ipConnected) {
    this.targetIp = targetIp;
    this.ipToConfirm = ipToConfirm;
    this.ipConnected = ipConnected;
  }

  public void launchDiscovery() {
    Iterator<String> itr = ipToConfirm.keySet();
    while (itr.hasNext()) {
      ArrayList<String> newConnectedIps =
          (ArrayList<String>) ipConnected.clone();
      newConnectedIps.add(Communic);

      Iterator<String> itr = connectedIps.iterator();
      while (itr.hasNext()) {
        String ip = itr.next();
        System.out.print("Connect to:" + ip + "\n ");
        AvroConnectionManager cm = new AvroConnectionManager(ip);
        try {
          cm.openConnection();
          List<String> remoteIps = cm.getConnectedIps(connectedIps);
          Iterator<String> itrRemote = remoteIps.iterator();
          System.out.println("Retrieved remote ips:");
          
          while (itrRemote.hasNext()) {
            String addr = itrRemote.next();
            System.out.println(addr);
          }
          System.out.println("*********************");
          cm.closeConnection();  
        }
        catch (ConnectionManager.OfflineUserException exc) {System.out.print("Offline user: " + ip + "\n ");}
        catch (ConnectionManager.ConnectionClosedException exc) {System.out.print("Closed connection: " + ip + "\n ");}
      }
      connectedIps = newConnectedIps;
    }
  }

  public void syncIpToConfirm() {

  }

  public void syncIpConnected() {

  }

  public void run() {

  }
}