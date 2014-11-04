/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sudoku.grid.editor;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;

/**
 *
 * @author groudame
 */
abstract class IHMGridLayout {
    private Group root;
    private Scene layout;
    private Text title;
    //Grid grille;
    public IHMGridLayout(String ttl){
        root = new Group();
        layout = new Scene(getRoot(), 300, 250);
        title = new Text(30,20,ttl);
        root.getChildren().add(title);
        
    }

    /**
     * @return the root
     */
    public Group getRoot() {
        return root;
    }

    /**
     * @return the layout
     */
    public Scene getLayout() {
        return layout;
    }

    /**
     * @return the title
     */
    public Text getTitle() {
        return title;
    }
    
}
