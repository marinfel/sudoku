package com.sudoku.data.manager;


import com.sudoku.data.model.AccessAction;
import com.sudoku.data.model.ContactCategory;
import com.sudoku.data.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class AccessManager {

  private static volatile AccessManager instance;

  private HashMap<String, HashSet<AccessAction>> accessRules = new HashMap<>();
  
  @JsonIgnore
  private File jsonFile;
  @JsonIgnore
  private  String jsonFilePath;
  
  private AccessManager(){
	  jsonFilePath = System.getProperty("user.home").concat("\\LO23Sudoku\\Backup\\");
	  addAccessRuleForGroup(new ContactCategory("hey"), AccessAction.play);
  }

  public static AccessManager getInstance(){
    if(instance == null){
      instance = new AccessManager();
    }
    return instance;
  }
  
  public HashMap<String, HashSet<AccessAction>> getAccessRules() {
	  return accessRules;
  }

  public void setAccessRules(HashMap<String, HashSet<AccessAction>> accessRules) {
	  this.accessRules = accessRules;
  }

  public void setAccessRulesForGroup(ContactCategory group, HashSet<AccessAction> allowedActions){
    if(group == null){return;}
    this.accessRules.put(group.getName(), allowedActions);
  }

  public void addAccessRuleForGroup(ContactCategory group, AccessAction action){
    if(group == null || action == null){return;}

    HashSet<AccessAction> actions = this.accessRules.get(group.getName());

    if(actions == null){
      actions = new HashSet<AccessAction>();
      actions.add(action);

      this.accessRules.put(group.getName(), actions);
    }
  }

  public void removeAccessRuleForGroup(ContactCategory group, AccessAction action){
    if(group == null || group.getName() == null || action == null){return;}
    HashSet<AccessAction> actions = this.accessRules.get(group.getName());
    if(actions != null){
      actions.remove(action);
    }
  }

  public HashSet<AccessAction> getAccessRulesForGroup(ContactCategory group){
    HashSet<AccessAction> actions = this.accessRules.get(group.getName());
    if(actions == null){return new HashSet<>();}
    return actions;
  }

  public boolean isAllowedTo(User u, AccessAction action){
    User loggedUser = UserManager.getInstance().getLoggedUser();
    if(loggedUser == null){return false;}
    ContactCategory uc = loggedUser.getUserCategory(u);
    if(uc == null){return false;}
    return isAllowedTo(uc, action);
  }

  public boolean isAllowedTo(ContactCategory c, AccessAction action){
    HashSet<AccessAction> actions = this.accessRules.get(c.getName());
    if(actions == null){return false;}
    return actions.contains(action);
  }
  
  public void SaveToJson(){
	  ObjectMapper mapper = new ObjectMapper();
	       //Pour sérializer les champs publics comme privés
	       mapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
	       //pour ne pas planter sur une valeur null
	       //mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
	       //mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
	        
	        try{  
	                System.out.println(jsonFilePath.concat("backupAccessManager.json"));
	                jsonFile = new File(jsonFilePath.concat("backupAccessManager.json"));
	           
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
	          jsonFile = new File(jsonFilePath.concat("backupAccessManager.json"));
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
	        return GridManager.getInstance();
	  
	  }
}
