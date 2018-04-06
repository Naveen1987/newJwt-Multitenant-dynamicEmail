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
import com.fynisys.model.parameters.FWMS_INDEX;
import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.parameters.FWMS_INDEXRepository;


@RestController
public class FWMS_INDEXController {
	@Autowired
	FUND_USERSRepository fund_UserRepository;
	@Autowired
	FWMS_INDEXRepository fWMS_INDEXRepository;
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;
	
	private static final Logger logger=LoggerFactory.getLogger("FUND INSTRUMENT MASTER");
	
	@PostMapping("/saveindex")
	public ResponseEntity<Map<String,Object>> saveIndex(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		Object WMS_INDEX_DESC=dataMap.get("indexname");
		Object WMS_SHORT_DESC=dataMap.get("indexshortname");
		Object createdby =dataMap.get("createdby");
		Object WMS_COMMENTS=dataMap.get("comment");
		Object SVL_SCREEN=dataMap.get("screentype");
		Object SVL_DESC=dataMap.get("log");
		if(WMS_INDEX_DESC==null||createdby==null||WMS_SHORT_DESC==null||SVL_SCREEN==null||SVL_DESC==null)
		{
			json.put("msg", "Please check Input json, missing some required attributes");
			logger.error("Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		else
		{

			FUND_USERS fuser=isValid(createdby.toString());
			if(fuser==null)
			{
					json.put("msg", "Createdby is not valid user");
					logger.error("Createdby is not valid user");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
			FWMS_INDEX fund_cur=new FWMS_INDEX();
			fund_cur.setWMS_INDEX_DESC(WMS_INDEX_DESC.toString().trim());
			fund_cur.setWMS_SHORT_DESC(WMS_SHORT_DESC.toString());
			if(WMS_COMMENTS!=null) {
			fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
			}
			fund_cur.setWMS_STATUS("Not Approved");
			
			fund_cur.setWMS_ENTER_UID(fuser.getSVC_UID());		
			fund_cur.setWMS_ENTER_DATE(Calendar.getInstance());
//			fund_cur.setWMS_LAST_UPDATE_UID(fuser.getSVC_UID());	
//			fund_cur.setWMS_LAST_UPDATE_DATE(Calendar.getInstance());
			try
			{
				fund_cur=fWMS_INDEXRepository.save(fund_cur);
				if(fund_cur!=null)
				{
					json.put("msg", "saved");
					logger.info("Saved New Index:"+fund_cur.getWMS_INDEX_DESC());
					FUND_USER_LOG ful=new FUND_USER_LOG();
					ful.setSVC_UID(fuser.getSVC_UID());
					ful.setSVL_USERID(fuser.getSVU_NAME());
					ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
					ful.setSVL_TTYPE("CREATE");
					ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
					ful.setSVL_DATE(Calendar.getInstance());
					ful=fUND_USER_LOGRepository.save(ful);
					if(ful!=null) {
						logger.info("Both Record and Logs saved for INDEX:"+fund_cur.getWMS_INDEX_DESC());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
					json.put("indexid", fund_cur.getWMS_INDEX_ID());
					json.put("indexname", fund_cur.getWMS_INDEX_DESC());
					json.put("createdby", fund_cur.getWMS_ENTER_UID());
					json.put("createdon", fund_cur.getWMS_ENTER_DATE());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Index not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Index not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
			
			
		}
	}
	
	@PostMapping("/updateindex")
	public ResponseEntity<Map<String,Object>> updateIndex(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		Object WMS_INDEX_ID=dataMap.get("indexid");
		Object WMS_INDEX_DESC=dataMap.get("indexname");
		Object WMS_SHORT_DESC=dataMap.get("indexshortname");
		Object createdby =dataMap.get("modifiedby");
		Object WMS_COMMENTS=dataMap.get("comment");
		Object SVL_SCREEN=dataMap.get("screentype");
		Object SVL_DESC=dataMap.get("log");
		
		if(WMS_INDEX_ID==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
		{
			json.put("msg", "Please check Input json, missing some required attributes");
			logger.error("Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		else
		{

			FUND_USERS fuser=isValid(createdby.toString());
			if(fuser==null)
			{
					json.put("msg", "Modified by is not valid user");
					logger.error("Modified by is not valid user");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			FWMS_INDEX fund_cur=fWMS_INDEXRepository.findOne(new Integer(WMS_INDEX_ID.toString()));
			if(fund_cur==null) {
				json.put("msg", "No Index found with this id->"+WMS_INDEX_ID.toString());
				logger.error("No Index found with this id->"+WMS_INDEX_ID.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			if(WMS_INDEX_DESC!=null) {
			fund_cur.setWMS_INDEX_DESC(WMS_INDEX_DESC.toString().trim());
			}
			
			if(WMS_SHORT_DESC!=null) {
				fund_cur.setWMS_SHORT_DESC(WMS_SHORT_DESC.toString());
			}
			
			if(WMS_COMMENTS!=null) {
			fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
			}
			
			
			fund_cur.setWMS_LAST_UPDATE_UID(fuser.getSVC_UID());	
			fund_cur.setWMS_LAST_UPDATE_DATE(Calendar.getInstance());
			try
			{
				fund_cur=fWMS_INDEXRepository.save(fund_cur);
				if(fund_cur!=null)
				{
					json.put("msg", "saved");
					logger.info("Index saved:"+fund_cur.getWMS_INDEX_DESC());
					FUND_USER_LOG ful=new FUND_USER_LOG();
					ful.setSVC_UID(fuser.getSVC_UID());
					ful.setSVL_USERID(fuser.getSVU_NAME());
					ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
					ful.setSVL_TTYPE("UPDATE");
					ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
					ful.setSVL_DATE(Calendar.getInstance());
					ful=fUND_USER_LOGRepository.save(ful);
					if(ful!=null) {
						logger.info("Both Record and Logs saved for INDEX:"+fund_cur.getWMS_INDEX_DESC());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is UPDATE but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
					json.put("indexid", fund_cur.getWMS_INDEX_ID());
					json.put("indexname", fund_cur.getWMS_INDEX_DESC());
					json.put("indexshortname", fund_cur.getWMS_SHORT_DESC());
					json.put("comment", fund_cur.getWMS_COMMENTS());
					json.put("status", fund_cur.getWMS_STATUS());
					FUND_USERS user=null;
					if(fund_cur.getWMS_ENTER_UID()!=null) {
						user=getUserName(fund_cur.getWMS_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", fund_cur.getWMS_ENTER_DATE());
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
					
					if(fund_cur.getWMS_APPROVE_UID()!=null) {
						user=getUserName(fund_cur.getWMS_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", fund_cur.getWMS_APPROVE_DATE());
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
					
					if(fund_cur.getWMS_LAST_UPDATE_UID()!=null) {
						user=getUserName(fund_cur.getWMS_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", fund_cur.getWMS_LAST_UPDATE_DATE());
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
					logger.error("Index Not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Index Not saved"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
			
			
		}
	}
	
	@PostMapping("/deleteindex")
	public ResponseEntity<Map<String,Object>> deleteIndex(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		Object WMS_INDEX_ID=dataMap.get("indexid");
		Object createdby =dataMap.get("modifiedby");
		
		if(WMS_INDEX_ID==null||createdby==null)
		{
			json.put("msg", "Please check Input json, missing some required attributes");
			logger.error("Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		else
		{

			FUND_USERS fuser=isValid(createdby.toString());
			if(fuser==null)
			{
					json.put("msg", "Modified by is not valid user");
					logger.error("Modified by is not valid user");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			FWMS_INDEX fund_cur=fWMS_INDEXRepository.findOne(new Integer(WMS_INDEX_ID.toString()));
			if(fund_cur==null) {
				json.put("msg", "No Index found with this id->"+WMS_INDEX_ID.toString());
				logger.error("No Index found with this id->"+WMS_INDEX_ID.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
			
			try
			{
				fWMS_INDEXRepository.delete(fund_cur);
				
					json.put("msg", "Deleted");
					logger.info("Index Detetd:"+fund_cur.getWMS_INDEX_DESC());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
			
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Index Not Detetd"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
			
			
		}
	}
	
		@PostMapping("approveindex")
		public ResponseEntity<Map<String,Object>> approveIndex(RequestEntity<Map<String,Object>> requestData)
		{
			Map<String,Object> json=new HashMap<String,Object>();
			Map<String,Object> dataMap=requestData.getBody();
			
			
			Object WMS_INDEX_ID=dataMap.get("indexid");
			Object createdby =dataMap.get("approvedby");
			Object WMS_COMMENTS=dataMap.get("comment");
			
			if(createdby==null||WMS_INDEX_ID==null)
			{
				json.put("msg", "Please check Input json, missing some required attributes");
				logger.error("Please check Input json, missing some required attributes");
				return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
			}
			else
			{

				FUND_USERS fuser=isValid(createdby.toString());
				if(fuser==null)
				{
						json.put("msg", "Approver is not valid user");
						logger.error("Approver is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				FWMS_INDEX fund_cur=fWMS_INDEXRepository.findOne(new Integer(WMS_INDEX_ID.toString()));
				if(fund_cur==null) {
					json.put("msg", "No Index found with this id->"+WMS_INDEX_ID.toString());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
				if(WMS_COMMENTS!=null) {
				fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
				/*
				 * Approve Status
				 * */
				fund_cur.setWMS_STATUS("Approved");
				fund_cur.setWMS_APPROVE_UID(fuser.getSVC_UID());
				fund_cur.setWMS_APPROVE_DATE(Calendar.getInstance());
				
//				fund_cur.setWMS_LAST_UPDATE_UID(fuser.getSVC_UID());	
//				fund_cur.setWMS_LAST_UPDATE_DATE(Calendar.getInstance());
				
				
				try
				{
					fund_cur=fWMS_INDEXRepository.save(fund_cur);
					if(fund_cur!=null)
					{
						json.put("msg", "saved");
						logger.info("Index Arroved saved :"+fund_cur.getWMS_INDEX_DESC());
						FUND_USERS user=null;
						json.put("indexid", fund_cur.getWMS_INDEX_ID());
						json.put("indexname", fund_cur.getWMS_INDEX_DESC());
						json.put("indexshortname", fund_cur.getWMS_SHORT_DESC());
						json.put("comments", fund_cur.getWMS_COMMENTS());
						json.put("status", fund_cur.getWMS_STATUS());
						if(fund_cur.getWMS_ENTER_UID()!=null) {
							user=getUserName(fund_cur.getWMS_ENTER_UID());
							if(user!=null) {
							json.put("enteredby", user.getSVU_NAME());
							json.put("enteredbyuserid", user.getSVU_USER_NAME());
							json.put("enteredbyuuid", user.getSVC_UID());
							json.put("entereddate", fund_cur.getWMS_ENTER_DATE());
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
						
						if(fund_cur.getWMS_APPROVE_UID()!=null) {
							user=getUserName(fund_cur.getWMS_APPROVE_UID());
							if(user!=null) {
							json.put("approvedby", user.getSVU_NAME());
							json.put("approvedbyuserid", user.getSVU_USER_NAME());
							json.put("approvedbyuuid", user.getSVC_UID());
							json.put("approveddate", fund_cur.getWMS_APPROVE_DATE());
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
						
						if(fund_cur.getWMS_LAST_UPDATE_UID()!=null) {
							user=getUserName(fund_cur.getWMS_LAST_UPDATE_UID());
							if(user!=null) {
							json.put("modifiedby", user.getSVU_NAME());
							json.put("modifiedbyuserid", user.getSVU_USER_NAME());
							json.put("modifiedbyuuid", user.getSVC_UID());
							json.put("modifieddate", fund_cur.getWMS_LAST_UPDATE_DATE());
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
						logger.info("Index Not saved without any error");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
				}
				catch(Exception e)
				{
					json.put("msg", e.getMessage());
					logger.info("Index Not saved"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					
				}
				
				
			}
		}
		
		
		@PostMapping("revokeindex")
		public ResponseEntity<Map<String,Object>> revokeIndex(RequestEntity<Map<String,Object>> requestData)
		{
			Map<String,Object> json=new HashMap<String,Object>();
			Map<String,Object> dataMap=requestData.getBody();
			
			
			Object WMS_INDEX_ID=dataMap.get("indexid");
			Object createdby =dataMap.get("modifiedby");
			Object WMS_COMMENTS=dataMap.get("comment");
			
			if(WMS_INDEX_ID==null||createdby==null||WMS_COMMENTS==null)
			{
				json.put("msg", "Please check Input json, missing some required attributes");
				logger.error("Please check Input json, missing some required attributes");
				return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
			}
			else
			{

				FUND_USERS fuser=isValid(createdby.toString());
				if(fuser==null)
				{
						json.put("msg", "Modified By is not valid user");
						logger.error("Modified By is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				FWMS_INDEX fund_cur=fWMS_INDEXRepository.findOne(new Integer(WMS_INDEX_ID.toString()));
				if(fund_cur==null) {
					json.put("msg", "No Index found with this id->"+WMS_INDEX_ID.toString());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
				
				fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
				/*
				 * Approve Status
				 * */
				fund_cur.setWMS_STATUS("Not Approved");
				fund_cur.setWMS_APPROVE_UID(null);
				fund_cur.setWMS_APPROVE_DATE(null);
//				fund_cur.setWMS_APPROVE_UID(fuser.getSVC_UID());
//				fund_cur.setWMS_APPROVE_DATE(Calendar.getInstance());
				
				fund_cur.setWMS_LAST_UPDATE_UID(fuser.getSVC_UID());	
				fund_cur.setWMS_LAST_UPDATE_DATE(Calendar.getInstance());
				
				try
				{
					fund_cur=fWMS_INDEXRepository.save(fund_cur);
					if(fund_cur!=null)
					{
						json.put("msg", "saved");
						logger.info("Index Revoke saved :"+fund_cur.getWMS_INDEX_DESC());
						FUND_USERS user=null;
						json.put("indexid", fund_cur.getWMS_INDEX_ID());
						json.put("indexname", fund_cur.getWMS_INDEX_DESC());
						json.put("indexshortname", fund_cur.getWMS_SHORT_DESC());
						json.put("comments", fund_cur.getWMS_COMMENTS());
						json.put("status", fund_cur.getWMS_STATUS());
						if(fund_cur.getWMS_ENTER_UID()!=null) {
							user=getUserName(fund_cur.getWMS_ENTER_UID());
							if(user!=null) {
							json.put("enteredby", user.getSVU_NAME());
							json.put("enteredbyuserid", user.getSVU_USER_NAME());
							json.put("enteredbyuuid", user.getSVC_UID());
							json.put("entereddate", fund_cur.getWMS_ENTER_DATE());
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
						
						if(fund_cur.getWMS_APPROVE_UID()!=null) {
							user=getUserName(fund_cur.getWMS_APPROVE_UID());
							if(user!=null) {
							json.put("approvedby", user.getSVU_NAME());
							json.put("approvedbyuserid", user.getSVU_USER_NAME());
							json.put("approvedbyuuid", user.getSVC_UID());
							json.put("approveddate", fund_cur.getWMS_APPROVE_DATE());
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
						
						if(fund_cur.getWMS_LAST_UPDATE_UID()!=null) {
							user=getUserName(fund_cur.getWMS_LAST_UPDATE_UID());
							if(user!=null) {
							json.put("modifiedby", user.getSVU_NAME());
							json.put("modifiedbyuserid", user.getSVU_USER_NAME());
							json.put("modifiedbyuuid", user.getSVC_UID());
							json.put("modifieddate", fund_cur.getWMS_LAST_UPDATE_DATE());
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
						logger.info("Index Not saved without any error");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
				}
				catch(Exception e)
				{
					json.put("msg", e.getMessage());
					logger.info("Index Not saved"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					
				}
				
				
			}
				
		}

		
		@PostMapping("/indecies")
		public ResponseEntity<List<Map<String,Object>>> getAllSectors(RequestEntity<Map<String,Object>> requestData)
		{
			List<Map<String,Object>> JsonCountry=new ArrayList<Map<String,Object>>();
			Map<String,Object> requestJson=requestData.getBody();
			String requestString=(String)requestJson.get("action");
			if(requestString==null) {
				fWMS_INDEXRepository.findAllIndex().forEach(conut->{
					
					Map<String,Object> json=new HashMap<String,Object>();
					FUND_USERS user=null;
					json.put("indexid", conut.getWMS_INDEX_ID());
					json.put("indexname", conut.getWMS_INDEX_DESC());
					json.put("indexshortname", conut.getWMS_SHORT_DESC());
					json.put("comments", conut.getWMS_COMMENTS());
					json.put("status", conut.getWMS_STATUS());
					if(conut.getWMS_ENTER_UID()!=null) {
						user=getUserName(conut.getWMS_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", conut.getWMS_ENTER_DATE());
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
					
					if(conut.getWMS_APPROVE_UID()!=null) {
						user=getUserName(conut.getWMS_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", conut.getWMS_APPROVE_DATE());
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
					
					if(conut.getWMS_LAST_UPDATE_UID()!=null) {
						user=getUserName(conut.getWMS_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", conut.getWMS_LAST_UPDATE_DATE());
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
				fWMS_INDEXRepository.findAllIndex().forEach(conut->{
					FUND_USERS user=null;
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("indexid", conut.getWMS_INDEX_ID());
					json.put("indexname", conut.getWMS_INDEX_DESC());
					json.put("indexshortname", conut.getWMS_SHORT_DESC());
					json.put("comments", conut.getWMS_COMMENTS());
					json.put("status", conut.getWMS_STATUS());
					if(conut.getWMS_ENTER_UID()!=null) {
						user=getUserName(conut.getWMS_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", conut.getWMS_ENTER_DATE());
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
					
					if(conut.getWMS_APPROVE_UID()!=null) {
						user=getUserName(conut.getWMS_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", conut.getWMS_APPROVE_DATE());
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
					
					if(conut.getWMS_LAST_UPDATE_UID()!=null) {
						user=getUserName(conut.getWMS_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", conut.getWMS_LAST_UPDATE_DATE());
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
				fWMS_INDEXRepository.findAllAPPROVEDIndex().forEach(conut->{
					FUND_USERS user=null;
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("indexid", conut.getWMS_INDEX_ID());
					json.put("indexname", conut.getWMS_INDEX_DESC());
					json.put("indexshortname", conut.getWMS_SHORT_DESC());
					json.put("comments", conut.getWMS_COMMENTS());
					json.put("status", conut.getWMS_STATUS());
					if(conut.getWMS_ENTER_UID()!=null) {
						user=getUserName(conut.getWMS_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", conut.getWMS_ENTER_DATE());
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
					
					if(conut.getWMS_APPROVE_UID()!=null) {
						user=getUserName(conut.getWMS_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", conut.getWMS_APPROVE_DATE());
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
					
					if(conut.getWMS_LAST_UPDATE_UID()!=null) {
						user=getUserName(conut.getWMS_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", conut.getWMS_LAST_UPDATE_DATE());
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
				fWMS_INDEXRepository.findAllUNAPPROVEDIndex().forEach(conut->{
					FUND_USERS user=null;
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("indexid", conut.getWMS_INDEX_ID());
					json.put("indexname", conut.getWMS_INDEX_DESC());
					json.put("indexshortname", conut.getWMS_SHORT_DESC());
					json.put("comments", conut.getWMS_COMMENTS());
					json.put("status", conut.getWMS_STATUS());
					if(conut.getWMS_ENTER_UID()!=null) {
						user=getUserName(conut.getWMS_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", conut.getWMS_ENTER_DATE());
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
					
					if(conut.getWMS_APPROVE_UID()!=null) {
						user=getUserName(conut.getWMS_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", conut.getWMS_APPROVE_DATE());
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
					
					if(conut.getWMS_LAST_UPDATE_UID()!=null) {
						user=getUserName(conut.getWMS_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", conut.getWMS_LAST_UPDATE_DATE());
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