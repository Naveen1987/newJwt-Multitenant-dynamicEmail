package com.fynisys.controller.parameters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.FUND_USER_LOG;
import com.fynisys.model.parameters.FUND_EXCHANGE_HOLIDAY;
import com.fynisys.model.parameters.FUND_EXCHANGE_MSTR;
import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.parameters.FUND_EXCHANGE_HOLIDAYRepository;
import com.fynisys.repository.parameters.FUND_EXCHANGE_MSTRRepositroy;

@RestController
public class FUND_EXCHANGE_MSTRController {
	
	@Autowired
	FUND_USERSRepository fund_UserRepository;
	
	@Autowired
	FUND_EXCHANGE_MSTRRepositroy fUND_EXCHANGE_MSTRRepositroy;  
	
	@Autowired
	FUND_EXCHANGE_HOLIDAYRepository fUND_EXCHANGE_HOLIDAYRepository;
	
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;
	
	private static final Logger logger=LoggerFactory.getLogger("FUND EXCHANGE MASTER");
	
	@PostMapping("/saveexchange")
	public ResponseEntity<Map<String,Object>> saveExchange(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		Object SVE_NAME=dataMap.get("exchangename");
		Object SVE_SHORT_NAME=dataMap.get("exchangeshortname");
		Object createdby =dataMap.get("createdby");
		Object WMS_COMMENTS=dataMap.get("comment");
		Object SVL_SCREEN=dataMap.get("screentype");
		Object SVL_DESC=dataMap.get("log");
		Object FEH_D1 =dataMap.get("monday");
		Object FEH_D2 =dataMap.get("tuesday");
		Object FEH_D3 =dataMap.get("wednesday");
		Object FEH_D4 =dataMap.get("thursday");
		Object FEH_D5 =dataMap.get("friday");
		Object FEH_D6 =dataMap.get("saturday");
		Object FEH_D7 =dataMap.get("sunday");
		if(SVE_NAME==null||SVE_SHORT_NAME==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null) {
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
			FUND_EXCHANGE_MSTR fund_im=new FUND_EXCHANGE_MSTR();
			fund_im.setSVE_NAME(SVE_NAME.toString().trim());
			fund_im.setSVE_SHORT_NAME(SVE_SHORT_NAME.toString().trim());
			fund_im.setSVC_UID(fuser.getSVC_UID());
			
			if(WMS_COMMENTS!=null) {
				fund_im.setWMS_COMMENTS(WMS_COMMENTS.toString());
			}
			
			fund_im.setWMS_STATUS("Not Approved");
			
			fund_im.setIV_ENTER_UID(fuser.getSVC_UID());
			fund_im.setIV_ENTER_DATE(Calendar.getInstance());
//			fund_im.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
//			fund_im.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
			try {
				fund_im=fUND_EXCHANGE_MSTRRepositroy.save(fund_im);
				
				if(fund_im!=null) {
					logger.info("Exchange Saved :"+fund_im.getSVE_NAME());
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
						logger.info("Both Record and Logs saved for EXCHANGE:"+fund_im.getSVE_NAME());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
					json.put("exchangeid", fund_im.getSVE_CODE());
					json.put("exchangename", fund_im.getSVE_NAME());
					json.put("createdby", fund_im.getIV_ENTER_UID());
					json.put("createdon", fund_im.getIV_ENTER_DATE());
					if(FEH_D1!=null||FEH_D2!=null||FEH_D3!=null||FEH_D4!=null
							||FEH_D5!=null||FEH_D6!=null||FEH_D7!=null) {
						try {
						FUND_EXCHANGE_HOLIDAY fholy=new FUND_EXCHANGE_HOLIDAY();
						if(FEH_D1!=null) {
							if(!FEH_D1.toString().isEmpty()) {
							fholy.setFEH_D1(new Integer(FEH_D1.toString()));
							}
						}
						
						if(FEH_D2!=null) {
							if(!FEH_D2.toString().isEmpty()) {
								fholy.setFEH_D2(new Integer(FEH_D2.toString()));
								}
						}
						
						
						if(FEH_D3!=null) {
							if(!FEH_D3.toString().isEmpty()) {
								fholy.setFEH_D3(new Integer(FEH_D3.toString()));
								}
						}
						
						if(FEH_D4!=null) {
							if(!FEH_D4.toString().isEmpty()) {
								fholy.setFEH_D4(new Integer(FEH_D4.toString()));
								}
						}
						
						if(FEH_D5!=null) {
							if(!FEH_D5.toString().isEmpty()) {
								fholy.setFEH_D5(new Integer(FEH_D5.toString()));
								}
						}
						
						if(FEH_D6!=null) {
							if(!FEH_D6.toString().isEmpty()) {
								fholy.setFEH_D6(new Integer(FEH_D6.toString()));
								}
						}
						
						if(FEH_D7!=null) {
							if(!FEH_D7.toString().isEmpty()) {
								fholy.setFEH_D7(new Integer(FEH_D7.toString()));
								}
						}
						fholy.setFEH_EXCHANGE(fund_im);
						fholy=fUND_EXCHANGE_HOLIDAYRepository.save(fholy);
						if(fholy!=null) {
							logger.info("Settlement Date is save and Id is:-"+fholy.getFEH_ETYPE());
							json.put("monday", fholy.getFEH_D1());
							json.put("tuesday", fholy.getFEH_D2());
							json.put("wednesday", fholy.getFEH_D3());
							json.put("thursday", fholy.getFEH_D4());
							json.put("friday", fholy.getFEH_D5());
							json.put("saturday", fholy.getFEH_D6());
							json.put("sunday", fholy.getFEH_D7());
						}
						}catch (Exception e) {
						logger.error("Settlement is not save error is:-"+e.getMessage());
						}
					}
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Exchange Not Saved without error");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				logger.error("Exchange Not Saved :-"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
		}
		
	}
	
	@PostMapping("/updateexchange")
	public ResponseEntity<Map<String,Object>> updateExchange(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		Object SVE_CODE=dataMap.get("exchangeid");
		Object SVE_NAME=dataMap.get("exchangename");
		Object SVE_SHORT_NAME=dataMap.get("exchangeshortname");
		Object createdby =dataMap.get("modifiedby");
		Object WMS_COMMENTS=dataMap.get("comment");
		Object SVL_SCREEN=dataMap.get("screentype");
		Object SVL_DESC=dataMap.get("log");
		
//		Object settlementid=dataMap.get("settlementid");
		Object FEH_D1 =dataMap.get("monday");
		Object FEH_D2 =dataMap.get("tuesday");
		Object FEH_D3 =dataMap.get("wednesday");
		Object FEH_D4 =dataMap.get("thursday");
		Object FEH_D5 =dataMap.get("friday");
		Object FEH_D6 =dataMap.get("saturday");
		Object FEH_D7 =dataMap.get("sunday");
		if(createdby==null||SVE_CODE==null||SVL_SCREEN==null||SVL_DESC==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			logger.error("Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		else {
			FUND_USERS fuser=isValid(createdby.toString());
			if(fuser==null)
			{
					json.put("msg", "Modifiedby is not valid user");
					logger.error("ModifiedBy is not valid user");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			FUND_EXCHANGE_MSTR fund_im=fUND_EXCHANGE_MSTRRepositroy.findOne(new Integer(SVE_CODE.toString()));
			if(fund_im==null) {
				json.put("msg", "No Exchange found with this id->"+SVE_CODE.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			if(SVE_NAME!=null) {
			fund_im.setSVE_NAME(SVE_NAME.toString().trim());
			}
			if(SVE_SHORT_NAME!=null) {
			fund_im.setSVE_SHORT_NAME(SVE_SHORT_NAME.toString().trim());
			}
			if(WMS_COMMENTS!=null) {
			fund_im.setWMS_COMMENTS(WMS_COMMENTS.toString().trim());
			}
			fund_im.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
			fund_im.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
			
			try {
				fund_im=fUND_EXCHANGE_MSTRRepositroy.save(fund_im);
				if(fund_im!=null) {
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
						logger.info("Both Record and Logs saved for EXCHANGE:"+fund_im.getSVE_NAME());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
					logger.info("Updated Successfully:-"+fund_im.getSVE_NAME());
					json.put("exchangeid", fund_im.getSVE_CODE());
					json.put("exchangename", fund_im.getSVE_NAME());
					json.put("exchangeshortname", fund_im.getSVE_SHORT_NAME());
					json.put("comment", fund_im.getWMS_COMMENTS());
					json.put("status", fund_im.getWMS_STATUS());
					FUND_USERS user=null;
					if(fund_im.getIV_ENTER_UID()!=null) {
						user=getUserName(fund_im.getIV_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", fund_im.getIV_ENTER_DATE());
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
					
					if(fund_im.getIV_APPROVE_UID()!=null) {
						user=getUserName(fund_im.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", fund_im.getIV_APPROVE_DATE());
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
					
					if(fund_im.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(fund_im.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", fund_im.getIV_LAST_UPDATE_DATE());
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
					/*
					 * Settlement Updating process
					 */
					if(FEH_D1!=null||FEH_D2!=null||FEH_D3!=null||FEH_D4!=null
							||FEH_D5!=null||FEH_D6!=null||FEH_D7!=null) {
						try {
						FUND_EXCHANGE_HOLIDAY fholy;
						List<FUND_EXCHANGE_HOLIDAY> FEH=fund_im.getfUND_EXCHANGE_HOLIDAY();
						if(FEH==null) {
							fholy=new FUND_EXCHANGE_HOLIDAY();
							fholy.setFEH_EXCHANGE(fund_im);
						}
						else if(FEH.isEmpty()) {
							fholy=new FUND_EXCHANGE_HOLIDAY();
							fholy.setFEH_EXCHANGE(fund_im);
						}else {
							/*
							 * Actully this is one to one relationship so only first record is needed
							 */
						
							fholy=FEH.get(0);
						}
						if(FEH_D1!=null) {
							if(!FEH_D1.toString().isEmpty()) {
							fholy.setFEH_D1(new Integer(FEH_D1.toString()));
							}
						}
						
						if(FEH_D2!=null) {
							if(!FEH_D2.toString().isEmpty()) {
								fholy.setFEH_D2(new Integer(FEH_D2.toString()));
								}
						}
						
						
						if(FEH_D3!=null) {
							if(!FEH_D3.toString().isEmpty()) {
								fholy.setFEH_D3(new Integer(FEH_D3.toString()));
								}
						}
						
						if(FEH_D4!=null) {
							if(!FEH_D4.toString().isEmpty()) {
								fholy.setFEH_D4(new Integer(FEH_D4.toString()));
								}
						}
						
						if(FEH_D5!=null) {
							if(!FEH_D5.toString().isEmpty()) {
								fholy.setFEH_D5(new Integer(FEH_D5.toString()));
								}
						}
						
						if(FEH_D6!=null) {
							if(!FEH_D6.toString().isEmpty()) {
								fholy.setFEH_D6(new Integer(FEH_D6.toString()));
								}
						}
						
						if(FEH_D7!=null) {
							if(!FEH_D7.toString().isEmpty()) {
								fholy.setFEH_D7(new Integer(FEH_D7.toString()));
								}
						}
						
						fholy=fUND_EXCHANGE_HOLIDAYRepository.save(fholy);
						if(fholy!=null) {
							logger.info("Settlement Date is Updated and Id is:-"+fholy.getFEH_ETYPE());
							//json.put("settlementid", fholy.getFEH_ETYPE());
							json.put("monday", fholy.getFEH_D1());
							json.put("tuesday", fholy.getFEH_D2());
							json.put("wednesday", fholy.getFEH_D3());
							json.put("thursday", fholy.getFEH_D4());
							json.put("friday", fholy.getFEH_D5());
							json.put("saturday", fholy.getFEH_D6());
							json.put("sunday", fholy.getFEH_D7());
						}
						}catch (Exception e) {
						logger.error("Settlement is not save error is:-"+e.getMessage());
						}
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
					}
					/*
					 * Settlement Updating process
					 */
					logger.error("there is not any Settlement update");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Exchange Not Saved without error");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				logger.error("Not Updated ->"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
		}
		
	}
	
	@PostMapping("/deleteexchange")
	public ResponseEntity<Map<String,Object>> deleteExchange(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		Object SVE_CODE=dataMap.get("exchangeid");
		Object createdby =dataMap.get("modifiedby");
		
		if(createdby==null||SVE_CODE==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			logger.error("Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		else {
			FUND_USERS fuser=isValid(createdby.toString());
			if(fuser==null)
			{
					json.put("msg", "Modifiedby is not valid user");
					logger.error("ModifiedBy is not valid user");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			FUND_EXCHANGE_MSTR fund_im=fUND_EXCHANGE_MSTRRepositroy.findOne(new Integer(SVE_CODE.toString()));
			if(fund_im==null) {
				json.put("msg", "No Exchange found with this id->"+SVE_CODE.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
						
			try {
				fUND_EXCHANGE_MSTRRepositroy.delete(fund_im);
			
					json.put("msg", "Deleted");
					logger.info("Detetd Successfully:-"+fund_im.getSVE_NAME());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				
			catch (Exception e) {
				json.put("msg", e.getMessage());
				logger.error("Not Deleted ->"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
		}
		
	}
	
	
	@PostMapping("/approveexchange")
	public ResponseEntity<Map<String,Object>> approveExchange(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		
		Object SVE_CODE=dataMap.get("exchangeid");
		Object approvedby =dataMap.get("approvedby");
		Object WMS_COMMENTS=dataMap.get("comment");
		
		if(approvedby==null||SVE_CODE==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			logger.error("Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		else {
			FUND_USERS fuser=isValid(approvedby.toString());
			if(fuser==null)
			{
					json.put("msg", "Approver is not valid user"+approvedby);
					logger.error("Approver is not valid user ->"+approvedby);
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			FUND_EXCHANGE_MSTR fund_im=fUND_EXCHANGE_MSTRRepositroy.findOne(new Integer(SVE_CODE.toString()));
			if(fund_im==null) {
				json.put("msg", "No Exchange found with this id->"+SVE_CODE.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
			if(WMS_COMMENTS!=null) {
			fund_im.setWMS_COMMENTS(WMS_COMMENTS.toString().trim());
			}
			/*
			 * Approve Status
			 * */
			fund_im.setWMS_STATUS("Approved");
			fund_im.setIV_APPROVE_UID(fuser.getSVC_UID());
			fund_im.setIV_APPROVE_DATE(Calendar.getInstance());
			
			
//			fund_im.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
//			fund_im.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
			
			
			try {
				fund_im=fUND_EXCHANGE_MSTRRepositroy.save(fund_im);
				if(fund_im!=null) {
					json.put("msg", "saved");
					logger.info("Updated Successfully:-"+fund_im.getSVE_NAME());
					json.put("exchangeid", fund_im.getSVE_CODE());
					json.put("exchangename", fund_im.getSVE_NAME());
					json.put("exchangeshortname", fund_im.getSVE_SHORT_NAME());
					json.put("comments", fund_im.getWMS_COMMENTS());
					json.put("status", fund_im.getWMS_STATUS());
					FUND_EXCHANGE_HOLIDAY fholy;
					List<FUND_EXCHANGE_HOLIDAY> FEH=fund_im.getfUND_EXCHANGE_HOLIDAY();
					if(FEH==null) {
						//json.put("settlementid", null);
						json.put("monday",null);
						json.put("tuesday", null);
						json.put("wednesday",null);
						json.put("thursday",null);
						json.put("friday", null);
						json.put("saturday", null);
						json.put("sunday",null);						
					}
					else if(FEH.isEmpty()) {
						//json.put("settlementid", null);
						json.put("monday",null);
						json.put("tuesday", null);
						json.put("wednesday",null);
						json.put("thursday",null);
						json.put("friday", null);
						json.put("saturday", null);
						json.put("sunday",null);
					}
					else {
						fholy=FEH.get(0);
						//json.put("settlementid", fholy.getFEH_ETYPE());
						json.put("monday", fholy.getFEH_D1());
						json.put("tuesday", fholy.getFEH_D2());
						json.put("wednesday", fholy.getFEH_D3());
						json.put("thursday", fholy.getFEH_D4());
						json.put("friday", fholy.getFEH_D5());
						json.put("saturday", fholy.getFEH_D6());
						json.put("sunday", fholy.getFEH_D7());
					}
					FUND_USERS user=null;
					if(fund_im.getIV_ENTER_UID()!=null) {
						user=getUserName(fund_im.getIV_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", fund_im.getIV_ENTER_DATE());
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
					
					if(fund_im.getIV_APPROVE_UID()!=null) {
						user=getUserName(fund_im.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", fund_im.getIV_APPROVE_DATE());
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
					
					if(fund_im.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(fund_im.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", fund_im.getIV_LAST_UPDATE_DATE());
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
					logger.error("Exchange Not Saved without error");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				logger.error("Not Updated Successfully:-"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
		}
		
	}
	
	@PostMapping("/revokeexchange")
	public ResponseEntity<Map<String,Object>> revokeExchange(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		
		Object SVE_CODE=dataMap.get("exchangeid");
		Object createdby =dataMap.get("modifiedby");
		Object WMS_COMMENTS=dataMap.get("comment");
		
		if(createdby==null||SVE_CODE==null||WMS_COMMENTS==null) {
			logger.error("Please check Input json, missing some required attributes");
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		else {
			FUND_USERS fuser=isValid(createdby.toString());
			if(fuser==null)
			{
					json.put("msg", "Revoker is not valid user");
					logger.error("Revoker is not valid user");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			FUND_EXCHANGE_MSTR fund_im=fUND_EXCHANGE_MSTRRepositroy.findOne(new Integer(SVE_CODE.toString()));
			if(fund_im==null) {
				json.put("msg", "No Exchange found with this id->"+SVE_CODE.toString());
				logger.error("No Exchange found with this id->"+SVE_CODE.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}

			
			fund_im.setWMS_COMMENTS(WMS_COMMENTS.toString().trim());
			/*
			 * Not Approve Status
			 * */
			fund_im.setWMS_STATUS("Not Approved");
			fund_im.setIV_APPROVE_UID(null);
			fund_im.setIV_APPROVE_DATE(null);
//			fund_im.setIV_APPROVE_UID(fuser.getSVC_UID());
//			fund_im.setIV_APPROVE_DATE(Calendar.getInstance());
			
			fund_im.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
			fund_im.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
			
			try {
				fund_im=fUND_EXCHANGE_MSTRRepositroy.save(fund_im);
				if(fund_im!=null) {
					json.put("msg", "saved");
					logger.info("Saved->"+ fund_im.getSVE_NAME());
					json.put("exchangeid", fund_im.getSVE_CODE());
					json.put("exchangename", fund_im.getSVE_NAME());
					json.put("exchangeshortname", fund_im.getSVE_SHORT_NAME());
					json.put("comments", fund_im.getWMS_COMMENTS());
					json.put("status", fund_im.getWMS_STATUS());
					FUND_EXCHANGE_HOLIDAY fholy;
					List<FUND_EXCHANGE_HOLIDAY> FEH=fund_im.getfUND_EXCHANGE_HOLIDAY();
					if(FEH==null) {
						//json.put("settlementid", null);
						json.put("monday",null);
						json.put("tuesday", null);
						json.put("wednesday",null);
						json.put("thursday",null);
						json.put("friday", null);
						json.put("saturday", null);
						json.put("sunday",null);						
					}
					else if(FEH.isEmpty()) {
						//json.put("settlementid", null);
						json.put("monday",null);
						json.put("tuesday", null);
						json.put("wednesday",null);
						json.put("thursday",null);
						json.put("friday", null);
						json.put("saturday", null);
						json.put("sunday",null);
					}
					else {
						fholy=FEH.get(0);
						//json.put("settlementid", fholy.getFEH_ETYPE());
						json.put("monday", fholy.getFEH_D1());
						json.put("tuesday", fholy.getFEH_D2());
						json.put("wednesday", fholy.getFEH_D3());
						json.put("thursday", fholy.getFEH_D4());
						json.put("friday", fholy.getFEH_D5());
						json.put("saturday", fholy.getFEH_D6());
						json.put("sunday", fholy.getFEH_D7());
					}
					FUND_USERS user=null;
					if(fund_im.getIV_ENTER_UID()!=null) {
						user=getUserName(fund_im.getIV_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", fund_im.getIV_ENTER_DATE());
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
					
					if(fund_im.getIV_APPROVE_UID()!=null) {
						user=getUserName(fund_im.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", fund_im.getIV_APPROVE_DATE());
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
					
					if(fund_im.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(fund_im.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", fund_im.getIV_LAST_UPDATE_DATE());
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
					logger.error("Exchange Not Saved without error");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				logger.error("Not Saved "+ e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
		}
		
	}
	

	
	@PostMapping("/exchanges")
	public ResponseEntity<List<Map<String,Object>>> getAllExchanges(RequestEntity<Map<String,Object>> requestData)
	{
		List<Map<String,Object>> JsonCountry=new ArrayList<Map<String,Object>>();
		Map<String,Object> requestJson=requestData.getBody();
		String requestString=(String)requestJson.get("action");
		if(requestString==null) {
			fUND_EXCHANGE_MSTRRepositroy.findAllExchange().forEach(conut->{
				FUND_USERS user=null;
				Map<String,Object> json=new HashMap<String,Object>();
				json.put("exchangeid", conut.getSVE_CODE());
				json.put("exchangename", conut.getSVE_NAME());
				json.put("exchangeshortname", conut.getSVE_SHORT_NAME());
				json.put("comments", conut.getWMS_COMMENTS());
				json.put("status", conut.getWMS_STATUS());
				/*
				 * Adding of settlement Dates
				 */
				FUND_EXCHANGE_HOLIDAY fholy;
				List<FUND_EXCHANGE_HOLIDAY> FEH=conut.getfUND_EXCHANGE_HOLIDAY();
				if(FEH==null) {
					//json.put("settlementid", null);
					json.put("monday",null);
					json.put("tuesday", null);
					json.put("wednesday",null);
					json.put("thursday",null);
					json.put("friday", null);
					json.put("saturday", null);
					json.put("sunday",null);						
				}
				else if(FEH.isEmpty()) {
					//json.put("settlementid", null);
					json.put("monday",null);
					json.put("tuesday", null);
					json.put("wednesday",null);
					json.put("thursday",null);
					json.put("friday", null);
					json.put("saturday", null);
					json.put("sunday",null);
				}
				else {
					fholy=FEH.get(0);
					//json.put("settlementid", fholy.getFEH_ETYPE());
					json.put("monday", fholy.getFEH_D1());
					json.put("tuesday", fholy.getFEH_D2());
					json.put("wednesday", fholy.getFEH_D3());
					json.put("thursday", fholy.getFEH_D4());
					json.put("friday", fholy.getFEH_D5());
					json.put("saturday", fholy.getFEH_D6());
					json.put("sunday", fholy.getFEH_D7());
				}
				
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
				JsonCountry.add(json);
			});
		}else if(requestString.equalsIgnoreCase("ALL")) {
			fUND_EXCHANGE_MSTRRepositroy.findAllExchange().forEach(conut->{
				FUND_USERS user=null;
				Map<String,Object> json=new HashMap<String,Object>();
				json.put("exchangeid", conut.getSVE_CODE());
				json.put("exchangename", conut.getSVE_NAME());
				json.put("exchangeshortname", conut.getSVE_SHORT_NAME());
				json.put("comments", conut.getWMS_COMMENTS());
				json.put("status", conut.getWMS_STATUS());
				/*
				 * Adding of settlement Dates
				 */
				FUND_EXCHANGE_HOLIDAY fholy;
				List<FUND_EXCHANGE_HOLIDAY> FEH=conut.getfUND_EXCHANGE_HOLIDAY();
				if(FEH==null) {
					//json.put("settlementid", null);
					json.put("monday",null);
					json.put("tuesday", null);
					json.put("wednesday",null);
					json.put("thursday",null);
					json.put("friday", null);
					json.put("saturday", null);
					json.put("sunday",null);						
				}
				else if(FEH.isEmpty()) {
					//json.put("settlementid", null);
					json.put("monday",null);
					json.put("tuesday", null);
					json.put("wednesday",null);
					json.put("thursday",null);
					json.put("friday", null);
					json.put("saturday", null);
					json.put("sunday",null);
				}
				else {
					fholy=FEH.get(0);
					//json.put("settlementid", fholy.getFEH_ETYPE());
					json.put("monday", fholy.getFEH_D1());
					json.put("tuesday", fholy.getFEH_D2());
					json.put("wednesday", fholy.getFEH_D3());
					json.put("thursday", fholy.getFEH_D4());
					json.put("friday", fholy.getFEH_D5());
					json.put("saturday", fholy.getFEH_D6());
					json.put("sunday", fholy.getFEH_D7());
				}
				
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
				JsonCountry.add(json);
			});
		}else if(requestString.equalsIgnoreCase("APPROVED")) {
			fUND_EXCHANGE_MSTRRepositroy.findAllAPPROVEDExchange().forEach(conut->{
				FUND_USERS user=null;
				Map<String,Object> json=new HashMap<String,Object>();
				json.put("exchangeid", conut.getSVE_CODE());
				json.put("exchangename", conut.getSVE_NAME());
				json.put("exchangeshortname", conut.getSVE_SHORT_NAME());
				json.put("comments", conut.getWMS_COMMENTS());
				json.put("status", conut.getWMS_STATUS());
				/*
				 * Adding of settlement Dates
				 */
				FUND_EXCHANGE_HOLIDAY fholy;
				List<FUND_EXCHANGE_HOLIDAY> FEH=conut.getfUND_EXCHANGE_HOLIDAY();
				if(FEH==null) {
					//json.put("settlementid", null);
					json.put("monday",null);
					json.put("tuesday", null);
					json.put("wednesday",null);
					json.put("thursday",null);
					json.put("friday", null);
					json.put("saturday", null);
					json.put("sunday",null);						
				}
				else if(FEH.isEmpty()) {
					//json.put("settlementid", null);
					json.put("monday",null);
					json.put("tuesday", null);
					json.put("wednesday",null);
					json.put("thursday",null);
					json.put("friday", null);
					json.put("saturday", null);
					json.put("sunday",null);
				}
				else {
					fholy=FEH.get(0);
					//json.put("settlementid", fholy.getFEH_ETYPE());
					json.put("monday", fholy.getFEH_D1());
					json.put("tuesday", fholy.getFEH_D2());
					json.put("wednesday", fholy.getFEH_D3());
					json.put("thursday", fholy.getFEH_D4());
					json.put("friday", fholy.getFEH_D5());
					json.put("saturday", fholy.getFEH_D6());
					json.put("sunday", fholy.getFEH_D7());
				}
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
				JsonCountry.add(json);
			});
		}else if(requestString.equalsIgnoreCase("UNAPPROVED")) {
			fUND_EXCHANGE_MSTRRepositroy.findAllUNAPPROVEDExchange().forEach(conut->{
				
				Map<String,Object> json=new HashMap<String,Object>();
				json.put("exchangeid", conut.getSVE_CODE());
				json.put("exchangename", conut.getSVE_NAME());
				json.put("exchangeshortname", conut.getSVE_SHORT_NAME());
				json.put("comments", conut.getWMS_COMMENTS());
				json.put("status", conut.getWMS_STATUS());
				/*
				 * Adding of settlement Dates
				 */
				FUND_EXCHANGE_HOLIDAY fholy;
				List<FUND_EXCHANGE_HOLIDAY> FEH=conut.getfUND_EXCHANGE_HOLIDAY();
				if(FEH==null) {
					//json.put("settlementid", null);
					json.put("monday",null);
					json.put("tuesday", null);
					json.put("wednesday",null);
					json.put("thursday",null);
					json.put("friday", null);
					json.put("saturday", null);
					json.put("sunday",null);						
				}
				else if(FEH.isEmpty()) {
					//json.put("settlementid", null);
					json.put("monday",null);
					json.put("tuesday", null);
					json.put("wednesday",null);
					json.put("thursday",null);
					json.put("friday", null);
					json.put("saturday", null);
					json.put("sunday",null);
				}
				else {
					fholy=FEH.get(0);
					//json.put("settlementid", fholy.getFEH_ETYPE());
					json.put("monday", fholy.getFEH_D1());
					json.put("tuesday", fholy.getFEH_D2());
					json.put("wednesday", fholy.getFEH_D3());
					json.put("thursday", fholy.getFEH_D4());
					json.put("friday", fholy.getFEH_D5());
					json.put("saturday", fholy.getFEH_D6());
					json.put("sunday", fholy.getFEH_D7());
				}
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
				JsonCountry.add(json);
			});
		}
		
		return new ResponseEntity<List<Map<String,Object>>>(JsonCountry,HttpStatus.OK);
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