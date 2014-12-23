/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sudoku.main.view;

import com.sudoku.grid.editor.IhmGridEditorRandomlyFilled;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.stage.WindowEvent;

/**
 * @author MOURAD
 */
public class FromFullGridController implements Initializable, ControlledScreen {

  ScreensController myController;
  @FXML
  private Pane fromFullPane;
  @FXML
  private ScrollPane GridEditorAuto;
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
  }


  @Override
  public void setScreenParents(ScreensController screenParent) {
    myController = screenParent;
    myController.addEventHandler(WindowEvent.WINDOW_SHOWING ,new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent e) {
                GridEditorAuto.setContent((Node)new IhmGridEditorRandomlyFilled());
            }
      });
  }        

  @FXML
  private void goToProgram(ActionEvent event) {
    myController.setScreen(SudukoIHM.programID);
  }
}
