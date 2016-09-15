package com.myRunningShoes.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.annotations.SerializedName;

@Service("User")
public class User implements Serializable, InitializingBean 
{

    
    @SerializedName("User")
    private int id;
    private String firstName;
    private String lastName;
    private List<UserShoes> userShoes;
    
    
    public void afterPropertiesSet() throws Exception {
        if (lastName == null) {
            throw new BeanCreationException("lastName not set for User");
        }
    }
    
    
    public User(int user_id, String firstName, String lastName) {
        this.id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public User() {
    	this.id = 0;
    	this.firstName = "NotFound";
    	this.lastName = "NotFound";
    }
    
    /**
     * Ctor to be used for user id's not found in the DB
     * @param userId
     */
    public User(int userId) {
    	this.id = userId;
    	this.firstName = "NotFound";
    	this.lastName = "NotFound";
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
    
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("***** User Details *****\n");
        sb.append("ID="+getId()+"\n");
        sb.append("FirstName="+getFirstName()+"\n");
        sb.append("lastName="+getLastName()+"\n");
        for(UserShoes shoe: userShoes) {
        	sb.append("shoe="+shoe.toString());
        }
        sb.append("*****************************");
         
        return sb.toString();
    }

}
