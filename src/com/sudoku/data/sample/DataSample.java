/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.sample;

import com.sudoku.data.factory.GridFactory;
import com.sudoku.data.manager.AccessManager;
import com.sudoku.data.model.*;
import java.util.List;


import com.sudoku.data.model.Comment;
import com.sudoku.data.model.Grid;
import com.sudoku.data.model.Tag;
import com.sudoku.data.model.User;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import com.sudoku.data.manager.DataManager;
import com.sudoku.data.manager.GridManager;
import com.sudoku.data.manager.UserManager;

/**
 * @author Jonathan
 */
public class DataSample {

    // User et grid sont accesibles directement, servez-vous
    public User a, b, c;
    public Grid g1,g2,g3;
    
    
    public DataSample(){
        
        g1= GridFactory.generateRandomGrid();
        g2= GridFactory.generateRandomGrid();
        g3= GridFactory.generateRandomGrid();
        }
    public void exec(){
        System.out.println(" Datasample started");
      try {
        a = new User("User1", "User1", new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("14/09/1989"), "");
        b = new User("User2", "User2", new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("01/01/2011"), "");
        c = new User("User3", "User3", new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("01/01/2011"), "");
      } catch (Exception ex) {
        // to be handled
      }
       // a.saveToJson();
       //création de Grid g1
       List<Comment> comments = new ArrayList<>();
       //Ajout de commentaires
       comments.add(new Comment("texte du commentaire 1",1, a));
       comments.add(new Comment("texte du commentaire 2",2, a));
       g1.setComments(comments);
       //Ajout de tags
       List<Tag> tags= new ArrayList<>();
       tags.add(new Tag("Hard"));
       tags.add(new Tag("Awesome"));
       tags.add(new Tag("Wonderful"));
       g1.setTags(tags);
       
      // g1.setCreateDate(new Timestamp(2014,11,4,14,48,0,0));
       g1.setCreateUser(a);
       g1.setDifficulty(3);
       g1.setDescription("This is the awesome grid n°1");
       //g1.setId(1);
       
       //Création de Grid g2
       List<Comment> comments2 = new ArrayList<>();
       //Ajout de commentaires
       comments2.add(new Comment("texte du commentaire 1",1, a));
       comments2.add(new Comment("texte du commentaire 2",2, a));
       g2.setComments(comments2);
       //Ajout de tags
       List<Tag> tags2= new ArrayList<>();
       tags2.add(new Tag("Hard2"));
       tags2.add(new Tag("Awesome2"));
       tags2.add(new Tag("Wonderful2"));
       g2.setTags(tags2);
        
       g2.setCreateDate(new Timestamp(2014,11,4,14,48,0,0));
       g2.setCreateUser(a);
       g2.setDifficulty(4);
       g2.setDescription("This is the awesome grid n°2");
    //       g2.setId(2);
       
       //Création de Grid g3
       List<Comment> comments3 = new ArrayList<>();
       //Ajout de commentaires
       comments3.add(new Comment("texte du commentaire 1",1, a));
       comments3.add(new Comment("texte du commentaire 2",2, a));
       g3.setComments(comments3);
       //Ajout de tags
       List<Tag> tags3= new LinkedList<>();
       tags3.add(new Tag("Hard2"));
       tags3.add(new Tag("Awesome2"));
       tags3.add(new Tag("Wonderful2"));
       g3.setTags(tags3);
        
       g3.setCreateDate(new Timestamp(2014,11,4,14,48,0,0));
       g3.setCreateUser(b);
       g3.setDifficulty(4);
       g3.setDescription("This is the awesome grid n°3");
      // g3.setId(2);
       //a.saveToJson();
       //On écrase b
       //b=User.buildFromJson();
       //System.out.println(b.getPseudo());
       
       UserManager userMgr = UserManager.getInstance();
       GridManager gridMgr = GridManager.getInstance();
       
       userMgr.addLocalUser(a);
       userMgr.addLocalUser(b);
       
       gridMgr.addGrid(g1);
       gridMgr.addGrid(g2);
       gridMgr.addGrid(g3);
       gridMgr.addPlayedGrid(g2,b);
       
       AccessManager accessMgr = AccessManager.getInstance();
       accessMgr.addAccessRule(g1, AccessType.revoked, AccessAction.display, a);
       accessMgr.addAccessRule(g1, AccessType.revoked, AccessAction.play, a);
       accessMgr.addAccessRule(g1, AccessType.revoked, AccessAction.comment, a);
       
       accessMgr.addAccessRule(g1, AccessType.revoked, AccessAction.comment, b);
       accessMgr.addAccessRule(g1, AccessType.revoked, AccessAction.play, b);
       
       DataManager dataMgr = DataManager.getInstance();
       
       //dataMgr.saveToJson();
       
       System.out.println(" Datasample finished");
       
    }
     public List<User> getUserList(){
        List<User> users = new LinkedList<>();
        users.add(a);
        users.add(b);
        return users;
     }

}
