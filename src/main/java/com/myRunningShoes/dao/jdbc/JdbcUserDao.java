package com.myRunningShoes.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.myRunningShoes.dao.UserDao;
import com.myRunningShoes.model.User;
import com.myRunningShoes.model.UserShoes;



public class JdbcUserDao implements UserDao, InitializingBean 
{
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private ApplicationContext appContext;
    
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
    public User getUser(int user_id) {
        String sql = "select id, first_name, last_name from USER where id = :user_id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("user_id", user_id);

        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new UserMapper());
    }
    
    
    private static final class UserMapper implements RowMapper<User> {

        public User mapRow(ResultSet rs, int rowNum) throws SQLException {

            User user = new User(rs.getInt("id"), rs.getString("first_name"),
                    rs.getString("last_name"));

            return user;
        }
        
    }
    
    @Override
    public List<UserShoes> getShoes(int user_id) {
        String sql = "select distinct us.id, us.shoe_id, us.id, us.user_id, s.make, s.model, s.year, us.miles, us.is_active from USER u, SHOE s, USER_SHOES us " +
                "where u.id = ? and u.id = us.user_id and s.id = us.shoe_id and us.is_active = true";

        return jdbcTemplate.query(sql,  new Object[] { user_id }, new UserShoesMapper());

    }

    private static final class UserShoesMapper implements ResultSetExtractor<List<UserShoes>> {

        public List<UserShoes> extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            Map<Integer, UserShoes> map = new HashMap<Integer, UserShoes>();
     
            UserShoes userShoes = null;
            while (rs.next()) {
                int shoeId = rs.getInt("shoe_id");
                userShoes = map.get(shoeId);
                if (userShoes == null) {  
                    userShoes = new UserShoes();
                    userShoes.setUserShoesId(rs.getInt("id"));
                    userShoes.setShoeId(rs.getInt("shoe_id"));
                    userShoes.setUser_id(rs.getInt("user_id"));
                    userShoes.setMake(rs.getString("make"));
                    userShoes.setModel(rs.getString("model"));
                    userShoes.setYear(rs.getInt("year"));
                    userShoes.setMiles(rs.getInt("miles"));
                    userShoes.setIs_active(rs.getInt("is_active"));
                    map.put(shoeId, userShoes);
                }
            }
    
            return new ArrayList<UserShoes> (map.values());
        }
    }
    
    @Override
    public void insertShoe(UserShoes shoe) {
        String sql = "insert into USER_SHOES (user_id, shoe_id, date_purchased, "
                + "miles, is_active) values (?,?, ?), ?, 1);";   
        jdbcTemplate.update(sql,  new Object[] { shoe.getUser_id(), shoe.getUserShoesId(), shoe.getDate_purchased(),
                shoe.getMiles()});
    
    }
    
    @Override
    public void addMiles(UserShoes shoe) {
        String sql = "update USER_SHOES set miles = ? from USER u, USER_SHOES us " +
    "where u.id = ? and u.id = us.user_id and us.shoe_id = ?, and us.id = ?";
        jdbcTemplate.update(sql, new Object[] { shoe.getMiles(), shoe.getUser_id(), shoe.getShoeId(),
                shoe.getUserShoesId()});
    }

    @Override
    public void deleteShoe(UserShoes shoe) {
        String sql = "update USER_SHOES set is_active = 0 where user_id = ?, and id = ?";
        
        jdbcTemplate.update(sql, new Object[] {shoe.getUser_id(), shoe.getUserShoesId()});
        
    }
}
