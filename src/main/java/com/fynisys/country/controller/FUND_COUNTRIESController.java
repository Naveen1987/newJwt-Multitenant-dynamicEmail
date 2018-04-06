package com.fynisys.country.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.convert.converter.Converter;
import com.fynisys.country.repository.FUND_COUNTRIESRepository;
import com.fynisys.model.FUND_COUNTRIES;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.FUND_USER_LOG;
import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USER_LOGRepository;





@RestController
public class FUND_COUNTRIESController {
	
	@Autowired
	FUND_USERSRepository fund_UserRepository;
	
	@Autowired
	FUND_COUNTRIESRepository fund_CountriesRepository;
	
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;
	
	private static final Logger logger=LoggerFactory.getLogger("FUND COUNTRY CONTROLLER");
	
//	@GetMapping("countrytest")
//	public String countryCheck(@RequestParam("countryname") String countryname) {
//		FUND_COUNTRIES fund_country=fund_CountriesRepository.getCountryCheck(countryname);
//		if(fund_country==null) {
//			return true+"";
//		}
//		return false+"";
//		
//	}
	
	
	public boolean countryCheck(String countryname) {
		List<FUND_COUNTRIES> fund_country=fund_CountriesRepository.getCountryCheck(countryname);
		System.out.println(fund_country);
		if(fund_country==null) {
			return true;
		}else if(fund_country.isEmpty()) {
			return true;
		}
		return false;
		
	}
	
//	@GetMapping("cn")
//	public boolean countryUpdateCheckUI(@RequestParam("cn")String countryname,@RequestParam("ci")long countryid) {
//		List<FUND_COUNTRIES> fund_country = fund_CountriesRepository.getCountryCheck(countryname);
//		if(fund_country==null) {
//			return true;
//			}
//		else if(fund_country.isEmpty()) {
//			return true;
//			}
//		else if(fund_country.size()>0) {
//			boolean status=false;
//			for(FUND_COUNTRIES country:fund_country) {
//				if(country.getSVC_ID()==countryid)
//				{
//				status=true;
//				break;
//				}
//			}
//		return status;
//		}
//		return false;
//	}
	public boolean countryUpdateCheck(String countryname,long countryid) {
		List<FUND_COUNTRIES> fund_country = fund_CountriesRepository.getCountryCheck(countryname);
		if(fund_country==null) {
			return true;
		}
		else if(fund_country.isEmpty()) {
			return true;
		}
		else if(fund_country.size()>0) {
			boolean status=false;
			for(FUND_COUNTRIES country:fund_country) {
				
				if(country.getSVC_ID()==countryid)
				{
				status=true;
				break;
				}
			}
			return status;
		}
		return false;
	}
	
	@PostMapping("/savecountry")
	public ResponseEntity<Map<String,Object>> saveCountry(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> data=requestData.getBody();
		String SVC_CODE=(String) data.get("isocode");
		String SVC_NAME=(String) data.get("countryname");
		String comments=(String) data.get("comments");
		String IV_ENTER_UID=(String) data.get("createdby");
		Object SVL_SCREEN=data.get("screentype");
		Object SVL_DESC=data.get("log");
		if(SVC_CODE==null||SVC_NAME==null||IV_ENTER_UID==null||SVL_SCREEN==null||SVL_DESC==null)
		{
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		else
		{
			if(countryCheck(SVC_NAME.trim())==false) {
				json.put("msg", "country exists");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
			FUND_USERS fuser=isValid(IV_ENTER_UID.toString());
			if(fuser==null)
			{
					json.put("msg", "Createdby is not valid user");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			FUND_COUNTRIES fund_countries=new FUND_COUNTRIES();
			
			//fund_countries.setSVC_SNO(SVC_SNO);
			fund_countries.setSVC_CODE(SVC_CODE);
			fund_countries.setSVC_NAME(SVC_NAME.trim());
			fund_countries.setSVC_COMMENTS(comments);
			fund_countries.setSVC_STATUS("1");
			fund_countries.setSVC_UID(fuser.getSVC_UID());
			fund_countries.setSVC_FLOW("0");
			fund_countries.setIV_ENTER_UID(fuser.getSVC_UID());
			fund_countries.setIV_ENTER_DATE(Calendar.getInstance());
//			fund_countries.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
//			fund_countries.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
			try
			{
				fund_countries=fund_CountriesRepository.save(fund_countries);
				if(fund_countries!=null)
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
						logger.info("Both Record and Logs saved for Country:"+fund_countries.getSVC_NAME());
						json.put("logs","logs are saved");
					}
					else {
						logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
					}
				
					json.put("countryid", fund_countries.getSVC_ID());
					json.put("countryname", fund_countries.getSVC_NAME());
					json.put("createdby", fund_countries.getIV_ENTER_UID());
					json.put("createdon", fund_countries.getIV_ENTER_DATE());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
			
			
		}
	}
	
	@PostMapping("/updatecountry")
	public ResponseEntity<Map<String,Object>> updateCountry(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> data=requestData.getBody();	
		Object SVC_ID= data.get("countryid");
		String SVC_CODE=(String) data.get("isocode");
		String SVC_NAME=(String) data.get("countryname");
		String comments=(String) data.get("comments");
		String IV_ENTER_UID=(String) data.get("modifiedby");
		Object SVL_SCREEN=data.get("screentype");
		Object SVL_DESC=data.get("log");
		
		if(SVC_ID==null||IV_ENTER_UID==null||SVL_SCREEN==null||SVL_DESC==null)
		{
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		else
		{
			
			if(countryUpdateCheck(SVC_NAME.trim(),new Long(SVC_ID.toString()))==false) {
				json.put("msg", "country exists");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			FUND_USERS fuser=isValid(IV_ENTER_UID.toString());
			if(fuser==null)
			{
					json.put("msg", "Createdby is not valid user");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			FUND_COUNTRIES fund_countries=fund_CountriesRepository.findOne(new Long(SVC_ID.toString()));
			if(fund_countries!=null) {
				if(SVC_CODE!=null) {
					fund_countries.setSVC_CODE(SVC_CODE.trim());
				}
				if(SVC_NAME!=null)
				{
				fund_countries.setSVC_NAME(SVC_NAME.trim());
				}
				if(comments!=null) {
				fund_countries.setSVC_COMMENTS(comments.trim());
				}
				fund_countries.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
				fund_countries.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
				try
				{
					fund_countries=fund_CountriesRepository.save(fund_countries);
					if(fund_countries!=null)
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
							logger.info("Both Record and Logs saved for Country:"+fund_countries.getSVC_NAME());
							json.put("logs","logs are saved");
						}
						else {
							logger.info("Record is UPDATE but logs can't saved due error in saving of logs");
							json.put("logs","Record is saved but logs can't saved due error in saving of logs");
						}
						json.put("countryid",fund_countries.getSVC_ID());
						json.put("isocode",fund_countries.getSVC_CODE());
						json.put("countryname",fund_countries.getSVC_NAME());
						json.put("countryshortname",fund_countries.getSVC_SHORT_NAME());
						json.put("comment", fund_countries.getSVC_COMMENTS());
						json.put("status", fund_countries.getSVC_STATUS());
						FUND_USERS user=null;
						if(fund_countries.getIV_ENTER_UID()!=null) {
							user=getUserName(fund_countries.getSVC_UID());
							if(user!=null) {
							json.put("enteredby", user.getSVU_NAME());
							json.put("enteredbyuserid", user.getSVU_USER_NAME());
							json.put("enteredbyuuid", user.getSVC_UID());
							json.put("entereddate", fund_countries.getIV_ENTER_DATE());
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
						
						if(fund_countries.getIV_APPROVE_UID()!=null) {
							user=getUserName(fund_countries.getIV_APPROVE_UID());
							if(user!=null) {
							json.put("approvedby", user.getSVU_NAME());
							json.put("approvedbyuserid", user.getSVU_USER_NAME());
							json.put("approvedbyuuid", user.getSVC_UID());
							json.put("approveddate", fund_countries.getIV_APPROVE_DATE());
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
						
						if(fund_countries.getIV_LAST_UPDATE_UID()!=null) {
							user=getUserName(fund_countries.getIV_LAST_UPDATE_UID());
							if(user!=null) {
							json.put("modifiedby", user.getSVU_NAME());
							json.put("modifiedbyuserid", user.getSVU_USER_NAME());
							json.put("modifiedbyuuid", user.getSVC_UID());
							json.put("modifieddate", fund_countries.getIV_LAST_UPDATE_DATE());
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
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
				}
				catch(Exception e)
				{
					json.put("msg", e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					
				}	
			}
			else {
				json.put("msg", "No country foun with this id->"+SVC_ID);
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
			
		}
	}
	
		@PostMapping("deletecountry")
		public ResponseEntity<Map<String,Object>> deactiveCountry(RequestEntity<Map<String,Object>> requestData)
		{
			Map<String,Object> json=new HashMap<String,Object>();
			Map<String,Object> data=requestData.getBody();
			 Object countryid=data.get("countryid");
			 String userid=(String) data.get("userid");
			 String comments=(String) data.get("comments");
			
			 if(countryid==null||userid==null||comments==null)
			 {
				 json.put("msg", "input attribut is missing");
				 return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			 }
			 else
			 {
				 
				 FUND_USERS fund_user=isValid(userid.toString());
					if(fund_user==null)
					{
							json.put("msg", "userid is not valid");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
				FUND_COUNTRIES fund_cuntries= fund_CountriesRepository.findOne(new Long(countryid.toString()).longValue());
				if(fund_cuntries!=null) {
					/*
					 * Deactive country
					 */
						 fund_cuntries.setSVC_STATUS("0".trim());
						 fund_cuntries.setIV_LAST_UPDATE_UID(fund_user.getSVC_UID());
						 fund_cuntries.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
						 fund_cuntries.setSVC_COMMENTS(comments);
						 
					 
					 
					 try
					 {
						 fund_cuntries=fund_CountriesRepository.save(fund_cuntries); 
						 if(fund_cuntries!=null)
						 {
							 logger.info("Country-"+fund_cuntries.getSVC_NAME()+" Deleted");
							 json.put("msg", "country is deleted");
							 json.put("deletedby", fund_cuntries.getIV_LAST_UPDATE_UID());
							 json.put("deletedon", fund_cuntries.getIV_LAST_UPDATE_DATE());
							 json.put("countryid",fund_cuntries.getSVC_ID() );
							 return new ResponseEntity<>(json,HttpStatus.OK);
						 }
						 
						 else
						 {
						 		json.put("msg","Not deleted");
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							
						 }
					 }
					 
					 catch(Exception e)
					 {
						 json.put("msg", e.getMessage());
						 return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
					 }
				}else {
					json.put("msg", "No country foun with this id->"+countryid.toString());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				}
			 }
	
		@PostMapping("activecountry")
		public ResponseEntity<Map<String,Object>> activeCountry(RequestEntity<Map<String,Object>> requestData)
		{
			Map<String,Object> json=new HashMap<String,Object>();
			Map<String,Object> data=requestData.getBody();
			 Object countryid=data.get("countryid");
			 String userid=(String) data.get("userid");
			
			 if(countryid==null||userid==null)
			 {
				 json.put("msg", "input attribut is missing");
				 return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			 }
			 else
			 {
				 
				 FUND_USERS fund_user=isValid(userid.toString());
					if(fund_user==null)
					{
							json.put("msg", "userid is not valid");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
				 FUND_COUNTRIES fund_cuntries= fund_CountriesRepository.findOne(new Long(countryid.toString()).longValue());
				if(fund_cuntries==null) {
					json.put("msg", "No country foun with this id->"+countryid.toString());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				 /*
				 * Active country
				 */
					 fund_cuntries.setSVC_STATUS("1".trim());
					 fund_cuntries.setIV_LAST_UPDATE_UID(fund_user.getSVC_UID());
					 fund_cuntries.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
				 
				 
				 try
				 {
					 fund_cuntries=fund_CountriesRepository.save(fund_cuntries); 
					 if(fund_cuntries!=null)
					 {
						 json.put("msg", "country is Activated");
						 json.put("activatedby", fund_cuntries.getIV_LAST_UPDATE_UID());
						 json.put("activatedon", fund_cuntries.getIV_LAST_UPDATE_DATE());
						 return new ResponseEntity<>(json,HttpStatus.OK);
					 }
					 
					 else
					 {
					 		json.put("msg","Not deleted");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					 }
				 }
				 
				 catch(Exception e)
				 {
					 json.put("msg", e.getMessage());
					 return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				 }
				}
			
	
			 }
	
		
		@PostMapping("/approverequest")	
		public ResponseEntity<Map<String,Object>> approveRequest(RequestEntity<Map<String,Object>> requestData)
		{
			
			Map<String,Object> json=new HashMap<String,Object>();
			Map<String,Object> data=requestData.getBody();
			
			Object countryid=data.get("countryid");
			String userid=(String) data.get("userid");
			if(countryid==null||userid==null)
			{
				json.put("msg", "please enter required field");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
			else
			{
				FUND_USERS fund_user=isValid(userid);
				if(fund_user==null)
				{
					json.put("msg", "userid is valid");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
				FUND_COUNTRIES fund_countries=fund_CountriesRepository.findOne(new Long(countryid.toString()).longValue());
				if(fund_countries==null) {
					json.put("msg", "No country foun with this id->"+countryid.toString());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				fund_countries.setSVC_FLOW("1");
//				fund_countries.setIV_LAST_UPDATE_UID(fund_user.getSVC_UID());
//				fund_countries.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
				fund_countries.setIV_APPROVE_UID(fund_user.getSVC_UID());
				fund_countries.setIV_APPROVE_DATE(Calendar.getInstance());
				
				try
				{
				fund_countries=fund_CountriesRepository.save(fund_countries);
				if(fund_countries!=null)
				{
					logger.info("Country approve successfully CountryId"+fund_countries.getSVC_ID());
					json.put("countryid",fund_countries.getSVC_ID());
					json.put("isocode",fund_countries.getSVC_CODE());
					json.put("countryname",fund_countries.getSVC_NAME());
					json.put("countryshortname",fund_countries.getSVC_SHORT_NAME());
					json.put("comment", fund_countries.getSVC_COMMENTS());
					json.put("status", fund_countries.getSVC_STATUS());
					FUND_USERS user=null;
					if(fund_countries.getIV_ENTER_UID()!=null) {
						user=getUserName(fund_countries.getSVC_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", fund_countries.getIV_ENTER_DATE());
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
					
					if(fund_countries.getIV_APPROVE_UID()!=null) {
						user=getUserName(fund_countries.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", fund_countries.getIV_APPROVE_DATE());
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
					
					if(fund_countries.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(fund_countries.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", fund_countries.getIV_LAST_UPDATE_DATE());
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
					return new ResponseEntity<>(json,HttpStatus.OK);
				}
				else
				{
					json.put("msg", "approve request is not sent");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
				}
				catch(Exception e)
				{
				json.put("msg", e.getMessage());
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
				}
				
				
			
			
		}
		
		@PostMapping("/revokerequest")	
		public ResponseEntity<Map<String,Object>> revokeRequest(RequestEntity<Map<String,Object>> requestData)
		{
			
			Map<String,Object> json=new HashMap<String,Object>();
			Map<String,Object> data=requestData.getBody();
			
			Object countryid=data.get("countryid");
			String userid=(String) data.get("userid");
			String comments=(String) data.get("comments");
			if(countryid==null||userid==null||comments==null)
			{
				json.put("msg", "please enter required field");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
			else
			{
				FUND_USERS fund_user=isValid(userid);
				if(fund_user==null)
				{
					json.put("msg", "userid is valid");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
				FUND_COUNTRIES fund_countries=fund_CountriesRepository.findOne(new Long(countryid.toString()).longValue());
				if(fund_countries==null) {
					json.put("msg", "No country foun with this id->"+countryid.toString());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				fund_countries.setSVC_FLOW("0");
				fund_countries.setIV_LAST_UPDATE_UID(fund_user.getSVC_UID());
				fund_countries.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
				fund_countries.setIV_APPROVE_UID(null);
				fund_countries.setIV_APPROVE_DATE(null);
				fund_countries.setSVC_COMMENTS(comments);
				
				try
				{
				fund_countries=fund_CountriesRepository.save(fund_countries);
				if(fund_countries!=null)
				{
					logger.info("revoke request has been sent successfully CountryId:"+fund_countries.getSVC_ID());
					json.put("countryid",fund_countries.getSVC_ID());
					json.put("isocode",fund_countries.getSVC_CODE());
					json.put("countryname",fund_countries.getSVC_NAME());
					json.put("countryshortname",fund_countries.getSVC_SHORT_NAME());
					json.put("comment", fund_countries.getSVC_COMMENTS());
					json.put("status", fund_countries.getSVC_STATUS());
					FUND_USERS user=null;
					if(fund_countries.getIV_ENTER_UID()!=null) {
						user=getUserName(fund_countries.getSVC_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", fund_countries.getIV_ENTER_DATE());
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
					
					if(fund_countries.getIV_APPROVE_UID()!=null) {
						user=getUserName(fund_countries.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", fund_countries.getIV_APPROVE_DATE());
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
					
					if(fund_countries.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(fund_countries.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", fund_countries.getIV_ENTER_DATE());
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
					return new ResponseEntity<>(json,HttpStatus.OK);
				}
				else
				{
					json.put("msg", "approve request is not sent");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
				}
				catch(Exception e)
				{
				json.put("msg", e.getMessage());
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
				}		
			}
		
		@GetMapping("/countrieswith")	
		public ResponseEntity<Page<Map<String,Object>>> getAllCountryPage(@RequestParam("action")String requestString,Pageable page)
		{
			Page<Map<String,Object>> JsonCountry=null;
//			Map<String,Object> requestJson=requestData.getBody();
			//String requestString=(String)requestJson.get("action");
			if(requestString==null) {
			Page<FUND_COUNTRIES> countries=fund_CountriesRepository.findAllCountries(page);
			JsonCountry=countries.map(new Converter<FUND_COUNTRIES, Map<String,Object>>() {
				@Override
				public Map<String, Object> convert(FUND_COUNTRIES conut) {
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("countryid", conut.getSVC_ID());
					json.put("isocode", conut.getSVC_CODE());
					json.put("countryname", conut.getSVC_NAME());
					json.put("comments", conut.getSVC_COMMENTS());
					json.put("status", conut.getSVC_FLOW());
					FUND_USERS user=null;
					if(conut.getIV_ENTER_UID()!=null) {
						user=getUserName(conut.getSVC_UID());
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
			});
			}
			else if(requestString.equalsIgnoreCase("ALL")){
				Page<FUND_COUNTRIES> countries=fund_CountriesRepository.findAllCountries(page);
				JsonCountry=countries.map(new Converter<FUND_COUNTRIES, Map<String,Object>>() {
					@Override
					public Map<String, Object> convert(FUND_COUNTRIES conut) {
						Map<String,Object> json=new HashMap<String,Object>();
						json.put("countryid", conut.getSVC_ID());
						json.put("isocode", conut.getSVC_CODE());
						json.put("countryname", conut.getSVC_NAME());
						json.put("comments", conut.getSVC_COMMENTS());
						json.put("status", conut.getSVC_FLOW());
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getSVC_UID());
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
				});
			}else if(requestString.equalsIgnoreCase("APPROVED")){
				Page<FUND_COUNTRIES> countries=fund_CountriesRepository.findAllAPPROVED(page);
				JsonCountry=countries.map(new Converter<FUND_COUNTRIES, Map<String,Object>>() {
					@Override
					public Map<String, Object> convert(FUND_COUNTRIES conut) {
						Map<String,Object> json=new HashMap<String,Object>();
						json.put("countryid", conut.getSVC_ID());
						json.put("isocode", conut.getSVC_CODE());
						json.put("countryname", conut.getSVC_NAME());
						json.put("comments", conut.getSVC_COMMENTS());
						json.put("status", conut.getSVC_FLOW());
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getSVC_UID());
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
				});
			}else if(requestString.equalsIgnoreCase("UNAPPROVED")) {
				Page<FUND_COUNTRIES> countries=fund_CountriesRepository.findAllUNAPPROVED(page);
				JsonCountry=countries.map(new Converter<FUND_COUNTRIES, Map<String,Object>>() {
					@Override
					public Map<String, Object> convert(FUND_COUNTRIES conut) {
						Map<String,Object> json=new HashMap<String,Object>();
						json.put("countryid", conut.getSVC_ID());
						json.put("isocode", conut.getSVC_CODE());
						json.put("countryname", conut.getSVC_NAME());
						json.put("comments", conut.getSVC_COMMENTS());
						json.put("status", conut.getSVC_FLOW());
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getSVC_UID());
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
				});
			}
			return new ResponseEntity<Page<Map<String,Object>>>(JsonCountry,HttpStatus.OK);
		}	
	
		@PostMapping("/countrieswithnew")	
		public ResponseEntity<Page<Map<String,Object>>> getAllCountryPage(RequestEntity<Map<String,Object>> requestData,Pageable page)
		{
			Page<Map<String,Object>> JsonCountry=null;
			Map<String,Object> requestJson=requestData.getBody();
			String requestString=(String)requestJson.get("action");
			if(requestString==null) {
			Page<FUND_COUNTRIES> countries=fund_CountriesRepository.findAllCountries(page);
			JsonCountry=countries.map(new Converter<FUND_COUNTRIES, Map<String,Object>>() {
				@Override
				public Map<String, Object> convert(FUND_COUNTRIES conut) {
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("countryid", conut.getSVC_ID());
					json.put("isocode", conut.getSVC_CODE());
					json.put("countryname", conut.getSVC_NAME());
					json.put("comments", conut.getSVC_COMMENTS());
					json.put("status", conut.getSVC_FLOW());
					FUND_USERS user=null;
					if(conut.getIV_ENTER_UID()!=null) {
						user=getUserName(conut.getSVC_UID());
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
			});
			}
			else if(requestString.equalsIgnoreCase("ALL")){
				Page<FUND_COUNTRIES> countries=fund_CountriesRepository.findAllCountries(page);
				JsonCountry=countries.map(new Converter<FUND_COUNTRIES, Map<String,Object>>() {
					@Override
					public Map<String, Object> convert(FUND_COUNTRIES conut) {
						Map<String,Object> json=new HashMap<String,Object>();
						json.put("countryid", conut.getSVC_ID());
						json.put("isocode", conut.getSVC_CODE());
						json.put("countryname", conut.getSVC_NAME());
						json.put("comments", conut.getSVC_COMMENTS());
						json.put("status", conut.getSVC_FLOW());
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getSVC_UID());
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
				});
			}else if(requestString.equalsIgnoreCase("APPROVED")){
				Page<FUND_COUNTRIES> countries=fund_CountriesRepository.findAllAPPROVED(page);
				JsonCountry=countries.map(new Converter<FUND_COUNTRIES, Map<String,Object>>() {
					@Override
					public Map<String, Object> convert(FUND_COUNTRIES conut) {
						Map<String,Object> json=new HashMap<String,Object>();
						json.put("countryid", conut.getSVC_ID());
						json.put("isocode", conut.getSVC_CODE());
						json.put("countryname", conut.getSVC_NAME());
						json.put("comments", conut.getSVC_COMMENTS());
						json.put("status", conut.getSVC_FLOW());
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getSVC_UID());
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
				});
			}else if(requestString.equalsIgnoreCase("UNAPPROVED")) {
				Page<FUND_COUNTRIES> countries=fund_CountriesRepository.findAllUNAPPROVED(page);
				JsonCountry=countries.map(new Converter<FUND_COUNTRIES, Map<String,Object>>() {
					@Override
					public Map<String, Object> convert(FUND_COUNTRIES conut) {
						Map<String,Object> json=new HashMap<String,Object>();
						json.put("countryid", conut.getSVC_ID());
						json.put("isocode", conut.getSVC_CODE());
						json.put("countryname", conut.getSVC_NAME());
						json.put("comments", conut.getSVC_COMMENTS());
						json.put("status", conut.getSVC_FLOW());
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getSVC_UID());
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
				});
			}
			return new ResponseEntity<Page<Map<String,Object>>>(JsonCountry,HttpStatus.OK);
		}	
	
		
		@PostMapping("/countries")	
		public ResponseEntity<List<Map<String,Object>>> getAllCountry(RequestEntity<Map<String,Object>> requestData)
		{
			List<Map<String,Object>> JsonCountry=new ArrayList<Map<String,Object>>();
			Map<String,Object> requestJson=requestData.getBody();
			String requestString=(String)requestJson.get("action");
			if(requestString==null) {
				fund_CountriesRepository.findAllCountries().forEach(conut->{
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("countryid", conut.getSVC_ID());
					json.put("isocode", conut.getSVC_CODE());
					json.put("countryname", conut.getSVC_NAME());
					json.put("comments", conut.getSVC_COMMENTS());
					json.put("status", conut.getSVC_FLOW());
					FUND_USERS user=null;
					if(conut.getIV_ENTER_UID()!=null) {
						user=getUserName(conut.getSVC_UID());
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
						json.put("modifieddate",conut.getIV_LAST_UPDATE_DATE());
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
				fund_CountriesRepository.findAllCountries().forEach(conut->{
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("countryid", conut.getSVC_ID());
					json.put("isocode", conut.getSVC_CODE());
					json.put("countryname", conut.getSVC_NAME());
					json.put("comments", conut.getSVC_COMMENTS());
					json.put("status", conut.getSVC_FLOW());
					FUND_USERS user=null;
					if(conut.getIV_ENTER_UID()!=null) {
						user=getUserName(conut.getSVC_UID());
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
				fund_CountriesRepository.findAllAPPROVED().forEach(conut->{
					FUND_USERS user=null;
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("countryid", conut.getSVC_ID());
					json.put("isocode", conut.getSVC_CODE());
					json.put("countryname", conut.getSVC_NAME());
					json.put("comments", conut.getSVC_COMMENTS());
					json.put("status", conut.getSVC_FLOW());
					if(conut.getIV_ENTER_UID()!=null) {
						user=getUserName(conut.getSVC_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVC_UID());
						json.put("entereddate", conut.getIV_ENTER_DATE());
						}
						else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("entereddate", null);
						}
					}else {
						json.put("enteredby", null);
						json.put("enteredbyuserid", null);
						json.put("entereddate", null);
					}
					
					if(conut.getIV_APPROVE_UID()!=null) {
						user=getUserName(conut.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVC_UID());
						json.put("approveddate", conut.getIV_APPROVE_DATE());
						}
						else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approveddate", null);
						}
					}else {
						json.put("approvedby", null);
						json.put("approvedbyuserid", null);
						json.put("approveddate", null);
					}
					
					if(conut.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(conut.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVC_UID());
						json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
						}
						else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifieddate", null);
						}
					}else {
						json.put("modifiedby", null);
						json.put("modifiedbyuserid", null);
						json.put("modifieddate", null);
					}
					JsonCountry.add(json);
				});
			}else if(requestString.equalsIgnoreCase("UNAPPROVED")) {
				fund_CountriesRepository.findAllUNAPPROVED().forEach(conut->{
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("countryid", conut.getSVC_ID());
					json.put("isocode", conut.getSVC_CODE());
					json.put("countryname", conut.getSVC_NAME());
					json.put("comments", conut.getSVC_COMMENTS());
					json.put("status", conut.getSVC_FLOW());
					FUND_USERS user=null;
					if(conut.getIV_ENTER_UID()!=null) {
						user=getUserName(conut.getSVC_UID());
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
