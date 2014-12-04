package com.myRunningShoes.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class User implements Serializable
{
    @SerializedName("User")
    private int id;
    private String firstName;
    private String lastName;
    private List<UserShoes> userShoes;
    
    
    public User(int user_id, String firstName, String lastName) {
        this.id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public List<UserShoes> getUserShoes()
    {
        return userShoes;
    }

    public void setUserShoes(List<UserShoes> userShoes)
    {
        this.userShoes = userShoes;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    
 /*   @Override
    public String toString(){
        return getId() + ", "+ getFirstName()+" "+ getLastName();
    }*/
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("***** User Details *****\n");
        sb.append("ID="+getId()+"\n");
        sb.append("FirstName="+getFirstName()+"\n");
        sb.append("lastName="+getLastName()+"\n");
        sb.append("*****************************");
         
        return sb.toString();
    }

}
