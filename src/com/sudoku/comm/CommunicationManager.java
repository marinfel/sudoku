package com.sudoku.comm;

import com.sudoku.comm.generated.Message;
import com.sudoku.comm.generated.NodeExplorer;
import com.sudoku.data.model.Comment;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.User;
import com.sudoku.util.CollectionUtil;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public final class CommunicationManager {
  private Logger logger = LoggerFactory.getLogger(CommunicationManager.class);
  
  private static volatile CommunicationManager instance = null;

  private String localIp;
  private String uuid;
  private String login;
  private ArrayList<String> connectedIps;
  private SudokuServer server;
  private final int PORT = 11023;

  private CommunicationManager() {
    super();
    server = new AvroServer();
  }

  public final static CommunicationManager getInstance() {
    if (instance == null) {
      synchronized(CommunicationManager.class) {
        if (instance == null){
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
    this.localIp = server.getServerInetAddresses();
    startServer();
  }

  private void startServer() {
    server.startServer();
  }

  public void discoverNodes() throws IOException {
    Message message = new Message(uuid, login, connectedIps);
    if (connectedIps != null) {
      ArrayList<String> newConnectedIps = connectedIps;
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
    server.stopServer();
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