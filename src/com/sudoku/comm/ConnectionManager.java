package com.sudoku.comm;

import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;

// Class representing a connection to a remote user
public abstract class ConnectionManager {
  // Remote user.
  protected String ipAddress;
  protected boolean isConnected;
  // should retrieve it from the communication manager or another conf singleton
  protected static final int NODEPORT = 11023;

  public ConnectionManager(String ip) {
    this.ipAddress = ip;
    this.isConnected = false;
  }

  /** 
   * Tries to open a connection.
   */
  public abstract void openConnection() throws OfflineUserException;
  
  public List<String> getConnectedIps(List<String> newConnectedIps)
     throws OfflineUserException, ConnectionClosedException {
    if (!isConnected) {
      throw new ConnectionClosedException("Must open connection first.");
    }
    return new ArrayList<String>();
  }
  
  public void publishIps(List<String> ips) throws OfflineUserException,
     ConnectionClosedException {
    if (!isConnected) {
      throw new ConnectionClosedException("Must open connection first.");
    }
  }

  public void disconnect() throws OfflineUserException,
     ConnectionClosedException {
    if (!isConnected) {
      throw new ConnectionClosedException("Must open connection first.");
    }
  }

  public abstract void closeConnection() throws OfflineUserException;

  //Exceptions
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
    public ConnectionClosedException(Throwable cause) { super(cause); }
  }

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
