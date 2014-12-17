/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.manager;

import com.sudoku.data.model.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import com.sudoku.util.*;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.TypeFactory;
        

/**
 * @author JE
 */
public class AccessManager {

  private static AccessManager instance;
  private HashMap<Grid, ArrayList<AccessRule>> rules;
  
  @JsonIgnore
  private File jsonFile;
  @JsonIgnore
  private String jsonFilePath;
    
  private AccessManager() {
    this.rules = new HashMap<>();
    jsonFilePath = System.getProperty("user.home").concat("\\LO23Sudoku\\Backup\\");
 
  }

  public static AccessManager getInstance() {
    if (instance == null) {
      instance = new AccessManager();
    }
    return instance;
  }

  /**
   * @return the rules
   */
  public HashMap<Grid, ArrayList<AccessRule>> getRules() {
    return rules;
  }

  //jsonignore
  @JsonIgnore
  public ArrayList<AccessRule> getAllAccessRulesForGrid(Grid grid) {
    return this.rules.get(grid);
  }

  //jsonignore
  @JsonIgnore
  private AccessRule getAccessRule(Grid grid, AccessAction accessAction, Ruleable appliedTo) {

    ArrayList<AccessRule> rulesList = this.rules.get(grid);
    if (rulesList == null) {
      return null;
    }

    for (AccessRule ar : rulesList) {
      if (ar.getAccessAction() == accessAction && ar.getAppliedTo().equals(appliedTo)) {
        return ar;
      }
    }
    return null;
  }

  /**
   * @param rules the rules to set
   */
  public void setRules(HashMap<Grid, ArrayList<AccessRule>> rules) {
    this.rules = rules;
  }

  public void setAccessRule(Grid grid, AccessType accessType, AccessAction accessAction, Ruleable appliedTo) {
    AccessRule oldAccessRule = this.getAccessRule(grid, accessAction, appliedTo);
    if (oldAccessRule == null) {
      this.addAccessRule(grid, accessType, accessAction, appliedTo);
    } else {
      oldAccessRule.setAccessType(accessType);
    }
  }

  public void setAccessRule(Grid grid, AccessRule accessRule) {
    setAccessRule(grid, accessRule.getAccessType(), accessRule.getAccessAction(), accessRule.getAppliedTo());
  }

  public void addAccessRule(Grid grid, AccessType accessType, AccessAction accessAction, Ruleable appliedTo) {
    if (this.rules.containsKey(grid)) {
      AccessRule oldAccessRule = this.getAccessRule(grid, accessAction, appliedTo);
      if (oldAccessRule != null) {
        oldAccessRule.setAccessType(accessType);
      } else {
        this.rules.get(grid).add(new AccessRule(accessType, accessAction, appliedTo));
      }
    } else {
      ArrayList<AccessRule> newRuleList = new ArrayList<>();
      newRuleList.add(new AccessRule(accessType, accessAction, appliedTo));
      this.rules.put(grid, newRuleList);
    }
  }

  public void addAccessRule(Grid grid, AccessRule accessRule) {
    addAccessRule(grid, accessRule.getAccessType(), accessRule.getAccessAction(), accessRule.getAppliedTo());
  }

  public void removeAllAccessRules() {
    this.rules.clear();
  }

  public void removeAllAccessRulesForGrid(Grid grid) {
    ArrayList<AccessRule> rulesList = this.rules.get(grid);
    if (rulesList != null) {
      rulesList.clear();
    }
  }

  public AccessType verifyAccess(User user, Grid grid, AccessAction accessAction) {

    // Retrouve les regles d'access pour la grille de Sudoku donnee
    ArrayList<AccessRule> rulesList = this.rules.get(grid);

    // Pas de regle pour cette grille: L'acces est permit
    if (rulesList == null) {
      return AccessType.granted;
    }

    AccessType userLevelAccessType = null;
    AccessType groupLevelAccessType = null;

    for (AccessRule ar : rulesList) {
      // On recupere les droits d'access au niveau utilisateur et groupe
      if (ar.getAccessAction() == accessAction && ar.getAppliedTo().hasUser(user)) {
        if (ar.getAppliedTo().isUser()) {
          userLevelAccessType = ar.getAccessType();
        } else {
          groupLevelAccessType = ar.getAccessType();
        }
      }
    }

    // Pas de regle indiquee pour cet utilisateur: L'acces est permit
    if (userLevelAccessType == null && groupLevelAccessType == null) {
      return AccessType.granted;
    }

    // Si pas de regle au niveau de l'utilisateur, on utilise la regle s'appliquant a son groupe
    if (userLevelAccessType == null) {
      return groupLevelAccessType;
    }

    // Sinon, la regle indiquee au niveau de l'utilisateur est prioritaire
    return userLevelAccessType;
  }
  
   public void SaveToJson(){
       //ObjectMapper mapper = new ObjectMapper();
       //Pour sérializer les champs publics comme privés
       //pour ne pas planter sur une valeur null
       //mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
       //mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
        SimpleModule module =  
          new SimpleModule("MyMapKeySerializerModule",  
          new Version(1, 0, 0, null));
        module.addKeySerializer(AccessManager.class, new AccessGridSerializer2());
        MapType myMapType;
        myMapType = TypeFactory.defaultInstance().constructMapType(HashMap.class, Grid.class,AccessRule.class);
        
        ObjectWriter writer = new ObjectMapper().withModule(module).typedWriter(myMapType);
       // mapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
      try {
          System.out.println(writer.writeValueAsString(rules));
      } catch (IOException ex) {
          Logger.getLogger(AccessManager.class.getName()).log(Level.SEVERE, null, ex);
      }
        try {
                System.out.println(jsonFilePath.concat("backupAccessManager.json"));
                jsonFile = new File(jsonFilePath.concat("backupAccessManager.json"));
                
                writer.writeValue(jsonFile, this);
                    //Pour logger le processus de sauvegarde
	        System.out.println(writer.writeValueAsString(this));
        } catch (JsonGenerationException ex) {
                   ex.printStackTrace();
 
	} catch (JsonMappingException ex) {
	 
	            ex.printStackTrace();
        }  catch (FileNotFoundException e){
            try {
          
                File tmpFile=new File(jsonFilePath);

                tmpFile.mkdirs();
                jsonFile = new File(jsonFilePath.concat("backupAccessManager.json"));
                writer.writeValue(jsonFile, this);
                //Pour logger le processus de sauvegarde
                System.out.println(writer.writeValueAsString(this));
            } catch (IOException ex) {
                Logger.getLogger(GridManager.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }catch (IOException ex) {
	 
	            ex.printStackTrace();
        }
    }
  public static AccessManager BuildFromJson(){
  ObjectMapper mapper= new ObjectMapper();
        // To avoid any undeclared property error
        //mapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIE‌​S , false);
            
        try {
                String jsonFilePath = System.getProperty("user.home").concat("\\LO23Sudoku\\Backup\\");
 
                File jsonFile = new File(jsonFilePath.concat("backupAccessManager.json"));
           
                //DataManager.instance = mapper.readValue(jsonFile, DataManager.class);
                AccessManager.instance = mapper.readValue(jsonFile, AccessManager.class);
        }catch (JsonGenerationException ex) {
         
	 
	            ex.printStackTrace();
 
	} catch (JsonMappingException ex) {
	 
	            ex.printStackTrace();
	 
	} catch (IOException ex) {
	 
	            ex.printStackTrace();
	 
	}
        return AccessManager.getInstance();
  
  }
}
