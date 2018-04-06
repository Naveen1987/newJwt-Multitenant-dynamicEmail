package com.fynisys.controller.crm;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fynisys.activitylogs.ActivityLogger;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.crm.RE_CRMWORKFLOW;
import com.fynisys.model.crm.RE_CRMWORKFLOWSTATUS;
import com.fynisys.model.crm.RE_INVESTOR;
import com.fynisys.repository.crm.RE_CRMWORKFLOWRepository;
import com.fynisys.repository.crm.RE_CRMWORKFLOWSTATUSRepository;
import com.fynisys.repository.crm.RE_INVESTORRepository;
import com.fynisys.utilities.FundUserValidate;

// TODO: Auto-generated Javadoc
/**
 * The Class RE_CRMWORKFLOWController.
 */
@RestController
public class RE_CRMWORKFLOWController {

	/** The fund user validate. */
	@Autowired
	FundUserValidate fundUserValidate;
	
	/** The activity logger. */
	@Autowired
	ActivityLogger activityLogger;
	
	/** The r E INVESTOR repository. */
	@Autowired
	RE_INVESTORRepository rE_INVESTORRepository;
	
	/** The r E CRMWORKFLOW repository. */
	@Autowired
	RE_CRMWORKFLOWRepository rE_CRMWORKFLOWRepository;
	
	/** The r E CRMWORKFLOWSTATUS repository. */
	@Autowired
	RE_CRMWORKFLOWSTATUSRepository rE_CRMWORKFLOWSTATUSRepository;
	
	/** The Constant logger. */
	private static final Logger logger=LoggerFactory.getLogger("CRMWORKFLOW CONTROLLER");

	/**
	 * Saveforfm.
	 *
	 * @param requestData the request data
	 * @return the response entity
	 */
	@PostMapping("/initiatForFm")
	public ResponseEntity<?> saveforfm(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Object INVESTOR_CODE=requestJson.get("clientid");
		Object INITIATOR=requestJson.get("initiator");
		Object FM=requestJson.get("FM");
		
		Map<String,Object> json=new HashMap<>();
		RE_INVESTOR re_INVESTOR=null;
	  if(INVESTOR_CODE!=null) {
			if(INVESTOR_CODE.toString().isEmpty()==false) {
				re_INVESTOR=rE_INVESTORRepository.findOne(new Long(INVESTOR_CODE.toString()));
				
				if(re_INVESTOR==null) {
					json.put("msg", "Client Not found");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
			}else {
				json.put("msg", "Please check client ID");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "ClientId is null");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}

	  FUND_USERS initiator=null;
	  if(INITIATOR!=null) {
			if(INITIATOR.toString().isEmpty()==false) {
				initiator=fundUserValidate.isValid(INITIATOR.toString());
				if(initiator==null) {
					json.put("msg", "initiator Not found");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
			}else {
				json.put("msg", "Please check initiator ID");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "initiator is null");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
	  
	  FUND_USERS fm=null;
	  if(FM!=null) {
			if(FM.toString().isEmpty()==false) {
				fm=fundUserValidate.isValid(FM.toString());
				if(fm==null) {
					json.put("msg", "fm Not found");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
			}else {
				json.put("msg", "Please check fm ID");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "fm is null");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
	  
	  RE_CRMWORKFLOW reCRM=null;
	  reCRM=rE_CRMWORKFLOWRepository.getExistingInvestor(re_INVESTOR.getRI_WMS_CODE());
	  if(reCRM==null) {
		  reCRM=new RE_CRMWORKFLOW();
	  }
	  reCRM.setCURRENT_STATUS(0);
	  reCRM.setINITIATOR(initiator.getSVC_UID());
	  reCRM.setFM(fm.getSVC_UID());
	  reCRM.setrE_INVESTOR(re_INVESTOR);
	  /*
	   * Null to next
	   */
	  reCRM.setCO(null);
	  reCRM.setOP(null);
	  
	  
	  RE_CRMWORKFLOWSTATUS reCRMSTATUS=new RE_CRMWORKFLOWSTATUS();
	  reCRMSTATUS.setAPPROVAL_DATE(Calendar.getInstance());
	  reCRMSTATUS.setSTATUS(0);
	  reCRMSTATUS.setROLEID("FM");
	  reCRMSTATUS.setAPPROVEDID(fm.getSVC_UID());
	  reCRMSTATUS.setrE_CRMWORKFLOW(reCRM);
	  
	  /*
	   * setting Relation ship
	   */
	  reCRM.getrE_CRMWORKFLOWSTATUS().add(reCRMSTATUS);
	  try {
		 reCRM=rE_CRMWORKFLOWRepository.save(reCRM);
		 if(reCRM!=null) {
			 logger.info("Saved");
			 json.put("msg", "FM APPROVAL Saved");
		     return new ResponseEntity<>(json,HttpStatus.OK);
		 }
		 else {
			    json.put("msg", "Not saved without cause");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		 }
	  }catch (Exception e) {
		  json.put("msg", e.getMessage());
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
	}
	
	}

	
	/**
	 * Fm approved.
	 *
	 * @param requestData the request data
	 * @return the response entity
	 */
	@PostMapping("/fmApproved")
	public ResponseEntity<?> fmApproved(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Object FM=requestJson.get("FM");
		Object INVESTOR_CODE=requestJson.get("clientid");
		Object CO=requestJson.get("CO");
		Map<String,Object> json=new HashMap<>();
		RE_CRMWORKFLOW reCRM=null;
	  if(INVESTOR_CODE!=null) {
			if(INVESTOR_CODE.toString().isEmpty()==false) {
				reCRM=rE_CRMWORKFLOWRepository.getExistingInvestor(new Long(INVESTOR_CODE.toString()));
				if(reCRM==null) {
					json.put("msg", "Client Not found");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
			}else {
				json.put("msg", "Please check client ID");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "ClientId is null");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}

	  FUND_USERS fm=null;
	  if(FM!=null) {
			if(FM.toString().isEmpty()==false) {
				fm=fundUserValidate.isValid(FM.toString());
				if(fm==null) {
					json.put("msg", "fm Not found");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
				if(!reCRM.getFM().equalsIgnoreCase(fm.getSVC_UID())) {
					json.put("msg", "Fm is not valid to approved");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
			}else {
				json.put("msg", "Please check fm ID");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "fm is null");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
	 
	  FUND_USERS co=null;
	  if(CO!=null) {
			if(CO.toString().isEmpty()==false) {
				co=fundUserValidate.isValid(CO.toString());
				if(co==null) {
					json.put("msg", "co Not found");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
			}else {
				json.put("msg", "Please check co ID");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "co is null");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
	  
	 /*
	  * 
	  */
	  reCRM.setCURRENT_STATUS(0);
	  reCRM.setCO(co.getSVC_UID());
	  /*
	   * Null to next
	   */
	  reCRM.setOP(null);
	  
	  RE_CRMWORKFLOWSTATUS reCRMSTATUS=new RE_CRMWORKFLOWSTATUS();
	  reCRMSTATUS.setAPPROVAL_DATE(Calendar.getInstance());
	  reCRMSTATUS.setSTATUS(1);
	  reCRMSTATUS.setROLEID("FM");
	  reCRMSTATUS.setAPPROVED_DATE(Calendar.getInstance());
	  reCRMSTATUS.setAPPROVEDID(fm.getSVC_UID());
	  reCRMSTATUS.setrE_CRMWORKFLOW(reCRM);
	  
	  RE_CRMWORKFLOWSTATUS reCRMSTATUS_1=new RE_CRMWORKFLOWSTATUS();
	  reCRMSTATUS_1.setAPPROVAL_DATE(Calendar.getInstance());
	  reCRMSTATUS_1.setSTATUS(0);
	  reCRMSTATUS_1.setROLEID("CO");
	  reCRMSTATUS_1.setAPPROVEDID(co.getSVC_UID());
	  reCRMSTATUS_1.setrE_CRMWORKFLOW(reCRM);
	  /*
	   * setting Relation ship
	   */
	  reCRM.getrE_CRMWORKFLOWSTATUS().add(reCRMSTATUS);
	  reCRM.getrE_CRMWORKFLOWSTATUS().add(reCRMSTATUS_1);
	  try {
		 reCRM=rE_CRMWORKFLOWRepository.save(reCRM);
		 if(reCRM!=null) {
			 logger.info("FM approved and CO for Approval saved");
			 json.put("msg", "Saved");
				return new ResponseEntity<>(json,HttpStatus.OK);
		 }
		 else {
			    json.put("msg", "Not saved without cause");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		 }
	  }catch (Exception e) {
		  json.put("msg", e.getMessage());
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
	}
	
	}

	/**
	 * Co approved.
	 *
	 * @param requestData the request data
	 * @return the response entity
	 */
	@PostMapping("/coApproved")
	public ResponseEntity<?> coApproved(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Object CO=requestJson.get("CO");
		Object INVESTOR_CODE=requestJson.get("clientid");
		Object OP=requestJson.get("OP");
		Map<String,Object> json=new HashMap<>();
		RE_CRMWORKFLOW reCRM=null;
	  if(INVESTOR_CODE!=null) {
			if(INVESTOR_CODE.toString().isEmpty()==false) {
				reCRM=rE_CRMWORKFLOWRepository.getExistingInvestor(new Long(INVESTOR_CODE.toString()));
				if(reCRM==null) {
					json.put("msg", "Client Not found");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
			}else {
				json.put("msg", "Please check client ID");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "ClientId is null");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}

	  FUND_USERS co=null;
	  if(CO!=null) {
			if(CO.toString().isEmpty()==false) {
				co=fundUserValidate.isValid(CO.toString());
				if(co==null) {
					json.put("msg", "co Not found");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
				if(!reCRM.getCO().equalsIgnoreCase(co.getSVC_UID())) {
					json.put("msg", "CO is not valid to approved");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
			}else {
				json.put("msg", "Please check CO ID");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "CO is null");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
	 
	  FUND_USERS op=null;
	  if(OP!=null) {
			if(OP.toString().isEmpty()==false) {
				op=fundUserValidate.isValid(OP.toString());
				if(op==null) {
					json.put("msg", "op Not found");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
			}else {
				json.put("msg", "Please check op ID");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "op is null");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
	  
	 /*
	  * 
	  */
	  reCRM.setCURRENT_STATUS(0);
	  reCRM.setOP(op.getSVC_UID());
	  
	  RE_CRMWORKFLOWSTATUS reCRMSTATUS=new RE_CRMWORKFLOWSTATUS();
	  reCRMSTATUS.setAPPROVAL_DATE(Calendar.getInstance());
	  reCRMSTATUS.setSTATUS(1);
	  reCRMSTATUS.setROLEID("CO");
	  reCRMSTATUS.setAPPROVED_DATE(Calendar.getInstance());
	  reCRMSTATUS.setAPPROVEDID(co.getSVC_UID());
	  reCRMSTATUS.setrE_CRMWORKFLOW(reCRM);
	  
	  RE_CRMWORKFLOWSTATUS reCRMSTATUS_1=new RE_CRMWORKFLOWSTATUS();
	  reCRMSTATUS_1.setAPPROVAL_DATE(Calendar.getInstance());
	  reCRMSTATUS_1.setSTATUS(0);
	  reCRMSTATUS_1.setROLEID("OP");
	  reCRMSTATUS_1.setAPPROVEDID(op.getSVC_UID());
	  reCRMSTATUS_1.setrE_CRMWORKFLOW(reCRM);
	  /*
	   * setting Relation ship
	   */
	  reCRM.getrE_CRMWORKFLOWSTATUS().add(reCRMSTATUS);
	  reCRM.getrE_CRMWORKFLOWSTATUS().add(reCRMSTATUS_1);
	  try {
		 reCRM=rE_CRMWORKFLOWRepository.save(reCRM);
		 if(reCRM!=null) {
			 logger.info("CO approved and OP for Approval saved");
			 json.put("msg", "Saved");
				return new ResponseEntity<>(json,HttpStatus.OK);
		 }
		 else {
			    json.put("msg", "Not saved without cause");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		 }
	  }catch (Exception e) {
		  json.put("msg", e.getMessage());
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
	}
	 
	}

	/**
	 * Op approved.
	 *
	 * @param requestData the request data
	 * @return the response entity
	 */
	@PostMapping("/opApproved")
	public ResponseEntity<?> opApproved(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Object INVESTOR_CODE=requestJson.get("clientid");
		Object OP=requestJson.get("OP");
		Map<String,Object> json=new HashMap<>();
		RE_CRMWORKFLOW reCRM=null;
	  if(INVESTOR_CODE!=null) {
			if(INVESTOR_CODE.toString().isEmpty()==false) {
				reCRM=rE_CRMWORKFLOWRepository.getExistingInvestor(new Long(INVESTOR_CODE.toString()));
				if(reCRM==null) {
					json.put("msg", "Client Not found");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
			}else {
				json.put("msg", "Please check client ID");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "ClientId is null");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}

	  FUND_USERS op=null;
	  if(OP!=null) {
			if(OP.toString().isEmpty()==false) {
				op=fundUserValidate.isValid(OP.toString());
				if(op==null) {
					json.put("msg", "op Not found");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
				if(!reCRM.getOP().equalsIgnoreCase(op.getSVC_UID())) {
					json.put("msg", "OP is not valid to approved");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
			}else {
				json.put("msg", "Please check OP ID");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "OP is null");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
	 
	
	 /*
	  * 
	  */
	  reCRM.setCURRENT_STATUS(1);
	  /*
	   * 
	   */
	  RE_CRMWORKFLOWSTATUS reCRMSTATUS=new RE_CRMWORKFLOWSTATUS();
	  reCRMSTATUS.setAPPROVAL_DATE(Calendar.getInstance());
	  reCRMSTATUS.setSTATUS(1);
	  reCRMSTATUS.setROLEID("OP");
	  reCRMSTATUS.setAPPROVED_DATE(Calendar.getInstance());
	  reCRMSTATUS.setAPPROVEDID(op.getSVC_UID());
	  reCRMSTATUS.setrE_CRMWORKFLOW(reCRM);
	  
	 
	  /*
	   * setting Relation ship
	   */
	  reCRM.getrE_CRMWORKFLOWSTATUS().add(reCRMSTATUS);
	  /*
	   * Now Update Parent record and provide investor id
	   */
	  reCRM.getrE_INVESTOR().setIV_LAST_UPDATE_DATE(Calendar.getInstance());
	  reCRM.getrE_INVESTOR().setIV_LAST_UPDATE_UID(op.getSVC_UID());
	  reCRM.getrE_INVESTOR().setRI_INVESTOR_CODE( reCRM.getrE_INVESTOR().getRI_WMS_CODE());
	  try {
		 reCRM=rE_CRMWORKFLOWRepository.save(reCRM);
		 if(reCRM!=null) {
			 logger.info("OP approved saved");
			 
			 json.put("msg", "Saved");
				return new ResponseEntity<>(json,HttpStatus.OK);
		 }
		 else {
			    json.put("msg", "Not saved without cause");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		 }
	  }catch (Exception e) {
		  json.put("msg", e.getMessage());
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
	}
	
	}

	
	
	/**
	 * Reject approved.
	 *
	 * @param requestData the request data
	 * @return the response entity
	 */
	@PostMapping("/rejectApproved")
	public ResponseEntity<?> rejectApproved(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> requestJson=requestData.getBody();
		Object type=requestJson.get("type");
		Object INVESTOR_CODE=requestJson.get("clientid");
		Object OP=requestJson.get("id");
		Map<String,Object> json=new HashMap<>();
		RE_CRMWORKFLOW reCRM=null;
	  if(INVESTOR_CODE!=null) {
			if(INVESTOR_CODE.toString().isEmpty()==false) {
				reCRM=rE_CRMWORKFLOWRepository.getExistingInvestor(new Long(INVESTOR_CODE.toString()));
				if(reCRM==null) {
					json.put("msg", "Client Not found");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
			}else {
				json.put("msg", "Please check client ID");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "ClientId is null");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}

	  FUND_USERS op=null;
	  if(OP!=null) {
			if(OP.toString().isEmpty()==false) {
				op=fundUserValidate.isValid(OP.toString());
				if(op==null) {
					json.put("msg", "ID Not found");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
				
				switch (type.toString()) {
				case "FM":
					if(!reCRM.getFM().equalsIgnoreCase(op.getSVC_UID())) {
						json.put("msg", "FM is not valid to Reject");
						return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
					}
					break;
				case "CO":
					if(!reCRM.getCO().equalsIgnoreCase(op.getSVC_UID())) {
						json.put("msg", "FM is not valid to Reject");
						return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
					}
					break;
				case "OP":
					if(!reCRM.getOP().equalsIgnoreCase(op.getSVC_UID())) {
						json.put("msg", "OP is not valid to Reject");
						return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
					}
					break;
				}
				
				
			}else {
				json.put("msg", "Please check ID");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			json.put("msg", "ID is null");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
	 
	
	 /*
	  * 
	  */
	  reCRM.setCO(null);
	  reCRM.setFM(null);
	  reCRM.setOP(null);
	  reCRM.setCURRENT_STATUS(0);
	  /*
	   * 
	   */
	  RE_CRMWORKFLOWSTATUS reCRMSTATUS=new RE_CRMWORKFLOWSTATUS();
	  reCRMSTATUS.setAPPROVAL_DATE(Calendar.getInstance());
	  reCRMSTATUS.setSTATUS(2);
	  reCRMSTATUS.setROLEID(type.toString());
	  reCRMSTATUS.setAPPROVEDID(op.getSVC_UID());
	  reCRMSTATUS.setrE_CRMWORKFLOW(reCRM);
	  /*
	   * setting Relation ship
	   */
	  reCRM.getrE_CRMWORKFLOWSTATUS().add(reCRMSTATUS);
	
	  try {
		 reCRM=rE_CRMWORKFLOWRepository.save(reCRM);
		 if(reCRM!=null) {
			 logger.info("OP approved saved");
			 
			 json.put("msg", "Saved");
				return new ResponseEntity<>(json,HttpStatus.OK);
		 }
		 else {
			    json.put("msg", "Not saved without cause");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		 }
	  }catch (Exception e) {
		  json.put("msg", e.getMessage());
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
	}
	 
	}

	
	
	
}
