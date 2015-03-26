package com.http.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;

import com.http.db.DatabaseData;
import com.http.parser.ParserInterface;
import com.sun.net.httpserver.HttpExchange;

public class StubHandlerImpl implements StubHandler {

	private ParserInterface parser;
	private BufferedReader bufReader;
	private DatabaseData db;
	private String emptyRequest="Empty";

	@Override
	public void handle(HttpExchange exchange) throws IOException {
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
		String inputHttpBody=null;
		bufReader=new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
		inputHttpBody=saveToString();
		if (inputHttpBody!=null){
			parse(inputHttpBody);
			db.saveHistory(inputHttpBody);
		}else{
			db.saveHistory(emptyRequest);
		}
	}
	
	private void response(HttpExchange exchange){
		exchange.getResponseHeaders().put("Content-Type", Arrays.asList("text/xml;charset=UTF-8"));
		exchange.getResponseHeaders().put("Connection", Arrays.asList("close"));
		OutputStream os=exchange.getResponseBody();
		String responseString=db.getResponseString();	
		try{
			exchange.sendResponseHeaders(200, responseString.getBytes().length);
			os.write(responseString.getBytes());
			os.close();
		}catch(IOException e){
			e.printStackTrace();
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
