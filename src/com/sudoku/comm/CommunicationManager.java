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

public final class CommunicationManager {
  private static volatile CommunicationManager instance = null;
  private final int PORT = 11023;
  private String localIp;
  private String uuid;
  private String login;
  //private ArrayList<String> connectedIps;
  private Server nodeExplorerServer;
  private ArrayList<String> listLocalIp;
  //list of all IPs to which the user was connected during the session
  private ArrayList<String> listCurrentSessionIp;
  private HashMap<String, ConnectionManager> ipToConfirm;
  private HashMap<String, ConnectionManager> ipConnected;

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
    this.ipToConfirm = new HashMap();
    this.ipConnected = new HashMap();
    this.localIp = nodeExplorerServer.getServerInetAddresses();
    startServer();
  }

  private void startServer() {
    nodeExplorerServer.startServer();
  }

  public void discoverNodes() throws IOException {
    if (connectedIps != null) {
      ArrayList<String> newConnectedIps =
          (ArrayList<String>) connectedIps.clone();
      newConnectedIps.add(localIp);
      Message message = new Message(uuid, login, newConnectedIps);
      newConnectedIps = connectedIps;
      for (String ip : connectedIps) {
        NettyTransceiver client =
            new NettyTransceiver(new InetSocketAddress(ip, PORT));
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

  public void fillIpToConfirm(HashMap) {

  }

  public void disconnect() throws IOException {
    if (connectedIps != null) {
      for (String ip : connectedIps) {
        NettyTransceiver client =
            new NettyTransceiver(new InetSocketAddress(ip, PORT));
        NodeExplorer explorer = (NodeExplorer)
            SpecificRequestor.getClient(NodeExplorer.class, client);
        explorer.disconnect(localIp);
        client.close();
      }
    }
    nodeExplorerServer.stopServer();
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