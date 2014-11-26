/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.ihm_popups;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 TODO: Labels don't use alignments given, why???
 */

/**
 * @author Marc-Antoine
 * @class IHM_Popup is a graphical object intem of a specific list (see
 * IHM_PopupList) The designe pattern Observer is used on this object
 */
public class IhmPopup extends GridPane {

  private final Timer timer = new Timer();
  private final TimeoutTimerTask timeoutTimerTask;
  private final IhmPopupCloseRequestEvent ihmPopupCloseRequestEvent;
  protected Label title = new Label();
  protected Label text = new Label("Here is the body...");
  protected int delay = 0;
  protected Label delayText = new Label(String.valueOf(delay) + " sec remaining before auto-closing");

  /**
   * @param title         is the main title
   * @param titleFontSize is the title font's size (often 12pt)
   * @param text          is the body text
   * @param textFontSize  is the body text font's size (often 10pt)
   * @param delay         is the showing delay, after this delay, the popup is removed
   *                      from the list
   * @class IHM_Popup constructor This constructor shouldn't be called by the
   * user, the object is automaticcally created by the list (see
   * IHM_PopupList)
   */
  public IhmPopup(double popupWidth, double popupHeight, String title, int titleFontSize, String text, int textFontSize, int delay) {
    //fix size
    this.setMaxSize(popupWidth, popupHeight);

    //Initialize the delay
    this.delay = delay;

    //Initilialize the title and add it to the layout
    this.title.setText(title);
    this.title.setMaxWidth(popupWidth);
    this.title.setMinWidth(popupWidth);
    this.title.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
    this.title.setStyle("-fx-font-size: " + titleFontSize + "pt; -fx-font-weight: bold; -fx-underline: true;");
    this.title.setAlignment(Pos.CENTER);
    this.add(this.title, 0, 0);

    //Initilialize the body text and add it to the layout
    this.text.setText(text);
    this.text.setMaxWidth(popupWidth);
    this.text.setMinWidth(popupWidth);
    this.text.setStyle("-fx-font-size: " + textFontSize + "pt;");
    this.text.setAlignment(Pos.TOP_LEFT);
    this.add(this.text, 0, 1);

    //Initilialize the delay text and add it to the layout
    this.delayText.setText(String.valueOf(delay) + " sec remaining before auto-closing");
    this.delayText.setMaxWidth(popupWidth);
    this.delayText.setMinWidth(popupWidth);
    this.delayText.setStyle("-fx-font-size: " + textFontSize + "pt; -fx-font-style: italic;");
    this.delayText.setAlignment(Pos.BOTTOM_RIGHT);
    this.add(this.delayText, 0, 2);

    //Set the listener when the user click on the pop-up (here it's calling a method wich handles the behavior
    setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent t) {
        mouseClickHandler(t);
      }

    });

    //Initialize the event fired when the popup need to be removed
    ihmPopupCloseRequestEvent = new IhmPopupCloseRequestEvent(this);

    //initialise the timer which decrease the showing time with a 1sec period
    timeoutTimerTask = new TimeoutTimerTask();
    timer.schedule(timeoutTimerTask, 0, 1000);
  }

  /**
   * Called when the object is destroyed
   */
  @Override
  public void finalize() {
    try {
      killTimer();
    } finally {
      try {
        super.finalize();
      } catch (Throwable ex) {
        Logger.getLogger(IhmPopup.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  /**
   * Set the behavior of a user click event on the widget, basically it
   * request to close the popup
   *
   * @param t is the mouse click event
   */
  protected void mouseClickHandler(MouseEvent t) {
    requestToClose();
  }

  public void killTimer() {
    timer.cancel();
  }

  /**
   * This method fires the event to notify all listener that the pop-up wants
   * itself to be removed
   */
  public void requestToClose() {
    killTimer();
    fireEvent(ihmPopupCloseRequestEvent);
  }

  /**
   * Method which decrease the showing time. It's call at each timeout of the
   * 1sec period timer
   */
  private void decreaseDelay() {
    if (delay > 0) {
      delay--;
      delayText.setText(String.valueOf(delay) + " sec remaining before auto-closing");
    } else {
      requestToClose();
    }
  }

  /**
   * @class TimeoutTimerTask behaves at each 1sec period timer timeout
   */
  private class TimeoutTimerTask extends TimerTask {

    /**
     * this method is automaticcaly called at each 1sec preiod timer timeout
     */
    @Override
    public void run() {
            /*We call the following method because we are in a detached thread
             compared to the User Interface JavaFX thread and our behaviour will
             affect the User Interface. To be able to do that, we need to signal
             it to JavaFX.*/
      Platform.runLater(new Runnable() {

        /**
         * Method run when JavaFX allows a detached thread to modify the
         * User Interface.
         */
        @Override
        public void run() {
          //Here we call the decrease delay method (see above)
          decreaseDelay();
        }

      });
    }
  }
}
