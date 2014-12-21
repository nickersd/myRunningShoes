package com.myRunningShoes.dao;

import java.util.List;

import com.myRunningShoes.model.Shoe;

public interface ShoesDao {

	public List<Shoe> getAvailableShoes();
}
