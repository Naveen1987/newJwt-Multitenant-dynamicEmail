package com.fynisys.controller.people;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntToLongFunction;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fynisys.activitylogs.ActivityLogger;
import com.fynisys.controller.people.beans.Commission;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.clienttype.FUND_MAR_CLIENT_TYPE;
import com.fynisys.model.crm.RE_INVESTOR;
import com.fynisys.model.funds.FUND_MSTR;
import com.fynisys.model.parameters.FUND_INSTRUMENT_MSTR;
import com.fynisys.model.people.FUND_BROKER_MSTR;
import com.fynisys.model.people.FWMS_COMMISSION;
import com.fynisys.model.stock.FUND_SHARE_COMPANY_MSTR;
import com.fynisys.repository.funds.FUND_MSTRRepository;
import com.fynisys.repository.people.FWMS_COMMISSIONRepository;
import com.fynisys.repository.stock.FUND_SHARE_COMPANY_MSTRRepository;
import com.fynisys.utilities.FundUserValidate;
import com.fynisys.utilities.LevelParameterValidator;
import com.fynisys.utilities.StockParameters;
/*
WMS_SNO                                                        NUMBER(4),       - auto number
 WMS_EDATE                                                      DATE, - effect date
 WMS_LEAVEL							VARCHAR2(25), - level 
 WMS_FUND							NUMBER(10), - fund
 WMS_CLIENT_TYPE                                                NUMBER(15), -  client type
 WMS_CLIENT_ID                                                  NUMBER(15), - client 
 WMS_BROKER                                                     NUMBER(15), - broker
 WMS_STOCK_EXCHANGE_ID                                          NUMBER(4), - stock exchange
 WMS_ASSET_TYPE_ID                                              NUMBER(4), - assest type
 WMS_SHARES_AMOUNT_CAL                                          VARCHAR2(15), - flag
 WMS_TRAN_PROC_FLAG                                             VARCHAR2(25), -perorder /per full tranaction 
 WMS_TRAN_PROC_FEE                                              NUMBER(25,8), - transaction procedure fee
 WMS_COMMISSION_FEE                                             NUMBER(25,8), - commission % 
 WMS_MIN_COMMISSION_FEE                                         NUMBER(25,8), - min commission fee
 WMS_SERVICE_TAX                                                NUMBER(15,8), - service tax
 WMS_STT                                                        NUMBER(15,8), - STT
 WMS_TURNOVER_CGS                                               NUMBER(15,8), - turnover
 WMS_RMS_CGS                                                    NUMBER(15,8), - RMS
 WMS_SEBI_CGS                                                   NUMBER(15,8), - SEBI
 WMS_STRU_CGS                                                   NUMBER(15,8), - STRU
 WMS_STAMP_DUTY                                                 NUMBER(15,8), - Duty
 WMS_VAT							NUMBER(15,8), - vat
 WMS_STATUS                                                     VARCHAR2(20), - status
 WMS_COMMENTS                                                   VARCHAR2(300), - comments
 */
@RestController
public class FWMS_COMMISSIONController {
	
	@Autowired
	LevelParameterValidator levelParameterValidator;
	@Autowired
	StockParameters stockParameters;
	@Autowired
	FUND_MSTRRepository fUND_MSTRRepository; 
	@Autowired
	FUND_SHARE_COMPANY_MSTRRepository fUND_SHARE_COMPANY_MSTRRepository;
	@Autowired
	ActivityLogger activityLogger;  
	@Autowired
	FundUserValidate fundUserValidate;
	@Autowired
	FWMS_COMMISSIONRepository fWMS_COMMISSIONRepository;
	private Logger logger=LoggerFactory.getLogger("FWMS_COMMISSION Controller");
	
	@PostMapping("/saveCommision")
	public ResponseEntity<?> save(RequestEntity<Map<String, Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		//Object WMS_SNO=requestJson.get("SNO");  //                                                        NUMBER(4),       - auto number
		Object WMS_EDATE=requestJson.get("EDATE");  //                                                      DATE, - effect date
		Object WMS_FUND=requestJson.get("FUNDID");  //							NUMBER(10), - fund
		Object WMS_STOCK_EXCHANGE_ID=requestJson.get("STOCK_EXCHANGE_ID");  //                                          NUMBER(4), - stock exchange
		Object WMS_ASSET_TYPE_ID=requestJson.get("ASSET_ID");  //                                              NUMBER(4), - assest type
		
		Object WMS_LEAVEL=requestJson.get("LEAVEL");  //							VARCHAR2(25), - level
		Object WMS_CLIENT_TYPE=requestJson.get("CLIENT_TYPEID");  //                                                NUMBER(15), -  client type
		Object WMS_CLIENT_ID=requestJson.get("CLIENT_ID");  //                                                  NUMBER(15), - client 
		Object WMS_BROKER=requestJson.get("BROKERID");  //                                                     NUMBER(15), - broker
		Object WMS_SHARES_AMOUNT_CAL=requestJson.get("SHARES_AMOUNT_CAL");  //                                          VARCHAR2(15), - flag
		Object WMS_TRAN_PROC_FLAG=requestJson.get("TRAN_PROC_FLAG");  //                                             VARCHAR2(25),  -perorder /per full tranaction 
		Object WMS_TRAN_PROC_FEE=requestJson.get("TRAN_PROC_FEE");  //                                              NUMBER(25,8), - transaction procedure fee
		Object WMS_COMMISSION_FEE=requestJson.get("COMMISSION_FEE");  //                                             NUMBER(25,8), - commission % 
		Object WMS_MIN_COMMISSION_FEE=requestJson.get("MIN_COMMISSION_FEE");  //                                         NUMBER(25,8), - min commission fee
		Object WMS_SERVICE_TAX=requestJson.get("SERVICE_TAX");  //                                                NUMBER(15,8), - service tax
		Object WMS_STT=requestJson.get("STT");  //                                                        NUMBER(15,8), - STT
		Object WMS_TURNOVER_CGS=requestJson.get("TURNOVER_CGS");  //                                               NUMBER(15,8), - turnover
		Object WMS_RMS_CGS=requestJson.get("RMS_CGS");  //                                                    NUMBER(15,8), - RMS
		Object WMS_SEBI_CGS=requestJson.get("SEBI_CGS");  //                                                   NUMBER(15,8), - SEBI
		Object WMS_STRU_CGS=requestJson.get("STRU_CGS");  //                                                   NUMBER(15,8), - STRU
		Object WMS_STAMP_DUTY=requestJson.get("STAMP_DUTY");  //                                                 NUMBER(15,8), - Duty
		Object WMS_VAT=requestJson.get("VAT");  //							NUMBER(15,8), - vat
		Object WMS_COMMENTS=requestJson.get("comments");
		Object createdby=requestJson.get("createdby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		if(WMS_EDATE==null||WMS_LEAVEL==null||WMS_FUND==null||WMS_STOCK_EXCHANGE_ID==null||
				WMS_ASSET_TYPE_ID==null||WMS_TRAN_PROC_FEE==null||createdby==null||
				SVL_DESC==null||SVL_SCREEN==null) {
			  json.put("msg", "Please check Input json, missing some required attributes");
			  logger.error("Please check Input json, missing some required attributes");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		  }
		 else {
		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FWMS_COMMISSION fc=new FWMS_COMMISSION();
		if(WMS_EDATE!=null) {
			if(WMS_EDATE.toString().isEmpty()==false) {
				Calendar cl=Calendar.getInstance();
				cl.setTimeInMillis(new Long(WMS_EDATE.toString().trim()));
				fc.setWMS_EDATE(cl);
			}
		}
		if(WMS_FUND!=null) {
			if(WMS_FUND.toString().isEmpty()==false) {
				fc.setWMS_FUND(new Long(WMS_FUND.toString().trim()));
			}
		}
		
		if(WMS_STOCK_EXCHANGE_ID!=null) {
			if(WMS_STOCK_EXCHANGE_ID.toString().isEmpty()==false) {
				fc.setWMS_STOCK_EXCHANGE_ID(new Long(WMS_STOCK_EXCHANGE_ID.toString().trim()));
			}
		}
		
		if(WMS_ASSET_TYPE_ID!=null) {
			if(WMS_ASSET_TYPE_ID.toString().isEmpty()==false) {
				fc.setWMS_ASSET_TYPE_ID(new Long(WMS_ASSET_TYPE_ID.toString().trim()));
			}
		}
		
		
		if(WMS_LEAVEL!=null) {
			if(WMS_LEAVEL.toString().isEmpty()==false) {
				String level=WMS_LEAVEL.toString().trim();
				/*
				 * setting Level
				 */
				fc.setWMS_LEAVEL(level);
				if(level.equalsIgnoreCase("broker")) {
					if(WMS_BROKER!=null) {
						if(WMS_BROKER.toString().isEmpty()==false) {
							fc.setWMS_BROKER(new Long(WMS_BROKER.toString().trim()));
						}
					}
				}else if(level.equalsIgnoreCase("client")) {
					if(WMS_CLIENT_ID!=null) {
						if(WMS_CLIENT_ID.toString().isEmpty()==false) {
							fc.setWMS_CLIENT_ID(new Long(WMS_CLIENT_ID.toString().trim()));
						}
					}
				}else if(level.equalsIgnoreCase("client type")) {
					if(WMS_CLIENT_TYPE!=null) {
						if(WMS_CLIENT_TYPE.toString().isEmpty()==false) {
							fc.setWMS_CLIENT_TYPE(new Long(WMS_CLIENT_TYPE.toString().trim()));
						}
					}
				} 
			}
		}
		
		
		
		if(WMS_SHARES_AMOUNT_CAL!=null) {
			if(WMS_SHARES_AMOUNT_CAL.toString().isEmpty()==false) {
				fc.setWMS_SHARES_AMOUNT_CAL(WMS_SHARES_AMOUNT_CAL.toString().trim());
			}
		}
		
		if(WMS_TRAN_PROC_FLAG!=null) {
			if(WMS_TRAN_PROC_FLAG.toString().isEmpty()==false) {
				fc.setWMS_TRAN_PROC_FLAG(WMS_TRAN_PROC_FLAG.toString().trim());
			}
		}
		
		if(WMS_TRAN_PROC_FEE!=null) {
			if(WMS_TRAN_PROC_FEE.toString().isEmpty()==false) {
				fc.setWMS_TRAN_PROC_FEE(new Double(WMS_TRAN_PROC_FEE.toString().trim()));
			}
		}
		

		if(WMS_COMMISSION_FEE!=null) {
			if(WMS_COMMISSION_FEE.toString().isEmpty()==false) {
				fc.setWMS_COMMISSION_FEE(new Double(WMS_COMMISSION_FEE.toString().trim()));
			}
		}
		
		if(WMS_MIN_COMMISSION_FEE!=null) {
			if(WMS_MIN_COMMISSION_FEE.toString().isEmpty()==false) {
				fc.setWMS_MIN_COMMISSION_FEE(new Double(WMS_MIN_COMMISSION_FEE.toString().trim()));
			}
		}
		
		if(WMS_SERVICE_TAX!=null) {
			if(WMS_SERVICE_TAX.toString().isEmpty()==false) {
				fc.setWMS_SERVICE_TAX(new Double(WMS_SERVICE_TAX.toString().trim()));
			}
		}
		
		if(WMS_STT!=null) {
			if(WMS_STT.toString().isEmpty()==false) {
				fc.setWMS_STT(new Double(WMS_STT.toString().trim()));
			}
		}
		
		if(WMS_TURNOVER_CGS!=null) {
			if(WMS_TURNOVER_CGS.toString().isEmpty()==false) {
				fc.setWMS_TURNOVER_CGS(new Double(WMS_TURNOVER_CGS.toString().trim()));
			}
		}
		
		if(WMS_RMS_CGS!=null) {
			if(WMS_RMS_CGS.toString().isEmpty()==false) {
				fc.setWMS_RMS_CGS(new Double(WMS_RMS_CGS.toString().trim()));
			}
		}
		

		if(WMS_SEBI_CGS!=null) {
			if(WMS_SEBI_CGS.toString().isEmpty()==false) {
				fc.setWMS_SEBI_CGS(new Double(WMS_SEBI_CGS.toString().trim()));
			}
		}

		if(WMS_STRU_CGS!=null) {
			if(WMS_STRU_CGS.toString().isEmpty()==false) {
				fc.setWMS_STRU_CGS(new Double(WMS_STRU_CGS.toString().trim()));
			}
		}
		
		if(WMS_STAMP_DUTY!=null) {
			if(WMS_STAMP_DUTY.toString().isEmpty()==false) {
				fc.setWMS_STAMP_DUTY(new Double(WMS_STAMP_DUTY.toString().trim()));
			}
		}
		
		if(WMS_VAT!=null) {
			if(WMS_VAT.toString().isEmpty()==false) {
				fc.setWMS_VAT(new Double(WMS_VAT.toString().trim()));
			}
		}
		
		if(WMS_COMMENTS!=null) {
			fc.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
		fc.setWMS_STATUS("Not Approved");
				
		 		
		fc.setWMS_ENTER_UID(fuser.getSVC_UID());
		fc.setWMS_ENTER_DATE(Calendar.getInstance());
		  try
			{
			  fc=fWMS_COMMISSIONRepository.save(fc);
			 if(fc!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "CREATE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Commsion:"+fc.getWMS_SNO());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Commsion:"+fc.getWMS_SNO());
					
					json.put("createdby", fc.getWMS_ENTER_UID());
					json.put("createdon", fc.getWMS_ENTER_DATE());
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Commsion not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Commsion not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}


	@PostMapping("/updateCommision")
	public ResponseEntity<?> update(RequestEntity<Map<String, Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object WMS_SNO=requestJson.get("SNO");  //                                                        NUMBER(4),       - auto number
		Object WMS_EDATE=requestJson.get("EDATE");  //                                                      DATE, - effect date
		Object WMS_FUND=requestJson.get("FUNDID");  //							NUMBER(10), - fund
		Object WMS_STOCK_EXCHANGE_ID=requestJson.get("STOCK_EXCHANGE_ID");  //                                          NUMBER(4), - stock exchange
		Object WMS_ASSET_TYPE_ID=requestJson.get("ASSET_ID");  //                                              NUMBER(4), - assest type
		Object WMS_LEAVEL=requestJson.get("LEAVEL");  //							VARCHAR2(25), - level
		Object WMS_CLIENT_TYPE=requestJson.get("CLIENT_TYPEID");  //                                                NUMBER(15), -  client type
		Object WMS_CLIENT_ID=requestJson.get("CLIENT_ID");  //                                                  NUMBER(15), - client 
		Object WMS_BROKER=requestJson.get("BROKERID");  //                                                     NUMBER(15), - broker
		Object WMS_SHARES_AMOUNT_CAL=requestJson.get("SHARES_AMOUNT_CAL");  //                                          VARCHAR2(15), - flag
		Object WMS_TRAN_PROC_FLAG=requestJson.get("TRAN_PROC_FLAG");  //                                             VARCHAR2(25),  -perorder /per full tranaction 
		Object WMS_TRAN_PROC_FEE=requestJson.get("TRAN_PROC_FEE");  //                                              NUMBER(25,8), - transaction procedure fee
		Object WMS_COMMISSION_FEE=requestJson.get("COMMISSION_FEE");  //                                             NUMBER(25,8), - commission % 
		Object WMS_MIN_COMMISSION_FEE=requestJson.get("MIN_COMMISSION_FEE");  //                                         NUMBER(25,8), - min commission fee
		Object WMS_SERVICE_TAX=requestJson.get("SERVICE_TAX");  //                                                NUMBER(15,8), - service tax
		Object WMS_STT=requestJson.get("STT");  //                                                        NUMBER(15,8), - STT
		Object WMS_TURNOVER_CGS=requestJson.get("TURNOVER_CGS");  //                                               NUMBER(15,8), - turnover
		Object WMS_RMS_CGS=requestJson.get("RMS_CGS");  //                                                    NUMBER(15,8), - RMS
		Object WMS_SEBI_CGS=requestJson.get("SEBI_CGS");  //                                                   NUMBER(15,8), - SEBI
		Object WMS_STRU_CGS=requestJson.get("STRU_CGS");  //                                                   NUMBER(15,8), - STRU
		Object WMS_STAMP_DUTY=requestJson.get("STAMP_DUTY");  //                                                 NUMBER(15,8), - Duty
		Object WMS_VAT=requestJson.get("VAT");  //							NUMBER(15,8), - vat
		Object WMS_COMMENTS=requestJson.get("comments");
		Object createdby=requestJson.get("createdby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		if(WMS_SNO==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
			  json.put("msg", "Please check Input json, missing some required attributes");
			  logger.error("Please check Input json, missing some required attributes");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		  }
		 else {
		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FWMS_COMMISSION fc=fWMS_COMMISSIONRepository.findOne(new Long(WMS_SNO.toString().trim()));
		if(fc==null) {
			json.put("msg", "Commision is not found");
			logger.error("Commision is not found");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		if(WMS_EDATE!=null) {
			if(WMS_EDATE.toString().isEmpty()==false) {
				Calendar cl=Calendar.getInstance();
				cl.setTimeInMillis(new Long(WMS_EDATE.toString().trim()));
				fc.setWMS_EDATE(cl);
			}
		}
		if(WMS_FUND!=null) {
			if(WMS_FUND.toString().isEmpty()==false) {
				fc.setWMS_FUND(new Long(WMS_FUND.toString().trim()));
			}
		}
		
		if(WMS_STOCK_EXCHANGE_ID!=null) {
			if(WMS_STOCK_EXCHANGE_ID.toString().isEmpty()==false) {
				fc.setWMS_STOCK_EXCHANGE_ID(new Long(WMS_STOCK_EXCHANGE_ID.toString().trim()));
			}
		}
		
		if(WMS_ASSET_TYPE_ID!=null) {
			if(WMS_ASSET_TYPE_ID.toString().isEmpty()==false) {
				fc.setWMS_ASSET_TYPE_ID(new Long(WMS_ASSET_TYPE_ID.toString().trim()));
			}
		}
		
		if(WMS_LEAVEL!=null) {
			if(WMS_LEAVEL.toString().isEmpty()==false) {
				String level=WMS_LEAVEL.toString().trim();
				/*
				 * setting Level
				 */
				fc.setWMS_LEAVEL(level);
				if(level.equalsIgnoreCase("broker")) {
					if(WMS_BROKER!=null) {
						if(WMS_BROKER.toString().isEmpty()==false) {
							fc.setWMS_BROKER(new Long(WMS_BROKER.toString().trim()));
						}
					}
				}else if(level.equalsIgnoreCase("client")) {
					if(WMS_CLIENT_ID!=null) {
						if(WMS_CLIENT_ID.toString().isEmpty()==false) {
							fc.setWMS_CLIENT_ID(new Long(WMS_CLIENT_ID.toString().trim()));
						}
					}
				}else if(level.equalsIgnoreCase("client type")) {
					if(WMS_CLIENT_TYPE!=null) {
						if(WMS_CLIENT_TYPE.toString().isEmpty()==false) {
							fc.setWMS_CLIENT_TYPE(new Long(WMS_CLIENT_TYPE.toString().trim()));
						}
					}
				} 
			}
		}
		
		
		if(WMS_SHARES_AMOUNT_CAL!=null) {
			if(WMS_SHARES_AMOUNT_CAL.toString().isEmpty()==false) {
				fc.setWMS_SHARES_AMOUNT_CAL(WMS_SHARES_AMOUNT_CAL.toString().trim());
			}
		}
		
		
		if(WMS_TRAN_PROC_FLAG!=null) {
			if(WMS_TRAN_PROC_FLAG.toString().isEmpty()==false) {
				fc.setWMS_TRAN_PROC_FLAG(WMS_TRAN_PROC_FLAG.toString().trim());
			}
		}
		
		if(WMS_TRAN_PROC_FEE!=null) {
			if(WMS_TRAN_PROC_FEE.toString().isEmpty()==false) {
				fc.setWMS_TRAN_PROC_FEE(new Double(WMS_TRAN_PROC_FEE.toString().trim()));
			}
		}
		

		if(WMS_COMMISSION_FEE!=null) {
			if(WMS_COMMISSION_FEE.toString().isEmpty()==false) {
				fc.setWMS_COMMISSION_FEE(new Double(WMS_COMMISSION_FEE.toString().trim()));
			}
		}
		
		if(WMS_MIN_COMMISSION_FEE!=null) {
			if(WMS_MIN_COMMISSION_FEE.toString().isEmpty()==false) {
				fc.setWMS_MIN_COMMISSION_FEE(new Double(WMS_MIN_COMMISSION_FEE.toString().trim()));
			}
		}
		
		if(WMS_SERVICE_TAX!=null) {
			if(WMS_SERVICE_TAX.toString().isEmpty()==false) {
				fc.setWMS_SERVICE_TAX(new Double(WMS_SERVICE_TAX.toString().trim()));
			}
		}
		
		if(WMS_STT!=null) {
			if(WMS_STT.toString().isEmpty()==false) {
				fc.setWMS_STT(new Double(WMS_STT.toString().trim()));
			}
		}
		
		if(WMS_TURNOVER_CGS!=null) {
			if(WMS_TURNOVER_CGS.toString().isEmpty()==false) {
				fc.setWMS_TURNOVER_CGS(new Double(WMS_TURNOVER_CGS.toString().trim()));
			}
		}
		
		if(WMS_RMS_CGS!=null) {
			if(WMS_RMS_CGS.toString().isEmpty()==false) {
				fc.setWMS_RMS_CGS(new Double(WMS_RMS_CGS.toString().trim()));
			}
		}
		

		if(WMS_SEBI_CGS!=null) {
			if(WMS_SEBI_CGS.toString().isEmpty()==false) {
				fc.setWMS_SEBI_CGS(new Double(WMS_SEBI_CGS.toString().trim()));
			}
		}

		if(WMS_STRU_CGS!=null) {
			if(WMS_STRU_CGS.toString().isEmpty()==false) {
				fc.setWMS_STRU_CGS(new Double(WMS_STRU_CGS.toString().trim()));
			}
		}
		
		if(WMS_STAMP_DUTY!=null) {
			if(WMS_STAMP_DUTY.toString().isEmpty()==false) {
				fc.setWMS_STAMP_DUTY(new Double(WMS_STAMP_DUTY.toString().trim()));
			}
		}
		
		if(WMS_VAT!=null) {
			if(WMS_VAT.toString().isEmpty()==false) {
				fc.setWMS_VAT(new Double(WMS_VAT.toString().trim()));
			}
		}
		
		if(WMS_COMMENTS!=null) {
			fc.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
		fc.setWMS_STATUS("Not Approved");	 		
		fc.setWMS_LAST_UPDATE_UID(fuser.getSVC_UID());
		fc.setWMS_LAST_UPDATE_DATE(Calendar.getInstance());
		  try
			{
			  fc=fWMS_COMMISSIONRepository.save(fc);
			 if(fc!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "UPDATE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Commsion:"+fc.getWMS_SNO());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Commsion:"+fc.getWMS_SNO());
					
					Commission cm=getJson(fc);
					cm.setMsg("saved");
					return new ResponseEntity<Commission>(cm,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Commsion not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Rm not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}


	@PostMapping("/approveCommision")
	public ResponseEntity<?> approve(RequestEntity<Map<String, Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object WMS_SNO=requestJson.get("SNO");  //                                                        NUMBER(4),       - auto number
		Object WMS_COMMENTS=requestJson.get("comments");
		Object createdby=requestJson.get("approvedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		if(WMS_SNO==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
			  json.put("msg", "Please check Input json, missing some required attributes");
			  logger.error("Please check Input json, missing some required attributes");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		  }
		 else {
		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FWMS_COMMISSION fc=fWMS_COMMISSIONRepository.findOne(new Long(WMS_SNO.toString().trim()));
		if(fc==null) {
			json.put("msg", "Commision is not found");
			logger.error("Commision is not found");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}	
		
	/*
	 * Check Approval first
	 */
		
		FUND_MSTR fd=fUND_MSTRRepository.findOne(fc.getWMS_FUND());
		if(fd!=null) {
			if(fd.getIV_APPROVE_UID()==null) {
				json.put("msg", "Fund is not Approved");
				logger.error("Fund is not Approved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		
		FUND_SHARE_COMPANY_MSTR fscm=fUND_SHARE_COMPANY_MSTRRepository.findOne(fc.getWMS_STOCK_EXCHANGE_ID());
		if(fscm!=null) {
			if(fscm.getIV_APPROVE_UID()==null) {
				json.put("msg", "Stock is not Approved");
				logger.error("Stock is not Approved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		
		FUND_MAR_CLIENT_TYPE type=levelParameterValidator.getCLIENT_TYPEApproved(fc.getWMS_CLIENT_TYPE());
		if(type!=null) {
			if(type.getIV_APPROVE_UID()==null) {
				json.put("msg", "Client Type is not Approved");
				logger.error("Client Type is not Approved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		
		RE_INVESTOR re=levelParameterValidator.getCLIENTApproved(fc.getWMS_CLIENT_ID());
		if(re!=null) {
			if(re.getIV_APPROVE_UID()==null) {
				json.put("msg", "Client is not Approved");
				logger.error("Client is not Approved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		
		FUND_BROKER_MSTR broker=levelParameterValidator.getBrokerApproved(fc.getWMS_BROKER());
		if(broker!=null) {
			if(broker.getIV_APPROVE_UID()==null) {
				json.put("msg", "Broker is not Approved");
				logger.error("Broker is not Approved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		
		FUND_INSTRUMENT_MSTR mstr=stockParameters.getFUND_INSTRUMENT_MSTR(fc.getWMS_ASSET_TYPE_ID());
		if(mstr!=null) {
			if(mstr.getIV_APPROVE_UID()==null) {
				json.put("msg", "Asset is not Approved");
				logger.error("Asset is not Approved");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}
		
		if(WMS_COMMENTS!=null) {
			fc.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
		fc.setWMS_STATUS("Approved");
				
		 		
		fc.setWMS_APPROVE_UID(fuser.getSVC_UID());
		fc.setWMS_APPROVE_DATE(Calendar.getInstance());
		  try
			{
			  fc=fWMS_COMMISSIONRepository.save(fc);
			 if(fc!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "APPROVE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Rm:"+fc.getWMS_SNO());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Commsion:"+fc.getWMS_SNO());
					
					Commission cm=getJson(fc);
					cm.setMsg("saved");
					return new ResponseEntity<Commission>(cm,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Commsion not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Commsion not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}


	@PostMapping("/revokeCommision")
	public ResponseEntity<?> revoke(RequestEntity<Map<String, Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object WMS_SNO=requestJson.get("SNO");  //                                                        NUMBER(4),       - auto number
		Object WMS_COMMENTS=requestJson.get("comments");
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		if(WMS_SNO==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
			  json.put("msg", "Please check Input json, missing some required attributes");
			  logger.error("Please check Input json, missing some required attributes");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		  }
		 else {
		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FWMS_COMMISSION fc=fWMS_COMMISSIONRepository.findOne(new Long(WMS_SNO.toString().trim()));
		if(fc==null) {
			json.put("msg", "Commision is not found");
			logger.error("Commision is not found");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}		
		if(WMS_COMMENTS!=null) {
			fc.setWMS_COMMENTS(WMS_COMMENTS.toString());
				}
		fc.setWMS_STATUS("Not Approved");
				
		 		
		fc.setWMS_APPROVE_UID(null);
		fc.setWMS_APPROVE_DATE(null);
		
		fc.setWMS_LAST_UPDATE_UID(fuser.getSVC_UID());
		fc.setWMS_LAST_UPDATE_DATE(Calendar.getInstance());
		  try
			{
			  fc=fWMS_COMMISSIONRepository.save(fc);
			 if(fc!=null) {
				 	json.put("msg", "saved");				
				 	boolean loggerw=activityLogger.writeLog(fuser, SVL_SCREEN, SVL_DESC, "REVOKE");
				 	if(loggerw==true) {
				 		logger.info("Both Record and Logs saved for Commsion:"+fc.getWMS_SNO());
						json.put("logs","logs are saved");
				 	}else {
				 		logger.info("Record is saved but logs can't saved due error in saving of logs");
						json.put("logs","Record is saved but logs can't saved due error in saving of logs");
				 	}
					
					logger.info("Saved New Commsion:"+fc.getWMS_SNO());
					
					Commission cm=getJson(fc);
					cm.setMsg("saved");
					return new ResponseEntity<Commission>(cm,HttpStatus.OK);
				}
				else {
					json.put("msg","Not saved");
					logger.error("Commsion not saved");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Commsion not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}

	@PostMapping("/deleteCommision")
	public ResponseEntity<?> delete(RequestEntity<Map<String, Object>>requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Map<String,Object> json=new HashMap<>();
		Object WMS_SNO=requestJson.get("SNO");  //                                                        NUMBER(4),       - auto number
		Object createdby=requestJson.get("modifiedby");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		if(WMS_SNO==null||createdby==null||SVL_DESC==null||SVL_SCREEN==null) {
			  json.put("msg", "Please check Input json, missing some required attributes");
			  logger.error("Please check Input json, missing some required attributes");
		      return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		  }
		 else {
		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FWMS_COMMISSION fc=fWMS_COMMISSIONRepository.findOne(new Long(WMS_SNO.toString().trim()));
		if(fc==null) {
			json.put("msg", "Commision is not found");
			logger.error("Commision is not found");
			return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}		
		
		  try
			{
			  fWMS_COMMISSIONRepository.delete(fc);
				json.put("msg", "DELETE");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.OK);
			}
			catch(Exception e)
			{
				json.put("msg", e.getMessage());
				logger.error("Commsion not saved :"+e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				
			}
	  }
	}
	@GetMapping("/commsionsearch")
	public ResponseEntity<?> getAllCOmmsion(Pageable page,
			@RequestParam(required=true,value="action")String action,
			@RequestParam(required=false,value="paramSearch")String paramString
			){
		
		List<FWMS_COMMISSION> allCommsion=null;
		/*
		 * Now for searching 
		 *
		 */
		    List<Long> fundids=new ArrayList<>();
			List<Long> clients=new ArrayList<>();
			List<Long> client_types=new ArrayList<>();
			List<Long> stocks=new ArrayList<>();
			List<Integer> asseta=new ArrayList<>();
			List<Long> assets=new ArrayList<>();
			List<Long> brokers=new ArrayList<>();
			if(paramString!=null) {
				if(paramString.isEmpty()==false) {
					fundids=fUND_MSTRRepository.fund_ids_Search(paramString);
					stocks=fUND_SHARE_COMPANY_MSTRRepository.find_Ids_FUND_SHARE_COMPANY_MSTR_ASC_SEARCH(paramString);
					asseta=stockParameters.getAllAssetsId(paramString);		
					clients=levelParameterValidator.get_ids_clients(paramString);
					client_types= levelParameterValidator.get_ids_clientType(paramString);
					brokers=levelParameterValidator.get_ids_Brokers(paramString);
				}
				
			}else {
				paramString="";
			}
			
if(fundids.size()==0) {
	fundids.add(new Long(-1));
}
if(stocks.size()==0) {
	stocks.add(new Long(-1));
}

if(asseta.size()==0) {
	assets.add(new Long(-1));
}else {
	
	assets=asseta.stream().map(i->new Long(i+"")).collect(Collectors.toList());
	//or
//	asseta.forEach(val->{
//		assets.add(new Long(val+""));
//	});
}
if(clients.size()==0) {
	clients.add(new Long(-1));
}

if(client_types.size()==0) {
	client_types.add(new Long(-1));
}
if(brokers.size()==0) {
	brokers.add(new Long(-1));
}
/*
 * 
 */
			if(action.isEmpty()==false) {
				if(action.equalsIgnoreCase("ALL")) {
					if(paramString.isEmpty()==false) {
				    allCommsion=fWMS_COMMISSIONRepository.findAllCommsion_search(fundids, client_types, clients, brokers, stocks, assets);
					}else {
					allCommsion=fWMS_COMMISSIONRepository.findAllCommsion();
					}
				}
				else if(action.equalsIgnoreCase("APPROVED")) {
					if(paramString.isEmpty()==false) {
						 allCommsion=fWMS_COMMISSIONRepository.findAllCommsion_search_APPROVED(fundids, client_types, clients, brokers, stocks, assets);
					}else {
						allCommsion=fWMS_COMMISSIONRepository.findAllAPPROVEDCommsion();	
					}
					
				}
				else if(action.equalsIgnoreCase("UNAPPROVED")) {
					if(paramString.isEmpty()==false) {
						 allCommsion=fWMS_COMMISSIONRepository.findAllCommsion_search_UNAPPROVED(fundids, client_types, clients, brokers, stocks, assets);
					}else {
						allCommsion=fWMS_COMMISSIONRepository.findAllUNAPPROVEDCommsion();
					}
					
				}
				else {
					Map<String,Object> jsonArray=new HashMap<>();
					 jsonArray.put("msg", "No data found");
					 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
				}
			}
			else {
				Map<String,Object> jsonArray=new HashMap<>();
				 jsonArray.put("msg", "Action value is missing");
				 return new ResponseEntity<Map<String,Object>>(jsonArray,HttpStatus.OK);
			}
		
		
		List<Commission> allcommsiondata= new ArrayList<Commission>();
		if(allCommsion!=null) {
			if(allCommsion.isEmpty()==false) {
				allCommsion.forEach(commsion->{
					Commission cm=getJson(commsion);
					allcommsiondata.add(cm);
				});
			}
		}
		/*
		 * Sort to data
		 */
		Collections.sort(allcommsiondata);
		int start = page.getOffset();
		int end = (start + page.getPageSize()) > allcommsiondata.size() ? allcommsiondata.size()
				: (start + page.getPageSize());
		Page<Commission> jsonArray = new PageImpl<Commission>(allcommsiondata.subList(start, end), page,
				allcommsiondata.size());
		 return new ResponseEntity<Page<Commission>>(jsonArray,HttpStatus.OK);	
	}

	
	public Commission getJson(FWMS_COMMISSION conut) {
		Commission ffb=new Commission();
		ffb.setSNO(conut.getWMS_SNO());    
		ffb.setEDATE(conut.getWMS_EDATE());                                
		ffb.setLEAVEL(conut.getWMS_LEAVEL()); 
		
		
		FUND_MAR_CLIENT_TYPE type=levelParameterValidator.getCLIENT_TYPEApproved(conut.getWMS_CLIENT_TYPE());
		RE_INVESTOR client= levelParameterValidator.getCLIENTApproved(conut.getWMS_CLIENT_ID());
		FUND_BROKER_MSTR broker=levelParameterValidator.getBrokerApproved(conut.getWMS_BROKER());
		FUND_INSTRUMENT_MSTR asset=stockParameters.getFUND_INSTRUMENT_MSTR(conut.getWMS_ASSET_TYPE_ID());
		FUND_SHARE_COMPANY_MSTR stock=fUND_SHARE_COMPANY_MSTRRepository.findOne(conut.getWMS_STOCK_EXCHANGE_ID());
		FUND_MSTR fund=fUND_MSTRRepository.findOne(conut.getWMS_FUND());
		
		if(type!=null) {
			ffb.setCLIENT_TYPEID(type.getFCT_ID());
			ffb.setCLIENT_TYPENAME(type.getFCT_NAME());
		}else {
			ffb.setCLIENT_TYPEID(null);
			ffb.setCLIENT_TYPENAME(null);
		}
		
		if(client!=null) {
			ffb.setCLIENT_ID(client.getRI_INVESTOR_CODE());
			ffb.setCLIENT_NAME(client.getRI_INVESTOR_NAME());
		}else {
			ffb.setCLIENT_ID(null);
			ffb.setCLIENT_NAME(null);
		}
		
		if(broker!=null) {
			ffb.setBROKERID(broker.getSVB_CODE());
			ffb.setBROKERNAME(broker.getSVB_NAME());
		}
		else {
			ffb.setBROKERID(null);
			ffb.setBROKERNAME(null);
		}
		
		if(asset!=null) {
			ffb.setASSET_ID(new Long(asset.getSVI_CODE()+""));
			ffb.setASSET_NAME(asset.getSVI_NAME());
		}
		else {
			ffb.setASSET_ID(null);
			ffb.setASSET_NAME(null);
		}
		
		if(fund!=null) {
			ffb.setFUNDID(fund.getSVC_CODE());
			ffb.setFUNDNAME(fund.getSVC_NAME());
			
		}else {
			ffb.setFUNDID(null);
			ffb.setFUNDNAME(null);
			
		}
		
		if(stock!=null) {
			ffb.setSTOCK_EXCHANGE_ID(stock.getSVC_CODE()); 
			ffb.setSTOCK_EXCHANGE_NAME(stock.getSVC_NAME());
				
		}
		else {
			ffb.setSTOCK_EXCHANGE_ID(null); 
			ffb.setSTOCK_EXCHANGE_NAME(null);
			
		}
		
		
		ffb.setSHARES_AMOUNT_CAL(conut.getWMS_SHARES_AMOUNT_CAL());                                      
		ffb.setTRAN_PROC_FLAG(conut.getWMS_TRAN_PROC_FLAG());                                       
		ffb.setTRAN_PROC_FEE(conut.getWMS_TRAN_PROC_FEE());                                      
		ffb.setCOMMISSION_FEE(conut.getWMS_COMMISSION_FEE());                                      
		ffb.setMIN_COMMISSION_FEE(conut.getWMS_MIN_COMMISSION_FEE());                                      
		ffb.setSERVICE_TAX(conut.getWMS_SERVICE_TAX());                                      
		ffb.setSTT(conut.getWMS_STT());                                      
		ffb.setTURNOVER_CGS(conut.getWMS_TURNOVER_CGS());                                      
		ffb.setRMS_CGS(conut.getWMS_RMS_CGS());                                      
		ffb.setSEBI_CGS(conut.getWMS_SEBI_CGS());                                      
		ffb.setSTRU_CGS(conut.getWMS_STRU_CGS());                                      
		ffb.setSTAMP_DUTY(conut.getWMS_STAMP_DUTY());                                      
		ffb.setVAT(conut.getWMS_VAT());     
		ffb.setStatus(conut.getWMS_STATUS());
		ffb.setComments(conut.getWMS_COMMENTS());
		FUND_USERS user=null;
		if(conut.getWMS_ENTER_UID()!=null) {
			user=fundUserValidate.getUserName(conut.getWMS_ENTER_UID());
			if(user!=null) {
			ffb.setEnteredby( user.getSVU_NAME());
			ffb.setEnteredbyuserid( user.getSVU_USER_NAME());
			ffb.setEnteredbyuuid( user.getSVC_UID());
			ffb.setEntereddate(conut.getWMS_ENTER_DATE());
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
		
		if(conut.getWMS_APPROVE_UID()!=null) {
			user=fundUserValidate.getUserName(conut.getWMS_APPROVE_UID());
			if(user!=null) {
			ffb.setApprovedby( user.getSVU_NAME());
			ffb.setApprovedbyuserid( user.getSVU_USER_NAME());
			ffb.setApprovedbyuuid( user.getSVC_UID());
			ffb.setApproveddate( conut.getWMS_APPROVE_DATE());
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
		
		if(conut.getWMS_LAST_UPDATE_UID()!=null) {
			user=fundUserValidate.getUserName(conut.getWMS_LAST_UPDATE_UID());
			if(user!=null) {
			ffb.setModifiedby( user.getSVU_NAME());
			ffb.setModifiedbyuserid( user.getSVU_USER_NAME());
			ffb.setModifiedbyuuid( user.getSVC_UID());
			ffb.setModifieddate( conut.getWMS_LAST_UPDATE_DATE());
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
