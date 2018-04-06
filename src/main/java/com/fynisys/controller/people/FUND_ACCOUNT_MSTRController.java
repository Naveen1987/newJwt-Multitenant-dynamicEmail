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
import com.fynisys.controller.people.beans.FundAccount;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.people.FUND_ACCOUNT_MSTR;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.people.FUND_ACCOUNT_MSTRRepository;
import com.fynisys.utilities.FundUserValidate;
import com.fynisys.utilities.StockParameters;
//GL code
@RestController
public class FUND_ACCOUNT_MSTRController {

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
	
	private static final Logger logger=LoggerFactory.getLogger("FUND ACCOUNT MSTR CONTROLLER");
	
	@PostMapping("/saveFundAccount")
	public ResponseEntity<?>save(RequestEntity<Map<String,Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SVA_NAME=requestJson.get("NAME");
		Object WMS_COMMENTS=requestJson.get("comments");
		Object createdby=requestJson.get("createdby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		 if(SVA_NAME==null ||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
		FUND_ACCOUNT_MSTR fund_account=new FUND_ACCOUNT_MSTR();
		if(SVA_NAME!=null) {
			if(SVA_NAME.toString().isEmpty()==false) {
				fund_account.setSVA_NAME(SVA_NAME.toString());
			}
		}
				
		
		if(WMS_COMMENTS!=null) {
			fund_account.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
		fund_account.setWMS_STATUS("Not Approved");
				
		 		
		fund_account.setIV_ENTER_UID(fuser.getSVC_UID());
		fund_account.setIV_ENTER_DATE(Calendar.getInstance());
		  try
			{
			  fund_account=fUND_ACCOUNT_MSTRRepository.save(fund_account);
			 if(fund_account!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "CREATE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Broker:"+fund_account.getSVA_CODE());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Broker:"+fund_account.getSVA_CODE());
					
					json.put("createdby", fund_account.getIV_ENTER_UID());
					json.put("createdon", fund_account.getIV_ENTER_DATE());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("FUND ACCOUNT not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("FUND ACCOUNT not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}
	
	@PostMapping("/updateFundAccount")
	public ResponseEntity<?>update(RequestEntity<Map<String,Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SVA_CODE=requestJson.get("CODE");
		Object SVA_NAME=requestJson.get("NAME");
		
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		 if(SVA_CODE==null ||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
		FUND_ACCOUNT_MSTR fund_account=fUND_ACCOUNT_MSTRRepository.findOne(new Long(SVA_CODE.toString()));
		if(fund_account==null) {
			json.put("msg", "Not Broker found with id:"+SVA_CODE.toString());
			logger.error("Not Broker found with id"+SVA_CODE.toString());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		
		
		if(SVA_NAME!=null) {
			if(SVA_NAME.toString().isEmpty()==false) {
				fund_account.setSVA_NAME(SVA_NAME.toString());
			}
		}
				
	
		
		if(WMS_COMMENTS!=null) {
			fund_account.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}		 		
		fund_account.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
		fund_account.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
		  try
			{
			  fund_account=fUND_ACCOUNT_MSTRRepository.save(fund_account);
			 if(fund_account!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "UPDATE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Broker:"+fund_account.getSVA_CODE());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Broker:"+fund_account.getSVA_CODE());
					
					FundAccount fund_accountJson=getJson(fund_account);
					fund_accountJson.setMsg("saved");
					return new ResponseEntity<FundAccount>(fund_accountJson,HttpStatus.OK);
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
	
	
	@PostMapping("/approveFundAccount")
	public ResponseEntity<?>approve(RequestEntity<Map<String,Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SVA_CODE=requestJson.get("CODE");
		Object WMS_COMMENTS=requestJson.get("comments");
		Object createdby=requestJson.get("approvedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		 if(SVA_CODE==null ||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
		FUND_ACCOUNT_MSTR fund_account=fUND_ACCOUNT_MSTRRepository.findOne(new Long(SVA_CODE.toString()));
		if(fund_account==null) {
			json.put("msg", "Not Broker found with id:"+SVA_CODE.toString());
			logger.error("Not Broker found with id"+SVA_CODE.toString());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
	
		if(WMS_COMMENTS!=null) {
			fund_account.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}	
		fund_account.setWMS_STATUS("Approved");
		fund_account.setIV_APPROVE_UID(fuser.getSVC_UID());
		fund_account.setIV_APPROVE_DATE(Calendar.getInstance());
		  try
			{
			  fund_account=fUND_ACCOUNT_MSTRRepository.save(fund_account);
			 if(fund_account!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "APPROVE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Broker:"+fund_account.getSVA_CODE());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Broker:"+fund_account.getSVA_CODE());
					
					FundAccount fund_accountJson=getJson(fund_account);
					fund_accountJson.setMsg("saved");
					return new ResponseEntity<FundAccount>(fund_accountJson,HttpStatus.OK);
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

	@PostMapping("/revokeFundAccount")
	public ResponseEntity<?>revoke(RequestEntity<Map<String,Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SVA_CODE=requestJson.get("CODE");
		Object WMS_COMMENTS=requestJson.get("comments");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		 if(SVA_CODE==null ||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
		FUND_ACCOUNT_MSTR fund_account=fUND_ACCOUNT_MSTRRepository.findOne(new Long(SVA_CODE.toString()));
		if(fund_account==null) {
			json.put("msg", "Not Broker found with id:"+SVA_CODE.toString());
			logger.error("Not Broker found with id"+SVA_CODE.toString());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		if(WMS_COMMENTS!=null) {
			fund_account.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}	
		fund_account.setWMS_STATUS("Not Approved");
		fund_account.setIV_APPROVE_UID(null);
		fund_account.setIV_APPROVE_DATE(null);
		fund_account.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
		fund_account.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
		  try
			{
			  fund_account=fUND_ACCOUNT_MSTRRepository.save(fund_account);
			 if(fund_account!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "REVOKE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Broker:"+fund_account.getSVA_CODE());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Broker:"+fund_account.getSVA_CODE());
					
					FundAccount fund_accountJson=getJson(fund_account);
					fund_accountJson.setMsg("saved");
					return new ResponseEntity<FundAccount>(fund_accountJson,HttpStatus.OK);
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

	@PostMapping("/deleteFundAccount")
	public ResponseEntity<?>delete(RequestEntity<Map<String,Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SVA_CODE=requestJson.get("CODE");
		Object WMS_COMMENTS=requestJson.get("comments");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		 if(SVA_CODE==null ||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
		FUND_ACCOUNT_MSTR fund_account=fUND_ACCOUNT_MSTRRepository.findOne(new Long(SVA_CODE.toString()));
		if(fund_account==null) {
			json.put("msg", "Not Broker found with id:"+SVA_CODE.toString());
			logger.error("Not Broker found with id"+SVA_CODE.toString());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		if(WMS_COMMENTS!=null) {
			fund_account.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}	
		fund_account.setWMS_STATUS("Not Approved");
		fund_account.setIV_APPROVE_UID(null);
		fund_account.setIV_APPROVE_DATE(null);
		fund_account.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
		fund_account.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
		  try
			{
			    fUND_ACCOUNT_MSTRRepository.delete(fund_account);
			  	json.put("msg", "Deleted");				
//			 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "DELETE");
//			 	if(loggerw==true) {
//				 		logger.info("Both Record and Logs saved for Broker:"+fund_account.getSVA_CODE());
//						json.put("logs","logs are saved");
//			 	}else {
//				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
//						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
//			 	}	
//					logger.info("Saved New Broker:"+fund_account.getSVA_CODE());
//					
//					json.put("createdby", fund_account.getIV_ENTER_UID());
//					json.put("createdon", fund_account.getIV_ENTER_DATE());
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
	
	@GetMapping("/FundAccountsearch")
	public ResponseEntity<?> getAllFundAccount(Pageable page,
			@RequestParam(required=false,value="action")String action,
			@RequestParam(required=false,value="paramSearch")String paramString
			){
		Map<String,Object> json=new HashMap<>();
		Page<FUND_ACCOUNT_MSTR> allFundAccount=null;
			if(action!=null) {
				if(action.isEmpty()==false) {
					if(action.equalsIgnoreCase("ALL")) {
						if(paramString!=null) {
							if(paramString.isEmpty()==false) {
								allFundAccount=fUND_ACCOUNT_MSTRRepository.findAllFundAccountSearchingPage(paramString,page);
							}
							else {
								allFundAccount=fUND_ACCOUNT_MSTRRepository.findAllFundAccount(page);
							}
						}else {
							allFundAccount=fUND_ACCOUNT_MSTRRepository.findAllFundAccount(page);
						}
					}
					else if(action.equalsIgnoreCase("APPROVED")) {
						if(paramString!=null) {
							if(paramString.isEmpty()==false) {
								allFundAccount=fUND_ACCOUNT_MSTRRepository.findAllAPPROVEDFundAccountSearchingPage(paramString,page);
							}
							else {
								allFundAccount=fUND_ACCOUNT_MSTRRepository.findAllAPPROVEDFundAccount(page);
							}
						}else {
							allFundAccount=fUND_ACCOUNT_MSTRRepository.findAllAPPROVEDFundAccount(page);
						}
					}
					else if(action.equalsIgnoreCase("UNAPPROVED")) {
						if(paramString!=null) {
							if(paramString.isEmpty()==false) {
								allFundAccount=fUND_ACCOUNT_MSTRRepository.findAllUNAPPROVEDFundAccountSearchingPage(paramString,page);
							}
							else {
								allFundAccount=fUND_ACCOUNT_MSTRRepository.findAllUNAPPROVEDFundAccount(page);
							}
						}else {
							allFundAccount=fUND_ACCOUNT_MSTRRepository.findAllUNAPPROVEDFundAccount(page);
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
				
				if(allFundAccount!=null) {
					Page<FundAccount> jsonarry=allFundAccount.map(new Converter<FUND_ACCOUNT_MSTR, FundAccount>() {

						@Override
						public FundAccount convert(FUND_ACCOUNT_MSTR arg0) {
							FundAccount ffb= getJson(arg0);
							return ffb;
						}
						
					});
					return new ResponseEntity<Page<FundAccount>>(jsonarry,HttpStatus.OK);
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
	
	@GetMapping("/FundAccounts")	
	public ResponseEntity<?> getAllFundAccount(){
		List<FUND_ACCOUNT_MSTR> allfundAcc=fUND_ACCOUNT_MSTRRepository.findAllFundAccount();
		List<FundAccount> allfunds= new ArrayList<>();
		allfundAcc.forEach(conut->{
			allfunds.add(getJson(conut));
		});
		return new ResponseEntity<>(allfunds,HttpStatus.OK);
	}
	
public FundAccount getJson(FUND_ACCOUNT_MSTR conut) {
	FundAccount ffb=new FundAccount();
	ffb.setFundAccount_CODE(conut.getSVA_CODE()+"");
	ffb.setFundAccount_NAME(conut.getSVA_NAME());
   
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
