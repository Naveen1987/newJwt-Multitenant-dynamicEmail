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
import org.springframework.web.multipart.MultipartFile;

import com.fynisys.companyprofile.model.FWMS_COMPANY_DEFAULT;
import com.fynisys.companyprofile.model.FWMS_REPORT_PARA;
import com.fynisys.companyprofile.repository.FWMS_COMPANY_DEFAULTRepository;
import com.fynisys.companyprofile.repository.FWMS_REPORT_PARARepository;

// TODO: Auto-generated Javadoc
/**
 * The Class FWMS_REPORT_PARAController.
 */
@RestController
public class FWMS_REPORT_PARAController {
	
	/** The WM S REPOR T PARA repository. */
	@Autowired
	FWMS_REPORT_PARARepository fWMS_REPORT_PARARepository;
	
	/** The WM S COMPAN Y DEFAULT repository. */
	@Autowired
	FWMS_COMPANY_DEFAULTRepository fWMS_COMPANY_DEFAULTRepository;
	
	/**
	 * Save.
	 *
	 * @param WMS_COMPANY_NAME the wms company name
	 * @param WMS_FLINE_1 the wms fline 1
	 * @param WMS_FLINE_2 the wms fline 2
	 * @param WMS_IMAGE_NAME the wms image name
	 * @param WMS_IMAGE the wms image
	 * @param FCD_DEFAULT_FCODE the fcd default fcode
	 * @return the response entity
	 * @throws SerialException the serial exception
	 * @throws SQLException the SQL exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@PostMapping("/savelogo")
	public ResponseEntity<?> save(
		@RequestParam("companyname") String WMS_COMPANY_NAME,
		@RequestParam("line1")String WMS_FLINE_1,
		@RequestParam("line2")String WMS_FLINE_2,
		@RequestParam("imgname")String WMS_IMAGE_NAME,
		@RequestParam("imgfile") MultipartFile WMS_IMAGE,
		@RequestParam("schemaid") Long FCD_DEFAULT_FCODE
		) throws SerialException, SQLException, IOException{
		
		if(WMS_COMPANY_NAME.isEmpty()==true||WMS_FLINE_1.isEmpty()==true||
				WMS_FLINE_2.isEmpty()==true||WMS_IMAGE_NAME.isEmpty()==true||FCD_DEFAULT_FCODE==null)
		{
			Map<String,Object>json=new HashMap<>();
			json.put("msg", "Param Have null value");
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
		if(FCD_DEFAULT_FCODE.toString().isEmpty()==true) {
			Map<String,Object>json=new HashMap<>();
			json.put("msg", "Param Have null value");
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
		
		FWMS_COMPANY_DEFAULT companydef=fWMS_COMPANY_DEFAULTRepository.findOne(new Long(FCD_DEFAULT_FCODE.toString()));
		if(companydef==null) {
			Map<String,Object>json=new HashMap<>();
			json.put("msg", "Not Company");
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
		FWMS_REPORT_PARA repo=new FWMS_REPORT_PARA();
	    repo.setWMS_COMPANY_NAME(WMS_COMPANY_NAME);
	    repo.setWMS_FLINE_1(WMS_FLINE_1);
	    repo.setWMS_FLINE_2(WMS_FLINE_2);
	    repo.setWMS_IMAGE_NAME(WMS_IMAGE_NAME);
	    repo.setfWMS_COMPANY_DEFAULT(companydef);
	    //Blob blob=new javax.sql.rowset.serial.SerialBlob(WMS_IMAGE.getBytes());
	    repo.setWMS_IMAGE(WMS_IMAGE.getBytes());
	    try {
	    	repo=fWMS_REPORT_PARARepository.save(repo);
	    
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
	@GetMapping(path = "/companyinfo")
	public ResponseEntity<?> downloadInfo(@RequestParam("companyname") String WMS_COMPANY_NAME,HttpServletResponse response) throws IOException {
		if(WMS_COMPANY_NAME.isEmpty()==false) {
			FWMS_REPORT_PARA fWMS_REPORT_PARA=fWMS_REPORT_PARARepository.getInfo(WMS_COMPANY_NAME);
			if(fWMS_REPORT_PARA==null) {
				Map<String,Object>json=new HashMap<>();
	 			json.put("msg", "No Company Found");
	 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
			}
			Map<String,Object> json=new HashMap<>();
			json.put("companyname", fWMS_REPORT_PARA.getWMS_COMPANY_NAME());
			json.put("line1", fWMS_REPORT_PARA.getWMS_FLINE_1());
			json.put("line2", fWMS_REPORT_PARA.getWMS_FLINE_2());
			json.put("imgname", fWMS_REPORT_PARA.getWMS_IMAGE_NAME());
			json.put("companyid", fWMS_REPORT_PARA.getfWMS_COMPANY_DEFAULT().getFCD_DEFAULT_FCODE());
			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
		}else{
			Map<String,Object>json=new HashMap<>();
 			json.put("msg", "Param is empty");
 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Download.
	 *
	 * @param WMS_COMPANY_NAME the wms company name
	 * @param response the response
	 * @return the response entity
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@GetMapping(path = "/downloadlogo")
	public ResponseEntity<?> download(@RequestParam("companyname") String WMS_COMPANY_NAME,HttpServletResponse response) throws IOException {
		if(WMS_COMPANY_NAME.isEmpty()==false) {
			FWMS_REPORT_PARA fWMS_REPORT_PARA=fWMS_REPORT_PARARepository.getInfo(WMS_COMPANY_NAME);
			if(fWMS_REPORT_PARA==null) {
				Map<String,Object>json=new HashMap<>();
	 			json.put("msg", "No Company Found");
	 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
			}
			response.setContentType("APPLICATION/OCTET-STREAM");   
			response.setHeader("Content-Disposition","attachment; filename=\"" + fWMS_REPORT_PARA.getWMS_IMAGE_NAME() + "\"");   
			response.getOutputStream().write(fWMS_REPORT_PARA.getWMS_IMAGE());
			response.flushBuffer();
			
			Map<String,Object>json=new HashMap<>();
 			json.put("msg", "Send");
 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}else{
			Map<String,Object>json=new HashMap<>();
 			json.put("msg", "Param is empty");
 			return new ResponseEntity<Map<String,Object>>(json,HttpStatus.BAD_REQUEST);
		}
	}
	
}
