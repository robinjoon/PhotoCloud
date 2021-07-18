package dao;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import secure.Secure;

public class AuthDao {
	private JdbcTemplate jdbcTemplate;

	public AuthDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public boolean checkToken(String id, String token) {
		String sql = "select token from auth where id = ?";
		String tokenInDB = jdbcTemplate.query(sql, new RowMapper<String>() {
		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("token");
		}},id).get(0);
		
		if(token.equals(tokenInDB)) {
			return true;
		}else {
			return false;
		}
	}
	
	public String getToken(String id, String pw) throws DataAccessException, NoSuchAlgorithmException {
		String sql = "select readToken,uploadToken from auth where id =? and pw = ?";
		String token = jdbcTemplate.query(sql, new RowMapper<String>() {
		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("token");
		}
		},id,Secure.sha256(pw)).get(0);
		return token;
	}
	
}
