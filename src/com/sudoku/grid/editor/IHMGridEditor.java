/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author celine
 */
public class IHMGridEditor extends IHMGridView{
    private TextField editTitle;
    // gestion des ajouts de tags
    private Button validBtn;
    private Button cancelBtn;
    
    public IHMGridEditor(String t) {
        super(t);
        
        // bouton d'enregistrement de la grille
        editTitle = new TextField();
        editTitle.setPromptText("Entrer le titre");
        validBtn = new Button("Valider");
        cancelBtn = new Button("Annuler");
        
    // layout du haut
        HBox topLayout = (HBox)super.getBorder().getTop();
        topLayout.getChildren().addAll(editTitle, validBtn);
        topLayout.setPrefHeight(100);

    // layout du bas : ajout de tags   
        VBox bottomLayout = (VBox)super.getBorder().getBottom();
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
        
        ImageView sudokuGrid = new ImageView(new Image("http://0.tqn.com/d/np/memory-booster-puzzles/037-1.jpg"));
        VBox centerLayout = (VBox)super.getBorder().getCenter();
        centerLayout.getChildren().addAll(sudokuGrid);
        sudokuGrid.setFitHeight(500);
        sudokuGrid.setFitWidth(500);
        
        editTitle.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // ecrire_en_dur_ou_en_memoire(editTitle.getText());
                
            }
        });
        
        validBtn.setOnAction(new EventHandler<ActionEvent>() {    
            @Override
            public void handle(ActionEvent event) {
                //envoyer le fichier de données à IHM-Main
                System.out.println("tags récupérés : " + tagsListValues.toString());
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
    
    
    
    

}
