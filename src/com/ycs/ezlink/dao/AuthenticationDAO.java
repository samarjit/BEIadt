package com.ycs.ezlink.dao;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.ycs.ezlink.util.PasswordHashing;
import com.ycs.fe.dao.DBConnector;
import com.ycs.fe.dto.PrepstmtDTO.DataType;
import com.ycs.fe.dto.PrepstmtDTOArray;
import com.ycs.fe.exception.BackendException;

public class AuthenticationDAO {
	private static Logger logger = Logger.getLogger(AuthenticationDAO.class);
	
	public static boolean authenticate(String user, String pass) throws BackendException {
		boolean ret = false;
		String passHashed = PasswordHashing.passwordHashing(pass);
		DBConnector db = new DBConnector();
		String SQL = "select count(1) from USER_ID_MAPPING uim, CUSTOMER_MASTER c, PIN_MASTER p " +
				"where  p.USER_ID = uim.system_user_id and c.cin = uim.cin_no and uim.login_user_id = ? and p.USER_PASSWORD =?";
		PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
		arPrepstmt.add(DataType.STRING, user);
		arPrepstmt.add(DataType.STRING, passHashed);
		CachedRowSet rs = db.executePreparedQuery(SQL, arPrepstmt );
		
		try {
			if(rs.next()){
				int count = rs.getInt(1);
				if(count>0)
				ret = true; 
			}
		} catch (SQLException e) {
			logger.error("Authentication retrieval error",e);
			ret = false;
		}finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}

	public String getAuthorization(String user) throws BackendException {
		String ret = null;
		DBConnector db = new DBConnector();
		String SQL = "select gr.TRANS_CODE from USER_ID_MAPPING uim, USER_MASTER u, GROUP_MASTER gm, GROUP_RIGHTS gr " +
				"where  u.USER_ID = uim.system_user_id " +
				"and gm.group_id = u.user_groups " +
				"and  uim.login_user_id = ? ";
		PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
		arPrepstmt.add(DataType.STRING, user);
	 
		JSONArray jar = new JSONArray();
		CachedRowSet rs = null;
		try {
			rs = db.executePreparedQuery(SQL, arPrepstmt );
			while(rs.next()){
				jar.add(rs.getString("TRANS_CODE")); 
			}
			ret = jar.toString();
		} catch (SQLException e) {
			logger.error("Authorization retrieval error",e);
		}finally {
			try {
				if(rs!= null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
}
