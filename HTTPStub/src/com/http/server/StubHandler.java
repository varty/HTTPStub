package com.http.server;

import com.http.db.DatabaseData;
import com.http.parser.ParserInterface;
import com.sun.net.httpserver.HttpHandler;

public interface StubHandler extends HttpHandler {
	
	public void setParser(ParserInterface parser);
	
	public void setDB(DatabaseData db);
	
}
