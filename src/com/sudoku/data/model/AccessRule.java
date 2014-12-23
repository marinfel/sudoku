/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

//JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class",defaultImpl = AccessRule.class)
public class AccessRule {

  private AccessType accessType;
  private AccessAction accessAction;
  private Ruleable appliedTo;

  public AccessRule() {
  }
  
  public AccessRule(AccessType accessType) {
    this.accessType = accessType;
  }

  public AccessRule(AccessType accessType, AccessAction accessAction) {
    this.accessType = accessType;
    this.accessAction = accessAction;
  }

  public AccessRule(AccessType accessType, AccessAction accessAction, Ruleable appliedTo) {
    this.accessType = accessType;
    this.accessAction = accessAction;
    this.appliedTo = appliedTo;
  }

  // For deserialization
  /*public AccessRule(String jsonString){
      ObjectMapper mapper = new ObjectMapper();
     // mapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIE‌​S, false);
      ArrayList<AccessRule> rules= null;
      try {
      rules = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(ArrayList.class, AccessRule.class));
    
      System.out.println (rules);
      
      } catch (JsonParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (JsonMappingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
  }*/
  /**
   * @return the accessType
   */
  public AccessType getAccessType() {
    return accessType;
  }

  /**
   * @param accessType the accessType to set
   */
  public void setAccessType(AccessType accessType) {
    this.accessType = accessType;
  }

  /**
   * @return the appliedTo
   */
  public Ruleable getAppliedTo() {
    return appliedTo;
  }

  /**
   * @param appliedTo the appliedTo to set
   */
  public void setAppliedTo(Ruleable appliedTo) {
    this.appliedTo = appliedTo;
  }

  /**
   * @return the accessAction
   */
  public AccessAction getAccessAction() {
    return accessAction;
  }

  /**
   * @param accessAction the accessAction to set
   */
  public void setAccessAction(AccessAction accessAction) {
    this.accessAction = accessAction;
  }
}
