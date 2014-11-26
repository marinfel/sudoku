package com.sudoku.comm;

import java.net.InetSocketAddress;
import java.io.IOException;

import org.apache.avro.ipc.specific.SpecificResponder;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.Server;

import com.sudoku.comm.SudokuServer;
import com.sudoku.comm.generated.NodeExplorer;

public class AvroServer extends SudokuServer {
  private static Server server;
  public AvroServer() {
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
