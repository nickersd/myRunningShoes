package com.myRunningShoes.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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

/**
 * Main servlet 
 */
@WebServlet("/user")
public class UserService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(UserService.class);

	@Autowired
	private User user;

	@Autowired
	private UserDao userDao;


	/**
	 * Handle GETs. Expected URI is <url>/myRunningShoes/user?userId=<#>
	 * 
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int userId = 0;

		Map<String, String[]> params = request.getParameterMap();
		if (params != null) {
			String UserIdStrArr[] = params.get("userId");
			if (null != UserIdStrArr && UserIdStrArr.length > 0)
				userId = Integer.parseInt(UserIdStrArr[0]);
		}

		if (userId != 0) {

			try {
			user = userDao.getUser(userId);
			user.setUserShoes(userDao.getShoes(userId));
			} catch (EmptyResultDataAccessException e) {
				user = new User(userId);
			}
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String userStr = gson.toJson(user);
			logger.debug("returning: " + userStr);
			PrintWriter out = response.getWriter();
			out.println(userStr);
		}
	}

	/**
	 * Unimplemented. 
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
