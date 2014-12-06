package com.myRunningShoes.dao;

import com.myRunningShoes.model.UserShoes;

public interface UserShoesDao
{
    public void saveMiles(UserShoes shoe);
    public UserShoes getShoe(int userId, int shoeId);
}
