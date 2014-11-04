/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.grid.manager;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Classe abstraite Manager
 * Permet de manipuler des sets d'objets
 * @author Celine TO, Mehdi KANE
 */

public abstract class Manager<T> {
    
    protected HashSet<T> items;
    
    public Manager(){
        items = new HashSet();
    }
    
    public Manager(HashSet<T> i) {
        items = new HashSet(i);
    }
    
    public void addItem(T item) {
        items.add(item);
    }
    
    public T removeItem(T item) {
        T temp = item; // a tester!! objet temporaire?
        items.remove(item);
        return temp;
    }
    
    public int getLength() {
        return items.size();
    }
    
    public Iterator<T> getIterator() {
        return items.iterator();
    }
    
    //A implementer dans les classes heritees pour pouvoir faire new Grid ou new User
    public abstract HashSet<T> copyItems() ;
    
    public HashSet<T> getItems() {
        return copyItems();
    }
    
    //public abstract List<T> search(HashSet<String> keywords);
  
    public void clearAll() {
        items.clear();
    }
    
    /**
     * @param args the command line arguments
     */
    
    
}
