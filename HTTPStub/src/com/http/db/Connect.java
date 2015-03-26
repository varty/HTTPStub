package com.http.db;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

public interface Connect {
	
	public void setLogin(String login);
	
	public void setPassword(String pass);
	
	public void setURLJDBC(String urlJdbc);
	
	public void setURLDB(String urlDb);
	
	public void setDriver(String driver);
	
	public void createConnection() 
			throws MalformedURLException, InstantiationException, 
			IllegalAccessException, ClassNotFoundException, SQLException;
	
	public void closeConnection() throws IOException;

	public void saveHistory(String body, String response) throws SQLException;
	
	public void saveHistory(String body) throws SQLException;
	
	public String getResponse(String key) throws SQLException;
}
