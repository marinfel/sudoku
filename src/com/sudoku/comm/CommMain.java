package com.sudoku.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 19/11/14.
 */
public class CommMain {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(CommMain.class);
  public static final int SLEEP_TIMER = 10000;
  
  private CommMain() {
  }

  public static void main(String[] args) {
    String uuid = "uuid";
    String login = "login";
    List<String> connectedIps = new ArrayList<>();
    connectedIps.add("172.26.25.19");
    connectedIps.add("172.26.25.20");
    connectedIps.add("172.26.25.21");
    connectedIps.add("172.26.25.22");
    connectedIps.add("172.26.25.23");
    connectedIps.add("172.26.25.24");
    connectedIps.add("172.26.25.25");
    connectedIps.add("172.26.25.26");

    CommunicationManager commManager = CommunicationManager.getInstance();
    commManager.init(uuid, login, connectedIps);

    LOGGER.info("ip: " + commManager.getLocalIp());
    try {
      commManager.discoverNodes();
      Thread.sleep(SLEEP_TIMER);
      commManager.disconnect();
    } catch (Exception ex) {
      LOGGER.error("error :", ex);
    }
  }
}
