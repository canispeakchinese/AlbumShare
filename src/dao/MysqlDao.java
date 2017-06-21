package dao;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public interface MysqlDao {
	void update(String sql, Object... args);
	SqlRowSet query(String sql, Object... args);
}
