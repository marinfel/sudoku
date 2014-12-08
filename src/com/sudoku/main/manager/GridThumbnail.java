/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.main.manager;
import com.sudoku.data.model.Grid;
import  javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author MOURAD
 */
public class GridThumbnail extends AnchorPane {
    
    Label gridTitle;
    Label gridUserCreator;
    Label gridDescription;
    Button SeeMoreButton;
    Grid instance;
    BorderPane border;
    
    
    GridThumbnail(Grid g)
    {
        super();
        instance = g;        
        border = new BorderPane();
        SeeMoreButton = new Button("See More");
        initThumbnail();
        setPositions();
        setThisAnchorElements();
    }
    
    public void initThumbnail()
    {
        gridTitle = new Label(instance.getTitle());
        gridUserCreator = new Label(instance.getCreateUser().getPseudo());
        gridDescription = new Label(instance.getDescription());
    }
    
    public void setPositions()
    {
        border.setLeft(gridTitle);
        border.setCenter(gridUserCreator);
        border.setRight(gridDescription);
        border.setBottom(SeeMoreButton);
    }
    
    public void setThisAnchorElements()
    {
        this.getChildren().add(border);
    }
}
