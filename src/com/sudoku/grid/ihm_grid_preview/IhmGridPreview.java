/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.ihm_grid_preview;
import com.sudoku.grid.ihm_grid_cells.IhmCell;
import com.sudoku.grid.ihm_grid_cells.IhmCellView;
import com.sudoku.data.model.Grid;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines;
import com.sudoku.grid.manager.IhmGridLayout;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Vector;
import static com.sudoku.data.factory.GridFactory.generateRandomGrid;

/**
 * @author Mélie
 * deux tailles de preview, envoyer 100 ou 200 pour définir la taille de la preview
 */
public class IhmGridPreview extends IhmGridLayout {

  Text authorName;
  Vector<StarView> grades;
  protected final BorderPane border;

  public IhmGridPreview(double numberOfStars, Grid gr, int size) {
    super(IhmGridLines.ALL_VIEW.add(IhmGridLines.FIT_GRID), gr, size);
	
	border = new BorderPane();
    getChildren().add(border);
    HBox topHBox = new HBox();
    VBox bottomHBox = new VBox();
    VBox centerVBox = new VBox();
	 // center a faire
    border.setTop(topHBox);
    border.setBottom(bottomHBox);
    border.setCenter(centerVBox);
	topHBox.getChildren().add(new Label("Preview"));
	centerVBox.getChildren().add(gridLines);
    /*int i;
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

    getChildren().add(box);*/
  }

}
