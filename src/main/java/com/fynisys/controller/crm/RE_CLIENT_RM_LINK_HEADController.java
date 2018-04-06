package com.fynisys.controller.crm;

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

import com.fynisys.activitylogs.ActivityLogger;
import com.fynisys.controller.crm.beans.RelationLink;
import com.fynisys.controller.crm.beans.RelationShip;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.crm.RE_CLIENT_RM_LINK;
import com.fynisys.model.crm.RE_CLIENT_RM_LINK_HEAD;
import com.fynisys.model.crm.RE_INVESTOR;
import com.fynisys.repository.crm.RE_CLIENT_RM_LINKRepository;
import com.fynisys.repository.crm.RE_CLIENT_RM_LINK_HEADRepository;
import com.fynisys.repository.crm.RE_INVESTORRepository;
import com.fynisys.utilities.FundUserValidate;
import com.fynisys.utilities.LevelParameterValidator;

// TODO: Auto-generated Javadoc
/**
 * The Class RE_CLIENT_RM_LINK_HEADController.
 */
@RestController
public class RE_CLIENT_RM_LINK_HEADController {
	
	/** The fund user validate. */
	@Autowired
	FundUserValidate fundUserValidate;
	
	/** The activity logger. */
	@Autowired
	ActivityLogger activityLogger;
	
	/** The r E INVESTOR repository. */
	@Autowired
	RE_INVESTORRepository rE_INVESTORRepository;
	
	/** The r E CLIEN T R M LIN K HEAD repository. */
	@Autowired
	RE_CLIENT_RM_LINK_HEADRepository rE_CLIENT_RM_LINK_HEADRepository;
	
	/** The r E CLIEN T R M LINK repository. */
	@Autowired
	RE_CLIENT_RM_LINKRepository rE_CLIENT_RM_LINKRepository;
	
	/** The level parameters. */
	@Autowired
	LevelParameterValidator levelParameters;
	
	/**
	 * Save.
	 *
	 * @param requestBody the request body
	 * @return the response entity
	 */
	@PostMapping("/saveInvestorRm")
	public ResponseEntity<?> save(RequestEntity<Map<String,Object>>requestBody){
		Map<String,Object> requestJson=requestBody.getBody();
		Object RCL_NO=requestJson.get("RCL_NO");
		Object RCL_EFECT_DATE=requestJson.get("RCL_EFECT_DATE");
		Object createdby=requestJson.get("createdby");
		Object RCL_CLIENT=requestJson.get("RCL_CLIENT");
		Object WMS_COMMENTS=requestJson.get("WMS_COMMENTS");
		List<Map<String,Object>> rm_links=(List<Map<String, Object>>) requestJson.get("CLIENT_RM_LINK");
		Map<String,Object> json=new HashMap<>();
		if(RCL_EFECT_DATE==null||RCL_CLIENT==null) {
			json.put("msg", "Please check input parameter");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
		RE_INVESTOR inv=null;
		if(RCL_CLIENT.toString().isEmpty()==false) {
		inv=rE_INVESTORRepository.findOne(new Long(RCL_CLIENT.toString()));
		if(inv==null) {
			json.put("msg", "Client not found");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
		}else{
			json.put("msg", "RCL_CLIENT is empty");
			return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
		 FUND_USERS fuser=null;
		 if(createdby.toString().isEmpty()==false) {
			 fuser=fundUserValidate.isValid(createdby.toString());
			 if(fuser==null) {
				    json.put("msg", "Createdby not found");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
			 }
		 }else {
			    json.put("msg", "Createdby is empty");
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		 }
		RE_CLIENT_RM_LINK_HEAD rcrlh=null;
		if(RCL_NO!=null) {
			if(RCL_NO.toString().isEmpty()==false) {
				rcrlh=rE_CLIENT_RM_LINK_HEADRepository.findOne(new Long(RCL_NO.toString()));
				if(rcrlh==null) {
					json.put("msg", "Not RE_CLIENT_RM_LINK_HEAD found");
					return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
				}
			}
		}else{
			rcrlh=new RE_CLIENT_RM_LINK_HEAD();
		}
		/*
		 * Realtion ship
		 */
		if(inv!=null) {
			rcrlh.setrE_INVESTOR(inv);
		}
		
		/*
		 * Createdby
		 */
		rcrlh.setIV_ENTER_UID(fuser.getSVC_UID());
		 if(RCL_EFECT_DATE.toString().isEmpty()==false) {
			 Calendar cl=Calendar.getInstance();
			 cl.setTimeInMillis(new Long(RCL_EFECT_DATE.toString()));
			 rcrlh.setRCL_EFECT_DATE(cl);
		 }else {
			 rcrlh.setRCL_EFECT_DATE(null);
		 }
		 
		 if(WMS_COMMENTS!=null) {
			 if(WMS_COMMENTS.toString().isEmpty()==false) {
				 rcrlh.setWMS_COMMENTS(WMS_COMMENTS.toString());
			 }
			 else {
				 rcrlh.setWMS_COMMENTS(null);
			 }
		 }
		 else {
			 rcrlh.setWMS_COMMENTS(null);
		 }
		
		 List<RE_CLIENT_RM_LINK> links=new ArrayList<>();
		 for(Map<String,Object> link:rm_links) {
			 RE_CLIENT_RM_LINK rcrl=null;
			 Object RCL_RM_CLIENT_ID=link.get("ID");
			 if(RCL_RM_CLIENT_ID!=null) {
				 if(RCL_RM_CLIENT_ID.toString().isEmpty()==false) {
					 rcrl=rE_CLIENT_RM_LINKRepository.findOne(new Long(RCL_RM_CLIENT_ID.toString()));
				 }else {
					 rcrl=new RE_CLIENT_RM_LINK();	 
				 }
			 }else{
				 rcrl=new RE_CLIENT_RM_LINK(); 
			 }
			 if(rcrl!=null) {
				Object RCL_RM_ID=link.get("RCL_RM_ID");
				Object RCL_FLAG=link.get("RCL_FLAG");
				Object RCL_FEE_PER=link.get("RCL_FEE_PER");
				if(RCL_RM_ID!=null) {
					if(RCL_RM_ID.toString().isEmpty()==false) {
						rcrl.setRCL_RM_ID(new Long(RCL_RM_ID.toString()));
					}else {
						rcrl.setRCL_RM_ID(null);
					}
				}else{
					rcrl.setRCL_RM_ID(null);
				}
			 		
			 if(RCL_FLAG!=null) {
					if(RCL_FLAG.toString().isEmpty()==false) {
						rcrl.setRCL_FLAG(RCL_FLAG.toString());
					}else {
						rcrl.setRCL_FLAG(null);
					}
				}else{
					rcrl.setRCL_FLAG(null);
				}
			 
			 if(RCL_FEE_PER!=null) {
					if(RCL_FEE_PER.toString().isEmpty()==false) {
						rcrl.setRCL_FEE_PER(new Double(RCL_FEE_PER.toString()));
					}else {
						rcrl.setRCL_FEE_PER(null);
					}
				}else{
					rcrl.setRCL_FEE_PER(null);
				}
			 /*
			  * Relation
			  */
			 rcrl.setrE_CLIENT_RM_LINK_HEAD(rcrlh);
			 /*
			  * Adding Childs
			  */
			 links.add(rcrl);
			 
			 }
			
		 }
		 /*
		  * Adding 
		  */
		 rcrlh.setrE_CLIENT_RM_LINK(links);
		 try {
			 rcrlh= rE_CLIENT_RM_LINK_HEADRepository.save(rcrlh);
			 if(rcrlh!=null) {
				    json.put("msg","saved");
					return new ResponseEntity<>(json,HttpStatus.OK);
			 }
			 else {
				    json.put("msg","Not saved without cause");
					return new ResponseEntity<>(json,HttpStatus.OK);
			 }
		 }catch (Exception e) {
			    json.put("msg", e.getMessage());
				return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
		}
	
	}
	
	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	@GetMapping("/rmlinks")
	public ResponseEntity<?> getList(){
		List<RelationShip> allList=new ArrayList<>();
		rE_CLIENT_RM_LINK_HEADRepository.findAll().forEach(head->{
			allList.add(getJson(head));
		});
		return new ResponseEntity<>(allList,HttpStatus.OK);
	}
	
	/**
	 * Gets the json.
	 *
	 * @param conut the conut
	 * @return the json
	 */
	public RelationShip getJson(RE_CLIENT_RM_LINK_HEAD conut) {
		RelationShip ffb=new RelationShip();
		ffb.setRCL_NO(conut.getRCL_NO());
		ffb.setRCL_CDATE(conut.getRCL_CDATE());
		ffb.setRCL_EFECT_DATE(conut.getRCL_EFECT_DATE());
		ffb.setWMS_COMMENTS( conut.getWMS_COMMENTS()+"");
		ffb.setWMS_STATUS( conut.getWMS_STATUS()+"");
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
		List<RelationLink> relationlink=new ArrayList<>();
		conut.getrE_CLIENT_RM_LINK().forEach(link->{
			RelationLink rm=new RelationLink();
			rm.setRCL_NO(link.getrE_CLIENT_RM_LINK_HEAD().getRCL_NO());
			rm.setRCL_FLAG(link.getRCL_FLAG());
			rm.setRCL_FEE_PER(link.getRCL_FEE_PER());
			if(link.getRCL_RM_ID()!=null) {
				RE_INVESTOR rv=rE_INVESTORRepository.findOne(link.getRCL_RM_ID());
				if(rv!=null) {
					rm.setRCL_RM_ID(rv.getRI_WMS_CODE());
					rm.setRCL_RM_ID_NAME(rv.getRI_INVESTOR_NAME());
				}
			}
			rm.setRCL_RM_CLIENT_ID(link.getRCL_RM_CLIENT_ID());
			relationlink.add(rm);
		});
		ffb.setRelationlink(relationlink);
		return ffb;
	}
}
