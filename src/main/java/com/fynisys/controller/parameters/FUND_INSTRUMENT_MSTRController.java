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
import com.fynisys.model.parameters.FUND_INSTRUMENT_MSTR;
import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.parameters.FUND_INSTRUMENT_MSTRRepositroy;

@RestController
public class FUND_INSTRUMENT_MSTRController {
	@Autowired
	FUND_USERSRepository fund_UserRepository;
	
	@Autowired
	FUND_INSTRUMENT_MSTRRepositroy fUND_INSTRUMENT_MSTRRepositroy;  
	
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;
	
	private static final Logger logger=LoggerFactory.getLogger("FUND INSTRUMENT MASTER");
	
	@PostMapping("/saveasset")
	public ResponseEntity<Map<String,Object>> saveAsset(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		Object SVI_NAME=dataMap.get("assetname");
		Object SVI_SHORT_NAME=dataMap.get("assetshortname");
		//Object SVI_GROUP=dataMap.get("group");
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
			FUND_INSTRUMENT_MSTR fund_im=new FUND_INSTRUMENT_MSTR();
			fund_im.setSVI_NAME(SVI_NAME.toString().trim());
			fund_im.setSVI_SHORT_NAME(SVI_SHORT_NAME.toString().trim());
			fund_im.setSVC_UID(fuser.getSVC_UID());
			//fund_im.setSVI_GROUP(new Long(SVI_GROUP.toString()));
			
			fund_im.setWMS_COMMENTS(WMS_COMMENTS.toString());
			fund_im.setWMS_STATUS("Not Approved");
			
			fund_im.setIV_ENTER_UID(fuser.getSVC_UID());
			fund_im.setIV_ENTER_DATE(Calendar.getInstance());
//			fund_im.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
//			fund_im.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
			try {
				fund_im=fUND_INSTRUMENT_MSTRRepositroy.save(fund_im);
				if(fund_im!=null) {
					logger.info("Asset Saved :"+fund_im.getSVI_NAME());
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
						logger.info("Both Record and Logs saved for ASSETS:"+fund_im.getSVI_NAME());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
					json.put("assetid", fund_im.getSVI_CODE());
					json.put("assetname", fund_im.getSVI_NAME());
					json.put("createdby", fund_im.getIV_ENTER_UID());
					json.put("createdon", fund_im.getIV_ENTER_DATE());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Asset Not Saved without error");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				logger.error("Asset Not Saved :-"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
		}
		
	}
	
	@PostMapping("/updateasset")
	public ResponseEntity<Map<String,Object>> updateAsset(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		Object SVI_CODE=dataMap.get("assetid");
		Object SVI_NAME=dataMap.get("assetname");
		//Object SVI_GROUP=dataMap.get("group");
		Object SVI_SHORT_NAME=dataMap.get("assetshortname");
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
			FUND_INSTRUMENT_MSTR fund_im=fUND_INSTRUMENT_MSTRRepositroy.findOne(new Integer(SVI_CODE.toString()));
			if(fund_im==null) {
				json.put("msg", "No Asset found with this id->"+SVI_CODE.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			if(SVI_NAME!=null) {
				fund_im.setSVI_NAME(SVI_NAME.toString().trim());
			}
			if(SVI_SHORT_NAME!=null) {
			fund_im.setSVI_SHORT_NAME(SVI_SHORT_NAME.toString().trim());
			}
//			if(SVI_GROUP!=null) {
//				fund_im.setSVI_GROUP(new Long(SVI_GROUP.toString()));
//			}
			if(WMS_COMMENTS!=null) {
			fund_im.setWMS_COMMENTS(WMS_COMMENTS.toString().trim());
			}
			
			
			fund_im.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
			fund_im.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
			
			try {
				fund_im=fUND_INSTRUMENT_MSTRRepositroy.save(fund_im);
				if(fund_im!=null) {
					json.put("msg", "saved");
					logger.info("Updated Successfully:-"+fund_im.getSVI_NAME());
					FUND_USER_LOG ful=new FUND_USER_LOG();
					ful.setSVC_UID(fuser.getSVC_UID());
					ful.setSVL_USERID(fuser.getSVU_NAME());
					ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
					ful.setSVL_TTYPE("UPDATE");
					ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
					ful.setSVL_DATE(Calendar.getInstance());
					ful=fUND_USER_LOGRepository.save(ful);
					if(ful!=null) {
						logger.info("Both Record and Logs saved for ASSETS:"+fund_im.getSVI_NAME());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
					json.put("assetid", fund_im.getSVI_CODE());
					json.put("assetname", fund_im.getSVI_NAME());
					json.put("assetshortname", fund_im.getSVI_SHORT_NAME());
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
					logger.error("Asset Not Saved without error");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				logger.error("Not Updated ->"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
		}
		
	}
	
	@PostMapping("/deleteasset")
	public ResponseEntity<Map<String,Object>> deleteAsset(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		Object SVI_CODE=dataMap.get("assetid");
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
			FUND_INSTRUMENT_MSTR fund_im=fUND_INSTRUMENT_MSTRRepositroy.findOne(new Integer(SVI_CODE.toString()));
			if(fund_im==null) {
				json.put("msg", "No Asset found with this id->"+SVI_CODE.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
						
			try {
				fUND_INSTRUMENT_MSTRRepositroy.delete(fund_im);
			
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
	
	
	@PostMapping("/approveasset")
	public ResponseEntity<Map<String,Object>> approveAsset(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		
		Object SVI_CODE=dataMap.get("assetid");
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
			FUND_INSTRUMENT_MSTR fund_im=fUND_INSTRUMENT_MSTRRepositroy.findOne(new Integer(SVI_CODE.toString()));
			if(fund_im==null) {
				json.put("msg", "No Asset found with this id->"+SVI_CODE.toString());
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
				fund_im=fUND_INSTRUMENT_MSTRRepositroy.save(fund_im);
				if(fund_im!=null) {
					json.put("msg", "saved");
					logger.info("Updated Successfully:-"+fund_im.getSVI_NAME());
					FUND_USERS user=null;
					json.put("assetid", fund_im.getSVI_CODE());
					json.put("assetname", fund_im.getSVI_NAME());
					json.put("assetshortname", fund_im.getSVI_SHORT_NAME());
					json.put("comments", fund_im.getWMS_COMMENTS());
					json.put("status", fund_im.getWMS_STATUS());
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
					logger.error("Asset Not Saved without error");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				logger.error("Not Updated Successfully:-"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
		}
		
	}
	
	@PostMapping("/revokeasset")
	public ResponseEntity<Map<String,Object>> revokeAsset(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		
		Object SVI_CODE=dataMap.get("assetid");
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
			FUND_INSTRUMENT_MSTR fund_im=fUND_INSTRUMENT_MSTRRepositroy.findOne(new Integer(SVI_CODE.toString()));
			if(fund_im==null) {
				json.put("msg", "No Asset found with this id->"+SVI_CODE.toString());
				logger.error("No Asset found with this id->"+SVI_CODE.toString());
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
				fund_im=fUND_INSTRUMENT_MSTRRepositroy.save(fund_im);
				if(fund_im!=null) {
					json.put("msg", "saved");
					logger.info("Saved->"+ fund_im.getSVI_NAME());
					FUND_USERS user=null;
					json.put("assetid", fund_im.getSVI_CODE());
					json.put("assetname", fund_im.getSVI_NAME());
					json.put("assetshortname", fund_im.getSVI_SHORT_NAME());
					json.put("comments", fund_im.getWMS_COMMENTS());
					json.put("status", fund_im.getWMS_STATUS());
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
					logger.error("Asset Not Saved without error");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				logger.error("Not Saved "+ e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
		}
		
	}
	

	
	@PostMapping("/assets")
	public ResponseEntity<List<Map<String,Object>>> getAllAssets(RequestEntity<Map<String,Object>> requestData)
	{
		List<Map<String,Object>> JsonCountry=new ArrayList<Map<String,Object>>();
		Map<String,Object> requestJson=requestData.getBody();
		String requestString=(String)requestJson.get("action");
		if(requestString==null) {
			fUND_INSTRUMENT_MSTRRepositroy.findAllAssets().forEach(conut->{
				Map<String,Object> json=new HashMap<String,Object>();
				FUND_USERS user=null;
				json.put("assetid", conut.getSVI_CODE());
				json.put("assetname", conut.getSVI_NAME());
				json.put("assetshortname", conut.getSVI_SHORT_NAME());
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
			fUND_INSTRUMENT_MSTRRepositroy.findAllAssets().forEach(conut->{
				FUND_USERS user=null;
				Map<String,Object> json=new HashMap<String,Object>();
				json.put("assetid", conut.getSVI_CODE());
				json.put("assetname", conut.getSVI_NAME());
				json.put("assetshortname", conut.getSVI_SHORT_NAME());
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
			fUND_INSTRUMENT_MSTRRepositroy.findAllAPPROVEDAssets().forEach(conut->{
				FUND_USERS user=null;
				Map<String,Object> json=new HashMap<String,Object>();
				json.put("assetid", conut.getSVI_CODE());
				json.put("assetname", conut.getSVI_NAME());
				json.put("assetshortname", conut.getSVI_SHORT_NAME());
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
		}else if(requestString.equalsIgnoreCase("UNAPPROVED")) {
			fUND_INSTRUMENT_MSTRRepositroy.findAllUNAPPROVEDAssets().forEach(conut->{
				FUND_USERS user=null;
				Map<String,Object> json=new HashMap<String,Object>();
				json.put("assetid", conut.getSVI_CODE());
				json.put("assetname", conut.getSVI_NAME());
				json.put("assetshortname", conut.getSVI_SHORT_NAME());
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