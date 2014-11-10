/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.ihm_grid_cells;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * @class IhmCellEditedEvent is an event fired when a cell is edited
 *
 * @author Marc-Antoine
 */
public class IhmCellEditedEvent extends Event {
    //CELL_EDITED a public constant to help to recognize this kind of event
    public static final EventType<IhmCellEditedEvent> CELL_EDITED = new EventType("CELL_EDITED");

    //An attribut to access to the cell which fired the event 
    public final IhmCellEditable cell;

    /**
     * IhmCellEditedEvent constructor
     *
     * @param cell is the cell which fired the event
     */
    public IhmCellEditedEvent(IhmCellEditable cell) {
        super(CELL_EDITED);

        //set the cell holds by the event
        this.cell = cell;
    }
}
