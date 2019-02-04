package com.dkarakaya.sample.databasehelper;

import java.sql.*;

public class DatabaseConnection {

	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/sampledb?autoReconnect=true&useSSL=false";
	private static String username = "root";
	private static String pass = "1234";
	private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();

	public static Connection getConnection() {
		Connection connection = connectionHolder.get();
		try {
			if (connection == null) {
				// open the connection (lazy loaded)
				Class.forName(driver);
				connection = DriverManager.getConnection(url, username, pass);
				// store it
				connectionHolder.set(connection);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;

	}

	public static void closeConnection() {
		try {
			Connection connection = connectionHolder.get();
			if (connection != null) {
				// close the connection
				connection.close();
				// remove it from the connection holder
				connectionHolder.remove();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
