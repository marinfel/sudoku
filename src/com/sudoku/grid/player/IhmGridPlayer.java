/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.player;

import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.Comment;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.User;
import com.sudoku.grid.editor.IhmGridView;
import com.sudoku.grid.gridcells.IhmGridLines;
import com.sudoku.grid.preview.StarsBox;
import java.io.File;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Laetitia
 */
public class IhmGridPlayer extends IhmGridView {

  private final int nbComm = 2;

  private StarsBox starsBox;
  //private User author;
  //private List<Comment> gridComments;

  private User author;
  private List<Comment> gridComments;
  private Label title;
  Label authorName;
  Image iAuthorPicture;

  public IhmGridPlayer(Grid grid) {
    super(IhmGridLines.ALL_EDITABLE.add(IhmGridLines.FIT_GRID), grid, 500);

    //ajout du titre
    HBox topLayout = (HBox) border.getTop();
    title = new Label(grid.getTitle());
    title.setFont(new Font(30));
    topLayout.getChildren().add(title);
    topLayout.setPrefHeight(100);
    topLayout.setAlignment(Pos.CENTER);

    //ajout de l'auteur
    VBox authorBox = (VBox) border.getLeft();
    try {
      author = grid.getCreateUser();
      authorName = new Label(grid.getCreateUser().getPseudo());
      if (author.getProfilePicturePath() != null) {
        iAuthorPicture = new Image(new File(author.getProfilePicturePath()).toURI().toString());
      } else {
        iAuthorPicture = new Image(new File("pictures/grid/inconnu.png").toURI().toString());
      }
    } catch (Exception e) {
      authorName = new Label("Unknown");
      iAuthorPicture = new Image(new File("pictures/grid/inconnu.png").toURI().toString());
    }

    ImageView authorPict = new ImageView();
    authorPict.setImage(iAuthorPicture);
    authorBox.getChildren().addAll(authorPict, authorName);
    authorBox.setAlignment(Pos.TOP_LEFT);

    //Zone des commentaires
    final HBox bottomLayout = (HBox) border.getBottom();

    // deux derniers commentaires
    VBox firstComm = new VBox();
    try {
      gridComments = grid.getComments();
    } catch (Exception e) {
      gridComments = null;
    }
    int size = gridComments.size();
    if (gridComments != null && size > 0) {
      for (int i = 1; i < nbComm; i++) {
        Comment comm = gridComments.get(size - i);
        Label commAuthorAndDate = new Label(comm.getAuthor() + " - "/*+comm.getCreateDate*/);
        firstComm.getChildren().add(commAuthorAndDate);
        Text commText = new Text(comm.getComment());
        firstComm.getChildren().add(commText);
      }
    }

    //ajout des boutons ajout et affichage
    HBox commButton = new HBox();
    Button addComment = new Button("Ajouter un com");
    addComment.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        System.out.println("showaddcom()");
        showAddCommentForm();

      }
    });

    Button showAllComments = new Button("Show all comments");
    showAllComments.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        showAllComments();
      }

    });
    VBox commBox = new VBox();
    commButton.getChildren().add(addComment);
    commButton.getChildren().add(showAllComments);
    commBox.getChildren().addAll(firstComm, commButton);
    bottomLayout.getChildren().add(commBox);

  }

  private void showAddCommentForm() {

    final Stage stage = new Stage();

    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    Scene scene = new Scene(gridPane, 300, 150);
    stage.setScene(scene);
    stage.setTitle("Add Comments");
    stage.initModality(Modality.APPLICATION_MODAL);

    Label labelText = new Label("Text");
    //textField.setPrefSize(50, 50);
    gridPane.add(labelText, 0, 2);
    final TextField textField = new TextField();
    gridPane.add(textField, 1, 2);

    /*Note */
    Label labelNote = new Label("Note");
    //gradeFormBox = getStarsBox(3);
    gridPane.add(labelNote, 0, 3);

    starsBox = getStarsBox();
    starsBox.setHoverable(true);
    gridPane.add(starsBox, 1, 3);

    stage.show();

    Button buttonOk = new Button("Ok");
    Button buttonCancel = new Button("Cancel");

    buttonOk.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          gridComments.add(new Comment(textField.getText(), (int) Math.round(starsBox.getValueAtClick()), UserManager.getInstance().getLoggedUser()));
        } catch (Exception e) {
        }
        stage.hide();
      }
    });

    buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        stage.hide();
      }
    });

    gridPane.add(buttonOk, 0, 4);
    gridPane.add(buttonCancel, 1, 4);

  }

  private void showAllComments() {
    final Stage stage = new Stage();
    final VBox vb = new VBox();
    final ScrollBar sc = new ScrollBar();
    DropShadow shadow = new DropShadow();
    Group root = new Group();
    Scene scene = new Scene(root, 300, 400);
    stage.setScene(scene);
    stage.setTitle("Show All Comments");
    root.getChildren().addAll(vb, sc);

    shadow.setColor(Color.GREY);
    shadow.setOffsetX(2);
    shadow.setOffsetY(2);

    vb.setLayoutX(5);
    vb.setSpacing(10);
    sc.setLayoutX(scene.getWidth() - sc.getWidth());
    sc.setMin(0);
    sc.setOrientation(Orientation.VERTICAL);
    sc.setPrefHeight(scene.getHeight());
    sc.setMax(scene.getHeight() + 50);

    if (gridComments != null && gridComments.size() > 0) {
      for (int i = 1; i < 10; i++) {
        Comment comm = gridComments.get(gridComments.size() - i);
        Label commAuthorAndDate = new Label(comm.getAuthor() + " - "/*+comm.getCreateDate*/);
        vb.getChildren().add(commAuthorAndDate);
        Text commText = new Text(comm.getComment());
        vb.getChildren().add(commText);
      }
    } else {
      Label emptyComm = new Label("No comments on this grid");
      vb.getChildren().add(emptyComm);
    }
    sc.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> ov,
        Number old_val, Number new_val) {
        vb.setLayoutY(-new_val.doubleValue());
      }
    });
    stage.initModality(Modality.APPLICATION_MODAL);
    Button closeButton = new Button("Close");
    closeButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        stage.hide();
      }
    });

    stage.show();
  }
}
