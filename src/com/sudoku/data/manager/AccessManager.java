package com.sudoku.data.manager;


import com.sudoku.data.model.AccessAction;
import com.sudoku.data.model.AccessRule;
import com.sudoku.data.model.ContactCategory;

import java.util.HashMap;
import java.util.HashSet;

public class AccessManager {

  private static AccessManager instance;

  private HashMap<ContactCategory, HashSet<AccessAction>> accessRules = new HashMap<>();

  private AccessManager(){}

  public static AccessManager getInstance(){
    if(instance == null){
      instance = new AccessManager();
    }
    return instance;
  }

  public void setAccessRulesForGroup(ContactCategory group, HashSet<AccessAction> allowedActions){
    if(group == null){return;}
    this.accessRules.put(group, allowedActions);
  }

  public void addAccessRuleForGroup(ContactCategory group, AccessAction action){
    HashSet<AccessAction> actions = this.accessRules.get(group);
    if(actions = null){

    }
  }

}
