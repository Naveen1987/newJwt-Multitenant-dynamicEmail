package com.fynisys.controller;

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
import com.fynisys.menutask.repository.FUND_ROLESRepository;
import com.fynisys.model.FUND_FUNDS_DETAIL;
import com.fynisys.model.FUND_PASSWORD_HISTORY;
import com.fynisys.model.FUND_ROLES;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.FUND_USERS_FUNDS;
import com.fynisys.otptempass.service.TempPassGeneratorService;
import com.fynisys.repository.FUND_FUNDS_DETAILRepository;
import com.fynisys.repository.FUND_PASSWORD_HISTORYRepository;
import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USERS_FUNDSRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class FUND_USERSController.
 */
@RestController
public class FUND_USERSController{
	
	/** The fund password history repository. */
	@Autowired
	FUND_PASSWORD_HISTORYRepository fund_password_historyRepository;
	
	/** The UN D USERS repository. */
	@Autowired
	FUND_USERSRepository fUND_USERSRepository;
	
	/** The temp pass generator service. */
	@Autowired
	TempPassGeneratorService tempPassGeneratorService;
	
	/** The UN D ROLES repository. */
	@Autowired
	FUND_ROLESRepository fUND_ROLESRepository;
	
	/** The UN D USER S FUNDS repository. */
	@Autowired
	FUND_USERS_FUNDSRepository fUND_USERS_FUNDSRepository;
	
	/** The UN D FUND S DETAIL repository. */
	@Autowired
	FUND_FUNDS_DETAILRepository fUND_FUNDS_DETAILRepository;
	
	/** The logger. */
	private final Logger logger=LoggerFactory.getLogger("FUND_USERS");
	
	/**
	 * Gets the login.
	 *
	 * @param user the user
	 * @param password the password
	 * @return the login
	 */
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> getLogin(@RequestParam("username")String user,@RequestParam("password") String password){
		Map<String,Object> json=new HashMap<String,Object>();
		FUND_USERS fundu=fUND_USERSRepository.getUser(user, password);
		if(fundu!=null){
			//edited by vikas
			
			if(fundu.getSVU_BLOCK().equals("1".trim()))
			{
				json.put("msg", "User is deactivated");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
			else
			{
			if(fundu.getSVU_FLAG()==0){
				Calendar tempassdate=fundu.getSVU_LAST_CHANGE();
				if(tempPassGeneratorService.invalidPassword(tempassdate)){
					json.put("msg","Again send OTP");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}else{
					json.put("msg","reset password");
					return new ResponseEntity<>(json,HttpStatus.OK);			
				}
			}
			else{
				json.put("msg","Successfully login");
				json.put("displayname", fundu.getSVU_NAME());
				json.put("svc_uid", fundu.getSVC_UID());
				json.put("lastseen", fundu.getSVU_USER_LASTSEEN());
				json.put("roleid", fundu.getFund_roles().getFRL_ROLEID());
				json.put("rolename", fundu.getFund_roles().getFRL_ROLENAME());
				fundu.setSVU_USER_LASTSEEN(Calendar.getInstance());
				try{
				fUND_USERSRepository.save(fundu);
				return new ResponseEntity<>(json, HttpStatus.OK);				
				}catch (Exception e) {
					json.put("msg", e.getMessage());
					return new ResponseEntity<>(json, HttpStatus.FORBIDDEN);
				}
			}
		}
		}
		else{
			json.put("msg","Invalid username or password");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}		
		
	}
	
	/**
	 * Gets the reset pass.
	 *
	 * @param data the data
	 * @return the reset pass
	 */
	@PostMapping("/resetpassword")
	public ResponseEntity<Map<String, Object>> getResetPass(RequestEntity<Map<String, Object>> data){
		Map<String,Object> users=data.getBody();
		Map<String,Object> json=new HashMap<String,Object>();
		String username=(String)users.get("username");
		String password=(String)users.get("oldpassword");
		String newpass=(String)users.get("newpassword");
		if(username!=null&&password!=null&&newpass!=null){
			FUND_USERS user=fUND_USERSRepository.getUserReset(username, password, 0);
			if(user!=null){
				//password History
				String uid=user.getSVC_UID();
				FUND_PASSWORD_HISTORY fph=fund_password_historyRepository.getPass(uid, newpass);
				if(fph!=null)
				{
					//System.out.println("password is present in table  "+newpass);
					json.put("msg", "You have used this password previously");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
				else{
					
					fph=new FUND_PASSWORD_HISTORY();
					fph.setFPH_PASSWORD(newpass);
					fph.setFPH_DATE(Calendar.getInstance());
					//fph.setFPH_HISTORY(fphRepo.maxCount(uid)+1);
					fph.setFund_users(user);
					try
					{
						fund_password_historyRepository.save(fph);
					if (fund_password_historyRepository.maxCount(uid) > 10 )
					{
						long id = fund_password_historyRepository.maxTimestamp(uid);
						 
						fund_password_historyRepository.delete(id);
						 //fphRepo.save(fph1);
						 }
					
					}
					catch(Exception e)
					{
						logger.error(e.getMessage());
						json.put("msg", e.getMessage());
						return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
					}
				
				user.setSVU_USER_PASSWORD(newpass);
				user.setSVU_FLAG(1);
				user.setSVU_LAST_CHANGE(Calendar.getInstance());
				try{
					user=fUND_USERSRepository.save(user);
					if(user!=null){
						json.put("msg", "Reset password done successfully");
						json.put("displayname", user.getSVU_NAME());
						json.put("svc_uid", user.getSVC_UID());
						json.put("lastseen", user.getSVU_USER_LASTSEEN());
						json.put("roleid", user.getFund_roles().getFRL_ROLEID());
						json.put("rolename", user.getFund_roles().getFRL_ROLENAME());
						return new ResponseEntity<>( json, HttpStatus.OK);
					}
				}catch (Exception e) {
					logger.error(e.getMessage());
					json.put("msg", e.getMessage());
					return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
				}
				
				}
			}


			else{
				json.put("msg", "Invalid username or password");
				return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);	
			}
		}
		else{
			json.put("msg", "username or old password or new password String is null");
			return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);	
		}
		json.put("msg", "Successfully");
		return new ResponseEntity<>(json, HttpStatus.OK);
	}
	
	
//	@GetMapping("/testuser")
//	public String userCheck(@RequestParam("userid")String userid) {
//		FUND_USERS fund_user = fUND_USERSRepository.getUserCheck(userid.trim());
//		if(fund_user==null) {
//			return true+"";
//		}
//		return false+"";
//	}
	
	/**
 * User check.
 *
 * @param userid the userid
 * @return true, if successful
 */
public boolean userCheck(String userid) {
		FUND_USERS fund_user = fUND_USERSRepository.getUserCheck(userid.trim());
		if(fund_user==null) {
			return true;
		}
		return false;
	}
	
//	public boolean userCheck(String userid) {
//		FUND_USERS fund_user = fUND_USERSRepository.getUserCheck(userid.trim());
//		if(fund_user==null) {
//			return true;
//		}
//		return false;
//	}

	/**
 * Save user.
 *
 * @param data the data
 * @return the response entity
 */
@PostMapping("/saveuser")
	public ResponseEntity<Map<String, Object>> saveUser(RequestEntity<Map<String, Object>> data){
		Map<String,Object> user=data.getBody();
		Map<String,Object> json=new HashMap<String,Object>();
		/*
		 * TODO
		 * */
		String SVU_NAME=(String)user.get("displayname");
		String SVU_USER_NAME=(String)user.get("userid");
		String SVU_EMAIL=(String)user.get("email");
		String SVU_REUTERS_CODE=(String)user.get("reuter_code");
		String SVU_USER_PASSWORD=(String)user.get("password");
		Object SVU_EXPIRY_DAYS=user.get("pass_exp_day");
		Object SVU_EXPIRY_DATE=user.get("pass_exp_date");
		Object FRL_ROLEID=user.get("roleid");
		Object SVU_BLOCK=user.get("status");
		Object SVU_DATE=user.get("date");
		/*
		 * for uncheck this warning
		 */ 
		@SuppressWarnings("unchecked")
		//List<Map<String,Object>> fund_details=(List<Map<String,Object>>)(List<?>)user.get("funds");
		List<Map<String,Object>> fund_details=(List<Map<String,Object>>)user.get("funds");
		String SVU_USER_CREATEDBY=(String)user.get("createdby");
		if(SVU_NAME==null||SVU_USER_NAME==null||SVU_EMAIL==null||SVU_USER_PASSWORD==null||SVU_EXPIRY_DAYS==null
				||SVU_EXPIRY_DATE==null||FRL_ROLEID==null||SVU_DATE==null||SVU_BLOCK==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		else {
			if(userCheck(SVU_USER_NAME.trim())==false) {
				json.put("msg", "user Exists");
				return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
			}
			FUND_USERS fund_user=new FUND_USERS();
			/*
			 * User Id only save in uppercase Only
			 */
			fund_user.setSVU_USER_NAME(SVU_USER_NAME.trim());
			fund_user.setSVU_NAME(SVU_NAME.trim());
			fund_user.setSVU_EMAIL(SVU_EMAIL);
			if(SVU_REUTERS_CODE!=null) {
				fund_user.setSVU_REUTERS_CODE(SVU_REUTERS_CODE);
			}
			fund_user.setSVU_USER_PASSWORD(SVU_USER_PASSWORD);
			fund_user.setSVU_FLAG(1);
			Calendar exp_date=Calendar.getInstance();
			exp_date.setTimeInMillis(new Long(SVU_EXPIRY_DATE.toString()).longValue());
			fund_user.setSVU_EXPIRY_DATE(exp_date);
			fund_user.setSVU_EXPIRY_DAYS(new Integer(SVU_EXPIRY_DAYS.toString()));
			Calendar date=Calendar.getInstance();
			date.setTimeInMillis(new Long(SVU_DATE.toString()).longValue());
			fund_user.setSVU_DATE(date);
			/**
			 * For block state 
			 * 0= active
			 * 1= not active
			 */
			fund_user.setSVU_BLOCK(SVU_BLOCK.toString());
			
			fund_user.setSVU_USER_CREATEDBY(SVU_USER_CREATEDBY);
			fund_user.setSVU_USER_MODIFIEDBY(SVU_USER_CREATEDBY);
			fund_user.setSVU_LAST_CHANGE(Calendar.getInstance());
			
			try {
				
				FUND_ROLES fundrole=fUND_ROLESRepository.findOne(new Long(FRL_ROLEID.toString()).longValue());
				if(fundrole==null) {
					json.put("msg", "Not role found");
					return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
				}
				else {
					/*
					 * Adding role
					 */
				fund_user.setFund_roles(fundrole);
				fund_user = fUND_USERSRepository.save(fund_user);
				if(fund_user!=null) {
					final String uuid=fund_user.getSVC_UID();
					FUND_PASSWORD_HISTORY fph=new FUND_PASSWORD_HISTORY();
					

					/*
					 * saving password in password history by vikas
					 */
					fph.setFPH_PASSWORD(SVU_USER_PASSWORD);
					fph.setFPH_DATE(Calendar.getInstance());
					fph.setFund_users(fund_user);
					//fph.setFPH_HISTORY(1);
					
					fund_password_historyRepository.save(fph);
					json.put("msg", "saved successfully");
					// saving null fund
					
					if(fund_details==null)
					{
						//FUND_FUNDS_DETAIL fdetail = fUND_FUNDS_DETAILRepository.findOne(funds.get("fundid").toString());
						//if (fdetail != null) {
						FUND_USERS_FUNDS fund = new FUND_USERS_FUNDS();
							////fund.setFFID(fdetail);
							//fund.setFFUNDS(fdetail.getFFUNDS());
							fund.setSVC_UID(uuid);
							fund = fUND_USERS_FUNDSRepository.save(fund);	
							
					}
					else
					{
					
					fund_details.forEach(funds->{
						FUND_USERS_FUNDS fund=new FUND_USERS_FUNDS();
						FUND_FUNDS_DETAIL fdetail=fUND_FUNDS_DETAILRepository.findOne(funds.get("fundid").toString());
						if(fdetail!=null) {
							fund.setFFID(fdetail);
							fund.setFFUNDS(fdetail.getFFUNDS());
							fund.setSVC_UID(uuid);
							fund=fUND_USERS_FUNDSRepository.save(fund);
							if(fund!=null) {
							json.put("assign",(json.get("assign")==null?"":json.get("assign")+",")+fdetail.getFFUNDS());
							}else {
							json.put("notassign",(json.get("notassign")==null?"":json.get("notassign")+",")+funds.get("fundid").toString());
							}
							
						}
						
					});
					}
					return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
				}
				else {
						json.put("msg", "not saved some internal error");
						return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
				}
			
				}
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	/**
	 * Gets the active user.
	 *
	 * @param data the data
	 * @return the active user
	 */
	@PostMapping("/activeuser")
	public ResponseEntity<Map<String, Object>> getActiveUser(RequestEntity<Map<String, Object>> data){
		Map<String,Object> user=data.getBody();
		Map<String,Object> json=new HashMap<String,Object>();
		/*
		 * TODO
		 * */
		String userid=(String)user.get("userid");
		String modifiedby=(String)user.get("modifiedby");
		if(userid==null||modifiedby==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
		else {
			FUND_USERS fund_user=fUND_USERSRepository.findByUSER_NAME(userid);
			if(fund_user==null) {
				json.put("msg", "No user found");
				return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
			fund_user.setSVU_BLOCK("0");
			fund_user.setSVU_USER_MODIFIEDBY(modifiedby);
			fund_user.setSVU_LAST_CHANGE(Calendar.getInstance());
			try {
				/*
				 * Save action
				 */
				fund_user=fUND_USERSRepository.save(fund_user);
				if(fund_user!=null) {
					json.put("msg", "Now "+userid+" status is : active");
					return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg", "not saved some internal error");
					return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	/**
	 * Gets the de active user.
	 *
	 * @param data the data
	 * @return the de active user
	 */
	@PostMapping("/deactiveuser")
	public ResponseEntity<Map<String, Object>> getDeActiveUser(RequestEntity<Map<String, Object>> data){
		Map<String,Object> user=data.getBody();
		Map<String,Object> json=new HashMap<String,Object>();
		/*
		 * TODO
		 * */
		String userid=(String)user.get("userid");
		String modifiedby=(String)user.get("modifiedby");
		if(userid==null||modifiedby==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
		else {
			FUND_USERS fund_user=fUND_USERSRepository.findByUSER_NAME(userid);
			if(fund_user==null) {
				json.put("msg", "No user found");
				return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
			fund_user.setSVU_BLOCK("1");
			fund_user.setSVU_USER_MODIFIEDBY(modifiedby);
			fund_user.setSVU_LAST_CHANGE(Calendar.getInstance());
			try {
				/*
				 * Save action
				 */
				fund_user=fUND_USERSRepository.save(fund_user);
				if(fund_user!=null) {
					json.put("msg", "Now "+userid+" status is : not active");
					return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg", "not saved some internal error");
					return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		
	}
	
	/**
	 * Gets the user.
	 *
	 * @param data the data
	 * @return the user
	 */
	@PostMapping("/getuser")
	public ResponseEntity<Map<String, Object>> getUser(RequestEntity<Map<String, Object>> data){
		Map<String,Object> user=data.getBody();
		Map<String,Object> json=new HashMap<String,Object>();
		/*
		 * TODO
		 * */
		String userid=(String)user.get("userid");
		if(userid==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
		else {
			FUND_USERS fund_user=fUND_USERSRepository.findByUSER_NAME(userid);
			if(fund_user==null) {
				json.put("msg", "No user found");
				return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
			}
			else {
				json.put("user_uuid", fund_user.getSVC_UID());
				json.put("userid", fund_user.getSVU_USER_NAME());
				json.put("displayname", fund_user.getSVU_NAME());
				json.put("email", fund_user.getSVU_EMAIL());
				json.put("isactive", fund_user.getSVU_BLOCK());
				json.put("date", fund_user.getSVU_DATE());
				json.put("roleid", fund_user.getFund_roles().getFRL_ROLEID());
				json.put("rolename", fund_user.getFund_roles().getFRL_ROLENAME());
				FUND_USERS fund_userm=fUND_USERSRepository.findByUSER_NAME(fund_user.getSVU_USER_CREATEDBY());
				if(fund_userm!=null) {
					json.put("enteredby",fund_userm.getSVU_NAME());
					json.put("enteredbyusername",fund_userm.getSVU_USER_NAME());
				}
//				List<Map<String,Object>> funds=getFundRelatedUser(fund_user.getSVC_UID());
//				json.put("funds", funds);
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
			}
			
		}
		
	}
	
//	public List<Map<String,Object>> getFundRelatedUser(String uuid){
//		List<Map<String,Object>> funds=new ArrayList<Map<String,Object>>();
//		fUND_FUNDS_DETAILRepository.findAll().forEach(fund->{
//			Map<String,Object> fundMap=new HashMap<String,Object>();
//			FUND_USERS_FUNDS fuf=fUND_USERS_FUNDSRepository.getFund(fund.getFID(), uuid);
//			if(fuf!=null) {
//				fundMap.put("fundid", fuf.getFID());
//				fundMap.put("fundname", fuf.getFFUNDS());
//				fundMap.put("assign", "true");
//			}
//			else {
//				fundMap.put("fundid", fund.getFID());
//				fundMap.put("fundname", fund.getFFUNDS());
//				fundMap.put("assign", "false");
//			}
//			funds.add(fundMap);
//		});
//		return funds;
//	}
	
	/**
 * Gets the all user.
 *
 * @return the all user
 */
@GetMapping("/getalluser")
	public ResponseEntity<List<Map<String, Object>>> getAllUser(){
		List<Map<String,Object>> json=new ArrayList<Map<String,Object>>();
		/*
		 * TODO
		 * */
		//Iterable<FUND_USERS> users=fUND_USERSRepository.findAll();
		Iterable<FUND_USERS> users=fUND_USERSRepository.orderBySVU_NAME();
		users.forEach(user->{
			Map<String,Object> ujson=new HashMap<String,Object>();
			ujson.put("user_uuid", user.getSVC_UID());
			ujson.put("userid", user.getSVU_USER_NAME());
			ujson.put("displayname", user.getSVU_NAME());
			ujson.put("email", user.getSVU_EMAIL());
			ujson.put("expiredays", user.getSVU_EXPIRY_DAYS());
			ujson.put("expiredate", user.getSVU_EXPIRY_DATE());
			ujson.put("isactive", user.getSVU_BLOCK());
			ujson.put("date", user.getSVU_DATE());
			ujson.put("roleid", user.getFund_roles().getFRL_ROLEID());
			ujson.put("rolename", user.getFund_roles().getFRL_ROLENAME());
			FUND_USERS fund_user=fUND_USERSRepository.findByUSER_NAME(user.getSVU_USER_CREATEDBY());
			if(fund_user!=null) {
				ujson.put("enteredby",fund_user.getSVU_NAME());
				ujson.put("enteredbyusername",fund_user.getSVU_USER_NAME());
			}
//			List<Map<String,Object>> funds=getFundRelatedUser(user.getSVC_UID());
//			ujson.put("funds", funds);
			json.add(ujson);
		});
		return new ResponseEntity<List<Map<String, Object>>>(json,HttpStatus.OK);
	}
	
	/**
	 * Gets the all user with page.
	 *
	 * @param page the page
	 * @return the all user with page
	 */
	@GetMapping("/getalluserwithpage")
	public ResponseEntity<Page<Map<String, Object>>> getAllUserWithPage(Pageable page){
		
		/*
		 * TODO
		 * */
		//Page<FUND_USERS> users=fUND_USERSRepository.findAll(page);
		Page<FUND_USERS> users=fUND_USERSRepository.orderBySVU_NAME(page);
		
		Page<Map<String,Object>> json=users.map(new Converter<FUND_USERS, Map<String,Object>>() {

			@Override
			public Map<String, Object> convert(FUND_USERS user) {
				Map<String,Object> ujson=new HashMap<String,Object>();
				ujson.put("user_uuid", user.getSVC_UID());
				ujson.put("userid", user.getSVU_USER_NAME());
				ujson.put("displayname", user.getSVU_NAME());
				ujson.put("email", user.getSVU_EMAIL());
				ujson.put("expiredays", user.getSVU_EXPIRY_DAYS());
				ujson.put("expiredate", user.getSVU_EXPIRY_DATE());
				ujson.put("isactive", user.getSVU_BLOCK());
				ujson.put("date", user.getSVU_DATE());
				ujson.put("roleid", user.getFund_roles().getFRL_ROLEID());
				ujson.put("rolename", user.getFund_roles().getFRL_ROLENAME());
				FUND_USERS fund_user=fUND_USERSRepository.findByUSER_NAME(user.getSVU_USER_CREATEDBY());
				if(fund_user!=null) {
					ujson.put("enteredby",fund_user.getSVU_NAME());
					ujson.put("enteredbyusername",fund_user.getSVU_USER_NAME());
				}
				
				return ujson;
			}
		});
		return new ResponseEntity<Page<Map<String, Object>>>(json,HttpStatus.OK);
		
	}
	
	
	

	/**
	 * Update user.
	 *
	 * @param requestData the request data
	 * @return the response entity
	 */
	@PostMapping("/updateuser")
	public ResponseEntity<Map<String,Object>> updateUser(RequestEntity<Map<String,Object>> requestData)
	{
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> data=requestData.getBody();
		String userid=(String) data.get("userid");
		String displayname=(String) data.get("displayname");
		String emailid=(String) data.get("email");
		String createdby=(String) data.get("createdby");
		Object status=(String) data.get("status");
		Object roleid=(String) data.get("roleid");
		List<Map<String,Object>> fundlist=(List<Map<String,Object>>) data.get("funds");
		
		if(userid==null||displayname==null||emailid==null||status==null||roleid==null)
		{
			json.put("msg", "please enter the mandetory field");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			
		}
		else
		{
			FUND_USERS fund_user=fUND_USERSRepository.findByUSER_NAME(userid);
			if(fund_user==null)
			{
				json.put("msg", "userid is not valid");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
			fund_user.setSVU_NAME(displayname);
			fund_user.setSVU_EMAIL(emailid);
			fund_user.setSVU_BLOCK(status.toString());
			FUND_USERS fund_u=fUND_USERSRepository.findByUSER_NAME(createdby);
			fund_user.setSVU_USER_MODIFIEDBY(fund_u.getSVU_USER_NAME());
			fund_user.setSVU_LAST_CHANGE(Calendar.getInstance());
			
						
			FUND_ROLES fund_roles =fUND_ROLESRepository.findOne(new Long(roleid.toString()).longValue());
			if(fund_roles==null)
			{
				json.put("msg", "role does not exist");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
			else
			{
				fund_user.setFund_roles(fund_roles);
				try
				{
				fund_user=fUND_USERSRepository.save(fund_user);
				if(fund_user!=null)
				{
					final String uuid=fund_user.getSVC_UID();
					json.put("msg", "updated successfully");
					//return new ResponseEntity<>(json,HttpStatus.OK);
					if(fundlist==null)
					{
						FUND_USERS_FUNDS fund_user_fund=new FUND_USERS_FUNDS();
						fund_user_fund.setSVC_UID(uuid);
						fund_user_fund=fUND_USERS_FUNDSRepository.save(fund_user_fund);
						
					}
					else
					{
						final String uid=fund_user.getSVC_UID();
						//for delete
						fundlist.forEach(fund->{
							FUND_USERS_FUNDS fuf=fUND_USERS_FUNDSRepository.getFund(fund.get("fundid").toString(),uid);
							fUND_USERS_FUNDSRepository.delete(fuf);
						});
						
						//again for insert
						fundlist.forEach(funds->{
							FUND_USERS_FUNDS fund = new FUND_USERS_FUNDS();
							FUND_FUNDS_DETAIL fdetail = fUND_FUNDS_DETAILRepository
									.findOne(funds.get("fundid").toString());
							
							if (fdetail != null) {
								fund.setFFID(fdetail);
								fund.setFFUNDS(fdetail.getFFUNDS());
								fund.setSVC_UID(uuid);
								fund = fUND_USERS_FUNDSRepository.save(fund);
								if (fund != null) {
									json.put("assign", (json.get("assign") == null ? "" : json.get("assign") + ",")
											+ fdetail.getFFUNDS());
								} else {
									json.put("notassign",
											(json.get("notassign") == null ? "" : json.get("notassign") + ",")
													+ funds.get("fundid").toString());
								}

							}
							
							
						});
					}
					return new ResponseEntity<>(json,HttpStatus.OK);
				}
				else
				{
					json.put("msg", "not upadated some internal error");
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

	}
	
}
