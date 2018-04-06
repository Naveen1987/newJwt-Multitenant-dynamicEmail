package com.fynisys.controller.funds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fynisys.controller.funds.beans.FundsFeesBean;
import com.fynisys.controller.funds.beans.MarginBean;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.FUND_USER_LOG;
import com.fynisys.model.clienttype.FUND_MAR_CLIENT_TYPE;
import com.fynisys.model.crm.RE_INVESTOR;
import com.fynisys.model.funds.FUND_MSTR;
import com.fynisys.model.funds.FWMS_MUTUAL_FUNDS_FEES;
import com.fynisys.model.funds.FWMS_SHARE_CLASS;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.clienttype.FUND_CLIENT_DOCUMENTS_TYPE2Repository;
import com.fynisys.repository.clienttype.FUND_MAR_CLIENT_TYPERespository;
import com.fynisys.repository.crm.RE_INVESTORRepository;
import com.fynisys.repository.funds.FUND_MSTRRepository;
import com.fynisys.repository.funds.FWMS_MUTUAL_FUNDS_FEESRepository;
import com.fynisys.repository.funds.FWMS_SHARE_CLASSRepository;
import com.fynisys.utilities.FundUserValidate;
import com.fynisys.utilities.StockParameters;

/*
WMS_EDATE	DATE			Effective Date
WMS_SNO	NUMBER (10)	PRIMARY KEY	NOT NULL	Sno
WMS_FUND_ID	NUMBER (4)			Fund Name (Should show fund name and store fund id in thebackend)

WMS_SHARE_CLASS	VARCHAR2 (20)			Share Class
WMS_FEE_PER_FLAG	VARCHAR2 (30)			Fee type (Flat Amount or Percentage)
WMS_CAL_FREQUENCY	VARCHAR2 (20)			Frequency
WMS_DIVISORY_DAY	NUMBER (4)			Divisory Days
WMS_CAL_FUND_SC_LEVEL	VARCHAR2 (15)			Calcualte on
WMS_WEEK_DAY	VARCHAR2 (20)			Day (List box show monday.... Sunday in the list al the days)
WMS_FEE_FLAG	VARCHAR2 (35)			Fees Flag ( List Box mANAGMENT Feeadmin etc)
WMS_FEE_PER	NUMBER (266)			fee % (Amount)
WMS_STATUS	VARCHAR2 (20)			Status
WMS_COMMENTS	VARCHAR2 (300)			Comment
WMS_ENTER_UID	VARCHAR2 (20)			
WMS_ENTER_FPC	VARCHAR2 (30)			
WMS_ENTER_DATE	DATE			
WMS_LAST_UPDATE_UID	VARCHAR2 (20)			
WMS_LAST_FPC	VARCHAR2 (30)			
WMS_LAST_UPDATE_DATE	DATE			
WMS_APPROVE_UID	VARCHAR2 (20)			
WMS_APPROVE_FPC	VARCHAR2 (30)			
WMS_APPROVE_DATE	DATE	
		
FMD_CLIENT_TYPE	NUMBER(15)			Client Type
FMD_LEVEL	VARCHAR2(30)			Level (Client Type Client   Fund )
FMD_CLIENT 	VARCHAR2(10)			Client Name ( You should show client name and in the backend u should store client id)
 */
@RestController
public class FWMS_MUTUAL_FUNDS_FEESController{
	
	
	@Autowired
	FundUserValidate fundUserValidate;
	
	
	@Autowired
	FUND_CLIENT_DOCUMENTS_TYPE2Repository fUND_CLIENT_DOCUMENTS_TYPE2Repository;
	
	@Autowired
	FUND_MAR_CLIENT_TYPERespository fUND_MAR_CLIENT_TYPERespository;
	
	@Autowired
	FWMS_SHARE_CLASSRepository fWMS_SHARE_CLASSRepository; 
	
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;

	@Autowired
	FWMS_MUTUAL_FUNDS_FEESRepository fWMS_MUTUAL_FUNDS_FEESRepository;
	
	@Autowired
	FUND_MSTRRepository fUND_MSTRRepository;
	
	@Autowired
	StockParameters stockParameters;
	
	@Autowired
	RE_INVESTORRepository rE_INVESTORRepository;
	
	private static final Logger logger=LoggerFactory.getLogger("FWMS_MUTUAL_FUNDS_FEES CONTROLLER");
	@PostMapping("/savefundfee")
	public ResponseEntity<Map<String,Object>> save(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
	
		Object WMS_EDATE  =requestJson.get("effectdate");
		Object WMS_SHARE_CLASS=requestJson.get("shareclassid");
		Object FMD_LEVEL=requestJson.get("Level");
		Object FMD_CLIENT =requestJson.get("Clientid");
		Object FMD_CLIENT_TYPE  =requestJson.get("Clienttypeid");
		Object WMS_FUND_ID =requestJson.get("Fund_Id");
		
		Object WMS_FEE_PER_FLAG=requestJson.get("Fee_flag");//Fee Falg (Flat Amount or Percentage)
		Object WMS_CAL_FREQUENCY=requestJson.get("Frequency");		//Frequency
		Object WMS_DIVISORY_DAY=requestJson.get("Divisory_Days");				//Divisory Days
		Object WMS_CAL_FUND_SC_LEVEL=requestJson.get("Calcualte_on");				//Calcualte on
		Object WMS_WEEK_DAY=requestJson.get("Day");				//Day (List box show monday.... Sunday in the list al the days)
		Object WMS_FEE_FLAG=requestJson.get("Fee_type");				//Fees Flag ( List Box mANAGMENT Feeadmin etc)
		Object WMS_FEE_PER=requestJson.get("Fee_Amount");		//fee % (Amount)
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("createdby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		 if(WMS_EDATE==null ||FMD_LEVEL==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
				
				
				FWMS_MUTUAL_FUNDS_FEES fscm=new FWMS_MUTUAL_FUNDS_FEES();
				  Calendar cl=Calendar.getInstance();
				  cl.setTimeInMillis(new Long(WMS_EDATE.toString().trim()));
				  fscm.setWMS_EDATE(cl);
				 
				  fscm.setFMD_LEVEL(FMD_LEVEL.toString().trim());
				 
				  if(WMS_SHARE_CLASS!=null) {
					  if(WMS_SHARE_CLASS.toString().trim().isEmpty()==false) {
						  fscm.setWMS_SHARE_CLASS(WMS_SHARE_CLASS.toString().trim());
					  }
				  }
				  if(FMD_CLIENT!=null) {
					  if(FMD_CLIENT.toString().isEmpty()==false) {
						  fscm.setFMD_CLIENT(new Long(FMD_CLIENT.toString()));
					  }
				  }
				  if(FMD_CLIENT_TYPE!=null) {
					  if(FMD_CLIENT_TYPE.toString().isEmpty()==false) {
						  fscm.setFMD_CLIENT_TYPE(new Long(FMD_CLIENT_TYPE.toString()));
					  }
				  }
				 if(WMS_FUND_ID!=null) {
					 if(WMS_FUND_ID.toString().isEmpty()==false) {
						 fscm.setWMS_FUND_ID(new Integer(WMS_FUND_ID.toString()));
					 }
				 }
 
				 if(WMS_FEE_PER_FLAG!=null) {
					 if(WMS_FEE_PER_FLAG.toString().isEmpty()==false) {
						 fscm.setWMS_FEE_PER_FLAG(WMS_FEE_PER_FLAG.toString());
					 }
				 }

				 if(WMS_CAL_FREQUENCY!=null) {
					 if(WMS_CAL_FREQUENCY.toString().isEmpty()==false) {
						 fscm.setWMS_CAL_FREQUENCY(WMS_CAL_FREQUENCY.toString());
					 } 
				 }
					
				 if(WMS_DIVISORY_DAY!=null) {
					 if(WMS_DIVISORY_DAY.toString().isEmpty()==false) {
						 fscm.setWMS_DIVISORY_DAY(new Integer(WMS_DIVISORY_DAY.toString()));
					 } 
				 }

				 if(WMS_CAL_FUND_SC_LEVEL!=null) {
					 if(WMS_CAL_FUND_SC_LEVEL.toString().isEmpty()==false) {
						 fscm.setWMS_CAL_FUND_SC_LEVEL(WMS_CAL_FUND_SC_LEVEL.toString());
					 } 
				 }
					
				 if(WMS_WEEK_DAY!=null) {
					 if(WMS_WEEK_DAY.toString().isEmpty()==false) {
						 fscm.setWMS_WEEK_DAY(WMS_WEEK_DAY.toString());
					 } 
				 }
					
				 
				 if(WMS_FEE_FLAG!=null) {
					 if(WMS_FEE_FLAG.toString().isEmpty()==false) {
						 fscm.setWMS_FEE_FLAG(WMS_FEE_FLAG.toString());
					 } 
				 }
					
				
				 if(WMS_FEE_PER!=null) {
					 if(WMS_FEE_PER.toString().isEmpty()==false) {
						 fscm.setWMS_FEE_PER(new Double(WMS_FEE_PER.toString()));
					 } 
				 }
					
					
					
				 if(WMS_COMMENTS!=null) {
					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
						}
				  fscm.setWMS_STATUS("Not Approved");
						
				 		
				  fscm.setWMS_ENTER_UID(fuser.getSVC_UID());
				  fscm.setWMS_ENTER_DATE(Calendar.getInstance());
				 
				
				  
				  try
					{
					  fscm=fWMS_MUTUAL_FUNDS_FEESRepository.save(fscm);
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
									logger.info("Both Record and Logs saved for fund Fee:"+fscm.getWMS_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved New fund Fee:"+fscm.getWMS_SNO());
							
							json.put("createdby", fscm.getWMS_ENTER_UID());
							json.put("createdon", fscm.getWMS_ENTER_DATE());
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("fund Fee not saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("fund Fee not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
					
				 
				
		 }		 
	}
		
	@PostMapping("/updatefundfee")
	public ResponseEntity<?> update(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
	
		Object WMS_SNO=requestJson.get("WMS_SNO");;
		Object WMS_EDATE  =requestJson.get("WMS_EDATE");
		Object WMS_SHARE_CLASS=requestJson.get("WMS_SHARE_CLASS");
		Object FMD_LEVEL=requestJson.get("Level");
		Object FMD_CLIENT =requestJson.get("Clientid");
		Object FMD_CLIENT_TYPE  =requestJson.get("Clienttypeid");
		Object WMS_FUND_ID =requestJson.get("Fund_Id");
		
		Object WMS_FEE_PER_FLAG=requestJson.get("Fee_flag");//Fee Falg (Flat Amount or Percentage)
		Object WMS_CAL_FREQUENCY=requestJson.get("Frequency");		//Frequency
		Object WMS_DIVISORY_DAY=requestJson.get("Divisory_Days");				//Divisory Days
		Object WMS_CAL_FUND_SC_LEVEL=requestJson.get("Calcualte_on");				//Calcualte on
		Object WMS_WEEK_DAY=requestJson.get("Day");				//Day (List box show monday.... Sunday in the list al the days)
		Object WMS_FEE_FLAG=requestJson.get("Fee_type");				//Fees Flag ( List Box mANAGMENT Feeadmin etc)
		Object WMS_FEE_PER=requestJson.get("Fee_Amount");		//fee % (Amount)
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		 if(WMS_SNO==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
				
				
				FWMS_MUTUAL_FUNDS_FEES fscm=fWMS_MUTUAL_FUNDS_FEESRepository.findOne(new Long(WMS_SNO.toString().trim()));
				 if(WMS_EDATE!=null) {
					 if(WMS_EDATE.toString().trim().isEmpty()==false) {
					 Calendar cl=Calendar.getInstance();
					  cl.setTimeInMillis(new Long(WMS_EDATE.toString().trim()));
					  fscm.setWMS_EDATE(cl);
					 }
				 }
				 if(FMD_LEVEL!=null) {
					if(FMD_LEVEL.toString().isEmpty()==false) {
						fscm.setFMD_LEVEL(FMD_LEVEL.toString().trim());
					}
				 }
				  
				 
				  if(WMS_SHARE_CLASS!=null) {
					  if(WMS_SHARE_CLASS.toString().trim().isEmpty()==false) {
						  fscm.setWMS_SHARE_CLASS(WMS_SHARE_CLASS.toString().trim());
					  }
				  }
				  if(FMD_CLIENT!=null) {
					  if(FMD_CLIENT.toString().isEmpty()==false) {
						  fscm.setFMD_CLIENT(new Long(FMD_CLIENT.toString()));
					  }
				  }
				  if(FMD_CLIENT_TYPE!=null) {
					  if(FMD_CLIENT_TYPE.toString().isEmpty()==false) {
						  fscm.setFMD_CLIENT_TYPE(new Long(FMD_CLIENT_TYPE.toString()));
					  }
				  }
				 if(WMS_FUND_ID!=null) {
					 if(WMS_FUND_ID.toString().isEmpty()==false) {
						 fscm.setWMS_FUND_ID(new Integer(WMS_FUND_ID.toString()));
					 }
				 }
 
				 if(WMS_FEE_PER_FLAG!=null) {
					 if(WMS_FEE_PER_FLAG.toString().isEmpty()==false) {
						 fscm.setWMS_FEE_PER_FLAG(WMS_FEE_PER_FLAG.toString());
					 }
				 }

				 if(WMS_CAL_FREQUENCY!=null) {
					 if(WMS_CAL_FREQUENCY.toString().isEmpty()==false) {
						 fscm.setWMS_CAL_FREQUENCY(WMS_CAL_FREQUENCY.toString());
					 } 
				 }
					
				 if(WMS_DIVISORY_DAY!=null) {
					 if(WMS_DIVISORY_DAY.toString().isEmpty()==false) {
						 fscm.setWMS_DIVISORY_DAY(new Integer(WMS_DIVISORY_DAY.toString()));
					 } 
				 }

				 if(WMS_CAL_FUND_SC_LEVEL!=null) {
					 if(WMS_CAL_FUND_SC_LEVEL.toString().isEmpty()==false) {
						 fscm.setWMS_CAL_FUND_SC_LEVEL(WMS_CAL_FUND_SC_LEVEL.toString());
					 } 
				 }
					
				 if(WMS_WEEK_DAY!=null) {
					 if(WMS_WEEK_DAY.toString().isEmpty()==false) {
						 fscm.setWMS_WEEK_DAY(WMS_WEEK_DAY.toString());
					 } 
				 }
					
				 
				 if(WMS_FEE_FLAG!=null) {
					 if(WMS_FEE_FLAG.toString().isEmpty()==false) {
						 fscm.setWMS_FEE_FLAG(WMS_FEE_FLAG.toString());
					 } 
				 }
					
				
				 if(WMS_FEE_PER!=null) {
					 if(WMS_FEE_PER.toString().isEmpty()==false) {
						 fscm.setWMS_FEE_PER(new Double(WMS_FEE_PER.toString()));
					 } 
				 }
					
					
					
				 if(WMS_COMMENTS!=null) {
					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
						}
				  
				  fscm.setWMS_LAST_UPDATE_UID(fuser.getSVC_UID());
				  fscm.setWMS_LAST_UPDATE_DATE(Calendar.getInstance());
				 
				
				  
				  try
					{
					  fscm=fWMS_MUTUAL_FUNDS_FEESRepository.save(fscm);
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
									logger.info("Both Record and Logs saved for fund Fee:"+fscm.getWMS_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved New fund Fee:"+fscm.getWMS_SNO());
							/*
							 * Creating json
							 */
							FundsFeesBean ffb=getJsonBean(fscm);
							ffb.setMsg("Saved");
							return new ResponseEntity<FundsFeesBean>(ffb,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("fund Fee not saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("fund Fee not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
					
				 
				
		 }		 
	}
		
	@PostMapping("/approvefundfee")
	public ResponseEntity<?> approve(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
	
		Object WMS_SNO=requestJson.get("WMS_SNO");;
		
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("approvedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		 if(WMS_SNO==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
				
				
				FWMS_MUTUAL_FUNDS_FEES fscm=fWMS_MUTUAL_FUNDS_FEESRepository.findOne(new Long(WMS_SNO.toString().trim()));
				 if(WMS_COMMENTS!=null) {
					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
						}
				 
				
						FWMS_SHARE_CLASS fshare=fWMS_SHARE_CLASSRepository.findOne(new Long(fscm.getWMS_SHARE_CLASS().toString().trim()));
						if(fshare!=null) {
							if(fshare.getWMS_APPROVE_UID()==null) {
								logger.error("Share class is not approved");
								json.put("msg", "Share class not approved");
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							}
						}
					
					
					String FMD_LEVEL=fscm.getFMD_LEVEL();	
					
					
					 /*
					   * FUND is always will put
					   */
					  Long FMD_FUND=fscm.getWMS_FUND_ID();
						FUND_MSTR fund_MSTR=fUND_MSTRRepository.findOne(new Long(FMD_FUND.toString()));
						  if(fund_MSTR!=null) {
								if(fund_MSTR.getIV_APPROVE_UID()==null) {
									logger.error("fund is not approved");
									json.put("msg", "fund not approved");
									return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
								}
						  }
					
				    if(FMD_LEVEL!=null) {
					 if(FMD_LEVEL.equalsIgnoreCase("Client level")) {
							  Long FMD_Client=new Long(fscm.getFMD_CLIENT().toString());
							  RE_INVESTOR fUND_MSTR=rE_INVESTORRepository.findOne(FMD_Client) ;
							  if(fUND_MSTR.getIV_APPROVE_UID()==null) {
									logger.error("Client is not approved");
									json.put("msg", "Client not approved");
									return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
								}
						  }
						
			      else if(FMD_LEVEL.equalsIgnoreCase("Client type")) {
				  				Long FMD_CLIENT_TYPE=fscm.getFMD_CLIENT_TYPE();
								  FUND_MAR_CLIENT_TYPE fUND_MSTR= fUND_MAR_CLIENT_TYPERespository.findOne(FMD_CLIENT_TYPE);
								  if(fUND_MSTR.getIV_APPROVE_UID()==null) {
										logger.error("Client Type is not approved");
										json.put("msg", "Client Type not approved");
										return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
									}
					  }
					}
				 
				 fscm.setWMS_STATUS("Approved");
				 fscm.setWMS_APPROVE_UID(fuser.getSVC_UID());
				 fscm.setWMS_APPROVE_DATE(Calendar.getInstance());
				  try
					{
					  fscm=fWMS_MUTUAL_FUNDS_FEESRepository.save(fscm);
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
									logger.info("Both Record and Logs saved for fund Fee:"+fscm.getWMS_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved New fund Fee:"+fscm.getWMS_SNO());
							/*
							 * Creating json
							 */
							FundsFeesBean ffb=getJsonBean(fscm);
							ffb.setMsg("Saved");
							return new ResponseEntity<FundsFeesBean>(ffb,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("fund Fee not saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("fund Fee not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
		 }		 
	}
		

	@PostMapping("/revokefundfee")
	public ResponseEntity<?> Revoke(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
	
		Object WMS_SNO=requestJson.get("WMS_SNO");;
		
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		 if(WMS_SNO==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
				
				
				FWMS_MUTUAL_FUNDS_FEES fscm=fWMS_MUTUAL_FUNDS_FEESRepository.findOne(new Long(WMS_SNO.toString().trim()));
				 if(WMS_COMMENTS!=null) {
					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
						}
				 
				
						
				 
				 fscm.setWMS_STATUS("Not Approved");
				 fscm.setWMS_APPROVE_UID(null);
				 fscm.setWMS_APPROVE_DATE(null);
				  fscm.setWMS_LAST_UPDATE_UID(fuser.getSVC_UID());
				  fscm.setWMS_LAST_UPDATE_DATE(Calendar.getInstance());
				 
				
				  try
					{
					  fscm=fWMS_MUTUAL_FUNDS_FEESRepository.save(fscm);
					 if(fscm!=null) {
							    json.put("msg", "saved");				
								FUND_USER_LOG ful=new FUND_USER_LOG();
								ful.setSVC_UID(fuser.getSVC_UID());
								ful.setSVL_USERID(fuser.getSVU_NAME());
								ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
								ful.setSVL_TTYPE("REVOKE");
								ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
								ful.setSVL_DATE(Calendar.getInstance());
								ful=fUND_USER_LOGRepository.save(ful);
								if(ful!=null) {
									logger.info("Both Record and Logs saved for fund Fee:"+fscm.getWMS_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved New fund Fee:"+fscm.getWMS_SNO());
							/*
							 * Creating json
							 */
							FundsFeesBean ffb=getJsonBean(fscm);
							ffb.setMsg("Saved");
							return new ResponseEntity<FundsFeesBean>(ffb,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("fund Fee not saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("fund Fee not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
		 }		 
	}
	
	@PostMapping("/deletefundfee")
	public ResponseEntity<Map<String,Object>> delete(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
	
		Object WMS_SNO=requestJson.get("sno");;
		
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		 if(WMS_SNO==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
				
				
				FWMS_MUTUAL_FUNDS_FEES fscm=fWMS_MUTUAL_FUNDS_FEESRepository.findOne(new Long(WMS_SNO.toString().trim()));
				 if(WMS_COMMENTS!=null) {
					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
						}
				 
				
						
				
				
				  try
					{
					  fWMS_MUTUAL_FUNDS_FEESRepository.delete(fscm);
//					 if(fscm!=null) {
//							    json.put("msg", "saved");				
//								FUND_USER_LOG ful=new FUND_USER_LOG();
//								ful.setSVC_UID(fuser.getSVC_UID());
//								ful.setSVL_USERID(fuser.getSVU_NAME());
//								ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
//								ful.setSVL_TTYPE("REVOKE");
//								ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
//								ful.setSVL_DATE(Calendar.getInstance());
//								ful=fUND_USER_LOGRepository.save(ful);
//								if(ful!=null) {
//									logger.info("Both Record and Logs saved for fund Fee:"+fscm.getWMS_SNO());
//									json.put("logs","logs are saved");
//								}
//								else {
//									logger.info("Record is saved but logs can't saved due error in saving of logs");
//									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
//								}
//							
//							logger.info("Saved New fund Fee:"+fscm.getWMS_SNO());
//							/*
//							 * Creating json
//							 */
//							json=getJson(fscm);
//							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
//						}
//						else {
//							json.put("msg","Not saved");
//							logger.error("fund Fee not saved");
//							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
//						}
					   logger.info("DELETED");
						/*
						 * Creating json
						 */
						json.put("msg", "DELETD");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("fund Fee not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
		 }		 
	}
		
	@GetMapping("/fundfeesearch")
	public ResponseEntity<?> getAllBenchmark(Pageable page,
			@RequestParam(required=false,value="action")String action,
			@RequestParam(required=false,value="paramSearch")String paramString,
			@RequestParam(required=false,value="level")String level
			){
	
		List<Long> fundids=new ArrayList<>();
		
		List<Long> levelids=new ArrayList<>();
		if(paramString!=null) {
			if(paramString.isEmpty()==false) {
				/*
				 * Fund information
				 */
				fundids=fUND_MSTRRepository.find_ids_FUND_MSTR_LIST(paramString);
				
				if(level!=null) {
					if(level.isEmpty()==false) {
						if(level.equalsIgnoreCase("Client name")) {
							levelids=rE_INVESTORRepository.find_ids_Client(paramString);
						}else if(level.equalsIgnoreCase("Client type")) {
							levelids=fUND_MAR_CLIENT_TYPERespository.find_ids_FUND_MAR_CLIENT_TYPE(paramString);
						}else if(level.equalsIgnoreCase("fund name")) {
							fundids=fUND_MSTRRepository.find_ids_FUND_MSTR_LIST(paramString);
						}
					}
				}

			}
		}
		
		if(fundids.isEmpty()) {
			fundids=Arrays.asList(new Long(-1));
		}
		
		if(levelids.isEmpty()) {
			levelids=Arrays.asList(new Long(-1));
		}
		if(level==null) {
			level="";
		} 
		
//		System.out.println(fundids);
//		System.out.println(levelids);
		  
	  //Page<FWMS_MUTUAL_FUNDS_FEES> allFUND_SHARE_COMPANY_MSTR=null;
		List<FWMS_MUTUAL_FUNDS_FEES> allFUND_SHARE_COMPANY_MSTR=null;
		if(action!=null) {
			if(action.toString().isEmpty()==false) {
				if(action.equalsIgnoreCase("ALL")) {
					if(paramString!=null) {
							if(paramString.isEmpty()==false) {
								if(level.isEmpty()==false) {
									allFUND_SHARE_COMPANY_MSTR=fWMS_MUTUAL_FUNDS_FEESRepository.findAllFWMS_MUTUAL_FUNDS_FEES_SEARCH(fundids,levelids, level);
	 
								}else {
									allFUND_SHARE_COMPANY_MSTR=fWMS_MUTUAL_FUNDS_FEESRepository.findAllFWMS_MUTUAL_FUNDS_FEES_SEARCH(fundids,levelids);
								}
							}else {
								if(level.isEmpty()==false) {
									allFUND_SHARE_COMPANY_MSTR=fWMS_MUTUAL_FUNDS_FEESRepository.findAllFWMS_MUTUAL_FUNDS_FEES_SEARCH(level);
	 
								}else {
									allFUND_SHARE_COMPANY_MSTR=(List<FWMS_MUTUAL_FUNDS_FEES>) fWMS_MUTUAL_FUNDS_FEESRepository.findAll();
								}
							}
						
					}else {
						allFUND_SHARE_COMPANY_MSTR=(List<FWMS_MUTUAL_FUNDS_FEES>) fWMS_MUTUAL_FUNDS_FEESRepository.findAll();
				}
					
				}
				
				if(action.equalsIgnoreCase("APPROVED")) {
					if(paramString!=null) {
							if(paramString.isEmpty()==false) {
								if(level.isEmpty()==false) {
									allFUND_SHARE_COMPANY_MSTR=fWMS_MUTUAL_FUNDS_FEESRepository.findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHAPPROVED(fundids,levelids, level);
	 
								}else {
									allFUND_SHARE_COMPANY_MSTR=fWMS_MUTUAL_FUNDS_FEESRepository.findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHAPPROVED(fundids,levelids);
								}
							}else {
								
								if(level.isEmpty()==false) {
									allFUND_SHARE_COMPANY_MSTR=fWMS_MUTUAL_FUNDS_FEESRepository.findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHAPPROVED(level);
	 
								}else {
									allFUND_SHARE_COMPANY_MSTR=fWMS_MUTUAL_FUNDS_FEESRepository.findAllAPPROVEDFWMS_MUTUAL_FUNDS_FEES();
								}
								
							}
						
					}else {
						allFUND_SHARE_COMPANY_MSTR=fWMS_MUTUAL_FUNDS_FEESRepository.findAllAPPROVEDFWMS_MUTUAL_FUNDS_FEES();
				}
					
				}
				
				if(action.equalsIgnoreCase("UNAPPROVED")) {
					if(paramString!=null) {
						if(paramString.isEmpty()==false) {
							if(level.isEmpty()==false) {
								allFUND_SHARE_COMPANY_MSTR=fWMS_MUTUAL_FUNDS_FEESRepository.findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHUNAPPROVED(fundids,levelids, level);
 
							}else {
								allFUND_SHARE_COMPANY_MSTR=fWMS_MUTUAL_FUNDS_FEESRepository.findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHUNAPPROVED(fundids,levelids);
							}
						}else {
							if(level.isEmpty()==false) {
								allFUND_SHARE_COMPANY_MSTR=fWMS_MUTUAL_FUNDS_FEESRepository.findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHUNAPPROVED(level);
 
							}else {
								allFUND_SHARE_COMPANY_MSTR=fWMS_MUTUAL_FUNDS_FEESRepository.findAllUNAPPROVEDFWMS_MUTUAL_FUNDS_FEES();
							}
							
						}
						
					}else {
						allFUND_SHARE_COMPANY_MSTR=fWMS_MUTUAL_FUNDS_FEESRepository.findAllUNAPPROVEDFWMS_MUTUAL_FUNDS_FEES();
				}
					
				}
			}
		}
		else {

			Map<String,Object>json=new HashMap<>();
			json.put("msg", "Value of action is missing");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		List<FundsFeesBean> allFundfees=new ArrayList<>();
		if(allFUND_SHARE_COMPANY_MSTR.size()>0) {
			allFUND_SHARE_COMPANY_MSTR.forEach(conut->{
				FundsFeesBean json=getJsonBean(conut);
				allFundfees.add(json);
			});
		}
		
//		Page<FundsFeesBean> jsonArray=allFUND_SHARE_COMPANY_MSTR.map(new Converter<FWMS_MUTUAL_FUNDS_FEES, FundsFeesBean>() {
//			@Override
//			public FundsFeesBean convert(FWMS_MUTUAL_FUNDS_FEES conut) {
//				FundsFeesBean json=getJsonBean(conut);
//						return json;
//			}
//			
//		});
		Collections.sort(allFundfees);
		int start = page.getOffset();
		int end = (start + page.getPageSize()) > allFundfees.size() ? allFundfees.size()
				: (start + page.getPageSize());
		Page<FundsFeesBean> jsonArray = new PageImpl<FundsFeesBean>(allFundfees.subList(start, end), page,
				allFundfees.size());
		return new ResponseEntity<Page<FundsFeesBean>>(jsonArray,HttpStatus.OK);
	}

	public FundsFeesBean getJsonBean(FWMS_MUTUAL_FUNDS_FEES conut){
		FundsFeesBean ffb=new FundsFeesBean();
	
        ffb.setSno(conut.getWMS_SNO()+"");
		ffb.setEffectdate(conut.getWMS_EDATE());
		Object WMS_SHARE_CLASS=conut.getWMS_SHARE_CLASS();
		if(WMS_SHARE_CLASS!=null) {
			FWMS_SHARE_CLASS fshare=fWMS_SHARE_CLASSRepository.findOne(new Long(WMS_SHARE_CLASS.toString().trim()));
			if(fshare!=null) {
				ffb.setShare_id(fshare.getWMS_SCLASS_ID()+"");
				ffb.setShare_name(fshare.getWMS_SCLASS_DESC());
			}else {

				ffb.setShare_id(null);
				ffb.setShare_name(null);
			}
		}
		
		String FMD_LEVEL=conut.getFMD_LEVEL();	
		ffb.setLevel(conut.getFMD_LEVEL());
		
		 /*
		   * FUND is always will put
		   */
		  Long FMD_FUND=conut.getWMS_FUND_ID();
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
				  Long FMD_Client=new Long(conut.getFMD_CLIENT().toString());
				  RE_INVESTOR fUND_MSTR=rE_INVESTORRepository.findOne(FMD_Client) ;
				  if(fUND_MSTR!=null) {
					  ffb.setClient_id(conut.getFMD_CLIENT()+"");
					  ffb.setClient_name(fUND_MSTR.getRI_INVESTOR_NAME());
				  }else {
					  ffb.setClient_id(null);
						ffb.setClient_id( null);
				  }
			  }
			
   else if(FMD_LEVEL.equalsIgnoreCase("Client type")) {
					Long FMD_CLIENT_TYPE=conut.getFMD_CLIENT_TYPE();
					  FUND_MAR_CLIENT_TYPE fUND_MSTR= fUND_MAR_CLIENT_TYPERespository.findOne(FMD_CLIENT_TYPE);
						  if(fUND_MSTR!=null) {
								ffb.setClient_type_id(fUND_MSTR.getFCT_ID()+"");
								ffb.setClient_type_name( fUND_MSTR.getFCT_NAME());
						  }else {
							  ffb.setClient_type_id(null);
								ffb.setClient_type_name(null);
						  }
		  }
		}
		
		  ffb.setFee_Flag(conut.getWMS_FEE_PER_FLAG());
		  ffb.setDivisory_Days(conut.getWMS_DIVISORY_DAY()+"");
		  ffb.setCalcualte_on(conut.getWMS_CAL_FUND_SC_LEVEL());
		  ffb.setDay(conut.getWMS_WEEK_DAY());
		  ffb.setFee_Type(conut.getWMS_FEE_FLAG());
		  ffb.setFee_Amount(conut.getWMS_FEE_PER()+"");		  
		  ffb.setComments( conut.getWMS_COMMENTS()+"");
		  ffb.setStatus( conut.getWMS_STATUS()+"");
		  ffb.setFrequency(conut.getWMS_CAL_FREQUENCY());
		FUND_USERS user=null;
		if(conut.getWMS_ENTER_UID()!=null) {
			user=fundUserValidate.getUserName(conut.getWMS_ENTER_UID());
			if(user!=null) {
			ffb.setEnteredby( user.getSVU_NAME());
			ffb.setEnteredbyuserid( user.getSVU_USER_NAME());
			ffb.setEnteredbyuuid( user.getSVC_UID());
			ffb.setEntereddate(conut.getWMS_ENTER_DATE());
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
		
		if(conut.getWMS_APPROVE_UID()!=null) {
			user=fundUserValidate.getUserName(conut.getWMS_APPROVE_UID());
			if(user!=null) {
			ffb.setApprovedby( user.getSVU_NAME());
			ffb.setApprovedbyuserid( user.getSVU_USER_NAME());
			ffb.setApprovedbyuuid( user.getSVC_UID());
			ffb.setApproveddate( conut.getWMS_APPROVE_DATE());
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
		
		if(conut.getWMS_LAST_UPDATE_UID()!=null) {
			user=fundUserValidate.getUserName(conut.getWMS_LAST_UPDATE_UID());
			if(user!=null) {
			ffb.setModifiedby( user.getSVU_NAME());
			ffb.setModifiedbyuserid( user.getSVU_USER_NAME());
			ffb.setModifiedbyuuid( user.getSVC_UID());
			ffb.setModifieddate( conut.getWMS_LAST_UPDATE_DATE());
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

//	public Map<String,Object> getJson(FWMS_MUTUAL_FUNDS_FEES conut){
//	Map<String,Object> json=new HashMap<String,Object>();
//    json.put("sno", conut.getWMS_SNO());
//	json.put("effectdate", conut.getWMS_EDATE());
//	Object WMS_SHARE_CLASS=conut.getWMS_SHARE_CLASS();
//	if(WMS_SHARE_CLASS!=null) {
//		FWMS_SHARE_CLASS fshare=fWMS_SHARE_CLASSRepository.findOne(new Long(WMS_SHARE_CLASS.toString().trim()));
//		if(fshare!=null) {
//			json.put("share_id", fshare.getWMS_SCLASS_ID());
//			json.put("share_name", fshare.getWMS_SCLASS_DESC());
//		}else {
//
//			json.put("share_id", null);
//			json.put("share_name",null);
//		}
//	}
//	
//	String FMD_LEVEL=conut.getFMD_LEVEL();	
//	json.put("level",conut.getFMD_LEVEL());
//	
//	 /*
//	   * FUND is always will put
//	   */
//	  Integer FMD_FUND=conut.getWMS_FUND_ID();
//		FUND_MSTR fund_MSTR=fUND_MSTRRepository.findOne(new Long(FMD_FUND.toString()));
//		  if(fund_MSTR!=null) {
//				json.put("fund_id",fund_MSTR.getSVC_CODE());
//				json.put("fund_name", fund_MSTR.getSVC_NAME());
//		  }else {
//			  json.put("fund_id",null);
//				json.put("fund_name", null);
//		 }
//	
//if(FMD_LEVEL!=null) {
//	 if(FMD_LEVEL.equalsIgnoreCase("Client level")) {
//			  Long FMD_Client=new Long(conut.getFMD_CLIENT().toString());
//			  RE_INVESTOR fUND_MSTR=rE_INVESTORRepository.findOne(FMD_Client) ;
//			  if(fUND_MSTR!=null) {
//				  json.put("client_id",conut.getFMD_CLIENT());
//				  json.put("client_name",fUND_MSTR.getRI_INVESTOR_NAME());
//			  }else {
//				  json.put("client_id",null);
//					json.put("client_id", null);
//			  }
//		  }
//		
//else if(FMD_LEVEL.equalsIgnoreCase("Client type")) {
//				Long FMD_CLIENT_TYPE=conut.getFMD_CLIENT_TYPE();
//				  FUND_MAR_CLIENT_TYPE fUND_MSTR= fUND_MAR_CLIENT_TYPERespository.findOne(FMD_CLIENT_TYPE);
//					  if(fUND_MSTR!=null) {
//							json.put("client_type_id",fUND_MSTR.getFCT_ID());
//							json.put("client_type_name", fUND_MSTR.getFCT_NAME());
//					  }else {
//						  json.put("client_type_id",null);
//							json.put("client_type_name",null);
//					  }
//	  }
//	}
//	
//	  json.put("Flat_Amount",conut.getWMS_FEE_PER_FLAG());
//	  json.put("Divisory_Days",conut.getWMS_DIVISORY_DAY());
//	  json.put("Calcualte_on",conut.getWMS_CAL_FUND_SC_LEVEL());
//	  json.put("Day",conut.getWMS_WEEK_DAY());
//	  json.put("Fee_Flag",conut.getWMS_FEE_FLAG());
//	  json.put("Fee_Amount",conut.getWMS_FEE_PER());		  
//	  json.put("comments", conut.getWMS_COMMENTS());
//	  json.put("status", conut.getWMS_STATUS());
//	
//	FUND_USERS user=null;
//	if(conut.getWMS_ENTER_UID()!=null) {
//		user=fundUserValidate.getUserName(conut.getWMS_ENTER_UID());
//		if(user!=null) {
//		json.put("enteredby", user.getSVU_NAME());
//		json.put("enteredbyuserid", user.getSVU_USER_NAME());
//		json.put("enteredbyuuid", user.getSVC_UID());
//		json.put("entereddate", conut.getWMS_ENTER_DATE());
//		}
//		else {
//			json.put("enteredby", null);
//			json.put("enteredbyuserid", null);
//			json.put("enteredbyuuid", null);
//			json.put("entereddate", null);
//		}
//	}else {
//		json.put("enteredby", null);
//		json.put("enteredbyuserid", null);
//		json.put("enteredbyuuid", null);
//		json.put("entereddate", null);
//	}
//	
//	if(conut.getWMS_APPROVE_UID()!=null) {
//		user=fundUserValidate.getUserName(conut.getWMS_APPROVE_UID());
//		if(user!=null) {
//		json.put("approvedby", user.getSVU_NAME());
//		json.put("approvedbyuserid", user.getSVU_USER_NAME());
//		json.put("approvedbyuuid", user.getSVC_UID());
//		json.put("approveddate", conut.getWMS_APPROVE_DATE());
//		}
//		else {
//			json.put("approvedby", null);
//			json.put("approvedbyuserid", null);
//			json.put("approvedbyuuid", null);
//			json.put("approveddate", null);
//		}
//	}else {
//		json.put("approvedby", null);
//		json.put("approvedbyuserid", null);
//		json.put("approvedbyuuid", null);
//		json.put("approveddate", null);
//	}
//	
//	if(conut.getWMS_LAST_UPDATE_UID()!=null) {
//		user=fundUserValidate.getUserName(conut.getWMS_LAST_UPDATE_UID());
//		if(user!=null) {
//		json.put("modifiedby", user.getSVU_NAME());
//		json.put("modifiedbyuserid", user.getSVU_USER_NAME());
//		json.put("modifiedbyuuid", user.getSVC_UID());
//		json.put("modifieddate", conut.getWMS_LAST_UPDATE_DATE());
//		}
//		else {
//			json.put("modifiedby", null);
//			json.put("modifiedbyuserid", null);
//			json.put("modifiedbyuuid", null);
//			json.put("modifieddate", null);
//		}
//	}else {
//		json.put("modifiedby", null);
//		json.put("modifiedbyuserid", null);
//		json.put("modifiedbyuuid", null);
//		json.put("modifieddate", null);
//	}
//	
//	return json;
//}
//
//
	
}