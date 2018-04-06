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
import com.fynisys.model.clienttype.FWMS_NATIONALITY;
import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.clienttype.FWMS_NATIONALITYRepository;




// TODO: Auto-generated Javadoc
/**
 * The Class FWMS_NATIONALITYController.
 */
@RestController
public class FWMS_NATIONALITYController{
	
	/** The WM S NATIONALITY repository. */
	@Autowired
	FWMS_NATIONALITYRepository fWMS_NATIONALITYRepository;
	
	/** The fund user repository. */
	@Autowired
	FUND_USERSRepository fund_UserRepository;

	
	/** The UN D USE R LOG repository. */
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;

	
	/** The Constant logger. */
	private static final Logger logger=LoggerFactory.getLogger("FWMS_NATIONALITY");
	
//	public boolean checkUniqueName(String name) {
//		List<FWMS_NATIONALITY> fn=fWMS_NATIONALITYRepository.findFWMS_NATIONALITYByName(name);
//		if(fn.size()>0) {
//			return false;
//		}
//		else {
//			return true;
//		}
//	}
//	
//	public boolean checkUniqueName(String name,Long id) {
//		List<FWMS_NATIONALITY> fn=fWMS_NATIONALITYRepository.findFWMS_NATIONALITYByName(name,id);
//		if(fn.size()==1) {
//			return true;
//		}else if(fn.size()==0) {
//			return true;
//		}
//		else {
//			return false;
//		}
//	}
//	
//
//	public boolean checkUniqueSortName(String name) {
//		List<FWMS_NATIONALITY> fn=fWMS_NATIONALITYRepository.findFWMS_NATIONALITYByWMS_SHORT_DESC(name);
//		if(fn.size()>0) {
//			if(fn.get(0).getWMS_NATIONALITY_DESC().equals(name))
//			{
//			return true;
//			}
//			else {
//				return	false;
//			}
//		}
//		else {
//			return true;
//		}
//	}
//	
//	public boolean checkUniqueSortName(String name,Long id) {
//		List<FWMS_NATIONALITY> fn=fWMS_NATIONALITYRepository.findFWMS_NATIONALITYByWMS_SHORT_DESC(name,id);
//		if(fn.size()==1) {
//			if(fn.get(0).getWMS_SHORT_DESC().equals(name))
//			{
//			return true;
//			}
//			else {
//				return	false;
//			}
//		}else if(fn.size()==0) {
//			return true;
//		}
//		else {
//			return false;
//		}
//	}
	
	/**
 * Save.
 *
 * @param requestData the request data
 * @return the response entity
 */
@PostMapping("/savenationality")
    public ResponseEntity<?> save(RequestEntity<Map<String, Object>>requestData){
	Map<String,Object> requestJson=	requestData.getBody();
	Map<String,Object> json=new HashMap<>();
	Object  WMS_NATIONALITY_DESC=requestJson.get("nationalityname");
	Object WMS_SHORT_DESC=requestJson.get("nationalityshortname");
	Object createdby =requestJson.get("createdby");
	Object WMS_COMMENTS=requestJson.get("comment");
	Object SVL_SCREEN=requestJson.get("screentype");
	Object SVL_DESC=requestJson.get("log");
	if(WMS_NATIONALITY_DESC==null||WMS_SHORT_DESC==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null) {
		json.put("msg", "Parameter Missing");
		return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
	}
	else
	{
		
//		if(!checkUniqueName(WMS_NATIONALITY_DESC.toString().trim())) {
//			json.put("msg", "SYS.UK_WMS_NATIONALITY_DESC");
//			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
//		}
//		
//		if(!checkUniqueSortName(WMS_SHORT_DESC.toString().trim())) {
//			json.put("msg", "SYS.UK_WMS_SHORT_DESC");
//			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
//		}
//		
		
		FUND_USERS fuser=isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FWMS_NATIONALITY fund_cur=new FWMS_NATIONALITY();
		
		
		fund_cur.setWMS_NATIONALITY_DESC(WMS_NATIONALITY_DESC.toString().trim());
		fund_cur.setWMS_SHORT_DESC(WMS_SHORT_DESC.toString().trim());
		if(WMS_COMMENTS!=null) {
		fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
		}
		fund_cur.setWMS_STATUS("Not Approved");
		
		fund_cur.setWMS_ENTER_UID(fuser.getSVC_UID());
		fund_cur.setWMS_ENTER_DATE(Calendar.getInstance());

		try
		{
		
			fund_cur=fWMS_NATIONALITYRepository.save(fund_cur);
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
						logger.info("Both Record and Logs saved for Nationality Type:"+fund_cur.getWMS_NATIONALITY_DESC());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
				
				logger.info("Saved New Nationality:"+fund_cur.getWMS_NATIONALITY_DESC());
				json.put("nationalityid", fund_cur.getWMS_NATIONALITY_ID());
				json.put("nationalityname", fund_cur.getWMS_NATIONALITY_DESC());
				json.put("nationalityshortname", fund_cur.getWMS_SHORT_DESC());
				json.put("createdby", fund_cur.getWMS_ENTER_UID());
				json.put("createdon", fund_cur.getWMS_ENTER_DATE());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
			}
			else {
				json.put("msg","Not saved");
				logger.error("Nationality not saved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e)
		{
			json.put("msg", e.getMessage());
			logger.error("Nationality not saved :"+e.getMessage());
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
	@PostMapping("/updatenationality")
	public ResponseEntity<Map<String,Object>> update(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> requestJson=	requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object WMS_NATIONALITY_ID =requestJson.get("nationalityid");
		Object WMS_NATIONALITY_DESC=requestJson.get("nationalityname");
		Object WMS_SHORT_DESC=requestJson.get("nationalityshortname");
		
		Object createdby =requestJson.get("modifiedby");
		Object WMS_COMMENTS=requestJson.get("comment");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		if(WMS_NATIONALITY_ID==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
		{
			json.put("msg", "Please check Input json, missing some required attributes");
			logger.error("Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		else
		{

//			if(!checkUniqueName(WMS_NATIONALITY_DESC.toString().trim(),new Long(WMS_NATIONALITY_ID.toString()))) {
//				json.put("msg", "SYS.UK_WMS_NATIONALITY_DESC");
//				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
//			}
//			
//			if(!checkUniqueSortName(WMS_SHORT_DESC.toString().trim(),new Long(WMS_NATIONALITY_ID.toString()))) {
//				json.put("msg", "SYS.UK_WMS_SHORT_DESC");
//				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
//			}
		
			FUND_USERS fuser=isValid(createdby.toString());
			if(fuser==null)
			{
					json.put("msg", "Modified by is not valid user");
					logger.error("Modified by is not valid user");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			FWMS_NATIONALITY fund_cur=fWMS_NATIONALITYRepository.findOne(new Long(WMS_NATIONALITY_ID.toString()));
			if(fund_cur==null) {
				json.put("msg", "No Nationality Type found with this id->"+WMS_NATIONALITY_ID.toString());
				logger.error("No Nationality Type found with this id->"+WMS_NATIONALITY_ID.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
			if(WMS_NATIONALITY_DESC!=null) {
			fund_cur.setWMS_NATIONALITY_DESC(WMS_NATIONALITY_DESC.toString().trim());
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
				fund_cur=fWMS_NATIONALITYRepository.save(fund_cur);
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
							logger.info("Both Record and Logs saved for Nationality:"+fund_cur.getWMS_NATIONALITY_DESC());
							
						}
						else {
							logger.info("Record is saved but logs can't saved due error in saving of logs");
							json.put("logs","Record is saved but logs can't saved due error in saving of logs");
						}
					
					
					logger.info("Nationality saved:"+fund_cur.getWMS_NATIONALITY_DESC());
					json.put("nationalityid", fund_cur.getWMS_NATIONALITY_ID());
					json.put("nationalityname", fund_cur.getWMS_NATIONALITY_DESC());
					json.put("nationalityshortname", fund_cur.getWMS_SHORT_DESC());
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
				logger.error("Nationality Not saved"+e.getMessage());
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
	@PostMapping("/approvednationality")
	public ResponseEntity<Map<String,Object>> approved(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> requestJson=	requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object WMS_NATIONALITY_ID =requestJson.get("nationalityid");
		Object createdby =requestJson.get("approvedby");
		Object WMS_COMMENTS=requestJson.get("comment");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		if(WMS_NATIONALITY_ID==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
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
			FWMS_NATIONALITY fund_cur=fWMS_NATIONALITYRepository.findOne(new Long(WMS_NATIONALITY_ID.toString()));
			if(fund_cur==null) {
				json.put("msg", "No Nationality Type found with this id->"+WMS_NATIONALITY_ID.toString());
				logger.error("No Nationality Type found with this id->"+WMS_NATIONALITY_ID.toString());
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
				fund_cur=fWMS_NATIONALITYRepository.save(fund_cur);
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
							logger.info("Both Record and Logs saved for Nationality:"+fund_cur.getWMS_NATIONALITY_DESC());
							
						}
						else {
							logger.info("Record is saved but logs can't saved due error in saving of logs");
							json.put("logs","Record is saved but logs can't saved due error in saving of logs");
						}
					
					
					logger.info("Nationality saved:"+fund_cur.getWMS_NATIONALITY_DESC());
					json.put("nationalityid", fund_cur.getWMS_NATIONALITY_ID());
					json.put("nationalityname", fund_cur.getWMS_NATIONALITY_DESC());
					json.put("nationalityshortname", fund_cur.getWMS_SHORT_DESC());
					
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
				logger.error("Nationality Not saved"+e.getMessage());
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
	@PostMapping("/revokenationality")
	public ResponseEntity<Map<String,Object>> revoke(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> requestJson=	requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object WMS_NATIONALITY_ID =requestJson.get("nationalityid");
		Object createdby =requestJson.get("modifiedby");
		Object WMS_COMMENTS=requestJson.get("comment");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		
		if(WMS_NATIONALITY_ID==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
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
			FWMS_NATIONALITY fund_cur=fWMS_NATIONALITYRepository.findOne(new Long(WMS_NATIONALITY_ID.toString()));
			if(fund_cur==null) {
				json.put("msg", "No Nationality Type found with this id->"+WMS_NATIONALITY_ID.toString());
				logger.error("No Nationality Type found with this id->"+WMS_NATIONALITY_ID.toString());
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
				fund_cur=fWMS_NATIONALITYRepository.save(fund_cur);
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
							logger.info("Both Record and Logs saved for Nationality:"+fund_cur.getWMS_NATIONALITY_DESC());
							
						}
						else {
							logger.info("Record is saved but logs can't saved due error in saving of logs");
							json.put("logs","Record is saved but logs can't saved due error in saving of logs");
						}
					
					
					logger.info("Nationality saved:"+fund_cur.getWMS_NATIONALITY_DESC());
					json.put("nationalityid", fund_cur.getWMS_NATIONALITY_ID());
					json.put("nationalityname", fund_cur.getWMS_NATIONALITY_DESC());
					json.put("nationalityshortname", fund_cur.getWMS_SHORT_DESC());
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
				logger.error("Nationality Not saved"+e.getMessage());
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
	@PostMapping("/deletenationality")
	public ResponseEntity<Map<String,Object>> delete(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> requestJson=	requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object WMS_NATIONALITY_ID =requestJson.get("nationalityid");
		Object createdby =requestJson.get("modifiedby");
//		Object WMS_COMMENTS=requestJson.get("comment");
//		Object SVL_SCREEN=requestJson.get("screentype");
//		Object SVL_DESC=requestJson.get("log");
		
		if(WMS_NATIONALITY_ID==null||createdby==null)
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
			FWMS_NATIONALITY fund_cur=fWMS_NATIONALITYRepository.findOne(new Long(WMS_NATIONALITY_ID.toString()));
			if(fund_cur==null) {
				json.put("msg", "No Nationality Type found with this id->"+WMS_NATIONALITY_ID.toString());
				logger.error("No Nationality Type found with this id->"+WMS_NATIONALITY_ID.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
				
			try
			{
				fWMS_NATIONALITYRepository.delete(fund_cur);
				json.put("msg", "DELETED");
				logger.info("Deleted"+WMS_NATIONALITY_ID.toString());
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
//							logger.info("Both Record and Logs saved for Nationality:"+fund_cur.getWMS_NATIONALITY_DESC());
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
				logger.error("Nationality Not saved"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
			
			
		}
	}
	
	/**
	 * Gets the all nationality type.
	 *
	 * @return the all nationality type
	 */
	@GetMapping("/nationalitieslist")
	public ResponseEntity<?> getAllNationalityType(){
		
		List<Map<String,Object>> JsonNationalityType=new ArrayList<Map<String,Object>>();
		Iterable<FWMS_NATIONALITY> allNationalityType=fWMS_NATIONALITYRepository.findAll();
		
		if(allNationalityType!=null)
		{
			allNationalityType.forEach(fund_cur->{
				Map<String,Object>json=new HashMap<>();
				json.put("nationalityid", fund_cur.getWMS_NATIONALITY_ID());
				json.put("nationalityname", fund_cur.getWMS_NATIONALITY_DESC());
				json.put("nationalityshortname", fund_cur.getWMS_SHORT_DESC());
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
				JsonNationalityType.add(json);
			});
		}else {
			return new ResponseEntity<>("No record found",HttpStatus.BAD_REQUEST);
		}
			return new ResponseEntity<List<Map<String,Object>>>(JsonNationalityType,HttpStatus.OK);
			
	}
	
	/**
	 * Gets the all nationality type.
	 *
	 * @param action the action
	 * @return the all nationality type
	 */
	@GetMapping("/nationalities")
	public ResponseEntity<?> getAllNationalityType(@RequestParam(value="action",required=false) String action){
		List<Map<String,Object>> JsonNationalityType=new ArrayList<Map<String,Object>>();
		List<FWMS_NATIONALITY> allNationalityType=null;
		if(action==null) {
			allNationalityType=fWMS_NATIONALITYRepository.findAllFWMS_NATIONALITY();
		}else if(action.equalsIgnoreCase("ALL")) {
			allNationalityType=fWMS_NATIONALITYRepository.findAllFWMS_NATIONALITY();
		}else if(action.equalsIgnoreCase("APPROVED")) {
			allNationalityType=fWMS_NATIONALITYRepository.findAllAPPROVEDFWMS_NATIONALITY();
		}else if(action.equalsIgnoreCase("UNAPPROVED"))
		{
			allNationalityType=fWMS_NATIONALITYRepository.findAllUNAPPROVEDFWMS_NATIONALITY();
		}
		if(allNationalityType!=null)
		{
			allNationalityType.forEach(fund_cur->{
				Map<String,Object>json=new HashMap<>();
				json.put("nationalityid", fund_cur.getWMS_NATIONALITY_ID());
				json.put("nationalityname", fund_cur.getWMS_NATIONALITY_DESC());
				json.put("nationalityshortname", fund_cur.getWMS_SHORT_DESC());
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
				JsonNationalityType.add(json);
			});	
			return new ResponseEntity<List<Map<String,Object>>>(JsonNationalityType,HttpStatus.OK);
			
		}else{
			Map<String,Object> json=new HashMap<>();
			json.put("msg", "Not found");
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
	
	}
	
	
	/**
	 * Gets the all nationality type page.
	 *
	 * @param action the action
	 * @param paramsearch the paramsearch
	 * @param page the page
	 * @return the all nationality type page
	 */
	@GetMapping("/nationalitiespage")
	public ResponseEntity<?> getAllNationalityTypePage(@RequestParam(value="action",required=false) String action,
			@RequestParam(value="paramSearch",required=false) String paramsearch,
			Pageable page){
		Page<FWMS_NATIONALITY> 	allNationalityType=null;
		if(action==null) {
			if(paramsearch!=null) {
				if(paramsearch.isEmpty()==false)
				{
					allNationalityType=fWMS_NATIONALITYRepository.findAllFWMS_NATIONALITYSearching(paramsearch,page);
				}else {
					allNationalityType=fWMS_NATIONALITYRepository.findAllFWMS_NATIONALITY(page);	
				}
			}else {
				allNationalityType=fWMS_NATIONALITYRepository.findAllFWMS_NATIONALITY(page);
			}
			
		}else if(action.equalsIgnoreCase("ALL")) {
			if(paramsearch!=null) {
				if(paramsearch.isEmpty()==false)
				{
					allNationalityType=fWMS_NATIONALITYRepository.findAllFWMS_NATIONALITYSearching(paramsearch,page);
				}else {
					allNationalityType=fWMS_NATIONALITYRepository.findAllFWMS_NATIONALITY(page);	
				}
			}else {
				allNationalityType=fWMS_NATIONALITYRepository.findAllFWMS_NATIONALITY(page);
			}
		}else if(action.equalsIgnoreCase("APPROVED")) {
			if(paramsearch!=null) {
				if(paramsearch.isEmpty()==false)
				{
					allNationalityType=fWMS_NATIONALITYRepository.findAllAPPROVEDFWMS_NATIONALITYSearching(paramsearch,page);
				}else {
					allNationalityType=fWMS_NATIONALITYRepository.findAllAPPROVEDFWMS_NATIONALITY(page);	
				}
			}else {
				allNationalityType=fWMS_NATIONALITYRepository.findAllAPPROVEDFWMS_NATIONALITY(page);
			}
		}else if(action.equalsIgnoreCase("UNAPPROVED"))
		{
			if(paramsearch!=null) {
				if(paramsearch.isEmpty()==false)
				{
					allNationalityType=fWMS_NATIONALITYRepository.findAllUNAPPROVEDFWMS_NATIONALITYSearching(paramsearch,page);
				}else {
					allNationalityType=fWMS_NATIONALITYRepository.findAllUNAPPROVEDFWMS_NATIONALITY(page);	
				}
			}else {
				allNationalityType=fWMS_NATIONALITYRepository.findAllUNAPPROVEDFWMS_NATIONALITY(page);
			}
		}
		
		if(allNationalityType!=null)
		{
			Page<Map<String,Object>> jsonData=allNationalityType.map(new Converter<FWMS_NATIONALITY, Map<String,Object>>() {

				@Override
				public Map<String, Object> convert(FWMS_NATIONALITY fund_cur) {
					Map<String,Object>json=new HashMap<>();
					json.put("nationalityid", fund_cur.getWMS_NATIONALITY_ID());
					json.put("nationalityname", fund_cur.getWMS_NATIONALITY_DESC());
					json.put("nationalityshortname", fund_cur.getWMS_SHORT_DESC());
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