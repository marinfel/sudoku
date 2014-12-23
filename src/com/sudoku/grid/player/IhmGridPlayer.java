/*
 * IHM d'une grille de sudoku
 * possibilite de jouer la grille, afficher les commentaires, ajouter un commentaires
 */
package com.sudoku.grid.player;

import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.Comment;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.User;
import com.sudoku.grid.editor.IhmGridView;
import com.sudoku.grid.gridcells.IhmGridLines;
import com.sudoku.grid.gridcells.IhmGridLinesCompleted;
import com.sudoku.grid.preview.StarsBox;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
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
import javax.imageio.ImageIO;

/**
 * @author Laetitia & Melie
 */
public class IhmGridPlayer extends IhmGridView implements EventHandler<IhmGridLinesCompleted> {

  //nombre de commentaires à afficher sous la grille
  private final int nbComm = 2;
  private StarsBox starsBox; //moyenne de la grille
  private User author; //auteur de la grille
  private List<Comment> gridComments; // liste des commentaires associés à la grille
  private Label title;
  Label authorName; //pseudo de l'auteur
  Image iAuthorPicture; // image de l'auteur

  public IhmGridPlayer(Grid grid) {
    //ajout d'une grille (cellules editables ET non éditables)
    super(IhmGridLines.FIT_GRID, grid, 500);

    //ajout du titre
    HBox topLayout = (HBox) border.getTop();
    title = new Label(grid.getTitle());
    title.setFont(new Font(30));
    topLayout.getChildren().add(title);
    topLayout.setPrefHeight(100);
    topLayout.setAlignment(Pos.CENTER);

    //ajout de l'auteur
    //une image par défaut et le pseudo "Unknown" est affiché
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

    //affichage, resize image
    BufferedImage authorPictB = SwingFXUtils.fromFXImage(iAuthorPicture, null);
    BufferedImage iAuthorPictureResized=resize(authorPictB,165,165);
    Image authImage = SwingFXUtils.toFXImage(iAuthorPictureResized, null);
    //authorPict.setImage(authImage);  
    
    ImageView authorPict = new ImageView(authImage);
    
    authorBox.getChildren().add(authorPict);
    authorBox.getChildren().add(authorName);
    authorBox.setAlignment(Pos.TOP_LEFT);

    //ajout de la moyenne
    final HBox bottomLayout = (HBox) border.getBottom();
    starsBox = getStarsBox();
    starsBox.setValue(grid.getMeanGrades());
    bottomLayout.getChildren().add(starsBox); //à redimensionner

    //Zone des commentaires
    // les deux derniers commentaires sont affichés au bas de la grille
    VBox firstComm = new VBox();
    gridComments = grid.getComments();
    int size = gridComments.size();
    if (size > 0) {
      for (int i = 1; i < nbComm; i++) {
        Comment comm = gridComments.get(size - i);
        Label commAuthorAndDate = new Label(comm.getAuthor().getPseudo() + " - " + comm.getCreationDate().toString());
        firstComm.getChildren().add(commAuthorAndDate);
        Text commText = new Text(comm.getComment());
        firstComm.getChildren().add(commText);
      }
    }

    //ajout des boutons "ajout" et "affichage" des commentaires
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
    //l'ajout de commentaire est possible seulement si l'auteur de la grille est connecté

    boolean showOrNotShow = UserManager.getInstance().getConnectedUsers().contains(grid.getCreateUser());
    if (!showOrNotShow) {
      showOrNotShow = (grid.getCreateUser().getSalt() == UserManager.getInstance().getLoggedUser().getSalt());
    }
    addComment.setVisible(showOrNotShow);

    commButton.getChildren().add(addComment);
    commButton.getChildren().add(showAllComments);
    commBox.getChildren().addAll(firstComm, commButton);
    bottomLayout.getChildren().add(commBox);

    gridLines.addEventHandler(IhmGridLinesCompleted.GRID_COMPLETED, this);
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

    stage.initModality(Modality.APPLICATION_MODAL); // impossible de jouer à la grille sans fermer cette fenêtre

    //formulaire
    Label labelText = new Label("Text");
    gridPane.add(labelText, 0, 2);
    final TextField textField = new TextField();
    gridPane.add(textField, 1, 2);

    /*Note */
    Label labelNote = new Label("Note");
    //gradeFormBox = getStarsBox(3);
    gridPane.add(labelNote, 0, 3);

    final StarsBox starsBox = getStarsBox();
    starsBox.setHoverable(true);
    gridPane.add(starsBox, 1, 3);

    stage.show();

    Button buttonOk = new Button("Ok");
    Button buttonCancel = new Button("Cancel");

    final IhmGridPlayer ihmGridPlayer = this;
    buttonOk.setOnAction(new EventHandler<ActionEvent>() {
      //ajout du commentaire et fermeture de la fenêtre
      @Override
      public void handle(ActionEvent event) {
        try {
          grid.addComment(new Comment(textField.getText(), starsBox.getValueAtClick(), UserManager.getInstance().getLoggedUser()));
          ihmGridPlayer.starsBox.setValue(grid.getMeanGrades());
        } catch (Exception e) {
        }
        stage.hide();
      }
    });

    buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
      //fermeture de la fenêtre
      @Override
      public void handle(ActionEvent event) {
        stage.hide();
      }
    });

    gridPane.add(buttonOk, 0, 4);
    gridPane.add(buttonCancel, 1, 4);

  }

  //affichage de tous les commentaires associés à la grille
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

    //ajout de la scroll bar
    sc.setLayoutX(scene.getWidth() - sc.getWidth());
    sc.setMin(0);
    sc.setOrientation(Orientation.VERTICAL);
    sc.setPrefHeight(scene.getHeight());
    sc.setMax(scene.getHeight() + 50);
    gridComments = grid.getComments();
    if (gridComments.size() > 0) {
      for (int i = 0; i < gridComments.size(); i++) {
        Comment comm = gridComments.get(i);
        Label commAuthorAndDate = new Label(comm.getAuthor().getPseudo() + " - " + comm.getCreationDate());
        vb.getChildren().add(commAuthorAndDate);
        Text commText = new Text(comm.getComment());
        commText.setWrappingWidth(270);
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

    stage.initModality(Modality.APPLICATION_MODAL); // impossible de kouer la grille sans fermer cette fenêtre

    Button closeButton = new Button("Close");
    closeButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        stage.hide();
      }
    });

    stage.show();
  }

  @Override
  public void handle(IhmGridLinesCompleted t) {
    fireEvent(t);
  }
  
  public static BufferedImage resize(BufferedImage image, int width, int height) 
  {
    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
    Graphics2D g2d = (Graphics2D) bi.createGraphics();
    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
    g2d.drawImage(image, 0, 0, width, height, null);
    g2d.dispose();
    return bi;
  }
  
}


/*File imgFile=new File(image);
		BufferedImage img = null;
		try 
		{
			img = ImageIO.read(imgFile);
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage resizedImage=resize(img,this.getWidth(),this.getHeight());
*/

