package com.http.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.http.db.DBInterface;
import com.http.server.HttpStubHandler;
import com.sun.net.httpserver.HttpHandler;

public class PropertiesData {
	
	private static String propertiesName="stub.properties";
	
	private static String PORT="Port";
	private static String CONTEXT="Context";
	private static String LOGIN="LoginDB";
	private static String PASSWORD="PasswordDB";
	private static String DRIVER="DriverDB";
	private static String URL="URL";
	private static String XML_ELEMENT="XMLElement";
	
	private static File propertiesFile;
	private static Properties properties;
	
	
	private PropertiesData(){}
	
	private static Properties getDefault(){
		propertiesFile=new File("/", propertiesName);
		Properties defaultSettings=new Properties();
		defaultSettings.put(PORT, 8000);
		defaultSettings.put(CONTEXT, "/test");
		defaultSettings.put(LOGIN, "user");
		defaultSettings.put(PASSWORD, "password");
		defaultSettings.put(DRIVER, "com.mysql.jdbc.Driver");
		defaultSettings.put(URL, "jdbc:mysql://hostname:port/dbname");
		defaultSettings.put(XML_ELEMENT,"element");
		
		FileOutputStream fos;
		try {
			fos=new FileOutputStream(propertiesFile);
			defaultSettings.store(fos, "Programm Properties");
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return defaultSettings;
			
	}
	
	public static void setFileProperties(String path){
		propertiesFile=new File(path);
	}
	
	public static void readSettings(){
		if(!propertiesFile.exists()){
			properties=getDefault();
		}else{
			FileInputStream fis;
			try {
				fis=new FileInputStream(propertiesFile.getAbsolutePath());
				properties=new Properties();
				properties.load(fis);
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static int getPort(){
		return Integer.parseInt(properties.getProperty(PORT));
	}
	
	public static String getContext(){
		return properties.getProperty(CONTEXT);
	}
	
	public static String getDriver(){
		return properties.getProperty(DRIVER);
	}
	
	public static String getLogin(){
		return properties.getProperty(LOGIN);
	}
	
	public static String getPassword(){
		return properties.getProperty(PASSWORD);
	}
	
	public static String getURL(){
		return properties.getProperty(URL);
	}
	
	
	public static String[] getXMLElementAddress(){
		return properties.getProperty(XML_ELEMENT).split("/");
	}
	
	public static HttpHandler getHttpHandler(){
		return new HttpStubHandler(getDBInterface());
	}
	
	public static DBInterface getDBInterface(){
		//TODO create return object
		return null;
	}
}
