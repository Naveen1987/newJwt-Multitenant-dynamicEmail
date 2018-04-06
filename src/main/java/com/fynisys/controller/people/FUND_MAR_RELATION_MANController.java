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
import com.fynisys.controller.people.beans.RelationshipManager;
import com.fynisys.model.FUND_COUNTRIES;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.people.FUND_MAR_RELATION_MAN;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.people.FUND_MAR_RELATION_MANRepository;
import com.fynisys.utilities.FundUserValidate;
import com.fynisys.utilities.StockParameters;


/*
 SVB_CODE             NUMBER(15),    		RM CODE
 SVB_NAME             VARCHAR2(50 BYTE),	RM NAME
 SVB_MOBILE_NO        VARCHAR2(20 BYTE),	MOBILE NO
 SVB_EMAIL_ID         VARCHAR2(200 BYTE),	EMAIL ID  ( Some times user may enter more then one email id so you should allow ; (sreenivasu@ciobera.com;vasuas@hotmail.com)
 SVB_COUNTRY          VARCHAR2(20 BYTE),	Country
  SVB_PHONE_NO         VARCHAR2(20 BYTE),	Phone no
  SVB_FAX_NO           VARCHAR2(20 BYTE), 	Fax no
  SVB_ADDR             VARCHAR2(20 BYTE),	Address - size is too short. please suggest the actual size
  SVB_POST_NO          VARCHAR2(20 BYTE),	Post no
  SVB_PC_NO            VARCHAR2(20 BYTE),	Post Code
SVB_STATUS           VARCHAR2(50 BYTE),		Status
WMS_COMMENTS					Comments  ( I have added below Alter command to add this field , Run the script)

 */
@RestController
public class FUND_MAR_RELATION_MANController {
	
	@Autowired
	StockParameters stockParameters;
	@Autowired
	FundUserValidate fundUserValidate;
	@Autowired
	ActivityLogger activityLogger;
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;
	@Autowired
	FUND_MAR_RELATION_MANRepository fUND_MAR_RELATION_MANRepository;
	
	private static final Logger logger=LoggerFactory.getLogger("FUND_MAR_RELATION_MAN CONTROLLER");
	
	@PostMapping("/saverm")
	public ResponseEntity<?>save(RequestEntity<Map<String,Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SVB_NAME=requestJson.get("NAME");
		Object SVB_COUNTRY=requestJson.get("COUNTRY");
		Object SVB_MOBILE_NO=requestJson.get("MOBILE_NO");
		Object SVB_EMAIL_ID=requestJson.get("EMAIL_ID");
		Object SVB_PHONE_NO=requestJson.get("PHONE_NO");
		Object SVB_FAX_NO=requestJson.get("FAX_NO");
		Object SVB_ADDR=requestJson.get("ADDR");
		Object SVB_POST_NO=requestJson.get("POST_NO");
		Object SVB_PC_NO=requestJson.get("PC_NO");
		Object WMS_COMMENTS=requestJson.get("comments");
		Object createdby=requestJson.get("createdby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		 if(SVB_NAME==null ||SVB_MOBILE_NO==null||SVB_EMAIL_ID==null||SVB_COUNTRY==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
		FUND_MAR_RELATION_MAN rm=new FUND_MAR_RELATION_MAN();
		if(SVB_NAME!=null) {
			if(SVB_NAME.toString().isEmpty()==false) {
				rm.setSVB_NAME(SVB_NAME.toString());
			}
		}
				
		
		if(SVB_COUNTRY!=null) {
			if(SVB_COUNTRY.toString().isEmpty()==false) {
				rm.setSVB_COUNTRY(new Long(SVB_COUNTRY.toString()));
			}
		}
		
		
		if(SVB_PHONE_NO!=null) {
			if(SVB_PHONE_NO.toString().isEmpty()==false) {
				rm.setSVB_PHONE_NO(SVB_PHONE_NO.toString());
			}
		}
		
		if(SVB_FAX_NO!=null) {
			if(SVB_FAX_NO.toString().isEmpty()==false) {
				rm.setSVB_FAX_NO(SVB_FAX_NO.toString());
			}
		}
		
		if(SVB_MOBILE_NO!=null) {
			if(SVB_MOBILE_NO.toString().isEmpty()==false) {
				rm.setSVB_MOBILE_NO(SVB_MOBILE_NO.toString());
			}
		}
		
		if(SVB_EMAIL_ID!=null) {
			if(SVB_EMAIL_ID.toString().isEmpty()==false) {
				rm.setSVB_EMAIL_ID(SVB_EMAIL_ID.toString());
			}
		}
		
		if(SVB_ADDR!=null) {
			if(SVB_ADDR.toString().isEmpty()==false) {
				rm.setSVB_ADDR(SVB_ADDR.toString());
			}
		}
		
		if(SVB_POST_NO!=null) {
			if(SVB_POST_NO.toString().isEmpty()==false) {
				rm.setSVB_POST_NO(SVB_POST_NO.toString());
			}
		}
		
		if(SVB_PC_NO!=null) {
			if(SVB_PC_NO.toString().isEmpty()==false) {
				rm.setSVB_PC_NO(SVB_PC_NO.toString());
			}
		}
		
		if(WMS_COMMENTS!=null) {
			rm.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
		rm.setSVB_STATUS("Not Approved");
				
		 		
		rm.setIV_ENTER_UID(fuser.getSVC_UID());
		rm.setIV_ENTER_DATE(Calendar.getInstance());
		  try
			{
			  rm=fUND_MAR_RELATION_MANRepository.save(rm);
			 if(rm!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "CREATE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Rm:"+rm.getSVB_CODE());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Rm:"+rm.getSVB_CODE());
					
					json.put("createdby", rm.getIV_ENTER_UID());
					json.put("createdon", rm.getIV_ENTER_DATE());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Rm not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Rm not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}
	
	@PostMapping("/updaterm")
	public ResponseEntity<?>update(RequestEntity<Map<String,Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SVB_CODE=requestJson.get("CODE");
		Object SVB_NAME=requestJson.get("NAME");
		Object SVB_COUNTRY=requestJson.get("COUNTRY");
		Object SVB_MOBILE_NO=requestJson.get("MOBILE_NO");
		Object SVB_EMAIL_ID=requestJson.get("EMAIL_ID");
		Object SVB_PHONE_NO=requestJson.get("PHONE_NO");
		Object SVB_FAX_NO=requestJson.get("FAX_NO");
		Object SVB_ADDR=requestJson.get("ADDR");
		Object SVB_POST_NO=requestJson.get("POST_NO");
		Object SVB_PC_NO=requestJson.get("PC_NO");
		Object WMS_COMMENTS=requestJson.get("comments");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		 if(SVB_CODE==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
		if(SVB_CODE.toString().isEmpty()==true) {
			json.put("msg", "Plase check id of RM");
			  logger.error("Please check Id of RM");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		FUND_MAR_RELATION_MAN rm=fUND_MAR_RELATION_MANRepository.findOne(new Long(SVB_CODE.toString()));
		if(rm==null) {
			json.put("msg", "Plase check id of RM");
			  logger.error("Please check Id of RM");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		if(SVB_NAME!=null) {
			if(SVB_NAME.toString().isEmpty()==false) {
				rm.setSVB_NAME(SVB_NAME.toString());
			}
		}
				
		
		if(SVB_COUNTRY!=null) {
			if(SVB_COUNTRY.toString().isEmpty()==false) {
				rm.setSVB_COUNTRY(new Long(SVB_COUNTRY.toString()));
			}
		}
		
		
		if(SVB_PHONE_NO!=null) {
			if(SVB_PHONE_NO.toString().isEmpty()==false) {
				rm.setSVB_PHONE_NO(SVB_PHONE_NO.toString());
			}
		}
		
		if(SVB_FAX_NO!=null) {
			if(SVB_FAX_NO.toString().isEmpty()==false) {
				rm.setSVB_FAX_NO(SVB_FAX_NO.toString());
			}
		}
		
		if(SVB_MOBILE_NO!=null) {
			if(SVB_MOBILE_NO.toString().isEmpty()==false) {
				rm.setSVB_MOBILE_NO(SVB_MOBILE_NO.toString());
			}
		}
		
		if(SVB_EMAIL_ID!=null) {
			if(SVB_EMAIL_ID.toString().isEmpty()==false) {
				rm.setSVB_EMAIL_ID(SVB_EMAIL_ID.toString());
			}
		}
		
		if(SVB_ADDR!=null) {
			if(SVB_ADDR.toString().isEmpty()==false) {
				rm.setSVB_ADDR(SVB_ADDR.toString());
			}
		}
		
		if(SVB_POST_NO!=null) {
			if(SVB_POST_NO.toString().isEmpty()==false) {
				rm.setSVB_POST_NO(SVB_POST_NO.toString());
			}
		}
		
		if(SVB_PC_NO!=null) {
			if(SVB_PC_NO.toString().isEmpty()==false) {
				rm.setSVB_PC_NO(SVB_PC_NO.toString());
			}
		}
		
		if(WMS_COMMENTS!=null) {
			rm.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
		//rm.setSVB_STATUS("Not Approved");
				
		 		
		rm.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
		rm.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
		  try
			{
			  rm=fUND_MAR_RELATION_MANRepository.save(rm);
			 if(rm!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "UPDATE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Rm:"+rm.getSVB_CODE());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Rm:"+rm.getSVB_CODE());
					
					RelationshipManager rmjson=getJson(rm);
					rmjson.setMsg("Saved");
					return new ResponseEntity<RelationshipManager>(rmjson,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Rm not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Rm not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}
	
	@PostMapping("/approverm")
	public ResponseEntity<?>approved(RequestEntity<Map<String,Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SVB_CODE=requestJson.get("CODE");
		Object WMS_COMMENTS=requestJson.get("comments");
		Object createdby=requestJson.get("approvedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		 if(SVB_CODE==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
		if(SVB_CODE.toString().isEmpty()==true) {
			json.put("msg", "Plase check id of RM");
			  logger.error("Please check Id of RM");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		FUND_MAR_RELATION_MAN rm=fUND_MAR_RELATION_MANRepository.findOne(new Long(SVB_CODE.toString()));
		if(rm==null) {
			json.put("msg", "Plase check id of RM");
			  logger.error("Please check Id of RM");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		
		if(WMS_COMMENTS!=null) {
			rm.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
		rm.setSVB_STATUS("Approved");
				
		 		
		rm.setIV_APPROVE_UID(fuser.getSVC_UID());
		rm.setIV_APPROVE_DATE(Calendar.getInstance());
		  try
			{
			  rm=fUND_MAR_RELATION_MANRepository.save(rm);
			 if(rm!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "APPROVE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Rm:"+rm.getSVB_CODE());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Rm:"+rm.getSVB_CODE());
					
					RelationshipManager rmjson=getJson(rm);
					rmjson.setMsg("Saved");
					return new ResponseEntity<RelationshipManager>(rmjson,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Rm not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Rm not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}
	
	@PostMapping("/revokerm")
	public ResponseEntity<?>revoke(RequestEntity<Map<String,Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SVB_CODE=requestJson.get("CODE");
		Object WMS_COMMENTS=requestJson.get("comments");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		 if(SVB_CODE==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
		if(SVB_CODE.toString().isEmpty()==true) {
			json.put("msg", "Plase check id of RM");
			  logger.error("Please check Id of RM");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		FUND_MAR_RELATION_MAN rm=fUND_MAR_RELATION_MANRepository.findOne(new Long(SVB_CODE.toString()));
		if(rm==null) {
			json.put("msg", "Plase check id of RM");
			  logger.error("Please check Id of RM");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		
		if(WMS_COMMENTS!=null) {
			rm.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
		rm.setSVB_STATUS("Not Approved");
				
		rm.setIV_APPROVE_UID(null);
		rm.setIV_APPROVE_DATE(null);
		 		
		rm.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
		rm.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
		  try
			{
			  rm=fUND_MAR_RELATION_MANRepository.save(rm);
			 if(rm!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "REVOKE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Rm:"+rm.getSVB_CODE());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Rm:"+rm.getSVB_CODE());
					
					RelationshipManager rmjson=getJson(rm);
					rmjson.setMsg("Saved");
					return new ResponseEntity<RelationshipManager>(rmjson,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Rm not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Rm not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}

	@PostMapping("/deleterm")
	public ResponseEntity<?>delete(RequestEntity<Map<String,Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SVB_CODE=requestJson.get("CODE");
		Object WMS_COMMENTS=requestJson.get("comments");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		 if(SVB_CODE==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
		if(SVB_CODE.toString().isEmpty()==true) {
			json.put("msg", "Plase check id of RM");
			  logger.error("Please check Id of RM");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		FUND_MAR_RELATION_MAN rm=fUND_MAR_RELATION_MANRepository.findOne(new Long(SVB_CODE.toString()));
		if(rm==null) {
			json.put("msg", "Plase check id of RM");
			  logger.error("Please check Id of RM");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		
		if(WMS_COMMENTS!=null) {
			rm.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
//		rm.setSVB_STATUS("Not Approved");
//				
//		rm.setIV_APPROVE_UID(null);
//		rm.setIV_APPROVE_DATE(null);
//		 		
//		rm.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
//		rm.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
		  try
			{
			  fUND_MAR_RELATION_MANRepository.delete(rm);
//			 if(rm!=null) {
//				 	json.put("msg", "saved");				
//				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "REVOKE");
//				 	if(loggerw==true) {
//				 		logger.info("Both Record and Logs saved for Rm:"+rm.getSVB_CODE());
//						json.put("logs","logs are saved");
//				 	}else {
//				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
//						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
//				 	}
//					
//					logger.info("Saved New Rm:"+rm.getSVB_CODE());
//					
//					RelationshipManager rmjson=getJson(rm);
//					rmjson.setMsg("Saved");
//					return new ResponseEntity<RelationshipManager>(rmjson,HttpStatus.OK);
//				}
//				else {
//					json.put("msg","Not saved");
//					logger.error("Rm not saved");
//					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
//				}
				json.put("msg","DELETE");
				logger.error("Rm DELETE");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Rm not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}
	
	@GetMapping("/rmsearch")
	public ResponseEntity<?> getAllMargin(Pageable page,
			@RequestParam(required=false,value="action")String action,
			@RequestParam(required=false,value="paramSearch")String paramString
			){
		Map<String,Object> json=new HashMap<>();
		Page<FUND_MAR_RELATION_MAN> allRm=null;
			if(action!=null) {
				if(action.isEmpty()==false) {
					if(action.equalsIgnoreCase("ALL")) {
						if(paramString!=null) {
							if(paramString.isEmpty()==false) {
								allRm=fUND_MAR_RELATION_MANRepository.findAllRmSearchingPage(paramString,page);
							}
							else {
								allRm=fUND_MAR_RELATION_MANRepository.findAllRm(page);
							}
						}else {
							allRm=fUND_MAR_RELATION_MANRepository.findAllRm(page);
						}
					}
					else if(action.equalsIgnoreCase("APPROVED")) {
						if(paramString!=null) {
							if(paramString.isEmpty()==false) {
								allRm=fUND_MAR_RELATION_MANRepository.findAllAPPROVEDRmSearchingPage(paramString,page);
							}
							else {
								allRm=fUND_MAR_RELATION_MANRepository.findAllAPPROVEDRm(page);
							}
						}else {
							allRm=fUND_MAR_RELATION_MANRepository.findAllAPPROVEDRm(page);
						}
					}
					else if(action.equalsIgnoreCase("UNAPPROVED")) {
						if(paramString!=null) {
							if(paramString.isEmpty()==false) {
								allRm=fUND_MAR_RELATION_MANRepository.findAllUNAPPROVEDRmSearchingPage(paramString,page);
							}
							else {
								allRm=fUND_MAR_RELATION_MANRepository.findAllUNAPPROVEDRm(page);
							}
						}else {
							allRm=fUND_MAR_RELATION_MANRepository.findAllUNAPPROVEDRm(page);
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
				
				if(allRm!=null) {
					Page<RelationshipManager> jsonarry=allRm.map(new Converter<FUND_MAR_RELATION_MAN, RelationshipManager>() {

						@Override
						public RelationshipManager convert(FUND_MAR_RELATION_MAN arg0) {
							RelationshipManager ffb= getJson(arg0);
							return ffb;
						}
						
					});
					return new ResponseEntity<Page<RelationshipManager>>(jsonarry,HttpStatus.OK);
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

	
	public RelationshipManager getJson(FUND_MAR_RELATION_MAN conut) {
		RelationshipManager ffb=new RelationshipManager();
		ffb.setCODE(conut.getSVB_CODE());
		ffb.setDATE(conut.getSVB_DATE()); 
		ffb.setNAME(conut.getSVB_NAME());
		ffb.setANAME(conut.getSVB_ANAME());  
		ffb.setWBROKER(conut.getSVB_WBROKER()); 
		ffb.setEXCHANGE(conut.getSVB_EXCHANGE()); 
		ffb.setCONTACT_PERSON(conut.getSVB_CONTACT_PERSON());  
		ffb.setPHONE_NO(conut.getSVB_PHONE_NO());    
		ffb.setFAX_NO(conut.getSVB_FAX_NO()); 
		ffb.setMOBILE_NO(conut.getSVB_MOBILE_NO());
		ffb.setEMAIL_ID(conut.getSVB_EMAIL_ID());
		ffb.setADDR(conut.getSVB_ADDR());
		ffb.setPOST_NO(conut.getSVB_POST_NO());  
		ffb.setPC_NO(conut.getSVB_PC_NO());
		FUND_COUNTRIES country=stockParameters.getFUND_COUNTRIES(conut.getSVB_COUNTRY());
		if(country!=null) {
			ffb.setCOUNTRYID(country.getSVC_ID());
			ffb.setCOUNTRYNAME(country.getSVC_NAME());
		}else {
			ffb.setCOUNTRYID(null);
			ffb.setCOUNTRYNAME(null);
		}
//	FUND_ACCOUNT_MSTR mstr=fUND_ACCOUNT_MSTRRepository.findOne(conut.getSVB_ACCOUNT_CODE());
//	if(mstr!=null) {
//		ffb.setACCOUNT_CODE(mstr.getSVA_CODE()+"");
//		ffb.setACCOUNT_NAME(mstr.getSVA_NAME()+"");
//	}else
//	{
//		
//			ffb.setACCOUNT_CODE(null);
//			ffb.setACCOUNT_NAME(null);
//		
//	}
		ffb.setACCOUNT_CODE(conut.getSVB_ACCOUNT_CODE());
		/*
		 * 
		 */
		ffb.setUTF_AC(conut.getSVB_UTF_AC());      
		ffb.setUGF_AC(conut.getSVB_UGF_AC());                                      
		ffb.setNBAD_AC(conut.getSVB_NBAD_AC());           
		ffb.setNOMINEE_AC(conut.getSVB_NOMINEE_AC());        
		ffb.setBANK_ACCOUNT(conut.getSVB_BANK_ACCOUNT());   
		ffb.setBANK_NAME(conut.getSVB_BANK_NAME());      
		ffb.setDR_CODE(conut.getSVB_DR_CODE());        
		ffb.setSHORT_NAME(conut.getSVB_SHORT_NAME());     
		ffb.setADDR_STREET(conut.getSVB_ADDR_STREET());    
		ffb.setADDR_AREA(conut.getSVB_ADDR_AREA());      
		ffb.setADDR_CITY(conut.getSVB_ADDR_CITY());      
		ffb.setWEBSITE(conut.getSVB_WEBSITE());        
		ffb.setP_NAME(conut.getSVB_P_NAME());         
		ffb.setP_DEPT(conut.getSVB_P_DEPT());         
		ffb.setP_POSITION(conut.getSVB_P_POSITION());     
		ffb.setP_DIR_PHONE(conut.getSVB_P_DIR_PHONE());                                       	
		ffb.setP_DIR_FAX(conut.getSVB_P_DIR_FAX());                                         
		ffb.setP_DIR_MOBILE(conut.getSVB_P_DIR_MOBILE());                                      
		ffb.setP_DIR_EMAIL(conut.getSVB_P_DIR_EMAIL());                                       
		ffb.setS_NAME(conut.getSVB_S_NAME());                                         
		ffb.setS_DEPT(conut.getSVB_S_DEPT());                                        
		ffb.setS_POSITION(conut.getSVB_T_POSITION());                                        
		ffb.setS_DIR_PHONE(conut.getSVB_S_DIR_PHONE());                                        
		ffb.setS_DIR_FAX(conut.getSVB_S_DIR_FAX());                                        
		ffb.setS_DIR_MOBILE(conut.getSVB_S_DIR_MOBILE());                                        
		ffb.setS_DIR_EMAIL(conut.getSVB_S_DIR_EMAIL());                                        
		ffb.setT_NAME(conut.getSVB_T_NAME());                                        
		ffb.setT_DEPT(conut.getSVB_T_DEPT());                                        
		ffb.setT_POSITION(conut.getSVB_T_POSITION());                                        
		ffb.setT_DIR_PHONE(conut.getSVB_T_DIR_PHONE());                                        
		ffb.setT_DIR_FAX(conut.getSVB_T_DIR_FAX());                                        
		ffb.setT_DIR_MOBILE(conut.getSVB_T_DIR_MOBILE());                                        
		ffb.setT_DIR_EMAIL(conut.getSVB_T_DIR_EMAIL());                                        
		ffb.setComments( conut.getWMS_COMMENTS()+"");
		ffb.setStatus( conut.getSVB_STATUS()+"");
		
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
