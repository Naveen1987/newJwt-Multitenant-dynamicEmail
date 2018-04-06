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
import com.fynisys.model.parameters.FUND_INDUSTRY_MSTR;
import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.parameters.FUND_INDUSTRY_MSTRRepositroy;

@RestController
public class FUND_INDUSTRY_MSTRController {

	@Autowired
	FUND_USERSRepository fund_UserRepository;
	
	@Autowired
	FUND_INDUSTRY_MSTRRepositroy fUND_INDUSTRY_MSTRRepositroy;  
	
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;
	
	private static final Logger logger=LoggerFactory.getLogger("FUND INDUSTRY MASTER");
	
	@PostMapping("/savesector")
	public ResponseEntity<Map<String,Object>> saveSector(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		Object SVI_NAME=dataMap.get("sectorname");
		Object SVI_SHORT_NAME=dataMap.get("sectorshortname");
		Object createdby =dataMap.get("createdby");
		Object WMS_COMMENTS=dataMap.get("comment");
		Object SVL_SCREEN=dataMap.get("screentype");
		Object SVL_DESC=dataMap.get("log");
		if(SVI_NAME==null||SVI_SHORT_NAME==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null) {
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
			FUND_INDUSTRY_MSTR fund_im=new FUND_INDUSTRY_MSTR();
			fund_im.setSVI_NAME(SVI_NAME.toString().trim());
			fund_im.setSVI_SHORT_NAME(SVI_SHORT_NAME.toString().trim());
			fund_im.setSVC_UID(fuser.getSVC_UID());
			
			fund_im.setWMS_COMMENTS(WMS_COMMENTS.toString());
			fund_im.setWMS_STATUS("Not Approved");
			
			fund_im.setIV_ENTER_UID(fuser.getSVC_UID());
			fund_im.setIV_ENTER_DATE(Calendar.getInstance());
//			fund_im.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
//			fund_im.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
			try {
				fund_im=fUND_INDUSTRY_MSTRRepositroy.save(fund_im);
				if(fund_im!=null) {
					logger.info("Sector Saved :"+fund_im.getSVI_NAME());
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
						logger.info("Both Record and Logs saved for SECTOR:"+fund_im.getSVI_NAME());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
					json.put("sectorid", fund_im.getSVI_CODE());
					json.put("sectorname", fund_im.getSVI_NAME());
					json.put("createdby", fund_im.getIV_ENTER_UID());
					json.put("createdon", fund_im.getIV_ENTER_DATE());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Sector Not Saved without error");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				logger.error("Sector Not Saved :-"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
		}
		
	}
	
	@PostMapping("/updatesector")
	public ResponseEntity<Map<String,Object>> updateSector(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		Object SVI_CODE=dataMap.get("sectorid");
		Object SVI_NAME=dataMap.get("sectorname");
		Object SVI_SHORT_NAME=dataMap.get("sectorshortname");
		Object createdby =dataMap.get("modifiedby");
		Object WMS_COMMENTS=dataMap.get("comment");
		Object SVL_SCREEN=dataMap.get("screentype");
		Object SVL_DESC=dataMap.get("log");
		
		if(createdby==null||SVI_CODE==null||SVL_SCREEN==null||SVL_DESC==null) {
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
			FUND_INDUSTRY_MSTR fund_im=fUND_INDUSTRY_MSTRRepositroy.findOne(new Integer(SVI_CODE.toString()));
			if(fund_im==null) {
				json.put("msg", "No Sector found with this id->"+SVI_CODE.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			if(SVI_NAME!=null) {
				fund_im.setSVI_NAME(SVI_NAME.toString().trim());
			}
			if(SVI_SHORT_NAME!=null) {
			fund_im.setSVI_SHORT_NAME(SVI_SHORT_NAME.toString().trim());
			}
			if(WMS_COMMENTS!=null) {
			fund_im.setWMS_COMMENTS(WMS_COMMENTS.toString().trim());
			}
			
			fund_im.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
			fund_im.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
			
			try {
				fund_im=fUND_INDUSTRY_MSTRRepositroy.save(fund_im);
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
						logger.info("Both Record and Logs saved for SECTOR:"+fund_im.getSVI_NAME());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
					logger.info("Updated Successfully:-"+fund_im.getSVI_NAME());
					json.put("sectorid", fund_im.getSVI_CODE());
					json.put("sectorname", fund_im.getSVI_NAME());
					json.put("sectorshortname", fund_im.getSVI_SHORT_NAME());
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
					
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Sector Not Saved without error");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				logger.error("Not Updated ->"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
		}
		
	}
	
	@PostMapping("/deletesector")
	public ResponseEntity<Map<String,Object>> deleteSector(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		Object SVI_CODE=dataMap.get("sectorid");
		Object createdby =dataMap.get("modifiedby");
		
		if(createdby==null||SVI_CODE==null) {
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
			FUND_INDUSTRY_MSTR fund_im=fUND_INDUSTRY_MSTRRepositroy.findOne(new Integer(SVI_CODE.toString()));
			if(fund_im==null) {
				json.put("msg", "No Sector found with this id->"+SVI_CODE.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
						
			try {
				fUND_INDUSTRY_MSTRRepositroy.delete(fund_im);
			
					json.put("msg", "Deleted");
					logger.info("Detetd Successfully:-"+fund_im.getSVI_NAME());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				
			catch (Exception e) {
				json.put("msg", e.getMessage());
				logger.error("Not Deleted ->"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
		}
		
	}
	
	
	@PostMapping("/approvesector")
	public ResponseEntity<Map<String,Object>> approveSector(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		
		Object SVI_CODE=dataMap.get("sectorid");
		Object approvedby =dataMap.get("approvedby");
		Object WMS_COMMENTS=dataMap.get("comment");
		
		if(approvedby==null||SVI_CODE==null) {
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
			FUND_INDUSTRY_MSTR fund_im=fUND_INDUSTRY_MSTRRepositroy.findOne(new Integer(SVI_CODE.toString()));
			if(fund_im==null) {
				json.put("msg", "No Sector found with this id->"+SVI_CODE.toString());
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
				fund_im=fUND_INDUSTRY_MSTRRepositroy.save(fund_im);
				if(fund_im!=null) {
					json.put("msg", "saved");
					logger.info("Updated Successfully:-"+fund_im.getSVI_NAME());
					json.put("sectorid", fund_im.getSVI_CODE());
					json.put("sectorname", fund_im.getSVI_NAME());
					json.put("sectorshortname", fund_im.getSVI_SHORT_NAME());
					json.put("comments", fund_im.getWMS_COMMENTS());
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
					
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Sector Not Saved without error");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				logger.error("Not Updated Successfully:-"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
		}
		
	}
	
	@PostMapping("/revokesector")
	public ResponseEntity<Map<String,Object>> revokeSector(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		
		Object SVI_CODE=dataMap.get("sectorid");
		Object createdby =dataMap.get("modifiedby");
		Object WMS_COMMENTS=dataMap.get("comment");
		
		if(createdby==null||SVI_CODE==null||WMS_COMMENTS==null) {
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
			FUND_INDUSTRY_MSTR fund_im=fUND_INDUSTRY_MSTRRepositroy.findOne(new Integer(SVI_CODE.toString()));
			if(fund_im==null) {
				json.put("msg", "No Sector found with this id->"+SVI_CODE.toString());
				logger.error("No Sector found with this id->"+SVI_CODE.toString());
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
				fund_im=fUND_INDUSTRY_MSTRRepositroy.save(fund_im);
				if(fund_im!=null) {
					json.put("msg", "saved");
					logger.info("Saved->"+ fund_im.getSVI_NAME());
					json.put("sectorid", fund_im.getSVI_CODE());
					json.put("sectorname", fund_im.getSVI_NAME());
					json.put("sectorshortname", fund_im.getSVI_SHORT_NAME());
					json.put("comments", fund_im.getWMS_COMMENTS());
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
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				else {
					logger.error("Sector Not Saved without error");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				logger.error("Not Saved "+ e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
		}
		
	}
	

	
	@PostMapping("/sectors")
	public ResponseEntity<List<Map<String,Object>>> getAllSectors(RequestEntity<Map<String,Object>> requestData)
	{
		List<Map<String,Object>> JsonCountry=new ArrayList<Map<String,Object>>();
		Map<String,Object> requestJson=requestData.getBody();
		String requestString=(String)requestJson.get("action");
		if(requestString==null) {
			fUND_INDUSTRY_MSTRRepositroy.findAllSectors().forEach(conut->{
				FUND_USERS user=null;
				Map<String,Object> json=new HashMap<String,Object>();
				json.put("sectorid", conut.getSVI_CODE());
				json.put("sectorname", conut.getSVI_NAME());
				json.put("sectorshortname", conut.getSVI_SHORT_NAME());
				json.put("comments", conut.getWMS_COMMENTS());
				json.put("status", conut.getWMS_STATUS());
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
			fUND_INDUSTRY_MSTRRepositroy.findAllSectors().forEach(conut->{
				FUND_USERS user=null;
				Map<String,Object> json=new HashMap<String,Object>();
				json.put("sectorid", conut.getSVI_CODE());
				json.put("sectorname", conut.getSVI_NAME());
				json.put("sectorshortname", conut.getSVI_SHORT_NAME());
				json.put("comments", conut.getWMS_COMMENTS());
				json.put("status", conut.getWMS_STATUS());
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
			fUND_INDUSTRY_MSTRRepositroy.findAllAPPROVEDSectors().forEach(conut->{
				
				Map<String,Object> json=new HashMap<String,Object>();
				json.put("sectorid", conut.getSVI_CODE());
				json.put("sectorname", conut.getSVI_NAME());
				json.put("sectorshortname", conut.getSVI_SHORT_NAME());
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
				JsonCountry.add(json);
			});
		}else if(requestString.equalsIgnoreCase("UNAPPROVED")) {
			fUND_INDUSTRY_MSTRRepositroy.findAllUNAPPROVEDSectors().forEach(conut->{
				FUND_USERS user=null;
				Map<String,Object> json=new HashMap<String,Object>();
				json.put("sectorid", conut.getSVI_CODE());
				json.put("sectorname", conut.getSVI_NAME());
				json.put("sectorshortname", conut.getSVI_SHORT_NAME());
				json.put("comments", conut.getWMS_COMMENTS());
				json.put("status", conut.getWMS_STATUS());
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