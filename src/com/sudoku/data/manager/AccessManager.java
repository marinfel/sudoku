package com.sudoku.data.manager;


import com.sudoku.data.model.AccessAction;
import com.sudoku.data.model.ContactCategory;
import com.sudoku.data.model.User;

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
    if(group == null || action == null){return;}

    HashSet<AccessAction> actions = this.accessRules.get(group);

    if(actions == null){
      actions = new HashSet<AccessAction>();
      actions.add(action);

      this.accessRules.put(group, actions);
    }
  }

  public void removeAccessRuleForGroup(ContactCategory group, AccessAction action){
    if(group == null || action == null){return;}
    HashSet<AccessAction> actions = this.accessRules.get(group);
    if(actions != null){
      actions.remove(action);
    }
  }

  public HashSet<AccessAction> getAccessRulesForGroup(ContactCategory group){
    HashSet<AccessAction> actions = this.accessRules.get(group);
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
    HashSet<AccessAction> actions = this.accessRules.get(c);
    if(actions == null){return false;}
    return actions.contains(action);
  }
}
