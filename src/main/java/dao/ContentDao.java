package dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import dto.*;
public class ContentDao {
	private JdbcTemplate jdbcTemplate;
	private static final String originName = "devicealbum";
	public ContentDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public boolean insert(Content content) {
		String tableName = content.getDevice()+"_"+content.getAlbum();
		copyTable(tableName, originName);
		String sql = "insert into "+tableName+"(device,album,originalFileName,systemFileName,timestamp,locate,url) values(?,?,?,?,?,?,?)";
		int affectedRowCount = 0;
		try {
			affectedRowCount = jdbcTemplate.update(sql,content.getDevice(),content.getAlbum(),
					content.getOriginalFileName(),content.getSystemFileName(),content.getTimestamp(),content.getLocate(),content.getUrl());
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(affectedRowCount==1)
			return true;
		else	
			return false;
	}
	public Content get(String tableName, int contentId) {
		String sql = "select * from "+tableName+" where contentId = ?";
		Content content;
		try {
			content = jdbcTemplate.query(sql, new ContentRowMapper<Content>(),contentId).get(0);
		}catch(Exception e) {
			content = null;
		}
		return content;
	}
	
	public List<Content> get(String tableName, int startID, int endID ){
		String sql = "select * from "+tableName+" where contentId >= ? and contentId <= ?";
		List<Content> contents;
		try {
			contents = jdbcTemplate.query(sql, new ContentRowMapper<Content>(),startID,endID);

		}catch (Exception e) {
			contents = null;
		}
		return contents;
	}
	
	public List<Content> getPage(String tableName, int page){
		String sql = "select * from "+tableName+" where contentId >= ? and contentId <= ?";
		List<Content> contents = jdbcTemplate.query(sql, new ContentRowMapper<Content>(),(page-1)*6 + 1,page*6);
		return contents;
	}
	public int getContentCount(String tableName) {
		String sql = "select count(*) as count from "+tableName;
		return jdbcTemplate.query(sql,new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("count");
			}
		}).get(0);
	}
	
	private class ContentRowMapper<T extends Content> implements RowMapper<T>{
		@Override
		public T mapRow(ResultSet rs, int rowNum) throws SQLException {
			@SuppressWarnings("unchecked")
			T t = (T) new Content(rs.getString("device"),rs.getString("album"),
					rs.getString("originalFileName"),rs.getString("systemFileName"));
			t.setContentId(rs.getInt("contentId"));
			t.setTimestamp(rs.getTimestamp("timestamp"));
			t.setLocate(rs.getString("locate"));
			t.setUrl(rs.getString("url")+rs.getInt("contentId"));
			t.setThumbNailUrl(rs.getString("url")+rs.getInt("contentId")+"/thumbnail");
			return t;
		}
	}
	private boolean copyTable(String copyName, String originName) {
		String sql = "CREATE TABLE IF NOT EXISTS "+copyName+" LIKE "+originName;
		int affectedRowCount = 0;
		try {
			affectedRowCount = jdbcTemplate.update(sql);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(affectedRowCount==1)
			return true;
		else	
			return false;
	}
}
