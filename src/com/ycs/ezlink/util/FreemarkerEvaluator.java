package com.ycs.ezlink.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerEvaluator {
	private static Log logger = LogFactory.getLog(FreemarkerEvaluator.class);
	
	public String process(String templateStr, JSONObject model){
		 StringTemplateLoader stringLoader = new StringTemplateLoader();
	        String firstTemplate = "firstTemplate";
			stringLoader.putTemplate(firstTemplate, templateStr );
	        // It's possible to add more than one template (they might include each other)
	        // String secondTemplate = "<#include \"greetTemplate\"><@greet/> World!";
	        // stringLoader.putTemplate("greetTemplate", secondTemplate);
	        Configuration cfg1 = new Configuration();
	        cfg1.setTemplateLoader(stringLoader);
	        
	        StringWriter str = new StringWriter();
			try {
				Template template = cfg1.getTemplate(firstTemplate);
				template.process(model, str); str.flush();
				
			} catch (IOException e) {
				logger.error("freemarker templat reading IO exception",e);
			} catch (TemplateException e) {
				logger.error("freemarker templat evaluating exception",e);
			}
	        
	        return str.toString();
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public static void main(String[] args) throws IOException, TemplateException {
		 StringTemplateLoader stringLoader = new StringTemplateLoader();
	        String firstTemplate = "firstTemplate";
	        String freemarkerTemplate = "this is my template ${user} ";
			stringLoader.putTemplate(firstTemplate, freemarkerTemplate );
	        // It's possible to add more than one template (they might include each other)
	        // String secondTemplate = "<#include \"greetTemplate\"><@greet/> World!";
	        // stringLoader.putTemplate("greetTemplate", secondTemplate);
	        Configuration cfg1 = new Configuration();
	        cfg1.setTemplateLoader(stringLoader);
	        
	        Template template = cfg1.getTemplate(firstTemplate);
	        
	        
	        Map root = new JSONObject();
	        root.put("user", "Big Joe");
	        StringWriter str = new StringWriter();
	        
	        template.process(root, str); str.flush();
	        System.out.println(str.toString());
	}

}
