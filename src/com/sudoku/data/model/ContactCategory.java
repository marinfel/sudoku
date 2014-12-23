/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class ContactCategory {

  private String name;
  private LinkedList<User> contacts = new LinkedList<>();

  public ContactCategory() {}

  public ContactCategory(String name){this.name = name;}

  public ContactCategory(String name, LinkedList<User> contacts) {
      this.name = name;
      this.contacts = contacts;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the contacts
   */
  public LinkedList<User> getContacts() {
    return contacts;
  }

  /**
   * @param contacts the contacts to set
   */
  public void setContacts(LinkedList<User> contacts) {
    this.contacts = contacts;
  }

  public void addContact(User user) {
    this.contacts.add(user);
  }

  public void removeContact(User user) {
    this.contacts.remove(user);
  }

  public boolean hasUser(User user){return this.contacts.contains(user);}

  @Override
  public boolean equals(Object other){
    if(other == null){return false;}
    if(other == this){return true;}
    if(!(other instanceof  ContactCategory)){return false;}
    ContactCategory o = (ContactCategory)other;
    return o.getName().equals(this.getName());
  }
}
