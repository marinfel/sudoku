/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

import com.sudoku.data.manager.GridManager;
import com.sudoku.data.model.FixedCell;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.Tag;
import com.sudoku.grid.ihm_grid_cells.IhmCell;
import com.sudoku.grid.ihm_grid_cells.IhmCellView;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines.Flags;
import com.sudoku.grid.ihm_popups.IhmPopupsList;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author celine
 */
public abstract class IhmGridEditor extends IhmGridView {

  private TextField editTitle;
  // gestion des ajouts de tags
  private Button validBtn;
  private Button cancelBtn;
  private Flags flag;

  public IhmGridEditor(Flags flagStatus, Grid gr) {
    super(flagStatus, gr);

    // bouton d'enregistrement de la grille
    editTitle = new TextField();
    editTitle.setPromptText("Entrer le titre");
    validBtn = new Button("Valider");
    cancelBtn = new Button("Annuler");

    flag = flagStatus;

    HBox topLayout = (HBox) border.getTop();

    topLayout.getChildren().add(editTitle);
    topLayout.setPrefHeight(100);
    topLayout.setAlignment(Pos.CENTER);

    // layout du bas : ajout de tags
    VBox bottomVBox = new VBox();
    // list of entered tags
    HBox firstHbox = new HBox();
    final ListView<String> tagsList = new ListView<String>();
    final ObservableList<String> tagsListValues = FXCollections.observableArrayList();
    tagsList.setItems(tagsListValues);
    tagsList.setOrientation(Orientation.HORIZONTAL);
    firstHbox.getChildren().add(tagsList);
    //bottomLayout.setPrefHeight(100);
    // enter a tag
    HBox secondHbox = new HBox();
    final TextField tagField = new TextField();
    tagField.setPromptText("Entrez votre tag");
    secondHbox.getChildren().add(tagField);
    Button submit = new Button("+");
    secondHbox.getChildren().add(submit);

    bottomVBox.getChildren().addAll(firstHbox, secondHbox);
    //bottomVBox.setPrefWidth(500.0);
    tagsList.setMaxHeight(75.0); //Sinon le tagsList cache les boutons du leftPane
    tagsList.setPrefWidth(400.0);

    // layout du bas
    HBox bottomLayout = (HBox) border.getBottom();

    bottomLayout.getChildren().addAll(bottomVBox, validBtn, cancelBtn);
    bottomLayout.setPrefHeight(100);

    VBox rightLayout = (VBox) border.getRight();
    rightLayout.setPrefWidth(110);
    rightLayout.setMinWidth(100);
    //rightLayout.setPrefWidth(150);

    border.setPadding(new Insets(0.0, 100.0, 10.0, 100.0));

// handlers
    editTitle.textProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable,
        String oldTitle, String newTitle) {
        // Handle any change on the textField
        grid.setTitle(newTitle);
      }
    });

    validBtn.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        if (!(grid.getTitle() == null) && !grid.getTitle().isEmpty()) {

          IhmCell[][] cells = gridLines.getCells();
          int count = 0;
          int i = 0, j = 0;
          // count cells number that are going to be FixedCell
          while (i < cells.length) {
            j = 0;
            while (j < cells[i].length && count < 17) {
              if ((flag.contains(IhmGridLines.ALL_EDITABLE) && cells[i][j].getValue() > 0)
                || (flag.contains(IhmGridLines.ALL_VIEW) && !((IhmCellView) cells[i][j]).isHidden())) {
                count++;
              }
              j++;
            }
            i++;
          }
          if (count < 17) {
            // display an error pop-up when 17 cells are not visible
            String title = new String("Not enough filled cells");
            String text = new String(
              "You need to fill at least 17 cells to validate your grid");
            IhmPopupsList.getInstance().addPopup(title, text, 10);
          } else {
            // save tags into data's grid objet
            ArrayList<Tag> tmpList = new ArrayList<Tag>();
            for (String str : tagsListValues) {
              tmpList.add(new Tag(str));
            }
            grid.setTags(tmpList);
            i = 0;
            j = 0;
            // save final grid into data's grid object
            if (flag.contains(IhmGridLines.ALL_VIEW)) {
              for (i = 0; i < cells.length; i++) {
                for (j = 0; j < cells[i].length; j++) {
                  if (!((IhmCellView) cells[i][j]).isHidden()) {
                    grid.setFixedCell((byte) i, (byte) j,
                      (byte) cells[i][j].getValue());
                  } else {
                    System.out.println("entrer dans setEmptyCell");
                    grid.setEmptyCell((byte) i, (byte) j);
                  }
                }
              }
            } else { // IhmGridLines.ALL_EDITABLE
              for (i = 0; i < cells.length; i++) {
                for (j = 0; j < cells[i].length; j++) {
                  if (cells[i][j].getValue() > 0) {
                    grid.setFixedCell((byte) i, (byte) j,
                      (byte) cells[i][j].getValue());
                  }
                }
              }
            }

            // display values of the grid object (by column)
            for (i = 0; i < cells.length; i++) {
              System.out.println("\n");
              for (j = 0; j < cells[i].length; j++) {
                if (grid.getCell(j, i) instanceof FixedCell) {
                  System.out.print(((FixedCell) grid.getCell(j, i)).getValue());
                } else {
                  System.out.print("0");
                }
              }
            }
            System.out.println("\n");
            // save creation date into data's grid object
            java.util.Date date = new java.util.Date();
            grid.setCreateDate(new Timestamp(date.getTime()));
            System.out.println(grid.getCreateDate());
            System.out.println(grid.getTitle());
            List<Tag> tags = grid.getTags();
            for (Tag tag : tags) {
              System.out.println(tag.getName());
            }

            // add a grid to GridManager
            GridManager gm = GridManager.getInstance();
            gm.addGrid(grid);

            //Envoie event a IhmMain pour indiquer la fin de l'edition
          }
        } else {
          String title = new String("No title");
          String text = new String(
            "You have to provide a title for your grid");
          IhmPopupsList.getInstance().addPopup(title, text, 10);

        }
      }
    }
    );

    cancelBtn.setOnAction(
      new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event
        ) {
          //supprimer le fichier en dur
        }
      });

    tagField.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
          tagsListValues.add(tagField.getText());
          tagField.clear();
        }
      }
    });

    submit.setOnAction(
      new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event
        ) {
          tagsListValues.add(tagField.getText());
          tagField.clear();
        }
      }
    );

    tagsList.setOnMouseClicked(
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event
        ) {
          tagsListValues.remove(tagsList.getSelectionModel().getSelectedItem());
        }
      }
    );
  }

  public Button getValidBtn() {
    return validBtn;
  }

}
