/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.main.manager;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;

/**
 *
 * @author MOURAD
 */
public class GridThumbnailContainer extends GridPane {
    public List<GridThumbnail> gridThumbnailList;
    
    public GridThumbnailContainer()
    {
        super();
        gridThumbnailList = new ArrayList<GridThumbnail>();
    }
    
    public void addGridThumbnail(GridThumbnail instance)
    {
        gridThumbnailList.add(instance);
    }
    
    public GridPane getInstance()
    {
        loadComponent();
        return this;
    }
    
    public void loadComponent()
    {
        for(int i=0;i<gridThumbnailList.size();i++)
        {
            this.setRowIndex(gridThumbnailList.get(i), i+1);
            this.getChildren().add(gridThumbnailList.get(i));
        }
    }
}
