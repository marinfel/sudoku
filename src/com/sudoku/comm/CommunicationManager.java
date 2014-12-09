package com.sudoku.comm;

import com.sudoku.comm.generated.Message;
import com.sudoku.comm.generated.NodeExplorer;
import com.sudoku.data.model.Comment;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.User;
import com.sudoku.util.CollectionUtil;
import com.sudoku.comm.ConnectionManager;
import com.sudoku.comm.DiscoverNodesTimerTask;

import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public final class CommunicationManager {
  private static volatile CommunicationManager instance = null;
  private final int PORT = 11023;
  private String localIp;
  private String uuid;
  private String login;
  private ArrayList<String> connectedIps;
  private Server nodeExplorerServer;

  private ArrayList<String> listLocalIp;
  //list of all IPs to which the user was connected during the session
  private ArrayList<String> ipsCurrentSession;
  private ConcurrentHashMap<String, ConnectionManager> ipsToConfirm;
  private ConcurrentHashMap<String, ConnectionManager> ipsConnected;

  private Timer timerDiscoverNodes;
  private DiscoverNodesTimerTask discoverNodesTimerTask;

  private CommunicationManager() {
    super();
    nodeExplorerServer = new NodeExplorerServer();
  }

  public final static CommunicationManager getInstance() {
    if (instance == null) {
      synchronized (CommunicationManager.class) {
        if (instance == null) {
          instance = new CommunicationManager();
        }
      }
    }
    return instance;
  }

  public void init(String uuid, String login, ArrayList<String> connectedIps) {
    this.uuid = uuid;
    this.login = login;
    this.connectedIps = connectedIps;
    this.listLocalIp = connectedIps;
    this.ipsCurrentSession = new ArrayList<String>();
    this.ipsConnected = new ConcurrentHashMap<String, ConnectionManager>();
    this.ipsToConfirm = new ConcurrentHashMap<String, ConnectionManager>();
    this.localIp = nodeExplorerServer.getServerInetAddresses();
    startServer();
  }

  private void startServer() {
    nodeExplorerServer.startServer();
  }

  public void discoverNodes() throws IOException {
    addIpToConfirm(listLocalIp);
    if(timerDiscoverNodes == null) {
        timerDiscoverNodes = new Timer();   
        timerDiscoverNodes.schedule(new DiscoverNodesTimerTask(), new Date(), 1000 * 5);
    }
  } 

  public ConcurrentHashMap<String, ConnectionManager> getIpsConnected() {
    return ipsConnected;
  }

  public ConcurrentHashMap<String, ConnectionManager> getIpsToConfirm() {
    return ipsToConfirm;
  }

  public void addIpConnected(String ip, ConnectionManager cm) {
    ipsConnected.put(ip, cm);
  }

  public void addIpCurrentSession(String ip) {
    ipsCurrentSession.add(ip);
  }

  public void syncIps(String ipToUpdate, Iterator<String> iterator) {
    ConnectionManager tmpCM = ipsToConfirm.get(ipToUpdate);
    addIpConnected(ipToUpdate, tmpCM);
    iterator.remove();
    addIpCurrentSession(ipToUpdate);
  }

  public void addIpToConfirm(List<String> listIp) {
    if(ipsToConfirm == null){
      ipsToConfirm = new ConcurrentHashMap();
    }

    Iterator<String> itr = listIp.iterator();
    while(itr.hasNext()) {
      addIpToConfirm(itr.next());
    }
  }

  public void addIpToConfirm(String ip) {
    if(ipsToConfirm == null){
      ipsToConfirm = new ConcurrentHashMap();
    }
    if(!ipsConnected.containsKey(ip) && ip != localIp) {
      ipsToConfirm.put(ip, new AvroConnectionManager(localIp));
    }
  }

  public void removeConnectedIp(String ip) {
    ConnectionManager cm = ipsConnected.get(ip);
    if (cm != null) {
      try {
        cm.closeConnection();
      }
      catch(ConnectionManager.OfflineUserException exc) {
        // Nothing to do, already closed
      }
      ipsConnected.remove(ip);
      ipsToConfirm.put(ip, cm);
    }
  }

  public void disconnect() throws IOException {
    Iterator<String> itr = ipsConnected.keySet().iterator();
    while(itr.hasNext()) {
      try {
        ConnectionManager cm = ipsConnected.get(itr.next());
        cm.openConnection();
        cm.disconnect();
        cm.closeConnection();
        
      }
      catch(ConnectionManager.OfflineUserException ex){}
      catch(ConnectionManager.ConnectionClosedException ex){}
      timerDiscoverNodes.cancel();
    }

  }

  public ArrayList<Grid> getAllGrids() {
    return null;
  }

  public ArrayList<Grid> getAllGrids(User user) {
    return null;
  }

  public ArrayList<User> getAllProfiles() {
    return null;
  }

  public void pushCommentAndSync(Comment newComment, Grid gridToSync) {
  }

  public ArrayList<String> getConnectedIps() {
    return connectedIps;
  }

  public void setConnectedIps(ArrayList<String> connectedIps) {
    this.connectedIps = connectedIps;
  }

  public String getLocalIp() {
    return localIp;
  }

  public String getUuid() {
    return uuid;
  }

  public String getLogin() {
    return login;
  }
}