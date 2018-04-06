package com.fynisys.menutask.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.fynisys.menutask.repository.FUND_MENU_GROUPRepository;
import com.fynisys.menutask.repository.FUND_MENU_TASKRepository;
import com.fynisys.menutask.repository.FUND_ROLESRepository;
import com.fynisys.menutask.repository.FUND_ROLE_SUB_TASKRepository;
import com.fynisys.menutask.repository.FUND_ROLE_TASKRepository;
import com.fynisys.repository.FUND_USERSRepository;


@RestController
public class FUND_ROLE_SUB_TASKController {

	@Autowired
	FUND_USERSRepository fUND_USERSRepository; 
	
	@Autowired
	FUND_MENU_GROUPRepository fUND_MENU_GROUPRepository; 
	
	@Autowired
	FUND_MENU_TASKRepository fUND_MENU_TASKRepository;  
	
	@Autowired
	FUND_ROLE_TASKRepository fUND_ROLE_TASKRepository;
	
	@Autowired
	FUND_ROLESRepository fUND_ROLESRepository;
	
	@Autowired
	FUND_ROLE_SUB_TASKRepository fUND_ROLE_SUB_TASKRepository;
	
	private final Logger log=LoggerFactory.getLogger("FUND ROLE SUB TASK");
	
}
