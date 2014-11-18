/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JE
 */
public class ListUtils {
    public static <T> ArrayList<T> inter(ArrayList<T> l1, ArrayList<T> l2){
        ArrayList<T> l = new ArrayList<>();
        for(T e : l1){
           if(l2.contains(e)){
               l.add(e);
           }
        }
        return l;
    } 
}
