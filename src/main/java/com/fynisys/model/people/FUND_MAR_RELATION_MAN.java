package com.fynisys.model.people;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
/*
FUND_MAR_RELATION_MAN

 SVB_CODE             NUMBER(15),    		RM CODE
 SVB_NAME             VARCHAR2(50 BYTE),	RM NAME
 SVB_MOBILE_NO        VARCHAR2(20 BYTE),	MOBILE NO
 SVB_EMAIL_ID         VARCHAR2(200 BYTE),	EMAIL ID  ( Some times user may enter more then one email id so you should allow ; (sreenivasu@ciobera.com;vasuas@hotmail.com)
 SVB_COUNTRY          VARCHAR2(20 BYTE),	Country

  SVB_PHONE_NO         VARCHAR2(20 BYTE),	Phone no
  SVB_FAX_NO           VARCHAR2(20 BYTE), 	Fax no
  SVB_ADDR             VARCHAR2(20 BYTE),	Address - size is too short. please suggest the actual size
  SVB_POST_NO          VARCHAR2(20 BYTE),	Post no
  SVB_PC_NO            VARCHAR2(20 BYTE),	Post Code


SVB_STATUS           VARCHAR2(50 BYTE),		Status
WMS_COMMENTS					Comments  ( I have added below Alter command to add this field , Run the script)
 
  IV_ENTER_UID         VARCHAR2(119 BYTE),
  IV_ENTER_DATE        DATE,
  IV_APPROVE_UID       VARCHAR2(119 BYTE),
  IV_APPROVE_DATE      DATE,
  IV_LAST_UPDATE_UID   VARCHAR2(119 BYTE),
  IV_LAST_UPDATE_DATE  DATE
RUN BELOW SCRIPT
ALTER TABLE FUND_MAR_RELATION_MAN ADD WMS_COMMENTS VARCHAR2(300);
 */
@Entity(name="FUND_MAR_RELATION_MAN")
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames="SVB_NAME",name="UK_SVB_NAME_RELATION_MAN")
})
public class FUND_MAR_RELATION_MAN {
	@Id
	@GeneratedValue
	private long SVB_CODE;                                         //NUMBER(15),
	private Calendar SVB_DATE;                                         //DATE,
	@Column(length=50)
	private String SVB_NAME;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_ANAME;                                         //VARCHAR2(50 BYTE),
	@Column(length=15)
	private String SVB_WBROKER;                                         //VARCHAR2(15 BYTE),
	@Column(length=15)
	private String SVB_EXCHANGE;                                         //VARCHAR2(15 BYTE),
	@Column(length=30)
	private String SVB_CONTACT_PERSON;                                         //VARCHAR2(30 BYTE),
	@Column(length=20)
	private String SVB_PHONE_NO;                                         //VARCHAR2(20 BYTE),
	@Column(length=20)
	private String SVB_FAX_NO;                                         //VARCHAR2(20 BYTE),
	@Column(length=20)
	private String SVB_MOBILE_NO;                                         //VARCHAR2(20 BYTE),
	@Column(length=200)
	private String SVB_EMAIL_ID;                                         //VARCHAR2(200 BYTE),
	@Column(length=200)
	private String SVB_ADDR;                                         //VARCHAR2(20 BYTE),
	@Column(length=20)
	private String SVB_POST_NO;                                         //VARCHAR2(20 BYTE),
	@Column(length=20)
	private String SVB_PC_NO;                                         //VARCHAR2(20 BYTE),
	@Column(length=20)
	private long SVB_COUNTRY;                                         //VARCHAR2(20 BYTE),
	@Column(length=15)
	private long SVB_ACCOUNT_CODE;                                         //VARCHAR2(15 BYTE),
	@Column(length=25)
	private String SVB_UTF_AC;                                         //VARCHAR2(25 BYTE),
	@Column(length=25)
	private String SVB_UGF_AC;                                         //VARCHAR2(25 BYTE),
	@Column(length=25)
	private String SVB_NBAD_AC;                                         //VARCHAR2(25 BYTE),
	@Column(length=25)
	private String SVB_NOMINEE_AC;                                         //VARCHAR2(25 BYTE),
	@Column(length=35)
	private String SVB_BANK_ACCOUNT;                                         //VARCHAR2(35 BYTE),
	@Column(length=55)
	private String SVB_BANK_NAME;                                         //VARCHAR2(55 BYTE),
	@Column(length=15)
	private String SVB_DR_CODE;                                         //VARCHAR2(15 BYTE),
	@Column(length=50)
	private String SVC_UID;                                         //VARCHAR2(15 BYTE),
	@Column(length=20)
	private String SVB_SHORT_NAME;                                         //VARCHAR2(20 BYTE),
	@Column(length=30)
	private String SVB_ADDR_STREET;                                         //VARCHAR2(30 BYTE),
	@Column(length=30)
	private String SVB_ADDR_AREA;                                         //VARCHAR2(30 BYTE),
	@Column(length=30)
	private String SVB_ADDR_CITY;                                         //VARCHAR2(30 BYTE),
	@Column(length=70)
	private String SVB_WEBSITE;                                         //VARCHAR2(70 BYTE),
	@Column(length=50)
	private String SVB_P_NAME;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_P_DEPT;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_P_POSITION;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_P_DIR_PHONE;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_P_DIR_FAX;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_P_DIR_MOBILE;                                         //VARCHAR2(50 BYTE),
	@Column(length=200)
	private String SVB_P_DIR_EMAIL;                                         //VARCHAR2(200 BYTE),
	@Column(length=50)
	private String SVB_S_NAME;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_S_DEPT;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_S_POSITION;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_S_DIR_PHONE;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_S_DIR_FAX;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_S_DIR_MOBILE;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_S_DIR_EMAIL;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_T_NAME;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_T_DEPT;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_T_POSITION;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_T_DIR_PHONE;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_T_DIR_FAX;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_T_DIR_MOBILE;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_T_DIR_EMAIL;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_MANAGER;                                         //VARCHAR2(50 BYTE),
	@Column(length=50)
	private String SVB_STATUS;                                         //VARCHAR2(50 BYTE),
	private Calendar SVB_STATUS_DATE;                                         //DATE,
	@Column(length=119)
	private String IV_ENTER_UID;                                         //VARCHAR2(119 BYTE),
	private Calendar IV_ENTER_DATE;                                         //DATE,
	@Column(length=119)
	private String IV_APPROVE_UID;                                         //VARCHAR2(119 BYTE),
	private Calendar  IV_APPROVE_DATE;
	@Column(length=119)
	private String IV_LAST_UPDATE_UID;                                         //VARCHAR2(119 BYTE),
	private Calendar IV_LAST_UPDATE_DATE;                                         //DATE
	@Column(length=300)
	private String WMS_COMMENTS;                                         //VARCHAR2(300)
	public long getSVB_CODE() {
		return SVB_CODE;
	}
	public void setSVB_CODE(long sVB_CODE) {
		SVB_CODE = sVB_CODE;
	}
	public Calendar getSVB_DATE() {
		return SVB_DATE;
	}
	public void setSVB_DATE(Calendar sVB_DATE) {
		SVB_DATE = sVB_DATE;
	}
	public String getSVB_NAME() {
		return SVB_NAME;
	}
	public void setSVB_NAME(String sVB_NAME) {
		SVB_NAME = sVB_NAME;
	}
	public String getSVB_ANAME() {
		return SVB_ANAME;
	}
	public void setSVB_ANAME(String sVB_ANAME) {
		SVB_ANAME = sVB_ANAME;
	}
	public String getSVB_WBROKER() {
		return SVB_WBROKER;
	}
	public void setSVB_WBROKER(String sVB_WBROKER) {
		SVB_WBROKER = sVB_WBROKER;
	}
	public String getSVB_EXCHANGE() {
		return SVB_EXCHANGE;
	}
	public void setSVB_EXCHANGE(String sVB_EXCHANGE) {
		SVB_EXCHANGE = sVB_EXCHANGE;
	}
	public String getSVB_CONTACT_PERSON() {
		return SVB_CONTACT_PERSON;
	}
	public void setSVB_CONTACT_PERSON(String sVB_CONTACT_PERSON) {
		SVB_CONTACT_PERSON = sVB_CONTACT_PERSON;
	}
	public String getSVB_PHONE_NO() {
		return SVB_PHONE_NO;
	}
	public void setSVB_PHONE_NO(String sVB_PHONE_NO) {
		SVB_PHONE_NO = sVB_PHONE_NO;
	}
	public String getSVB_FAX_NO() {
		return SVB_FAX_NO;
	}
	public void setSVB_FAX_NO(String sVB_FAX_NO) {
		SVB_FAX_NO = sVB_FAX_NO;
	}
	public String getSVB_MOBILE_NO() {
		return SVB_MOBILE_NO;
	}
	public void setSVB_MOBILE_NO(String sVB_MOBILE_NO) {
		SVB_MOBILE_NO = sVB_MOBILE_NO;
	}
	public String getSVB_EMAIL_ID() {
		return SVB_EMAIL_ID;
	}
	public void setSVB_EMAIL_ID(String sVB_EMAIL_ID) {
		SVB_EMAIL_ID = sVB_EMAIL_ID;
	}
	public String getSVB_ADDR() {
		return SVB_ADDR;
	}
	public void setSVB_ADDR(String sVB_ADDR) {
		SVB_ADDR = sVB_ADDR;
	}
	public String getSVB_POST_NO() {
		return SVB_POST_NO;
	}
	public void setSVB_POST_NO(String sVB_POST_NO) {
		SVB_POST_NO = sVB_POST_NO;
	}
	public String getSVB_PC_NO() {
		return SVB_PC_NO;
	}
	public void setSVB_PC_NO(String sVB_PC_NO) {
		SVB_PC_NO = sVB_PC_NO;
	}
	public long getSVB_COUNTRY() {
		return SVB_COUNTRY;
	}
	public void setSVB_COUNTRY(long sVB_COUNTRY) {
		SVB_COUNTRY = sVB_COUNTRY;
	}
	public long getSVB_ACCOUNT_CODE() {
		return SVB_ACCOUNT_CODE;
	}
	public void setSVB_ACCOUNT_CODE(long sVB_ACCOUNT_CODE) {
		SVB_ACCOUNT_CODE = sVB_ACCOUNT_CODE;
	}
	public String getSVB_UTF_AC() {
		return SVB_UTF_AC;
	}
	public void setSVB_UTF_AC(String sVB_UTF_AC) {
		SVB_UTF_AC = sVB_UTF_AC;
	}
	public String getSVB_UGF_AC() {
		return SVB_UGF_AC;
	}
	public void setSVB_UGF_AC(String sVB_UGF_AC) {
		SVB_UGF_AC = sVB_UGF_AC;
	}
	public String getSVB_NBAD_AC() {
		return SVB_NBAD_AC;
	}
	public void setSVB_NBAD_AC(String sVB_NBAD_AC) {
		SVB_NBAD_AC = sVB_NBAD_AC;
	}
	public String getSVB_NOMINEE_AC() {
		return SVB_NOMINEE_AC;
	}
	public void setSVB_NOMINEE_AC(String sVB_NOMINEE_AC) {
		SVB_NOMINEE_AC = sVB_NOMINEE_AC;
	}
	public String getSVB_BANK_ACCOUNT() {
		return SVB_BANK_ACCOUNT;
	}
	public void setSVB_BANK_ACCOUNT(String sVB_BANK_ACCOUNT) {
		SVB_BANK_ACCOUNT = sVB_BANK_ACCOUNT;
	}
	public String getSVB_BANK_NAME() {
		return SVB_BANK_NAME;
	}
	public void setSVB_BANK_NAME(String sVB_BANK_NAME) {
		SVB_BANK_NAME = sVB_BANK_NAME;
	}
	public String getSVB_DR_CODE() {
		return SVB_DR_CODE;
	}
	public void setSVB_DR_CODE(String sVB_DR_CODE) {
		SVB_DR_CODE = sVB_DR_CODE;
	}
	public String getSVC_UID() {
		return SVC_UID;
	}
	public void setSVC_UID(String sVC_UID) {
		SVC_UID = sVC_UID;
	}
	public String getSVB_SHORT_NAME() {
		return SVB_SHORT_NAME;
	}
	public void setSVB_SHORT_NAME(String sVB_SHORT_NAME) {
		SVB_SHORT_NAME = sVB_SHORT_NAME;
	}
	public String getSVB_ADDR_STREET() {
		return SVB_ADDR_STREET;
	}
	public void setSVB_ADDR_STREET(String sVB_ADDR_STREET) {
		SVB_ADDR_STREET = sVB_ADDR_STREET;
	}
	public String getSVB_ADDR_AREA() {
		return SVB_ADDR_AREA;
	}
	public void setSVB_ADDR_AREA(String sVB_ADDR_AREA) {
		SVB_ADDR_AREA = sVB_ADDR_AREA;
	}
	public String getSVB_ADDR_CITY() {
		return SVB_ADDR_CITY;
	}
	public void setSVB_ADDR_CITY(String sVB_ADDR_CITY) {
		SVB_ADDR_CITY = sVB_ADDR_CITY;
	}
	public String getSVB_WEBSITE() {
		return SVB_WEBSITE;
	}
	public void setSVB_WEBSITE(String sVB_WEBSITE) {
		SVB_WEBSITE = sVB_WEBSITE;
	}
	public String getSVB_P_NAME() {
		return SVB_P_NAME;
	}
	public void setSVB_P_NAME(String sVB_P_NAME) {
		SVB_P_NAME = sVB_P_NAME;
	}
	public String getSVB_P_DEPT() {
		return SVB_P_DEPT;
	}
	public void setSVB_P_DEPT(String sVB_P_DEPT) {
		SVB_P_DEPT = sVB_P_DEPT;
	}
	public String getSVB_P_POSITION() {
		return SVB_P_POSITION;
	}
	public void setSVB_P_POSITION(String sVB_P_POSITION) {
		SVB_P_POSITION = sVB_P_POSITION;
	}
	public String getSVB_P_DIR_PHONE() {
		return SVB_P_DIR_PHONE;
	}
	public void setSVB_P_DIR_PHONE(String sVB_P_DIR_PHONE) {
		SVB_P_DIR_PHONE = sVB_P_DIR_PHONE;
	}
	public String getSVB_P_DIR_FAX() {
		return SVB_P_DIR_FAX;
	}
	public void setSVB_P_DIR_FAX(String sVB_P_DIR_FAX) {
		SVB_P_DIR_FAX = sVB_P_DIR_FAX;
	}
	public String getSVB_P_DIR_MOBILE() {
		return SVB_P_DIR_MOBILE;
	}
	public void setSVB_P_DIR_MOBILE(String sVB_P_DIR_MOBILE) {
		SVB_P_DIR_MOBILE = sVB_P_DIR_MOBILE;
	}
	public String getSVB_P_DIR_EMAIL() {
		return SVB_P_DIR_EMAIL;
	}
	public void setSVB_P_DIR_EMAIL(String sVB_P_DIR_EMAIL) {
		SVB_P_DIR_EMAIL = sVB_P_DIR_EMAIL;
	}
	public String getSVB_S_NAME() {
		return SVB_S_NAME;
	}
	public void setSVB_S_NAME(String sVB_S_NAME) {
		SVB_S_NAME = sVB_S_NAME;
	}
	public String getSVB_S_DEPT() {
		return SVB_S_DEPT;
	}
	public void setSVB_S_DEPT(String sVB_S_DEPT) {
		SVB_S_DEPT = sVB_S_DEPT;
	}
	public String getSVB_S_POSITION() {
		return SVB_S_POSITION;
	}
	public void setSVB_S_POSITION(String sVB_S_POSITION) {
		SVB_S_POSITION = sVB_S_POSITION;
	}
	public String getSVB_S_DIR_PHONE() {
		return SVB_S_DIR_PHONE;
	}
	public void setSVB_S_DIR_PHONE(String sVB_S_DIR_PHONE) {
		SVB_S_DIR_PHONE = sVB_S_DIR_PHONE;
	}
	public String getSVB_S_DIR_FAX() {
		return SVB_S_DIR_FAX;
	}
	public void setSVB_S_DIR_FAX(String sVB_S_DIR_FAX) {
		SVB_S_DIR_FAX = sVB_S_DIR_FAX;
	}
	public String getSVB_S_DIR_MOBILE() {
		return SVB_S_DIR_MOBILE;
	}
	public void setSVB_S_DIR_MOBILE(String sVB_S_DIR_MOBILE) {
		SVB_S_DIR_MOBILE = sVB_S_DIR_MOBILE;
	}
	public String getSVB_S_DIR_EMAIL() {
		return SVB_S_DIR_EMAIL;
	}
	public void setSVB_S_DIR_EMAIL(String sVB_S_DIR_EMAIL) {
		SVB_S_DIR_EMAIL = sVB_S_DIR_EMAIL;
	}
	public String getSVB_T_NAME() {
		return SVB_T_NAME;
	}
	public void setSVB_T_NAME(String sVB_T_NAME) {
		SVB_T_NAME = sVB_T_NAME;
	}
	public String getSVB_T_DEPT() {
		return SVB_T_DEPT;
	}
	public void setSVB_T_DEPT(String sVB_T_DEPT) {
		SVB_T_DEPT = sVB_T_DEPT;
	}
	public String getSVB_T_POSITION() {
		return SVB_T_POSITION;
	}
	public void setSVB_T_POSITION(String sVB_T_POSITION) {
		SVB_T_POSITION = sVB_T_POSITION;
	}
	public String getSVB_T_DIR_PHONE() {
		return SVB_T_DIR_PHONE;
	}
	public void setSVB_T_DIR_PHONE(String sVB_T_DIR_PHONE) {
		SVB_T_DIR_PHONE = sVB_T_DIR_PHONE;
	}
	public String getSVB_T_DIR_FAX() {
		return SVB_T_DIR_FAX;
	}
	public void setSVB_T_DIR_FAX(String sVB_T_DIR_FAX) {
		SVB_T_DIR_FAX = sVB_T_DIR_FAX;
	}
	public String getSVB_T_DIR_MOBILE() {
		return SVB_T_DIR_MOBILE;
	}
	public void setSVB_T_DIR_MOBILE(String sVB_T_DIR_MOBILE) {
		SVB_T_DIR_MOBILE = sVB_T_DIR_MOBILE;
	}
	public String getSVB_T_DIR_EMAIL() {
		return SVB_T_DIR_EMAIL;
	}
	public void setSVB_T_DIR_EMAIL(String sVB_T_DIR_EMAIL) {
		SVB_T_DIR_EMAIL = sVB_T_DIR_EMAIL;
	}
	public String getSVB_MANAGER() {
		return SVB_MANAGER;
	}
	public void setSVB_MANAGER(String sVB_MANAGER) {
		SVB_MANAGER = sVB_MANAGER;
	}
	public String getSVB_STATUS() {
		return SVB_STATUS;
	}
	public void setSVB_STATUS(String sVB_STATUS) {
		SVB_STATUS = sVB_STATUS;
	}
	public Calendar getSVB_STATUS_DATE() {
		return SVB_STATUS_DATE;
	}
	public void setSVB_STATUS_DATE(Calendar sVB_STATUS_DATE) {
		SVB_STATUS_DATE = sVB_STATUS_DATE;
	}
	public String getIV_ENTER_UID() {
		return IV_ENTER_UID;
	}
	public void setIV_ENTER_UID(String iV_ENTER_UID) {
		IV_ENTER_UID = iV_ENTER_UID;
	}
	public Calendar getIV_ENTER_DATE() {
		return IV_ENTER_DATE;
	}
	public void setIV_ENTER_DATE(Calendar iV_ENTER_DATE) {
		IV_ENTER_DATE = iV_ENTER_DATE;
	}
	public String getIV_APPROVE_UID() {
		return IV_APPROVE_UID;
	}
	public void setIV_APPROVE_UID(String iV_APPROVE_UID) {
		IV_APPROVE_UID = iV_APPROVE_UID;
	}
	public Calendar getIV_APPROVE_DATE() {
		return IV_APPROVE_DATE;
	}
	public void setIV_APPROVE_DATE(Calendar iV_APPROVE_DATE) {
		IV_APPROVE_DATE = iV_APPROVE_DATE;
	}
	public String getIV_LAST_UPDATE_UID() {
		return IV_LAST_UPDATE_UID;
	}
	public void setIV_LAST_UPDATE_UID(String iV_LAST_UPDATE_UID) {
		IV_LAST_UPDATE_UID = iV_LAST_UPDATE_UID;
	}
	public Calendar getIV_LAST_UPDATE_DATE() {
		return IV_LAST_UPDATE_DATE;
	}
	public void setIV_LAST_UPDATE_DATE(Calendar iV_LAST_UPDATE_DATE) {
		IV_LAST_UPDATE_DATE = iV_LAST_UPDATE_DATE;
	}
	public String getWMS_COMMENTS() {
		return WMS_COMMENTS;
	}
	public void setWMS_COMMENTS(String wMS_COMMENTS) {
		WMS_COMMENTS = wMS_COMMENTS;
	}
	
}
