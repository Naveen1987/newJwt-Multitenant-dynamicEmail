package com.fynisys.menutask.controller;

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
import org.springframework.web.bind.annotation.RestController;
import com.fynisys.menutask.repository.FUND_MENU_SUB_TASKRepository;
import com.fynisys.menutask.repository.FUND_MENU_TASKRepository;
import com.fynisys.model.FUND_MENU_SUB_TASK;
import com.fynisys.model.FUND_MENU_TASK;
import com.fynisys.model.FUND_USERS;
import com.fynisys.repository.FUND_USERSRepository;

@RestController
public class FUND_MENU_SUB_TASKController{
	@Autowired
	FUND_USERSRepository fUND_USERSRepository; 
	
	@Autowired
	FUND_MENU_TASKRepository fUND_MENU_TASKRepository;  
	
	@Autowired
	FUND_MENU_SUB_TASKRepository fUND_MENU_SUB_TASKRepository;
	
	private final Logger logger=LoggerFactory.getLogger("FUND MENU SUB TASK");
	
	@PostMapping("/savemenusubtask")
	public ResponseEntity<Map<String, Object>> saveMenuTask(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> data=requestData.getBody();
		Object FMT_MENUNAMEID=data.get("menuid");
		String FMT_CREATEDBY=(String)data.get("createdby");
		if(FMT_MENUNAMEID==null||FMT_CREATEDBY==null||FMT_CREATEDBY==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		else {
			FUND_USERS fund_user=isValidUser(FMT_CREATEDBY);
			if(fund_user==null) {
				json.put("msg", "User is not valid to create");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
			}
			final FUND_MENU_TASK fmt=fUND_MENU_TASKRepository.findOne(new Long(FMT_MENUNAMEID.toString()));
			if(fmt==null)
			{
				json.put("msg", "Menu id is not exists");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
			}
			/*
			 * Submenu from response
			 */
			List<String> submenu=(List<String>) data.get("submenus");
			if(submenu!=null) {
				if(submenu.isEmpty()==false) {
					submenu.forEach(sub->{
						FUND_MENU_SUB_TASK fmst=fUND_MENU_SUB_TASKRepository.findMenuSubTaskByMenuId(sub, fmt.getFMT_MENUTASKID());
						if(fmst==null) {
						fmst=new FUND_MENU_SUB_TASK();
						fmst.setFMST_CREATEDBY(fund_user.getSVC_UID());
						fmst.setFMST_DATE(Calendar.getInstance());
						/*
						 * Same refrence
						 */
						fmst.setFun_menu_task(fmt);
						}
						fmst.setFMST_MODIFIEDBY(fund_user.getSVC_UID());
						fmst.setFMST_LAST_CHANGE(Calendar.getInstance());
						fmst.setFMST_MENUSUBNAME(sub);
						
						try {
							fmst=fUND_MENU_SUB_TASKRepository.save(fmst);
							if(fmst!=null) {
								logger.info(sub+"Saved");
								json.put(sub, "saved");
							}	
						}catch (Exception e) {
						logger.error(e.getMessage());
						json.put(sub, e.getMessage());
						}
						
					});
				}
			}
		}
		
		return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
	}
	
	@GetMapping("/getallmenusubtask")
	public ResponseEntity<List<Map<String, Object>>> getAllsubTask(){
		List<Map<String,Object>> json=new ArrayList<Map<String,Object>>();
		fUND_MENU_SUB_TASKRepository.findAll().forEach(menu->{
			Map<String, Object> mjson=new HashMap<String,Object>();
			mjson.put("submenuid", menu.getFMST_MENUSUBTASKID());
			mjson.put("submenuname", menu.getFMST_MENUSUBNAME());
			mjson.put("menuid", menu.getFun_menu_task().getFMT_MENUTASKID());
			mjson.put("menuname", menu.getFun_menu_task().getFMT_MENUNAME());
			mjson.put("enteredby", menu.getFMST_CREATEDBY());
			mjson.put("date", menu.getFMST_DATE());
			mjson.put("groupid", menu.getFun_menu_task().getFund_menu_group().getFMG_GROUPID());
			mjson.put("groupname", menu.getFun_menu_task().getFund_menu_group().getFMG_GROUPNAME());
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
