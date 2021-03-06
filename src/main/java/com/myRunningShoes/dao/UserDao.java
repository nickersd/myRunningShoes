package com.myRunningShoes.dao;

import java.util.List;

import com.myRunningShoes.model.User;
import com.myRunningShoes.model.UserShoes;

public interface UserDao
{
	
	public User auth (String email, String password);

    public User getUser(int user_id);

    public List<UserShoes> getShoes(int user_id);

    public void insertShoe(int userId, int shoeId);

    public void addMiles(UserShoes shoe);

    public void deleteShoe(UserShoes shoe);
    
    public User addUser(String firstName, String lastName, String email, 
    		String password);

}
