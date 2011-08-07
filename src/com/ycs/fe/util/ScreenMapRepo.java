package com.ycs.fe.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ycs.struts.mock.ServletActionContext;

 
 

public class ScreenMapRepo {
	private static  Logger logger = Logger.getLogger(ScreenMapRepo.class);
	
	
	/**
	 * This returns the mappings of screenName to XML config
	 * @param scrName
	 * @return path of mapping XML
	 */
	public static String findMapXMLPath(String scrName){
		String path = null;
		String screenpath = ServletActionContext.getServletContext().getRealPath("WEB-INF/classes/map"); 
		
//		InputStream scrxml = ScreenMapRepo.class.getResourceAsStream("/screenmap.xml");


		Document doc;
		try {
			InputStream scrxml = new BufferedInputStream(new FileInputStream(screenpath+"\\screenmap.xml"));
		
			doc = new SAXReader().read(scrxml);
			Element root = doc.getRootElement();
			Element n = (Element) root.selectSingleNode("screen[@name='"+scrName+"']");
			if(n == null){
				logger.debug("Mapping of <screen name="+scrName+" /> is not defined in screenmap.xml!");
				return null;
			}
			
			path = n.attributeValue("mappingxml");
			String tplpath = ServletActionContext.getServletContext().getRealPath("WEB-INF/classes");
			
			File f = new File(tplpath+"/"+path);
			path = f.getAbsolutePath();
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return path;
	}
	
	public static Element findMapXMLRoot(String scrName){
		String path = findMapXMLPath(scrName);
		Element root = null;
		try {
			Document doc = new SAXReader().read(path);
			root = doc.getRootElement();
		} catch (DocumentException e) {
			logger.debug("XML Load Exception path="+path+" ScreenName="+scrName);
		}
		return root;
	}
	
	public static void main(String[] args) {
		System.out.println(ScreenMapRepo.findMapXMLPath("ProgramSetup"));
	}
}
