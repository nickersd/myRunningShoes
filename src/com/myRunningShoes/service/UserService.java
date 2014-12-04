package com.myRunningShoes.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myRunningShoes.dao.UserDao;
import com.myRunningShoes.dao.jdbc.JdbcUserDao;
import com.myRunningShoes.model.User;

/**
 * Servlet implementation class AnimalService
 */
@WebServlet("/User")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:app-context-xml.xml");
        ctx.refresh();
        
        UserDao userDao = ctx.getBean("UserDao", JdbcUserDao.class);
        User user = userDao.getUser(1);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userStr = gson.toJson(user);
        //logger.debug(userStr);
        PrintWriter out = response.getWriter();
        out.println(userStr);	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
