package com.http.db;

import java.net.MalformedURLException;
import java.sql.SQLException;

import com.http.properties.PropertiesData;

public class DatabaseDataImpl implements DatabaseData{
	
	private Connect connect;
	private String response;
	private String xml;
	
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
		return response;
	}
	
	public void setXML(String xml){
		this.xml=xml;
	}
	
	@Override
	public void setResponseString(String key) {
		if (key==null || key.length()==0){
			response=getDefaultResponse();
		}else{
			try {
				response=connect.getResponse(key, xml);
			} catch (SQLException e) {
				e.printStackTrace();
				saveHistoryNull();
			}
		}
	}
	
	@Override
	public void saveHistoryNull() {
		response=null;
		try {
			connect.saveHistory(xml);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String getDefaultResponse() {
		return null;		
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
