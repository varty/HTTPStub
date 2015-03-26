package com.http.db;

import java.net.MalformedURLException;
import java.sql.SQLException;

import com.http.properties.PropertiesData;

public class DatabaseDataImpl implements DatabaseData{
	
	private Connect connect;
	private String response;
	
	public DatabaseDataImpl(){
		connect=PropertiesData.getConnect();
		connect.setLogin(PropertiesData.getLogin());
		connect.setPassword(PropertiesData.getPassword());
		connect.setURLDB(PropertiesData.getURL());
		connect.setURLJDBC(PropertiesData.getURLJDBC());
		connect.setDriver(PropertiesData.getDriver());
		createConnection();
	}

	public String getResponseString() {
		if(response==null || response.length()==0)
			response=getDefaultResponse();
		return response;
	}
	
	@Override
	public void setResponseString(String key) {
		if (key==null || key.length()==0){
			response=getDefaultResponse();
		}else{
			try {
				response=connect.getResponse(key);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void saveHistory(String body) {
		try {
			connect.saveHistory(body, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String getDefaultResponse() {
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<string>I can't understand you</string>";		
	}
	
	private void createConnection(){
		try {
			connect.createConnection();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
