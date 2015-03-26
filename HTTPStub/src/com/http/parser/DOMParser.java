package com.http.parser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.http.db.DatabaseData;
import com.http.properties.PropertiesData;


public class DOMParser implements ParserInterface {
	
	private DatabaseData db;

	public void parse(InputStream in){
		DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = dbFactory.newDocumentBuilder();
			Document doc=docBuilder.parse(in);
			in.close();
			
			XPathFactory xpFactory=XPathFactory.newInstance();
			XPath path=xpFactory.newXPath();
			db.setResponseString(analys(doc, path));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private String analys(Document doc, XPath path){
		String expression=PropertiesData.getXPath();
		try {
			String key=path.evaluate(expression, doc);
			return key;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return "Expression is incorrect";
	}

	@Override
	public void setDBInterface(DatabaseData db) {
		this.db=db;
	}

}
