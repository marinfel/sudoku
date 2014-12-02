package com.sudoku.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by ben on 19/11/14.
 */
public class CommMain {
  private static Logger logger =
      LoggerFactory.getLogger(CommMain.class);

  public static void main(String[] args) {
    String uuid = "uuid";
    String login = "login";
    ArrayList<String> connectedIps = new ArrayList<>();
    connectedIps.add(args[0]);
    connectedIps.add("172.26.25.20");
    connectedIps.add("172.26.25.21");
    connectedIps.add("172.26.25.22");
    connectedIps.add("172.26.25.23");
    connectedIps.add("172.26.25.24");
    connectedIps.add("172.26.25.25");
    connectedIps.add("172.26.25.26");

    CommunicationManager commManager = CommunicationManager.getInstance();
    commManager.init(uuid, login, connectedIps);

    System.out.println("ip: " + commManager.getLocalIp());
    try {
      commManager.discoverNodes();
      //Thread.sleep(5000);
      /*for (String ip : commManager.getConnectedIps()) {
        System.out.println(ip);
      }*/
      commManager.disconnect();
    } catch (Exception ex) {
      logger.error(ex.toString());
    }
  }
}
