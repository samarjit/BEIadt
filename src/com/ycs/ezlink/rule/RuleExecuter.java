package com.ycs.ezlink.rule;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.rowset.CachedRowSet;

import com.ycs.fe.dao.DBConnector;
import com.ycs.fe.dto.PrepstmtDTO.DataType;
import com.ycs.fe.dto.PrepstmtDTOArray;
import com.ycs.fe.exception.BackendException;

public class RuleExecuter {

private static Logger logger = Logger.getLogger(RuleExecuter.class.getName());
/*
Condition  COND_1 : ( ( Transit No >= 20 ;   No of Transit Days : 25;   Agent : ERP )   AND   ( Transit Amount >= 10 ;   No of Transit Days : 25;   Agent : BUS ) ) 
Redemption Rule for per Member per Card 
Redemption Units Limit:   10 in No of Days:  2   
Redemption Units Limit at each POS Terminal:  5  



select count('x') num_trans from TXN_HISTORY where user_id= ?  and TXN_TIME > sysdate - 25 and agent_id in ('ERP')
  num_trans >= 20 
select sum(TXN_AMOUNT) trans_amount from TXN_HISTORY where user_id= ? and TXN_TIME > sysdate - 25 and agent_id in ('BUS')
  trans_amount >= 10;
and
select count(offer_items) from DEAL_TXN_HISTORY where  user_id=? and REDEMPTION_DATE < sysdate - 2

and
Redemption Units:         select count(1) from (select sum(OFFERING_QUANTITY_AVAIL) redm_sum from DEAL_TXN_HISTORY where user_id=? and REDEMPTION_DATE >  SYSDATE - 2 group by terminal_id  ) where redm_sum < 10
Redemption Units per POS: select count(1) from (select sum(OFFERING_QUANTITY_AVAIL) redm_sum from DEAL_TXN_HISTORY where user_id=?  and terminal_ID=? group by terminal_id  ) where redm_sum < 5
for each deal .. select 1 from dual where substr(to_char(sysdate,'DAY'),1,3) in (select RUL_APPLICABLE_DAY from deal_master where  deal_id =?) 

select DEAL_ID from deal_master where sysdate between DEAL_START_DATE and DEAL_END_DATE and deal_id =? and OFFERING_QTY_LEFT > 0;
and USER_REG_STATUS =?('Registered/NonRegistered') and deal_id =? ;
and RUL_APPLICABLE_MEMBER_TYPE =?
and PAYMENT_MODE = ?

FOR all deals between start date and end date 
FOR all deals that are having offering quantity left  > 0 

Note: No SQL injection possibility as variables used in queries are all derived form other tables

*/

public static HashMap<String, Object> queryCache = new HashMap<String, Object>();

private static class RuleQry{
	public String query;
	public int constValue;
	public String oper;
	private int queryValue;
	public RuleQry(String query,int queryValue, int constValue, String oper) {
		this.query = query;
		this.queryValue = queryValue;
		this.constValue = constValue;
		this.oper = oper;
	}
	
}

	public void processRule(String cardNo, String terminalId, String paymentMode){
		
		SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String SYSDATE = sm.format(new Date());
		//MEMBER_TYPE  ezlink, ezreload, passion etc.
		String userQuery = "select MEMBER_TYPE , USER_STATUS \"REG_STATUS\", cin from CUSTOMER_MASTER cust , CARD_MASTER card where cust.CIN = card.CIN and card.CARD_NO=?";
		DBConnector db = new DBConnector();
		CachedRowSet crs = null;
		String userCin = null;
		String memberType = null;
		String userRegStatus = null;
		String conditionStr = null; 
		try{
			PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
			arPrepstmt.add(DataType.STRING, cardNo);
			crs = db.executePreparedQuery(userQuery, arPrepstmt );
			if(crs.next()){
				userCin = crs.getString("cin"); 
				memberType = crs.getString("MEMBER_TYPE");
				userRegStatus = crs.getString("REG_STATUS");
			}
		}catch(SQLException e){
			logger.warning("SQL exception in getting member details "+e);
		} catch (BackendException e) {
			logger.warning("SQL exception in getting member details "+e);
		}finally{
			if (crs != null){
				try {
					crs.close();
				} catch (SQLException e) {
					logger.warning("SQL connection closing error");
				}
				crs = null;
			}
		}
		
		
		//For each deal 
		String deal_query = "select DEAL_ID, RULE_ID,REDM_UNIT_LIMIT, REDM_UNIT_LIMIT_IN_DAYS, REDM_UNIT_LIMIT_PER_TERMNL, rule.TXN_CONDITION from deal_master d, RULE_MASTER rule where d.RULE_ID(+) = rule.RULE_ID AND TO_DATE('"+SYSDATE+"','DD/MM/YYYY HH24:MI:SS') between DEAL_START_DATE and DEAL_END_DATE and OFFERING_QTY_LEFT > 0 \n" + 
				"and USER_REG_STATUS =? \n" + 
				"and RUL_APPLICABLE_MEMBER_TYPE =? \n" ;
		if(paymentMode != null && !"".equals(paymentMode)){
			deal_query+= "and PAYMENT_MODE = ?";
		}
		
		try {
			PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
			arPrepstmt.add(DataType.STRING, userRegStatus);
			arPrepstmt.add(DataType.STRING, memberType);
			if(paymentMode != null && !"".equals(paymentMode)){
				arPrepstmt.add(DataType.STRING, paymentMode);
			}
			String deal_id = null;
			String ruleId = null;
			String redmUnitLimit= null;
			String redmUnitLimitDays = null;
			String redmUnitLimitPerTerminal = null;
			if(queryCache.containsKey(arPrepstmt.toString(deal_query))){
				ArrayList<String> arTemp = (ArrayList<String>) queryCache.get(arPrepstmt.toString(deal_query));
				deal_id = arTemp.get(0);
				ruleId = arTemp.get(1);
				redmUnitLimit = arTemp.get(2);
				redmUnitLimitDays = arTemp.get(3);
				redmUnitLimitPerTerminal = arTemp.get(4);
				conditionStr = arTemp.get(5);
				
			}else{
				crs = db.executePreparedQuery(deal_query , arPrepstmt);
				if(crs.next()){
					deal_id = crs.getString("DEAL_ID");
					ruleId = crs.getString("RULE_ID");
					redmUnitLimit = crs.getString("REDM_UNIT_LIMIT");
					redmUnitLimitDays = crs.getString("REDM_UNIT_LIMIT_IN_DAYS");
					redmUnitLimitPerTerminal = crs.getString("REDM_UNIT_LIMIT_PER_TERMNL");
					conditionStr = crs.getString("TXN_CONDITION");
					List<String> arTemp = Arrays.asList(new String[]{deal_id,ruleId,redmUnitLimit, redmUnitLimitDays,redmUnitLimitPerTerminal, conditionStr});
					queryCache.put(arPrepstmt.toString(deal_query), arTemp);
				}
				crs.close();
				crs = null;
			}
		
			//Deal Applicable DAY
			String isTodayDealSQL = "select 1 from dual where substr(to_char(TO_DATE('"+SYSDATE+"','DD/MM/YYYY HH24:MI:SS'),'DAY'),1,3) in (select RUL_APPLICABLE_DAY from deal_master where  deal_id =?) ";
			CachedRowSet crs_today = null;
			try {
				
				PrepstmtDTOArray arPrepstmt1 = new PrepstmtDTOArray();
				arPrepstmt1.add(DataType.STRING, deal_id);
				int isTodayDealApplicable = 0;
				if (queryCache.containsKey(arPrepstmt1.toString(isTodayDealSQL))) {
					 isTodayDealApplicable = (Integer) queryCache.get(arPrepstmt1.toString(isTodayDealSQL));
				}else{
					crs_today = db.executePreparedQuery(isTodayDealSQL, arPrepstmt1);
					if (crs_today.next()) {
						isTodayDealApplicable = crs_today.getInt(1);
					}
					queryCache.put(arPrepstmt1.toString(isTodayDealSQL), isTodayDealApplicable);
				}
				if(isTodayDealApplicable == 0){
					logger.info("Deal["+deal_id+"] is not applicable today");
					return;
				}
			} catch (SQLException e) {
				logger.warning("SQL exception in getting member details " + e);
			} finally {
				if (crs_today != null) {
					try {
						crs_today.close();
					} catch (SQLException e) {
						logger.warning("SQL connection closing error");
					}
					crs_today = null;
				}
			}
			
			StringBuffer s = new StringBuffer();
			int c = 0;
			
			
			int mode = 0; //1 reading string, 2 reading oprators
			ArrayList<String> arLiteral  = new ArrayList<String>();
			ArrayList<String> arOperator  = new ArrayList<String>();
			HashMap <String,RuleQry> hmRuleQry = new HashMap<String, RuleExecuter.RuleQry>();
			boolean combineCond = true;
				if (conditionStr != null && !"".equals(conditionStr)) {
					String cond = conditionStr;//"COND_1 : ( ( Transit No >= 20 ;   No of Transit Days : 25;   Agent : 10,34 )   OR   ( Transit Amount = 10 ;   No of Transit Days : 21;   Agent : BUS ) )";
					StringReader sw = new StringReader(cond);
					try {
						boolean first = false;
						while ((c = sw.read()) != -1) {
							if ('(' == (char) c) {
								if (!first) {
									if (mode == 2)
										arOperator.add(s.toString().trim());
									s.setLength(0);
									mode = 1;
								} else {
									//skip first (
								}
							} else if (')' == (char) c) {
								if (mode == 1) {
									arLiteral.add(s.toString().trim());
									s.setLength(0);
									mode = 2;
								}
								if (mode == 2) {
									//s.append(s.toString());
								}
							} else {
								if (mode == 1 || mode == 2) {
									s.append((char) c);
								}
							}
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println(arLiteral);
					System.out.println(arOperator);
					int rule_counter = 0;
					String intermediateExpr = "COND_SEQ_";
					for (String expr : arLiteral) {
						String operator = null;
						int aggregateType = 0;
						int valueAggregate = 0;
						String valueAggrStr = null;
						String exprs[] = expr.split(";");
						//exprs[0]
						Pattern p = null;//Pattern.compile("(Transit No)([\\s\\S]*?)(\\d+).*");
						Matcher eachExprMatcher = null;//p.matcher(exprs[0]);

						if (exprs[0].startsWith("Transit No")) {
							aggregateType = 1;
							p = Pattern.compile("Transit No([\\s\\S]*?)(\\d+).*");
							eachExprMatcher = p.matcher(exprs[0]);

						} else if (exprs[0].startsWith("Transit Amount")) {
							p = Pattern.compile("Transit Amount([\\s\\S]*?)(\\d+).*");
							eachExprMatcher = p.matcher(exprs[0]);
							aggregateType = 2;
						} else {
							logger.warning("Error in matching expression!!");
						}
						eachExprMatcher.find();
						operator = eachExprMatcher.group(1);
						valueAggrStr = eachExprMatcher.group(2);
						valueAggregate = Integer.parseInt(valueAggrStr.trim());

						//exprs[1]
						int numTransitDays = 0;
						String numTransitStr = exprs[1].substring(exprs[1].indexOf(':') + 1);
						numTransitDays = Integer.parseInt(numTransitStr.trim());

						//exprs[2]
						String agentVal = exprs[2].substring(exprs[2].indexOf(':') + 1).trim();
						String agentQueryStr = "";
						if (agentVal.equals("")) {
							logger.warning("ERROR: Agent is required");
						} else if (agentVal.equals("ERP")) {
							agentQueryStr = " = '32906' ";
						} else if (agentVal.equals("BUS")) {
							agentQueryStr = " in ('16','17') ";
						} else if (agentVal.equals("TRAIN")) {
							agentQueryStr = " = '18' ";
						} else if (agentVal.equals("ALL")) {
							agentQueryStr = "  is not null ";
						} else { //handle specific agents
							agentVal = agentVal.replaceAll("'", "");
							String agentVals[] = agentVal.split(",");
							boolean first1 = true;
							for (String agent : agentVals) {
								agentQueryStr += (first1) ? "" : ",";
								first1 = false;
								agentQueryStr += "'" + agent + "'";
							}
							agentQueryStr = " in (" + agentQueryStr + ")";
						}
						//Creating query
						String qry = null;
						if (aggregateType == 1) {
							qry = "select count('x') num_trans from TXN_HISTORY where user_id = '" + userCin + "'  and TXN_TIME > TO_DATE('" + SYSDATE + "','DD/MM/YYYY HH24:MI:SS') - "
									+ numTransitDays + " and agent_id " + agentQueryStr;
						} else if (aggregateType == 2) {
							qry = "select sum(TXN_AMOUNT) trans_amount from TXN_HISTORY where user_id = '" + userCin + "'  and TXN_TIME > TO_DATE('" + SYSDATE + "','DD/MM/YYYY HH24:MI:SS') - "
									+ numTransitDays + " and agent_id " + agentQueryStr;
						} else {
							logger.info("The expression was not found"); // must be caught earlier
						}
						System.out.println(qry);
						rule_counter++;

						hmRuleQry.put((intermediateExpr + rule_counter), new RuleQry(qry, -1, valueAggregate, operator));
					}
					//execute query and then execute COND_1
					for (Entry<String, RuleQry> entryRule : hmRuleQry.entrySet()) {
						String key = entryRule.getKey();
						RuleQry rule = entryRule.getValue();
						String qry = rule.query;
						CachedRowSet crsCondPart = null;
						try {
							if(queryCache.containsKey(rule.query)){
								rule.queryValue = (Integer) queryCache.get(rule.query);
							}else{
								PrepstmtDTOArray arPrepstmt_cond = new PrepstmtDTOArray();
								//						arPrepstmt_cond.add(DataType.STRING, cardNo);
								crsCondPart = db.executePreparedQuery(qry, arPrepstmt_cond);
								if (crsCondPart.next()) {
									rule.queryValue = crsCondPart.getInt(1);
								}
								queryCache.put(rule.query, rule.queryValue);
							}
							hmRuleQry.put(key, rule);
						} catch (SQLException e) {
							logger.warning("SQL exception in getting member details " + e);
						} finally {
							if (crsCondPart != null) {
								try {
									crsCondPart.close();
								} catch (SQLException e) {
									logger.warning("SQL connection closing error");
								}
								crsCondPart = null;
							}
						}
					}
					//execute COND_1 condition
					ArrayList<Integer> condPartVal = new ArrayList<Integer>();
					boolean conditionResult = true;
					boolean lastConditionResult = true;
					combineCond = true;
					int i = 0;
					boolean first = true;
					for (RuleQry rule : hmRuleQry.values()) {
						
						//COND_PART1
						if (rule.oper != null && !"".equals(rule.oper)) {
							if (rule.oper.equals(">=")) {
								conditionResult &= rule.queryValue >= rule.constValue;
							} else if (rule.oper.equals(">")) {
								conditionResult &= rule.queryValue > rule.constValue;
							} else if (rule.oper.equals("=")) {
								conditionResult &= rule.queryValue == rule.constValue;
							} else if (rule.oper.equals("<=")) {
								conditionResult &= rule.queryValue <= rule.constValue;
							} else if (rule.oper.equals("<")) {
								conditionResult &= rule.queryValue < rule.constValue;
							}
						}
						
						//(COND_PART1 AND COND_PART2 OR COND_PART3)
						if(!first){
							String operCond = arOperator.get(i);i++;
							if (operCond.equals("AND")) {
								combineCond = lastConditionResult & conditionResult;
							} else if (operCond.equals("OR")) {
								combineCond = lastConditionResult | conditionResult;
							}
						}
						first = false;
						lastConditionResult =  conditionResult;
					}
				} ///if end combined conditionStr
				
				//Redemption unit limit days per card
				String redm_qry1 = "select count(1) from (select sum(OFFERING_QUANTITY_AVAIL) redm_sum from DEAL_TXN_HISTORY where user_id=? and REDEMPTION_DATE >  TO_DATE('"+SYSDATE+"','DD/MM/YYYY HH24:MI:SS') - ? group by terminal_id  ) where redm_sum < ?"; 
					CachedRowSet crs_redm1 = null;
					try {
						PrepstmtDTOArray arPrepstmt1 = new PrepstmtDTOArray();
						arPrepstmt1.add(DataType.STRING, userCin);
						arPrepstmt1.add(DataType.STRING, redmUnitLimitDays);
						arPrepstmt1.add(DataType.STRING, redmUnitLimit);
						logger.info(arPrepstmt1.toString(redm_qry1));
						if(queryCache.containsKey(arPrepstmt1.toString(redm_qry1))){
							combineCond &= (Boolean) queryCache.get(arPrepstmt1.toString(redm_qry1));
						}else{
							crs_redm1 = db.executePreparedQuery(redm_qry1, arPrepstmt1);
							if(crs_redm1.next()){
								combineCond &= true;
								queryCache.put(arPrepstmt1.toString(redm_qry1), true);
							}else{
								combineCond &= false;
								queryCache.put(arPrepstmt1.toString(redm_qry1), false);
							}
							
						}
					} catch (SQLException e) {
						logger.warning("SQL exception in getting member details "+ e);
					} finally {
						if (crs_redm1 != null) {
							try {
								crs_redm1.close();
							} catch (SQLException e) {
								logger.warning("SQL connection closing error");
							}
							crs_redm1 = null;
						}
					}
					
				//Redemption Unit limit per POS terminal (per merchant)
				String redm_qry2 = "select count(1) from (select sum(OFFERING_QUANTITY_AVAIL) redm_sum from DEAL_TXN_HISTORY where user_id=?  and terminal_ID=? group by terminal_id  ) where redm_sum < ?";
					CachedRowSet crs_redm2 = null;
					try {
						PrepstmtDTOArray arPrepstmt1 = new PrepstmtDTOArray();
						arPrepstmt1.add(DataType.STRING, userCin);
						arPrepstmt1.add(DataType.STRING, terminalId);
						arPrepstmt1.add(DataType.STRING, redmUnitLimitPerTerminal);
						logger.info(arPrepstmt1.toString(redm_qry2));
						if (queryCache.containsKey(arPrepstmt1.toString(redm_qry2))) {
							combineCond &= (Boolean) queryCache.get(arPrepstmt1.toString(redm_qry2));
						}else{
							crs_redm2 = db.executePreparedQuery(redm_qry1, arPrepstmt1);
							if (crs_redm2.next()) {
								combineCond &= true;
								queryCache.put(arPrepstmt1.toString(redm_qry2), true);
							} else {
								combineCond &= false;
								queryCache.put(arPrepstmt1.toString(redm_qry2), true);
							}
						}
					} catch (SQLException e) {
						logger.warning("SQL exception in getting member details " + e);
					} finally {
						if (crs_redm2 != null) {
							try {
								crs_redm2.close();
							} catch (SQLException e) {
								logger.warning("SQL connection closing error");
							}
							crs_redm2 = null;
						}
					}
				
				
			
				
				logger.warning("suggestion deal_id:"+deal_id);
		} catch (SQLException e) {
			logger.warning("SQL exception in getting member details " + e);
		} catch (BackendException e) {
			logger.warning("SQL exception in getting member details " + e);
		} finally {
			if (crs != null) {
				try {
					crs.close();
				} catch (SQLException e) {
					logger.warning("SQL connection closing error");
				}
				crs = null;
			}
		}
	}
	
	
	public static void main(String args[]){
		new RuleExecuter().processRule(null, null, null);
	}
}
