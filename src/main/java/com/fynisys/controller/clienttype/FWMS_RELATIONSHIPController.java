package com.fynisys.controller.clienttype;

import org.springframework.web.bind.annotation.RestController;

import com.fynisys.model.FUND_USERS;
import com.fynisys.model.FUND_USER_LOG;
import com.fynisys.model.clienttype.FWMS_RELATIONSHIP;
import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.clienttype.FWMS_RELATIONSHIPRepository;
import com.fynisys.repository.clienttype.beans.RelationshipBean;

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


// TODO: Auto-generated Javadoc
/**
 * The Class FWMS_RELATIONSHIPController.
 */
@RestController
public class FWMS_RELATIONSHIPController{	
	
	/** The WM S RELATIONSHIP repository. */
	@Autowired
	FWMS_RELATIONSHIPRepository fWMS_RELATIONSHIPRepository;
	
	/** The fund user repository. */
	@Autowired
	FUND_USERSRepository fund_UserRepository;

	
	/** The UN D USE R LOG repository. */
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;

	
	/** The Constant logger. */
	private static final Logger logger=LoggerFactory.getLogger("FWMS_RELATIONSHIP");
	
	/**
	 * Save.
	 *
	 * @param requestData the request data
	 * @return the response entity
	 */
	@PostMapping("/saverelation")
    public ResponseEntity<?> save(RequestEntity<Map<String, Object>>requestData){
	Map<String,Object> requestJson=	requestData.getBody();
	Map<String,Object> json=new HashMap<>();
	Object  WMS_RELATIONSHIP_DESC=requestJson.get("relationname");
	Object WMS_SHORT_DESC=requestJson.get("relationshortname");
	Object createdby =requestJson.get("createdby");
	Object WMS_COMMENTS=requestJson.get("comment");
	Object SVL_SCREEN=requestJson.get("screentype");
	Object SVL_DESC=requestJson.get("log");
	if(WMS_RELATIONSHIP_DESC==null||WMS_SHORT_DESC==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null) {
		json.put("msg", "Parameter Missing");
		return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
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
		FWMS_RELATIONSHIP fund_cur=new FWMS_RELATIONSHIP();
		
		
		fund_cur.setWMS_RELATIONSHIP_DESC(WMS_RELATIONSHIP_DESC.toString().trim());
		fund_cur.setWMS_SHORT_DESC(WMS_SHORT_DESC.toString().trim());
		if(WMS_COMMENTS!=null) {
		fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
		}
		fund_cur.setWMS_STATUS("Not Approved");
		
		fund_cur.setWMS_ENTER_UID(fuser.getSVC_UID());
		fund_cur.setWMS_ENTER_DATE(Calendar.getInstance());

		try
		{
			fund_cur=fWMS_RELATIONSHIPRepository.save(fund_cur);
			if(fund_cur!=null)
			{
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
						logger.info("Both Record and Logs saved for relation Type:"+fund_cur.getWMS_RELATIONSHIP_DESC());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
				
				logger.info("Saved New relation:"+fund_cur.getWMS_RELATIONSHIP_DESC());
				json.put("relationid", fund_cur.getWMS_RELATIONSHIP_ID());
				json.put("relationname", fund_cur.getWMS_RELATIONSHIP_DESC());
				json.put("relationshortname", fund_cur.getWMS_SHORT_DESC());
				json.put("createdby", fund_cur.getWMS_ENTER_UID());
				json.put("createdon", fund_cur.getWMS_ENTER_DATE());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
			}
			else {
				json.put("msg","Not saved");
				logger.error("relation not saved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e)
		{
			json.put("msg", e.getMessage());
			logger.error("relation not saved :"+e.getMessage());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			
		}
		
		
	}
}
	
	/**
	 * Update.
	 *
	 * @param requestData the request data
	 * @return the response entity
	 */
	@PostMapping("/updaterelation")
	public ResponseEntity<Map<String,Object>> update(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> requestJson=	requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object WMS_RELATIONSHIP_ID =requestJson.get("relationid");
		Object WMS_RELATIONSHIP_DESC=requestJson.get("relationname");
		Object WMS_SHORT_DESC=requestJson.get("relationshortname");
		
		Object createdby =requestJson.get("modifiedby");
		Object WMS_COMMENTS=requestJson.get("comment");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		if(WMS_RELATIONSHIP_ID==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
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
			FWMS_RELATIONSHIP fund_cur=fWMS_RELATIONSHIPRepository.findOne(new Long(WMS_RELATIONSHIP_ID.toString()));
			if(fund_cur==null) {
				json.put("msg", "No relation Type found with this id->"+WMS_RELATIONSHIP_ID.toString());
				logger.error("No relation Type found with this id->"+WMS_RELATIONSHIP_ID.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
			if(WMS_RELATIONSHIP_DESC!=null) {
			fund_cur.setWMS_RELATIONSHIP_DESC(WMS_RELATIONSHIP_DESC.toString().trim());
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
				fund_cur=fWMS_RELATIONSHIPRepository.save(fund_cur);
				if(fund_cur!=null)
				{
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
							json.put("logs","logs are saved");
							logger.info("Both Record and Logs saved for relation:"+fund_cur.getWMS_RELATIONSHIP_DESC());
							
						}
						else {
							logger.info("Record is saved but logs can't saved due error in saving of logs");
							json.put("logs","Record is saved but logs can't saved due error in saving of logs");
						}
					
					
					logger.info("relation saved:"+fund_cur.getWMS_RELATIONSHIP_DESC());
					json.put("relationid", fund_cur.getWMS_RELATIONSHIP_ID());
					json.put("relationname", fund_cur.getWMS_RELATIONSHIP_DESC());
					json.put("relationshortname", fund_cur.getWMS_SHORT_DESC());
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
					logger.error("Currency Not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("relation Not saved"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
			
			
		}
	}
	
	

	/**
	 * Approved.
	 *
	 * @param requestData the request data
	 * @return the response entity
	 */
	@PostMapping("/approvedrelation")
	public ResponseEntity<Map<String,Object>> approved(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> requestJson=	requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object WMS_RELATIONSHIP_ID =requestJson.get("relationid");
		Object createdby =requestJson.get("approvedby");
		Object WMS_COMMENTS=requestJson.get("comment");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		if(WMS_RELATIONSHIP_ID==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
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
			FWMS_RELATIONSHIP fund_cur=fWMS_RELATIONSHIPRepository.findOne(new Long(WMS_RELATIONSHIP_ID.toString()));
			if(fund_cur==null) {
				json.put("msg", "No relation Type found with this id->"+WMS_RELATIONSHIP_ID.toString());
				logger.error("No relation Type found with this id->"+WMS_RELATIONSHIP_ID.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
					
			if(WMS_COMMENTS!=null) {
				fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
			fund_cur.setWMS_STATUS("Approved");
			fund_cur.setWMS_APPROVE_UID(fuser.getSVC_UID());
			fund_cur.setWMS_APPROVE_DATE(Calendar.getInstance());
			try
			{
				fund_cur=fWMS_RELATIONSHIPRepository.save(fund_cur);
				if(fund_cur!=null)
				{
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
							json.put("logs","logs are saved");
							logger.info("Both Record and Logs saved for relation:"+fund_cur.getWMS_RELATIONSHIP_DESC());
							
						}
						else {
							logger.info("Record is saved but logs can't saved due error in saving of logs");
							json.put("logs","Record is saved but logs can't saved due error in saving of logs");
						}
					
					
					logger.info("relation saved:"+fund_cur.getWMS_RELATIONSHIP_DESC());
					json.put("relationid", fund_cur.getWMS_RELATIONSHIP_ID());
					json.put("relationname", fund_cur.getWMS_RELATIONSHIP_DESC());
					json.put("relationshortname", fund_cur.getWMS_SHORT_DESC());
					
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
					logger.error("Currency Not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("relation Not saved"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
			
			
		}
	}
	
	
	/**
	 * Revoke.
	 *
	 * @param requestData the request data
	 * @return the response entity
	 */
	@PostMapping("/revokerelation")
	public ResponseEntity<Map<String,Object>> revoke(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> requestJson=	requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object WMS_RELATIONSHIP_ID =requestJson.get("relationid");
		Object createdby =requestJson.get("modifiedby");
		Object WMS_COMMENTS=requestJson.get("comment");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		if(WMS_RELATIONSHIP_ID==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
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
			FWMS_RELATIONSHIP fund_cur=fWMS_RELATIONSHIPRepository.findOne(new Long(WMS_RELATIONSHIP_ID.toString()));
			if(fund_cur==null) {
				json.put("msg", "No relation Type found with this id->"+WMS_RELATIONSHIP_ID.toString());
				logger.error("No relation Type found with this id->"+WMS_RELATIONSHIP_ID.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		    
			if(WMS_COMMENTS!=null) {
//				fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
			fund_cur.setWMS_STATUS("Not Approved");
			fund_cur.setWMS_APPROVE_UID(null);
			fund_cur.setWMS_APPROVE_DATE(null);
			fund_cur.setWMS_LAST_UPDATE_UID(fuser.getSVC_UID());	
			fund_cur.setWMS_LAST_UPDATE_DATE(Calendar.getInstance());
			try
			{
				fund_cur=fWMS_RELATIONSHIPRepository.save(fund_cur);
				if(fund_cur!=null)
				{
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
							json.put("logs","logs are saved");
							logger.info("Both Record and Logs saved for relation:"+fund_cur.getWMS_RELATIONSHIP_DESC());
							
						}
						else {
							logger.info("Record is saved but logs can't saved due error in saving of logs");
							json.put("logs","Record is saved but logs can't saved due error in saving of logs");
						}
					
					
					logger.info("relation saved:"+fund_cur.getWMS_RELATIONSHIP_DESC());
					json.put("relationid", fund_cur.getWMS_RELATIONSHIP_ID());
					json.put("relationname", fund_cur.getWMS_RELATIONSHIP_DESC());
					json.put("relationshortname", fund_cur.getWMS_SHORT_DESC());
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
					logger.error("Currency Not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("relation Not saved"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
			
			
		}
	}
	
	/**
	 * Delete.
	 *
	 * @param requestData the request data
	 * @return the response entity
	 */
	@PostMapping("/deleterelation")
	public ResponseEntity<Map<String,Object>> delete(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> requestJson=	requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object WMS_RELATIONSHIP_ID =requestJson.get("relationid");
		Object createdby =requestJson.get("modifiedby");
//		Object WMS_COMMENTS=requestJson.get("comment");
//		Object SVL_SCREEN=requestJson.get("screentype");
//		Object SVL_DESC=requestJson.get("log");
		
		if(WMS_RELATIONSHIP_ID==null||createdby==null)
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
			FWMS_RELATIONSHIP fund_cur=fWMS_RELATIONSHIPRepository.findOne(new Long(WMS_RELATIONSHIP_ID.toString()));
			if(fund_cur==null) {
				json.put("msg", "No relation Type found with this id->"+WMS_RELATIONSHIP_ID.toString());
				logger.error("No relation Type found with this id->"+WMS_RELATIONSHIP_ID.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
				
			try
			{
				fWMS_RELATIONSHIPRepository.delete(fund_cur);
				json.put("msg", "DELETED");
				logger.info("Deleted"+WMS_RELATIONSHIP_ID.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
//						json.put("msg", "DELETED");
//						FUND_USER_LOG ful=new FUND_USER_LOG();
//						ful.setSVC_UID(fuser.getSVC_UID());
//						ful.setSVL_USERID(fuser.getSVU_NAME());
//						ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
//						ful.setSVL_TTYPE("DELETED");
//						ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
//						ful.setSVL_DATE(Calendar.getInstance());
//						ful=fUND_USER_LOGRepository.save(ful);
//						if(ful!=null) {
//							json.put("logs","logs are saved");
//							logger.info("Both Record and Logs saved for relation:"+fund_cur.getWMS_RELATIONSHIP_DESC());
//							
//						}
//						else {
//							logger.info("Record is saved but logs can't saved due error in saving of logs");
//							json.put("logs","Record is saved but logs can't saved due error in saving of logs");
//						}
//					
					
					
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("relation Not saved"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
			
			
		}
	}
	
	
	/**
	 * Gets the allrelation type.
	 *
	 * @param action the action
	 * @return the allrelation type
	 */
	@GetMapping("/relations")
	public ResponseEntity<?> getAllrelationType(@RequestParam(value="action",required=false) String action){
		List<Map<String,Object>> JsonrelationType=new ArrayList<Map<String,Object>>();
		List<FWMS_RELATIONSHIP> allrelationType=null;
		if(action==null) {
			allrelationType=fWMS_RELATIONSHIPRepository.findAllFWMS_RELATIONSHIP();
		}else if(action.equalsIgnoreCase("ALL")) {
			allrelationType=fWMS_RELATIONSHIPRepository.findAllFWMS_RELATIONSHIP();
		}else if(action.equalsIgnoreCase("APPROVED")) {
			allrelationType=fWMS_RELATIONSHIPRepository.findAllAPPROVEDFWMS_RELATIONSHIP();
		}else if(action.equalsIgnoreCase("UNAPPROVED"))
		{
			allrelationType=fWMS_RELATIONSHIPRepository.findAllUNAPPROVEDFWMS_RELATIONSHIP();
		}
		if(allrelationType!=null)
		{
			allrelationType.forEach(fund_cur->{
				Map<String,Object>json=new HashMap<>();
				json.put("relationid", fund_cur.getWMS_RELATIONSHIP_ID());
				json.put("relationname", fund_cur.getWMS_RELATIONSHIP_DESC());
				json.put("relationshortname", fund_cur.getWMS_SHORT_DESC());
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
				JsonrelationType.add(json);
			});	
			return new ResponseEntity<List<Map<String,Object>>>(JsonrelationType,HttpStatus.OK);
			
		}else{
			Map<String,Object> json=new HashMap<>();
			json.put("msg", "Not found");
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
	
	}
	
	
	/**
	 * Gets the allrelation type page.
	 *
	 * @param action the action
	 * @param paramsearch the paramsearch
	 * @param page the page
	 * @return the allrelation type page
	 */
	@GetMapping("/relationspage")
	public ResponseEntity<?> getAllrelationTypePage(@RequestParam(value="action",required=false) String action,
			@RequestParam(value="paramSearch",required=false) String paramsearch,
			Pageable page){
		Page<FWMS_RELATIONSHIP> 	allrelationType=null;
		if(action==null) {
			if(paramsearch!=null) {
				if(paramsearch.isEmpty()==false)
				{
					allrelationType=fWMS_RELATIONSHIPRepository.findAllFWMS_RELATIONSHIPSearching(paramsearch,page);
				}else {
					allrelationType=fWMS_RELATIONSHIPRepository.findAllFWMS_RELATIONSHIP(page);	
				}
			}else {
				allrelationType=fWMS_RELATIONSHIPRepository.findAllFWMS_RELATIONSHIP(page);
			}
			
		}else if(action.equalsIgnoreCase("ALL")) {
			if(paramsearch!=null) {
				if(paramsearch.isEmpty()==false)
				{
					allrelationType=fWMS_RELATIONSHIPRepository.findAllFWMS_RELATIONSHIPSearching(paramsearch,page);
				}else {
					allrelationType=fWMS_RELATIONSHIPRepository.findAllFWMS_RELATIONSHIP(page);	
				}
			}else {
				allrelationType=fWMS_RELATIONSHIPRepository.findAllFWMS_RELATIONSHIP(page);
			}
		}else if(action.equalsIgnoreCase("APPROVED")) {
			if(paramsearch!=null) {
				if(paramsearch.isEmpty()==false)
				{
					allrelationType=fWMS_RELATIONSHIPRepository.findAllAPPROVEDFWMS_RELATIONSHIPSearching(paramsearch,page);
				}else {
					allrelationType=fWMS_RELATIONSHIPRepository.findAllAPPROVEDFWMS_RELATIONSHIP(page);	
				}
			}else {
				allrelationType=fWMS_RELATIONSHIPRepository.findAllAPPROVEDFWMS_RELATIONSHIP(page);
			}
		}else if(action.equalsIgnoreCase("UNAPPROVED"))
		{
			if(paramsearch!=null) {
				if(paramsearch.isEmpty()==false)
				{
					allrelationType=fWMS_RELATIONSHIPRepository.findAllUNAPPROVEDFWMS_RELATIONSHIPSearching(paramsearch,page);
				}else {
					allrelationType=fWMS_RELATIONSHIPRepository.findAllUNAPPROVEDFWMS_RELATIONSHIP(page);	
				}
			}else {
				allrelationType=fWMS_RELATIONSHIPRepository.findAllUNAPPROVEDFWMS_RELATIONSHIP(page);
			}
		}
		if(allrelationType!=null)
		{
			Page<Map<String,Object>> jsonData=allrelationType.map(new Converter<FWMS_RELATIONSHIP, Map<String,Object>>() {

				@Override
				public Map<String, Object> convert(FWMS_RELATIONSHIP fund_cur) {
					Map<String,Object>json=new HashMap<>();
					json.put("relationid", fund_cur.getWMS_RELATIONSHIP_ID());
					json.put("relationname", fund_cur.getWMS_RELATIONSHIP_DESC());
					json.put("relationshortname", fund_cur.getWMS_SHORT_DESC());
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
					return json;
				}
			});
			return new ResponseEntity<Page<Map<String,Object>>>(jsonData,HttpStatus.OK);
			
		}else{
			Map<String,Object> json=new HashMap<>();
			json.put("msg", "Not found");
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
	
	}

	/**
	 * Gets the json.
	 *
	 * @param fund_cur the fund cur
	 * @return the json
	 */
	public RelationshipBean getJson(FWMS_RELATIONSHIP fund_cur) {
		RelationshipBean rsb=new RelationshipBean();
		rsb.setRelationid( fund_cur.getWMS_RELATIONSHIP_ID()+"");
		rsb.setRelationname( fund_cur.getWMS_RELATIONSHIP_DESC());
		rsb.setRelationshortname( fund_cur.getWMS_SHORT_DESC());
		rsb.setComment( fund_cur.getWMS_COMMENTS());
		rsb.setStatus( fund_cur.getWMS_STATUS());
							FUND_USERS user=null;
							if(fund_cur.getWMS_ENTER_UID()!=null) {
								user=getUserName(fund_cur.getWMS_ENTER_UID());
								if(user!=null) {
			rsb.setEnteredby( user.getSVU_NAME());
			rsb.setEnteredbyuserid( user.getSVU_USER_NAME());
			rsb.setEnteredbyuuid( user.getSVC_UID());
			rsb.setEntereddate( fund_cur.getWMS_ENTER_DATE().getTimeInMillis()+"");
								}
								else {
				rsb.setEnteredby( null);
				rsb.setEnteredbyuserid( null);
				rsb.setEnteredbyuuid( null);
				rsb.setEntereddate( null);
								}
							}else {
			rsb.setEnteredby( null);
			rsb.setEnteredbyuserid( null);
			rsb.setEnteredbyuuid( null);
			rsb.setEntereddate( null);
							}
							
							if(fund_cur.getWMS_APPROVE_UID()!=null) {
								user=getUserName(fund_cur.getWMS_APPROVE_UID());
								if(user!=null) {
			rsb.setApprovedby( user.getSVU_NAME());
			rsb.setApprovedbyuserid( user.getSVU_USER_NAME());
			rsb.setApprovedbyuuid( user.getSVC_UID());
			rsb.setApproveddate( fund_cur.getWMS_APPROVE_DATE().getTimeInMillis()+"");
								}
								else {
				rsb.setApprovedby( null);
				rsb.setApprovedbyuserid( null);
				rsb.setApprovedbyuuid( null);
				rsb.setApproveddate( null);
								}
							}else {
			    rsb.setApprovedby( null);
				rsb.setApprovedbyuserid( null);
				rsb.setApprovedbyuuid( null);
				rsb.setApproveddate( null);
							}
							
							if(fund_cur.getWMS_LAST_UPDATE_UID()!=null) {
								user=getUserName(fund_cur.getWMS_LAST_UPDATE_UID());
								if(user!=null) {
			rsb.setModifiedby( user.getSVU_NAME());
			rsb.setModifiedbyuserid( user.getSVU_USER_NAME());
			rsb.setModifiedbyuuid( user.getSVC_UID());
			rsb.setModifieddate( fund_cur.getWMS_LAST_UPDATE_DATE().getTimeInMillis()+"");
								}
								else {
				rsb.setModifiedby( null);
				rsb.setModifiedbyuserid( null);
				rsb.setModifiedbyuuid( null);
				rsb.setModifieddate( null);
								}
							}else {
			rsb.setModifiedby( null);
			rsb.setModifiedbyuserid( null);
			rsb.setModifiedbyuuid( null);
			rsb.setModifieddate( null);
							}
		return rsb;
	}
	
	/**
	 * Checks if is valid.
	 *
	 * @param userid the userid
	 * @return the fund users
	 */
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
	
	/**
	 * Gets the user name.
	 *
	 * @param userid the userid
	 * @return the user name
	 */
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