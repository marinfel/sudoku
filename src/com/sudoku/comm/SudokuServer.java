package com.sudoku.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public abstract class SudokuServer {
  protected final int PORT = 11023;
  protected String localIp;
  private Logger logger = LoggerFactory.getLogger(SudokuServer.class);

  public SudokuServer() {
    try {
      this.localIp = getLocalInetAddress().getHostAddress();
    } catch (UnknownHostException ex) {
      logger.error(ex.toString());
    }
  }

  public abstract void startServer();

  public abstract void stopServer();

  public String getServerInetAddresses() {
    return this.localIp;
  }

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
      logger.error(ex.toString());
      UnknownHostException unknownHostException =
          new UnknownHostException("Failed to determine IP: " + ex);
      unknownHostException.initCause(ex);
      throw unknownHostException;
    }
  }
}
