package util.db;

import java.sql.Connection;

public interface IBaseDAO {
	Connection getConnection();
	void commit();
	void rollBack();
	void closeConnection();
	void setAutoCommit(boolean flag);
}
