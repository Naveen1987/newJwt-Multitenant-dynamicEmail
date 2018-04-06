package com.fynisys.controller.stock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fynisys.model.FUND_COUNTRIES;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.FUND_USER_LOG;
import com.fynisys.model.parameters.FUND_CURRENCY_MSTR;
import com.fynisys.model.parameters.FUND_EXCHANGE_MSTR;
import com.fynisys.model.parameters.FUND_INDUSTRY_MSTR;
import com.fynisys.model.parameters.FUND_INSTRUMENT_MSTR;
import com.fynisys.model.stock.FUND_BOND_INT_RATE;
import com.fynisys.model.stock.FUND_SHARE_COMPANY_MSTR;
import com.fynisys.repository.FUND_USERSRepository;
import com.fynisys.repository.FUND_USER_LOGRepository;
import com.fynisys.repository.stock.FUND_BOND_INT_RATERepository;
import com.fynisys.repository.stock.FUND_SHARE_COMPANY_MSTRRepository;
import com.fynisys.utilities.StockParameters;

@Controller
public class FUND_SHARE_COMPANY_MSTRController{

	@Autowired
	FUND_USERSRepository fund_UserRepository;
	
	@Autowired
	FUND_SHARE_COMPANY_MSTRRepository fUND_SHARE_COMPANY_MSTRRepository;
	
	@Autowired
	FUND_BOND_INT_RATERepository fUND_BOND_INT_RATERepository;
	
	@Autowired
	StockParameters stockParameters;
	
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;
	
	private static final Logger logger=LoggerFactory.getLogger("FUND SHARE CONTROLLER");
	/*
1. Serial number
2. stockname
3.countryid
4.currencyid 
5.exchangeid
6.sectorid
7.assetid
8. bloombergcode
9. isincode
10.reuterscode
11.close_alternate_price
12. price_calculated_flag
13.calculate_value
14.ticker
15.cusip
16.sedol
17. comments
18. status
1.expiry_date
2.lot_size
1. issue_number
2. issue_date
3. maturity_date
4. unit_price
5. coupon_rate
6. Divisor_days
7. Frequency
8.User can also add effect date of coupons by providing the following details
 - Effect Dated 
 - Coupon Percentage
	 */
	@PostMapping("/savestock")
	public ResponseEntity<Map<String, Object>> save(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		//Object SVC_DATE                   Record Creation Date - Auto
		  //Object SVC_CODE                   Auto Serial Number
		  Object SVC_NAME =requestJson.get("stockname");
		  Object SVC_EXEC_CODE=requestJson.get("ticker");	    			// Ticker
		  Object SVC_CUSIP=requestJson.get("cusip");              			//  Cusip
		  Object SVC_RETUER_CODE=requestJson.get("reuterscode");        	//    Retuer Code
		  Object SVC_BLOM_CODE=requestJson.get("bloombergcode");       		//      Bloomberg code
		  Object SVC_ISIN_CODE=requestJson.get("isincode");            		// ISIN Code
		  Object SVC_SHARES_ISSUE=requestJson.get("sedol");          		// SEDOL
		  Object SVC_CUST_COUNTRY=requestJson.get("countryid");	  			//Country
		  Object SVC_CURR_CODE =requestJson.get("currencyid");	 			// Currency
		  Object SVC_EXCHANGE=requestJson.get("exchangeid");    			// Exchange
		  Object SVC_INDUSTRY_TYPE=requestJson.get("sectorid"); 			// Sector
		  Object SVC_CUST_SECURITY=requestJson.get("assetid");				//  Asset
		  Object SVC_PUT_CALL_EDATE=requestJson.get("expiry_date");			// Expiry Date
		  Object SVC_PUT_CALL_LOTSIZE=requestJson.get("lot_size"); 			//Lot Size
		  Object SVC_BOND_ISSUE_NO=requestJson.get("issue_number");			//  Issue No
		  Object SVC_BOND_DATE =requestJson.get("issue_date");  			 // Issue Date
		  Object SVC_BOND_MATURITY_DT=requestJson.get("maturity_date");		// Maturity Date
		  Object SVC_PAIDUP_VALUE=requestJson.get("unit_price");			//Unit Price / Rate/
		  Object SVC_BOND_DURATION=requestJson.get("frequency"); 			//Frequency 
		  Object SVC_BOND_RATE =requestJson.get("coupon_rate");				// Coupon Rate %
		  Object SVC_DIVISOR_DAYS=requestJson.get("Divisor_days");			//Divisor Days
		  Object SVC_CLOSE_ALTERNATE_PRICE=requestJson.get("close_alternate_price");	//Close/Alternate price,
		  Object SVC_PRICE_CALCULATE=requestJson.get("price_calculated_flag");			//Price calculated flag,
		  Object SVC_MARGIN_MAX=requestJson.get("calculate_value");						//Calculate Value,
		  Object WMS_COMMENTS=requestJson.get("comments");
		 // Object WMS_STATUS=requestJson.get("status");
		  Object createdby=requestJson.get("createdby");
		  Object SVL_SCREEN=requestJson.get("screentype");
		  Object SVL_DESC=requestJson.get("log");
		  List<Map<String,Object>> coupen=(List<Map<String,Object>>)requestJson.get("coupens");
		  if(SVC_NAME==null||SVC_CUST_COUNTRY==null||SVC_CURR_CODE==null||
		  SVC_EXCHANGE==null||SVC_INDUSTRY_TYPE==null||SVC_CUST_SECURITY==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
			  json.put("msg", "Please check Input json, missing some required attributes");
			  logger.error("Please check Input json, missing some required attributes");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		  }
		  else {
			  FUND_USERS fuser=isValid(createdby.toString());
				if(fuser==null)
				{
						json.put("msg", "Createdby is not valid user");
						logger.error("Createdby is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				  FUND_SHARE_COMPANY_MSTR fscm=new FUND_SHARE_COMPANY_MSTR();
				  fscm.setSVC_NAME(SVC_NAME.toString().trim());
				  fscm.setSVC_CUST_COUNTRY(new Integer(SVC_CUST_COUNTRY.toString()));
				  fscm.setSVC_CURR_CODE(SVC_CURR_CODE.toString().trim());
				  fscm.setSVC_EXCHANGE(SVC_EXCHANGE.toString().trim());
				  fscm.setSVC_INDUSTRY_TYPE(SVC_INDUSTRY_TYPE.toString().trim());
				  if(SVC_CUST_SECURITY.toString().isEmpty()==false) {
					  fscm.setSVC_CUST_SECURITY(new Integer(SVC_CUST_SECURITY.toString().trim()));
				  }
				  
				  if(SVC_BLOM_CODE!=null) {
					  if(!SVC_BLOM_CODE.toString().isEmpty()) {
					  if(fUND_SHARE_COMPANY_MSTRRepository.find_UK_BLOM_CODE(SVC_BLOM_CODE.toString().trim())==0) {
						  fscm.setSVC_BLOM_CODE(SVC_BLOM_CODE.toString().trim());
					  }
					  else {
						  json.put("msg","SVC_BLOM_CODE");
						  logger.error("Stock not saved SVC_BLOM_CODE duplicate");
						  return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST); 
					  }
					 }
				  }
				  
				  if(SVC_ISIN_CODE!=null) {
					  if(!SVC_ISIN_CODE.toString().isEmpty()) {
					  if(fUND_SHARE_COMPANY_MSTRRepository.find_UK_ISIN_CODE(SVC_ISIN_CODE.toString().trim())==0)
					  {
						  fscm.setSVC_ISIN_CODE(SVC_ISIN_CODE.toString().trim());  
					  }else {
						  json.put("msg","UK_ISIN_CODE");
						  logger.error("Stock not saved UK_ISIN_CODE duplicate");
						  return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST); 
					  }
					}
				 
				  }
				  
				  if(SVC_RETUER_CODE!=null) {
					  if(!SVC_RETUER_CODE.toString().isEmpty()) {
					  if(fUND_SHARE_COMPANY_MSTRRepository.find_UK_RETUER_CODE(SVC_RETUER_CODE.toString().trim())==0) {
						  fscm.setSVC_RETUER_CODE(SVC_RETUER_CODE.toString().trim()); 
					  }
					  else {
						  json.put("msg","UK_RETUER_CODE");
						  logger.error("Stock not saved UK_RETUER_CODE duplicate");
						  return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST); 
					  }
					}
				
				  }
				  
				
				  
				  if(SVC_EXEC_CODE!=null) {
					  if(!SVC_EXEC_CODE.toString().isEmpty()) {
					  if(fUND_SHARE_COMPANY_MSTRRepository.find_UK_TICKER(SVC_EXEC_CODE.toString().trim())==0) {
					  fscm.setSVC_EXEC_CODE(SVC_EXEC_CODE.toString().trim());
					  }else {
						json.put("msg","UK_TICKER");
						logger.error("Stock not saved UK_TICKER duplicate");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);  
					  }
					 }
				  }
				  
				  if(SVC_CUSIP!=null) {
					 if(!SVC_CUSIP.toString().isEmpty()) {
					  if(fUND_SHARE_COMPANY_MSTRRepository.find_UK_CUSIP(SVC_CUSIP.toString().trim())==0) {
						  fscm.setSVC_CUSIP(SVC_CUSIP.toString().trim());
					  }else {
						  json.put("msg","UK_CUSIP");
						  logger.error("Stock not saved UK_CUSIP duplicate");
						  return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					  }
					 }
				  }
				
				 
				  
				  if(SVC_SHARES_ISSUE!=null) {
					 if(!SVC_SHARES_ISSUE.toString().isEmpty()) {
					  if(SVC_SHARES_ISSUE.toString().isEmpty()==false) {
						  if(fUND_SHARE_COMPANY_MSTRRepository.find_UK_SEDOL(new Long(SVC_SHARES_ISSUE.toString().trim()))==0)
						  {
						  fscm.setSVC_SHARES_ISSUE(new Long(SVC_SHARES_ISSUE.toString().trim()));
						  }
						  else {
							  json.put("msg","UK_SEDOL");
							  logger.error("Stock not saved UK_SEDOL duplicate");
							  return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);  
						  }
					  }
					 }
				  }
				  
				 
				  if(SVC_PUT_CALL_EDATE!=null) {
					  if(SVC_PUT_CALL_EDATE.toString().isEmpty()==false) {
						  Calendar cl=Calendar.getInstance();
						  cl.setTimeInMillis(new Long(SVC_PUT_CALL_EDATE.toString().trim()));
						  fscm.setSVC_PUT_CALL_EDATE(cl);
					  }
				
				  }
				 if(SVC_PUT_CALL_LOTSIZE!=null) {
					 if(SVC_PUT_CALL_LOTSIZE.toString().isEmpty()==false) {
						 fscm.setSVC_PUT_CALL_LOTSIZE(new Integer(SVC_PUT_CALL_LOTSIZE.toString().trim()));
					 }
				 }
				 if(SVC_BOND_ISSUE_NO!=null) {
				  fscm.setSVC_BOND_ISSUE_NO(SVC_BOND_ISSUE_NO.toString());
				 }
				 if(SVC_BOND_DATE!=null) {
					 if(SVC_BOND_DATE.toString().isEmpty()==false) {
						 Calendar cl=Calendar.getInstance();
						 cl.setTimeInMillis(new Long(SVC_BOND_DATE.toString().trim()));
						 fscm.setSVC_BOND_DATE(cl);
					 }
				 }
				 if(SVC_BOND_MATURITY_DT!=null) {
					 if(SVC_BOND_MATURITY_DT.toString().isEmpty()==false) {
						 Calendar cl=Calendar.getInstance();
						 cl.setTimeInMillis(new Long(SVC_BOND_MATURITY_DT.toString().trim()));
						 fscm.setSVC_BOND_MATURITY_DT(cl);
					 }
				 
				 }
				 
				 if(SVC_PAIDUP_VALUE!=null) {
					 fscm.setSVC_PAIDUP_VALUE(SVC_PAIDUP_VALUE.toString()); 
				 }
				 
				 if(SVC_BOND_DURATION!=null) {
					 fscm.setSVC_BOND_DURATION(SVC_BOND_DURATION.toString()); 
				 }
				  if(SVC_BOND_RATE!=null) {
					  if(SVC_BOND_RATE.toString().isEmpty()==false) {
						  fscm.setSVC_BOND_RATE(new Double(SVC_BOND_RATE.toString().trim()));
					  }
				  }
				  if(SVC_DIVISOR_DAYS!=null) {
					  if(SVC_DIVISOR_DAYS.toString().isEmpty()==false) {
						  fscm.setSVC_DIVISOR_DAYS(new Integer(SVC_DIVISOR_DAYS.toString().trim()));
					  }
				  }
				  if(SVC_CLOSE_ALTERNATE_PRICE!=null) {
				  fscm.setSVC_CLOSE_ALTERNATE_PRICE(SVC_CLOSE_ALTERNATE_PRICE.toString());
				  }
				  if(SVC_PRICE_CALCULATE!=null) {
				  fscm.setSVC_PRICE_CALCULATE(SVC_PRICE_CALCULATE.toString());
				  }
				  if(SVC_MARGIN_MAX!=null) {
					  if(SVC_MARGIN_MAX.toString().isEmpty()==false) {
						  fscm.setSVC_MARGIN_MAX(new Double(SVC_MARGIN_MAX.toString().trim()));
					  }
				  }
				  if(WMS_COMMENTS!=null) {
					  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
						}
				  fscm.setWMS_STATUS("Not Approved");
						
				  fscm.setSVC_UID(fuser.getSVC_UID());		
				  fscm.setIV_ENTER_UID(fuser.getSVC_UID());
				  fscm.setIV_ENTER_DATE(Calendar.getInstance());
				  fscm.setSVC_DATE(Calendar.getInstance());
				  try
					{
					  fscm=fUND_SHARE_COMPANY_MSTRRepository.save(fscm);
					  final  FUND_SHARE_COMPANY_MSTR fscmt=fscm;
						if(fscm!=null)
						{
							if(coupen!=null) {
							if(coupen.isEmpty()==false) {
								coupen.forEach(coup->{
									FUND_BOND_INT_RATE fbir=new FUND_BOND_INT_RATE();
								  try {
									if(coup.get("effectivedate")!=null) {
										Calendar cm=Calendar.getInstance();
										cm.setTimeInMillis(new Long(coup.get("effectivedate").toString()));
										fbir.setFBI_DATE(cm);
									}
									fbir.setFBI_INT(new Double(coup.get("coupenrate").toString()));
									fbir.setSVC_CODE(fscmt);
									fbir=fUND_BOND_INT_RATERepository.save(fbir);
									if(fbir!=null) {
										logger.info("Both Bond saved for Stock:"+fbir.getFBI_BOND());
									}
								  }catch (Exception e) {
									logger.error("In coupen update->"+e.getMessage());
								}
									
								});
							}else {
								logger.error("Stock Without coupen");
							}
						}
							
							    json.put("msg", "saved");				
								FUND_USER_LOG ful=new FUND_USER_LOG();
								ful.setSVC_UID(fuser.getSVC_UID());
								ful.setSVL_USERID(fuser.getSVU_NAME());
								ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
								ful.setSVL_TTYPE("CREATE");
								ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
								ful.setSVL_DATE(Calendar.getInstance());
								ful=fUND_USER_LOGRepository.save(ful);
								if(ful!=null) {
									logger.info("Both Record and Logs saved for Stock:"+fscm.getSVC_NAME());
									json.put("logs","logs are saved");
								}
								else {
									logger.info("Record is saved but logs can't saved due error in saving of logs");
									json.put("logs","Record is saved but logs can't saved due error in saving of logs");
								}
							
							logger.info("Saved New Stock:"+fscm.getSVC_NAME());
							json.put("stockid", fscm.getSVC_CODE());
							json.put("stcokname", fscm.getSVC_NAME());
							json.put("createdby", fscm.getIV_ENTER_UID());
							json.put("createdon", fscm.getIV_ENTER_DATE());
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
						}
						else {
							json.put("msg","Not saved");
							logger.error("Stock not saved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					catch(Exception e)
					{
						json.put("msg", e.getMessage());
						logger.error("Stock not saved :"+e.getMessage());
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						
					}
					
				
				  	
		  }

	}
	
	@PostMapping("/updatestock")
	public ResponseEntity<Map<String, Object>> update(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		//Object SVC_DATE                   Record Creation Date - Auto
		  //Object SVC_CODE                   Auto Serial Number
		  Object SVC_CODE =requestJson.get("stockid");
		  Object SVC_NAME =requestJson.get("stockname");
		  Object SVC_EXEC_CODE=requestJson.get("ticker");	    			// Ticker
		  Object SVC_CUSIP=requestJson.get("cusip");              			//  Cusip
		  Object SVC_RETUER_CODE=requestJson.get("reuterscode");        	//    Retuer Code
		  Object SVC_BLOM_CODE=requestJson.get("bloombergcode");       		//      Bloomberg code
		  Object SVC_ISIN_CODE=requestJson.get("isincode");            		// ISIN Code
		  Object SVC_SHARES_ISSUE=requestJson.get("sedol");          		// SEDOL
		  Object SVC_CUST_COUNTRY=requestJson.get("countryid");	  			//Country
		  Object SVC_CURR_CODE =requestJson.get("currencyid");	 			// Currency
		  Object SVC_EXCHANGE=requestJson.get("exchangeid");    			// Exchange
		  Object SVC_INDUSTRY_TYPE=requestJson.get("sectorid"); 			// Sector
		  Object SVC_CUST_SECURITY=requestJson.get("assetid");				//  Asset
		  Object SVC_PUT_CALL_EDATE=requestJson.get("expiry_date");			// Expiry Date
		  Object SVC_PUT_CALL_LOTSIZE=requestJson.get("lot_size"); 			//Lot Size
		  Object SVC_BOND_ISSUE_NO=requestJson.get("issue_number");			//  Issue No
		  Object SVC_BOND_DATE =requestJson.get("issue_date");  			 // Issue Date
		  Object SVC_BOND_MATURITY_DT=requestJson.get("maturity_date");		// Maturity Date
		  Object SVC_PAIDUP_VALUE=requestJson.get("unit_price");			//Unit Price / Rate/
		  Object SVC_BOND_DURATION=requestJson.get("frequency"); 			//Frequency 
		  Object SVC_BOND_RATE =requestJson.get("coupon_rate");				// Coupon Rate %
		  Object SVC_DIVISOR_DAYS=requestJson.get("Divisor_days");			//Divisor Days
		  Object SVC_CLOSE_ALTERNATE_PRICE=requestJson.get("close_alternate_price");	//Close/Alternate price,
		  Object SVC_PRICE_CALCULATE=requestJson.get("price_calculated_flag");			//Price calculated flag,
		  Object SVC_MARGIN_MAX=requestJson.get("calculate_value");						//Calculate Value,
		  Object WMS_COMMENTS=requestJson.get("comments");
		  Object createdby=requestJson.get("modifiedby");
		  Object SVL_SCREEN=requestJson.get("screentype");
		  Object SVL_DESC=requestJson.get("log");
		  List<Map<String,Object>> coupen=(List<Map<String,Object>>)requestJson.get("coupens");
		  if(SVC_CODE==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
			  json.put("msg", "Please check Input json, missing some required attributes");
			  logger.error("Please check Input json, missing some required attributes");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		  }
		  else {
			  FUND_USERS fuser=isValid(createdby.toString());
				if(fuser==null)
				{
						json.put("msg", "Createdby is not valid user");
						logger.error("Createdby is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
				try {
					FUND_SHARE_COMPANY_MSTR fscm=fUND_SHARE_COMPANY_MSTRRepository.findOne(new Long(SVC_CODE.toString().trim()));
					if(fscm==null) {
						json.put("msg", "Stock is Not valid please send correct Stock id");
						logger.error("Stock is Not valid");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
					 
					  fscm.setSVC_NAME(SVC_NAME.toString().trim());
					  if(SVC_CUST_COUNTRY!=null) {
						  if(SVC_CUST_COUNTRY.toString().isEmpty()==false) {
							  fscm.setSVC_CUST_COUNTRY(new Integer(SVC_CUST_COUNTRY.toString()));
						  }
					  }
					 if(SVC_CURR_CODE!=null)
					 {
						 if(SVC_CURR_CODE.toString().isEmpty()==false)
						 {
							 fscm.setSVC_CURR_CODE(SVC_CURR_CODE.toString().trim());
						 }
					 }
					 if(SVC_EXCHANGE!=null) {
						 if(SVC_EXCHANGE.toString().isEmpty()==false) {
							  fscm.setSVC_EXCHANGE(SVC_EXCHANGE.toString().trim());
						 }
					 }
					
					  if(SVC_INDUSTRY_TYPE!=null) {
						  if(SVC_INDUSTRY_TYPE.toString().isEmpty()==false) {
							  fscm.setSVC_INDUSTRY_TYPE(SVC_INDUSTRY_TYPE.toString().trim());  
						  }
					  }
					 
					  fscm.setSVC_CUST_SECURITY(new Integer(SVC_CUST_SECURITY.toString()));
					  
//					  if(SVC_EXEC_CODE!=null) {
//						  fscm.setSVC_EXEC_CODE(SVC_EXEC_CODE.toString().trim());
//					  }
//					  if(SVC_CUSIP!=null) {
//					  fscm.setSVC_CUSIP(SVC_CUSIP.toString().trim());
//					  }
//					  if(SVC_RETUER_CODE!=null) {
//						 fscm.setSVC_RETUER_CODE(SVC_RETUER_CODE.toString().trim()); 
//					  }
					  
//					  if(SVC_BLOM_CODE!=null) {
//					  fscm.setSVC_BLOM_CODE(SVC_BLOM_CODE.toString().trim());
//					  }
//					  if(SVC_ISIN_CODE!=null) {
//					  fscm.setSVC_ISIN_CODE(SVC_ISIN_CODE.toString().trim());
//					  }
//					  if(SVC_SHARES_ISSUE!=null) {
//					  fscm.setSVC_SHARES_ISSUE(new Long(SVC_SHARES_ISSUE.toString()));
//					  }
					 
					  if(SVC_PUT_CALL_EDATE!=null) {
					  Calendar cl=Calendar.getInstance();
					  cl.setTimeInMillis(new Long(SVC_PUT_CALL_EDATE.toString()));
					  fscm.setSVC_PUT_CALL_EDATE(cl);
					  }
					 if(SVC_PUT_CALL_LOTSIZE!=null) {
					  fscm.setSVC_PUT_CALL_LOTSIZE(new Integer(SVC_PUT_CALL_LOTSIZE.toString()));
					 }
					 if(SVC_BOND_ISSUE_NO!=null) {
					  fscm.setSVC_BOND_ISSUE_NO(SVC_BOND_ISSUE_NO.toString());
					 }
					 if(SVC_BOND_DATE!=null) {
						 Calendar cl=Calendar.getInstance();
					  cl.setTimeInMillis(new Long(SVC_BOND_DATE.toString()));
					  fscm.setSVC_BOND_DATE(cl);
					 }
					 if(SVC_BOND_MATURITY_DT!=null) {
					  Calendar cl=Calendar.getInstance();
					  cl.setTimeInMillis(new Long(SVC_BOND_MATURITY_DT.toString()));
					  fscm.setSVC_BOND_MATURITY_DT(cl);
					 }
					 
					 if(SVC_PAIDUP_VALUE!=null) {
						 fscm.setSVC_PAIDUP_VALUE(SVC_PAIDUP_VALUE.toString()); 
					 }
					 
					 if(SVC_BOND_DURATION!=null) {
						 fscm.setSVC_BOND_DURATION(SVC_BOND_DURATION.toString()); 
					 }
					  if(SVC_BOND_RATE!=null) {
						  if(SVC_BOND_RATE.toString().isEmpty()==false) {
							  fscm.setSVC_BOND_RATE(new Double(SVC_BOND_RATE.toString().trim()));
						  }
					  }
					  if(SVC_DIVISOR_DAYS!=null) {
						  if(SVC_DIVISOR_DAYS.toString().isEmpty()==false) {
							  fscm.setSVC_DIVISOR_DAYS(new Integer(SVC_DIVISOR_DAYS.toString().trim()));
						  }
					  }
					  if(SVC_CLOSE_ALTERNATE_PRICE!=null) {
					  fscm.setSVC_CLOSE_ALTERNATE_PRICE(SVC_CLOSE_ALTERNATE_PRICE.toString());
					  }
					  if(SVC_PRICE_CALCULATE!=null) {
					  fscm.setSVC_PRICE_CALCULATE(SVC_PRICE_CALCULATE.toString());
					  }
					  if(SVC_MARGIN_MAX!=null) {
						  if(SVC_MARGIN_MAX.toString().isEmpty()==false) {
							  fscm.setSVC_MARGIN_MAX(new Double(SVC_MARGIN_MAX.toString().trim()));
						  }
					  }
					  if(WMS_COMMENTS!=null) {
						  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
					  }
					
					  /*
					   * For duplicate 
					   */
					  
					  if(SVC_BLOM_CODE!=null) {
						  if(!SVC_BLOM_CODE.toString().isEmpty()) {
						  if(fUND_SHARE_COMPANY_MSTRRepository.find_UK_BLOM_CODE(SVC_BLOM_CODE.toString().trim(),fscm.getSVC_CODE())==0) {
							  fscm.setSVC_BLOM_CODE(SVC_BLOM_CODE.toString().trim());
						  }
						  else {
							  json.put("msg","SVC_BLOM_CODE");
							  logger.error("Stock not saved SVC_BLOM_CODE duplicate");
							  return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST); 
						  }
						 }
					  }
					  
					  if(SVC_ISIN_CODE!=null) {
						  if(!SVC_ISIN_CODE.toString().isEmpty()) {
						  if(fUND_SHARE_COMPANY_MSTRRepository.find_UK_ISIN_CODE(SVC_ISIN_CODE.toString().trim(),fscm.getSVC_CODE())==0)
						  {
							  fscm.setSVC_ISIN_CODE(SVC_ISIN_CODE.toString().trim());  
						  }else {
							  json.put("msg","UK_ISIN_CODE");
							  logger.error("Stock not saved UK_ISIN_CODE duplicate");
							  return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST); 
						  }
						}
					 
					  }
					  
					  if(SVC_RETUER_CODE!=null) {
						  if(!SVC_RETUER_CODE.toString().isEmpty()) {
						  if(fUND_SHARE_COMPANY_MSTRRepository.find_UK_RETUER_CODE(SVC_RETUER_CODE.toString().trim(),fscm.getSVC_CODE())==0) {
							  fscm.setSVC_RETUER_CODE(SVC_RETUER_CODE.toString().trim()); 
						  }
						  else {
							  json.put("msg","UK_RETUER_CODE");
							  logger.error("Stock not saved UK_RETUER_CODE duplicate");
							  return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST); 
						  }
						}
					
					  }
					  
					
					  
					  if(SVC_EXEC_CODE!=null) {
						  if(!SVC_EXEC_CODE.toString().isEmpty()) {
						  if(fUND_SHARE_COMPANY_MSTRRepository.find_UK_TICKER(SVC_EXEC_CODE.toString().trim(),fscm.getSVC_CODE())==0) {
						  fscm.setSVC_EXEC_CODE(SVC_EXEC_CODE.toString().trim());
						  }else {
							json.put("msg","UK_TICKER");
							logger.error("Stock not saved UK_TICKER duplicate");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);  
						  }
						 }
					  }
					  
					  if(SVC_CUSIP!=null) {
						 if(!SVC_CUSIP.toString().isEmpty()) {
						  if(fUND_SHARE_COMPANY_MSTRRepository.find_UK_CUSIP(SVC_CUSIP.toString().trim(),fscm.getSVC_CODE())==0) {
							  fscm.setSVC_CUSIP(SVC_CUSIP.toString().trim());
						  }else {
							  json.put("msg","UK_CUSIP");
							  logger.error("Stock not saved UK_CUSIP duplicate");
							  return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						  }
						 }
					  }
					
					 
					  
					  if(SVC_SHARES_ISSUE!=null) {
						 if(!SVC_SHARES_ISSUE.toString().isEmpty()) {
						  if(SVC_SHARES_ISSUE.toString().isEmpty()==false) {
							  if(fUND_SHARE_COMPANY_MSTRRepository.find_UK_SEDOL(new Long(SVC_SHARES_ISSUE.toString().trim()),fscm.getSVC_CODE())==0)
							  {
							  fscm.setSVC_SHARES_ISSUE(new Long(SVC_SHARES_ISSUE.toString().trim()));
							  }
							  else {
								  json.put("msg","UK_SEDOL");
								  logger.error("Stock not saved UK_SEDOL duplicate");
								  return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);  
							  }
						  }
						 }
					  }
					  
					  
					  /*
					   * For Duplicate
					   * 
					   */
					  fscm.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());
					  fscm.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
					  
					  try
						{
						  fscm=fUND_SHARE_COMPANY_MSTRRepository.save(fscm);
						  final  FUND_SHARE_COMPANY_MSTR fscmt=fscm;
							if(fscm!=null)
							{
								if(coupen!=null) {
									coupen.forEach(coup->{
										Object bondid=coup.get("bondid");
										if(bondid==null) {
											logger.error("No Coupen found");
										}else {
											try {
												FUND_BOND_INT_RATE fbir=fUND_BOND_INT_RATERepository.findOne(new Long(bondid.toString().trim()));
														if(coup.get("effectivedate")!=null) {
															Calendar cm=Calendar.getInstance();
															cm.setTimeInMillis(new Long(coup.get("effectivedate").toString()));
															fbir.setFBI_DATE(cm);
														}
														fbir.setFBI_INT(new Double(coup.get("coupenrate").toString()));
														fbir.setSVC_CODE(fscmt);
														fbir=fUND_BOND_INT_RATERepository.save(fbir);
														if(fbir!=null) {
															logger.info("Both Bond saved for Stock:"+fbir.getFBI_BOND());
														}
											}catch (Exception e) {
												logger.error("In coupen update->"+e.getMessage());
											}
										}
										
									});
									
								}
								
								    json.put("msg", "saved");				
									FUND_USER_LOG ful=new FUND_USER_LOG();
									ful.setSVC_UID(fuser.getSVC_UID());
									ful.setSVL_USERID(fuser.getSVU_NAME());
									ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
									ful.setSVL_TTYPE("UPDATE");
									ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
									ful.setSVL_DATE(Calendar.getInstance());
									ful=fUND_USER_LOGRepository.save(ful);
									if(ful!=null) {
										logger.info("Both Record and Logs saved for Stock:"+fscm.getSVC_NAME());
										json.put("logs","logs are saved");
									}
									else {
										logger.info("Record is saved but logs can't saved due error in saving of logs");
										json.put("logs","Record is saved but logs can't saved due error in saving of logs");
									}
								
								logger.info("Saved New Stock:"+fscm.getSVC_NAME());
								
								json.put("stockid", fscm.getSVC_CODE());
								json.put("stockname",fscm.getSVC_NAME());
								json.put("ticker",fscm.getSVC_EXEC_CODE());
								json.put("cusip",fscm.getSVC_CUSIP());
								json.put("reuterscode",fscm.getSVC_RETUER_CODE());
								json.put("bloombergcode",fscm.getSVC_BLOM_CODE());
								json.put("isincode",fscm.getSVC_ISIN_CODE());
								json.put("sedol",fscm.getSVC_SHARES_ISSUE());
								FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(fscm.getSVC_CURR_CODE());
								if(fcm!=null) {
									json.put("currencyid",fcm.getSVC_CODE());
									json.put("currencyname", fcm.getSVC_NAME());
									json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
								}
								
								FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(fscm.getSVC_CUST_COUNTRY());
								if(fcc!=null) {
									json.put("countryid",fcc.getSVC_CODE());
									json.put("countryname", fcc.getSVC_NAME());
									json.put("countryshortname", fcc.getSVC_SHORT_NAME());
								}
								
								FUND_EXCHANGE_MSTR fem=stockParameters.getFUND_EXCHANGE_MSTR(fscm.getSVC_EXCHANGE());
								if(fem!=null) {
									json.put("exchangeid",fem.getSVE_CODE());
									json.put("exchangename", fem.getSVE_NAME());
									json.put("exchangeshortname", fem.getSVE_SHORT_NAME());
								}
								
								FUND_INDUSTRY_MSTR fim=stockParameters.getFUND_INDUSTRY_MSTR(fscm.getSVC_INDUSTRY_TYPE());
								if(fim!=null) {
									json.put("sectorid",fim.getSVI_CODE());
									json.put("sectorname", fim.getSVI_NAME());
									json.put("sectorshortname", fim.getSVI_SHORT_NAME());
								}
								
								FUND_INSTRUMENT_MSTR fiim=stockParameters.getFUND_INSTRUMENT_MSTR(fscm.getSVC_CUST_SECURITY());
								if(fiim!=null) {
									json.put("assetid",fiim.getSVI_CODE());
									json.put("assetname", fiim.getSVI_NAME());
									json.put("assetshortname", fiim.getSVI_SHORT_NAME());
								}				
								
								json.put("expiry_date",fscm.getSVC_PUT_CALL_EDATE());
								json.put("lot_size",fscm.getSVC_PUT_CALL_LOTSIZE());
								json.put("issue_number",fscm.getSVC_BOND_ISSUE_NO());
								json.put("issue_date",fscm.getSVC_BOND_DATE());
								json.put("maturity_date",fscm.getSVC_BOND_MATURITY_DT());
								json.put("unit_price",fscm.getSVC_PAIDUP_VALUE());
								json.put("frequency",fscm.getSVC_BOND_DURATION());
								json.put("coupon_rate",fscm.getSVC_BOND_RATE());
								json.put("Divisor_days",fscm.getSVC_DIVISOR_DAYS());
								json.put("close_alternate_price",fscm.getSVC_CLOSE_ALTERNATE_PRICE());
								json.put("price_calculated_flag",fscm.getSVC_PRICE_CALCULATE());
								json.put("calculate_value",fscm.getSVC_MARGIN_MAX());
								json.put("comments", fscm.getWMS_COMMENTS());
								json.put("status", fscm.getWMS_STATUS());
								List<Map<String,Object>> coupens=new ArrayList<Map<String,Object>>();
								fscm.getfUND_BOND_INT_RATE().forEach(bond->{
									Map<String,Object> bondJson=new HashMap<>();
									bondJson.put("bondid", bond.getFBI_BOND());
									bondJson.put("expiredate", bond.getFBI_DATE());
									bondJson.put("rate", bond.getFBI_INT());
									
									coupens.add(bondJson);
									
								});
								json.put("coupens", coupens);
								FUND_USERS user=null;
								if(fscm.getIV_ENTER_UID()!=null) {
									user=getUserName(fscm.getIV_ENTER_UID());
									if(user!=null) {
									json.put("enteredby", user.getSVU_NAME());
									json.put("enteredbyuserid", user.getSVU_USER_NAME());
									json.put("enteredbyuuid", user.getSVC_UID());
									json.put("entereddate", fscm.getIV_ENTER_DATE());
									}
									else {
										json.put("enteredby", null);
										json.put("enteredbyuserid", null);
										json.put("enteredbyuuid", null);
										json.put("entereddate", null);
									}
								}else {
									json.put("enteredby", null);
									json.put("enteredbyuserid", null);
									json.put("enteredbyuuid", null);
									json.put("entereddate", null);
								}
								
								if(fscm.getIV_APPROVE_UID()!=null) {
									user=getUserName(fscm.getIV_APPROVE_UID());
									if(user!=null) {
									json.put("approvedby", user.getSVU_NAME());
									json.put("approvedbyuserid", user.getSVU_USER_NAME());
									json.put("approvedbyuuid", user.getSVC_UID());
									json.put("approveddate", fscm.getIV_APPROVE_DATE());
									}
									else {
										json.put("approvedby", null);
										json.put("approvedbyuserid", null);
										json.put("approvedbyuuid", null);
										json.put("approveddate", null);
									}
								}else {
									json.put("approvedby", null);
									json.put("approvedbyuserid", null);
									json.put("approvedbyuuid", null);
									json.put("approveddate", null);
								}
								
								if(fscm.getIV_LAST_UPDATE_UID()!=null) {
									user=getUserName(fscm.getIV_LAST_UPDATE_UID());
									if(user!=null) {
									json.put("modifiedby", user.getSVU_NAME());
									json.put("modifiedbyuserid", user.getSVU_USER_NAME());
									json.put("modifiedbyuuid", user.getSVC_UID());
									json.put("modifieddate", fscm.getIV_LAST_UPDATE_DATE());
									}
									else {
										json.put("modifiedby", null);
										json.put("modifiedbyuserid", null);
										json.put("modifiedbyuuid", null);
										json.put("modifieddate", null);
									}
								}else {
									json.put("modifiedby", null);
									json.put("modifiedbyuserid", null);
									json.put("modifiedbyuuid", null);
									json.put("modifieddate", null);
								}
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
							}
							else {
								json.put("msg","Not saved");
								logger.error("Stock not saved");
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							}
						}
						catch(Exception e)
						{
							json.put("msg", e.getMessage());
							logger.error("Stock not saved :"+e.getMessage());
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							
						}
						
					
					  	
				}catch (Exception e) {
					json.put("msg",e.getMessage());
					logger.error("Stock is Not valid"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
		  }
		
		  

	}

	@PostMapping("/approvedstock")
	public ResponseEntity<Map<String, Object>> Approved(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		//Object SVC_DATE                   Record Creation Date - Auto
		  //Object SVC_CODE                   Auto Serial Number
		  Object SVC_CODE =requestJson.get("stockid");
		 
		  Object WMS_COMMENTS=requestJson.get("comments");
		  Object createdby=requestJson.get("approvedby");
		  Object SVL_SCREEN=requestJson.get("screentype");
		  Object SVL_DESC=requestJson.get("log");
		  
		  if(SVC_CODE==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
			  json.put("msg", "Please check Input json, missing some required attributes");
			  logger.error("Please check Input json, missing some required attributes");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		  }
		  else {
			  FUND_USERS fuser=isValid(createdby.toString());
				if(fuser==null)
				{
						json.put("msg", "Createdby is not valid user");
						logger.error("Createdby is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
				try {
					FUND_SHARE_COMPANY_MSTR fscm=fUND_SHARE_COMPANY_MSTRRepository.findOne(new Long(SVC_CODE.toString().trim()));
					if(fscm==null) {
						json.put("msg", "Stock is Not valid please send correct Stock id");
						logger.error("Stock is Not valid");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
					 
					/*
					 * Checking Parameter for approve
					 * SVC_CURR_CODE=countryid
					 * SVC_CURR_CODE=currencyid
					 * SVC_INDUSTRY_TYPE=sectorid
					 * SVC_CUST_SECURITY=assetid
					 * SVC_EXCHANGE=exchangeid
					 */
					Object SVC_CUST_COUNTRY=fscm.getSVC_CUST_COUNTRY();
					Object SVC_CURR_CODE =fscm.getSVC_CURR_CODE();
					Object SVC_INDUSTRY_TYPE =fscm.getSVC_INDUSTRY_TYPE();
					Object SVC_CUST_SECURITY =fscm.getSVC_CUST_SECURITY();
					Object SVC_EXCHANGE =fscm.getSVC_EXCHANGE();
					
					if(SVC_CUST_COUNTRY!=null) {
						FUND_COUNTRIES fund_cust=stockParameters.getFUND_COUNTRIES(SVC_CUST_COUNTRY);
						if(fund_cust.getIV_APPROVE_UID()==null) {
							json.put("msg", "Country is not approved");
							logger.error("Country is not approved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}
					}
					if(SVC_CURR_CODE!=null) {
						FUND_CURRENCY_MSTR fund_cust=stockParameters.getFUND_CURRENCY_MSTR(SVC_CURR_CODE);
						if(fund_cust.getIV_APPROVE_UID()==null) {
							json.put("msg", "Currency is not approved");
							logger.error("Currency is not approved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}	
					}
					
					if(SVC_INDUSTRY_TYPE!=null) {
						FUND_INDUSTRY_MSTR fund_cust=stockParameters.getFUND_INDUSTRY_MSTR(SVC_INDUSTRY_TYPE);
						if(fund_cust.getIV_APPROVE_UID()==null) {
							json.put("msg", "Sector is not approved");
							logger.error("Sector is not approved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}	
					}
					
					if(SVC_CUST_SECURITY!=null) {
						FUND_INSTRUMENT_MSTR fund_cust=stockParameters.getFUND_INSTRUMENT_MSTR(SVC_CUST_SECURITY);
						if(fund_cust.getIV_APPROVE_UID()==null) {
							json.put("msg", "Asset is not approved");
							logger.error("Asset is not approved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}	
					}
					
					if(SVC_EXCHANGE!=null) {
						FUND_EXCHANGE_MSTR fund_cust=stockParameters.getFUND_EXCHANGE_MSTR(SVC_EXCHANGE);
						if(fund_cust.getIV_APPROVE_UID()==null) {
							json.put("msg", "Exchange is not approved");
							logger.error("Exchange is not approved");
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
						}	
					}
					/*
					 * Update parameter
					 * 
					 */
					
					  if(WMS_COMMENTS!=null) {
						  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
					  }
					  fscm.setWMS_STATUS("Approved");
					  fscm.setIV_APPROVE_UID(fuser.getSVC_UID());
					  fscm.setIV_APPROVE_DATE(Calendar.getInstance());
					  
					  try
						{
						  fscm=fUND_SHARE_COMPANY_MSTRRepository.save(fscm);
							if(fscm!=null)
							{							
								    json.put("msg", "saved");				
									FUND_USER_LOG ful=new FUND_USER_LOG();
									ful.setSVC_UID(fuser.getSVC_UID());
									ful.setSVL_USERID(fuser.getSVU_NAME());
									ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
									ful.setSVL_TTYPE("ARROVED");
									ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
									ful.setSVL_DATE(Calendar.getInstance());
									ful=fUND_USER_LOGRepository.save(ful);
									if(ful!=null) {
										logger.info("Both Record and Logs saved for Stock:"+fscm.getSVC_NAME());
										json.put("logs","logs are saved");
									}
									else {
										logger.info("Record is saved but logs can't saved due error in saving of logs");
										json.put("logs","Record is saved but logs can't saved due error in saving of logs");
									}
								
								logger.info("Stock Approved:"+fscm.getSVC_NAME());
								
								json.put("stockid", fscm.getSVC_CODE());
								json.put("stockname",fscm.getSVC_NAME());
								json.put("ticker",fscm.getSVC_EXEC_CODE());
								json.put("cusip",fscm.getSVC_CUSIP());
								json.put("reuterscode",fscm.getSVC_RETUER_CODE());
								json.put("bloombergcode",fscm.getSVC_BLOM_CODE());
								json.put("isincode",fscm.getSVC_ISIN_CODE());
								json.put("sedol",fscm.getSVC_SHARES_ISSUE());

								FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(fscm.getSVC_CURR_CODE());
								if(fcm!=null) {
									json.put("currencyid",fcm.getSVC_CODE());
									json.put("currencyname", fcm.getSVC_NAME());
									json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
								}
								
								FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(fscm.getSVC_CUST_COUNTRY());
								if(fcc!=null) {
									json.put("countryid",fcc.getSVC_CODE());
									json.put("countryname", fcc.getSVC_NAME());
									json.put("countryshortname", fcc.getSVC_SHORT_NAME());
								}
								
								FUND_EXCHANGE_MSTR fem=stockParameters.getFUND_EXCHANGE_MSTR(fscm.getSVC_EXCHANGE());
								if(fem!=null) {
									json.put("exchangeid",fem.getSVE_CODE());
									json.put("exchangename", fem.getSVE_NAME());
									json.put("exchangeshortname", fem.getSVE_SHORT_NAME());
								}
								
								FUND_INDUSTRY_MSTR fim=stockParameters.getFUND_INDUSTRY_MSTR(fscm.getSVC_INDUSTRY_TYPE());
								if(fim!=null) {
									json.put("sectorid",fim.getSVI_CODE());
									json.put("sectorname", fim.getSVI_NAME());
									json.put("sectorshortname", fim.getSVI_SHORT_NAME());
								}
								
								FUND_INSTRUMENT_MSTR fiim=stockParameters.getFUND_INSTRUMENT_MSTR(fscm.getSVC_CUST_SECURITY());
								if(fiim!=null) {
									json.put("assetid",fiim.getSVI_CODE());
									json.put("assetname", fiim.getSVI_NAME());
									json.put("assetshortname", fiim.getSVI_SHORT_NAME());
								}				
								
								json.put("expiry_date",fscm.getSVC_PUT_CALL_EDATE());
								json.put("lot_size",fscm.getSVC_PUT_CALL_LOTSIZE());
								json.put("issue_number",fscm.getSVC_BOND_ISSUE_NO());
								json.put("issue_date",fscm.getSVC_BOND_DATE());
								json.put("maturity_date",fscm.getSVC_BOND_MATURITY_DT());
								json.put("unit_price",fscm.getSVC_PAIDUP_VALUE());
								json.put("frequency",fscm.getSVC_BOND_DURATION());
								json.put("coupon_rate",fscm.getSVC_BOND_RATE());
								json.put("Divisor_days",fscm.getSVC_DIVISOR_DAYS());
								json.put("close_alternate_price",fscm.getSVC_CLOSE_ALTERNATE_PRICE());
								json.put("price_calculated_flag",fscm.getSVC_PRICE_CALCULATE());
								json.put("calculate_value",fscm.getSVC_MARGIN_MAX());
								json.put("comments", fscm.getWMS_COMMENTS());
								json.put("status", fscm.getWMS_STATUS());
								List<Map<String,Object>> coupens=new ArrayList<Map<String,Object>>();
								fscm.getfUND_BOND_INT_RATE().forEach(bond->{
									Map<String,Object> bondJson=new HashMap<>();
									bondJson.put("bondid", bond.getFBI_BOND());
									bondJson.put("expiredate", bond.getFBI_DATE());
									bondJson.put("rate", bond.getFBI_INT());
									
									coupens.add(bondJson);
									
								});
								json.put("coupens", coupens);
								FUND_USERS user=null;
								if(fscm.getIV_ENTER_UID()!=null) {
									user=getUserName(fscm.getIV_ENTER_UID());
									if(user!=null) {
									json.put("enteredby", user.getSVU_NAME());
									json.put("enteredbyuserid", user.getSVU_USER_NAME());
									json.put("enteredbyuuid", user.getSVC_UID());
									json.put("entereddate", fscm.getIV_ENTER_DATE());
									}
									else {
										json.put("enteredby", null);
										json.put("enteredbyuserid", null);
										json.put("enteredbyuuid", null);
										json.put("entereddate", null);
									}
								}else {
									json.put("enteredby", null);
									json.put("enteredbyuserid", null);
									json.put("enteredbyuuid", null);
									json.put("entereddate", null);
								}
								
								if(fscm.getIV_APPROVE_UID()!=null) {
									user=getUserName(fscm.getIV_APPROVE_UID());
									if(user!=null) {
									json.put("approvedby", user.getSVU_NAME());
									json.put("approvedbyuserid", user.getSVU_USER_NAME());
									json.put("approvedbyuuid", user.getSVC_UID());
									json.put("approveddate", fscm.getIV_APPROVE_DATE());
									}
									else {
										json.put("approvedby", null);
										json.put("approvedbyuserid", null);
										json.put("approvedbyuuid", null);
										json.put("approveddate", null);
									}
								}else {
									json.put("approvedby", null);
									json.put("approvedbyuserid", null);
									json.put("approvedbyuuid", null);
									json.put("approveddate", null);
								}
								
								if(fscm.getIV_LAST_UPDATE_UID()!=null) {
									user=getUserName(fscm.getIV_LAST_UPDATE_UID());
									if(user!=null) {
									json.put("modifiedby", user.getSVU_NAME());
									json.put("modifiedbyuserid", user.getSVU_USER_NAME());
									json.put("modifiedbyuuid", user.getSVC_UID());
									json.put("modifieddate", fscm.getIV_LAST_UPDATE_DATE());
									}
									else {
										json.put("modifiedby", null);
										json.put("modifiedbyuserid", null);
										json.put("modifiedbyuuid", null);
										json.put("modifieddate", null);
									}
								}else {
									json.put("modifiedby", null);
									json.put("modifiedbyuserid", null);
									json.put("modifiedbyuuid", null);
									json.put("modifieddate", null);
								}
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
							}
							else {
								json.put("msg","Not saved");
								logger.error("Stock not saved");
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							}
						}
						catch(Exception e)
						{
							json.put("msg", e.getMessage());
							logger.error("Stock not saved :"+e.getMessage());
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							
						}
						
					
					  	
				}catch (Exception e) {
					json.put("msg",e.getMessage());
					logger.error("Stock is Not valid"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
		  }
		
		  

	}

	@PostMapping("/revokestock")
	public ResponseEntity<Map<String, Object>> revoke(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		//Object SVC_DATE                   Record Creation Date - Auto
		  //Object SVC_CODE                   Auto Serial Number
		  Object SVC_CODE =requestJson.get("stockid");
		 
		  Object WMS_COMMENTS=requestJson.get("comments");
		  Object createdby=requestJson.get("modifiedby");
		  Object SVL_SCREEN=requestJson.get("screentype");
		  Object SVL_DESC=requestJson.get("log");
		 
		  if(SVC_CODE==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null||WMS_COMMENTS==null) {
			  json.put("msg", "Please check Input json, missing some required attributes");
			  logger.error("Please check Input json, missing some required attributes");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		  }
		  else {
			  FUND_USERS fuser=isValid(createdby.toString());
				if(fuser==null)
				{
						json.put("msg", "Createdby is not valid user");
						logger.error("Createdby is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
				try {
					FUND_SHARE_COMPANY_MSTR fscm=fUND_SHARE_COMPANY_MSTRRepository.findOne(new Long(SVC_CODE.toString().trim()));
					if(fscm==null) {
						json.put("msg", "Stock is Not valid please send correct Stock id");
						logger.error("Stock is Not valid");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
					 
					  if(WMS_COMMENTS!=null) {
//						  fscm.setWMS_COMMENTS(WMS_COMMENTS.toString());
					  }
					  fscm.setWMS_STATUS("Not Approved");
					  fscm.setIV_APPROVE_UID(null);
					  fscm.setIV_APPROVE_DATE(null);
					  fscm.setIV_LAST_UPDATE_UID(fuser.getSVC_UID());	
					  fscm.setIV_LAST_UPDATE_DATE(Calendar.getInstance());
					  
					  try
						{
						  fscm=fUND_SHARE_COMPANY_MSTRRepository.save(fscm);
							if(fscm!=null)
							{							
								    json.put("msg", "saved");				
									FUND_USER_LOG ful=new FUND_USER_LOG();
									ful.setSVC_UID(fuser.getSVC_UID());
									ful.setSVL_USERID(fuser.getSVU_NAME());
									ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
									ful.setSVL_TTYPE("REVOKE");
									ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
									ful.setSVL_DATE(Calendar.getInstance());
									ful=fUND_USER_LOGRepository.save(ful);
									if(ful!=null) {
										logger.info("Both Record and Logs saved for Stock:"+fscm.getSVC_NAME());
										json.put("logs","logs are saved");
									}
									else {
										logger.info("Record is saved but logs can't saved due error in saving of logs");
										json.put("logs","Record is saved but logs can't saved due error in saving of logs");
									}
								
								logger.info("Stock Revoked:"+fscm.getSVC_NAME());
								
								json.put("stockid", fscm.getSVC_CODE());
								json.put("stockname",fscm.getSVC_NAME());
								json.put("ticker",fscm.getSVC_EXEC_CODE());
								json.put("cusip",fscm.getSVC_CUSIP());
								json.put("reuterscode",fscm.getSVC_RETUER_CODE());
								json.put("bloombergcode",fscm.getSVC_BLOM_CODE());
								json.put("isincode",fscm.getSVC_ISIN_CODE());
								json.put("sedol",fscm.getSVC_SHARES_ISSUE());
								
								FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(fscm.getSVC_CURR_CODE());
								if(fcm!=null) {
									json.put("currencyid",fcm.getSVC_CODE());
									json.put("currencyname", fcm.getSVC_NAME());
									json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
								}
								
								FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(fscm.getSVC_CUST_COUNTRY());
								if(fcc!=null) {
									json.put("countryid",fcc.getSVC_CODE());
									json.put("countryname", fcc.getSVC_NAME());
									json.put("countryshortname", fcc.getSVC_SHORT_NAME());
								}
								
								FUND_EXCHANGE_MSTR fem=stockParameters.getFUND_EXCHANGE_MSTR(fscm.getSVC_EXCHANGE());
								if(fem!=null) {
									json.put("exchangeid",fem.getSVE_CODE());
									json.put("exchangename", fem.getSVE_NAME());
									json.put("exchangeshortname", fem.getSVE_SHORT_NAME());
								}
								
								FUND_INDUSTRY_MSTR fim=stockParameters.getFUND_INDUSTRY_MSTR(fscm.getSVC_INDUSTRY_TYPE());
								if(fim!=null) {
									json.put("sectorid",fim.getSVI_CODE());
									json.put("sectorname", fim.getSVI_NAME());
									json.put("sectorshortname", fim.getSVI_SHORT_NAME());
								}
								
								FUND_INSTRUMENT_MSTR fiim=stockParameters.getFUND_INSTRUMENT_MSTR(fscm.getSVC_CUST_SECURITY());
								if(fiim!=null) {
									json.put("assetid",fiim.getSVI_CODE());
									json.put("assetname", fiim.getSVI_NAME());
									json.put("assetshortname", fiim.getSVI_SHORT_NAME());
								}				
								
								
								json.put("expiry_date",fscm.getSVC_PUT_CALL_EDATE());
								json.put("lot_size",fscm.getSVC_PUT_CALL_LOTSIZE());
								json.put("issue_number",fscm.getSVC_BOND_ISSUE_NO());
								json.put("issue_date",fscm.getSVC_BOND_DATE());
								json.put("maturity_date",fscm.getSVC_BOND_MATURITY_DT());
								json.put("unit_price",fscm.getSVC_PAIDUP_VALUE());
								json.put("frequency",fscm.getSVC_BOND_DURATION());
								json.put("coupon_rate",fscm.getSVC_BOND_RATE());
								json.put("Divisor_days",fscm.getSVC_DIVISOR_DAYS());
								json.put("close_alternate_price",fscm.getSVC_CLOSE_ALTERNATE_PRICE());
								json.put("price_calculated_flag",fscm.getSVC_PRICE_CALCULATE());
								json.put("calculate_value",fscm.getSVC_MARGIN_MAX());
								json.put("comments", fscm.getWMS_COMMENTS());
								json.put("status", fscm.getWMS_STATUS());
								List<Map<String,Object>> coupens=new ArrayList<Map<String,Object>>();
								fscm.getfUND_BOND_INT_RATE().forEach(bond->{
									Map<String,Object> bondJson=new HashMap<>();
									bondJson.put("bondid", bond.getFBI_BOND());
									bondJson.put("expiredate", bond.getFBI_DATE());
									bondJson.put("rate", bond.getFBI_INT());
									
									coupens.add(bondJson);
									
								});
								json.put("coupens", coupens);
								FUND_USERS user=null;
								if(fscm.getIV_ENTER_UID()!=null) {
									user=getUserName(fscm.getIV_ENTER_UID());
									if(user!=null) {
									json.put("enteredby", user.getSVU_NAME());
									json.put("enteredbyuserid", user.getSVU_USER_NAME());
									json.put("enteredbyuuid", user.getSVC_UID());
									json.put("entereddate", fscm.getIV_ENTER_DATE());
									}
									else {
										json.put("enteredby", null);
										json.put("enteredbyuserid", null);
										json.put("enteredbyuuid", null);
										json.put("entereddate", null);
									}
								}else {
									json.put("enteredby", null);
									json.put("enteredbyuserid", null);
									json.put("enteredbyuuid", null);
									json.put("entereddate", null);
								}
								
								if(fscm.getIV_APPROVE_UID()!=null) {
									user=getUserName(fscm.getIV_APPROVE_UID());
									if(user!=null) {
									json.put("approvedby", user.getSVU_NAME());
									json.put("approvedbyuserid", user.getSVU_USER_NAME());
									json.put("approvedbyuuid", user.getSVC_UID());
									json.put("approveddate", fscm.getIV_APPROVE_DATE());
									}
									else {
										json.put("approvedby", null);
										json.put("approvedbyuserid", null);
										json.put("approvedbyuuid", null);
										json.put("approveddate", null);
									}
								}else {
									json.put("approvedby", null);
									json.put("approvedbyuserid", null);
									json.put("approvedbyuuid", null);
									json.put("approveddate", null);
								}
								
								if(fscm.getIV_LAST_UPDATE_UID()!=null) {
									user=getUserName(fscm.getIV_LAST_UPDATE_UID());
									if(user!=null) {
									json.put("modifiedby", user.getSVU_NAME());
									json.put("modifiedbyuserid", user.getSVU_USER_NAME());
									json.put("modifiedbyuuid", user.getSVC_UID());
									json.put("modifieddate", fscm.getIV_LAST_UPDATE_DATE());
									}
									else {
										json.put("modifiedby", null);
										json.put("modifiedbyuserid", null);
										json.put("modifiedbyuuid", null);
										json.put("modifieddate", null);
									}
								}else {
									json.put("modifiedby", null);
									json.put("modifiedbyuserid", null);
									json.put("modifiedbyuuid", null);
									json.put("modifieddate", null);
								}
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
							}
							else {
								json.put("msg","Not saved");
								logger.error("Stock not saved");
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							}
						}
						catch(Exception e)
						{
							json.put("msg", e.getMessage());
							logger.error("Stock not saved :"+e.getMessage());
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							
						}
						
					
					  	
				}catch (Exception e) {
					json.put("msg",e.getMessage());
					logger.error("Stock is Not valid"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
		  }
		
		  

	}
	
	@PostMapping("/deletestock")
	public ResponseEntity<Map<String, Object>> delete(RequestEntity<Map<String, Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		  Object SVC_CODE =requestJson.get("stockid");
		  Object createdby=requestJson.get("modifiedby");
		  Object SVL_SCREEN=requestJson.get("screentype");
		  Object SVL_DESC=requestJson.get("log");
		 
		  if(SVC_CODE==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
			  json.put("msg", "Please check Input json, missing some required attributes");
			  logger.error("Please check Input json, missing some required attributes");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		  }
		  else {
			  FUND_USERS fuser=isValid(createdby.toString());
				if(fuser==null)
				{
						json.put("msg", "Createdby is not valid user");
						logger.error("Createdby is not valid user");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
				
				try {
					FUND_SHARE_COMPANY_MSTR fscm=fUND_SHARE_COMPANY_MSTRRepository.findOne(new Long(SVC_CODE.toString().trim()));
					if(fscm==null) {
						json.put("msg", "Stock is Not valid please send correct Stock id");
						logger.error("Stock is Not valid");
						return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
					}
					  try
						{
						  fUND_SHARE_COMPANY_MSTRRepository.delete(fscm);				
								    json.put("msg", "Deleted");				
									FUND_USER_LOG ful=new FUND_USER_LOG();
									ful.setSVC_UID(fuser.getSVC_UID());
									ful.setSVL_USERID(fuser.getSVU_NAME());
									ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
									ful.setSVL_TTYPE("DELETE");
									ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
									ful.setSVL_DATE(Calendar.getInstance());
									ful=fUND_USER_LOGRepository.save(ful);
									if(ful!=null) {
										logger.info("Both Record and Logs saved for Stock:"+fscm.getSVC_NAME());
										json.put("logs","logs are saved");
									}
									else {
										logger.info("Record is saved but logs can't saved due error in saving of logs");
										json.put("logs","Record is saved but logs can't saved due error in saving of logs");
									}
								return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
							
						}
						catch(Exception e)
						{
							json.put("msg", e.getMessage());
							logger.error("Stock not DELETE :"+e.getMessage());
							return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
							
						}
						
					
					  	
				}catch (Exception e) {
					json.put("msg",e.getMessage());
					logger.error("Stock is Not valid"+e.getMessage());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
		  }
		
	}

	
	@GetMapping("/stocks")
	public ResponseEntity<?> getAllFUND_SHARE_COMPANY_ALL(@RequestParam(required=false,value="action")String action,Pageable page){
		 if(action==null) {
			 Page<FUND_SHARE_COMPANY_MSTR> allFUND_SHARE_COMPANY_MSTR=fUND_SHARE_COMPANY_MSTRRepository.findAllFUND_SHARE_COMPANY_MSTR_ASC(page);
				Page<Map<String,Object>> jsonArray=allFUND_SHARE_COMPANY_MSTR.map(new Converter<FUND_SHARE_COMPANY_MSTR, Map<String,Object>>() {

					@Override
					public Map<String, Object> convert(FUND_SHARE_COMPANY_MSTR conut) {
						Map<String,Object> json=new HashMap<String,Object>();
						json.put("stockid", conut.getSVC_CODE());
						json.put("stockname",conut.getSVC_NAME());
						json.put("ticker",conut.getSVC_EXEC_CODE());
						json.put("cusip",conut.getSVC_CUSIP());
						json.put("reuterscode",conut.getSVC_RETUER_CODE());
						json.put("bloombergcode",conut.getSVC_BLOM_CODE());
						json.put("isincode",conut.getSVC_ISIN_CODE());
						json.put("sedol",conut.getSVC_SHARES_ISSUE());
						
						FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getSVC_CURR_CODE());
						if(fcm!=null) {
							json.put("currencyid",fcm.getSVC_CODE());
							json.put("currencyname", fcm.getSVC_NAME());
							json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
						}
						
						FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(conut.getSVC_CUST_COUNTRY());
						if(fcc!=null) {
							json.put("countryid",fcc.getSVC_CODE());
							json.put("countryname", fcc.getSVC_NAME());
							json.put("countryshortname", fcc.getSVC_SHORT_NAME());
						}
						
						FUND_EXCHANGE_MSTR fem=stockParameters.getFUND_EXCHANGE_MSTR(conut.getSVC_EXCHANGE());
						if(fem!=null) {
							json.put("exchangeid",fem.getSVE_CODE());
							json.put("exchangename", fem.getSVE_NAME());
							json.put("exchangeshortname", fem.getSVE_SHORT_NAME());
						}
						
						FUND_INDUSTRY_MSTR fim=stockParameters.getFUND_INDUSTRY_MSTR(conut.getSVC_INDUSTRY_TYPE());
						if(fim!=null) {
							json.put("sectorid",fim.getSVI_CODE());
							json.put("sectorname", fim.getSVI_NAME());
							json.put("sectorshortname", fim.getSVI_SHORT_NAME());
						}
						
						FUND_INSTRUMENT_MSTR fiim=stockParameters.getFUND_INSTRUMENT_MSTR(conut.getSVC_CUST_SECURITY());
						if(fiim!=null) {
							json.put("assetid",fiim.getSVI_CODE());
							json.put("assetname", fiim.getSVI_NAME());
							json.put("assetshortname", fiim.getSVI_SHORT_NAME());
						}				
						
						
						json.put("expiry_date",conut.getSVC_PUT_CALL_EDATE());
						json.put("lot_size",conut.getSVC_PUT_CALL_LOTSIZE());
						json.put("issue_number",conut.getSVC_BOND_ISSUE_NO());
						json.put("issue_date",conut.getSVC_BOND_DATE());
						json.put("maturity_date",conut.getSVC_BOND_MATURITY_DT());
						json.put("unit_price",conut.getSVC_PAIDUP_VALUE());
						json.put("frequency",conut.getSVC_BOND_DURATION());
						json.put("coupon_rate",conut.getSVC_BOND_RATE());
						json.put("Divisor_days",conut.getSVC_DIVISOR_DAYS());
						json.put("close_alternate_price",conut.getSVC_CLOSE_ALTERNATE_PRICE());
						json.put("price_calculated_flag",conut.getSVC_PRICE_CALCULATE());
						json.put("calculate_value",conut.getSVC_MARGIN_MAX());
						json.put("comments", conut.getWMS_COMMENTS());
						json.put("status", conut.getWMS_STATUS());
						List<Map<String,Object>> coupens=new ArrayList<Map<String,Object>>();
						conut.getfUND_BOND_INT_RATE().forEach(bond->{
							Map<String,Object> bondJson=new HashMap<>();
							bondJson.put("bondid", bond.getFBI_BOND());
							bondJson.put("expiredate", bond.getFBI_DATE());
							bondJson.put("rate", bond.getFBI_INT());
							
							coupens.add(bondJson);
							
						});
						json.put("coupens", coupens);
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getIV_ENTER_UID());
							if(user!=null) {
							json.put("enteredby", user.getSVU_NAME());
							json.put("enteredbyuserid", user.getSVU_USER_NAME());
							json.put("enteredbyuuid", user.getSVC_UID());
							json.put("entereddate", conut.getIV_ENTER_DATE());
							}
							else {
								json.put("enteredby", null);
								json.put("enteredbyuserid", null);
								json.put("enteredbyuuid", null);
								json.put("entereddate", null);
							}
						}else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
						
						if(conut.getIV_APPROVE_UID()!=null) {
							user=getUserName(conut.getIV_APPROVE_UID());
							if(user!=null) {
							json.put("approvedby", user.getSVU_NAME());
							json.put("approvedbyuserid", user.getSVU_USER_NAME());
							json.put("approvedbyuuid", user.getSVC_UID());
							json.put("approveddate", conut.getIV_APPROVE_DATE());
							}
							else {
								json.put("approvedby", null);
								json.put("approvedbyuserid", null);
								json.put("approvedbyuuid", null);
								json.put("approveddate", null);
							}
						}else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
						
						if(conut.getIV_LAST_UPDATE_UID()!=null) {
							user=getUserName(conut.getIV_LAST_UPDATE_UID());
							if(user!=null) {
							json.put("modifiedby", user.getSVU_NAME());
							json.put("modifiedbyuserid", user.getSVU_USER_NAME());
							json.put("modifiedbyuuid", user.getSVC_UID());
							json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
							}
							else {
								json.put("modifiedby", null);
								json.put("modifiedbyuserid", null);
								json.put("modifiedbyuuid", null);
								json.put("modifieddate", null);
							}
						}else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
						
						return json;
						
						
					}
					
				});
				return new ResponseEntity<Page<Map<String,Object>>>(jsonArray,HttpStatus.OK);
		 }
		 else if(action.toString().trim().equalsIgnoreCase("ALL")){
			 Page<FUND_SHARE_COMPANY_MSTR> allFUND_SHARE_COMPANY_MSTR=fUND_SHARE_COMPANY_MSTRRepository.findAllFUND_SHARE_COMPANY_MSTR_ASC(page);
				Page<Map<String,Object>> jsonArray=allFUND_SHARE_COMPANY_MSTR.map(new Converter<FUND_SHARE_COMPANY_MSTR, Map<String,Object>>() {

					@Override
					public Map<String, Object> convert(FUND_SHARE_COMPANY_MSTR conut) {
						Map<String,Object> json=new HashMap<String,Object>();
						json.put("stockid", conut.getSVC_CODE());
						json.put("stockname",conut.getSVC_NAME());
						json.put("ticker",conut.getSVC_EXEC_CODE());
						json.put("cusip",conut.getSVC_CUSIP());
						json.put("reuterscode",conut.getSVC_RETUER_CODE());
						json.put("bloombergcode",conut.getSVC_BLOM_CODE());
						json.put("isincode",conut.getSVC_ISIN_CODE());
						json.put("sedol",conut.getSVC_SHARES_ISSUE());
						
						FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getSVC_CURR_CODE());
						if(fcm!=null) {
							json.put("currencyid",fcm.getSVC_CODE());
							json.put("currencyname", fcm.getSVC_NAME());
							json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
						}
						
						FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(conut.getSVC_CUST_COUNTRY());
						if(fcc!=null) {
							json.put("countryid",fcc.getSVC_CODE());
							json.put("countryname", fcc.getSVC_NAME());
							json.put("countryshortname", fcc.getSVC_SHORT_NAME());
						}
						
						FUND_EXCHANGE_MSTR fem=stockParameters.getFUND_EXCHANGE_MSTR(conut.getSVC_EXCHANGE());
						if(fem!=null) {
							json.put("exchangeid",fem.getSVE_CODE());
							json.put("exchangename", fem.getSVE_NAME());
							json.put("exchangeshortname", fem.getSVE_SHORT_NAME());
						}
						
						FUND_INDUSTRY_MSTR fim=stockParameters.getFUND_INDUSTRY_MSTR(conut.getSVC_INDUSTRY_TYPE());
						if(fim!=null) {
							json.put("sectorid",fim.getSVI_CODE());
							json.put("sectorname", fim.getSVI_NAME());
							json.put("sectorshortname", fim.getSVI_SHORT_NAME());
						}
						
						FUND_INSTRUMENT_MSTR fiim=stockParameters.getFUND_INSTRUMENT_MSTR(conut.getSVC_CUST_SECURITY());
						if(fiim!=null) {
							json.put("assetid",fiim.getSVI_CODE());
							json.put("assetname", fiim.getSVI_NAME());
							json.put("assetshortname", fiim.getSVI_SHORT_NAME());
						}				
						
						
						json.put("expiry_date",conut.getSVC_PUT_CALL_EDATE());
						json.put("lot_size",conut.getSVC_PUT_CALL_LOTSIZE());
						json.put("issue_number",conut.getSVC_BOND_ISSUE_NO());
						json.put("issue_date",conut.getSVC_BOND_DATE());
						json.put("maturity_date",conut.getSVC_BOND_MATURITY_DT());
						json.put("unit_price",conut.getSVC_PAIDUP_VALUE());
						json.put("frequency",conut.getSVC_BOND_DURATION());
						json.put("coupon_rate",conut.getSVC_BOND_RATE());
						json.put("Divisor_days",conut.getSVC_DIVISOR_DAYS());
						json.put("close_alternate_price",conut.getSVC_CLOSE_ALTERNATE_PRICE());
						json.put("price_calculated_flag",conut.getSVC_PRICE_CALCULATE());
						json.put("calculate_value",conut.getSVC_MARGIN_MAX());
						json.put("comments", conut.getWMS_COMMENTS());
						json.put("status", conut.getWMS_STATUS());
						List<Map<String,Object>> coupens=new ArrayList<Map<String,Object>>();
						conut.getfUND_BOND_INT_RATE().forEach(bond->{
							Map<String,Object> bondJson=new HashMap<>();
							bondJson.put("bondid", bond.getFBI_BOND());
							bondJson.put("expiredate", bond.getFBI_DATE());
							bondJson.put("rate", bond.getFBI_INT());
							
							coupens.add(bondJson);
							
						});
						json.put("coupens", coupens);
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getIV_ENTER_UID());
							if(user!=null) {
							json.put("enteredby", user.getSVU_NAME());
							json.put("enteredbyuserid", user.getSVU_USER_NAME());
							json.put("enteredbyuuid", user.getSVC_UID());
							json.put("entereddate", conut.getIV_ENTER_DATE());
							}
							else {
								json.put("enteredby", null);
								json.put("enteredbyuserid", null);
								json.put("enteredbyuuid", null);
								json.put("entereddate", null);
							}
						}else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
						
						if(conut.getIV_APPROVE_UID()!=null) {
							user=getUserName(conut.getIV_APPROVE_UID());
							if(user!=null) {
							json.put("approvedby", user.getSVU_NAME());
							json.put("approvedbyuserid", user.getSVU_USER_NAME());
							json.put("approvedbyuuid", user.getSVC_UID());
							json.put("approveddate", conut.getIV_APPROVE_DATE());
							}
							else {
								json.put("approvedby", null);
								json.put("approvedbyuserid", null);
								json.put("approvedbyuuid", null);
								json.put("approveddate", null);
							}
						}else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
						
						if(conut.getIV_LAST_UPDATE_UID()!=null) {
							user=getUserName(conut.getIV_LAST_UPDATE_UID());
							if(user!=null) {
							json.put("modifiedby", user.getSVU_NAME());
							json.put("modifiedbyuserid", user.getSVU_USER_NAME());
							json.put("modifiedbyuuid", user.getSVC_UID());
							json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
							}
							else {
								json.put("modifiedby", null);
								json.put("modifiedbyuserid", null);
								json.put("modifiedbyuuid", null);
								json.put("modifieddate", null);
							}
						}else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
						
						return json;
						
						
					}
					
				});
				return new ResponseEntity<Page<Map<String,Object>>>(jsonArray,HttpStatus.OK);
		 }
		 else if(action.toString().trim().equalsIgnoreCase("APPROVED")){
			 Page<FUND_SHARE_COMPANY_MSTR> allFUND_SHARE_COMPANY_MSTR=fUND_SHARE_COMPANY_MSTRRepository.findAllAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC(page);
				Page<Map<String,Object>> jsonArray=allFUND_SHARE_COMPANY_MSTR.map(new Converter<FUND_SHARE_COMPANY_MSTR, Map<String,Object>>() {

					@Override
					public Map<String, Object> convert(FUND_SHARE_COMPANY_MSTR conut) {
						Map<String,Object> json=new HashMap<String,Object>();
						json.put("stockid", conut.getSVC_CODE());
						json.put("stockname",conut.getSVC_NAME());
						json.put("ticker",conut.getSVC_EXEC_CODE());
						json.put("cusip",conut.getSVC_CUSIP());
						json.put("reuterscode",conut.getSVC_RETUER_CODE());
						json.put("bloombergcode",conut.getSVC_BLOM_CODE());
						json.put("isincode",conut.getSVC_ISIN_CODE());
						json.put("sedol",conut.getSVC_SHARES_ISSUE());
						
						FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getSVC_CURR_CODE());
						if(fcm!=null) {
							json.put("currencyid",fcm.getSVC_CODE());
							json.put("currencyname", fcm.getSVC_NAME());
							json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
						}
						
						FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(conut.getSVC_CUST_COUNTRY());
						if(fcc!=null) {
							json.put("countryid",fcc.getSVC_CODE());
							json.put("countryname", fcc.getSVC_NAME());
							json.put("countryshortname", fcc.getSVC_SHORT_NAME());
						}
						
						FUND_EXCHANGE_MSTR fem=stockParameters.getFUND_EXCHANGE_MSTR(conut.getSVC_EXCHANGE());
						if(fem!=null) {
							json.put("exchangeid",fem.getSVE_CODE());
							json.put("exchangename", fem.getSVE_NAME());
							json.put("exchangeshortname", fem.getSVE_SHORT_NAME());
						}
						
						FUND_INDUSTRY_MSTR fim=stockParameters.getFUND_INDUSTRY_MSTR(conut.getSVC_INDUSTRY_TYPE());
						if(fim!=null) {
							json.put("sectorid",fim.getSVI_CODE());
							json.put("sectorname", fim.getSVI_NAME());
							json.put("sectorshortname", fim.getSVI_SHORT_NAME());
						}
						
						FUND_INSTRUMENT_MSTR fiim=stockParameters.getFUND_INSTRUMENT_MSTR(conut.getSVC_CUST_SECURITY());
						if(fiim!=null) {
							json.put("assetid",fiim.getSVI_CODE());
							json.put("assetname", fiim.getSVI_NAME());
							json.put("assetshortname", fiim.getSVI_SHORT_NAME());
						}				
						
						
						json.put("expiry_date",conut.getSVC_PUT_CALL_EDATE());
						json.put("lot_size",conut.getSVC_PUT_CALL_LOTSIZE());
						json.put("issue_number",conut.getSVC_BOND_ISSUE_NO());
						json.put("issue_date",conut.getSVC_BOND_DATE());
						json.put("maturity_date",conut.getSVC_BOND_MATURITY_DT());
						json.put("unit_price",conut.getSVC_PAIDUP_VALUE());
						json.put("frequency",conut.getSVC_BOND_DURATION());
						json.put("coupon_rate",conut.getSVC_BOND_RATE());
						json.put("Divisor_days",conut.getSVC_DIVISOR_DAYS());
						json.put("close_alternate_price",conut.getSVC_CLOSE_ALTERNATE_PRICE());
						json.put("price_calculated_flag",conut.getSVC_PRICE_CALCULATE());
						json.put("calculate_value",conut.getSVC_MARGIN_MAX());
						json.put("comments", conut.getWMS_COMMENTS());
						json.put("status", conut.getWMS_STATUS());
						List<Map<String,Object>> coupens=new ArrayList<Map<String,Object>>();
						conut.getfUND_BOND_INT_RATE().forEach(bond->{
							Map<String,Object> bondJson=new HashMap<>();
							bondJson.put("bondid", bond.getFBI_BOND());
							bondJson.put("expiredate", bond.getFBI_DATE());
							bondJson.put("rate", bond.getFBI_INT());
							
							coupens.add(bondJson);
							
						});
						json.put("coupens", coupens);
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getIV_ENTER_UID());
							if(user!=null) {
							json.put("enteredby", user.getSVU_NAME());
							json.put("enteredbyuserid", user.getSVU_USER_NAME());
							json.put("enteredbyuuid", user.getSVC_UID());
							json.put("entereddate", conut.getIV_ENTER_DATE());
							}
							else {
								json.put("enteredby", null);
								json.put("enteredbyuserid", null);
								json.put("enteredbyuuid", null);
								json.put("entereddate", null);
							}
						}else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
						
						if(conut.getIV_APPROVE_UID()!=null) {
							user=getUserName(conut.getIV_APPROVE_UID());
							if(user!=null) {
							json.put("approvedby", user.getSVU_NAME());
							json.put("approvedbyuserid", user.getSVU_USER_NAME());
							json.put("approvedbyuuid", user.getSVC_UID());
							json.put("approveddate", conut.getIV_APPROVE_DATE());
							}
							else {
								json.put("approvedby", null);
								json.put("approvedbyuserid", null);
								json.put("approvedbyuuid", null);
								json.put("approveddate", null);
							}
						}else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
						
						if(conut.getIV_LAST_UPDATE_UID()!=null) {
							user=getUserName(conut.getIV_LAST_UPDATE_UID());
							if(user!=null) {
							json.put("modifiedby", user.getSVU_NAME());
							json.put("modifiedbyuserid", user.getSVU_USER_NAME());
							json.put("modifiedbyuuid", user.getSVC_UID());
							json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
							}
							else {
								json.put("modifiedby", null);
								json.put("modifiedbyuserid", null);
								json.put("modifiedbyuuid", null);
								json.put("modifieddate", null);
							}
						}else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
						
						return json;
						
						
					}
					
				});
				return new ResponseEntity<Page<Map<String,Object>>>(jsonArray,HttpStatus.OK);
		 }
		 else if(action.toString().trim().equalsIgnoreCase("UNAPPROVED")){
			 Page<FUND_SHARE_COMPANY_MSTR> allFUND_SHARE_COMPANY_MSTR=fUND_SHARE_COMPANY_MSTRRepository.findAllUNAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC(page);
				Page<Map<String,Object>> jsonArray=allFUND_SHARE_COMPANY_MSTR.map(new Converter<FUND_SHARE_COMPANY_MSTR, Map<String,Object>>() {

					@Override
					public Map<String, Object> convert(FUND_SHARE_COMPANY_MSTR conut) {
						Map<String,Object> json=new HashMap<String,Object>();
						json.put("stockid", conut.getSVC_CODE());
						json.put("stockname",conut.getSVC_NAME());
						json.put("ticker",conut.getSVC_EXEC_CODE());
						json.put("cusip",conut.getSVC_CUSIP());
						json.put("reuterscode",conut.getSVC_RETUER_CODE());
						json.put("bloombergcode",conut.getSVC_BLOM_CODE());
						json.put("isincode",conut.getSVC_ISIN_CODE());
						json.put("sedol",conut.getSVC_SHARES_ISSUE());
						
						FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getSVC_CURR_CODE());
						if(fcm!=null) {
							json.put("currencyid",fcm.getSVC_CODE());
							json.put("currencyname", fcm.getSVC_NAME());
							json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
						}
						
						FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(conut.getSVC_CUST_COUNTRY());
						if(fcc!=null) {
							json.put("countryid",fcc.getSVC_CODE());
							json.put("countryname", fcc.getSVC_NAME());
							json.put("countryshortname", fcc.getSVC_SHORT_NAME());
						}
						
						FUND_EXCHANGE_MSTR fem=stockParameters.getFUND_EXCHANGE_MSTR(conut.getSVC_EXCHANGE());
						if(fem!=null) {
							json.put("exchangeid",fem.getSVE_CODE());
							json.put("exchangename", fem.getSVE_NAME());
							json.put("exchangeshortname", fem.getSVE_SHORT_NAME());
						}
						
						FUND_INDUSTRY_MSTR fim=stockParameters.getFUND_INDUSTRY_MSTR(conut.getSVC_INDUSTRY_TYPE());
						if(fim!=null) {
							json.put("sectorid",fim.getSVI_CODE());
							json.put("sectorname", fim.getSVI_NAME());
							json.put("sectorshortname", fim.getSVI_SHORT_NAME());
						}
						
						FUND_INSTRUMENT_MSTR fiim=stockParameters.getFUND_INSTRUMENT_MSTR(conut.getSVC_CUST_SECURITY());
						if(fiim!=null) {
							json.put("assetid",fiim.getSVI_CODE());
							json.put("assetname", fiim.getSVI_NAME());
							json.put("assetshortname", fiim.getSVI_SHORT_NAME());
						}				
						
						
						json.put("expiry_date",conut.getSVC_PUT_CALL_EDATE());
						json.put("lot_size",conut.getSVC_PUT_CALL_LOTSIZE());
						json.put("issue_number",conut.getSVC_BOND_ISSUE_NO());
						json.put("issue_date",conut.getSVC_BOND_DATE());
						json.put("maturity_date",conut.getSVC_BOND_MATURITY_DT());
						json.put("unit_price",conut.getSVC_PAIDUP_VALUE());
						json.put("frequency",conut.getSVC_BOND_DURATION());
						json.put("coupon_rate",conut.getSVC_BOND_RATE());
						json.put("Divisor_days",conut.getSVC_DIVISOR_DAYS());
						json.put("close_alternate_price",conut.getSVC_CLOSE_ALTERNATE_PRICE());
						json.put("price_calculated_flag",conut.getSVC_PRICE_CALCULATE());
						json.put("calculate_value",conut.getSVC_MARGIN_MAX());
						json.put("comments", conut.getWMS_COMMENTS());
						json.put("status", conut.getWMS_STATUS());
						List<Map<String,Object>> coupens=new ArrayList<Map<String,Object>>();
						conut.getfUND_BOND_INT_RATE().forEach(bond->{
							Map<String,Object> bondJson=new HashMap<>();
							bondJson.put("bondid", bond.getFBI_BOND());
							bondJson.put("expiredate", bond.getFBI_DATE());
							bondJson.put("rate", bond.getFBI_INT());
							
							coupens.add(bondJson);
							
						});
						json.put("coupens", coupens);
						FUND_USERS user=null;
						if(conut.getIV_ENTER_UID()!=null) {
							user=getUserName(conut.getIV_ENTER_UID());
							if(user!=null) {
							json.put("enteredby", user.getSVU_NAME());
							json.put("enteredbyuserid", user.getSVU_USER_NAME());
							json.put("enteredbyuuid", user.getSVC_UID());
							json.put("entereddate", conut.getIV_ENTER_DATE());
							}
							else {
								json.put("enteredby", null);
								json.put("enteredbyuserid", null);
								json.put("enteredbyuuid", null);
								json.put("entereddate", null);
							}
						}else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
						
						if(conut.getIV_APPROVE_UID()!=null) {
							user=getUserName(conut.getIV_APPROVE_UID());
							if(user!=null) {
							json.put("approvedby", user.getSVU_NAME());
							json.put("approvedbyuserid", user.getSVU_USER_NAME());
							json.put("approvedbyuuid", user.getSVC_UID());
							json.put("approveddate", conut.getIV_APPROVE_DATE());
							}
							else {
								json.put("approvedby", null);
								json.put("approvedbyuserid", null);
								json.put("approvedbyuuid", null);
								json.put("approveddate", null);
							}
						}else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
						
						if(conut.getIV_LAST_UPDATE_UID()!=null) {
							user=getUserName(conut.getIV_LAST_UPDATE_UID());
							if(user!=null) {
							json.put("modifiedby", user.getSVU_NAME());
							json.put("modifiedbyuserid", user.getSVU_USER_NAME());
							json.put("modifiedbyuuid", user.getSVC_UID());
							json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
							}
							else {
								json.put("modifiedby", null);
								json.put("modifiedbyuserid", null);
								json.put("modifiedbyuuid", null);
								json.put("modifieddate", null);
							}
						}else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
						
						return json;
						
						
					}
					
				});
				return new ResponseEntity<Page<Map<String,Object>>>(jsonArray,HttpStatus.OK);
		 }
		 else {
			 Map<String,Object> jsonArray=new HashMap<>();
			 jsonArray.put("msg", "Not found");
			 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
		 }
	}
 
	
	@GetMapping("/stocksearch")
	public ResponseEntity<?> getAllFUND_SHARE_COMPANY_ALL_Search(@RequestParam(required=false,value="action")String action,@RequestParam(required=false,value="paramSearch")String paramSearch,Pageable page){
		Page<FUND_SHARE_COMPANY_MSTR> allFUND_SHARE_COMPANY_MSTR=null;
		
		List<String> curlist=new ArrayList<>();
		List<Integer> custlist=new ArrayList<>();
		List<String> exclist=new ArrayList<>();
				
		
		if(paramSearch!=null) {
			if(paramSearch.isEmpty()==false) {
				curlist=stockParameters.getAllCurrencyId(paramSearch).stream().map(i->i.toString()).collect(Collectors.toList());;
				custlist=stockParameters.getAllCountriesId(paramSearch).stream().map(i->new Integer(i+"")).collect(Collectors.toList());;
				exclist=stockParameters.getAllExchangeId(paramSearch).stream().map(i->i.toString()).collect(Collectors.toList());;
			}
		}
		
		
		if(curlist.isEmpty()) {
			curlist.add("-1");
		}
		if(custlist.isEmpty()) {
			custlist.add(new Integer(-1+""));
		}
		if(exclist.isEmpty()) {
			exclist.add("-1");
		}
		
//		System.out.println(curlist+" "+custlist+""+exclist);
		if(action==null) {
			 	if(paramSearch==null) {
			 		allFUND_SHARE_COMPANY_MSTR=fUND_SHARE_COMPANY_MSTRRepository.findAllAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC(page);
			 	}else {
			 		allFUND_SHARE_COMPANY_MSTR=fUND_SHARE_COMPANY_MSTRRepository.findAllFUND_SHARE_COMPANY_MSTR_ASC_SEARCH(paramSearch,
			 				curlist,
			 				custlist,
			 				exclist,
			 				page);
			 	}
		}
		else if(action.toString().trim().equalsIgnoreCase("ALL")) {
			if(paramSearch==null) {
		 		allFUND_SHARE_COMPANY_MSTR=fUND_SHARE_COMPANY_MSTRRepository.findAllAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC(page);
		 	}else {
		 		allFUND_SHARE_COMPANY_MSTR=fUND_SHARE_COMPANY_MSTRRepository.findAllFUND_SHARE_COMPANY_MSTR_ASC_SEARCH(paramSearch,
		 				curlist,
		 				custlist,
		 				exclist,
		 				page);
		 	}
		}else if(action.toString().trim().equalsIgnoreCase("APPROVED")) {
			if(paramSearch==null) {
		 		allFUND_SHARE_COMPANY_MSTR=fUND_SHARE_COMPANY_MSTRRepository.findAllAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC(page);
		 	}else {
		 		allFUND_SHARE_COMPANY_MSTR=fUND_SHARE_COMPANY_MSTRRepository.findAllAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC_SEARCH(paramSearch,
		 				curlist,
		 				custlist,
		 				exclist,
		 				page);
		 	}
		}else if(action.toString().trim().equalsIgnoreCase("UNAPPROVED")) {
			if(paramSearch==null) {
		 		allFUND_SHARE_COMPANY_MSTR=fUND_SHARE_COMPANY_MSTRRepository.findAllUNAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC(page);
		 	}else {
		 		allFUND_SHARE_COMPANY_MSTR=fUND_SHARE_COMPANY_MSTRRepository.findAllUNAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC_SEARCH(paramSearch,
		 				curlist,
		 				custlist,
		 				exclist,
		 				page);
		 	}
		}else {
			Map<String,Object> jsonArray=new HashMap<>();
			 jsonArray.put("msg", "Not found");
			 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
		}
		/*
		 * Start of Json
		 */
	 	if(allFUND_SHARE_COMPANY_MSTR!=null)
	 	{
	 		
	 		Page<Map<String,Object>> jsonArray=allFUND_SHARE_COMPANY_MSTR.map(new Converter<FUND_SHARE_COMPANY_MSTR, Map<String,Object>>() {

				@Override
				public Map<String, Object> convert(FUND_SHARE_COMPANY_MSTR conut) {
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("stockid", conut.getSVC_CODE());
					json.put("stockname",conut.getSVC_NAME());
					json.put("ticker",conut.getSVC_EXEC_CODE());
					json.put("cusip",conut.getSVC_CUSIP());
					json.put("reuterscode",conut.getSVC_RETUER_CODE());
					json.put("bloombergcode",conut.getSVC_BLOM_CODE());
					json.put("isincode",conut.getSVC_ISIN_CODE());
					json.put("sedol",conut.getSVC_SHARES_ISSUE());
					
					FUND_CURRENCY_MSTR fcm=stockParameters.getFUND_CURRENCY_MSTR(conut.getSVC_CURR_CODE());
					if(fcm!=null) {
						json.put("currencyid",fcm.getSVC_CODE());
						json.put("currencyname", fcm.getSVC_NAME());
						json.put("currencyshortname", fcm.getSVC_SHORT_NAME());
					}
					
					FUND_COUNTRIES fcc=stockParameters.getFUND_COUNTRIES(conut.getSVC_CUST_COUNTRY());
					if(fcc!=null) {
						json.put("countryid",fcc.getSVC_CODE());
						json.put("countryname", fcc.getSVC_NAME());
						json.put("countryshortname", fcc.getSVC_SHORT_NAME());
					}
					
					FUND_EXCHANGE_MSTR fem=stockParameters.getFUND_EXCHANGE_MSTR(conut.getSVC_EXCHANGE());
					if(fem!=null) {
						json.put("exchangeid",fem.getSVE_CODE());
						json.put("exchangename", fem.getSVE_NAME());
						json.put("exchangeshortname", fem.getSVE_SHORT_NAME());
					}
					
					FUND_INDUSTRY_MSTR fim=stockParameters.getFUND_INDUSTRY_MSTR(conut.getSVC_INDUSTRY_TYPE());
					if(fim!=null) {
						json.put("sectorid",fim.getSVI_CODE());
						json.put("sectorname", fim.getSVI_NAME());
						json.put("sectorshortname", fim.getSVI_SHORT_NAME());
					}
					
					FUND_INSTRUMENT_MSTR fiim=stockParameters.getFUND_INSTRUMENT_MSTR(conut.getSVC_CUST_SECURITY());
					if(fiim!=null) {
						json.put("assetid",fiim.getSVI_CODE());
						json.put("assetname", fiim.getSVI_NAME());
						json.put("assetshortname", fiim.getSVI_SHORT_NAME());
					}				
					
					
					json.put("expiry_date",conut.getSVC_PUT_CALL_EDATE());
					json.put("lot_size",conut.getSVC_PUT_CALL_LOTSIZE());
					json.put("issue_number",conut.getSVC_BOND_ISSUE_NO());
					json.put("issue_date",conut.getSVC_BOND_DATE());
					json.put("maturity_date",conut.getSVC_BOND_MATURITY_DT());
					json.put("unit_price",conut.getSVC_PAIDUP_VALUE());
					json.put("frequency",conut.getSVC_BOND_DURATION());
					json.put("coupon_rate",conut.getSVC_BOND_RATE());
					json.put("Divisor_days",conut.getSVC_DIVISOR_DAYS());
					json.put("close_alternate_price",conut.getSVC_CLOSE_ALTERNATE_PRICE());
					json.put("price_calculated_flag",conut.getSVC_PRICE_CALCULATE());
					json.put("calculate_value",conut.getSVC_MARGIN_MAX());
					json.put("comments", conut.getWMS_COMMENTS());
					json.put("status", conut.getWMS_STATUS());
					List<Map<String,Object>> coupens=new ArrayList<Map<String,Object>>();
					conut.getfUND_BOND_INT_RATE().forEach(bond->{
						Map<String,Object> bondJson=new HashMap<>();
						bondJson.put("bondid", bond.getFBI_BOND());
						bondJson.put("expiredate", bond.getFBI_DATE());
						bondJson.put("rate", bond.getFBI_INT());
						
						coupens.add(bondJson);
						
					});
					json.put("coupens", coupens);
					FUND_USERS user=null;
					if(conut.getIV_ENTER_UID()!=null) {
						user=getUserName(conut.getIV_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", conut.getIV_ENTER_DATE());
						}
						else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
					}else {
						json.put("enteredby", null);
						json.put("enteredbyuserid", null);
						json.put("enteredbyuuid", null);
						json.put("entereddate", null);
					}
					
					if(conut.getIV_APPROVE_UID()!=null) {
						user=getUserName(conut.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", conut.getIV_APPROVE_DATE());
						}
						else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
					}else {
						json.put("approvedby", null);
						json.put("approvedbyuserid", null);
						json.put("approvedbyuuid", null);
						json.put("approveddate", null);
					}
					
					if(conut.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(conut.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
						}
						else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
					}else {
						json.put("modifiedby", null);
						json.put("modifiedbyuserid", null);
						json.put("modifiedbyuuid", null);
						json.put("modifieddate", null);
					}
					
					return json;
				}
				
			});
			return new ResponseEntity<Page<Map<String,Object>>>(jsonArray,HttpStatus.OK);	
	 	}else {
	 		 Map<String,Object> jsonArray=new HashMap<>();
	 		 jsonArray.put("msg", "Not found");
	 		 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
	 	}
	 	/*
	 	 * End of Json
	 	 */
		
}
	
	@GetMapping("/stockslist")
	public ResponseEntity<?> getAllFUND_SHARE_COMPANY_ALL_LIST(@RequestParam(value="action",required=false)String action){
		List<Map<String,Object>> jsonArray=new ArrayList<Map<String,Object>>();
		
		 if(action==null) {
			 fUND_SHARE_COMPANY_MSTRRepository.findAllFUND_SHARE_COMPANY_MSTR_ASC().forEach(conut->{
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("stockid", conut.getSVC_CODE());
					json.put("stockname",conut.getSVC_NAME());
					json.put("ticker",conut.getSVC_EXEC_CODE());
					json.put("cusip",conut.getSVC_CUSIP());
					json.put("reuterscode",conut.getSVC_RETUER_CODE());
					json.put("bloombergcode",conut.getSVC_BLOM_CODE());
					json.put("isincode",conut.getSVC_ISIN_CODE());
					json.put("sedol",conut.getSVC_SHARES_ISSUE());
					json.put("countryid",conut.getSVC_CUST_COUNTRY());
					json.put("currencyid",conut.getSVC_CURR_CODE());
					json.put("exchangeid",conut.getSVC_EXCHANGE());
					json.put("sectorid",conut.getSVC_INDUSTRY_TYPE());
					json.put("assetid",conut.getSVC_CUST_SECURITY());
					json.put("expiry_date",conut.getSVC_PUT_CALL_EDATE());
					json.put("lot_size",conut.getSVC_PUT_CALL_LOTSIZE());
					json.put("issue_number",conut.getSVC_BOND_ISSUE_NO());
					json.put("issue_date",conut.getSVC_BOND_DATE());
					json.put("maturity_date",conut.getSVC_BOND_MATURITY_DT());
					json.put("unit_price",conut.getSVC_BOND_DURATION());
					json.put("coupon_rate",conut.getSVC_BOND_RATE());
					json.put("Divisor_days",conut.getSVC_DIVISOR_DAYS());
					json.put("close_alternate_price",conut.getSVC_CLOSE_ALTERNATE_PRICE());
					json.put("price_calculated_flag",conut.getSVC_PRICE_CALCULATE());
					json.put("calculate_value",conut.getSVC_MARGIN_MAX());
					json.put("comments", conut.getWMS_COMMENTS());
					json.put("status", conut.getWMS_STATUS());
					List<Map<String,Object>> coupens=new ArrayList<Map<String,Object>>();
					conut.getfUND_BOND_INT_RATE().forEach(bond->{
						Map<String,Object> bondJson=new HashMap<>();
						bondJson.put("bondid", bond.getFBI_BOND());
						bondJson.put("expiredate", bond.getFBI_DATE());
						bondJson.put("rate", bond.getFBI_INT());
						
						coupens.add(bondJson);
						
					});
					json.put("coupens", coupens);
					FUND_USERS user=null;
					if(conut.getIV_ENTER_UID()!=null) {
						user=getUserName(conut.getIV_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", conut.getIV_ENTER_DATE());
						}
						else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
					}else {
						json.put("enteredby", null);
						json.put("enteredbyuserid", null);
						json.put("enteredbyuuid", null);
						json.put("entereddate", null);
					}
					
					if(conut.getIV_APPROVE_UID()!=null) {
						user=getUserName(conut.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", conut.getIV_APPROVE_DATE());
						}
						else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
					}else {
						json.put("approvedby", null);
						json.put("approvedbyuserid", null);
						json.put("approvedbyuuid", null);
						json.put("approveddate", null);
					}
					
					if(conut.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(conut.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
						}
						else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
					}else {
						json.put("modifiedby", null);
						json.put("modifiedbyuserid", null);
						json.put("modifiedbyuuid", null);
						json.put("modifieddate", null);
					}
					jsonArray.add(json);
					
				}); 
		 }else if(action.toString().trim().equalsIgnoreCase("ALL")) {
			 fUND_SHARE_COMPANY_MSTRRepository.findAllFUND_SHARE_COMPANY_MSTR_ASC().forEach(conut->{
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("stockid", conut.getSVC_CODE());
					json.put("stockname",conut.getSVC_NAME());
					json.put("ticker",conut.getSVC_EXEC_CODE());
					json.put("cusip",conut.getSVC_CUSIP());
					json.put("reuterscode",conut.getSVC_RETUER_CODE());
					json.put("bloombergcode",conut.getSVC_BLOM_CODE());
					json.put("isincode",conut.getSVC_ISIN_CODE());
					json.put("sedol",conut.getSVC_SHARES_ISSUE());
					json.put("countryid",conut.getSVC_CUST_COUNTRY());
					json.put("currencyid",conut.getSVC_CURR_CODE());
					json.put("exchangeid",conut.getSVC_EXCHANGE());
					json.put("sectorid",conut.getSVC_INDUSTRY_TYPE());
					json.put("assetid",conut.getSVC_CUST_SECURITY());
					json.put("expiry_date",conut.getSVC_PUT_CALL_EDATE());
					json.put("lot_size",conut.getSVC_PUT_CALL_LOTSIZE());
					json.put("issue_number",conut.getSVC_BOND_ISSUE_NO());
					json.put("issue_date",conut.getSVC_BOND_DATE());
					json.put("maturity_date",conut.getSVC_BOND_MATURITY_DT());
					json.put("unit_price",conut.getSVC_BOND_DURATION());
					json.put("coupon_rate",conut.getSVC_BOND_RATE());
					json.put("Divisor_days",conut.getSVC_DIVISOR_DAYS());
					json.put("close_alternate_price",conut.getSVC_CLOSE_ALTERNATE_PRICE());
					json.put("price_calculated_flag",conut.getSVC_PRICE_CALCULATE());
					json.put("calculate_value",conut.getSVC_MARGIN_MAX());
					json.put("comments", conut.getWMS_COMMENTS());
					json.put("status", conut.getWMS_STATUS());
					List<Map<String,Object>> coupens=new ArrayList<Map<String,Object>>();
					conut.getfUND_BOND_INT_RATE().forEach(bond->{
						Map<String,Object> bondJson=new HashMap<>();
						bondJson.put("bondid", bond.getFBI_BOND());
						bondJson.put("expiredate", bond.getFBI_DATE());
						bondJson.put("rate", bond.getFBI_INT());
						
						coupens.add(bondJson);
						
					});
					json.put("coupens", coupens);
					FUND_USERS user=null;
					if(conut.getIV_ENTER_UID()!=null) {
						user=getUserName(conut.getIV_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", conut.getIV_ENTER_DATE());
						}
						else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
					}else {
						json.put("enteredby", null);
						json.put("enteredbyuserid", null);
						json.put("enteredbyuuid", null);
						json.put("entereddate", null);
					}
					
					if(conut.getIV_APPROVE_UID()!=null) {
						user=getUserName(conut.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", conut.getIV_APPROVE_DATE());
						}
						else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
					}else {
						json.put("approvedby", null);
						json.put("approvedbyuserid", null);
						json.put("approvedbyuuid", null);
						json.put("approveddate", null);
					}
					
					if(conut.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(conut.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
						}
						else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
					}else {
						json.put("modifiedby", null);
						json.put("modifiedbyuserid", null);
						json.put("modifiedbyuuid", null);
						json.put("modifieddate", null);
					}
					jsonArray.add(json);
					
				});fUND_SHARE_COMPANY_MSTRRepository.findAllFUND_SHARE_COMPANY_MSTR_ASC().forEach(conut->{
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("stockid", conut.getSVC_CODE());
					json.put("stockname",conut.getSVC_NAME());
					json.put("ticker",conut.getSVC_EXEC_CODE());
					json.put("cusip",conut.getSVC_CUSIP());
					json.put("reuterscode",conut.getSVC_RETUER_CODE());
					json.put("bloombergcode",conut.getSVC_BLOM_CODE());
					json.put("isincode",conut.getSVC_ISIN_CODE());
					json.put("sedol",conut.getSVC_SHARES_ISSUE());
					json.put("countryid",conut.getSVC_CUST_COUNTRY());
					json.put("currencyid",conut.getSVC_CURR_CODE());
					json.put("exchangeid",conut.getSVC_EXCHANGE());
					json.put("sectorid",conut.getSVC_INDUSTRY_TYPE());
					json.put("assetid",conut.getSVC_CUST_SECURITY());
					json.put("expiry_date",conut.getSVC_PUT_CALL_EDATE());
					json.put("lot_size",conut.getSVC_PUT_CALL_LOTSIZE());
					json.put("issue_number",conut.getSVC_BOND_ISSUE_NO());
					json.put("issue_date",conut.getSVC_BOND_DATE());
					json.put("maturity_date",conut.getSVC_BOND_MATURITY_DT());
					json.put("unit_price",conut.getSVC_BOND_DURATION());
					json.put("coupon_rate",conut.getSVC_BOND_RATE());
					json.put("Divisor_days",conut.getSVC_DIVISOR_DAYS());
					json.put("close_alternate_price",conut.getSVC_CLOSE_ALTERNATE_PRICE());
					json.put("price_calculated_flag",conut.getSVC_PRICE_CALCULATE());
					json.put("calculate_value",conut.getSVC_MARGIN_MAX());
					json.put("comments", conut.getWMS_COMMENTS());
					json.put("status", conut.getWMS_STATUS());
					List<Map<String,Object>> coupens=new ArrayList<Map<String,Object>>();
					conut.getfUND_BOND_INT_RATE().forEach(bond->{
						Map<String,Object> bondJson=new HashMap<>();
						bondJson.put("bondid", bond.getFBI_BOND());
						bondJson.put("expiredate", bond.getFBI_DATE());
						bondJson.put("rate", bond.getFBI_INT());
						
						coupens.add(bondJson);
						
					});
					json.put("coupens", coupens);
					FUND_USERS user=null;
					if(conut.getIV_ENTER_UID()!=null) {
						user=getUserName(conut.getIV_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", conut.getIV_ENTER_DATE());
						}
						else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
					}else {
						json.put("enteredby", null);
						json.put("enteredbyuserid", null);
						json.put("enteredbyuuid", null);
						json.put("entereddate", null);
					}
					
					if(conut.getIV_APPROVE_UID()!=null) {
						user=getUserName(conut.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", conut.getIV_APPROVE_DATE());
						}
						else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
					}else {
						json.put("approvedby", null);
						json.put("approvedbyuserid", null);
						json.put("approvedbyuuid", null);
						json.put("approveddate", null);
					}
					
					if(conut.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(conut.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
						}
						else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
					}else {
						json.put("modifiedby", null);
						json.put("modifiedbyuserid", null);
						json.put("modifiedbyuuid", null);
						json.put("modifieddate", null);
					}
					jsonArray.add(json);
					
				}); 
		 }
		 else if(action.toString().trim().equalsIgnoreCase("APPROVED")) {
			 fUND_SHARE_COMPANY_MSTRRepository.findAllAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC().forEach(conut->{
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("stockid", conut.getSVC_CODE());
					json.put("stockname",conut.getSVC_NAME());
					json.put("ticker",conut.getSVC_EXEC_CODE());
					json.put("cusip",conut.getSVC_CUSIP());
					json.put("reuterscode",conut.getSVC_RETUER_CODE());
					json.put("bloombergcode",conut.getSVC_BLOM_CODE());
					json.put("isincode",conut.getSVC_ISIN_CODE());
					json.put("sedol",conut.getSVC_SHARES_ISSUE());
					json.put("countryid",conut.getSVC_CUST_COUNTRY());
					json.put("currencyid",conut.getSVC_CURR_CODE());
					json.put("exchangeid",conut.getSVC_EXCHANGE());
					json.put("sectorid",conut.getSVC_INDUSTRY_TYPE());
					json.put("assetid",conut.getSVC_CUST_SECURITY());
					json.put("expiry_date",conut.getSVC_PUT_CALL_EDATE());
					json.put("lot_size",conut.getSVC_PUT_CALL_LOTSIZE());
					json.put("issue_number",conut.getSVC_BOND_ISSUE_NO());
					json.put("issue_date",conut.getSVC_BOND_DATE());
					json.put("maturity_date",conut.getSVC_BOND_MATURITY_DT());
					json.put("unit_price",conut.getSVC_BOND_DURATION());
					json.put("coupon_rate",conut.getSVC_BOND_RATE());
					json.put("Divisor_days",conut.getSVC_DIVISOR_DAYS());
					json.put("close_alternate_price",conut.getSVC_CLOSE_ALTERNATE_PRICE());
					json.put("price_calculated_flag",conut.getSVC_PRICE_CALCULATE());
					json.put("calculate_value",conut.getSVC_MARGIN_MAX());
					json.put("comments", conut.getWMS_COMMENTS());
					json.put("status", conut.getWMS_STATUS());
					List<Map<String,Object>> coupens=new ArrayList<Map<String,Object>>();
					conut.getfUND_BOND_INT_RATE().forEach(bond->{
						Map<String,Object> bondJson=new HashMap<>();
						bondJson.put("bondid", bond.getFBI_BOND());
						bondJson.put("expiredate", bond.getFBI_DATE());
						bondJson.put("rate", bond.getFBI_INT());
						
						coupens.add(bondJson);
						
					});
					json.put("coupens", coupens);
					FUND_USERS user=null;
					if(conut.getIV_ENTER_UID()!=null) {
						user=getUserName(conut.getIV_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", conut.getIV_ENTER_DATE());
						}
						else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
					}else {
						json.put("enteredby", null);
						json.put("enteredbyuserid", null);
						json.put("enteredbyuuid", null);
						json.put("entereddate", null);
					}
					
					if(conut.getIV_APPROVE_UID()!=null) {
						user=getUserName(conut.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", conut.getIV_APPROVE_DATE());
						}
						else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
					}else {
						json.put("approvedby", null);
						json.put("approvedbyuserid", null);
						json.put("approvedbyuuid", null);
						json.put("approveddate", null);
					}
					
					if(conut.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(conut.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
						}
						else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
					}else {
						json.put("modifiedby", null);
						json.put("modifiedbyuserid", null);
						json.put("modifiedbyuuid", null);
						json.put("modifieddate", null);
					}
					jsonArray.add(json);
					
				});
		 }
		 else if(action.toString().trim().equalsIgnoreCase("UNAPPROVED")) {
			 fUND_SHARE_COMPANY_MSTRRepository.findAllUNAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC().forEach(conut->{
					Map<String,Object> json=new HashMap<String,Object>();
					json.put("stockid", conut.getSVC_CODE());
					json.put("stockname",conut.getSVC_NAME());
					json.put("ticker",conut.getSVC_EXEC_CODE());
					json.put("cusip",conut.getSVC_CUSIP());
					json.put("reuterscode",conut.getSVC_RETUER_CODE());
					json.put("bloombergcode",conut.getSVC_BLOM_CODE());
					json.put("isincode",conut.getSVC_ISIN_CODE());
					json.put("sedol",conut.getSVC_SHARES_ISSUE());
					json.put("countryid",conut.getSVC_CUST_COUNTRY());
					json.put("currencyid",conut.getSVC_CURR_CODE());
					json.put("exchangeid",conut.getSVC_EXCHANGE());
					json.put("sectorid",conut.getSVC_INDUSTRY_TYPE());
					json.put("assetid",conut.getSVC_CUST_SECURITY());
					json.put("expiry_date",conut.getSVC_PUT_CALL_EDATE());
					json.put("lot_size",conut.getSVC_PUT_CALL_LOTSIZE());
					json.put("issue_number",conut.getSVC_BOND_ISSUE_NO());
					json.put("issue_date",conut.getSVC_BOND_DATE());
					json.put("maturity_date",conut.getSVC_BOND_MATURITY_DT());
					json.put("unit_price",conut.getSVC_BOND_DURATION());
					json.put("coupon_rate",conut.getSVC_BOND_RATE());
					json.put("Divisor_days",conut.getSVC_DIVISOR_DAYS());
					json.put("close_alternate_price",conut.getSVC_CLOSE_ALTERNATE_PRICE());
					json.put("price_calculated_flag",conut.getSVC_PRICE_CALCULATE());
					json.put("calculate_value",conut.getSVC_MARGIN_MAX());
					json.put("comments", conut.getWMS_COMMENTS());
					json.put("status", conut.getWMS_STATUS());
					List<Map<String,Object>> coupens=new ArrayList<Map<String,Object>>();
					conut.getfUND_BOND_INT_RATE().forEach(bond->{
						Map<String,Object> bondJson=new HashMap<>();
						bondJson.put("bondid", bond.getFBI_BOND());
						bondJson.put("expiredate", bond.getFBI_DATE());
						bondJson.put("rate", bond.getFBI_INT());
						
						coupens.add(bondJson);
						
					});
					json.put("coupens", coupens);
					FUND_USERS user=null;
					if(conut.getIV_ENTER_UID()!=null) {
						user=getUserName(conut.getIV_ENTER_UID());
						if(user!=null) {
						json.put("enteredby", user.getSVU_NAME());
						json.put("enteredbyuserid", user.getSVU_USER_NAME());
						json.put("enteredbyuuid", user.getSVC_UID());
						json.put("entereddate", conut.getIV_ENTER_DATE());
						}
						else {
							json.put("enteredby", null);
							json.put("enteredbyuserid", null);
							json.put("enteredbyuuid", null);
							json.put("entereddate", null);
						}
					}else {
						json.put("enteredby", null);
						json.put("enteredbyuserid", null);
						json.put("enteredbyuuid", null);
						json.put("entereddate", null);
					}
					
					if(conut.getIV_APPROVE_UID()!=null) {
						user=getUserName(conut.getIV_APPROVE_UID());
						if(user!=null) {
						json.put("approvedby", user.getSVU_NAME());
						json.put("approvedbyuserid", user.getSVU_USER_NAME());
						json.put("approvedbyuuid", user.getSVC_UID());
						json.put("approveddate", conut.getIV_APPROVE_DATE());
						}
						else {
							json.put("approvedby", null);
							json.put("approvedbyuserid", null);
							json.put("approvedbyuuid", null);
							json.put("approveddate", null);
						}
					}else {
						json.put("approvedby", null);
						json.put("approvedbyuserid", null);
						json.put("approvedbyuuid", null);
						json.put("approveddate", null);
					}
					
					if(conut.getIV_LAST_UPDATE_UID()!=null) {
						user=getUserName(conut.getIV_LAST_UPDATE_UID());
						if(user!=null) {
						json.put("modifiedby", user.getSVU_NAME());
						json.put("modifiedbyuserid", user.getSVU_USER_NAME());
						json.put("modifiedbyuuid", user.getSVC_UID());
						json.put("modifieddate", conut.getIV_LAST_UPDATE_DATE());
						}
						else {
							json.put("modifiedby", null);
							json.put("modifiedbyuserid", null);
							json.put("modifiedbyuuid", null);
							json.put("modifieddate", null);
						}
					}else {
						json.put("modifiedby", null);
						json.put("modifiedbyuserid", null);
						json.put("modifiedbyuuid", null);
						json.put("modifieddate", null);
					}
					jsonArray.add(json);
					
				});
		 }
		return new ResponseEntity<List<Map<String,Object>>>(jsonArray,HttpStatus.OK);
	}
 
	
	public FUND_USERS isValid(String userid)
	{
		FUND_USERS fuser= fund_UserRepository.findByUSER_NAME(userid);
		if(fuser!=null)
		{
			return fuser;
		}
		else
		{
		
		return null;
		}
		
	}
	
	public FUND_USERS getUserName(String userid)
	{
		FUND_USERS fuser= fund_UserRepository.findOne(userid);
		if(fuser!=null)
		{
			return fuser;
		}
		else
		{
		
		return null;
		}
		
	}

	
}
