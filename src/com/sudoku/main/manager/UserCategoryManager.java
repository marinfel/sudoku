/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.main.manager;

import com.sudoku.data.model.ContactCategory;
import com.sudoku.data.model.User;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    
    /**
     * @param contCat catégories de l'utilisateur connecté
     * @param connUsers
     * @return un HashMap avec le nom de toutes les catégories de l'utilisateur connecté
     * et une liste des utilisateurs qui appartiennent à chaque catégorie
     */
    public HashMap<String,List<User>> getUsersCategories(List<ContactCategory> contCat, List<User> connUsers){        
        List<User> globalUsers = connUsers; // Liste des utilisateurs qui n'ont pas une catégorie définie
        List<User> usersWithCateg = new LinkedList<>(); // Liste des utilisateurs qui ont une catégorie définie        
        HashMap<String,List<User>> retur = new HashMap<>(); // HashMap à retourner        
        Iterator<ContactCategory> itCat = contCat.iterator(); //Pour parcourir la liste de catégories
        while(itCat.hasNext()){ 
            List<User> categoryUsers; // Liste des utilisateurs à retourner pour chaque catégorie
            ContactCategory cat = itCat.next();
            categoryUsers = cat.getContacts();
            retur.put(cat.getName(), categoryUsers);
            usersWithCateg.addAll(cat.getContacts());
            categoryUsers.clear();
        }
        globalUsers.removeAll(usersWithCateg);
        if(!globalUsers.isEmpty()){
            System.out.println("AAAAAAAAAA");
            retur.put("Global",globalUsers);
        }
        return retur;
    }
    
    public HashMap<String,ObservableList> changeToObservableData(HashMap<String, List<User>> categoryAndUsers) {
        HashMap<String,ObservableList> dataToShow = new HashMap<>();
        ObservableList usersCategory = FXCollections.observableArrayList();
        for (Map.Entry e : categoryAndUsers.entrySet()) {
            System.out.println("CAT: "+e.getKey());
            String categ = (String)e.getKey();
            List<User> liUser = (List<User>)e.getValue();
            Iterator<User> itUs = liUser.iterator();
            while(itUs.hasNext()){
                //System.out.println("US: "+itUs.next().getPseudo());
                usersCategory.add(itUs.next().getPseudo());                
            }
            dataToShow.put(categ, usersCategory);
            usersCategory.clear();
        }
        return dataToShow;
    }
    
}
