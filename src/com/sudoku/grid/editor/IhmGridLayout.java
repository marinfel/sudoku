/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sudoku.grid.editor;

import com.sudoku.data.model.Grid;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;

/**
 *
 * @author groudame
 */
abstract class IhmGridLayout {
    private Group root;
    private Scene layout;
    private Text title;
    protected Grid grid;
    public IhmGridLayout(String ttl){
        root = new Group();
        layout = new Scene(getRoot(), 300, 250);
        title = new Text(30,20,ttl);
        root.getChildren().add(title);
        grid = new Grid();
    }

    /**
     * @return the root
     */
    public Group getRoot() {
        return root;
    }

    public Grid getGrid(){
        return grid;
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
