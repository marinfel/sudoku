package com.sudoku.ihm_grid.ihm_grid_cells;
import javafx.scene.text.Text;

/**
 * @class IHM_CellView
 * this is a class to afficher the chiffer in a cell
 * @author 
 */
public class IHM_CellView extends IHM_Cell 
{
    protected Text valueView;
    protected boolean hidden;
    
    public IHM_CellView()
    {
        // Init hidden attribut at false
        hidden = false; 
        
        /*init valueView attribut and add it to the layout*/
        valueView = new Text();
        setConstraints(valueView,0,0);
        getChildren().add(valueView);
        System.out.println("IHM_CellView()");
    }

    @Override
    public void setValue(int value)
    {
        //first check the value
        if(checkValue(value))
            //set the value
            valueView.setText(String.valueOf(value));
        
    }
    
}
