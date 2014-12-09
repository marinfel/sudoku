/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.ihm_grid_cells;

import com.sudoku.data.model.Cell;
import com.sudoku.data.model.FixedCell;
import com.sudoku.data.model.Grid;
import com.sudoku.grid.ihm_popups.IhmPopupsList;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

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

  protected Rectangle lines[] = new Rectangle[(CELL_NUMBER_PER_SIDE - 1) * (CELL_NUMBER_PER_SIDE - 1)];
  protected IhmCell cells[][] = new IhmCell[CELL_NUMBER_PER_SIDE][CELL_NUMBER_PER_SIDE];
  protected int cellSide;
  protected int side;
  private final ArrayList<IhmCellEditable> emtpyCellsEditable = new ArrayList<>();
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
      int X = getColIndexFromArrayIndex(i, gridSide);
      int Y = getRowIndexFromArrayIndex(i, gridSide);

      if (X % 2 == 0 && Y % 2 == 0) {
        // Add a cell half a time
        IhmCell ihmCell = null;

        //*
        Cell cell = grid.getCell(X >> 1, Y >> 1);
        if (cell == null) {
          throw new NullPointerException();
        }

        if ((cell instanceof FixedCell && cellsFlag.contains(FIT_GRID)) || cellsFlag.contains(ALL_VIEW)) {
          ihmCell = new IhmCellView(cellSide);
          ihmCell.setValue(((FixedCell) cell).getValue());
          if (cellsFlag.contains(FIXED_HIDABLE)) {
            ((IhmCellView) ihmCell).setHideable(true);
          }
        } else {
          ihmCell = new IhmCellEditable(cellSide);
        }
        //*/
        if (ihmCell == null) {
          throw new NullPointerException();
        }

        ihmCell.setX(X >> 1);
        ihmCell.setY(Y >> 1);

        add(ihmCell, X, Y);
        cells[X >> 1][Y >> 1] = ihmCell;

        //remove the 2 next lines and uncomment to previous lines
        //ihm_cell = new IhmCellEditable(cellSide);
        //ihm_cell.setValue(8);
        //set this list as a listener of a close request event on the pop-up
        if (ihmCell instanceof IhmCellEditable) {
          ihmCell.addEventHandler(IhmCellEditedEvent.CELL_EDITED, this);
          ihmCell.addEventHandler(IhmCellEditedEvent.CELL_MODIFIED, this);
          emtpyCellsEditable.add((IhmCellEditable) ihmCell);
        }
      } else if (X % 2 == 1 && Y % 2 == 0) {
        // Add a VLine
        Rectangle vline = new Rectangle();
        if (((X + 1) / 2) % CELL_NUMBER_PER_SUBSIDE == 0 && ((X + 1) / 2) % CELL_NUMBER_PER_SIDE > 0) // Double the size one in three to separate subsquares
        {
          vline.setWidth(DOUBLE_LINES_SIDE);
        } else {
          vline.setWidth(SIMPLE_LINES_SIDE);
        }

        vline.setHeight(cellSide);
        add(vline, X, Y);

      } else if (X % 2 == 0 && Y % 2 == 1) {
        // Add a HLine
        Rectangle hline = new Rectangle();
        if (((Y + 1) / 2) % CELL_NUMBER_PER_SUBSIDE == 0 && ((Y + 1) / 2) % CELL_NUMBER_PER_SIDE > 0) // Double the size one in three to separate subsquares
        {
          hline.setHeight(DOUBLE_LINES_SIDE);
        } else {
          hline.setHeight(SIMPLE_LINES_SIDE);
        }

        hline.setWidth(cellSide);
        add(hline, X, Y);
      } else {
        Rectangle intersectionRect = new Rectangle();
        if (((X + 1) / 2) % CELL_NUMBER_PER_SUBSIDE == 0 && ((X + 1) / 2) % CELL_NUMBER_PER_SIDE > 0) // Double the size one in three to separate subsquares
        {
          intersectionRect.setWidth(2);
        } else {
          intersectionRect.setWidth(1);
        }

        if (((Y + 1) / 2) % CELL_NUMBER_PER_SUBSIDE == 0 && ((Y + 1) / 2) % CELL_NUMBER_PER_SIDE > 0) // Double the size one in three to separate subsquares
        {
          intersectionRect.setHeight(2);
        } else {
          intersectionRect.setHeight(1);
        }

        add(intersectionRect, X, Y);
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
    }
  }

  private void checkCellValue(IhmCellEditable cell) {
    //check if the cell's value already exists in the row, the column or the sub-square
    //If so, add a popup to the popupList and delete the cell's value

    int gridSide = 2 * CELL_NUMBER_PER_SIDE - 1;

    int index = this.getChildren().indexOf(cell);
    int X = getColIndexFromArrayIndex(index, gridSide);
    int Y = getRowIndexFromArrayIndex(index, gridSide);
    /*System.out.println("cell(" + X + ", " + Y + "): " + getChildren().size());
     for(int i = 0; i < getChildren().size(); i++)
     System.out.println("(" + getRowIndexFromArrayIndex(i, gridSide) + ", " + getColIndexFromArrayIndex(i, gridSide) + "): " + getChildren().get(i).getClass().toString());
     */
    //System.out.println("checkCellValue(" + cell.getValue() + ")");

    //System.out.println("line:");
    //check row
    boolean foundInRow = false;
    for (int x = 0; x < gridSide; x += 2) {
      if (x % 2 == 0 && x != X) {
        //System.out.print("\t(" + x + ", " + Y + "): " + getArrayIndexFromColRowIndex(x, Y, gridSide) + ": ");
        IhmCell c = (IhmCell) getChildren().get(getArrayIndexFromColRowIndex(x, Y, gridSide));
        //System.out.println(c.getValue());
        if (c.getValue() == cell.getValue()) {
          foundInRow = true;
          break;
        }
      }
    }

    //check column
    //System.out.println("row:");
    boolean foundInColumn = false;
    for (int y = 0; y < gridSide; y += 2) {
      if (y % 2 == 0 && y != Y) {
        //System.out.print("\t(" + X + ", " + y + "): " + getArrayIndexFromColRowIndex(X, y, gridSide) + ": ");
        IhmCell c = (IhmCell) getChildren().get(getArrayIndexFromColRowIndex(X, y, gridSide));
        //System.out.println(c.getValue());
        if (c.getValue() == cell.getValue()) {
          foundInColumn = true;
          break;
        }
      }
    }

    //check sub-square
    int Xs = (int) ((double) X / (double) (2 * CELL_NUMBER_PER_SUBSIDE)) * 2 * CELL_NUMBER_PER_SUBSIDE;
    int Ys = (int) ((double) Y / (double) (2 * CELL_NUMBER_PER_SUBSIDE)) * 2 * CELL_NUMBER_PER_SUBSIDE;
    //System.out.println("sub-square (" + Xs + ", " + Ys + "):");
    boolean foundInSubSquare = false;
    for (int x = Xs; x < Xs + 2 * CELL_NUMBER_PER_SUBSIDE; x += 2) {
      for (int y = Ys; y < Ys + 2 * CELL_NUMBER_PER_SUBSIDE; y += 2) {
        if (x % 2 == 0 && y % 2 == 0 && (x != X || y != Y)) {
          //System.out.print("\t(" + x + ", " + y + "): ");
          IhmCell c = (IhmCell) getChildren().get(getArrayIndexFromColRowIndex(x, y, gridSide));
          //System.out.println(c.getValue());
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
      return ((flagValue & flag.flagValue) > 0);
    }
  }
}
