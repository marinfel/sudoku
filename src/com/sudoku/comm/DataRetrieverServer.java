package com.sudoku.comm;

import com.sudoku.comm.generated.DataRetriever;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.specific.SpecificResponder;

import java.net.InetSocketAddress;

/**
 * Class implementing the server in charge of data retrieving
 * @author Benjamin Frat
 * @see Server
 */
public class DataRetrieverServer extends Server {
  public static final int PORT = 11024;

  /**
   * Class constructor
   */
  public DataRetrieverServer() {
    super();
  }

  /**
   * Starts the server
   */
  @Override
  public void startServer() {
    server = new NettyServer(
        new SpecificResponder(DataRetriever.class, new DataRetrieverImpl()),
        new InetSocketAddress(localIp, PORT)
    );
  }

  /**
   * Stops the server
   */
  @Override
  public void stopServer() {
    if (server != null) {
      server.close();
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
