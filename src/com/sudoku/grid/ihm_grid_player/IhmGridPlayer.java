/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.ihm_grid_player;

import com.sudoku.data.model.Comment;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.User;
import com.sudoku.grid.editor.IhmGridView;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines;
import com.sudoku.grid.ihm_grid_cells.IhmGridLines.Flags;
import com.sudoku.grid.ihm_grid_preview.StarView;
import java.util.List;
import java.util.Vector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Laetitia
 */
public class IhmGridPlayer extends IhmGridView {

  private Vector<StarView> grades;
  private User author;
  private List<Comment> gridComments;
  private final int nbComm = 2;
  private Grid playGrid;

  public IhmGridPlayer(String t, Flags flagStatus, Grid gr) {
    super(t, flagStatus, gr);

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

    // ajouter un commentaire
    HBox addCommBox = new HBox();
    final TextField titleField = new TextField();
    titleField.setPromptText("Entrez un titre");
    addCommBox.getChildren().add(titleField);
    final TextField commField = new TextField();
    commField.setPromptText("Entrez un commentaire");
    addCommBox.getChildren().add(commField);
    Button submit = new Button("+");
    addCommBox.getChildren().add(submit);

    commBox.getChildren().addAll(addCommBox);

    //la note
    grades = getStars(2/*playGrid.getGrade()*/);

    //HBox gradeBox = new HBox();
    HBox gradeBox = (HBox) border.getTop();

    int i;
    for (i = 0; i < 5; i++) {
      gradeBox.getChildren().add(grades.elementAt(i).getStar());
    }

    //popups
    //auteur
    VBox authorBox = (VBox) border.getLeft();
    Label authorName = new Label("auth"/*author.getPseudo()*/);
    //Image iAuthorPicture = new Image(author.getProfilePicturePath(),true);
    ImageView authorPict = new ImageView();
    //authorPict.setImage(iAuthorPicture);
    authorBox.getChildren().addAll(authorName, authorPict);

    submit.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Comment newComm = new Comment();
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
         commBox.getChildren().add(1,oneCommBox);*/
      }
    });
  }

  private Vector<StarView> getStars(int numberOfStars) {
    grades = new Vector();
    int i;
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
    return grades;
  }
}
