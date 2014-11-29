package com.sudoku.grid.manager;

import com.sudoku.grid.editor.IhmGridEditor;
import com.sudoku.grid.ihm_grid_player.IhmGridPlayer;
import com.sudoku.grid.ihm_grid_preview.IhmGridPreview;
import java.util.ArrayList;

public class IhmGrid {

  private static IhmGrid instance;

  /**
   * @explain: this is constraction function of class IhmGrid
   */
  private IhmGrid() {

  }

  /*
   * @explain: this is a function to get the instance of the class IHM_Grid, 
   *           or create an instance if there isn't any instance of this class
   */
  public static IhmGrid getInstance() {
    if (instance == null) {
      instance = new IhmGrid();
    }
    return instance;
  }

  /**
   *
   * @param createEmptyGrid
   * @return
   */
  public IhmGridEditor CreateGrid(boolean createEmptyGrid) {
    //TODO
    return null;
  }

  /**
   *
   * @return
   */
  public IhmGridPlayer playGrid() {
    //TODO
    return null;
  }

  /**
   *
   * @param keayword
   * @return
   */
  public ArrayList<IhmGridPreview> getGridList(ArrayList<String> keayword) {
    //TODO
    return null;
  }

  /**
   *
   * @return
   */
  public Manager getGridManager() {
    return null;
  }
}
