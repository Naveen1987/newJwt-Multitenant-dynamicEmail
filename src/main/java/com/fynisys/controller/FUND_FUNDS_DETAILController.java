package com.fynisys.controller;

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

import com.fynisys.model.FUND_FUNDS_DETAIL;
import com.fynisys.model.FUND_USERS;
import com.fynisys.repository.FUND_FUNDS_DETAILRepository;
import com.fynisys.repository.FUND_USERSRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class FUND_FUNDS_DETAILController.
 */
@RestController
public class FUND_FUNDS_DETAILController {

	
	/** The UN D FUND S DETAIL repository. */
	@Autowired
	FUND_FUNDS_DETAILRepository fUND_FUNDS_DETAILRepository;
	
	/** The UN D USERS repository. */
	@Autowired
	FUND_USERSRepository fUND_USERSRepository ; 
	
	/**
	 * Save fund.
	 *
	 * @param requestBody the request body
	 * @return the response entity
	 */
	@PostMapping("/savefund")
	public ResponseEntity<Map<String, Object>> saveFund(RequestEntity<Map<String, Object>> requestBody){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> fund=requestBody.getBody();
		Object fund_name=fund.get("fundname");
		Object fund_details=fund.get("funddetail");
		Object createdby=fund.get("createdby");
		if(fund_name==null||fund_details==null||createdby==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		else {
			FUND_USERS fund_user=isValidUser(createdby.toString());
			if(fund_user==null) {
				json.put("msg", "Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
			FUND_FUNDS_DETAIL funds=new FUND_FUNDS_DETAIL();
			funds.setFFUNDS(fund_name.toString());
			funds.setFDETAILS(fund_details.toString());
			funds.setFCREATEDBY(fund_user.getSVC_UID());
			funds.setFDATE(Calendar.getInstance());
			funds.setFMODIFIEDBY(fund_user.getSVC_UID());
			funds.setFLAST_CHANGE(Calendar.getInstance());
			try {
			
			funds=fUND_FUNDS_DETAILRepository.save(funds);
			if(funds!=null) {
				json.put("msg","Saved");
				json.put("fundid", funds.getFID());
				json.put("fundname", funds.getFFUNDS());
				json.put("funddetail", funds.getFDETAILS());
				json.put("enteredby", funds.getFCREATEDBY());
				json.put("createdon", funds.getFDATE().getTimeInMillis());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
			}
			else {
				json.put("msg","Not saved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
		}
		
		
	}
	
	/**
	 * Save funds.
	 *
	 * @param data the data
	 * @return the response entity
	 */
	@PostMapping("/savefunds")
	public ResponseEntity<Map<String, Object>> saveFunds(RequestEntity<Map<String, Object>> data){
		Map<String,Object> json=new HashMap<String,Object>();
		
		
		return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
		
	}
		
	/**
	 * Gets the funds.
	 *
	 * @return the funds
	 */
	@GetMapping("/getfunds")
	public ResponseEntity<List<Map<String, Object>>> getFunds(){
		List<Map<String,Object>> json=new ArrayList<Map<String,Object>>();
		fUND_FUNDS_DETAILRepository.findAll().forEach(funds->{
			Map<String, Object> fjson=new HashMap<String,Object>();
			fjson.put("fundid", funds.getFID());
			fjson.put("fundname", funds.getFFUNDS());
			fjson.put("assign", "false");
			//fjson.put("funddetail", funds.getFDETAILS());
			//fjson.put("enteredby", funds.getFCREATEDBY());
			//fjson.put("createdon", funds.getFDATE().getTimeInMillis());
			json.add(fjson);
		});		
		return new ResponseEntity<List<Map<String, Object>>>(json,HttpStatus.OK);
		
	}
	
	/**
	 * Update funds.
	 *
	 * @param requestBody the request body
	 * @return the response entity
	 */
	@PostMapping("/updatefunds")
	public ResponseEntity<Map<String, Object>> updateFunds(RequestEntity<Map<String, Object>> requestBody){
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String,Object> fund=requestBody.getBody();
		Object fund_name=fund.get("fundname");
		Object fund_id=fund.get("fundid");
		Object fund_details=fund.get("funddetail");
		Object modifiedby=fund.get("modifiedby");
		if(fund_id==null||modifiedby==null) {
			json.put("msg", "Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		else {
			FUND_USERS fund_user=isValidUser(modifiedby.toString());
			if(fund_user==null) {
				json.put("msg", "Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			FUND_FUNDS_DETAIL funds=fUND_FUNDS_DETAILRepository.findOne(fund_id.toString());
			if(fund_name!=null) {
			funds.setFFUNDS(fund_name.toString());
			}
			if(fund_details!=null) {
			funds.setFDETAILS(fund_details.toString());
			}
			funds.setFMODIFIEDBY(fund_user.getSVC_UID());
			funds.setFLAST_CHANGE(Calendar.getInstance());
			try {
			funds=fUND_FUNDS_DETAILRepository.save(funds);
			if(funds!=null) {
				json.put("msg","Updated Saved");
				json.put("fundid", funds.getFID());
				json.put("fundname", funds.getFFUNDS());
				json.put("funddetail", funds.getFDETAILS());
				json.put("enteredby", funds.getFCREATEDBY());
				json.put("createdon", funds.getFDATE().getTimeInMillis());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
			}
			else {
				json.put("msg","Not saved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
			
		}	
	}
	
	/**
	 * Testing for created by and modified by.
	 *
	 * @param userid the userid
	 * @return the fund users
	 */
	public FUND_USERS isValidUser(String userid) {
		FUND_USERS fund_user=fUND_USERSRepository.findByUSER_NAME(userid);
		if(fund_user!=null) {
			return fund_user;
		}
		return null;
	}
}
