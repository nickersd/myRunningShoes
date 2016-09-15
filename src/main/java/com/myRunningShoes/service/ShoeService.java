package com.myRunningShoes.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
import com.myRunningShoes.dao.ShoesDao;
import com.myRunningShoes.model.Shoe;

@WebServlet("/shoe")
public class ShoeService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(ShoeService.class);

	@Autowired
	private ShoesDao shoesDao;

	/**
	 * Handle GETs. Expected URI is <url>/myRunningShoes/shoe
	 * 
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Shoe> shoeList = new ArrayList<Shoe>();

		try {
			shoeList = shoesDao.getAvailableShoes();
		} catch (EmptyResultDataAccessException e) {
			logger.debug("No available shoes");
		}
		

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonShoeArrStr = gson.toJson(shoeList);
		logger.debug("returning: " + jsonShoeArrStr);
		PrintWriter out = response.getWriter();
		out.println(jsonShoeArrStr);
	}

}
