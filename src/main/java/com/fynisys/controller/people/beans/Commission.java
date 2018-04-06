package com.fynisys.controller.people.beans;

import java.util.Calendar;

public class Commission implements Comparable<Commission>{
	private Long SNO;                                         
	private Calendar EDATE;                                  
	private String LEAVEL;   
	
	private Long FUNDID;
	private String FUNDNAME;
	
	private Long CLIENT_TYPEID;
	private String CLIENT_TYPENAME;
	
	private Long CLIENT_ID;  
	private String CLIENT_NAME;
	
	private Long BROKERID;
	private String BROKERNAME;
	
	private Long STOCK_EXCHANGE_ID;   
	private String STOCK_EXCHANGE_NAME;
	
	private Long ASSET_ID; 
	private String ASSET_NAME;
	
	private String SHARES_AMOUNT_CAL;                                        
	private String TRAN_PROC_FLAG;                                         
	private Double TRAN_PROC_FEE;                                        
	private Double COMMISSION_FEE;                                        
	private Double MIN_COMMISSION_FEE;                                        
	private Double SERVICE_TAX;                                        
	private Double STT;                                        
	private Double TURNOVER_CGS;                                        
	private Double RMS_CGS;                                        
	private Double SEBI_CGS;                                        
	private Double STRU_CGS;                                        
	private Double STAMP_DUTY;                                        
	private Double VAT;              
	private String comments;
	private String status;
	private String enteredby;
	private String enteredbyuserid;
	private String enteredbyuuid;
	private Calendar entereddate;
	private String approvedby;
	private String approvedbyuserid;
	private String approvedbyuuid;
	private Calendar approveddate;
	private String modifiedby;
	private String modifiedbyuserid;
	private String modifiedbyuuid;
	private Calendar modifieddate;
	private String msg;
	public Long getSNO() {
		return SNO;
	}
	public void setSNO(Long sNO) {
		SNO = sNO;
	}
	public Calendar getEDATE() {
		return EDATE;
	}
	public void setEDATE(Calendar eDATE) {
		EDATE = eDATE;
	}
	public String getLEAVEL() {
		return LEAVEL;
	}
	public void setLEAVEL(String lEAVEL) {
		LEAVEL = lEAVEL;
	}
	public Long getFUNDID() {
		return FUNDID;
	}
	public void setFUNDID(Long fUNDID) {
		FUNDID = fUNDID;
	}
	public String getFUNDNAME() {
		return FUNDNAME;
	}
	public void setFUNDNAME(String fUNDNAME) {
		FUNDNAME = fUNDNAME;
	}
	public Long getCLIENT_TYPEID() {
		return CLIENT_TYPEID;
	}
	public void setCLIENT_TYPEID(Long cLIENT_TYPEID) {
		CLIENT_TYPEID = cLIENT_TYPEID;
	}
	public String getCLIENT_TYPENAME() {
		return CLIENT_TYPENAME;
	}
	public void setCLIENT_TYPENAME(String cLIENT_TYPENAME) {
		CLIENT_TYPENAME = cLIENT_TYPENAME;
	}
	public Long getCLIENT_ID() {
		return CLIENT_ID;
	}
	public void setCLIENT_ID(Long cLIENT_ID) {
		CLIENT_ID = cLIENT_ID;
	}
	public String getCLIENT_NAME() {
		return CLIENT_NAME;
	}
	public void setCLIENT_NAME(String cLIENT_NAME) {
		CLIENT_NAME = cLIENT_NAME;
	}
	public Long getBROKERID() {
		return BROKERID;
	}
	public void setBROKERID(Long bROKERID) {
		BROKERID = bROKERID;
	}
	public String getBROKERNAME() {
		return BROKERNAME;
	}
	public void setBROKERNAME(String bROKERNAME) {
		BROKERNAME = bROKERNAME;
	}
	public Long getSTOCK_EXCHANGE_ID() {
		return STOCK_EXCHANGE_ID;
	}
	public void setSTOCK_EXCHANGE_ID(Long sTOCK_EXCHANGE_ID) {
		STOCK_EXCHANGE_ID = sTOCK_EXCHANGE_ID;
	}
	public String getSTOCK_EXCHANGE_NAME() {
		return STOCK_EXCHANGE_NAME;
	}
	public void setSTOCK_EXCHANGE_NAME(String sTOCK_EXCHANGE_NAME) {
		STOCK_EXCHANGE_NAME = sTOCK_EXCHANGE_NAME;
	}
	public Long getASSET_ID() {
		return ASSET_ID;
	}
	public void setASSET_ID(Long aSSET_ID) {
		ASSET_ID = aSSET_ID;
	}
	public String getASSET_NAME() {
		return ASSET_NAME;
	}
	public void setASSET_NAME(String aSSET_NAME) {
		ASSET_NAME = aSSET_NAME;
	}
	public String getSHARES_AMOUNT_CAL() {
		return SHARES_AMOUNT_CAL;
	}
	public void setSHARES_AMOUNT_CAL(String sHARES_AMOUNT_CAL) {
		SHARES_AMOUNT_CAL = sHARES_AMOUNT_CAL;
	}
	public String getTRAN_PROC_FLAG() {
		return TRAN_PROC_FLAG;
	}
	public void setTRAN_PROC_FLAG(String tRAN_PROC_FLAG) {
		TRAN_PROC_FLAG = tRAN_PROC_FLAG;
	}
	public Double getTRAN_PROC_FEE() {
		return TRAN_PROC_FEE;
	}
	public void setTRAN_PROC_FEE(Double tRAN_PROC_FEE) {
		TRAN_PROC_FEE = tRAN_PROC_FEE;
	}
	public Double getCOMMISSION_FEE() {
		return COMMISSION_FEE;
	}
	public void setCOMMISSION_FEE(Double cOMMISSION_FEE) {
		COMMISSION_FEE = cOMMISSION_FEE;
	}
	public Double getMIN_COMMISSION_FEE() {
		return MIN_COMMISSION_FEE;
	}
	public void setMIN_COMMISSION_FEE(Double mIN_COMMISSION_FEE) {
		MIN_COMMISSION_FEE = mIN_COMMISSION_FEE;
	}
	public Double getSERVICE_TAX() {
		return SERVICE_TAX;
	}
	public void setSERVICE_TAX(Double sERVICE_TAX) {
		SERVICE_TAX = sERVICE_TAX;
	}
	public Double getSTT() {
		return STT;
	}
	public void setSTT(Double sTT) {
		STT = sTT;
	}
	public Double getTURNOVER_CGS() {
		return TURNOVER_CGS;
	}
	public void setTURNOVER_CGS(Double tURNOVER_CGS) {
		TURNOVER_CGS = tURNOVER_CGS;
	}
	public Double getRMS_CGS() {
		return RMS_CGS;
	}
	public void setRMS_CGS(Double rMS_CGS) {
		RMS_CGS = rMS_CGS;
	}
	public Double getSEBI_CGS() {
		return SEBI_CGS;
	}
	public void setSEBI_CGS(Double sEBI_CGS) {
		SEBI_CGS = sEBI_CGS;
	}
	public Double getSTRU_CGS() {
		return STRU_CGS;
	}
	public void setSTRU_CGS(Double sTRU_CGS) {
		STRU_CGS = sTRU_CGS;
	}
	public Double getSTAMP_DUTY() {
		return STAMP_DUTY;
	}
	public void setSTAMP_DUTY(Double sTAMP_DUTY) {
		STAMP_DUTY = sTAMP_DUTY;
	}
	public Double getVAT() {
		return VAT;
	}
	public void setVAT(Double vAT) {
		VAT = vAT;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEnteredby() {
		return enteredby;
	}
	public void setEnteredby(String enteredby) {
		this.enteredby = enteredby;
	}
	public String getEnteredbyuserid() {
		return enteredbyuserid;
	}
	public void setEnteredbyuserid(String enteredbyuserid) {
		this.enteredbyuserid = enteredbyuserid;
	}
	public String getEnteredbyuuid() {
		return enteredbyuuid;
	}
	public void setEnteredbyuuid(String enteredbyuuid) {
		this.enteredbyuuid = enteredbyuuid;
	}
	public Calendar getEntereddate() {
		return entereddate;
	}
	public void setEntereddate(Calendar entereddate) {
		this.entereddate = entereddate;
	}
	public String getApprovedby() {
		return approvedby;
	}
	public void setApprovedby(String approvedby) {
		this.approvedby = approvedby;
	}
	public String getApprovedbyuserid() {
		return approvedbyuserid;
	}
	public void setApprovedbyuserid(String approvedbyuserid) {
		this.approvedbyuserid = approvedbyuserid;
	}
	public String getApprovedbyuuid() {
		return approvedbyuuid;
	}
	public void setApprovedbyuuid(String approvedbyuuid) {
		this.approvedbyuuid = approvedbyuuid;
	}
	public Calendar getApproveddate() {
		return approveddate;
	}
	public void setApproveddate(Calendar approveddate) {
		this.approveddate = approveddate;
	}
	public String getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	public String getModifiedbyuserid() {
		return modifiedbyuserid;
	}
	public void setModifiedbyuserid(String modifiedbyuserid) {
		this.modifiedbyuserid = modifiedbyuserid;
	}
	public String getModifiedbyuuid() {
		return modifiedbyuuid;
	}
	public void setModifiedbyuuid(String modifiedbyuuid) {
		this.modifiedbyuuid = modifiedbyuuid;
	}
	public Calendar getModifieddate() {
		return modifieddate;
	}
	public void setModifieddate(Calendar modifieddate) {
		this.modifieddate = modifieddate;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public int compareTo(Commission o) {
		if(FUNDNAME.compareTo(o.getFUNDNAME())>0) {
			return 1;
		}else if(FUNDNAME.compareTo(o.getFUNDNAME())<0)
		{
			return -1;
		}else {
			return 0;
		}
	}
}
