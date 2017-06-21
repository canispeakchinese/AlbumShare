package dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class MysqlDaoImpl implements MysqlDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public SqlRowSet query(String sql, Object... args) {
		return jdbcTemplate.queryForRowSet(sql, args);
	}
	
	@Override
	public void update(String sql, Object... args) {
		jdbcTemplate.update(sql, args);
	}
}
