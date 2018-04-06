package com.fynisys.controller.funds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fynisys.controller.funds.beans.MarginBean;
import com.fynisys.controller.funds.beans.StopLossBean;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.FUND_USER_LOG;
import com.fynisys.model.clienttype.FUND_MAR_CLIENT_TYPE;
import com.fynisys.model.crm.RE_INVESTOR;
import com.fynisys.model.funds.FUND_MAR_MARGIN_MSTR_SECU_LEV;
import com.fynisys.model.funds.FUND_MSTR;
import com.fynisys.model.parameters.FUND_INSTRUMENT_MSTR;
import com.fynisys.model.stock.FUND_SHARE_COMPANY_MSTR;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.clienttype.FUND_CLIENT_DOCUMENTS_TYPE2Repository;
import com.fynisys.repository.clienttype.FUND_MAR_CLIENT_TYPERespository;
import com.fynisys.repository.crm.RE_INVESTORRepository;
import com.fynisys.repository.funds.FUND_MAR_MARGIN_MSTR_SECU_LEVRepository;
import com.fynisys.repository.funds.FUND_MSTRRepository;
import com.fynisys.repository.stock.FUND_SHARE_COMPANY_MSTRRepository;
import com.fynisys.utilities.FundUserValidate;
import com.fynisys.utilities.StockParameters;
/*
FMD_SNO	NUMBER (10)	PRIMARY KEY	NOT NULL	Serial no (Auto generated)
FMD_DATE	DATE			effect date
FMD_INITIAL	NUMBER (156)			
FMD_MAINTENANCE	NUMBER (156)			
FMD_LIQUIDATION	NUMBER (156)			
FMD_UID	VARCHAR2 (15)			
FMD_IU_DATE	DATE		
	
FMD_CONCENTRATION	NUMBER (156)			Margin %
IV_ENTER_UID	VARCHAR2 (119)			
IV_ENTER_DATE	DATE			
IV_APPROVE_UID	VARCHAR2 (119)			
IV_APPROVE_DATE	DATE			
IV_LAST_UPDATE_UID	VARCHAR2 (119)			
IV_LAST_UPDATE_DATE	DATE			

FMD_MARGIN_LEVEL	VARCHAR2(30)			margen level (Stock Name or Asset)
FMD_CID	NUMBER (15)			Stock id or asset id

FMD_LEVEL	VARCHAR2(30)			fund level(Client Type Client Broker Custodian Fund )
FMD_BROKER	NUMBER(10)			broker name
FMD_CUSTODIAN	NUMBER(10)			CUSTODIAN name
FMD_FUND	NUMBER(10)			Fund name
FMD_CLIENT	NUMBER(10)			client name
FMD_C_TYPE	NUMBER (15)			cient type

WMS_COMMENTS	VARCHAR2(300)			comment
WMS_STATUS	VARCHAR2(20)			status				
 */
@RestController
public class FUND_MAR_MARGIN_MSTR_SECU_LEVController{
	
	@Autowired
	FundUserValidate fundUserValidate;
	
	
	@Autowired
	FUND_CLIENT_DOCUMENTS_TYPE2Repository fUND_CLIENT_DOCUMENTS_TYPE2Repository;
	
	@Autowired
	FUND_MAR_CLIENT_TYPERespository fUND_MAR_CLIENT_TYPERespository;
	
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;

	@Autowired
	FUND_MAR_MARGIN_MSTR_SECU_LEVRepository fUND_MAR_MARGIN_MSTR_SECU_LEVRepository;
	
	@Autowired
	FUND_MSTRRepository fUND_MSTRRepository;
	
	@Autowired
	StockParameters stockParameters;
	
	@Autowired
	FUND_SHARE_COMPANY_MSTRRepository fUND_SHARE_COMPANY_MSTRRepository;
	
	@Autowired
	RE_INVESTORRepository rE_INVESTORRepository;
	
	private static final Logger logger=LoggerFactory.getLogger("FWMS_MUTUAL_FUNDS_FEES CONTROLLER");
	
	@PostMapping("/savemargin")
	public ResponseEntity<Map<String,Object>> save(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
	
		Object FMD_DATE  =requestJson.get("FMD_DATE");
		
		Object FMD_MARGIN_LEVEL=requestJson.get("FMD_MARGIN_LEVEL");
		Object FMD_CID=requestJson.get("FMD_CID");//ASSET or Stock Id
		
		Object FMD_LEVEL=requestJson.get("Level");
		Object FMD_CLIENT =requestJson.get("Clientid");
		Object FMD_C_TYPE  =requestJson.get("Clienttypeid");
		Object FMD_FUND =requestJson.get("Fund_Id");
		Object FMD_BROKER =requestJson.get("broker_Id");
		Object FMD_CUSTODIAN =requestJson.get("custodian_Id");
		

		Object FMD_INITIAL=requestJson.get("FMD_INITIAL");
		Object FMD_MAINTENANCE=requestJson.get("FMD_MAINTENANCE");		
		Object FMD_LIQUIDATION=requestJson.get("FMD_LIQUIDATION");		
		Object FMD_UID=requestJson.get("FMD_UID");			
		Object FMD_IU_DATE=requestJson.get("FMD_IU_DATE");	
		Object FMD_CONCENTRATION=requestJson.get("margin_per");				//Margin %
		
		
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("createdby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		 if(FMD_DATE==null ||FMD_LEVEL==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
				
				
				FUND_MAR_MARGIN_MSTR_SECU_LEV fscm=new FUND_MAR_MARGIN_MSTR_SECU_LEV();
				 
				 if(FMD_IU_DATE!=null) {
					 if(FMD_IU_DATE.toString().trim().isEmpty()==false) {
						 Calendar cl=Calendar.getInstance();
						  cl.setTimeInMillis(new Long(FMD_IU_DATE.toString().trim()));
						  fscm.setFMD_IU_DATE(cl);
					 }
				 }
				
				if(FMD_UID!=null) {
					if(FMD_UID.toString().trim().isEmpty()==false) {
						fscm.setFMD_UID(FMD_UID.toString().trim());
					}
				}
				
				if(FMD_INITIAL!=null) {
					if(FMD_INITIAL.toString().trim().isEmpty()==false) {
						fscm.setFMD_INITIAL(new Long(FMD_INITIAL.toString().trim()));
					}
				}
				
				if(FMD_MAINTENANCE!=null) {
					if(FMD_MAINTENANCE.toString().trim().isEmpty()==false) {
						fscm.setFMD_MAINTENANCE(new Long(FMD_MAINTENANCE.toString().trim()));
					}
				}
				
				if(FMD_LIQUIDATION!=null) {
					if(FMD_LIQUIDATION.toString().trim().isEmpty()==false) {
						fscm.setFMD_LIQUIDATION(new Long(FMD_LIQUIDATION.toString().trim()));
					}
				}
				
				if(FMD_CONCENTRATION!=null) {
					if(FMD_CONCENTRATION.toString().trim().isEmpty()==false) {
						fscm.setFMD_CONCENTRATION(new Double(FMD_CONCENTRATION.toString().trim()));
					}
				}
				
				 if(FMD_DATE!=null) {
					 if(FMD_DATE.toString().trim().isEmpty()==false) {
						 Calendar cl=Calendar.getInstance();
						  cl.setTimeInMillis(new Long(FMD_DATE.toString().trim()));
						  fscm.setFMD_DATE(cl);
					 }
				 }
				 
				  if(FMD_MARGIN_LEVEL!=null) {
					  if(FMD_MARGIN_LEVEL.toString().trim().isEmpty()==false) {
						  fscm.setFMD_MARGIN_LEVEL(FMD_MARGIN_LEVEL.toString().trim());
					  }
				  }
				  
				  if(FMD_CID!=null) {
					  if(FMD_CID.toString().trim().isEmpty()==false) {
						  fscm.setFMD_CID(new Long(FMD_CID.toString().trim()));
					  }
				  }
				  
				  
				  if(FMD_LEVEL!=null) {
					  if(FMD_LEVEL.toString().trim().isEmpty()==false) {
						  fscm.setFMD_LEVEL(FMD_LEVEL.toString().trim());
					  }
				  }
				  
				
				  
				  if(FMD_CLIENT!=null) {
					  if(FMD_CLIENT.toString().isEmpty()==false) {
						  fscm.setFMD_CLIENT(new Long(FMD_CLIENT.toString()));
					  }
				  }
				  if(FMD_C_TYPE!=null) {
					  if(FMD_C_TYPE.toString().isEmpty()==false) {
						  fscm.setFMD_C_TYPE(new Long(FMD_C_TYPE.toString()));
					  }
				  }
				 if(FMD_FUND!=null) {
					 if(FMD_FUND.toString().isEmpty()==false) {
						 fscm.setFMD_FUND(new Long(FMD_FUND.toString()));
					 }
				 }
 
				 
				 if(FMD_CUSTODIAN!=null) {
					 if(FMD_CUSTODIAN.toString().isEmpty()==false) {
						 fscm.setFMD_CUSTODIAN(new Long(FMD_CUSTODIAN.toString()));
					 }
				 }
 
				 
				 if(FMD_BROKER!=null) {
					 if(FMD_BROKER.toString().isEmpty()==false) {
						 fscm.setFMD_BROKER(new Long(FMD_BROKER.toString()));
					 }
				 }
 
				
					
					
				 if(WMS_COMMENTS!=null) {
					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
						}
				  fscm.setWMS_STATUS("Not Approved");
						
				 		
				  fscm.setIV_ENTER_UID(fuser.getSVC_UID());
				  fscm.setIV_ENTER_DATE(Calendar.getInstance());
				 
				
				  
				  try
					{
					  fscm=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.save(fscm);
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
									logger.info("Both Record and Logs saved for Margin:"+fscm.getFMD_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved New Margin:"+fscm.getFMD_SNO());
							
							json.put("createdby", fscm.getIV_ENTER_UID());
							json.put("createdon", fscm.getIV_ENTER_DATE());
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("Margin not saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("Margin not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
					
				 
				
		 }		 
	}


	@PostMapping("/updatemargin")
	public ResponseEntity<?> Update(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
	
		Object FMD_DATE  =requestJson.get("FMD_DATE");
		Object FMD_SNO =requestJson.get("FMD_SNO");
		Object FMD_MARGIN_LEVEL=requestJson.get("FMD_MARGIN_LEVEL");
		Object FMD_CID=requestJson.get("FMD_CID");//ASSET or Stock Id
		
		Object FMD_LEVEL=requestJson.get("Level");
		Object FMD_CLIENT =requestJson.get("Clientid");
		Object FMD_C_TYPE  =requestJson.get("Clienttypeid");
		Object FMD_FUND =requestJson.get("Fund_Id");
		Object FMD_BROKER =requestJson.get("broker_Id");
		Object FMD_CUSTODIAN =requestJson.get("custodian_Id");
		

		Object FMD_INITIAL=requestJson.get("FMD_INITIAL");
		Object FMD_MAINTENANCE=requestJson.get("FMD_MAINTENANCE");		
		Object FMD_LIQUIDATION=requestJson.get("FMD_LIQUIDATION");		
		Object FMD_UID=requestJson.get("FMD_UID");			
		Object FMD_IU_DATE=requestJson.get("FMD_IU_DATE");	
		Object FMD_CONCENTRATION=requestJson.get("margin_per");				//Margin %
		
		
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		 if(FMD_SNO==null ||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
				
				
				FUND_MAR_MARGIN_MSTR_SECU_LEV fscm=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findOne(new Long(FMD_SNO.toString().trim()));
				 
				 if(FMD_IU_DATE!=null) {
					 if(FMD_IU_DATE.toString().trim().isEmpty()==false) {
						 Calendar cl=Calendar.getInstance();
						  cl.setTimeInMillis(new Long(FMD_IU_DATE.toString().trim()));
						  fscm.setFMD_IU_DATE(cl);
					 }
				 }
				
				if(FMD_UID!=null) {
					if(FMD_UID.toString().trim().isEmpty()==false) {
						fscm.setFMD_UID(FMD_UID.toString().trim());
					}
				}
				
				if(FMD_INITIAL!=null) {
					if(FMD_INITIAL.toString().trim().isEmpty()==false) {
						fscm.setFMD_INITIAL(new Long(FMD_INITIAL.toString().trim()));
					}
				}
				
				if(FMD_MAINTENANCE!=null) {
					if(FMD_MAINTENANCE.toString().trim().isEmpty()==false) {
						fscm.setFMD_MAINTENANCE(new Long(FMD_MAINTENANCE.toString().trim()));
					}
				}
				
				if(FMD_LIQUIDATION!=null) {
					if(FMD_LIQUIDATION.toString().trim().isEmpty()==false) {
						fscm.setFMD_LIQUIDATION(new Long(FMD_LIQUIDATION.toString().trim()));
					}
				}
				
				if(FMD_CONCENTRATION!=null) {
					if(FMD_CONCENTRATION.toString().trim().isEmpty()==false) {
						fscm.setFMD_CONCENTRATION(new Double(FMD_CONCENTRATION.toString().trim()));
					}
				}
				
				 if(FMD_DATE!=null) {
					 if(FMD_DATE.toString().trim().isEmpty()==false) {
						 Calendar cl=Calendar.getInstance();
						  cl.setTimeInMillis(new Long(FMD_DATE.toString().trim()));
						  fscm.setFMD_DATE(cl);
					 }
				 }
				 
				  if(FMD_MARGIN_LEVEL!=null) {
					  if(FMD_MARGIN_LEVEL.toString().trim().isEmpty()==false) {
						  fscm.setFMD_MARGIN_LEVEL(FMD_MARGIN_LEVEL.toString().trim());
					  }
				  }
				  
				  if(FMD_CID!=null) {
					  if(FMD_CID.toString().trim().isEmpty()==false) {
						  fscm.setFMD_CID(new Long(FMD_CID.toString().trim()));
					  }
				  }
				  
				  
				  if(FMD_LEVEL!=null) {
					  if(FMD_LEVEL.toString().trim().isEmpty()==false) {
						  fscm.setFMD_LEVEL(FMD_LEVEL.toString().trim());
					  }
				  }
				  
				
				  
				  if(FMD_CLIENT!=null) {
					  if(FMD_CLIENT.toString().isEmpty()==false) {
						  fscm.setFMD_CLIENT(new Long(FMD_CLIENT.toString()));
					  }
				  }
				  if(FMD_C_TYPE!=null) {
					  if(FMD_C_TYPE.toString().isEmpty()==false) {
						  fscm.setFMD_C_TYPE(new Long(FMD_C_TYPE.toString()));
					  }
				  }
				 if(FMD_FUND!=null) {
					 if(FMD_FUND.toString().isEmpty()==false) {
						 fscm.setFMD_FUND(new Long(FMD_FUND.toString()));
					 }
				 }
 
				 
				 if(FMD_CUSTODIAN!=null) {
					 if(FMD_CUSTODIAN.toString().isEmpty()==false) {
						 fscm.setFMD_CUSTODIAN(new Long(FMD_CUSTODIAN.toString()));
					 }
				 }
 
				 
				 if(FMD_BROKER!=null) {
					 if(FMD_BROKER.toString().isEmpty()==false) {
						 fscm.setFMD_BROKER(new Long(FMD_BROKER.toString()));
					 }
				 }
 
				
					
					
				 if(WMS_COMMENTS!=null) {
					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
						}
//				  fscm.setWMS_STATUS("Not Approved");
				  fscm.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
				  fscm.setIV_LAST_UPDATE_DATE(Calendar.getInstance());

				  try
					{
					  fscm=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.save(fscm);
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
									logger.info("Both Record and Logs saved for Margin:"+fscm.getFMD_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved New Margin:"+fscm.getFMD_SNO());
							
							MarginBean jsonBean=getJson(fscm);
							jsonBean.setMsg("Saved");
							return new ResponseEntity<MarginBean>(jsonBean,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("Margin not saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("Margin not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
		 }		 
	}
	

	@PostMapping("/approvemargin")
	public ResponseEntity<?> approve(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
	
		
		Object FMD_SNO =requestJson.get("FMD_SNO");
		
		
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("approvedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		 if(FMD_SNO==null ||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
				
				
				FUND_MAR_MARGIN_MSTR_SECU_LEV fscm=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findOne(new Long(FMD_SNO.toString().trim()));
				
				 if(WMS_COMMENTS!=null) {
					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
						}
				 
				 
				 /*
				  * Level
				  */
				 Long FMD_FUND=fscm.getFMD_FUND();
				  
				  if(FMD_FUND!=null) {
					  FUND_MSTR fUND_MSTR=  fUND_MSTRRepository.findOne(FMD_FUND);
					  if(fUND_MSTR!=null) {
						  if(fUND_MSTR.getIV_APPROVE_UID()==null) {
							  json.put("msg", "Fund is not approved");
								logger.error("fund not approved");
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);  
						  }
					  }
				  }
				  
				 String Level=fscm.getFMD_LEVEL();				 
				 if(Level.equalsIgnoreCase("client type")) {
						Long clienttype=fscm.getFMD_C_TYPE();
						FUND_MAR_CLIENT_TYPE fUND_MAR_CLIENT_TYPE=fUND_MAR_CLIENT_TYPERespository.findOne(clienttype);
						if(fUND_MAR_CLIENT_TYPE!=null) {
						if(fUND_MAR_CLIENT_TYPE.getIV_APPROVE_UID()==null) {
							 json.put("msg", "Client Type is not approved");
								logger.error("Client Type  not approved");
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
						}
					}else if(Level.equalsIgnoreCase("client")) {
						Long client=fscm.getFMD_CLIENT();
						RE_INVESTOR rE_INVESTOR=rE_INVESTORRepository.findOne(client);
						if(rE_INVESTOR!=null) {
							if(rE_INVESTOR.getIV_APPROVE_UID()==null) {
								 json.put("msg", "Client is not approved");
									logger.error("Client not approved");
									return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							}
						}
					}else if(Level.equalsIgnoreCase("broker")) {
						Long client=fscm.getFMD_CLIENT();
						RE_INVESTOR rE_INVESTOR=rE_INVESTORRepository.findOne(client);
						if(rE_INVESTOR!=null) {
							if(rE_INVESTOR.getIV_APPROVE_UID()==null) {
								 json.put("msg", "Client is not approved");
									logger.error("Client not approved");
									return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							}
						}
					}else if(Level.equalsIgnoreCase("Custodian")) {
						Long client=fscm.getFMD_CLIENT();
						RE_INVESTOR rE_INVESTOR=rE_INVESTORRepository.findOne(client);
						if(rE_INVESTOR!=null) {
							if(rE_INVESTOR.getIV_APPROVE_UID()==null) {
								 json.put("msg", "Client is not approved");
									logger.error("Client not approved");
									return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							}
						}
					}
				
				  /*
				   * SL level
				   */
				 String FMD_SL_LEVEL=fscm.getFMD_MARGIN_LEVEL();
				  if(FMD_SL_LEVEL!=null) {
					  Long FMD_SID=fscm.getFMD_CID();
					  if(FMD_SL_LEVEL.equalsIgnoreCase("Asset")) {
						  FUND_INSTRUMENT_MSTR fUND_MSTR= stockParameters.getFUND_INSTRUMENT_MSTR(FMD_SID);
						  if(fUND_MSTR!=null) {
							  if(fUND_MSTR.getIV_APPROVE_UID()==null) {
								  json.put("msg", "Asset is not approved");
									logger.error("Asset not approved");
									return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);  
							  }
						  }
					  }
					  else if(FMD_SL_LEVEL.equalsIgnoreCase("stock")) {
						  FUND_SHARE_COMPANY_MSTR fUND_MSTR=fUND_SHARE_COMPANY_MSTRRepository.findOne(FMD_SID) ;
						  if(fUND_MSTR!=null) {
							  if(fUND_MSTR.getIV_APPROVE_UID()==null) {
								  json.put("msg", "Stock is not approved");
									logger.error("Stock not approved");
									return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);  
							  }
						  }
					  }
				  }
				 
				  fscm.setWMS_STATUS("Approved");	
				  fscm.setIV_APPROVE_UID(fuser.getSVC_UID());
				  fscm.setIV_APPROVE_DATE(Calendar.getInstance());
				  
				  try
					{
					  fscm=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.save(fscm);
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
									logger.info("Both Record and Logs saved for Margin:"+fscm.getFMD_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved New Margin:"+fscm.getFMD_SNO());
							
							MarginBean jsonBean=getJson(fscm);
							jsonBean.setMsg("Saved");
							return new ResponseEntity<MarginBean>(jsonBean,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("Margin not saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("Margin not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
		 }		 
	}
	
	@PostMapping("/revokemargin")
	public ResponseEntity<?> revoke(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
	
		
		Object FMD_SNO =requestJson.get("FMD_SNO");
		
		
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		 if(FMD_SNO==null ||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
				
				
				FUND_MAR_MARGIN_MSTR_SECU_LEV fscm=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findOne(new Long(FMD_SNO.toString().trim()));
				
				 if(WMS_COMMENTS!=null) {
					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
						}
				  fscm.setWMS_STATUS("Not Approved");			 		
				  fscm.setIV_APPROVE_UID(null);
				  fscm.setIV_APPROVE_DATE(null);
				  fscm.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
				  fscm.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
				  
				  try
					{
					  fscm=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.save(fscm);
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
									logger.info("Both Record and Logs saved for Margin:"+fscm.getFMD_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved New Margin:"+fscm.getFMD_SNO());
							
							MarginBean jsonBean=getJson(fscm);
							jsonBean.setMsg("Saved");
							return new ResponseEntity<MarginBean>(jsonBean,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("Margin not saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("Margin not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
		 }		 
	}
	
	
	@PostMapping("/deletemargin")
	public ResponseEntity<?> delete(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
	
		
		Object FMD_SNO =requestJson.get("FMD_SNO");
		
		
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		 if(FMD_SNO==null ||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
				
				
				FUND_MAR_MARGIN_MSTR_SECU_LEV fscm=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findOne(new Long(FMD_SNO.toString().trim()));
				
				 if(WMS_COMMENTS!=null) {
					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
						}
				  fscm.setWMS_STATUS("Not Approved");			 		
				  fscm.setIV_APPROVE_UID(null);
				  fscm.setIV_APPROVE_DATE(null);
				  fscm.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
				  fscm.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
				  
				  try
					{
					  fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.delete(fscm);
//					 if(fscm!=null) {
//							    json.put("msg", "saved");				
//								FUND_USER_LOG ful=new FUND_USER_LOG();
//								ful.setSVC_UID(fuser.getSVC_UID());
//								ful.setSVL_USERID(fuser.getSVU_NAME());
//								ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
//								ful.setSVL_TTYPE("APPROVED");
//								ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
//								ful.setSVL_DATE(Calendar.getInstance());
//								ful=fUND_USER_LOGRepository.save(ful);
//								if(ful!=null) {
//									logger.info("Both Record and Logs saved for Margin:"+fscm.getFMD_SNO());
//									json.put("logs","logs are saved");
//								}
//								else {
//									logger.info("Record is saved but logs can't saved due error in saving of logs");
//									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
//								}
//							
//							logger.info("Saved New Margin:"+fscm.getFMD_SNO());
//							
//							MarginBean jsonBean=getJson(fscm);
//							return new ResponseEntity<MarginBean>(jsonBean,HttpStatus.OK);
//						}
//						else {
//							json.put("msg","Not saved");
//							logger.error("Margin not saved");
//							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
//						}
					    logger.info("DELETED");
						json.put("msg", "Deleted");
						return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("Margin not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
		 }		 
	}
	

	@GetMapping("/marginsearch")
	public ResponseEntity<?> getAllMargin(Pageable page,
			@RequestParam(required=false,value="action")String action,
			@RequestParam(required=false,value="paramSearch")String paramString,
			@RequestParam(required=false,value="marginlevel")String marginlevel,
			@RequestParam(required=false,value="level")String level
			){
		List<Long> fundids=new ArrayList<>();
		List<Long> marginlevelids=new ArrayList<>();
		List<Long> levelids=new ArrayList<>();
		if(paramString!=null) {
			if(paramString.isEmpty()==false) {
				/*
				 * Fund information
				 */
				fundids=fUND_MSTRRepository.find_ids_FUND_MSTR_LIST(paramString);
				
				if(marginlevel!=null) {
					if(marginlevel.isEmpty()==false) {
						if(marginlevel.equalsIgnoreCase("stock")) {
							marginlevelids=fUND_SHARE_COMPANY_MSTRRepository.findAllFUND_SHARE_COMPANY_MSTR_DESC(paramString);			
						}else if(marginlevel.equalsIgnoreCase("asset")) {
							marginlevelids=stockParameters.getAllAssetsId(paramString).stream().map(s->Long.parseLong(s+"")).collect(Collectors.toList());				
						}
					}
				}
				
				if(level!=null) {
					if(level.isEmpty()==false) {
						if(level.equalsIgnoreCase("Client name")) {
							levelids=rE_INVESTORRepository.find_ids_Client(paramString);
						}else if(level.equalsIgnoreCase("Client type")) {
							levelids=fUND_MAR_CLIENT_TYPERespository.find_ids_FUND_MAR_CLIENT_TYPE(paramString);
						}else if(level.equalsIgnoreCase("Custodian")) {
							//levelids=
						}else if(level.equalsIgnoreCase("Broker")) {
							//levelids
						}else if(level.equalsIgnoreCase("fund name")) {
							fundids=fUND_MSTRRepository.find_ids_FUND_MSTR_LIST(paramString);
						}
					}
				}

				if(fundids.isEmpty()) {
					fundids=Arrays.asList(new Long(-1));
				}
				if(marginlevelids.isEmpty()) {
					marginlevelids=Arrays.asList(new Long(-1));
				}
				if(levelids.isEmpty()) {
					levelids=Arrays.asList(new Long(-1));
				}

			}
		}
		
		if(level==null) {
			level="";
		} 
		if(marginlevel==null) {
			marginlevel="";
		}
		
		
		List<FUND_MAR_MARGIN_MSTR_SECU_LEV> allFUND_MAR_MARGIN_MSTR_SECU_LEV=null;
		if(action!=null) {
		if(action.isEmpty()==false) {
			if(action.equalsIgnoreCase("ALL")) {
				if(paramString!=null) {
					if(paramString.isEmpty()==false) {
						if(level.isEmpty()==false&&marginlevel.isEmpty()==false) {
							allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL(fundids, levelids, marginlevelids,level, marginlevel);
						}
						else if(level.isEmpty()==true&&marginlevel.isEmpty()==false)
						{
							allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL(marginlevelids, marginlevel);
						}else if(level.isEmpty()==false&&marginlevel.isEmpty()==true) {
							allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL(fundids, levelids, level);
						}else if(level.isEmpty()==true&&marginlevel.isEmpty()==true){
							allFUND_MAR_MARGIN_MSTR_SECU_LEV=(List<FUND_MAR_MARGIN_MSTR_SECU_LEV>) fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findAll();			
						}
					}else {
						if(level.isEmpty()==false&&marginlevel.isEmpty()==false) {
							allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL(level,marginlevel);
						}
						else if(level.isEmpty()==true&&marginlevel.isEmpty()==false)
						{
							allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL(marginlevel);
						}else if(level.isEmpty()==false&&marginlevel.isEmpty()==true)  {
							allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL(level);
						}else  if(level.isEmpty()==true&&marginlevel.isEmpty()==true){
							allFUND_MAR_MARGIN_MSTR_SECU_LEV=(List<FUND_MAR_MARGIN_MSTR_SECU_LEV>) fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findAll();			
						}
					}
				}else {
					if(level.isEmpty()==false&&marginlevel.isEmpty()==false) {
						allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL(level,marginlevel);
					}
					else if(level.isEmpty()==true&&marginlevel.isEmpty()==false)
					{
						allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL(marginlevel);
					}else if(level.isEmpty()==false&&marginlevel.isEmpty()==true)  {
						allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL(level);
					}else  if(level.isEmpty()==true&&marginlevel.isEmpty()==true){
						allFUND_MAR_MARGIN_MSTR_SECU_LEV=(List<FUND_MAR_MARGIN_MSTR_SECU_LEV>) fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findAll();			
					}		
			}
				
			}else if(action.equalsIgnoreCase("APPROVED")) {
				if(paramString!=null) {
					if(paramString.isEmpty()==false) {
						if(level.isEmpty()==false&&marginlevel.isEmpty()==false) {
							allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL_APPROVED(fundids, levelids, level, marginlevelids, marginlevel);
						}
						else if(level.isEmpty()==true&&marginlevel.isEmpty()==false)
						{
							allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL_APPROVED(marginlevelids, marginlevel);
						}else if(level.isEmpty()==false&&marginlevel.isEmpty()==true)  {
							allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL_APPROVED(fundids, levelids, level);
						}else  if(level.isEmpty()==true&&marginlevel.isEmpty()==true){
							allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findAllAPPROVEDFUND_MAR_MARGIN_MSTR_SECU_LEV();			
						}
					}else {
						if(level.isEmpty()==false&&marginlevel.isEmpty()==false) {
							allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL_APPROVED(level,marginlevel);
						}
						else if(level.isEmpty()==true&&marginlevel.isEmpty()==false)
						{
							allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL_APPROVED(marginlevel);
						}else if(level.isEmpty()==false&&marginlevel.isEmpty()==true)  {
							allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL_APPROVED(level);
						}else  if(level.isEmpty()==true&&marginlevel.isEmpty()==true){
							allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findAllAPPROVEDFUND_MAR_MARGIN_MSTR_SECU_LEV();			
						}
					}
				}else {
					if(level.isEmpty()==false&&marginlevel.isEmpty()==false) {
						allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL_APPROVED(level,marginlevel);
					}
					else if(level.isEmpty()==true&&marginlevel.isEmpty()==false)
					{
						allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL_APPROVED(marginlevel);
					}else if(level.isEmpty()==false&&marginlevel.isEmpty()==true)  {
						allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL_APPROVED(level);
					}else  if(level.isEmpty()==true&&marginlevel.isEmpty()==true){
						allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findAllAPPROVEDFUND_MAR_MARGIN_MSTR_SECU_LEV();			
					}
			}
				
		}else if(action.equalsIgnoreCase("UNAPPROVED")) {
			if(paramString!=null) {
				if(paramString.isEmpty()==false) {
					if(level.isEmpty()==false&&marginlevel.isEmpty()==false) {
						allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL_UNAPPROVED(fundids, levelids, level, marginlevelids, marginlevel);
					}
					else if(level.isEmpty()==true&&marginlevel.isEmpty()==false)
					{
						allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL_UNAPPROVED(marginlevelids, marginlevel);
					}else if(level.isEmpty()==false&&marginlevel.isEmpty()==true)  {
						allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL_UNAPPROVED(fundids, levelids, level);
					}else  if(level.isEmpty()==true&&marginlevel.isEmpty()==true){
						allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findAllUNAPPROVEDFUND_MAR_MARGIN_MSTR_SECU_LEV();			
					}
				}else {
					if(level.isEmpty()==false&&marginlevel.isEmpty()==false) {
						allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL_UNAPPROVED(level,marginlevel);
					}
					else if(level.isEmpty()==true&&marginlevel.isEmpty()==false)
					{
						allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL_UNAPPROVED(marginlevel);
					}else if(level.isEmpty()==false&&marginlevel.isEmpty()==true)  {
						allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL_UNAPPROVED(level);
					}else  if(level.isEmpty()==true&&marginlevel.isEmpty()==true){
						allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findAllUNAPPROVEDFUND_MAR_MARGIN_MSTR_SECU_LEV();			
					}
				}
			}else {
				if(level.isEmpty()==false&&marginlevel.isEmpty()==false) {
					allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL_UNAPPROVED(level,marginlevel);
				}
				else if(level.isEmpty()==true&&marginlevel.isEmpty()==false)
				{
					allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL_UNAPPROVED(marginlevel);
				}else if(level.isEmpty()==false&&marginlevel.isEmpty()==true)  {
					allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findFUND_MARGIN_SEARCH_WITH_LEVEL_UNAPPROVED(level);
				}else  if(level.isEmpty()==true&&marginlevel.isEmpty()==true){
					allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findAllUNAPPROVEDFUND_MAR_MARGIN_MSTR_SECU_LEV();			
				}
			}
		}
			else {
				Map<String,Object> jsonArray=new HashMap<>();
				 jsonArray.put("msg", "No data found");
				 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
			}
		}else {
			Map<String,Object> jsonArray=new HashMap<>();
			 jsonArray.put("msg", "Action value is missing");
			 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
		}
		}else {	
			Map<String,Object> jsonArray=new HashMap<>();
			 jsonArray.put("msg", "Action value is missing");
			 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
		}
		
		List<MarginBean> allMargindata= new ArrayList<MarginBean>();
		
		if(allFUND_MAR_MARGIN_MSTR_SECU_LEV.size()>0) {
			allFUND_MAR_MARGIN_MSTR_SECU_LEV.forEach(conut->{
				MarginBean mgr=getJson(conut);
				allMargindata.add(mgr);
			});
		}
//		//allFUND_MAR_MARGIN_MSTR_SECU_LEV=fUND_MAR_MARGIN_MSTR_SECU_LEVRepository.findAll();
//		Page<MarginBean> jsonArray=allFUND_MAR_MARGIN_MSTR_SECU_LEV.map(new Converter<FUND_MAR_MARGIN_MSTR_SECU_LEV, MarginBean>() {
//
//			@Override
//			public MarginBean convert(FUND_MAR_MARGIN_MSTR_SECU_LEV conut) {
//				MarginBean json=getJson(conut);
//				return json;
//			}
//		});
		Collections.sort(allMargindata);
		int start = page.getOffset();
		int end = (start + page.getPageSize()) > allMargindata.size() ? allMargindata.size()
				: (start + page.getPageSize());
		Page<MarginBean> jsonArray = new PageImpl<MarginBean>(allMargindata.subList(start, end), page,
				allMargindata.size());
		 return new ResponseEntity<Page<MarginBean>>(jsonArray,HttpStatus.OK);	
	}
	
	public MarginBean getJson(FUND_MAR_MARGIN_MSTR_SECU_LEV conut) {
		MarginBean ffb=new MarginBean();
		
        ffb.setSno(conut.getFMD_SNO()+"");
		ffb.setFMD_DATE(conut.getFMD_DATE());
		
		ffb.setMargin_level(conut.getFMD_MARGIN_LEVEL().toString().trim());
		
		if(conut.getFMD_MARGIN_LEVEL().toString().equalsIgnoreCase("stock")) {
			FUND_SHARE_COMPANY_MSTR fUND_SHARE_COMPANY_MSTR=fUND_SHARE_COMPANY_MSTRRepository.findOne(conut.getFMD_CID());
			if(fUND_SHARE_COMPANY_MSTR!=null) {
				ffb.setStockid(fUND_SHARE_COMPANY_MSTR.getSVC_CODE()+"");
				ffb.setStockname(fUND_SHARE_COMPANY_MSTR.getSVC_NAME()+"");
			}else {
				ffb.setStockid(null);
				ffb.setStockname(null);
			}
		}else if(conut.getFMD_MARGIN_LEVEL().toString().equalsIgnoreCase("asset")) {
			FUND_INSTRUMENT_MSTR asset=stockParameters.getFUND_INSTRUMENT_MSTR(conut.getFMD_CID());
			if(asset!=null) {
				ffb.setAssetid(asset.getSVI_CODE()+"");
				ffb.setAssetname(asset.getSVI_NAME());
			}
		} 
		String FMD_LEVEL=conut.getFMD_LEVEL();	
		ffb.setLevel(conut.getFMD_LEVEL());
		 /*
		   * FUND is always will put
		   */
		  Long FMD_FUND=conut.getFMD_FUND();
			FUND_MSTR fund_MSTR=fUND_MSTRRepository.findOne(new Long(FMD_FUND.toString()));
			  if(fund_MSTR!=null) {
					ffb.setFund_id(fund_MSTR.getSVC_CODE()+"");
					ffb.setFund_name( fund_MSTR.getSVC_NAME());
			  }else {
				  ffb.setFund_id(null);
					ffb.setFund_name( null);
			 }
		
	if(FMD_LEVEL!=null) {
		 if(FMD_LEVEL.equalsIgnoreCase("Client level")) {
				  Long FMD_Client=new Long(conut.getFMD_CLIENT());
				  RE_INVESTOR fUND_MSTR=rE_INVESTORRepository.findOne(FMD_Client) ;
				  if(fUND_MSTR!=null) {
					  ffb.setClient_id(conut.getFMD_CLIENT()+"");
					  ffb.setClient_name(fUND_MSTR.getRI_INVESTOR_NAME());
				  }else {
					  ffb.setClient_id(null);
						ffb.setClient_name( null);
				  }
			  }
			
   else if(FMD_LEVEL.equalsIgnoreCase("Client type")) {
					Long FMD_CLIENT_TYPE=conut.getFMD_C_TYPE();
					  FUND_MAR_CLIENT_TYPE fUND_MSTR= fUND_MAR_CLIENT_TYPERespository.findOne(FMD_CLIENT_TYPE);
						  if(fUND_MSTR!=null) {
								ffb.setClient_type_id(fUND_MSTR.getFCT_ID()+"");
								ffb.setClient_type_name( fUND_MSTR.getFCT_NAME());
						  }else {
							  ffb.setClient_type_id(null);
								ffb.setClient_type_name(null);
						  }
		  }
		
	 else if(FMD_LEVEL.equalsIgnoreCase("Broker")) {
			Long FMD_BROKER=conut.getFMD_BROKER();
			  FUND_MAR_CLIENT_TYPE fUND_MSTR= fUND_MAR_CLIENT_TYPERespository.findOne(FMD_BROKER);
				  if(fUND_MSTR!=null) {
						ffb.setBroker_Id(fUND_MSTR.getFCT_ID()+"");
						ffb.setBroker_name( fUND_MSTR.getFCT_NAME());
				  }else {
					  ffb.setBroker_Id(null);
						ffb.setBroker_name(null);
				  }
	 	}
	
	 else if(FMD_LEVEL.equalsIgnoreCase("Custodian")) {
			Long FMD_CUSTODIAN=conut.getFMD_CUSTODIAN();
			  FUND_MAR_CLIENT_TYPE fUND_MSTR= fUND_MAR_CLIENT_TYPERespository.findOne(FMD_CUSTODIAN);
				  if(fUND_MSTR!=null) {
						ffb.setCustodian_Id(fUND_MSTR.getFCT_ID()+"");
						ffb.setCustodian_name(fUND_MSTR.getFCT_NAME());
				  }else {
						ffb.setCustodian_Id(null);
						ffb.setCustodian_name(null);
				  }
	 	}
}
		

		  ffb.setFmd_initial(conut.getFMD_INITIAL()+"");
		  ffb.setFmd_maintenance(conut.getFMD_MAINTENANCE()+"");
		  ffb.setFmd_liquiation(conut.getFMD_LIQUIDATION()+"");
		  ffb.setFmd_uid(conut.getFMD_UID());
		  ffb.setFmd_iu_date(conut.getFMD_IU_DATE());
		  ffb.setMargin_per(conut.getFMD_CONCENTRATION()+"");
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