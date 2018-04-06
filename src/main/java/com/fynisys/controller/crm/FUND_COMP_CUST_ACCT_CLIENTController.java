package com.fynisys.controller.crm;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.fynisys.controller.crm.beans.CommisonDocument;
import com.fynisys.model.FUND_COUNTRIES;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.crm.FUND_COMP_CUST_ACCT_CLIENT;
import com.fynisys.model.crm.RE_INVESTOR;
import com.fynisys.repository.crm.FUND_COMP_CUST_ACCT_CLIENTRepository;
import com.fynisys.repository.crm.RE_INVESTORRepository;
import com.fynisys.utilities.FundUserValidate;
import com.fynisys.utilities.StockParameters;

// TODO: Auto-generated Javadoc
/**
 * The Class FUND_COMP_CUST_ACCT_CLIENTController.
 */
@RestController
public class FUND_COMP_CUST_ACCT_CLIENTController{
	
	/** The fund user validate. */
	@Autowired
	FundUserValidate fundUserValidate;
	
	/** The activity logger. */
	@Autowired
	ActivityLogger activityLogger;
	
	/** The UN D COM P CUS T ACC T CLIENT repository. */
	@Autowired
	FUND_COMP_CUST_ACCT_CLIENTRepository fUND_COMP_CUST_ACCT_CLIENTRepository;
	
	/** The r E INVESTOR repository. */
	@Autowired
	RE_INVESTORRepository rE_INVESTORRepository;
	
	/** The stock parameters. */
	@Autowired
	StockParameters stockParameters;
	
	/** The Constant logger. */
	private static final Logger logger=LoggerFactory.getLogger("FUND COMP CUST ACCT CLIENT CONTROLLER");
	
	/**
	 * Save.
	 *
	 * @param requestJson the request json
	 * @return the fund comp cust acct client
	 */
	public FUND_COMP_CUST_ACCT_CLIENT save(Map<String,Object>requestJson){
		Object RI_WMS_CODE=requestJson.get("RI_WMS_CODE");
		
		/*
		 * Primary key
		 */
		Object SAC_FUND_CODE=requestJson.get("SAC_FUND_CODE");
		Object SAC_CLIENT_NO=requestJson.get("SAC_CLIENT_NO");
		Object SAC_SECURITY_CLASS=requestJson.get("SAC_SECURITY_CLASS");
		Object SAC_SAFE_RATE=requestJson.get("SAC_SAFE_RATE");
		Object SAC_SAFE_MINIMUM=requestJson.get("SAC_SAFE_MINIMUM");
		Object SAC_SAFE_PERIOD=requestJson.get("SAC_SAFE_PERIOD");
		Object SAC_BLOCKING=requestJson.get("SAC_BLOCKING");
		Object SAC_UNBLOCKING=requestJson.get("SAC_UNBLOCKING");
		Object SAC_OTHER_CHARGES=requestJson.get("SAC_OTHER_CHARGES");
		Object SAC_OTHER_REMARKS=requestJson.get("SAC_OTHER_REMARKS");
		Object SAC_OTHER_CHARGES1=requestJson.get("SAC_OTHER_CHARGES1");
		Object SAC_OTHER_REMARKS1=requestJson.get("SAC_OTHER_REMARKS1");
		Object SAC_REBATE_CHARGES=requestJson.get("SAC_REBATE_CHARGES");
		Object SAC_ANNUAL_CHAREGES=requestJson.get("SAC_ANNUAL_CHAREGES");
		Object SAC_COUNTRY=requestJson.get("SAC_COUNTRY");
		Object SAC_CURR=requestJson.get("SAC_CURR");
		Object SAC_SAFE_DIVISOR_DAYS=requestJson.get("SAC_SAFE_DIVISOR_DAYS");
		Object SAC_SAFE_MONTH_DAYS=requestJson.get("SAC_SAFE_MONTH_DAYS");
		Object SAC_REBATE_REMARKS4=requestJson.get("SAC_REBATE_REMARKS4");
		Object SAC_TRAN_OTHER_FLAT_RATE=requestJson.get("SAC_TRAN_OTHER_FLAT_RATE");
		Object SAC_BROKERAGE_FEE=requestJson.get("SAC_BROKERAGE_FEE");
		Object SAC_BROKERAGE_MIN=requestJson.get("SAC_BROKERAGE_MIN");
		Object SAC_TRAN_OTHER_FLAT_RATE2=requestJson.get("SAC_TRAN_OTHER_FLAT_RATE2");
		Object FMD_C_TYPE=requestJson.get("FMD_C_TYPE");
		Object SVC_EDATE=requestJson.get("SVC_EDATE");
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
		
		
		FUND_COMP_CUST_ACCT_CLIENT ffb=new FUND_COMP_CUST_ACCT_CLIENT();
		/*
		 * It actually search for existing and new
		 */
		if(SAC_FUND_CODE!=null) {
			if(SAC_FUND_CODE.toString().isEmpty()==false) {
				ffb.setSAC_FUND_CODE(new Long(SAC_FUND_CODE.toString()));
				ffb.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
				ffb.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
			}
			
		}else {
			ffb.setIV_ENTER_UID(fuser.getSVC_UID());
			ffb.setIV_ENTER_DATE(Calendar.getInstance());
		}
		
			if(SAC_CLIENT_NO!=null) {
			if(SAC_CLIENT_NO.toString().isEmpty()==false){
			ffb.setSAC_CLIENT_NO(new Long(SAC_CLIENT_NO.toString()));
			}
			 else{
			ffb.setSAC_CLIENT_NO(null);
			}
			}
			if(SAC_SECURITY_CLASS!=null) {
			if(SAC_SECURITY_CLASS.toString().isEmpty()==false){
			ffb.setSAC_SECURITY_CLASS(SAC_SECURITY_CLASS.toString());
			}
			 else{
			ffb.setSAC_SECURITY_CLASS(null);
			}
			}
			if(SAC_SAFE_RATE!=null) {
			if(SAC_SAFE_RATE.toString().isEmpty()==false){
			ffb.setSAC_SAFE_RATE(new Double(SAC_SAFE_RATE.toString()));
			}
			 else{
			ffb.setSAC_SAFE_RATE(null);
			}
			}
			if(SAC_SAFE_MINIMUM!=null) {
			if(SAC_SAFE_MINIMUM.toString().isEmpty()==false){
			ffb.setSAC_SAFE_MINIMUM(new Double(SAC_SAFE_MINIMUM.toString()));
			}
			 else{
			ffb.setSAC_SAFE_MINIMUM(null);
			}
			}
			if(SAC_SAFE_PERIOD!=null) {
			if(SAC_SAFE_PERIOD.toString().isEmpty()==false){
			ffb.setSAC_SAFE_PERIOD(new Long(SAC_SAFE_PERIOD.toString()));
			}
			 else{
			ffb.setSAC_SAFE_PERIOD(null);
			}
			}
			if(SAC_BLOCKING!=null) {
			if(SAC_BLOCKING.toString().isEmpty()==false){
			ffb.setSAC_BLOCKING(new Double(SAC_BLOCKING.toString()));
			}
			 else{
			ffb.setSAC_BLOCKING(null);
			}
			}
			if(SAC_UNBLOCKING!=null) {
			if(SAC_UNBLOCKING.toString().isEmpty()==false){
			ffb.setSAC_UNBLOCKING(new Double(SAC_UNBLOCKING.toString()));
			}
			 else{
			ffb.setSAC_UNBLOCKING(null);
			}
			}
			if(SAC_OTHER_CHARGES!=null) {
			if(SAC_OTHER_CHARGES.toString().isEmpty()==false){
			ffb.setSAC_OTHER_CHARGES(new Double(SAC_OTHER_CHARGES.toString()));
			}
			 else{
			ffb.setSAC_OTHER_CHARGES(null);
			}
			}
			if(SAC_OTHER_REMARKS!=null) {
			if(SAC_OTHER_REMARKS.toString().isEmpty()==false){
			ffb.setSAC_OTHER_REMARKS(SAC_OTHER_REMARKS.toString());
			}
			 else{
			ffb.setSAC_OTHER_REMARKS(null);
			}
			}
			if(SAC_OTHER_CHARGES1!=null) {
			if(SAC_OTHER_CHARGES1.toString().isEmpty()==false){
			ffb.setSAC_OTHER_CHARGES1(new Double(SAC_OTHER_CHARGES1.toString()));
			}
			 else{
			ffb.setSAC_OTHER_CHARGES1(null);
			}
			}
			if(SAC_OTHER_REMARKS1!=null) {
			if(SAC_OTHER_REMARKS1.toString().isEmpty()==false){
			ffb.setSAC_OTHER_REMARKS1(SAC_OTHER_REMARKS1.toString());
			}
			 else{
			ffb.setSAC_OTHER_REMARKS1(null);
			}
			}
			if(SAC_REBATE_CHARGES!=null) {
			if(SAC_REBATE_CHARGES.toString().isEmpty()==false){
			ffb.setSAC_REBATE_CHARGES(new Double(SAC_REBATE_CHARGES.toString()));
			}
			 else{
			ffb.setSAC_REBATE_CHARGES(null);
			}
			}
			if(SAC_ANNUAL_CHAREGES!=null) {
			if(SAC_ANNUAL_CHAREGES.toString().isEmpty()==false){
			ffb.setSAC_ANNUAL_CHAREGES(new Double(SAC_ANNUAL_CHAREGES.toString()));
			}
			 else{
			ffb.setSAC_ANNUAL_CHAREGES(null);
			}
			}
			if(SAC_COUNTRY!=null) {
			if(SAC_COUNTRY.toString().isEmpty()==false){
			ffb.setSAC_COUNTRY(SAC_COUNTRY.toString());
			}
			 else{
			ffb.setSAC_COUNTRY(null);
			}
			}
			if(SAC_CURR!=null) {
			if(SAC_CURR.toString().isEmpty()==false){
			ffb.setSAC_CURR(SAC_CURR.toString());
			}
			 else{
			ffb.setSAC_CURR(null);
			}
			}
			if(SAC_SAFE_DIVISOR_DAYS!=null) {
			if(SAC_SAFE_DIVISOR_DAYS.toString().isEmpty()==false){
			ffb.setSAC_SAFE_DIVISOR_DAYS(SAC_SAFE_DIVISOR_DAYS.toString());
			}
			 else{
			ffb.setSAC_SAFE_DIVISOR_DAYS(null);
			}
			}
			if(SAC_SAFE_MONTH_DAYS!=null) {
			if(SAC_SAFE_MONTH_DAYS.toString().isEmpty()==false){
			ffb.setSAC_SAFE_MONTH_DAYS(SAC_SAFE_MONTH_DAYS.toString());
			}
			 else{
			ffb.setSAC_SAFE_MONTH_DAYS(null);
			}
			}
			if(SAC_REBATE_REMARKS4!=null) {
			if(SAC_REBATE_REMARKS4.toString().isEmpty()==false){
			ffb.setSAC_REBATE_REMARKS4(SAC_REBATE_REMARKS4.toString());
			}
			 else{
			ffb.setSAC_REBATE_REMARKS4(null);
			}
			}
			if(SAC_TRAN_OTHER_FLAT_RATE!=null) {
			if(SAC_TRAN_OTHER_FLAT_RATE.toString().isEmpty()==false){
			ffb.setSAC_TRAN_OTHER_FLAT_RATE(new Double(SAC_TRAN_OTHER_FLAT_RATE.toString()));
			}
			 else{
			ffb.setSAC_TRAN_OTHER_FLAT_RATE(null);
			}
			}
			if(SAC_BROKERAGE_FEE!=null) {
			if(SAC_BROKERAGE_FEE.toString().isEmpty()==false){
			ffb.setSAC_BROKERAGE_FEE(new Double(SAC_BROKERAGE_FEE.toString()));
			}
			 else{
			ffb.setSAC_BROKERAGE_FEE(null);
			}
			}
			if(SAC_BROKERAGE_MIN!=null) {
			if(SAC_BROKERAGE_MIN.toString().isEmpty()==false){
			ffb.setSAC_BROKERAGE_MIN(new Double(SAC_BROKERAGE_MIN.toString()));
			}
			 else{
			ffb.setSAC_BROKERAGE_MIN(null);
			}
			}
			if(SAC_TRAN_OTHER_FLAT_RATE2!=null) {
			if(SAC_TRAN_OTHER_FLAT_RATE2.toString().isEmpty()==false){
			ffb.setSAC_TRAN_OTHER_FLAT_RATE2(new Double(SAC_TRAN_OTHER_FLAT_RATE2.toString()));
			}
			 else{
			ffb.setSAC_TRAN_OTHER_FLAT_RATE2(null);
			}
			}
			if(FMD_C_TYPE!=null) {
			if(FMD_C_TYPE.toString().isEmpty()==false){
			ffb.setFMD_C_TYPE(new Long(FMD_C_TYPE.toString()));
			}
			 else{
			ffb.setFMD_C_TYPE(null);
			}
			}
			if(SVC_EDATE!=null) {
			if(SVC_EDATE.toString().isEmpty()==false){
			Calendar cl=Calendar.getInstance();
			cl.setTimeInMillis(new Long(SVC_EDATE.toString()));
			ffb.setSVC_EDATE(cl);
			}
			 else{
			ffb.setSVC_EDATE(null);
			}
			}

		
		if(re_inv!=null) {
		ffb.setrE_INVESTOR(re_inv);
		}
		
		try {
			ffb=fUND_COMP_CUST_ACCT_CLIENTRepository.save(ffb);
			if(ffb!=null) {
				logger.info("Fund Commission Document is saved");
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
	@PostMapping("/saveCommissionDoc")
	public ResponseEntity<?> save(RequestEntity<List<Map<String,Object>>>requestBody){
		List<Map<String,Object>> jsonData=requestBody.getBody();
		List<CommisonDocument> arrClient=new ArrayList<>();
		jsonData.forEach(Json->{
			FUND_COMP_CUST_ACCT_CLIENT ffb=save(Json);
			if(ffb!=null) {
				CommisonDocument commisonDocument=getJson(ffb);
				arrClient.add(commisonDocument);
			}
		});
		return new ResponseEntity<>(arrClient,HttpStatus.OK);
	}
	
	/**
	 * Gets the commsion rate.
	 *
	 * @return the commsion rate
	 */
	@GetMapping("/commisionRate")
	public ResponseEntity<?> getCommsionRate(){
		List<CommisonDocument> arrClient=new ArrayList<>();
		fUND_COMP_CUST_ACCT_CLIENTRepository.findAll().forEach(conut->{
			arrClient.add(getJson(conut));
		});
		return new ResponseEntity<>(arrClient,HttpStatus.OK);
	}
	
	/**
	 * Gets the json.
	 *
	 * @param conut the conut
	 * @return the json
	 */
	public CommisonDocument getJson(FUND_COMP_CUST_ACCT_CLIENT conut) {
		CommisonDocument ffb=new CommisonDocument();
		ffb.setSAC_FUND_CODE(conut.getSAC_FUND_CODE());
		ffb.setSAC_CLIENT_NO(conut.getSAC_CLIENT_NO());
		ffb.setSAC_SECURITY_CLASS(conut.getSAC_SECURITY_CLASS());
		ffb.setSAC_SAFE_RATE(conut.getSAC_SAFE_RATE());
		ffb.setSAC_SAFE_MINIMUM(conut.getSAC_SAFE_MINIMUM());
		ffb.setSAC_SAFE_PERIOD(conut.getSAC_SAFE_PERIOD());
		ffb.setSAC_BLOCKING(conut.getSAC_BLOCKING());
		ffb.setSAC_UNBLOCKING(conut.getSAC_UNBLOCKING());
		ffb.setSAC_OTHER_CHARGES(conut.getSAC_OTHER_CHARGES());
		ffb.setSAC_OTHER_REMARKS(conut.getSAC_OTHER_REMARKS());
		ffb.setSAC_OTHER_CHARGES1(conut.getSAC_OTHER_CHARGES1());
		ffb.setSAC_OTHER_REMARKS1(conut.getSAC_OTHER_REMARKS1());
		ffb.setSAC_REBATE_CHARGES(conut.getSAC_REBATE_CHARGES());
		ffb.setSAC_ANNUAL_CHAREGES(conut.getSAC_ANNUAL_CHAREGES());
		if(conut.getSAC_COUNTRY()!=null) {
			 FUND_COUNTRIES cust=stockParameters.getFUND_COUNTRIES(conut.getSAC_COUNTRY());
			 if(cust!=null) {
				 ffb.setSAC_COUNTRY(conut.getSAC_COUNTRY());
				 ffb.setSAC_COUNTRY_NAME(cust.getSVC_NAME());		 
			 }
		 }
		ffb.setSAC_CURR(conut.getSAC_CURR());
		ffb.setSAC_SAFE_DIVISOR_DAYS(conut.getSAC_SAFE_DIVISOR_DAYS());
		ffb.setSAC_SAFE_MONTH_DAYS(conut.getSAC_SAFE_MONTH_DAYS());
		ffb.setSAC_REBATE_REMARKS4(conut.getSAC_REBATE_REMARKS4());
		ffb.setSAC_TRAN_OTHER_FLAT_RATE(conut.getSAC_TRAN_OTHER_FLAT_RATE());
		ffb.setSAC_BROKERAGE_FEE(conut.getSAC_BROKERAGE_FEE());
		ffb.setSAC_BROKERAGE_MIN(conut.getSAC_BROKERAGE_MIN());
		ffb.setSAC_TRAN_OTHER_FLAT_RATE2(conut.getSAC_TRAN_OTHER_FLAT_RATE2());
		ffb.setFMD_C_TYPE(conut.getFMD_C_TYPE());
		ffb.setSVC_EDATE(conut.getSVC_EDATE());
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