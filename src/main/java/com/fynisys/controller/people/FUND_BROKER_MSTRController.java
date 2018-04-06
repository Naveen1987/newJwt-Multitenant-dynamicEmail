package com.fynisys.controller.people;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
import com.fynisys.controller.people.beans.Broker;
import com.fynisys.model.FUND_COUNTRIES;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.people.FUND_ACCOUNT_MSTR;
import com.fynisys.model.people.FUND_BROKER_MSTR;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.people.FUND_ACCOUNT_MSTRRepository;
import com.fynisys.repository.people.FUND_BROKER_MSTRRepository;
import com.fynisys.utilities.FundUserValidate;
import com.fynisys.utilities.StockParameters;
//broker
@RestController
public class FUND_BROKER_MSTRController {

	@Autowired
	FUND_BROKER_MSTRRepository fUND_BROKER_MSTRRepository;
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
	
	@PostMapping("/saveBroker")
	public ResponseEntity<?>save(RequestEntity<Map<String,Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();

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
		FUND_BROKER_MSTR broker=new FUND_BROKER_MSTR();
		if(SVB_NAME!=null) {
			if(SVB_NAME.toString().isEmpty()==false) {
				broker.setSVB_NAME(SVB_NAME.toString());
			}
		}
				
		if(SVB_ACCOUNT_CODE!=null) {
			if(SVB_ACCOUNT_CODE.toString().isEmpty()==false) {
				broker.setSVB_ACCOUNT_CODE(new Long(SVB_ACCOUNT_CODE.toString()));
			}
		}
		
		if(SVB_COUNTRY!=null) {
			if(SVB_COUNTRY.toString().isEmpty()==false) {
				broker.setSVB_COUNTRY(new Long(SVB_COUNTRY.toString()));
			}
		}
		
		if(SVB_CONTACT_PERSON!=null) {
			if(SVB_CONTACT_PERSON.toString().isEmpty()==false) {
				broker.setSVB_CONTACT_PERSON(SVB_CONTACT_PERSON.toString());
			}
		}
		
		if(SVB_PHONE_NO!=null) {
			if(SVB_PHONE_NO.toString().isEmpty()==false) {
				broker.setSVB_PHONE_NO(SVB_PHONE_NO.toString());
			}
		}
		
		if(SVB_FAX_NO!=null) {
			if(SVB_FAX_NO.toString().isEmpty()==false) {
				broker.setSVB_FAX_NO(SVB_FAX_NO.toString());
			}
		}
		
		if(SVB_MOBILE_NO!=null) {
			if(SVB_MOBILE_NO.toString().isEmpty()==false) {
				broker.setSVB_MOBILE_NO(SVB_MOBILE_NO.toString());
			}
		}
		
		if(SVB_EMAIL_ID!=null) {
			if(SVB_EMAIL_ID.toString().isEmpty()==false) {
				broker.setSVB_EMAIL_ID(SVB_EMAIL_ID.toString());
			}
		}
		
		if(SVB_ADDR!=null) {
			if(SVB_ADDR.toString().isEmpty()==false) {
				broker.setSVB_ADDR(SVB_ADDR.toString());
			}
		}
		
		if(SVB_POST_NO!=null) {
			if(SVB_POST_NO.toString().isEmpty()==false) {
				broker.setSVB_POST_NO(SVB_POST_NO.toString());
			}
		}
		
		if(SVB_PC_NO!=null) {
			if(SVB_PC_NO.toString().isEmpty()==false) {
				broker.setSVB_PC_NO(SVB_PC_NO.toString());
			}
		}
		
		if(WMS_COMMENTS!=null) {
			broker.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
		broker.setWMS_STATUS("Not Approved");
				
		 		
		broker.setIV_ENTER_UID(fuser.getSVC_UID());
		broker.setIV_ENTER_DATE(Calendar.getInstance());
		  try
			{
			  broker=fUND_BROKER_MSTRRepository.save(broker);
			 if(broker!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "CREATE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Broker:"+broker.getSVB_CODE());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Broker:"+broker.getSVB_CODE());
					
					json.put("createdby", broker.getIV_ENTER_UID());
					json.put("createdon", broker.getIV_ENTER_DATE());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Broker not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Broker not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}
	
	@PostMapping("/updateBroker")
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
		FUND_BROKER_MSTR broker=fUND_BROKER_MSTRRepository.findOne(new Long(SVB_CODE.toString()));
		if(broker==null) {
			json.put("msg", "Not Broker found with id:"+SVB_CODE.toString());
			logger.error("Not Broker found with id"+SVB_CODE.toString());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		
		
		if(SVB_NAME!=null) {
			if(SVB_NAME.toString().isEmpty()==false) {
				broker.setSVB_NAME(SVB_NAME.toString());
			}
		}
				
		if(SVB_ACCOUNT_CODE!=null) {
			if(SVB_ACCOUNT_CODE.toString().isEmpty()==false) {
				broker.setSVB_ACCOUNT_CODE(new Long(SVB_ACCOUNT_CODE.toString()));
			}
		}
		
		if(SVB_COUNTRY!=null) {
			if(SVB_COUNTRY.toString().isEmpty()==false) {
				broker.setSVB_COUNTRY(new Long(SVB_COUNTRY.toString()));
			}
		}
		
		if(SVB_CONTACT_PERSON!=null) {
			if(SVB_CONTACT_PERSON.toString().isEmpty()==false) {
				broker.setSVB_CONTACT_PERSON(SVB_CONTACT_PERSON.toString());
			}
		}
		
		if(SVB_PHONE_NO!=null) {
			if(SVB_PHONE_NO.toString().isEmpty()==false) {
				broker.setSVB_PHONE_NO(SVB_PHONE_NO.toString());
			}
		}
		
		if(SVB_FAX_NO!=null) {
			if(SVB_FAX_NO.toString().isEmpty()==false) {
				broker.setSVB_FAX_NO(SVB_FAX_NO.toString());
			}
		}
		
		if(SVB_MOBILE_NO!=null) {
			if(SVB_MOBILE_NO.toString().isEmpty()==false) {
				broker.setSVB_MOBILE_NO(SVB_MOBILE_NO.toString());
			}
		}
		
		if(SVB_EMAIL_ID!=null) {
			if(SVB_EMAIL_ID.toString().isEmpty()==false) {
				broker.setSVB_EMAIL_ID(SVB_EMAIL_ID.toString());
			}
		}
		
		if(SVB_ADDR!=null) {
			if(SVB_ADDR.toString().isEmpty()==false) {
				broker.setSVB_ADDR(SVB_ADDR.toString());
			}
		}
		
		if(SVB_POST_NO!=null) {
			if(SVB_POST_NO.toString().isEmpty()==false) {
				broker.setSVB_POST_NO(SVB_POST_NO.toString());
			}
		}
		
		if(SVB_PC_NO!=null) {
			if(SVB_PC_NO.toString().isEmpty()==false) {
				broker.setSVB_PC_NO(SVB_PC_NO.toString());
			}
		}
		
		if(WMS_COMMENTS!=null) {
			broker.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}		 		
		broker.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
		broker.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
		  try
			{
			  broker=fUND_BROKER_MSTRRepository.save(broker);
			 if(broker!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "UPDATE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Broker:"+broker.getSVB_CODE());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Broker:"+broker.getSVB_CODE());
					
//					json.put("createdby", broker.getIV_ENTER_UID());
//					json.put("createdon", broker.getIV_ENTER_DATE());
//					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
					Broker brokerJson=getJson(broker);
					brokerJson.setMsg("saved");
					return new ResponseEntity<Broker>(brokerJson,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Broker not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Broker not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}
	
	
	@PostMapping("/approveBroker")
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
		FUND_BROKER_MSTR broker=fUND_BROKER_MSTRRepository.findOne(new Long(SVB_CODE.toString()));
		if(broker==null) {
			json.put("msg", "Not Broker found with id:"+SVB_CODE.toString());
			logger.error("Not Broker found with id"+SVB_CODE.toString());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		FUND_COUNTRIES fcountry=stockParameters.getFUND_COUNTRIES(broker.getSVB_COUNTRY());
		if(fcountry!=null) {
			if(fcountry.getIV_APPROVE_UID()==null) {
				json.put("msg", "Country is not approved");
				logger.error("Country is not approved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		
		/*
		 * FUND_ACCOUNT_MSTR Work is pending
		 */
		 FUND_ACCOUNT_MSTR  faccount=fUND_ACCOUNT_MSTRRepository.findOne(broker.getSVB_ACCOUNT_CODE());
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
			broker.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}	
		broker.setWMS_STATUS("Approved");
		broker.setIV_APPROVE_UID(fuser.getSVC_UID());
		broker.setIV_APPROVE_DATE(Calendar.getInstance());
		  try
			{
			  broker=fUND_BROKER_MSTRRepository.save(broker);
			 if(broker!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "APPROVE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Broker:"+broker.getSVB_CODE());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Broker:"+broker.getSVB_CODE());
					
					Broker brokerJson=getJson(broker);
					brokerJson.setMsg("saved");
					return new ResponseEntity<Broker>(brokerJson,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Broker not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Broker not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}

	@PostMapping("/revokeBroker")
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
		FUND_BROKER_MSTR broker=fUND_BROKER_MSTRRepository.findOne(new Long(SVB_CODE.toString()));
		if(broker==null) {
			json.put("msg", "Not Broker found with id:"+SVB_CODE.toString());
			logger.error("Not Broker found with id"+SVB_CODE.toString());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		if(WMS_COMMENTS!=null) {
			broker.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}	
		broker.setWMS_STATUS("Not Approved");
		broker.setIV_APPROVE_UID(null);
		broker.setIV_APPROVE_DATE(null);
		broker.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
		broker.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
		  try
			{
			  broker=fUND_BROKER_MSTRRepository.save(broker);
			 if(broker!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "REVOKE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Broker:"+broker.getSVB_CODE());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Broker:"+broker.getSVB_CODE());
					
					Broker brokerJson=getJson(broker);
					brokerJson.setMsg("saved");
					return new ResponseEntity<Broker>(brokerJson,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Broker not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Broker not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}

	@PostMapping("/deleteBroker")
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
		FUND_BROKER_MSTR broker=fUND_BROKER_MSTRRepository.findOne(new Long(SVB_CODE.toString()));
		if(broker==null) {
			json.put("msg", "Not Broker found with id:"+SVB_CODE.toString());
			logger.error("Not Broker found with id"+SVB_CODE.toString());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		if(WMS_COMMENTS!=null) {
			broker.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}	
		broker.setWMS_STATUS("Not Approved");
		broker.setIV_APPROVE_UID(null);
		broker.setIV_APPROVE_DATE(null);
		broker.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
		broker.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
		  try
			{
			    fUND_BROKER_MSTRRepository.delete(broker);
			  	json.put("msg", "Deleted");				
//			 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "DELETE");
//			 	if(loggerw==true) {
//				 		logger.info("Both Record and Logs saved for Broker:"+broker.getSVB_CODE());
//						json.put("logs","logs are saved");
//			 	}else {
//				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
//						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
//			 	}	
//					logger.info("Saved New Broker:"+broker.getSVB_CODE());
//					
//					json.put("createdby", broker.getIV_ENTER_UID());
//					json.put("createdon", broker.getIV_ENTER_DATE());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Broker not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}
	
	@GetMapping("/brokerslist")
	public  ResponseEntity<?> getAllBroker(){
		List<Broker> brokers=new ArrayList<>();
		fUND_BROKER_MSTRRepository.findAll().forEach(bro->{
			Broker br=getJson(bro);
			brokers.add(br);
		});	
		return new ResponseEntity<>(brokers,HttpStatus.OK);
	}
	
	@GetMapping("/brokersearch")
	public ResponseEntity<?> getAllMargin(Pageable page,
			@RequestParam(required=false,value="action")String action,
			@RequestParam(required=false,value="paramSearch")String paramString
			){
		Map<String,Object> json=new HashMap<>();
		Page<FUND_BROKER_MSTR> allBroker=null;
			if(action!=null) {
				if(action.isEmpty()==false) {
					if(action.equalsIgnoreCase("ALL")) {
						if(paramString!=null) {
							if(paramString.isEmpty()==false) {
								allBroker=fUND_BROKER_MSTRRepository.findAllBrokerSearchingPage(paramString,page);
							}
							else {
								allBroker=fUND_BROKER_MSTRRepository.findAllBroker(page);
							}
						}else {
							allBroker=fUND_BROKER_MSTRRepository.findAllBroker(page);
						}
					}
					else if(action.equalsIgnoreCase("APPROVED")) {
						if(paramString!=null) {
							if(paramString.isEmpty()==false) {
								allBroker=fUND_BROKER_MSTRRepository.findAllAPPROVEDBrokerSearchingPage(paramString,page);
							}
							else {
								allBroker=fUND_BROKER_MSTRRepository.findAllAPPROVEDBroker(page);
							}
						}else {
							allBroker=fUND_BROKER_MSTRRepository.findAllAPPROVEDBroker(page);
						}
					}
					else if(action.equalsIgnoreCase("UNAPPROVED")) {
						if(paramString!=null) {
							if(paramString.isEmpty()==false) {
								allBroker=fUND_BROKER_MSTRRepository.findAllUNAPPROVEDBrokerSearchingPage(paramString,page);
							}
							else {
								allBroker=fUND_BROKER_MSTRRepository.findAllUNAPPROVEDBroker(page);
							}
						}else {
							allBroker=fUND_BROKER_MSTRRepository.findAllUNAPPROVEDBroker(page);
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
				
				if(allBroker!=null) {
					Page<Broker> jsonarry=allBroker.map(new Converter<FUND_BROKER_MSTR, Broker>() {

						@Override
						public Broker convert(FUND_BROKER_MSTR arg0) {
							Broker ffb= getJson(arg0);
							return ffb;
						}
						
					});
					return new ResponseEntity<Page<Broker>>(jsonarry,HttpStatus.OK);
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
	
public Broker getJson(FUND_BROKER_MSTR conut) {
	Broker ffb=new Broker();
	ffb.setBroker_CODE(conut.getSVB_CODE()+"");
	ffb.setBroker_NAME(conut.getSVB_NAME());
	ffb.setBroker_ANAME(conut.getSVB_ANAME());  
	ffb.setBroker_WBROKER(conut.getSVB_WBROKER()); 
	ffb.setBroker_EXCHANGE(conut.getSVB_EXCHANGE()); 
	ffb.setBroker_CONTACT_PERSON(conut.getSVB_CONTACT_PERSON());  
	ffb.setBroker_PHONE_NO(conut.getSVB_PHONE_NO());    
	ffb.setBroker_FAX_NO(conut.getSVB_FAX_NO()); 
	ffb.setBroker_MOBILE_NO(conut.getSVB_MOBILE_NO());
	ffb.setBroker_EMAIL_ID(conut.getSVB_EMAIL_ID());
	ffb.setBroker_ADDR(conut.getSVB_ADDR());
	ffb.setBroker_POST_NO(conut.getSVB_POST_NO());  
	ffb.setBroker_PC_NO(conut.getSVB_PC_NO());
	FUND_COUNTRIES country=stockParameters.getFUND_COUNTRIES(conut.getSVB_COUNTRY());
	if(country!=null) {
		ffb.setBroker_COUNTRYID(conut.getSVB_COUNTRY()+"");
		ffb.setBroker_COUNTRYNAME(country.getSVC_NAME());
	}else {
		ffb.setBroker_COUNTRYID(null);
		ffb.setBroker_COUNTRYNAME(null);
	}
FUND_ACCOUNT_MSTR mstr=fUND_ACCOUNT_MSTRRepository.findOne(conut.getSVB_ACCOUNT_CODE());
if(mstr!=null) {
	ffb.setBroker_ACCOUNT_CODE(mstr.getSVA_CODE()+"");
	ffb.setBroker_ACCOUNT_NAME(mstr.getSVA_NAME()+"");
}else
{
	
		ffb.setBroker_ACCOUNT_CODE(null);
		ffb.setBroker_ACCOUNT_NAME(null);
	
}	ffb.setBroker_UTF_AC(conut.getSVB_UTF_AC());      
	ffb.setBroker_UGF_AC(conut.getSVB_UGF_AC());                                      
	ffb.setBroker_NBAD_AC(conut.getSVB_NBAD_AC());           
	ffb.setBroker_NOMINEE_AC(conut.getSVB_NOMINEE_AC());        
	ffb.setBroker_BANK_ACCOUNT(conut.getSVB_BANK_ACCOUNT());   
	ffb.setBroker_BANK_NAME(conut.getSVB_BANK_NAME());      
	ffb.setBroker_DR_CODE(conut.getSVB_DR_CODE());        
	ffb.setBroker_SVC_UID(conut.getSVC_UID());            
	ffb.setBroker_SHORT_NAME(conut.getSVB_SHORT_NAME());    
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
