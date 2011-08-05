package com.ycs.be.dto; 

  
 public class PrepstmtDTO { 
 //public static String STRING="STRING"; 
 //public static String INT="INT"; 
 //public static String DATE="DATE"; 
 //public static String DOUBLE="DOUBLE"; 
 //public static String FLOAT="FLOAT"; 
          
 public PrepstmtDTO(DataType type,String data){ 
         this.type = type; 
         this.data = data; 
 } 
 public DataType type; 
 public String getData() { 
         return data; 
 } 
 public void setData(String data) { 
         this.data = data; 
 } 
  
 public static enum DataType { 
         STRING,INT,DATE,FLOAT,DOUBLE 
 } 
 public DataType getType() { 
         return type; 
 } 
 public String getTypeString() { 
     if(type == DataType.STRING)
    	 return "STRING"; 
     if(type == DataType.INT)
    	 return "INT"; 
     if(type == DataType.DATE)
    	 return "DATE"; 
     if(type == DataType.FLOAT)
    	 return "FLOAT";
     if(type == DataType.DOUBLE)
    	 return "DOUBLE"; 
     else
    	 return "INVALID_TYPE";
 }
 public static DataType getDataTypeFrmStr(String type) throws DataTypeException{
	 if(type.equals("STRING") )
    	 return DataType.STRING; 
	 if(type.equals("INT") )
    	 return DataType.INT; 
	 if(type.equals("DATE") )
    	 return DataType.DATE; 
	 if(type.equals("FLOAT") )
    	 return DataType.FLOAT;
	 if(type.equals("DOUBLE") )
    	 return DataType.DOUBLE; 
     else
    	 throw new DataTypeException("DataType undefined");
 }
 
 public void setType(DataType type) { 
         this.type = type; 
 } 
          
 public String data; 
  
 } 