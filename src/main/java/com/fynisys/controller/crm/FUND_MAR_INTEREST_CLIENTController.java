package com.fynisys.controller.crm;

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
import com.fynisys.activitylogs.ActivityLogger;
import com.fynisys.controller.crm.beans.InterestClient;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.clienttype.FUND_MAR_CLIENT_TYPE;
import com.fynisys.model.crm.FUND_MAR_INTEREST_CLIENT;
import com.fynisys.model.crm.RE_INVESTOR;
import com.fynisys.model.parameters.FUND_CURRENCY_MSTR;
import com.fynisys.repository.crm.FUND_MAR_INTEREST_CLIENTRepository;
import com.fynisys.repository.crm.RE_INVESTORRepository;
import com.fynisys.utilities.FundUserValidate;
import com.fynisys.utilities.LevelParameterValidator;
import com.fynisys.utilities.StockParameters;

// TODO: Auto-generated Javadoc
/**
 * The Class FUND_MAR_INTEREST_CLIENTController.
 */
@RestController
public class FUND_MAR_INTEREST_CLIENTController{
	
	/** The fund user validate. */
	@Autowired
	FundUserValidate fundUserValidate;
	
	/** The activity logger. */
	@Autowired
	ActivityLogger activityLogger;
	
	/** The UN D MA R INTERES T CLIENT repository. */
	@Autowired
	FUND_MAR_INTEREST_CLIENTRepository fUND_MAR_INTEREST_CLIENTRepository;
	
	/** The r E INVESTOR repository. */
	@Autowired
	RE_INVESTORRepository rE_INVESTORRepository;
	
	/** The stock parameters. */
	@Autowired
	StockParameters stockParameters;
	
	/** The level parameters. */
	@Autowired
	LevelParameterValidator levelParameters;
	
	/** The Constant logger. */
	private static final Logger logger=LoggerFactory.getLogger("FUND MAR INTEREST CLIENT CONTROLLER");
	
	/**
	 * Save.
	 *
	 * @param requestJson the request json
	 * @return the fund mar interest client
	 */
	public FUND_MAR_INTEREST_CLIENT save(Map<String,Object>requestJson){
		Object RI_WMS_CODE=requestJson.get("RI_WMS_CODE");
		/*
		 * Primary key
		 */
		Object FMD_SNO=requestJson.get("FMD_SNO");
		Object FID_DATE=requestJson.get("FID_DATE");
		Object FID_CURRENCY=requestJson.get("FID_CURRENCY");
		Object FID_CLIENT=requestJson.get("FID_CLIENT");
		Object WMS_COMMENTS=requestJson.get("WMS_COMMENTS");
		Object WMS_STATUS=requestJson.get("WMS_STATUS");
		Object FID_CLIENT_TYPE=requestJson.get("FID_CLIENT_TYPE");
		Object FID_LEVEL=requestJson.get("FID_LEVEL");
		Object FID_BANK=requestJson.get("FID_BANK");
		Object FID_F_SLAB=requestJson.get("FID_F_SLAB");
		Object FID_T_SLAB=requestJson.get("FID_T_SLAB");
		Object FID_DR_INT=requestJson.get("FID_DR_INT");
		Object FID_CR_INT=requestJson.get("FID_CR_INT");
		Object FID_PDR_INT=requestJson.get("FID_PDR_INT");
		Object FID_PCR_INT=requestJson.get("FID_PCR_INT");
		Object FID_UID=requestJson.get("FID_UID");
		Object FID_IU_DATE=requestJson.get("FID_IU_DATE");
		String createdby=(String)requestJson.get("createdby");
		
		if(RI_WMS_CODE==null||createdby==null)
		{	
			logger.error("Please check Input json, missing some required attributes");
			return null;
		}
		
		if(RI_WMS_CODE.toString().isEmpty()) {		
			logger.error("Please check RI_WMS_CODE is empty attribute");
			return null;
		}
		
		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{				
				logger.error("Createdby is not valid user");
				return null;
		}
		RE_INVESTOR re_inv=rE_INVESTORRepository.findOne(new Long(RI_WMS_CODE.toString()));
		if(re_inv==null) {
			logger.error("Invertor code is not valid");
			return null;
		}
		
		
		FUND_MAR_INTEREST_CLIENT ffb=new FUND_MAR_INTEREST_CLIENT();
		/*
		 * It actually search for existing and new
		 */
		if(FMD_SNO!=null) {
			if(FMD_SNO.toString().isEmpty()==false) {
				ffb.setFMD_SNO(new Long(FMD_SNO.toString()));
				ffb.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
				ffb.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
			}
			
		}else {
			ffb.setIV_ENTER_UID(fuser.getSVC_UID());
			ffb.setIV_ENTER_DATE(Calendar.getInstance());
		}
		
		
		
			if(FID_DATE!=null) {
			if(FID_DATE.toString().isEmpty()==false){
				Calendar cl=Calendar.getInstance();
				cl.setTimeInMillis(new Long(FID_DATE.toString()));
			    ffb.setFID_DATE(cl);
			 }else{
			ffb.setFID_DATE(null);
			}
			}
			if(FID_CURRENCY!=null) {
			if(FID_CURRENCY.toString().isEmpty()==false){
			ffb.setFID_CURRENCY(new Long(FID_CURRENCY.toString()));
			 }else{
			ffb.setFID_CURRENCY(null);
			}
			}
			
			if(FID_LEVEL!=null) {
				if(FID_LEVEL.toString().isEmpty()==false) {
					ffb.setFID_LEVEL(FID_LEVEL.toString());
				}else {
					ffb.setFID_LEVEL(null);
				}
			}
			
			if(FID_CLIENT!=null) {
			if(FID_CLIENT.toString().isEmpty()==false){
			ffb.setFID_CLIENT(new Long(FID_CLIENT.toString()));
			 }else{
			ffb.setFID_CLIENT(null);
			}
			}
			
			if(FID_CLIENT_TYPE!=null) {
				if(FID_CLIENT_TYPE.toString().isEmpty()==false){
				ffb.setFID_CLIENT_TYPE(new Long(FID_CLIENT_TYPE.toString()));
				 }else{
				ffb.setFID_CLIENT_TYPE(null);
				}
				}
			
			if(FID_BANK!=null) {
				if(FID_BANK.toString().isEmpty()==false) {
					ffb.setFID_BANK(FID_BANK.toString());
				}else {
					ffb.setFID_BANK(null);
				}
			}
			
			if(FID_F_SLAB!=null) {
			if(FID_F_SLAB.toString().isEmpty()==false){
			ffb.setFID_F_SLAB(new Double(FID_F_SLAB.toString()));
			 }else{
			ffb.setFID_F_SLAB(null);
			}
			}
			if(FID_T_SLAB!=null) {
			if(FID_T_SLAB.toString().isEmpty()==false){
			ffb.setFID_T_SLAB(new Double(FID_T_SLAB.toString()));
			 }else{
			ffb.setFID_T_SLAB(null);
			}
			}
			if(FID_DR_INT!=null) {
			if(FID_DR_INT.toString().isEmpty()==false){
			ffb.setFID_DR_INT(new Double(FID_DR_INT.toString()));
			 }else{
			ffb.setFID_DR_INT(null);
			}
			}
			if(FID_CR_INT!=null) {
			if(FID_CR_INT.toString().isEmpty()==false){
			ffb.setFID_CR_INT(new Double(FID_CR_INT.toString()));
			 }else{
			ffb.setFID_CR_INT(null);
			}
			}
			if(FID_PDR_INT!=null) {
			if(FID_PDR_INT.toString().isEmpty()==false){
			ffb.setFID_PDR_INT(new Double(FID_PDR_INT.toString()));
			 }else{
			ffb.setFID_PDR_INT(null);
			}
			}
			if(FID_PCR_INT!=null) {
			if(FID_PCR_INT.toString().isEmpty()==false){
			ffb.setFID_PCR_INT(new Double(FID_PCR_INT.toString()));
			 }else{
			ffb.setFID_PCR_INT(null);
			}
			}
			if(FID_UID!=null) {
			if(FID_UID.toString().isEmpty()==false){
			ffb.setFID_UID(FID_UID.toString());
			 }else{
			ffb.setFID_UID(null);
			}
			}
			if(FID_IU_DATE!=null) {
			if(FID_IU_DATE.toString().isEmpty()==false){
				Calendar cl=Calendar.getInstance();
				cl.setTimeInMillis(new Long(FID_IU_DATE.toString()));
			    ffb.setFID_IU_DATE(cl);
			 }else{
			ffb.setFID_IU_DATE(null);
			}
			}
		
			if(WMS_COMMENTS!=null) {
				if(WMS_COMMENTS.toString().isEmpty()==false) {
					ffb.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}else {
					ffb.setWMS_COMMENTS(null);
				}
			}
			
			if(WMS_STATUS!=null) {
				if(WMS_STATUS.toString().isEmpty()==false) {
					ffb.setWMS_STATUS(WMS_STATUS.toString());
				}else {
					ffb.setWMS_STATUS(null);
				}
			}
			
		if(re_inv!=null) {
		ffb.setrE_INVESTOR(re_inv);
		}
		
		try {
			ffb=fUND_MAR_INTEREST_CLIENTRepository.save(ffb);
			if(ffb!=null) {
				logger.info("Fund Inerest Document is saved");
				return ffb;
//			 	boolean loggerw=activityLogger.writeLog(fuser,SNO,SVL_SCREEN, SVL_DESC, "CREATE");
//			 	if(loggerw==true) {
//			 		logger.info("Both Record and Logs saved for Broker:"+ffb.getSCD_CLIENT());
//					json.put("logs","logs are saved");
//			 	}else {
//			 		logger.info("Record is saved but logs can't saved due error in saving of logs");
//					json.put("logs","Record is saved but logs can't saved due error in saving of logs");
//			 	}
//				InvestorBean ib=getJson(fc);
//				ib.setMsg("Investor saved");
	
			}else {
				logger.error("Fund Client Document can not saved");
				return null;
			}
			}catch (Exception e) {
			logger.error(e.getMessage());
			return null;
			}
	   }
	
	/**
	 * Save.
	 *
	 * @param requestBody the request body
	 * @return the response entity
	 */
	@PostMapping("/saveInterest")
	public ResponseEntity<?> save(RequestEntity<List<Map<String,Object>>>requestBody){
		Map<String,Object> json=new HashMap<>();
		List<Map<String,Object>> jsonData=requestBody.getBody();
		if(jsonData==null) {
			json.put("msg", "Json not found in body");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
		List<InterestClient> arrClient=new ArrayList<>();
		jsonData.forEach(Json->{
			FUND_MAR_INTEREST_CLIENT ffb=save(Json);
			if(ffb!=null) {
				InterestClient interestClient=getJson(ffb);
				arrClient.add(interestClient);
			}
		});
		return new ResponseEntity<>(arrClient,HttpStatus.OK);
	}
	
	/**
	 * Gets the interest.
	 *
	 * @return the interest
	 */
	@GetMapping("/interests")
	public ResponseEntity<?> getInterest(){
		List<InterestClient> allIn=new ArrayList<>();
		fUND_MAR_INTEREST_CLIENTRepository.findAll().forEach(conut->{
			allIn.add(getJson(conut));
		});
		return new ResponseEntity<>(allIn,HttpStatus.OK);
	}
	
	/**
	 * Gets the json.
	 *
	 * @param conut the conut
	 * @return the json
	 */
	public InterestClient getJson(FUND_MAR_INTEREST_CLIENT conut) {
		InterestClient ffb=new InterestClient();
		ffb.setFMD_SNO(conut.getFMD_SNO());
		ffb.setFID_DATE(conut.getFID_DATE());

		if(conut.getFID_CURRENCY()!=null) {
			FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getFID_CURRENCY());
			if(fcm!=null) {
				ffb.setFID_CURRENCY(conut.getFID_CURRENCY());
				ffb.setFID_CURRENCY_NAME(fcm.getSVC_NAME());
			}
		}
		
		ffb.setFID_LEVEL(conut.getFID_LEVEL());
		
		if(ffb.getFID_CLIENT()!=null) {
			RE_INVESTOR re=levelParameters.getCLIENTApproved(ffb.getFID_CLIENT());
			if(re!=null) {
				ffb.setFID_CLIENT(re.getRI_WMS_CODE());
				ffb.setFID_CLIENT_NAME(re.getRI_INVESTOR_NAME());
			}
		}
		
		if(ffb.getFID_CLIENT_TYPE()!=null) {
			FUND_MAR_CLIENT_TYPE cl=levelParameters.getCLIENT_TYPEApproved(ffb.getFID_CLIENT_TYPE());
			if(cl!=null) {
				ffb.setFID_CLIENT_TYPE(cl.getFCT_ID());
				ffb.setFID_CLIENT_TYPE_NAME(cl.getFCT_NAME());
			}
		}
		ffb.setFID_BANK(conut.getFID_BANK());
		
		ffb.setFID_CLIENT(conut.getFID_CLIENT());
		ffb.setFID_F_SLAB(conut.getFID_F_SLAB());
		ffb.setFID_T_SLAB(conut.getFID_T_SLAB());
		ffb.setFID_DR_INT(conut.getFID_DR_INT());
		ffb.setFID_CR_INT(conut.getFID_CR_INT());
		ffb.setFID_PDR_INT(conut.getFID_PDR_INT());
		ffb.setFID_PCR_INT(conut.getFID_PCR_INT());
		ffb.setFID_UID(conut.getFID_UID());
		ffb.setFID_IU_DATE(conut.getFID_IU_DATE());
		ffb.setWMS_COMMENTS(conut.getWMS_COMMENTS());
		ffb.setWMS_STATUS(conut.getWMS_STATUS());
		FUND_USERS user=null;
		if(conut.getIV_ENTER_UID()!=null) {
			user=fundUserValidate.getUserName(conut.getIV_ENTER_UID());
			if(user!=null) {
			ffb.setEnteredby( user.getSVU_NAME());
			ffb.setEnteredbyuserid( user.getSVU_USER_NAME());
			ffb.setEnteredbyuuid( user.getSVC_UID());
			ffb.setEntereddate(conut.getIV_ENTER_DATE());
			}
			else {
				ffb.setEnteredby( null);
				ffb.setEnteredbyuserid( null);
				ffb.setEnteredbyuuid( null);
				ffb.setEntereddate( null);
			}
		}else {
			ffb.setEnteredby( null);
			ffb.setEnteredbyuserid( null);
			ffb.setEnteredbyuuid( null);
			ffb.setEntereddate( null);
		}
		
		if(conut.getIV_APPROVE_UID()!=null) {
			user=fundUserValidate.getUserName(conut.getIV_APPROVE_UID());
			if(user!=null) {
			ffb.setApprovedby( user.getSVU_NAME());
			ffb.setApprovedbyuserid( user.getSVU_USER_NAME());
			ffb.setApprovedbyuuid( user.getSVC_UID());
			ffb.setApproveddate( conut.getIV_APPROVE_DATE());
			}
			else {
				ffb.setApprovedby( null);
				ffb.setApprovedbyuserid( null);
				ffb.setApprovedbyuuid( null);
				ffb.setApproveddate( null);
			}
		}else {
			ffb.setApprovedby( null);
			ffb.setApprovedbyuserid( null);
			ffb.setApprovedbyuuid( null);
			ffb.setApproveddate( null);
		}
		
		if(conut.getIV_LAST_UPDATE_UID()!=null) {
			user=fundUserValidate.getUserName(conut.getIV_LAST_UPDATE_UID());
			if(user!=null) {
			ffb.setModifiedby( user.getSVU_NAME());
			ffb.setModifiedbyuserid( user.getSVU_USER_NAME());
			ffb.setModifiedbyuuid( user.getSVC_UID());
			ffb.setModifieddate( conut.getIV_LAST_UPDATE_DATE());
			}
			else {
				ffb.setModifiedby( null);
				ffb.setModifiedbyuserid( null);
				ffb.setModifiedbyuuid( null);
				ffb.setModifieddate( null);
			}
		}else {
			ffb.setModifiedby( null);
			ffb.setModifiedbyuserid( null);
			ffb.setModifiedbyuuid( null);
			ffb.setModifieddate( null);
		}
		return ffb;
	}
}