package com.ycs.fe.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.ycs.fe.dao.DBConnector;

public class ReverseEngineerXml {
	private String globalSQL = "  SELECT PRODUCT_CODE ,  PRODUCT_NAME ,  PLASTIC_CODE,   PLASTIC_DESC    FROM PRODUCT_DETAILS ";
	public static String toPropoerCase(String inputString) {

		StringBuffer result = new StringBuffer();
		String[] list = null;
		if (inputString != null && inputString.length() > 0) {
			StringTokenizer tok = new StringTokenizer(inputString, "_ ");
			if (tok.hasMoreElements())
				list = new String[tok.countTokens()];
			int n = 0;
			while (tok.hasMoreElements()) {
				list[n] = (String) tok.nextElement();
				n++;
			}
			if (list != null && list.length > 0) {
				for (int i = 0; i < list.length; i++) {
					String str = list[i];
					str = str.toLowerCase();
					char[] charArray = str.toCharArray();
					charArray[0] = Character.toUpperCase(charArray[0]); // list[i]
																		// = new
																		// String(charArray);
					result.append(new String(charArray));
					if (i < n)
						result.append(" ");
				}
			}
		}
		return result.toString();
	}

	public String getXmlmapDataTypeName(String dbimplType, boolean scale){
		if(dbimplType.equals("VARCHAR2"))return "STRING";
		if(dbimplType.equals("VARCHAR"))return "STRING";
		if(dbimplType.equals("DATE"))return "DATE_NS";
		if(dbimplType.equals("TIMESTAMP"))return "TIMESTAMP";
		
		if(dbimplType.equals("NUMBER") || dbimplType.equals("DECIMAL")  ){
			if(scale)return "FLOAT";
			else
			return "INT";
		}
		if(dbimplType.equals("INTEGER"))return "INT";
		if(dbimplType.equals("FLOAT"))return "FLOAT";
		if(dbimplType.equals("DOUBLE"))return "DOUBLE";
		if(dbimplType.equals("LONG"))return "LONG";
		if(dbimplType.equals("BIGINT"))return "LONG";
		
		return "STRING";
	}
	public void reverseEng() throws Exception {
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		Statement st = con.createStatement();

		String sql = globalSQL;
		StringBuffer inputSql = new StringBuffer();//new BufferedReader(new InputStreamReader(System.in)).readLine();
		int ch = -1;
		System.out.println("Enter a query followed by ';' or simply enter ';' and press <enter>\r\n:");
		while((ch = System.in.read())!= ';'){
			inputSql.append((char)ch);
		}
		if(inputSql.length() >0){
			sql= inputSql.toString();
		}
		ResultSet rs = st.executeQuery(sql);
		ResultSetMetaData metaData = rs.getMetaData();
		int rowCount = metaData.getColumnCount();
		System.out.println("Table Name : " + metaData.getTableName(1));
		System.out.println("Field  \tsize\tDataType");
		ArrayList<String> arheader = new ArrayList<String>();
		ArrayList<String> aralias = new ArrayList<String>();
		ArrayList<String> arcol = new ArrayList<String>();
		ArrayList<String> ardatatype = new ArrayList<String>();
		ArrayList<Integer> arcolprecision = new ArrayList<Integer>();
		String columnName;
		String alias;
		String col; //temp
		int size = 0;
		int scale= 0;
		String tableName = "";
		tableName = metaData.getTableName(1);
		for (int i = 0; i < rowCount; i++) {
			columnName = metaData.getColumnName(i + 1);
			 
			arcol.add(columnName);
			col = toPropoerCase(columnName);
			col = col.replaceAll("\\_", " ");
			arheader.add(col);
			alias = col.toLowerCase().replaceAll(" ", "");
			aralias.add(alias);
			size = metaData.getPrecision(i+1);//metaData.getColumnDisplaySize(i + 1);
			scale= metaData.getScale(i+1);
			ardatatype.add(getXmlmapDataTypeName(metaData.getColumnTypeName(i + 1), scale != 0));
			arcolprecision.add(size);
			
			System.out.print(metaData.getColumnName(i + 1) + "  \t");
			System.out.print(metaData.getColumnDisplaySize(i + 1) + " ==? " + metaData.getPrecision(i+1)+"\t");
			System.out.println(metaData.getColumnTypeName(i + 1));
			
			
		}
		
		if(con!= null){
			con.close();
		}
		//headers
		System.out.println("jQuery(\"#listid\").jqGrid( {\r\n");
		System.out.println("      	url:'<%= request.getContextPath() %>/jqgrid.action?command=true&screenName=XXXXXTestPage&submitdata={bulkcmd=\"XXXXXprodgrid\"}',");
		System.out.println("      	datatype: \"json\",");
		String colNames= "      	colNames:[";
		boolean first = true;
		for (String string : arheader) {
			colNames += (first)?"":","; first = false;
			colNames += "'"+string.trim()+"'";
		}
		colNames += "      	],";
		
		System.out.println(colNames);
		
		//jqgrid column model
		String colModel = "      	colModel:[";first =true;
		for (int i = 0; i < aralias.size(); i++) {
			alias = aralias.get(i);
			size = arcolprecision.get(i);
			colModel += (first)?"\r\n":",\r\n";first = false;
			colModel +="      	{name: '"+alias+"', index: '"+alias+"' , width:"+size+" }" ;
		}
		colModel +="\r\n      	],";
		System.out.println(colModel);
		System.out.println("      	rowNum: 10,\r\n" + 
				"      	rowList: [ 10, 20, 30],\r\n" + 
				"      	pager: '#pagerid',\r\n" + 
				"      	sortname: 'XXXXSORTING_COLUMN_REF',\r\n" + 
				"        viewrecords: true,\r\n" + 
				"        sortorder: \"desc\",\r\n" + 
				"        jsonReader: {\r\n" + 
				"    		repeatitems : false,\r\n" + 
				"    		id: \"0\"\r\n" + 
				"    	},\r\n" + 
				"       caption: \"XXXXType the Caption here\"\r\n" + 
				"   } ).navGrid('#pager1',{edit:true,add:true,del:true});");
		System.out.println("...\n  <table id=\"listid\" ></table>\r\n" + 
				"		 <div id=\"pagerid\"></div>");
		
		//validation xml
		 String datatype = "";
		String validationXml = "";
		for (int i = 0; i < aralias.size(); i++) {
			alias = aralias.get(i);
			size = arcolprecision.get(i);
			datatype = ardatatype.get(i);
			col = arcol.get(i);
			
			validationXml +="<validationfld dbcolsize=\""+size+"\" name=\""+alias+"\" column=\""+col+"\" mandatory=\"yes\" forid=\""+alias+"\" dbdatatype=\""+datatype+"\" />\r\n" ;
		}
		 System.out.println(validationXml);
		
		 
		 System.out.println("<form name=\"form1\" id=\"form1\" method=\"post\">\r\n" + 
		 		"        	 <table>" );
		 for (int i = 0; i < aralias.size(); i++) {
			 alias = aralias.get(i);
			 System.out.println("        	   <tr><td>"+arheader.get(i)+"</td><td><input name=\""+alias+"\" id=\""+alias+"\" value=\"${resultDTO.data.formonload[0]."+alias+"}\"/></td></tr>");
		 }
		 System.out.println("        	   \r\n\r\n");
		 for (int i = 0; i < aralias.size(); i++) {
			 alias = aralias.get(i);
			 System.out.println("        	   <tr><td>"+arheader.get(i)+"</td><td><s:property value=\"#resultDTO.data.formonload[0]."+alias+"\"  /></td></tr>");
		 }		 
		 System.out.println("        	 </table>\r\n" + 
		 		"</form>");
		 //select clause;
		
		 String sel = "SELECT ";first = true;
		 String sel2 = "SELECT ";
			for (int i = 0; i < aralias.size(); i++) {
				alias = aralias.get(i);
				size = arcolprecision.get(i);
				datatype = ardatatype.get(i);
				col = arcol.get(i);
				sel += (first)?"":",";  
				sel += col+" \""+alias+"\"";
				sel2 += (first)?"":","; first = false;
				sel2 += col+" ";
			}
			sel += " FROM "+tableName;
			sel2 += " FROM "+tableName;
			
			System.out.println(sel);
			System.out.println(sel2);
			
			String crs = "";
			String crs1 = "";
			String crs2 = "";
			String insertSql = " insert into "+tableName+" (\"+insertCol+\") values (\"+insertVal+\")";
			String insertCol = "";
			String insertVal = "";
			String ins = "";
			String upd = "";
			for (int i = 0; i < arheader.size(); i++) {
				crs += "res.set"+arheader.get(i).replace(" ", "")+"(crs.getString(\""+arcol.get(i)+"\"));\r\n";
				crs1 += "res.set"+arheader.get(i).replace(" ", "")+"(crs.getString(\""+aralias.get(i)+"\"));\r\n";
				crs2 += "res.set"+ toPropoerCase(aralias.get(i).replace(" ", ""))+"(crs.getString(\""+aralias.get(i)+"\"));\r\n";
				
				upd+="if (pinMaster.get"+arheader.get(i).replace(" ", "")+"() != null) {\r\n" + 
						"				qry+= (first)?\"\":\",\";first = false;\r\n" + 
						"				qry+= \""+arcol.get(i)+" = ?\";\r\n" + 
						"				arPrepstmt.add(DataType."+ardatatype.get(i)+", pinMaster.get"+arheader.get(i).replace(" ", "")+"());\r\n" + 
						"			}\r\n";
				
				ins +="if (pinMaster.get"+arheader.get(i).replace(" ", "")+"() != null) {\r\n" + 
						"              insertCol += (first)?\"\":\",\";\r\n" + 
						"              insertVal += (first)?\"\":\",\";\r\n" + 
						"              insertCol +=\"" +arcol.get(i)+"\";\r\n"+
						"              insertVal += \"?\";\r\n" + 
						"              first = false;\r\n" + 
						"              arPrepstmt.add(DataType."+ardatatype.get(i)+", pinMaster.get"+arheader.get(i).replace(" ", "")+"());\r\n" + 
						"           }\r\n";
			}
			System.out.println(crs+"\r\n");
			System.out.println(crs1+"\r\n");
			System.out.println(crs2+"\r\n");
			System.out.println(upd+"\r\n");
			System.out.println(ins+"\r\n");
			System.out.println(insertSql+"\r\n");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		new ReverseEngineerXml().reverseEng();
	}

}
