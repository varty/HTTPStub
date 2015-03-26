package com.http.db;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectImpl implements Connect {

	private String pass;
	private String login;
	private String url;
	private String urlJdbc;
	private String driver;
	private Connection conn;
	private URLClassLoader cl;
	
	private final String getResponseQuery="SELECT ResponseBody, ResponseID FROM responseset WHERE KeyString = ?";
	private final String saveHistory="INSERT INTO history (RequestBody, Response) VALUES (?,?)";
	private final String saveHistoryNull="INSERT INTO history (RequestBody) VALUES (?)";
	
	@Override
	public void setPassword(String pass) {
		this.pass=pass;
	}

	@Override
	public void setLogin(String login) {
		this.login=login;
	}

	@Override
	public void setURLDB(String urlDb) {
		this.url=urlDb;
	}

	@Override
	public void setURLJDBC(String urlJdbc) {
		this.urlJdbc=urlJdbc;
	}

	@Override
	public void setDriver(String driver) {
		this.driver=driver;
	}

	@Override
	public void createConnection() throws MalformedURLException, InstantiationException, 
								IllegalAccessException, ClassNotFoundException, SQLException {
		cl = new URLClassLoader(new URL[]{new URL(urlJdbc)});
		Driver drv = (Driver) cl.loadClass(driver).newInstance();
		conn=drv.connect(url+"?user="+login+"&password="+pass, null);
		DriverManager.registerDriver(drv);
	}

	@Override
	public void closeConnection() throws IOException {
		cl.close();
	}

	@Override
	public void saveHistory(String body, String response) throws SQLException {
		PreparedStatement historyStat=conn.prepareStatement(saveHistory);
		historyStat.setString(1, body);
		historyStat.setString(2, response);
		historyStat.executeUpdate();
		historyStat.close();
	}
	
	@Override
	public void saveHistory(String body) throws SQLException {
		PreparedStatement historyStat=conn.prepareStatement(saveHistoryNull);
		historyStat.setString(1, body);
		historyStat.executeUpdate();
		historyStat.close();
	} 
	
	@Override
	public String getResponse(String key, String xml) throws SQLException{
		PreparedStatement responseStat=conn.prepareStatement(getResponseQuery);
		responseStat.setString(1, key);
		ResultSet rs=responseStat.executeQuery();
		rs.next();
		saveHistory(xml,rs.getString("ResponseID"));
		String response=rs.getString("ResponseBody");
		responseStat.close();
		return response;
	}
	
}
