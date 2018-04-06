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
import com.fynisys.model.clienttype.FUND_MAR_CLIENT_TYPE;
import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.clienttype.FUND_MAR_CLIENT_TYPERespository;




// TODO: Auto-generated Javadoc
/**
 * The Class FUND_MAR_CLIENT_TYPEController.
 */
@RestController
public class FUND_MAR_CLIENT_TYPEController{
	
	/** The UN D MA R CLIEN T TYPE respository. */
	@Autowired
	FUND_MAR_CLIENT_TYPERespository fUND_MAR_CLIENT_TYPERespository;
	
	/** The fund user repository. */
	@Autowired
	FUND_USERSRepository fund_UserRepository;

	
	/** The UN D USE R LOG repository. */
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;

	
	/** The Constant logger. */
	private static final Logger logger=LoggerFactory.getLogger("FUND_MAR_CLIENT_TYPE");
	
//	public boolean check(String name)
//	{
//		List<FUND_MAR_CLIENT_TYPE> fmct=fUND_MAR_CLIENT_TYPERespository.findByFUND_MAR_CLIENT_TYPE_Name(name);
//		if(fmct.size()>0) {
//			re
//		}
//	}
	
	
	/**
 * Save.
 *
 * @param requestData the request data
 * @return the response entity
 */
@PostMapping("/saveclienttype")
    public ResponseEntity<?> save(RequestEntity<Map<String, Object>>requestData){
	Map<String,Object> requestJson=	requestData.getBody();
	Map<String,Object> json=new HashMap<>();
	Object  FCT_NAME=requestJson.get("clienttypename");
	Object FCT_SHORT_NAME=requestJson.get("clientshortname");
	Object FCT_MARGIN_LIMIT=requestJson.get("margin_limit");
	Object createdby =requestJson.get("createdby");
	Object WMS_COMMENTS=requestJson.get("comment");
	Object SVL_SCREEN=requestJson.get("screentype");
	Object SVL_DESC=requestJson.get("log");
	if(FCT_NAME==null||FCT_SHORT_NAME==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null) {
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
		FUND_MAR_CLIENT_TYPE fund_cur=new FUND_MAR_CLIENT_TYPE();
		
		
		fund_cur.setFCT_NAME(FCT_NAME.toString().trim());
		fund_cur.setFCT_SHORT_NAME(FCT_SHORT_NAME.toString().trim());
		if(FCT_MARGIN_LIMIT!=null) {
			if(FCT_MARGIN_LIMIT.toString().isEmpty()==false) {
				fund_cur.setFCT_MARGIN_LIMIT(new Double(FCT_MARGIN_LIMIT.toString().trim()));
			}
		}
		
		if(WMS_COMMENTS!=null) {
		fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
		}
		fund_cur.setWMS_STATUS("Not Approved");
		fund_cur.setFCT_DATE(Calendar.getInstance());
		fund_cur.setIV_ENTER_UID(fuser.getSVC_UID());
		fund_cur.setIV_ENTER_DATE(Calendar.getInstance());
		try
		{
			fund_cur=fUND_MAR_CLIENT_TYPERespository.save(fund_cur);
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
						logger.info("Both Record and Logs saved for Client Type:"+fund_cur.getFCT_NAME());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
				
				logger.info("Saved New Client Type:"+fund_cur.getFCT_NAME());
				json.put("clientid", fund_cur.getFCT_ID());
				json.put("clienttypename", fund_cur.getFCT_NAME());
				json.put("clientshortname", fund_cur.getFCT_SHORT_NAME());
				json.put("createdby", fund_cur.getIV_ENTER_UID());
				json.put("createdon", fund_cur.getIV_ENTER_DATE());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
			}
			else {
				json.put("msg","Not saved");
				logger.error("Client Type not saved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e)
		{
			json.put("msg", e.getMessage());
			logger.error("Client Type not saved :"+e.getMessage());
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
	@PostMapping("/updateclienttype")
	public ResponseEntity<Map<String,Object>> update(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> requestJson=	requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object FCT_ID =requestJson.get("clienttypeid");
		Object FCT_NAME=requestJson.get("clienttypename");
		Object FCT_SHORT_NAME=requestJson.get("clientshortname");
		Object FCT_MARGIN_LIMIT=requestJson.get("margin_limit");
		Object createdby =requestJson.get("modifiedby");
		Object WMS_COMMENTS=requestJson.get("comment");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		if(FCT_ID==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
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
			FUND_MAR_CLIENT_TYPE fund_cur=fUND_MAR_CLIENT_TYPERespository.findOne(new Long(FCT_ID.toString()));
			if(fund_cur==null) {
				json.put("msg", "No Client Type found with this id->"+FCT_ID.toString());
				logger.error("No Client Type found with this id->"+FCT_ID.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
			if(FCT_NAME!=null) {
			fund_cur.setFCT_NAME(FCT_NAME.toString().trim());
			}
			if(FCT_SHORT_NAME!=null) {
				fund_cur.setFCT_SHORT_NAME(FCT_SHORT_NAME.toString());
			}
			if(WMS_COMMENTS!=null) {
			fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
			}
			if(FCT_MARGIN_LIMIT!=null) {
				if(FCT_MARGIN_LIMIT.toString().isEmpty()==false) {
					fund_cur.setFCT_MARGIN_LIMIT(new Double(FCT_MARGIN_LIMIT.toString().trim()));
				}
			}
			
			fund_cur.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
			fund_cur.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
			try
			{
				fund_cur=fUND_MAR_CLIENT_TYPERespository.save(fund_cur);
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
							logger.info("Both Record and Logs saved for Client:"+fund_cur.getFCT_NAME());
							
						}
						else {
							logger.info("Record is saved but logs can't saved due error in saving of logs");
							json.put("logs","Record is saved but logs can't saved due error in saving of logs");
						}
					
					
					logger.info("Client saved:"+fund_cur.getFCT_NAME());
					json.put("clientid", fund_cur.getFCT_ID());
					json.put("clientname", fund_cur.getFCT_NAME());
					json.put("clientshortname", fund_cur.getFCT_SHORT_NAME());
					json.put("clientshortname", fund_cur.getFCT_SHORT_NAME());
					json.put("margin_limit", fund_cur.getFCT_MARGIN_LIMIT());
					json.put("comment", fund_cur.getWMS_COMMENTS());
					json.put("status", fund_cur.getWMS_STATUS());
					FUND_USERS user=null;
					if(fund_cur.getIV_ENTER_UID()!=null) {
						user=getUserName(fund_cur.getIV_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", fund_cur.getIV_ENTER_DATE());
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
					
					if(fund_cur.getIV_APPROVE_UID()!=null) {
						user=getUserName(fund_cur.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", fund_cur.getIV_APPROVE_DATE());
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
					
					if(fund_cur.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(fund_cur.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", fund_cur.getIV_LAST_UPDATE_DATE());
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
				logger.error("Client Not saved"+e.getMessage());
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
	@PostMapping("/approvedclienttype")
	public ResponseEntity<Map<String,Object>> approved(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> requestJson=	requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object FCT_ID =requestJson.get("clienttypeid");
		Object createdby =requestJson.get("approvedby");
		Object WMS_COMMENTS=requestJson.get("comment");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		if(FCT_ID==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
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
			FUND_MAR_CLIENT_TYPE fund_cur=fUND_MAR_CLIENT_TYPERespository.findOne(new Long(FCT_ID.toString()));
			if(fund_cur==null) {
				json.put("msg", "No Client Type found with this id->"+FCT_ID.toString());
				logger.error("No Client Type found with this id->"+FCT_ID.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
					
			if(WMS_COMMENTS!=null) {
				fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
			fund_cur.setWMS_STATUS("Approved");
			fund_cur.setIV_APPROVE_UID(fuser.getSVC_UID());
			fund_cur.setIV_APPROVE_DATE(Calendar.getInstance());
			try
			{
				fund_cur=fUND_MAR_CLIENT_TYPERespository.save(fund_cur);
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
							logger.info("Both Record and Logs saved for Client:"+fund_cur.getFCT_NAME());
							
						}
						else {
							logger.info("Record is saved but logs can't saved due error in saving of logs");
							json.put("logs","Record is saved but logs can't saved due error in saving of logs");
						}
					
					
					logger.info("Client saved:"+fund_cur.getFCT_NAME());
					json.put("clientid", fund_cur.getFCT_ID());
					json.put("clientname", fund_cur.getFCT_NAME());
					json.put("clientshortname", fund_cur.getFCT_SHORT_NAME());
					json.put("margin_limit", fund_cur.getFCT_MARGIN_LIMIT());
					json.put("comment", fund_cur.getWMS_COMMENTS());
					json.put("status", fund_cur.getWMS_STATUS());
					FUND_USERS user=null;
					if(fund_cur.getIV_ENTER_UID()!=null) {
						user=getUserName(fund_cur.getIV_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", fund_cur.getIV_ENTER_DATE());
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
					
					if(fund_cur.getIV_APPROVE_UID()!=null) {
						user=getUserName(fund_cur.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", fund_cur.getIV_APPROVE_DATE());
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
					
					if(fund_cur.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(fund_cur.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", fund_cur.getIV_LAST_UPDATE_DATE());
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
				logger.error("Client Not saved"+e.getMessage());
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
	@PostMapping("/revokeclienttype")
	public ResponseEntity<Map<String,Object>> revoke(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> requestJson=	requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object FCT_ID =requestJson.get("clienttypeid");
		Object createdby =requestJson.get("modifiedby");
		Object WMS_COMMENTS=requestJson.get("comment");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		if(FCT_ID==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
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
			FUND_MAR_CLIENT_TYPE fund_cur=fUND_MAR_CLIENT_TYPERespository.findOne(new Long(FCT_ID.toString()));
			if(fund_cur==null) {
				json.put("msg", "No Client Type found with this id->"+FCT_ID.toString());
				logger.error("No Client Type found with this id->"+FCT_ID.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		    
			if(WMS_COMMENTS!=null) {
//				fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
			fund_cur.setWMS_STATUS("Not Approved");
			fund_cur.setIV_APPROVE_UID(null);
			fund_cur.setIV_APPROVE_DATE(null);
			fund_cur.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
			fund_cur.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
			try
			{
				fund_cur=fUND_MAR_CLIENT_TYPERespository.save(fund_cur);
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
							logger.info("Both Record and Logs saved for Client:"+fund_cur.getFCT_NAME());
							
						}
						else {
							logger.info("Record is saved but logs can't saved due error in saving of logs");
							json.put("logs","Record is saved but logs can't saved due error in saving of logs");
						}
					
					
					logger.info("Client saved:"+fund_cur.getFCT_NAME());
					json.put("clientid", fund_cur.getFCT_ID());
					json.put("clientname", fund_cur.getFCT_NAME());
					json.put("clientshortname", fund_cur.getFCT_SHORT_NAME());
					json.put("margin_limit", fund_cur.getFCT_MARGIN_LIMIT());
					json.put("comment", fund_cur.getWMS_COMMENTS());
					json.put("status", fund_cur.getWMS_STATUS());
					FUND_USERS user=null;
					if(fund_cur.getIV_ENTER_UID()!=null) {
						user=getUserName(fund_cur.getIV_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", fund_cur.getIV_ENTER_DATE());
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
					
					if(fund_cur.getIV_APPROVE_UID()!=null) {
						user=getUserName(fund_cur.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", fund_cur.getIV_APPROVE_DATE());
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
					
					if(fund_cur.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(fund_cur.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", fund_cur.getIV_LAST_UPDATE_DATE());
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
				logger.error("Client Not saved"+e.getMessage());
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
	@PostMapping("/deleteclienttype")
	public ResponseEntity<Map<String,Object>> delete(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> requestJson=	requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object FCT_ID =requestJson.get("clienttypeid");
		Object createdby =requestJson.get("modifiedby");
//		Object WMS_COMMENTS=requestJson.get("comment");
//		Object SVL_SCREEN=requestJson.get("screentype");
//		Object SVL_DESC=requestJson.get("log");
		
		if(FCT_ID==null||createdby==null)
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
			FUND_MAR_CLIENT_TYPE fund_cur=fUND_MAR_CLIENT_TYPERespository.findOne(new Long(FCT_ID.toString()));
			if(fund_cur==null) {
				json.put("msg", "No Client Type found with this id->"+FCT_ID.toString());
				logger.error("No Client Type found with this id->"+FCT_ID.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			try
			{
				fUND_MAR_CLIENT_TYPERespository.delete(fund_cur);
				json.put("msg", "DELETED");
				logger.info("Deleted"+FCT_ID.toString());
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
//							logger.info("Both Record and Logs saved for Client:"+fund_cur.getFCT_NAME());
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
				logger.error("Client Not saved"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
			
			
		}
	}
	
	
	/**
	 * Gets the all client type.
	 *
	 * @param action the action
	 * @return the all client type
	 */
	@GetMapping("/clienttypes")
	public ResponseEntity<?> getAllClientType(@RequestParam(value="action",required=false) String action){
		List<Map<String,Object>> JsonClientType=new ArrayList<Map<String,Object>>();
		List<FUND_MAR_CLIENT_TYPE> allClientType=null;
		if(action==null) {
			allClientType=fUND_MAR_CLIENT_TYPERespository.findAllFUND_MAR_CLIENT_TYPE();
		}else if(action.equalsIgnoreCase("ALL")) {
			allClientType=fUND_MAR_CLIENT_TYPERespository.findAllFUND_MAR_CLIENT_TYPE();
		}else if(action.equalsIgnoreCase("APPROVED")) {
			allClientType=fUND_MAR_CLIENT_TYPERespository.findAllAPPROVEDFUND_MAR_CLIENT_TYPE();
		}else if(action.equalsIgnoreCase("UNAPPROVED"))
		{
			allClientType=fUND_MAR_CLIENT_TYPERespository.findAllUNAPPROVEDFUND_MAR_CLIENT_TYPE();
		}
		if(allClientType!=null)
		{
			allClientType.forEach(fund_cur->{
				Map<String,Object>json=new HashMap<>();
				json.put("clientid", fund_cur.getFCT_ID());
				json.put("clientname", fund_cur.getFCT_NAME());
				json.put("clientshortname", fund_cur.getFCT_SHORT_NAME());
				json.put("margin_limit", fund_cur.getFCT_MARGIN_LIMIT());
				json.put("comment", fund_cur.getWMS_COMMENTS());
				json.put("status", fund_cur.getWMS_STATUS());
				FUND_USERS user=null;
				if(fund_cur.getIV_ENTER_UID()!=null) {
					user=getUserName(fund_cur.getIV_ENTER_UID());
					if(user!=null) {
					json.put("enteredby", user.getSVU_NAME());
					json.put("enteredbyuserid", user.getSVU_USER_NAME());
					json.put("enteredbyuuid", user.getSVC_UID());
					json.put("entereddate", fund_cur.getIV_ENTER_DATE());
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
				
				if(fund_cur.getIV_APPROVE_UID()!=null) {
					user=getUserName(fund_cur.getIV_APPROVE_UID());
					if(user!=null) {
					json.put("approvedby", user.getSVU_NAME());
					json.put("approvedbyuserid", user.getSVU_USER_NAME());
					json.put("approvedbyuuid", user.getSVC_UID());
					json.put("approveddate", fund_cur.getIV_APPROVE_DATE());
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
				
				if(fund_cur.getIV_LAST_UPDATE_UID()!=null) {
					user=getUserName(fund_cur.getIV_LAST_UPDATE_UID());
					if(user!=null) {
					json.put("modifiedby", user.getSVU_NAME());
					json.put("modifiedbyuserid", user.getSVU_USER_NAME());
					json.put("modifiedbyuuid", user.getSVC_UID());
					json.put("modifieddate", fund_cur.getIV_LAST_UPDATE_DATE());
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
				JsonClientType.add(json);
			});	
			return new ResponseEntity<List<Map<String,Object>>>(JsonClientType,HttpStatus.OK);
			
		}else{
			Map<String,Object> json=new HashMap<>();
			json.put("msg", "Not found");
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
	
	}
	
	
	/**
	 * Gets the all client type page.
	 *
	 * @param action the action
	 * @param paramsearch the paramsearch
	 * @param page the page
	 * @return the all client type page
	 */
	@GetMapping("/clienttypespage")
	public ResponseEntity<?> getAllClientTypePage(@RequestParam(value="action",required=false) String action,
			@RequestParam(value="paramSearch",required=false) String paramsearch,
			Pageable page){
		Page<FUND_MAR_CLIENT_TYPE> 	allClientType=null;
		if(action==null) {
			if(paramsearch!=null) {
				if(paramsearch.isEmpty()==false)
				{
					allClientType=fUND_MAR_CLIENT_TYPERespository.findAllFUND_MAR_CLIENT_TYPESearching(paramsearch,page);
				}else {
					allClientType=fUND_MAR_CLIENT_TYPERespository.findAllFUND_MAR_CLIENT_TYPE(page);	
				}
			}else {
				allClientType=fUND_MAR_CLIENT_TYPERespository.findAllFUND_MAR_CLIENT_TYPE(page);
			}
			
		}else if(action.equalsIgnoreCase("ALL")) {
			if(paramsearch!=null) {
				if(paramsearch.isEmpty()==false)
				{
					allClientType=fUND_MAR_CLIENT_TYPERespository.findAllFUND_MAR_CLIENT_TYPESearching(paramsearch,page);
				}else {
					allClientType=fUND_MAR_CLIENT_TYPERespository.findAllFUND_MAR_CLIENT_TYPE(page);	
				}
			}else {
				allClientType=fUND_MAR_CLIENT_TYPERespository.findAllFUND_MAR_CLIENT_TYPE(page);
			}
		}else if(action.equalsIgnoreCase("APPROVED")) {
			if(paramsearch!=null) {
				if(paramsearch.isEmpty()==false)
				{
					allClientType=fUND_MAR_CLIENT_TYPERespository.findAllAPPROVEDFUND_MAR_CLIENT_TYPESearching(paramsearch,page);
				}else {
					allClientType=fUND_MAR_CLIENT_TYPERespository.findAllAPPROVEDFUND_MAR_CLIENT_TYPE(page);	
				}
			}else {
				allClientType=fUND_MAR_CLIENT_TYPERespository.findAllAPPROVEDFUND_MAR_CLIENT_TYPE(page);
			}
		}else if(action.equalsIgnoreCase("UNAPPROVED"))
		{
			if(paramsearch!=null) {
				if(paramsearch.isEmpty()==false)
				{
					allClientType=fUND_MAR_CLIENT_TYPERespository.findAllUNAPPROVEDFUND_MAR_CLIENT_TYPESearching(paramsearch,page);
				}else {
					allClientType=fUND_MAR_CLIENT_TYPERespository.findAllUNAPPROVEDFUND_MAR_CLIENT_TYPE(page);	
				}
			}else {
				allClientType=fUND_MAR_CLIENT_TYPERespository.findAllUNAPPROVEDFUND_MAR_CLIENT_TYPE(page);
			}
		}
		if(allClientType!=null)
		{
			Page<Map<String,Object>> jsonData=allClientType.map(new Converter<FUND_MAR_CLIENT_TYPE, Map<String,Object>>() {

				@Override
				public Map<String, Object> convert(FUND_MAR_CLIENT_TYPE fund_cur) {
					Map<String,Object>json=new HashMap<>();
					json.put("clientid", fund_cur.getFCT_ID());
					json.put("clientname", fund_cur.getFCT_NAME());
					json.put("clientshortname", fund_cur.getFCT_SHORT_NAME());
					json.put("margin_limit", fund_cur.getFCT_MARGIN_LIMIT());
					json.put("comment", fund_cur.getWMS_COMMENTS());
					json.put("status", fund_cur.getWMS_STATUS());
					FUND_USERS user=null;
					if(fund_cur.getIV_ENTER_UID()!=null) {
						user=getUserName(fund_cur.getIV_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", fund_cur.getIV_ENTER_DATE());
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
					
					if(fund_cur.getIV_APPROVE_UID()!=null) {
						user=getUserName(fund_cur.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", fund_cur.getIV_APPROVE_DATE());
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
					
					if(fund_cur.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(fund_cur.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", fund_cur.getIV_LAST_UPDATE_DATE());
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