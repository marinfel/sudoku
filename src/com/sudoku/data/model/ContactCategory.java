/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class ContactCategory implements Ruleable {

  private String name;
  private List<User> contacts = new ArrayList<>();

  public ContactCategory() {
  }

  public ContactCategory(String name, List<User> contacts) {
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
  public List<User> getContacts() {
    return contacts;
  }

  /**
   * @param contacts the contacts to set
   */
  public void setContacts(List<User> contacts) {
    this.contacts = contacts;
  }

  public void addContact(User user) {
    this.contacts.add(user);
  }

  public void removeContact(User user) {
    this.contacts.remove(user);
  }

  @JsonIgnore
  @Override
  public Boolean hasUser(User user) {
    return this.contacts.contains(user);
  }

  @JsonIgnore
  @Override
  public Boolean isUser() {
    return false;
  }
}
