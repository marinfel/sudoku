package com.sudoku.data.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *  
 * @author jonathan
 */

public class Comment {
    /** Champs de la classe Comment
     * *
     */
    private String author;
    private String comment;
    private Integer grade;
    /**Méthode de la classe  Comment
     * 
     */
    
    public Comment(){
        comment="";
    }
    public Comment(String comment){
        this.comment=comment;
    }
    
    public Comment(Integer grade){
        this.grade=grade;
    }
    public Comment(String comment,Integer grade){
        this.comment=comment;
        this.grade=grade;
    }
    
    public String getAuthor(){
        return author;
    }
    
    public void setAuthor(String author){
        this.author=author;
    }
    public String getComment(){
        
        return comment;
    }
    public void setComment(String comment){
        this.comment=comment;
    }
    public Integer getGrade(){
        return grade;
    }
    public void setGrade(Integer grade){
        this.grade=grade;
            
    }

}