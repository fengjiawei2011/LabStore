package cmpe282.lab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {
	
	private String dbDriver = "com.mysql.jdbc.Driver";
	private String username = "root";
	private String password = "root";
	private String URL = "jdbc:mysql://localhost/amazionstore";
	private String table = "user";
	
	
	public Connection connectDatabase() {
		Connection connection = null;
		try {
			Class.forName(dbDriver).newInstance();
			connection = DriverManager.getConnection(URL, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	public static void closeAllConnection(ResultSet rs, Statement ps , Connection con){
		MySQL.closeStatement(ps);
		MySQL.closeResultSet(rs);
		MySQL.closeConnection(con);
	}

	public String getTable() {
		return table;
	}
	

}
