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

import com.fynisys.menutask.repository.FUND_MENU_GROUPRepository;
import com.fynisys.menutask.repository.FUND_MENU_SUB_TASKRepository;
import com.fynisys.menutask.repository.FUND_MENU_TASKRepository;
import com.fynisys.model.FUND_MENU_GROUP;
import com.fynisys.model.FUND_USERS;
import com.fynisys.repository.FUND_USERSRepository;

@RestController
public class FUND_MENU_GROUPController {
	
	@Autowired
	FUND_USERSRepository fUND_USERSRepository; 
	
	@Autowired
	FUND_MENU_GROUPRepository fUND_MENU_GROUPRepository;

	@Autowired
	FUND_MENU_TASKRepository fUND_MENU_TASKRepository;  
	
	@Autowired
	FUND_MENU_SUB_TASKRepository fUND_MENU_SUB_TASKRepository;
	
	private final Logger logger=LoggerFactory.getLogger("FUND MENU GROUP");
	
	//@PostMapping("/savegroup")
	/*
	 * It save single menu one time
	 */
	public ResponseEntity<Map<String, Object>> saveMenuGroup(RequestEntity<Map<String,Object>> requestData){
		Map<String, Object> json=new HashMap<String,Object>();
		Map<String,Object> jdata=requestData.getBody();
		String FMG_GROUPNAME=(String)jdata.get("groupname");
		String FMG_CREATEDBY=(String)jdata.get("createdby");
		if(FMG_GROUPNAME==null||FMG_CREATEDBY==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		else {
			FUND_USERS fund_user=isValidUser(FMG_CREATEDBY);
			if(fund_user==null) {
				json.put("msg", "User is not valid to create");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			FUND_MENU_GROUP fUND_MENU_GROUP=new FUND_MENU_GROUP();
			fUND_MENU_GROUP.setFMG_GROUPNAME(FMG_GROUPNAME);
			fUND_MENU_GROUP.setFMG_CREATEDBY(fund_user.getSVC_UID());
			fUND_MENU_GROUP.setFMG_DATE(Calendar.getInstance());
			fUND_MENU_GROUP.setFMG_MODIFIEDBY(fund_user.getSVC_UID());
			fUND_MENU_GROUP.setFMG_LAST_CHANGE(Calendar.getInstance());
			try {
				fUND_MENU_GROUP =fUND_MENU_GROUPRepository.save(fUND_MENU_GROUP);
				if(fUND_MENU_GROUP!=null) {
					json.put("msg", "Menu Group Created Successfully");
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
	
	@PostMapping("/savegroup")
	public ResponseEntity<Map<String, Object>> saveMenuGroupMultiple(RequestEntity<Map<String,Object>> requestData){
		Map<String, Object> json=new HashMap<String,Object>();
		Map<String,Object> jdata=requestData.getBody();
		
		String FMG_CREATEDBY=(String)jdata.get("createdby");
		if(FMG_CREATEDBY==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		else {
			FUND_USERS fund_user=isValidUser(FMG_CREATEDBY);
			if(fund_user==null) {
				json.put("msg", "User is not valid to create");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		List<String> FMG_GROUPNAME=(List<String>)jdata.get("groups");
		FMG_GROUPNAME.forEach(groupname->{
			FUND_MENU_GROUP menu_group=fUND_MENU_GROUPRepository.getGroup(groupname);
			if(menu_group==null) {
				menu_group=new FUND_MENU_GROUP();
				menu_group.setFMG_CREATEDBY(fund_user.getSVC_UID());
				menu_group.setFMG_DATE(Calendar.getInstance());
			}
			menu_group.setFMG_GROUPNAME(groupname);
			menu_group.setFMG_MODIFIEDBY(fund_user.getSVC_UID());
			menu_group.setFMG_LAST_CHANGE(Calendar.getInstance());
			try {
				menu_group =fUND_MENU_GROUPRepository.save(menu_group);
				if(menu_group!=null) {
					json.put(groupname, "saved");
				}
				else {
					json.put(groupname, "Some internal error");
				}
			}catch (Exception e) {
				json.put(groupname, e.getMessage());
			}
		});
		return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
		}
	}
	
	
	@PostMapping("/updategroup")
	public ResponseEntity<Map<String, Object>> updateMenuGroup(RequestEntity<Map<String,Object>> requestData){
		Map<String, Object> json=new HashMap<String,Object>();
		Map<String,Object> jdata=requestData.getBody();
		Object FMG_GROUPNAMID=jdata.get("groupid");
		String FMG_GROUPNAME=(String)jdata.get("groupname");
		String FMG_CREATEDBY=(String)jdata.get("modifiedby");
		if(FMG_GROUPNAMID==null||FMG_GROUPNAME==null||FMG_CREATEDBY==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		else {
			FUND_USERS fund_user=isValidUser(FMG_CREATEDBY);
			if(fund_user==null) {
				json.put("msg", "User is not valid to create");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			FUND_MENU_GROUP fUND_MENU_GROUP=fUND_MENU_GROUPRepository.findOne(new Long(FMG_GROUPNAMID.toString()));
			fUND_MENU_GROUP.setFMG_GROUPNAME(FMG_GROUPNAME);
			fUND_MENU_GROUP.setFMG_MODIFIEDBY(fund_user.getSVC_UID());
			fUND_MENU_GROUP.setFMG_LAST_CHANGE(Calendar.getInstance());
			try {
				fUND_MENU_GROUP =fUND_MENU_GROUPRepository.save(fUND_MENU_GROUP);
				if(fUND_MENU_GROUP!=null) {
					json.put("msg", "Menu Group updated Successfully");
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
	@GetMapping("/getmenugroups")
	public ResponseEntity<List<Map<String, Object>>> getAll(){
		List<Map<String,Object>> json=new ArrayList<Map<String,Object>>();
		fUND_MENU_GROUPRepository.findAll().forEach(menu->{
			Map<String, Object> mjson=new HashMap<String,Object>();
			mjson.put("menuid", menu.getFMG_GROUPID());
			mjson.put("menuname", menu.getFMG_GROUPNAME());
			mjson.put("enteredby", menu.getFMG_CREATEDBY());
			mjson.put("date", menu.getFMG_DATE());
			json.add(mjson);
		});
		return new ResponseEntity<List<Map<String,Object>>>(json,HttpStatus.OK);
	}
	/*
	 * This will return all groups,meus,submenus
	 * 
	 */
	
	@GetMapping("/getallgroups")
	public ResponseEntity<List<Map<String, Object>>> getAllGroups(){
		/*
		 * fUND_USERSRepository; 
		 * fUND_MENU_GROUPRepository;
		 * fUND_MENU_TASKRepository;  
		 * fUND_MENU_SUB_TASKRepository;
		 */
		List<Map<String,Object>> json=new ArrayList<Map<String,Object>>();
		fUND_MENU_GROUPRepository.findAll().forEach(group->{
			Map<String, Object> jgroup=new HashMap<String,Object>();
			jgroup.put("groupid", group.getFMG_GROUPID());
			jgroup.put("groupname", group.getFMG_GROUPNAME());
			//jgroup.put("enteredby", group.getFMG_CREATEDBY());
			//jgroup.put("date", group.getFMG_DATE());
			List<Map<String,Object>> menus=new ArrayList<Map<String,Object>>();
			group.getFund_menu_task().forEach(menu->{
				Map<String, Object> jmenu=new HashMap<String,Object>();
				jmenu.put("menuid",menu.getFMT_MENUTASKID());
				jmenu.put("menuname",menu.getFMT_MENUNAME());
				List<Map<String,Object>> submenus=new ArrayList<Map<String,Object>>();
				menu.getFund_menu_sub_task().forEach(submenu->{
					Map<String, Object> jsubmenu=new HashMap<String,Object>();
					jsubmenu.put("submenuid",submenu.getFMST_MENUSUBTASKID());
					jsubmenu.put("submenuname",submenu.getFMST_MENUSUBNAME());
					submenus.add(jsubmenu);
				});
				
				jmenu.put("submenus", submenus);
				menus.add(jmenu);
			});
			jgroup.put("menus",menus);
			json.add(jgroup);
		});
		return new ResponseEntity<List<Map<String,Object>>>(json,HttpStatus.OK);
	}
	
	@GetMapping("/getmenuwithtask")
	public ResponseEntity<List<Map<String, Object>>> getAllTask(){
		List<Map<String,Object>> json=new ArrayList<Map<String,Object>>();
		fUND_MENU_GROUPRepository.findAll().forEach(menu->{
			Map<String, Object> mjson=new HashMap<String,Object>();
			mjson.put("menuid", menu.getFMG_GROUPID());
			mjson.put("menuname", menu.getFMG_GROUPNAME());
			List<Map<String,Object>> tasks=new ArrayList<Map<String,Object>>();
			menu.getFund_menu_task().forEach(task->{
				Map<String,Object>jtask=new HashMap<String,Object>();
				jtask.put("taskid", task.getFMT_MENUTASKID());
				jtask.put("taskname", task.getFMT_MENUNAME());
				List<Map<String,Object>> subtasks=new ArrayList<Map<String,Object>>();
				task.getFund_menu_sub_task().forEach(sub->{
					Map<String,Object>jsubtask=new HashMap<String,Object>();
					jsubtask.put("subtaskid", sub.getFMST_MENUSUBTASKID());
					jsubtask.put("subtaskname", sub.getFMST_MENUSUBNAME());
					subtasks.add(jsubtask);
				});		
				jtask.put("subtask", subtasks);
				tasks.add(jtask);
			});
			mjson.put("enteredby", menu.getFMG_CREATEDBY());
			mjson.put("date", menu.getFMG_DATE());
			mjson.put("task", tasks);
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
