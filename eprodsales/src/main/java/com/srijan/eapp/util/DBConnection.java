package com.srijan.eapp.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


//singleton pattern
public class DBConnection {
	
	private static DBConnection dbConnection;
	private static Connection conn;

	private DBConnection() {
	}

	private Properties readDBProps() throws IOException{

        //InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try(InputStream inputStream = loader.getResourceAsStream("application.properties")){
            properties.load(inputStream);
        }
        return properties;

    }

	private Properties readDBProps2() throws IOException {

		Properties properties = new Properties();
		properties.load(new FileInputStream("src/main/resources/application.properties"));
		return properties;

	}

	public static DBConnection getInstance() {
		return new DBConnection();
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException {

		try {
			Properties properties = readDBProps();
			String driver = properties.getProperty("jdbc.driver");
			if (driver != null) {
				Class.forName(driver);
			}

			String url = properties.getProperty("jdbc.url");
			String username = properties.getProperty("jdbc.username");
			String password = properties.getProperty("jdbc.password");

			conn = DriverManager.getConnection(url, username, password);

		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return conn;
	}

	public void closeConnection() {

		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
