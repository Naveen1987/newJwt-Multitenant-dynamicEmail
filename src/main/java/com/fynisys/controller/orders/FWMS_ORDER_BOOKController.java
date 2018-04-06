package com.fynisys.controller.orders;

import java.util.Calendar;
import java.util.HashMap;
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
import com.fynisys.controller.orders.beans.OrderBook;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.crm.RE_INVESTOR;
import com.fynisys.model.funds.FUND_MSTR;
import com.fynisys.model.orders.FWMS_ORDER_BOOK;
import com.fynisys.model.people.FUND_BROKER_MSTR;
import com.fynisys.repository.crm.RE_INVESTORRepository;
import com.fynisys.repository.funds.FUND_MSTRRepository;
import com.fynisys.repository.orders.FWMS_ORDER_BOOKRepository;
import com.fynisys.repository.stock.FUND_SHARE_COMPANY_MSTRRepository;
import com.fynisys.utilities.FundUserValidate;
import com.fynisys.utilities.LevelParameterValidator;

@RestController
public class FWMS_ORDER_BOOKController {

	@Autowired
	FundUserValidate fundUserValidate;
	@Autowired
	ActivityLogger activityLogger;
	@Autowired
	RE_INVESTORRepository rE_INVESTORRepository;
	@Autowired
	LevelParameterValidator levelParameters;
	@Autowired
	FUND_MSTRRepository fUND_MSTRRepository;
	@Autowired
	FUND_SHARE_COMPANY_MSTRRepository fUND_SHARE_COMPANY_MSTRRepository;
	@Autowired
	FWMS_ORDER_BOOKRepository fWMS_ORDER_BOOKRepository;
	
	private static final Logger logger=LoggerFactory.getLogger("FWMS ORDER BOOK CONTROLLER");
	
	@PostMapping("/saveOrder")
	public ResponseEntity<?> saveforfm(RequestEntity<Map<String,Object>> requestData){
		Map<String,Object> json=new HashMap<>();
		Map<String,Object> requestJson=requestData.getBody();
		Object FOB_NO=requestJson.get("FOB_NO");
		Object FOB_DATE=requestJson.get("FOB_DATE");
		Object FOB_TIME=requestJson.get("FOB_TIME");
		Object FOB_B_S=requestJson.get("FOB_B_S");
		Object FOB_L_S=requestJson.get("FOB_L_S");
		Object FOB_SHARE_CLASS=requestJson.get("FOB_SHARE_CLASS");
		Object FOB_FUND=requestJson.get("FOB_FUND");
		Object FOB_BROKER=requestJson.get("FOB_BROKER");
		Object FOB_CLIENT=requestJson.get("FOB_CLIENT");
		Object FOB_STOCK=requestJson.get("FOB_STOCK");
		Object FOB_STOCK_NAME=requestJson.get("FOB_STOCK_NAME");
		Object FOB_TICKER=requestJson.get("FOB_TICKER");
		Object FOB_L_M=requestJson.get("FOB_L_M");
		Object FOB_QTY=requestJson.get("FOB_QTY");
		Object FOB_PRICE=requestJson.get("FOB_PRICE");
		Object FOB_VALUE=requestJson.get("FOB_VALUE");
		Object FOB_MPRICE=requestJson.get("FOB_MPRICE");
		Object FOB_BQTY=requestJson.get("FOB_BQTY");
		Object FOB_BPRICE=requestJson.get("FOB_BPRICE");
		Object FOB_AQTY=requestJson.get("FOB_AQTY");
		Object FOB_APRICE=requestJson.get("FOB_APRICE");
		Object FOB_HIGH=requestJson.get("FOB_HIGH");
		Object FOB_LOW=requestJson.get("FOB_LOW");
		Object FOB_VOLUME=requestJson.get("FOB_VOLUME");
		Object FOB_BROKER_QTY=requestJson.get("FOB_BROKER_QTY");
		Object FOB_BROKER_CASH=requestJson.get("FOB_BROKER_CASH");
		Object FOB_MARGIN_AMT=requestJson.get("FOB_MARGIN_AMT");
		Object FOB_OSTATUS=requestJson.get("FOB_OSTATUS");
		Object FOB_EQTY=requestJson.get("FOB_EQTY");
		Object FOB_EPRICE=requestJson.get("FOB_EPRICE");
		Object FOB_OTYPE=requestJson.get("FOB_OTYPE");
		Object FOB_DGF_TYPE=requestJson.get("FOB_DGF_TYPE");
		Object FOB_SEQNO=requestJson.get("FOB_SEQNO");
		Object FOB_L_SEQNO=requestJson.get("FOB_L_SEQNO");
		Object FOB_EDATE=requestJson.get("FOB_EDATE");
		Object createdby=requestJson.get("createdby");
		//Object WMS_COMMENTS=requestJson.get("comment");
		Object SVL_SCREEN=requestJson.get("screentype");
		Object SVL_DESC=requestJson.get("log");
		Object status=requestJson.get("status");
		Object SNO=requestJson.get("sno");
		if(createdby==null||SVL_SCREEN==null||SVL_DESC==null)
		{
			json.put("msg", "Please check Input json, missing some required attributes");
			logger.error("Please check Input json, missing some required attributes");
			return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		
		FUND_USERS fuser=fundUserValidate.isValid(createdby.toString());
		if(fuser==null)
		{
				json.put("msg", "Createdby is not valid user");
				logger.error("Createdby is not valid user");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
		}
		/*
		 * Work for both save and update
		 */
		FWMS_ORDER_BOOK fob=null;
		if(FOB_NO!=null) {
			if(FOB_NO.toString().isEmpty()==false) {
				fob=fWMS_ORDER_BOOKRepository.findOne(new Long(FOB_NO.toString()));
				fob.setWMS_LAST_UPDATE_UID(fuser.getSVC_UID());
			}else {
				json.put("msg", "Order id id not valid");
				logger.error("Order id id not valid");
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}
		}else {
			fob=new FWMS_ORDER_BOOK();
			fob.setWMS_ENTER_UID(fuser.getSVC_UID());
		}
		
			if(FOB_DATE!=null) {
			if(FOB_DATE.toString().isEmpty()==false) {
				Calendar cl=Calendar.getInstance();
				cl.setTimeInMillis(new Long(FOB_DATE.toString()));
				fob.setFOB_DATE(cl);
			}
			else{
			fob.setFOB_DATE(null);
			}
			}

			if(FOB_TIME!=null) {
			if(FOB_TIME.toString().isEmpty()==false) {
			fob.setFOB_TIME(FOB_TIME.toString());
			}
			else{
			fob.setFOB_TIME(null);
			}
			}

			if(FOB_B_S!=null) {
			if(FOB_B_S.toString().isEmpty()==false) {
			fob.setFOB_B_S(FOB_B_S.toString());
			}
			else{
			fob.setFOB_B_S(null);
			}
			}

			if(FOB_L_S!=null) {
			if(FOB_L_S.toString().isEmpty()==false) {
			fob.setFOB_L_S(FOB_L_S.toString());
			}
			else{
			fob.setFOB_L_S(null);
			}
			}

			if(FOB_SHARE_CLASS!=null) {
			if(FOB_SHARE_CLASS.toString().isEmpty()==false) {
			fob.setFOB_SHARE_CLASS(FOB_SHARE_CLASS.toString());
			}
			else{
			fob.setFOB_SHARE_CLASS(null);
			}
			}

			if(FOB_FUND!=null) {
			if(FOB_FUND.toString().isEmpty()==false) {
			fob.setFOB_FUND(new Long(FOB_FUND.toString()));
			}
			else{
			fob.setFOB_FUND(null);
			}
			}

			if(FOB_BROKER!=null) {
			if(FOB_BROKER.toString().isEmpty()==false) {
			fob.setFOB_BROKER(new Long(FOB_BROKER.toString()));
			}
			else{
			fob.setFOB_BROKER(null);
			}
			}

			if(FOB_CLIENT!=null) {
			if(FOB_CLIENT.toString().isEmpty()==false) {
			fob.setFOB_CLIENT(new Long(FOB_CLIENT.toString()));
			}
			else{
			fob.setFOB_CLIENT(null);
			}
			}

			if(FOB_STOCK!=null) {
			if(FOB_STOCK.toString().isEmpty()==false) {
			fob.setFOB_STOCK(new Long(FOB_STOCK.toString()));
			}
			else{
			fob.setFOB_STOCK(null);
			}
			}

			if(FOB_STOCK_NAME!=null) {
			if(FOB_STOCK_NAME.toString().isEmpty()==false) {
			fob.setFOB_STOCK_NAME(FOB_STOCK_NAME.toString());
			}
			else{
			fob.setFOB_STOCK_NAME(null);
			}
			}

			if(FOB_TICKER!=null) {
			if(FOB_TICKER.toString().isEmpty()==false) {
			fob.setFOB_TICKER(FOB_TICKER.toString());
			}
			else{
			fob.setFOB_TICKER(null);
			}
			}

			if(FOB_L_M!=null) {
			if(FOB_L_M.toString().isEmpty()==false) {
			fob.setFOB_L_M(FOB_L_M.toString());
			}
			else{
			fob.setFOB_L_M(null);
			}
			}

			if(FOB_QTY!=null) {
			if(FOB_QTY.toString().isEmpty()==false) {
			fob.setFOB_QTY(new Double(FOB_QTY.toString()));
			}
			else{
			fob.setFOB_QTY(null);
			}
			}

			if(FOB_PRICE!=null) {
			if(FOB_PRICE.toString().isEmpty()==false) {
			fob.setFOB_PRICE(new Double(FOB_PRICE.toString()));
			}
			else{
			fob.setFOB_PRICE(null);
			}
			}

			if(FOB_VALUE!=null) {
			if(FOB_VALUE.toString().isEmpty()==false) {
			fob.setFOB_VALUE(new Double(FOB_VALUE.toString()));
			}
			else{
			fob.setFOB_VALUE(null);
			}
			}

			if(FOB_MPRICE!=null) {
			if(FOB_MPRICE.toString().isEmpty()==false) {
			fob.setFOB_MPRICE(new Double(FOB_MPRICE.toString()));
			}
			else{
			fob.setFOB_MPRICE(null);
			}
			}

			if(FOB_BQTY!=null) {
			if(FOB_BQTY.toString().isEmpty()==false) {
			fob.setFOB_BQTY(new Double(FOB_BQTY.toString()));
			}
			else{
			fob.setFOB_BQTY(null);
			}
			}

			if(FOB_BPRICE!=null) {
			if(FOB_BPRICE.toString().isEmpty()==false) {
			fob.setFOB_BPRICE(new Double(FOB_BPRICE.toString()));
			}
			else{
			fob.setFOB_BPRICE(null);
			}
			}

			if(FOB_AQTY!=null) {
			if(FOB_AQTY.toString().isEmpty()==false) {
			fob.setFOB_AQTY(new Double(FOB_AQTY.toString()));
			}
			else{
			fob.setFOB_AQTY(null);
			}
			}

			if(FOB_APRICE!=null) {
			if(FOB_APRICE.toString().isEmpty()==false) {
			fob.setFOB_APRICE(new Double(FOB_APRICE.toString()));
			}
			else{
			fob.setFOB_APRICE(null);
			}
			}

			if(FOB_HIGH!=null) {
			if(FOB_HIGH.toString().isEmpty()==false) {
			fob.setFOB_HIGH(new Double(FOB_HIGH.toString()));
			}
			else{
			fob.setFOB_HIGH(null);
			}
			}

			if(FOB_LOW!=null) {
			if(FOB_LOW.toString().isEmpty()==false) {
			fob.setFOB_LOW(new Double(FOB_LOW.toString()));
			}
			else{
			fob.setFOB_LOW(null);
			}
			}

			if(FOB_VOLUME!=null) {
			if(FOB_VOLUME.toString().isEmpty()==false) {
			fob.setFOB_VOLUME(new Double(FOB_VOLUME.toString()));
			}
			else{
			fob.setFOB_VOLUME(null);
			}
			}

			if(FOB_BROKER_QTY!=null) {
			if(FOB_BROKER_QTY.toString().isEmpty()==false) {
			fob.setFOB_BROKER_QTY(new Double(FOB_BROKER_QTY.toString()));
			}
			else{
			fob.setFOB_BROKER_QTY(null);
			}
			}

			if(FOB_BROKER_CASH!=null) {
			if(FOB_BROKER_CASH.toString().isEmpty()==false) {
			fob.setFOB_BROKER_CASH(new Double(FOB_BROKER_CASH.toString()));
			}
			else{
			fob.setFOB_BROKER_CASH(null);
			}
			}

			if(FOB_MARGIN_AMT!=null) {
			if(FOB_MARGIN_AMT.toString().isEmpty()==false) {
			fob.setFOB_MARGIN_AMT(new Double(FOB_MARGIN_AMT.toString()));
			}
			else{
			fob.setFOB_MARGIN_AMT(null);
			}
			}

			if(FOB_OSTATUS!=null) {
			if(FOB_OSTATUS.toString().isEmpty()==false) {
			fob.setFOB_OSTATUS(FOB_OSTATUS.toString());
			}
			else{
			fob.setFOB_OSTATUS(null);
			}
			}

			if(FOB_EQTY!=null) {
			if(FOB_EQTY.toString().isEmpty()==false) {
			fob.setFOB_EQTY(new Double(FOB_EQTY.toString()));
			}
			else{
			fob.setFOB_EQTY(null);
			}
			}

			if(FOB_EPRICE!=null) {
			if(FOB_EPRICE.toString().isEmpty()==false) {
			fob.setFOB_EPRICE(new Double(FOB_EPRICE.toString()));
			}
			else{
			fob.setFOB_EPRICE(null);
			}
			}

			if(FOB_OTYPE!=null) {
			if(FOB_OTYPE.toString().isEmpty()==false) {
			fob.setFOB_OTYPE(new Long(FOB_OTYPE.toString()));
			}
			else{
			fob.setFOB_OTYPE(null);
			}
			}

			if(FOB_DGF_TYPE!=null) {
			if(FOB_DGF_TYPE.toString().isEmpty()==false) {
			fob.setFOB_DGF_TYPE(FOB_DGF_TYPE.toString());
			}
			else{
			fob.setFOB_DGF_TYPE(null);
			}
			}

			if(FOB_SEQNO!=null) {
			if(FOB_SEQNO.toString().isEmpty()==false) {
			fob.setFOB_SEQNO(new Long(FOB_SEQNO.toString()));
			}
			else{
			fob.setFOB_SEQNO(null);
			}
			}

			if(FOB_L_SEQNO!=null) {
			if(FOB_L_SEQNO.toString().isEmpty()==false) {
			fob.setFOB_L_SEQNO(new Long(FOB_L_SEQNO.toString()));
			}
			else{
			fob.setFOB_L_SEQNO(null);
			}
			}

			if(FOB_EDATE!=null) {
			if(FOB_EDATE.toString().isEmpty()==false) {
				Calendar cl=Calendar.getInstance();
				cl.setTimeInMillis(new Long(FOB_EDATE.toString()));
			fob.setFOB_EDATE(cl);
			}
			else{
			fob.setFOB_EDATE(null);
			}
			}
						
			try {
				fob=fWMS_ORDER_BOOKRepository.save(fob);
				if(fob!=null) {
					OrderBook ffb=getJson(fob);
					ffb.setMsg("saved");
					new ResponseEntity<>(ffb,HttpStatus.OK);
				}else {
					json.put("msg", "Not saved without error");
					logger.error("Not saved without error");
					return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
				}
			}catch (Exception e) {
				json.put("msg", e.getMessage());
				logger.error(e.getMessage());
				return new ResponseEntity<Map<String, Object>>(json,HttpStatus.BAD_REQUEST);
			}

		
		return null;
	}
	
	
	
	
	
	@GetMapping("/orders")
	public ResponseEntity<?> getAllOrder(Pageable page,@RequestParam("search")String search){
		
		Page<FWMS_ORDER_BOOK> allorder=null;
		if(search.isEmpty()==false) {
			allorder=fWMS_ORDER_BOOKRepository.findAll(page);
		}else {
			allorder=fWMS_ORDER_BOOKRepository.findAll(page);
		}
		Page<OrderBook> allOrders=allorder.map(new Converter<FWMS_ORDER_BOOK, OrderBook>() {

			@Override
			public OrderBook convert(FWMS_ORDER_BOOK arg0) {
				OrderBook ffb=getJson(arg0);
				return ffb;
			}
			
		});
		
		return new ResponseEntity<>(allOrders,HttpStatus.OK);
	}
	
	public OrderBook getJson(FWMS_ORDER_BOOK conut) {
		OrderBook ffb=new OrderBook();
		ffb.setFOB_NO(conut.getFOB_NO());
		ffb.setFOB_DATE(conut.getFOB_DATE());
		ffb.setFOB_TIME(conut.getFOB_TIME());
		ffb.setFOB_B_S(conut.getFOB_B_S());
		ffb.setFOB_L_S(conut.getFOB_L_S());
		ffb.setFOB_SHARE_CLASS(conut.getFOB_SHARE_CLASS());
		
		if(conut.getFOB_FUND()!=null) {
			FUND_MSTR fund=fUND_MSTRRepository.findOne(conut.getFOB_FUND());
			if(fund!=null) {
				ffb.setFOB_FUND(conut.getFOB_FUND());
				ffb.setFOB_FUND_NAME(fund.getSVC_NAME());
			}
		}
		
		if(conut.getFOB_BROKER()!=null) {
			FUND_BROKER_MSTR broker=levelParameters.getBrokerApproved(conut.getFOB_BROKER());
			if(broker!=null) {
				ffb.setFOB_BROKER(conut.getFOB_BROKER());
				ffb.setFOB_BROKER_NAME(broker.getSVB_NAME());
			}
		}
		
		if(conut.getFOB_CLIENT()!=null) {
			RE_INVESTOR ien=levelParameters.getCLIENTApproved(conut.getFOB_CLIENT());
			if(ien!=null) {
				ffb.setFOB_CLIENT(conut.getFOB_CLIENT());
				ffb.setFOB_CLIENT_NAME(ien.getRI_INVESTOR_NAME());
			}
		}
		
		
		ffb.setFOB_STOCK(conut.getFOB_STOCK());
		ffb.setFOB_STOCK_NAME(conut.getFOB_STOCK_NAME());
		
		ffb.setFOB_TICKER(conut.getFOB_TICKER());
		ffb.setFOB_L_M(conut.getFOB_L_M());
		ffb.setFOB_QTY(conut.getFOB_QTY());
		ffb.setFOB_PRICE(conut.getFOB_PRICE());
		ffb.setFOB_VALUE(conut.getFOB_VALUE());
		ffb.setFOB_MPRICE(conut.getFOB_MPRICE());
		ffb.setFOB_BQTY(conut.getFOB_BQTY());
		ffb.setFOB_BPRICE(conut.getFOB_BPRICE());
		ffb.setFOB_AQTY(conut.getFOB_AQTY());
		ffb.setFOB_APRICE(conut.getFOB_APRICE());
		ffb.setFOB_HIGH(conut.getFOB_HIGH());
		ffb.setFOB_LOW(conut.getFOB_LOW());
		ffb.setFOB_VOLUME(conut.getFOB_VOLUME());
		ffb.setFOB_BROKER_QTY(conut.getFOB_BROKER_QTY());
		ffb.setFOB_BROKER_CASH(conut.getFOB_BROKER_CASH());
		ffb.setFOB_MARGIN_AMT(conut.getFOB_MARGIN_AMT());
		ffb.setFOB_OSTATUS(conut.getFOB_OSTATUS());
		ffb.setFOB_EQTY(conut.getFOB_EQTY());
		ffb.setFOB_EPRICE(conut.getFOB_EPRICE());
		ffb.setFOB_OTYPE(conut.getFOB_OTYPE());
		ffb.setFOB_DGF_TYPE(conut.getFOB_DGF_TYPE());
		ffb.setFOB_SEQNO(conut.getFOB_SEQNO());
		ffb.setFOB_L_SEQNO(conut.getFOB_L_SEQNO());
		ffb.setFOB_EDATE(conut.getFOB_EDATE());
		
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
