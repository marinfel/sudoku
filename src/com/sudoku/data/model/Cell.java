/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author xuncao
 */

package com.sudoku.data.model;

public class Cell {
    
    public static class Errors{
        public static final String Cell_illegal_position = "La position de la cellule devrait etre comprise entre 0 et 8";
        public static final String Cell_illegal_value = "Le contenu de la cellule devrait etre compris entre 1 et 9";
    }
    
    protected byte x;
    protected byte y;
    
    protected Cell(byte x, byte y) throws IllegalArgumentException{
        
        if(x < 0 || x >= 9 || y < 0 || y >= 9){
            throw new IllegalArgumentException(Cell.Errors.Cell_illegal_position);
        }
        
        this.x = x;
        this.y = y;
    }
}
