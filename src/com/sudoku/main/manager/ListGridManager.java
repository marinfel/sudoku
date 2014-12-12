/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.main.manager;

import com.sudoku.data.manager.GridManager;
import com.sudoku.data.model.Grid;
import com.sudoku.data.sample.DataSample;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


/**
 *
 * @author MOURAD
 */
public class ListGridManager {
    private DataSample instance;
    
    public ListGridManager(DataSample i)
    {
        instance=i;
    }
    
    public List<AnchorPane> AllGrid()
    {
        List GridList = new ArrayList<AnchorPane>();
        GridList.add(new GridThumbnail(instance.g1));
        /*GridList.add(new GridThumbnail(instance.g2));
        GridList.add(new GridThumbnail(instance.g3));*/
        return GridList;
    }
    
    public GridPane getGridThumbnailContainer()
    {
        GridThumbnailContainer container = new GridThumbnailContainer();
        /*container.addGridThumbnail(new GridThumbnail(instance.g1));
        container.addGridThumbnail(new GridThumbnail(instance.g2));
        container.addGridThumbnail(new GridThumbnail(instance.g3));
        container.addGridThumbnail(new GridThumbnail(instance.g1));
        container.addGridThumbnail(new GridThumbnail(instance.g2));*/
        for(int i = 0; i<GridManager.getInstance().getUserGrids(instance.a).size();i++)
        {
            container.addGridThumbnail(new GridThumbnail(GridManager.getInstance().getUserGrids(instance.a).get(i)));
        }
        return container.getInstance();
    }
}
