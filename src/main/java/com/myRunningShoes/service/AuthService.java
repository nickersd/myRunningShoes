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
import org.springframework.dao.EmptyResultDataAccessException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myRunningShoes.dao.UserDao;
import com.myRunningShoes.dao.jdbc.JdbcUserDao;
import com.myRunningShoes.model.User;
import com.myRunningShoes.util.MRSApplicationContext;

@WebServlet("/auth")
public class AuthService  extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(AuthService.class);
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String email = "";
		String password = "";
		
		User user = MRSApplicationContext.getInstance().getBean("User", User.class);
		
		Map<String, String[]> params = request.getParameterMap();
		if (params != null) {
			String emailStrArr[] = params.get("email");
			if (null != emailStrArr && emailStrArr.length > 0)
				email = emailStrArr[0];
			String passwordStrArr[] = params.get("password");
			if (null != passwordStrArr && passwordStrArr.length > 0)
				password = passwordStrArr[0];
		}

		if (email.length() > 0 && password.length() > 0) {


			UserDao userDao = MRSApplicationContext.getInstance().getBean("UserDao", JdbcUserDao.class);

			try {
			user = userDao.auth(email, password);
			user.setUserShoes(userDao.getShoes(user.getId()));
			} catch (EmptyResultDataAccessException e) {
				logger.debug("No such user " + email);
			}
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String userStr = gson.toJson(user);
			logger.debug("returning: " + userStr);
			PrintWriter out = response.getWriter();
			out.println(userStr);
		}

	}

}
