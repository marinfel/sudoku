package com.sudoku.comm;

/**
 * Created by ben on 19/11/14.
 */
public class CommMain {
  public static void main(String[] args) {
    NodeExplorerImpl nodeExplorer = new NodeExplorerImpl();
    try {
      System.out.println(
          nodeExplorer.getInetAddressOfLocalhost().getHostAddress());
    } catch(Exception ex) {

    }
  }
}
