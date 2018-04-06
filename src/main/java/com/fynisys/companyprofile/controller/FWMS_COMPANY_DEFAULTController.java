package com.fynisys.companyprofile.controller;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fynisys.companyprofile.model.FWMS_COMPANY_DEFAULT;
import com.fynisys.companyprofile.repository.FWMS_COMPANY_DEFAULTRepository;
import com.fynisys.model.FUND_USERS;
import com.fynisys.repository.FUND_USERSRepository;


// TODO: Auto-generated Javadoc
/**
 * The Class FWMS_COMPANY_DEFAULTController.
 */
@RestController
public class FWMS_COMPANY_DEFAULTController {

	/** The WM S COMPAN Y DEFAULT repository. */
	@Autowired
	FWMS_COMPANY_DEFAULTRepository fWMS_COMPANY_DEFAULTRepository;
	
	/** The UN D USERS repository. */
	@Autowired
	FUND_USERSRepository fUND_USERSRepository;
	
	/**
	 * Save.
	 *
	 * @param WMS_COMPANY_NAME the wms company name
	 * @param WMS_SCHEMA_NAME the wms schema name
	 * @param WMS_SCHEMA_USER the wms schema user
	 * @param WMS_SCHEMA_PASSWORD the wms schema password
	 * @param WMS_SCHEMA_STRING the wms schema string
	 * @return the response entity
	 * @throws SerialException the serial exception
	 * @throws SQLException the SQL exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@PostMapping("/saveschema")
	public ResponseEntity<?> save(
		@RequestParam("companyname") String WMS_COMPANY_NAME,
		@RequestParam("schema_name")String WMS_SCHEMA_NAME,
		@RequestParam("schema_username")String WMS_SCHEMA_USER,
		@RequestParam("schema_password")String WMS_SCHEMA_PASSWORD,
		@RequestParam("schema_string") String WMS_SCHEMA_STRING
		) throws SerialException, SQLException, IOException{
		
		if(WMS_COMPANY_NAME.isEmpty()==true||WMS_SCHEMA_USER.isEmpty()==true||
				WMS_SCHEMA_NAME.isEmpty()==true||WMS_SCHEMA_PASSWORD.isEmpty()==true||
						WMS_SCHEMA_STRING.isEmpty()==true
				)
		{
			Map<String,Object>json=new HashMap<>();
			json.put("msg", "Param Have null value");
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FWMS_COMPANY_DEFAULT repo=new FWMS_COMPANY_DEFAULT();
	    repo.setWMS_COMPANY_NAME(WMS_COMPANY_NAME);
	    repo.setWMS_SCHEMA_NAME(WMS_SCHEMA_NAME);
	    repo.setWMS_SCHEMA_USER(WMS_SCHEMA_USER);
	    repo.setWMS_SCHEMA_PASSWORD(WMS_SCHEMA_PASSWORD);
	    repo.setWMS_SCHEMA_STRING(WMS_SCHEMA_STRING);
	    
	    try {
	    	repo=fWMS_COMPANY_DEFAULTRepository.save(repo);
	    
	 	    if(repo!=null) {

	 			Map<String,Object>json=new HashMap<>();
	 			json.put("msg", "Saved");
	 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
	 	    }else {

	 			Map<String,Object>json=new HashMap<>();
	 			json.put("msg", "Param Have null value");
	 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
	 	    }
	    }catch (Exception e) {
	    	Map<String,Object>json=new HashMap<>();
 			json.put("msg", e.getMessage());
 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
	   
    }
	
	/**
	 * Download info.
	 *
	 * @param WMS_COMPANY_NAME the wms company name
	 * @param response the response
	 * @return the response entity
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@GetMapping(path = "/schemainfo")
	public ResponseEntity<?> downloadInfo(@RequestParam("companyname") String WMS_COMPANY_NAME,HttpServletResponse response) throws IOException {
		if(WMS_COMPANY_NAME.isEmpty()==false) {
			FWMS_COMPANY_DEFAULT fWMS_REPORT_PARA=fWMS_COMPANY_DEFAULTRepository.getInfo(WMS_COMPANY_NAME);
			if(fWMS_REPORT_PARA==null) {
				Map<String,Object>json=new HashMap<>();
	 			json.put("msg", "No Company Found");
	 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
			}
			Map<String,Object> json=getJson(fWMS_REPORT_PARA);
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
		}else{
			Map<String,Object>json=new HashMap<>();
 			json.put("msg", "Param is empty");
 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Downloadusername.
	 *
	 * @param username the username
	 * @param response the response
	 * @return the response entity
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@GetMapping(path = "/companyinfobyuser")
	public ResponseEntity<?> downloadusername(@RequestParam("username") String username,HttpServletResponse response) throws IOException {
		if(username.isEmpty()) {
			Map<String,Object>json=new HashMap<>();
 			json.put("msg", "No User");
 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		FUND_USERS users=fUND_USERSRepository.findByCompany(username);
		if(users==null) {
			Map<String,Object>json=new HashMap<>();
 			json.put("msg", "No User");
 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
		Long WMS_COMPANY_NAME=users.getFCD_DEFAULT_FCODE();
		if(WMS_COMPANY_NAME!=null) {
			FWMS_COMPANY_DEFAULT fWMS_REPORT_PARA=fWMS_COMPANY_DEFAULTRepository.findOne(WMS_COMPANY_NAME);
			if(fWMS_REPORT_PARA==null) {
				Map<String,Object>json=new HashMap<>();
	 			json.put("msg", "No Company Found");
	 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
			}
			Map<String,Object> json=getJson(fWMS_REPORT_PARA);
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
		}
		
			Map<String,Object>json=new HashMap<>();
 			json.put("msg", "Param is empty");
 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		
	}
	
	/**
	 * Download.
	 *
	 * @param username the username
	 * @param response the response
	 * @return the response entity
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@GetMapping(path = "/downloadlogobyuser")
	public ResponseEntity<?> download(@RequestParam("username") String username,HttpServletResponse response) throws IOException {
		if(username.isEmpty()) {
			Map<String,Object>json=new HashMap<>();
 			json.put("msg", "No User");
 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		FUND_USERS users=fUND_USERSRepository.findByCompany(username);
		if(users==null) {
			Map<String,Object>json=new HashMap<>();
 			json.put("msg", "No User");
 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
		Long WMS_COMPANY_NAME=users.getFCD_DEFAULT_FCODE();
		if(WMS_COMPANY_NAME!=null) {
			FWMS_COMPANY_DEFAULT fWMS_REPORT_PARA=fWMS_COMPANY_DEFAULTRepository.findOne(WMS_COMPANY_NAME);
			if(fWMS_REPORT_PARA==null) {
				Map<String,Object>json=new HashMap<>();
	 			json.put("msg", "No Company Found");
	 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
			}
			response.setContentType("APPLICATION/OCTET-STREAM");   
			response.setHeader("Content-Disposition","attachment; filename=\"" + fWMS_REPORT_PARA.getfWMS_REPORT_PARA().getWMS_IMAGE_NAME() + "\"");   
			response.getOutputStream().write(fWMS_REPORT_PARA.getfWMS_REPORT_PARA().getWMS_IMAGE());
			response.flushBuffer();
			
			Map<String,Object>json=new HashMap<>();
 			json.put("msg", "Send");
 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
			Map<String,Object>json=new HashMap<>();
 			json.put("msg", "Param is empty");
 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		
	}
	
	/**
	 * Gets the json.
	 *
	 * @param fWMS_REPORT_PARA the f WM S REPOR T PARA
	 * @return the json
	 */
	public Map<String,Object> getJson(FWMS_COMPANY_DEFAULT fWMS_REPORT_PARA){
		Map<String,Object> json=new HashMap<>();
		json.put("companyid", fWMS_REPORT_PARA.getFCD_DEFAULT_FCODE());
		json.put("companyname", fWMS_REPORT_PARA.getWMS_COMPANY_NAME());
		json.put("schema_name", fWMS_REPORT_PARA.getWMS_SCHEMA_NAME());
		json.put("schema_user", fWMS_REPORT_PARA.getWMS_SCHEMA_USER());
		json.put("schema_pass", fWMS_REPORT_PARA.getWMS_SCHEMA_PASSWORD());
		json.put("schema_string", fWMS_REPORT_PARA.getWMS_SCHEMA_STRING());
		json.put("line1", fWMS_REPORT_PARA.getfWMS_REPORT_PARA().getWMS_FLINE_1());
		json.put("line2", fWMS_REPORT_PARA.getfWMS_REPORT_PARA().getWMS_FLINE_2());
		json.put("imgname", fWMS_REPORT_PARA.getfWMS_REPORT_PARA().getWMS_IMAGE_NAME());
		json.put("smtp_host",fWMS_REPORT_PARA.getSMTP_SERVER());
		json.put("smtp_port",fWMS_REPORT_PARA.getSMTP_PORT());
		json.put("smtp_email",fWMS_REPORT_PARA.getSMTP_EMAIL_ADDRESS());
		json.put("smtp_name",fWMS_REPORT_PARA.getSMTP_YOUR_NAME());
		json.put("smtp_user",fWMS_REPORT_PARA.getSMTP_USER_NAME());
		json.put("smtp_password",fWMS_REPORT_PARA.getSMTP_PASSWORD());
		return json;
	}
}
