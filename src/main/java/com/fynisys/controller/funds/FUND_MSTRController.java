package com.fynisys.controller.funds;


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

import com.fynisys.model.FUND_COUNTRIES;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.FUND_USER_LOG;
import com.fynisys.model.funds.FUND_MSTR;
import com.fynisys.model.parameters.FUND_CURRENCY_MSTR;
import com.fynisys.model.parameters.FUND_EXCHANGE_MSTR;
import com.fynisys.model.parameters.FUND_INDUSTRY_MSTR;
import com.fynisys.model.parameters.FUND_INSTRUMENT_MSTR;
import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.funds.FUND_MSTRRepository;
import com.fynisys.utilities.StockParameters;

/*
  SVC_DATE                       Record Creation Date 
  SVC_CODE                       Auto Number
  SVC_MODULE                     Type of Fund List box (Fund Managment,Investment Banking,Portfolio Management,Private Equity,Proprietary Investments, ) 
  SVC_NAME                       Fund Name
  SVC_SHORT_NAME                 Fund Short Name 
  SVC_SHARE_PRICE	         NAV 
  SVC_CURRECNY                   Fund Currency

  SVC_FUND_MANAGER               Contact Person
  SVC_PHONE                      Phone
  SVC_FAX                        Fax
  SVC_EMAIL                      Email
  SVC_FUND_IPO			 Mobile no

  SVC_PB                         Post box
  SVC_PC                         Post code
  SVC_STATE                      State
  SVC_COUNTRY                    Country
  SVC_REMARKS			 Address
 
  SVC_FUND_TYPE 		FLAG	
  SVC_TERM                       Duration
 SVC_START_DATE                 Issue Date 
  SVC_END_DATE                   End DATE
 SVC_MIN_INVESTMENT             Min Investment
 SVC_MAX_INVESTMENT             Max Investment
 * 
 */
@RestController
public class FUND_MSTRController{
	@Autowired
	FUND_USERSRepository fund_UserRepository;
	
	@Autowired
	FUND_MSTRRepository fUND_MSTRRepository;
	
	@Autowired
	StockParameters stockParameters;
	
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;
	
	private static final Logger logger=LoggerFactory.getLogger("FUND_MSTR CONTROLLER");
	
	
	public boolean checkUniqueName(String name) {
		List<FUND_MSTR> fn=fUND_MSTRRepository.findAllFUND_MSTR_NAME(name);
		if(fn.size()>0) {
			return false;
		}
		else {
			return true;
		}
	}
	

	public boolean checkUniqueSortName(String name) {
		List<FUND_MSTR> fn=fUND_MSTRRepository.findAllFUND_MSTR_SHORTNAME(name);
		if(fn.size()>0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	@PostMapping("/addfund")
	public ResponseEntity<Map<String,Object>> save(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();

		  Object SVC_NAME=requestJson.get("fund_name");
		 
		  Object SVC_MODULE=requestJson.get("business_line");
		  Object SVC_SHARE_PRICE=requestJson.get("nav");
		  Object SVC_CURRECNY=requestJson.get("currencyid");
		  
		  Object SVC_SHORT_NAME=requestJson.get("short_desc");
		  Object SVC_FUND_MANAGER=requestJson.get("person_name");
		  Object SVC_PHONE=requestJson.get("phone_number");
		  Object SVC_FAX=requestJson.get("fax");
		  Object SVC_EMAIL=requestJson.get("email");
		  Object SVC_FUND_IPO=requestJson.get("mobile");
		  Object address1=requestJson.get("address_line_1");
		  Object address2=requestJson.get("address_line_2");
		  //SVC_REMARKS			 Address
		  Object SVC_PB=requestJson.get("post_box");
		  Object SVC_PC=requestJson.get("pc");
		  Object SVC_STATE=requestJson.get("state");
		  Object SVC_COUNTRY=requestJson.get("countryid");
		  Object SVC_FUND_TYPE=requestJson.get("flag");
		  Object SVC_TERM=requestJson.get("duration");
		 
		  Object SVC_START_DATE=requestJson.get("issue_date"); 
		  Object SVC_END_DATE=requestJson.get("end_date");
		 // System.out.println(SVC_START_DATE+""+SVC_END_DATE);
		  Object SVC_MIN_INVESTMENT=requestJson.get("min_investment");
		  Object SVC_MAX_INVESTMENT=requestJson.get("max_investment");
		  Object WMS_COMMENTS=requestJson.get("comments");
			 // Object WMS_STATUS=requestJson.get("status");
		 Object createdby=requestJson.get("createdby");
		 Object SVL_SCREEN=requestJson.get("screentype");
		 Object SVL_DESC=requestJson.get("log");
		 if(SVC_NAME==null||SVC_MODULE==null||SVC_SHARE_PRICE==null||
				 SVC_CURRECNY==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
					  json.put("msg", "Please check Input json, missing some required attributes");
					  logger.error("Please check Input json, missing some required attributes");
				      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
				  }
		 else {
			  FUND_USERS fuser=isValid(createdby.toString());
			  

				if(!checkUniqueName(SVC_NAME.toString().trim())) {
						json.put("msg", "SYS.UK_FUND_MSTR_SVC_NAME");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
					if(!checkUniqueSortName(SVC_SHORT_NAME.toString().trim())) {
						json.put("msg", "SYS.UK_SVC_SHORT_NAME");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
			  
				if(fuser==null)
				{
						json.put("msg", "Createdby is not valid user");
						logger.error("Createdby is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
							
						FUND_MSTR fscm=new FUND_MSTR();
						fscm.setSVC_NAME(SVC_NAME.toString().trim());
						 fscm.setSVC_SHORT_NAME(SVC_SHORT_NAME.toString().trim());
						 fscm.setSVC_MODULE(new Integer(SVC_MODULE.toString().trim()));
						 fscm.setSVC_SHARE_PRICE(new Double(SVC_SHARE_PRICE.toString()));
						 fscm.setSVC_CURRECNY(SVC_CURRECNY.toString().trim());
						  if(SVC_FUND_MANAGER!=null) {
							  fscm.setSVC_FUND_MANAGER(SVC_FUND_MANAGER.toString().trim());
						  }
						 if(SVC_PHONE!=null) {
						 fscm.setSVC_PHONE(SVC_PHONE.toString().trim());
						 }
						 if(SVC_FAX!=null) {
						 fscm.setSVC_FAX(SVC_FAX.toString().trim());
						 }
						 if(SVC_EMAIL!=null) {
						 fscm.setSVC_EMAIL(SVC_EMAIL.toString().trim());
						 }
						 if(SVC_FUND_IPO!=null) {
						 fscm.setSVC_FUND_IPO(SVC_FUND_IPO.toString().trim());
						 }
						 if(address1!=null) {
						 fscm.setSVC_REMARKS(address1.toString().trim());
						 }
						 if(address2!=null) {
							fscm.setSVC_REMARKS(fscm.getSVC_REMARKS()+"*"+address2.toString().trim());
						 }
						  //SVC_REMARKS			 Address
						 if(SVC_PB!=null) {
						 fscm.setSVC_PB(SVC_PB.toString().trim());
						 }
						 if(SVC_PC!=null) {
						 fscm.setSVC_PC(SVC_PC.toString().trim());
						 }
						 if(SVC_STATE!=null) {
						 fscm.setSVC_STATE(SVC_STATE.toString().trim());
						 }
						 if(SVC_COUNTRY!=null) {
						 fscm.setSVC_COUNTRY(SVC_COUNTRY.toString().trim());
						 }
						 if(SVC_FUND_TYPE!=null) {
						 fscm.setSVC_FUND_TYPE(SVC_FUND_TYPE.toString().trim());
						 }
						 
						 if(SVC_TERM!=null)
						 {
							 if(SVC_TERM.toString().isEmpty()==false) {
							 fscm.setSVC_TERM(new Integer(SVC_TERM.toString()));
							 }
						 }
						 if(SVC_START_DATE!=null) {
							 if(!SVC_START_DATE.toString().isEmpty()) {
								 Calendar cal=Calendar.getInstance();
								 	cal.setTimeInMillis(new Long(SVC_START_DATE.toString()));
								 	 fscm.setSVC_START_DATE(cal);
							 }
						 
						 }
						 if(SVC_END_DATE!=null) {
							 if(!SVC_END_DATE.toString().isEmpty())
							 {
								 Calendar cal=Calendar.getInstance();
								 cal.setTimeInMillis(new Long(SVC_END_DATE.toString()));
								 fscm.setSVC_END_DATE(cal);
							 }
						 }
						 if(SVC_MIN_INVESTMENT!=null) {
						 if(SVC_MIN_INVESTMENT.toString().isEmpty()==false)
						 {
							 fscm.setSVC_MIN_INVESTMENT(new Double(SVC_MIN_INVESTMENT.toString().trim()));
						 }
						 }
						 if(SVC_MAX_INVESTMENT!=null) {
							 
						 if(SVC_MAX_INVESTMENT.toString().isEmpty()==false)
						 {
							 fscm.setSVC_MAX_INVESTMENT(new Double(SVC_MAX_INVESTMENT.toString().trim()));
						 }
						 }
						 if(WMS_COMMENTS!=null) {
							  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
								}
						  fscm.setWMS_STATUS("Not Approved");
								
						  fscm.setSVC_UID(fuser.getSVC_UID());		
						  fscm.setIV_ENTER_UID(fuser.getSVC_UID());
						  fscm.setIV_ENTER_DATE(Calendar.getInstance());
						  fscm.setSVC_DATE(Calendar.getInstance());
						
						  
						  try
							{
							  fscm=fUND_MSTRRepository.save(fscm);
							 if(fscm!=null) {
									    json.put("msg", "saved");				
										FUND_USER_LOG ful=new FUND_USER_LOG();
										ful.setSVC_UID(fuser.getSVC_UID());
										ful.setSVL_USERID(fuser.getSVU_NAME());
										ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
										ful.setSVL_TTYPE("CREATE");
										ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
										ful.setSVL_DATE(Calendar.getInstance());
										ful=fUND_USER_LOGRepository.save(ful);
										if(ful!=null) {
											logger.info("Both Record and Logs saved for Fund:"+fscm.getSVC_NAME());
											json.put("logs","logs are saved");
										}
										else {
											logger.info("Record is saved but logs can't saved due error in saving of logs");
											json.put("logs","Record is saved but logs can't saved due error in saving of logs");
										}
									
									logger.info("Saved New Stock:"+fscm.getSVC_NAME());
									json.put("fundid", fscm.getSVC_CODE());
									json.put("fundname", fscm.getSVC_NAME());
									json.put("createdby", fscm.getIV_ENTER_UID());
									json.put("createdon", fscm.getIV_ENTER_DATE());
									return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
								}
								else {
									json.put("msg","Not saved");
									logger.error("fund not saved");
									return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
								}
							}
							catch(Exception e)
							{
								json.put("msg", e.getMessage());
								logger.error("Fund not saved :"+e.getMessage());
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
								
							}
							
						 
						 
				  	
		  }
		
		 
		 
	}
	
	
	@PostMapping("/updatefund")
	public ResponseEntity<Map<String,Object>> update(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();

		  Object SVC_CODE=requestJson.get("fund_id");
		  Object SVC_NAME=requestJson.get("fund_name");
		 
		  Object SVC_MODULE=requestJson.get("business_line");
		  Object SVC_SHARE_PRICE=requestJson.get("nav");
		  Object SVC_CURRECNY=requestJson.get("currencyid");
		  
		  Object SVC_SHORT_NAME=requestJson.get("short_desc");
		  Object SVC_FUND_MANAGER=requestJson.get("person_name");
		  Object SVC_PHONE=requestJson.get("phone_number");
		  Object SVC_FAX=requestJson.get("fax");
		  Object SVC_EMAIL=requestJson.get("email");
		  Object SVC_FUND_IPO=requestJson.get("mobile");
		  Object address1=requestJson.get("address_line_1");
		  Object address2=requestJson.get("address_line_2");
		  //SVC_REMARKS			 Address
		  Object SVC_PB=requestJson.get("post_box");
		  Object SVC_PC=requestJson.get("pc");
		  Object SVC_STATE=requestJson.get("state");
		  Object SVC_COUNTRY=requestJson.get("countryid");
		  Object SVC_FUND_TYPE=requestJson.get("flag");
		  Object SVC_TERM=requestJson.get("duration");
		 
		  Object SVC_START_DATE=requestJson.get("issue_date"); 
		  Object SVC_END_DATE=requestJson.get("end_date");
		  Object SVC_MIN_INVESTMENT=requestJson.get("min_investment");
		  Object SVC_MAX_INVESTMENT=requestJson.get("max_investment");
		  Object WMS_COMMENTS=requestJson.get("comment");
			 // Object WMS_STATUS=requestJson.get("status");
		 Object createdby=requestJson.get("modifiedby");
		 Object SVL_SCREEN=requestJson.get("screentype");
		 Object SVL_DESC=requestJson.get("log");
		 if(SVC_CODE==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
					  json.put("msg", "Please check Input json, missing some required attributes");
					  logger.error("Please check Input json, missing some required attributes");
				      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
				  }
		 else {
			  FUND_USERS fuser=isValid(createdby.toString());
				if(fuser==null)
				{
						json.put("msg", "Modifiedby is not valid user");
						logger.error("Modifiedby is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
							
				try {
					FUND_MSTR fscm=fUND_MSTRRepository.findOne(new Long(SVC_CODE.toString().trim()));
					if(fscm==null) {
						json.put("msg", "FUND is Not valid please send correct Stock id");
						logger.error("FUND is Not valid");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
					if(SVC_NAME!=null) {
					fscm.setSVC_NAME(SVC_NAME.toString().trim());
					}
					if(SVC_SHORT_NAME!=null) {
						if(SVC_SHORT_NAME.toString().isEmpty()==false) {
							fscm.setSVC_SHORT_NAME(SVC_SHORT_NAME.toString().trim());
						}
					}
					if(SVC_MODULE!=null) {
						if(SVC_MODULE.toString().isEmpty()==false)
						{
							fscm.setSVC_MODULE(new Integer(SVC_MODULE.toString().trim()));
						}
					}
					if(SVC_SHARE_PRICE!=null)
					{
						if(SVC_SHARE_PRICE.toString().isEmpty()==false)
						{
							fscm.setSVC_SHARE_PRICE(new Double(SVC_SHARE_PRICE.toString()));
						}
					}
					if(SVC_CURRECNY!=null) {
						fscm.setSVC_CURRECNY(SVC_CURRECNY.toString().trim());
					}
					if(SVC_FUND_MANAGER!=null) {
						  fscm.setSVC_FUND_MANAGER(SVC_FUND_MANAGER.toString().trim());
					}
					
					if(SVC_PHONE!=null) {
					fscm.setSVC_PHONE(SVC_PHONE.toString().trim());
					}
					
					if(SVC_FAX!=null) {
					 fscm.setSVC_FAX(SVC_FAX.toString().trim());
					 }
					 if(SVC_EMAIL!=null) {
					 fscm.setSVC_EMAIL(SVC_EMAIL.toString().trim());
					 }
					 if(SVC_FUND_IPO!=null) {
					 fscm.setSVC_FUND_IPO(SVC_FUND_IPO.toString().trim());
					 }
					 if(address1!=null) {
					 fscm.setSVC_REMARKS(address1.toString().trim());
					 }
					 if(address2!=null) {
						fscm.setSVC_REMARKS(fscm.getSVC_REMARKS()+"*"+address2.toString().trim());
					 }
					  //SVC_REMARKS			 Address
					 if(SVC_PB!=null) {
					 fscm.setSVC_PB(SVC_PB.toString().trim());
					 }
					 if(SVC_PC!=null) {
					 fscm.setSVC_PC(SVC_PC.toString().trim());
					 }
					 if(SVC_STATE!=null) {
					 fscm.setSVC_STATE(SVC_STATE.toString().trim());
					 }
					 if(SVC_COUNTRY!=null) {
					 fscm.setSVC_COUNTRY(SVC_COUNTRY.toString().trim());
					 }
					 if(SVC_FUND_TYPE!=null) {
					 fscm.setSVC_FUND_TYPE(SVC_FUND_TYPE.toString().trim());
					 }
					
					 if(SVC_TERM!=null)
					 {
						 if(!SVC_TERM.toString().isEmpty())
						 fscm.setSVC_TERM(new Integer(SVC_TERM.toString()));
					 }
					 if(SVC_START_DATE!=null) {
						 if(!SVC_START_DATE.toString().isEmpty()) {
							  Calendar cal=Calendar.getInstance();
							 	cal.setTimeInMillis(new Long(SVC_START_DATE.toString()));
							 	 fscm.setSVC_START_DATE(cal);
						 }
					 
					 }
					 if(SVC_END_DATE!=null) {
						 if(!SVC_END_DATE.toString().isEmpty())
						 {
							 Calendar cal=Calendar.getInstance();
							 cal.setTimeInMillis(new Long(SVC_END_DATE.toString()));
							 fscm.setSVC_END_DATE(cal);
						 }
					 }
					 if(SVC_MIN_INVESTMENT!=null) {
					 if(SVC_MIN_INVESTMENT.toString().isEmpty()==false)
					 {
						 fscm.setSVC_MIN_INVESTMENT(new Double(SVC_MIN_INVESTMENT.toString().trim()));
					 }
					 else {
						 fscm.setSVC_MIN_INVESTMENT(new Double(0.0));
					 }
					 }
					 if(SVC_MAX_INVESTMENT!=null) {
						 
					 if(SVC_MAX_INVESTMENT.toString().isEmpty()==false)
					 {
						 fscm.setSVC_MAX_INVESTMENT(new Double(SVC_MAX_INVESTMENT.toString().trim()));
					 }else {
						 fscm.setSVC_MAX_INVESTMENT(new Double(0.0));
					 }
					 }
					
					 if(WMS_COMMENTS!=null) {
						  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
							}
											
					  fscm.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
					  fscm.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
					
					  
					  try
						{
						  fscm=fUND_MSTRRepository.save(fscm);
						 if(fscm!=null) {
								    json.put("msg", "saved");				
									FUND_USER_LOG ful=new FUND_USER_LOG();
									ful.setSVC_UID(fuser.getSVC_UID());
									ful.setSVL_USERID(fuser.getSVU_NAME());
									ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
									ful.setSVL_TTYPE("UPDATE");
									ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
									ful.setSVL_DATE(Calendar.getInstance());
									ful=fUND_USER_LOGRepository.save(ful);
									if(ful!=null) {
										logger.info("Both Record and Logs saved for Fund:"+fscm.getSVC_NAME());
										json.put("logs","logs are saved");
									}
									else {
										logger.info("Record is saved but logs can't saved due error in saving of logs");
										json.put("logs","Record is saved but logs can't saved due error in saving of logs");
									}
								
								logger.info("Saved New Stock:"+fscm.getSVC_NAME());
								json.put("fundid", fscm.getSVC_CODE());
								json.put("fund_name",fscm.getSVC_NAME());
								json.put("short_desc",fscm.getSVC_SHORT_NAME());
								json.put("business_line",fscm.getSVC_MODULE());
								json.put("nav",fscm.getSVC_SHARE_PRICE());
								
								json.put("person_name",fscm.getSVC_FUND_MANAGER());
								json.put("phone_number",fscm.getSVC_PHONE());
								json.put("fax",fscm.getSVC_FAX());
								json.put("email",fscm.getSVC_EMAIL());
								json.put("mobile",fscm.getSVC_FUND_IPO());
								json.put("address",fscm.getSVC_REMARKS());
								json.put("post_box",fscm.getSVC_PB());
								json.put("pc",fscm.getSVC_PC());
								json.put("state",fscm.getSVC_STATE());
								
								FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(fscm.getSVC_CURRECNY());
								if(fcm!=null) {
									json.put("currencyid",fcm.getSVC_CODE());
									json.put("currencyname", fcm.getSVC_NAME());
									json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
								}
								
								FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(fscm.getSVC_COUNTRY());
								if(fcc!=null) {
									json.put("countryid",fcc.getSVC_CODE());
									json.put("countryname", fcc.getSVC_NAME());
									json.put("countryshortname", fcc.getSVC_SHORT_NAME());
								}
								
								
								json.put("flag",fscm.getSVC_FUND_TYPE());
								json.put("duration",fscm.getSVC_TERM());
								//json.put("issue_name",);
								json.put("issue_date",fscm.getSVC_START_DATE());
								json.put("enddate", fscm.getSVC_END_DATE());
								json.put("min_investment",fscm.getSVC_MIN_INVESTMENT());
								json.put("max_investment",fscm.getSVC_MAX_INVESTMENT());
								json.put("comments", fscm.getWMS_COMMENTS());
								json.put("status", fscm.getWMS_STATUS());
								FUND_USERS user=null;
								if(fscm.getIV_ENTER_UID()!=null) {
									user=getUserName(fscm.getSVC_UID());
									if(user!=null) {
									json.put("enteredby", user.getSVU_NAME());
									json.put("enteredbyuserid", user.getSVU_USER_NAME());
									json.put("enteredbyuuid", user.getSVC_UID());
									json.put("entereddate", fscm.getIV_ENTER_DATE());
									}
									else {
										json.put("enteredby", null);
										json.put("enteredbyuserid", null);
										json.put("enteredbyuuid", null);
										json.put("entereddate", null);
									}
								}else {
									json.put("enteredby", null);
									json.put("enteredbyuserid", null);
									json.put("enteredbyuuid", null);
									json.put("entereddate", null);
								}
								
								if(fscm.getIV_APPROVE_UID()!=null) {
									user=getUserName(fscm.getIV_APPROVE_UID());
									if(user!=null) {
									json.put("approvedby", user.getSVU_NAME());
									json.put("approvedbyuserid", user.getSVU_USER_NAME());
									json.put("approvedbyuuid", user.getSVC_UID());
									json.put("approveddate", fscm.getIV_APPROVE_DATE());
									}
									else {
										json.put("approvedby", null);
										json.put("approvedbyuserid", null);
										json.put("approvedbyuuid", null);
										json.put("approveddate", null);
									}
								}else {
									json.put("approvedby", null);
									json.put("approvedbyuserid", null);
									json.put("approvedbyuuid", null);
									json.put("approveddate", null);
								}
								
								if(fscm.getIV_LAST_UPDATE_UID()!=null) {
									user=getUserName(fscm.getIV_LAST_UPDATE_UID());
									if(user!=null) {
									json.put("modifiedby", user.getSVU_NAME());
									json.put("modifiedbyuserid", user.getSVU_USER_NAME());
									json.put("modifiedbyuuid", user.getSVC_UID());
									json.put("modifieddate", fscm.getIV_LAST_UPDATE_DATE());
									}
									else {
										json.put("modifiedby", null);
										json.put("modifiedbyuserid", null);
										json.put("modifiedbyuuid", null);
										json.put("modifieddate", null);
									}
								}else {
									json.put("modifiedby", null);
									json.put("modifiedbyuserid", null);
									json.put("modifiedbyuuid", null);
									json.put("modifieddate", null);
								}
								
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
							}
							else {
								json.put("msg","Not saved");
								logger.error("fund not saved");
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							}
						}
						catch(Exception e)
						{
							json.put("msg", e.getMessage());
							logger.error("Fund not saved :"+e.getMessage());
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							
						}
						
									
				}catch (Exception e) {
					json.put("msg",e.getMessage());
					logger.error("Fund is Not valid"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
						 
						 
				  	
		  }
		
		 
		 
	}
	
	@PostMapping("/approvefund")
	public ResponseEntity<Map<String,Object>> approveFund(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();

		  Object SVC_CODE=requestJson.get("fund_id");
		 
		  Object WMS_COMMENTS=requestJson.get("comments");
			 // Object WMS_STATUS=requestJson.get("status");
		 Object createdby=requestJson.get("approvedby");
		 Object SVL_SCREEN=requestJson.get("screentype");
		 Object SVL_DESC=requestJson.get("log");
		 if(SVC_CODE==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
					  json.put("msg", "Please check Input json, missing some required attributes");
					  logger.error("Please check Input json, missing some required attributes");
				      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
				  }
		 else {
			  FUND_USERS fuser=isValid(createdby.toString());
				if(fuser==null)
				{
						json.put("msg", "Modifiedby is not valid user");
						logger.error("Modifiedby is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
							
				try {
					FUND_MSTR fscm=fUND_MSTRRepository.findOne(new Long(SVC_CODE.toString().trim()));
					if(fscm==null) {
						json.put("msg", "FUND is Not valid please send correct Stock id");
						logger.error("FUND is Not valid");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
					/*
					 * 
					 */
					Object SVC_CUST_COUNTRY=fscm.getSVC_COUNTRY();
					Object SVC_CURR_CODE =fscm.getSVC_CURRECNY();
					
					
					if(SVC_CUST_COUNTRY!=null) {
						FUND_COUNTRIES fund_cust=stockParameters.getFUND_COUNTRIES(SVC_CUST_COUNTRY);
						if(fund_cust.getIV_APPROVE_UID()==null) {
							json.put("msg", "Country is not approved");
							logger.error("Country is not approved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					if(SVC_CURR_CODE!=null) {
						FUND_CURRENCY_MSTR fund_cust=stockParameters.getFUND_CURRENCY_MSTR(SVC_CURR_CODE);
						if(fund_cust.getIV_APPROVE_UID()==null) {
							json.put("msg", "Currency is not approved");
							logger.error("Currency is not approved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}	
					}
					
					 if(WMS_COMMENTS!=null) {
						  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
					  }
					  fscm.setWMS_STATUS("Approved");
					  fscm.setIV_APPROVE_UID(fuser.getSVC_UID());
					  fscm.setIV_APPROVE_DATE(Calendar.getInstance());
					
					  
					  try
						{
						  fscm=fUND_MSTRRepository.save(fscm);
						 if(fscm!=null) {
								    json.put("msg", "saved");				
									FUND_USER_LOG ful=new FUND_USER_LOG();
									ful.setSVC_UID(fuser.getSVC_UID());
									ful.setSVL_USERID(fuser.getSVU_NAME());
									ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
									ful.setSVL_TTYPE("APPROVED");
									ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
									ful.setSVL_DATE(Calendar.getInstance());
									ful=fUND_USER_LOGRepository.save(ful);
									if(ful!=null) {
										logger.info("Both Record and Logs saved for Fund:"+fscm.getSVC_NAME());
										json.put("logs","logs are saved");
									}
									else {
										logger.info("Record is saved but logs can't saved due error in saving of logs");
										json.put("logs","Record is saved but logs can't saved due error in saving of logs");
									}
								
								logger.info("Saved New Stock:"+fscm.getSVC_NAME());
								json.put("fundid", fscm.getSVC_CODE());
								json.put("fund_name",fscm.getSVC_NAME());
								json.put("short_desc",fscm.getSVC_SHORT_NAME());
								json.put("business_line",fscm.getSVC_MODULE());
								json.put("nav",fscm.getSVC_SHARE_PRICE());
								
								json.put("person_name",fscm.getSVC_FUND_MANAGER());
								json.put("phone_number",fscm.getSVC_PHONE());
								json.put("fax",fscm.getSVC_FAX());
								json.put("email",fscm.getSVC_EMAIL());
								json.put("mobile",fscm.getSVC_FUND_IPO());
								json.put("address",fscm.getSVC_REMARKS());
								json.put("post_box",fscm.getSVC_PB());
								json.put("pc",fscm.getSVC_PC());
								json.put("state",fscm.getSVC_STATE());
								
								FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(fscm.getSVC_CURRECNY());
								if(fcm!=null) {
									json.put("currencyid",fcm.getSVC_CODE());
									json.put("currencyname", fcm.getSVC_NAME());
									json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
								}
								
								FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(fscm.getSVC_COUNTRY());
								if(fcc!=null) {
									json.put("countryid",fcc.getSVC_CODE());
									json.put("countryname", fcc.getSVC_NAME());
									json.put("countryshortname", fcc.getSVC_SHORT_NAME());
								}
								
								
								json.put("flag",fscm.getSVC_FUND_TYPE());
								json.put("duration",fscm.getSVC_TERM());
								//json.put("issue_name",);
								json.put("issue_date",fscm.getSVC_START_DATE());
								json.put("enddate", fscm.getSVC_END_DATE());
								json.put("min_investment",fscm.getSVC_MIN_INVESTMENT());
								json.put("max_investment",fscm.getSVC_MAX_INVESTMENT());
								json.put("comments", fscm.getWMS_COMMENTS());
								json.put("status", fscm.getWMS_STATUS());
								FUND_USERS user=null;
								if(fscm.getIV_ENTER_UID()!=null) {
									user=getUserName(fscm.getSVC_UID());
									if(user!=null) {
									json.put("enteredby", user.getSVU_NAME());
									json.put("enteredbyuserid", user.getSVU_USER_NAME());
									json.put("enteredbyuuid", user.getSVC_UID());
									json.put("entereddate", fscm.getIV_ENTER_DATE());
									}
									else {
										json.put("enteredby", null);
										json.put("enteredbyuserid", null);
										json.put("enteredbyuuid", null);
										json.put("entereddate", null);
									}
								}else {
									json.put("enteredby", null);
									json.put("enteredbyuserid", null);
									json.put("enteredbyuuid", null);
									json.put("entereddate", null);
								}
								
								if(fscm.getIV_APPROVE_UID()!=null) {
									user=getUserName(fscm.getIV_APPROVE_UID());
									if(user!=null) {
									json.put("approvedby", user.getSVU_NAME());
									json.put("approvedbyuserid", user.getSVU_USER_NAME());
									json.put("approvedbyuuid", user.getSVC_UID());
									json.put("approveddate", fscm.getIV_APPROVE_DATE());
									}
									else {
										json.put("approvedby", null);
										json.put("approvedbyuserid", null);
										json.put("approvedbyuuid", null);
										json.put("approveddate", null);
									}
								}else {
									json.put("approvedby", null);
									json.put("approvedbyuserid", null);
									json.put("approvedbyuuid", null);
									json.put("approveddate", null);
								}
								
								if(fscm.getIV_LAST_UPDATE_UID()!=null) {
									user=getUserName(fscm.getIV_LAST_UPDATE_UID());
									if(user!=null) {
									json.put("modifiedby", user.getSVU_NAME());
									json.put("modifiedbyuserid", user.getSVU_USER_NAME());
									json.put("modifiedbyuuid", user.getSVC_UID());
									json.put("modifieddate", fscm.getIV_LAST_UPDATE_DATE());
									}
									else {
										json.put("modifiedby", null);
										json.put("modifiedbyuserid", null);
										json.put("modifiedbyuuid", null);
										json.put("modifieddate", null);
									}
								}else {
									json.put("modifiedby", null);
									json.put("modifiedbyuserid", null);
									json.put("modifiedbyuuid", null);
									json.put("modifieddate", null);
								}
								
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
							}
							else {
								json.put("msg","Not saved");
								logger.error("fund not saved");
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							}
						}
						catch(Exception e)
						{
							json.put("msg", e.getMessage());
							logger.error("Fund not saved :"+e.getMessage());
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							
						}
						
									
				}catch (Exception e) {
					json.put("msg",e.getMessage());
					logger.error("Fund is Not valid"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
						 
						 
				  	
		  }
		
		 
		 
	}
	
	@PostMapping("/revokefund")
	public ResponseEntity<Map<String,Object>> revokeFund(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();

		  Object SVC_CODE=requestJson.get("fund_id");
		 
		  Object WMS_COMMENTS=requestJson.get("comments");
			 // Object WMS_STATUS=requestJson.get("status");
		 Object createdby=requestJson.get("modifiedby");
		 Object SVL_SCREEN=requestJson.get("screentype");
		 Object SVL_DESC=requestJson.get("log");
		 if(SVC_CODE==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
					  json.put("msg", "Please check Input json, missing some required attributes");
					  logger.error("Please check Input json, missing some required attributes");
				      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
				  }
		 else {
			  FUND_USERS fuser=isValid(createdby.toString());
				if(fuser==null)
				{
						json.put("msg", "Modifiedby is not valid user");
						logger.error("Modifiedby is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
							
				try {
					FUND_MSTR fscm=fUND_MSTRRepository.findOne(new Long(SVC_CODE.toString().trim()));
					if(fscm==null) {
						json.put("msg", "FUND is Not valid please send correct Stock id");
						logger.error("FUND is Not valid");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
					
					 if(WMS_COMMENTS!=null) {
						//  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
					  }
					  fscm.setWMS_STATUS("Not Approved");
					  fscm.setIV_APPROVE_UID(null);
					  fscm.setIV_APPROVE_DATE(null);
					  fscm.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
					  fscm.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
					  
					  try
						{
						  fscm=fUND_MSTRRepository.save(fscm);
						 if(fscm!=null) {
								    json.put("msg", "saved");				
									FUND_USER_LOG ful=new FUND_USER_LOG();
									ful.setSVC_UID(fuser.getSVC_UID());
									ful.setSVL_USERID(fuser.getSVU_NAME());
									ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
									ful.setSVL_TTYPE("REVOKED");
									ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
									ful.setSVL_DATE(Calendar.getInstance());
									ful=fUND_USER_LOGRepository.save(ful);
									if(ful!=null) {
										logger.info("Both Record and Logs saved for Fund:"+fscm.getSVC_NAME());
										json.put("logs","logs are saved");
									}
									else {
										logger.info("Record is saved but logs can't saved due error in saving of logs");
										json.put("logs","Record is saved but logs can't saved due error in saving of logs");
									}
								
								logger.info("Saved New Stock:"+fscm.getSVC_NAME());
								json.put("fundid", fscm.getSVC_CODE());
								json.put("fund_name",fscm.getSVC_NAME());
								json.put("short_desc",fscm.getSVC_SHORT_NAME());
								json.put("business_line",fscm.getSVC_MODULE());
								json.put("nav",fscm.getSVC_SHARE_PRICE());
								
								json.put("person_name",fscm.getSVC_FUND_MANAGER());
								json.put("phone_number",fscm.getSVC_PHONE());
								json.put("fax",fscm.getSVC_FAX());
								json.put("email",fscm.getSVC_EMAIL());
								json.put("mobile",fscm.getSVC_FUND_IPO());
								json.put("address",fscm.getSVC_REMARKS());
								json.put("post_box",fscm.getSVC_PB());
								json.put("pc",fscm.getSVC_PC());
								json.put("state",fscm.getSVC_STATE());
								
								FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(fscm.getSVC_CURRECNY());
								if(fcm!=null) {
									json.put("currencyid",fcm.getSVC_CODE());
									json.put("currencyname", fcm.getSVC_NAME());
									json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
								}
								
								FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(fscm.getSVC_COUNTRY());
								if(fcc!=null) {
									json.put("countryid",fcc.getSVC_CODE());
									json.put("countryname", fcc.getSVC_NAME());
									json.put("countryshortname", fcc.getSVC_SHORT_NAME());
								}
								
								
								json.put("flag",fscm.getSVC_FUND_TYPE());
								json.put("duration",fscm.getSVC_TERM());
								//json.put("issue_name",);
								json.put("issue_date",fscm.getSVC_START_DATE());
								json.put("enddate", fscm.getSVC_END_DATE());
								json.put("min_investment",fscm.getSVC_MIN_INVESTMENT());
								json.put("max_investment",fscm.getSVC_MAX_INVESTMENT());
								json.put("comments", fscm.getWMS_COMMENTS());
								json.put("status", fscm.getWMS_STATUS());
								FUND_USERS user=null;
								if(fscm.getIV_ENTER_UID()!=null) {
									user=getUserName(fscm.getSVC_UID());
									if(user!=null) {
									json.put("enteredby", user.getSVU_NAME());
									json.put("enteredbyuserid", user.getSVU_USER_NAME());
									json.put("enteredbyuuid", user.getSVC_UID());
									json.put("entereddate", fscm.getIV_ENTER_DATE());
									}
									else {
										json.put("enteredby", null);
										json.put("enteredbyuserid", null);
										json.put("enteredbyuuid", null);
										json.put("entereddate", null);
									}
								}else {
									json.put("enteredby", null);
									json.put("enteredbyuserid", null);
									json.put("enteredbyuuid", null);
									json.put("entereddate", null);
								}
								
								if(fscm.getIV_APPROVE_UID()!=null) {
									user=getUserName(fscm.getIV_APPROVE_UID());
									if(user!=null) {
									json.put("approvedby", user.getSVU_NAME());
									json.put("approvedbyuserid", user.getSVU_USER_NAME());
									json.put("approvedbyuuid", user.getSVC_UID());
									json.put("approveddate", fscm.getIV_APPROVE_DATE());
									}
									else {
										json.put("approvedby", null);
										json.put("approvedbyuserid", null);
										json.put("approvedbyuuid", null);
										json.put("approveddate", null);
									}
								}else {
									json.put("approvedby", null);
									json.put("approvedbyuserid", null);
									json.put("approvedbyuuid", null);
									json.put("approveddate", null);
								}
								
								if(fscm.getIV_LAST_UPDATE_UID()!=null) {
									user=getUserName(fscm.getIV_LAST_UPDATE_UID());
									if(user!=null) {
									json.put("modifiedby", user.getSVU_NAME());
									json.put("modifiedbyuserid", user.getSVU_USER_NAME());
									json.put("modifiedbyuuid", user.getSVC_UID());
									json.put("modifieddate", fscm.getIV_LAST_UPDATE_DATE());
									}
									else {
										json.put("modifiedby", null);
										json.put("modifiedbyuserid", null);
										json.put("modifiedbyuuid", null);
										json.put("modifieddate", null);
									}
								}else {
									json.put("modifiedby", null);
									json.put("modifiedbyuserid", null);
									json.put("modifiedbyuuid", null);
									json.put("modifieddate", null);
								}
								
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
							}
							else {
								json.put("msg","Not saved");
								logger.error("fund not saved");
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							}
						}
						catch(Exception e)
						{
							json.put("msg", e.getMessage());
							logger.error("Fund not saved :"+e.getMessage());
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							
						}
						
									
				}catch (Exception e) {
					json.put("msg",e.getMessage());
					logger.error("Fund is Not valid"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
						 
						 
				  	
		  }
		
		 
		 
	}
	
	
	@PostMapping("/deletefund")
	public ResponseEntity<Map<String,Object>> deleteFund(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();

		  Object SVC_CODE=requestJson.get("fund_id");
		  Object createdby=requestJson.get("modifiedby");
		  Object SVL_SCREEN=requestJson.get("screentype");
		  Object SVL_DESC=requestJson.get("log");
		 if(SVC_CODE==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
					  json.put("msg", "Please check Input json, missing some required attributes");
					  logger.error("Please check Input json, missing some required attributes");
				      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
				  }
		 else {
			  FUND_USERS fuser=isValid(createdby.toString());
				if(fuser==null)
				{
						json.put("msg", "Modifiedby is not valid user");
						logger.error("Modifiedby is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
							
				try {
					FUND_MSTR fscm=fUND_MSTRRepository.findOne(new Long(SVC_CODE.toString().trim()));
					if(fscm==null) {
						json.put("msg", "FUND is Not valid please send correct Stock id");
						logger.error("FUND is Not valid");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
					
					
					  try
						{
						  fUND_MSTRRepository.delete(fscm);
						
								    json.put("msg", "Deleted");				
									FUND_USER_LOG ful=new FUND_USER_LOG();
									ful.setSVC_UID(fuser.getSVC_UID());
									ful.setSVL_USERID(fuser.getSVU_NAME());
									ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
									ful.setSVL_TTYPE("REVOKED");
									ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
									ful.setSVL_DATE(Calendar.getInstance());
									ful=fUND_USER_LOGRepository.save(ful);
									if(ful!=null) {
										logger.info("Both Record and Logs saved for Fund:"+fscm.getSVC_NAME());
										json.put("logs","logs are saved");
									}
									else {
										logger.info("Record is saved but logs can't saved due error in saving of logs");
										json.put("logs","Record is saved but logs can't saved due error in saving of logs");
									}
								
								logger.info("Saved New Stock:"+fscm.getSVC_NAME());
								
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
							
						}
						catch(Exception e)
						{
							json.put("msg", e.getMessage());
							logger.error("Fund not saved :"+e.getMessage());
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							
						}
						
									
				}catch (Exception e) {
					json.put("msg",e.getMessage());
					logger.error("Fund is Not valid"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
						 
						 
				  	
		  }
		
		 
		 
	}
	
	
	@GetMapping("/fundsearch")
	public ResponseEntity<?> getAllFUND_MSTR(Pageable page,
			@RequestParam(value="action",required=false) String action,
			@RequestParam(value="paramSearch",required=false) String paramSearch
			){
		Page<FUND_MSTR> allFUND_MSTR=null;
		List<String>userids=new ArrayList<>();
		if(paramSearch!=null) {
		Iterable<FUND_USERS> users=fund_UserRepository.findByNAME(paramSearch);
		users.forEach(user->{
			userids.add(user.getSVC_UID());
		});
		}
		  if(action==null) {
			 	if(paramSearch==null) {
			 		allFUND_MSTR=fUND_MSTRRepository.findAll(page);
			 	}else {
			 		if(userids.size()>0) {
			 			allFUND_MSTR=fUND_MSTRRepository.findAllFUND_MSTR_ASC_SEARCH(userids,paramSearch, page);
			 		}
			 		else {
			 			allFUND_MSTR=fUND_MSTRRepository.findAllFUND_MSTR_ASC_SEARCH(paramSearch, page);		
			 		}
			 	
			 	}
		   }
		  else if(action.toString().trim().equalsIgnoreCase("ALL")) {
				if(paramSearch==null) {
			 		allFUND_MSTR=fUND_MSTRRepository.findAll(page);
			 	}else {
			 		if(userids.size()>0) {
			 			allFUND_MSTR=fUND_MSTRRepository.findAllFUND_MSTR_ASC_SEARCH(userids,paramSearch, page);
			 		}
			 		else {
			 		allFUND_MSTR=fUND_MSTRRepository.findAllFUND_MSTR_ASC_SEARCH(paramSearch, page);
			 	}
			 	}	
		  }
		  else if(action.toString().trim().equalsIgnoreCase("APPROVED")) {
			  if(paramSearch==null) {
			 		allFUND_MSTR=fUND_MSTRRepository.findAllAPPROVEDFUND_MSTR_ASC(page);
			 	}else {
			 		if(userids.size()>0) {
			 			allFUND_MSTR=fUND_MSTRRepository.findAllAPPROVEDFUND_MSTR_ASC_SEARCH(userids,paramSearch, page);
			 		}
			 		else {
			 		allFUND_MSTR=fUND_MSTRRepository.findAllAPPROVEDFUND_MSTR_ASC_SEARCH(paramSearch, page);
			 		}
			 	}
		  }
		  else if(action.toString().trim().equalsIgnoreCase("UNAPPROVED")) {
			  if(paramSearch==null) {
			 		allFUND_MSTR=fUND_MSTRRepository.findAllUNAPPROVEDFUND_MSTR_ASC(page);
			 	}else {
			 		if(userids.size()>0) {
			 			allFUND_MSTR=fUND_MSTRRepository.findAllUNAPPROVEDFUND_MSTR_ASC_SEARCH(userids,paramSearch, page);
			 		}
			 		else {
			 		allFUND_MSTR=fUND_MSTRRepository.findAllUNAPPROVEDFUND_MSTR_ASC_SEARCH(paramSearch, page);
			 		}
			 	}
		  }
		  if(allFUND_MSTR!=null)
		  {
			  Page<Map<String,Object>> jsonArray=allFUND_MSTR.map(new Converter<FUND_MSTR, Map<String,Object>>() {
					@Override
					public Map<String, Object> convert(FUND_MSTR conut) {
						Map<String,Object> json=new HashMap<String,Object>();
						json.put("fundid", conut.getSVC_CODE());
						json.put("fund_name",conut.getSVC_NAME());
						json.put("short_desc",conut.getSVC_SHORT_NAME());
						json.put("business_line",conut.getSVC_MODULE());
						json.put("nav",conut.getSVC_SHARE_PRICE());
						//json.put("currencyid",conut.getSVC_CURRECNY());
						json.put("person_name",conut.getSVC_FUND_MANAGER());
						json.put("phone_number",conut.getSVC_PHONE());
						json.put("fax",conut.getSVC_FAX());
						json.put("email",conut.getSVC_EMAIL());
						json.put("mobile",conut.getSVC_FUND_IPO());
						json.put("address",conut.getSVC_REMARKS());
						json.put("post_box",conut.getSVC_PB());
						json.put("pc",conut.getSVC_PC());
						json.put("state",conut.getSVC_STATE());
						FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getSVC_CURRECNY());
						if(fcm!=null) {
							json.put("currencyid",fcm.getSVC_CODE());
							json.put("currencyname", fcm.getSVC_NAME());
							json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
						}
						
						FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(conut.getSVC_COUNTRY());
						if(fcc!=null) {
							json.put("countryid",fcc.getSVC_CODE());
							json.put("countryname", fcc.getSVC_NAME());
							json.put("countryshortname", fcc.getSVC_SHORT_NAME());
						}
						//json.put("countryid",conut.getSVC_COUNTRY());
						json.put("flag",conut.getSVC_FUND_TYPE());
						json.put("duration",conut.getSVC_TERM());
						//json.put("issue_name",);
						json.put("issue_date",conut.getSVC_START_DATE());
						json.put("enddate", conut.getSVC_END_DATE());
						json.put("min_investment",conut.getSVC_MIN_INVESTMENT());
						json.put("max_investment",conut.getSVC_MAX_INVESTMENT());
						json.put("comments", conut.getWMS_COMMENTS());
						json.put("status", conut.getWMS_STATUS());
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getSVC_UID());
							if(user!=null) {
							json.put("enteredby", user.getSVU_NAME());
							json.put("enteredbyuserid", user.getSVU_USER_NAME());
							json.put("enteredbyuuid", user.getSVC_UID());
							json.put("entereddate", conut.getIV_ENTER_DATE());
							}
							else {
								json.put("enteredby", null);
								json.put("enteredbyuserid", null);
								json.put("enteredbyuuid", null);
								json.put("entereddate", null);
							}
						}else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
						
						if(conut.getIV_APPROVE_UID()!=null) {
							user=getUserName(conut.getIV_APPROVE_UID());
							if(user!=null) {
							json.put("approvedby", user.getSVU_NAME());
							json.put("approvedbyuserid", user.getSVU_USER_NAME());
							json.put("approvedbyuuid", user.getSVC_UID());
							json.put("approveddate", conut.getIV_APPROVE_DATE());
							}
							else {
								json.put("approvedby", null);
								json.put("approvedbyuserid", null);
								json.put("approvedbyuuid", null);
								json.put("approveddate", null);
							}
						}else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
						
						if(conut.getIV_LAST_UPDATE_UID()!=null) {
							user=getUserName(conut.getIV_LAST_UPDATE_UID());
							if(user!=null) {
							json.put("modifiedby", user.getSVU_NAME());
							json.put("modifiedbyuserid", user.getSVU_USER_NAME());
							json.put("modifiedbyuuid", user.getSVC_UID());
							json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
							}
							else {
								json.put("modifiedby", null);
								json.put("modifiedbyuserid", null);
								json.put("modifiedbyuuid", null);
								json.put("modifieddate", null);
							}
						}else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
						
						return json;
						
						
					}
					
				});
				return new ResponseEntity<Page<Map<String,Object>>>(jsonArray,HttpStatus.OK);  
		  }
		   Map<String,Object> jsonArray=new HashMap<>();
		   jsonArray.put("msg", "No Record Found");
		   return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK); 
		   
	}
	
	@GetMapping("/allfunds")
	public ResponseEntity<?> getAllFUND_MSTR(Pageable page,@RequestParam(value="action",required=false) String action){
		   if(action==null) {
				Page<FUND_MSTR> allFUND_SHARE_COMPANY_MSTR=fUND_MSTRRepository.findAllFUND_MSTR_ASC(page);
				Page<Map<String,Object>> jsonArray=allFUND_SHARE_COMPANY_MSTR.map(new Converter<FUND_MSTR, Map<String,Object>>() {
					@Override
					public Map<String, Object> convert(FUND_MSTR conut) {
						Map<String,Object> json=new HashMap<String,Object>();
						json.put("fundid", conut.getSVC_CODE());
						json.put("fund_name",conut.getSVC_NAME());
						json.put("short_desc",conut.getSVC_SHORT_NAME());
						json.put("business_line",conut.getSVC_MODULE());
						json.put("nav",conut.getSVC_SHARE_PRICE());
						
						json.put("person_name",conut.getSVC_FUND_MANAGER());
						json.put("phone_number",conut.getSVC_PHONE());
						json.put("fax",conut.getSVC_FAX());
						json.put("email",conut.getSVC_EMAIL());
						json.put("mobile",conut.getSVC_FUND_IPO());
						json.put("address",conut.getSVC_REMARKS());
						json.put("post_box",conut.getSVC_PB());
						json.put("pc",conut.getSVC_PC());
						json.put("state",conut.getSVC_STATE());
						if(conut.getSVC_CURRECNY()!=null) {
						FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getSVC_CURRECNY());
						 if(fcm!=null) {
							json.put("currencyid",fcm.getSVC_CODE());
							json.put("currencyname", fcm.getSVC_NAME());
							json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
						 }
						}else {
							
									json.put("currencyid",null);
									json.put("currencyname", null);
									json.put("currencyshortname", null);
							
						}
						if(conut.getSVC_COUNTRY()!=null) {
							FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(conut.getSVC_COUNTRY());
							if(fcc!=null) {
								json.put("countryid",fcc.getSVC_CODE());
								json.put("countryname", fcc.getSVC_NAME());
								json.put("countryshortname", fcc.getSVC_SHORT_NAME());
							}	
						}
						else {
							json.put("countryid",null);
							json.put("countryname", null);
							json.put("countryshortname", null);
						}
						
						
						json.put("flag",conut.getSVC_FUND_TYPE());
						json.put("duration",conut.getSVC_TERM());
						//json.put("issue_name",);
						json.put("issue_date",conut.getSVC_START_DATE());
						json.put("enddate", conut.getSVC_END_DATE());
						json.put("min_investment",conut.getSVC_MIN_INVESTMENT());
						json.put("max_investment",conut.getSVC_MAX_INVESTMENT());
						json.put("comments", conut.getWMS_COMMENTS());
						json.put("status", conut.getWMS_STATUS());
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getSVC_UID());
							if(user!=null) {
							json.put("enteredby", user.getSVU_NAME());
							json.put("enteredbyuserid", user.getSVU_USER_NAME());
							json.put("enteredbyuuid", user.getSVC_UID());
							json.put("entereddate", conut.getIV_ENTER_DATE());
							}
							else {
								json.put("enteredby", null);
								json.put("enteredbyuserid", null);
								json.put("enteredbyuuid", null);
								json.put("entereddate", null);
							}
						}else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
						
						if(conut.getIV_APPROVE_UID()!=null) {
							user=getUserName(conut.getIV_APPROVE_UID());
							if(user!=null) {
							json.put("approvedby", user.getSVU_NAME());
							json.put("approvedbyuserid", user.getSVU_USER_NAME());
							json.put("approvedbyuuid", user.getSVC_UID());
							json.put("approveddate", conut.getIV_APPROVE_DATE());
							}
							else {
								json.put("approvedby", null);
								json.put("approvedbyuserid", null);
								json.put("approvedbyuuid", null);
								json.put("approveddate", null);
							}
						}else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
						
						if(conut.getIV_LAST_UPDATE_UID()!=null) {
							user=getUserName(conut.getIV_LAST_UPDATE_UID());
							if(user!=null) {
							json.put("modifiedby", user.getSVU_NAME());
							json.put("modifiedbyuserid", user.getSVU_USER_NAME());
							json.put("modifiedbyuuid", user.getSVC_UID());
							json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
							}
							else {
								json.put("modifiedby", null);
								json.put("modifiedbyuserid", null);
								json.put("modifiedbyuuid", null);
								json.put("modifieddate", null);
							}
						}else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
						
						return json;
						
						
					}
					
				});
				return new ResponseEntity<Page<Map<String,Object>>>(jsonArray,HttpStatus.OK);
		   }
		   else if(action.toString().trim().equalsIgnoreCase("ALL")) {
			   Page<FUND_MSTR> allFUND_SHARE_COMPANY_MSTR=fUND_MSTRRepository.findAllFUND_MSTR_ASC(page);
				Page<Map<String,Object>> jsonArray=allFUND_SHARE_COMPANY_MSTR.map(new Converter<FUND_MSTR, Map<String,Object>>() {
					@Override
					public Map<String, Object> convert(FUND_MSTR conut) {
						Map<String,Object> json=new HashMap<String,Object>();
						json.put("fundid", conut.getSVC_CODE());
						json.put("fund_name",conut.getSVC_NAME());
						json.put("short_desc",conut.getSVC_SHORT_NAME());
						json.put("business_line",conut.getSVC_MODULE());
						json.put("nav",conut.getSVC_SHARE_PRICE());
						
						json.put("person_name",conut.getSVC_FUND_MANAGER());
						json.put("phone_number",conut.getSVC_PHONE());
						json.put("fax",conut.getSVC_FAX());
						json.put("email",conut.getSVC_EMAIL());
						json.put("mobile",conut.getSVC_FUND_IPO());
						json.put("address",conut.getSVC_REMARKS());
						json.put("post_box",conut.getSVC_PB());
						json.put("pc",conut.getSVC_PC());
						json.put("state",conut.getSVC_STATE());
						
						FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getSVC_CURRECNY());
						if(fcm!=null) {
							json.put("currencyid",fcm.getSVC_CODE());
							json.put("currencyname", fcm.getSVC_NAME());
							json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
						}
						
						FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(conut.getSVC_COUNTRY());
						if(fcc!=null) {
							json.put("countryid",fcc.getSVC_CODE());
							json.put("countryname", fcc.getSVC_NAME());
							json.put("countryshortname", fcc.getSVC_SHORT_NAME());
						}
						
						json.put("flag",conut.getSVC_FUND_TYPE());
						json.put("duration",conut.getSVC_TERM());
						//json.put("issue_name",);
						json.put("issue_date",conut.getSVC_START_DATE());
						json.put("enddate", conut.getSVC_END_DATE());
						json.put("min_investment",conut.getSVC_MIN_INVESTMENT());
						json.put("max_investment",conut.getSVC_MAX_INVESTMENT());
						json.put("comments", conut.getWMS_COMMENTS());
						json.put("status", conut.getWMS_STATUS());
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getSVC_UID());
							if(user!=null) {
							json.put("enteredby", user.getSVU_NAME());
							json.put("enteredbyuserid", user.getSVU_USER_NAME());
							json.put("enteredbyuuid", user.getSVC_UID());
							json.put("entereddate", conut.getIV_ENTER_DATE());
							}
							else {
								json.put("enteredby", null);
								json.put("enteredbyuserid", null);
								json.put("enteredbyuuid", null);
								json.put("entereddate", null);
							}
						}else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
						
						if(conut.getIV_APPROVE_UID()!=null) {
							user=getUserName(conut.getIV_APPROVE_UID());
							if(user!=null) {
							json.put("approvedby", user.getSVU_NAME());
							json.put("approvedbyuserid", user.getSVU_USER_NAME());
							json.put("approvedbyuuid", user.getSVC_UID());
							json.put("approveddate", conut.getIV_APPROVE_DATE());
							}
							else {
								json.put("approvedby", null);
								json.put("approvedbyuserid", null);
								json.put("approvedbyuuid", null);
								json.put("approveddate", null);
							}
						}else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
						
						if(conut.getIV_LAST_UPDATE_UID()!=null) {
							user=getUserName(conut.getIV_LAST_UPDATE_UID());
							if(user!=null) {
							json.put("modifiedby", user.getSVU_NAME());
							json.put("modifiedbyuserid", user.getSVU_USER_NAME());
							json.put("modifiedbyuuid", user.getSVC_UID());
							json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
							}
							else {
								json.put("modifiedby", null);
								json.put("modifiedbyuserid", null);
								json.put("modifiedbyuuid", null);
								json.put("modifieddate", null);
							}
						}else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
						
						return json;
						
						
					}
					
				});
				return new ResponseEntity<Page<Map<String,Object>>>(jsonArray,HttpStatus.OK);
		   }
		   else if(action.toString().trim().equalsIgnoreCase("APPROVED")) {
			   Page<FUND_MSTR> allFUND_SHARE_COMPANY_MSTR=fUND_MSTRRepository.findAllAPPROVEDFUND_MSTR_ASC(page);
				Page<Map<String,Object>> jsonArray=allFUND_SHARE_COMPANY_MSTR.map(new Converter<FUND_MSTR, Map<String,Object>>() {
					@Override
					public Map<String, Object> convert(FUND_MSTR conut) {
						Map<String,Object> json=new HashMap<String,Object>();
						json.put("fundid", conut.getSVC_CODE());
						json.put("fund_name",conut.getSVC_NAME());
						json.put("short_desc",conut.getSVC_SHORT_NAME());
						json.put("business_line",conut.getSVC_MODULE());
						json.put("nav",conut.getSVC_SHARE_PRICE());
						
						json.put("person_name",conut.getSVC_FUND_MANAGER());
						json.put("phone_number",conut.getSVC_PHONE());
						json.put("fax",conut.getSVC_FAX());
						json.put("email",conut.getSVC_EMAIL());
						json.put("mobile",conut.getSVC_FUND_IPO());
						json.put("address",conut.getSVC_REMARKS());
						json.put("post_box",conut.getSVC_PB());
						json.put("pc",conut.getSVC_PC());
						json.put("state",conut.getSVC_STATE());
						
						FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getSVC_CURRECNY());
						if(fcm!=null) {
							json.put("currencyid",fcm.getSVC_CODE());
							json.put("currencyname", fcm.getSVC_NAME());
							json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
						}
						
						FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(conut.getSVC_COUNTRY());
						if(fcc!=null) {
							json.put("countryid",fcc.getSVC_CODE());
							json.put("countryname", fcc.getSVC_NAME());
							json.put("countryshortname", fcc.getSVC_SHORT_NAME());
						}
						json.put("flag",conut.getSVC_FUND_TYPE());
						json.put("duration",conut.getSVC_TERM());
						//json.put("issue_name",);
						json.put("issue_date",conut.getSVC_START_DATE());
						json.put("enddate", conut.getSVC_END_DATE());
						json.put("min_investment",conut.getSVC_MIN_INVESTMENT());
						json.put("max_investment",conut.getSVC_MAX_INVESTMENT());
						json.put("comments", conut.getWMS_COMMENTS());
						json.put("status", conut.getWMS_STATUS());
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getSVC_UID());
							if(user!=null) {
							json.put("enteredby", user.getSVU_NAME());
							json.put("enteredbyuserid", user.getSVU_USER_NAME());
							json.put("enteredbyuuid", user.getSVC_UID());
							json.put("entereddate", conut.getIV_ENTER_DATE());
							}
							else {
								json.put("enteredby", null);
								json.put("enteredbyuserid", null);
								json.put("enteredbyuuid", null);
								json.put("entereddate", null);
							}
						}else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
						
						if(conut.getIV_APPROVE_UID()!=null) {
							user=getUserName(conut.getIV_APPROVE_UID());
							if(user!=null) {
							json.put("approvedby", user.getSVU_NAME());
							json.put("approvedbyuserid", user.getSVU_USER_NAME());
							json.put("approvedbyuuid", user.getSVC_UID());
							json.put("approveddate", conut.getIV_APPROVE_DATE());
							}
							else {
								json.put("approvedby", null);
								json.put("approvedbyuserid", null);
								json.put("approvedbyuuid", null);
								json.put("approveddate", null);
							}
						}else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
						
						if(conut.getIV_LAST_UPDATE_UID()!=null) {
							user=getUserName(conut.getIV_LAST_UPDATE_UID());
							if(user!=null) {
							json.put("modifiedby", user.getSVU_NAME());
							json.put("modifiedbyuserid", user.getSVU_USER_NAME());
							json.put("modifiedbyuuid", user.getSVC_UID());
							json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
							}
							else {
								json.put("modifiedby", null);
								json.put("modifiedbyuserid", null);
								json.put("modifiedbyuuid", null);
								json.put("modifieddate", null);
							}
						}else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
						
						return json;
						
						
					}
					
				});
				return new ResponseEntity<Page<Map<String,Object>>>(jsonArray,HttpStatus.OK);
		   }
		   else if(action.toString().trim().equalsIgnoreCase("UNAPPROVED")) {
			   Page<FUND_MSTR> allFUND_SHARE_COMPANY_MSTR=fUND_MSTRRepository.findAllUNAPPROVEDFUND_MSTR_ASC(page);
				Page<Map<String,Object>> jsonArray=allFUND_SHARE_COMPANY_MSTR.map(new Converter<FUND_MSTR, Map<String,Object>>() {
					@Override
					public Map<String, Object> convert(FUND_MSTR conut) {
						Map<String,Object> json=new HashMap<String,Object>();
						json.put("fundid", conut.getSVC_CODE());
						json.put("fund_name",conut.getSVC_NAME());
						json.put("short_desc",conut.getSVC_SHORT_NAME());
						json.put("business_line",conut.getSVC_MODULE());
						json.put("nav",conut.getSVC_SHARE_PRICE());
						
						json.put("person_name",conut.getSVC_FUND_MANAGER());
						json.put("phone_number",conut.getSVC_PHONE());
						json.put("fax",conut.getSVC_FAX());
						json.put("email",conut.getSVC_EMAIL());
						json.put("mobile",conut.getSVC_FUND_IPO());
						json.put("address",conut.getSVC_REMARKS());
						json.put("post_box",conut.getSVC_PB());
						json.put("pc",conut.getSVC_PC());
						json.put("state",conut.getSVC_STATE());
						
						FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getSVC_CURRECNY());
						if(fcm!=null) {
							json.put("currencyid",fcm.getSVC_CODE());
							json.put("currencyname", fcm.getSVC_NAME());
							json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
						}
						
						FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(conut.getSVC_COUNTRY());
						if(fcc!=null) {
							json.put("countryid",fcc.getSVC_CODE());
							json.put("countryname", fcc.getSVC_NAME());
							json.put("countryshortname", fcc.getSVC_SHORT_NAME());
						}
						json.put("flag",conut.getSVC_FUND_TYPE());
						json.put("duration",conut.getSVC_TERM());
						//json.put("issue_name",);
						json.put("issue_date",conut.getSVC_START_DATE());
						json.put("enddate", conut.getSVC_END_DATE());
						json.put("min_investment",conut.getSVC_MIN_INVESTMENT());
						json.put("max_investment",conut.getSVC_MAX_INVESTMENT());
						json.put("comments", conut.getWMS_COMMENTS());
						json.put("status", conut.getWMS_STATUS());
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getSVC_UID());
							if(user!=null) {
							json.put("enteredby", user.getSVU_NAME());
							json.put("enteredbyuserid", user.getSVU_USER_NAME());
							json.put("enteredbyuuid", user.getSVC_UID());
							json.put("entereddate", conut.getIV_ENTER_DATE());
							}
							else {
								json.put("enteredby", null);
								json.put("enteredbyuserid", null);
								json.put("enteredbyuuid", null);
								json.put("entereddate", null);
							}
						}else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
						
						if(conut.getIV_APPROVE_UID()!=null) {
							user=getUserName(conut.getIV_APPROVE_UID());
							if(user!=null) {
							json.put("approvedby", user.getSVU_NAME());
							json.put("approvedbyuserid", user.getSVU_USER_NAME());
							json.put("approvedbyuuid", user.getSVC_UID());
							json.put("approveddate", conut.getIV_APPROVE_DATE());
							}
							else {
								json.put("approvedby", null);
								json.put("approvedbyuserid", null);
								json.put("approvedbyuuid", null);
								json.put("approveddate", null);
							}
						}else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
						
						if(conut.getIV_LAST_UPDATE_UID()!=null) {
							user=getUserName(conut.getIV_LAST_UPDATE_UID());
							if(user!=null) {
							json.put("modifiedby", user.getSVU_NAME());
							json.put("modifiedbyuserid", user.getSVU_USER_NAME());
							json.put("modifiedbyuuid", user.getSVC_UID());
							json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
							}
							else {
								json.put("modifiedby", null);
								json.put("modifiedbyuserid", null);
								json.put("modifiedbyuuid", null);
								json.put("modifieddate", null);
							}
						}else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
						
						return json;
						
						
					}
					
				});
				return new ResponseEntity<Page<Map<String,Object>>>(jsonArray,HttpStatus.OK);
		   }else {
			   Map<String,Object> jsonArray=new HashMap<>();
			   jsonArray.put("msg", "No Record Found");
			   return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK); 
		   }
	}
	
	@PostMapping("/allfundslist")
	public ResponseEntity<?> getAllFUND_MSTR_LIST(@RequestParam(value="action",required=false) String action){
		List<Map<String,Object>> jsonArray=new ArrayList<Map<String,Object>>();
		   if(action==null) {
			fUND_MSTRRepository.findAllFUND_MSTR_ASC().forEach(conut->{
				Map<String,Object> json=new HashMap<String,Object>();
				json.put("fundid", conut.getSVC_CODE());
				json.put("fund_name",conut.getSVC_NAME());
				json.put("short_desc",conut.getSVC_SHORT_NAME());
				json.put("business_line",conut.getSVC_MODULE());
				json.put("nav",conut.getSVC_SHARE_PRICE());
				
				json.put("person_name",conut.getSVC_FUND_MANAGER());
				json.put("phone_number",conut.getSVC_PHONE());
				json.put("fax",conut.getSVC_FAX());
				json.put("email",conut.getSVC_EMAIL());
				json.put("mobile",conut.getSVC_FUND_IPO());
				json.put("address",conut.getSVC_REMARKS());
				json.put("post_box",conut.getSVC_PB());
				json.put("pc",conut.getSVC_PC());
				json.put("state",conut.getSVC_STATE());
				
				FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getSVC_CURRECNY());
				if(fcm!=null) {
					json.put("currencyid",fcm.getSVC_CODE());
					json.put("currencyname", fcm.getSVC_NAME());
					json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
				}
				
				FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(conut.getSVC_COUNTRY());
				if(fcc!=null) {
					json.put("countryid",fcc.getSVC_CODE());
					json.put("countryname", fcc.getSVC_NAME());
					json.put("countryshortname", fcc.getSVC_SHORT_NAME());
				}
				
				json.put("flag",conut.getSVC_FUND_TYPE());
				json.put("duration",conut.getSVC_TERM());
				//json.put("issue_name",);
				json.put("issue_date",conut.getSVC_START_DATE());
				json.put("enddate", conut.getSVC_END_DATE());
				json.put("min_investment",conut.getSVC_MIN_INVESTMENT());
				json.put("max_investment",conut.getSVC_MAX_INVESTMENT());
				json.put("comments", conut.getWMS_COMMENTS());
				json.put("status", conut.getWMS_STATUS());
				FUND_USERS user=null;
				if(conut.getIV_ENTER_UID()!=null) {
					user=getUserName(conut.getSVC_UID());
					if(user!=null) {
					json.put("enteredby", user.getSVU_NAME());
					json.put("enteredbyuserid", user.getSVU_USER_NAME());
					json.put("enteredbyuuid", user.getSVC_UID());
					json.put("entereddate", conut.getIV_ENTER_DATE());
					}
					else {
						json.put("enteredby", null);
						json.put("enteredbyuserid", null);
						json.put("enteredbyuuid", null);
						json.put("entereddate", null);
					}
				}else {
					json.put("enteredby", null);
					json.put("enteredbyuserid", null);
					json.put("enteredbyuuid", null);
					json.put("entereddate", null);
				}
				
				if(conut.getIV_APPROVE_UID()!=null) {
					user=getUserName(conut.getIV_APPROVE_UID());
					if(user!=null) {
					json.put("approvedby", user.getSVU_NAME());
					json.put("approvedbyuserid", user.getSVU_USER_NAME());
					json.put("approvedbyuuid", user.getSVC_UID());
					json.put("approveddate", conut.getIV_APPROVE_DATE());
					}
					else {
						json.put("approvedby", null);
						json.put("approvedbyuserid", null);
						json.put("approvedbyuuid", null);
						json.put("approveddate", null);
					}
				}else {
					json.put("approvedby", null);
					json.put("approvedbyuserid", null);
					json.put("approvedbyuuid", null);
					json.put("approveddate", null);
				}
				
				if(conut.getIV_LAST_UPDATE_UID()!=null) {
					user=getUserName(conut.getIV_LAST_UPDATE_UID());
					if(user!=null) {
					json.put("modifiedby", user.getSVU_NAME());
					json.put("modifiedbyuserid", user.getSVU_USER_NAME());
					json.put("modifiedbyuuid", user.getSVC_UID());
					json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
					}
					else {
						json.put("modifiedby", null);
						json.put("modifiedbyuserid", null);
						json.put("modifiedbyuuid", null);
						json.put("modifieddate", null);
					}
				}else {
					json.put("modifiedby", null);
					json.put("modifiedbyuserid", null);
					json.put("modifiedbyuuid", null);
					json.put("modifieddate", null);
				}
				
				jsonArray.add(json);
				
				
			});
		   }else if(action.toString().trim().equalsIgnoreCase("ALL")) {
				fUND_MSTRRepository.findAllFUND_MSTR_ASC().forEach(conut->{
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("fundid", conut.getSVC_CODE());
					json.put("fund_name",conut.getSVC_NAME());
					json.put("short_desc",conut.getSVC_SHORT_NAME());
					json.put("business_line",conut.getSVC_MODULE());
					json.put("nav",conut.getSVC_SHARE_PRICE());
					
					json.put("person_name",conut.getSVC_FUND_MANAGER());
					json.put("phone_number",conut.getSVC_PHONE());
					json.put("fax",conut.getSVC_FAX());
					json.put("email",conut.getSVC_EMAIL());
					json.put("mobile",conut.getSVC_FUND_IPO());
					json.put("address",conut.getSVC_REMARKS());
					json.put("post_box",conut.getSVC_PB());
					json.put("pc",conut.getSVC_PC());
					json.put("state",conut.getSVC_STATE());
					FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getSVC_CURRECNY());
					if(fcm!=null) {
						json.put("currencyid",fcm.getSVC_CODE());
						json.put("currencyname", fcm.getSVC_NAME());
						json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
					}
					
					FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(conut.getSVC_COUNTRY());
					if(fcc!=null) {
						json.put("countryid",fcc.getSVC_CODE());
						json.put("countryname", fcc.getSVC_NAME());
						json.put("countryshortname", fcc.getSVC_SHORT_NAME());
					}
					json.put("flag",conut.getSVC_FUND_TYPE());
					json.put("duration",conut.getSVC_TERM());
					//json.put("issue_name",);
					json.put("issue_date",conut.getSVC_START_DATE());
					json.put("enddate", conut.getSVC_END_DATE());
					json.put("min_investment",conut.getSVC_MIN_INVESTMENT());
					json.put("max_investment",conut.getSVC_MAX_INVESTMENT());
					json.put("comments", conut.getWMS_COMMENTS());
					json.put("status", conut.getWMS_STATUS());
					FUND_USERS user=null;
					if(conut.getIV_ENTER_UID()!=null) {
						user=getUserName(conut.getSVC_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", conut.getIV_ENTER_DATE());
						}
						else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
					}else {
						json.put("enteredby", null);
						json.put("enteredbyuserid", null);
						json.put("enteredbyuuid", null);
						json.put("entereddate", null);
					}
					
					if(conut.getIV_APPROVE_UID()!=null) {
						user=getUserName(conut.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", conut.getIV_APPROVE_DATE());
						}
						else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
					}else {
						json.put("approvedby", null);
						json.put("approvedbyuserid", null);
						json.put("approvedbyuuid", null);
						json.put("approveddate", null);
					}
					
					if(conut.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(conut.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
						}
						else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
					}else {
						json.put("modifiedby", null);
						json.put("modifiedbyuserid", null);
						json.put("modifiedbyuuid", null);
						json.put("modifieddate", null);
					}
					
					jsonArray.add(json);
					
					
				});
		   }else if(action.toString().trim().equalsIgnoreCase("APPROVED")) {
				fUND_MSTRRepository.findAllAPPROVEDFUND_MSTR_ASC().forEach(conut->{
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("fundid", conut.getSVC_CODE());
					json.put("fund_name",conut.getSVC_NAME());
					json.put("short_desc",conut.getSVC_SHORT_NAME());
					json.put("business_line",conut.getSVC_MODULE());
					json.put("nav",conut.getSVC_SHARE_PRICE());
					
					json.put("person_name",conut.getSVC_FUND_MANAGER());
					json.put("phone_number",conut.getSVC_PHONE());
					json.put("fax",conut.getSVC_FAX());
					json.put("email",conut.getSVC_EMAIL());
					json.put("mobile",conut.getSVC_FUND_IPO());
					json.put("address",conut.getSVC_REMARKS());
					json.put("post_box",conut.getSVC_PB());
					json.put("pc",conut.getSVC_PC());
					json.put("state",conut.getSVC_STATE());
					FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getSVC_CURRECNY());
					if(fcm!=null) {
						json.put("currencyid",fcm.getSVC_CODE());
						json.put("currencyname", fcm.getSVC_NAME());
						json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
					}
					
					FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(conut.getSVC_COUNTRY());
					if(fcc!=null) {
						json.put("countryid",fcc.getSVC_CODE());
						json.put("countryname", fcc.getSVC_NAME());
						json.put("countryshortname", fcc.getSVC_SHORT_NAME());
					}
					json.put("flag",conut.getSVC_FUND_TYPE());
					json.put("duration",conut.getSVC_TERM());
					//json.put("issue_name",);
					json.put("issue_date",conut.getSVC_START_DATE());
					json.put("enddate", conut.getSVC_END_DATE());
					json.put("min_investment",conut.getSVC_MIN_INVESTMENT());
					json.put("max_investment",conut.getSVC_MAX_INVESTMENT());
					json.put("comments", conut.getWMS_COMMENTS());
					json.put("status", conut.getWMS_STATUS());
					FUND_USERS user=null;
					if(conut.getIV_ENTER_UID()!=null) {
						user=getUserName(conut.getSVC_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", conut.getIV_ENTER_DATE());
						}
						else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
					}else {
						json.put("enteredby", null);
						json.put("enteredbyuserid", null);
						json.put("enteredbyuuid", null);
						json.put("entereddate", null);
					}
					
					if(conut.getIV_APPROVE_UID()!=null) {
						user=getUserName(conut.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", conut.getIV_APPROVE_DATE());
						}
						else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
					}else {
						json.put("approvedby", null);
						json.put("approvedbyuserid", null);
						json.put("approvedbyuuid", null);
						json.put("approveddate", null);
					}
					
					if(conut.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(conut.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
						}
						else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
					}else {
						json.put("modifiedby", null);
						json.put("modifiedbyuserid", null);
						json.put("modifiedbyuuid", null);
						json.put("modifieddate", null);
					}
					
					jsonArray.add(json);
					
					
				});
		   }else if(action.toString().trim().equalsIgnoreCase("UNAPPROVED")) {
				fUND_MSTRRepository.findAllUNAPPROVEDFUND_MSTR_ASC().forEach(conut->{
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("fundid", conut.getSVC_CODE());
					json.put("fund_name",conut.getSVC_NAME());
					json.put("short_desc",conut.getSVC_SHORT_NAME());
					json.put("business_line",conut.getSVC_MODULE());
					json.put("nav",conut.getSVC_SHARE_PRICE());
					
					json.put("person_name",conut.getSVC_FUND_MANAGER());
					json.put("phone_number",conut.getSVC_PHONE());
					json.put("fax",conut.getSVC_FAX());
					json.put("email",conut.getSVC_EMAIL());
					json.put("mobile",conut.getSVC_FUND_IPO());
					json.put("address",conut.getSVC_REMARKS());
					json.put("post_box",conut.getSVC_PB());
					json.put("pc",conut.getSVC_PC());
					json.put("state",conut.getSVC_STATE());
					FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getSVC_CURRECNY());
					if(fcm!=null) {
						json.put("currencyid",fcm.getSVC_CODE());
						json.put("currencyname", fcm.getSVC_NAME());
						json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
					}
					
					FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(conut.getSVC_COUNTRY());
					if(fcc!=null) {
						json.put("countryid",fcc.getSVC_CODE());
						json.put("countryname", fcc.getSVC_NAME());
						json.put("countryshortname", fcc.getSVC_SHORT_NAME());
					}
					json.put("flag",conut.getSVC_FUND_TYPE());
					json.put("duration",conut.getSVC_TERM());
					//json.put("issue_name",);
					json.put("issue_date",conut.getSVC_START_DATE());
					json.put("enddate", conut.getSVC_END_DATE());
					json.put("min_investment",conut.getSVC_MIN_INVESTMENT());
					json.put("max_investment",conut.getSVC_MAX_INVESTMENT());
					json.put("comments", conut.getWMS_COMMENTS());
					json.put("status", conut.getWMS_STATUS());
					FUND_USERS user=null;
					if(conut.getIV_ENTER_UID()!=null) {
						user=getUserName(conut.getSVC_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", conut.getIV_ENTER_DATE());
						}
						else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
					}else {
						json.put("enteredby", null);
						json.put("enteredbyuserid", null);
						json.put("enteredbyuuid", null);
						json.put("entereddate", null);
					}
					
					if(conut.getIV_APPROVE_UID()!=null) {
						user=getUserName(conut.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", conut.getIV_APPROVE_DATE());
						}
						else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
					}else {
						json.put("approvedby", null);
						json.put("approvedbyuserid", null);
						json.put("approvedbyuuid", null);
						json.put("approveddate", null);
					}
					
					if(conut.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(conut.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
						}
						else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
					}else {
						json.put("modifiedby", null);
						json.put("modifiedbyuserid", null);
						json.put("modifiedbyuuid", null);
						json.put("modifieddate", null);
					}
					
					jsonArray.add(json);
					
					
				});
		   }
		return new ResponseEntity<List<Map<String,Object>>>(jsonArray,HttpStatus.OK);
	}
 
	
	
	
	public FUND_USERS isValid(String userid)
	{
		FUND_USERS fuser= fund_UserRepository.findByUSER_NAME(userid);
		if(fuser!=null)
		{
			return fuser;
		}
		else
		{
		
		return null;
		}
		
	}
	
	public FUND_USERS getUserName(String userid)
	{
		FUND_USERS fuser= fund_UserRepository.findOne(userid);
		if(fuser!=null)
		{
			return fuser;
		}
		else
		{
		
		return null;
		}
		
	}

	
}

