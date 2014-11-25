/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.model;

import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class User implements Ruleable {

    private String pseudo;
    private String salt;
    private String password;
    private Date birthdate;
    private String profilePicturePath;
    private Date createDate;
    private Date updateDate;
    private String ipAdress;
    private List<ContactCategory> contactCategories;
    
    public User(String pseudo, String brutPassword, Date birthdate, String profilePicturePath){
        
        
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
     * @return the birthdate
     */
    Date getBirthdate() {
        return birthdate;
    }

    /**
     * @param birthdate the birthdate to set
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
        this.UpdateDate();
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
        this.UpdateDate();
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
     * Update The UpdateDate
     */
    private void UpdateDate() {
        Calendar cal = new GregorianCalendar();
        this.updateDate = cal.getTime ();
    }

    /**
     * @return the ipAdress
     */
    public String getIpAdress() {
        return ipAdress;
    }

    /**
     * @param ipAdress the ipAdress to set
     */
    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
        this.UpdateDate();
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
        this.UpdateDate();
    }

    @Override
    public Boolean hasUser(User user) {
        return this.equals(user);
    }
    
    @Override
    public Boolean isUser(){
        return true;
    }

}
