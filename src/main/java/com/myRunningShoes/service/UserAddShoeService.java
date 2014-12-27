package com.myRunningShoes.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.myRunningShoes.dao.UserDao;
import com.myRunningShoes.dao.jdbc.JdbcUserDao;
import com.myRunningShoes.model.Shoe;
import com.myRunningShoes.util.MRSApplicationContext;

@WebServlet("/userAddShoe")
public class UserAddShoeService  extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(UserAddShoeService.class);
	
	public UserAddShoeService() {
		super();
	}
	
	/**
	 * Handle GETs. Expected URI is <url>/myRunningShoes/userAddShoe?userId=<#>&shoeId=<#>
	 * 
	 * dynamic number of shoes
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int userId = 0;
		int shoeId = 0;
		int shoeYear = 0;
		
		Map<String, String[]> params = request.getParameterMap();
		if (params != null) {
			String UserIdStrArr[] = params.get("userId");
			if (null != UserIdStrArr && UserIdStrArr.length > 0)
				userId = Integer.parseInt(UserIdStrArr[0]);
			String shoeIdArr[] = params.get("shoeId");
			if(null != shoeIdArr && shoeIdArr.length > 0)
				shoeId = Integer.parseInt(shoeIdArr[0]);

		}
		
		if (userId != 0 && shoeId != 0) {
			
			UserDao userDao = MRSApplicationContext.getInstance().getBean("UserDao", JdbcUserDao.class);
			userDao.insertShoe(userId, shoeId);
			
		}
	}


}
