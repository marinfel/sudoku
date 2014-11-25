package com.sudoku.comm;

import com.sudoku.comm.generated.Message;
import com.sudoku.comm.generated.NodeExplorer;
import com.sudoku.data.model.*;

import com.sudoku.util.CollectionUtil;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.avro.ipc.specific.SpecificResponder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Enumeration;

public final class CommunicationManager {
  private Logger logger = LoggerFactory.getLogger(CommunicationManager.class);
  
  private static volatile CommunicationManager instance = null;

  private String localIp;
  private String uuid;
  private String login;
  private ArrayList<String> connectedIps;
  private Server server;
  private final int PORT = 11023;

  private CommunicationManager() {
    super();
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
    try {
      this.localIp = getLocalInetAddress().getHostAddress();
    } catch (UnknownHostException ex) {
      logger.error(ex.toString());
    }
    startServer();
  }

  private void startServer() {
    server = new NettyServer(
        new SpecificResponder(NodeExplorer.class, new NodeExplorerImpl()),
        new InetSocketAddress(localIp, PORT)
    );
    server.start();
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
    server.close();
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

  private InetAddress getLocalInetAddress() throws UnknownHostException {
    try {
      InetAddress candidateAddress = null;

      for (Enumeration<NetworkInterface> networkInterfaces =
               NetworkInterface.getNetworkInterfaces();
           networkInterfaces.hasMoreElements();) {

        NetworkInterface networkInterface =
            (NetworkInterface) networkInterfaces.nextElement();
        for (Enumeration<InetAddress> inetAddresses =
                 networkInterface.getInetAddresses();
             inetAddresses.hasMoreElements();) {

          InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
          if (!inetAddress.isLoopbackAddress()) {
            if (inetAddress.isSiteLocalAddress()) {
              return inetAddress;
            } else if (candidateAddress == null) {
              candidateAddress = inetAddress;
            }
          }
        }
      }

      if (candidateAddress != null) {
        return candidateAddress;
      }

      InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
      if(jdkSuppliedAddress == null) {
        throw new UnknownHostException("InetAddress.getLocalHost() is null.");
      }
      return jdkSuppliedAddress;
    } catch (Exception ex) {
      logger.error(ex.toString());
      UnknownHostException unknownHostException =
          new UnknownHostException("Failed to determine IP: " + ex);
      unknownHostException.initCause(ex);
      throw unknownHostException;
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