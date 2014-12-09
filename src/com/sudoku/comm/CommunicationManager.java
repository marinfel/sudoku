package com.sudoku.comm;

import com.sudoku.comm.generated.*;
import com.sudoku.util.CollectionUtil;
import com.sudoku.comm.ConnectionManager;
import com.sudoku.comm.DiscoverNodesTimerTask;

import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

import java.util.concurrent.ConcurrentHashMap;

public final class CommunicationManager {
  private static volatile CommunicationManager instance = null;
  private String localIp;
  private String uuid;
  private String login;
  private ArrayList<String> connectedIps;
  private Server nodeExplorerServer;
  private Server dataRetrieverServer;

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
    dataRetrieverServer = new DataRetrieverServer();
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
    this.localIp = nodeExplorerServer.getInetAddress();
    this.listLocalIp = connectedIps;
    this.ipsCurrentSession = new ArrayList<String>();
    this.ipsConnected = new ConcurrentHashMap<String, ConnectionManager>();
    this.ipsToConfirm = new ConcurrentHashMap<String, ConnectionManager>();
    startServer();
  }

  private void startServer() {
    nodeExplorerServer.startServer();
    dataRetrieverServer.startServer();
  }

  public void discoverNodes() throws IOException {
    /*if (connectedIps != null) {
      ArrayList<String> newConnectedIps =
          (ArrayList<String>) connectedIps.clone();
      newConnectedIps.add(localIp);
      Message message = new Message(uuid, login, newConnectedIps);
      newConnectedIps = connectedIps;
      for (String ip : connectedIps) {
        NettyTransceiver client = new NettyTransceiver(
            new InetSocketAddress(ip, nodeExplorerServer.getPort()));
        NodeExplorer explorer = (NodeExplorer)
            SpecificRequestor.getClient(NodeExplorer.class, client);
        Message receivedMessage = explorer.discoverNode(message);
        // TODO: deal with storing uuids and logins: hashtable with uuid as key
        newConnectedIps = CollectionUtil.merge(newConnectedIps,
            receivedMessage.getListIps(),
            localIp);
        client.close();
      }
      connectedIps = newConnectedIps;*/
    addIpToConfirm(listLocalIp);
    if(timerDiscoverNodes == null) {
        timerDiscoverNodes = new Timer();   
        timerDiscoverNodes
            .schedule(new DiscoverNodesTimerTask(), new Date(), 1000 * 5);
    }
  } 

  public ConcurrentHashMap<String, ConnectionManager> getIpsConnected() {
    return ipsConnected;
  }

  public void disconnect() throws IOException {
   /* if (connectedIps != null) {
      for (String ip : connectedIps) {
        NettyTransceiver client = new NettyTransceiver(
            new InetSocketAddress(ip, nodeExplorerServer.getPort()));
        NodeExplorer explorer = (NodeExplorer)
            SpecificRequestor.getClient(NodeExplorer.class, client);
        explorer.disconnect(localIp);
        client.close();
      }
    }
    dataRetrieverServer.stopServer();
    nodeExplorerServer.stopServer();*/
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
      ipsToConfirm = new ConcurrentHashMap<>();
    }

    Iterator<String> itr = listIp.iterator();
    while(itr.hasNext()) {
      addIpToConfirm(itr.next());
    }
  }

  public void addIpToConfirm(String ip) {
    if(ipsToConfirm == null){
      ipsToConfirm = new ConcurrentHashMap<>();
    }
    if(!ipsConnected.containsKey(ip) && !ip.equals(localIp)) {
      ipsToConfirm.put(ip, new AvroConnectionManager(localIp));
    }
  }

  public ArrayList<com.sudoku.data.model.Grid> getAllGrids()
      throws IOException {
    ArrayList<com.sudoku.data.model.Grid> resultGrids = new ArrayList<>();
    if (connectedIps != null) {
      ArrayList<Grid> grids = new ArrayList<>();
      for (String ip : connectedIps) {
        NettyTransceiver client = new NettyTransceiver(
            new InetSocketAddress(ip, dataRetrieverServer.getPort()));
        DataRetriever retriever = (DataRetriever)
            SpecificRequestor.getClient(DataRetriever.class, client);
        grids.addAll(retriever.getGrids());
      }
      for (Grid grid : grids) {
        resultGrids.add(com.sudoku.data.model.Grid.buildFromAvroGrid(grid));
      }
    }
    return resultGrids;
  }

  public ArrayList<com.sudoku.data.model.Grid> getAllGrids(
      com.sudoku.data.model.User user) throws IOException {
    return getAllGrids(user.getIpAddress());
  }

  public ArrayList<com.sudoku.data.model.Grid> getAllGrids(String ip)
      throws IOException {
    ArrayList<com.sudoku.data.model.Grid> grids = new ArrayList<>();
    if (ip != null) {
      NettyTransceiver client = new NettyTransceiver(
          new InetSocketAddress(ip, dataRetrieverServer.getPort()));
      DataRetriever retriever = (DataRetriever)
          SpecificRequestor.getClient(DataRetriever.class, client);
      for (Grid grid : retriever.getGrids()) {
        grids.add(com.sudoku.data.model.Grid.buildFromAvroGrid(grid));
      }
    }
    return grids;
  }

  public ArrayList<com.sudoku.data.model.User> getAllProfiles()
      throws IOException {
    ArrayList<com.sudoku.data.model.User> users = new ArrayList<>();
    if (connectedIps != null) {
      for (String ip : connectedIps) {
        NettyTransceiver client = new NettyTransceiver(
            new InetSocketAddress(ip, dataRetrieverServer.getPort()));
        DataRetriever retriever = (DataRetriever)
            SpecificRequestor.getClient(DataRetriever.class, client);
        users.add(com.sudoku.data.model.User
            .buildFromAvroUser(retriever.getProfile()));
      }
    }
    return users;
  }

  public com.sudoku.data.model.User getProfile(User user) throws IOException {
    return getProfile(user.getIpAddress());
  }

  public com.sudoku.data.model.User getProfile(String ip) throws IOException {
    if (ip != null) {
      NettyTransceiver client = new NettyTransceiver(
          new InetSocketAddress(ip, dataRetrieverServer.getPort()));
      DataRetriever retriever = (DataRetriever)
          SpecificRequestor.getClient(DataRetriever.class, client);
      return com.sudoku.data.model.User
          .buildFromAvroUser(retriever.getProfile());
    }
    return null;
  }

  public void pushComment(Comment comment, UUID gridUuid) throws IOException {
    if (connectedIps != null) {
      for (String ip : connectedIps) {
        NettyTransceiver client = new NettyTransceiver(
            new InetSocketAddress(ip, dataRetrieverServer.getPort()));
        DataRetriever retriever = (DataRetriever)
            SpecificRequestor.getClient(DataRetriever.class, client);
        retriever.commentGrid(comment, gridUuid.toString());
      }
    }
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