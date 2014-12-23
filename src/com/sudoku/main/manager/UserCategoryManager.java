/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.main.manager;

import com.sudoku.data.model.ContactCategory;
import com.sudoku.data.model.User;

import java.util.*;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author JULIANC
 */
public class UserCategoryManager {
    
    private static UserCategoryManager instance;
    
    public UserCategoryManager(){
    }
    
    public static UserCategoryManager getInstance() {
    if (instance == null)
        instance = new UserCategoryManager();
    return instance;
  }
    
    public ObservableList getUsersToShow(List<User> ListUsers){
        ObservableList ObsList  = FXCollections.observableArrayList();
        Iterator<User> i = ListUsers.iterator();
        while(i.hasNext())
            ObsList.add(i.next().getPseudo());
        return ObsList;
    }
    
    public ObservableList getCategoriesToShow(List<ContactCategory> ListCat){
        ObservableList ObsList  = FXCollections.observableArrayList();
        Iterator<ContactCategory> i = ListCat.iterator();
        while(i.hasNext())
            ObsList.add(i.next().getName());
        return ObsList;
    }
    
    /**
     * @param contCat Liste de catégories à l'utilisateur connecté
     * @param connUsers LIste des utilisateurs qui sont connectés
     * @return Un HashMap avec le nom de toutes les catégories à l'utilisateur connecté
     * avec une liste d'utilisateurs qui appartiennent à chaque catégorie
     */
    public HashMap<String,List<User>> getUsersCategories(List<ContactCategory> contCat, List<User> connUsers){        
        List<User> globalUsers = connUsers; // Liste des utilisateurs qui n'ont pas une catégorie définie
        List<User> usersWithCateg = new LinkedList<>(); // Liste des utilisateurs qui ont une catégorie définie
        List<User> categoryUsers; // Liste des utilisateurs à retourner pour chaque catégorie
        HashMap<String,List<User>> retur = new HashMap<>(); // HashMap à retourner
        Iterator<ContactCategory> itCat = contCat.iterator(); //Pour parcourir la liste de catégories
        retur.put("Global",globalUsers);
        while(itCat.hasNext()){ 
            ContactCategory cat = itCat.next();
            categoryUsers = new ArrayList<>(cat.getContacts());
            retur.put(cat.getName(), categoryUsers);
            usersWithCateg.addAll(cat.getContacts());
        }
        globalUsers.removeAll(usersWithCateg);
        if(!globalUsers.isEmpty()){
            retur.put("Global",globalUsers);
        }
        return retur;
    }
    
    public HashMap<String,ObservableList> changeToObservableData(HashMap<String, List<User>> categoryAndUsers) {
        HashMap<String,ObservableList> dataToShow = new HashMap<>();
        ObservableList usersCategory = FXCollections.observableArrayList();
        List<User> liUser;
        for (Map.Entry e : categoryAndUsers.entrySet()) {
            String categ = (String)e.getKey();
            liUser = (List<User>)e.getValue();
            Iterator<User> itUs = liUser.iterator();
            while(itUs.hasNext()){
                //System.out.println("US: "+itUs.next().getPseudo());
                String userAdd = itUs.next().getPseudo();
                usersCategory.add(userAdd);
            }
            dataToShow.put(categ, usersCategory);
            //usersCategory.clear();
            liUser.clear();
        }
        return dataToShow;
    }
    
}
