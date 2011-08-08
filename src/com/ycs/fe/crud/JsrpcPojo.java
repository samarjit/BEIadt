package com.ycs.fe.crud;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.InvalidXPathException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ycs.fe.dao.FETranslatorDAO;
import com.ycs.fe.dto.DataTypeException;
import com.ycs.fe.dto.InputDTO;
import com.ycs.fe.dto.PrepstmtDTO;
import com.ycs.fe.dto.PrepstmtDTOArray;
import com.ycs.fe.dto.ResultDTO;
import com.ycs.fe.dto.PrepstmtDTO.DataType;
import com.ycs.fe.util.ScreenMapRepo;
import com.ycs.struts.mock.ActionContext;
import com.ycs.struts.mock.ValueStack;

public class JsrpcPojo {
private Logger logger = Logger.getLogger(getClass()); 
	public ResultDTO selectData(String screenName, String panelname,  JSONObject jsonObject, InputDTO jsonInput, ResultDTO prevResultDTO) {
		logger.debug("calling first default(first) sqlselect query");
		return selectData(screenName, panelname,"sqlselect", jsonObject, jsonInput, prevResultDTO);
	}
	
	public ResultDTO selectData(String screenName, String panelname,String querynode, JSONObject jsonRecord, InputDTO jsonInput, ResultDTO prevResultDTO) {
		 
		 
		    String pageconfigxml =  ScreenMapRepo.findMapXMLPath(screenName);
//			String tplpath = ServletActionContext.getServletContext().getRealPath("WEB-INF/classes/map");
			String parsedquery = "";
			ResultDTO resultDTO = new ResultDTO();
			try {
				org.dom4j.Document document1 = new SAXReader().read(pageconfigxml);
				org.dom4j.Element root = document1.getRootElement();
				Node crudnode = root.selectSingleNode("//crud");
				Node queryNode = crudnode.selectSingleNode(querynode);
				if(queryNode == null)throw new Exception("<"+querynode+"> node not defined");
				
				String outstack = ((Element) queryNode).attributeValue("outstack"); 
				panelname = outstack;
				
				String updatequery = "";
				updatequery += queryNode.getText();
				
				Element errorNode = (Element) queryNode.selectSingleNode("error");
				String errorTemplate = "";
				if(errorNode !=null)errorTemplate=errorNode.attributeValue("message");
				
				Element messageNode = (Element) queryNode.selectSingleNode("message");
				String messageTemplate = "";
				if(messageNode !=null)messageTemplate=messageNode.attributeValue("message");
				
				List<Element> nodeList = crudnode.selectNodes("//fields/field/*");
				logger.debug("fields size:"+nodeList.size());
				HashMap<String, DataType> hmfielddbtype = new HashMap<String, PrepstmtDTO.DataType>();
				QueryParser.populateFieldDBType(nodeList, hmfielddbtype);
				
				/*Pattern pattern  = Pattern.compile(":(\\w*)",Pattern.DOTALL|Pattern.MULTILINE);
				Matcher m = pattern.matcher(updatequery);
				while(m.find()){
					String val = "";
					logger.debug(m.group(0)+ " "+ m.group(1));
					if(jsonObject.has(m.group(1))){
						val = jsonObject.getString(m.group(1));
						updatequery = updatequery.replaceAll(":"+m.group(1), val);
					}
				}*/
				//SET
				List<Element> primarykeys = crudnode.selectNodes("//fields/field/*[@primarykey]");
				FETranslatorDAO fetranslatorDAO = new FETranslatorDAO();
				//pagination
				Element countqrynode = (Element)queryNode.selectSingleNode("countquery");
				if(countqrynode != null){
					
					String strpagesize = countqrynode.attributeValue("pagesize");
					int pagesize = 0;
					if(strpagesize != null ){
						pagesize = Integer.parseInt(strpagesize);
					}
					String countquery = countqrynode.getText();
					
					
					if(countquery != null){
						PrepstmtDTOArray  arparam = new PrepstmtDTOArray();
						parsedquery = QueryParser.parseQuery(countquery, outstack, jsonRecord, arparam, hmfielddbtype,jsonInput, prevResultDTO);
						int reccount = fetranslatorDAO.executeCountQry(screenName, parsedquery, outstack, arparam);
						logger.debug("Processing count query"+countquery);
						if(reccount > pagesize){
							JSONObject jobject = jsonRecord.getJSONObject("pagination");
							int pageno = 0;
							 
							if(jobject.size()>0 ){
								JSONObject	panel =  jobject.getJSONObject(outstack);
								pageno =  panel.getInt("currentpage");
							}else{
								pageno = 1;
								logger.debug("Pagination assuming first page as no page data is given" );
							}
								int pagecount = (int) Math.ceil((double)reccount / pagesize); 
								 
								//pagination:{form1:{currentpage:1,pagecount:200}} 
								ValueStack stack = ActionContext.getContext().getValueStack();
								ResultDTO tempresDTO = (ResultDTO) stack.getContext().get("resultDTO");
								if(tempresDTO == null){
									tempresDTO = new ResultDTO();
								}
								tempresDTO.setPageDetails(outstack, pageno, pagecount, pagesize);
								logger.debug("Now setetting resultDTO in JsonRPC pojo="+JSONSerializer.toJSON(tempresDTO));
								stack.getContext().put("resultDTO",tempresDTO); 
								logger.debug("Pagination set with pageno:"+pageno+" pagecount:"+pagecount+" pagesize:"+pagesize);
								int recfrom = pageno * pagesize;
								int recto = recfrom + pagesize;
								jsonRecord.put("recto", recto); //put into current row value the recfrom and recto so that it can be used in count query
								jsonRecord.put("recfrom", recfrom);
								hmfielddbtype.put("recto",PrepstmtDTO.getDataTypeFrmStr("INT") );
								hmfielddbtype.put("recfrom",PrepstmtDTO.getDataTypeFrmStr("INT"));
							
						}
					}
				}
				//pagination end
				
				PrepstmtDTOArray  arparam = new PrepstmtDTOArray();
				parsedquery = QueryParser.parseQuery(updatequery, panelname, jsonRecord, arparam, hmfielddbtype,  jsonInput, prevResultDTO);
				
			       logger.debug("JsonRPC query:"+parsedquery+"\n Expanded prep:"+arparam.toString(parsedquery));
			       fetranslatorDAO = new FETranslatorDAO();
			       resultDTO = fetranslatorDAO.executecrud(screenName, parsedquery, panelname, jsonRecord, arparam, errorTemplate,messageTemplate);
			       
			}catch(InvalidXPathException e){
				logger.debug("Exception caught in InsertData",e);
			} catch (DataTypeException e) {
				logger.debug("Exception caught in InsertData",e);
			} catch (JSONException e) {
				logger.debug("Exception caught in InsertData",e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.debug("Exception caught in InsertData",e);
				e.printStackTrace();
			}  
		return resultDTO;
	}

}
