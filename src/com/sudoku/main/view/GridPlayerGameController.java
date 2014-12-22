/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.main.view;

import com.sudoku.grid.gridcells.IhmGridLinesCompleted;
import com.sudoku.grid.player.IhmGridPlayer;
import com.sudoku.grid.popups.IhmPopupsList;
import com.sudoku.main.manager.RefreshGridPlayer;
import com.sudoku.data.manager.GridManager;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.Cell;
import com.sudoku.grid.gridcells.IhmCell;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author MOURAD
 */
public class GridPlayerGameController implements Initializable, ControlledScreen,EventHandler<IhmGridLinesCompleted>{
    
    ScreensController myController;
    IhmGridPlayer GridP;
    
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
                          //IhmPopupsList.getInstance().killAllTimers();
                          GridP = new IhmGridPlayer(instance.getCurrentGrid());
                          GridPlayerContainer.setContent(GridP);
                          GridP.addEventHandler(IhmGridLinesCompleted.GRID_COMPLETED, gridPlayerGameController);
                    }
                }
          });
    }
    
    @FXML
    private void goToProgram(ActionEvent event) {
        Grid currentGrid = RefreshGridPlayer.getInstance().getCurrentGrid();
        if(currentGrid != null)
            GridManager.getInstance().addPlayedGrid(currentGrid,currentGrid.getCreateUser());
        myController.setScreen(SudukoIHM.programID);
    }

    @Override
    public void handle(IhmGridLinesCompleted t) {
        IhmGridLinesCompleted instance = t;
        if(t.isConsumed())
        {
            return ;
        }

        if(t.getEventType()==IhmGridLinesCompleted.GRID_COMPLETED)
        {
            t.consume();
            Grid currentGrid = RefreshGridPlayer.getInstance().getCurrentGrid();
            Grid finishedGrid = copyGrid(currentGrid,instance);
            GridManager.getInstance().addPlayedGrid(finishedGrid,finishedGrid.getCreateUser());
            myController.setScreen(SudukoIHM.programID);
        }
    }
    
    public Cell[][] updateCells(IhmGridLinesCompleted t)
    {
        Cell[][] UpdatedCells=new Cell[9][9];
        IhmCell[][] currentState=t.grid.getCells();
        int X,Y,value;
        byte xByte,yByte,valueByte;
        int i2,y2;
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                UpdatedCells[i][j] = new Cell();
                X=currentState[i][j].getX();
                Y=currentState[i][j].getY();
                value = currentState[i][j].getValue();
                xByte = (byte) X;
                yByte = (byte) Y;
                valueByte= (byte) value;
                UpdatedCells[i][j].setX( xByte);
                UpdatedCells[i][j].setY( yByte);
                UpdatedCells[i][j].setValue(valueByte);
                /*i2 = UpdatedCells[i][j].getValue() & 0xFF;
                System.out.println(valueByte);*/
            }
        }
        return UpdatedCells;
    }
    
    public Grid copyGrid(Grid g,IhmGridLinesCompleted completed)
    {
        Grid newInstance = new Grid(g.getTitle(), g.getCreateUser());
        newInstance.setComments(g.getComments());
        newInstance.setCreateDate((Timestamp) g.getCreateDate());
        newInstance.setDifficulty(g.getDifficulty());
        newInstance.setGrid(updateCells(completed));
        newInstance.setTags(g.getTags());
        return newInstance;
    }
}
