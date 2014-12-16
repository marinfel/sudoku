/**
 * StarsBox est la classe representant l objet graphique Liste d etoiles. 
 * Elle peut etre dynamique ou statique selon l utilisation
 * 
 */

package com.sudoku.grid.preview;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * @author marco, groudame, lleichtn
 */
public class StarsBox extends HBox implements EventHandler<MouseEvent> {

    protected final int maxNumberOfStars;

    protected List<StarView> stars = new ArrayList<>();

    protected double valueAtClick = 0;

    /**
     * Constructeur de l'objet liste d etoile pour l' affichage de
     * la note moyenne d' une grille ou pour faire une notation
     * @param numberOfStars
     */
    public StarsBox(int numberOfStars) {
        
      maxNumberOfStars = numberOfStars;

      for (int i = 0; i < maxNumberOfStars; i++) {
        stars.add(new StarView());
        getChildren().add(stars.get(i));
      }
    }

  @Override
  public void handle(MouseEvent t) {
    if (t.isConsumed()) {
      return;
    }

    if (t.getEventType() == MouseEvent.MOUSE_MOVED) {
      t.consume();
      setValue((double) (t.getX() * maxNumberOfStars) / (double) getWidth() + 0.5);
    } else if (t.getEventType() == MouseEvent.MOUSE_CLICKED) {
      valueAtClick = (double) (t.getX() * maxNumberOfStars) / (double) getWidth() + 0.5;
    } else if (t.getEventType() == MouseEvent.MOUSE_EXITED) {
      setValue(valueAtClick);
    }
  }

  /**
   * Cette fonction permet de 
   * @param value
   */
    
  public void setValue(double value) {
      
    double newValue = value;
    
    if (value < 0) {
      newValue = 0;
    }

    if (value > maxNumberOfStars) {
      newValue = maxNumberOfStars;
    }

    int i = 0;
   
    for (; i < Math.floor(newValue); i++) {
      stars.get(i).setType(StarView.StarTypes.FILLED);
    }

    if (Math.round(newValue) != Math.floor(newValue)) {
      stars.get(i++).setType(StarView.StarTypes.HALF);
    }

    //add complements empty stars
    for (; i < maxNumberOfStars; i++) {
      stars.get(i).setType(StarView.StarTypes.EMPTY);
    }
  }
  /* 
   * Permet de rendre l objet dynamique et autoriser les handlers. 
   * @param hoverable 
   */

  public void setHoverable(boolean hoverable) {
    if (hoverable) {
      addEventHandler(MouseEvent.MOUSE_MOVED, this);
      addEventHandler(MouseEvent.MOUSE_CLICKED, this);
      addEventHandler(MouseEvent.MOUSE_EXITED, this);
    } else {
      removeEventHandler(MouseEvent.MOUSE_MOVED, this);
      removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
      removeEventHandler(MouseEvent.MOUSE_EXITED, this);
    }
  }

  /**
   * Retourne la valeur selectionne lors du clic de souris
   * @return valueAtClick
   */
  public double getValueAtClick() {
    return valueAtClick;
  }

  /**
   * Reset la valeur de valueAtClick ^pour permettre une nouvelle selection.
   */
  public void reset() {
    setValue(0);
    valueAtClick = 0;
  }
}
