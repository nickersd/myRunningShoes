package com.myRunningShoes.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myRunningShoes.dao.UserDao;
import com.myRunningShoes.dao.jdbc.JdbcUserDao;
import com.myRunningShoes.model.User;
import com.myRunningShoes.util.MRSApplicationContext;

@WebServlet("/newUser")
public class NewUserService  extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(NewUserService.class);
	
	/**
	 * Handle GETs. Expected URI is <url>/myRunningShoes/newUser?firstName=<string>&lastName=<string>&email=<string>&password=<string>
	 * 
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String firstName = null;
		String lastName = null;
		String email = null;
		String password = null;
		
		
		Map<String, String[]> params = request.getParameterMap();
		if (params != null) {
			String firstNameArr[] = params.get("firstName");
			if (null != firstNameArr && firstNameArr.length > 0)
				firstName = firstNameArr[0];
			String lastNameArr[] = params.get("lastName");
			if (null != lastNameArr && lastNameArr.length > 0)
				lastName = lastNameArr[0];
			String emailArr[] = params.get("email");
			if (null != emailArr && emailArr.length > 0)
				email = emailArr[0];
			String passwordArr[] = params.get("password");
			if (null != passwordArr && passwordArr.length > 0)
				password = passwordArr[0];
		}
		
		if(firstName != null && lastName != null && email != null && password != null) {
			
			UserDao userDao = MRSApplicationContext.getInstance().getBean("UserDao", JdbcUserDao.class);
			User user = userDao.addUser(firstName, lastName, email, password);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String userStr = gson.toJson(user);
			logger.debug("returning: " + userStr);
			PrintWriter out = response.getWriter();
			out.println(userStr);
		}
		
	}
}
