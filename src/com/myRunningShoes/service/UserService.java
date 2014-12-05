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

import org.apache.commons.httpclient.URIException;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myRunningShoes.dao.UserDao;
import com.myRunningShoes.dao.UserShoesDao;
import com.myRunningShoes.dao.jdbc.JdbcUserDao;
import com.myRunningShoes.dao.jdbc.JdbcUserShoesDao;
import com.myRunningShoes.model.User;

/**
 * Servlet implementation class AnimalService
 */
@WebServlet("/user")
public class UserService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(UserService.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserService() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int userId = 0;
		User user = new User();
		
		Map<String, String[]> params = request.getParameterMap();
		// String uri = request.getRequestURI();
		if (params != null) {
			String UserIdStrArr[] = params.get("userId");
			if (null != UserIdStrArr && UserIdStrArr.length > 0)
				userId = Integer.parseInt(UserIdStrArr[0]);
		}
		/*
		 * try { params = URLEncodedUtils.parse(new URI(uri), "UTF-8"); } catch
		 * (URISyntaxException e) { logger.error("bad URI format"); }
		 * 
		 * for (NameValuePair param : params) { if
		 * (param.getName().equalsIgnoreCase("user")) { userId =
		 * Integer.getInteger(param.getValue()); } }
		 */
		if (userId != 0) {
			GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
			ctx.load("classpath:app-context-xml.xml");
			ctx.refresh();

			UserDao userDao = ctx.getBean("UserDao", JdbcUserDao.class);

			try {
			user = userDao.getUser(userId);
			user.setUserShoes(userDao.getShoes(userId));
			} catch (EmptyResultDataAccessException e) {
				user = new User(userId);
			}
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String userStr = gson.toJson(user);
			logger.debug(userStr);
			PrintWriter out = response.getWriter();
			out.println(userStr);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
