/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.ihm_grid_preview;

//import ihm_grid.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author groudame
 */
public class StarView {

  ImageView star;

  public StarView(int typeOfStars) {
    Image iStar;
    switch (typeOfStars) {
      case 1: //yellow
        iStar = new Image(getClass().getResource("yellowStar.png").toExternalForm());
        //iStar = new Image("yellowStar.png", true);
        break;
      case 2: //white and yellow
        iStar = new Image(getClass().getResource("yellowWhiteStar.png").toExternalForm());
        //iStar = new Image("yellowWhiteStar.png");
        break;
      default: //white
        iStar = new Image(getClass().getResource("whiteStar.png").toExternalForm());
        //iStar = new Image("whiteStar.png");
        break;
    }
    star = new ImageView();
    star.setImage(iStar);

  }

  /**
   * @return the star
   */
  public ImageView getStar() {
    return star;
  }

}
