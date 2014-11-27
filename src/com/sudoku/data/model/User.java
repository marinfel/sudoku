/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.model;

import org.springframework.security.crypto.codec.Base64;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class User implements Ruleable {

  private String pseudo;
  private String salt;
  private String password;
  private Date birthDate;
  private String profilePicturePath;
  private Date createDate;
  private Date updateDate;
  private String ipAddress;
  private List<ContactCategory> contactCategories;

  public User(String pseudo, String brutPassword, Date birthDate,
              String profilePicturePath)
      throws NoSuchAlgorithmException, UnsupportedEncodingException,
      UnknownHostException {
    MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
    Calendar cal = new GregorianCalendar();

    this.pseudo = pseudo;
    this.salt = this.randomSalt();
    String toBeHashed = password + this.salt;
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

  @Override
  public Boolean hasUser(User user) {
    return this.equals(user);
  }

  @Override
  public Boolean isUser() {
    return true;
  }

}
