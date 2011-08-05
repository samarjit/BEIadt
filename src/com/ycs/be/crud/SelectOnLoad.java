package com.ycs.be.crud;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.google.gson.Gson;
import com.ycs.be.dao.FETranslatorDAO;
import com.ycs.be.dto.PrepstmtDTO.DataType;
import com.ycs.be.dto.PrepstmtDTOArray;
import com.ycs.be.dto.ResultDTO;
import com.ycs.be.util.Constants;
import com.ycs.be.util.ScreenMapRepo;
import com.ycs.struts.mock.ActionContext;

/**
 * Used for prepopulating data onto value stack for. It can be called from any Action class or Interceptor. 
 * @author Samarjit
 *
 */
public class SelectOnLoad {
	private Logger logger = Logger.getLogger(this.getClass());
	
	public void selectOnLoad(String screenName1, JSONObject jsonsubmitdata ){
		if(Constants.CMD_PROCESSOR == Constants.APP_LAYER){
			localSelectOnLoad(  screenName1,   jsonsubmitdata );
		}else{
			remoteSelectOnLoad(  screenName1,   jsonsubmitdata.toString() );
		}
		
	}
	
	public void localSelectOnLoad(String screenName1, JSONObject jsonsubmitdata ){
		String xmlconfigfile =  ScreenMapRepo.findMapXMLPath(screenName1);
		if(screenName1 != null && screenName1.length() >0)	{
			try {
				org.dom4j.Document document1 = new SAXReader().read(xmlconfigfile);
				org.dom4j.Element root = document1.getRootElement();
				//preload select queries
				List nodeList = root.selectNodes("//query");
				logger.debug("query list size:"+nodeList.size());
				for (Iterator queryList = nodeList.iterator(); queryList.hasNext();) {
					org.dom4j.Node node = (org.dom4j.Node) queryList.next();
					logger.debug("Query Node:"+node.getText());
					String stackid = ((org.dom4j.Element) node).attributeValue("stackid");
					String type = ((org.dom4j.Element) node).attributeValue("type");
					String sqlquery = node.getText();
					FETranslatorDAO feDAO = new FETranslatorDAO();
					feDAO.executequery(sqlquery,stackid,type);
					org.dom4j.Element e = (org.dom4j.Element) node;
				
				}
				//preload selectonload queries
				List selonloadnl = root.selectNodes("//selectonload");
				Element elm = (Element) root.selectSingleNode("/root/screen");
				String screenName = elm.attributeValue("name");
				logger.debug("query selectonload list size:"+selonloadnl.size());
				for (Iterator queryList = selonloadnl.iterator(); queryList.hasNext();) {
					org.dom4j.Node queryNode = (org.dom4j.Node) queryList.next();
					logger.debug("Query Node:"+queryNode.getText());
					String stackid = ((org.dom4j.Element) queryNode).attributeValue("outstack");
					String type = ((org.dom4j.Element) queryNode).attributeValue("type");
					String sqlquery = queryNode.getText();
					
					Element errorNode = (Element) queryNode.selectSingleNode("error");
					String errorTemplate = "";
					if(errorNode !=null)errorTemplate=errorNode.attributeValue("message");
					
					Element messageNode = (Element) queryNode.selectSingleNode("message");
					String messageTemplate = "";
					if(messageNode !=null)messageTemplate=messageNode.attributeValue("message");
					
					List<Element> nl = root.selectNodes("//fields/field/*");
					HashMap<String, DataType> hmfielddbtype= new HashMap<String, DataType>();
					QueryParser.populateFieldDBType(nl, hmfielddbtype);
					
					PrepstmtDTOArray arparam = new PrepstmtDTOArray();
					String parsedquery = QueryParser.parseQuery(sqlquery, null, jsonsubmitdata, arparam, hmfielddbtype);
					logger.debug("selonload Query query:"+parsedquery+"\n Expanded prep:"+arparam.toString(parsedquery));
					FETranslatorDAO feDAO = new FETranslatorDAO();
					ResultDTO resDTO = feDAO.executecrud(screenName,parsedquery,stackid,jsonsubmitdata, arparam, errorTemplate, messageTemplate );
					
					logger.debug("resDTO (gson converter)= "+new Gson().toJson(resDTO).toString());
					logger.debug("resDTO (JSONSerializer converter)= "+JSONSerializer.toJSON(resDTO).toString());
					ActionContext.getContext().getValueStack().set("resDTO",new Gson().toJson(resDTO).toString());
					ActionContext.getContext().getValueStack().getContext().put("ZHello", "World");
					ActionContext.getContext().getValueStack().set("ZHello2", "World2");
					org.dom4j.Element e = (org.dom4j.Element) queryNode;
					System.out.println("HTMLProcessor **************** populating value stack");
				}
				
			} catch (DocumentException e) {
				logger.debug("result xml file not readable --",e);
				e.printStackTrace();
			} catch (JSONException e) {
				logger.debug("result xml file not readable --",e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.debug("result xml file not readable --",e);
				e.printStackTrace();
			}
			
			 
			 
			
		}
	}
	
	public void remoteSelectOnLoad(String screenName1, String jsonsubmitdata ){
		
	}
}
