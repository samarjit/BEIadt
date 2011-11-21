package com.ycs.ezlink.util;

import java.sql.Date;

public class EzLinkConstant {

	//Refund Channel RC_ 
	public static final String RC_Nominated_Card = "Nominated Card"; 
	public static final String RC_Bank_Account = "Bank Account"; 
	public static final String RC_Cheque = "Cheque"; 
	
	//Refund Mode RM_ 
	public static final String RM_EzReload = "EzReload"; 
	public static final String RM_EzRetention = "EzRetention"; 
	
	//Refund Status
	public static final String RS_CardOpsQueue = "CardOpsQueue";
	public static final String RS_ReplacementQueue = "ReplacementQueue";
	public static final String RS_Replaced = "Replaced";
	public static final String RS_FileCreatedQueue = "FileCreatedQueue";
	public static final String RS_FileSent = "FileSent";
	public static final String RS_ReadyToClaim = "ReadyToClaim";
	public static final String RS_Refunded = "Refunded";
	public static final String RS_ReportDownloadQueue = "ReportDownloadQueue";
	public static final String RS_BackendProcessed = "BackendProcessed";
	public static final String RS_NotNominated = "NotNominated";
	public static final String RS_NotClaimed = "NotClaimed";
	
	// Configurable Params
	public static final int DEAL_SUM_PAGE_SIZE = 2;
	public static final int MAX_NO_REG_CARD = 3;
	public static final String PREFIX_CIN = "trsCin";
	public static final String PREFIX_SYSTEM_USER_ID = "trsUsr";
	public static final int OTP_VALID_TIME = 1 * 24 * 60 * 60 * 1000;
	public static final int OTP_BLOCKED_TIME =  5 * 24 * 60 * 60 * 1000;
	public static final int PASSWORD_VALID_TILL =  180; // in days
	public static final int NO_OF_PREV_MONTHS = 3;
	public static final int NOMINATE_CARDS_DAYS_LIMIT = 70;
	//transactionHistory
	public static final String TRANSIT_ACQ_LIST = "1";
	public static final String RETAIL_ACQ_LIST = "17";
	public static final String CARPARK_ACQ_LIST = "13";
	public static final String ERP_ACQ_LIST = "0";
	public static final String TOPUP_ACQ_LIST = "101";
	public static final String TOTAL_SPENT_ACQ_LIST = "1,17,13,0";
	
	public static final String BLOCKED_CARD_COMPLETED = "'Refunded', 'Charity'";
	public static final String BULK_EMAIL_PREFIX = "email_batch";
	//Insurance prefix
	public static final String INSURANCE_SIGNUP_PREFIX="ins_signup_";
	public static final String INSURANCE_SIGNUP_RETURN_PREFIX="ins_signupreturn";
	public static final String INSURANCE_CLAIM_PREFIX="ins_claim";
	public static final String INSURANCE_CLAIM_RETURN_PREFIX="ins_claimreturn";
	public static final String POLICE_RPT_PREFIX="police_rpt_";//CARD_NOyyyyMMddhhmmss
	
	// db values
	public static final String CARD_STATUS_ACTIVE = "I";
	public static final String CARD_STATUS_BLOCKED = "B";
	public static final String PRODUCT_TYPE_VAL = "EZLink";
	public static final String USER_TYPE = "EZ";
	public static final String USER_GROUP = "EZCUSTOMER";
	public static final String USER_STATUS = "A";
	public static final String ALERT_TEMP_ID_SMS_OTP = "SMS_OTP";
	public static final String ALERT_TEMP_ID_SMS_FORGOT_PASS = "SMS_FORGOT_PASS";
	public static final String ALERT_TEMP_ID_EMAIL_FORGOT_PASS = "EMAIL_TEMP_PASS";
	public static final String ALERT_TYPE_SMS = "SMS";
	public static final String ALERT_TYPE_EMAIL = "EMAIL";
	public static final String REG_STATUS = "REG";
	public static final String REG_STATUS_PAR = "PAR";
	public static final String REG_STATUS_PEND_REG = "PEND_REG";
	public static final String REG_STATUS_DELETE = "DELETE";
	
	
	// property files
	public static final String PASSWORD_INCORRECT_LIMIT = "pass.incorrect.limit";
	public static final String OTP_SEND_LITMIT = "otp.send.limit";
	public static final String OTP_INCORRECT_LIMIT = "otp.incorrect.limit";
	public static final String PATH_CONFIG_PROPERTY_FILE_NAME = "path_config";
	// values
	public static final String MOBILE_USER ="mobileUser";
	public static final String WEB_USER ="webUser";
	
	
	//input parameter names
	public static final String NRIC = "nric";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String GENDER = "gender";
	public static final String DOB = "dob";
	public static final String POSTAL_CODE = "postalCode";
	public static final String EMAIL = "email";
	public static final String MOBILE ="mobile";
	public static final String CARD_NO = "cardNo";
	public static final String PASSWORD = "password";
	public static final String NEW_PASSWORD = "newPassword";
	public static final String OLD_PASSWORD = "oldPassword";
	public static final String BLOCK_CARD_NO = "blockCardNo";
	public static final String REFUND_CARD_NO = "refundCardNo";
	public static final String TIME_STAMP = "timeStamp";
	public static final String DEVICE_ID ="deviceId";
	public static final String HASHCODE = "hashcode";
	public static final String INSURANCE_POLICY_NO = "insuranceId";
	public static final String DEAL_ID = "dealID";
	public static final String VAS_POSTER_ID = "VASPosterID";
	public static final String FORM_NAME = "ezlinkform1";
	public static final String CREATION_MODE = "creationMode";
	public static final String MEMBER_TYPE = "memberType";
	public static final String PASSWORD_TYPE = "passwordType";
	public static final String STATUS = "status";
	public static final String MAKER_ID = "makerId";
	public static final String OTP = "OTP";
	public static final String ANSWER = "answer";
	public static final String REG_STATUS_NAME = "regStatus";
	public static final String PAGE = "page";
	public static final String TYPE = "type";
	public static final String REQ_FROM = "requestFrom";
	public static final String PROMOTION_NAME = "promotionName";
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";
	public static final String DETAILED_DESC = "detailDesc";
	public static final String TERMS_CONDITION = "termsCondition";
	public static final String LOCATION = "location";
	public static final String FILE_NAME = "fileName";
	public static final String FILE_TYPE = "fileType";
	public static final String PARAM = "param";
	
	
	//json object
	public static final String CONSOLIDATED_HISTORY_TXN = "ConsolidatedTransaction";
	public static final String TXN_DETAIL = "TransactionDetail";
	public static final String CARD_DETAIL = "cardDetail";
	public static final String BLOCKED_CARD_DETAIL = "blockedCard";
	public static final String ACCOUNT_DETAIL = "accountDetail";
	public static final String TXN_LIST = "transactionList";
	public static final String SECURITY_QUES = "securityQues";
	public static final String INPUT = "input";
	public static final String REFUND_BLOCKED_LIST = "refundBlockList";
	public static final String RESULT = "result";
	public static final String DEAL_SUM = "dealSum";
	public static final String DEAL_DETAIL = "dealDetail";
	
	
	
	// daylight names
	public static final String HP = "hp";
	public static final String ACCOUNT_ID = "accountID";
	public static final String CARD_ID = "cardID";

	

	 
	// Result codes
	
	public static final String SUCCESS = "success";
	public static final String DB_ERROR = "dberror";
	public static final String FAIL = "fail";
	public static final String RESULT_CODE = "resultCode";
	public static final String RESULT_NAME = "resultName";
	public static final String DATA = "data";
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";
	// Error codes
	public static final String DATE_FORMAT_ERROR = "date.format.error";
	public static final String SQL_ERROR = "sql.error";
	public static final String UNEXPECTED_ERROR = "unexpected.error";
	public static final String NULL_VALUE = "null.value";
	
	//edit user
	public static final String EDITUSER_UPDATE_FAILED = "edituser.update.failed";
	public static final String EDITUSER_UPDATE_SUCCESS = "edituser.update.success";
	//register user
	public static final String USER_ALREADY_REGISTERED = "user.already.registered";
	public static final String USER_REGISTER_FAILED = "user.register.failed";
	public static final String USER_REGISTER_SUCCESS = "user.register.success";
	//password validation
	public static final String PASSWORD_EXPIRED = "password.expired";
	public static final String PASSWORD_INVALID = "password.invalid";
	//public static final String PASSWORD_VALIDATION_FAIL = "password.validation.failed";
	public static final String PASSWORD_VALID = "password.valid";
	public static final String PASSWORD_EXCEEDS_INCORRECT_LIMIT = "password.exceeds.incorrect.limit";
	//changePassword
	public static final String CHANGE_PASSWORD_FAIL = "changepassword.fail";
	public static final String CHANGE_PASSWORD_SUCCESS = "changepassword.success";
	public static final String OLDPASS_NEWPASS_SAME = "oldpass.newpass.same";
	public static final String NEWPASS_PREVPASS_SAME = "newpass.prevpass.same";
	public static final String USER_NOT_REGISTERED = "user.not.registered";
	public static final String OLDPASS_WRONG = "oldpassword.wrong";
	//getcardlist
	public static final String GET_CARDLIST_FAIL = "get.cardlist.fail";
	// deregister card
	public static final String CARD_NOT_REGISTERED = "card.not.registered";
	public static final String DEREGISTER_FAIL = "deregister.card.failed";
	public static final String DEREGISTER_SUCCESS = "deregister.card.success";
	// registercard
	public static final String CARD_MAX_REGISTERED = "card.maximum.registered";
	public static final String CARD_REGISTER_FAIL = "card.register.failed";
	public static final String CARD_REGISTER_SUCCESS = "card.register.success";
	public static final String CARD_ALREADY_REGISTERED = "card.already.registered";
	public static final String CARD_BLOCKED = "card.blocked";
	//replacement card (refund card)
	public static final String REPLACEMENT_CARD_UPDATE_FAIL = "replacement.card.update.failed";
	public static final String REPLACEMENT_CARD_UPDATE_SUCCESS = "replacement.card.update.success";
	public static final String NOMINATE_CARD_DAYS_EXPIRED = "nominate.card.days.expired";
	//getaccount details
	public static final String AUTHENDICATE_FAIL = "authendicate.fail";
	public static final String GET_ACCOUNTDETAIL_FAIL = "get.acountdetail.fail";
	public static final String GET_ACCOUNTDETAIL_SUCCESS = "get.acountdetail.success";
	//retrieveTransaction
	public static final String HASHCODE_VALIDATION_FAIL = "hashcode.validate.failed";
	public static final String TXN_RETRIEVE_FAIL = "transaction.retrieve.failed";
	public static final String TXN_RETRIEVE_SUCCESS = "transaction.retrieve.success";
	//send otp
	public static final String OTP_SENT_SUCCESS = "otp.sent.success";
	public static final String OTP_SENT_FAIL = "otp.sent.fail";
	public static final String OTP_RESENT_SUCCESS = "otp.resent.success";
	public static final String OTP_RESENT_FAIL = "otp.resent.fail";
	public static final String OTP_RESENT_REACHED_MAX = "otp.resent.reached.maximum";
	//validate OTP
	public static final String OTP_NOT_REQUESTED = "otp.not.requested";
	// validate secruity question
	public static final String SECURITY_QUES_VALIDATION_FAIL = "security.ques.validation.fail";
	public static final String SECURITY_QUES_VALIDATION_SUCCESS = "security.ques.validation.success";
	
// front end validation
	public static final String NRIC_ISREQD = "nric.required";
	public static final String FIRST_NAME_ISREQD = "firstName.required";
	public static final String LAST_NAME_ISREQD = "lastName.required";
	public static final String GENDER_ISREQD = "gender.required";
	public static final String DOB_ISREQD = "dob.required";
	public static final String POSTAL_CODE_ISREQD = "postalCode.required";
	public static final String EMAIL_ISREQD = "email.required";
	public static final String MOBILE_ISREQD ="mobile.required";
	public static final String CARD_NO_ISREQD = "cardNo.required";
	public static final String PASSWORD_ISREQD = "password.required";
	public static final String NEW_PASSWORD_ISREQD = "newPassword.required";
	public static final String OLD_PASSWORD_ISREQD = "oldPassword.required";
	public static final String BLOCK_CARD_NO_ISREQD = "blockCardNo.required";
	public static final String REFUND_CARD_NO_ISREQD = "refundCardNo.required";
	public static final String TIME_STAMP_ISREQD = "timeStamp.required";
	public static final String DEVICE_ID_ISREQD ="deviceId.required";
	public static final String HASHCODE_ISREQD = "hashcode.required";
	public static final String INSURANCE_POLICY_NO_ISREQD = "insuranceId.required";
	public static final String DEAL_ID_ISREQD = "dealID.required";
	public static final String VAS_POSTER_ID_ISREQD = "VASPosterID.required";
	public static final String ANSWER_ISREQD = "answer.required";
	public static final String SECURITY_QUES_ISREQD = "question.required";

// invalid formats
	public static final String NRIC_INVALID = "nric.invalid";
	public static final String DOB_INVALID = "dob.invalid";
	public static final String POSTAL_CODE_INVALID = "postalCode.invalid";
	public static final String EMAIL_INVALID = "email.invalid";
	public static final String MOBILE_INVALID ="mobile.invalid";
	public static final String CARD_NO_INVALID = "cardNo.invalid";
}


