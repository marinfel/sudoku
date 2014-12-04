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
import javafx.stage.WindowEvent;

/**
 * @author MOURAD
 */
public class FromFullGridController implements Initializable, ControlledScreen {

  // Partie JulianC
  ScreensController myController;
  @FXML
  private Pane fromFullPane;
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
        IhmGridEditorRandomlyFilled editor = new IhmGridEditorRandomlyFilled(); 
        fromFullPane.getChildren().add((Node)editor);
  }


  @Override
  public void setScreenParents(ScreensController screenParent) {
    myController = screenParent;
    myController.addEventHandler(WindowEvent.WINDOW_SHOWING ,new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent e) {
                System.out.println("event cought : "+e.getEventType().toString());
                fromFullPane.getChildren().removeAll();
                fromFullPane.getChildren().add((Node)new IhmGridEditorRandomlyFilled());
            }
      });
  }        

  @FXML
  private void goToProgram(ActionEvent event) {
    myController.setScreen(SudukoIHM.programID);
  }
}
