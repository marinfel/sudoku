package com.sudoku.comm;

import com.sudoku.comm.generated.*;

import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author someone
 */
public final class CommunicationManager {
  private static volatile CommunicationManager instance = null;
  private String localIp;
  private String uuid;
  private String login;
  private Server nodeExplorerServer;
  private Server dataRetrieverServer;
  private static final int MS_TIMER = 1000 * 5;

  private static final Logger LOGGER = LoggerFactory.getLogger(DiscoverNodesTimerTask.class);

  private List<String> localIps;
  private Map<String, ConnectionManager> ipsToConfirm;
  private Map<String, ConnectionManager> ipsConnected;

  private Timer timerDiscoverNodes;

  private CommunicationManager() {
    super();
    nodeExplorerServer = new NodeExplorerServer();
    dataRetrieverServer = new DataRetrieverServer();
  }

  public static final CommunicationManager getInstance() {
    if (instance == null) {
      synchronized (CommunicationManager.class) {
        if (instance == null) {
          instance = new CommunicationManager();
        }
      }
    }
    return instance;
  }

  public void init(String uuid, String login, List<String> localIps)
      throws IOException {
    this.uuid = uuid;
    this.login = login;
    this.localIp = nodeExplorerServer.getInetAddress();
    this.localIps = localIps;
    this.ipsConnected = new ConcurrentHashMap<String, ConnectionManager>();
    this.ipsToConfirm = new ConcurrentHashMap<String, ConnectionManager>();
    startServer();
    discoverNodes();
  }

  private void startServer() {
    nodeExplorerServer.startServer();
    dataRetrieverServer.startServer();
  }

  private void discoverNodes() throws IOException {
    addIpToConfirm(localIps);
    if(timerDiscoverNodes == null) {
      timerDiscoverNodes = new Timer();
      timerDiscoverNodes
          .schedule(new DiscoverNodesTimerTask(), new Date(), MS_TIMER);
    }
  } 

  public Map<String, ConnectionManager> getIpsConnected() {
    return ipsConnected;
  }

  public Map<String, ConnectionManager> getIpsToConfirm() {
    return ipsToConfirm;
  }

  public void addIpConnected(String ip, ConnectionManager cm) {
    ipsConnected.put(ip, cm);
  }

  public void syncIps(String ipToUpdate, Iterator<String> iterator) {
    ConnectionManager tmpCM = ipsToConfirm.get(ipToUpdate);
    addIpConnected(ipToUpdate, tmpCM);
    iterator.remove();
  }

  public void addIpToConfirm(List<String> listIp) {
    if(ipsToConfirm == null){
      ipsToConfirm = new ConcurrentHashMap<>();
    }

    Iterator<String> itr = listIp.iterator();
    while (itr.hasNext()) {
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

  public List<com.sudoku.data.model.Grid> getAllGrids()
      throws IOException {
    List<com.sudoku.data.model.Grid> resultGrids = new ArrayList<>();
    List<Grid> grids = new ArrayList<>();
    for (String ip : ipsConnected.keySet()) {
      NettyTransceiver client = new NettyTransceiver(
          new InetSocketAddress(ip, dataRetrieverServer.getPort()));
      DataRetriever retriever = (DataRetriever)
          SpecificRequestor.getClient(DataRetriever.class, client);
      grids.addAll(retriever.getGrids());
    }
    for (Grid grid : grids) {
      resultGrids.add(com.sudoku.data.model.Grid.buildFromAvroGrid(grid));
    }
    return resultGrids;
  }

  public void removeConnectedIp(String ip) {
    ConnectionManager cm = ipsConnected.get(ip);
    if (cm != null) {
      try {
        cm.closeConnection();
      } catch(ConnectionManager.OfflineUserException exc) {
        // Nothing to do, already closed
      }
      ipsConnected.remove(ip);
      ipsToConfirm.put(ip, cm);
    }
  }

  public void disconnect() throws IOException {
    nodeExplorerServer.stopServer();
    timerDiscoverNodes.cancel();
    Iterator<String> itr = ipsConnected.keySet().iterator();
    while(itr.hasNext()) {
      try {
        String ip = itr.next();
        ConnectionManager cm = ipsConnected.get(ip);
        cm.openConnection();
        cm.disconnect();
        cm.closeConnection();
      }catch(ConnectionManager.OfflineUserException |
          ConnectionManager.ConnectionClosedException ex) {
        LOGGER.info(ex.toString(), ex);
      }
      itr.remove();
    }
  }

  public List<com.sudoku.data.model.Grid> getAllGrids(
      com.sudoku.data.model.User user) throws IOException {
    return getAllGrids(user.getIpAddress());
  }

  public List<com.sudoku.data.model.Grid> getAllGrids(String ip)
      throws IOException {
    List<com.sudoku.data.model.Grid> grids = new ArrayList<>();
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

  public List<com.sudoku.data.model.User> getAllProfiles()
      throws IOException {
    List<com.sudoku.data.model.User> users = new ArrayList<>();
    for (String ip : ipsConnected.keySet()) {
      NettyTransceiver client = new NettyTransceiver(
          new InetSocketAddress(ip, dataRetrieverServer.getPort()));
      DataRetriever retriever = (DataRetriever)
          SpecificRequestor.getClient(DataRetriever.class, client);
      users.add(com.sudoku.data.model.User
          .buildFromAvroUser(retriever.getProfile()));
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
    for (String ip : ipsConnected.keySet()) {
      NettyTransceiver client = new NettyTransceiver(
          new InetSocketAddress(ip, dataRetrieverServer.getPort()));
      DataRetriever retriever = (DataRetriever)
          SpecificRequestor.getClient(DataRetriever.class, client);
      retriever.commentGrid(comment, gridUuid.toString());
    }
  }

  public Set<String> getConnectedIps() {
    return ipsConnected.keySet();
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