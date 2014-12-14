/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.main.manager;

import com.sudoku.data.manager.GridManager;
import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.User;
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
    private UserManager usrManager;
    private GridManager gridManager;
    
    public ListGridManager(DataSample i)
    {
        instance=i;
        usrManager = UserManager.getInstance();
        gridManager= GridManager.getInstance();
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
        for(int i = 0; i<gridManager.getUserGrids(usrManager.getLoggedUser()).size();i++)
        {
            container.addGridThumbnail(new GridThumbnail(gridManager.getUserGrids(usrManager.getLoggedUser()).get(i)));
        }
        return container.getInstance();
    }
    
   public GridPane getFinishedGridThumbnailContainer()
    {
        GridThumbnailContainer container = new GridThumbnailContainer();
        for(int i = 0; i<gridManager.getFinishedGrid().size();i++)
        {
            container.addGridThumbnail(new GridThumbnail(gridManager.getFinishedGrid().get(i).getGrid()));
        }
        return container.getInstance();
    }
   
    public GridPane getCurrentGridThumbnailContainer()
    {
        GridThumbnailContainer container = new GridThumbnailContainer();
        for(int i = 0; i<gridManager.getIncompleteGrid().size();i++)
        {
            container.addGridThumbnail(new GridThumbnail(gridManager.getIncompleteGrid().get(i).getGrid()));
        }
        return container.getInstance();
    }
    
    public GridPane getDistantGridThumbnailContainer()
    {
        GridThumbnailContainer container = new GridThumbnailContainer();
        List<User> DistantUser = usrManager.getDistantUsers();
        List<Grid> grids;
        for(int i = 0; i<DistantUser.size();i++)
        {
            grids = gridManager.getUserGrids(DistantUser.get(i));
            for(int j=0; i<grids.size();j++)
            {
                container.addGridThumbnail(new GridThumbnail(grids.get(j)));
            }
        }
        return container.getInstance();
    }
}
