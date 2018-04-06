package com.fynisys.controller.funds;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.fynisys.controller.funds.beans.StopLossBean;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.FUND_USER_LOG;
import com.fynisys.model.clienttype.FUND_MAR_CLIENT_TYPE;
import com.fynisys.model.crm.RE_INVESTOR;
import com.fynisys.model.funds.FUND_MSTR;
import com.fynisys.model.funds.FWMS_STOP_LOSS;
import com.fynisys.model.parameters.FUND_INSTRUMENT_MSTR;
import com.fynisys.model.stock.FUND_SHARE_COMPANY_MSTR;
import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.clienttype.FUND_MAR_CLIENT_TYPERespository;
import com.fynisys.repository.crm.RE_INVESTORRepository;
import com.fynisys.repository.funds.FUND_MSTRRepository;
import com.fynisys.repository.funds.FWMS_STOP_LOSSRepository;
import com.fynisys.repository.stock.FUND_SHARE_COMPANY_MSTRRepository;
import com.fynisys.utilities.StockParameters;

@RestController
public class FWMS_STOP_LOSSController {
	@Autowired
	FUND_USERSRepository fund_UserRepository;
	
	@Autowired
	FWMS_STOP_LOSSRepository fWMS_STOP_LOSSRepository;
	
	@Autowired
	FUND_MSTRRepository fUND_MSTRRepository;
	
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;
	
	@Autowired
	StockParameters stockParameters;
	
	@Autowired
	FUND_SHARE_COMPANY_MSTRRepository fUND_SHARE_COMPANY_MSTRRepository; 
	
	@Autowired
	RE_INVESTORRepository rE_INVESTORRepository;
	
	@Autowired
	FUND_MAR_CLIENT_TYPERespository fUND_MAR_CLIENT_TYPERespository;
	
	
	private static final Logger logger=LoggerFactory.getLogger("FWMS_STOP_LOSS CONTROLLER");
	@PostMapping("/addstoploss")
	public ResponseEntity<Map<String,Object>> save(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
			/*
		 	Serial Number
		   	Effect Date (textfield : Masking of date format is dd-mm-yyyy along with calendar option)
			Fund Name [Dropdown from master data]
			Stop/Loss Level[Dropdown: Asset/Stock. Default : Stock]
			Level[Dropdown: Client/Client Type/ Fund level. Default selected would be : Fund Level]
			Stop/Loss% [Textfield](Numbers can be entered only)
			Client Name [Dropdown from master data]
			Client Type [Dropdown from master data]
			Asset Type [Dropdown from master data]
			Stock Name [Dropdown from master data]
			Comment
			Status */
		Object FMD_DATE  =requestJson.get("effectdate");
		Object FMD_FUND =requestJson.get("fund_id");
		Object FMD_SL_LEVEL=requestJson.get("stop_loss_level");
		Object FMD_LEVEL=requestJson.get("level");
		Object FMD_CLIENT =requestJson.get("client_id");
		Object FMD_C_TYPE  =requestJson.get("client_type");
		Object FMD_SID  =requestJson.get("type");
		Object FMD_STOP_LOSS =requestJson.get("stop_loss");
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("createdby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		 if(FMD_DATE==null||FMD_FUND==null||FMD_SL_LEVEL==null||FMD_STOP_LOSS==null||
				 FMD_LEVEL==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
					  json.put("msg", "Please check Input json, missing some required attributes");
					  logger.error("Please check Input json, missing some required attributes");
				      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
				  }
		 else {
			  FUND_USERS fuser=isValid(createdby.toString());
				if(fuser==null)
				{
						json.put("msg", "Createdby is not valid user");
						logger.error("Createdby is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
				
				
				FWMS_STOP_LOSS fscm=new FWMS_STOP_LOSS();
				//effective Date
				  Calendar cl=Calendar.getInstance();
				  cl.setTimeInMillis(new Long(FMD_DATE.toString().trim()));
				  fscm.setFMD_DATE(cl);
				  
				  fscm.setFMD_SL_LEVEL(FMD_SL_LEVEL.toString());
				 
				  if(FMD_LEVEL!=null) {
				  fscm.setFMD_LEVEL(FMD_LEVEL.toString().trim());
				  }
				  fscm.setFMD_FUND(new Long(FMD_FUND.toString().trim()));
				  if(FMD_CLIENT!=null) {
					  if(FMD_CLIENT.toString().isEmpty()==false)
					  {
						  fscm.setFMD_CLIENT(new Long(FMD_CLIENT.toString().trim())); 
					  }
				  }
				  if(FMD_C_TYPE!=null)
				  {
					  if(FMD_C_TYPE.toString().isEmpty()==false) {
						  fscm.setFMD_C_TYPE(new Long(FMD_C_TYPE.toString().trim()));
					  }
				  }
				  if(FMD_SID!=null) {
					  if(FMD_SID.toString().isEmpty()==false) {
						  fscm.setFMD_SID(new Long(FMD_SID.toString().trim())); 
					  }
				  }
				 if(FMD_STOP_LOSS!=null) {
					 if(FMD_STOP_LOSS.toString().isEmpty()==false) {
					   fscm.setFMD_STOP_LOSS(new Double(FMD_STOP_LOSS.toString().trim()));
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
					long val=0;
					if(FMD_LEVEL.toString().equalsIgnoreCase("Fund Level")){
							val=fWMS_STOP_LOSSRepository.fundCheckFundLevelDuplicate(new Long(FMD_FUND.toString().trim()), 
									cl, FMD_SL_LEVEL.toString(), FMD_LEVEL.toString(), new Long(FMD_SID.toString().trim()));
					}else if(FMD_LEVEL.toString().equalsIgnoreCase("Client")) {
							val=fWMS_STOP_LOSSRepository.fundCheckClentDuplicate(new Long(FMD_FUND.toString().trim()), 
									cl, FMD_SL_LEVEL.toString(), FMD_LEVEL.toString(), new Long(FMD_SID.toString().trim()),
									new Long(FMD_CLIENT.toString().trim()));
					}else if(FMD_LEVEL.toString().equalsIgnoreCase("Client Type")) {
						val=fWMS_STOP_LOSSRepository.fundCheckClientTypeDuplicate(new Long(FMD_FUND.toString().trim()), 
								cl, FMD_SL_LEVEL.toString(), FMD_LEVEL.toString(), new Long(FMD_SID.toString().trim()),
								new Long(FMD_C_TYPE.toString().trim()));
					}
					if(val>0) {
						json.put("msg", "Record is duplicate");
						logger.error("FWMS_STOP_LOSS not saved due to duplicate");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
					
					
					
					 fscm=fWMS_STOP_LOSSRepository.save(fscm);
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
									logger.info("Both Record and Logs saved for FWMS_STOP_LOSS:"+fscm.getFMD_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved New STOCK LOSS:"+fscm.getFMD_SNO());
							
							json.put("createdby", fscm.getIV_ENTER_UID());
							json.put("createdon", fscm.getIV_ENTER_DATE());
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("FWMS_STOP_LOSS not saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("FWMS_STOP_LOSS not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
					
				 
				
		 }
	 
	}
	
	
	@PostMapping("/updatestoploss")
	public ResponseEntity<?> update(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object FMD_SNO	=requestJson.get("fmd_sno");
		Object FMD_DATE  =requestJson.get("effectdate");
		Object FMD_FUND =requestJson.get("fund_id");
		Object FMD_SL_LEVEL=requestJson.get("stop_loss_level");
		Object FMD_LEVEL=requestJson.get("level");
		Object FMD_CLIENT =requestJson.get("client_id");
		Object FMD_C_TYPE  =requestJson.get("client_type");
		Object FMD_SID  =requestJson.get("type");
		Object FMD_STOP_LOSS =requestJson.get("stop_loss");
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		 if(FMD_SNO==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
					  json.put("msg", "Please check Input json, missing some required attributes");
					  logger.error("Please check Input json, missing some required attributes");
				      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
				  }
		 else {
			  FUND_USERS fuser=isValid(createdby.toString());
				if(fuser==null)
				{
						json.put("msg", "Createdby is not valid user");
						logger.error("Createdby is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				try {
				FWMS_STOP_LOSS fscm=fWMS_STOP_LOSSRepository.findOne(new Long(FMD_SNO.toString().trim()));
				 
			
				  Calendar cl=Calendar.getInstance();
				  cl.setTimeInMillis(new Long(FMD_DATE.toString().trim()));
				  fscm.setFMD_DATE(cl);
				  
				  fscm.setFMD_SL_LEVEL(FMD_SL_LEVEL.toString());
				 
				  if(FMD_LEVEL!=null) {
				  fscm.setFMD_LEVEL(FMD_LEVEL.toString().trim());
				  }
				  if(FMD_FUND!=null) {
					  if(FMD_FUND.toString().isEmpty()==false) {
						  fscm.setFMD_FUND(new Long(FMD_FUND.toString().trim()));
					  }
				  }
				 
				  if(FMD_CLIENT!=null) {
					  if(FMD_CLIENT.toString().isEmpty()==false)
					  {
						  fscm.setFMD_CLIENT(new Long(FMD_CLIENT.toString().trim())); 
					  }
				  }
				  if(FMD_C_TYPE!=null)
				  {
					  if(FMD_C_TYPE.toString().isEmpty()==false) {
						  fscm.setFMD_C_TYPE(new Long(FMD_C_TYPE.toString().trim()));
					  }
				  }
				  fscm.setFMD_SID(new Long(FMD_SID.toString().trim())); 
				  fscm.setFMD_STOP_LOSS(new Double(FMD_STOP_LOSS.toString().trim()));
				  if(WMS_COMMENTS!=null) {
					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
						}
				
				  fscm.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
				  fscm.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
				  
				  
				  try
					{
					  fscm=fWMS_STOP_LOSSRepository.save(fscm);
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
									logger.info("Both Record and Logs saved for FWMS_STOP_LOSS:"+fscm.getFMD_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved New STOCK LOSS:"+fscm.getFMD_SNO());
							StopLossBean slb=new StopLossBean();
							slb.setMsg("saved");
							slb.setSno(fscm.getFMD_SNO()+"");
							slb.setEffectdate(fscm.getFMD_DATE().getTimeInMillis()+"");
							
							
							slb.setLevel(fscm.getFMD_LEVEL());
							FUND_MSTR fund_mstr=fUND_MSTRRepository.findOne(fscm.getFMD_FUND());
							if(fund_mstr!=null) {
								slb.setFund_id(fund_mstr.getSVC_CODE()+"");
								slb.setFund_name(fund_mstr.getSVC_NAME());
							}
							else {
								slb.setFund_id(fscm.getFMD_FUND()+"");
							}
							
							if(fscm.getFMD_LEVEL().equalsIgnoreCase("client type")) {
								Long clienttype=fscm.getFMD_C_TYPE();
								FUND_MAR_CLIENT_TYPE fUND_MAR_CLIENT_TYPE=fUND_MAR_CLIENT_TYPERespository.findOne(clienttype);
								if(fUND_MAR_CLIENT_TYPE!=null) {
									slb.setClient_type_id(fUND_MAR_CLIENT_TYPE.getFCT_ID()+"");
									slb.setClient_type_name(fUND_MAR_CLIENT_TYPE.getFCT_NAME());
								}else {
									slb.setClient_type_id(null);
									slb.setClient_type_name(null);
								}
							}else if(fscm.getFMD_LEVEL().equalsIgnoreCase("client")) {
								Long client=fscm.getFMD_CLIENT();
								RE_INVESTOR rE_INVESTOR=rE_INVESTORRepository.findOne(client);
								if(rE_INVESTOR!=null) {
									slb.setClient_id(rE_INVESTOR.getRI_INVESTOR_CODE()+"");
									slb.setClient_name(rE_INVESTOR.getRI_INVESTOR_NAME()+"");
								}else {
									slb.setClient_id(null);
									slb.setClient_name(null);
								}
							}
							
//						
							slb.setStop_loss_level(fscm.getFMD_SL_LEVEL());
							if(fscm.getFMD_SL_LEVEL().toString().equalsIgnoreCase("stock"))
							{
								
								FUND_SHARE_COMPANY_MSTR fundscm=fUND_SHARE_COMPANY_MSTRRepository.findOne(fscm.getFMD_SID());
								if(fundscm!=null) {
									slb.setType(fundscm.getSVC_NAME());
								
								}
							}else if(fscm.getFMD_SL_LEVEL().toString().equalsIgnoreCase("asset")) {
								FUND_INSTRUMENT_MSTR fim=stockParameters.getFUND_INSTRUMENT_MSTR(fscm.getFMD_SID());
								if(fim!=null) {
									slb.setType(fim.getSVI_NAME());
								}
							}
							slb.setStop_loss(fscm.getFMD_STOP_LOSS()+"");
							slb.setComments(fscm.getWMS_COMMENTS());
							slb.setStatus(fscm.getWMS_STATUS());
							FUND_USERS user=null;
							if(fscm.getIV_ENTER_UID()!=null) {
								user=getUserName(fscm.getIV_ENTER_UID());
								if(user!=null) {
								slb.setEnteredby(user.getSVU_NAME());
								slb.setEnteredbyuserid(user.getSVU_USER_NAME());
								slb.setEnteredbyuuid(user.getSVC_UID());
								slb.setEntereddate(fscm.getIV_ENTER_DATE().getTimeInMillis()+"");
								}
								else {
									slb.setEnteredby(null);
									slb.setEnteredbyuserid(null);
									slb.setEnteredbyuuid(null);
									slb.setEntereddate(null);
								}
							}else {
								slb.setEnteredby(null);
								slb.setEnteredbyuserid(null);
								slb.setEnteredbyuuid(null);
								slb.setEntereddate(null);
							}
							
							if(fscm.getIV_APPROVE_UID()!=null) {
								user=getUserName(fscm.getIV_APPROVE_UID());
								if(user!=null) {
								slb.setApprovedby(user.getSVU_NAME());
								slb.setApprovedbyuserid( user.getSVU_USER_NAME());
								slb.setApprovedbyuuid(user.getSVC_UID());
								slb.setApproveddate(fscm.getIV_APPROVE_DATE().getTimeInMillis()+"");
								}
								else {
									slb.setApprovedby( null);
									slb.setApprovedbyuserid(  null);
									slb.setApprovedbyuuid( null);
									slb.setApproveddate( null);
								}
							}else {
								slb.setApprovedby( null);
								slb.setApprovedbyuserid(  null);
								slb.setApprovedbyuuid( null);
								slb.setApproveddate( null);
							}
							
							if(fscm.getIV_LAST_UPDATE_UID()!=null) {
								user=getUserName(fscm.getIV_LAST_UPDATE_UID());
								if(user!=null) {
								slb.setModifiedby(user.getSVU_NAME());
								slb.setModifiedbyuserid(user.getSVU_USER_NAME());
								slb.setModifiedbyuuid(user.getSVC_UID());
								slb.setModifieddate(fscm.getIV_LAST_UPDATE_DATE().getTimeInMillis()+"");
								}
								else {
									slb.setModifiedby( null);
									slb.setModifiedbyuserid(null);
									slb.setModifiedbyuuid(null);
									slb.setModifieddate(null);
								}
							}else {
								slb.setModifiedby( null);
								slb.setModifiedbyuserid(null);
								slb.setModifiedbyuuid(null);
								slb.setModifieddate(null);
								}
							
							return new ResponseEntity<StopLossBean>(slb,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("FWMS_STOP_LOSS not saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("FWMS_STOP_LOSS not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
				}catch (Exception e) {
					json.put("msg", "fmd_sno is not correct "+e.getMessage());
					logger.error("FWMS_STOP_LOSS not saved :"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
		 }
	 
	}
	
	@PostMapping("/approvestoploss")
	public ResponseEntity<?> approve(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object FMD_SNO	=requestJson.get("fmd_sno");
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("approvedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		 if(FMD_SNO==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
					  json.put("msg", "Please check Input json, missing some required attributes");
					  logger.error("Please check Input json, missing some required attributes");
				      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
				  }
		 else {
			  FUND_USERS fuser=isValid(createdby.toString());
				if(fuser==null)
				{
						json.put("msg", "Createdby is not valid user");
						logger.error("Createdby is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				try {
				FWMS_STOP_LOSS fscm=fWMS_STOP_LOSSRepository.findOne(new Long(FMD_SNO.toString().trim()));
				 
				 if(WMS_COMMENTS!=null) {
					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
						}
		
				  /*
				   Object FMD_DATE  =requestJson.get("effectdate");
		Object FMD_FUND =requestJson.get("fund_id");
		Object FMD_SL_LEVEL=requestJson.get("stop_loss_level");
		Object FMD_LEVEL=requestJson.get("level");
		Object FMD_CLIENT =requestJson.get("client_id");
		Object FMD_C_TYPE  =requestJson.get("client_type");
		Object FMD_SID  =requestJson.get("type");
		Object FMD_STOP_LOSS =requestJson.get("stop_loss");
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("createdby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
				   */
				 
				
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
					}
				
				  /*
				   * SL level
				   */
				 String FMD_SL_LEVEL=fscm.getFMD_SL_LEVEL();
				  if(FMD_SL_LEVEL!=null) {
					  Long FMD_SID=fscm.getFMD_SID();
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
					  fscm=fWMS_STOP_LOSSRepository.save(fscm);
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
									logger.info("Both Record and Logs saved for FWMS_STOP_LOSS:"+fscm.getFMD_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved New STOCK LOSS:"+fscm.getFMD_SNO());
							StopLossBean slb=new StopLossBean();
							slb.setMsg("saved");
							slb.setSno(fscm.getFMD_SNO()+"");
							slb.setEffectdate(fscm.getFMD_DATE().getTimeInMillis()+"");
							
							
							slb.setLevel(fscm.getFMD_LEVEL());
							FUND_MSTR fund_mstr=fUND_MSTRRepository.findOne(fscm.getFMD_FUND());
							if(fund_mstr!=null) {
								slb.setFund_id(fund_mstr.getSVC_CODE()+"");
								slb.setFund_name(fund_mstr.getSVC_NAME());
							}
							else {
								slb.setFund_id(fscm.getFMD_FUND()+"");
							}
							
							if(fscm.getFMD_LEVEL().equalsIgnoreCase("client type")) {
								Long clienttype=fscm.getFMD_C_TYPE();
								FUND_MAR_CLIENT_TYPE fUND_MAR_CLIENT_TYPE=fUND_MAR_CLIENT_TYPERespository.findOne(clienttype);
								if(fUND_MAR_CLIENT_TYPE!=null) {
									slb.setClient_type_id(fUND_MAR_CLIENT_TYPE.getFCT_ID()+"");
									slb.setClient_type_name(fUND_MAR_CLIENT_TYPE.getFCT_NAME());
								}else {
									slb.setClient_type_id(null);
									slb.setClient_type_name(null);
								}
							}else if(fscm.getFMD_LEVEL().equalsIgnoreCase("client")) {
								Long client=fscm.getFMD_CLIENT();
								RE_INVESTOR rE_INVESTOR=rE_INVESTORRepository.findOne(client);
								if(rE_INVESTOR!=null) {
									slb.setClient_id(rE_INVESTOR.getRI_INVESTOR_CODE()+"");
									slb.setClient_name(rE_INVESTOR.getRI_INVESTOR_NAME()+"");
								}else {
									slb.setClient_id(null);
									slb.setClient_name(null);
								}
							}
							
//						
							slb.setStop_loss_level(fscm.getFMD_SL_LEVEL());
							if(fscm.getFMD_SL_LEVEL().toString().equalsIgnoreCase("stock"))
							{
								
								FUND_SHARE_COMPANY_MSTR fundscm=fUND_SHARE_COMPANY_MSTRRepository.findOne(fscm.getFMD_SID());
								if(fundscm!=null) {
									slb.setType(fundscm.getSVC_NAME());
								
								}
							}else if(fscm.getFMD_SL_LEVEL().toString().equalsIgnoreCase("asset")) {
								FUND_INSTRUMENT_MSTR fim=stockParameters.getFUND_INSTRUMENT_MSTR(fscm.getFMD_SID());
								if(fim!=null) {
									slb.setType(fim.getSVI_NAME());
								}
							}
							slb.setStop_loss(fscm.getFMD_STOP_LOSS()+"");
							slb.setComments(fscm.getWMS_COMMENTS());
							slb.setStatus(fscm.getWMS_STATUS());
							FUND_USERS user=null;
							if(fscm.getIV_ENTER_UID()!=null) {
								user=getUserName(fscm.getIV_ENTER_UID());
								if(user!=null) {
								slb.setEnteredby(user.getSVU_NAME());
								slb.setEnteredbyuserid(user.getSVU_USER_NAME());
								slb.setEnteredbyuuid(user.getSVC_UID());
								slb.setEntereddate(fscm.getIV_ENTER_DATE().getTimeInMillis()+"");
								}
								else {
									slb.setEnteredby(null);
									slb.setEnteredbyuserid(null);
									slb.setEnteredbyuuid(null);
									slb.setEntereddate(null);
								}
							}else {
								slb.setEnteredby(null);
								slb.setEnteredbyuserid(null);
								slb.setEnteredbyuuid(null);
								slb.setEntereddate(null);
							}
							
							if(fscm.getIV_APPROVE_UID()!=null) {
								user=getUserName(fscm.getIV_APPROVE_UID());
								if(user!=null) {
								slb.setApprovedby(user.getSVU_NAME());
								slb.setApprovedbyuserid( user.getSVU_USER_NAME());
								slb.setApprovedbyuuid(user.getSVC_UID());
								slb.setApproveddate(fscm.getIV_APPROVE_DATE().getTimeInMillis()+"");
								}
								else {
									slb.setApprovedby( null);
									slb.setApprovedbyuserid(  null);
									slb.setApprovedbyuuid( null);
									slb.setApproveddate( null);
								}
							}else {
								slb.setApprovedby( null);
								slb.setApprovedbyuserid(  null);
								slb.setApprovedbyuuid( null);
								slb.setApproveddate( null);
							}
							
							if(fscm.getIV_LAST_UPDATE_UID()!=null) {
								user=getUserName(fscm.getIV_LAST_UPDATE_UID());
								if(user!=null) {
								slb.setModifiedby(user.getSVU_NAME());
								slb.setModifiedbyuserid(user.getSVU_USER_NAME());
								slb.setModifiedbyuuid(user.getSVC_UID());
								slb.setModifieddate(fscm.getIV_LAST_UPDATE_DATE().getTimeInMillis()+"");
								}
								else {
									slb.setModifiedby( null);
									slb.setModifiedbyuserid(null);
									slb.setModifiedbyuuid(null);
									slb.setModifieddate(null);
								}
							}else {
								slb.setModifiedby( null);
								slb.setModifiedbyuserid(null);
								slb.setModifiedbyuuid(null);
								slb.setModifieddate(null);
								}
							
							return new ResponseEntity<StopLossBean>(slb,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("FWMS_STOP_LOSS not saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("FWMS_STOP_LOSS not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
				}catch (Exception e) {
					json.put("msg", "fmd_sno is not correct "+e.getMessage());
					logger.error("FWMS_STOP_LOSS not saved :"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
		 }
	 
	}
	
	@PostMapping("/revokestoploss")
	public ResponseEntity<?> revoke(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object FMD_SNO	=requestJson.get("fmd_sno");
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		 if(FMD_SNO==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
					  json.put("msg", "Please check Input json, missing some required attributes");
					  logger.error("Please check Input json, missing some required attributes");
				      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
				  }
		 else {
			  FUND_USERS fuser=isValid(createdby.toString());
				if(fuser==null)
				{
						json.put("msg", "Createdby is not valid user");
						logger.error("Createdby is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				try {
				FWMS_STOP_LOSS fscm=fWMS_STOP_LOSSRepository.findOne(new Long(FMD_SNO.toString().trim()));
				if(WMS_COMMENTS!=null) {
//					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
				  }
				  fscm.setWMS_STATUS("Not Approved");
				  fscm.setIV_APPROVE_UID(null);
				  fscm.setIV_APPROVE_DATE(null);
				  fscm.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
				  fscm.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
				  try
					{
					  fscm=fWMS_STOP_LOSSRepository.save(fscm);
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
									logger.info("Both Record and Logs saved for FWMS_STOP_LOSS:"+fscm.getFMD_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved New STOCK LOSS:"+fscm.getFMD_SNO());
							StopLossBean slb=new StopLossBean();
							slb.setMsg("saved");
							slb.setSno(fscm.getFMD_SNO()+"");
							slb.setEffectdate(fscm.getFMD_DATE().getTimeInMillis()+"");
							
							
							slb.setLevel(fscm.getFMD_LEVEL());
							FUND_MSTR fund_mstr=fUND_MSTRRepository.findOne(fscm.getFMD_FUND());
							if(fund_mstr!=null) {
								slb.setFund_id(fund_mstr.getSVC_CODE()+"");
								slb.setFund_name(fund_mstr.getSVC_NAME());
							}
							else {
								slb.setFund_id(fscm.getFMD_FUND()+"");
							}
							
							if(fscm.getFMD_LEVEL().equalsIgnoreCase("client type")) {
								Long clienttype=fscm.getFMD_C_TYPE();
								FUND_MAR_CLIENT_TYPE fUND_MAR_CLIENT_TYPE=fUND_MAR_CLIENT_TYPERespository.findOne(clienttype);
								if(fUND_MAR_CLIENT_TYPE!=null) {
									slb.setClient_type_id(fUND_MAR_CLIENT_TYPE.getFCT_ID()+"");
									slb.setClient_type_name(fUND_MAR_CLIENT_TYPE.getFCT_NAME());
								}else {
									slb.setClient_type_id(null);
									slb.setClient_type_name(null);
								}
							}else if(fscm.getFMD_LEVEL().equalsIgnoreCase("client")) {
								Long client=fscm.getFMD_CLIENT();
								RE_INVESTOR rE_INVESTOR=rE_INVESTORRepository.findOne(client);
								if(rE_INVESTOR!=null) {
									slb.setClient_id(rE_INVESTOR.getRI_INVESTOR_CODE()+"");
									slb.setClient_name(rE_INVESTOR.getRI_INVESTOR_NAME()+"");
								}else {
									slb.setClient_id(null);
									slb.setClient_name(null);
								}
							}
							
//						
							slb.setStop_loss_level(fscm.getFMD_SL_LEVEL());
							if(fscm.getFMD_SL_LEVEL().toString().equalsIgnoreCase("stock"))
							{
								
								FUND_SHARE_COMPANY_MSTR fundscm=fUND_SHARE_COMPANY_MSTRRepository.findOne(fscm.getFMD_SID());
								if(fundscm!=null) {
									slb.setType(fundscm.getSVC_NAME());
								
								}
							}else if(fscm.getFMD_SL_LEVEL().toString().equalsIgnoreCase("asset")) {
								FUND_INSTRUMENT_MSTR fim=stockParameters.getFUND_INSTRUMENT_MSTR(fscm.getFMD_SID());
								if(fim!=null) {
									slb.setType(fim.getSVI_NAME());
								}
							}
							slb.setStop_loss(fscm.getFMD_STOP_LOSS()+"");
							slb.setComments(fscm.getWMS_COMMENTS());
							slb.setStatus(fscm.getWMS_STATUS());
							FUND_USERS user=null;
							if(fscm.getIV_ENTER_UID()!=null) {
								user=getUserName(fscm.getIV_ENTER_UID());
								if(user!=null) {
								slb.setEnteredby(user.getSVU_NAME());
								slb.setEnteredbyuserid(user.getSVU_USER_NAME());
								slb.setEnteredbyuuid(user.getSVC_UID());
								slb.setEntereddate(fscm.getIV_ENTER_DATE().getTimeInMillis()+"");
								}
								else {
									slb.setEnteredby(null);
									slb.setEnteredbyuserid(null);
									slb.setEnteredbyuuid(null);
									slb.setEntereddate(null);
								}
							}else {
								slb.setEnteredby(null);
								slb.setEnteredbyuserid(null);
								slb.setEnteredbyuuid(null);
								slb.setEntereddate(null);
							}
							
							if(fscm.getIV_APPROVE_UID()!=null) {
								user=getUserName(fscm.getIV_APPROVE_UID());
								if(user!=null) {
								slb.setApprovedby(user.getSVU_NAME());
								slb.setApprovedbyuserid( user.getSVU_USER_NAME());
								slb.setApprovedbyuuid(user.getSVC_UID());
								slb.setApproveddate(fscm.getIV_APPROVE_DATE().getTimeInMillis()+"");
								}
								else {
									slb.setApprovedby( null);
									slb.setApprovedbyuserid(  null);
									slb.setApprovedbyuuid( null);
									slb.setApproveddate( null);
								}
							}else {
								slb.setApprovedby( null);
								slb.setApprovedbyuserid(  null);
								slb.setApprovedbyuuid( null);
								slb.setApproveddate( null);
							}
							
							if(fscm.getIV_LAST_UPDATE_UID()!=null) {
								user=getUserName(fscm.getIV_LAST_UPDATE_UID());
								if(user!=null) {
								slb.setModifiedby(user.getSVU_NAME());
								slb.setModifiedbyuserid(user.getSVU_USER_NAME());
								slb.setModifiedbyuuid(user.getSVC_UID());
								slb.setModifieddate(fscm.getIV_LAST_UPDATE_DATE().getTimeInMillis()+"");
								}
								else {
									slb.setModifiedby( null);
									slb.setModifiedbyuserid(null);
									slb.setModifiedbyuuid(null);
									slb.setModifieddate(null);
								}
							}else {
								slb.setModifiedby( null);
								slb.setModifiedbyuserid(null);
								slb.setModifiedbyuuid(null);
								slb.setModifieddate(null);
								}
							
							return new ResponseEntity<StopLossBean>(slb,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("FWMS_STOP_LOSS not saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("FWMS_STOP_LOSS not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
				}catch (Exception e) {
					json.put("msg", "fmd_sno is not correct "+e.getMessage());
					logger.error("FWMS_STOP_LOSS not saved :"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
		 }
	 
	}
	
	
	@PostMapping("/deletestoploss")
	public ResponseEntity<Map<String,Object>> delete(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object FMD_SNO	=requestJson.get("fmd_sno");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		 if(FMD_SNO==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
					  json.put("msg", "Please check Input json, missing some required attributes");
					  logger.error("Please check Input json, missing some required attributes");
				      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
				  }
		 else {
			  FUND_USERS fuser=isValid(createdby.toString());
				if(fuser==null)
				{
						json.put("msg", "Createdby is not valid user");
						logger.error("Createdby is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				try {
				FWMS_STOP_LOSS fscm=fWMS_STOP_LOSSRepository.findOne(new Long(FMD_SNO.toString().trim()));
			
				  try
					{
					  fWMS_STOP_LOSSRepository.delete(fscm);
					
							    json.put("msg", "Deleted");				
								FUND_USER_LOG ful=new FUND_USER_LOG();
								ful.setSVC_UID(fuser.getSVC_UID());
								ful.setSVL_USERID(fuser.getSVU_NAME());
								ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
								ful.setSVL_TTYPE("DELETE");
								ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
								ful.setSVL_DATE(Calendar.getInstance());
								ful=fUND_USER_LOGRepository.save(ful);
								if(ful!=null) {
									logger.info("Both Record and Logs saved for FWMS_STOP_LOSS:"+fscm.getFMD_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
								logger.error("FWMS_STOP_LOSS DELETE ");
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
						
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("FWMS_STOP_LOSS not DELETE :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
				}catch (Exception e) {
					json.put("msg", "fmd_sno is not correct "+e.getMessage());
					logger.error("FWMS_STOP_LOSS not DELETE :"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
		 }
	 
	}
	
	
	
	
	@GetMapping("/stoplosssearch")
	public ResponseEntity<?> getAllStopLosspage(Pageable page,
			@RequestParam(required=false,value="action")String action,
			@RequestParam(required=false,value="paramSearch")String paramString,
			@RequestParam(required=false,value="level")String level,
			@RequestParam(required=false,value="sl_level")String sl_level
			){
		List<Long> fundids=new ArrayList<>();
		if(paramString!=null) {
			if(paramString.isEmpty()==false) {
				List<FUND_MSTR> funds=fUND_MSTRRepository.findAllFUND_MSTR_LIST(paramString);
				if(funds!=null) {
				 funds.forEach(fund->{
					 fundids.add(fund.getSVC_CODE());
				 });
				}
			}
		}
		
		List<FWMS_STOP_LOSS> allFUND_STOP_LOSS_MSTR=null;
		if(action==null) {
			if(fundids.size()>0) {
				if(level!=null&&sl_level!=null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllFWMS_STOP_LOSS_LEVEL_SL_LEVEL_SEARCH(level,sl_level,fundids);
				}
				else if(level==null&&sl_level!=null)
				{
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllFWMS_STOP_LOSS_SL_LEVEL_SEARCH(sl_level,fundids);
				}else if(level!=null&&sl_level==null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllFWMS_STOP_LOSS_LEVEL_SEARCH(level,fundids);
				}else if(level==null&&sl_level==null){
				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllFWMS_STOP_LOSS_SEARCH(fundids);
				}
			}else if(paramString==null){
				if(level!=null&&sl_level!=null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL(level,sl_level);
				}
				else if(level==null&&sl_level!=null)
				{
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL(sl_level);
				}else if(level!=null&&sl_level==null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL(level);
				}else if(level==null&&sl_level==null){
				    allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllStopLoss();
				}
			}
			else if(paramString.isEmpty()==true){
				if(level!=null&&sl_level!=null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL(level,sl_level);
				}
				else if(level==null&&sl_level!=null)
				{
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL(sl_level);
				}else if(level!=null&&sl_level==null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL(level);
				}else if(level==null&&sl_level==null){
				    allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllStopLoss();
				}
			}
			else {
				 Map<String,Object> jsonArray=new HashMap<>();
				 jsonArray.put("msg", "Not found");
				 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
			}
			}
		else if(action.toString().trim().equalsIgnoreCase("ALL")) {
			if(fundids.size()>0) {
				if(level!=null&&sl_level!=null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllFWMS_STOP_LOSS_LEVEL_SL_LEVEL_SEARCH(level,sl_level,fundids);
				}
				else if(level==null&&sl_level!=null)
				{
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllFWMS_STOP_LOSS_SL_LEVEL_SEARCH(sl_level,fundids);
				}else if(level!=null&&sl_level==null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllFWMS_STOP_LOSS_LEVEL_SEARCH(level,fundids);
				}else if(level==null&&sl_level==null){
				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllFWMS_STOP_LOSS_SEARCH(fundids);
				}
			}else if(paramString==null){
				if(level!=null&&sl_level!=null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL(level,sl_level);
				}
				else if(level==null&&sl_level!=null)
				{
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL(sl_level);
				}else if(level!=null&&sl_level==null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL(level);
				}else if(level==null&&sl_level==null){
				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllStopLoss();
				}
			}
			else if(paramString.isEmpty()==true) {
				if(level!=null&&sl_level!=null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL(level,sl_level);
				}
				else if(level==null&&sl_level!=null)
				{
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL(sl_level);
				}else if(level!=null&&sl_level==null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL(level);
				}else if(level==null&&sl_level==null){
				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllStopLoss();
				}
			}
			else {
				 Map<String,Object> jsonArray=new HashMap<>();
				 jsonArray.put("msg", "Not found");
				 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
			}
			}
	  else if(action.toString().trim().equalsIgnoreCase("APPROVED")) {
		  if(fundids.size()>0) {
				if(level!=null&&sl_level!=null) {
				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllAPPROVEDFWMS_STOP_LOSS_LEVEL_SL_LEVEL_SEARCH(level,sl_level,fundids);
				}
				else if(level==null&&sl_level!=null)
				{
				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllAPPROVEDFWMS_STOP_LOSS_SL_LEVEL_SEARCH(sl_level,fundids);
				}else if(level!=null&&sl_level==null) {
				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllAPPROVEDFWMS_STOP_LOSS_LEVEL_SEARCH(level,fundids);
				}else if(level==null&&sl_level==null){
				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllAPPROVEDFWMS_STOP_LOSS_SEARCH(fundids);
				}
			}else if(paramString==null){
				if(level!=null&&sl_level!=null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL_Approved(level,sl_level);
				}
				else if(level==null&&sl_level!=null)
				{
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL_Approved(sl_level);
				}else if(level!=null&&sl_level==null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_Approved(level);
				}else if(level==null&&sl_level==null){
				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllAPPROVEDFWMS_STOP_LOSS();
				}
			}
			else if(paramString.isEmpty()==true) {
				if(level!=null&&sl_level!=null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL_Approved(level,sl_level);
				}
				else if(level==null&&sl_level!=null)
				{
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL_Approved(sl_level);
				}else if(level!=null&&sl_level==null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_Approved(level);
				}else if(level==null&&sl_level==null){
				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllAPPROVEDFWMS_STOP_LOSS();
				}
			}
			else {
				 Map<String,Object> jsonArray=new HashMap<>();
				 jsonArray.put("msg", "Not found");
				 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
			}
	  		}
	 else if(action.toString().trim().equalsIgnoreCase("UNAPPROVED")) {
		 if(fundids.size()>0) {
				if(level!=null&&sl_level!=null) {
				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllUNAPPROVEDFWMS_STOP_LOSS_LEVEL_SL_LEVEL_SEARCH(level,sl_level,fundids);
				}
				else if(level==null&&sl_level!=null)
				{
				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllUNAPPROVEDFWMS_STOP_LOSS_SL_LEVEL_SEARCH(sl_level,fundids);
				}else if(level!=null&&sl_level==null) {
				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllUNAPPROVEDFWMS_STOP_LOSS_LEVEL_SEARCH(level,fundids);
				}else if(level==null&&sl_level==null){
				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllUNAPPROVEDFWMS_STOP_LOSS_SEARCH(fundids);
				}
			}else if(paramString==null){
				if(level!=null&&sl_level!=null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL_UNApproved(level,sl_level);
				}
				else if(level==null&&sl_level!=null)
				{
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL_UNApproved(sl_level);
				}else if(level!=null&&sl_level==null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_UNApproved(level);
				}else if(level==null&&sl_level==null){
				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllUNAPPROVEDFWMS_STOP_LOSS();
				}
				
			}
			else if(paramString.isEmpty()==true) {
				if(level!=null&&sl_level!=null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL_UNApproved(level,sl_level);
				}
				else if(level==null&&sl_level!=null)
				{
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL_UNApproved(sl_level);
				}else if(level!=null&&sl_level==null) {
					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_UNApproved(level);
				}else if(level==null&&sl_level==null){
				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllUNAPPROVEDFWMS_STOP_LOSS();
				}
			}
			else {
				 Map<String,Object> jsonArray=new HashMap<>();
				 jsonArray.put("msg", "Not found");
				 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
			}
			}
		List<StopLossBean> stoplossArray=new ArrayList<StopLossBean>();
	   if(allFUND_STOP_LOSS_MSTR!=null) {
		allFUND_STOP_LOSS_MSTR.forEach(conut ->{
			StopLossBean slb=new StopLossBean();
			
				slb.setSno(conut.getFMD_SNO()+"");
				slb.setEffectdate(conut.getFMD_DATE().getTimeInMillis()+"");
				
				
				slb.setLevel(conut.getFMD_LEVEL());
				FUND_MSTR fund_mstr=fUND_MSTRRepository.findOne(conut.getFMD_FUND());
				if(fund_mstr!=null) {
					slb.setFund_id(fund_mstr.getSVC_CODE()+"");
					slb.setFund_name(fund_mstr.getSVC_NAME());
				}
				else {
					slb.setFund_id(conut.getFMD_FUND()+"");
				}
				
				if(conut.getFMD_LEVEL().equalsIgnoreCase("client type")) {
					Long clienttype=conut.getFMD_C_TYPE();
					FUND_MAR_CLIENT_TYPE fUND_MAR_CLIENT_TYPE=fUND_MAR_CLIENT_TYPERespository.findOne(clienttype);
					if(fUND_MAR_CLIENT_TYPE!=null) {
						slb.setClient_type_id(fUND_MAR_CLIENT_TYPE.getFCT_ID()+"");
						slb.setClient_type_name(fUND_MAR_CLIENT_TYPE.getFCT_NAME());
					}else {
						slb.setClient_type_id(null);
						slb.setClient_type_name(null);
					}
				}else if(conut.getFMD_LEVEL().equalsIgnoreCase("client")) {
					Long client=conut.getFMD_CLIENT();
					RE_INVESTOR rE_INVESTOR=rE_INVESTORRepository.findOne(client);
					if(rE_INVESTOR!=null) {
						slb.setClient_id(rE_INVESTOR.getRI_INVESTOR_CODE()+"");
						slb.setClient_name(rE_INVESTOR.getRI_INVESTOR_NAME()+"");
					}else {
						slb.setClient_id(null);
						slb.setClient_name(null);
					}
				}
				
//			
				slb.setStop_loss_level(conut.getFMD_SL_LEVEL());
				if(conut.getFMD_SL_LEVEL().toString().equalsIgnoreCase("stock"))
				{
					
					FUND_SHARE_COMPANY_MSTR fundscm=fUND_SHARE_COMPANY_MSTRRepository.findOne(conut.getFMD_SID());
					if(fundscm!=null) {
						slb.setType(fundscm.getSVC_NAME());
					
					}
				}else if(conut.getFMD_SL_LEVEL().toString().equalsIgnoreCase("asset")) {
					FUND_INSTRUMENT_MSTR fim=stockParameters.getFUND_INSTRUMENT_MSTR(conut.getFMD_SID());
					if(fim!=null) {
						slb.setType(fim.getSVI_NAME());
					}
				}
				slb.setStop_loss(conut.getFMD_STOP_LOSS()+"");
				slb.setComments(conut.getWMS_COMMENTS());
				slb.setStatus(conut.getWMS_STATUS());
				FUND_USERS user=null;
				if(conut.getIV_ENTER_UID()!=null) {
					user=getUserName(conut.getIV_ENTER_UID());
					if(user!=null) {
					slb.setEnteredby(user.getSVU_NAME());
					slb.setEnteredbyuserid(user.getSVU_USER_NAME());
					slb.setEnteredbyuuid(user.getSVC_UID());
					slb.setEntereddate(conut.getIV_ENTER_DATE().getTimeInMillis()+"");
					}
					else {
						slb.setEnteredby(null);
						slb.setEnteredbyuserid(null);
						slb.setEnteredbyuuid(null);
						slb.setEntereddate(null);
					}
				}else {
					slb.setEnteredby(null);
					slb.setEnteredbyuserid(null);
					slb.setEnteredbyuuid(null);
					slb.setEntereddate(null);
				}
				
				if(conut.getIV_APPROVE_UID()!=null) {
					user=getUserName(conut.getIV_APPROVE_UID());
					if(user!=null) {
					slb.setApprovedby(user.getSVU_NAME());
					slb.setApprovedbyuserid( user.getSVU_USER_NAME());
					slb.setApprovedbyuuid(user.getSVC_UID());
					slb.setApproveddate(conut.getIV_APPROVE_DATE().getTimeInMillis()+"");
					}
					else {
						slb.setApprovedby( null);
						slb.setApprovedbyuserid(  null);
						slb.setApprovedbyuuid( null);
						slb.setApproveddate( null);
					}
				}else {
					slb.setApprovedby( null);
					slb.setApprovedbyuserid(  null);
					slb.setApprovedbyuuid( null);
					slb.setApproveddate( null);
				}
				
				if(conut.getIV_LAST_UPDATE_UID()!=null) {
					user=getUserName(conut.getIV_LAST_UPDATE_UID());
					if(user!=null) {
					slb.setModifiedby(user.getSVU_NAME());
					slb.setModifiedbyuserid(user.getSVU_USER_NAME());
					slb.setModifiedbyuuid(user.getSVC_UID());
					slb.setModifieddate(conut.getIV_LAST_UPDATE_DATE().getTimeInMillis()+"");
					}
					else {
						slb.setModifiedby( null);
						slb.setModifiedbyuserid(null);
						slb.setModifiedbyuuid(null);
						slb.setModifieddate(null);
					}
				}else {
					slb.setModifiedby( null);
					slb.setModifiedbyuserid(null);
					slb.setModifiedbyuuid(null);
					slb.setModifieddate(null);
					}
				
			stoplossArray.add(slb);
		});
		Collections.sort(stoplossArray);
		//Data reading for for Paging
		int start = page.getOffset();
		int end = (start + page.getPageSize()) > stoplossArray.size() ? stoplossArray.size()
				: (start + page.getPageSize());
		Page<StopLossBean> jsonArray = new PageImpl<StopLossBean>(stoplossArray.subList(start, end), page,
				stoplossArray.size());		
		return new ResponseEntity<Page<StopLossBean>>(jsonArray,HttpStatus.OK);
		
		//Old
//	Page<FWMS_STOP_LOSS> allFUND_STOP_LOSS_MSTR=null;
//		if(action==null) {
//			if(fundids.size()>0) {
//				if(level!=null&&sl_level!=null) {
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllFWMS_STOP_LOSS_LEVEL_SL_LEVEL_SEARCH(level,sl_level,fundids,page);
//				}
//				else if(sl_level!=null)
//				{
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllFWMS_STOP_LOSS_SL_LEVEL_SEARCH(sl_level,fundids,page);
//				}else if(level!=null) {
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllFWMS_STOP_LOSS_LEVEL_SEARCH(level,fundids,page);
//				}else {
//				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllFWMS_STOP_LOSS_SEARCH(fundids, page);
//				}
//			}else if(paramString==null){
//				if(level!=null&&sl_level!=null) {
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL(level,sl_level,page);
//				}
//				else if(level==null&&sl_level!=null)
//				{
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL(sl_level,page);
//				}else if(level!=null&&sl_level==null) {
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL(level,page);
//				}else if(level==null&&sl_level==null){
//				    allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll(page);
//				}
//			}else {
//				 Map<String,Object> jsonArray=new HashMap<>();
//				 jsonArray.put("msg", "Not found");
//				 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
//			}
//			}
//		else if(action.toString().trim().equalsIgnoreCase("ALL")) {
//			if(fundids.size()>0) {
//				
//				if(level!=null&&sl_level!=null) {
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllFWMS_STOP_LOSS_LEVEL_SL_LEVEL_SEARCH(level,sl_level,fundids,page);
//				}
//				else if(sl_level!=null)
//				{
//					
//						allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllFWMS_STOP_LOSS_SL_LEVEL_SEARCH(sl_level,fundids,page);
//								
//				}else if(level!=null) {
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllFWMS_STOP_LOSS_LEVEL_SEARCH(level,fundids,page);
//				}else{
//				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllFWMS_STOP_LOSS_SEARCH(fundids, page);
//				}
//			}else if(paramString==null){
//				if(level!=null&&sl_level!=null) {
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL(level,sl_level,page);
//				}
//				else if(sl_level!=null)
//				{
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL(sl_level,page);
//				}else if(level!=null) {
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL(level,page);
//				}else{
//				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll(page);
//				}
//			}else {
//				 Map<String,Object> jsonArray=new HashMap<>();
//				 jsonArray.put("msg", "Not found");
//				 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
//			}
//			}
//	  else if(action.toString().trim().equalsIgnoreCase("APPROVED")) {
//		  if(fundids.size()>0) {
//				if(level!=null&&sl_level!=null) {
//				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllAPPROVEDFWMS_STOP_LOSS_LEVEL_SL_LEVEL_SEARCH(level,sl_level,fundids,page);
//				}
//				else if(sl_level!=null)
//				{
//				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllAPPROVEDFWMS_STOP_LOSS_SL_LEVEL_SEARCH(sl_level,fundids,page);
//				}else if(level!=null) {
//				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllAPPROVEDFWMS_STOP_LOSS_LEVEL_SEARCH(level,fundids,page);
//				}else{
//				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllAPPROVEDFWMS_STOP_LOSS_SEARCH(fundids, page);
//				}
//			}else if(paramString==null){
//				if(level!=null&&sl_level!=null) {
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL_Approved(level,sl_level,page);
//				}
//				else if(sl_level!=null)
//				{
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL_Approved(sl_level,page);
//				}else if(level!=null) {
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_Approved(level,page);
//				}else{
//				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllAPPROVEDFWMS_STOP_LOSS(page);
//				}
//			}else {
//				 Map<String,Object> jsonArray=new HashMap<>();
//				 jsonArray.put("msg", "Not found");
//				 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
//			}
//	  		}
//	 else if(action.toString().trim().equalsIgnoreCase("UNAPPROVED")) {
//		 if(fundids.size()>0) {
//				if(level!=null&&sl_level!=null) {
//				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllAPPROVEDFWMS_STOP_LOSS_LEVEL_SL_LEVEL_SEARCH(level,sl_level,fundids,page);
//				}
//				else if(sl_level!=null)
//				{
//				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllUNAPPROVEDFWMS_STOP_LOSS_SL_LEVEL_SEARCH(sl_level,fundids,page);
//				}else if(level!=null) {
//				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllUNAPPROVEDFWMS_STOP_LOSS_LEVEL_SEARCH(level,fundids,page);
//				}else{
//				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllUNAPPROVEDFWMS_STOP_LOSS_SEARCH(fundids, page);
//				}
//			}else if(paramString==null){
//				if(level!=null&&sl_level!=null) {
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL_UNApproved(level,sl_level,page);
//				}
//				else if(sl_level!=null)
//				{
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_SL_LEVEL_UNApproved(sl_level,page);
//				}else if(level!=null) {
//					allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAll_STOP_LOSS_ON_LEVEL_UNApproved(level,page);
//				}else{
//				allFUND_STOP_LOSS_MSTR=fWMS_STOP_LOSSRepository.findAllUNAPPROVEDFWMS_STOP_LOSS(page);
//				}
//			}else {
//				 Map<String,Object> jsonArray=new HashMap<>();
//				 jsonArray.put("msg", "Not found");
//				 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
//			}
//			}
//	if(allFUND_STOP_LOSS_MSTR!=null) {
//		Page<Map<String,Object>> jsonArray=allFUND_STOP_LOSS_MSTR.map(new Converter<FWMS_STOP_LOSS, Map<String,Object>>() {
//			@Override
//			public Map<String, Object> convert(FWMS_STOP_LOSS conut) {
//				Map<String,Object> json=new HashMap<String,Object>();
//                json.put("sno", conut.getFMD_SNO());
//				json.put("effectdate", conut.getFMD_DATE());
//				FUND_MSTR fund_mstr=fUND_MSTRRepository.findOne(conut.getFMD_FUND());
//				if(fund_mstr!=null) {
//					json.put("fund_id",fund_mstr.getSVC_CODE());
//					json.put("fund_name", fund_mstr.getSVC_NAME());
//				}
//				else {
//					json.put("fund_id",conut.getFMD_FUND());
//				}
//				json.put("stop_loss_level",conut.getFMD_SL_LEVEL());
//				json.put("level",conut.getFMD_LEVEL());
//				json.put("client_id",conut.getFMD_CLIENT());
//				json.put("client_type",conut.getFMD_C_TYPE());
//				//json.put("type",conut.getFMD_SID());
//				if(conut.getFMD_SL_LEVEL().toString().equalsIgnoreCase("stock"))
//				{
//					
//					FUND_SHARE_COMPANY_MSTR fundscm=fUND_SHARE_COMPANY_MSTRRepository.findOne(conut.getFMD_SID());
//					if(fundscm!=null) {
//						json.put("type",fundscm.getSVC_NAME());
//					
//					}
//				}else if(conut.getFMD_SL_LEVEL().toString().equalsIgnoreCase("asset")) {
//					FUND_INSTRUMENT_MSTR fim=stockParameters.getFUND_INSTRUMENT_MSTR(conut.getFMD_SID());
//					if(fim!=null) {
//						json.put("type",fim.getSVI_NAME());
//					}
//				}
//				json.put("stop_loss",conut.getFMD_STOP_LOSS());
//				json.put("comments", conut.getWMS_COMMENTS());
//				json.put("status", conut.getWMS_STATUS());
//				FUND_USERS user=null;
//				if(conut.getIV_ENTER_UID()!=null) {
//					user=getUserName(conut.getIV_ENTER_UID());
//					if(user!=null) {
//					json.put("enteredby", user.getSVU_NAME());
//					json.put("enteredbyuserid", user.getSVU_USER_NAME());
//					json.put("enteredbyuuid", user.getSVC_UID());
//					json.put("entereddate", conut.getIV_ENTER_DATE());
//					}
//					else {
//						json.put("enteredby", null);
//						json.put("enteredbyuserid", null);
//						json.put("enteredbyuuid", null);
//						json.put("entereddate", null);
//					}
//				}else {
//					json.put("enteredby", null);
//					json.put("enteredbyuserid", null);
//					json.put("enteredbyuuid", null);
//					json.put("entereddate", null);
//				}
//				
//				if(conut.getIV_APPROVE_UID()!=null) {
//					user=getUserName(conut.getIV_APPROVE_UID());
//					if(user!=null) {
//					json.put("approvedby", user.getSVU_NAME());
//					json.put("approvedbyuserid", user.getSVU_USER_NAME());
//					json.put("approvedbyuuid", user.getSVC_UID());
//					json.put("approveddate", conut.getIV_APPROVE_DATE());
//					}
//					else {
//						json.put("approvedby", null);
//						json.put("approvedbyuserid", null);
//						json.put("approvedbyuuid", null);
//						json.put("approveddate", null);
//					}
//				}else {
//					json.put("approvedby", null);
//					json.put("approvedbyuserid", null);
//					json.put("approvedbyuuid", null);
//					json.put("approveddate", null);
//				}
//				
//				if(conut.getIV_LAST_UPDATE_UID()!=null) {
//					user=getUserName(conut.getIV_LAST_UPDATE_UID());
//					if(user!=null) {
//					json.put("modifiedby", user.getSVU_NAME());
//					json.put("modifiedbyuserid", user.getSVU_USER_NAME());
//					json.put("modifiedbyuuid", user.getSVC_UID());
//					json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
//					}
//					else {
//						json.put("modifiedby", null);
//						json.put("modifiedbyuserid", null);
//						json.put("modifiedbyuuid", null);
//						json.put("modifieddate", null);
//					}
//				}else {
//					json.put("modifiedby", null);
//					json.put("modifiedbyuserid", null);
//					json.put("modifiedbyuuid", null);
//					json.put("modifieddate", null);
//				}
//				
//				return json;
//				
//				
//			}
//			
//		});
//		
//		
//		return new ResponseEntity<Page<Map<String,Object>>>(jsonArray,HttpStatus.OK);
//		
//		
	}
	else {
		 Map<String,Object> jsonArray=new HashMap<>();
		 jsonArray.put("msg", "Not found");
		 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
	}
		
}
	
	@PostMapping("/allstoploss")
	public ResponseEntity<?> getAllStopLoss(RequestEntity<Map<String,Object>>requestData){
			List<Map<String,Object>> jsonArray=new ArrayList<Map<String,Object>>();
			Map<String,Object> requestJson=requestData.getBody();
			  Object action =requestJson.get("action");
			   if(action==null) {
				   fWMS_STOP_LOSSRepository.findAll().forEach(conut->{
						Map<String,Object> json=new HashMap<String,Object>();
			            json.put("sno", conut.getFMD_SNO());
						json.put("effectdate", conut.getFMD_DATE());
						FUND_MSTR fund_mstr=fUND_MSTRRepository.findOne(conut.getFMD_FUND());
						if(fund_mstr!=null) {
							json.put("fund_id",fund_mstr.getSVC_CODE());
							json.put("fund_name", fund_mstr.getSVC_NAME());
						}
						else {
							json.put("fund_id",conut.getFMD_FUND());
						}
						json.put("stop_loss_level",conut.getFMD_SL_LEVEL());
						json.put("level",conut.getFMD_LEVEL());
						json.put("client_id",conut.getFMD_CLIENT());
						json.put("client_type",conut.getFMD_C_TYPE());
						//json.put("type",conut.getFMD_SID());
						json.put("type",conut.getFMD_SID());
						if(conut.getFMD_SL_LEVEL().toString().equalsIgnoreCase("stock"))
						{
							
							FUND_SHARE_COMPANY_MSTR fundscm=fUND_SHARE_COMPANY_MSTRRepository.findOne(conut.getFMD_SID());
							if(fundscm!=null) {
								json.put("type",fundscm.getSVC_NAME());
							
							}
						}else if(conut.getFMD_SL_LEVEL().toString().equalsIgnoreCase("asset")) {
							FUND_INSTRUMENT_MSTR fim=stockParameters.getFUND_INSTRUMENT_MSTR(conut.getFMD_SID());
							if(fim!=null) {
								json.put("type",fim.getSVI_NAME());
							}
						}
						json.put("stop_loss",conut.getFMD_STOP_LOSS());
						json.put("comments", conut.getWMS_COMMENTS());
						json.put("status", conut.getWMS_STATUS());
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getIV_ENTER_UID());
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
					});;
					
			   }
			   else if(action.toString().trim().equalsIgnoreCase("ALL")) {
				   fWMS_STOP_LOSSRepository.findAll().forEach(conut->{
						Map<String,Object> json=new HashMap<String,Object>();
			            json.put("sno", conut.getFMD_SNO());
						json.put("effectdate", conut.getFMD_DATE());
						FUND_MSTR fund_mstr=fUND_MSTRRepository.findOne(conut.getFMD_FUND());
						if(fund_mstr!=null) {
							json.put("fund_id",fund_mstr.getSVC_CODE());
							json.put("fund_name", fund_mstr.getSVC_NAME());
						}
						else {
							json.put("fund_id",conut.getFMD_FUND());
						}
						json.put("stop_loss_level",conut.getFMD_SL_LEVEL());
						json.put("level",conut.getFMD_LEVEL());
						json.put("client_id",conut.getFMD_CLIENT());
						json.put("client_type",conut.getFMD_C_TYPE());
						//json.put("type",conut.getFMD_SID());
						json.put("type",conut.getFMD_SID());
						if(conut.getFMD_SL_LEVEL().toString().equalsIgnoreCase("stock"))
						{
							
							FUND_SHARE_COMPANY_MSTR fundscm=fUND_SHARE_COMPANY_MSTRRepository.findOne(conut.getFMD_SID());
							if(fundscm!=null) {
								json.put("type",fundscm.getSVC_NAME());
							
							}
						}else if(conut.getFMD_SL_LEVEL().toString().equalsIgnoreCase("asset")) {
							FUND_INSTRUMENT_MSTR fim=stockParameters.getFUND_INSTRUMENT_MSTR(conut.getFMD_SID());
							if(fim!=null) {
								json.put("type",fim.getSVI_NAME());
							}
						}
						json.put("stop_loss",conut.getFMD_STOP_LOSS());
						
						json.put("comments", conut.getWMS_COMMENTS());
						json.put("status", conut.getWMS_STATUS());
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getIV_ENTER_UID());
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
					});;
					
				   
			   }else if(action.toString().trim().equalsIgnoreCase("APPROVED")) {
				   fWMS_STOP_LOSSRepository.findAllAPPROVEDFWMS_STOP_LOSS().forEach(conut->{
						Map<String,Object> json=new HashMap<String,Object>();
			            json.put("sno", conut.getFMD_SNO());
						json.put("effectdate", conut.getFMD_DATE());
						FUND_MSTR fund_mstr=fUND_MSTRRepository.findOne(conut.getFMD_FUND());
						if(fund_mstr!=null) {
							json.put("fund_id",fund_mstr.getSVC_CODE());
							json.put("fund_name", fund_mstr.getSVC_NAME());
						}
						else {
							json.put("fund_id",conut.getFMD_FUND());
						}
						json.put("stop_loss_level",conut.getFMD_SL_LEVEL());
						json.put("level",conut.getFMD_LEVEL());
						json.put("client_id",conut.getFMD_CLIENT());
						json.put("client_type",conut.getFMD_C_TYPE());
						//json.put("type",conut.getFMD_SID());
						json.put("type",conut.getFMD_SID());
						if(conut.getFMD_SL_LEVEL().toString().equalsIgnoreCase("stock"))
						{
							
							FUND_SHARE_COMPANY_MSTR fundscm=fUND_SHARE_COMPANY_MSTRRepository.findOne(conut.getFMD_SID());
							if(fundscm!=null) {
								json.put("type",fundscm.getSVC_NAME());
							
							}
						}else if(conut.getFMD_SL_LEVEL().toString().equalsIgnoreCase("asset")) {
							FUND_INSTRUMENT_MSTR fim=stockParameters.getFUND_INSTRUMENT_MSTR(conut.getFMD_SID());
							if(fim!=null) {
								json.put("type",fim.getSVI_NAME());
							}
						}
						json.put("stop_loss",conut.getFMD_STOP_LOSS());
						
						json.put("comments", conut.getWMS_COMMENTS());
						json.put("status", conut.getWMS_STATUS());
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getIV_ENTER_UID());
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
					});;
					
				   
			   }else if(action.toString().trim().equalsIgnoreCase("UNAPPROVED")) {
				   fWMS_STOP_LOSSRepository.findAllUNAPPROVEDFWMS_STOP_LOSS().forEach(conut->{
						Map<String,Object> json=new HashMap<String,Object>();
			            json.put("sno", conut.getFMD_SNO());
						json.put("effectdate", conut.getFMD_DATE());
						FUND_MSTR fund_mstr=fUND_MSTRRepository.findOne(conut.getFMD_FUND());
						if(fund_mstr!=null) {
							json.put("fund_id",fund_mstr.getSVC_CODE());
							json.put("fund_name", fund_mstr.getSVC_NAME());
						}
						else {
							json.put("fund_id",conut.getFMD_FUND());
						}
						json.put("stop_loss_level",conut.getFMD_SL_LEVEL());
						json.put("level",conut.getFMD_LEVEL());
						json.put("client_id",conut.getFMD_CLIENT());
						json.put("client_type",conut.getFMD_C_TYPE());
						//json.put("type",conut.getFMD_SID());
						json.put("type",conut.getFMD_SID());
						if(conut.getFMD_SL_LEVEL().toString().equalsIgnoreCase("stock"))
						{
							
							FUND_SHARE_COMPANY_MSTR fundscm=fUND_SHARE_COMPANY_MSTRRepository.findOne(conut.getFMD_SID());
							if(fundscm!=null) {
								json.put("type",fundscm.getSVC_NAME());
							
							}
						}else if(conut.getFMD_SL_LEVEL().toString().equalsIgnoreCase("asset")) {
							FUND_INSTRUMENT_MSTR fim=stockParameters.getFUND_INSTRUMENT_MSTR(conut.getFMD_SID());
							if(fim!=null) {
								json.put("type",fim.getSVI_NAME());
							}
						}
						json.put("stop_loss",conut.getFMD_STOP_LOSS());
						
						json.put("comments", conut.getWMS_COMMENTS());
						json.put("status", conut.getWMS_STATUS());
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getIV_ENTER_UID());
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
					});;
					
				   
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


