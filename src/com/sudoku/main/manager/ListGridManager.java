/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.main.manager;

import com.sudoku.data.model.Grid;
import com.sudoku.data.sample.DataSample;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.AnchorPane;


/**
 *
 * @author MOURAD
 */
public class ListGridManager {
    private DataSample instance;
    
    public ListGridManager()
    {
        instance = new DataSample();
        instance.exec();
    }
    
    public List<AnchorPane> AllGrid()
    {
        List GridList = new ArrayList<AnchorPane>();
        GridList.add(new GridThumbnail(instance.g1));
        GridList.add(new GridThumbnail(instance.g2));
        GridList.add(new GridThumbnail(instance.g3));
        return GridList;
    }
}
