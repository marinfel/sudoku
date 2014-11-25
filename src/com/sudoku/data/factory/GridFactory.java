package com.sudoku.data.factory;

import com.sudoku.data.model.EmptyCell;
import com.sudoku.data.model.FixedCell;
import com.sudoku.data.model.Grid;
import java.util.Random;

import solver.Solver;
import solver.constraints.IntConstraintFactory;
import solver.search.strategy.IntStrategyFactory;
import solver.variables.IntVar;
import solver.variables.VariableFactory;

/**
 *
 * @author JE
 */
public class GridFactory {
	
	final static int n = 9;
    
    public static Grid generateRandomGrid(){
        return generateRandomGrid(0);
    }
    
    public static Grid generateRandomGrid(int nbEmptyCells){        
    	Grid g = new Grid();
    	byte x,y;
        Random r = new Random(System.currentTimeMillis());
        for(byte i = 1 ; i < 10 ; i++){
        	do{
    			x = (byte)r.nextInt(9);
    			y = (byte)r.nextInt(9);
    		}
    		while(!(g.getCell(x,y) instanceof EmptyCell));
        	g.setFixedCell((byte)r.nextInt(9),(byte)r.nextInt(9), i);
        }
        SolverObject solver = generateSolver(g);
        if (solver.solver.findSolution()) {
        	g = generateGrid(solver);
        	for(int i = 0; i < nbEmptyCells; i++){
        		do{
        			x = (byte)r.nextInt(9);
        			y = (byte)r.nextInt(9);
        		}
        		while((g.getCell(x,y) instanceof EmptyCell));
        		g.setEmptyCell(x,y);
            }
        } else {
        	g = null;
        }
        return g;
    }
    
    
    private static Grid generateGrid(SolverObject solver) {
    	Grid g = new Grid();
    	for (byte i = 0; i < n; i++) {
    		for (byte j = 0; j < n; j++) {
    			byte b = (byte) solver.rows[i][j].getValue();
    			g.setFixedCell(i, j, b);
    		}
    	}
    	return g;
	}

	public static SolverObject generateSolver(Grid grid) {
		
		SolverObject solver = new SolverObject();
    	solver.solver = new Solver("Sudoku Solver");
    	IntVar[][] cols, sectors;
        solver.rows = new IntVar[n][n];
        cols = new IntVar[n][n];
        sectors = new IntVar[n][n];
 
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.getCell(i, j) instanceof FixedCell) {
                	solver.rows[i][j] = VariableFactory.fixed(((FixedCell) grid.getCell(i, j)).getValue(), solver.solver);
                } else {
                	solver.rows[i][j] = VariableFactory.enumerated("c_" + i + "_" + j, 1, n, solver.solver);
                }
                cols[j][i] = solver.rows[i][j];
            }
        }
 
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    sectors[j + k * 3][i] = solver.rows[k * 3][i + j * 3];
                    sectors[j + k * 3][i + 3] = solver.rows[1 + k * 3][i + j * 3];
                    sectors[j + k * 3][i + 6] = solver.rows[2 + k * 3][i + j * 3];
                }
            }
        }
        
        for (int i = 0; i < 9; i++) {
            solver.solver.post(IntConstraintFactory.alldifferent(solver.rows[i], "AC"));
            solver.solver.post(IntConstraintFactory.alldifferent(cols[i], "AC"));
            solver.solver.post(IntConstraintFactory.alldifferent(sectors[i], "AC"));
        }
        
        IntVar[] row = new IntVar[n*n];
    	for (byte i = 0; i < n; i++) {
    		for (byte j = 0; j < n; j++) {
    			row[i*n+j] = solver.rows[i][j];
    		}
    	}
    	solver.solver.set(IntStrategyFactory.minDom_UB(row)); 
        return solver;
    }
	
	public static class SolverObject{
		public Solver solver;
		public IntVar[][] rows;
	}
}
