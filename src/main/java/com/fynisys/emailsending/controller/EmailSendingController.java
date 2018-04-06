package com.fynisys.emailsending.controller;

import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fynisys.model.FUND_USERS;
import com.fynisys.model.FUND_USER_PASSWORD;
import com.fynisys.otptempass.service.OtpGeneratorService;
import com.fynisys.otptempass.service.TempPassGeneratorService;
import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USER_PASSWORDRepository;

@RestController
public class EmailSendingController {
	
	@Autowired
	OtpGeneratorService otpGeneratorService;
	
	@Autowired
	TempPassGeneratorService tempPassGeneratorService;
	
	@Autowired
	FUND_USER_PASSWORDRepository fUND_USER_PASSWORDRepository;
		
	@Autowired
	FUND_USERSRepository fUND_USERSRepository;
	
	@PostMapping("/sendotp")
	public ResponseEntity<String> sendOTP(RequestEntity<Map<String,Object>> data) throws MessagingException {
		Map<String, Object> userData=data.getBody();
		String userName=(String)userData.get("username");
		if(userName!=null){
			FUND_USERS user=fUND_USERSRepository.findByUSER_NAME(userName);
			
			if(user!=null){
				otpGeneratorService.sendOTP(user.getSVU_EMAIL().trim(), "New OTP",user);
			}
			else{
				return new ResponseEntity<>("InValid USER Name",HttpStatus.BAD_REQUEST);
			}
		}
		//emailService.sendSimpleMessage("vikas.kumardaffodil@gmail.com", "Testing", "Hi");
		return new ResponseEntity<>("OTP sent",HttpStatus.OK);
	}
	
	@PostMapping("/sendtemppass")
	public ResponseEntity<String> sendTempPass(RequestEntity<Map<String,Object>> data) throws MessagingException {
		Map<String, Object> userData=data.getBody();
		String userName=(String)userData.get("username");
		String otp=(String)userData.get("otp");
		if(userName!=null){
			FUND_USERS user=fUND_USERSRepository.findByUSER_NAME(userName);		
			if(user!=null){
				FUND_USER_PASSWORD pass=fUND_USER_PASSWORDRepository.findOptValid(otp, user.getSVC_UID());
				
		    if(pass!=null){
				if(otpGeneratorService.validOTP(pass.getFCDATE())){
					
					if(tempPassGeneratorService.sendTempPass(user))
					{
						return new ResponseEntity<>("Password Sent",HttpStatus.OK);
					}
					else{
						return new ResponseEntity<>("Password Not Sent",HttpStatus.BAD_REQUEST);	
					}
				}
				else{
					return new ResponseEntity<>("OTP has been Expired",HttpStatus.BAD_REQUEST);	
				}
			}
			else{
				return new ResponseEntity<>("InValid OTP",HttpStatus.BAD_REQUEST);
			}
			}
			else{
				return new ResponseEntity<>("InValid USER Name",HttpStatus.BAD_REQUEST);
			}
		}
		//tempPassGeneratorService.sendTempPass("vikas.kumardaffodil@gmail.com", "New Temporary Password for Reset Password");
		return new ResponseEntity<>("successfully sent",HttpStatus.OK);
	}
	
	
	
	
}
