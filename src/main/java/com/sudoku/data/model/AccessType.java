/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.model;

/**
 *
 * @author JE
 */
public enum AccessType {
    granted(0),
    revoked(1);
    
    private int accessNum;
    
    AccessType(int accessNum){
        this.accessNum = accessNum;
    }
}
