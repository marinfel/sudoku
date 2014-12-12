package com.sudoku.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Abstract class encapsulating an Avro server
 * @author Murat Cansiz
 * @see NodeExplorerServer and DataRetrieverServer
 */
public abstract class Server {
  protected static org.apache.avro.ipc.Server avroServer;
  protected String localIp;
  private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

  /**
   * Class constructor
   */
  public Server() {
    try {
      this.localIp = getLocalInetAddress().getHostAddress();
    } catch (UnknownHostException ex) {
      LOGGER.error(ex.toString(), ex);
    }
  }

  /**
   * Abstract method to start the server
   */
  public abstract void startServer();

  /**
   * Abstract method to stop the server
   */
  public abstract void stopServer();

  /**
   * Abstract method to retrieve the port used by this specific server
   * @return the port used by the server
   */
  public abstract int getPort();

  /**
   * Retrieves the ip address of the server this code is running on
   * @return the ip address as a string
   */
  public String getInetAddress() {
    return this.localIp;
  }

  /**
   * Finds out the ip address of the server this code is running on
   * @return the ip address as an InetAddress object
   * @throws UnknownHostException
   */
  private InetAddress getLocalInetAddress() throws UnknownHostException {
    try {
      InetAddress candidateAddress = null;

      for (Enumeration<NetworkInterface> networkInterfaces =
               NetworkInterface.getNetworkInterfaces();
           networkInterfaces.hasMoreElements(); ) {

        NetworkInterface networkInterface =
            (NetworkInterface) networkInterfaces.nextElement();
        for (Enumeration<InetAddress> inetAddresses =
                 networkInterface.getInetAddresses();
             inetAddresses.hasMoreElements(); ) {

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
      if (jdkSuppliedAddress == null) {
        throw new UnknownHostException("InetAddress.getLocalHost() is null.");
      }
      return jdkSuppliedAddress;
    } catch (Exception ex) {
      LOGGER.error(ex.toString());
      UnknownHostException unknownHostException =
          new UnknownHostException("Failed to determine IP: " + ex);
      unknownHostException.initCause(ex);
      throw unknownHostException;
    }
  }
}
