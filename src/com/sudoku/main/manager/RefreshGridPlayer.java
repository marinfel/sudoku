/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.main.manager;

import com.sudoku.data.model.Grid;

/**
 *
 * @author MOURAD
 */
public class RefreshGridPlayer {
    
    
    private static volatile RefreshGridPlayer instance = null;
    private Grid currentGrid;
    
    public RefreshGridPlayer()
    {
        this.currentGrid=null;
    }
    
    public final static RefreshGridPlayer getInstance()
    {
        if(instance == null)
        {
            synchronized (RefreshGridPlayer.class)
            {
                if(instance == null)
                {
                    instance = new RefreshGridPlayer();
                }
            }
        }
        return instance;
    }
    
    public Grid getCurrentGrid()
    {
        return currentGrid;
    }
    
    public void setCurrentGrid(Grid g)
    {
        currentGrid=g;
    }
}
