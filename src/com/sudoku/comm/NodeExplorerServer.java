package com.sudoku.comm;

import com.sudoku.comm.generated.NodeExplorer;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.specific.SpecificResponder;

import java.net.InetSocketAddress;

public class NodeExplorerServer extends Server {

  public NodeExplorerServer() {
    super();
  }

  public void startServer() {
    server = new NettyServer(
        new SpecificResponder(NodeExplorer.class, new NodeExplorerImpl()),
        new InetSocketAddress(localIp, EXPLORER_PORT)
    );
  }

  public void stopServer() {
    if (server != null) {
      server.close();
    }
  }
}
