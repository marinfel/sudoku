package com.sudoku.comm;

import com.sudoku.comm.generated.NodeExplorer;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.specific.SpecificResponder;

import java.net.InetSocketAddress;

public class NodeExplorerServer extends Server {
  public static final int PORT = 11023;

  public NodeExplorerServer() {
    super();
  }

  @Override
  public void startServer() {
    avroServer = new NettyServer(
        new SpecificResponder(NodeExplorer.class, new NodeExplorerImpl()),
        new InetSocketAddress(localIp, PORT)
    );
  }

  @Override
  public void stopServer() {
    if (avroServer != null) {
      avroServer.close();
    }
  }

  @Override
  public int getPort() {
    return PORT;
  }
}
