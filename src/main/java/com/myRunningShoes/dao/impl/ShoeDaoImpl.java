package com.myRunningShoes.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.myRunningShoes.dao.ShoesDao;
import com.myRunningShoes.model.Shoe;
import org.springframework.stereotype.Repository;

@Repository
public class ShoeDaoImpl implements ShoesDao, InitializingBean {

	private JdbcTemplate jdbcTemplate;

	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}

	public void afterPropertiesSet() throws Exception {
		if (dataSource == null) {
			throw new BeanCreationException("Must set dataSource on ContactDao");
		}
	}
	
    @Override
	public List<Shoe> getAvailableShoes() {
    	String sql = "select * from SHOE";
        return jdbcTemplate.query(sql, new ShoesMapper());

	}
    private static final class ShoesMapper implements ResultSetExtractor<List<Shoe>> {

        public List<Shoe> extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            List<Shoe> shoeList = new ArrayList<Shoe>();
            
            Shoe shoe = null;
            while (rs.next()) {
            	shoe = new Shoe(rs.getString("make"), rs.getString("model"), 
            			rs.getInt("year"), rs.getInt("id"));
            	shoeList.add(shoe);
            }
            
    return shoeList;
        }
    }
}
