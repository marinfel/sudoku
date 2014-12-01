package com.sudoku.comm;

import com.sudoku.data.model.Comment;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.User;

import java.io.IOException;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;

// Class representing a connection to a remote user
public abstract class ConnectionManager {
  protected String ipAddress;
  // should retrieve it from the communication manager or another conf singleton
  protected final int NODE_PORT = 11023;
  protected String uuid;
  protected String  login;
  public ConnectionManager(String addr, String id, String  l)
     throws IOException {
    this.ipAddress = addr;
    this.uuid = id;
    this.login = l;
  }

  /** Default, returns the connected ips of the remote user
   * param newConnectedIps: the connected ips of the local user
   */
  public List<String> openConnection(ArrayList<String> newConnectedIps)
     throws OfflineUserException {
    return getConnectedIps(newConnectedIps);
  }
  public abstract List<String> getConnectedIps(ArrayList<String> newConnectedIps)
    throws OfflineUserException;
  public abstract void closeConnection() throws OfflineUserException;
  public abstract ArrayList<Grid> getGrids() throws ConnectionClosedException;
  public abstract User getProfile() throws ConnectionClosedException;
  public abstract void pushComment(Comment c, Grid g) throws ConnectionClosedException;
  public abstract void publishComment(Comment c, Grid g) throws ConnectionClosedException;

  //Exceptions
  public class ConnectionClosedException extends Exception {
    public ConnectionClosedException() { super(); }
    public ConnectionClosedException(String message) { super(message); }
    public ConnectionClosedException(String message, Throwable cause) { super(message, cause); }
    public ConnectionClosedException(Throwable cause) { super(cause); }
  }

  public class OfflineUserException extends Exception {
    public OfflineUserException() { super(); }
    public OfflineUserException(String message) { super(message); }
    public OfflineUserException(String message, Throwable cause) { super(message, cause); }
    public OfflineUserException(Throwable cause) { super(cause); }
  }
}
