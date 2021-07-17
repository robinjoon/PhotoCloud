package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class DeviceDao {
	private JdbcTemplate jdbcTemplate;
	public DeviceDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<String> getDeviceList(){
		String sql = "show tables where Tables_in_photocloud REGEXP '_'";
		List<String> tableList = jdbcTemplate.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				return rs.getString("Tables_in_photocloud");
			}
			
		});
		List<String> deviceList = new ArrayList<String>();
		for(int i=0;i<tableList.size();i++) {
			StringTokenizer st = new StringTokenizer(tableList.get(i),"_");
			String device = st.nextToken();
			if(!deviceList.contains(device))
				deviceList.add(device);
		}
		return deviceList;
	}
	
	public List<String> getalbumList(String device){
		String sql = "show tables where Tables_in_photocloud REGEXP '"+device+"_'";
		List<String> tableList = jdbcTemplate.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				return rs.getString("Tables_in_photocloud");
			}
			
		});
		List<String> albumList = new ArrayList<String>();
		for(int i=0;i<tableList.size();i++) {
			StringTokenizer st = new StringTokenizer(tableList.get(i),"_");
			st.nextToken();
			albumList.add(st.nextToken());
		}
		return albumList;
	}
}
