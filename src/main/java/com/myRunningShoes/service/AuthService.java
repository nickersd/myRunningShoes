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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myRunningShoes.dao.UserDao;
import com.myRunningShoes.model.User;

@WebServlet("/auth")
public class AuthService  extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(AuthService.class);

	@Autowired
	private User user;

	@Autowired
	private UserDao userDao;
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String email = "";
		String password = "";

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

			try {
			user = userDao.auth(email, password);
			user.setUserShoes(userDao.getShoes(user.getId()));
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String userStr = gson.toJson(user);
			logger.debug("returning: " + userStr);
			PrintWriter out = response.getWriter();
			out.println(userStr);
			} catch (EmptyResultDataAccessException e) {
				logger.debug("No such user " + email);
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}

		}

	}

}
