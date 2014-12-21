package com.myRunningShoes.dao;

import java.util.List;

import com.myRunningShoes.model.User;
import com.myRunningShoes.model.UserShoes;

public interface UserDao
{

    public User getUser(int user_id);

    public List<UserShoes> getShoes(int user_id);

    public void insertShoe(int userId, int shoeId);

    public void addMiles(UserShoes shoe);

    public void deleteShoe(UserShoes shoe);

}
