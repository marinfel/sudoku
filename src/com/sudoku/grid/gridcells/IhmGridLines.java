/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.gridcells;

import com.sudoku.data.model.Cell;
import com.sudoku.data.model.FixedCell;
import com.sudoku.data.model.Grid;
import com.sudoku.grid.popups.IhmPopupsList;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marc-Antoine MARTIN
 */
public class IhmGridLines extends GridPane implements EventHandler<IhmCellEditedEvent> {

  protected static final int CELL_NUMBER_PER_SIDE = 9;
  protected static final int CELL_NUMBER_PER_SUBSIDE = 3;
  protected static final int POPUP_DELAY = 10;
  public static final Flags FIT_GRID = Flags.resetAutoFlag();
  public static final Flags FIXED_HIDABLE = Flags.getAutoNextFlag();
  public static final Flags ALL_VIEW = Flags.getAutoNextFlag();
  public static final Flags ALL_EDITABLE = Flags.getAutoNextFlag();
  protected static final int SIMPLE_LINES_SIDE = 1;
  protected static final int DOUBLE_LINES_SIDE = 2 * SIMPLE_LINES_SIDE;

  protected Rectangle[] lines = new Rectangle[(CELL_NUMBER_PER_SIDE - 1) * (CELL_NUMBER_PER_SIDE - 1)];
  protected IhmCell[][] cells = new IhmCell[CELL_NUMBER_PER_SIDE][CELL_NUMBER_PER_SIDE];
  protected int cellSide;
  protected int side;
  private final List<IhmCellEditable> emtpyCellsEditable = new ArrayList<>();
  private IhmGridLinesCompleted ihmGridLinesCompleted;

  /**
   * IHM_GridLines constructor
   *
   * @param grid: grid which contains cells to show
   * @param side is only on side in pixel of the grid which will be square
   * (TODO: @param grid is a grid which contains information about cells (fixed,
   * editables, values, etc.)
   * @param cellsFlag: How cells will be shown in the graphical interface
   * @throws NullPointerException if grid is null or an error occure when
   * parsing grid
   */
  public IhmGridLines(Grid grid, int side, Flags cellsFlag) {
    //if grid is null
    if (grid == null) {
      throw new NullPointerException();
    }

    //Init the side attribute
    this.side = side;

    //Calc number of simple lines on one side
    int nbSLines = ((CELL_NUMBER_PER_SIDE / CELL_NUMBER_PER_SUBSIDE) - 1) * CELL_NUMBER_PER_SUBSIDE;

    //Calc number of double lines on on side
    int nbDLines = CELL_NUMBER_PER_SIDE - nbSLines - 1;

    //Calc the side of one cell = side - sum_slines_sizes - sum_dlines_sizes
    cellSide = (int) ((side - (nbSLines * SIMPLE_LINES_SIDE) - (nbDLines * DOUBLE_LINES_SIDE)) / (double) CELL_NUMBER_PER_SIDE);
    int gridSide = (CELL_NUMBER_PER_SIDE - 1) + CELL_NUMBER_PER_SIDE;
    int gridSize = (int) Math.pow(gridSide, 2);

    //Draw Lines with cells
    for (int i = 0; i < gridSize; i++) {
      int cellX = getColIndexFromArrayIndex(i, gridSide);
      int cellY = getRowIndexFromArrayIndex(i, gridSide);

      if (cellX % 2 == 0 && cellY % 2 == 0) {
        // Add a cell half a time
        IhmCell ihmCell = null;

        Cell cell = grid.getCell(cellX >> 1, cellY >> 1);
        if (cell == null) {
          throw new NullPointerException();
        }

        if ((cell instanceof FixedCell && cellsFlag.contains(FIT_GRID)) || cellsFlag.contains(ALL_VIEW)) {
          ihmCell = new IhmCellView(cellSide);
          if (cell instanceof FixedCell) {
            ihmCell.setValue(((FixedCell) cell).getValue());
          } else {
            ihmCell.setValue(0);
          }
          if (cellsFlag.contains(FIXED_HIDABLE)) {
            ((IhmCellView) ihmCell).setHideable(true);
          }
        } else {
          ihmCell = new IhmCellEditable(cellSide);
        }

        if (ihmCell == null) {
          throw new NullPointerException();
        }

        ihmCell.setX(cellX >> 1);
        ihmCell.setY(cellY >> 1);

        add(ihmCell, cellX, cellY);
        cells[cellX >> 1][cellY >> 1] = ihmCell;

        //set this list as a listener of a close request event on the pop-up
        if (ihmCell instanceof IhmCellEditable) {
          ihmCell.addEventHandler(IhmCellEditedEvent.CELL_EDITED, this);
          ihmCell.addEventHandler(IhmCellEditedEvent.CELL_MODIFIED, this);
          ihmCell.addEventHandler(IhmCellEditedEvent.UP_KEY_TYPED, this);
          ihmCell.addEventHandler(IhmCellEditedEvent.DOWN_KEY_TYPED, this);
          ihmCell.addEventHandler(IhmCellEditedEvent.LEFT_KEY_TYPED, this);
          ihmCell.addEventHandler(IhmCellEditedEvent.RIGHT_KEY_TYPED, this);
          emtpyCellsEditable.add((IhmCellEditable) ihmCell);
        }
      } else if (cellX % 2 == 1 && cellY % 2 == 0) {
        // Add a VLine
        Rectangle vline = new Rectangle();

        // Double the size one in three to separate subsquares
        if (((cellX + 1) / 2) % CELL_NUMBER_PER_SUBSIDE == 0 && ((cellX + 1) / 2) % CELL_NUMBER_PER_SIDE > 0) {
          vline.setWidth(DOUBLE_LINES_SIDE);
        } else {
          vline.setWidth(SIMPLE_LINES_SIDE);
        }

        vline.setHeight(cellSide);
        add(vline, cellX, cellY);

      } else if (cellX % 2 == 0 && cellY % 2 == 1) {
        // Add a HLine
        Rectangle hline = new Rectangle();

        // Double the size one in three to separate subsquares
        if (((cellY + 1) / 2) % CELL_NUMBER_PER_SUBSIDE == 0 && ((cellY + 1) / 2) % CELL_NUMBER_PER_SIDE > 0) {
          hline.setHeight(DOUBLE_LINES_SIDE);
        } else {
          hline.setHeight(SIMPLE_LINES_SIDE);
        }

        hline.setWidth(cellSide);
        add(hline, cellX, cellY);
      } else {
        Rectangle intersectionRect = new Rectangle();

        // Double the size one in three to separate subsquares
        if (((cellX + 1) / 2) % CELL_NUMBER_PER_SUBSIDE == 0 && ((cellX + 1) / 2) % CELL_NUMBER_PER_SIDE > 0) {
          intersectionRect.setWidth(2);
        } else {
          intersectionRect.setWidth(1);
        }

        // Double the size one in three to separate subsquares
        if (((cellY + 1) / 2) % CELL_NUMBER_PER_SUBSIDE == 0 && ((cellY + 1) / 2) % CELL_NUMBER_PER_SIDE > 0) {
          intersectionRect.setHeight(2);
        } else {
          intersectionRect.setHeight(1);
        }

        add(intersectionRect, cellX, cellY);
      }
    }

    ihmGridLinesCompleted = new IhmGridLinesCompleted(this, IhmGridLinesCompleted.GRID_COMPLETED);
  }

  public static int getColIndexFromArrayIndex(int index, int arraySide) {
    return index % arraySide;
  }

  public static int getRowIndexFromArrayIndex(int index, int arraySide) {
    return (int) ((double) index / (double) arraySide);
  }

  public static int getArrayIndexFromColRowIndex(int col, int row, int arraySide) {
    return (int) (row * arraySide) + col;
  }

  public IhmCell[][] getCells() {
    return cells;
  }

  @Override
  public void handle(IhmCellEditedEvent t) {
    if (t.isConsumed()) {
      return;
    }

    if (t.getEventType() == IhmCellEditedEvent.CELL_EDITED) {
      t.consume();
      checkCellValue(t.cell);
    } else if (t.getEventType() == IhmCellEditedEvent.CELL_MODIFIED) {
      //Cell has been modified but value may be invalid
      //We just add the cell to the array emtpyCellsEditable if it has been removed
      if (!emtpyCellsEditable.contains(t.cell)) {
        emtpyCellsEditable.add(t.cell);
      }
    } else if (t.getEventType() == IhmCellEditedEvent.UP_KEY_TYPED) {
      for (int y = t.cell.getY() - 1; y >= 0; y--) {
        if (cells[t.cell.getX()][y] instanceof IhmCellEditable) {
          ((IhmCellEditable) cells[t.cell.getX()][y]).setFocusOn();
          break;
        }
      }
    } else if (t.getEventType() == IhmCellEditedEvent.DOWN_KEY_TYPED) {
      for (int y = t.cell.getY() + 1; y < CELL_NUMBER_PER_SIDE; y++) {
        if (cells[t.cell.getX()][y] instanceof IhmCellEditable) {
          ((IhmCellEditable) cells[t.cell.getX()][y]).setFocusOn();
          break;
        }
      }
    } else if (t.getEventType() == IhmCellEditedEvent.LEFT_KEY_TYPED) {
      for (int x = t.cell.getX() - 1; x >= 0; x--) {
        if (cells[x][t.cell.getY()] instanceof IhmCellEditable) {
          ((IhmCellEditable) cells[x][t.cell.getY()]).setFocusOn();
          break;
        }
      }
    } else if (t.getEventType() == IhmCellEditedEvent.RIGHT_KEY_TYPED) {
      for (int x = t.cell.getX() + 1; x < CELL_NUMBER_PER_SIDE; x++) {
        if (cells[x][t.cell.getY()] instanceof IhmCellEditable) {
          ((IhmCellEditable) cells[x][t.cell.getY()]).setFocusOn();
          break;
        }
      }
    }
  }

  private void checkCellValue(IhmCellEditable cell) {
    //check if the cell's value already exists in the row, the column or the sub-square
    //If so, add a popup to the popupList and delete the cell's value

    int gridSide = 2 * CELL_NUMBER_PER_SIDE - 1;

    int index = this.getChildren().indexOf(cell);
    int cellX = getColIndexFromArrayIndex(index, gridSide);
    int cellY = getRowIndexFromArrayIndex(index, gridSide);

    //check row
    boolean foundInRow = false;
    for (int x = 0; x < gridSide; x += 2) {
      if (x % 2 == 0 && x != cellX) {
        IhmCell c = (IhmCell) getChildren().get(getArrayIndexFromColRowIndex(x, cellY, gridSide));

        if (c.getValue() == cell.getValue()) {
          foundInRow = true;
          break;
        }
      }
    }

    //check column
    boolean foundInColumn = false;
    for (int y = 0; y < gridSide; y += 2) {
      if (y % 2 == 0 && y != cellY) {
        IhmCell c = (IhmCell) getChildren().get(getArrayIndexFromColRowIndex(cellX, y, gridSide));

        if (c.getValue() == cell.getValue()) {
          foundInColumn = true;
          break;
        }
      }
    }

    //check sub-square
    int cellXs = (int) ((double) cellX / (double) (2 * CELL_NUMBER_PER_SUBSIDE)) * 2 * CELL_NUMBER_PER_SUBSIDE;
    int cellYs = (int) ((double) cellY / (double) (2 * CELL_NUMBER_PER_SUBSIDE)) * 2 * CELL_NUMBER_PER_SUBSIDE;

    boolean foundInSubSquare = false;
    for (int x = cellXs; x < cellXs + 2 * CELL_NUMBER_PER_SUBSIDE; x += 2) {
      for (int y = cellYs; y < cellYs + 2 * CELL_NUMBER_PER_SUBSIDE; y += 2) {
        if (x % 2 == 0 && y % 2 == 0 && (x != cellX || y != cellY)) {
          IhmCell c = (IhmCell) getChildren().get(getArrayIndexFromColRowIndex(x, y, gridSide));

          if (c.getValue() == cell.getValue()) {
            foundInSubSquare = true;
            break;
          }
        }
      }
      if (foundInSubSquare) {
        break;
      }
    }

    //apply what needed to be applied
    if (foundInRow || foundInColumn || foundInSubSquare) {
      //create pop-up(s) if cell is already in the Grid
      if (IhmPopupsList.getInstance() != null) {
        if (foundInRow) {
          IhmPopupsList.getInstance().addPopup("Value Error!", cell.getValue() + " is already in the row.", POPUP_DELAY);
        }
        if (foundInColumn) {
          IhmPopupsList.getInstance().addPopup("Value Error!", cell.getValue() + " is already in the column.", POPUP_DELAY);
        }
        if (foundInSubSquare) {
          IhmPopupsList.getInstance().addPopup("Value Error!", cell.getValue() + " is already in the sub-square.", POPUP_DELAY);
        }
      }
      //clear the cell
      cell.setValue(0);
    } else {
      //if cell is valid remove it from the array emtpyCellsEditable
      emtpyCellsEditable.remove(cell);
      if (emtpyCellsEditable.isEmpty()) {
        //Finish, user has completely filled the grid
        fireEvent(ihmGridLinesCompleted);
      }
    }
  }

  public static class Flags {

    private static long autoFlagValue = 0x1;
    private long flagValue = 0x0;

    private Flags(long flag) {
      this.flagValue = flag;
    }

    public static Flags getFlag(long flagValue) {
      return new Flags(flagValue);
    }

    public static Flags getAutoNextFlag() {
      Flags f = getFlag(autoFlagValue);
      autoFlagValue <<= 0x1;
      return f;
    }

    public static Flags resetAutoFlag() {
      return resetAutoFlag(0x1);
    }

    public static Flags resetAutoFlag(int flag) {
      autoFlagValue = flag;
      return getAutoNextFlag();
    }

    public Flags add(Flags flag) {
      return new Flags(flagValue | flag.flagValue);
    }

    public boolean contains(Flags flag) {
      return (flagValue & flag.flagValue) > 0;
    }
  }
}
