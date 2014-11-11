/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

/**
 *
 * @author mecton08
 */
public class IhmGridEditorManuallyFilled extends IhmGridEditor{
    
    public IhmGridEditorManuallyFilled(String title){
        super(title);
        
        super.getValidBtn().setOnAction(new EventHandler<ActionEvent>() {    
            @Override
            public void handle(ActionEvent event) {                
                VBox gridLines = (VBox)border.getCenter();
                for(int i = 0; i < 81; i++){
                    //Fonction getCells a implementer, renvoie le tableau de IhmCellEditable
                    //grid.setFixedCell( i/9, i%9, gridLines.getCells()[i].getValue() );
                }
            }
        });
    }
    
    
 
}
