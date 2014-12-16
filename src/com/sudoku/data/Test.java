/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data;

import com.sudoku.data.factory.GridFactory;
import com.sudoku.data.model.Grid;
import com.sudoku.data.sample.DataSample;
import java.io.IOException;

/**
 * @author JE
 */
public class Test {
  public static void main(String[] args) throws IOException {
   // for (int i = 0; i < 500; i++) {
    //  Grid c = GridFactory.generateRandomGrid();
     // System.out.println(c);
   //}
    DataSample ds;
    System.out.println(" création data sample");
    ds = new DataSample();
    System.out.println(" création data sample terminated");
    ds.exec();
    //ds.getUserList();
  }
}
