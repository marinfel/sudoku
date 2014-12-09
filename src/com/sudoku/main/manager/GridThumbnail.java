/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.main.manager;
import com.sudoku.data.model.Grid;
import com.sudoku.grid.ihm_grid_preview.IhmGridPreview;
import javafx.geometry.Insets;
import  javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

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
    IhmGridPreview gridInstance;
    GridPane GridInfo;
    
    
    GridThumbnail(Grid g)
    {
        super();
        instance = g;
        border = new BorderPane();
        GridInfo = new GridPane();
        SeeMoreButton = new Button("See More");
        gridInstance = new IhmGridPreview(3,instance,100);
        initThumbnail();
        setPositions();
        setThisAnchorElements();
        this.setStyle("-fx-background-color: #336699;");
        this.setPrefSize(800, 200);
        this.setPadding(new Insets(0, 10, 0, 10));
    }
    
    public void initThumbnail()
    {
        gridTitle = new Label("Titre : "+instance.getTitle());
        gridUserCreator = new Label("Créateur : "+instance.getCreateUser().getPseudo());
        gridDescription = new Label("Description : "+instance.getDescription());
    }
    
    public void setPositions()
    {
        border.setCenter(gridInstance);
        GridInfo.setRowIndex(gridTitle,1);
        GridInfo.setRowIndex(gridUserCreator,2);
        GridInfo.setRowIndex(gridDescription,3);
        GridInfo.setRowIndex(SeeMoreButton,5);
        GridInfo.getChildren().add(gridTitle);
        GridInfo.getChildren().add(gridUserCreator);
        GridInfo.getChildren().add(gridDescription);
        border.setRight(GridInfo);
    }
    
    public void setThisAnchorElements()
    {
        this.getChildren().add(border);
    }
}
