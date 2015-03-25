package com.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.http.properties.PropertiesData;
import com.sun.net.httpserver.HttpServer;

public class HttpStubServer {

	public static void main(String[] args) {
		
		
		HttpStubServer server=new HttpStubServer();
		try {
			server.startServer();
		} catch (IOException e) {
			System.out.println("Port is busy");
			e.printStackTrace();
		}
	}
	
	public void startServer() throws IOException{
    	HttpServer	server = HttpServer.create(new InetSocketAddress(PropertiesData.getPort()), 0);
		server.createContext(PropertiesData.getContext(), PropertiesData.getHttpHandler());
	    server.setExecutor(Executors.newCachedThreadPool());
	    server.start();
	}

}
