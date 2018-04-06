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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fynisys.menutask.repository.FUND_MENU_GROUPRepository;
import com.fynisys.menutask.repository.FUND_MENU_SUB_TASKRepository;
import com.fynisys.menutask.repository.FUND_MENU_TASKRepository;
import com.fynisys.menutask.repository.FUND_ROLESRepository;
import com.fynisys.menutask.repository.FUND_ROLE_SUB_TASKRepository;
import com.fynisys.menutask.repository.FUND_ROLE_TASKRepository;
import com.fynisys.model.FUND_MENU_GROUP;
import com.fynisys.model.FUND_MENU_SUB_TASK;
import com.fynisys.model.FUND_MENU_TASK;
import com.fynisys.model.FUND_ROLES;
import com.fynisys.model.FUND_ROLE_SUB_TASK;
import com.fynisys.model.FUND_ROLE_TASK;
import com.fynisys.model.FUND_USERS;
import com.fynisys.repository.FUND_USERSRepository;

@RestController
public class FUND_ROLE_TASKController {

	@Autowired
	FUND_USERSRepository fUND_USERSRepository; 
	
	@Autowired
	FUND_MENU_GROUPRepository fUND_MENU_GROUPRepository; 
	
	@Autowired
	FUND_MENU_TASKRepository fUND_MENU_TASKRepository;  
	
	@Autowired
	FUND_MENU_SUB_TASKRepository fUND_MENU_SUB_TASKRepository;  
	
	@Autowired
	FUND_ROLE_TASKRepository fUND_ROLE_TASKRepository;
	
	@Autowired
	FUND_ROLE_SUB_TASKRepository fUND_ROLE_SUB_TASKRepository;
	
	
	@Autowired
	FUND_ROLESRepository fUND_ROLESRepository;
	
	private final Logger log=LoggerFactory.getLogger("FUND ROLE TASK");
	
	
	//@PostMapping("/savetaskrole")
	public ResponseEntity<Map<String, Object>> saveTaskRole(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object>data=requestData.getBody();
		Object roleid=data.get("roleid");
		String rolename=(String)data.get("rolename");
		Object createdby=data.get("createdby");
		/*
		 * Read Menus
		 */
		List<Map<String,Object>> menus=(List<Map<String, Object>>) data.get("menus");
		if(roleid==null||createdby==null||menus==null||menus.isEmpty()) {
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FUND_USERS fund_user=fUND_USERSRepository.findByUSER_NAME(createdby.toString());
		if(fund_user==null) {
			json.put("msg", "Not user exists");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FUND_ROLES role=fUND_ROLESRepository.findOne(new Long(roleid.toString()));
		if(role==null) {
			json.put("msg", "Not role exists");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
		}
		else {
			if(rolename!=null) {
				role.setFRL_ROLENAME(rolename);
				role.setFRL_LAST_CHANGE(Calendar.getInstance());
				role.setFRL_MODIFIEDBY(fund_user.getSVC_UID());
				try {
					role=fUND_ROLESRepository.save(role);
					if(role==null) {
						json.put("msg", "Role is not Updated");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
					}
					else {
						log.error(rolename, " is Updated");
						json.put(rolename, "Role is Updated");
					
					}
				}catch (Exception e) {
					log.error(e.getMessage());
					json.put("msg","Role Name must Unique. No futher action will execute.");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			
			/*
			 * Menu task
			 */
			/*
			 * This is final because internal block require a final role
			 */
		    final FUND_ROLES frole=role;
			menus.forEach(menu->{
				Object menuid=menu.get("menuid");
				if(menuid!=null) {
					FUND_MENU_TASK menu_task=fUND_MENU_TASKRepository.findOne(new Long(menuid.toString()));
					if(menu_task!=null) {
						FUND_ROLE_TASK	fUND_ROLE_TASK=fUND_ROLE_TASKRepository.getMenuOnRole(frole.getFRL_ROLEID(), menu_task.getFMT_MENUTASKID());
						fUND_ROLE_TASK=saveRoleTask(fUND_ROLE_TASK,menu_task,frole,fund_user,menu);
						if(fUND_ROLE_TASK!=null) {
							json.put(menu_task.getFMT_MENUNAME(), "saved");
							/*
							 * Read Submenus
							 */
							List<Map<String,Object>> submenus=(List<Map<String, Object>>) menu.get("submenus");
						
							/*
							 * Submenu Task
							 */
							submenus.forEach(submenu->{
								Object submenuid=submenu.get("submenuid");
								if(submenuid!=null) {
									FUND_MENU_SUB_TASK menu_sub_task=fUND_MENU_SUB_TASKRepository.findOne(new Long(submenuid.toString()));
									if(menu_sub_task!=null) {
										
										/*
										 * START
										 */
										FUND_ROLE_SUB_TASK fUND_ROLE_SUB_TASK=fUND_ROLE_SUB_TASKRepository.getSubMenuOnRole(frole.getFRL_ROLEID(), menu_sub_task.getFMST_MENUSUBTASKID());
										fUND_ROLE_SUB_TASK=saveRoleSubTask(fUND_ROLE_SUB_TASK,menu_sub_task,frole,fund_user,submenu,new Long(menuid.toString()));
										if(fUND_ROLE_SUB_TASK!=null) {
											json.put(menu_sub_task.getFMST_MENUSUBNAME(), "saved");
											log.info("Sub Task Saved successfully");
										}
										/*
										 * END
										 */
									}
								}
							});
						}
					}else {
						log.info(menuid+" Not exists");
					}
												
				}
			});
			/*
			 * End of Task
			 */
		}
		
		
		return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
	}
	
	
	@PostMapping("/savetaskrole")
	public ResponseEntity<Map<String, Object>> updateTaskRole(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object>data=requestData.getBody();
		Object roleid=data.get("roleid");
		Object createdby=data.get("createdby");
		String rolename=(String)data.get("rolename");
		@SuppressWarnings("unchecked")
		List<String> groups=(List<String>)data.get("groups");
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> menus=(List<Map<String, Object>>) data.get("menus");
		if(roleid==null||createdby==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FUND_USERS fund_user=fUND_USERSRepository.findByUSER_NAME(createdby.toString());
		if(fund_user==null) {
			json.put("msg", "Not user exists");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FUND_ROLES role=fUND_ROLESRepository.findOne(new Long(roleid.toString()));
		if(role==null) {
			json.put("msg", "Not role exists");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
		}
		
		else {
			if(rolename!=null) {
				role.setFRL_ROLENAME(rolename);
				role.setFRL_LAST_CHANGE(Calendar.getInstance());
				role.setFRL_MODIFIEDBY(fund_user.getSVC_UID());
				try {
					role=fUND_ROLESRepository.save(role);
					if(role==null) {
						json.put("msg", "Role is not Updated");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
					}
					else {
						log.info(rolename +" is Updated");
						json.put(rolename, "Role is Updated");
					
					}
				}catch (Exception e) {
					log.error(e.getMessage());
					json.put("msg","Role Name must Unique. No futher action will execute.");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			
			/*
			 * Listing of All MENU ON ROLE
			 */
			List<FUND_ROLE_TASK> role_task=fUND_ROLE_TASKRepository.getMenuOnRole(new Long(roleid.toString()));
			/*
			 * Listing of All MENU ON ROLE
			 */
			List<FUND_ROLE_SUB_TASK> role_sub_task=fUND_ROLE_SUB_TASKRepository.getSubMenuOnRole(new Long(roleid.toString()));
			/*
			 * Table Refreshed ROLE SUB TASK
			 */
			role_sub_task.forEach(submenurole->{
				fUND_ROLE_SUB_TASKRepository.delete(submenurole);
			});
			/*
			 * Table Refreshed ROLE TASK
			 */
			role_task.forEach(menurole->{
				
				fUND_ROLE_TASKRepository.delete(menurole);
			});
			
			/*
			 * Now Updating role
			 * */
			role.setFRL_ROLETYPE("No Permission");
			try {
				role=fUND_ROLESRepository.save(role);
				if(role==null) {
					json.put("msg", "Role is not Updated with No permission");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
				}
				else {
					log.info(rolename +" is Updated");
					json.put(rolename, "Role is Updated");
				
				}
			}catch (Exception e) {
				log.error(e.getMessage());
				json.put("msg","Role Name must Unique. No futher action will execute.");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			/*
		   	 * Role Updated
		   	 */
			/*
			 * Inserting again
			 */
			/*
			 * This is final because internal block require a final role
			 */
			
		    final FUND_ROLES frole=role;
		    /*
		     * Adding Groups
		     */
		    if(groups!=null) {
		    	if(groups.size()>0)
			    {
			    	groups.forEach(group->{
			    	long groupid=new Long(group).longValue();
			    	FUND_MENU_GROUP fmg=fUND_MENU_GROUPRepository.findOne(groupid);
			    	if(fmg!=null) {
			    		fmg.getFund_menu_task().forEach(menu_task->{
			    			FUND_ROLE_TASK	fUND_ROLE_TASK=fUND_ROLE_TASKRepository.getMenuOnRole(frole.getFRL_ROLEID(), menu_task.getFMT_MENUTASKID());
			    			fUND_ROLE_TASK=saveRoleTask(fUND_ROLE_TASK,menu_task,frole,fund_user);
			    			if(fUND_ROLE_TASK!=null) {
								json.put("grp-"+menu_task.getFMT_MENUNAME(), " menu is assign To "+frole.getFRL_ROLENAME());
								/*
								 * Submenu Task
								 */
								menu_task.getFund_menu_sub_task().forEach(submenu->{
										FUND_MENU_SUB_TASK menu_sub_task=fUND_MENU_SUB_TASKRepository.findOne(submenu.getFMST_MENUSUBTASKID());
										if(menu_sub_task!=null) {
											/*
											 * START
											 */
											FUND_ROLE_SUB_TASK fUND_ROLE_SUB_TASK=fUND_ROLE_SUB_TASKRepository.getSubMenuOnRole(frole.getFRL_ROLEID(), menu_sub_task.getFMST_MENUSUBTASKID());
											fUND_ROLE_SUB_TASK=saveRoleSubTask(fUND_ROLE_SUB_TASK,menu_sub_task,frole,fund_user,menu_task.getFMT_MENUTASKID());
											if(fUND_ROLE_SUB_TASK!=null) {
												json.put("grp-"+menu_sub_task.getFMST_MENUSUBNAME(), " submenu is assign To "+frole.getFRL_ROLENAME());
												log.info("Sub Task Saved successfully");
											}
											/*
											 * END
											 */
										}
									
								});
								
							}
			    		});
			    	 }
			    	});
			    	/*
				   	 * Role Updated
				   	 */
			    	role.setFRL_ROLETYPE("Custom Permission");
					try {
						role=fUND_ROLESRepository.save(role);
						if(role==null) {
							json.put("msg", "Role is not Updated with Custom permission");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
						}
						else {
							log.info(rolename +" is Updated");
							json.put(rolename, "Role is Updated");
						
						}
					}catch (Exception e) {
						log.error(e.getMessage());
						json.put("msg","Role Name must Unique. No futher action will execute.");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
					/*
				   	 * Role Updated
				   	 */
			    }
		    }
		    /*
		     * Adding Menus
		     */
		   
			if(menus!=null) {
				if(menus.isEmpty()==false) {
					menus.forEach(menu->{
						Object menuid=menu.get("menuid");
						if(menuid!=null) {
							FUND_MENU_TASK menu_task=fUND_MENU_TASKRepository.findOne(new Long(menuid.toString()));
							if(menu_task!=null) {
								FUND_ROLE_TASK	fUND_ROLE_TASK=fUND_ROLE_TASKRepository.getMenuOnRole(frole.getFRL_ROLEID(), menu_task.getFMT_MENUTASKID());
								fUND_ROLE_TASK=saveRoleTask(fUND_ROLE_TASK,menu_task,frole,fund_user,menu);
								if(fUND_ROLE_TASK!=null) {
									json.put(menu_task.getFMT_MENUNAME(), " menu is assign To "+frole.getFRL_ROLENAME());
									/*
									 * Read Submenus
									 */
									List<Map<String,Object>> submenus=(List<Map<String, Object>>) menu.get("submenus");
									/*
									 * Submenu Task
									 */
									if(submenus!=null) {
									submenus.forEach(submenu->{
										Object submenuid=submenu.get("submenuid");
										if(submenuid!=null) {
											FUND_MENU_SUB_TASK menu_sub_task=fUND_MENU_SUB_TASKRepository.findOne(new Long(submenuid.toString()));
											if(menu_sub_task!=null) {
												/*
												 * START
												 */
												FUND_ROLE_SUB_TASK fUND_ROLE_SUB_TASK=fUND_ROLE_SUB_TASKRepository.getSubMenuOnRole(frole.getFRL_ROLEID(), menu_sub_task.getFMST_MENUSUBTASKID());
												fUND_ROLE_SUB_TASK=saveRoleSubTask(fUND_ROLE_SUB_TASK,menu_sub_task,frole,fund_user,submenu,new Long(menuid.toString()));
												if(fUND_ROLE_SUB_TASK!=null) {
													json.put(menu_sub_task.getFMST_MENUSUBNAME(), " submenu is assign To "+frole.getFRL_ROLENAME());
													log.info("Sub Task Saved successfully");
												}
												/*
												 * END
												 */
											}
										}
									});
								 }
									
								}
							}else {
								log.info(menuid+" Not exists");
							}
														
						}
					});
					/*
					 * End of Task
					 */
					/*
				   	 * Role Updated
				   	 */
					role.setFRL_ROLETYPE("Custom Permission");
					try {
						role=fUND_ROLESRepository.save(role);
						if(role==null) {
							json.put("msg", "Role is not Updated with Custom Permission");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
						}
						else {
							log.info(rolename +" is Updated");
							json.put(rolename, "Role is Updated");
						
						}
					}catch (Exception e) {
						log.error(e.getMessage());
						json.put("msg","Role Name must Unique. No futher action will execute.");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
			   	/*
			   	 * Role Updated
			   	 */
				}
				
			}
		}
		
		return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
	}
	
	
	
	
	private FUND_ROLE_SUB_TASK saveRoleSubTask(FUND_ROLE_SUB_TASK fUND_rOLE_sUB_tASK,FUND_MENU_SUB_TASK menu_sub_task,FUND_ROLES frole,FUND_USERS fund_user,Map<String,Object> submenu,long menuid) {
		FUND_ROLE_SUB_TASK fUND_ROLE_SUB_TASK=fUND_rOLE_sUB_tASK;
		Object ACCESS=submenu.get("ACCESS");
		Object INSERT=submenu.get("INSERT");
		Object UPDATE=submenu.get("UPDATE");
		Object DELETE=submenu.get("DELETE");
		Object APPROVE=submenu.get("APPROVE");
		if(fUND_ROLE_SUB_TASK==null) {
			fUND_ROLE_SUB_TASK=new  FUND_ROLE_SUB_TASK();
			fUND_ROLE_SUB_TASK.setFund_roles(frole);
			fUND_ROLE_SUB_TASK.setFRST_CREATEDBY(fund_user.getSVC_UID());
			fUND_ROLE_SUB_TASK.setFRST_DATE(Calendar.getInstance());
			fUND_ROLE_SUB_TASK.setFund_menu_sub_task(menu_sub_task);
		}
		fUND_ROLE_SUB_TASK.setFMT_MENUTASKID(menuid);
		fUND_ROLE_SUB_TASK.setFRST_MODIFIEDBY(fund_user.getSVC_UID());
		fUND_ROLE_SUB_TASK.setFRST_LAST_CHANGE(Calendar.getInstance());
		if(ACCESS!=null) {
			if(ACCESS.toString().equalsIgnoreCase("true")) {
				fUND_ROLE_SUB_TASK.setFRST_ACCESS(true);
			}
		}
		if(UPDATE!=null) {
			if(UPDATE.toString().equalsIgnoreCase("true")) {
				fUND_ROLE_SUB_TASK.setFRST_UPDATE(true);
			}
		}
		if(INSERT!=null) {
			if(INSERT.toString().equalsIgnoreCase("true")) {
				fUND_ROLE_SUB_TASK.setFRST_INSERT(true);
			}
		}
		if(DELETE!=null) {
			if(DELETE.toString().equalsIgnoreCase("true")) {
				fUND_ROLE_SUB_TASK.setFRST_DELETE(true);
			}
		}
		if(APPROVE!=null) {
			if(APPROVE.toString().equalsIgnoreCase("true")) {
				fUND_ROLE_SUB_TASK.setFRST_APPROVE(true);
			}
		}
	try {
		////edited....by vikas
//		frole.setFRL_ROLETYPE("Custom Type");
//		frole=fUND_ROLESRepository.save(frole);
//		
		
		fUND_ROLE_SUB_TASK=fUND_ROLE_SUB_TASKRepository.save(fUND_ROLE_SUB_TASK);
		if(fUND_ROLE_SUB_TASK!=null) {
			log.info("Role Task saved:"+fUND_ROLE_SUB_TASK.getFRST_FRTASKID());
		}	
		else {
			log.info("Role Task not saved");
		}
	}
	catch (Exception e) {
		log.info("Role Task not saved"+e.getMessage());
	}
	return fUND_ROLE_SUB_TASK;
	}
	
	private FUND_ROLE_SUB_TASK saveRoleSubTask(FUND_ROLE_SUB_TASK fUND_rOLE_sUB_tASK,FUND_MENU_SUB_TASK menu_sub_task,FUND_ROLES frole,FUND_USERS fund_user,long menuid) {
		FUND_ROLE_SUB_TASK fUND_ROLE_SUB_TASK=fUND_rOLE_sUB_tASK;
		if(fUND_ROLE_SUB_TASK==null) {
			fUND_ROLE_SUB_TASK=new  FUND_ROLE_SUB_TASK();
			fUND_ROLE_SUB_TASK.setFund_roles(frole);
			fUND_ROLE_SUB_TASK.setFRST_CREATEDBY(fund_user.getSVC_UID());
			fUND_ROLE_SUB_TASK.setFRST_DATE(Calendar.getInstance());
			fUND_ROLE_SUB_TASK.setFund_menu_sub_task(menu_sub_task);
		}
		fUND_ROLE_SUB_TASK.setFMT_MENUTASKID(menuid);
		fUND_ROLE_SUB_TASK.setFRST_MODIFIEDBY(fund_user.getSVC_UID());
		fUND_ROLE_SUB_TASK.setFRST_LAST_CHANGE(Calendar.getInstance());
		fUND_ROLE_SUB_TASK.setFRST_ACCESS(true);
		fUND_ROLE_SUB_TASK.setFRST_UPDATE(true);
		fUND_ROLE_SUB_TASK.setFRST_INSERT(true);
		fUND_ROLE_SUB_TASK.setFRST_DELETE(true);
		fUND_ROLE_SUB_TASK.setFRST_APPROVE(true);
		try {
			//edit by vikas....................................
//			frole.setFRL_ROLETYPE("Custom Type");
//			frole=fUND_ROLESRepository.save(frole);
//			
		fUND_ROLE_SUB_TASK=fUND_ROLE_SUB_TASKRepository.save(fUND_ROLE_SUB_TASK);
		if(fUND_ROLE_SUB_TASK!=null) {
			log.info("Role Task saved:"+fUND_ROLE_SUB_TASK.getFRST_FRTASKID());
		}	
		else {
			log.info("Role Task not saved");
		}
	}
	catch (Exception e) {
		log.info("Role Task not saved"+e.getMessage());
	}
	return fUND_ROLE_SUB_TASK;
	}
	
	
	/*
	 * Saving menu
	 */
	
	private FUND_ROLE_TASK saveRoleTask(FUND_ROLE_TASK	fUND_rOLE_tASK,FUND_MENU_TASK menu_task,FUND_ROLES frole,FUND_USERS fund_user,Map<String,Object> menu) {
		FUND_ROLE_TASK	fUND_ROLE_TASK=fUND_rOLE_tASK;
		Object ACCESS=menu.get("ACCESS");
		Object INSERT=menu.get("INSERT");
		Object UPDATE=menu.get("UPDATE");
		Object DELETE=menu.get("DELETE");
		Object APPROVE=menu.get("APPROVE");
		if(fUND_ROLE_TASK==null) {
			fUND_ROLE_TASK=new  FUND_ROLE_TASK();
			fUND_ROLE_TASK.setFund_roles(frole);
			fUND_ROLE_TASK.setFRT_CREATEDBY(fund_user.getSVC_UID());
			fUND_ROLE_TASK.setFRT_DATE(Calendar.getInstance());
			fUND_ROLE_TASK.setFund_menu_task(menu_task);
		}
		fUND_ROLE_TASK.setFRT_MODIFIEDBY(fund_user.getSVC_UID());
		fUND_ROLE_TASK.setFRT_LAST_CHANGE(Calendar.getInstance());
		if(ACCESS!=null) {
			if(ACCESS.toString().equalsIgnoreCase("true")) {
				fUND_ROLE_TASK.setFRT_ACCESS(true);
			}
		}
		if(UPDATE!=null) {
			if(UPDATE.toString().equalsIgnoreCase("true")) {
				fUND_ROLE_TASK.setFRT_UPDATE(true);
			}
		}
		if(INSERT!=null) {
			if(INSERT.toString().equalsIgnoreCase("true")) {
				fUND_ROLE_TASK.setFRT_INSERT(true);
			}
		}
		if(DELETE!=null) {
			if(DELETE.toString().equalsIgnoreCase("true")) {
				fUND_ROLE_TASK.setFRT_DELETE(true);
			}
		}
		if(APPROVE!=null) {
			if(APPROVE.toString().equalsIgnoreCase("true")) {
				fUND_ROLE_TASK.setFRT_APPROVE(true);
			}
		}
	try {
		fUND_ROLE_TASK=fUND_ROLE_TASKRepository.save(fUND_ROLE_TASK);
		if(fUND_ROLE_TASK!=null) {
			log.info("Role Task saved:"+fUND_ROLE_TASK.getFRT_FRTASKID());
		}	
		else {
			log.info("Role Task not saved");
		}
	}
	catch (Exception e) {
		log.info("Role Task not saved"+e.getMessage());
	}
	return fUND_ROLE_TASK;
	}
	
	
	private FUND_ROLE_TASK saveRoleTask(FUND_ROLE_TASK	fUND_rOLE_tASK,FUND_MENU_TASK menu_task,FUND_ROLES frole,FUND_USERS fund_user) {
		FUND_ROLE_TASK	fUND_ROLE_TASK=fUND_rOLE_tASK;
		
		if(fUND_ROLE_TASK==null) {
			fUND_ROLE_TASK=new  FUND_ROLE_TASK();
			fUND_ROLE_TASK.setFund_roles(frole);
			fUND_ROLE_TASK.setFRT_CREATEDBY(fund_user.getSVC_UID());
			fUND_ROLE_TASK.setFRT_DATE(Calendar.getInstance());
			fUND_ROLE_TASK.setFund_menu_task(menu_task);
		}
		fUND_ROLE_TASK.setFRT_MODIFIEDBY(fund_user.getSVC_UID());
		fUND_ROLE_TASK.setFRT_LAST_CHANGE(Calendar.getInstance());
		fUND_ROLE_TASK.setFRT_ACCESS(true);
		fUND_ROLE_TASK.setFRT_UPDATE(true);
		fUND_ROLE_TASK.setFRT_INSERT(true);
		fUND_ROLE_TASK.setFRT_DELETE(true);
		fUND_ROLE_TASK.setFRT_APPROVE(true);
	try {
		fUND_ROLE_TASK=fUND_ROLE_TASKRepository.save(fUND_ROLE_TASK);
		if(fUND_ROLE_TASK!=null) {
			log.info("Role Task saved:"+fUND_ROLE_TASK.getFRT_FRTASKID());
		}	
		else {
			log.info("Role Task not saved");
		}
	}
	catch (Exception e) {
		log.info("Role Task not saved"+e.getMessage());
	}
	return fUND_ROLE_TASK;
	}
	
/*
 * New Chnages Here
 */
	
	public List<Map<String, Object>> getAllGroupsChnage(String roleid){
		/*
		 * fUND_USERSRepository; 
		 * fUND_MENU_GROUPRepository;
		 * fUND_MENU_TASKRepository;  
		 * fUND_MENU_SUB_TASKRepository;
		 */
		List<Map<String,Object>> json=new ArrayList<Map<String,Object>>();
		fUND_MENU_GROUPRepository.findAll().forEach(group->{
			Map<String, Object> jgroup=new HashMap<String,Object>();
			jgroup.put("roleid", roleid);
			jgroup.put("groupid", group.getFMG_GROUPID());
			jgroup.put("groupname", group.getFMG_GROUPNAME());
			List<Map<String,Object>> menus=new ArrayList<Map<String,Object>>();
			group.getFund_menu_task().forEach(menu->{
				Map<String, Object> jmenu=new HashMap<String,Object>();
				jmenu.put("menuid",menu.getFMT_MENUTASKID());
				jmenu.put("menuname",menu.getFMT_MENUNAME());
				FUND_ROLE_TASK role_task=fUND_ROLE_TASKRepository.getMenuOnRole(new Long(roleid.toString()), menu.getFMT_MENUTASKID());
				if(role_task!=null) {
					jmenu.put("ACCESS",role_task.isFRT_ACCESS()+"");
					jmenu.put("INSERT",role_task.isFRT_INSERT()+"");
					jmenu.put("UPDATE",role_task.isFRT_UPDATE()+"");
					jmenu.put("DELETE",role_task.isFRT_DELETE()+"");
					jmenu.put("APPROVE",role_task.isFRT_APPROVE()+"");
				}else {
					jmenu.put("ACCESS","false");
					jmenu.put("INSERT","false");
					jmenu.put("UPDATE","false");
					jmenu.put("DELETE","false");
					jmenu.put("APPROVE","false");
				}
				List<Map<String,Object>> submenus=new ArrayList<Map<String,Object>>();
				menu.getFund_menu_sub_task().forEach(submenu->{
					Map<String, Object> jsubmenu=new HashMap<String,Object>();
					jsubmenu.put("submenuid",submenu.getFMST_MENUSUBTASKID());
					jsubmenu.put("submenuname",submenu.getFMST_MENUSUBNAME());
					FUND_ROLE_SUB_TASK role_sub_task=fUND_ROLE_SUB_TASKRepository.getSubMenuOnRole(new Long(roleid.toString()), submenu.getFMST_MENUSUBTASKID());
					if(role_sub_task!=null) {
						jsubmenu.put("ACCESS",role_sub_task.isFRST_ACCESS()+"");
						jsubmenu.put("INSERT",role_sub_task.isFRST_INSERT()+"");
						jsubmenu.put("UPDATE",role_sub_task.isFRST_UPDATE()+"");
						jsubmenu.put("DELETE",role_sub_task.isFRST_DELETE()+"");
						jsubmenu.put("APPROVE",role_sub_task.isFRST_APPROVE()+"");
					}else {
						jsubmenu.put("ACCESS","false");
						jsubmenu.put("INSERT","false");
						jsubmenu.put("UPDATE","false");
						jsubmenu.put("DELETE","false");
						jsubmenu.put("APPROVE","false");
					}
					submenus.add(jsubmenu);
				});
				
				jmenu.put("submenus", submenus);
				menus.add(jmenu);
			});
			jgroup.put("menus",menus);
			json.add(jgroup);
		});
		return json;
	}
	
	
	@PostMapping("/gettaskrole")
	public ResponseEntity<List<Map<String, Object>>> getTaskRoleChange(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> menutask=requestData.getBody();
		
		Object roleid=menutask.get("roleid");
		List<Map<String,Object>> json=getAllGroupsChnage(roleid.toString());
		//json.put("roleid", new Long(roleid.toString()));
		return new ResponseEntity<>(json,HttpStatus.OK);
	}
	
	/*
	 * End of New Changes
	 */
	//OLD CODE of ROLE TASK
	//@PostMapping("/gettaskrole")
	public ResponseEntity<Map<String, Object>> getTaskRole(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> menutask=requestData.getBody();
		Object roleid=menutask.get("roleid");
		if(roleid==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
		}
		try {
			json.put("roleid", new Long(roleid.toString()));
			List<Map<String, Object>> groupList=new ArrayList<Map<String, Object>>();
			fUND_MENU_GROUPRepository.findAll().forEach(groups->{
				Map<String,Object> group=new HashMap<String,Object>();
				group.put("groupid", groups.getFMG_GROUPID());
				group.put("groupname", groups.getFMG_GROUPNAME());
				List<Map<String, Object>> menuList=new ArrayList<Map<String, Object>>();	
				groups.getFund_menu_task().forEach(menu->{
					Map<String,Object> menus=new HashMap<String,Object>();
					FUND_ROLE_TASK role_task=fUND_ROLE_TASKRepository.getMenuOnRole(new Long(roleid.toString()), menu.getFMT_MENUTASKID());
					if(role_task!=null) {
					menus.put("menuid",role_task.getFund_menu_task().getFMT_MENUTASKID());
					menus.put("menuname",role_task.getFund_menu_task().getFMT_MENUNAME());
					menus.put("ACCESS",role_task.isFRT_ACCESS());
					menus.put("INSERT",role_task.isFRT_ACCESS());
					menus.put("UPDATE",role_task.isFRT_UPDATE());
					menus.put("DELETE",role_task.isFRT_DELETE());
					menus.put("APPROVE",role_task.isFRT_APPROVE());
					List<Map<String, Object>> submenuList=new ArrayList<Map<String, Object>>();
					menu.getFund_menu_sub_task().forEach(submenu->{
						Map<String,Object> submenus=new HashMap<String,Object>();
						FUND_ROLE_SUB_TASK role_sub_task=fUND_ROLE_SUB_TASKRepository.getSubMenuOnRole(new Long(roleid.toString()), submenu.getFMST_MENUSUBTASKID());
						if(role_sub_task!=null) {
							submenus.put("submenuid", role_sub_task.getFund_menu_sub_task().getFMST_MENUSUBTASKID());
							submenus.put("submenuname", role_sub_task.getFund_menu_sub_task().getFMST_MENUSUBNAME());
							submenus.put("ACCESS",role_sub_task.isFRST_ACCESS());
							submenus.put("INSERT",role_sub_task.isFRST_ACCESS());
							submenus.put("UPDATE",role_sub_task.isFRST_UPDATE());
							submenus.put("DELETE",role_sub_task.isFRST_DELETE());
							submenus.put("APPROVE",role_sub_task.isFRST_APPROVE());
						}
						
						if(!submenus.isEmpty()) {
							submenuList.add(submenus);
							menus.put("submenus", submenuList);
						}	
					});
					}
					if(!menus.isEmpty()) {
						menuList.add(menus);	
					}					
				});
				group.put("menus", menuList);
				groupList.add(group);
			});
			json.put("groups", groupList);
		return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
		}catch (Exception e) {
			json.put("msg", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
		}
		
	}	
	

}
