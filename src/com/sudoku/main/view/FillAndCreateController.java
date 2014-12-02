/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sudoku.main.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author MOURAD
 */
public class FillAndCreateController implements Initializable, ControlledScreen {

  // Partie JulianC
  ScreensController myController;


  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }


  @Override
  public void setScreenParents(ScreensController screenParent) {
    myController = screenParent;
  }

  @FXML
  private void goToProgram(ActionEvent event) {
    myController.setScreen(SudukoIHM.programID);
  }
}
