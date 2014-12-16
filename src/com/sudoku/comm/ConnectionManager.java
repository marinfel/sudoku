package com.sudoku.comm;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a connection to a remote user
 * @author Murat Cansiz
 * @see com.sudoku.comm.AvroConnectionManager
 */
public abstract class ConnectionManager {
  private static final String ERROR_MSG = "Must open connection first.";
  
  /* Remote user parameters */
  protected String ipAddress;
  protected boolean isConnected;
  
  /* those parameters should be retrieved from the communication manager or
   * another conf singleton
   */
  protected static final int NODEPORT = 11023;

  /**
   * Class constructor
   * @param ip ip of the connection
   */
  public ConnectionManager(String ip) {
    this.ipAddress = ip;
    this.isConnected = false;
  }

  /** 
   * Tries to open a connection.
   * @throws com.sudoku.comm.ConnectionManager.OfflineUserException
   */
  public abstract void openConnection() throws OfflineUserException;

  /**
   * Gets the list of distant connected ips
   * @param newConnectedIps connected ips to send to the distant connection
   * @return a list of connected ips
   * @throws ConnectionClosedException
   * @throws OfflineUserException
   */
  public List<String> getConnectedIps(List<String> newConnectedIps)
     throws OfflineUserException, ConnectionClosedException {
    if (!isConnected) {
      throw new ConnectionClosedException(ERROR_MSG);
    }
    return new ArrayList<>();
  }

  /**
   * Publishes the current list of connected ips to the distant connection
   * @param ips list of ips to be published
   * @throws OfflineUserException
   * @throws ConnectionClosedException
   */
  public void publishIps(List<String> ips) throws OfflineUserException,
     ConnectionClosedException {
    if (!isConnected) {
      throw new ConnectionClosedException(ERROR_MSG);
    }
  }

  /**
   * Disconnects this peer to the distant peer
   * @throws OfflineUserException
   * @throws ConnectionClosedException
   */
  public void disconnect() throws OfflineUserException,
     ConnectionClosedException {
    if (!isConnected) {
      throw new ConnectionClosedException(ERROR_MSG);
    }
  }

  /**
   * Closes connection with the distant peer
   * @throws OfflineUserException
   */
  public abstract void closeConnection() throws OfflineUserException;

  /**
   * Exception class occurring when a connection closed unexpectedly
   */
  public class ConnectionClosedException extends Exception {
    public ConnectionClosedException() {
      super();
    }
    public ConnectionClosedException(String message) {
      super(message);
    }
    public ConnectionClosedException(String message, Throwable cause) {
      super(message, cause);
    }
    public ConnectionClosedException(Throwable cause) {
      super(cause);
    }
  }

  /**
   * Exception class occurring when a user is unexpectedly offline
   */
  public class OfflineUserException extends Exception {
    public OfflineUserException() {
      super();
    }
    public OfflineUserException(String message) {
      super(message);
    }
    public OfflineUserException(String message, Throwable cause) {
      super(message, cause);
    }
    public OfflineUserException(Throwable cause) {
      super(cause);
    }
  }
}
