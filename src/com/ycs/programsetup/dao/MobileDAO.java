package com.ycs.programsetup.dao;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ycs.fe.dao.DBConnector;
import com.ycs.fe.dto.PrepstmtDTO.DataType;
import com.ycs.fe.dto.PrepstmtDTOArray;
import com.ycs.fe.exception.BackendException;

public class MobileDAO {
	
	Logger logger = Logger.getLogger(MobileDAO.class);
	
	private String getDashboard(int cardID) {
		DBConnector db = new DBConnector();
		CachedRowSet crs = null;
		JSONObject jsonRec = new JSONObject();
		JSONArray jsonResultArray = new JSONArray();
		try {
	     int month = 3;
		 while(month > 0){	
			String qry =  "select sum(TXN_AMOUNT) from TXN_HISTORY where txn_time between    sysdate - " + (month) * 30 +"  and sysdate "+ (month -1) * 30+"   and AGENT_ID in(?) AND CARD_NO =  ?";
			PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
			String agentId = "100";  //BUS
			float transit = 0.0f; 
			float retail = 0.0f; 
			float erp = 0.0f; 
			float carpark = 0.0f; 
			float totalTopup = 0.0f;
			float totalSpent = 0.0f;
			
			arPrepstmt.add(DataType.INT, agentId);
			arPrepstmt.add(DataType.STRING, String.valueOf(cardID));
			
			crs = db.executePreparedQuery(qry, arPrepstmt );
			while(crs.next()){
				transit = crs.getFloat(1);
			}
			crs.close();
			crs = null;
			arPrepstmt = null;
			
			arPrepstmt = new PrepstmtDTOArray();
			arPrepstmt.add(DataType.INT, "102" ); //rtail
			arPrepstmt.add(DataType.STRING, String.valueOf(cardID));
			
			crs = db.executePreparedQuery(qry, arPrepstmt );
			while(crs.next()){
				retail = crs.getFloat(1);
			}
			crs.close();
			crs = null;
			arPrepstmt = null;

			arPrepstmt = new PrepstmtDTOArray();
			arPrepstmt.add(DataType.INT, "102" ); //rtail
			arPrepstmt.add(DataType.STRING, String.valueOf(cardID));
			
			crs = db.executePreparedQuery(qry, arPrepstmt );
			while(crs.next()){
				erp = crs.getFloat(1);
			}
			crs.close();
			crs = null;
			arPrepstmt = null;

			arPrepstmt = new PrepstmtDTOArray();
			arPrepstmt.add(DataType.INT, "102" ); //rtail
			arPrepstmt.add(DataType.STRING, String.valueOf(cardID));
			
			crs = db.executePreparedQuery(qry, arPrepstmt );
			while(crs.next()){
				carpark = crs.getFloat(1);
			}
			crs.close();
			crs = null;
			arPrepstmt = null;

			arPrepstmt = new PrepstmtDTOArray();
			arPrepstmt.add(DataType.INT, "102" ); //rtail
			arPrepstmt.add(DataType.STRING, String.valueOf(cardID));
			
			crs = db.executePreparedQuery(qry, arPrepstmt );
			while(crs.next()){
				totalTopup = crs.getFloat(1);
			}
			crs.close();
			crs = null;
			arPrepstmt = null;
			
			arPrepstmt = new PrepstmtDTOArray();
			arPrepstmt.add(DataType.INT, "102" ); //rtail
			arPrepstmt.add(DataType.STRING, String.valueOf(cardID));
			
			crs = db.executePreparedQuery(qry, arPrepstmt );
			while(crs.next()){
				totalSpent = crs.getFloat(1);
			}
			crs.close();
			crs = null;
			arPrepstmt = null;
			
			jsonRec.put("transit", transit); 
			jsonRec.put("retail",retail);
			jsonRec.put("erp", erp); 
			jsonRec.put("carpark", carpark); 
			jsonRec.put("totalTopup",totalTopup);
			jsonRec.put("totalSpent",totalSpent);
			
			jsonResultArray.add(jsonRec);
		 }
		 
		 
		} catch (BackendException e) {
			logger.debug(e);
		} catch (SQLException e) {
			logger.debug(e);
		}finally{
			if(crs!= null){
				try {
					crs.close();
				} catch (SQLException e) {
					logger.debug(e);
				}
				
			}
		}
		
		return jsonResultArray.toString();
	}

	private void retrieveTransaction(int cardID, String hashCode, String deviceId) {
		DBConnector db = new DBConnector();
		CachedRowSet crs = null;

		try {
			String qry = "select bal_amount , txn_time   from (select * from txn_history  order by txn_time  desc )  where rownum < 2" ;
			PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
			//arPrepstmt.add(DataType.INT, agentId);
			String res = null;
			crs = db.executePreparedQuery(qry, arPrepstmt);
			while (crs.next()) {
				res = crs.getString("BAL_AMOUNT");
				res = crs.getString("TXN_TIME");
			}
			crs.close();
			crs = null;
			arPrepstmt = null;
			
			qry = "select TXN_ID,USER_ID,CARD_NO,TXN_TIME,TXN_TYPE,TXN_AMOUNT,BAL_AMOUNT,a.AGENT_NAME ,ACQ_ID from txn_history tx, Agent_ID a where a.AGENT_ID = tx.AGENT_ID and TXN_TIME between sysdate - 30 and sysdate";
			
			crs = db.executePreparedQuery(qry, arPrepstmt);
			while (crs.next()) {
				res = crs.getString("BAL_AMOUNT");
				res = crs.getString("TXN_TIME");
			}
			crs.close();
			crs = null;
			arPrepstmt = null;
			
		} catch (BackendException e) {
			logger.debug(e);
		} catch (SQLException e) {
			logger.debug(e);
		} finally {
			if (crs != null) {
				try {
					crs.close();
				} catch (SQLException e) {
					logger.debug(e);
				}

			}
		}
		
	}
}
