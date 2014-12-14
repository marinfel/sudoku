/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.gridcells;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * @author Marc-Antoine
 * @class IhmCellEditedEvent is an event fired when a cell is edited
 */
public class IhmCellEditedEvent extends Event {

  //CELL_EDITED a public constant to help to recognize this kind of event
  //it's fired when the value is valid
  public static final EventType<IhmCellEditedEvent> CELL_EDITED = new EventType("CELL_EDITED");

  //CELL_MODIFIED is like CELL_EDITED but is fired whenever the value
  public static final EventType<IhmCellEditedEvent> CELL_MODIFIED = new EventType("CELL_MODIFIED");

  //UP_KEY_TYPED is fired when the user press key up while editing the cell
  public static final EventType<IhmCellEditedEvent> UP_KEY_TYPED = new EventType("UP_KEY_TYPED");

  //UP_KEY_TYPED is fired when the user press key up while editing the cell
  public static final EventType<IhmCellEditedEvent> DOWN_KEY_TYPED = new EventType("DOWN_KEY_TYPED");

  //UP_KEY_TYPED is fired when the user press key up while editing the cell
  public static final EventType<IhmCellEditedEvent> LEFT_KEY_TYPED = new EventType("LEFT_KEY_TYPED");

  //UP_KEY_TYPED is fired when the user press key up while editing the cell
  public static final EventType<IhmCellEditedEvent> RIGHT_KEY_TYPED = new EventType("RIGHT_KEY_TYPED");

  //An attribut to access to the cell which fired the event 
  public final IhmCellEditable cell;

  /**
   * IhmCellEditedEvent constructor
   *
   * @param cell is the cell which fired the event
   */
  public IhmCellEditedEvent(IhmCellEditable cell, EventType<IhmCellEditedEvent> event) {
    super(event);

    //set the cell holds by the event
    this.cell = cell;
  }
}
