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
 * Singleton handling every distant operations
 * @author Murat Cansiz, Karim El Aktaa, Benjamin Fradet
 */
public final class CommunicationManager {
  private static volatile CommunicationManager instance = null;
  private String localIp;
  private String uuid;
  private String login;
  private Server nodeExplorerServer;
  private Server dataRetrieverServer;
  private static final int MS_TIMER = 1000 * 5;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(DiscoverNodesTimerTask.class);

  private List<String> localIps;
  private Map<String, ConnectionManager> ipsToConfirm;
  private Map<String, ConnectionManager> ipsConnected;

  private Timer timerDiscoverNodes;

  /**
   * Private class constructor
   */
  private CommunicationManager() {
    super();
    nodeExplorerServer = new NodeExplorerServer();
    dataRetrieverServer = new DataRetrieverServer();
  }

  /**
   * Singleton static method
   * @return the instance of CommunicationManager
   */
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

  /**
   * Mandatory initialization method
   * @param uuid uuid of the current user
   * @param login login of the current user
   * @param localIps deserialized list of ips
   * @throws IOException
   */
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

  /**
   * Method starting the server
   */
  private void startServer() {
    nodeExplorerServer.startServer();
    dataRetrieverServer.startServer();
  }

  /**
   * Node discovery method
   * @throws IOException
   */
  private void discoverNodes() throws IOException {
    addIpToConfirm(localIps);
    if(timerDiscoverNodes == null) {
      timerDiscoverNodes = new Timer();
      timerDiscoverNodes
          .schedule(new DiscoverNodesTimerTask(), new Date(), MS_TIMER);
    }
  }

  /**
   * Disconnects the current user
   * @throws IOException
   */
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

  /**
   * Method to retrieve all distant grids
   * @return a list of grids
   * @throws IOException
   */
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

  /**
   * Retrieves all distant grids of a specific user
   * @param user a user whose grids have to be retrieved
   * @return a list of grids
   * @throws IOException
   */
  public List<com.sudoku.data.model.Grid> getAllGrids(
      com.sudoku.data.model.User user) throws IOException {
    return getAllGrids(user.getIpAddress());
  }

  /**
   * Retrieves all distant grids of a specific ip
   * @param ip ip of the user whose grids have to be retrieved
   * @return a list of grids
   * @throws IOException
   */
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

  /**
   * Retrieves the profiles of the connected users
   * @return a list of users
   * @throws IOException
   */
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

  /**
   * Retrieves the profile of a specific user
   * @param user user whose profile has to be retrieved
   * @return an updated user
   * @throws IOException
   */
  public com.sudoku.data.model.User getProfile(User user) throws IOException {
    return getProfile(user.getIpAddress());
  }

  /**
   * Retrieves the profile of a specific ip
   * @param ip ip of the user whose profile has to be retrieved
   * @return an updated user
   * @throws IOException
   */
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

  /**
   * Method to push a comment to the distant connected users
   * @param comment comment to be pushed
   * @param gridUuid uuid of the grid owning the comment
   * @throws IOException
   */
  public void pushComment(Comment comment, UUID gridUuid) throws IOException {
    for (String ip : ipsConnected.keySet()) {
      NettyTransceiver client = new NettyTransceiver(
          new InetSocketAddress(ip, dataRetrieverServer.getPort()));
      DataRetriever retriever = (DataRetriever)
          SpecificRequestor.getClient(DataRetriever.class, client);
      retriever.commentGrid(comment, gridUuid.toString());
    }
  }

  /**
   * Retrieves the connected ips
   * @return a map of ips associated to a connection manager
   */
  public Map<String, ConnectionManager> getIpsConnected() {
    return ipsConnected;
  }

  /**
   * Retrieves the ips to be confirmed
   * @return a map of ips associated to a connection manager
   */
  public Map<String, ConnectionManager> getIpsToConfirm() {
    return ipsToConfirm;
  }

  /**
   * Adds an ip to the map of connected ips
   * @param ip ip to be added
   * @param cm connection manager to be added
   */
  public void addIpConnected(String ip, ConnectionManager cm) {
    ipsConnected.put(ip, cm);
  }

  /**
   * Removes an ip of the map of connected ips
   * @param ip to be removed
   */
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

  /**
   * Method to sync ips between the connected and to be confirmed ips
   * @param ipToUpdate ip to be updated
   * @param iterator iterator
   */
  public void syncIps(String ipToUpdate, Iterator<String> iterator) {
    ConnectionManager tmpCM = ipsToConfirm.get(ipToUpdate);
    addIpConnected(ipToUpdate, tmpCM);
    iterator.remove();
  }

  /**
   * Adds a list of ips to the map of ips to be confirmed
   * @param listIp a list of ips to be added
   */
  public void addIpToConfirm(List<String> listIp) {
    if(ipsToConfirm == null){
      ipsToConfirm = new ConcurrentHashMap<>();
    }

    Iterator<String> itr = listIp.iterator();
    while (itr.hasNext()) {
      addIpToConfirm(itr.next());
    }
  }

  /**
   * Adds an ip to the map of ips to be confirmed
   * @param ip a list of ips to be added
   */
  public void addIpToConfirm(String ip) {
    if(ipsToConfirm == null){
      ipsToConfirm = new ConcurrentHashMap<>();
    }
    if(!ipsConnected.containsKey(ip) && !ip.equals(localIp)) {
      ipsToConfirm.put(ip, new AvroConnectionManager(localIp));
    }
  }

  /**
   * Retrieves a set of connected ips
   * @return a set of ips
   */
  public Set<String> getConnectedIps() {
    return ipsConnected.keySet();
  }

  /**
   * Retrieves the ip of the pc currently running the code
   * @return an ip
   */
  public String getLocalIp() {
    return localIp;
  }

  /**
   * Retrieves the uuid
   * @return a string
   */
  public String getUuid() {
    return uuid;
  }

  /**
   * Retrieves the login
   * @return a string
   */
  public String getLogin() {
    return login;
  }
}