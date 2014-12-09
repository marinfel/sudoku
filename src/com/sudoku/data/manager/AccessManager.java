/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.manager;

import com.sudoku.data.model.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author JE
 */
public class AccessManager {

  private static AccessManager instance;
  private HashMap<Grid, ArrayList<AccessRule>> rules;

  private AccessManager() {
    this.rules = new HashMap<>();
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
  public ArrayList<AccessRule> getAllAccessRulesForGrid(Grid grid) {
    return this.rules.get(grid);
  }

  //jsonignore
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
}
