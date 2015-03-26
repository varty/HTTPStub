package com.http.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;

import com.http.db.DatabaseData;
import com.http.parser.ParserInterface;
import com.http.properties.PropertiesData;
import com.sun.net.httpserver.HttpExchange;

public class StubHandlerImpl implements StubHandler {

	private ParserInterface parser;
	private BufferedReader bufReader;
	private DatabaseData db;

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		setDB(PropertiesData.getDBInterface());
		setParser(PropertiesData.getParser());
		getRequest(exchange);
		response(exchange);
	}

	public void setParser(ParserInterface parser){
		this.parser=parser;
		this.parser.setDBInterface(db);
	}
	
	public void setDB(DatabaseData db){
		this.db=db;
	}
	
	private void getRequest(HttpExchange exchange) throws IOException{
		String inputHttpBody;
		bufReader=new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
		inputHttpBody=saveToString();
		db.setXML(inputHttpBody);
		if (inputHttpBody!=null){
			parse(inputHttpBody);
		}else{
			db.saveHistoryNull();
		}
	}
	
	private void response(HttpExchange exchange) throws IOException{
		exchange.getResponseHeaders().put("Content-Type", Arrays.asList("text/xml;charset=UTF-8"));
		exchange.getResponseHeaders().put("Connection", Arrays.asList("close"));
		String responseString=db.getResponseString();
		if (responseString==null)
			exchange.sendResponseHeaders(200, 0);
		else{
			OutputStream os=exchange.getResponseBody();
			exchange.sendResponseHeaders(200, responseString.getBytes().length);
			os.write(responseString.getBytes());
			os.close();
		}
		exchange.close();
	}

	private String saveToString() throws IOException{
		String inputString;
		StringBuffer strBuffer=new StringBuffer();
		while((inputString=bufReader.readLine())!=null){
			strBuffer.append(inputString);
		}
		if (strBuffer.length()>0) 
			return strBuffer.toString();
		else return null;
	}
	
	private void parse(String body){
		parser.parse(new ByteArrayInputStream(body.getBytes()));
	}

}
