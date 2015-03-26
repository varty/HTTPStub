package com.http.parser;

import java.io.InputStream;

import com.http.db.DatabaseData;

public interface ParserInterface {
	
	public void parse(InputStream is);
	
	public void setDBInterface(DatabaseData db);
}
