/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.preview;

//import ihm_grid.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author groudame
 */
public final class StarView extends ImageView {

  public enum StarTypes {

    FILLED,
    HALF,
    EMPTY;
  };

  protected final Image starFilled;
  protected final Image starHalf;
  protected final Image starEmpty;

  public StarView() {
    //yellow - com.sudoku.grid.ihm_grid_preview.
    starFilled = new Image(new File("pictures/grid/yellowStar.png").toURI().toString());
    //white and yellow
    starHalf = new Image(new File("pictures/grid/yellowWhiteStar.png").toURI().toString());
    //white
    //iStar = new Image(getClass().getResource("whiteStar.png").toExternalForm());
    starEmpty = new Image(new File("pictures/grid/whiteStar.png").toURI().toString());

    setType(StarTypes.EMPTY);
  }

  public StarView(StarTypes typeOfStar) {
    this();
    setType(typeOfStar);
  }

  public void setType(StarTypes type) {
    switch (type) {
      case FILLED:
        setImage(starFilled);
        break;
      case HALF:
        setImage(starHalf);
        break;
      case EMPTY:
        setImage(starEmpty);
        break;
      default:
        break;
    }
  }
}
