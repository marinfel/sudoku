/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.ihm_grid_preview;

import com.sudoku.grid.manager.IhmGridLayout;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines;
import java.util.Vector;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 *
 * @author Mélie
 */
public class IhmGridPreview extends IhmGridLayout {

    Text authorName;
    Vector<StarView> grades;

    public IhmGridPreview(String ttl, double numberOfStars) {
        super(ttl);

        IhmGridLines ihmGridLines = new IhmGridLines(null, 100, IhmGridLines.ALL_VIEW);
        getChildren().add(ihmGridLines);

        int i;
        grades = new Vector();
        int num = (int) Math.floor(numberOfStars);
        for (i = 0; i <= num; i++) {
            grades.add(new StarView(1));
        }
        if (Math.round(numberOfStars * 2) % 2 != 0) {
            grades.add(new StarView(2));
        }
        for (i = grades.size(); i < 6; i++) {
            grades.add(new StarView(3));
        }
        HBox box = new HBox();
        box.setLayoutX(5);
        box.setLayoutY(sceneHeight - 30);
        for (i = 1; i < 6; i++) {
            box.getChildren().add(grades.get(i).getStar());
        }

        getChildren().add(box);
    }

}
