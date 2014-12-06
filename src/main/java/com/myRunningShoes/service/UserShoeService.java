package com.myRunningShoes.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.myRunningShoes.dao.UserDao;
import com.myRunningShoes.dao.UserShoesDao;
import com.myRunningShoes.dao.jdbc.JdbcUserDao;
import com.myRunningShoes.dao.jdbc.JdbcUserShoesDao;
import com.myRunningShoes.model.User;
import com.myRunningShoes.model.UserShoes;

/**
 * Main servlet 
 */
@WebServlet("/shoe")
public class UserShoeService  extends HttpServlet {
	
	final static Logger logger = Logger.getLogger(UserShoeService.class);

	/**
	 * Handle GETs. Expected URI is <url>/shoe?userId=<#>&shoeId=<#>&miles=<#>
	 * 
	 * @return void but prints json representation of user and
	 * dynamic number of shoes
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	int userId = 0;
	int shoeId = 0;
	int miles = 0;
	User user = null;
	UserShoes shoe = null;
	
	Map<String, String[]> params = request.getParameterMap();
	if (params != null) {
		String UserIdStrArr[] = params.get("userId");
		if (null != UserIdStrArr && UserIdStrArr.length > 0)
			userId = Integer.parseInt(UserIdStrArr[0]);
		String shoeIdArr[] = params.get("shoeId");
		if(null != shoeIdArr && shoeIdArr.length > 0)
			shoeId = Integer.parseInt(shoeIdArr[0]);
		String milesArr[] = params.get("miles");
		if(null != milesArr && milesArr.length > 0)
			miles = Integer.parseInt(milesArr[0]);
	}
	
	if(userId > 0 && shoeId > 0 && miles > 0) {
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:app-context-xml.xml");
		ctx.refresh();

		UserShoesDao userShoeDao = ctx.getBean("UserShoesDao", JdbcUserShoesDao.class);
		shoe = userShoeDao.getShoe(userId, shoeId);
		shoe.setMiles(shoe.getMiles() + miles); 
		try {
			userShoeDao.saveMiles(shoe);
		} catch (Exception e) {
			
		}
	}
	
	
	
	
	
	
	
		
	}
	
}
