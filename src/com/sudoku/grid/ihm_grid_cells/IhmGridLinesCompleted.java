/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.ihm_grid_cells;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * @author Marc-Antoine
 * @class IhmCellLinesCompleted is an event fired when a grid is completed
 */
public class IhmGridLinesCompleted extends Event {

  //GRID_COMPLETED a public constant to help to recognize this kind of event
  //it's fired when the grid has been completely filled (so it's valid)
  public static final EventType<IhmGridLinesCompleted> GRID_COMPLETED = new EventType("GRID_COMPLETED");

  //An attribut to access to the grid which fired the event
  public final IhmGridLines grid;

  /**
   * IhmCellLinesCompleted constructor
   *
   * @param grid is the grid which fired the event
   */
  public IhmGridLinesCompleted(IhmGridLines grid, EventType<IhmGridLinesCompleted> event) {
    super(event);

    //set the grid holds by the event
    this.grid = grid;
  }
}
