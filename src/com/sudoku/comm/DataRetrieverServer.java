package com.sudoku.comm;

import com.sudoku.comm.generated.DataRetriever;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.specific.SpecificResponder;

import java.net.InetSocketAddress;

/**
 * Created by ben on 01/12/14.
 */
public class DataRetrieverServer extends Server {
  public static final int PORT = 11024;

  public DataRetrieverServer() {
    super();
  }

  @Override
  public void startServer() {
    avroServer = new NettyServer(
        new SpecificResponder(DataRetriever.class, new DataRetrieverImpl()),
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
