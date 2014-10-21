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
    private String comment;
    private Integer grade;
    /**Méthode de la classe  Comment
     * 
     */
    public void Comment(){
        comment="";
    }
    public void Comment(String comment){
        this.comment=comment;
    }
    
    public void Comment(Integer grade){
        this.grade=grade;
    }
    public void Comment(String comment,Integer grade){
        this.comment=comment;
        this.grade=grade;
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