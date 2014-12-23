package com.sudoku.data.manager;

import com.sudoku.comm.CommunicationManager;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.PlayedGrid;
import com.sudoku.data.model.Tag;
import com.sudoku.data.model.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileSystemView;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonAutoDetect;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public final class GridManager {
  private static volatile GridManager instance = null;
  private List<Grid> availableGrids;
  private List<PlayedGrid> playedGrids;

  @JsonIgnore
  private File jsonFile;
  @JsonIgnore
  private  String jsonFilePath;

  private GridManager() {
    this.availableGrids = new ArrayList<>();
    this.playedGrids = new ArrayList<>();
    // path vers le fichier de sauvegarde
    jsonFilePath = System.getProperty("user.home").concat("\\LO23Sudoku\\Backup\\");
  }

  public final static GridManager getInstance() {
    if (instance == null) {
      synchronized (GridManager.class) {
        if (instance == null) {
          instance = new GridManager();
        }
      }
    }
    return instance;
  }

  public void save() { // Serialize and save grids

    // TO BE COMPLETED

  }

  public boolean addGrid(Grid grid) {
    return availableGrids.add(grid);
  }

  public boolean addPlayedGrid(Grid grid, User player) {
    return playedGrids.add(new PlayedGrid(grid, player));
  }


  public boolean addPlayedGrid(PlayedGrid pgrid) {
    return playedGrids.add(pgrid);
  }

  public boolean removeGrid(Grid grid) {
    return availableGrids.remove(grid);
  }

  public List<Grid> filterGridsByTags(List<Tag> tags){
    List<Grid> grids = new ArrayList<>();
    for(Grid g : this.availableGrids){
      boolean filtered = false;
      for(Tag t : tags){
        if(g.hasTag(t)) filtered = true;
      }
      if(filtered) grids.add(g);
    }
    return grids;
  }

  public List<Grid> filterGridsByGrade(int grade){
    List<Grid> grids = new ArrayList<>();
    for(Grid g : this.availableGrids){
      if(g.getAverageGrade() == grade){
        grids.add(g);
      }
    }
    return grids;
  }

  public boolean updateGridList() {
    try {
		for(Grid g : CommunicationManager.getInstance().getAllGrids()){
			if(!availableGrids.contains(g)){
				availableGrids.add(g);
			} else {
				for(Grid grid : availableGrids){
					if(g.equals(grid)){
						grid.setComments(g.getComments());
					}
				}
			}
		}
		return true;
	} catch (IOException e) {
		e.printStackTrace();
	}
    return false;
  }
  @JsonIgnore
  public Grid getLatestPlayedGrid() {
    Grid g = availableGrids.get(0);
    for (Grid grid : availableGrids) {
      if (grid.getUpdateDate().after(g.getUpdateDate())) {
        g = grid;
      }
    }
    return g;
  }

  public List<PlayedGrid> getFinishedGrid() {
    List<PlayedGrid> result = new ArrayList<PlayedGrid>();
    for (PlayedGrid grid : playedGrids) {
      if (grid.isComplete())
        result.add(grid);
    }
    return result;
  }

  public List<PlayedGrid> getIncompleteGrid() {
    List<PlayedGrid> result = new ArrayList<PlayedGrid>();
    for (PlayedGrid grid : playedGrids) {
      if (!grid.isComplete())
        result.add(grid);
    }
    return result;
  }

  public Grid getGridById(UUID uuid) {
    for (Grid grid : availableGrids) {
      if (grid.getId() == uuid) {
        return grid;
      }
    }
    return null;
  }

  public List<Grid> getAvailableGrids() {
    return availableGrids;
  }
  
  public List<Grid> getUserGrids(User user) {
      List<Grid> usersGrids = new ArrayList<>();
      
      for(Grid g : this.availableGrids){
          if(g.getCreateUser() == user){
              usersGrids.add(g);
          }
      }
      
      return usersGrids;
  }
  
  public List<PlayedGrid> getUserPlayedGrids(User user) {
	  List<PlayedGrid> usersPlayedGrids = new ArrayList<PlayedGrid>();
      
      for(PlayedGrid g : this.playedGrids){
          if(g.getPlayer() == user){
              usersPlayedGrids.add(g);
          }
      }
      
      return usersPlayedGrids;
	}
  
  //for deserialisation use only
  public List<PlayedGrid> getPlayedGrids(){
      return playedGrids;
  }
  public void SaveToJson(){
  ObjectMapper mapper = new ObjectMapper();
       //Pour sérializer les champs publics comme privés
       mapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
       //pour ne pas planter sur une valeur null
       //mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
       //mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
        
        try{  
                System.out.println(jsonFilePath.concat("backupGridManager.json"));
                jsonFile = new File(jsonFilePath.concat("backupGridManager.json"));
           
                mapper.writeValue(jsonFile, this);
                    //Pour logger le processus de sauvegarde
	        //System.out.println(mapper.writeValueAsString(this));
        } catch (JsonGenerationException ex) {
                   ex.printStackTrace();
 
	} catch (JsonMappingException ex) {
	 
	            ex.printStackTrace();
        }
        catch (FileNotFoundException e){
      try {
          
          File tmpFile=new File(jsonFilePath);
          
          tmpFile.mkdirs();
          jsonFile = new File(jsonFilePath.concat("backupGridManager.json"));
          mapper.writeValue(jsonFile, this);
          //Pour logger le processus de sauvegarde
          System.out.println(mapper.writeValueAsString(this));
      } catch (IOException ex) {
          Logger.getLogger(GridManager.class.getName()).log(Level.SEVERE, null, ex);
      }
        }
        catch (IOException ex) {
	 
	            ex.printStackTrace();
        }
        
    }
  public static GridManager BuildFromJson(){
  ObjectMapper mapper= new ObjectMapper();
        // To avoid any undeclared property error
        //mapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIE‌​S , false);
            
        try {
	       String jsonFilePath = System.getProperty("user.home").concat("\\LO23Sudoku\\Backup\\");
 
                File jsonFile = new File(jsonFilePath.concat("backupGridManager.json"));
           
	 
                //DataManager.instance = mapper.readValue(jsonFile, DataManager.class);
                GridManager.instance = mapper.readValue(jsonFile, GridManager.class);
        }catch (JsonGenerationException ex) {
         
	 
	            ex.printStackTrace();
 
	} catch (JsonMappingException ex) {
	 
	            ex.printStackTrace();
	 
	} catch (IOException ex) {
	 
	            ex.printStackTrace();
	 
	}
        return GridManager.getInstance();
  
  }
}
