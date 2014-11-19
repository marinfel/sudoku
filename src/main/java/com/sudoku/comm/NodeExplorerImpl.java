package com.sudoku.comm;

import com.sudoku.comm.generated.Message;
import com.sudoku.comm.generated.NodeExplorer;

import org.apache.avro.AvroRemoteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by ben on 19/11/14.
 */
public class NodeExplorerImpl implements NodeExplorer {
  private Logger logger = LoggerFactory.getLogger(NodeExplorerImpl.class);

  @Override
  public Message discoverNode(Message sentMessage) throws AvroRemoteException {
    return null;
  }

  @Override
  public Void disconnect(CharSequence ip) throws AvroRemoteException {
    return null;
  }

  public void getInetAddressOfLocalhost() {
    try {
      Enumeration<NetworkInterface> networkInterfaces =
          NetworkInterface.getNetworkInterfaces();
      while(networkInterfaces.hasMoreElements()) {
        NetworkInterface networkInterface =
            (NetworkInterface) networkInterfaces.nextElement();
        Enumeration<InetAddress> inetAddresses =
            networkInterface.getInetAddresses();
        while (inetAddresses.hasMoreElements()) {
          InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
          System.out.println(inetAddress.getHostAddress());
        }
      }
    } catch (SocketException ex) {
      logger.error(ex.toString());
    }
  }
}
