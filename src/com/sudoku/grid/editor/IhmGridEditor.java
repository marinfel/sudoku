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
import com.sudoku.grid.gridcells.IhmCell;
import com.sudoku.grid.gridcells.IhmCellView;
import com.sudoku.grid.gridcells.IhmGridLines;
import com.sudoku.grid.gridcells.IhmGridLines.Flags;
import com.sudoku.grid.popups.IhmPopupsList;
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
 * @author Celine To and Mehdi Kane
 */
public abstract class IhmGridEditor extends IhmGridView {

  private TextField editTitle; //Field to enter the grid title
  private Button validBtn; //Button to valid the edited grid
  private Button cancelBtn; //Cancel edition
  private Flags flag; //Flag to know if the cells are editable or fixed

  /**
   *
   * @param flagStatus Indicates whether the cells have to be fixed or editable
   * @param gr Grid object created by data
   * @param size Size of the grid in pixels
   */
  public IhmGridEditor(Flags flagStatus, Grid gr, int size) {
    super(flagStatus, gr, size); //IhmGridView's constructor

    editTitle = new TextField();
    editTitle.setPromptText("Enter a grid title");
    validBtn = new Button("Submit");
    cancelBtn = new Button("Cancel");
    flag = flagStatus;

    //Adds the editTitle text field to the top of the borderPane
    HBox topLayout = (HBox) border.getTop();
    topLayout.getChildren().add(editTitle);
    topLayout.setPrefHeight(100);
    topLayout.setAlignment(Pos.CENTER);

    //HBox that contains the list of tags (i.e. tagsList)
    HBox firstHbox = new HBox();
    final ListView<String> tagsList = new ListView<String>();
    final ObservableList<String> tagsListValues = FXCollections.observableArrayList();
    tagsList.setItems(tagsListValues);
    tagsList.setOrientation(Orientation.HORIZONTAL);
    firstHbox.getChildren().add(tagsList);

    //HBox that contains the text field to enter a new tag
    HBox secondHbox = new HBox();
    final TextField tagField = new TextField();
    tagField.setPromptText("Type in a tag");
    secondHbox.getChildren().add(tagField);
    Button submit = new Button("+");
    secondHbox.getChildren().add(submit);

    //Adds the two previous HBox a VBox to align them vertically
    VBox bottomVBox = new VBox();
    bottomVBox.getChildren().addAll(firstHbox, secondHbox);
    tagsList.setMaxHeight(75.0); //Avoids that the tag list hides the grid
    tagsList.setPrefWidth(400.0);

    /*Adds the previous VBox, the valid and cancel buttons
     to the bottom of the borderPane
     */
    HBox bottomLayout = (HBox) border.getBottom();
    bottomLayout.getChildren().addAll(bottomVBox, validBtn, cancelBtn);
    bottomLayout.setPrefHeight(100);

    //Adjusts the dimensions of the right pane of the borderPane
    VBox rightLayout = (VBox) border.getRight();
    rightLayout.setPrefWidth(110);
    rightLayout.setMinWidth(100);

    //Sets the padding around the four sides of the borderPane
    border.setPadding(new Insets(0.0, 100.0, 10.0, 100.0));

    /*Handler that changes the grid title whenever the content of the editTitle
     textField is modified
     */
    editTitle.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable,
        String oldTitle, String newTitle) {
        grid.setTitle(newTitle);
      }
    });

    //Handler that checks if everything is valid before leaving the edition mode
    validBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //Checks that the grid title is not null and is not empty
        if (!(grid.getTitle() == null) && !grid.getTitle().isEmpty()) {

          IhmCell[][] cells = gridLines.getCells();
          int count = 0;
          int i = 0, j = 0;
          //Counts the number of FixedCell
          while (i < cells.length) {
            j = 0;
            while (j < cells[i].length && count < 17) {
              /*Cells that are editable and have a positive value
               (IhmGridEditorManuallyFilled) or cells that are fixed and visible
               (IhmGridEditorRandomlyFilled) will be FixedCell
               */
              if ((flag.contains(IhmGridLines.ALL_EDITABLE) && cells[i][j].getValue() > 0)
                || (flag.contains(IhmGridLines.ALL_VIEW) && !((IhmCellView) cells[i][j]).isHidden())) {
                count++;
              }
              j++;
            }
            i++;
          }
          if (count < 17) { //At least 17 cells have to be filled
            //Displays an error pop-up to inform the user
            String title = new String("Not enough filled cells");
            String text = new String(
              "You need to fill at least 17 cells to validate your grid");
            IhmPopupsList.getInstance().addPopup(title, text, 10);
          } else {
            //Saves the tags in data's grid object
            ArrayList<Tag> tmpList = new ArrayList<Tag>();
            for (String str : tagsListValues) {
              tmpList.add(new Tag(str));
            }
            grid.setTags(tmpList);

            i = 0;
            j = 0;
            //Saves the edited grid in data's grid object
            if (flag.contains(IhmGridLines.ALL_VIEW)) {
              for (i = 0; i < cells.length; i++) {
                for (j = 0; j < cells[i].length; j++) {
                  if (!((IhmCellView) cells[i][j]).isHidden()) {
                    grid.setFixedCell((byte) i, (byte) j,
                      (byte) cells[i][j].getValue());
                  } else {
                    grid.setEmptyCell((byte) i, (byte) j);
                  }
                }
              }
            } else { //IhmGridLines.ALL_EDITABLE
              for (i = 0; i < cells.length; i++) {
                for (j = 0; j < cells[i].length; j++) {
                  if (cells[i][j].getValue() > 0) {
                    grid.setFixedCell((byte) i, (byte) j,
                      (byte) cells[i][j].getValue());
                  }
                }
              }
            }

            //Display values of the grid object (by column)
            /*for (i = 0; i < cells.length; i++) {
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
             */
            //Saves creation date into data's grid object
            java.util.Date date = new java.util.Date();
            grid.setCreateDate(new Timestamp(date.getTime()));

            //Adds the grid to the GridManager to make it available for Main
            GridManager gm = GridManager.getInstance();
            gm.addGrid(grid);

            //Notify Main of the successful creation of a grid
          }
        } else { //If there is no title to the Grid
          //Displays a popup error to notify the user
          String title = new String("No Title");
          String text = new String(
            "You have to provide a title for your grid");
          IhmPopupsList.getInstance().addPopup(title, text, 10);

        }
      }
    }
    );

    /*
     Handler that cancels the grid creation process and displays the editing
     choice screen
     */
    cancelBtn.setOnAction(
      new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

        }
      });

    //When the Enter key is pressed adds a tag to the tagList
    tagField.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER) && !tagField.getText().isEmpty()) {
          tagsListValues.add(tagField.getText());
          tagField.clear();
        }
      }
    });

    //Adds a tag when the submit button is pressed
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

    //Remove the clicked tag from the tag list
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

  //Utilité concretement ??????
  public Button getValidBtn() {
    return validBtn;
  }

}
