/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Classe Tag 
 * @author jonathan
 */
public class Tag {
    /**
     * Champs de la classe Tag
     */
    private String name;
    /**
     * Méthodes de la classe Tag
     */
    public Tag(){
        name="";
    }
    public Tag(String name){
        this.name=name;
    }
    public Tag(Tag tag){
        this.name=tag.getName();
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
            
}
