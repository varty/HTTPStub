package com.http.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;

import com.http.db.DBInterface;
import com.http.parser.ParserInterface;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HttpStubHandler implements HttpHandler {

	private ParserInterface parser;
	private BufferedReader bufReader;
	private DBInterface db;
	
	public HttpStubHandler(DBInterface db){
		this.db=db;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		getRequest(exchange);
		response(exchange);
	}

	public void setParser(ParserInterface parser){
		this.parser=parser;
	}
	
	private void getRequest(HttpExchange exchange) throws IOException{
		String inputHttpBody=null;
		bufReader=new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
		inputHttpBody=saveToString();
		if (inputHttpBody!=null){
			//TODO Save input body to DB method
			parse(inputHttpBody);
		}
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

}
