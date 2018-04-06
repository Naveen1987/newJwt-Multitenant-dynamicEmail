package com.fynisys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fynisys.model.FUND_USER_LOG;
import com.fynisys.repository.FUND_USER_LOGRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class FUND_USER_LOGController.
 */
@RestController
public class FUND_USER_LOGController{

	/** The UN D USE R LOG repository. */
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;
	
	/** The logger. */
	private final Logger logger=LoggerFactory.getLogger("FUND USER LOG");
	
	/**
	 * Gets the all logs on screen.
	 *
	 * @param request the request
	 * @return the all logs on screen
	 */
	@PostMapping("/getalllogs")
	public ResponseEntity<Map<String,Object>> getAllLogsOnScreen(RequestEntity<Map<String,Object>> request){
		Map<String,Object> requestData=request.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SVL_SCREEN=requestData.get("screentype");
		if(SVL_SCREEN!=null) {
			json.put("screentype", SVL_SCREEN);
			List<FUND_USER_LOG>logs=fUND_USER_LOGRepository.getLogs(SVL_SCREEN.toString());
			List<Map<String,Object>> jsonArray=new ArrayList<Map<String,Object>>();
			logs.forEach(log->{
				Map<String,Object> jsonLog=new HashMap<>();
				jsonLog.put("userid", log.getSVC_UID());
				jsonLog.put("ttype", log.getSVL_TTYPE());
				jsonLog.put("username", log.getSVL_USERID());
				jsonLog.put("logdesc", log.getSVL_DESC());
				jsonLog.put("logdate", log.getSVL_DATE());
				jsonArray.add(jsonLog);
				
			});
			json.put("logs", jsonArray);
			return new ResponseEntity<>(json, HttpStatus.OK);
		}else {
			logger.error("Input parameters are not in curret format");
			json.put("msg", "Input parameters are not in curret format");
			return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	/**
	 * Gets the all logs.
	 *
	 * @param request the request
	 * @return the all logs
	 */
	@PostMapping("/getalllogsOnCrm")
	public ResponseEntity<Map<String,Object>> getAllLogs(RequestEntity<Map<String,Object>> request){
		Map<String,Object> requestData=request.getBody();
		Map<String,Object> json=new HashMap<>();
		Object SVL_SCREEN=requestData.get("screentype");
		Object SNO=requestData.get("sno");
		if(SVL_SCREEN!=null&&SNO!=null) {
			json.put("screentype", SVL_SCREEN);
			List<FUND_USER_LOG>logs=fUND_USER_LOGRepository.getLogs(SNO.toString(),SVL_SCREEN.toString());
			List<Map<String,Object>> jsonArray=new ArrayList<Map<String,Object>>();
			logs.forEach(log->{
				Map<String,Object> jsonLog=new HashMap<>();
				jsonLog.put("userid", log.getSVC_UID());
				jsonLog.put("ttype", log.getSVL_TTYPE());
				jsonLog.put("username", log.getSVL_USERID());
				jsonLog.put("logdesc", log.getSVL_DESC());
				jsonLog.put("logdate", log.getSVL_DATE());
				jsonArray.add(jsonLog);
				
			});
			json.put("logs", jsonArray);
			return new ResponseEntity<>(json, HttpStatus.OK);
		}else {
			logger.error("Input parameters are not in curret format");
			json.put("msg", "Input parameters are not in curret format");
			return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
		}
		
	}
	
//	@PostMapping("/getlogs")
//	public ResponseEntity<Map<String,Object>> getLogs(RequestEntity<Map<String,Object>> request){
//		Map<String,Object> requestData=request.getBody();
//		Map<String,Object> json=new HashMap<>();
//		Object SVL_SCREEN=requestData.get("screentype");
//		Object SVL_TTY=requestData.get("ttype");
//		if(SVL_SCREEN!=null&&SVL_TTY!=null) {
//			json.put("screentype", SVL_SCREEN);
//			json.put("ttype", SVL_TTY);
//			List<FUND_USER_LOG>logs=fUND_USER_LOGRepository.getLogs(SVL_SCREEN.toString(), SVL_TTY.toString());
//			List<Map<String,Object>> jsonArray=new ArrayList<Map<String,Object>>();
//			logs.forEach(log->{
//				Map<String,Object> jsonLog=new HashMap<>();
//				jsonLog.put("userid", log.getSVC_UID());
//				jsonLog.put("ttype", log.getSVL_TTYPE());
//				jsonLog.put("username", log.getSVL_USERID());
//				jsonLog.put("logdesc", log.getSVL_DESC());
//				jsonLog.put("logdate", log.getSVL_DATE());
//				jsonArray.add(jsonLog);
//				
//			});
//			json.put("logs", jsonArray);
//			return new ResponseEntity<>(json, HttpStatus.OK);
//		}else {
//			logger.error("Input parameters are not in curret format");
//			json.put("msg", "Input parameters are not in curret format");
//			return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
//		}
//		
//	}
}
