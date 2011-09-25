package com.ycs.ezlink.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;

import com.ycs.ezlink.dto.JobDTO;
import com.ycs.fe.dao.DBConnector;
import com.ycs.fe.dto.PrepstmtDTOArray;
import com.ycs.fe.exception.BackendException;

public class SchedulerDAO {
	private static Logger logger = Logger.getLogger(SchedulerDAO.class);
	public Map<String,JobDTO> getAllJobs(){
		DBConnector db = new DBConnector();
		CachedRowSet crs = null;
		HashMap<String,JobDTO> jobDTOlist = new HashMap<String, JobDTO>();

		try {
			String qry = "select JOB_ID, CRON_EXP, JOB_DESC from JOB_SCHEDULAR";
			PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
			//arPrepstmt.add(DataType.INT, agentId);
			String res = null;
			crs = db.executePreparedQuery(qry, arPrepstmt);
			while (crs.next()) {
				JobDTO jobDTO = new JobDTO();
				res = crs.getString("JOB_ID");
				jobDTO.setJobId(res);
				res = crs.getString("CRON_EXP");
				jobDTO.setCronExp(res);
				res = crs.getString("JOB_DESC");
				jobDTO.setJobDesc(res);
				
				jobDTOlist.put(jobDTO.getJobId(), jobDTO);
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
		return jobDTOlist;
	}
}
