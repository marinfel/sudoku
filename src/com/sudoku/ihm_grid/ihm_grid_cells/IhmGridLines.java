/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.ihm_grid.ihm_grid_cells;

import com.sudoku.data.model.Grid;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Marc-Antoine MARTIN
 */
public class IhmGridLines extends GridPane {

    protected static final int cellNumberPerSide = 9;
    protected static final int cellNumberPerSubside = 3;

    protected int simpleLinesSide = 1;
    protected int doubleLinesSide = 2 * simpleLinesSide;
    protected int cellSide;
    protected int side;

    protected Rectangle lines[] = new Rectangle[(cellNumberPerSide - 1) * (cellNumberPerSide - 1)];
    protected IhmCell cells[] = new IhmCell[cellNumberPerSide * cellNumberPerSide];

    /**
     * IHM_GridLines constructor
     *
     * @param side is only on side in pixel of the grid which will be square
     * (TODO: @param grid is a grid which contains information about cells
     * (fixed, editables, values, etc.)
     */
    public IhmGridLines(Grid grid, int side) {
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
                //TODO: utiliser <grid> pour former la grid en fonction des cellules quelle contient
                IhmCellView cell = new IhmCellView(8, 20, cellSide, cellSide);
                add(cell, X, Y);

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
            }
        }
    }

    public static int getColIndexFromArrayIndex(int index, int arraySide) {
        return index % arraySide;
    }

    public static int getRowIndexFromArrayIndex(int index, int arraySide) {
        return (int) (index / arraySide);
    }

    public static int getArrayIndexFromColRowIndex(int col, int row, int arraySide) {
        return (int) (row * arraySide) + col;
    }
}
