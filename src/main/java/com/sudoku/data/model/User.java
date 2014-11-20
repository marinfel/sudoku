/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.model;

import java.util.List;

public class User implements Ruleable {

    private int id;
    private String pseudo;
    private String salt;
    private String birthdate;
    private String profilePicturePath;
    private String createDate;
    private String updateDate;
    private String ipAdress;
    private List<ContactCategory> contactCategories;
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @param pseudo the pseudo to set
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return the birthdate
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * @param birthdate the birthdate to set
     */
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
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
    }

    /**
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the updateDate
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
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
