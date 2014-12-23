/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.model;

import java.util.HashSet;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class ContactCategory {

  private String name;
  private HashSet<User> contacts = new HashSet<>();

  public ContactCategory() {}

  public ContactCategory(String name){this.name = name;}

  public ContactCategory(String name, HashSet<User> contacts) {
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
  public HashSet<User> getContacts() {
    return contacts;
  }

  /**
   * @param contacts the contacts to set
   */
  public void setContacts(HashSet<User> contacts) {
    this.contacts = contacts;
  }

  public void addContact(User user) {
    this.contacts.add(user);
  }

  public void removeContact(User user) {
    this.contacts.remove(user);
  }

  public boolean hasUser(User user){return this.contacts.contains(user);}

}
