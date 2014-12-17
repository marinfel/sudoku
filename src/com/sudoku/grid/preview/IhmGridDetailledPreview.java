/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * This class generates a bigger preview with detailled information
 *
 * @author Celine To
 */
package com.sudoku.grid.preview;

import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.Comment;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.Tag;
import com.sudoku.data.model.User;
import com.sudoku.grid.gridcells.IhmGridLines;
import com.sudoku.grid.manager.IhmGridLayout;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class IhmGridDetailledPreview extends IhmGridLayout {

  protected final BorderPane border;

  public IhmGridDetailledPreview(Grid g, int size) {

    super(IhmGridLines.ALL_VIEW.add(IhmGridLines.FIT_GRID), g, size);

    // retrieve grid's name, grid creator's pseudo, tags, and first comment to display
    Grid gr = g;
    String gridTitle = gr.getTitle();
    String userPseudo = "test pseudo"; //gr.getCreateUser().getPseudo();
    List<Tag> tags = gr.getTags();
    List<String> tagsNames = new ArrayList<String>();
    for (Tag tag : tags) {
      tagsNames.add(tag.getName());
    }
    List<Comment> comments = gr.getComments();
    String comment1 = comments.get(1).getComment();

    border = new BorderPane();
    getChildren().add(border);
    border.setPadding(new Insets(20.0, 20.0, 20.0, 20.0));

    VBox topVBox = new VBox();
    //topVBox.setAlignment(Pos.CENTER);
    // Amount of vertical space between each child in the vbox
    topVBox.setSpacing(5);
    topVBox.getChildren().add(new Label("titre : " + gridTitle));
    topVBox.getChildren().add(new Label("user's pseudo : " + userPseudo));
    topVBox.getChildren().add(gridLines);
    topVBox.getChildren().add(new Label("tags : " + tagsNames.toString()));
    topVBox.getChildren().add(new Label("comments : " + comment1));
    border.setCenter(topVBox);

  }

}
