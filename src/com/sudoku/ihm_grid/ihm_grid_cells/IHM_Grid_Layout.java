package com.sudoku.ihm_grid.ihm_grid_cells;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;

/**
 *
 * @author groudame
 */
abstract class IHM_Grid_Layout {
    private Group root;
    private Scene layout;
    private Text title;
    //Grid grille;
    public IHM_Grid_Layout(String ttl){
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
