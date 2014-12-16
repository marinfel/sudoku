/**
 * StarView est la classe representant l objet graphique etoile. Cet objet sert 
 * a la représentation de la note d une grille.
 * 
 */

package com.sudoku.grid.preview;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author groudame, lleichtn
 */
public final class StarView extends ImageView {

  /**
  * \enum StarTypes permet de definir le type d une etoile
  */  
  public enum StarTypes {

      /**
       * FILLED etoile pleine (en jaune)
       */
      FILLED,

      /**
       * HALF etoile demi-pleine (jaune et blanc)
       */
      HALF,

      /**
       * EMPTY etoile vide (blanc)
       */
      EMPTY;
  }

    protected final Image starFilled;
    protected final Image starHalf;
    protected final Image starEmpty;
  
    /**
     * Constructeur par défaut de l objet StarView.
     * Cette methode recupere les images etoiles 
     */
    public StarView() {
      
    starFilled = new Image(new File("pictures/grid/yellowStar.png").toURI().toString());
    starHalf = new Image(new File("pictures/grid/yellowWhiteStar.png").toURI().toString());
    starEmpty = new Image(new File("pictures/grid/whiteStar.png").toURI().toString());

    setType(StarTypes.EMPTY);
  }

    /**
     * Constructeur de l objet StarView. 
     * @param typeOfStar
     */
    public StarView(StarTypes typeOfStar) {
    this();
    setType(typeOfStar);
  }

    /**
     * Cette methode fixe le type de l'objet star  
     * @param type
     */
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
