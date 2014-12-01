package com.sudoku.comm;

import com.sudoku.comm.generated.NodeExplorer;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.specific.SpecificResponder;

import java.net.InetSocketAddress;

public class NodeExplorerServer extends Server {
  private final int PORT = 11023;

  public NodeExplorerServer() {
    super();
  }

  public void startServer() {
    server = new NettyServer(
        new SpecificResponder(NodeExplorer.class, new NodeExplorerImpl()),
        new InetSocketAddress(localIp, PORT)
    );
  }

  public void stopServer() {
    if (server != null) {
      server.close();
    }
  }
}
