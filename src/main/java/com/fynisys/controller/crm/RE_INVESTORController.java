package com.fynisys.controller.crm;

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
import com.fynisys.activitylogs.ActivityLogger;
import com.fynisys.controller.crm.beans.InvestorBean;
import com.fynisys.controller.crm.beans.Workflow;
import com.fynisys.model.FUND_COUNTRIES;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.clienttype.FUND_MAR_CLIENT_TYPE;
import com.fynisys.model.clienttype.FWMS_NATIONALITY;
import com.fynisys.model.crm.RE_CRMWORKFLOW;
import com.fynisys.model.crm.RE_INVESTOR;
import com.fynisys.model.parameters.FUND_CURRENCY_MSTR;
import com.fynisys.repository.clienttype.FWMS_NATIONALITYRepository;
import com.fynisys.repository.crm.RE_INVESTORRepository;
import com.fynisys.repository.people.FUND_ACCOUNT_MSTRRepository;
import com.fynisys.repository.people.FUND_BROKER_MSTRRepository;
import com.fynisys.utilities.FundUserValidate;
import com.fynisys.utilities.LevelParameterValidator;
import com.fynisys.utilities.StockParameters;


// TODO: Auto-generated Javadoc
/**
 * The Class RE_INVESTORController.
 */
@RestController
public class RE_INVESTORController{
	
	/** The r E INVESTOR repository. */
	@Autowired
	RE_INVESTORRepository rE_INVESTORRepository;
	
	/** The UN D BROKE R MSTR repository. */
	@Autowired
	FUND_BROKER_MSTRRepository fUND_BROKER_MSTRRepository;
	
	/** The stock parameters. */
	@Autowired
	StockParameters stockParameters;
	
	/** The fund user validate. */
	@Autowired
	FundUserValidate fundUserValidate;
	
	/** The activity logger. */
	@Autowired
	ActivityLogger activityLogger;
	
	/** The UN D ACCOUN T MSTR repository. */
	@Autowired
	FUND_ACCOUNT_MSTRRepository fUND_ACCOUNT_MSTRRepository;
	
	/** The WM S NATIONALITY repository. */
	@Autowired
	FWMS_NATIONALITYRepository fWMS_NATIONALITYRepository;
	
	/** The UN D ACCOUN T LINK service. */
	/*
	 * For Account info Saving
	 */
	@Autowired
	FUND_ACCOUNT_LINKService fUND_ACCOUNT_LINKService;
	
	/** The client type parameters. */
	/*
	 * 
	 */
	@Autowired
	LevelParameterValidator clientTypeParameters;
	
	/** The Constant logger. */
	private static final Logger logger=LoggerFactory.getLogger("FUND INVESTOR CONTROLLER");
	
		
	/**
	 * Save.
	 *
	 * @param requestBody the request body
	 * @return the response entity
	 */
	@PostMapping("/saveInvestor")
    public ResponseEntity<?> save(RequestEntity<Map<String,Object>>requestBody){
	Map<String,Object> requestJson=requestBody.getBody();
	String type=(String)requestJson.get("type");
	String clientname=(String)requestJson.get("clientName");
	String createdby=(String)requestJson.get("createdby");
	Object SVL_SCREEN=requestJson.get("screentype");
	Object SVL_DESC=requestJson.get("log");
	Map<String,Object> json=new HashMap<>();
	if(type==null)
	{
		json.put("msg", "type is missing");
		return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
	}
	if(clientname==null) {
		json.put("msg", "Name is missing");
		return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
	}
	if(clientname.isEmpty()||type.isEmpty()||createdby.isEmpty()) {
		json.put("msg", "Type or Name or createdby is Empty");
		return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
	}
	FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
	if(fuser==null)
	{
			json.put("msg", "Createdby is not valid user");
			logger.error("Createdby is not valid user");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
	}
	/*
	 * Adding record
	 */
	RE_INVESTOR fc=new RE_INVESTOR();
	if(type.equalsIgnoreCase("corporate")) {
			fc.setRI_CORPORATE_NAME(clientname.trim());
	}else if(type.matches("(?i)individual|joint")){
		fc.setRI_INVESTOR_NAME(clientname.trim());
	}else{
		json.put("msg", "type is not with corrrect value");
		return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
	}
   fc.setRI_INVESTOR_TYPE(type);
   fc.setIV_ENTER_UID(fuser.getSVC_UID());
	try {
		fc=rE_INVESTORRepository.save(fc);

		 if(fc!=null) {
             json.put("msg", "save");
             boolean lg=activityLogger.writeLog(fuser, fc.getRI_WMS_CODE(), SVL_SCREEN, SVL_DESC, "CREATE");
             if(lg==true) {
            	 logger.info("log successfully written for newly created investor");
             }
             json.put("ri_WMS_CODE", fc.getRI_WMS_CODE());
             json.put("entereddate", fc.getIV_ENTER_DATE());
             if(type.equalsIgnoreCase("corporate")) {
                 json.put("ri_CORPORATE_NAME", fc.getRI_CORPORATE_NAME());
             }else if(type.matches("(?i)individual|joint")){
                 json.put("ri_INVESTOR_NAME", fc.getRI_INVESTOR_NAME());
             }
            json.put("ri_INVESTOR_TYPE", fc.getRI_INVESTOR_TYPE());
             
             
             return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
         }
		else {
			json.put("msg", "Not save");
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
	}catch (Exception e) {
		logger.error(e.getMessage());
		json.put("msg", e.getMessage());
		return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
	}
	
}
	
	
	/**
	 * Save info.
	 *
	 * @param requestBody the request body
	 * @return the response entity
	 */
	@PostMapping("/saveInvestorInfo")
    public ResponseEntity<?> saveInfo(RequestEntity<Map<String, Object>> requestBody){
	Map<String,Object>requestJson=requestBody.getBody();	
	Object RI_WMS_CODE=requestJson.get("RI_WMS_CODE");
   //	Object RI_INVESTOR_CODE=requestJson.get("RI_INVESTOR_CODE");
   //Object RI_INVESTOR_TYPE=requestJson.get("RI_INVESTOR_TYPE");
	Object RI_CREATE_DATE=requestJson.get("RI_CREATE_DATE");
//	Object RI_INVESTOR_NAME=requestJson.get("RI_INVESTOR_NAME");
	Object RI_ADDRESS_1=requestJson.get("RI_ADDRESS_1");
	Object RI_POST_BOX=requestJson.get("RI_POST_BOX");
	Object RI_POST_CODE=requestJson.get("RI_POST_CODE");
	Object RI_CITY=requestJson.get("RI_CITY");
	Object RI_COUNTRY=requestJson.get("RI_COUNTRY");
	Object RI_TEL_NO=requestJson.get("RI_TEL_NO");
	Object RI_MOBILE_NO=requestJson.get("RI_MOBILE_NO");
	Object RI_FAX_NO=requestJson.get("RI_FAX_NO");
	Object RI_EMAIL=requestJson.get("RI_EMAIL");
	Object RI_COMPANY_NAME=requestJson.get("RI_COMPANY_NAME");
	Object RI_COMPANY_LICENSE_NO=requestJson.get("RI_COMPANY_LICENSE_NO");
	Object RI_EXPIRY_DATE=requestJson.get("RI_EXPIRY_DATE");
	Object RI_OCCUPATION=requestJson.get("RI_OCCUPATION");
	Object RI_NATIONALITY=requestJson.get("RI_NATIONALITY");
	Object RI_DOB=requestJson.get("RI_DOB");
	Object RI_GENDER=requestJson.get("RI_GENDER");
	Object RI_JOINT_AUTHORISATION=requestJson.get("RI_JOINT_AUTHORISATION");
	Object RI_STATUS=requestJson.get("RI_STATUS");
	Object SVC_UID=requestJson.get("SVC_UID");
	Object RI_INDUSTRY=requestJson.get("RI_INDUSTRY");
	Object RI_REMARKS=requestJson.get("RI_REMARKS");
	Object RI_MARITAL_STATUS=requestJson.get("RI_MARITAL_STATUS");
	Object RI_MARITAL_DEPENDENTS=requestJson.get("RI_MARITAL_DEPENDENTS");
	Object RI_NATIONAL_IDENITY=requestJson.get("RI_NATIONAL_IDENITY");
	Object RI_PASSPORT_IPLACE=requestJson.get("RI_PASSPORT_IPLACE");
	Object RI_PASSPORT_IDATE=requestJson.get("RI_PASSPORT_IDATE");
	Object RI_PASSPORT_ID=requestJson.get("RI_PASSPORT_ID");
	Object RI_INVESTOR_NAME2=requestJson.get("RI_INVESTOR_NAME2");
	Object RI_COMPANY_NAME2=requestJson.get("RI_COMPANY_NAME2");
	Object RI_OCCUPATION2=requestJson.get("RI_OCCUPATION2");
	Object RI_NATIONALITY2=requestJson.get("RI_NATIONALITY2");
	Object RI_DOB2=requestJson.get("RI_DOB2");
	Object RI_GENDER2=requestJson.get("RI_GENDER2");
	Object RI_MARITAL_STATUS2=requestJson.get("RI_MARITAL_STATUS2");
	Object RI_MARITAL_DEPENDENTS2=requestJson.get("RI_MARITAL_DEPENDENTS2");
	Object RI_PASSPORT_IPLACE2=requestJson.get("RI_PASSPORT_IPLACE2");
	Object RI_PASSPORT_IDATE2=requestJson.get("RI_PASSPORT_IDATE2");
	Object RI_NATIONAL_IDENITY2=requestJson.get("RI_NATIONAL_IDENITY2");
	Object RI_PASSPORT_ID2=requestJson.get("RI_PASSPORT_ID2");
	Object RI_PRIMARY_CONTACT_NAME=requestJson.get("RI_PRIMARY_CONTACT_NAME");
	Object RI_OFFICE_APART_NO=requestJson.get("RI_OFFICE_APART_NO");
	Object RI_STREET=requestJson.get("RI_STREET");
	Object RI_AREA=requestJson.get("RI_AREA");
	Object RI_TEL_NO_HOME=requestJson.get("RI_TEL_NO_HOME");
	Object RI_PRIMARY_CONTACT_NAME2=requestJson.get("RI_PRIMARY_CONTACT_NAME2");
	Object RI_ADDRESS_12=requestJson.get("RI_ADDRESS_12");
	Object RI_OFFICE_APART_NO2=requestJson.get("RI_OFFICE_APART_NO2");
	Object RI_STREET2=requestJson.get("RI_STREET2");
	Object RI_AREA2=requestJson.get("RI_AREA2");
	Object RI_POST_BOX2=requestJson.get("RI_POST_BOX2");
	Object RI_CITY2=requestJson.get("RI_CITY2");
	Object RI_COUNTRY2=requestJson.get("RI_COUNTRY2");
	Object RI_POST_CODE2=requestJson.get("RI_POST_CODE2");
	Object RI_TEL_NO2=requestJson.get("RI_TEL_NO2");
	Object RI_TEL_NO_HOME2=requestJson.get("RI_TEL_NO_HOME2");
	Object RI_MOBILE_NO2=requestJson.get("RI_MOBILE_NO2");
	Object RI_FAX_NO2=requestJson.get("RI_FAX_NO2");
	Object RI_EMAIL2=requestJson.get("RI_EMAIL2");
	Object RI_EXPIRY_DATE2=requestJson.get("RI_EXPIRY_DATE2");
	Object RI_BANK_ANAME=requestJson.get("RI_BANK_ANAME");
	Object RI_BANK_ANUMBER=requestJson.get("RI_BANK_ANUMBER");
	Object RI_BANK_BANK_NAME=requestJson.get("RI_BANK_BANK_NAME");
	Object RI_BANK_CURRENCY=requestJson.get("RI_BANK_CURRENCY");
	Object RI_BANK_BRANCH=requestJson.get("RI_BANK_BRANCH");
	Object RI_BANK_COUNTRY=requestJson.get("RI_BANK_COUNTRY");
	Object RI_BANK_CITY=requestJson.get("RI_BANK_CITY");
	Object RI_NOTI_FAX=requestJson.get("RI_NOTI_FAX");
	Object RI_NOTI_SMS=requestJson.get("RI_NOTI_SMS");
	Object RI_NOTI_MAIL=requestJson.get("RI_NOTI_MAIL");
	Object RI_NOTI_EMAIL=requestJson.get("RI_NOTI_EMAIL");
	Object RI_MARGIN_TYPE=requestJson.get("RI_MARGIN_TYPE");
	Object RI_INTEREST_TYPE=requestJson.get("RI_INTEREST_TYPE");
	Object RI_RELATION_MANAGER=requestJson.get("RI_RELATION_MANAGER");
	//Object RI_CORPORATE_NAME=requestJson.get("RI_CORPORATE_NAME");
	Object RI_BUSINESS_ACTIVITY=requestJson.get("RI_BUSINESS_ACTIVITY");
	Object RI_COUNTRY_ESTA=requestJson.get("RI_COUNTRY_ESTA");
	Object RI_COMMERCIAL_REGIS_NO=requestJson.get("RI_COMMERCIAL_REGIS_NO");
	Object RI_TRADE_LICENSE_NO=requestJson.get("RI_TRADE_LICENSE_NO");
	Object RI_TRADE_LICENSE_IDATE=requestJson.get("RI_TRADE_LICENSE_IDATE");
	Object RI_TRADE_LICENSE_EDATE=requestJson.get("RI_TRADE_LICENSE_EDATE");
	Object RI_AUTHORIZED_NAME=requestJson.get("RI_AUTHORIZED_NAME");
	Object RI_CAPACITY=requestJson.get("RI_CAPACITY");
	Object RI_MARGIN_CLIENT_FLAG=requestJson.get("RI_MARGIN_CLIENT_FLAG");
	Object RI_MARGIN_REPORT_CURRENCY=requestJson.get("RI_MARGIN_REPORT_CURRENCY");
	Object RI_MARGIN_AGGDATE=requestJson.get("RI_MARGIN_AGGDATE");
	Object RI_RELATION_ALMAL=requestJson.get("RI_RELATION_ALMAL");
	Object RI_REFFER_BY=requestJson.get("RI_REFFER_BY");
	Object RI_PMR_DAY=requestJson.get("RI_PMR_DAY");
	Object RI_PMR_WEEKLY=requestJson.get("RI_PMR_WEEKLY");
	Object RI_PMR_MONTH=requestJson.get("RI_PMR_MONTH");
	Object RI_MARKET_AMT=requestJson.get("RI_MARKET_AMT");
	Object RI_MARGIN_SUB_TYPE=requestJson.get("RI_MARGIN_SUB_TYPE");
	Object RI_OWN_OR_CLIENT=requestJson.get("RI_OWN_OR_CLIENT");
	Object RI_MONTH_END_REP_FLAG=requestJson.get("RI_MONTH_END_REP_FLAG");
	Object RI_INTEREST_POST=requestJson.get("RI_INTEREST_POST");
	Object RI_INTEREST_REMARKS=requestJson.get("RI_INTEREST_REMARKS");
	Object RI_ACCOUNT_CLOSE=requestJson.get("RI_ACCOUNT_CLOSE");
	Object RI_ACCOUNT_CLOSE_REMARKS=requestJson.get("RI_ACCOUNT_CLOSE_REMARKS");
	Object RI_SALVATION=requestJson.get("RI_SALVATION");
	Object RI_FIRST_NAME=requestJson.get("RI_FIRST_NAME");
	Object RI_LAST_NAME=requestJson.get("RI_LAST_NAME");
	Object RI_MIDDLE_NAME=requestJson.get("RI_MIDDLE_NAME");
	Object RI_SUFFIX=requestJson.get("RI_SUFFIX");
	Object RI_JOINT_SALVATION=requestJson.get("RI_JOINT_SALVATION");
	Object RI_JOINT_FIRST_NAME=requestJson.get("RI_JOINT_FIRST_NAME");
	Object RI_JOINT_LAST_NAME=requestJson.get("RI_JOINT_LAST_NAME");
	Object RI_JOINT_MIDDLE_NAME=requestJson.get("RI_JOINT_MIDDLE_NAME");
	Object RI_JOINT_SUFFIX=requestJson.get("RI_JOINT_SUFFIX");
	Object RI_CORP_CD_SALVATION=requestJson.get("RI_CORP_CD_SALVATION");
	Object RI_CORP_CD_FIRST_NAME=requestJson.get("RI_CORP_CD_FIRST_NAME");
	Object RI_CORP_CD_LAST_NAME=requestJson.get("RI_CORP_CD_LAST_NAME");
	Object RI_CORP_CD_MIDDLE_NAME=requestJson.get("RI_CORP_CD_MIDDLE_NAME");
	Object RI_CORP_CD_SUFFIX=requestJson.get("RI_CORP_CD_SUFFIX");
	Object RI_ACCOUNT_CLOSE_DATE=requestJson.get("RI_ACCOUNT_CLOSE_DATE");
	Object RI_CRATE=requestJson.get("RI_CRATE");
	Object createdby=requestJson.get("createdby");
	Object WMS_COMMENTS=requestJson.get("comment");
	Object SVL_SCREEN=requestJson.get("screentype");
	Object SVL_DESC=requestJson.get("log");
	Object SNO=requestJson.get("sno");
	Map<String,Object> json=new HashMap<>();
	if(RI_WMS_CODE==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
	{
		json.put("msg", "Please check Input json, missing some required attributes");
		logger.error("Please check Input json, missing some required attributes");
		return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
	}
	if(RI_WMS_CODE.toString().isEmpty()) {
		json.put("msg", "Please check RI_WMS_CODE is empty attribute");
		logger.error("Please check RI_WMS_CODE is empty attribute");
		return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
	}
	
	FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
	if(fuser==null)
	{
			json.put("msg", "Createdby is not valid user");
			logger.error("Createdby is not valid user");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
	}
	
	RE_INVESTOR fc=rE_INVESTORRepository.findOne(new Long(RI_WMS_CODE.toString()));
	if(fc==null) {
		json.put("msg", "No investor found for this id "+RI_WMS_CODE.toString());
		logger.error("No investor found for this id "+RI_WMS_CODE.toString());
		return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
	}
	
/*
 * It will generate after work flow
 */
	
//		if(RI_INVESTOR_CODE!=null){
//		 if(RI_INVESTOR_CODE.toString().isEmpty()==false){
//		fc.setRI_INVESTOR_CODE(RI_INVESTOR_CODE.toString());
//		}
//		}
	
//		if(RI_INVESTOR_TYPE!=null){
//		 if(RI_INVESTOR_TYPE.toString().isEmpty()==false){
//		fc.setRI_INVESTOR_TYPE(RI_INVESTOR_TYPE.toString());
//		}
//		}
	
		if(RI_CREATE_DATE!=null){
		 if(RI_CREATE_DATE.toString().isEmpty()==false){
	     Calendar cl=Calendar.getInstance();
		 cl.setTimeInMillis(new Long(RI_CREATE_DATE.toString()));
		 fc.setRI_CREATE_DATE(cl);
		}
		}
//		if(RI_INVESTOR_NAME!=null){
//		 if(RI_INVESTOR_NAME.toString().isEmpty()==false){
//		fc.setRI_INVESTOR_NAME(RI_INVESTOR_NAME.toString());
//		}
//		}
		if(RI_ADDRESS_1!=null){
		 if(RI_ADDRESS_1.toString().isEmpty()==false){
		fc.setRI_ADDRESS_1(RI_ADDRESS_1.toString());
		}
		}
		if(RI_POST_BOX!=null){
		 if(RI_POST_BOX.toString().isEmpty()==false){
		fc.setRI_POST_BOX(RI_POST_BOX.toString());
		}
		}
		if(RI_POST_CODE!=null){
		 if(RI_POST_CODE.toString().isEmpty()==false){
		fc.setRI_POST_CODE(RI_POST_CODE.toString());
		}
		}
		if(RI_CITY!=null){
		 if(RI_CITY.toString().isEmpty()==false){
		fc.setRI_CITY(RI_CITY.toString());
		}
		}
		if(RI_COUNTRY!=null){
		 if(RI_COUNTRY.toString().isEmpty()==false){
		fc.setRI_COUNTRY(RI_COUNTRY.toString());
		}
		}
		if(RI_TEL_NO!=null){
		 if(RI_TEL_NO.toString().isEmpty()==false){
		fc.setRI_TEL_NO(RI_TEL_NO.toString());
		}
		}
		if(RI_MOBILE_NO!=null){
		 if(RI_MOBILE_NO.toString().isEmpty()==false){
		fc.setRI_MOBILE_NO(RI_MOBILE_NO.toString());
		}
		}
		if(RI_FAX_NO!=null){
		 if(RI_FAX_NO.toString().isEmpty()==false){
		fc.setRI_FAX_NO(RI_FAX_NO.toString());
		}
		}
		if(RI_EMAIL!=null){
		 if(RI_EMAIL.toString().isEmpty()==false){
		fc.setRI_EMAIL(RI_EMAIL.toString());
		}
		}
		if(RI_COMPANY_NAME!=null){
		 if(RI_COMPANY_NAME.toString().isEmpty()==false){
		fc.setRI_COMPANY_NAME(RI_COMPANY_NAME.toString());
		}
		}
		if(RI_COMPANY_LICENSE_NO!=null){
		 if(RI_COMPANY_LICENSE_NO.toString().isEmpty()==false){
		fc.setRI_COMPANY_LICENSE_NO(RI_COMPANY_LICENSE_NO.toString());
		}
		}
		if(RI_EXPIRY_DATE!=null){
		 if(RI_EXPIRY_DATE.toString().isEmpty()==false){
			 Calendar cl=Calendar.getInstance();
			 cl.setTimeInMillis(new Long(RI_EXPIRY_DATE.toString()));
		fc.setRI_EXPIRY_DATE(cl);
		}
		}
		if(RI_OCCUPATION!=null){
		 if(RI_OCCUPATION.toString().isEmpty()==false){
		fc.setRI_OCCUPATION(RI_OCCUPATION.toString());
		}
		}
		if(RI_NATIONALITY!=null){
		 if(RI_NATIONALITY.toString().isEmpty()==false){
		fc.setRI_NATIONALITY(RI_NATIONALITY.toString());
		}
		}
		if(RI_DOB!=null){
		 if(RI_DOB.toString().isEmpty()==false){
		Calendar cl=Calendar.getInstance();
		cl.setTimeInMillis(new Long(RI_DOB.toString()));
		fc.setRI_DOB(cl);
		}
		}
		if(RI_GENDER!=null){
		 if(RI_GENDER.toString().isEmpty()==false){
		fc.setRI_GENDER(RI_GENDER.toString());
		}
		}
		if(RI_JOINT_AUTHORISATION!=null){
		 if(RI_JOINT_AUTHORISATION.toString().isEmpty()==false){
		fc.setRI_JOINT_AUTHORISATION(RI_JOINT_AUTHORISATION.toString());
		}
		}
		if(RI_STATUS!=null){
		 if(RI_STATUS.toString().isEmpty()==false){
		fc.setRI_STATUS(RI_STATUS.toString());
		}
		}
		if(SVC_UID!=null){
		 if(SVC_UID.toString().isEmpty()==false){
		fc.setSVC_UID(SVC_UID.toString());
		}
		}
		if(RI_INDUSTRY!=null){
		 if(RI_INDUSTRY.toString().isEmpty()==false){
		fc.setRI_INDUSTRY(RI_INDUSTRY.toString());
		}
		}
		if(RI_REMARKS!=null){
		 if(RI_REMARKS.toString().isEmpty()==false){
		fc.setRI_REMARKS(RI_REMARKS.toString());
		}
		}
		if(RI_MARITAL_STATUS!=null){
		 if(RI_MARITAL_STATUS.toString().isEmpty()==false){
		fc.setRI_MARITAL_STATUS(RI_MARITAL_STATUS.toString());
		}
		}
		if(RI_MARITAL_DEPENDENTS!=null){
		 if(RI_MARITAL_DEPENDENTS.toString().isEmpty()==false){
		fc.setRI_MARITAL_DEPENDENTS(RI_MARITAL_DEPENDENTS.toString());
		}
		}
		if(RI_NATIONAL_IDENITY!=null){
		 if(RI_NATIONAL_IDENITY.toString().isEmpty()==false){
		fc.setRI_NATIONAL_IDENITY(RI_NATIONAL_IDENITY.toString());
		}
		}
		if(RI_PASSPORT_IPLACE!=null){
		 if(RI_PASSPORT_IPLACE.toString().isEmpty()==false){
		fc.setRI_PASSPORT_IPLACE(RI_PASSPORT_IPLACE.toString());
		}
		}
		if(RI_PASSPORT_IDATE!=null){
		 if(RI_PASSPORT_IDATE.toString().isEmpty()==false){
			 Calendar cl=Calendar.getInstance();
			 cl.setTimeInMillis(new Long(RI_PASSPORT_IDATE.toString()));
		fc.setRI_PASSPORT_IDATE(cl);
		}
		}
		if(RI_PASSPORT_ID!=null){
		 if(RI_PASSPORT_ID.toString().isEmpty()==false){
		fc.setRI_PASSPORT_ID(RI_PASSPORT_ID.toString());
		}
		}
		if(RI_INVESTOR_NAME2!=null){
		 if(RI_INVESTOR_NAME2.toString().isEmpty()==false){
		fc.setRI_INVESTOR_NAME2(RI_INVESTOR_NAME2.toString());
		}
		}
		if(RI_COMPANY_NAME2!=null){
		 if(RI_COMPANY_NAME2.toString().isEmpty()==false){
		fc.setRI_COMPANY_NAME2(RI_COMPANY_NAME2.toString());
		}
		}
		if(RI_OCCUPATION2!=null){
		 if(RI_OCCUPATION2.toString().isEmpty()==false){
		fc.setRI_OCCUPATION2(RI_OCCUPATION2.toString());
		}
		}
		if(RI_NATIONALITY2!=null){
		 if(RI_NATIONALITY2.toString().isEmpty()==false){
		fc.setRI_NATIONALITY2(RI_NATIONALITY2.toString());
		}
		}
		if(RI_DOB2!=null){
		 if(RI_DOB2.toString().isEmpty()==false){
			 Calendar cl=Calendar.getInstance();
			 cl.setTimeInMillis(new Long(RI_DOB2.toString()));
			 fc.setRI_DOB2(cl);
		}
		}
		if(RI_GENDER2!=null){
		 if(RI_GENDER2.toString().isEmpty()==false){
		fc.setRI_GENDER2(RI_GENDER2.toString());
		}
		}
		if(RI_MARITAL_STATUS2!=null){
		 if(RI_MARITAL_STATUS2.toString().isEmpty()==false){
		fc.setRI_MARITAL_STATUS2(RI_MARITAL_STATUS2.toString());
		}
		}
		if(RI_MARITAL_DEPENDENTS2!=null){
		 if(RI_MARITAL_DEPENDENTS2.toString().isEmpty()==false){
		fc.setRI_MARITAL_DEPENDENTS2(RI_MARITAL_DEPENDENTS2.toString());
		}
		}
		if(RI_PASSPORT_IPLACE2!=null){
		 if(RI_PASSPORT_IPLACE2.toString().isEmpty()==false){
		fc.setRI_PASSPORT_IPLACE2(RI_PASSPORT_IPLACE2.toString());
		}
		}
		if(RI_PASSPORT_IDATE2!=null){
		 if(RI_PASSPORT_IDATE2.toString().isEmpty()==false){
			 Calendar cl=Calendar.getInstance();
			 cl.setTimeInMillis(new Long(RI_PASSPORT_IDATE2.toString()));
		fc.setRI_PASSPORT_IDATE2(cl);
		}
		}
		if(RI_NATIONAL_IDENITY2!=null){
		 if(RI_NATIONAL_IDENITY2.toString().isEmpty()==false){
		fc.setRI_NATIONAL_IDENITY2(RI_NATIONAL_IDENITY2.toString());
		}
		}
		if(RI_PASSPORT_ID2!=null){
		 if(RI_PASSPORT_ID2.toString().isEmpty()==false){
		fc.setRI_PASSPORT_ID2(RI_PASSPORT_ID2.toString());
		}
		}
		if(RI_PRIMARY_CONTACT_NAME!=null){
		 if(RI_PRIMARY_CONTACT_NAME.toString().isEmpty()==false){
		fc.setRI_PRIMARY_CONTACT_NAME(RI_PRIMARY_CONTACT_NAME.toString());
		}
		}
		if(RI_OFFICE_APART_NO!=null){
		 if(RI_OFFICE_APART_NO.toString().isEmpty()==false){
		fc.setRI_OFFICE_APART_NO(RI_OFFICE_APART_NO.toString());
		}
		}
		if(RI_STREET!=null){
		 if(RI_STREET.toString().isEmpty()==false){
		fc.setRI_STREET(RI_STREET.toString());
		}
		}
		if(RI_AREA!=null){
		 if(RI_AREA.toString().isEmpty()==false){
		fc.setRI_AREA(RI_AREA.toString());
		}
		}
		if(RI_TEL_NO_HOME!=null){
		 if(RI_TEL_NO_HOME.toString().isEmpty()==false){
		fc.setRI_TEL_NO_HOME(RI_TEL_NO_HOME.toString());
		}
		}
		if(RI_PRIMARY_CONTACT_NAME2!=null){
		 if(RI_PRIMARY_CONTACT_NAME2.toString().isEmpty()==false){
		fc.setRI_PRIMARY_CONTACT_NAME2(RI_PRIMARY_CONTACT_NAME2.toString());
		}
		}
		if(RI_ADDRESS_12!=null){
		 if(RI_ADDRESS_12.toString().isEmpty()==false){
		fc.setRI_ADDRESS_12(RI_ADDRESS_12.toString());
		}
		}
		if(RI_OFFICE_APART_NO2!=null){
		 if(RI_OFFICE_APART_NO2.toString().isEmpty()==false){
		fc.setRI_OFFICE_APART_NO2(RI_OFFICE_APART_NO2.toString());
		}
		}
		if(RI_STREET2!=null){
		 if(RI_STREET2.toString().isEmpty()==false){
		fc.setRI_STREET2(RI_STREET2.toString());
		}
		}
		if(RI_AREA2!=null){
		 if(RI_AREA2.toString().isEmpty()==false){
		fc.setRI_AREA2(RI_AREA2.toString());
		}
		}
		if(RI_POST_BOX2!=null){
		 if(RI_POST_BOX2.toString().isEmpty()==false){
		fc.setRI_POST_BOX2(RI_POST_BOX2.toString());
		}
		}
		if(RI_CITY2!=null){
		 if(RI_CITY2.toString().isEmpty()==false){
		fc.setRI_CITY2(RI_CITY2.toString());
		}
		}
		if(RI_COUNTRY2!=null){
		 if(RI_COUNTRY2.toString().isEmpty()==false){
		fc.setRI_COUNTRY2(RI_COUNTRY2.toString());
		}
		}
		if(RI_POST_CODE2!=null){
		 if(RI_POST_CODE2.toString().isEmpty()==false){
		fc.setRI_POST_CODE2(RI_POST_CODE2.toString());
		}
		}
		if(RI_TEL_NO2!=null){
		 if(RI_TEL_NO2.toString().isEmpty()==false){
		fc.setRI_TEL_NO2(RI_TEL_NO2.toString());
		}
		}
		if(RI_TEL_NO_HOME2!=null){
		 if(RI_TEL_NO_HOME2.toString().isEmpty()==false){
		fc.setRI_TEL_NO_HOME2(RI_TEL_NO_HOME2.toString());
		}
		}
		if(RI_MOBILE_NO2!=null){
		 if(RI_MOBILE_NO2.toString().isEmpty()==false){
		fc.setRI_MOBILE_NO2(RI_MOBILE_NO2.toString());
		}
		}
		if(RI_FAX_NO2!=null){
		 if(RI_FAX_NO2.toString().isEmpty()==false){
		fc.setRI_FAX_NO2(RI_FAX_NO2.toString());
		}
		}
		if(RI_EMAIL2!=null){
		 if(RI_EMAIL2.toString().isEmpty()==false){
		fc.setRI_EMAIL2(RI_EMAIL2.toString());
		}
		}
		if(RI_EXPIRY_DATE2!=null){
		 if(RI_EXPIRY_DATE2.toString().isEmpty()==false){
			 Calendar cl=Calendar.getInstance();
			 cl.setTimeInMillis(new Long(RI_EXPIRY_DATE2.toString()));
		fc.setRI_EXPIRY_DATE2(cl);
		}
		}
		if(RI_BANK_ANAME!=null){
		 if(RI_BANK_ANAME.toString().isEmpty()==false){
		fc.setRI_BANK_ANAME(RI_BANK_ANAME.toString());
		}
		}
		if(RI_BANK_ANUMBER!=null){
		 if(RI_BANK_ANUMBER.toString().isEmpty()==false){
		fc.setRI_BANK_ANUMBER(RI_BANK_ANUMBER.toString());
		}
		}
		if(RI_BANK_BANK_NAME!=null){
		 if(RI_BANK_BANK_NAME.toString().isEmpty()==false){
		fc.setRI_BANK_BANK_NAME(RI_BANK_BANK_NAME.toString());
		}
		}
		if(RI_BANK_CURRENCY!=null){
		 if(RI_BANK_CURRENCY.toString().isEmpty()==false){
		fc.setRI_BANK_CURRENCY(RI_BANK_CURRENCY.toString());
		}
		}
		if(RI_BANK_BRANCH!=null){
		 if(RI_BANK_BRANCH.toString().isEmpty()==false){
		fc.setRI_BANK_BRANCH(RI_BANK_BRANCH.toString());
		}
		}
		if(RI_BANK_COUNTRY!=null){
		 if(RI_BANK_COUNTRY.toString().isEmpty()==false){
		fc.setRI_BANK_COUNTRY(RI_BANK_COUNTRY.toString());
		}
		}
		if(RI_BANK_CITY!=null){
		 if(RI_BANK_CITY.toString().isEmpty()==false){
		fc.setRI_BANK_CITY(RI_BANK_CITY.toString());
		}
		}
		if(RI_NOTI_FAX!=null){
		 if(RI_NOTI_FAX.toString().isEmpty()==false){
		fc.setRI_NOTI_FAX(RI_NOTI_FAX.toString());
		}
		}
		if(RI_NOTI_SMS!=null){
		 if(RI_NOTI_SMS.toString().isEmpty()==false){
		fc.setRI_NOTI_SMS(RI_NOTI_SMS.toString());
		}
		}
		if(RI_NOTI_MAIL!=null){
		 if(RI_NOTI_MAIL.toString().isEmpty()==false){
		fc.setRI_NOTI_MAIL(RI_NOTI_MAIL.toString());
		}
		}
		if(RI_NOTI_EMAIL!=null){
		 if(RI_NOTI_EMAIL.toString().isEmpty()==false){
		fc.setRI_NOTI_EMAIL(RI_NOTI_EMAIL.toString());
		}
		}
		if(RI_MARGIN_TYPE!=null){
		 if(RI_MARGIN_TYPE.toString().isEmpty()==false){
		fc.setRI_MARGIN_TYPE(new Long(RI_MARGIN_TYPE.toString()));
		}
		}
		if(RI_INTEREST_TYPE!=null){
		 if(RI_INTEREST_TYPE.toString().isEmpty()==false){
		fc.setRI_INTEREST_TYPE(new Long(RI_INTEREST_TYPE.toString()));
		}
		}
		if(RI_RELATION_MANAGER!=null){
		if(RI_RELATION_MANAGER.toString().isEmpty()==false){
		fc.setRI_RELATION_MANAGER(new Long(RI_RELATION_MANAGER.toString()));
		}
		}
//		if(RI_CORPORATE_NAME!=null){
//		 if(RI_CORPORATE_NAME.toString().isEmpty()==false){
//		fc.setRI_CORPORATE_NAME(RI_CORPORATE_NAME.toString());
//		}
//		}
		if(RI_BUSINESS_ACTIVITY!=null){
		 if(RI_BUSINESS_ACTIVITY.toString().isEmpty()==false){
		fc.setRI_BUSINESS_ACTIVITY(RI_BUSINESS_ACTIVITY.toString());
		}
		}
		if(RI_COUNTRY_ESTA!=null){
		 if(RI_COUNTRY_ESTA.toString().isEmpty()==false){
		fc.setRI_COUNTRY_ESTA(RI_COUNTRY_ESTA.toString());
		}
		}
		if(RI_COMMERCIAL_REGIS_NO!=null){
		 if(RI_COMMERCIAL_REGIS_NO.toString().isEmpty()==false){
		fc.setRI_COMMERCIAL_REGIS_NO(RI_COMMERCIAL_REGIS_NO.toString());
		}
		}
		if(RI_TRADE_LICENSE_NO!=null){
		 if(RI_TRADE_LICENSE_NO.toString().isEmpty()==false){
		fc.setRI_TRADE_LICENSE_NO(RI_TRADE_LICENSE_NO.toString());
		}
		}
		if(RI_TRADE_LICENSE_IDATE!=null){
		 if(RI_TRADE_LICENSE_IDATE.toString().isEmpty()==false){
			 Calendar cl=Calendar.getInstance();
			 cl.setTimeInMillis(new Long(RI_TRADE_LICENSE_IDATE.toString()));
		fc.setRI_TRADE_LICENSE_IDATE(cl);
		}
		}
		if(RI_TRADE_LICENSE_EDATE!=null){
		 if(RI_TRADE_LICENSE_EDATE.toString().isEmpty()==false){
			 Calendar cl=Calendar.getInstance();
			 cl.setTimeInMillis(new Long(RI_TRADE_LICENSE_EDATE.toString()));
		fc.setRI_TRADE_LICENSE_EDATE(cl);
		}
		}
		if(RI_AUTHORIZED_NAME!=null){
		 if(RI_AUTHORIZED_NAME.toString().isEmpty()==false){
		fc.setRI_AUTHORIZED_NAME(RI_AUTHORIZED_NAME.toString());
		}
		}
		if(RI_CAPACITY!=null){
		 if(RI_CAPACITY.toString().isEmpty()==false){
		fc.setRI_CAPACITY(RI_CAPACITY.toString());
		}
		}
		if(RI_MARGIN_CLIENT_FLAG!=null){
		 if(RI_MARGIN_CLIENT_FLAG.toString().isEmpty()==false){
		fc.setRI_MARGIN_CLIENT_FLAG(RI_MARGIN_CLIENT_FLAG.toString());
		}
		}
		if(RI_MARGIN_REPORT_CURRENCY!=null){
		 if(RI_MARGIN_REPORT_CURRENCY.toString().isEmpty()==false){
		fc.setRI_MARGIN_REPORT_CURRENCY(new Long(RI_MARGIN_REPORT_CURRENCY.toString()));
		}
		}
		if(RI_MARGIN_AGGDATE!=null){
		 if(RI_MARGIN_AGGDATE.toString().isEmpty()==false){
			 Calendar cl=Calendar.getInstance();
			 cl.setTimeInMillis(new Long(RI_MARGIN_AGGDATE.toString()));
		fc.setRI_MARGIN_AGGDATE(cl);
		}
		}
		if(RI_RELATION_ALMAL!=null){
		 if(RI_RELATION_ALMAL.toString().isEmpty()==false){
		fc.setRI_RELATION_ALMAL(RI_RELATION_ALMAL.toString());
		}
		}
		if(RI_REFFER_BY!=null){
		 if(RI_REFFER_BY.toString().isEmpty()==false){
		fc.setRI_REFFER_BY(RI_REFFER_BY.toString());
		}
		}
		if(RI_PMR_DAY!=null){
		 if(RI_PMR_DAY.toString().isEmpty()==false){
		fc.setRI_PMR_DAY(RI_PMR_DAY.toString());
		}
		}
		if(RI_PMR_WEEKLY!=null){
		 if(RI_PMR_WEEKLY.toString().isEmpty()==false){
		fc.setRI_PMR_WEEKLY(RI_PMR_WEEKLY.toString());
		}
		}
		if(RI_PMR_MONTH!=null){
		 if(RI_PMR_MONTH.toString().isEmpty()==false){
		fc.setRI_PMR_MONTH(RI_PMR_MONTH.toString());
		}
		}
		if(RI_MARKET_AMT!=null){
		 if(RI_MARKET_AMT.toString().isEmpty()==false){
		fc.setRI_MARKET_AMT(new Double(RI_MARKET_AMT.toString()));
		}
		}
		if(RI_MARGIN_SUB_TYPE!=null){
		 if(RI_MARGIN_SUB_TYPE.toString().isEmpty()==false){
		fc.setRI_MARGIN_SUB_TYPE(new Long(RI_MARGIN_SUB_TYPE.toString()));
		}
		}
		if(RI_OWN_OR_CLIENT!=null){
		 if(RI_OWN_OR_CLIENT.toString().isEmpty()==false){
		fc.setRI_OWN_OR_CLIENT(RI_OWN_OR_CLIENT.toString());
		}
		}
		if(RI_MONTH_END_REP_FLAG!=null){
		 if(RI_MONTH_END_REP_FLAG.toString().isEmpty()==false){
		fc.setRI_MONTH_END_REP_FLAG(RI_MONTH_END_REP_FLAG.toString());
		}
		}
		if(RI_INTEREST_POST!=null){
		 if(RI_INTEREST_POST.toString().isEmpty()==false){
		fc.setRI_INTEREST_POST(RI_INTEREST_POST.toString());
		}
		}
		if(RI_INTEREST_REMARKS!=null){
		 if(RI_INTEREST_REMARKS.toString().isEmpty()==false){
		fc.setRI_INTEREST_REMARKS(RI_INTEREST_REMARKS.toString());
		}
		}
		if(RI_ACCOUNT_CLOSE!=null){
		 if(RI_ACCOUNT_CLOSE.toString().isEmpty()==false){
		fc.setRI_ACCOUNT_CLOSE(RI_ACCOUNT_CLOSE.toString());
		}
		}
		if(RI_ACCOUNT_CLOSE_REMARKS!=null){
		 if(RI_ACCOUNT_CLOSE_REMARKS.toString().isEmpty()==false){
		fc.setRI_ACCOUNT_CLOSE_REMARKS(RI_ACCOUNT_CLOSE_REMARKS.toString());
		}
		}
		if(RI_SALVATION!=null){
		 if(RI_SALVATION.toString().isEmpty()==false){
		fc.setRI_SALVATION(RI_SALVATION.toString());
		}
		}
		if(RI_FIRST_NAME!=null){
		 if(RI_FIRST_NAME.toString().isEmpty()==false){
		fc.setRI_FIRST_NAME(RI_FIRST_NAME.toString());
		}
		}
		if(RI_LAST_NAME!=null){
		 if(RI_LAST_NAME.toString().isEmpty()==false){
		fc.setRI_LAST_NAME(RI_LAST_NAME.toString());
		}
		}
		if(RI_MIDDLE_NAME!=null){
		 if(RI_MIDDLE_NAME.toString().isEmpty()==false){
		fc.setRI_MIDDLE_NAME(RI_MIDDLE_NAME.toString());
		}
		}
		if(RI_SUFFIX!=null){
		 if(RI_SUFFIX.toString().isEmpty()==false){
		fc.setRI_SUFFIX(RI_SUFFIX.toString());
		}
		}
		if(RI_JOINT_SALVATION!=null){
		 if(RI_JOINT_SALVATION.toString().isEmpty()==false){
		fc.setRI_JOINT_SALVATION(RI_JOINT_SALVATION.toString());
		}
		}
		if(RI_JOINT_FIRST_NAME!=null){
		 if(RI_JOINT_FIRST_NAME.toString().isEmpty()==false){
		fc.setRI_JOINT_FIRST_NAME(RI_JOINT_FIRST_NAME.toString());
		}
		}
		if(RI_JOINT_LAST_NAME!=null){
		 if(RI_JOINT_LAST_NAME.toString().isEmpty()==false){
		fc.setRI_JOINT_LAST_NAME(RI_JOINT_LAST_NAME.toString());
		}
		}
		if(RI_JOINT_MIDDLE_NAME!=null){
		 if(RI_JOINT_MIDDLE_NAME.toString().isEmpty()==false){
		fc.setRI_JOINT_MIDDLE_NAME(RI_JOINT_MIDDLE_NAME.toString());
		}
		}
		if(RI_JOINT_SUFFIX!=null){
		 if(RI_JOINT_SUFFIX.toString().isEmpty()==false){
		fc.setRI_JOINT_SUFFIX(RI_JOINT_SUFFIX.toString());
		}
		}
		if(RI_CORP_CD_SALVATION!=null){
		 if(RI_CORP_CD_SALVATION.toString().isEmpty()==false){
		fc.setRI_CORP_CD_SALVATION(RI_CORP_CD_SALVATION.toString());
		}
		}
		if(RI_CORP_CD_FIRST_NAME!=null){
		 if(RI_CORP_CD_FIRST_NAME.toString().isEmpty()==false){
		fc.setRI_CORP_CD_FIRST_NAME(RI_CORP_CD_FIRST_NAME.toString());
		}
		}
		if(RI_CORP_CD_LAST_NAME!=null){
		 if(RI_CORP_CD_LAST_NAME.toString().isEmpty()==false){
		fc.setRI_CORP_CD_LAST_NAME(RI_CORP_CD_LAST_NAME.toString());
		}
		}
		if(RI_CORP_CD_MIDDLE_NAME!=null){
		 if(RI_CORP_CD_MIDDLE_NAME.toString().isEmpty()==false){
		fc.setRI_CORP_CD_MIDDLE_NAME(RI_CORP_CD_MIDDLE_NAME.toString());
		}
		}
		if(RI_CORP_CD_SUFFIX!=null){
		 if(RI_CORP_CD_SUFFIX.toString().isEmpty()==false){
		fc.setRI_CORP_CD_SUFFIX(RI_CORP_CD_SUFFIX.toString());
		}
		}
		if(RI_ACCOUNT_CLOSE_DATE!=null){
		 if(RI_ACCOUNT_CLOSE_DATE.toString().isEmpty()==false){
			 Calendar cl=Calendar.getInstance();
			 cl.setTimeInMillis(new Long(RI_ACCOUNT_CLOSE_DATE.toString()));
		fc.setRI_ACCOUNT_CLOSE_DATE(cl);
		}
		}
		if(RI_CRATE!=null){
		 if(RI_CRATE.toString().isEmpty()==false){

		fc.setRI_CRATE(new Double(RI_CRATE.toString()));
		}
		}
		
		if(WMS_COMMENTS!=null) {
		if(WMS_COMMENTS.toString().isEmpty()==false) {
			fc.setWMS_COMMENTS(WMS_COMMENTS.toString());
			}
		}
		
		/*
		 * Default setting
		 */
		fc.setIV_ENTER_UID(fuser.getSVC_UID());
		fc.setIV_ENTER_DATE(Calendar.getInstance());
		fc.setWMS_STATUS("Waiting for WorkFlow");
		
		try {
		fc=	rE_INVESTORRepository.save(fc);
		if(fc!=null) {

			json.put("msg", "Investor saved");				
		 	boolean loggerw=activityLogger.writeLog(fuser,SNO,SVL_SCREEN, SVL_DESC, "CREATE");
		 	if(loggerw==true) {
		 		logger.info("Both Record and Logs saved for Broker:"+fc.getRI_WMS_CODE());
				json.put("logs","logs are saved");
		 	}else {
		 		logger.info("Record is saved but logs can't saved due error in saving of logs");
				json.put("logs","Record is saved but logs can't saved due error in saving of logs");
		 	}
			InvestorBean ib=getJson(fc);
			return new ResponseEntity<>(ib,HttpStatus.OK);
		}else {
			json.put("msg", "Investor can not saved");
			logger.error("Investor can not saved");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
		}catch (Exception e) {
		json.put("msg", e.getMessage());
		logger.error(e.getMessage());
		return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
		
}
	
	/**
	 * Update info.
	 *
	 * @param requestBody the request body
	 * @return the response entity
	 */
	@PostMapping("/updateInvestorInfo")
    public ResponseEntity<?> updateInfo(RequestEntity<Map<String, Object>> requestBody){
	Map<String,Object>requestJson=requestBody.getBody();	
	Object RI_WMS_CODE=requestJson.get("RI_WMS_CODE");
//	Object RI_INVESTOR_CODE=requestJson.get("RI_INVESTOR_CODE");
//	Object RI_INVESTOR_TYPE=requestJson.get("RI_INVESTOR_TYPE");
	Object RI_CREATE_DATE=requestJson.get("RI_CREATE_DATE");
	Object RI_INVESTOR_NAME=requestJson.get("RI_INVESTOR_NAME");
	Object RI_ADDRESS_1=requestJson.get("RI_ADDRESS_1");
	Object RI_POST_BOX=requestJson.get("RI_POST_BOX");
	Object RI_POST_CODE=requestJson.get("RI_POST_CODE");
	Object RI_CITY=requestJson.get("RI_CITY");
	Object RI_COUNTRY=requestJson.get("RI_COUNTRY");
	Object RI_TEL_NO=requestJson.get("RI_TEL_NO");
	Object RI_MOBILE_NO=requestJson.get("RI_MOBILE_NO");
	Object RI_FAX_NO=requestJson.get("RI_FAX_NO");
	Object RI_EMAIL=requestJson.get("RI_EMAIL");
	Object RI_COMPANY_NAME=requestJson.get("RI_COMPANY_NAME");
	Object RI_COMPANY_LICENSE_NO=requestJson.get("RI_COMPANY_LICENSE_NO");
	Object RI_EXPIRY_DATE=requestJson.get("RI_EXPIRY_DATE");
	Object RI_OCCUPATION=requestJson.get("RI_OCCUPATION");
	Object RI_NATIONALITY=requestJson.get("RI_NATIONALITY");
	Object RI_DOB=requestJson.get("RI_DOB");
	Object RI_GENDER=requestJson.get("RI_GENDER");
	Object RI_JOINT_AUTHORISATION=requestJson.get("RI_JOINT_AUTHORISATION");
	Object RI_STATUS=requestJson.get("RI_STATUS");
	Object SVC_UID=requestJson.get("SVC_UID");
	Object RI_INDUSTRY=requestJson.get("RI_INDUSTRY");
	Object RI_REMARKS=requestJson.get("RI_REMARKS");
	Object RI_MARITAL_STATUS=requestJson.get("RI_MARITAL_STATUS");
	Object RI_MARITAL_DEPENDENTS=requestJson.get("RI_MARITAL_DEPENDENTS");
	Object RI_NATIONAL_IDENITY=requestJson.get("RI_NATIONAL_IDENITY");
	Object RI_PASSPORT_IPLACE=requestJson.get("RI_PASSPORT_IPLACE");
	Object RI_PASSPORT_IDATE=requestJson.get("RI_PASSPORT_IDATE");
	Object RI_PASSPORT_ID=requestJson.get("RI_PASSPORT_ID");
	Object RI_INVESTOR_NAME2=requestJson.get("RI_INVESTOR_NAME2");
	Object RI_COMPANY_NAME2=requestJson.get("RI_COMPANY_NAME2");
	Object RI_OCCUPATION2=requestJson.get("RI_OCCUPATION2");
	Object RI_NATIONALITY2=requestJson.get("RI_NATIONALITY2");
	Object RI_DOB2=requestJson.get("RI_DOB2");
	Object RI_GENDER2=requestJson.get("RI_GENDER2");
	Object RI_MARITAL_STATUS2=requestJson.get("RI_MARITAL_STATUS2");
	Object RI_MARITAL_DEPENDENTS2=requestJson.get("RI_MARITAL_DEPENDENTS2");
	Object RI_PASSPORT_IPLACE2=requestJson.get("RI_PASSPORT_IPLACE2");
	Object RI_PASSPORT_IDATE2=requestJson.get("RI_PASSPORT_IDATE2");
	Object RI_NATIONAL_IDENITY2=requestJson.get("RI_NATIONAL_IDENITY2");
	Object RI_PASSPORT_ID2=requestJson.get("RI_PASSPORT_ID2");
	Object RI_PRIMARY_CONTACT_NAME=requestJson.get("RI_PRIMARY_CONTACT_NAME");
	Object RI_OFFICE_APART_NO=requestJson.get("RI_OFFICE_APART_NO");
	Object RI_STREET=requestJson.get("RI_STREET");
	Object RI_AREA=requestJson.get("RI_AREA");
	Object RI_TEL_NO_HOME=requestJson.get("RI_TEL_NO_HOME");
	Object RI_PRIMARY_CONTACT_NAME2=requestJson.get("RI_PRIMARY_CONTACT_NAME2");
	Object RI_ADDRESS_12=requestJson.get("RI_ADDRESS_12");
	Object RI_OFFICE_APART_NO2=requestJson.get("RI_OFFICE_APART_NO2");
	Object RI_STREET2=requestJson.get("RI_STREET2");
	Object RI_AREA2=requestJson.get("RI_AREA2");
	Object RI_POST_BOX2=requestJson.get("RI_POST_BOX2");
	Object RI_CITY2=requestJson.get("RI_CITY2");
	Object RI_COUNTRY2=requestJson.get("RI_COUNTRY2");
	Object RI_POST_CODE2=requestJson.get("RI_POST_CODE2");
	Object RI_TEL_NO2=requestJson.get("RI_TEL_NO2");
	Object RI_TEL_NO_HOME2=requestJson.get("RI_TEL_NO_HOME2");
	Object RI_MOBILE_NO2=requestJson.get("RI_MOBILE_NO2");
	Object RI_FAX_NO2=requestJson.get("RI_FAX_NO2");
	Object RI_EMAIL2=requestJson.get("RI_EMAIL2");
	Object RI_EXPIRY_DATE2=requestJson.get("RI_EXPIRY_DATE2");
	Object RI_BANK_ANAME=requestJson.get("RI_BANK_ANAME");
	Object RI_BANK_ANUMBER=requestJson.get("RI_BANK_ANUMBER");
	Object RI_BANK_BANK_NAME=requestJson.get("RI_BANK_BANK_NAME");
	Object RI_BANK_CURRENCY=requestJson.get("RI_BANK_CURRENCY");
	Object RI_BANK_BRANCH=requestJson.get("RI_BANK_BRANCH");
	Object RI_BANK_COUNTRY=requestJson.get("RI_BANK_COUNTRY");
	Object RI_BANK_CITY=requestJson.get("RI_BANK_CITY");
	Object RI_NOTI_FAX=requestJson.get("RI_NOTI_FAX");
	Object RI_NOTI_SMS=requestJson.get("RI_NOTI_SMS");
	Object RI_NOTI_MAIL=requestJson.get("RI_NOTI_MAIL");
	Object RI_NOTI_EMAIL=requestJson.get("RI_NOTI_EMAIL");
	Object RI_MARGIN_TYPE=requestJson.get("RI_MARGIN_TYPE");
	Object RI_INTEREST_TYPE=requestJson.get("RI_INTEREST_TYPE");
	Object RI_RELATION_MANAGER=requestJson.get("RI_RELATION_MANAGER");
	Object RI_CORPORATE_NAME=requestJson.get("RI_CORPORATE_NAME");
	Object RI_BUSINESS_ACTIVITY=requestJson.get("RI_BUSINESS_ACTIVITY");
	Object RI_COUNTRY_ESTA=requestJson.get("RI_COUNTRY_ESTA");
	Object RI_COMMERCIAL_REGIS_NO=requestJson.get("RI_COMMERCIAL_REGIS_NO");
	Object RI_TRADE_LICENSE_NO=requestJson.get("RI_TRADE_LICENSE_NO");
	Object RI_TRADE_LICENSE_IDATE=requestJson.get("RI_TRADE_LICENSE_IDATE");
	Object RI_TRADE_LICENSE_EDATE=requestJson.get("RI_TRADE_LICENSE_EDATE");
	Object RI_AUTHORIZED_NAME=requestJson.get("RI_AUTHORIZED_NAME");
	Object RI_CAPACITY=requestJson.get("RI_CAPACITY");
	Object RI_MARGIN_CLIENT_FLAG=requestJson.get("RI_MARGIN_CLIENT_FLAG");
	Object RI_MARGIN_REPORT_CURRENCY=requestJson.get("RI_MARGIN_REPORT_CURRENCY");
	Object RI_MARGIN_AGGDATE=requestJson.get("RI_MARGIN_AGGDATE");
	Object RI_RELATION_ALMAL=requestJson.get("RI_RELATION_ALMAL");
	Object RI_REFFER_BY=requestJson.get("RI_REFFER_BY");
	Object RI_PMR_DAY=requestJson.get("RI_PMR_DAY");
	Object RI_PMR_WEEKLY=requestJson.get("RI_PMR_WEEKLY");
	Object RI_PMR_MONTH=requestJson.get("RI_PMR_MONTH");
	Object RI_MARKET_AMT=requestJson.get("RI_MARKET_AMT");
	Object RI_MARGIN_SUB_TYPE=requestJson.get("RI_MARGIN_SUB_TYPE");
	Object RI_OWN_OR_CLIENT=requestJson.get("RI_OWN_OR_CLIENT");
	Object RI_MONTH_END_REP_FLAG=requestJson.get("RI_MONTH_END_REP_FLAG");
	Object RI_INTEREST_POST=requestJson.get("RI_INTEREST_POST");
	Object RI_INTEREST_REMARKS=requestJson.get("RI_INTEREST_REMARKS");
	Object RI_ACCOUNT_CLOSE=requestJson.get("RI_ACCOUNT_CLOSE");
	Object RI_ACCOUNT_CLOSE_REMARKS=requestJson.get("RI_ACCOUNT_CLOSE_REMARKS");
	Object RI_SALVATION=requestJson.get("RI_SALVATION");
	Object RI_FIRST_NAME=requestJson.get("RI_FIRST_NAME");
	Object RI_LAST_NAME=requestJson.get("RI_LAST_NAME");
	Object RI_MIDDLE_NAME=requestJson.get("RI_MIDDLE_NAME");
	Object RI_SUFFIX=requestJson.get("RI_SUFFIX");
	Object RI_JOINT_SALVATION=requestJson.get("RI_JOINT_SALVATION");
	Object RI_JOINT_FIRST_NAME=requestJson.get("RI_JOINT_FIRST_NAME");
	Object RI_JOINT_LAST_NAME=requestJson.get("RI_JOINT_LAST_NAME");
	Object RI_JOINT_MIDDLE_NAME=requestJson.get("RI_JOINT_MIDDLE_NAME");
	Object RI_JOINT_SUFFIX=requestJson.get("RI_JOINT_SUFFIX");
	Object RI_CORP_CD_SALVATION=requestJson.get("RI_CORP_CD_SALVATION");
	Object RI_CORP_CD_FIRST_NAME=requestJson.get("RI_CORP_CD_FIRST_NAME");
	Object RI_CORP_CD_LAST_NAME=requestJson.get("RI_CORP_CD_LAST_NAME");
	Object RI_CORP_CD_MIDDLE_NAME=requestJson.get("RI_CORP_CD_MIDDLE_NAME");
	Object RI_CORP_CD_SUFFIX=requestJson.get("RI_CORP_CD_SUFFIX");
	Object RI_ACCOUNT_CLOSE_DATE=requestJson.get("RI_ACCOUNT_CLOSE_DATE");
	Object RI_CRATE=requestJson.get("RI_CRATE");
	Object createdby=requestJson.get("modifiedby");
	Object WMS_COMMENTS=requestJson.get("comment");
	Object SVL_SCREEN=requestJson.get("screentype");
	Object SVL_DESC=requestJson.get("log");
	Object status=requestJson.get("status");
	Object SNO=requestJson.get("sno");
	Map<String,Object> json=new HashMap<>();
	//System.out.println("Values - "+RI_WMS_CODE+"===="+createdby+"===="+SVL_SCREEN+"===="+SVL_DESC+"===="+status);
	if(RI_WMS_CODE==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null||status==null||SNO==null)
	{
		json.put("msg", "Please check Input json, missing some required attributes");
		logger.error("Please check Input json, missing some required attributes");
		return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
	}
	if(RI_WMS_CODE.toString().isEmpty()) {
		json.put("msg", "Please check RI_WMS_CODE is empty attribute");
		logger.error("Please check RI_WMS_CODE is empty attribute");
		return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
	}
	
	FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
	if(fuser==null)
	{
			json.put("msg", "Createdby is not valid user");
			logger.error("Createdby is not valid user");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
	}
	
//	FUND_USERS fusermodified=fundUserValidate.isValid(modifiedby.toString());
//	if(fusermodified==null)
//	{
//			json.put("msg", "modifiedby is not valid user");
//			logger.error("modifiedby is not valid user");
//			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
//	}
	
	
	RE_INVESTOR fc=rE_INVESTORRepository.findOne(new Long(RI_WMS_CODE.toString()));
	if(fc==null) {
		json.put("msg", "No investor found for this id "+RI_WMS_CODE.toString());
		logger.error("No investor found for this id "+RI_WMS_CODE.toString());
		return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
	}
	
/*
 * It will generate after work flow
 */
	
//		if(RI_INVESTOR_CODE!=null){
//		 if(RI_INVESTOR_CODE.toString().isEmpty()==false){
//		fc.setRI_INVESTOR_CODE(RI_INVESTOR_CODE.toString());
//		}
//		}
	
//		if(RI_INVESTOR_TYPE!=null){
//		 if(RI_INVESTOR_TYPE.toString().isEmpty()==false){
//		fc.setRI_INVESTOR_TYPE(RI_INVESTOR_TYPE.toString());
//		}
//		}
	
//		if(RI_CREATE_DATE!=null){
//		 if(RI_CREATE_DATE.toString().isEmpty()==false){
//	     Calendar cl=Calendar.getInstance();
//		 cl.setTimeInMillis(new Long(RI_CREATE_DATE.toString()));
//		 fc.setRI_CREATE_DATE(cl);
//		}
//		}
//		if(RI_INVESTOR_NAME!=null){
//		 if(RI_INVESTOR_NAME.toString().isEmpty()==false){
//		fc.setRI_INVESTOR_NAME(RI_INVESTOR_NAME.toString());
//		}
//		}
//		if(RI_ADDRESS_1!=null){
//		 if(RI_ADDRESS_1.toString().isEmpty()==false){
//		fc.setRI_ADDRESS_1(RI_ADDRESS_1.toString());
//		}
//		}
//		if(RI_POST_BOX!=null){
//		 if(RI_POST_BOX.toString().isEmpty()==false){
//		fc.setRI_POST_BOX(RI_POST_BOX.toString());
//		}
//		}
//		if(RI_POST_CODE!=null){
//		 if(RI_POST_CODE.toString().isEmpty()==false){
//		fc.setRI_POST_CODE(RI_POST_CODE.toString());
//		}
//		}
//		if(RI_CITY!=null){
//		 if(RI_CITY.toString().isEmpty()==false){
//		fc.setRI_CITY(RI_CITY.toString());
//		}
//		}
//		if(RI_COUNTRY!=null){
//		 if(RI_COUNTRY.toString().isEmpty()==false){
//		fc.setRI_COUNTRY(RI_COUNTRY.toString());
//		}
//		}
//		if(RI_TEL_NO!=null){
//		 if(RI_TEL_NO.toString().isEmpty()==false){
//		fc.setRI_TEL_NO(RI_TEL_NO.toString());
//		}
//		}
//		if(RI_MOBILE_NO!=null){
//		 if(RI_MOBILE_NO.toString().isEmpty()==false){
//		fc.setRI_MOBILE_NO(RI_MOBILE_NO.toString());
//		}
//		}
//		if(RI_FAX_NO!=null){
//		 if(RI_FAX_NO.toString().isEmpty()==false){
//		fc.setRI_FAX_NO(RI_FAX_NO.toString());
//		}
//		}
//		if(RI_EMAIL!=null){
//		 if(RI_EMAIL.toString().isEmpty()==false){
//		fc.setRI_EMAIL(RI_EMAIL.toString());
//		}
//		}
//		if(RI_COMPANY_NAME!=null){
//		 if(RI_COMPANY_NAME.toString().isEmpty()==false){
//		fc.setRI_COMPANY_NAME(RI_COMPANY_NAME.toString());
//		}
//		}
//		if(RI_COMPANY_LICENSE_NO!=null){
//		 if(RI_COMPANY_LICENSE_NO.toString().isEmpty()==false){
//		fc.setRI_COMPANY_LICENSE_NO(RI_COMPANY_LICENSE_NO.toString());
//		}
//		}
//		if(RI_EXPIRY_DATE!=null){
//		 if(RI_EXPIRY_DATE.toString().isEmpty()==false){
//			 Calendar cl=Calendar.getInstance();
//			 cl.setTimeInMillis(new Long(RI_EXPIRY_DATE.toString()));
//		fc.setRI_EXPIRY_DATE(cl);
//		}
//		}
//		if(RI_OCCUPATION!=null){
//		 if(RI_OCCUPATION.toString().isEmpty()==false){
//		fc.setRI_OCCUPATION(RI_OCCUPATION.toString());
//		}
//		}
//		if(RI_NATIONALITY!=null){
//		 if(RI_NATIONALITY.toString().isEmpty()==false){
//		fc.setRI_NATIONALITY(RI_NATIONALITY.toString());
//		}
//		}
//		if(RI_DOB!=null){
//		 if(RI_DOB.toString().isEmpty()==false){
//		Calendar cl=Calendar.getInstance();
//		cl.setTimeInMillis(new Long(RI_DOB.toString()));
//		fc.setRI_DOB(cl);
//		}
//		}
//		if(RI_GENDER!=null){
//		 if(RI_GENDER.toString().isEmpty()==false){
//		fc.setRI_GENDER(RI_GENDER.toString());
//		}
//		}
//		if(RI_JOINT_AUTHORISATION!=null){
//		 if(RI_JOINT_AUTHORISATION.toString().isEmpty()==false){
//		fc.setRI_JOINT_AUTHORISATION(RI_JOINT_AUTHORISATION.toString());
//		}
//		}
//		if(RI_STATUS!=null){
//		 if(RI_STATUS.toString().isEmpty()==false){
//		fc.setRI_STATUS(RI_STATUS.toString());
//		}
//		}
//		if(SVC_UID!=null){
//		 if(SVC_UID.toString().isEmpty()==false){
//		fc.setSVC_UID(SVC_UID.toString());
//		}
//		}
//		if(RI_INDUSTRY!=null){
//		 if(RI_INDUSTRY.toString().isEmpty()==false){
//		fc.setRI_INDUSTRY(RI_INDUSTRY.toString());
//		}
//		}
//		if(RI_REMARKS!=null){
//		 if(RI_REMARKS.toString().isEmpty()==false){
//		fc.setRI_REMARKS(RI_REMARKS.toString());
//		}
//		}
//		if(RI_MARITAL_STATUS!=null){
//		 if(RI_MARITAL_STATUS.toString().isEmpty()==false){
//		fc.setRI_MARITAL_STATUS(RI_MARITAL_STATUS.toString());
//		}
//		}
//		if(RI_MARITAL_DEPENDENTS!=null){
//		 if(RI_MARITAL_DEPENDENTS.toString().isEmpty()==false){
//		fc.setRI_MARITAL_DEPENDENTS(RI_MARITAL_DEPENDENTS.toString());
//		}
//		}
//		if(RI_NATIONAL_IDENITY!=null){
//		 if(RI_NATIONAL_IDENITY.toString().isEmpty()==false){
//		fc.setRI_NATIONAL_IDENITY(RI_NATIONAL_IDENITY.toString());
//		}
//		}
//		if(RI_PASSPORT_IPLACE!=null){
//		 if(RI_PASSPORT_IPLACE.toString().isEmpty()==false){
//		fc.setRI_PASSPORT_IPLACE(RI_PASSPORT_IPLACE.toString());
//		}
//		}
//		if(RI_PASSPORT_IDATE!=null){
//		 if(RI_PASSPORT_IDATE.toString().isEmpty()==false){
//			 Calendar cl=Calendar.getInstance();
//			 cl.setTimeInMillis(new Long(RI_PASSPORT_IDATE.toString()));
//		fc.setRI_PASSPORT_IDATE(cl);
//		}
//		}
//		if(RI_PASSPORT_ID!=null){
//		 if(RI_PASSPORT_ID.toString().isEmpty()==false){
//		fc.setRI_PASSPORT_ID(RI_PASSPORT_ID.toString());
//		}
//		}
//		if(RI_INVESTOR_NAME2!=null){
//		 if(RI_INVESTOR_NAME2.toString().isEmpty()==false){
//		fc.setRI_INVESTOR_NAME2(RI_INVESTOR_NAME2.toString());
//		}
//		}
//		if(RI_COMPANY_NAME2!=null){
//		 if(RI_COMPANY_NAME2.toString().isEmpty()==false){
//		fc.setRI_COMPANY_NAME2(RI_COMPANY_NAME2.toString());
//		}
//		}
//		if(RI_OCCUPATION2!=null){
//		 if(RI_OCCUPATION2.toString().isEmpty()==false){
//		fc.setRI_OCCUPATION2(RI_OCCUPATION2.toString());
//		}
//		}
//		if(RI_NATIONALITY2!=null){
//		 if(RI_NATIONALITY2.toString().isEmpty()==false){
//		fc.setRI_NATIONALITY2(RI_NATIONALITY2.toString());
//		}
//		}
//		if(RI_DOB2!=null){
//		 if(RI_DOB2.toString().isEmpty()==false){
//			 Calendar cl=Calendar.getInstance();
//			 cl.setTimeInMillis(new Long(RI_DOB2.toString()));
//			 fc.setRI_DOB2(cl);
//		}
//		}
//		if(RI_GENDER2!=null){
//		 if(RI_GENDER2.toString().isEmpty()==false){
//		fc.setRI_GENDER2(RI_GENDER2.toString());
//		}
//		}
//		if(RI_MARITAL_STATUS2!=null){
//		 if(RI_MARITAL_STATUS2.toString().isEmpty()==false){
//		fc.setRI_MARITAL_STATUS2(RI_MARITAL_STATUS2.toString());
//		}
//		}
//		if(RI_MARITAL_DEPENDENTS2!=null){
//		 if(RI_MARITAL_DEPENDENTS2.toString().isEmpty()==false){
//		fc.setRI_MARITAL_DEPENDENTS2(RI_MARITAL_DEPENDENTS2.toString());
//		}
//		}
//		if(RI_PASSPORT_IPLACE2!=null){
//		 if(RI_PASSPORT_IPLACE2.toString().isEmpty()==false){
//		fc.setRI_PASSPORT_IPLACE2(RI_PASSPORT_IPLACE2.toString());
//		}
//		}
//		if(RI_PASSPORT_IDATE2!=null){
//		 if(RI_PASSPORT_IDATE2.toString().isEmpty()==false){
//			 Calendar cl=Calendar.getInstance();
//			 cl.setTimeInMillis(new Long(RI_PASSPORT_IDATE2.toString()));
//		fc.setRI_PASSPORT_IDATE2(cl);
//		}
//		}
//		if(RI_NATIONAL_IDENITY2!=null){
//		 if(RI_NATIONAL_IDENITY2.toString().isEmpty()==false){
//		fc.setRI_NATIONAL_IDENITY2(RI_NATIONAL_IDENITY2.toString());
//		}
//		}
//		if(RI_PASSPORT_ID2!=null){
//		 if(RI_PASSPORT_ID2.toString().isEmpty()==false){
//		fc.setRI_PASSPORT_ID2(RI_PASSPORT_ID2.toString());
//		}
//		}
//		if(RI_PRIMARY_CONTACT_NAME!=null){
//		 if(RI_PRIMARY_CONTACT_NAME.toString().isEmpty()==false){
//		fc.setRI_PRIMARY_CONTACT_NAME(RI_PRIMARY_CONTACT_NAME.toString());
//		}
//		}
//		if(RI_OFFICE_APART_NO!=null){
//		 if(RI_OFFICE_APART_NO.toString().isEmpty()==false){
//		fc.setRI_OFFICE_APART_NO(RI_OFFICE_APART_NO.toString());
//		}
//		}
//		if(RI_STREET!=null){
//		 if(RI_STREET.toString().isEmpty()==false){
//		fc.setRI_STREET(RI_STREET.toString());
//		}
//		}
//		if(RI_AREA!=null){
//		 if(RI_AREA.toString().isEmpty()==false){
//		fc.setRI_AREA(RI_AREA.toString());
//		}
//		}
//		if(RI_TEL_NO_HOME!=null){
//		 if(RI_TEL_NO_HOME.toString().isEmpty()==false){
//		fc.setRI_TEL_NO_HOME(RI_TEL_NO_HOME.toString());
//		}
//		}
//		if(RI_PRIMARY_CONTACT_NAME2!=null){
//		 if(RI_PRIMARY_CONTACT_NAME2.toString().isEmpty()==false){
//		fc.setRI_PRIMARY_CONTACT_NAME2(RI_PRIMARY_CONTACT_NAME2.toString());
//		}
//		}
//		if(RI_ADDRESS_12!=null){
//		 if(RI_ADDRESS_12.toString().isEmpty()==false){
//		fc.setRI_ADDRESS_12(RI_ADDRESS_12.toString());
//		}
//		}
//		if(RI_OFFICE_APART_NO2!=null){
//		 if(RI_OFFICE_APART_NO2.toString().isEmpty()==false){
//		fc.setRI_OFFICE_APART_NO2(RI_OFFICE_APART_NO2.toString());
//		}
//		}
//		if(RI_STREET2!=null){
//		 if(RI_STREET2.toString().isEmpty()==false){
//		fc.setRI_STREET2(RI_STREET2.toString());
//		}
//		}
//		if(RI_AREA2!=null){
//		 if(RI_AREA2.toString().isEmpty()==false){
//		fc.setRI_AREA2(RI_AREA2.toString());
//		}
//		}
//		if(RI_POST_BOX2!=null){
//		 if(RI_POST_BOX2.toString().isEmpty()==false){
//		fc.setRI_POST_BOX2(RI_POST_BOX2.toString());
//		}
//		}
//		if(RI_CITY2!=null){
//		 if(RI_CITY2.toString().isEmpty()==false){
//		fc.setRI_CITY2(RI_CITY2.toString());
//		}
//		}
//		if(RI_COUNTRY2!=null){
//		 if(RI_COUNTRY2.toString().isEmpty()==false){
//		fc.setRI_COUNTRY2(RI_COUNTRY2.toString());
//		}
//		}
//		if(RI_POST_CODE2!=null){
//		 if(RI_POST_CODE2.toString().isEmpty()==false){
//		fc.setRI_POST_CODE2(RI_POST_CODE2.toString());
//		}
//		}
//		if(RI_TEL_NO2!=null){
//		 if(RI_TEL_NO2.toString().isEmpty()==false){
//		fc.setRI_TEL_NO2(RI_TEL_NO2.toString());
//		}
//		}
//		if(RI_TEL_NO_HOME2!=null){
//		 if(RI_TEL_NO_HOME2.toString().isEmpty()==false){
//		fc.setRI_TEL_NO_HOME2(RI_TEL_NO_HOME2.toString());
//		}
//		}
//		if(RI_MOBILE_NO2!=null){
//		 if(RI_MOBILE_NO2.toString().isEmpty()==false){
//		fc.setRI_MOBILE_NO2(RI_MOBILE_NO2.toString());
//		}
//		}
//		if(RI_FAX_NO2!=null){
//		 if(RI_FAX_NO2.toString().isEmpty()==false){
//		fc.setRI_FAX_NO2(RI_FAX_NO2.toString());
//		}
//		}
//		if(RI_EMAIL2!=null){
//		 if(RI_EMAIL2.toString().isEmpty()==false){
//		fc.setRI_EMAIL2(RI_EMAIL2.toString());
//		}
//		}
//		if(RI_EXPIRY_DATE2!=null){
//		 if(RI_EXPIRY_DATE2.toString().isEmpty()==false){
//			 Calendar cl=Calendar.getInstance();
//			 cl.setTimeInMillis(new Long(RI_EXPIRY_DATE2.toString()));
//		fc.setRI_EXPIRY_DATE2(cl);
//		}
//		}
//		if(RI_BANK_ANAME!=null){
//		 if(RI_BANK_ANAME.toString().isEmpty()==false){
//		fc.setRI_BANK_ANAME(RI_BANK_ANAME.toString());
//		}
//		}
//		if(RI_BANK_ANUMBER!=null){
//		 if(RI_BANK_ANUMBER.toString().isEmpty()==false){
//		fc.setRI_BANK_ANUMBER(RI_BANK_ANUMBER.toString());
//		}
//		}
//		if(RI_BANK_BANK_NAME!=null){
//		 if(RI_BANK_BANK_NAME.toString().isEmpty()==false){
//		fc.setRI_BANK_BANK_NAME(RI_BANK_BANK_NAME.toString());
//		}
//		}
//		if(RI_BANK_CURRENCY!=null){
//		 if(RI_BANK_CURRENCY.toString().isEmpty()==false){
//		fc.setRI_BANK_CURRENCY(RI_BANK_CURRENCY.toString());
//		}
//		}
//		if(RI_BANK_BRANCH!=null){
//		 if(RI_BANK_BRANCH.toString().isEmpty()==false){
//		fc.setRI_BANK_BRANCH(RI_BANK_BRANCH.toString());
//		}
//		}
//		if(RI_BANK_COUNTRY!=null){
//		 if(RI_BANK_COUNTRY.toString().isEmpty()==false){
//		fc.setRI_BANK_COUNTRY(RI_BANK_COUNTRY.toString());
//		}
//		}
//		if(RI_BANK_CITY!=null){
//		 if(RI_BANK_CITY.toString().isEmpty()==false){
//		fc.setRI_BANK_CITY(RI_BANK_CITY.toString());
//		}
//		}
//		if(RI_NOTI_FAX!=null){
//		 if(RI_NOTI_FAX.toString().isEmpty()==false){
//		fc.setRI_NOTI_FAX(RI_NOTI_FAX.toString());
//		}
//		}
//		if(RI_NOTI_SMS!=null){
//		 if(RI_NOTI_SMS.toString().isEmpty()==false){
//		fc.setRI_NOTI_SMS(RI_NOTI_SMS.toString());
//		}
//		}
//		if(RI_NOTI_MAIL!=null){
//		 if(RI_NOTI_MAIL.toString().isEmpty()==false){
//		fc.setRI_NOTI_MAIL(RI_NOTI_MAIL.toString());
//		}
//		}
//		if(RI_NOTI_EMAIL!=null){
//		 if(RI_NOTI_EMAIL.toString().isEmpty()==false){
//		fc.setRI_NOTI_EMAIL(RI_NOTI_EMAIL.toString());
//		}
//		}
//		if(RI_MARGIN_TYPE!=null){
//		 if(RI_MARGIN_TYPE.toString().isEmpty()==false){
//		fc.setRI_MARGIN_TYPE(new Long(RI_MARGIN_TYPE.toString()));
//		}
//		}
//		if(RI_INTEREST_TYPE!=null){
//		 if(RI_INTEREST_TYPE.toString().isEmpty()==false){
//		fc.setRI_INTEREST_TYPE(new Long(RI_INTEREST_TYPE.toString()));
//		}
//		}
//		if(RI_RELATION_MANAGER!=null){
//		if(RI_RELATION_MANAGER.toString().isEmpty()==false){
//		fc.setRI_RELATION_MANAGER(new Long(RI_RELATION_MANAGER.toString()));
//		}
//		}
//		
////		if(RI_CORPORATE_NAME!=null){
////		 if(RI_CORPORATE_NAME.toString().isEmpty()==false){
////		fc.setRI_CORPORATE_NAME(RI_CORPORATE_NAME.toString());
////		}
////		}
//		
//		if(RI_BUSINESS_ACTIVITY!=null){
//		 if(RI_BUSINESS_ACTIVITY.toString().isEmpty()==false){
//		fc.setRI_BUSINESS_ACTIVITY(RI_BUSINESS_ACTIVITY.toString());
//		}
//		}
//		if(RI_COUNTRY_ESTA!=null){
//		 if(RI_COUNTRY_ESTA.toString().isEmpty()==false){
//		fc.setRI_COUNTRY_ESTA(RI_COUNTRY_ESTA.toString());
//		}
//		}
//		if(RI_COMMERCIAL_REGIS_NO!=null){
//		 if(RI_COMMERCIAL_REGIS_NO.toString().isEmpty()==false){
//		fc.setRI_COMMERCIAL_REGIS_NO(RI_COMMERCIAL_REGIS_NO.toString());
//		}
//		}
//		if(RI_TRADE_LICENSE_NO!=null){
//		 if(RI_TRADE_LICENSE_NO.toString().isEmpty()==false){
//		fc.setRI_TRADE_LICENSE_NO(RI_TRADE_LICENSE_NO.toString());
//		}
//		}
//		if(RI_TRADE_LICENSE_IDATE!=null){
//		 if(RI_TRADE_LICENSE_IDATE.toString().isEmpty()==false){
//			 Calendar cl=Calendar.getInstance();
//			 cl.setTimeInMillis(new Long(RI_TRADE_LICENSE_IDATE.toString()));
//		fc.setRI_TRADE_LICENSE_IDATE(cl);
//		}
//		}
//		if(RI_TRADE_LICENSE_EDATE!=null){
//		 if(RI_TRADE_LICENSE_EDATE.toString().isEmpty()==false){
//			 Calendar cl=Calendar.getInstance();
//			 cl.setTimeInMillis(new Long(RI_TRADE_LICENSE_EDATE.toString()));
//		fc.setRI_TRADE_LICENSE_EDATE(cl);
//		}
//		}
//		if(RI_AUTHORIZED_NAME!=null){
//		 if(RI_AUTHORIZED_NAME.toString().isEmpty()==false){
//		fc.setRI_AUTHORIZED_NAME(RI_AUTHORIZED_NAME.toString());
//		}
//		}
//		if(RI_CAPACITY!=null){
//		 if(RI_CAPACITY.toString().isEmpty()==false){
//		fc.setRI_CAPACITY(RI_CAPACITY.toString());
//		}
//		}
//		if(RI_MARGIN_CLIENT_FLAG!=null){
//		 if(RI_MARGIN_CLIENT_FLAG.toString().isEmpty()==false){
//		fc.setRI_MARGIN_CLIENT_FLAG(RI_MARGIN_CLIENT_FLAG.toString());
//		}
//		}
//		if(RI_MARGIN_REPORT_CURRENCY!=null){
//		 if(RI_MARGIN_REPORT_CURRENCY.toString().isEmpty()==false){
//		fc.setRI_MARGIN_REPORT_CURRENCY(new Long(RI_MARGIN_REPORT_CURRENCY.toString()));
//		}
//		}
//		if(RI_MARGIN_AGGDATE!=null){
//		 if(RI_MARGIN_AGGDATE.toString().isEmpty()==false){
//			 Calendar cl=Calendar.getInstance();
//			 cl.setTimeInMillis(new Long(RI_MARGIN_AGGDATE.toString()));
//		fc.setRI_MARGIN_AGGDATE(cl);
//		}
//		}
//		if(RI_RELATION_ALMAL!=null){
//		 if(RI_RELATION_ALMAL.toString().isEmpty()==false){
//		fc.setRI_RELATION_ALMAL(RI_RELATION_ALMAL.toString());
//		}
//		}
//		if(RI_REFFER_BY!=null){
//		 if(RI_REFFER_BY.toString().isEmpty()==false){
//		fc.setRI_REFFER_BY(RI_REFFER_BY.toString());
//		}
//		}
//		if(RI_PMR_DAY!=null){
//		 if(RI_PMR_DAY.toString().isEmpty()==false){
//		fc.setRI_PMR_DAY(RI_PMR_DAY.toString());
//		}
//		}
//		if(RI_PMR_WEEKLY!=null){
//		 if(RI_PMR_WEEKLY.toString().isEmpty()==false){
//		fc.setRI_PMR_WEEKLY(RI_PMR_WEEKLY.toString());
//		}
//		}
//		if(RI_PMR_MONTH!=null){
//		 if(RI_PMR_MONTH.toString().isEmpty()==false){
//		fc.setRI_PMR_MONTH(RI_PMR_MONTH.toString());
//		}
//		}
//		if(RI_MARKET_AMT!=null){
//		 if(RI_MARKET_AMT.toString().isEmpty()==false){
//		fc.setRI_MARKET_AMT(new Double(RI_MARKET_AMT.toString()));
//		}
//		}
//		if(RI_MARGIN_SUB_TYPE!=null){
//		 if(RI_MARGIN_SUB_TYPE.toString().isEmpty()==false){
//		fc.setRI_MARGIN_SUB_TYPE(new Long(RI_MARGIN_SUB_TYPE.toString()));
//		}
//		}
//		if(RI_OWN_OR_CLIENT!=null){
//		 if(RI_OWN_OR_CLIENT.toString().isEmpty()==false){
//		fc.setRI_OWN_OR_CLIENT(RI_OWN_OR_CLIENT.toString());
//		}
//		}
//		if(RI_MONTH_END_REP_FLAG!=null){
//		 if(RI_MONTH_END_REP_FLAG.toString().isEmpty()==false){
//		fc.setRI_MONTH_END_REP_FLAG(RI_MONTH_END_REP_FLAG.toString());
//		}
//		}
//		if(RI_INTEREST_POST!=null){
//		 if(RI_INTEREST_POST.toString().isEmpty()==false){
//		fc.setRI_INTEREST_POST(RI_INTEREST_POST.toString());
//		}
//		}
//		if(RI_INTEREST_REMARKS!=null){
//		 if(RI_INTEREST_REMARKS.toString().isEmpty()==false){
//		fc.setRI_INTEREST_REMARKS(RI_INTEREST_REMARKS.toString());
//		}
//		}
//		if(RI_ACCOUNT_CLOSE!=null){
//		 if(RI_ACCOUNT_CLOSE.toString().isEmpty()==false){
//		fc.setRI_ACCOUNT_CLOSE(RI_ACCOUNT_CLOSE.toString());
//		}
//		}
//		if(RI_ACCOUNT_CLOSE_REMARKS!=null){
//		 if(RI_ACCOUNT_CLOSE_REMARKS.toString().isEmpty()==false){
//		fc.setRI_ACCOUNT_CLOSE_REMARKS(RI_ACCOUNT_CLOSE_REMARKS.toString());
//		}
//		}
//		if(RI_SALVATION!=null){
//		 if(RI_SALVATION.toString().isEmpty()==false){
//		fc.setRI_SALVATION(RI_SALVATION.toString());
//		}
//		}
//		if(RI_FIRST_NAME!=null){
//		 if(RI_FIRST_NAME.toString().isEmpty()==false){
//		fc.setRI_FIRST_NAME(RI_FIRST_NAME.toString());
//		}
//		}
//		if(RI_LAST_NAME!=null){
//		 if(RI_LAST_NAME.toString().isEmpty()==false){
//		fc.setRI_LAST_NAME(RI_LAST_NAME.toString());
//		}
//		}
//		if(RI_MIDDLE_NAME!=null){
//		 if(RI_MIDDLE_NAME.toString().isEmpty()==false){
//		fc.setRI_MIDDLE_NAME(RI_MIDDLE_NAME.toString());
//		}
//		}
//		if(RI_SUFFIX!=null){
//		 if(RI_SUFFIX.toString().isEmpty()==false){
//		fc.setRI_SUFFIX(RI_SUFFIX.toString());
//		}
//		}
//		if(RI_JOINT_SALVATION!=null){
//		 if(RI_JOINT_SALVATION.toString().isEmpty()==false){
//		fc.setRI_JOINT_SALVATION(RI_JOINT_SALVATION.toString());
//		}
//		}
//		if(RI_JOINT_FIRST_NAME!=null){
//		 if(RI_JOINT_FIRST_NAME.toString().isEmpty()==false){
//		fc.setRI_JOINT_FIRST_NAME(RI_JOINT_FIRST_NAME.toString());
//		}
//		}
//		if(RI_JOINT_LAST_NAME!=null){
//		 if(RI_JOINT_LAST_NAME.toString().isEmpty()==false){
//		fc.setRI_JOINT_LAST_NAME(RI_JOINT_LAST_NAME.toString());
//		}
//		}
//		if(RI_JOINT_MIDDLE_NAME!=null){
//		 if(RI_JOINT_MIDDLE_NAME.toString().isEmpty()==false){
//		fc.setRI_JOINT_MIDDLE_NAME(RI_JOINT_MIDDLE_NAME.toString());
//		}
//		}
//		if(RI_JOINT_SUFFIX!=null){
//		 if(RI_JOINT_SUFFIX.toString().isEmpty()==false){
//		fc.setRI_JOINT_SUFFIX(RI_JOINT_SUFFIX.toString());
//		}
//		}
//		if(RI_CORP_CD_SALVATION!=null){
//		 if(RI_CORP_CD_SALVATION.toString().isEmpty()==false){
//		fc.setRI_CORP_CD_SALVATION(RI_CORP_CD_SALVATION.toString());
//		}
//		}
//		if(RI_CORP_CD_FIRST_NAME!=null){
//		 if(RI_CORP_CD_FIRST_NAME.toString().isEmpty()==false){
//		fc.setRI_CORP_CD_FIRST_NAME(RI_CORP_CD_FIRST_NAME.toString());
//		}
//		}
//		if(RI_CORP_CD_LAST_NAME!=null){
//		 if(RI_CORP_CD_LAST_NAME.toString().isEmpty()==false){
//		fc.setRI_CORP_CD_LAST_NAME(RI_CORP_CD_LAST_NAME.toString());
//		}
//		}
//		if(RI_CORP_CD_MIDDLE_NAME!=null){
//		 if(RI_CORP_CD_MIDDLE_NAME.toString().isEmpty()==false){
//		fc.setRI_CORP_CD_MIDDLE_NAME(RI_CORP_CD_MIDDLE_NAME.toString());
//		}
//		}
//		if(RI_CORP_CD_SUFFIX!=null){
//		 if(RI_CORP_CD_SUFFIX.toString().isEmpty()==false){
//		fc.setRI_CORP_CD_SUFFIX(RI_CORP_CD_SUFFIX.toString());
//		}
//		}
//		if(RI_ACCOUNT_CLOSE_DATE!=null){
//		 if(RI_ACCOUNT_CLOSE_DATE.toString().isEmpty()==false){
//			 Calendar cl=Calendar.getInstance();
//			 cl.setTimeInMillis(new Long(RI_ACCOUNT_CLOSE_DATE.toString()));
//		fc.setRI_ACCOUNT_CLOSE_DATE(cl);
//		}
//		}
//		if(RI_CRATE!=null){
//		 if(RI_CRATE.toString().isEmpty()==false){
//
//		fc.setRI_CRATE(new Double(RI_CRATE.toString()));
//		}
//		}
//		
//		if(WMS_COMMENTS!=null) {
//		if(WMS_COMMENTS.toString().isEmpty()==false) {
//			fc.setWMS_COMMENTS(WMS_COMMENTS.toString());
//			}
//		}
	/*
	 * 
	 * Set Date 23-03-2018
	 * 
	 */
	
	//if(RI_WMS_CODE!=null){
//	 fc.setRI_WMS_CODE(RI_WMS_CODE.toString());
//	}
	//if(RI_INVESTOR_CODE!=null){
//	 fc.setRI_INVESTOR_CODE(RI_INVESTOR_CODE.toString());
//	}
//	if(RI_INVESTOR_TYPE!=null){
//	 fc.setRI_INVESTOR_TYPE(RI_INVESTOR_TYPE.toString());
//	}
	if(RI_CREATE_DATE!=null){
		if(RI_CREATE_DATE.toString().isEmpty()==false) {
			Calendar cl=Calendar.getInstance();
			cl.setTimeInMillis(new Long(RI_CREATE_DATE.toString()));
			fc.setRI_CREATE_DATE(cl);
		}else {
			 fc.setRI_CREATE_DATE(null);
		}
		
	}
	if(RI_INVESTOR_NAME!=null){
	 fc.setRI_INVESTOR_NAME(RI_INVESTOR_NAME.toString());
	}
	if(RI_ADDRESS_1!=null){
	 fc.setRI_ADDRESS_1(RI_ADDRESS_1.toString());
	}
	if(RI_POST_BOX!=null){
	 fc.setRI_POST_BOX(RI_POST_BOX.toString());
	}
	if(RI_POST_CODE!=null){
	 fc.setRI_POST_CODE(RI_POST_CODE.toString());
	}
	if(RI_CITY!=null){
	 fc.setRI_CITY(RI_CITY.toString());
	}
	if(RI_COUNTRY!=null){
	 fc.setRI_COUNTRY(RI_COUNTRY.toString());
	}
	if(RI_TEL_NO!=null){
	 fc.setRI_TEL_NO(RI_TEL_NO.toString());
	}
	if(RI_MOBILE_NO!=null){
	 fc.setRI_MOBILE_NO(RI_MOBILE_NO.toString());
	}
	if(RI_FAX_NO!=null){
	 fc.setRI_FAX_NO(RI_FAX_NO.toString());
	}
	if(RI_EMAIL!=null){
	 fc.setRI_EMAIL(RI_EMAIL.toString());
	}
	if(RI_COMPANY_NAME!=null){
	 fc.setRI_COMPANY_NAME(RI_COMPANY_NAME.toString());
	}
	if(RI_COMPANY_LICENSE_NO!=null){
	 fc.setRI_COMPANY_LICENSE_NO(RI_COMPANY_LICENSE_NO.toString());
	}
	if(RI_EXPIRY_DATE!=null){
		if(RI_EXPIRY_DATE.toString().isEmpty()==false) {
			Calendar cl=Calendar.getInstance();
			cl.setTimeInMillis(new Long(RI_EXPIRY_DATE.toString()));
			fc.setRI_EXPIRY_DATE(cl);
		}else {
	       fc.setRI_EXPIRY_DATE(null);
		}
	}
	if(RI_OCCUPATION!=null){
	 fc.setRI_OCCUPATION(RI_OCCUPATION.toString());
	}
	if(RI_NATIONALITY!=null){
	 fc.setRI_NATIONALITY(RI_NATIONALITY.toString());
	}
	if(RI_DOB!=null){
		if(RI_DOB.toString().isEmpty()==false) {
			Calendar cl=Calendar.getInstance();
			cl.setTimeInMillis(new Long(RI_DOB.toString()));
			fc.setRI_DOB(cl);
		}else {
			 fc.setRI_DOB(null);
		}
	
	}
	if(RI_GENDER!=null){
	 fc.setRI_GENDER(RI_GENDER.toString());
	}
	if(RI_JOINT_AUTHORISATION!=null){
	 fc.setRI_JOINT_AUTHORISATION(RI_JOINT_AUTHORISATION.toString());
	}
	if(RI_STATUS!=null){
	 fc.setRI_STATUS(RI_STATUS.toString());
	}
	if(SVC_UID!=null){
	 fc.setSVC_UID(SVC_UID.toString());
	}
	if(RI_INDUSTRY!=null){
	 fc.setRI_INDUSTRY(RI_INDUSTRY.toString());
	}
	if(RI_REMARKS!=null){
	 fc.setRI_REMARKS(RI_REMARKS.toString());
	}
	if(RI_MARITAL_STATUS!=null){
	 fc.setRI_MARITAL_STATUS(RI_MARITAL_STATUS.toString());
	}
	if(RI_MARITAL_DEPENDENTS!=null){
	 fc.setRI_MARITAL_DEPENDENTS(RI_MARITAL_DEPENDENTS.toString());
	}
	if(RI_NATIONAL_IDENITY!=null){
	 fc.setRI_NATIONAL_IDENITY(RI_NATIONAL_IDENITY.toString());
	}
	if(RI_PASSPORT_IPLACE!=null){
	 fc.setRI_PASSPORT_IPLACE(RI_PASSPORT_IPLACE.toString());
	}
	if(RI_PASSPORT_IDATE!=null){
	  if(RI_PASSPORT_IDATE.toString().isEmpty()==false) {
		  Calendar cl=Calendar.getInstance();
		  cl.setTimeInMillis(new Long(RI_PASSPORT_IDATE.toString()));
		  fc.setRI_PASSPORT_IDATE(cl);
	  }else {
		  fc.setRI_PASSPORT_IDATE(null);
	  }
	}
	if(RI_PASSPORT_ID!=null){
	 fc.setRI_PASSPORT_ID(RI_PASSPORT_ID.toString());
	}
	if(RI_INVESTOR_NAME2!=null){
	 fc.setRI_INVESTOR_NAME2(RI_INVESTOR_NAME2.toString());
	}
	if(RI_COMPANY_NAME2!=null){
	 fc.setRI_COMPANY_NAME2(RI_COMPANY_NAME2.toString());
	}
	if(RI_OCCUPATION2!=null){
	 fc.setRI_OCCUPATION2(RI_OCCUPATION2.toString());
	}
	if(RI_NATIONALITY2!=null){
	 fc.setRI_NATIONALITY2(RI_NATIONALITY2.toString());
	}
	if(RI_DOB2!=null){
	if(RI_DOB2.toString().isEmpty()==false) {
		Calendar cl=Calendar.getInstance();
		cl.setTimeInMillis(new Long(RI_DOB2.toString()));
		 fc.setRI_DOB2(cl);
	}else {
		 fc.setRI_DOB2(null);
	}
	}
	if(RI_GENDER2!=null){
	 fc.setRI_GENDER2(RI_GENDER2.toString());
	}
	if(RI_MARITAL_STATUS2!=null){
	 fc.setRI_MARITAL_STATUS2(RI_MARITAL_STATUS2.toString());
	}
	if(RI_MARITAL_DEPENDENTS2!=null){
	 fc.setRI_MARITAL_DEPENDENTS2(RI_MARITAL_DEPENDENTS2.toString());
	}
	if(RI_PASSPORT_IPLACE2!=null){
	 fc.setRI_PASSPORT_IPLACE2(RI_PASSPORT_IPLACE2.toString());
	}
	if(RI_PASSPORT_IDATE2!=null){
	if(RI_PASSPORT_IDATE2.toString().isEmpty()==false) {
		Calendar cl=Calendar.getInstance();
		cl.setTimeInMillis(new Long(RI_PASSPORT_IDATE2.toString()));
		 fc.setRI_PASSPORT_IDATE2(cl);
	}else {
		 fc.setRI_PASSPORT_IDATE2(null);
	}
	}
	if(RI_NATIONAL_IDENITY2!=null){
	 fc.setRI_NATIONAL_IDENITY2(RI_NATIONAL_IDENITY2.toString());
	}
	if(RI_PASSPORT_ID2!=null){
	 fc.setRI_PASSPORT_ID2(RI_PASSPORT_ID2.toString());
	}
	if(RI_PRIMARY_CONTACT_NAME!=null){
	 fc.setRI_PRIMARY_CONTACT_NAME(RI_PRIMARY_CONTACT_NAME.toString());
	}
	if(RI_OFFICE_APART_NO!=null){
	 fc.setRI_OFFICE_APART_NO(RI_OFFICE_APART_NO.toString());
	}
	if(RI_STREET!=null){
	 fc.setRI_STREET(RI_STREET.toString());
	}
	if(RI_AREA!=null){
	 fc.setRI_AREA(RI_AREA.toString());
	}
	if(RI_TEL_NO_HOME!=null){
	 fc.setRI_TEL_NO_HOME(RI_TEL_NO_HOME.toString());
	}
	if(RI_PRIMARY_CONTACT_NAME2!=null){
	 fc.setRI_PRIMARY_CONTACT_NAME2(RI_PRIMARY_CONTACT_NAME2.toString());
	}
	if(RI_ADDRESS_12!=null){
	 fc.setRI_ADDRESS_12(RI_ADDRESS_12.toString());
	}
	if(RI_OFFICE_APART_NO2!=null){
	 fc.setRI_OFFICE_APART_NO2(RI_OFFICE_APART_NO2.toString());
	}
	if(RI_STREET2!=null){
	 fc.setRI_STREET2(RI_STREET2.toString());
	}
	if(RI_AREA2!=null){
	 fc.setRI_AREA2(RI_AREA2.toString());
	}
	if(RI_POST_BOX2!=null){
	 fc.setRI_POST_BOX2(RI_POST_BOX2.toString());
	}
	if(RI_CITY2!=null){
	 fc.setRI_CITY2(RI_CITY2.toString());
	}
	if(RI_COUNTRY2!=null){
	 fc.setRI_COUNTRY2(RI_COUNTRY2.toString());
	}
	if(RI_POST_CODE2!=null){
	 fc.setRI_POST_CODE2(RI_POST_CODE2.toString());
	}
	if(RI_TEL_NO2!=null){
	 fc.setRI_TEL_NO2(RI_TEL_NO2.toString());
	}
	if(RI_TEL_NO_HOME2!=null){
	 fc.setRI_TEL_NO_HOME2(RI_TEL_NO_HOME2.toString());
	}
	if(RI_MOBILE_NO2!=null){
	 fc.setRI_MOBILE_NO2(RI_MOBILE_NO2.toString());
	}
	if(RI_FAX_NO2!=null){
	 fc.setRI_FAX_NO2(RI_FAX_NO2.toString());
	}
	if(RI_EMAIL2!=null){
	 fc.setRI_EMAIL2(RI_EMAIL2.toString());
	}
	if(RI_EXPIRY_DATE2!=null){
	if(RI_EXPIRY_DATE2.toString().isEmpty()==false) {
		Calendar cl=Calendar.getInstance();
		cl.setTimeInMillis(new Long(RI_EXPIRY_DATE2.toString()));
		 fc.setRI_EXPIRY_DATE2(cl);
	}else {
		 fc.setRI_EXPIRY_DATE2(null);
	}
	}
	if(RI_BANK_ANAME!=null){
	 fc.setRI_BANK_ANAME(RI_BANK_ANAME.toString());
	}
	if(RI_BANK_ANUMBER!=null){
	 fc.setRI_BANK_ANUMBER(RI_BANK_ANUMBER.toString());
	}
	if(RI_BANK_BANK_NAME!=null){
	 fc.setRI_BANK_BANK_NAME(RI_BANK_BANK_NAME.toString());
	}
	if(RI_BANK_CURRENCY!=null){
	 fc.setRI_BANK_CURRENCY(RI_BANK_CURRENCY.toString());
	}
	if(RI_BANK_BRANCH!=null){
	 fc.setRI_BANK_BRANCH(RI_BANK_BRANCH.toString());
	}
	if(RI_BANK_COUNTRY!=null){
	 fc.setRI_BANK_COUNTRY(RI_BANK_COUNTRY.toString());
	}
	if(RI_BANK_CITY!=null){
	 fc.setRI_BANK_CITY(RI_BANK_CITY.toString());
	}
	if(RI_NOTI_FAX!=null){
	 fc.setRI_NOTI_FAX(RI_NOTI_FAX.toString());
	}
	if(RI_NOTI_SMS!=null){
	 fc.setRI_NOTI_SMS(RI_NOTI_SMS.toString());
	}
	if(RI_NOTI_MAIL!=null){
	 fc.setRI_NOTI_MAIL(RI_NOTI_MAIL.toString());
	}
	if(RI_NOTI_EMAIL!=null){
	 fc.setRI_NOTI_EMAIL(RI_NOTI_EMAIL.toString());
	}
	if(RI_MARGIN_TYPE!=null){
		if(RI_MARGIN_TYPE.toString().isEmpty()==false) {
			 fc.setRI_MARGIN_TYPE(new Long(RI_MARGIN_TYPE.toString()));
		}else {
			 fc.setRI_MARGIN_TYPE(null);
		}
	
	}
	if(RI_INTEREST_TYPE!=null){
	 if(RI_INTEREST_TYPE.toString().isEmpty()==false)
	 {
		 fc.setRI_INTEREST_TYPE(new Long(RI_INTEREST_TYPE.toString()));
	 }else {
		 fc.setRI_INTEREST_TYPE(null);
	 }
	}
	if(RI_RELATION_MANAGER!=null){
	 if(RI_RELATION_MANAGER.toString().isEmpty()==false) {
		 fc.setRI_RELATION_MANAGER(new Long(RI_RELATION_MANAGER.toString()));
	 }else {
		 fc.setRI_RELATION_MANAGER(null);
	 }
	}
	if(RI_CORPORATE_NAME!=null){
		  if(RI_CORPORATE_NAME.toString().isEmpty()==false) {
			  fc.setRI_CORPORATE_NAME(RI_CORPORATE_NAME.toString().trim());
		  }else {
			  fc.setRI_CORPORATE_NAME(null); 
		  }
		}
	if(RI_BUSINESS_ACTIVITY!=null){
	 fc.setRI_BUSINESS_ACTIVITY(RI_BUSINESS_ACTIVITY.toString());
	}
	if(RI_COUNTRY_ESTA!=null){
	 fc.setRI_COUNTRY_ESTA(RI_COUNTRY_ESTA.toString());
	}
	if(RI_COMMERCIAL_REGIS_NO!=null){
	 fc.setRI_COMMERCIAL_REGIS_NO(RI_COMMERCIAL_REGIS_NO.toString());
	}
	if(RI_TRADE_LICENSE_NO!=null){
	 fc.setRI_TRADE_LICENSE_NO(RI_TRADE_LICENSE_NO.toString());
	}
	if(RI_TRADE_LICENSE_IDATE!=null){
	 if(RI_TRADE_LICENSE_IDATE.toString().isEmpty()==false) {
		 Calendar cl=Calendar.getInstance();
		 cl.setTimeInMillis(new Long(RI_TRADE_LICENSE_IDATE.toString()));
		 fc.setRI_TRADE_LICENSE_IDATE(cl);
	 }else {
		 fc.setRI_TRADE_LICENSE_IDATE(null);
	 }
	}
	if(RI_TRADE_LICENSE_EDATE!=null){
	 if(RI_TRADE_LICENSE_EDATE.toString().isEmpty()==false) {
		 Calendar cl=Calendar.getInstance();
		 cl.setTimeInMillis(new Long(RI_TRADE_LICENSE_EDATE.toString()));
		 fc.setRI_TRADE_LICENSE_EDATE(cl);
	 }else {
		 fc.setRI_TRADE_LICENSE_EDATE(null);
	 }
	}
	if(RI_AUTHORIZED_NAME!=null){
	 fc.setRI_AUTHORIZED_NAME(RI_AUTHORIZED_NAME.toString());
	}
	if(RI_CAPACITY!=null){
	 fc.setRI_CAPACITY(RI_CAPACITY.toString());
	}
	if(RI_MARGIN_CLIENT_FLAG!=null){
	 fc.setRI_MARGIN_CLIENT_FLAG(RI_MARGIN_CLIENT_FLAG.toString());
	}
	if(RI_MARGIN_REPORT_CURRENCY!=null){
	if(RI_MARGIN_REPORT_CURRENCY.toString().isEmpty()==false) {
		 fc.setRI_MARGIN_REPORT_CURRENCY(new Long(RI_MARGIN_REPORT_CURRENCY.toString()));
	}else{
		 fc.setRI_MARGIN_REPORT_CURRENCY(null);
	}
	}
	if(RI_MARGIN_AGGDATE!=null){
	  if(RI_MARGIN_AGGDATE.toString().isEmpty()==false) {
		  Calendar cl=Calendar.getInstance();
		  cl.setTimeInMillis(new Long(RI_MARGIN_AGGDATE.toString()));
		  fc.setRI_MARGIN_AGGDATE(cl);
	  }else {
		  fc.setRI_MARGIN_AGGDATE(null);
	  }
	}
	if(RI_RELATION_ALMAL!=null){
	 fc.setRI_RELATION_ALMAL(RI_RELATION_ALMAL.toString());
	}
	if(RI_REFFER_BY!=null){
	 fc.setRI_REFFER_BY(RI_REFFER_BY.toString());
	}
	if(RI_PMR_DAY!=null){
	 fc.setRI_PMR_DAY(RI_PMR_DAY.toString());
	}
	if(RI_PMR_WEEKLY!=null){
	 fc.setRI_PMR_WEEKLY(RI_PMR_WEEKLY.toString());
	}
	if(RI_PMR_MONTH!=null){
	 fc.setRI_PMR_MONTH(RI_PMR_MONTH.toString());
	}
	if(RI_MARKET_AMT!=null){
	 if(RI_MARKET_AMT.toString().isEmpty()==false) {
		 fc.setRI_MARKET_AMT(new Double(RI_MARKET_AMT.toString()));
	 }else{
		 fc.setRI_MARKET_AMT(0.0);
	 }
	}
	if(RI_MARGIN_SUB_TYPE!=null){
		if(RI_MARGIN_SUB_TYPE.toString().isEmpty()==false) {
			 fc.setRI_MARGIN_SUB_TYPE(new Long(RI_MARGIN_SUB_TYPE.toString()));
		}else {
			 fc.setRI_MARGIN_SUB_TYPE(null);
		}
	
	}
	if(RI_OWN_OR_CLIENT!=null){
	 fc.setRI_OWN_OR_CLIENT(RI_OWN_OR_CLIENT.toString());
	}
	if(RI_MONTH_END_REP_FLAG!=null){
	 fc.setRI_MONTH_END_REP_FLAG(RI_MONTH_END_REP_FLAG.toString());
	}
	if(RI_INTEREST_POST!=null){
	 fc.setRI_INTEREST_POST(RI_INTEREST_POST.toString());
	}
	if(RI_INTEREST_REMARKS!=null){
	 fc.setRI_INTEREST_REMARKS(RI_INTEREST_REMARKS.toString());
	}
	if(RI_ACCOUNT_CLOSE!=null){
	 fc.setRI_ACCOUNT_CLOSE(RI_ACCOUNT_CLOSE.toString());
	}
	if(RI_ACCOUNT_CLOSE_REMARKS!=null){
	 fc.setRI_ACCOUNT_CLOSE_REMARKS(RI_ACCOUNT_CLOSE_REMARKS.toString());
	}
	if(RI_SALVATION!=null){
	 fc.setRI_SALVATION(RI_SALVATION.toString());
	}
	if(RI_FIRST_NAME!=null){
	 fc.setRI_FIRST_NAME(RI_FIRST_NAME.toString());
	}
	if(RI_LAST_NAME!=null){
	 fc.setRI_LAST_NAME(RI_LAST_NAME.toString());
	}
	if(RI_MIDDLE_NAME!=null){
	 fc.setRI_MIDDLE_NAME(RI_MIDDLE_NAME.toString());
	}
	if(RI_SUFFIX!=null){
	 fc.setRI_SUFFIX(RI_SUFFIX.toString());
	}
	if(RI_JOINT_SALVATION!=null){
	 fc.setRI_JOINT_SALVATION(RI_JOINT_SALVATION.toString());
	}
	if(RI_JOINT_FIRST_NAME!=null){
	 fc.setRI_JOINT_FIRST_NAME(RI_JOINT_FIRST_NAME.toString());
	}
	if(RI_JOINT_LAST_NAME!=null){
	 fc.setRI_JOINT_LAST_NAME(RI_JOINT_LAST_NAME.toString());
	}
	if(RI_JOINT_MIDDLE_NAME!=null){
	 fc.setRI_JOINT_MIDDLE_NAME(RI_JOINT_MIDDLE_NAME.toString());
	}
	if(RI_JOINT_SUFFIX!=null){
	 fc.setRI_JOINT_SUFFIX(RI_JOINT_SUFFIX.toString());
	}
	if(RI_CORP_CD_SALVATION!=null){
	 fc.setRI_CORP_CD_SALVATION(RI_CORP_CD_SALVATION.toString());
	}
	if(RI_CORP_CD_FIRST_NAME!=null){
	 fc.setRI_CORP_CD_FIRST_NAME(RI_CORP_CD_FIRST_NAME.toString());
	}
	if(RI_CORP_CD_LAST_NAME!=null){
	 fc.setRI_CORP_CD_LAST_NAME(RI_CORP_CD_LAST_NAME.toString());
	}
	if(RI_CORP_CD_MIDDLE_NAME!=null){
	 fc.setRI_CORP_CD_MIDDLE_NAME(RI_CORP_CD_MIDDLE_NAME.toString());
	}
	if(RI_CORP_CD_SUFFIX!=null){
	 fc.setRI_CORP_CD_SUFFIX(RI_CORP_CD_SUFFIX.toString());
	}
	if(RI_ACCOUNT_CLOSE_DATE!=null){
	  if(RI_ACCOUNT_CLOSE_DATE.toString().isEmpty()==false) {
		  Calendar cl=Calendar.getInstance();
		  cl.setTimeInMillis(new Long(RI_ACCOUNT_CLOSE_DATE.toString()));
		  fc.setRI_ACCOUNT_CLOSE_DATE(cl);
	  }else {
		  fc.setRI_ACCOUNT_CLOSE_DATE(null);
	  }
	}
	
	if(RI_CRATE!=null){
	 if(RI_CRATE.toString().isEmpty()==false) {
		 fc.setRI_CRATE(new Long(RI_CRATE.toString()));
	 }else {
		 fc.setRI_CRATE(0);
	 }
	}

	if(WMS_COMMENTS!=null) {
		if(WMS_COMMENTS.toString().isEmpty()==false) {
			fc.setWMS_COMMENTS(WMS_COMMENTS.toString());
			}
		}
	
		/*
		 * Default setting
		 */
		fc.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
		fc.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
		fc.setWMS_STATUS(status.toString());
		
		try {
		fc=	rE_INVESTORRepository.save(fc);
		if(fc!=null) {
			json.put("msg", "Investor saved");	
			/*
			 * Account Info Saved
			 */
			List<Map<String,Object>> AccountLink=(List<Map<String, Object>>) requestJson.get("accountlinks");
			fUND_ACCOUNT_LINKService.save(AccountLink,fc);
			/*
			 * Account Info Saved
			 */
		 	boolean loggerw=activityLogger.writeLog(fuser,SNO,SVL_SCREEN, SVL_DESC, "CREATE");
		 	if(loggerw==true) {
		 		logger.info("Both Record and Logs saved for Broker:"+fc.getRI_WMS_CODE());
				json.put("logs","logs are saved");
		 	}else {
		 		logger.info("Record is saved but logs can't saved due error in saving of logs");
				json.put("logs","Record is saved but logs can't saved due error in saving of logs");
		 	}
			InvestorBean ib=getJson(fc);
			ib.setMsg("Investor saved");
			return new ResponseEntity<>(ib,HttpStatus.OK);
		}else {
			json.put("msg", "Investor can not saved");
			logger.error("Investor can not saved");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
		}catch (Exception e) {
		json.put("msg", e.getMessage());
		logger.error(e.getMessage());
		return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
		
}
	
	
	
	
	/**
	 * Gets the all client type.
	 *
	 * @return the all client type
	 */
	@PostMapping("/clients")
	public ResponseEntity<?> getAllClientType(){
		List<Map<String,Object>> arrayJson=new ArrayList<Map<String,Object>>();
		rE_INVESTORRepository.findAll().forEach(conut->{
			Map<String,Object> json=new HashMap<>();
			json.put("clientid", conut.getRI_INVESTOR_CODE());
			json.put("clientname", conut.getRI_INVESTOR_NAME());
			arrayJson.add(json);
		});
		return new ResponseEntity<List<Map<String,Object>>>(arrayJson,HttpStatus.OK);
	}
	
	
	/**
	 * Approved info.
	 *
	 * @param requestBody the request body
	 * @return the response entity
	 */
	@PostMapping("/approveInvestorInfo")
    public ResponseEntity<?> approvedInfo(RequestEntity<Map<String, Object>> requestBody){
	Map<String,Object>requestJson=requestBody.getBody();	
	Object RI_WMS_CODE=requestJson.get("RI_WMS_CODE");
	Object createdby=requestJson.get("approvedby");
	Object WMS_COMMENTS=requestJson.get("comment");
	Object SVL_SCREEN=requestJson.get("screentype");
	Object SVL_DESC=requestJson.get("log");
	Object SNO=requestJson.get("sno");
	Map<String,Object> json=new HashMap<>();
	if(RI_WMS_CODE==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
	{
		json.put("msg", "Please check Input json, missing some required attributes");
		logger.error("Please check Input json, missing some required attributes");
		return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
	}
	if(RI_WMS_CODE.toString().isEmpty()) {
		json.put("msg", "Please check RI_WMS_CODE is empty attribute");
		logger.error("Please check RI_WMS_CODE is empty attribute");
		return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
	}
	
	FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
	if(fuser==null)
	{
			json.put("msg", "Createdby is not valid user");
			logger.error("Createdby is not valid user");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
	}
	
	RE_INVESTOR fc=rE_INVESTORRepository.findOne(new Long(RI_WMS_CODE.toString()));
	if(fc==null) {
		json.put("msg", "No investor found for this id "+RI_WMS_CODE.toString());
		logger.error("No investor found for this id "+RI_WMS_CODE.toString());
		return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
	}
	
		if(WMS_COMMENTS!=null) {
		if(WMS_COMMENTS.toString().isEmpty()==false) {
			fc.setWMS_COMMENTS(WMS_COMMENTS.toString());
			}
		}
		/*
		 * Nationality Test
		 */
		if(fc.getRI_INVESTOR_TYPE().matches("(?i)individual|joint"))
		{
			if(fc.getRI_NATIONALITY()!=null) {
				FWMS_NATIONALITY nation=fWMS_NATIONALITYRepository.findOne(new Long(fc.getRI_NATIONALITY()));
				if(nation!=null) {
					if(nation.getWMS_APPROVE_UID()==null) {
						json.put("msg", "Nationality is not approved of first");
						logger.error("Nationality is not approved of first");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
					}
				}
			}
			if(fc.getRI_NATIONALITY2()!=null) {
				FWMS_NATIONALITY nation=fWMS_NATIONALITYRepository.findOne(new Long(fc.getRI_NATIONALITY2()));
				if(nation!=null) {
					if(nation.getWMS_APPROVE_UID()==null) {
						json.put("msg", "Nationality is not approved of Second");
						logger.error("Nationality is not approved of Second");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);	
					}
				}
			}
		}
		/*
		 * Corporate
		 */
		if(fc.getRI_INVESTOR_TYPE().matches("(?i)corporate"))
		{
			FUND_COUNTRIES country=stockParameters.getFUND_COUNTRIES(fc.getRI_COUNTRY_ESTA());
			if(country!=null) {
				if(country.getIV_APPROVE_UID()==null) {
					json.put("msg", "Country is not approved of Corporate");
					logger.error("Country is not approved of Corporate");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
		}
		/*
		 * Default setting
		 */
		fc.setIV_APPROVE_UID(fuser.getSVC_UID());
		fc.setIV_APPROVE_DATE(Calendar.getInstance());
		fc.setWMS_STATUS("Approved");
		try {
		fc=	rE_INVESTORRepository.save(fc);
		if(fc!=null) {
			json.put("msg", "Investor saved");				
		 	boolean loggerw=activityLogger.writeLog(fuser,SNO,SVL_SCREEN, SVL_DESC, "CREATE");
		 	if(loggerw==true) {
		 		logger.info("Both Record and Logs saved for Broker:"+fc.getRI_WMS_CODE());
				json.put("logs","logs are saved");
		 	}else {
		 		logger.info("Record is saved but logs can't saved due error in saving of logs");
				json.put("logs","Record is saved but logs can't saved due error in saving of logs");
		 	}
			InvestorBean ib=getJson(fc);
			return new ResponseEntity<>(ib,HttpStatus.OK);
		}else {
			json.put("msg", "Investor can not saved");
			logger.error("Investor can not saved");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
		}catch (Exception e) {
		json.put("msg", e.getMessage());
		logger.error(e.getMessage());
		return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
		
}
	
	/**
	 * Revoke info.
	 *
	 * @param requestBody the request body
	 * @return the response entity
	 */
	@PostMapping("/revokeInvestorInfo")
    public ResponseEntity<?> revokeInfo(RequestEntity<Map<String, Object>> requestBody){
	Map<String,Object>requestJson=requestBody.getBody();	
	Object RI_WMS_CODE=requestJson.get("RI_WMS_CODE");
	Object createdby=requestJson.get("modifiedby");
	Object WMS_COMMENTS=requestJson.get("comment");
	Object SVL_SCREEN=requestJson.get("screentype");
	Object SVL_DESC=requestJson.get("log");
	Object SNO=requestJson.get("sno");
	Map<String,Object> json=new HashMap<>();
	if(RI_WMS_CODE==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
	{
		json.put("msg", "Please check Input json, missing some required attributes");
		logger.error("Please check Input json, missing some required attributes");
		return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
	}
	if(RI_WMS_CODE.toString().isEmpty()) {
		json.put("msg", "Please check RI_WMS_CODE is empty attribute");
		logger.error("Please check RI_WMS_CODE is empty attribute");
		return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
	}
	
	FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
	if(fuser==null)
	{
			json.put("msg", "Createdby is not valid user");
			logger.error("Createdby is not valid user");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
	}
	
	RE_INVESTOR fc=rE_INVESTORRepository.findOne(new Long(RI_WMS_CODE.toString()));
	if(fc==null) {
		json.put("msg", "No investor found for this id "+RI_WMS_CODE.toString());
		logger.error("No investor found for this id "+RI_WMS_CODE.toString());
		return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
	}
	
		if(WMS_COMMENTS!=null) {
		if(WMS_COMMENTS.toString().isEmpty()==false) {
			fc.setWMS_COMMENTS(WMS_COMMENTS.toString());
			}
		}
		/*
		 * Default setting
		 */
		fc.setIV_APPROVE_UID(null);
		fc.setIV_APPROVE_DATE(null);
		fc.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
		fc.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
		fc.setWMS_STATUS("Not Approved");
		try {
		fc=	rE_INVESTORRepository.save(fc);
		if(fc!=null) {
			json.put("msg", "Investor saved");				
		 	boolean loggerw=activityLogger.writeLog(fuser,SNO,SVL_SCREEN, SVL_DESC, "CREATE");
		 	if(loggerw==true) {
		 		logger.info("Both Record and Logs saved for Broker:"+fc.getRI_WMS_CODE());
				json.put("logs","logs are saved");
		 	}else {
		 		logger.info("Record is saved but logs can't saved due error in saving of logs");
				json.put("logs","Record is saved but logs can't saved due error in saving of logs");
		 	}
			InvestorBean ib=getJson(fc);
			return new ResponseEntity<>(ib,HttpStatus.OK);
		}else {
			json.put("msg", "Investor can not saved");
			logger.error("Investor can not saved");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
		}catch (Exception e) {
		json.put("msg", e.getMessage());
		logger.error(e.getMessage());
		return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
		
}

	
	
	/**
	 * Delete info.
	 *
	 * @param requestBody the request body
	 * @return the response entity
	 */
	@PostMapping("/deleteInvestorInfo")
    public ResponseEntity<?> deleteInfo(RequestEntity<Map<String, Object>> requestBody){
	Map<String,Object>requestJson=requestBody.getBody();	
	Object RI_WMS_CODE=requestJson.get("RI_WMS_CODE");
	Object createdby=requestJson.get("modifiedby");
//	Object WMS_COMMENTS=requestJson.get("comment");
	Object SVL_SCREEN=requestJson.get("screentype");
	Object SVL_DESC=requestJson.get("log");
	Map<String,Object> json=new HashMap<>();
	if(RI_WMS_CODE==null||createdby==null||SVL_SCREEN==null||SVL_DESC==null)
	{
		json.put("msg", "Please check Input json, missing some required attributes");
		logger.error("Please check Input json, missing some required attributes");
		return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
	}
	if(RI_WMS_CODE.toString().isEmpty()) {
		json.put("msg", "Please check RI_WMS_CODE is empty attribute");
		logger.error("Please check RI_WMS_CODE is empty attribute");
		return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
	}
	
	FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
	if(fuser==null)
	{
			json.put("msg", "Createdby is not valid user");
			logger.error("Createdby is not valid user");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
	}
	
	RE_INVESTOR fc=rE_INVESTORRepository.findOne(new Long(RI_WMS_CODE.toString()));
	if(fc==null) {
		json.put("msg", "No investor found for this id "+RI_WMS_CODE.toString());
		logger.error("No investor found for this id "+RI_WMS_CODE.toString());
		return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
	}
	
	
		try {
		rE_INVESTORRepository.delete(fc);
		json.put("msg", "Investor Deleted");
		logger.error("Investor Deleted");
		return new ResponseEntity<>(json,HttpStatus.OK);
		}catch (Exception e) {
		json.put("msg", e.getMessage());
		logger.error(e.getMessage());
		return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
		
}

	
	

	
	/**
	 * Gets the all investors.
	 *
	 * @param investorid the investorid
	 * @return the all investors
	 */
	@GetMapping("/investor")
	public ResponseEntity<?> getAllInvestors(@RequestParam(value="investorid",required=true) Long investorid){
		
		RE_INVESTOR re=rE_INVESTORRepository.findOne(investorid);
		if(re!=null) {
			InvestorBean json=getJson(re);
			return new ResponseEntity<>(json,HttpStatus.OK);
		}else {
			Map<String,Object>json=new HashMap<>();
			json.put("msg", "Not found");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	
	/**
	 * Gets the all investors.
	 *
	 * @param action the action
	 * @param paramsearch the paramsearch
	 * @param page the page
	 * @return the all investors
	 */
	@GetMapping("/investors")
	public ResponseEntity<?> getAllInvestors(@RequestParam(value="action",required=true) String action,
			@RequestParam(value="paramSearch",required=false) String paramsearch,
			Pageable page){
		Map<String,Object>json=new HashMap<>();
		Page<RE_INVESTOR> allInvertors=null;
		if(paramsearch==null) {
			paramsearch="";
		}
		
		if(action.isEmpty()==false) {
			if(action.equalsIgnoreCase("ALL")) {
				if(paramsearch.isEmpty()==false) {
					allInvertors=rE_INVESTORRepository.findAllInvestor_Serach(paramsearch,page);
				}else {
					allInvertors=rE_INVESTORRepository.findAllInvestor(page);
				}
			}else if(action.equalsIgnoreCase("APPROVED")) {
				if(paramsearch.isEmpty()==false) {
					allInvertors=rE_INVESTORRepository.findAllInvestor_Serach(paramsearch,page);
				}else {
					allInvertors=rE_INVESTORRepository.findAllInvestor(page);
				}
			}else if(action.equalsIgnoreCase("UNAPPROVED")) {
				if(paramsearch.isEmpty()==false) {
					allInvertors=rE_INVESTORRepository.findAllInvestor_Serach(paramsearch,page);
				}else {
					allInvertors=rE_INVESTORRepository.findAllInvestor(page);
				}
			}else {
				json.put("msg", "Action value is missing");
				logger.error("Action value is missing");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
			if(allInvertors!=null) {
				Page<InvestorBean>alnvestors=allInvertors.map(new Converter<RE_INVESTOR, InvestorBean>() {

					@Override
					public InvestorBean convert(RE_INVESTOR conut) {
						InvestorBean json=getJson(conut);
						return json;
					}
				
				});
				return new ResponseEntity<>(alnvestors,HttpStatus.OK);
			}else {
				json.put("msg", "No Record found");
				logger.error("Bo record found");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "Action value is missing");
			logger.error("Action value is missing");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/**
	 * Investors no page.
	 *
	 * @return the response entity
	 */
	@PostMapping("/investorsNoPage")
	public ResponseEntity<?> investorsNoPage(){
		List<InvestorBean> arrayJson=new ArrayList<>();
		rE_INVESTORRepository.findAllInvestorWithCode().forEach(conut->{
			InvestorBean json=getJson(conut);
			arrayJson.add(json);
		});
		return new ResponseEntity<>(arrayJson,HttpStatus.OK);
	}
	
	
	/**
	 * Gets the json.
	 *
	 * @param conut the conut
	 * @return the json
	 */
	public InvestorBean getJson(RE_INVESTOR conut) {
		InvestorBean ffb=new InvestorBean();
		ffb.setRI_WMS_CODE(conut.getRI_WMS_CODE());
		ffb.setRI_INVESTOR_CODE(conut.getRI_INVESTOR_CODE());
		ffb.setRI_INVESTOR_TYPE(conut.getRI_INVESTOR_TYPE());
		ffb.setRI_CREATE_DATE(conut.getRI_CREATE_DATE());
		ffb.setRI_INVESTOR_NAME(conut.getRI_INVESTOR_NAME());
		ffb.setRI_ADDRESS_1(conut.getRI_ADDRESS_1());
		ffb.setRI_POST_BOX(conut.getRI_POST_BOX());
		ffb.setRI_POST_CODE(conut.getRI_POST_CODE());
		ffb.setRI_CITY(conut.getRI_CITY());
		/*
		 * Country
		 */
		if(conut.getRI_COUNTRY()!=null) {
		FUND_COUNTRIES country=stockParameters.getFUND_COUNTRIES(conut.getRI_COUNTRY());
		if(country!=null) {
			ffb.setRI_COUNTRY(conut.getRI_COUNTRY()+"");
			ffb.setRI_COUNTRY_NAME(country.getSVC_NAME());
		}else {
			ffb.setRI_COUNTRY(null);
			ffb.setRI_COUNTRY_NAME(null);
		}
		}else {
			ffb.setRI_COUNTRY(null);
			ffb.setRI_COUNTRY_NAME(null);
		}
		
		ffb.setRI_TEL_NO(conut.getRI_TEL_NO());
		ffb.setRI_MOBILE_NO(conut.getRI_MOBILE_NO());
		ffb.setRI_FAX_NO(conut.getRI_FAX_NO());
		ffb.setRI_EMAIL(conut.getRI_EMAIL());
		ffb.setRI_COMPANY_NAME(conut.getRI_COMPANY_NAME());
		ffb.setRI_COMPANY_LICENSE_NO(conut.getRI_COMPANY_LICENSE_NO());
		ffb.setRI_EXPIRY_DATE(conut.getRI_EXPIRY_DATE());
		ffb.setRI_OCCUPATION(conut.getRI_OCCUPATION());
		
		if(conut.getRI_NATIONALITY()!=null) {
		FWMS_NATIONALITY nationality=stockParameters.getNationality(conut.getRI_NATIONALITY());
		if(nationality!=null)
		{
			ffb.setRI_NATIONALITY(conut.getRI_NATIONALITY()+"");
			ffb.setRI_NATIONALITYNAME(nationality.getWMS_NATIONALITY_DESC());
		}
		else
		{
			ffb.setRI_NATIONALITY(null);
			ffb.setRI_NATIONALITYNAME(null);
		}
		}
		else
		{
			ffb.setRI_NATIONALITY(null);
			ffb.setRI_NATIONALITYNAME(null);
		}
		
		ffb.setRI_DOB(conut.getRI_DOB());
		ffb.setRI_GENDER(conut.getRI_GENDER());
		ffb.setRI_JOINT_AUTHORISATION(conut.getRI_JOINT_AUTHORISATION());
		ffb.setRI_STATUS(conut.getRI_STATUS());
		ffb.setSVC_UID(conut.getSVC_UID());
		ffb.setRI_INDUSTRY(conut.getRI_INDUSTRY());
		ffb.setRI_REMARKS(conut.getRI_REMARKS());
		ffb.setRI_MARITAL_STATUS(conut.getRI_MARITAL_STATUS());
		ffb.setRI_MARITAL_DEPENDENTS(conut.getRI_MARITAL_DEPENDENTS());
		ffb.setRI_NATIONAL_IDENITY(conut.getRI_NATIONAL_IDENITY());
		ffb.setRI_PASSPORT_IPLACE(conut.getRI_PASSPORT_IPLACE());
		ffb.setRI_PASSPORT_IDATE(conut.getRI_PASSPORT_IDATE());
		ffb.setRI_PASSPORT_ID(conut.getRI_PASSPORT_ID());
		ffb.setRI_INVESTOR_NAME2(conut.getRI_INVESTOR_NAME2());
		ffb.setRI_COMPANY_NAME2(conut.getRI_COMPANY_NAME2());
		ffb.setRI_OCCUPATION2(conut.getRI_OCCUPATION2());
		//ffb.setRI_NATIONALITY2(conut.getRI_NATIONALITY2());
		FWMS_NATIONALITY nationality2=stockParameters.getNationality(conut.getRI_NATIONALITY2());
		if(nationality2!=null)
		{
			ffb.setRI_NATIONALITY2(conut.getRI_NATIONALITY2()+"");
			ffb.setRI_NATIONALITYNAME2(nationality2.getWMS_NATIONALITY_DESC());
		}
		else
		{
			ffb.setRI_NATIONALITY2(null);
			ffb.setRI_NATIONALITYNAME2(null);
		}
		
		ffb.setRI_DOB2(conut.getRI_DOB2());
		ffb.setRI_GENDER2(conut.getRI_GENDER2());
		ffb.setRI_MARITAL_STATUS2(conut.getRI_MARITAL_STATUS2());
		ffb.setRI_MARITAL_DEPENDENTS2(conut.getRI_MARITAL_DEPENDENTS2());
		ffb.setRI_PASSPORT_IPLACE2(conut.getRI_PASSPORT_IPLACE2());
		ffb.setRI_PASSPORT_IDATE2(conut.getRI_PASSPORT_IDATE2());
		ffb.setRI_NATIONAL_IDENITY2(conut.getRI_NATIONAL_IDENITY2());
		ffb.setRI_PASSPORT_ID2(conut.getRI_PASSPORT_ID2());
		ffb.setRI_PRIMARY_CONTACT_NAME(conut.getRI_PRIMARY_CONTACT_NAME());
		ffb.setRI_OFFICE_APART_NO(conut.getRI_OFFICE_APART_NO());
		ffb.setRI_STREET(conut.getRI_STREET());
		ffb.setRI_AREA(conut.getRI_AREA());
		ffb.setRI_TEL_NO_HOME(conut.getRI_TEL_NO_HOME());
		ffb.setRI_PRIMARY_CONTACT_NAME2(conut.getRI_PRIMARY_CONTACT_NAME2());
		ffb.setRI_ADDRESS_12(conut.getRI_ADDRESS_12());
		ffb.setRI_OFFICE_APART_NO2(conut.getRI_OFFICE_APART_NO2());
		ffb.setRI_STREET2(conut.getRI_STREET2());
		ffb.setRI_AREA2(conut.getRI_AREA2());
		ffb.setRI_POST_BOX2(conut.getRI_POST_BOX2());
		ffb.setRI_CITY2(conut.getRI_CITY2());
		/*
		 * Country
		 */
		if(conut.getRI_COUNTRY2()!=null) {
		FUND_COUNTRIES country2=stockParameters.getFUND_COUNTRIES(conut.getRI_COUNTRY2());
		if(country2!=null) {
			ffb.setRI_COUNTRY2(conut.getRI_COUNTRY2()+"");
			ffb.setRI_COUNTRY2_NAME(country2.getSVC_NAME());
		}else {
			
			ffb.setRI_COUNTRY2(null);
			ffb.setRI_COUNTRY2_NAME(null);
		}
		}else {
			
			ffb.setRI_COUNTRY2(null);
			ffb.setRI_COUNTRY2_NAME(null);
		}
		
		ffb.setRI_POST_CODE2(conut.getRI_POST_CODE2());
		ffb.setRI_TEL_NO2(conut.getRI_TEL_NO2());
		ffb.setRI_TEL_NO_HOME2(conut.getRI_TEL_NO_HOME2());
		ffb.setRI_MOBILE_NO2(conut.getRI_MOBILE_NO2());
		ffb.setRI_FAX_NO2(conut.getRI_FAX_NO2());
		ffb.setRI_EMAIL2(conut.getRI_EMAIL2());
		ffb.setRI_EXPIRY_DATE2(conut.getRI_EXPIRY_DATE2());
		ffb.setRI_BANK_ANAME(conut.getRI_BANK_ANAME());
		ffb.setRI_BANK_ANUMBER(conut.getRI_BANK_ANUMBER());
		ffb.setRI_BANK_BANK_NAME(conut.getRI_BANK_BANK_NAME());
		/*
		 * currency code
		 */
		
		if(conut.getRI_BANK_CURRENCY()!=null) {
			FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getRI_BANK_CURRENCY());
			if(fcm!=null) {
				ffb.setRI_BANK_CURRENCY(conut.getRI_BANK_CURRENCY());
				ffb.setRI_BANK_CURRENCY_NAME(fcm.getSVC_NAME());
			}
		}
		ffb.setRI_BANK_BRANCH(conut.getRI_BANK_BRANCH());
		/*
		 * Country
		 */
		if(conut.getRI_BANK_COUNTRY()!=null) {
		FUND_COUNTRIES RI_BANK_COUNTRY=stockParameters.getFUND_COUNTRIES(conut.getRI_BANK_COUNTRY());
		if(RI_BANK_COUNTRY!=null) {
			ffb.setRI_BANK_COUNTRY(conut.getRI_BANK_COUNTRY());
			ffb.setRI_BANK_COUNTRY_NAME(RI_BANK_COUNTRY.getSVC_NAME());
			
		}else {
			
			ffb.setRI_BANK_COUNTRY(null);
			ffb.setRI_BANK_COUNTRY_NAME(null);
		}
		}else {
			
			ffb.setRI_BANK_COUNTRY(null);
			ffb.setRI_BANK_COUNTRY_NAME(null);
		}
		
		ffb.setRI_BANK_CITY(conut.getRI_BANK_CITY());
		ffb.setRI_NOTI_FAX(conut.getRI_NOTI_FAX());
		ffb.setRI_NOTI_SMS(conut.getRI_NOTI_SMS());
		ffb.setRI_NOTI_MAIL(conut.getRI_NOTI_MAIL());
		ffb.setRI_NOTI_EMAIL(conut.getRI_NOTI_EMAIL());
		/*
		 * Marging Type client Type
		 */
		if(conut.getRI_MARGIN_TYPE()!=null) {
			FUND_MAR_CLIENT_TYPE type=clientTypeParameters.getCLIENT_TYPEApproved(conut.getRI_MARGIN_TYPE());
			if(type!=null) {
				ffb.setRI_MARGIN_TYPE(conut.getRI_MARGIN_TYPE());
				ffb.setRI_MARGIN_TYPE_NAME(type.getFCT_NAME());
			}else {
				ffb.setRI_MARGIN_TYPE(null);
				ffb.setRI_MARGIN_TYPE_NAME(null);
			}
		}
		
		ffb.setRI_INTEREST_TYPE(conut.getRI_INTEREST_TYPE());
		ffb.setRI_RELATION_MANAGER(conut.getRI_RELATION_MANAGER());
		ffb.setRI_CORPORATE_NAME(conut.getRI_CORPORATE_NAME());
		ffb.setRI_BUSINESS_ACTIVITY(conut.getRI_BUSINESS_ACTIVITY());
		if(conut.getRI_COUNTRY_ESTA()!=null) {
		FUND_COUNTRIES countryESTA=stockParameters.getFUND_COUNTRIES(conut.getRI_COUNTRY_ESTA());
		if(countryESTA!=null) {
			ffb.setRI_COUNTRY_ESTA(conut.getRI_COUNTRY_ESTA()+"");
			ffb.setRI_COUNTRYNAME(countryESTA.getSVC_NAME());
		}else {
			
			ffb.setRI_COUNTRY_ESTA(null);
			ffb.setRI_COUNTRYNAME(null);
		}}else {
			
			ffb.setRI_COUNTRY_ESTA(null);
			ffb.setRI_COUNTRYNAME(null);
		}
		
		ffb.setRI_COMMERCIAL_REGIS_NO(conut.getRI_COMMERCIAL_REGIS_NO());
		ffb.setRI_TRADE_LICENSE_NO(conut.getRI_TRADE_LICENSE_NO());
		ffb.setRI_TRADE_LICENSE_IDATE(conut.getRI_TRADE_LICENSE_IDATE());
		ffb.setRI_TRADE_LICENSE_EDATE(conut.getRI_TRADE_LICENSE_EDATE());
		ffb.setRI_AUTHORIZED_NAME(conut.getRI_AUTHORIZED_NAME());
		ffb.setRI_CAPACITY(conut.getRI_CAPACITY());
		ffb.setRI_MARGIN_CLIENT_FLAG(conut.getRI_MARGIN_CLIENT_FLAG());
		/*
		 * Margin Currency
		 */
		if(conut.getRI_MARGIN_REPORT_CURRENCY()!=null) {
			FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getRI_MARGIN_REPORT_CURRENCY());
			if(fcm!=null) {
				ffb.setRI_MARGIN_REPORT_CURRENCY(conut.getRI_MARGIN_REPORT_CURRENCY());
				ffb.setRI_MARGIN_REPORT_CURRENCY_NAME(fcm.getSVC_NAME());
			}
		}
		ffb.setRI_MARGIN_AGGDATE(conut.getRI_MARGIN_AGGDATE());
		ffb.setRI_RELATION_ALMAL(conut.getRI_RELATION_ALMAL());
		ffb.setRI_REFFER_BY(conut.getRI_REFFER_BY());
		ffb.setRI_PMR_DAY(conut.getRI_PMR_DAY());
		ffb.setRI_PMR_WEEKLY(conut.getRI_PMR_WEEKLY());
		ffb.setRI_PMR_MONTH(conut.getRI_PMR_MONTH());
		ffb.setRI_MARKET_AMT(conut.getRI_MARKET_AMT());
		ffb.setRI_MARGIN_SUB_TYPE(conut.getRI_MARGIN_SUB_TYPE());
		ffb.setRI_OWN_OR_CLIENT(conut.getRI_OWN_OR_CLIENT());
		ffb.setRI_MONTH_END_REP_FLAG(conut.getRI_MONTH_END_REP_FLAG());
		ffb.setRI_INTEREST_POST(conut.getRI_INTEREST_POST());
		ffb.setRI_INTEREST_REMARKS(conut.getRI_INTEREST_REMARKS());
		ffb.setRI_ACCOUNT_CLOSE(conut.getRI_ACCOUNT_CLOSE());
		ffb.setRI_ACCOUNT_CLOSE_REMARKS(conut.getRI_ACCOUNT_CLOSE_REMARKS());
		ffb.setRI_SALVATION(conut.getRI_SALVATION());
		ffb.setRI_FIRST_NAME(conut.getRI_FIRST_NAME());
		ffb.setRI_LAST_NAME(conut.getRI_LAST_NAME());
		ffb.setRI_MIDDLE_NAME(conut.getRI_MIDDLE_NAME());
		ffb.setRI_SUFFIX(conut.getRI_SUFFIX());
		ffb.setRI_JOINT_SALVATION(conut.getRI_JOINT_SALVATION());
		ffb.setRI_JOINT_FIRST_NAME(conut.getRI_JOINT_FIRST_NAME());
		ffb.setRI_JOINT_LAST_NAME(conut.getRI_JOINT_LAST_NAME());
		ffb.setRI_JOINT_MIDDLE_NAME(conut.getRI_JOINT_MIDDLE_NAME());
		ffb.setRI_JOINT_SUFFIX(conut.getRI_JOINT_SUFFIX());
		ffb.setRI_CORP_CD_SALVATION(conut.getRI_CORP_CD_SALVATION());
		ffb.setRI_CORP_CD_FIRST_NAME(conut.getRI_CORP_CD_FIRST_NAME());
		ffb.setRI_CORP_CD_LAST_NAME(conut.getRI_CORP_CD_LAST_NAME());
		ffb.setRI_CORP_CD_MIDDLE_NAME(conut.getRI_CORP_CD_MIDDLE_NAME());
		ffb.setRI_CORP_CD_SUFFIX(conut.getRI_CORP_CD_SUFFIX());
		ffb.setRI_ACCOUNT_CLOSE_DATE(conut.getRI_ACCOUNT_CLOSE_DATE());
		ffb.setRI_CRATE(conut.getRI_CRATE());	
		
		ffb.setComments( conut.getWMS_COMMENTS()+"");
		ffb.setStatus( conut.getWMS_STATUS()+"");
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
		
		/*
		 * Set All Acount Links
		 */
		ffb.setAccounts(fUND_ACCOUNT_LINKService.accountLinks(ffb.getRI_WMS_CODE()));
		/*
		 * Setting workflow
		 */
		Workflow wf=new Workflow();
		RE_CRMWORKFLOW work=conut.getrE_CRMWORKFLOW();
		if(work!=null) {
			wf.setWorkflowid(work.getRE_CRMWORKFLOW_ID());
			wf.setFm(work.getFM());
			wf.setCo(work.getCO());
			wf.setOp(work.getOP());
			wf.setInitiator(work.getINITIATOR());
			wf.setCurrentstatus(work.getCURRENT_STATUS());
		}
		ffb.setWorkFlow(wf);
		return ffb;
	}
	
}