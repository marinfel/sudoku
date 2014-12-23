/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Base64;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.codehaus.jackson.annotate.JsonIgnore;

public class User {
  private static Logger logger = LoggerFactory.getLogger(User.class);
  private String pseudo;
  private String salt;
  private String password;
  private Date birthDate;
  private String profilePicturePath;
  private Date createDate;
  private Date updateDate;
  private String ipAddress;
  private List<ContactCategory> contactCategories;

  private User() {
  }

  public User(String pseudo, String brutPassword, Date birthDate,
              String profilePicturePath)
      throws NoSuchAlgorithmException, UnsupportedEncodingException,
      UnknownHostException {
    MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
    Calendar cal = new GregorianCalendar();

    this.pseudo = pseudo;
    this.salt = this.randomSalt();
    String toBeHashed = brutPassword + this.salt;
    // hash of pwd+salt
    this.password =
        new String(Base64.encode(mDigest.digest(toBeHashed.getBytes("UTF-8"))));
    this.birthDate = birthDate;
    this.profilePicturePath = profilePicturePath;
    this.createDate = cal.getTime();
    this.updateDate = this.createDate;
    this.ipAddress = InetAddress.getLocalHost().getHostAddress();
    contactCategories = null;
  }

  public boolean checkPassword(String rawPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
    String toBeHashed = rawPassword + this.getSalt();
    if (new String(Base64.encode(
       mDigest.digest(toBeHashed.getBytes("UTF-8"))))
       .equals(this.getPassword())){
      return true;
    }
    else{
      return false;
    }
  }

  public void setpassword(String rawPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException{
      MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
      String toBeHashed = rawPassword + this.salt;
      this.password = 
              new String(Base64.encode(mDigest.digest(toBeHashed.getBytes("UTF-8"))));
  }

  public static User buildFromAvroUser(com.sudoku.comm.generated.User user) {
    User resultUser = new User();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    resultUser.pseudo = user.getPseudo();
    resultUser.profilePicturePath = user.getProfilePicturePath();
    resultUser.ipAddress = user.getIpAddress();
    resultUser.salt = user.getSalt();
    try {
      resultUser.birthDate = df.parse(user.getBirthDate());
      resultUser.createDate = df.parse(user.getCreateDate());
      resultUser.updateDate = df.parse(user.getUpdateDate());
    } catch (ParseException ex) {
      logger.error(ex.toString());
    }
    return resultUser;
  }

  public static com.sudoku.comm.generated.User buildAvroUser(User user) {
    return com.sudoku.comm.generated.User.newBuilder()
        .setBirthDate(user.getBirthDate().toString())
        .setCreateDate(user.getCreateDate().toString())
        .setIpAddress(user.getIpAddress())
        .setProfilePicturePath(user.getProfilePicturePath())
        .setPseudo(user.getPseudo())
        .setSalt(user.getSalt())
        .setUpdateDate(user.getUpdateDate().toString())
        .build();
  }

  private String randomSalt() {
    SecureRandom random = new SecureRandom();
    return new BigInteger(130, random).toString(32);
  }

  /**
   * @return the pseudo
   */
  public String getPseudo() {
    return pseudo;
  }


  /**
   * @return the salt
   */
  public String getSalt() {
    return salt;
  }

  /**
   * @return the hash of the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @return the birthDate
   */
  public Date getBirthDate() {
    return birthDate;
  }

  /**
   * @param birthDate the birthDate to set
   */
  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
    this.updateDate();
  }

  /**
   * @return the profilePicturePath
   */
  public String getProfilePicturePath() {
    return profilePicturePath;
  }

  /**
   * @param profilePicturePath the profilePicturePath to set
   */
  public void setProfilePicturePath(String profilePicturePath) {
    this.profilePicturePath = profilePicturePath;
    this.updateDate();
  }

  /**
   * @return the createDate
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * @return the updateDate
   */
  public Date getUpdateDate() {
    return updateDate;
  }

  /**
   * Update The updateDate
   */
  private void updateDate() {
    Calendar cal = new GregorianCalendar();
    this.updateDate = cal.getTime();
  }

  /**
   * @return the ipAddress
   */
  public String getIpAddress() {
    return ipAddress;
  }

  /**
   * @param ipAddress the ipAddress to set
   */
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
    this.updateDate();
  }

  /**
   * @return the contactCategories
   */
  public List<ContactCategory> getContactCategories() {
    return contactCategories;
  }

  /**
   * @param contactCategories the contactCategories to set
   */
  public void setContactCategories(List<ContactCategory> contactCategories) {
    this.contactCategories = contactCategories;
    this.updateDate();
  }

  public void createContactCategory(String name){
    ContactCategory newContactCategory = this.getContactCategory(name);
    if(newContactCategory == null){
      newContactCategory = new ContactCategory(name);
      this.contactCategories.add(newContactCategory);
    }
  }

  public boolean hasContactCategory(String name){
    for(ContactCategory cc : this.contactCategories){
      if(cc.getName().equals(name)){
        return true;
      }
    }
    return false;
  }

  public boolean removeContactCategory(String name){
    ContactCategory cc = this.getContactCategory(name);
    if(cc == null) return false;
    return contactCategories.remove(cc);
  }

  public ContactCategory getContactCategory(String name){
    for(ContactCategory cc : this.contactCategories){
      if(cc.getName().equals(name)){
        return cc;
      }
    }
    return null;
  }

  @JsonIgnore
  public ContactCategory getUserCategory(User u){
    for(ContactCategory c : this.contactCategories){
      if(c.hasUser(u)){return c;}
    }
    return null;
  }

  public boolean addUserToContactCategory(String name, User u){
    ContactCategory cc = this.getContactCategory(name);
    if(cc == null){return false;}

    ContactCategory userCategory = this.getUserCategory(u);
    if(userCategory != null){userCategory.removeContact(u);}

    cc.addContact(u);
    return true;
  }

  public boolean equals(Object other){
    if(other == null) return false;
    if(other == this) return true;
    if(!(other instanceof User)) return false;

    User o = (User)other;
    return o.getSalt().equals(this.getSalt()) && o.getPseudo().equals(this.getPseudo());
  }
}
