package com.fynisys.menutask.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.fynisys.menutask.repository.FUND_ROLESRepository;
import com.fynisys.model.FUND_ROLES;
import com.fynisys.model.FUND_USERS;
import com.fynisys.repository.FUND_USERSRepository;

@RestController
public class FUND_ROLESController {
	@Autowired
	private FUND_USERSRepository fund_users_Repository;
	
	
	@Autowired
	private FUND_ROLESRepository fund_roles_Repository;
	/*
	 * Reguest is post inn json form and params are
	 * rolename : String
	 * userid : String
	 */
	
//	@PostMapping("/testrole")
//	public String roleCheckMethod(@RequestParam("rolename")String rolename) {
//	FUND_ROLES fund_role = fund_roles_Repository.getRoleCheck(rolename.trim());
//	System.out.println(rolename.trim());
//	System.out.println(fund_role);
//	if(fund_role==null) {
//		return true+"";
//	}
//	
//	return false+"";
//	}
	
	public boolean roleCheck(String rolename) {
		List<FUND_ROLES> fund_role = fund_roles_Repository.getRoleCheck(rolename.trim());
		if(fund_role==null) {
			return true;
		}else if(fund_role.size()==0) {
			return true;
		}
		return false;
	}
	
//	@GetMapping("/roleupdate")
//	public String roleCheckforUpdateUI(@RequestParam("rolename")String rolename,@RequestParam("roleid")long roleid) {
//		List<FUND_ROLES> fund_role = fund_roles_Repository.getRoleCheck(rolename.trim());
//		if(fund_role==null) {
//			return true+"";
//		}
//		else if(fund_role.size()>0) {
//			boolean status=false;
//			for(FUND_ROLES role:fund_role) {
//				if(role.getFRL_ROLEID()==roleid)
//				{
//				status=true;
//				break;
//				}
//			}
//			return status+"";
//		}
//		return false+"";
//	}
	
	
	public boolean roleCheckforUpdate(String rolename,long roleid) {
		List<FUND_ROLES> fund_role = fund_roles_Repository.getRoleCheck(rolename.trim());
		if(fund_role==null) {
			return true;
		}
		else if(fund_role.size()>0) {
			boolean status=false;
			for(FUND_ROLES role:fund_role) {
				if(role.getFRL_ROLEID()==roleid)
				{
				status=true;
				break;
				}
			}
			return status;
		}
		return false;
	}
		
	@PostMapping("/saverole")
	public ResponseEntity<Map<String, Object>> saveRole(
			RequestEntity<Map<String,Object>> requestData
			){
		Map<String, Object> json=new HashMap<String,Object>();
		Map<String,Object> data=requestData.getBody();
		String rolename=(String)data.get("rolename");
		String createdby=(String)data.get("userid");
		if(rolename!=null&&createdby!=null) {
			
			if(roleCheck(rolename.trim())==false) {
				json.put("msg", "role Exists");
				return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
			}
			FUND_ROLES fund_roles=new FUND_ROLES();
			fund_roles.setFRL_ROLENAME(rolename.trim());
			fund_roles.setFRL_ROLETYPE("No permissions");
			fund_roles.setFRL_DATE(Calendar.getInstance());
			FUND_USERS fund_users=fund_users_Repository.findByUSER_NAME(createdby);
			if(fund_users!=null) {
				fund_roles.setFRL_CREATEDBY(fund_users.getSVC_UID());	
			   try {
				  fund_roles=fund_roles_Repository.save(fund_roles);
				  json.put("msg","FUND ROLES "+rolename+" Successfully saved");
				  json.put("roleid",fund_roles.getFRL_ROLEID());
				  json.put("rolename", fund_roles.getFRL_ROLENAME());
				  json.put("roletype", fund_roles.getFRL_ROLETYPE());
				  json.put("enteredby", createdby);
				  Calendar cal=fund_roles.getFRL_DATE();
				  json.put("date",(cal!=null?cal.getTimeInMillis():null));		  
				  return new ResponseEntity<>(json,HttpStatus.OK);
			   }catch (Exception e) {
				json.put("msg", e.getMessage());
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
			}
			else {
				json.put("msg", "Please check username");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "Please check Input json, missing some required attributes");
		}
		return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/buildinrole")
	public ResponseEntity<Map<String, Object>> buildinrole(
			RequestEntity<Map<String,Object>> requestData
			){
		Map<String, Object> json=new HashMap<String,Object>();
		Map<String,Object> data=requestData.getBody();
		String rolename=(String)data.get("rolename");
		String createdby=(String)data.get("userid");
		if(rolename!=null) {
			FUND_ROLES fund_roles=new FUND_ROLES();
			fund_roles.setFRL_ROLENAME(rolename);
			fund_roles.setFRL_ROLETYPE("build-in");
			fund_roles.setFRL_DATE(Calendar.getInstance());
			FUND_USERS fund_users=fund_users_Repository.findByUSER_NAME(createdby);
			if(fund_users!=null) {
				fund_roles.setFRL_CREATEDBY(fund_users.getSVC_UID());	
			}
			try {
				  fund_roles=fund_roles_Repository.save(fund_roles);
				  json.put("msg","FUND ROLES "+rolename+" Successfully saved");
				  json.put("roleid",fund_roles.getFRL_ROLEID());
				  json.put("rolename", fund_roles.getFRL_ROLENAME());
				  json.put("roletype", fund_roles.getFRL_ROLETYPE());
				  json.put("enteredby", createdby);
				  Calendar cal=fund_roles.getFRL_DATE();
				  json.put("date",(cal!=null?cal.getTimeInMillis():null));		  
				  return new ResponseEntity<>(json,HttpStatus.OK);
			   }catch (Exception e) {
				json.put("msg", e.getMessage());
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "Please check Input json, missing some required attributes");
		}
		return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
	}
	
	/*
	 * Reguest is post inn json form and params are
	 * roleid : Long 
	 * rolename : String
	 * userid : String
	 */
	
	@PostMapping("/updaterole")
	public ResponseEntity<Map<String, Object>> updateRole(
			RequestEntity<Map<String,Object>> requestData
			){
		Map<String, Object> json=new HashMap<String,Object>();
		Map<String,Object> data=requestData.getBody();
		Object roleid=data.get("roleid");
		String rolename=(String)data.get("rolename");
		String modifiedby=(String)data.get("userid");
		if(rolename!=null&&modifiedby!=null&&roleid!=null) {
			if(roleCheckforUpdate(rolename.trim(), new Long(roleid.toString()))==false) {
				json.put("msg", "role Exists");
				return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
			}
			FUND_ROLES fund_roles=fund_roles_Repository.findOne(new Long(roleid.toString()));
			fund_roles.setFRL_ROLENAME(rolename);
			fund_roles.setFRL_ROLETYPE("No permissions");
			fund_roles.setFRL_DATE(Calendar.getInstance());
			FUND_USERS fund_users=fund_users_Repository.findByUSER_NAME(modifiedby);
			if(fund_users!=null) {
				fund_roles.setFRL_MODIFIEDBY(fund_users.getSVC_UID());
				fund_roles.setFRL_LAST_CHANGE(Calendar.getInstance());
			   try {
				  fund_roles=fund_roles_Repository.save(fund_roles);
				  json.put("msg","FUND ROLES "+rolename+" Successfully saved");
				  json.put("roleid",fund_roles.getFRL_ROLEID());
				  json.put("rolename", fund_roles.getFRL_ROLENAME());
				  json.put("roletype", fund_roles.getFRL_ROLETYPE());
				  json.put("enteredby", fund_roles.getFRL_CREATEDBY());
				  Calendar cal=fund_roles.getFRL_DATE();
				  json.put("date",(cal!=null?cal.getTimeInMillis():null));		  
				  return new ResponseEntity<>(json,HttpStatus.OK);
			   }catch (Exception e) {
				json.put("msg", e.getMessage());
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
			}
			else {
				json.put("msg", "Please check username");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "Please check Input json, missing some required attributes");
		}
		return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
	}
	
	/* 
	 * To all Roles information without page
	 */
	@GetMapping("/roles")
	public ResponseEntity<List<Map<String, Object>>> getRoles(){
		List<Map<String, Object>> jsonall=new ArrayList<Map<String,Object>>();
		fund_roles_Repository.ordeByFRL_ROLENAME().forEach(record->{
			Map<String, Object> json=new HashMap<String, Object>();
			json.put("roleid",record.getFRL_ROLEID());
			json.put("rolename", record.getFRL_ROLENAME());
			json.put("roletype", record.getFRL_ROLETYPE());
			String username=record.getFRL_CREATEDBY();
			if(username==null) {
				json.put("enteredby", "Admin User");
			}else {
				FUND_USERS creator=getUserName(username);
				if(creator!=null) {
				json.put("enteredby", creator.getSVU_NAME());
				}
			}
			Calendar cal=record.getFRL_DATE();
			json.put("date",(cal!=null?cal.getTimeInMillis():null));
			jsonall.add(json);
		});
		return new ResponseEntity<>(jsonall,HttpStatus.OK);
		
	}
	

	/* 
	 * To all Roles information with page
	 */
	@GetMapping("/roleswithpage")
	public ResponseEntity<Page<Map<String, Object>>> getRolesWithPagination(Pageable page){
		Page<FUND_ROLES> allroles=fund_roles_Repository.ordeByFRL_ROLENAME(page);
		Page<Map<String, Object>> jsonall=allroles.map(new Converter<FUND_ROLES, Map<String, Object>>() {
			@Override
			public Map<String, Object> convert(FUND_ROLES record) {
				Map<String, Object> json=new HashMap<String, Object>();
				json.put("roleid",record.getFRL_ROLEID());
				json.put("rolename", record.getFRL_ROLENAME());
				json.put("roletype", record.getFRL_ROLETYPE());
				String username=record.getFRL_CREATEDBY();
				if(username==null) {
					json.put("enteredby", "Admin User");
				}else {
					FUND_USERS creator=getUserName(username);
					if(creator!=null) {
					json.put("enteredby", creator.getSVU_NAME());
					}
				}
				Calendar cal=record.getFRL_DATE();
				json.put("date",(cal!=null?cal.getTimeInMillis():null));
				return json;
			}
		});
		return new ResponseEntity<>(jsonall,HttpStatus.OK);
		
	}
	
	/*
	 * TO get particular role information
	 */
	
	@PostMapping("/rolebyname")
	public ResponseEntity<Map<String, Object>> getRole(
			RequestEntity<Map<String,Object>> requestData
			){
		Map<String, Object> json=new HashMap<String,Object>();
		Map<String,Object> data=requestData.getBody();
		String rolename=(String)data.get("rolename");
		if(rolename!=null) {
			FUND_ROLES fund_roles=fund_roles_Repository.findByRoleName(rolename);
			if(fund_roles!=null) {				  
				  fund_roles=fund_roles_Repository.save(fund_roles);
				  json.put("roleid",fund_roles.getFRL_ROLEID());
				  json.put("rolename", fund_roles.getFRL_ROLENAME());
				  json.put("roletype", fund_roles.getFRL_ROLETYPE());
				  Calendar cal=fund_roles.getFRL_DATE();
				  json.put("date",(cal!=null?cal.getTimeInMillis():null));
				  String username=fund_roles.getFRL_CREATEDBY();
					if(username==null) {
						json.put("enteredby", "Admin User");
					}else {
						FUND_USERS creator=getUserName(username);
						if(creator!=null) {
						json.put("enteredby", creator.getSVU_NAME());
						}
					}
				 
				  return new ResponseEntity<>(json,HttpStatus.OK);
			}
			else {
				json.put("msg", "Please check rolename");
			}
			}
			else {
				json.put("msg", "Please check Input json, missing some required attributes");
				
			}
		
		return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
	}
	
	/*
	 * Searching For roles with page
	 * 
	 */
	
	@PostMapping("/searchrole")
	public ResponseEntity<List<Map<String, Object>>> searchRole(
			RequestEntity<Map<String,Object>> requestData
			){
		List<Map<String, Object>> jsonall=new ArrayList<Map<String,Object>>();
		Map<String,Object> data=requestData.getBody();
		String rolename=(String)data.get("rolename");
		fund_roles_Repository.findByRoleNameSearch(rolename).forEach(record->{
			Map<String, Object> json=new HashMap<String, Object>();
			json.put("roleid",record.getFRL_ROLEID());
			json.put("rolename", record.getFRL_ROLENAME());
			json.put("roletype", record.getFRL_ROLETYPE());
			String username=record.getFRL_CREATEDBY();
			if(username==null) {
				json.put("enteredby", "Admin User");
			}else {
				FUND_USERS creator=getUserName(username);
				if(creator!=null) {
				json.put("enteredby", creator.getSVU_NAME());
				}
			}
			Calendar cal=record.getFRL_DATE();
			json.put("date",(cal!=null?cal.getTimeInMillis():null));
			jsonall.add(json);
		});
		return new ResponseEntity<>(jsonall,HttpStatus.OK);
		
	}
	
	
	/*
	 * Searching For roles with paging
	 * 
	 */
	@GetMapping("/searchrolewithpage")
	public ResponseEntity<Page<Map<String, Object>>>  searchRoleWithPagination(
			RequestEntity<Map<String,Object>> requestData,
			Pageable page
			){
		Map<String,Object> data=requestData.getBody();
		String rolename=(String)data.get("rolename");		
		Page<FUND_ROLES> allroles=fund_roles_Repository.findByRoleNameSearch(rolename, page);
		Page<Map<String, Object>> jsonall=allroles.map(new Converter<FUND_ROLES, Map<String, Object>>() {
			@Override
			public Map<String, Object> convert(FUND_ROLES record) {
				Map<String, Object> json=new HashMap<String, Object>();
				json.put("roleid",record.getFRL_ROLEID());
				json.put("rolename", record.getFRL_ROLENAME());
				json.put("roletype", record.getFRL_ROLETYPE());
				String username=record.getFRL_CREATEDBY();
				if(username==null) {
					json.put("enteredby", "Admin User");
				}else {
					FUND_USERS creator=getUserName(username);
					if(creator!=null) {
					json.put("enteredby", creator.getSVU_NAME());
					}
				}
				Calendar cal=record.getFRL_DATE();
				json.put("date",(cal!=null?cal.getTimeInMillis():null));
				return json;
			}
		});
		return new ResponseEntity<>(jsonall,HttpStatus.OK);
		
	}
	
	
	public FUND_USERS getUserName(String userid)
	{
		FUND_USERS fuser= fund_users_Repository.findOne(userid);
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
