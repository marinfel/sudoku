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
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 *
 * @author Marc-Antoine MARTIN
 */
public class IhmGridLines extends GridPane implements EventHandler<IhmCellEditedEvent> {

    protected static final int cellNumberPerSide = 9;
    protected static final int cellNumberPerSubside = 3;

    protected static final int popupDelay = 10;

    protected int simpleLinesSide = 1;
    protected int doubleLinesSide = 2 * simpleLinesSide;
    protected int cellSide;
    protected int side;

    protected Rectangle lines[] = new Rectangle[(cellNumberPerSide - 1) * (cellNumberPerSide - 1)];
    protected IhmCell cells[][] = new IhmCell[cellNumberPerSide][cellNumberPerSide];

    public static int FIT_GRID = 0x1;
    public static int FIXED_HIDABLE = 0x2;
    public static int ALL_VIEW = 0x4;
    public static int ALL_EDITABLE = 0x8;

    /**
     * IHM_GridLines constructor
     *
     * @param grid: grid which contains cells to show
     * @param side is only on side in pixel of the grid which will be square
     * (TODO: @param grid is a grid which contains information about cells
     * (fixed, editables, values, etc.)
     * @param cellsFlag: How cells will be shown in the graphical interface
     * @throws NullPointerException if grid is null or an error occure when
     * parsing grid
     */
    public IhmGridLines(Grid grid, int side, int cellsFlag) {
        //if grid is null
        if (grid == null) {
            throw new NullPointerException();
        }

        //Init the side attribute
        this.side = side;

        //Calc number of simple lines on one side
        int nbSLines = ((cellNumberPerSide / cellNumberPerSubside) - 1) * cellNumberPerSubside;

        //Calc number of double lines on on side
        int nbDLines = cellNumberPerSide - nbSLines - 1;

        //Calc the side of one cell = side - sum_slines_sizes - sum_dlines_sizes
        cellSide = (int) ((side - (nbSLines * simpleLinesSide) - (nbDLines * doubleLinesSide)) / (double) cellNumberPerSide);
        int gridSide = (cellNumberPerSide - 1) + cellNumberPerSide;
        int gridSize = (int) Math.pow(gridSide, 2);

        //Draw Lines with empty spaces instead of cells
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

                if ((cell instanceof FixedCell && (cellsFlag & FIT_GRID) > 0) || (cellsFlag & ALL_VIEW) > 0) {
                    ihmCell = new IhmCellView(cellSide);
                    ihmCell.setValue(((FixedCell) cell).getValue());
                    if ((cellsFlag & FIXED_HIDABLE) > 0) {
                        ((IhmCellView) ihmCell).setHidable(true);
                    }
                } else {
                    ihmCell = new IhmCellEditable(cellSide);
                }
                //*/
                if (ihmCell == null) {
                    throw new NullPointerException();
                }

                //remove the 2 next lines and uncomment to previous lines
                //ihm_cell = new IhmCellEditable(cellSide);
                //ihm_cell.setValue(8);
                //set this list as a listener of a close request event on the pop-up
                if (ihmCell instanceof IhmCellEditable) {
                    ihmCell.addEventHandler(IhmCellEditedEvent.CELL_EDITED, this);
                }

                ihmCell.setX(X >> 1);
                ihmCell.setY(Y >> 1);

                add(ihmCell, X, Y);
                cells[X >> 1][Y >> 1] = ihmCell;

            } else if (X % 2 == 1 && Y % 2 == 0) {
                // Add a VLine
                Rectangle vline = new Rectangle();
                if (((X + 1) / 2) % cellNumberPerSubside == 0 && ((X + 1) / 2) % cellNumberPerSide > 0) // Double the size one in three to separate subsquares
                {
                    vline.setWidth(doubleLinesSide);
                } else {
                    vline.setWidth(simpleLinesSide);
                }

                vline.setHeight(cellSide);
                add(vline, X, Y);

            } else if (X % 2 == 0 && Y % 2 == 1) {
                // Add a HLine
                Rectangle hline = new Rectangle();
                if (((Y + 1) / 2) % cellNumberPerSubside == 0 && ((Y + 1) / 2) % cellNumberPerSide > 0) // Double the size one in three to separate subsquares
                {
                    hline.setHeight(doubleLinesSide);
                } else {
                    hline.setHeight(simpleLinesSide);
                }

                hline.setWidth(cellSide);
                add(hline, X, Y);
            } else {
                Rectangle intersectionRect = new Rectangle();
                if (((X + 1) / 2) % cellNumberPerSubside == 0 && ((X + 1) / 2) % cellNumberPerSide > 0) // Double the size one in three to separate subsquares
                {
                    intersectionRect.setWidth(2);
                } else {
                    intersectionRect.setWidth(1);
                }

                if (((Y + 1) / 2) % cellNumberPerSubside == 0 && ((Y + 1) / 2) % cellNumberPerSide > 0) // Double the size one in three to separate subsquares
                {
                    intersectionRect.setHeight(2);
                } else {
                    intersectionRect.setHeight(1);
                }

                add(intersectionRect, X, Y);
            }
        }
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
        }
    }

    private void checkCellValue(IhmCellEditable cell) {
        //check if the cell's value already exists in the row, the column or the sub-square
        //If so, add a popup to the popupList and delete the cell's value

        int gridSide = 2 * cellNumberPerSide - 1;

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
        int Xs = (int) ((double) X / (double) (2 * cellNumberPerSubside)) * 2 * cellNumberPerSubside;
        int Ys = (int) ((double) Y / (double) (2 * cellNumberPerSubside)) * 2 * cellNumberPerSubside;
        //System.out.println("sub-square (" + Xs + ", " + Ys + "):");
        boolean foundInSubSquare = false;
        for (int x = Xs; x < Xs + 2 * cellNumberPerSubside; x += 2) {
            for (int y = Ys; y < Ys + 2 * cellNumberPerSubside; y += 2) {
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
            //create pop-up(s)
            if (IhmPopupsList.getInstance() != null) {
                if (foundInRow) {
                    IhmPopupsList.getInstance().addPopup("Value Error!", cell.getValue() + " is already in the row.", popupDelay);
                }
                if (foundInColumn) {
                    IhmPopupsList.getInstance().addPopup("Value Error!", cell.getValue() + " is already in the column.", popupDelay);
                }
                if (foundInSubSquare) {
                    IhmPopupsList.getInstance().addPopup("Value Error!", cell.getValue() + " is already in the sub-square.", popupDelay);
                }
            }
            //clear the cell
            cell.setValue(0);
        }
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
}
