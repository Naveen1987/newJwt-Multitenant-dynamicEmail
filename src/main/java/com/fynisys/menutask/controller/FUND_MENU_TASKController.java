package com.fynisys.menutask.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fynisys.menutask.repository.FUND_MENU_GROUPRepository;
import com.fynisys.menutask.repository.FUND_MENU_TASKRepository;
import com.fynisys.model.FUND_MENU_GROUP;
import com.fynisys.model.FUND_MENU_TASK;
import com.fynisys.model.FUND_USERS;
import com.fynisys.repository.FUND_USERSRepository;

@RestController
public class FUND_MENU_TASKController {
	@Autowired
	FUND_USERSRepository fUND_USERSRepository; 
	
	@Autowired
	FUND_MENU_GROUPRepository fUND_MENU_GROUPRepository; 
	
	@Autowired
	FUND_MENU_TASKRepository fUND_MENU_TASKRepository;  
	
	//@PostMapping("/savemenutask")
	/*
	 * This only for single save
	 */
	public ResponseEntity<Map<String, Object>> saveMenuTask(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> data=requestData.getBody();
		String FMT_MENUNAME=(String)data.get("menuname");
		String FMT_CREATEDBY=(String)data.get("createdby");
		Object FMG_GROUPID=data.get("groupid");
		if(FMT_MENUNAME==null||FMT_CREATEDBY==null||FMG_GROUPID==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		else {
			FUND_USERS fund_user=isValidUser(FMT_CREATEDBY);
			if(fund_user==null) {
				json.put("msg", "User is not valid to create");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
			}
			FUND_MENU_GROUP fmg=fUND_MENU_GROUPRepository.findOne(new Long(FMG_GROUPID.toString()));
			if(fmg==null)
			{
				json.put("msg", "Group id is not exists");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
			}
			FUND_MENU_TASK fUND_MENU_TASK=new FUND_MENU_TASK();
			fUND_MENU_TASK.setFMT_MENUNAME(FMT_MENUNAME);
			fUND_MENU_TASK.setFMT_DATE(Calendar.getInstance());
			fUND_MENU_TASK.setFMT_LAST_CHANGE(Calendar.getInstance());
			fUND_MENU_TASK.setFMT_CREATEDBY(fund_user.getSVC_UID());
			fUND_MENU_TASK.setFMT_MODIFIEDBY(fund_user.getSVC_UID());
			fUND_MENU_TASK.setFund_menu_group(fmg);
			try {
				fUND_MENU_TASK=fUND_MENU_TASKRepository.save(fUND_MENU_TASK);
				if(fUND_MENU_TASK!=null) {
					json.put("msg", "Menu  saved Successfully");
					return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg", "Some internal error");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
			}
		}
		
	}
	
	
	@PostMapping("/savemenutask")
	public ResponseEntity<Map<String, Object>> saveMenuTaskMany(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> data=requestData.getBody();
		String FMT_CREATEDBY=(String)data.get("createdby");
		Object FMG_GROUPID=data.get("groupid");
		if(FMT_CREATEDBY==null||FMG_GROUPID==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		else {
			FUND_USERS fund_user=isValidUser(FMT_CREATEDBY);
			if(fund_user==null) {
				json.put("msg", "User is not valid to create");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
			}
			FUND_MENU_GROUP fmg=fUND_MENU_GROUPRepository.findOne(new Long(FMG_GROUPID.toString()));
			if(fmg==null)
			{
				json.put("msg", "Group id is not exists");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
			}
			@SuppressWarnings("unchecked")
			List<String> FMT_MENUNAME=(List<String>)data.get("menus");
			FMT_MENUNAME.forEach(menu->{
				FUND_MENU_TASK fUND_MENU_TASK=fUND_MENU_TASKRepository.getMenu(menu,fmg.getFMG_GROUPID());
				if(fUND_MENU_TASK==null) {
					fUND_MENU_TASK=new FUND_MENU_TASK();
					fUND_MENU_TASK.setFMT_DATE(Calendar.getInstance());
					fUND_MENU_TASK.setFMT_CREATEDBY(fund_user.getSVC_UID());
					fUND_MENU_TASK.setFund_menu_group(fmg);
				}
				fUND_MENU_TASK.setFMT_MENUNAME(menu);
				fUND_MENU_TASK.setFMT_LAST_CHANGE(Calendar.getInstance());
				fUND_MENU_TASK.setFMT_MODIFIEDBY(fund_user.getSVC_UID());
				try {
					fUND_MENU_TASK=fUND_MENU_TASKRepository.save(fUND_MENU_TASK);
					if(fUND_MENU_TASK!=null) {
						json.put(menu, "Menu  saved Successfully");
						
					}
					else {
						json.put(menu, "Some internal error");
						
					}
					
				}catch (Exception e) {
					json.put(menu, e.getMessage());
					
				}
			});
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
		}
		
	}
	
	@PostMapping("/updatemenutask")
	public ResponseEntity<Map<String, Object>> updateMenuTask(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> data=requestData.getBody();
		Object FMT_MENUID=data.get("menuid");
		String FMT_MENUNAME=(String)data.get("menuname");
		String FMT_CREATEDBY=(String)data.get("modifiedby");
		Object FMG_GROUPID=data.get("groupid");
		if(FMT_MENUID==null||FMT_MENUNAME==null||FMT_CREATEDBY==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		else {
			FUND_USERS fund_user=isValidUser(FMT_CREATEDBY);
			if(fund_user==null) {
				json.put("msg", "User is not valid to create");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
			}
			
			
			FUND_MENU_TASK fUND_MENU_TASK=fUND_MENU_TASKRepository.findOne(new Long(FMT_MENUID.toString()));
			fUND_MENU_TASK.setFMT_MENUNAME(FMT_MENUNAME);
			fUND_MENU_TASK.setFMT_DATE(Calendar.getInstance());
			fUND_MENU_TASK.setFMT_LAST_CHANGE(Calendar.getInstance());
			fUND_MENU_TASK.setFMT_CREATEDBY(fund_user.getSVC_UID());
			fUND_MENU_TASK.setFMT_MODIFIEDBY(fund_user.getSVC_UID());
			if(FMG_GROUPID!=null) {
				FUND_MENU_GROUP fmg=fUND_MENU_GROUPRepository.findOne(new Long(FMG_GROUPID.toString()));
				if(fmg==null)
				{
					json.put("msg", "Group id is not exists");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
				}else {
					fUND_MENU_TASK.setFund_menu_group(fmg);	
				}
				
			}
			try {
				fUND_MENU_TASK=fUND_MENU_TASKRepository.save(fUND_MENU_TASK);
				if(fUND_MENU_TASK!=null) {
					json.put("msg", "Menu  updated Successfully");
					return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg", "Some internal error");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
			}
		}
		
	}
	
	@GetMapping("/getallmenutask")
	public ResponseEntity<List<Map<String, Object>>> getAll(){
		List<Map<String,Object>> json=new ArrayList<Map<String,Object>>();
		fUND_MENU_TASKRepository.findAll().forEach(menu->{
			Map<String, Object> mjson=new HashMap<String,Object>();
			mjson.put("menuid", menu.getFMT_MENUTASKID());
			mjson.put("menuname", menu.getFMT_MENUNAME());
			mjson.put("enteredby", menu.getFMT_CREATEDBY());
			mjson.put("date", menu.getFMT_DATE());
			mjson.put("groupid", menu.getFund_menu_group().getFMG_GROUPID());
			mjson.put("groupid", menu.getFund_menu_group().getFMG_GROUPNAME());
			json.add(mjson);
		});
		return new ResponseEntity<List<Map<String,Object>>>(json,HttpStatus.OK);
	}
	
	/**
	 * 
	 *Testing for created by and modified by
	 */
	public FUND_USERS isValidUser(String userid) {
		FUND_USERS fund_user=fUND_USERSRepository.findByUSER_NAME(userid);
		if(fund_user!=null) {
			return fund_user;
		}
		return null;
	}
}
