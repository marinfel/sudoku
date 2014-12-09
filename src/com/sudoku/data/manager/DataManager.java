/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.manager;
import com.sudoku.data.manager.GridManager;
import com.sudoku.data.model.User;
import java.io.File;
import java.io.IOException;
import org.apache.velocity.runtime.directive.Include;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL;

/**
 *
 * @author Jonathan
 */
public class DataManager {
    private static volatile DataManager instance = null;
    UserManager userMgr;
    GridManager gridMgr;
    AccessManager accMgr;
    private File jsonFile;
    private static final String jsonFilePath= "C:\\Sudoku\\Backup\\backup.json";
    
    private DataManager(){
        //Problème à la serialisation de grid manager au niveau de la fonction get lastplayed grid
        //sans solution pour le moment.
        
        gridMgr= GridManager.getInstance();
        userMgr=UserManager.getInstance();
        accMgr=AccessManager.getInstance();
        
    }
    private DataManager(DataManager DM){
        
    }
    public static DataManager getInstance(){
        if(instance==null){
            instance= new DataManager();
        }
        
    return instance;
    } 
    
      public void saveToJson(){
       ObjectMapper mapper = new ObjectMapper();
       //Pour sérializer les champs publics comme privés
       mapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
       //pour ne pas planter sur une valeur null
       mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
       mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
        
        try {
	            jsonFile = new File(jsonFilePath);
	 
	            mapper.writeValue(jsonFile, this);
                    //Pour logger le processus de sauvegarde
	            System.out.println(mapper.writeValueAsString(this));
        } catch (JsonGenerationException ex) {
                   ex.printStackTrace();
 
	} catch (JsonMappingException ex) {
	 
	            ex.printStackTrace();
        } catch (IOException ex) {
	 
	            ex.printStackTrace();
        }
    }
    public static DataManager buildFromJson(){
        ObjectMapper mapper= new ObjectMapper();
        // To avoid any undeclared property error
        //mapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIE‌​S , false);
            
        try {
	 
                File jsonFile = new File(jsonFilePath);
	 
                DataManager.instance = mapper.readValue(jsonFile, DataManager.class);
                
        }catch (JsonGenerationException ex) {
         
	 
	            ex.printStackTrace();
 
	} catch (JsonMappingException ex) {
	 
	            ex.printStackTrace();
	 
	} catch (IOException ex) {
	 
	            ex.printStackTrace();
	 
	}
        return DataManager.getInstance();
        
    }
}
