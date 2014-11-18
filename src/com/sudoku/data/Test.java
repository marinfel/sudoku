/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data;

import com.sudoku.data.factory.GridFactory;
import com.sudoku.data.model.FixedCell;
import com.sudoku.data.model.Grid;

/**
 *
 * @author JE
 */
public class Test {
    public static void main(String[] args){
        for(int i = 0; i < 500; i++){
            Grid c = GridFactory.generateRandomGrid(30);
            System.out.println(c);
        }
    }
}
