package com.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.http.properties.PropertiesData;
import com.sun.net.httpserver.HttpServer;

public class HttpStubServer {
	
	private static StubHandler stub;

	public static void main(String[] args) {
		if (args==null){
			System.exit(0);
		}
		PropertiesData.setFileProperties(args[0]);
		stub=PropertiesData.getStubHandler();
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
    	server.createContext(PropertiesData.getContext(), stub);
	    server.setExecutor(Executors.newFixedThreadPool(50));
	    server.start();
	}

}
