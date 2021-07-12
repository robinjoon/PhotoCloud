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
	
	public ContentDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public boolean insert(Content content) {
		String sql = "insert into contents(device,album,originalFileName,systemFileName) values(?,?,?,?)";
		int affectedRowCount = 0;
		try {
			affectedRowCount = jdbcTemplate.update(sql, content.getDevice(),content.getAlbum(),content.getOriginalFileName(),content.getSystemFileName());
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(affectedRowCount==1)
			return true;
		else	
			return false;
	}
	public Content get(String device, String album, int contentId) {
		String sql = "select * from contents where device = ? and album = ? and contentId = ?";
		Content content = jdbcTemplate.query(sql, new ContentRowMapper<Content>(),device,album,contentId).get(0);
		return content;
	}
	
	public List<Content> get(String device, String album, int startId, int endId){
		String sql = "select * from contents where device = ? and album = ? and contentId >= ? and contentId <= ?";
		List<Content> contents = jdbcTemplate.query(sql, new ContentRowMapper<Content>(),device,album,startId,endId);
		return contents;
	}
	
	private class ContentRowMapper<T extends Content> implements RowMapper<T>{
		@Override
		public T mapRow(ResultSet rs, int rowNum) throws SQLException {
			@SuppressWarnings("unchecked")
			T t = (T) new Content(rs.getString("device"),rs.getString("album"),
					rs.getString("originalFileName"),rs.getString("systemFileName"));
			t.setContentId(rs.getInt("contentId"));
			return t;
		}
	}
}
