/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.preview;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 *
 * @author Marc-Antoine
 */
public class StarsBox extends HBox implements EventHandler<MouseEvent> {

  protected final int maxNumberOfStars;
  protected ArrayList<StarView> stars = new ArrayList<>();
  protected double valueAtClick = 0;

  public StarsBox(int numberOfStars) {
    maxNumberOfStars = numberOfStars;
    stars.ensureCapacity(maxNumberOfStars);

    //Ajoute une étoile jaune (remplie)
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

  public void setValue(double value) {
    if (value < 0) {
      value = 0;
    }

    if (value > maxNumberOfStars) {
      value = maxNumberOfStars;
    }

    int i = 0;
    //add yallow stars
    for (; i < Math.floor(value); i++) {
      stars.get(i).setType(StarView.StarTypes.FILLED);
    }

    if (Math.round(value) != Math.floor(value)) {
      stars.get(i++).setType(StarView.StarTypes.HALF);
    }

    //add complements empty stars
    for (; i < maxNumberOfStars; i++) {
      stars.get(i).setType(StarView.StarTypes.EMPTY);
    }
  }

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

  public double getValueAtClick() {
    return valueAtClick;
  }

  public void reset() {
    setValue(0);
    valueAtClick = 0;
  }
}
