/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.preview;

import com.sudoku.grid.gridcells.IhmCell;
import com.sudoku.grid.gridcells.IhmCellView;
import com.sudoku.data.model.Grid;
import com.sudoku.grid.gridcells.IhmGridLines;
import com.sudoku.grid.manager.IhmGridLayout;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * @author Mélie deux tailles de preview, envoyer 100 ou 200 pour définir la
 * taille de la preview
 */
public class IhmGridPreview extends IhmGridLayout {

    /**
     *
     */
    protected Text authorName;

    /**
     *
     */
    protected StarsBox starsBox;

    /**
     *
     */
    protected final BorderPane border;

    /**
     *
     * @param numberOfStars
     * @param gr
     * @param size
     */
    public IhmGridPreview(Grid gr, int size) {
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

    starsBox = getStarsBox();
    starsBox.setValue(gr.getMeanGrades());
    getChildren().add(starsBox); //à redimensionner
  }

}
