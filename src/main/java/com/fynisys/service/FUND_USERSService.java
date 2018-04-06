package com.fynisys.service;


import org.springframework.stereotype.Service;

@Service
public class FUND_USERSService{
	
/*	@Autowired
	FUND_USERSRepository fUND_USERSRepository;
	@Autowired
	TempPassGeneratorService tempPassGeneratorService;
	
	private final Logger logger=LoggerFactory.getLogger("FUND_USERS");
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> getLogin(@RequestParam("username")String user,@RequestParam("password") String password){
		Map<String,Object> json=new HashMap<String,Object>();
		FUND_USERS fundu=fUND_USERSRepository.getUser(user, password);
		if(fundu!=null){
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
		else{
			json.put("msg","Invalid username or password");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}		
		
	}
	
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
				user.setSVU_USER_PASSWORD(newpass);
				user.setSVU_FLAG(1);
				user.setSVU_LAST_CHANGE(Calendar.getInstance());
				try{
					user=fUND_USERSRepository.save(user);
					if(user!=null){
						json.put("msg", "Reset password done successfully");
						return new ResponseEntity<>( json, HttpStatus.OK);
					}
				}catch (Exception e) {
					logger.error(e.getMessage());
					json.put("msg", e.getMessage());
					return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
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
	}*/
}
