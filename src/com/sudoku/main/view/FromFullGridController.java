/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sudoku.main.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

/**
 *
 * @author MOURAD
 */
public class FromFullGridController implements Initializable, ControlledScreen {
    
    @FXML   private Pane fromFullPane;
    // Partie JulianC
    ScreensController myController;    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //IhmGridEditor editor = IhmGrid.getInstance().createGrid(true);
        //add editor to fromFullPane
    }      


    @Override
    public void setScreenParents(ScreensController screenParent) {
        myController = screenParent;
    }
    
    @FXML
    private void goToProgram(ActionEvent event) {   
        System.out.println("Ca clique bordel ???");
        myController.setScreen(SudukoIHM.programID);                
    }    
}
