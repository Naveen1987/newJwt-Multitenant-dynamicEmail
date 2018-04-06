package com.fynisys.controller.clienttype;



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

import com.fynisys.model.FUND_USERS;
import com.fynisys.model.FUND_USER_LOG;
import com.fynisys.model.clienttype.FUND_CLIENT_DOCUMENTS_TYPE2;

import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.clienttype.FUND_CLIENT_DOCUMENTS_TYPE2Repository;





// TODO: Auto-generated Javadoc
/**
 * The Class FUND_CLIENT_DOCUMENTS_TYPE2Controller.
 */
@RestController
public class FUND_CLIENT_DOCUMENTS_TYPE2Controller{
	
	/** The UN D CLIEN T DOCUMENT S TYPE 2 repository. */
	@Autowired
	FUND_CLIENT_DOCUMENTS_TYPE2Repository fUND_CLIENT_DOCUMENTS_TYPE2Repository;
	
	/** The fund user repository. */
	@Autowired
	FUND_USERSRepository fund_UserRepository;

	
	/** The UN D USE R LOG repository. */
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;

	
	/** The Constant logger. */
	private static final Logger logger=LoggerFactory.getLogger("FUND_CLIENT_DOCUMENTS_TYPE2");
	
	/**
	 * Save.
	 *
	 * @param requestData the request data
	 * @return the response entity
	 */
	@PostMapping("/savedoctype")
    public ResponseEntity<?> save(RequestEntity<Map<String, Object>>requestData){
	Map<String,Object> requestJson=	requestData.getBody();
	Map<String,Object> json=new HashMap<>();
	Object  SCD_DESC=requestJson.get("docname");
	Object  SCD_SHORT_DESC=requestJson.get("docshortname");
	Object createdby =requestJson.get("createdby");
	Object WMS_COMMENTS=requestJson.get("comment");
	Object SVL_SCREEN=requestJson.get("screentype");
	Object SVL_DESC=requestJson.get("log");
	if(SCD_DESC==null||SCD_SHORT_DESC==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null) {
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
		FUND_CLIENT_DOCUMENTS_TYPE2 fund_cur=new FUND_CLIENT_DOCUMENTS_TYPE2();
		fund_cur.setSCD_DESC(SCD_DESC.toString().trim());
		fund_cur.setSCD_SHORT_DESC(SCD_SHORT_DESC.toString().trim());
		if(WMS_COMMENTS!=null) {
		fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
		}
		fund_cur.setWMS_STATUS("Not Approved");
		fund_cur.setWMS_ENTER_UID(fuser.getSVC_UID());
		fund_cur.setWMS_ENTER_DATE(Calendar.getInstance());

		try
		{
			fund_cur=fUND_CLIENT_DOCUMENTS_TYPE2Repository.save(fund_cur);
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
						logger.info("Both Record and Logs saved for DOC Type:"+fund_cur.getSCD_DESC());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
				
				logger.info("Saved New DOC Type:"+fund_cur.getSCD_DESC());
				json.put("docid", fund_cur.getSCD_TYPE());
				json.put("doctype", fund_cur.getSCD_DESC());
				json.put("docshortname", fund_cur.getSCD_SHORT_DESC());
				json.put("createdby", fund_cur.getWMS_ENTER_UID());
				json.put("createdon", fund_cur.getWMS_ENTER_DATE());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
			}
			else {
				json.put("msg","Not saved");
				logger.error("DOC Type not saved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e)
		{
			json.put("msg", e.getMessage());
			logger.error("DOC Type not saved :"+e.getMessage());
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
	@PostMapping("/updatedoctype")
	public ResponseEntity<Map<String,Object>> update(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> requestJson=	requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SCD_TYPE=requestJson.get("docid");
		Object  SCD_DESC=requestJson.get("docname");
		Object  SCD_SHORT_DESC=requestJson.get("docshortname");
		Object createdby =requestJson.get("createdby");
		Object WMS_COMMENTS=requestJson.get("comment");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		if(SCD_TYPE==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
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
			FUND_CLIENT_DOCUMENTS_TYPE2 fund_cur=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findOne(new Long(SCD_TYPE.toString()));
			if(fund_cur==null) {
				json.put("msg", "No DocType Type found with this id->"+SCD_TYPE.toString());
				logger.error("No DocType Type found with this id->"+SCD_TYPE.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
			if(SCD_DESC!=null) {
			fund_cur.setSCD_DESC(SCD_DESC.toString().trim());
			}
			if(SCD_SHORT_DESC!=null) {
				fund_cur.setSCD_SHORT_DESC(SCD_SHORT_DESC.toString());
			}
			if(WMS_COMMENTS!=null) {
			fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
			}
			
			
			fund_cur.setWMS_LAST_UPDATE_UID(fuser.getSVC_UID());	
			fund_cur.setWMS_LAST_UPDATE_DATE(Calendar.getInstance());
			try
			{
				fund_cur=fUND_CLIENT_DOCUMENTS_TYPE2Repository.save(fund_cur);
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
							logger.info("Both Record and Logs saved for DocType:"+fund_cur.getSCD_DESC());
							
						}
						else {
							logger.info("Record is saved but logs can't saved due error in saving of logs");
							json.put("logs","Record is saved but logs can't saved due error in saving of logs");
						}
					
					
					logger.info("DocType saved:"+fund_cur.getSCD_DESC());
					json.put("docid", fund_cur.getSCD_TYPE());
					json.put("docname", fund_cur.getSCD_DESC());
					json.put("docshortname", fund_cur.getSCD_SHORT_DESC());					
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
					logger.error("Document Type Not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("DocType Not saved"+e.getMessage());
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
	@PostMapping("/approveddoctype")
	public ResponseEntity<Map<String,Object>> approved(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> requestJson=	requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SCD_TYPE =requestJson.get("docid");
		Object createdby =requestJson.get("approvedby");
		Object WMS_COMMENTS=requestJson.get("comment");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		if(SCD_TYPE==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null||WMS_COMMENTS==null)
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
			FUND_CLIENT_DOCUMENTS_TYPE2 fund_cur=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findOne(new Long(SCD_TYPE.toString()));
			if(fund_cur==null) {
				json.put("msg", "No DocType Type found with this id->"+SCD_TYPE.toString());
				logger.error("No DocType Type found with this id->"+SCD_TYPE.toString());
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
				fund_cur=fUND_CLIENT_DOCUMENTS_TYPE2Repository.save(fund_cur);
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
							logger.info("Both Record and Logs saved for DocType:"+fund_cur.getSCD_DESC());
							
						}
						else {
							logger.info("Record is saved but logs can't saved due error in saving of logs");
							json.put("logs","Record is saved but logs can't saved due error in saving of logs");
						}
					
					
					logger.info("DocType saved:"+fund_cur.getSCD_DESC());
					json.put("docid", fund_cur.getSCD_TYPE());
					json.put("docname", fund_cur.getSCD_DESC());
					json.put("docshortname", fund_cur.getSCD_SHORT_DESC());
					
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
					logger.error("Document Type Not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("DocType Not saved"+e.getMessage());
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
	@PostMapping("/revokedoctype")
	public ResponseEntity<Map<String,Object>> revoke(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> requestJson=	requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SCD_TYPE =requestJson.get("docid");
		Object createdby =requestJson.get("modifiedby");
		Object WMS_COMMENTS=requestJson.get("comment");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		if(SCD_TYPE==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
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
			FUND_CLIENT_DOCUMENTS_TYPE2 fund_cur=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findOne(new Long(SCD_TYPE.toString()));
			if(fund_cur==null) {
				json.put("msg", "No DocType Type found with this id->"+SCD_TYPE.toString());
				logger.error("No DocType Type found with this id->"+SCD_TYPE.toString());
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
				fund_cur=fUND_CLIENT_DOCUMENTS_TYPE2Repository.save(fund_cur);
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
							logger.info("Both Record and Logs saved for DocType:"+fund_cur.getSCD_DESC());
							
						}
						else {
							logger.info("Record is saved but logs can't saved due error in saving of logs");
							json.put("logs","Record is saved but logs can't saved due error in saving of logs");
						}
					
					
					logger.info("DocType saved:"+fund_cur.getSCD_DESC());
					json.put("docid", fund_cur.getSCD_TYPE());
					json.put("docname", fund_cur.getSCD_DESC());
					json.put("docshortname", fund_cur.getSCD_SHORT_DESC());
					
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
					logger.error("Document Type Not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("DocType Not saved"+e.getMessage());
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
	@PostMapping("/deletedoctype")
	public ResponseEntity<Map<String,Object>> delete(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> requestJson=	requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SCD_TYPE =requestJson.get("doctid");
		Object createdby =requestJson.get("modifiedby");
//		Object WMS_COMMENTS=requestJson.get("comment");
//		Object SVL_SCREEN=requestJson.get("screentype");
//		Object SVL_DESC=requestJson.get("log");
		
		if(SCD_TYPE==null||createdby==null)
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
			FUND_CLIENT_DOCUMENTS_TYPE2 fund_cur=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findOne(new Long(SCD_TYPE.toString()));
			if(fund_cur==null) {
				json.put("msg", "No DocType Type found with this id->"+SCD_TYPE.toString());
				logger.error("No DocType Type found with this id->"+SCD_TYPE.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			try
			{
				fUND_CLIENT_DOCUMENTS_TYPE2Repository.delete(fund_cur);
				json.put("msg", "DELETED");
				logger.info("Deleted"+SCD_TYPE.toString());
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
//							logger.info("Both Record and Logs saved for DocType:"+fund_cur.getSCD_DESC());
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
				logger.error("DocType Not saved"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
			
			
		}
	}
	
	
	/**
	 * Gets the all doc type type.
	 *
	 * @param action the action
	 * @return the all doc type type
	 */
	@GetMapping("/doctypes")
	public ResponseEntity<?> getAllDocTypeType(@RequestParam(value="action",required=false) String action){
		List<Map<String,Object>> JsonDocTypeType=new ArrayList<Map<String,Object>>();
		List<FUND_CLIENT_DOCUMENTS_TYPE2> allDocTypeType=null;
		if(action==null) {
			allDocTypeType=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findAllFUND_CLIENT_DOCUMENTS_TYPE2();
		}else if(action.equalsIgnoreCase("ALL")) {
			allDocTypeType=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findAllFUND_CLIENT_DOCUMENTS_TYPE2();
		}else if(action.equalsIgnoreCase("APPROVED")) {
			allDocTypeType=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findAllAPPROVEDFUND_CLIENT_DOCUMENTS_TYPE2();
		}else if(action.equalsIgnoreCase("UNAPPROVED"))
		{
			allDocTypeType=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findAllUNAPPROVEDFUND_CLIENT_DOCUMENTS_TYPE2();
		}
		if(allDocTypeType!=null)
		{
			allDocTypeType.forEach(fund_cur->{
				Map<String,Object>json=new HashMap<>();
				json.put("docid", fund_cur.getSCD_TYPE());
				json.put("docname", fund_cur.getSCD_DESC());
				json.put("docshortname", fund_cur.getSCD_SHORT_DESC());
				
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
				JsonDocTypeType.add(json);
			});	
			return new ResponseEntity<List<Map<String,Object>>>(JsonDocTypeType,HttpStatus.OK);
			
		}else{
			Map<String,Object> json=new HashMap<>();
			json.put("msg", "Not found");
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
	
	}
	
	
	/**
	 * Gets the all doc type type page.
	 *
	 * @param action the action
	 * @param paramsearch the paramsearch
	 * @param page the page
	 * @return the all doc type type page
	 */
	@GetMapping("/doctypespage")
	public ResponseEntity<?> getAllDocTypeTypePage(@RequestParam(value="action",required=false) String action,
			@RequestParam(value="paramSearch",required=false) String paramsearch,
			Pageable page){
		Page<FUND_CLIENT_DOCUMENTS_TYPE2> 	allDocTypeType=null;
		if(action==null) {
			if(paramsearch!=null) {
				if(paramsearch.isEmpty()==false)
				{
					allDocTypeType=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findAllFUND_CLIENT_DOCUMENTS_TYPE2Searching(paramsearch,page);
				}else {
					allDocTypeType=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findAllFUND_CLIENT_DOCUMENTS_TYPE2(page);	
				}
			}else {
				allDocTypeType=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findAllFUND_CLIENT_DOCUMENTS_TYPE2(page);
			}
			
		}else if(action.equalsIgnoreCase("ALL")) {
			if(paramsearch!=null) {
				if(paramsearch.isEmpty()==false)
				{
					allDocTypeType=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findAllFUND_CLIENT_DOCUMENTS_TYPE2Searching(paramsearch,page);
				}else {
					allDocTypeType=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findAllFUND_CLIENT_DOCUMENTS_TYPE2(page);	
				}
			}else {
				allDocTypeType=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findAllFUND_CLIENT_DOCUMENTS_TYPE2(page);
			}
		}else if(action.equalsIgnoreCase("APPROVED")) {
			if(paramsearch!=null) {
				if(paramsearch.isEmpty()==false)
				{
					allDocTypeType=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findAllAPPROVEDFUND_CLIENT_DOCUMENTS_TYPE2Searching(paramsearch,page);
				}else {
					allDocTypeType=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findAllAPPROVEDFUND_CLIENT_DOCUMENTS_TYPE2(page);	
				}
			}else {
				allDocTypeType=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findAllAPPROVEDFUND_CLIENT_DOCUMENTS_TYPE2(page);
			}
		}else if(action.equalsIgnoreCase("UNAPPROVED"))
		{
			if(paramsearch!=null) {
				if(paramsearch.isEmpty()==false)
				{
					allDocTypeType=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findAllUNAPPROVEDFUND_CLIENT_DOCUMENTS_TYPE2Searching(paramsearch,page);
				}else {
					allDocTypeType=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findAllUNAPPROVEDFUND_CLIENT_DOCUMENTS_TYPE2(page);	
				}
			}else {
				allDocTypeType=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findAllUNAPPROVEDFUND_CLIENT_DOCUMENTS_TYPE2(page);
			}
		}
		if(allDocTypeType!=null)
		{
			Page<Map<String,Object>> jsonData=allDocTypeType.map(new Converter<FUND_CLIENT_DOCUMENTS_TYPE2, Map<String,Object>>() {

				@Override
				public Map<String, Object> convert(FUND_CLIENT_DOCUMENTS_TYPE2 fund_cur) {
					Map<String,Object>json=new HashMap<>();
					json.put("docid", fund_cur.getSCD_TYPE());
					json.put("docname", fund_cur.getSCD_DESC());
					json.put("docshortname", fund_cur.getSCD_SHORT_DESC());
					
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