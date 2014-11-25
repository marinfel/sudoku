/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.factory;

import com.sudoku.data.model.Grid;
import com.sudoku.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author JE
 */
public class GridFactory {
    
    public static Grid generateRandomGrid(){
        return generateRandomGrid(0);
    }
    
    public static Grid generateRandomGrid(int nbEmptyCells){        
        ArrayList<Integer>[] avGridsNum = new ArrayList[9]; 
        ArrayList<Integer>[] avLinesNum = new ArrayList[9]; 
        ArrayList<Integer>[] avColsNum = new ArrayList[9]; 
        
        boolean done = false;
        Grid g = null;
        Random r = new Random(System.currentTimeMillis());
        
        while(!done){
            g = new Grid();
            
            for(int i = 0; i < 9; i++){
                    avGridsNum[i] = new ArrayList<>();
                    avLinesNum[i] = new ArrayList<>();
                    avColsNum[i] = new ArrayList<>();

                for(int j = 1; j <= 9; j++){
                    avGridsNum[i].add(j);
                    avLinesNum[i].add(j);
                    avColsNum[i].add(j);
                }
            }

            boolean valid = true;
            for(byte i = 0; i < 9 && valid; i ++){
                for(byte j = 0; j < 9 && valid; j++){

                    int k = getSubGridNumber(i,j);

                    ArrayList<Integer> avNumbers = CollectionUtil.inter(avLinesNum[i], avColsNum[j]);
                    avNumbers = CollectionUtil.inter(avNumbers, avGridsNum[k]);

                    valid = avNumbers.size() > 0;

                    if(valid){
                        int idx = r.nextInt(avNumbers.size());                        
                        Integer pickedNumber = avNumbers.get(idx);

                        g.setFixedCell(i, j, pickedNumber.byteValue());

                        avLinesNum[i].remove(pickedNumber);
                        avGridsNum[k].remove(pickedNumber);
                        avColsNum[j].remove(pickedNumber);
                    }
                }
            }
            done = valid;
        }
        
        for(int i = 0; i < Math.min(nbEmptyCells, 81); i++){
            g.setEmptyCell((byte)r.nextInt(9), (byte)r.nextInt(9));
        }
        
        return g;
    }
    
    public static int getSubGridNumber(int lineNumber, int colNumber){
        int subLineNumber = (int)Math.floor(lineNumber / 3);
        int subColNumber = (int)Math.floor(colNumber / 3);
        
        return subLineNumber * 3 + subColNumber;
    }
}
