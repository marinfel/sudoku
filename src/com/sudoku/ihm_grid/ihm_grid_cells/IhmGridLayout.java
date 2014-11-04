package com.sudoku.ihm_grid.ihm_grid_cells;

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
    //Grid grille;
    public IhmGridLayout(String ttl){
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
