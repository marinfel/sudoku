/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.ihm_grid_preview;

import com.sudoku.data.model.Grid;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines;
import com.sudoku.grid.manager.IhmGridLayout;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * @author Mélie
 */
public class IhmGridPreview extends IhmGridLayout {

  protected Text authorName;
  protected StarsBox starsBox;

  public IhmGridPreview(Grid gr) {
    super(IhmGridLines.ALL_VIEW, gr);

    getChildren().add(gridLines);

    starsBox = getStarsBox();
    starsBox.setValue(gr.getAverageGrade());
    getChildren().add(starsBox);
  }

}
