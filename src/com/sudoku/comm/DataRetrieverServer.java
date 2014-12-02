package com.sudoku.comm;

import com.sudoku.comm.generated.DataRetriever;
import com.sudoku.comm.generated.NodeExplorer;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.specific.SpecificResponder;

import java.net.InetSocketAddress;

/**
 * Created by ben on 01/12/14.
 */
public class DataRetrieverServer extends Server {
  public final int PORT = 11024;

  @Override
  public void startServer() {
    server = new NettyServer(
        new SpecificResponder(DataRetriever.class, new DataRetrieverImpl()),
        new InetSocketAddress(localIp, PORT)
    );
  }

  @Override
  public void stopServer() {
    if (server != null) {
      server.close();
    }
  }

  @Override
  public int getPort() {
    return PORT;
  }
}
