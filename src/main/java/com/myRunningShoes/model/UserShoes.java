package com.myRunningShoes.model;

import java.io.Serializable;
import java.util.Date;

public class UserShoes extends Shoe implements Serializable {
	private int userShoesId;
	private int user_id;
	private Date date_purchased;
	private int miles;
	private int is_active;
	
	public UserShoes() {
		super();
	}
	
	public UserShoes(int id, int userId, int year) {
		
	}
	
	public int getUserShoesId() {
		return userShoesId;
	}

	public void setUserShoesId(int id) {
		this.userShoesId = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Date getDate_purchased() {
		return date_purchased;
	}

	public void setDate_purchased(Date date_purchased) {
		this.date_purchased = date_purchased;
	}

	public int getMiles() {
		return miles;
	}

	public void setMiles(int miles) {
		this.miles = miles;
	}

	public int getIs_active() {
		return is_active;
	}

	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}

	@Override
	public String toString() {
		return getUserShoesId() + ", " + getUser_id() + ", "
				+ getDate_purchased() + ", " + getMiles();
	}
}
