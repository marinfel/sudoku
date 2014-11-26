/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

import com.sudoku.data.model.Grid;
import com.sudoku.data.model.Tag;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines.Flags;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * @author celine
 */
public abstract class IhmGridEditor extends IhmGridView {
  private TextField editTitle;
  // gestion des ajouts de tags
  private Button validBtn;
  private Button cancelBtn;

  public IhmGridEditor(String ttl, Flags flagStatus, Grid gr) {
    super(ttl, flagStatus, gr);

    // bouton d'enregistrement de la grille
    editTitle = new TextField();
    editTitle.setPromptText("Entrer le titre");
    validBtn = new Button("Valider");
    cancelBtn = new Button("Annuler");

    // layout du haut
    HBox topLayout = (HBox) border.getTop();

    topLayout.getChildren().addAll(editTitle, validBtn);
    topLayout.setPrefHeight(100);

    // layout du bas : ajout de tags   
    VBox bottomLayout = (VBox) border.getBottom();
    // list of entered tags
    HBox firstHbox = new HBox();
    final ListView<String> tagsList = new ListView<String>();
    final ObservableList<String> tagsListValues = FXCollections.observableArrayList();
    tagsList.setItems(tagsListValues);
    tagsList.setOrientation(Orientation.HORIZONTAL);
    firstHbox.getChildren().add(tagsList);
    bottomLayout.setPrefHeight(100);
    // enter a tag
    HBox secondHbox = new HBox();
    final TextField tagField = new TextField();
    tagField.setPromptText("Entrez votre tag");
    secondHbox.getChildren().add(tagField);
    Button submit = new Button("+");
    secondHbox.getChildren().add(submit);

    bottomLayout.getChildren().addAll(firstHbox, secondHbox);

    tagsList.setMaxHeight(75.0); //Sinon le tagsList cache les boutons du leftPane
    // handlers

    editTitle.textProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable,
                          String oldDesc, String newDesc) {
        // Handle any change on the textField
        grid.setDescription(newDesc);
        System.out.println(grid.getDescription());
      }
    });
        
        /*
        editTitle.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                getGrid().setDescription(editTitle.getText());
                System.out.println(getGrid().getDescription());
            }
        });
        */

    validBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //envoyer le fichier de données à IHM-Main
        ArrayList<Tag> tmpList = new ArrayList<Tag>();
        for (String str : tagsListValues) {
          tmpList.add(new Tag(str));
        }
        grid.setTags(tmpList);
      }
    });

    cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //supprimer le fichier en dur
      }
    });

    submit.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        tagsListValues.add(tagField.getText());
        tagField.clear();
      }
    });

    tagsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        tagsListValues.remove(tagsList.getSelectionModel().getSelectedItem());
      }
    });
  }

  public Button getValidBtn() {
    return validBtn;
  }


}
