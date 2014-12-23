/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.main.manager;
import com.sudoku.data.model.Grid;
import com.sudoku.grid.preview.IhmGridDetailledPreview;
import com.sudoku.grid.preview.IhmGridPreview;
import com.sudoku.main.view.ScreensController;
import com.sudoku.main.view.SudukoIHM;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import  javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 *
 * @author MOURAD
 */
public class GridThumbnail extends AnchorPane {
    
    Label gridTitle;
    Label gridUserCreator;
    Label gridDescription;
    Button SeeMoreButton;
    Button PlayButton;
    Grid instance;
    BorderPane border;
    IhmGridPreview gridInstance;
    GridPane GridInfo;
    ScrollPane preview;
    ScreensController myController;
    
    
    GridThumbnail(Grid g,ScrollPane p,ScreensController sc)
    {
        super();
        instance = g;
        preview = p;
        border = new BorderPane();
        GridInfo = new GridPane();
        SeeMoreButton = new Button("Afficher");
        PlayButton = new Button("Jouer");
        gridInstance = new IhmGridPreview(instance,100);
        initThumbnail();
        setPositions();
        setListener();
        setThisAnchorElements();
        //this.setStyle("-fx-background-color: #336699;");
        this.setPrefSize(800, 150);
        myController = sc;
    }
    
    public void initThumbnail()
    {
        gridTitle = new Label("Titre : "+instance.getTitle());
        gridUserCreator = new Label("Créateur : "+instance.getCreateUser().getPseudo());
        gridDescription = new Label("Description : "+instance.getDescription());
        gridTitle.setFont(Font.font("Verdana", 13));
        gridUserCreator.setFont(Font.font("Verdana", 13));
        gridDescription.setFont(Font.font("Verdana", 13));
    }
    
    public void setPositions()
    {
        border.setCenter(gridInstance);
        GridInfo.setRowIndex(gridTitle,1);
        GridInfo.setRowIndex(gridUserCreator,2);
        GridInfo.setRowIndex(gridDescription,3);
        GridInfo.setRowIndex(SeeMoreButton,4);
        GridInfo.setColumnIndex(SeeMoreButton,1);
        GridInfo.setRowIndex(PlayButton,4);
        GridInfo.setColumnIndex(PlayButton,2);
        GridInfo.getChildren().add(gridTitle);
        GridInfo.getChildren().add(gridUserCreator);
        GridInfo.getChildren().add(gridDescription);
        GridInfo.getChildren().add(SeeMoreButton);
        GridInfo.getChildren().add(PlayButton);
        border.setRight(GridInfo);
    }
    
    public void resizeComponent()
    {
        PlayButton.setPrefSize(100, 10);
        SeeMoreButton.setPrefSize(100, 10);
    }
    
    public void setListener()
    {
        PlayButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            RefreshGridPlayer.getInstance().setCurrentGrid(instance);
            myController.setScreen(SudukoIHM.gridPlayerGameID);
        }
      });
        SeeMoreButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            preview.setContent(new IhmGridDetailledPreview(instance,200));
        }
      });
    }
    
    public void setThisAnchorElements()
    {
        this.getChildren().add(border);
    }
}
