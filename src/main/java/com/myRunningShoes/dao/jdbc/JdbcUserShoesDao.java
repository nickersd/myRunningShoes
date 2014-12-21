package com.myRunningShoes.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.myRunningShoes.dao.UserShoesDao;
import com.myRunningShoes.model.User;
import com.myRunningShoes.model.UserShoes;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
        String sql = "update USER_SHOES set miles = ? where user_id = ? and shoe_id = ? and id = ?;";   
        jdbcTemplate.update(sql,  new Object[] {shoe.getMiles(), shoe.getUser_id(), shoe.getShoeId(), shoe.getUserShoesId()  });
         
    }
    
    @Override
    public UserShoes getShoe(int userId, int shoeId) {
    	String sql = "SELECT distinct us.user_id, us.id, us.shoe_id, s.make, s.model, us.miles, us.is_active from USER u, SHOE s, USER_SHOES us " +
    			"where u.id = ? and us.id = ? and u.id = us.user_id and s.id = us.shoe_id and us.is_active = true;";
        return jdbcTemplate.queryForObject(sql,  new Object[] { userId, shoeId }, new UserShoesMapper());

    }
    
    private static final class UserShoesMapper implements RowMapper<UserShoes> {

        public UserShoes mapRow(ResultSet rs, int rowNum) throws SQLException {

            UserShoes userShoes = new UserShoes();
            userShoes.setUserShoesId(rs.getInt("id"));
            userShoes.setShoeId(rs.getInt("shoe_id"));
            userShoes.setUser_id(rs.getInt("user_id"));
            userShoes.setMake(rs.getString("make"));
            userShoes.setModel(rs.getString("model"));
            userShoes.setMiles(rs.getInt("miles"));
            userShoes.setIs_active(rs.getInt("is_active"));

            return userShoes;
        }
        
    }
}
