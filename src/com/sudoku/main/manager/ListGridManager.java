/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.main.manager;

import com.sudoku.comm.CommunicationManager;
import com.sudoku.data.manager.GridManager;
import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.Tag;
import com.sudoku.data.model.User;
import com.sudoku.data.sample.DataSample;
import com.sudoku.main.view.ScreensController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


/**
 *
 * @author MOURAD
 */
public class ListGridManager {
    //private DataSample instance;
    private UserManager usrManager;
    private GridManager gridManager;
    public ScrollPane preview;
    public ScreensController myController;

    
    public ListGridManager(ScrollPane p,ScreensController sc)
    {
        //instance=i;
        preview = p;
        usrManager = UserManager.getInstance();
        gridManager= GridManager.getInstance();
        myController = sc;
    }
    
    public List<AnchorPane> AllGrid()
    {
        List GridList = new ArrayList<AnchorPane>();
        return GridList;
    }
    
    public GridPane getGridThumbnailContainer(boolean filter, int type, List<Tag> tags,int grads)
    {
        GridThumbnailContainer container = new GridThumbnailContainer();
        if(!filter)
        {
            for(int i = 0; i<gridManager.getUserGrids(usrManager.getLoggedUser()).size();i++)
            {
                container.addGridThumbnail(new GridThumbnail(gridManager.getUserGrids(usrManager.getLoggedUser()).get(i),preview,myController));
            }
        }
        else
        {
            if(type==0)
            {
                for(int i = 0; i<gridManager.filterGridsByTags(tags).size();i++)
                {
                    container.addGridThumbnail(new GridThumbnail(gridManager.filterGridsByTags(tags).get(i),preview,myController));
                }
            }else if(type == 1)
            {
                for(int i = 0; i<gridManager.filterGridsByGrade(grads).size();i++)
                {
                    container.addGridThumbnail(new GridThumbnail(gridManager.filterGridsByGrade(grads).get(i),preview,myController));
                }
            }
        }
        return container.getInstance();
    }
    
   public GridPane getFinishedGridThumbnailContainer()
    {
        GridThumbnailContainer container = new GridThumbnailContainer();
        for(int i = 0; i<gridManager.getFinishedGrid().size();i++)
        {
            container.addGridThumbnail(new GridThumbnail(gridManager.getFinishedGrid().get(i).getGrid(),preview,myController));
        }
        return container.getInstance();
    }
   
    public GridPane getCurrentGridThumbnailContainer()
    {
        GridThumbnailContainer container = new GridThumbnailContainer();
        for(int i = 0; i<gridManager.getIncompleteGrid().size();i++)
        {
            container.addGridThumbnail(new GridThumbnail(gridManager.getIncompleteGrid().get(i).getGrid(),preview,myController));
        }
        return container.getInstance();
    }
    
    public GridPane getDistantGridThumbnailContainer()
    {
        GridThumbnailContainer container = new GridThumbnailContainer();
        List<User> DistantUser = usrManager.getDistantUsers();
        List<Grid> grids;

       /* System.out.println("************************ DEBUG MOURAD *************************");
        System.out.println("Utilisateur connectée : "+DistantUser.size());
        
        
        CommunicationManager cm = CommunicationManager.getInstance();
        try {
            DistantUser = cm.getAllProfiles();
        } catch (IOException ex) {
            Logger.getLogger(ListGridManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Comm weeesh ma gueule : "+DistantUser.size());
        for(int i = 0; i<DistantUser.size();i++)
        {
            grids = gridManager.getUserGrids(DistantUser.get(i));
            for(int j=0; i<grids.size();j++)
            {
                container.addGridThumbnail(new GridThumbnail(grids.get(j),preview,myController));
            }
        }*/
        return container.getInstance();
    }
}
