package com.fynisys.controller.people.beans;

import java.util.Calendar;
public class RelationshipManager {
	private Long CODE;                                        
	private Calendar DATE;                                       
	private String NAME;                                         
	private String ANAME;                                         
	private String WBROKER;                                         
	private String EXCHANGE;                                         
	private String CONTACT_PERSON;                                         
	private String PHONE_NO;                                         
	private String FAX_NO;                                         
	private String MOBILE_NO;                                         
	private String EMAIL_ID;                                        
	private String ADDR;                                         
	private String POST_NO;                                         
	private String PC_NO;                                         
	private Long COUNTRYID;
	private String COUNTRYNAME;
	private Long ACCOUNT_CODE;                                         
	private String UTF_AC;                                         
	private String UGF_AC;                                         
	private String NBAD_AC;                                         
	private String NOMINEE_AC;                                         
	private String BANK_ACCOUNT;                                         
	private String BANK_NAME;                                         
	private String DR_CODE;                                         
	private String UID;                                         
	private String SHORT_NAME;                                         
	private String ADDR_STREET;                                         
	private String ADDR_AREA;                                         
	private String ADDR_CITY;                                         
	private String WEBSITE;                                         
	private String P_NAME;                                         
	private String P_DEPT;                                         
	private String P_POSITION;                                         
	private String P_DIR_PHONE;                                         
	private String P_DIR_FAX;                                         
	private String P_DIR_MOBILE;                                         
	private String P_DIR_EMAIL;                                        
	private String S_NAME;                                         
	private String S_DEPT;                                         
	private String S_POSITION;                                         
	private String S_DIR_PHONE;                                         
	private String S_DIR_FAX;                                         
	private String S_DIR_MOBILE;                                         
	private String S_DIR_EMAIL;                                         
	private String T_NAME;                                         
	private String T_DEPT;                                         
	private String T_POSITION;                                         
	private String T_DIR_PHONE;                                         
	private String T_DIR_FAX;                                         
	private String T_DIR_MOBILE;                                         
	private String T_DIR_EMAIL;                                         
	private String MANAGER;                                                                              
	private Calendar STATUS_DATE;                                       
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
		public Long getCODE() {
			return CODE;
		}
		public void setCODE(Long cODE) {
			CODE = cODE;
		}
		public Calendar getDATE() {
			return DATE;
		}
		public void setDATE(Calendar dATE) {
			DATE = dATE;
		}
		public String getNAME() {
			return NAME;
		}
		public void setNAME(String nAME) {
			NAME = nAME;
		}
		public String getANAME() {
			return ANAME;
		}
		public void setANAME(String aNAME) {
			ANAME = aNAME;
		}
		public String getWBROKER() {
			return WBROKER;
		}
		public void setWBROKER(String wBROKER) {
			WBROKER = wBROKER;
		}
		public String getEXCHANGE() {
			return EXCHANGE;
		}
		public void setEXCHANGE(String eXCHANGE) {
			EXCHANGE = eXCHANGE;
		}
		public String getCONTACT_PERSON() {
			return CONTACT_PERSON;
		}
		public void setCONTACT_PERSON(String cONTACT_PERSON) {
			CONTACT_PERSON = cONTACT_PERSON;
		}
		public String getPHONE_NO() {
			return PHONE_NO;
		}
		public void setPHONE_NO(String pHONE_NO) {
			PHONE_NO = pHONE_NO;
		}
		public String getFAX_NO() {
			return FAX_NO;
		}
		public void setFAX_NO(String fAX_NO) {
			FAX_NO = fAX_NO;
		}
		public String getMOBILE_NO() {
			return MOBILE_NO;
		}
		public void setMOBILE_NO(String mOBILE_NO) {
			MOBILE_NO = mOBILE_NO;
		}
		public String getEMAIL_ID() {
			return EMAIL_ID;
		}
		public void setEMAIL_ID(String eMAIL_ID) {
			EMAIL_ID = eMAIL_ID;
		}
		public String getADDR() {
			return ADDR;
		}
		public void setADDR(String aDDR) {
			ADDR = aDDR;
		}
		public String getPOST_NO() {
			return POST_NO;
		}
		public void setPOST_NO(String pOST_NO) {
			POST_NO = pOST_NO;
		}
		public String getPC_NO() {
			return PC_NO;
		}
		public void setPC_NO(String pC_NO) {
			PC_NO = pC_NO;
		}
		public Long getCOUNTRYID() {
			return COUNTRYID;
		}
		public void setCOUNTRYID(Long cOUNTRYID) {
			COUNTRYID = cOUNTRYID;
		}
		public String getCOUNTRYNAME() {
			return COUNTRYNAME;
		}
		public void setCOUNTRYNAME(String cOUNTRYNAME) {
			COUNTRYNAME = cOUNTRYNAME;
		}
		public Long getACCOUNT_CODE() {
			return ACCOUNT_CODE;
		}
		public void setACCOUNT_CODE(Long aCCOUNT_CODE) {
			ACCOUNT_CODE = aCCOUNT_CODE;
		}
		public String getUTF_AC() {
			return UTF_AC;
		}
		public void setUTF_AC(String uTF_AC) {
			UTF_AC = uTF_AC;
		}
		public String getUGF_AC() {
			return UGF_AC;
		}
		public void setUGF_AC(String uGF_AC) {
			UGF_AC = uGF_AC;
		}
		public String getNBAD_AC() {
			return NBAD_AC;
		}
		public void setNBAD_AC(String nBAD_AC) {
			NBAD_AC = nBAD_AC;
		}
		public String getNOMINEE_AC() {
			return NOMINEE_AC;
		}
		public void setNOMINEE_AC(String nOMINEE_AC) {
			NOMINEE_AC = nOMINEE_AC;
		}
		public String getBANK_ACCOUNT() {
			return BANK_ACCOUNT;
		}
		public void setBANK_ACCOUNT(String bANK_ACCOUNT) {
			BANK_ACCOUNT = bANK_ACCOUNT;
		}
		public String getBANK_NAME() {
			return BANK_NAME;
		}
		public void setBANK_NAME(String bANK_NAME) {
			BANK_NAME = bANK_NAME;
		}
		public String getDR_CODE() {
			return DR_CODE;
		}
		public void setDR_CODE(String dR_CODE) {
			DR_CODE = dR_CODE;
		}
		public String getUID() {
			return UID;
		}
		public void setUID(String uID) {
			UID = uID;
		}
		public String getSHORT_NAME() {
			return SHORT_NAME;
		}
		public void setSHORT_NAME(String sHORT_NAME) {
			SHORT_NAME = sHORT_NAME;
		}
		public String getADDR_STREET() {
			return ADDR_STREET;
		}
		public void setADDR_STREET(String aDDR_STREET) {
			ADDR_STREET = aDDR_STREET;
		}
		public String getADDR_AREA() {
			return ADDR_AREA;
		}
		public void setADDR_AREA(String aDDR_AREA) {
			ADDR_AREA = aDDR_AREA;
		}
		public String getADDR_CITY() {
			return ADDR_CITY;
		}
		public void setADDR_CITY(String aDDR_CITY) {
			ADDR_CITY = aDDR_CITY;
		}
		public String getWEBSITE() {
			return WEBSITE;
		}
		public void setWEBSITE(String wEBSITE) {
			WEBSITE = wEBSITE;
		}
		public String getP_NAME() {
			return P_NAME;
		}
		public void setP_NAME(String p_NAME) {
			P_NAME = p_NAME;
		}
		public String getP_DEPT() {
			return P_DEPT;
		}
		public void setP_DEPT(String p_DEPT) {
			P_DEPT = p_DEPT;
		}
		public String getP_POSITION() {
			return P_POSITION;
		}
		public void setP_POSITION(String p_POSITION) {
			P_POSITION = p_POSITION;
		}
		public String getP_DIR_PHONE() {
			return P_DIR_PHONE;
		}
		public void setP_DIR_PHONE(String p_DIR_PHONE) {
			P_DIR_PHONE = p_DIR_PHONE;
		}
		public String getP_DIR_FAX() {
			return P_DIR_FAX;
		}
		public void setP_DIR_FAX(String p_DIR_FAX) {
			P_DIR_FAX = p_DIR_FAX;
		}
		public String getP_DIR_MOBILE() {
			return P_DIR_MOBILE;
		}
		public void setP_DIR_MOBILE(String p_DIR_MOBILE) {
			P_DIR_MOBILE = p_DIR_MOBILE;
		}
		public String getP_DIR_EMAIL() {
			return P_DIR_EMAIL;
		}
		public void setP_DIR_EMAIL(String p_DIR_EMAIL) {
			P_DIR_EMAIL = p_DIR_EMAIL;
		}
		public String getS_NAME() {
			return S_NAME;
		}
		public void setS_NAME(String s_NAME) {
			S_NAME = s_NAME;
		}
		public String getS_DEPT() {
			return S_DEPT;
		}
		public void setS_DEPT(String s_DEPT) {
			S_DEPT = s_DEPT;
		}
		public String getS_POSITION() {
			return S_POSITION;
		}
		public void setS_POSITION(String s_POSITION) {
			S_POSITION = s_POSITION;
		}
		public String getS_DIR_PHONE() {
			return S_DIR_PHONE;
		}
		public void setS_DIR_PHONE(String s_DIR_PHONE) {
			S_DIR_PHONE = s_DIR_PHONE;
		}
		public String getS_DIR_FAX() {
			return S_DIR_FAX;
		}
		public void setS_DIR_FAX(String s_DIR_FAX) {
			S_DIR_FAX = s_DIR_FAX;
		}
		public String getS_DIR_MOBILE() {
			return S_DIR_MOBILE;
		}
		public void setS_DIR_MOBILE(String s_DIR_MOBILE) {
			S_DIR_MOBILE = s_DIR_MOBILE;
		}
		public String getS_DIR_EMAIL() {
			return S_DIR_EMAIL;
		}
		public void setS_DIR_EMAIL(String s_DIR_EMAIL) {
			S_DIR_EMAIL = s_DIR_EMAIL;
		}
		public String getT_NAME() {
			return T_NAME;
		}
		public void setT_NAME(String t_NAME) {
			T_NAME = t_NAME;
		}
		public String getT_DEPT() {
			return T_DEPT;
		}
		public void setT_DEPT(String t_DEPT) {
			T_DEPT = t_DEPT;
		}
		public String getT_POSITION() {
			return T_POSITION;
		}
		public void setT_POSITION(String t_POSITION) {
			T_POSITION = t_POSITION;
		}
		public String getT_DIR_PHONE() {
			return T_DIR_PHONE;
		}
		public void setT_DIR_PHONE(String t_DIR_PHONE) {
			T_DIR_PHONE = t_DIR_PHONE;
		}
		public String getT_DIR_FAX() {
			return T_DIR_FAX;
		}
		public void setT_DIR_FAX(String t_DIR_FAX) {
			T_DIR_FAX = t_DIR_FAX;
		}
		public String getT_DIR_MOBILE() {
			return T_DIR_MOBILE;
		}
		public void setT_DIR_MOBILE(String t_DIR_MOBILE) {
			T_DIR_MOBILE = t_DIR_MOBILE;
		}
		public String getT_DIR_EMAIL() {
			return T_DIR_EMAIL;
		}
		public void setT_DIR_EMAIL(String t_DIR_EMAIL) {
			T_DIR_EMAIL = t_DIR_EMAIL;
		}
		public String getMANAGER() {
			return MANAGER;
		}
		public void setMANAGER(String mANAGER) {
			MANAGER = mANAGER;
		}
		public Calendar getSTATUS_DATE() {
			return STATUS_DATE;
		}
		public void setSTATUS_DATE(Calendar sTATUS_DATE) {
			STATUS_DATE = sTATUS_DATE;
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
	
	}
