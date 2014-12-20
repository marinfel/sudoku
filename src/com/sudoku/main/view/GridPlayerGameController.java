/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.main.view;

import com.sudoku.grid.editor.IhmGridEditorRandomlyFilled;
import com.sudoku.grid.gridcells.IhmGridLinesCompleted;
import com.sudoku.grid.player.IhmGridPlayer;
import com.sudoku.grid.popups.IhmPopupsList;
import com.sudoku.main.manager.RefreshGridPlayer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author MOURAD
 */
public class GridPlayerGameController implements Initializable, ControlledScreen,EventHandler<IhmGridLinesCompleted>{
    
    ScreensController myController;
    
    @FXML
    private ScrollPane GridPlayerContainer;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @Override
    public void setScreenParents(ScreensController screenParent) {
        myController = screenParent;
        final GridPlayerGameController gridPlayerGameController = this;
        myController.addEventHandler(WindowEvent.WINDOW_SHOWING ,new EventHandler<WindowEvent>() {
                @Override public void handle(WindowEvent e) {
                    RefreshGridPlayer instance = RefreshGridPlayer.getInstance();
                    if(instance.getCurrentGrid() != null)
                    {
                        if(instance.getCurrentGrid()!=null)
                        {
                            IhmPopupsList.getInstance().killAllTimers();
                          IhmGridPlayer GridP = new IhmGridPlayer(instance.getCurrentGrid());
                          GridPlayerContainer.setContent(GridP);
                          GridP.addEventHandler(IhmGridLinesCompleted.GRID_COMPLETED, gridPlayerGameController);
                        }
                    }
                }
          });
    }
    
    @FXML
    private void goToProgram(ActionEvent event) {
      myController.setScreen(SudukoIHM.programID);
    }

    @Override
    public void handle(IhmGridLinesCompleted t) {
        if(t.isConsumed())
        {
            return ;
        }

        if(t.getEventType()==IhmGridLinesCompleted.GRID_COMPLETED)
        {
            t.consume();
            System.out.println("Biiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiim--------");
        }
    }
    
}
