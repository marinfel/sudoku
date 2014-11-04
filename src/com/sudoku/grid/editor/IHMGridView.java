/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author mecton08
 */
public abstract class IHMGridView extends IHMGridLayout {
    //protected textItemInput textInput;
    //protected textItemView textView;
    protected enum mode {
        Play, 
        Edit
    }
    
    private final BorderPane border;
   
    
    public IHMGridView(String title){
        super(title);
        border = new BorderPane();
        HBox topHBox = new HBox();
        VBox leftVBox = new VBox();
        VBox bottomHBox = new VBox();
        VBox rightVBox = new VBox();
        VBox centerVBox = new VBox();
        
        // center a faire
        
        border.setTop(topHBox);
        border.setLeft(leftVBox);
        border.setBottom(bottomHBox);
        border.setRight(rightVBox);
        border.setCenter(centerVBox);

        //border.setCenter();
       // 
        
    }

    public BorderPane getBorder() {
        return border;
    }
   
    
}
