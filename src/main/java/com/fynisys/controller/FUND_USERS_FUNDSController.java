package com.fynisys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fynisys.model.FUND_USERS;
import com.fynisys.model.FUND_USERS_FUNDS;
import com.fynisys.repository.FUND_FUNDS_DETAILRepository;
import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USERS_FUNDSRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class FUND_USERS_FUNDSController.
 */
@RestController
public class FUND_USERS_FUNDSController{

	/** The fund user repository. */
	@Autowired
	FUND_USERSRepository fund_UserRepository;
	
	/** The UN D USER S FUNDS repository. */
	@Autowired
	FUND_USERS_FUNDSRepository fUND_USERS_FUNDSRepository;
	
	/** The UN D FUND S DETAIL repository. */
	@Autowired
	FUND_FUNDS_DETAILRepository fUND_FUNDS_DETAILRepository;
		
	/**
	 * Gets the fund related user.
	 *
	 * @param requestData the request data
	 * @return the fund related user
	 */
	@PostMapping("/getfundbyuser")
	public ResponseEntity<?> getFundRelatedUser(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		String userid=(String)requestJson.get("userid");
		List<Map<String,Object>> funds=new ArrayList<Map<String,Object>>();
		FUND_USERS user=fund_UserRepository.findByUSER_NAME(userid);
		if(user!=null) {
		fUND_FUNDS_DETAILRepository.findAll().forEach(fund->{
			Map<String,Object> fundMap=new HashMap<String,Object>();
			FUND_USERS_FUNDS fuf=fUND_USERS_FUNDSRepository.getFund(fund.getFID(), user.getSVC_UID());
			if(fuf!=null) {
				fundMap.put("fundid", fuf.getFID());
				fundMap.put("fundname", fuf.getFFUNDS());
				fundMap.put("assign", "true");
			}
			else {
				fundMap.put("fundid", fund.getFID());
				fundMap.put("fundname", fund.getFFUNDS());
				fundMap.put("assign", "false");
			}
			funds.add(fundMap);
		});
		}
		else {
			Map<String, Object> json=new HashMap<String,Object>();
			json.put("msg", "UserId don't exists");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(funds,HttpStatus.OK);
	}
}
