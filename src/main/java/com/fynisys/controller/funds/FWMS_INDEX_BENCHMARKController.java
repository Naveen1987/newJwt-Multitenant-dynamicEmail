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

import com.fynisys.controller.funds.beans.BenchMarkBean;
import com.fynisys.controller.funds.beans.MarginBean;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.FUND_USER_LOG;
import com.fynisys.model.clienttype.FUND_MAR_CLIENT_TYPE;
import com.fynisys.model.crm.RE_INVESTOR;
import com.fynisys.model.funds.FUND_MSTR;
import com.fynisys.model.funds.FWMS_INDEX_BENCHMARK;
import com.fynisys.model.parameters.FWMS_INDEX;
import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.clienttype.FUND_MAR_CLIENT_TYPERespository;
import com.fynisys.repository.crm.RE_INVESTORRepository;
import com.fynisys.repository.funds.FUND_MSTRRepository;
import com.fynisys.repository.funds.FWMS_INDEX_BENCHMARKRepository;
import com.fynisys.utilities.StockParameters;

/*
FMD_SNO	NUMBER (10)	PRIMARY KEY	NOT NULL	Serial No Auto
FMD_DATE	DATE			Effect Date
FMD_C_TYPE	NUMBER (15)			Client Type
FMD_INDEX_BM	NUMBER (6)			Index Name Below fields are standard
IV_ENTER_UID	VARCHAR2 (119)			
IV_ENTER_DATE	DATE			
IV_APPROVE_UID	VARCHAR2 (119)			
IV_APPROVE_DATE	DATE			
IV_LAST_UPDATE_UID	VARCHAR2 (119)			
IV_LAST_UPDATE_DATE	DATE			
FMD_LEVEL	VARCHAR2(30)			Level(Client Type Client Fund)
FMD_FUND	NUMBER(10)			FUND information (fund name)
FMD_CLIENT	NUMBER(10)			Client name
WMS_COMMENTS	VARCHAR2(300)			comment
WMS_STATUS	VARCHAR2(20)			Status
 *
 *
 */

@RestController
public class FWMS_INDEX_BENCHMARKController {
	@Autowired
	FUND_USERSRepository fund_UserRepository;
	
	@Autowired
	FWMS_INDEX_BENCHMARKRepository fWMS_INDEX_BENCHMARKRepository;
	
	@Autowired
	FUND_MSTRRepository fUND_MSTRRepository;
	
	@Autowired
	RE_INVESTORRepository rE_INVESTORRepository;
	
	@Autowired
	FUND_MAR_CLIENT_TYPERespository fUND_MAR_CLIENT_TYPERespository;
	
	@Autowired
	StockParameters stockParameters;
	
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;
	
	
	
	private static final Logger logger=LoggerFactory.getLogger("FWMS_INDEX_BENCHMARK CONTROLLER");
	@PostMapping("/savebenchmark")
	public ResponseEntity<Map<String,Object>> save(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object FMD_DATE  =requestJson.get("effectdate");
		Object FMD_FUND =requestJson.get("fund_id");
		
		Object FMD_LEVEL=requestJson.get("level");
		Object FMD_CLIENT =requestJson.get("client_id");
		Object FMD_C_TYPE  =requestJson.get("client_type");
		Object FMD_INDEX_BM   =requestJson.get("indexid");
		
		Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		Object createdby=requestJson.get("createdby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		 if(FMD_DATE==null||FMD_LEVEL==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
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
				
				
				  FWMS_INDEX_BENCHMARK fscm=new FWMS_INDEX_BENCHMARK();
				  Calendar cl=Calendar.getInstance();
				  cl.setTimeInMillis(new Long(FMD_DATE.toString().trim()));
				  fscm.setFMD_DATE(cl);
				 
				  if(FMD_FUND!=null) {
					  if(FMD_FUND.toString().isEmpty()==false) {
						  fscm.setFMD_FUND(new Long(FMD_FUND.toString().trim()));
					  }
				  }
				  
				  fscm.setFMD_LEVEL(FMD_LEVEL.toString().trim());
				  fscm.setFMD_FUND(new Long(FMD_FUND.toString().trim()));
				  
				  if(FMD_LEVEL.toString().equalsIgnoreCase("Client Type")) {
					  fscm.setFMD_C_TYPE(new Long(FMD_C_TYPE.toString().trim()));
				  }else if(FMD_LEVEL.toString().equalsIgnoreCase("Client")) {
					  fscm.setFMD_CLIENT(new Long(FMD_CLIENT.toString().trim()));
				  }
				  
				 
				 
				  fscm.setFMD_INDEX_BM(new Integer(FMD_INDEX_BM.toString().trim()));
				 
				 if(WMS_COMMENTS!=null) {
					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
						}
				  fscm.setWMS_STATUS("Not Approved");
						
				 		
				  fscm.setIV_ENTER_UID(fuser.getSVC_UID());
				  fscm.setIV_ENTER_DATE(Calendar.getInstance());
				 
				
				  
				  try
					{
					  fscm=fWMS_INDEX_BENCHMARKRepository.save(fscm);
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
									logger.info("Both Record and Logs saved for Benckmark:"+fscm.getFMD_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved New Benckmark:"+fscm.getFMD_SNO());
							
							json.put("createdby", fscm.getIV_ENTER_UID());
							json.put("createdon", fscm.getIV_ENTER_DATE());
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("Benckmark not saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("Benckmark not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
					
				 
				
		 }		 
	}
		

	@PostMapping("/updatebenchmark")
	public ResponseEntity<Map<String,Object>> update(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object FMD_SNO	=requestJson.get("fmd_sno");
		Object FMD_DATE  =requestJson.get("effectdate");
		Object FMD_FUND =requestJson.get("fund_id");
		
		Object FMD_LEVEL=requestJson.get("level");
		Object FMD_CLIENT =requestJson.get("client_id");
		Object FMD_C_TYPE  =requestJson.get("client_type");
		Object FMD_INDEX_BM   =requestJson.get("indexid");
		
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
				FWMS_INDEX_BENCHMARK fscm=fWMS_INDEX_BENCHMARKRepository.findOne(new Long(FMD_SNO.toString().trim()));
				 
			
				  Calendar cl=Calendar.getInstance();
				  cl.setTimeInMillis(new Long(FMD_DATE.toString().trim()));
				  fscm.setFMD_DATE(cl);
				  
				  
				 
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
				 if(FMD_INDEX_BM!=null) {
					 if(FMD_INDEX_BM.toString().isEmpty()==false) {
						 fscm.setFMD_INDEX_BM(new Integer(FMD_INDEX_BM.toString().trim()));
					 }
				 }
				 if(WMS_COMMENTS!=null) {
					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
						}
				
				  fscm.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
				  fscm.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
				  
				  
				  try
					{
					  fscm=fWMS_INDEX_BENCHMARKRepository.save(fscm);
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
									logger.info("Both Record and Logs saved for FWMS_INDEX_BENCHMARK:"+fscm.getFMD_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved New STOCK LOSS:"+fscm.getFMD_SNO());
							json=getJson(fscm);
							json.put("msz","saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("FWMS_INDEX_BENCHMARK not saved");
							return new ResponseEntity(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("FWMS_INDEX_BENCHMARK not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
				}catch (Exception e) {
					json.put("msg", "fmd_sno is not correct "+e.getMessage());
					logger.error("FWMS_INDEX_BENCHMARK not saved :"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
		 }
	 
	}
	
	@PostMapping("/approvebenchmark")
	public ResponseEntity<Map<String,Object>> approve(RequestEntity<Map<String, Object>> requestData){
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
				FWMS_INDEX_BENCHMARK fscm=fWMS_INDEX_BENCHMARKRepository.findOne(new Long(FMD_SNO.toString().trim()));
				 
				 if(WMS_COMMENTS!=null) {
					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
						}
				
				  fscm.setWMS_STATUS("Approved");
				  fscm.setIV_APPROVE_UID(fuser.getSVC_UID());
				  fscm.setIV_APPROVE_DATE(Calendar.getInstance());
				  Integer FMD_INDEX_BM=fscm.getFMD_INDEX_BM();
				  if(FMD_INDEX_BM!=null) {
					  FWMS_INDEX index=stockParameters.getFWMS_INDEX(FMD_INDEX_BM);
					  if(index!=null) {
						  if(index.getWMS_APPROVE_UID()==null) {
							  json.put("msg", "Index is not approved");
								logger.error("Index not approved");
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);  
						  }
					  }
				  }
				  
				  String FMD_LEVEL=fscm.getFMD_LEVEL();
				  
				  
				  if(FMD_LEVEL!=null) {
					if(FMD_LEVEL.equalsIgnoreCase("Client level")) {
						Long FMD_CLIENT=fscm.getFMD_CLIENT();
					  FUND_MAR_CLIENT_TYPE fUND_MSTR= fUND_MAR_CLIENT_TYPERespository.findOne(FMD_CLIENT);
						  if(fUND_MSTR!=null) {
							  if(fUND_MSTR.getIV_APPROVE_UID()==null) {
								  json.put("msg", "Client Type is not approved");
									logger.error("Client not approved");
									return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);  
							  }
						  }
					  }
				
					  else if(FMD_LEVEL.equalsIgnoreCase("Client type")) {
						  Long FMD_C_TYPE=fscm.getFMD_C_TYPE();
						  RE_INVESTOR fUND_MSTR=rE_INVESTORRepository.findOne(FMD_C_TYPE) ;
						  if(fUND_MSTR!=null) {
							  if(fUND_MSTR.getIV_APPROVE_UID()==null) {
								  json.put("msg", "Stock is not approved");
									logger.error("Stock not approved");
									return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);  
							  }
						  }
					  }
					  else if(FMD_LEVEL.equalsIgnoreCase("Fund level")) {
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
				  }
				  }
				  
				  try{
					  fscm=fWMS_INDEX_BENCHMARKRepository.save(fscm);
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
									logger.info("Both Record and Logs saved for FWMS_INDEX_BENCHMARK:"+fscm.getFMD_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved:"+fscm.getFMD_SNO());
							json=getJson(fscm);
							json.put("msg","saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("FWMS_INDEX_BENCHMARK not saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("FWMS_INDEX_BENCHMARK not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
				}
				catch (Exception e) {
					json.put("msg", "fmd_sno is not correct "+e.getMessage());
					logger.error("FWMS_INDEX_BENCHMARK not saved :"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
		 }
	 
	}
	
	@PostMapping("/revokebenchmark")
	public ResponseEntity<Map<String,Object>> revoke(RequestEntity<Map<String, Object>> requestData){
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
				FWMS_INDEX_BENCHMARK fscm=fWMS_INDEX_BENCHMARKRepository.findOne(new Long(FMD_SNO.toString().trim()));
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
					  fscm=fWMS_INDEX_BENCHMARKRepository.save(fscm);
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
									logger.info("Both Record and Logs saved for FWMS_INDEX_BENCHMARK:"+fscm.getFMD_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved "+fscm.getFMD_SNO());
							json=getJson(fscm);
							json.put("msg","saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("FWMS_INDEX_BENCHMARK not saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("FWMS_INDEX_BENCHMARK not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
				}catch (Exception e) {
					json.put("msg", "fmd_sno is not correct "+e.getMessage());
					logger.error("FWMS_INDEX_BENCHMARK not saved :"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
		 }
	 
	}
	
	
	@PostMapping("/deletebenchmark")
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
				FWMS_INDEX_BENCHMARK fscm=fWMS_INDEX_BENCHMARKRepository.findOne(new Long(FMD_SNO.toString().trim()));
			
				  try
					{
					  fWMS_INDEX_BENCHMARKRepository.delete(fscm);
					
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
									logger.info("Both Record and Logs saved for FWMS_INDEX_BENCHMARK:"+fscm.getFMD_SNO());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
								logger.error("FWMS_INDEX_BENCHMARK DELETE ");
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
						
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("FWMS_INDEX_BENCHMARK not DELETE :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
				}catch (Exception e) {
					json.put("msg", "fmd_sno is not correct "+e.getMessage());
					logger.error("FWMS_INDEX_BENCHMARK not DELETE :"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
		 }
	 
	}
	

	@GetMapping("/benchmarkSearch")
	public ResponseEntity<?> getAllBenchmark(Pageable page,
			@RequestParam(required=false,value="action")String action,
			@RequestParam(required=false,value="paramSearch")String paramString,
			@RequestParam(required=false,value="level")String FMD_LEVEL
			){
		List<Long> ids=new ArrayList<>();
		  if(FMD_LEVEL!=null) {
			if(FMD_LEVEL.toString().isEmpty()==false&&paramString.isEmpty()==false) {
				if(FMD_LEVEL.equalsIgnoreCase("Client level")) {
					ids=rE_INVESTORRepository.find_ids_Client(paramString);
				  }
				  else if(FMD_LEVEL.equalsIgnoreCase("Client type")) {
					 
					 ids=fUND_MAR_CLIENT_TYPERespository.find_ids_FUND_MAR_CLIENT_TYPE(paramString);
					
				  }
				  else if(FMD_LEVEL.equalsIgnoreCase("Fund level")) {
					ids=fUND_MSTRRepository.find_ids_FUND_MSTR_LIST(paramString);  
				  }
				if(ids.isEmpty()) {
					ids.add(new Long("-1"));
				}
			}
			else {
				ids=fUND_MSTRRepository.find_ids_FUND_MSTR_LIST(paramString);
				if(ids.isEmpty()) {
					ids.add(new Long("-1"));
				}
			}
//			else if(paramString.isEmpty()==false) {
//				List<Long> ids1=fUND_MAR_CLIENT_TYPERespository.find_ids_FUND_MAR_CLIENT_TYPE(paramString);
//				List<Long> ids2=rE_INVESTORRepository.find_ids_Client(paramString);
//				List<Long> ids3=fUND_MSTRRepository.find_ids_FUND_MSTR_LIST(paramString);
//				Collections.copy(ids, ids1);
//				Collections.copy(ids, ids2);
//				Collections.copy(ids, ids3);
//			}
		  }
		  
		  List<FWMS_INDEX_BENCHMARK> allFUND_SHARE_COMPANY_MSTR=null;
		if(action!=null) {
			if(action.toString().isEmpty()==false) {
				if(action.equalsIgnoreCase("ALL")) {
					if(ids.isEmpty()==false) {
						if(FMD_LEVEL.isEmpty()==false) {
							allFUND_SHARE_COMPANY_MSTR=fWMS_INDEX_BENCHMARKRepository.findAllFWMS_INDEX_BENCHMARK_SEARCH(ids, FMD_LEVEL);
						}else {
							allFUND_SHARE_COMPANY_MSTR=fWMS_INDEX_BENCHMARKRepository.findAllFWMS_INDEX_BENCHMARK_SEARCH(ids);
						}
					}else {
						if(FMD_LEVEL.isEmpty()==false) {
							allFUND_SHARE_COMPANY_MSTR=fWMS_INDEX_BENCHMARKRepository.findAllFWMS_INDEX_BENCHMARK_SEARCH(FMD_LEVEL);
						}else {
							allFUND_SHARE_COMPANY_MSTR=(List<FWMS_INDEX_BENCHMARK>) fWMS_INDEX_BENCHMARKRepository.findAll();
						}
					}
				}
				else if(action.equalsIgnoreCase("APPROVED")) {
					if(ids.isEmpty()==false) {
						if(FMD_LEVEL.isEmpty()==false) {
							allFUND_SHARE_COMPANY_MSTR=fWMS_INDEX_BENCHMARKRepository.findAllFWMS_INDEX_BENCHMARK_SEARCHAPPROVED(ids, FMD_LEVEL);
						}else {
							allFUND_SHARE_COMPANY_MSTR=fWMS_INDEX_BENCHMARKRepository.findAllFWMS_INDEX_BENCHMARK_SEARCHAPPROVED(ids);
						}
					}else {
						if(FMD_LEVEL.isEmpty()==false) {
							allFUND_SHARE_COMPANY_MSTR=fWMS_INDEX_BENCHMARKRepository.findAllFWMS_INDEX_BENCHMARK_SEARCHAPPROVED(FMD_LEVEL);
						}else {
							allFUND_SHARE_COMPANY_MSTR=fWMS_INDEX_BENCHMARKRepository.findAllAPPROVEDFWMS_INDEX_BENCHMARK();
						}
					}
					
				}else if(action.equalsIgnoreCase("UNAPPROVED")) {
					if(ids.isEmpty()==false) {
						if(FMD_LEVEL.isEmpty()==false) {
							allFUND_SHARE_COMPANY_MSTR=fWMS_INDEX_BENCHMARKRepository.findAllFWMS_INDEX_BENCHMARK_SEARCHUNAPPROVED(ids, FMD_LEVEL);
						}else {
							allFUND_SHARE_COMPANY_MSTR=fWMS_INDEX_BENCHMARKRepository.findAllFWMS_INDEX_BENCHMARK_SEARCHUNAPPROVED(ids);
						}
					}else{
						if(FMD_LEVEL.isEmpty()==false) {
							allFUND_SHARE_COMPANY_MSTR=fWMS_INDEX_BENCHMARKRepository.findAllFWMS_INDEX_BENCHMARK_SEARCHUNAPPROVED(FMD_LEVEL);
						}else {
							allFUND_SHARE_COMPANY_MSTR=fWMS_INDEX_BENCHMARKRepository.findAllUNAPPROVEDFWMS_INDEX_BENCHMARK();
						}
					}
				}
			}
		}
		else {

			Map<String,Object>json=new HashMap<>();
			json.put("msg", "Value of action is missing");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		
//		Page<Map<String,Object>> jsonArray=allFUND_SHARE_COMPANY_MSTR.map(new Converter<FWMS_INDEX_BENCHMARK, Map<String,Object>>() {
//			@Override
//			public Map<String, Object> convert(FWMS_INDEX_BENCHMARK conut) {
//				Map<String,Object> json=getJson(conut);
//						return json;
//			}
//			
//		});
		List<BenchMarkBean> allbeanchs=new ArrayList<>();
		if(allFUND_SHARE_COMPANY_MSTR.size()>0) {
			allFUND_SHARE_COMPANY_MSTR.forEach(count->{
				BenchMarkBean bmb=getJsonBean(count);
				allbeanchs.add(bmb);
			});
		}
		Collections.sort(allbeanchs);
		int start = page.getOffset();
		int end = (start + page.getPageSize()) > allbeanchs.size() ? allbeanchs.size()
				: (start + page.getPageSize());
		Page<BenchMarkBean> jsonArray = new PageImpl<BenchMarkBean>(allbeanchs.subList(start, end), page,
				allbeanchs.size());
		return new ResponseEntity<Page<BenchMarkBean>>(jsonArray,HttpStatus.OK);
	}
	
	
	public Map<String,Object> getJson(FWMS_INDEX_BENCHMARK conut){
		Map<String,Object> json=new HashMap<String,Object>();
        json.put("sno", conut.getFMD_SNO());
		json.put("effectdate", conut.getFMD_DATE());
//		json.put("fund_id",conut.getFMD_FUND());
//		
		
//		json.put("client_id",conut.getFMD_CLIENT());
//		json.put("client_type",conut.getFMD_C_TYPE());
//		json.put("index",conut.getFMD_INDEX_BM());
		
		String FMD_LEVEL=conut.getFMD_LEVEL();
		json.put("level",conut.getFMD_LEVEL());
		
		  if(FMD_LEVEL!=null) {
			if(FMD_LEVEL.equalsIgnoreCase("Fund level")) {
				Long FMD_FUND=conut.getFMD_FUND();
				FUND_MSTR fund_MSTR=fUND_MSTRRepository.findOne(FMD_FUND);
				  if(fund_MSTR!=null) {
						json.put("fund_id",fund_MSTR.getSVC_CODE());
						json.put("fund_name", fund_MSTR.getSVC_NAME());
				  }else {
					  json.put("fund_id",null);
						json.put("fund_name", null);
				  }
			  }
		
			  else if(FMD_LEVEL.equalsIgnoreCase("Client level")) {
				  Long FMD_Client=conut.getFMD_CLIENT();
				  RE_INVESTOR fUND_MSTR=rE_INVESTORRepository.findOne(FMD_Client) ;
				  if(fUND_MSTR!=null) {
					  json.put("client_id",conut.getFMD_CLIENT());
					  json.put("client_name",fUND_MSTR.getRI_INVESTOR_NAME());
				  }else {
					  json.put("client_id",null);
						json.put("client_id", null);
				  }
			  }
			
			  else if(FMD_LEVEL.equalsIgnoreCase("Client type")) {
					Long FMD_CLIENT_TYPE=conut.getFMD_C_TYPE();
					  FUND_MAR_CLIENT_TYPE fUND_MSTR= fUND_MAR_CLIENT_TYPERespository.findOne(FMD_CLIENT_TYPE);
						  if(fUND_MSTR!=null) {
								json.put("client_type_id",fUND_MSTR.getFCT_ID());
								json.put("client_type_name", fUND_MSTR.getFCT_NAME());
						  }else {
							  json.put("client_type_id",null);
								json.put("client_type_name",null);
						  }
		  }
		}
			Long FMD_FUND=conut.getFMD_FUND();
			FUND_MSTR fund_MSTR=fUND_MSTRRepository.findOne(FMD_FUND);
			  if(fund_MSTR!=null) {
					json.put("fund_id",fund_MSTR.getSVC_CODE());
					json.put("fund_name", fund_MSTR.getSVC_NAME());
			  }else {
				  json.put("fund_id",null);
					json.put("fund_name", null);
			  }
			  
		FWMS_INDEX fmd=stockParameters.getFWMS_INDEX(conut.getFMD_INDEX_BM());
		if(fmd!=null) {
			json.put("indexid",conut.getFMD_INDEX_BM());
			json.put("indexname",fmd.getWMS_INDEX_DESC());
		}
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
		
		return json;
	}

	public BenchMarkBean getJsonBean(FWMS_INDEX_BENCHMARK conut){
		BenchMarkBean bmb=new BenchMarkBean();
        bmb.setSno( conut.getFMD_SNO()+"");
		bmb.setEffectdate( conut.getFMD_DATE().getTimeInMillis()+"");
//		bmb.setfund_id(conut.getFMD_FUND());
//		
		
//		bmb.setclient_id(conut.getFMD_CLIENT());
//		bmb.setclient_type(conut.getFMD_C_TYPE());
//		bmb.setindex(conut.getFMD_INDEX_BM());
		
		String FMD_LEVEL=conut.getFMD_LEVEL();
		bmb.setLevel(conut.getFMD_LEVEL());
		
		  if(FMD_LEVEL!=null) {
			if(FMD_LEVEL.equalsIgnoreCase("Fund level")) {
				Long FMD_FUND=conut.getFMD_FUND();
				FUND_MSTR fund_MSTR=fUND_MSTRRepository.findOne(FMD_FUND);
				  if(fund_MSTR!=null) {
						bmb.setFund_id(fund_MSTR.getSVC_CODE()+"");
						bmb.setFund_name( fund_MSTR.getSVC_NAME());
				  }else {
					  bmb.setFund_id(null);
						bmb.setFund_name( null);
				  }
			  }
		
			  else if(FMD_LEVEL.equalsIgnoreCase("Client level")) {
				  Long FMD_Client=conut.getFMD_CLIENT();
				  RE_INVESTOR fUND_MSTR=rE_INVESTORRepository.findOne(FMD_Client) ;
				  if(fUND_MSTR!=null) {
					  bmb.setClient_id(conut.getFMD_CLIENT()+"");
					  bmb.setClient_name(fUND_MSTR.getRI_INVESTOR_NAME());
				  }else {
					  bmb.setClient_id(null);
						bmb.setClient_id( null);
				  }
			  }
			
			  else if(FMD_LEVEL.equalsIgnoreCase("Client type")) {
					Long FMD_CLIENT_TYPE=conut.getFMD_C_TYPE();
					  FUND_MAR_CLIENT_TYPE fUND_MSTR= fUND_MAR_CLIENT_TYPERespository.findOne(FMD_CLIENT_TYPE);
						  if(fUND_MSTR!=null) {
								bmb.setClient_type_id(fUND_MSTR.getFCT_ID()+"");
								bmb.setClient_type_name( fUND_MSTR.getFCT_NAME());
						  }else {
							  bmb.setClient_type_id(null);
								bmb.setClient_type_name(null);
						  }
		  }
		}
			Long FMD_FUND=conut.getFMD_FUND();
			FUND_MSTR fund_MSTR=fUND_MSTRRepository.findOne(FMD_FUND);
			  if(fund_MSTR!=null) {
					bmb.setFund_id(fund_MSTR.getSVC_CODE()+"");
					bmb.setFund_name( fund_MSTR.getSVC_NAME());
			  }else {
				  bmb.setFund_id(null);
					bmb.setFund_name( null);
			  }
			  
		FWMS_INDEX fmd=stockParameters.getFWMS_INDEX(conut.getFMD_INDEX_BM());
		if(fmd!=null) {
			bmb.setIndexid(conut.getFMD_INDEX_BM()+"");
			bmb.setIndexname(fmd.getWMS_INDEX_DESC());
		}
		bmb.setComments( conut.getWMS_COMMENTS());
		bmb.setStatus( conut.getWMS_STATUS());
		FUND_USERS user=null;
		if(conut.getIV_ENTER_UID()!=null) {
			user=getUserName(conut.getIV_ENTER_UID());
			if(user!=null) {
			bmb.setEnteredby( user.getSVU_NAME());
			bmb.setEnteredbyuserid( user.getSVU_USER_NAME());
			bmb.setEnteredbyuuid( user.getSVC_UID());
			bmb.setEntereddate( conut.getIV_ENTER_DATE().getTimeInMillis()+"");
			}
			else {
				bmb.setEnteredby( null);
				bmb.setEnteredbyuserid( null);
				bmb.setEnteredbyuuid( null);
				bmb.setEntereddate( null);
			}
		}else {
			bmb.setEnteredby( null);
			bmb.setEnteredbyuserid( null);
			bmb.setEnteredbyuuid( null);
			bmb.setEntereddate( null);
		}
		
		if(conut.getIV_APPROVE_UID()!=null) {
			user=getUserName(conut.getIV_APPROVE_UID());
			if(user!=null) {
			bmb.setApprovedby( user.getSVU_NAME());
			bmb.setApprovedbyuserid( user.getSVU_USER_NAME());
			bmb.setApprovedbyuuid( user.getSVC_UID());
			bmb.setApproveddate( conut.getIV_APPROVE_DATE().getTimeInMillis()+"");
			}
			else {
				bmb.setApprovedby( null);
				bmb.setApprovedbyuserid( null);
				bmb.setApprovedbyuuid( null);
				bmb.setApproveddate( null);
			}
		}else {
			bmb.setApprovedby( null);
			bmb.setApprovedbyuserid( null);
			bmb.setApprovedbyuuid( null);
			bmb.setApproveddate( null);
		}
		
		if(conut.getIV_LAST_UPDATE_UID()!=null) {
			user=getUserName(conut.getIV_LAST_UPDATE_UID());
			if(user!=null) {
			bmb.setModifiedby( user.getSVU_NAME());
			bmb.setModifiedbyuserid( user.getSVU_USER_NAME());
			bmb.setModifiedbyuuid( user.getSVC_UID());
			bmb.setModifieddate( conut.getIV_LAST_UPDATE_DATE().getTimeInMillis()+"");
			}
			else {
				bmb.setModifiedby( null);
				bmb.setModifiedbyuserid( null);
				bmb.setModifiedbyuuid( null);
				bmb.setModifieddate( null);
			}
		}else {
			bmb.setModifiedby( null);
			bmb.setModifiedbyuserid( null);
			bmb.setModifiedbyuuid( null);
			bmb.setModifieddate( null);
		}
		
		return bmb;
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
