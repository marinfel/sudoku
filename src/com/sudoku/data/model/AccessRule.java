/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.model;

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
