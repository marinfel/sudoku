/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.ihm_grid_player;

//import com.sudoku.data.model.Comment;
import com.sudoku.data.model.Grid;
//import com.sudoku.data.model.User;
import com.sudoku.grid.editor.IhmGridView;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines.Flags;
import com.sudoku.grid.ihm_grid_preview.StarView;
import com.sudoku.grid.ihm_grid_preview.StarsBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Vector;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Laetitia
 */
public class IhmGridPlayer extends IhmGridView {

  private final int nbComm = 2;
  private Vector<StarView> grades;
  private StarsBox starsBox;
  //private User author;
  //private List<Comment> gridComments;
  private Grid playGrid;

  public IhmGridPlayer(Grid gr) {
    super(IhmGridLines.FIT_GRID, gr);

    //mettre la grille
    //commentaires
    //gridComments = playGrid.getComments();
    final VBox commBox = (VBox) border.getBottom();

    // list of comm
    for (int i = 1; i < nbComm; i++) {
      //Comment comm = gridComments.get(gridComments.size()-i);
      VBox oneCommBox = new VBox();
      Label commTitle = new Label("premier com"/*comm.getTitle()*/);
      Label commAuthorAndDate = new Label("author"/*comm.getAuthor()+" - "+comm.getCreateDate()*/);
      Text commText = new Text("com ..."/*comm.getComment()*/);
      oneCommBox.getChildren().addAll(commTitle, commAuthorAndDate, commText);
      commBox.getChildren().addAll(oneCommBox);
    }

    HBox commButton = new HBox();

    Button addComment = new Button("Ajouter un com");
    addComment.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
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

    commButton.getChildren().add(addComment);
    commButton.getChildren().add(showAllComments);
    commBox.getChildren().addAll(commButton);

    // ajouter un commentaire
    /*HBox addCommBox = new HBox();
     final TextField titleField = new TextField();
     titleField.setPromptText("Entrez un titre");
     addCommBox.getChildren().add(titleField);
     final TextField commField = new TextField();
     commField.setPromptText("Entrez un commentaire");
     addCommBox.getChildren().add(commField);
     Button submit = new Button("+");
     addCommBox.getChildren().add(submit);

     commBox.getChildren().addAll(addCommBox);*/
    //la note
    //grades = getStars(2/*playGrid.getGrade()*/);
    //HBox gradeBox = new HBox();
    //HBox gradeBox = (HBox) border.getTop();
    //int i;
//    for (i = 0; i < 5; i++) {
//      gradeBox.getChildren().add(grades.elementAt(i).getStarsBox());
//    }
    //popups
    //auteur
    VBox authorBox = (VBox) border.getLeft();
    Label authorName = new Label("auth"/*author.getPseudo()*/);
    //Image iAuthorPicture = new Image(author.getProfilePicturePath(),true);
    ImageView authorPict = new ImageView();
    //authorPict.setImage(iAuthorPicture);
    authorBox.getChildren().addAll(authorName, authorPict);

    /*submit.setOnAction(new EventHandler<ActionEvent>() {
     @Override
     public void handle(ActionEvent event) {
     //Comment newComm = new Comment("", 1, author);
     /*
     ajout du titre :
     newComm.setTitle(textField.getText());
     ajout de la date :
     SimpleDateFormat = new SimpleDateFormat("dd/MM/yy H:mm:ss");
     newComm.setDate(formater.format(date));
     ajout de l'auteur :
     newComm.setAuthor(getCurrentPlayer());
     */
    /*playGrid.addComment(newComm);
     commField.clear();
     titleField.clear();
     commBox.getChildren().remove(nbComm-1);
     VBox oneCommBox = new VBox();
     //Label commTitle = new Label(/*newComm.getTitle());
     //Label commAuthorAndDate = new Label(/*newComm.getAuthor()+" - "+newComm.getCreateDate());
     /*Text commText = new Text(newComm.getComment());
     oneCommBox.getChildren().addAll(commTitle,commAuthorAndDate,commText);
     commBox.getChildren().add(1,oneCommBox);
     }
     });*/
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

    Label labelTitle = new Label("Title");
    TextField titleField = new TextField();
    gridPane.add(labelTitle, 0, 1);
    gridPane.add(titleField, 1, 1);

    Label labelText = new Label("Text");
    //textField.setPrefSize(50, 50);
    gridPane.add(labelText, 0, 2);
    TextField textField = new TextField();
    gridPane.add(textField, 1, 2);

    /*Note */
    Label labelNote = new Label("Note");
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
        //addComment(); ->data
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
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    Scene scene = new Scene(gridPane, 300, 150);
    stage.setScene(scene);
    stage.setTitle("Comments");
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();
  }
}
