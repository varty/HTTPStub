package com.http.db;

public interface DatabaseData {
	
	public String getResponseString();
	
	public void setResponseString(String key);
	
	public void setXML(String xml);
	
	void saveHistoryNull();
}
