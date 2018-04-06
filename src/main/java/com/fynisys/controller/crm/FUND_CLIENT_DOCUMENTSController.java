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
import com.fynisys.controller.crm.beans.ClientDocuments;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.clienttype.FUND_CLIENT_DOCUMENTS_TYPE2;
import com.fynisys.model.clienttype.FUND_MAR_CLIENT_TYPE;
import com.fynisys.model.crm.FUND_CLIENT_DOCUMENTS;
import com.fynisys.model.crm.RE_INVESTOR;
import com.fynisys.repository.clienttype.FUND_CLIENT_DOCUMENTS_TYPE2Repository;
import com.fynisys.repository.clienttype.FUND_MAR_CLIENT_TYPERespository;
import com.fynisys.repository.crm.FUND_CLIENT_DOCUMENTSRepository;
import com.fynisys.repository.crm.RE_INVESTORRepository;
import com.fynisys.utilities.FundUserValidate;



// TODO: Auto-generated Javadoc
/**
 * The Class FUND_CLIENT_DOCUMENTSController.
 */
@RestController
public class FUND_CLIENT_DOCUMENTSController {
	
	/** The fund user validate. */
	@Autowired
	FundUserValidate fundUserValidate;
	
	/** The activity logger. */
	@Autowired
	ActivityLogger activityLogger;
	
	/** The UN D CLIEN T DOCUMENTS repository. */
	@Autowired
	FUND_CLIENT_DOCUMENTSRepository fUND_CLIENT_DOCUMENTSRepository;
	
	/** The r E INVESTOR repository. */
	@Autowired
	RE_INVESTORRepository rE_INVESTORRepository;
	
	/** The UN D CLIEN T DOCUMENT S TYPE 2 repository. */
	@Autowired
	FUND_CLIENT_DOCUMENTS_TYPE2Repository fUND_CLIENT_DOCUMENTS_TYPE2Repository;
	
	/** The UN D MA R CLIEN T TYPE respository. */
	@Autowired
	FUND_MAR_CLIENT_TYPERespository fUND_MAR_CLIENT_TYPERespository;
	
	/** The Constant logger. */
	private static final Logger logger=LoggerFactory.getLogger("FUND CLIENT DOCUMENTS CONTROLLER");
	
	/**
	 * Save.
	 *
	 * @param requestJson the request json
	 * @return the fund client documents
	 */
	public FUND_CLIENT_DOCUMENTS save(Map<String,Object>requestJson){
		Object RI_WMS_CODE=requestJson.get("RI_WMS_CODE");
		Object SCD_EFFECT_DATE=requestJson.get("SCD_EFFECT_DATE");
		/*
		 * Primary key
		 */
		Object SCD_CLIENT=requestJson.get("SCD_CLIENT");
		Object SCD_TYPE=requestJson.get("SCD_TYPE");
		Object SCD_NAME=requestJson.get("SCD_NAME");
		Object SCD_DOC_TYPE=requestJson.get("SCD_DOC_TYPE");
		Object SCD_DOC_IDATE=requestJson.get("SCD_DOC_IDATE");
		Object SCD_DOC_EDATE=requestJson.get("SCD_DOC_EDATE");
		Object SCD_DOC_IDNO=requestJson.get("SCD_DOC_IDNO");
		Object SCD_STATUS=requestJson.get("SCD_STATUS");
		Object SCD_UAE_RED_EDATE=requestJson.get("SCD_UAE_RED_EDATE");
		Object CRM_FUND_NO=requestJson.get("CRM_FUND_NO");
		Object CRM_FUND_REF_NO=requestJson.get("CRM_FUND_REF_NO");
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
		
		
		FUND_CLIENT_DOCUMENTS ffb=new FUND_CLIENT_DOCUMENTS();
		/*
		 * It actually search for existing and new
		 */
		if(SCD_CLIENT!=null) {
			if(SCD_CLIENT.toString().isEmpty()==false) {
				ffb.setSCD_CLIENT(new Long(SCD_CLIENT.toString()));
				ffb.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
				ffb.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
			}
			
		}else {
			ffb.setIV_ENTER_UID(fuser.getSVC_UID());
			ffb.setIV_ENTER_DATE(Calendar.getInstance());
		}
		
		
		if(SCD_EFFECT_DATE!=null) {
			if(SCD_EFFECT_DATE.toString().isEmpty()==false) {
				Calendar cl=Calendar.getInstance();
				cl.setTimeInMillis(new Long(SCD_EFFECT_DATE.toString()));
				ffb.setSCD_EFFECT_DATE(cl);
			}else {
				ffb.setSCD_EFFECT_DATE(null);
			}
		}
		
		
		if(SCD_UAE_RED_EDATE!=null) {
			if(SCD_UAE_RED_EDATE.toString().isEmpty()==false) {
				Calendar cl=Calendar.getInstance();
				cl.setTimeInMillis(new Long(SCD_UAE_RED_EDATE.toString()));
				ffb.setSCD_UAE_RED_EDATE(cl);
			}else {
				ffb.setSCD_UAE_RED_EDATE(null);
			}
		}
		
	
		if(CRM_FUND_REF_NO!=null) {
				ffb.setCRM_FUND_REF_NO(CRM_FUND_REF_NO.toString());
		}
		
		if(CRM_FUND_NO!=null) {
			if(CRM_FUND_NO.toString().isEmpty()==false) {
				ffb.setCRM_FUND_NO(new Long(CRM_FUND_NO.toString()));
			}else {
				ffb.setCRM_FUND_NO(null);
			}
			
		}
		if(SCD_TYPE!=null) {
			if(SCD_TYPE.toString().isEmpty()==false) {
				ffb.setSCD_TYPE(new Long(SCD_TYPE.toString()));
			}else {
				ffb.setSCD_TYPE(null);
			}
			
		}
		
		if(SCD_NAME!=null) {
			ffb.setSCD_NAME(SCD_NAME.toString());
		}
		
		if(SCD_DOC_TYPE!=null) {
			if(SCD_DOC_TYPE.toString().isEmpty()==false) {
				ffb.setSCD_DOC_TYPE(new Long(SCD_DOC_TYPE.toString()));
			}else {
				ffb.setSCD_DOC_TYPE(null);
			}
			
		}
		if(SCD_DOC_IDATE!=null) {
			if(SCD_DOC_IDATE.toString().isEmpty()==false) {
				Calendar cl=Calendar.getInstance();
				cl.setTimeInMillis(new Long(SCD_DOC_IDATE.toString()));
				ffb.setSCD_DOC_IDATE(cl);
			}else {
				ffb.setSCD_DOC_IDATE(null);
			}
		}
		
		if(SCD_DOC_EDATE!=null) {
			if(SCD_DOC_EDATE.toString().isEmpty()==false) {
				Calendar cl=Calendar.getInstance();
				cl.setTimeInMillis(new Long(SCD_DOC_EDATE.toString()));
				ffb.setSCD_DOC_EDATE(cl);
			}else {
				ffb.setSCD_DOC_EDATE(null);
			}
		}
		if(SCD_DOC_IDNO!=null) {
			ffb.setSCD_DOC_IDNO(SCD_DOC_IDNO.toString());
			}
		
		if(SCD_STATUS!=null) {
			ffb.setSCD_STATUS(SCD_STATUS.toString());
			}
		
		if(re_inv!=null) {
		ffb.setrE_INVESTOR(re_inv);
		}
		
		try {
			ffb=fUND_CLIENT_DOCUMENTSRepository.save(ffb);
			if(ffb!=null) {
				logger.info("Fund Client Document is saved");
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
	@PostMapping("/saveClientDoc")
	public ResponseEntity<?> save(RequestEntity<List<Map<String,Object>>>requestBody){
		List<Map<String,Object>> jsonData=requestBody.getBody();
		List<ClientDocuments> arrClient=new ArrayList<>();
		jsonData.forEach(Json->{
			FUND_CLIENT_DOCUMENTS ffb=save(Json);
			if(ffb!=null) {
				ClientDocuments clientDocuments=getJson(ffb);
				arrClient.add(clientDocuments);
			}
		});
		return new ResponseEntity<>(arrClient,HttpStatus.OK);
	}
	
	/**
	 * Gets the client doc.
	 *
	 * @return the client doc
	 */
	@GetMapping("/docs")
	public ResponseEntity<?> getClientDoc(){
		List<ClientDocuments> arrClient=new ArrayList<>();
		fUND_CLIENT_DOCUMENTSRepository.findAll().forEach(conut->{
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
	public ClientDocuments getJson(FUND_CLIENT_DOCUMENTS conut) {
		ClientDocuments ffb=new ClientDocuments();
		ffb.setSCD_EFFECT_DATE(conut.getSCD_EFFECT_DATE());
		ffb.setSCD_CLIENT(conut.getSCD_CLIENT());
		/*
		 * Client Type
		 */
		
		if(conut.getSCD_TYPE()!=null) {
			FUND_MAR_CLIENT_TYPE fmct=fUND_MAR_CLIENT_TYPERespository.findOne(conut.getSCD_TYPE());
			if(fmct!=null) {
				ffb.setSCD_TYPE(conut.getSCD_TYPE());
				ffb.setSCD_TYPE_NAME(fmct.getFCT_NAME());
			}
		}
		ffb.setSCD_NAME(conut.getSCD_NAME());
		/*
		 * Doc type
		 */

		if(conut.getSCD_DOC_TYPE()!=null) {
			FUND_CLIENT_DOCUMENTS_TYPE2 fcdt=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findOne(conut.getSCD_DOC_TYPE());
			if(fcdt!=null) {
				ffb.setSCD_DOC_TYPE(conut.getSCD_DOC_TYPE());
				ffb.setSCD_DOC_TYPE_NAME(fcdt.getSCD_DESC());
			}
		}
		
		ffb.setSCD_DOC_IDATE(conut.getSCD_DOC_IDATE());
		ffb.setSCD_DOC_EDATE(conut.getSCD_DOC_EDATE());
		ffb.setSCD_DOC_IDNO(conut.getSCD_DOC_IDNO());
		ffb.setSCD_STATUS(conut.getSCD_STATUS());
		ffb.setSCD_COMMENTS(conut.getSCD_COMMENTS());
		ffb.setSCD_UAE_RED_EDATE(conut.getSCD_UAE_RED_EDATE());
		ffb.setCRM_FUND_NO(conut.getCRM_FUND_NO());
		ffb.setCRM_FUND_REF_NO(conut.getCRM_FUND_REF_NO());
		ffb.setRI_WMS_CODE(conut.getrE_INVESTOR().getRI_WMS_CODE());
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