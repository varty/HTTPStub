package com.http.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.http.db.Connect;
import com.http.db.ConnectImpl;
import com.http.db.DatabaseDataImpl;
import com.http.db.DatabaseData;
import com.http.parser.DOMParser;
import com.http.parser.ParserInterface;
import com.http.server.StubHandlerImpl;
import com.http.server.StubHandler;

public final class PropertiesData {
	
	private static String propertiesName="example.properties";
	
	private static String PORT="Port";
	private static String CONTEXT="Context";
	private static String LOGIN="LoginDB";
	private static String PASSWORD="PasswordDB";
	private static String DRIVER="DriverDB";
	private static String URL="URL";
	private static String URL_JDBC="URL_JDBC";
	private static String X_PATH="X_Path";
	
	private static File propertiesFile;
	private static Properties properties;
	
	
	private PropertiesData(){}
	
	private static Properties getDefault(){
		Properties defaultSettings=new Properties();
		defaultSettings.put(PORT, "8000");
		defaultSettings.put(CONTEXT, "/test");
		defaultSettings.put(LOGIN, "root");
		defaultSettings.put(PASSWORD, "root");
		defaultSettings.put(DRIVER, "com.mysql.jdbc.Driver");
		defaultSettings.put(URL, "jdbc:mysql://localhost:3306/httpstubserver");
		defaultSettings.put(URL_JDBC, "jar:file:///C:/Program Files/MySQL/mysql-connector-java-5.1.35/mysql-connector-java-5.1.35-bin.jar!/");
		defaultSettings.put(X_PATH,"//*");
		
		FileOutputStream fos;
		try {
			fos=new FileOutputStream(propertiesFile);
			defaultSettings.store(fos, "Programm Properties");
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Example properties file created");
		return defaultSettings;
	}
	
	private static void readSettings(){
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
	
	public static void setFileProperties(String path){
		if (path.length()>0)
			propertiesFile=new File(path);
		else propertiesFile=new File("\\", propertiesName);
		readSettings();
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
	
	public static String getURLJDBC(){
		return properties.getProperty(URL_JDBC);
	}
	
	public static String getXPath(){
		return properties.getProperty(X_PATH);
	}
	
	public static ParserInterface getParser(){
		return new DOMParser();
	}
	
	public static StubHandler getStubHandler(){
		return new StubHandlerImpl();
	}
	
	public static DatabaseData getDBInterface(){
		return new DatabaseDataImpl();
	}

	public static Connect getConnect() {
		return new ConnectImpl();
	}
}
