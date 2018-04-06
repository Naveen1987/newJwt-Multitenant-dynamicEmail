package com.fynisys.controller.clienttype;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
/*
WMS_SNO	NUMBER (4)
WMS_CLIENT_TYPE	NUMBER (4)
WMS_DOCUMENT_TYPE	NUMBER (4)
WMS_STATUS	VARCHAR2 (20)
WMS_COMMENTS	VARCHAR2 (300)
WMS_ENTER_UID	VARCHAR2 (20)
WMS_ENTER_FPC	VARCHAR2 (30)
WMS_ENTER_DATE	DATE
WMS_LAST_UPDATE_UID	VARCHAR2 (20)
WMS_LAST_FPC	VARCHAR2 (30)
WMS_LAST_UPDATE_DATE	DATE
WMS_APPROVE_UID	VARCHAR2 (20)
WMS_APPROVE_FPC	VARCHAR2 (30)
WMS_APPROVE_DATE	DATE
 */

import com.fynisys.model.FUND_USERS;
import com.fynisys.model.FUND_USER_LOG;
import com.fynisys.model.clienttype.FUND_CLIENT_DOCUMENTS_TYPE2;
import com.fynisys.model.clienttype.FUND_MAR_CLIENT_TYPE;
import com.fynisys.model.clienttype.FWMS_REQUIRED_DOCUMENTS;

import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.clienttype.FUND_CLIENT_DOCUMENTS_TYPE2Repository;
import com.fynisys.repository.clienttype.FUND_MAR_CLIENT_TYPERespository;
import com.fynisys.repository.clienttype.FWMS_REQUIRED_DOCUMENTSRepository;
import com.fynisys.repository.clienttype.beans.RequiredDocumentBean;
import com.fynisys.utilities.FundUserValidate;



// TODO: Auto-generated Javadoc
/**
 * The Class FWMS_REQUIRED_DOCUMENTSController.
 */
@RestController
public class FWMS_REQUIRED_DOCUMENTSController{
	
	/** The fund user validate. */
	@Autowired
	FundUserValidate fundUserValidate;
	
	
	/** The UN D CLIEN T DOCUMENT S TYPE 2 repository. */
	@Autowired
	FUND_CLIENT_DOCUMENTS_TYPE2Repository fUND_CLIENT_DOCUMENTS_TYPE2Repository;
	
	/** The UN D MA R CLIEN T TYPE respository. */
	@Autowired
	FUND_MAR_CLIENT_TYPERespository fUND_MAR_CLIENT_TYPERespository;
	

	/** The UN D USE R LOG repository. */
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;

	/** The WM S REQUIRE D DOCUMENTS repository. */
	@Autowired
	FWMS_REQUIRED_DOCUMENTSRepository fWMS_REQUIRED_DOCUMENTSRepository;
	
	/** The Constant logger. */
	private static final Logger logger=LoggerFactory.getLogger("FWMS_RELATIONSHIP");
	
	/**
	 * Save.
	 *
	 * @param requestBody the request body
	 * @return the response entity
	 */
	@PostMapping("/savedocrequired")
    public ResponseEntity<?> save(RequestEntity<Map<String,Object>>requestBody){
	Map<String,Object> requestParam=requestBody.getBody();
	Map<String,Object> json=new HashMap<>();
	Object WMS_CLIENT_TYPE=requestParam.get("WMS_CLIENT_TYPE");
	Object WMS_DOCUMENT_TYPE=requestParam.get("WMS_DOCUMENT_TYPE");
	Object createdby =requestParam.get("createdby");
	Object WMS_COMMENTS=requestParam.get("comment");
	Object SVL_SCREEN=requestParam.get("screentype");
	Object SVL_DESC=requestParam.get("log");
	if(WMS_CLIENT_TYPE==null||WMS_DOCUMENT_TYPE==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null) {
		json.put("msg", "Parameter Missing");
		return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
	}
	else
	{

		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		FWMS_REQUIRED_DOCUMENTS fund_cur=new FWMS_REQUIRED_DOCUMENTS ();
		if(!WMS_CLIENT_TYPE.toString().isEmpty()) {
			fund_cur.setWMS_CLIENT_TYPE(new Long(WMS_CLIENT_TYPE.toString()));
		}else
		{
			json.put("msg", "Client Type can not null");
			logger.error("Client Type can not null");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		 if(!WMS_DOCUMENT_TYPE.toString().isEmpty()) {
			 fund_cur.setWMS_DOCUMENT_TYPE(new Long(WMS_DOCUMENT_TYPE.toString()));
		 }else {
				json.put("msg", "DOC Type can not null");
				logger.error("DOC Type can not null");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		 }
	
		if(WMS_COMMENTS!=null) {
		fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
		}
		fund_cur.setWMS_STATUS("Not Approved");
		
		fund_cur.setWMS_ENTER_UID(fuser.getSVC_UID());
		fund_cur.setWMS_ENTER_DATE(Calendar.getInstance());

		try
		{
			fund_cur=fWMS_REQUIRED_DOCUMENTSRepository.save(fund_cur);
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
						logger.info("Both Record and Logs saved for doc required Type:"+fund_cur.getWMS_SNO());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
				
				logger.info("Saved New doc required:"+fund_cur.getWMS_SNO());
				json.put("createdby", fund_cur.getWMS_ENTER_UID());
				json.put("createdon", fund_cur.getWMS_ENTER_DATE());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
			}
			else {
				json.put("msg","Not saved");
				logger.error("doc required not saved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e)
		{
			json.put("msg", e.getMessage());
			logger.error("doc required not saved :"+e.getMessage());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			
		}
		

	}
	
	}

	
	/**
	 * Update.
	 *
	 * @param requestBody the request body
	 * @return the response entity
	 */
	@PostMapping("/updatedocrequired")
    public ResponseEntity<?> update(RequestEntity<Map<String,Object>>requestBody){
	Map<String,Object> requestParam=requestBody.getBody();
	Map<String,Object> json=new HashMap<>();
	Object WMS_SNO=requestParam.get("WMS_SNO");
	Object WMS_CLIENT_TYPE=requestParam.get("WMS_CLIENT_TYPE");
	Object WMS_DOCUMENT_TYPE=requestParam.get("WMS_DOCUMENT_TYPE");
	Object createdby =requestParam.get("modifiedby");
	Object WMS_COMMENTS=requestParam.get("comment");
	Object SVL_SCREEN=requestParam.get("screentype");
	Object SVL_DESC=requestParam.get("log");
	if(WMS_SNO==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null) {
		json.put("msg", "Parameter Missing");
		return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
	}
	else
	{

		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FWMS_REQUIRED_DOCUMENTS fund_cur=fWMS_REQUIRED_DOCUMENTSRepository.findOne(new Long(WMS_SNO.toString()));
		if(fund_cur==null) {
			json.put("msg", "Not valied DOC required");
			logger.error("Not valied DOC required");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		if(WMS_CLIENT_TYPE!=null) {
			if(!WMS_CLIENT_TYPE.toString().isEmpty()) {
				fund_cur.setWMS_CLIENT_TYPE(new Long(WMS_CLIENT_TYPE.toString()));
			}else
			{
				json.put("msg", "Client Type can not null");
				logger.error("Client Type can not null");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		if(WMS_DOCUMENT_TYPE!=null) {
			if(!WMS_DOCUMENT_TYPE.toString().isEmpty()) {
				 fund_cur.setWMS_DOCUMENT_TYPE(new Long(WMS_DOCUMENT_TYPE.toString()));
			 }else {
					json.put("msg", "DOC Type can not null");
					logger.error("DOC Type can not null");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			 }
		
		}
		 
		if(WMS_COMMENTS!=null) {
		fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
		}
		
		//fund_cur.setWMS_STATUS("Not Approved");
		fund_cur.setWMS_LAST_UPDATE_UID(fuser.getSVC_UID());
		fund_cur.setWMS_LAST_UPDATE_DATE(Calendar.getInstance());

		try
		{
			fund_cur=fWMS_REQUIRED_DOCUMENTSRepository.save(fund_cur);
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
						logger.info("Both Record and Logs saved for doc required Type:"+fund_cur.getWMS_SNO());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
				
				logger.info("Saved New doc required:"+fund_cur.getWMS_SNO());
				//json generated
				RequiredDocumentBean jsonBean=getJson(fund_cur);
				jsonBean.setMsg("saved");
				return new ResponseEntity<RequiredDocumentBean>(jsonBean,HttpStatus.OK);
			}
			else {
				json.put("msg","Not saved");
				logger.error("doc required not saved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e)
		{
			json.put("msg", e.getMessage());
			logger.error("doc required not saved :"+e.getMessage());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			
		}
		

	}
	
	}


	/**
	 * Approve.
	 *
	 * @param requestBody the request body
	 * @return the response entity
	 */
	@PostMapping("/approvedocrequired")
    public ResponseEntity<?> approve(RequestEntity<Map<String,Object>>requestBody){
	Map<String,Object> requestParam=requestBody.getBody();
	Map<String,Object> json=new HashMap<>();
	Object WMS_SNO=requestParam.get("WMS_SNO");
	
	Object createdby =requestParam.get("approvedby");
	Object WMS_COMMENTS=requestParam.get("comment");
	Object SVL_SCREEN=requestParam.get("screentype");
	Object SVL_DESC=requestParam.get("log");
	if(WMS_SNO==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null) {
		json.put("msg", "Parameter Missing");
		return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
	}
	else
	{

		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		
		FWMS_REQUIRED_DOCUMENTS fund_cur=fWMS_REQUIRED_DOCUMENTSRepository.findOne(new Long(WMS_SNO.toString()));
		if(fund_cur==null) {
			json.put("msg", "Not valied DOC required");
			logger.error("Not valied DOC required");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		FUND_MAR_CLIENT_TYPE fund_client=fUND_MAR_CLIENT_TYPERespository.findOne(fund_cur.getWMS_CLIENT_TYPE());
		if(fund_client.getIV_APPROVE_UID()==null) {
			json.put("msg", "Client type is not approved");
			logger.error("Client type is not approved");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		FUND_CLIENT_DOCUMENTS_TYPE2 doctype=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findOne(fund_cur.getWMS_DOCUMENT_TYPE());
		if(doctype.getWMS_APPROVE_UID()==null) {
			json.put("msg", "Doc type is not approved");
			logger.error("Doc type is not approved");
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
			fund_cur=fWMS_REQUIRED_DOCUMENTSRepository.save(fund_cur);
			if(fund_cur!=null)
			{
				json.put("msg", "saved");
									
					FUND_USER_LOG ful=new FUND_USER_LOG();
					ful.setSVC_UID(fuser.getSVC_UID());
					ful.setSVL_USERID(fuser.getSVU_NAME());
					ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
					ful.setSVL_TTYPE("APPROVE");
					ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
					ful.setSVL_DATE(Calendar.getInstance());
					ful=fUND_USER_LOGRepository.save(ful);
					if(ful!=null) {
						logger.info("Both Record and Logs saved for doc required Type:"+fund_cur.getWMS_SNO());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
				
				logger.info("Saved New doc required:"+fund_cur.getWMS_SNO());
				//json generated
				RequiredDocumentBean jsonBean=getJson(fund_cur);
				jsonBean.setMsg("saved");
				return new ResponseEntity<RequiredDocumentBean>(jsonBean,HttpStatus.OK);
			}
			else {
				json.put("msg","Not saved");
				logger.error("doc required not saved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e)
		{
			json.put("msg", e.getMessage());
			logger.error("doc required not saved :"+e.getMessage());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			
		}
		

	}
	
	}

	/**
	 * Revoke.
	 *
	 * @param requestBody the request body
	 * @return the response entity
	 */
	@PostMapping("/revokedocrequired")
    public ResponseEntity<?> revoke(RequestEntity<Map<String,Object>>requestBody){
	Map<String,Object> requestParam=requestBody.getBody();
	Map<String,Object> json=new HashMap<>();
	Object WMS_SNO=requestParam.get("WMS_SNO");
	
	Object createdby =requestParam.get("modifiedby");
	Object WMS_COMMENTS=requestParam.get("comment");
	Object SVL_SCREEN=requestParam.get("screentype");
	Object SVL_DESC=requestParam.get("log");
	if(WMS_SNO==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null) {
		json.put("msg", "Parameter Missing");
		return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
	}
	else
	{

		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FWMS_REQUIRED_DOCUMENTS fund_cur=fWMS_REQUIRED_DOCUMENTSRepository.findOne(new Long(WMS_SNO.toString()));
	
		
		if(fund_cur==null) {
			json.put("msg", "Not valied DOC required");
			logger.error("Not valied DOC required");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		
		
		if(WMS_COMMENTS!=null) {
		fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
		}
		
		fund_cur.setWMS_STATUS("Not Approved");
		fund_cur.setWMS_APPROVE_UID(null);
		fund_cur.setWMS_APPROVE_DATE(null);
		fund_cur.setWMS_LAST_UPDATE_UID(fuser.getSVC_UID());
		fund_cur.setWMS_LAST_UPDATE_DATE(Calendar.getInstance());

		
		try
		{
			fund_cur=fWMS_REQUIRED_DOCUMENTSRepository.save(fund_cur);
			if(fund_cur!=null)
			{
				json.put("msg", "saved");
									
					FUND_USER_LOG ful=new FUND_USER_LOG();
					ful.setSVC_UID(fuser.getSVC_UID());
					ful.setSVL_USERID(fuser.getSVU_NAME());
					ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
					ful.setSVL_TTYPE("REVOKE");
					ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
					ful.setSVL_DATE(Calendar.getInstance());
					ful=fUND_USER_LOGRepository.save(ful);
					if(ful!=null) {
						logger.info("Both Record and Logs saved for doc required Type:"+fund_cur.getWMS_SNO());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
				
				logger.info("Saved New doc required:"+fund_cur.getWMS_SNO());
				//json generated
				RequiredDocumentBean jsonBean=getJson(fund_cur);
				jsonBean.setMsg("saved");
				return new ResponseEntity<RequiredDocumentBean>(jsonBean,HttpStatus.OK);
			}
			else {
				json.put("msg","Not saved");
				logger.error("doc required not saved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e)
		{
			json.put("msg", e.getMessage());
			logger.error("doc required not saved :"+e.getMessage());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			
		}
		

	}
	
	}
	
	/**
	 * Delete.
	 *
	 * @param requestBody the request body
	 * @return the response entity
	 */
	@PostMapping("/deletedocrequired")
    public ResponseEntity<?> delete(RequestEntity<Map<String,Object>>requestBody){
	Map<String,Object> requestParam=requestBody.getBody();
	Map<String,Object> json=new HashMap<>();
	Object WMS_SNO=requestParam.get("WMS_SNO");
	
	Object createdby =requestParam.get("modifiedby");
	Object WMS_COMMENTS=requestParam.get("comment");
	Object SVL_SCREEN=requestParam.get("screentype");
	Object SVL_DESC=requestParam.get("log");
	if(WMS_SNO==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null) {
		json.put("msg", "Parameter Missing");
		return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
	}
	else
	{

		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FWMS_REQUIRED_DOCUMENTS fund_cur=fWMS_REQUIRED_DOCUMENTSRepository.findOne(new Long(WMS_SNO.toString()));
	
		
		if(fund_cur==null) {
			json.put("msg", "Not valied DOC required");
			logger.error("Not valied DOC required");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		
		
		if(WMS_COMMENTS!=null) {
		fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
		}
		
		fund_cur.setWMS_STATUS("Not Approved");
		fund_cur.setWMS_APPROVE_UID(null);
		fund_cur.setWMS_APPROVE_DATE(null);
		fund_cur.setWMS_LAST_UPDATE_UID(fuser.getSVC_UID());
		fund_cur.setWMS_LAST_UPDATE_DATE(Calendar.getInstance());

		
		try
		{
			fWMS_REQUIRED_DOCUMENTSRepository.delete(fund_cur);
			json.put("msg", "Deleted");
			logger.info("Deleted");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
//			if(fund_cur!=null)
//			{
//				json.put("msg", "saved");
//									
//					FUND_USER_LOG ful=new FUND_USER_LOG();
//					ful.setSVC_UID(fuser.getSVC_UID());
//					ful.setSVL_USERID(fuser.getSVU_NAME());
//					ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
//					ful.setSVL_TTYPE("REVOKE");
//					ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
//					ful.setSVL_DATE(Calendar.getInstance());
//					ful=fUND_USER_LOGRepository.save(ful);
//					if(ful!=null) {
//						logger.info("Both Record and Logs saved for doc required Type:"+fund_cur.getWMS_SNO());
//						json.put("logs","logs are saved");
//					}
//					else {
//						logger.info("Record is saved but logs can't saved due error in saving of logs");
//						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
//					}
//				
//				logger.info("Saved New doc required:"+fund_cur.getWMS_SNO());
//				//json generated
//				json=getJson(fund_cur);
//				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
//			}
//			else {
//				json.put("msg","Not saved");
//				logger.error("doc required not saved");
//				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
//			}
		}
		catch(Exception e)
		{
			json.put("msg", e.getMessage());
			logger.error("doc required not saved :"+e.getMessage());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			
			}
		

		}
	
	}
	
	/**
	 * Gets the all stop losspage.
	 *
	 * @param page the page
	 * @param action the action
	 * @param paramString the param string
	 * @return the all stop losspage
	 */
	@GetMapping("/docrequiredsearch")
	public ResponseEntity<?> getAllStopLosspage(Pageable page,
			@RequestParam(required=false,value="action")String action,
			@RequestParam(required=false,value="paramSearch")String paramString
			){
		List<Long> clientids=new ArrayList<>();
		List<Long> doctypeids=new ArrayList<>();
		List<String> userids=new ArrayList<>();
		if(paramString!=null) {
			if(paramString.isEmpty()==false) {
				clientids=fUND_MAR_CLIENT_TYPERespository.find_ids_FUND_MAR_CLIENT_TYPE(paramString);
				doctypeids=fUND_CLIENT_DOCUMENTS_TYPE2Repository.find_Ids_FUND_CLIENT_DOCUMENTS_TYPE2(paramString);
				userids=fundUserValidate.getUserIds(paramString);
				if(clientids.isEmpty()) {
					clientids.add(new Long("-1"));
				}
				if(doctypeids.isEmpty()) {
					doctypeids.add(new Long("-1"));
				}
				if(userids.isEmpty()) {
					userids.add("-1".toString());
				}
			}
			}
		Page<FWMS_REQUIRED_DOCUMENTS> allFWMS_REQUIRED_DOCUMENTS=null;
		if(action!=null) {
			if(action.equalsIgnoreCase("ALL")) {
				if(paramString!=null) {
					if(paramString.isEmpty()==false) {
						allFWMS_REQUIRED_DOCUMENTS=fWMS_REQUIRED_DOCUMENTSRepository.findAllFWMS_REQUIRED_DOCUMENTS_SEARCH(clientids, doctypeids, userids, page);
					}else {
						allFWMS_REQUIRED_DOCUMENTS=fWMS_REQUIRED_DOCUMENTSRepository.findAll(page);
					}
				}else {
					allFWMS_REQUIRED_DOCUMENTS=fWMS_REQUIRED_DOCUMENTSRepository.findAll(page);
				}
			}else if(action.equalsIgnoreCase("APPROVED")) {
				if(paramString!=null) {
					if(paramString.isEmpty()==false) {
						allFWMS_REQUIRED_DOCUMENTS=fWMS_REQUIRED_DOCUMENTSRepository.findAllFWMS_REQUIRED_DOCUMENTS_SEARCHAPPROVED(clientids, doctypeids, userids, page);
					}else {
						allFWMS_REQUIRED_DOCUMENTS=fWMS_REQUIRED_DOCUMENTSRepository.findAllAPPROVEDFWMS_REQUIRED_DOCUMENTS(page);
					}
				}else {
					allFWMS_REQUIRED_DOCUMENTS=fWMS_REQUIRED_DOCUMENTSRepository.findAllAPPROVEDFWMS_REQUIRED_DOCUMENTS(page);
				}
			}else if(action.equalsIgnoreCase("UNAPPROVED")) {
				if(paramString!=null) {
					if(paramString.isEmpty()==false) {
						allFWMS_REQUIRED_DOCUMENTS=fWMS_REQUIRED_DOCUMENTSRepository.findAllFWMS_REQUIRED_DOCUMENTS_SEARCHUNAPPROVED(clientids, doctypeids, userids, page);
					}else {
						allFWMS_REQUIRED_DOCUMENTS=fWMS_REQUIRED_DOCUMENTSRepository.findAllUNAPPROVEDFWMS_REQUIRED_DOCUMENTS(page);
					}
				}else {
					allFWMS_REQUIRED_DOCUMENTS=fWMS_REQUIRED_DOCUMENTSRepository.findAllUNAPPROVEDFWMS_REQUIRED_DOCUMENTS(page);
				}
			} 
		}else {

			Map<String,Object>json=new HashMap<>();
			json.put("msg", "Value of action is missing");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
		}
		
		//allFWMS_REQUIRED_DOCUMENTS=fWMS_REQUIRED_DOCUMENTSRepository.findAll(page);
		Page<RequiredDocumentBean> jsonArray=allFWMS_REQUIRED_DOCUMENTS.map(new Converter<FWMS_REQUIRED_DOCUMENTS, RequiredDocumentBean>() {
			@Override
			public RequiredDocumentBean convert(FWMS_REQUIRED_DOCUMENTS conut) {
				RequiredDocumentBean json=getJson(conut);
			    return json;
			}
			
		});
		
		List<RequiredDocumentBean> jsonlist=new ArrayList<>(jsonArray.getContent());
//		System.out.println(jsonlist);
		Collections.sort(jsonlist);
		//Data reading for for Paging
		int start = page.getOffset();
		int end = (start + page.getPageSize()) > jsonlist.size() ? jsonlist.size()
				: (start + page.getPageSize());
		jsonArray = new PageImpl<RequiredDocumentBean>(jsonlist.subList(start, end), page,
				jsonlist.size());		
		return new ResponseEntity<>(jsonArray,HttpStatus.OK);
		
		}
		

	
	
	
	
	
	/**
	 * Gets the json.
	 *
	 * @param fund_cur the fund cur
	 * @return the json
	 */
	/*
	 * Json for all
	 */
		public RequiredDocumentBean getJson(FWMS_REQUIRED_DOCUMENTS fund_cur){
			
			RequiredDocumentBean json=new RequiredDocumentBean();
			json.setDocreqid(fund_cur.getWMS_SNO()+"");
			FUND_MAR_CLIENT_TYPE fund_client=fUND_MAR_CLIENT_TYPERespository.findOne(fund_cur.getWMS_CLIENT_TYPE());
			json.setClienttypeid( fund_client.getFCT_ID()+"");
			json.setClienttypename( fund_client.getFCT_NAME());
			
			FUND_CLIENT_DOCUMENTS_TYPE2 doctype=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findOne(fund_cur.getWMS_DOCUMENT_TYPE());
			json.setDocid( doctype.getSCD_TYPE()+"");
			json.setDoctypename( doctype.getSCD_DESC());
			json.setComment( fund_cur.getWMS_COMMENTS());
			json.setStatus( fund_cur.getWMS_STATUS());
			FUND_USERS user=null;
			if(fund_cur.getWMS_ENTER_UID()!=null) {
				user=fundUserValidate.getUserName(fund_cur.getWMS_ENTER_UID());
				if(user!=null) {
				json.setEnteredby( user.getSVU_NAME());
				json.setEnteredbyuserid( user.getSVU_USER_NAME());
				json.setEnteredbyuuid( user.getSVC_UID());
				json.setEntereddate( fund_cur.getWMS_ENTER_DATE());
				}
				else {
					json.setEnteredby( null);
					json.setEnteredbyuserid( null);
					json.setEnteredbyuuid( null);
					json.setEntereddate( null);
				}
			}else {
				json.setEnteredby( null);
				json.setEnteredbyuserid( null);
				json.setEnteredbyuuid( null);
				json.setEntereddate( null);
			}
			
			if(fund_cur.getWMS_APPROVE_UID()!=null) {
				user=fundUserValidate.getUserName(fund_cur.getWMS_APPROVE_UID());
				if(user!=null) {
				json.setApprovedby( user.getSVU_NAME());
				json.setApprovedbyuserid( user.getSVU_USER_NAME());
				json.setApprovedbyuuid( user.getSVC_UID());
				json.setApproveddate( fund_cur.getWMS_APPROVE_DATE());
				}
				else {
					json.setApprovedby( null);
					json.setApprovedbyuserid( null);
					json.setApprovedbyuuid( null);
					json.setApproveddate( null);
				}
			}else {
				json.setApprovedby( null);
				json.setApprovedbyuserid( null);
				json.setApprovedbyuuid( null);
				json.setApproveddate( null);
			}
			
			if(fund_cur.getWMS_LAST_UPDATE_UID()!=null) {
				user=fundUserValidate.getUserName(fund_cur.getWMS_LAST_UPDATE_UID());
				if(user!=null) {
				json.setModifiedby( user.getSVU_NAME());
				json.setModifiedbyuserid( user.getSVU_USER_NAME());
				json.setModifiedbyuuid( user.getSVC_UID());
				json.setModifieddate( fund_cur.getWMS_LAST_UPDATE_DATE());
				}
				else {
					json.setModifiedby( null);
					json.setModifiedbyuserid( null);
					json.setModifiedbyuuid( null);
					json.setModifieddate( null);
				}
			}else {
				json.setModifiedby( null);
				json.setModifiedbyuserid( null);
				json.setModifiedbyuuid( null);
				json.setModifieddate( null);
			}
			return json;
		}
	
}