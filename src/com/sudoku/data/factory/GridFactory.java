/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.factory;

import com.sudoku.data.model.Grid;
import com.sudoku.utils.ListUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author JE
 */
public class GridFactory {
    public static Grid generateRandomGrid(){
        Grid g = new Grid();
        
        ArrayList<Integer>[] avGridsNum = new ArrayList[9]; 
        ArrayList<Integer>[] avLinesNum = new ArrayList[9]; 
        ArrayList<Integer>[] avColsNum = new ArrayList[9]; 
        
        for(int i = 0; i < 9; i++){
            for(int j = 1; j <= 9; j++){
                avGridsNum[i].add(j);
                avLinesNum[i].add(j);
                avColsNum[i].add(j);
            }
        }
        
        for(byte i = 0; i < 9; i ++){
            for(byte j = 0; j < 9; j++){
                ArrayList<Integer> avNumbers = ListUtils.inter(avLinesNum[i], avColsNum[j]);
                avNumbers = ListUtils.inter(avNumbers, avGridsNum[getSubGridNumber(i,j)]);
                
                Collections.shuffle(avNumbers);
                g.setFixedCell(i, j, avNumbers.get(0).byteValue());
            }
        }
        
        return g;
    }
    
    private static int getSubGridNumber(int lineNumber, int colNumber){
        int subLineNumber = (int)Math.floor(lineNumber / 3);
        int subColNumber = (int)Math.floor(colNumber / 3);
        
        return subLineNumber * 3 + subColNumber;
    }
}
