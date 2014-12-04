package com.myRunningShoes.integrationTest;

import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myRunningShoes.dao.UserDao;
import com.myRunningShoes.dao.UserShoesDao;
import com.myRunningShoes.dao.jdbc.JdbcUserDao;
import com.myRunningShoes.dao.jdbc.JdbcUserShoesDao;
import com.myRunningShoes.model.User;
import com.myRunningShoes.model.UserShoes;

import org.apache.log4j.Logger;


public class MRSTestDao
{
    final static Logger logger = Logger.getLogger(MRSTestDao.class);

    public static void main(String[] args)
    {

        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:app-context-xml.xml");
        ctx.refresh();
        
        UserDao userDao = ctx.getBean("UserDao", JdbcUserDao.class);
        UserShoesDao userShoesDao = ctx.getBean("UserShoesDao", JdbcUserShoesDao.class);

        User user = userDao.getUser(1);
        logger.info("User with id 1 is: " + user.getFirstName() + " " + user.getLastName());
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userStr = gson.toJson(user);
        logger.info(userStr);
        
        List<UserShoes> shoes = userDao.getShoes(user.getId());
        for(UserShoes userShoes: shoes) {
            logger.info(userShoes.getMake() + " " + userShoes.getModel() + ": " + userShoes.getMiles());
        }

        for(UserShoes userShoes: shoes) {
            if (userShoes.getShoe_id() == 1) {
                userShoes.setMiles(userShoes.getMiles() + 10);
                logger.info("After running 10 miles" + userShoes.getMake() + " "
                        + userShoes.getModel() + "has " + userShoes.getMiles() + " miles");
                userShoesDao.saveMiles(userShoes);
                
                
            }

        }
        
        
    }

}
