/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data;

import com.sudoku.data.factory.GridFactory;
import com.sudoku.data.manager.AccessManager;
import com.sudoku.data.manager.DataManager;
import com.sudoku.data.manager.GridManager;
import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.AccessRule;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.PlayedGrid;
import com.sudoku.data.model.User;
import com.sudoku.data.sample.DataSample;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author JE
 */
public class TestRestore {
  public static void main(String[] args) {
    //for (int i = 0; i < 500; i++) {
    //  Grid c = GridFactory.generateRandomGrid();
    //  System.out.println(c);
    //}
    /* DataSample ds;
    System.out.println(" création data sample");
    ds = new DataSample();
    System.out.println(" création data sample terminated");
    ds.exec();
    //ds.getUserList();
    */
    //UserManager.BuildFromJson();
    //GridManager.BuildFromJson();
      DataManager.buildFromJson();
    List<User> ul1= UserManager.getInstance().getLocalUsers();
    List<Grid> gl1= GridManager.getInstance().getAvailableGrids();
  
    HashMap<Grid, ArrayList<AccessRule>> al1 = AccessManager.getInstance().getRules();
    System.out.println(ul1.get(0).getIpAddress());
    System.out.println(gl1.get(0).getCreateUser().getPseudo());
    //System.out.println(al1.toString());
    System.out.println("Au fait, je m'éxécute");
    //System.out.println(al1.get(GridManager.getInstance().getUserGrids(ul1.get(0))));
  }
}
