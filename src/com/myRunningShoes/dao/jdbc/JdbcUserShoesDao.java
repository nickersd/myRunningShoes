package com.myRunningShoes.dao.jdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.myRunningShoes.dao.UserShoesDao;
import com.myRunningShoes.model.UserShoes;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class JdbcUserShoesDao implements UserShoesDao, InitializingBean
{
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    
    public void afterPropertiesSet() throws Exception {
        if (dataSource == null) {
            throw new BeanCreationException("Must set dataSource on ContactDao");
        }
    }
    
    @Override
    public void saveMiles(UserShoes shoe) {
        String sql = "update user_shoes set miles = ? where user_id = ? and shoe_id = ? and id = ?;";   
        jdbcTemplate.update(sql,  new Object[] {shoe.getMiles(), shoe.getUser_id(), shoe.getShoe_id(), shoe.getUserShoesId()  });
         
    }
}
