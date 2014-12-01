/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.main.view;

/**
 *
 * @author SAMSUNG
 */
public interface ControlledScreen {
    
    //This method will allow the injection of the Parent ScreenPane
    public void setScreenParents(ScreensController screenParent);
    
}
