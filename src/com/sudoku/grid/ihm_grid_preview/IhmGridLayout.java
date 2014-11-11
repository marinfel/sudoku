/*
 * To change this license header, choose License Headers in Project 
 * Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sudoku.grid.ihm_grid_preview;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
/**
 *
 * @author Mélie
 */
abstract class IhmGridLayout {
    protected Group root;
    protected Scene layout;
    protected Label title;
    protected final int sceneHeight = 200;
    protected final int sceneLength = 200;
    //Grid grille;
    public IhmGridLayout (String ttl){
        root = new Group();
        layout = new Scene(root, 200, 200);
        title = new Label(ttl);
        root.getChildren().add(title);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
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
    public Label getTitle() {
        return title;
    }
}
