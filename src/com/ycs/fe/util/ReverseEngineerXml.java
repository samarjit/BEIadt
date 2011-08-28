package com.ycs.fe.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.ycs.fe.dao.DBConnector;

public class ReverseEngineerXml {

	public static String toPropoerCase(String inputString) {

		StringBuffer result = new StringBuffer();
		String[] list = null;
		if (inputString != null && inputString.length() > 0) {
			StringTokenizer tok = new StringTokenizer(inputString);
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

		String sql = "select * from DEAL_MASTER";

		ResultSet rs = st.executeQuery(sql);
		ResultSetMetaData metaData = rs.getMetaData();
		int rowCount = metaData.getColumnCount();
		System.out.println("Table Name : " + metaData.getTableName(2));
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
		tableName = metaData.getTableName(2);
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
		
		//headers
		String colNames= "colNames:[";
		boolean first = true;
		for (String string : arheader) {
			colNames += (first)?"":","; first = false;
			colNames += "'"+string.trim()+"'";
		}
		colNames += "],";
		
		System.out.println(colNames);
		
		//jqgrid column model
		String colModel = "colModel:[";first =true;
		for (int i = 0; i < aralias.size(); i++) {
			alias = aralias.get(i);
			size = arcolprecision.get(i);
			colModel += (first)?"":",\r\n";first = false;
			colModel +="{name: '"+alias+"', index: '"+alias+"' , width:"+size+" }" ;
		}
		colModel +="\r\n],";
		System.out.println(colModel);
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
		
		 //select clause;
		
		 String sel = "SELECT ";first = true;
			for (int i = 0; i < aralias.size(); i++) {
				alias = aralias.get(i);
				size = arcolprecision.get(i);
				datatype = ardatatype.get(i);
				col = arcol.get(i);
				sel += (first)?"":","; first = false;
				sel += col+" \""+alias+"\"";
			}
			sel += " FROM "+tableName;
			
			System.out.println(sel);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		new ReverseEngineerXml().reverseEng();
	}

}
