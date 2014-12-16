/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.ihm_popups;

import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marc-Antoine
 * @class IHM_PopupsList is a graphical object which is unique. The design
 * pattern singleton is used on this object.
 */
public class IhmPopupsList extends VBox implements EventHandler<IhmPopupCloseRequestEvent> {

  protected static final int TITLE_FONT_SIZE = 12;
  protected static final int TEXT_FONT_SIZE = 10;

  private static IhmPopupsList instance = null;
  protected double popupHeight;
  protected double popupWidth;
  protected int nbMaxPopups;
  protected ArrayList<IhmPopup> popups = new ArrayList<>();

  /**
   * @class IHM_PopupsList constructor Use IHM_PopupsList.getInstance() instead.
   */
  private IhmPopupsList(double width, double height, int nbMaxPopus) {
    //set the maximum number of pop-ups that the list can hold
    this.nbMaxPopups = nbMaxPopus;

    //define the height of each pop-up. -1 is here because we add a line of one pixel above each pop-up as separator
    popupHeight = height / (double) nbMaxPopus - 1;

    //define the width of each pop-up which is the same as the width defined by the user for the entire list
    popupWidth = width;

    //set max size of the graphical object list
    setMaxSize(width, height);
  }

  /**
   * @param height is the max height of this graphical object
   * @param width is the max width of this graphical object
   * @param nbMaxPopus is the max number of popup shown in the list. It will
   * determine the height of each pop-ups
   * @class IHM_PopupsList initializer, you must call it before calling
   * getInstance();
   */
  public static void init(double width, double height, int nbMaxPopus) {
    //If the instance doesn't exist, we create it
    if (instance == null) {
      instance = new IhmPopupsList(width, height, nbMaxPopus);
    }
  }

  /**
   * @return an instance of IhmPopupsList. If the instance hasn't been
   * instanciated, return null;
   * @throws NullPointerException if the list hasn't been initialised
   * @class IHM_PopupsList getter, you must call init() before.
   */
  public static IhmPopupsList getInstance() {
    //return the instance created (if not created, return null)
    if (instance == null) {
      throw new NullPointerException();
    }
    return instance;
  }

  /**
   * Finalize the list by killing timers of its popups Needed to be call when
   * the application is quit (ie: Stage.setOnCloseRequest(handler which calls
   * IhmPopupsList.finalize())
   */
  @Override
  public void finalize() {
    try {
      for (IhmPopup popup : popups) //Kill the timer otherwise there will continue to run
      //Can't use the requestToClose() function because it will modify the list that we are using
      {
        popup.finalize();
      }
    } finally {
      try {
        super.finalize();
      } catch (Throwable ex) {
        Logger.getLogger(IhmPopupsList.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  /**
   * Used to create a new popups with parameters given and return it to the
   * user. The popup is automatically added to the list.
   *
   * @param title is the pop-up's title
   * @param text is the pop-up's body text
   * @param delay is the pop-up's showing delay
   * @return the instance of the popup created
   */
  public IhmPopup addPopup(String title, String text, int delay) {
    //Create a new pop-up
    IhmPopup popup = new IhmPopup(popupWidth, popupHeight, title, TITLE_FONT_SIZE, text, TEXT_FONT_SIZE, delay);

    //set this list as a listener of a close request event on the pop-up
    popup.addEventHandler(IhmPopupCloseRequestEvent.POPUP_CLOSEREQUEST, this);

    //add the popup to the pop-ups list
    popups.add(popup);

    //If there is no enough room, the oldest popup
    if ((getChildren().size() >> 1) >= nbMaxPopups) {
      ((IhmPopup) getChildren().get(getChildren().size() - 2)).requestToClose();
    }

    //create a line as a separator between 2 pop-up in the list
    Line line = new Line(0, 0, popupWidth, 0);

    //add the pop-up and the line-separator to the layout on the top of the list
    getChildren().add(0, popup);
    getChildren().add(1, line);

    return popup;
  }

  /**
   * Cannot be called directly from this class. Should call requestToClose()
   * method of IhmPopup object instead.
   *
   * @param popup is the popup which will be deleted from the list
   */
  private void deletePopup(IhmPopup popup) {
    //get the index of the popup in the layout
    int index = getChildren().indexOf(popup);

    //remove the popup and it's above line-separator from the layout
    getChildren().remove(index, index + 2);

    //remove popup from the list
    popups.remove(popup);
  }

  /**
   * Method which handle a IhmPopupCloseRequestEvent event. When received, it
   * calls the method to remove the popup attached.
   *
   * @param t is the event wich holds the popup to remove
   */
  @Override
  public void handle(IhmPopupCloseRequestEvent t) {
    if (t.isConsumed()) {
      return;
    }

    if (t.getEventType() == IhmPopupCloseRequestEvent.POPUP_CLOSEREQUEST) {
      t.consume();
      deletePopup(t.popup);
    }
  }
}
