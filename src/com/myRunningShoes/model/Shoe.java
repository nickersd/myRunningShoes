package com.myRunningShoes.model;

import java.io.Serializable;

public class Shoe implements Serializable
{
    private String make;
    private String model;
    private int year;
    private int shoeId;
    
    public int getUserShoesId() {
		return shoeId;
	}
	public void setUserShoesId(int id) {
		this.shoeId = id;
	}
	
	public String getMake()
    {
        return make;
    }
    public void setMake(String make)
    {
        this.make = make;
    }
    public String getModel()
    {
        return model;
    }
    public void setModel(String model)
    {
        this.model = model;
    }
    public int getYear()
    {
        return year;
    }
    public void setYear(int year)
    {
        this.year = year;
    }
    @Override
    public String toString(){
        return getUserShoesId() + ", " + getYear() + ", "+ getMake() + ", "+ getModel();
    }

}
