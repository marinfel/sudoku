package com.sudoku.comm;

import com.sudoku.comm.generated.*;
import com.sudoku.util.CollectionUtil;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public final class CommunicationManager {
  private static volatile CommunicationManager instance = null;
  private String localIp;
  private String uuid;
  private String login;
  private ArrayList<String> connectedIps;
  private Server nodeExplorerServer;
  private Server dataRetrieverServer;

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
    startServer();
  }

  private void startServer() {
    nodeExplorerServer.startServer();
    dataRetrieverServer.startServer();
  }

  public void discoverNodes() throws IOException {
    if (connectedIps != null) {
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
      connectedIps = newConnectedIps;
    }
  }

  public void disconnect() throws IOException {
    if (connectedIps != null) {
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
    nodeExplorerServer.stopServer();
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

  public void pushCommentAndSync(Comment newComment, Grid gridToSync) { }

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