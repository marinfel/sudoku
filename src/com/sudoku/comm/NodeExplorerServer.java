package com.sudoku.comm;

import com.sudoku.comm.generated.NodeExplorer;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.specific.SpecificResponder;

import java.net.InetSocketAddress;

/**
 * Class implementing the server in charge of node exploring
 * @author Murat Cansiz
 * @see Server
 */
public class NodeExplorerServer extends Server {
  public static final int PORT = 11023;

  /**
   * Class constructor
   */
  public NodeExplorerServer() {
    super();
  }

  /**
   * Starts the server
   */
  @Override
  public void startServer() {
    avroServer = new NettyServer(
        new SpecificResponder(NodeExplorer.class, new NodeExplorerImpl()),
        new InetSocketAddress(localIp, PORT)
    );
  }

  /**
   * Stops the server
   */
  @Override
  public void stopServer() {
    if (avroServer != null) {
      avroServer.close();
    }
  }

  /**
   * Retrieves the port used by this server
   * @return the port used
   */
  @Override
  public int getPort() {
    return PORT;
  }
}
