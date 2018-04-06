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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.FUND_USER_LOG;
import com.fynisys.model.parameters.FUND_CURRENCY_MSTR;
import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.parameters.FUND_CURRENCY_MSTRRepositroy;

@RestController
public class FUND_CURRENCY_MSTRController {
	
	@Autowired
	FUND_USERSRepository fund_UserRepository;
	
	@Autowired
	FUND_CURRENCY_MSTRRepositroy fUND_CURRENCY_MSTRRepositroy;
	
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;
	
	private static final Logger logger=LoggerFactory.getLogger("FUND CURRENCY CONTROLLER");
	
	@PostMapping("/savecurrency")
	public ResponseEntity<Map<String,Object>> saveCurrency(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		Object SVC_NAME=dataMap.get("currencyname");
		Object SVC_SHORT_NAME=dataMap.get("currencyshortname");
		Object createdby =dataMap.get("createdby");
		Object WMS_COMMENTS=dataMap.get("comment");
		Object SVL_SCREEN=dataMap.get("screentype");
		Object SVL_DESC=dataMap.get("log");
		if(SVC_NAME==null||createdby==null||SVC_SHORT_NAME==null||SVL_SCREEN==null||SVL_DESC==null)
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
			FUND_CURRENCY_MSTR fund_cur=new FUND_CURRENCY_MSTR();
			
			
			fund_cur.setSVC_NAME(SVC_NAME.toString().trim());
			fund_cur.setSVC_SHORT_NAME(SVC_SHORT_NAME.toString());
			if(WMS_COMMENTS!=null) {
			fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
			}
			fund_cur.setWMS_STATUS("Not Approved");
			
			fund_cur.setSVC_UID(fuser.getSVC_UID());		
			fund_cur.setIV_ENTER_UID(fuser.getSVC_UID());
			fund_cur.setIV_ENTER_DATE(Calendar.getInstance());
//			fund_cur.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
//			fund_cur.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
			try
			{
				fund_cur=fUND_CURRENCY_MSTRRepositroy.save(fund_cur);
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
							logger.info("Both Record and Logs saved for currency:"+fund_cur.getSVC_NAME());
							json.put("logs","logs are saved");
						}
						else {
							logger.info("Record is saved but logs can't saved due error in saving of logs");
							json.put("logs","Record is saved but logs can't saved due error in saving of logs");
						}
					
					logger.info("Saved New Currency:"+fund_cur.getSVC_NAME());
					json.put("countryid", fund_cur.getSVC_CODE());
					json.put("countryname", fund_cur.getSVC_NAME());
					json.put("createdby", fund_cur.getIV_ENTER_UID());
					json.put("createdon", fund_cur.getIV_ENTER_DATE());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Currency not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Currency not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
			
			
		}
	}
	
	@PostMapping("/updatecurrency")
	public ResponseEntity<Map<String,Object>> updateCurrency(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		Object SVC_CODE=dataMap.get("currencyid");
		Object SVC_NAME=dataMap.get("currencyname");
		Object SVC_SHORT_NAME=dataMap.get("currencyshortname");
		Object createdby =dataMap.get("modifiedby");
		Object WMS_COMMENTS=dataMap.get("comment");
		Object SVL_SCREEN=dataMap.get("screentype");
		Object SVL_DESC=dataMap.get("log");
		
		if(SVC_CODE==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
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
			FUND_CURRENCY_MSTR fund_cur=fUND_CURRENCY_MSTRRepositroy.findOne(new Integer(SVC_CODE.toString()));
			if(fund_cur==null) {
				json.put("msg", "No Currency found with this id->"+SVC_CODE.toString());
				logger.error("No Currency found with this id->"+SVC_CODE.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
			if(SVC_NAME!=null) {
			fund_cur.setSVC_NAME(SVC_NAME.toString().trim());
			}
			if(SVC_SHORT_NAME!=null) {
				fund_cur.setSVC_SHORT_NAME(SVC_SHORT_NAME.toString());
			}
			if(WMS_COMMENTS!=null) {
			fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
			}
			
			
			
			fund_cur.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
			fund_cur.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
			try
			{
				fund_cur=fUND_CURRENCY_MSTRRepositroy.save(fund_cur);
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
							logger.info("Both Record and Logs saved for currency:"+fund_cur.getSVC_NAME());
							
						}
						else {
							logger.info("Record is saved but logs can't saved due error in saving of logs");
							json.put("logs","Record is saved but logs can't saved due error in saving of logs");
						}
					
					
					logger.info("Currency saved:"+fund_cur.getSVC_NAME());
					json.put("countryid", fund_cur.getSVC_CODE());
					json.put("currencyname", fund_cur.getSVC_NAME());
					json.put("currencyshortname", fund_cur.getSVC_SHORT_NAME());
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
				logger.error("Currency Not saved"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
			
			
		}
	}
	
	@PostMapping("/deletecurrency")
	public ResponseEntity<Map<String,Object>> deleteCurrency(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> dataMap=requestData.getBody();
		Object SVC_CODE=dataMap.get("currencyid");
		Object createdby =dataMap.get("modifiedby");
		
		if(SVC_CODE==null||createdby==null)
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
			FUND_CURRENCY_MSTR fund_cur=fUND_CURRENCY_MSTRRepositroy.findOne(new Integer(SVC_CODE.toString()));
			if(fund_cur==null) {
				json.put("msg", "No Currency found with this id->"+SVC_CODE.toString());
				logger.error("No Currency found with this id->"+SVC_CODE.toString());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
			
			try
			{
				fUND_CURRENCY_MSTRRepositroy.delete(fund_cur);
				
					json.put("msg", "Deleted");
					logger.info("Currency Detetd:"+fund_cur.getSVC_NAME());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
			
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Currency Not Detetd"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
			
			
		}
	}
	
		@PostMapping("approvecurrency")
		public ResponseEntity<Map<String,Object>> approveCurrency(RequestEntity<Map<String,Object>> requestData)
		{
			Map<String,Object> json=new HashMap<String,Object>();
			Map<String,Object> dataMap=requestData.getBody();
			
			
			Object SVC_CODE=dataMap.get("currencyid");
			Object createdby =dataMap.get("approvedby");
			Object WMS_COMMENTS=dataMap.get("comment");
			
			if(createdby==null||SVC_CODE==null)
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
				FUND_CURRENCY_MSTR fund_cur=fUND_CURRENCY_MSTRRepositroy.findOne(new Integer(SVC_CODE.toString()));
				if(fund_cur==null) {
					json.put("msg", "No Currency found with this id->"+SVC_CODE.toString());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
				if(WMS_COMMENTS!=null) {
				fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
				/*
				 * Approve Status
				 * */
				fund_cur.setWMS_STATUS("Approved");
				fund_cur.setIV_APPROVE_UID(fuser.getSVC_UID());
				fund_cur.setIV_APPROVE_DATE(Calendar.getInstance());
				
//				fund_cur.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
//				fund_cur.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
				
				
				try
				{
					fund_cur=fUND_CURRENCY_MSTRRepositroy.save(fund_cur);
					if(fund_cur!=null)
					{
						json.put("msg", "saved");
						logger.info("Currency Arroved saved :"+fund_cur.getSVC_NAME());
						json.put("countryid", fund_cur.getSVC_CODE());
						json.put("countryname", fund_cur.getSVC_NAME());
						FUND_USERS user=null;
						json.put("comments", fund_cur.getWMS_COMMENTS());
						json.put("status", fund_cur.getWMS_STATUS());
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
						logger.info("Currency Not saved without any error");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
				}
				catch(Exception e)
				{
					json.put("msg", e.getMessage());
					logger.info("Currency Not saved"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					
				}
				
				
			}
		}
		
		
		@PostMapping("revokecurrency")
		public ResponseEntity<Map<String,Object>> revokeCurrency(RequestEntity<Map<String,Object>> requestData)
		{
			Map<String,Object> json=new HashMap<String,Object>();
			Map<String,Object> dataMap=requestData.getBody();
			
			
			Object SVC_CODE=dataMap.get("currencyid");
			Object createdby =dataMap.get("modifiedby");
			Object WMS_COMMENTS=dataMap.get("comment");
			
			if(SVC_CODE==null||createdby==null||WMS_COMMENTS==null)
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
				FUND_CURRENCY_MSTR fund_cur=fUND_CURRENCY_MSTRRepositroy.findOne(new Integer(SVC_CODE.toString()));
				if(fund_cur==null) {
					json.put("msg", "No Currency found with this id->"+SVC_CODE.toString());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
				
				fund_cur.setWMS_COMMENTS(WMS_COMMENTS.toString());
				/*
				 * Approve Status
				 * */
				fund_cur.setWMS_STATUS("Not Approved");
				fund_cur.setIV_APPROVE_UID(null);
				fund_cur.setIV_APPROVE_DATE(null);
//				fund_cur.setIV_APPROVE_UID(fuser.getSVC_UID());
//				fund_cur.setIV_APPROVE_DATE(Calendar.getInstance());
				
				fund_cur.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
				fund_cur.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
				
				try
				{
					fund_cur=fUND_CURRENCY_MSTRRepositroy.save(fund_cur);
					if(fund_cur!=null)
					{
						json.put("msg", "saved");
						logger.info("Currency Revoke saved :"+fund_cur.getSVC_NAME());
						json.put("countryid", fund_cur.getSVC_CODE());
						json.put("countryname", fund_cur.getSVC_NAME());
						FUND_USERS user=null;
						json.put("comments", fund_cur.getWMS_COMMENTS());
						json.put("status", fund_cur.getWMS_STATUS());
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
						logger.info("Currency Not saved without any error");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
				}
				catch(Exception e)
				{
					json.put("msg", e.getMessage());
					logger.info("Currency Not saved"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					
				}
				
				
			}
				
		}

		
		@PostMapping("/currencies")
		public ResponseEntity<List<Map<String,Object>>> getAllCurrencies(RequestEntity<Map<String,Object>> requestData)
		{
			List<Map<String,Object>> JsonCountry=new ArrayList<Map<String,Object>>();
			Map<String,Object> requestJson=requestData.getBody();
			String requestString=(String)requestJson.get("action");
			if(requestString==null) {
				fUND_CURRENCY_MSTRRepositroy.findAllCurrency().forEach(conut->{
					Map<String,Object>json=getJson(conut);
					JsonCountry.add(json);
				});
			}else if(requestString.equalsIgnoreCase("ALL")) {
				fUND_CURRENCY_MSTRRepositroy.findAllCurrency().forEach(conut->{
					Map<String,Object>json=getJson(conut);
					JsonCountry.add(json);
				});
			}else if(requestString.equalsIgnoreCase("APPROVED")) {
				fUND_CURRENCY_MSTRRepositroy.findAllAPPROVEDCurrency().forEach(conut->{
					Map<String,Object>json=getJson(conut);		
					JsonCountry.add(json);
				});
			}else if(requestString.equalsIgnoreCase("UNAPPROVED")) {
				fUND_CURRENCY_MSTRRepositroy.findAllUNAPPROVEDCurrency().forEach(conut->{
					Map<String,Object>json=getJson(conut);
					JsonCountry.add(json);
				});
			}
			
			return new ResponseEntity<List<Map<String,Object>>>(JsonCountry,HttpStatus.OK);
		}	

		@GetMapping("/currencyList")
		public ResponseEntity<List<Map<String,Object>>> getAllCurrenciesWithList(@RequestParam(value="param",required=true)String param)
		{
			List<Map<String,Object>> JsonCountry=new ArrayList<Map<String,Object>>();
			if(param.isEmpty()==false) {
			  fUND_CURRENCY_MSTRRepositroy.findAllCurrencies(param).forEach(conut->{
					Map<String,Object>json=getJson(conut);
					JsonCountry.add(json);
				});
			}else {
				 fUND_CURRENCY_MSTRRepositroy.findAll().forEach(conut->{
						Map<String,Object>json=getJson(conut);
						JsonCountry.add(json);
					});
			}
			
			return new ResponseEntity<List<Map<String,Object>>>(JsonCountry,HttpStatus.OK);
		}	

   		
   		
		
	public Map<String,Object> getJson(FUND_CURRENCY_MSTR conut)	{
		FUND_USERS user=null;
		Map<String,Object> json=new HashMap<String,Object>();
		json.put("currencyid", conut.getSVC_CODE());
		json.put("currencyname", conut.getSVC_NAME());
		json.put("currencyshortname", conut.getSVC_SHORT_NAME());
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
		return json;
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