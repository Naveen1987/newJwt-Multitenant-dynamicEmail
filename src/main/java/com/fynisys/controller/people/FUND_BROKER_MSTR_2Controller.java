package com.fynisys.controller.people;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fynisys.activitylogs.ActivityLogger;
import com.fynisys.controller.people.beans.Custodian;
import com.fynisys.model.FUND_COUNTRIES;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.people.FUND_ACCOUNT_MSTR;
import com.fynisys.model.people.FUND_BROKER_MSTR_2;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.people.FUND_ACCOUNT_MSTRRepository;
import com.fynisys.repository.people.FUND_BROKER_MSTR_2Repository;
import com.fynisys.utilities.FundUserValidate;
import com.fynisys.utilities.StockParameters;

//custodian

/*
SVB_CODE             NUMBER(15)               Code
SVB_NAME             VARCHAR2(50 BYTE),	      Name
SVB_ACCOUNT_CODE     VARCHAR2(15 BYTE),	      GL Code ( this list will come from FUND_ACCOUNT_MSTR , SVA_CODE,SVA_NAME)
SVB_COUNTRY          VARCHAR2(20 BYTE),	      Country

SVB_CONTACT_PERSON   VARCHAR2(30 BYTE),	      Contact Person
SVB_PHONE_NO         VARCHAR2(20 BYTE),	      Phone No
SVB_FAX_NO           VARCHAR2(20 BYTE),	      Fax no
SVB_MOBILE_NO        VARCHAR2(20 BYTE),	      Mobile No
SVB_EMAIL_ID         VARCHAR2(20 BYTE),	  	Email ID
SVB_ADDR             VARCHAR2(20 BYTE),		Address - size is too short. please suggest the actual size
SVB_POST_NO          VARCHAR2(20 BYTE),		Post Box
SVB_PC_NO            VARCHAR2(20 BYTE),		Post Code

WMS_COMMENTS					Comments	(Run below script this fields were not given u before)		   
WMS_STATUS					Status		(Run below script this fields were not given u before)	
 */
@RestController
public class FUND_BROKER_MSTR_2Controller {

	@Autowired
	FUND_BROKER_MSTR_2Repository fUND_BROKER_MSTR_2Repository;
	@Autowired
	StockParameters stockParameters;
	@Autowired
	FundUserValidate fundUserValidate;
	@Autowired
	ActivityLogger activityLogger;
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;
	@Autowired
	FUND_ACCOUNT_MSTRRepository fUND_ACCOUNT_MSTRRepository;
	
	private static final Logger logger=LoggerFactory.getLogger("CUSTODIAN CONTROLLER");
	
	@PostMapping("/saveCustodian")
	public ResponseEntity<?>save(RequestEntity<Map<String,Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
//		Object SVB_CODE=requestJson.get("comments");
		Object SVB_NAME=requestJson.get("NAME");
		Object SVB_ACCOUNT_CODE=requestJson.get("ACCOUNT_CODE");
		Object SVB_COUNTRY=requestJson.get("COUNTRY");
		Object SVB_CONTACT_PERSON=requestJson.get("CONTACT_PERSON");
		Object SVB_PHONE_NO=requestJson.get("PHONE_NO");
		Object SVB_FAX_NO=requestJson.get("FAX_NO");
		Object SVB_MOBILE_NO=requestJson.get("MOBILE_NO");
		Object SVB_EMAIL_ID=requestJson.get("EMAIL_ID");
		Object SVB_ADDR=requestJson.get("ADDR");
		Object SVB_POST_NO=requestJson.get("POST_NO");
		Object SVB_PC_NO=requestJson.get("PC_NO");
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("createdby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		 if(SVB_NAME==null ||SVB_ACCOUNT_CODE==null||SVB_COUNTRY==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
			  json.put("msg", "Please check Input json, missing some required attributes");
			  logger.error("Please check Input json, missing some required attributes");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		  }
		 else {
		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FUND_BROKER_MSTR_2 custodian=new FUND_BROKER_MSTR_2();
		if(SVB_NAME!=null) {
			if(SVB_NAME.toString().isEmpty()==false) {
				custodian.setSVB_NAME(SVB_NAME.toString());
			}
		}
				
		if(SVB_ACCOUNT_CODE!=null) {
			if(SVB_ACCOUNT_CODE.toString().isEmpty()==false) {
				custodian.setSVB_ACCOUNT_CODE(new Long(SVB_ACCOUNT_CODE.toString()));
			}
		}
		
		if(SVB_COUNTRY!=null) {
			if(SVB_COUNTRY.toString().isEmpty()==false) {
				custodian.setSVB_COUNTRY(new Long(SVB_COUNTRY.toString()));
			}
		}
		
		if(SVB_CONTACT_PERSON!=null) {
			if(SVB_CONTACT_PERSON.toString().isEmpty()==false) {
				custodian.setSVB_CONTACT_PERSON(SVB_CONTACT_PERSON.toString());
			}
		}
		
		if(SVB_PHONE_NO!=null) {
			if(SVB_PHONE_NO.toString().isEmpty()==false) {
				custodian.setSVB_PHONE_NO(SVB_PHONE_NO.toString());
			}
		}
		
		if(SVB_FAX_NO!=null) {
			if(SVB_FAX_NO.toString().isEmpty()==false) {
				custodian.setSVB_FAX_NO(SVB_FAX_NO.toString());
			}
		}
		
		if(SVB_MOBILE_NO!=null) {
			if(SVB_MOBILE_NO.toString().isEmpty()==false) {
				custodian.setSVB_MOBILE_NO(SVB_MOBILE_NO.toString());
			}
		}
		
		if(SVB_EMAIL_ID!=null) {
			if(SVB_EMAIL_ID.toString().isEmpty()==false) {
				custodian.setSVB_EMAIL_ID(SVB_EMAIL_ID.toString());
			}
		}
		
		if(SVB_ADDR!=null) {
			if(SVB_ADDR.toString().isEmpty()==false) {
				custodian.setSVB_ADDR(SVB_ADDR.toString());
			}
		}
		
		if(SVB_POST_NO!=null) {
			if(SVB_POST_NO.toString().isEmpty()==false) {
				custodian.setSVB_POST_NO(SVB_POST_NO.toString());
			}
		}
		
		if(SVB_PC_NO!=null) {
			if(SVB_PC_NO.toString().isEmpty()==false) {
				custodian.setSVB_PC_NO(SVB_PC_NO.toString());
			}
		}
		
		if(WMS_COMMENTS!=null) {
			custodian.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
		custodian.setWMS_STATUS("Not Approved");
				
		 		
		custodian.setIV_ENTER_UID(fuser.getSVC_UID());
		custodian.setIV_ENTER_DATE(Calendar.getInstance());
		  try
			{
			  custodian=fUND_BROKER_MSTR_2Repository.save(custodian);
			 if(custodian!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "CREATE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Custodian:"+custodian.getSVB_CODE());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Custodian:"+custodian.getSVB_CODE());
					
					json.put("createdby", custodian.getIV_ENTER_UID());
					json.put("createdon", custodian.getIV_ENTER_DATE());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Custodian not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Custodian not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}
	
	@PostMapping("/updateCustodian")
	public ResponseEntity<?>update(RequestEntity<Map<String,Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SVB_CODE=requestJson.get("CODE");
		Object SVB_NAME=requestJson.get("NAME");
		Object SVB_ACCOUNT_CODE=requestJson.get("ACCOUNT_CODE");
		Object SVB_COUNTRY=requestJson.get("COUNTRY");
		Object SVB_CONTACT_PERSON=requestJson.get("CONTACT_PERSON");
		Object SVB_PHONE_NO=requestJson.get("PHONE_NO");
		Object SVB_FAX_NO=requestJson.get("FAX_NO");
		Object SVB_MOBILE_NO=requestJson.get("MOBILE_NO");
		Object SVB_EMAIL_ID=requestJson.get("EMAIL_ID");
		Object SVB_ADDR=requestJson.get("ADDR");
		Object SVB_POST_NO=requestJson.get("POST_NO");
		Object SVB_PC_NO=requestJson.get("PC_NO");
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		 if(SVB_CODE==null ||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
			  json.put("msg", "Please check Input json, missing some required attributes");
			  logger.error("Please check Input json, missing some required attributes");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		  }
		 else {
		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FUND_BROKER_MSTR_2 custodian=fUND_BROKER_MSTR_2Repository.findOne(new Long(SVB_CODE.toString()));
		if(custodian==null) {
			json.put("msg", "Not Custodian found with id:"+SVB_CODE.toString());
			logger.error("Not Custodian found with id"+SVB_CODE.toString());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		
		
		if(SVB_NAME!=null) {
			if(SVB_NAME.toString().isEmpty()==false) {
				custodian.setSVB_NAME(SVB_NAME.toString());
			}
		}
				
		if(SVB_ACCOUNT_CODE!=null) {
			if(SVB_ACCOUNT_CODE.toString().isEmpty()==false) {
				custodian.setSVB_ACCOUNT_CODE(new Long(SVB_ACCOUNT_CODE.toString()));
			}
		}
		
		if(SVB_COUNTRY!=null) {
			if(SVB_COUNTRY.toString().isEmpty()==false) {
				custodian.setSVB_COUNTRY(new Long(SVB_COUNTRY.toString()));
			}
		}
		
		if(SVB_CONTACT_PERSON!=null) {
			if(SVB_CONTACT_PERSON.toString().isEmpty()==false) {
				custodian.setSVB_CONTACT_PERSON(SVB_CONTACT_PERSON.toString());
			}
		}
		
		if(SVB_PHONE_NO!=null) {
			if(SVB_PHONE_NO.toString().isEmpty()==false) {
				custodian.setSVB_PHONE_NO(SVB_PHONE_NO.toString());
			}
		}
		
		if(SVB_FAX_NO!=null) {
			if(SVB_FAX_NO.toString().isEmpty()==false) {
				custodian.setSVB_FAX_NO(SVB_FAX_NO.toString());
			}
		}
		
		if(SVB_MOBILE_NO!=null) {
			if(SVB_MOBILE_NO.toString().isEmpty()==false) {
				custodian.setSVB_MOBILE_NO(SVB_MOBILE_NO.toString());
			}
		}
		
		if(SVB_EMAIL_ID!=null) {
			if(SVB_EMAIL_ID.toString().isEmpty()==false) {
				custodian.setSVB_EMAIL_ID(SVB_EMAIL_ID.toString());
			}
		}
		
		if(SVB_ADDR!=null) {
			if(SVB_ADDR.toString().isEmpty()==false) {
				custodian.setSVB_ADDR(SVB_ADDR.toString());
			}
		}
		
		if(SVB_POST_NO!=null) {
			if(SVB_POST_NO.toString().isEmpty()==false) {
				custodian.setSVB_POST_NO(SVB_POST_NO.toString());
			}
		}
		
		if(SVB_PC_NO!=null) {
			if(SVB_PC_NO.toString().isEmpty()==false) {
				custodian.setSVB_PC_NO(SVB_PC_NO.toString());
			}
		}
		
		if(WMS_COMMENTS!=null) {
			custodian.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}		 		
		custodian.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
		custodian.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
		  try
			{
			  custodian=fUND_BROKER_MSTR_2Repository.save(custodian);
			 if(custodian!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "UPDATE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Custodian:"+custodian.getSVB_CODE());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Custodian:"+custodian.getSVB_CODE());
					
//					json.put("createdby", custodian.getIV_ENTER_UID());
//					json.put("createdon", custodian.getIV_ENTER_DATE());
//					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
					Custodian custodianJson=getJson(custodian);
					custodianJson.setMsg("saved");
					return new ResponseEntity<Custodian>(custodianJson,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Custodian not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Custodian not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}
	
	
	@PostMapping("/approveCustodian")
	public ResponseEntity<?>approve(RequestEntity<Map<String,Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SVB_CODE=requestJson.get("CODE");
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("approvedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		 if(SVB_CODE==null ||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
			  json.put("msg", "Please check Input json, missing some required attributes");
			  logger.error("Please check Input json, missing some required attributes");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		  }
		 else {
		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FUND_BROKER_MSTR_2 custodian=fUND_BROKER_MSTR_2Repository.findOne(new Long(SVB_CODE.toString()));
		if(custodian==null) {
			json.put("msg", "Not Custodian found with id:"+SVB_CODE.toString());
			logger.error("Not Custodian found with id"+SVB_CODE.toString());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		FUND_COUNTRIES fcountry=stockParameters.getFUND_COUNTRIES(custodian.getSVB_COUNTRY());
		if(fcountry!=null) {
			if(fcountry.getIV_APPROVE_UID()==null) {
				json.put("msg", "Country is not approved");
				logger.error("Country is not approved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		
		/*
		 * FUND_ACCOUNT_MSTR GL COOE
		 */
		 FUND_ACCOUNT_MSTR  faccount=fUND_ACCOUNT_MSTRRepository.findOne(custodian.getSVB_ACCOUNT_CODE());
		 if(faccount!=null) {
			 if(faccount.getIV_APPROVE_UID()==null) {
				 json.put("msg", "GL code is not approved");
					logger.error("GL code is not approved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			 }
		 }
		/*
		 * 
		 */
		if(WMS_COMMENTS!=null) {
			custodian.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}	
		custodian.setWMS_STATUS("Approved");
		custodian.setIV_APPROVE_UID(fuser.getSVC_UID());
		custodian.setIV_APPROVE_DATE(Calendar.getInstance());
		  try
			{
			  custodian=fUND_BROKER_MSTR_2Repository.save(custodian);
			 if(custodian!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "APPROVE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Custodian:"+custodian.getSVB_CODE());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Custodian:"+custodian.getSVB_CODE());
					
					Custodian custodianJson=getJson(custodian);
					custodianJson.setMsg("saved");
					return new ResponseEntity<Custodian>(custodianJson,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Custodian not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Custodian not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}

	@PostMapping("/revokeCustodian")
	public ResponseEntity<?>revoke(RequestEntity<Map<String,Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SVB_CODE=requestJson.get("CODE");
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		 if(SVB_CODE==null ||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
			  json.put("msg", "Please check Input json, missing some required attributes");
			  logger.error("Please check Input json, missing some required attributes");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		  }
		 else {
		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FUND_BROKER_MSTR_2 custodian=fUND_BROKER_MSTR_2Repository.findOne(new Long(SVB_CODE.toString()));
		if(custodian==null) {
			json.put("msg", "Not Custodian found with id:"+SVB_CODE.toString());
			logger.error("Not Custodian found with id"+SVB_CODE.toString());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		if(WMS_COMMENTS!=null) {
			custodian.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}	
		custodian.setWMS_STATUS("Not Approved");
		custodian.setIV_APPROVE_UID(null);
		custodian.setIV_APPROVE_DATE(null);
		custodian.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
		custodian.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
		  try
			{
			  custodian=fUND_BROKER_MSTR_2Repository.save(custodian);
			 if(custodian!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "REVOKE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Custodian:"+custodian.getSVB_CODE());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Custodian:"+custodian.getSVB_CODE());
					
					Custodian custodianJson=getJson(custodian);
					custodianJson.setMsg("saved");
					return new ResponseEntity<Custodian>(custodianJson,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Custodian not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Custodian not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}

	@PostMapping("/deleteCustodian")
	public ResponseEntity<?>delete(RequestEntity<Map<String,Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SVB_CODE=requestJson.get("CODE");
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		 if(SVB_CODE==null ||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
			  json.put("msg", "Please check Input json, missing some required attributes");
			  logger.error("Please check Input json, missing some required attributes");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		  }
		 else {
		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FUND_BROKER_MSTR_2 custodian=fUND_BROKER_MSTR_2Repository.findOne(new Long(SVB_CODE.toString()));
		if(custodian==null) {
			json.put("msg", "Not Custodian found with id:"+SVB_CODE.toString());
			logger.error("Not Custodian found with id"+SVB_CODE.toString());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		if(WMS_COMMENTS!=null) {
			custodian.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}	
		custodian.setWMS_STATUS("Not Approved");
		custodian.setIV_APPROVE_UID(null);
		custodian.setIV_APPROVE_DATE(null);
		custodian.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
		custodian.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
		  try
			{
			    fUND_BROKER_MSTR_2Repository.delete(custodian);
			  	json.put("msg", "Deleted");				
//			 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "DELETE");
//			 	if(loggerw==true) {
//				 		logger.info("Both Record and Logs saved for Custodian:"+custodian.getSVB_CODE());
//						json.put("logs","logs are saved");
//			 	}else {
//				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
//						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
//			 	}	
//					logger.info("Saved New Custodian:"+custodian.getSVB_CODE());
//					
//					json.put("createdby", custodian.getIV_ENTER_UID());
//					json.put("createdon", custodian.getIV_ENTER_DATE());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Custodian not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}
	
	@GetMapping("/custodiansearch")
	public ResponseEntity<?> getAllMargin(Pageable page,
			@RequestParam(required=false,value="action")String action,
			@RequestParam(required=false,value="paramSearch")String paramString
			){
		Map<String,Object> json=new HashMap<>();
		Page<FUND_BROKER_MSTR_2> allCustodian=null;
			if(action!=null) {
				if(action.isEmpty()==false) {
					if(action.equalsIgnoreCase("ALL")) {
						if(paramString!=null) {
							if(paramString.isEmpty()==false) {
								allCustodian=fUND_BROKER_MSTR_2Repository.findAllCustodianSearchingPage(paramString,page);
							}
							else {
								allCustodian=fUND_BROKER_MSTR_2Repository.findAllCustodian(page);
							}
						}else {
							allCustodian=fUND_BROKER_MSTR_2Repository.findAllCustodian(page);
						}
					}
					else if(action.equalsIgnoreCase("APPROVED")) {
						if(paramString!=null) {
							if(paramString.isEmpty()==false) {
								allCustodian=fUND_BROKER_MSTR_2Repository.findAllAPPROVEDCustodianSearchingPage(paramString,page);
							}
							else {
								allCustodian=fUND_BROKER_MSTR_2Repository.findAllAPPROVEDCustodian(page);
							}
						}else {
							allCustodian=fUND_BROKER_MSTR_2Repository.findAllAPPROVEDCustodian(page);
						}
					}
					else if(action.equalsIgnoreCase("UNAPPROVED")) {
						if(paramString!=null) {
							if(paramString.isEmpty()==false) {
								allCustodian=fUND_BROKER_MSTR_2Repository.findAllUNAPPROVEDCustodianSearchingPage(paramString,page);
							}
							else {
								allCustodian=fUND_BROKER_MSTR_2Repository.findAllUNAPPROVEDCustodian(page);
							}
						}else {
							allCustodian=fUND_BROKER_MSTR_2Repository.findAllUNAPPROVEDCustodian(page);
						}
					}
					else {
						json.put("msg", "action is not match");
						logger.error("action is match");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
				}
				else {
					json.put("msg", "action is not match");
					logger.error("action is match");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
				if(allCustodian!=null) {
					Page<Custodian> jsonarry=allCustodian.map(new Converter<FUND_BROKER_MSTR_2, Custodian>() {

						@Override
						public Custodian convert(FUND_BROKER_MSTR_2 arg0) {
							Custodian ffb= getJson(arg0);
							return ffb;
						}
						
					});
					return new ResponseEntity<Page<Custodian>>(jsonarry,HttpStatus.OK);
				}
			}
			else {
				json.put("msg", "action is null");
				logger.error("action is null");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			json.put("msg", "Error");
			logger.error("ERROR");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
	}
	
public Custodian getJson(FUND_BROKER_MSTR_2 conut) {
	Custodian ffb=new Custodian();
	ffb.setCustodian_CODE(conut.getSVB_CODE()+"");
	ffb.setCustodian_DATE(conut.getSVB_DATE()); 
	ffb.setCustodian_NAME(conut.getSVB_NAME());
	ffb.setCustodian_ANAME(conut.getSVB_ANAME());  
	ffb.setCustodian_WBROKER(conut.getSVB_WBROKER()); 
	ffb.setCustodian_EXCHANGE(conut.getSVB_EXCHANGE()); 
	ffb.setCustodian_CONTACT_PERSON(conut.getSVB_CONTACT_PERSON());  
	ffb.setCustodian_PHONE_NO(conut.getSVB_PHONE_NO());    
	ffb.setCustodian_FAX_NO(conut.getSVB_FAX_NO()); 
	ffb.setCustodian_MOBILE_NO(conut.getSVB_MOBILE_NO());
	ffb.setCustodian_EMAIL_ID(conut.getSVB_EMAIL_ID());
	ffb.setCustodian_ADDR(conut.getSVB_ADDR());
	ffb.setCustodian_POST_NO(conut.getSVB_POST_NO());  
	ffb.setCustodian_PC_NO(conut.getSVB_PC_NO());
	FUND_COUNTRIES country=stockParameters.getFUND_COUNTRIES(conut.getSVB_COUNTRY());
	if(country!=null) {
		ffb.setCustodian_COUNTRYID(conut.getSVB_COUNTRY()+"");
		ffb.setCustodian_COUNTRYNAME(country.getSVC_NAME());
	}else {
		ffb.setCustodian_COUNTRYID(null);
		ffb.setCustodian_COUNTRYNAME(null);
	}
FUND_ACCOUNT_MSTR mstr=fUND_ACCOUNT_MSTRRepository.findOne(conut.getSVB_ACCOUNT_CODE());
if(mstr!=null) {
	ffb.setCustodian_ACCOUNT_CODE(mstr.getSVA_CODE()+"");
	ffb.setCustodian_ACCOUNT_NAME(mstr.getSVA_NAME()+"");
}else
{
	
		ffb.setCustodian_ACCOUNT_CODE(null);
		ffb.setCustodian_ACCOUNT_NAME(null);
	
}
	//ffb.setCustodian_ACCOUNT_CODE(conut.getSVB_ACCOUNT_CODE()+"");
	ffb.setCustodian_UTF_AC(conut.getSVB_UTF_AC());      
	ffb.setCustodian_UGF_AC(conut.getSVB_UGF_AC());                                      
	ffb.setCustodian_NBAD_AC(conut.getSVB_NBAD_AC());           
	ffb.setCustodian_NOMINEE_AC(conut.getSVB_NOMINEE_AC());        
	ffb.setCustodian_BANK_ACCOUNT(conut.getSVB_BANK_ACCOUNT());   
	ffb.setCustodian_BANK_NAME(conut.getSVB_BANK_NAME());      
	ffb.setCustodian_DR_CODE(conut.getSVB_DR_CODE());        
	ffb.setCustodian_SVC_UID(conut.getSVC_UID());            
	ffb.setCustodian_SHORT_NAME(conut.getSVB_SHORT_NAME());     
	ffb.setCustodian_ADDR_STREET(conut.getSVB_ADDR_STREET());    
	ffb.setCustodian_ADDR_AREA(conut.getSVB_ADDR_AREA());      
	ffb.setCustodian_ADDR_CITY(conut.getSVB_ADDR_CITY());      
	ffb.setCustodian_WEBSITE(conut.getSVB_WEBSITE());        
	ffb.setCustodian_P_NAME(conut.getSVB_P_NAME());         
	ffb.setCustodian_P_DEPT(conut.getSVB_P_DEPT());         
	ffb.setCustodian_P_POSITION(conut.getSVB_P_POSITION());     
	ffb.setCustodian_P_DIR_PHONE(conut.getSVB_P_DIR_PHONE());                                       	
	ffb.setCustodian_P_DIR_FAX(conut.getSVB_P_DIR_FAX());                                         
	ffb.setCustodian_P_DIR_MOBILE(conut.getSVB_P_DIR_MOBILE());                                      
	ffb.setCustodian_P_DIR_EMAIL(conut.getSVB_P_DIR_EMAIL());                                       
	ffb.setCustodian_S_NAME(conut.getSVB_S_NAME());                                         
	ffb.setCustodian_S_DEPT(conut.getSVB_S_DEPT());                                        
	ffb.setCustodian_S_POSITION(conut.getSVB_T_POSITION());                                        
	ffb.setCustodian_S_DIR_PHONE(conut.getSVB_S_DIR_PHONE());                                        
	ffb.setCustodian_S_DIR_FAX(conut.getSVB_S_DIR_FAX());                                        
	ffb.setCustodian_S_DIR_MOBILE(conut.getSVB_S_DIR_MOBILE());                                        
	ffb.setCustodian_S_DIR_EMAIL(conut.getSVB_S_DIR_EMAIL());                                        
	ffb.setCustodian_T_NAME(conut.getSVB_T_NAME());                                        
	ffb.setCustodian_T_DEPT(conut.getSVB_T_DEPT());                                        
	ffb.setCustodian_T_POSITION(conut.getSVB_T_POSITION());                                        
	ffb.setCustodian_T_DIR_PHONE(conut.getSVB_T_DIR_PHONE());                                        
	ffb.setCustodian_T_DIR_FAX(conut.getSVB_T_DIR_FAX());                                        
	ffb.setCustodian_T_DIR_MOBILE(conut.getSVB_T_DIR_MOBILE());                                        
	ffb.setCustodian_T_DIR_EMAIL(conut.getSVB_T_DIR_EMAIL());                                        
	ffb.setCustodian_GROUP(conut.getSVB_GROUP());                                      
	ffb.setCustodian_ID_MARKET(conut.getSVB_ID_MARKET()); 
	 ffb.setComments( conut.getWMS_COMMENTS()+"");
	  ffb.setStatus( conut.getWMS_STATUS()+"");
	
	FUND_USERS user=null;
	if(conut.getIV_ENTER_UID()!=null) {
		user=fundUserValidate.getUserName(conut.getIV_ENTER_UID());
		if(user!=null) {
		ffb.setEnteredby( user.getSVU_NAME());
		ffb.setEnteredbyuserid( user.getSVU_USER_NAME());
		ffb.setEnteredbyuuid( user.getSVC_UID());
		ffb.setEntereddate(conut.getIV_ENTER_DATE());
		}
		else {
			ffb.setEnteredby( null);
			ffb.setEnteredbyuserid( null);
			ffb.setEnteredbyuuid( null);
			ffb.setEntereddate( null);
		}
	}else {
		ffb.setEnteredby( null);
		ffb.setEnteredbyuserid( null);
		ffb.setEnteredbyuuid( null);
		ffb.setEntereddate( null);
	}
	
	if(conut.getIV_APPROVE_UID()!=null) {
		user=fundUserValidate.getUserName(conut.getIV_APPROVE_UID());
		if(user!=null) {
		ffb.setApprovedby( user.getSVU_NAME());
		ffb.setApprovedbyuserid( user.getSVU_USER_NAME());
		ffb.setApprovedbyuuid( user.getSVC_UID());
		ffb.setApproveddate( conut.getIV_APPROVE_DATE());
		}
		else {
			ffb.setApprovedby( null);
			ffb.setApprovedbyuserid( null);
			ffb.setApprovedbyuuid( null);
			ffb.setApproveddate( null);
		}
	}else {
		ffb.setApprovedby( null);
		ffb.setApprovedbyuserid( null);
		ffb.setApprovedbyuuid( null);
		ffb.setApproveddate( null);
	}
	
	if(conut.getIV_LAST_UPDATE_UID()!=null) {
		user=fundUserValidate.getUserName(conut.getIV_LAST_UPDATE_UID());
		if(user!=null) {
		ffb.setModifiedby( user.getSVU_NAME());
		ffb.setModifiedbyuserid( user.getSVU_USER_NAME());
		ffb.setModifiedbyuuid( user.getSVC_UID());
		ffb.setModifieddate( conut.getIV_LAST_UPDATE_DATE());
		}
		else {
			ffb.setModifiedby( null);
			ffb.setModifiedbyuserid( null);
			ffb.setModifiedbyuuid( null);
			ffb.setModifieddate( null);
		}
	}else {
		ffb.setModifiedby( null);
		ffb.setModifiedbyuserid( null);
		ffb.setModifiedbyuuid( null);
		ffb.setModifieddate( null);
	}
	
	
	return ffb;
}
}
