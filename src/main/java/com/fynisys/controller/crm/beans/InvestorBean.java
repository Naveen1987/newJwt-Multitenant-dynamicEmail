package com.fynisys.controller.crm.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
// TODO: Auto-generated Javadoc

/**
 * The Class InvestorBean.
 */
public class InvestorBean implements Comparable<InvestorBean> {
	
	/** The ri wms code. */
	private Long RI_WMS_CODE ;
	
	/** The ri investor code. */
	private Long RI_INVESTOR_CODE ;
	
	/** The ri investor type. */
	private String RI_INVESTOR_TYPE ;
	
	/** The ri create date. */
	private Calendar RI_CREATE_DATE ;
	
	/** The ri investor name. */
	private String RI_INVESTOR_NAME ;
	
	/** The ri address 1. */
	private String RI_ADDRESS_1;
	
	/** The ri post box. */
	private String RI_POST_BOX;
	
	/** The ri post code. */
	private String RI_POST_CODE;
	
	/** The ri city. */
	private String RI_CITY ;
	
	/** The ri country. */
	/*
	 * Country
	 */
	private String RI_COUNTRY ;
	
	/** The ri country name. */
	private String RI_COUNTRY_NAME ;
	
	/** The ri tel no. */
	private String RI_TEL_NO;
	
	/** The ri mobile no. */
	private String RI_MOBILE_NO;
	
	/** The ri fax no. */
	private String RI_FAX_NO;
	
	/** The ri email. */
	private String RI_EMAIL ;
	
	/** The ri company name. */
	private String RI_COMPANY_NAME;
	
	/** The ri company license no. */
	private String RI_COMPANY_LICENSE_NO;
	
	/** The ri expiry date. */
	private Calendar RI_EXPIRY_DATE ;
	
	/** The ri occupation. */
	private String RI_OCCUPATION;
	
	/** The ri nationality. */
	private String RI_NATIONALITY ;
	
	/** The ri dob. */
	private Calendar RI_DOB ;
	
	/** The ri gender. */
	private String RI_GENDER;
	
	/** The ri joint authorisation. */
	private String RI_JOINT_AUTHORISATION ;
	
	/** The ri status. */
	private String RI_STATUS;
	
	/** The svc uid. */
	private String SVC_UID;
	
	/** The ri industry. */
	private String RI_INDUSTRY;
	
	/** The ri remarks. */
	private String RI_REMARKS ;
	
	/** The ri marital status. */
	private String RI_MARITAL_STATUS;
	
	/** The ri marital dependents. */
	private String RI_MARITAL_DEPENDENTS;
	
	/** The ri national idenity. */
	private String RI_NATIONAL_IDENITY;
	
	/** The ri passport iplace. */
	private String RI_PASSPORT_IPLACE ;
	
	/** The ri passport idate. */
	private Calendar RI_PASSPORT_IDATE;
	
	/** The ri passport id. */
	private String RI_PASSPORT_ID ;
	
	/** The ri investor name2. */
	private String RI_INVESTOR_NAME2;
	
	/** The ri company name2. */
	private String RI_COMPANY_NAME2 ;
	
	/** The ri occupation2. */
	private String RI_OCCUPATION2 ;
	
	/** The ri nationality2. */
	private String RI_NATIONALITY2;
	
	/** The ri dob2. */
	private Calendar RI_DOB2;
	
	/** The ri gender2. */
	private String RI_GENDER2 ;
	
	/** The ri marital status2. */
	private String RI_MARITAL_STATUS2 ;
	
	/** The ri marital dependents2. */
	private String RI_MARITAL_DEPENDENTS2 ;
	
	/** The ri passport iplace2. */
	private String RI_PASSPORT_IPLACE2;
	
	/** The ri passport idate2. */
	private Calendar RI_PASSPORT_IDATE2 ;
	
	/** The ri national idenity2. */
	private String RI_NATIONAL_IDENITY2 ;
	
	/** The ri passport id2. */
	private String RI_PASSPORT_ID2;
	
	/** The ri primary contact name. */
	private String RI_PRIMARY_CONTACT_NAME;
	
	/** The ri office apart no. */
	private String RI_OFFICE_APART_NO ;
	
	/** The ri street. */
	private String RI_STREET;
	
	/** The ri area. */
	private String RI_AREA;
	
	/** The ri tel no home. */
	private String RI_TEL_NO_HOME ;
	
	/** The ri primary contact name2. */
	private String RI_PRIMARY_CONTACT_NAME2 ;
	
	/** The ri address 12. */
	private String RI_ADDRESS_12;
	
	/** The ri office apart no2. */
	private String RI_OFFICE_APART_NO2;
	
	/** The ri street2. */
	private String RI_STREET2 ;
	
	/** The ri area2. */
	private String RI_AREA2 ;
	
	/** The ri post box2. */
	private String RI_POST_BOX2 ;
	
	/** The ri city2. */
	private String RI_CITY2 ;
	
	/** The ri country2. */
	/*
	 * Country
	 */
	private String RI_COUNTRY2;
	
	/** The ri country2 name. */
	private String RI_COUNTRY2_NAME;
	
	/** The ri post code2. */
	private String RI_POST_CODE2;
	
	/** The ri tel no2. */
	private String RI_TEL_NO2 ;
	
	/** The ri tel no home2. */
	private String RI_TEL_NO_HOME2;
	
	/** The ri mobile no2. */
	private String RI_MOBILE_NO2;
	
	/** The ri fax no2. */
	private String RI_FAX_NO2 ;
	
	/** The ri email2. */
	private String RI_EMAIL2;
	
	/** The ri expiry date2. */
	private Calendar RI_EXPIRY_DATE2;
	
	/** The ri bank aname. */
	private String RI_BANK_ANAME;
	
	/** The ri bank anumber. */
	private String RI_BANK_ANUMBER;
	
	/** The ri bank bank name. */
	private String RI_BANK_BANK_NAME;
	
	/** The ri bank currency. */
	private String RI_BANK_CURRENCY ;
	
	/** The ri bank currency name. */
	private String RI_BANK_CURRENCY_NAME ;
	
	/** The ri bank branch. */
	private String RI_BANK_BRANCH ;
	
	/** The ri bank country. */
	/*
	 * Country
	 */
	private String RI_BANK_COUNTRY;
	
	/** The ri bank country name. */
	private String RI_BANK_COUNTRY_NAME;
	
	/** The ri bank city. */
	private String RI_BANK_CITY ;
	
	/** The ri noti fax. */
	private String RI_NOTI_FAX;
	
	/** The ri noti sms. */
	private String RI_NOTI_SMS;
	
	/** The ri noti mail. */
	private String RI_NOTI_MAIL ;
	
	/** The ri noti email. */
	private String RI_NOTI_EMAIL;
	
	/** The ri margin type. */
	/*
	 * Client type 
	 */
	private Long RI_MARGIN_TYPE ;
	
	/** The ri margin type name. */
	private String RI_MARGIN_TYPE_NAME ;
	
	/** The ri interest type. */
	private Long RI_INTEREST_TYPE ;
	
	/** The ri relation manager. */
	private Long RI_RELATION_MANAGER;
	
	/** The ri corporate name. */
	private String RI_CORPORATE_NAME;
	
	/** The ri business activity. */
	private String RI_BUSINESS_ACTIVITY ;
	
	/** The ri country esta. */
	/*
	 * County
	 */
	private String RI_COUNTRY_ESTA;
	
	/** The ri countryname. */
	private String RI_COUNTRYNAME;
	
	/** The ri commercial regis no. */
	private String RI_COMMERCIAL_REGIS_NO ;
	
	/** The ri trade license no. */
	private String RI_TRADE_LICENSE_NO;
	
	/** The ri trade license idate. */
	private Calendar RI_TRADE_LICENSE_IDATE ;
	
	/** The ri trade license edate. */
	private Calendar RI_TRADE_LICENSE_EDATE ;
	
	/** The ri authorized name. */
	private String RI_AUTHORIZED_NAME ;
	
	/** The ri capacity. */
	private String RI_CAPACITY;
	
	/** The ri margin client flag. */
	private String RI_MARGIN_CLIENT_FLAG;
	
	/** The ri margin report currency. */
	/*
	 * Currency Name
	 */
	private Long RI_MARGIN_REPORT_CURRENCY;
	
	/** The ri margin report currency name. */
	private String RI_MARGIN_REPORT_CURRENCY_NAME;
	
	/** The ri margin aggdate. */
	private Calendar RI_MARGIN_AGGDATE;
	
	/** The ri relation almal. */
	private String RI_RELATION_ALMAL;
	
	/** The ri reffer by. */
	private String RI_REFFER_BY ;
	
	/** The ri pmr day. */
	private String RI_PMR_DAY ;
	
	/** The ri pmr weekly. */
	private String RI_PMR_WEEKLY;
	
	/** The ri pmr month. */
	private String RI_PMR_MONTH ;
	
	/** The ri market amt. */
	private Double RI_MARKET_AMT;
	
	/** The ri margin sub type. */
	private Long RI_MARGIN_SUB_TYPE ;
	
	/** The ri own or client. */
	private String RI_OWN_OR_CLIENT ;
	
	/** The ri month end rep flag. */
	private String RI_MONTH_END_REP_FLAG;
	
	/** The ri interest post. */
	private String RI_INTEREST_POST ;
	
	/** The ri interest remarks. */
	private String RI_INTEREST_REMARKS;
	
	/** The ri account close. */
	private String RI_ACCOUNT_CLOSE ;
	
	/** The ri account close remarks. */
	private String RI_ACCOUNT_CLOSE_REMARKS ;
	
	/** The ri salvation. */
	private String RI_SALVATION ;
	
	/** The ri first name. */
	private String RI_FIRST_NAME;
	
	/** The ri last name. */
	private String RI_LAST_NAME ;
	
	/** The ri middle name. */
	private String RI_MIDDLE_NAME ;
	
	/** The ri suffix. */
	private String RI_SUFFIX;
	
	/** The ri joint salvation. */
	private String RI_JOINT_SALVATION ;
	
	/** The ri joint first name. */
	private String RI_JOINT_FIRST_NAME;
	
	/** The ri joint last name. */
	private String RI_JOINT_LAST_NAME ;
	
	/** The ri joint middle name. */
	private String RI_JOINT_MIDDLE_NAME ;
	
	/** The ri joint suffix. */
	private String RI_JOINT_SUFFIX;
	
	/** The ri corp cd salvation. */
	private String RI_CORP_CD_SALVATION ;
	
	/** The ri corp cd first name. */
	private String RI_CORP_CD_FIRST_NAME;
	
	/** The ri corp cd last name. */
	private String RI_CORP_CD_LAST_NAME ;
	
	/** The ri corp cd middle name. */
	private String RI_CORP_CD_MIDDLE_NAME ;
	
	/** The ri corp cd suffix. */
	private String RI_CORP_CD_SUFFIX;
	
	/** The ri account close date. */
	private Calendar RI_ACCOUNT_CLOSE_DATE;
	
	
	/** The ri crate. */
	private Double RI_CRATE ;
	
	/** The comments. */
	private String comments;
	
	/** The status. */
	private String status;
	
	/** The enteredby. */
	private String enteredby;
	
	/** The enteredbyuserid. */
	private String enteredbyuserid;
	
	/** The enteredbyuuid. */
	private String enteredbyuuid;
	
	/** The entereddate. */
	private Calendar entereddate;
	
	/** The approvedby. */
	private String approvedby;
	
	/** The approvedbyuserid. */
	private String approvedbyuserid;
	
	/** The approvedbyuuid. */
	private String approvedbyuuid;
	
	/** The approveddate. */
	private Calendar approveddate;
	
	/** The modifiedby. */
	private String modifiedby;
	
	/** The modifiedbyuserid. */
	private String modifiedbyuserid;
	
	/** The modifiedbyuuid. */
	private String modifiedbyuuid;
	
	/** The modifieddate. */
	private Calendar modifieddate;
	
	/** The msg. */
	private String msg;
	
	
	
	/** The ri nationalityname. */
	private String RI_NATIONALITYNAME;
	
	/** The ri nationalityname2. */
	private String RI_NATIONALITYNAME2;
	
	/** The accounts. */
	/*
	 * Account Link info
	 */
	private List<AccountLink> accounts=new ArrayList<>();
	 
 	/** The work flow. */
 	/*
	  * Workflow Info
	  */
	private Workflow workFlow;
	
	/**
	 * Gets the work flow.
	 *
	 * @return the work flow
	 */
	public Workflow getWorkFlow() {
		return workFlow;
	}


	/**
	 * Sets the work flow.
	 *
	 * @param workFlow the new work flow
	 */
	public void setWorkFlow(Workflow workFlow) {
		this.workFlow = workFlow;
	}


	/**
	 * Gets the accounts.
	 *
	 * @return the accounts
	 */
	public List<AccountLink> getAccounts() {
		return accounts;
	}
	
	
	/**
	 * Sets the accounts.
	 *
	 * @param accounts the new accounts
	 */
	public void setAccounts(List<AccountLink> accounts) {
		this.accounts = accounts;
	}
	
	/**
	 * Gets the ri nationalityname2.
	 *
	 * @return the ri nationalityname2
	 */
	public String getRI_NATIONALITYNAME2() {
		return RI_NATIONALITYNAME2;
	}
	
	/**
	 * Sets the ri nationalityname2.
	 *
	 * @param rI_NATIONALITYNAME2 the new ri nationalityname2
	 */
	public void setRI_NATIONALITYNAME2(String rI_NATIONALITYNAME2) {
		RI_NATIONALITYNAME2 = rI_NATIONALITYNAME2;
	}
	
	/**
	 * Gets the ri nationalityname.
	 *
	 * @return the ri nationalityname
	 */
	public String getRI_NATIONALITYNAME() {
		return RI_NATIONALITYNAME;
	}
	
	/**
	 * Sets the ri nationalityname.
	 *
	 * @param rI_NATIONALITYNAME the new ri nationalityname
	 */
	public void setRI_NATIONALITYNAME(String rI_NATIONALITYNAME) {
		RI_NATIONALITYNAME = rI_NATIONALITYNAME;
	}
	
	/**
	 * Gets the ri countryname.
	 *
	 * @return the ri countryname
	 */
	public String getRI_COUNTRYNAME() {
		return RI_COUNTRYNAME;
	}
	
	/**
	 * Sets the ri countryname.
	 *
	 * @param rI_COUNTRYNAME the new ri countryname
	 */
	public void setRI_COUNTRYNAME(String rI_COUNTRYNAME) {
		RI_COUNTRYNAME = rI_COUNTRYNAME;
	}
	
	/**
	 * Gets the ri wms code.
	 *
	 * @return the ri wms code
	 */
	public Long getRI_WMS_CODE() {
		return RI_WMS_CODE;
	}
	
	/**
	 * Sets the ri wms code.
	 *
	 * @param rI_WMS_CODE the new ri wms code
	 */
	public void setRI_WMS_CODE(Long rI_WMS_CODE) {
		RI_WMS_CODE = rI_WMS_CODE;
	}
	
	/**
	 * Gets the ri investor code.
	 *
	 * @return the ri investor code
	 */
	public Long getRI_INVESTOR_CODE() {
		return RI_INVESTOR_CODE;
	}
	
	/**
	 * Sets the ri investor code.
	 *
	 * @param rI_INVESTOR_CODE the new ri investor code
	 */
	public void setRI_INVESTOR_CODE(Long rI_INVESTOR_CODE) {
		RI_INVESTOR_CODE = rI_INVESTOR_CODE;
	}
	
	/**
	 * Gets the ri investor type.
	 *
	 * @return the ri investor type
	 */
	public String getRI_INVESTOR_TYPE() {
		return RI_INVESTOR_TYPE;
	}
	
	/**
	 * Sets the ri investor type.
	 *
	 * @param rI_INVESTOR_TYPE the new ri investor type
	 */
	public void setRI_INVESTOR_TYPE(String rI_INVESTOR_TYPE) {
		RI_INVESTOR_TYPE = rI_INVESTOR_TYPE;
	}
	
	/**
	 * Gets the ri create date.
	 *
	 * @return the ri create date
	 */
	public Calendar getRI_CREATE_DATE() {
		return RI_CREATE_DATE;
	}
	
	/**
	 * Sets the ri create date.
	 *
	 * @param rI_CREATE_DATE the new ri create date
	 */
	public void setRI_CREATE_DATE(Calendar rI_CREATE_DATE) {
		RI_CREATE_DATE = rI_CREATE_DATE;
	}
	
	/**
	 * Gets the ri investor name.
	 *
	 * @return the ri investor name
	 */
	public String getRI_INVESTOR_NAME() {
		return RI_INVESTOR_NAME;
	}
	
	/**
	 * Sets the ri investor name.
	 *
	 * @param rI_INVESTOR_NAME the new ri investor name
	 */
	public void setRI_INVESTOR_NAME(String rI_INVESTOR_NAME) {
		RI_INVESTOR_NAME = rI_INVESTOR_NAME;
	}
	
	/**
	 * Gets the ri address 1.
	 *
	 * @return the ri address 1
	 */
	public String getRI_ADDRESS_1() {
		return RI_ADDRESS_1;
	}
	
	/**
	 * Sets the ri address 1.
	 *
	 * @param rI_ADDRESS_1 the new ri address 1
	 */
	public void setRI_ADDRESS_1(String rI_ADDRESS_1) {
		RI_ADDRESS_1 = rI_ADDRESS_1;
	}
	
	/**
	 * Gets the ri post box.
	 *
	 * @return the ri post box
	 */
	public String getRI_POST_BOX() {
		return RI_POST_BOX;
	}
	
	/**
	 * Sets the ri post box.
	 *
	 * @param rI_POST_BOX the new ri post box
	 */
	public void setRI_POST_BOX(String rI_POST_BOX) {
		RI_POST_BOX = rI_POST_BOX;
	}
	
	/**
	 * Gets the ri post code.
	 *
	 * @return the ri post code
	 */
	public String getRI_POST_CODE() {
		return RI_POST_CODE;
	}
	
	/**
	 * Sets the ri post code.
	 *
	 * @param rI_POST_CODE the new ri post code
	 */
	public void setRI_POST_CODE(String rI_POST_CODE) {
		RI_POST_CODE = rI_POST_CODE;
	}
	
	/**
	 * Gets the ri city.
	 *
	 * @return the ri city
	 */
	public String getRI_CITY() {
		return RI_CITY;
	}
	
	/**
	 * Sets the ri city.
	 *
	 * @param rI_CITY the new ri city
	 */
	public void setRI_CITY(String rI_CITY) {
		RI_CITY = rI_CITY;
	}
	
	/**
	 * Gets the ri country.
	 *
	 * @return the ri country
	 */
	public String getRI_COUNTRY() {
		return RI_COUNTRY;
	}
	
	/**
	 * Sets the ri country.
	 *
	 * @param rI_COUNTRY the new ri country
	 */
	public void setRI_COUNTRY(String rI_COUNTRY) {
		RI_COUNTRY = rI_COUNTRY;
	}
	
	/**
	 * Gets the ri tel no.
	 *
	 * @return the ri tel no
	 */
	public String getRI_TEL_NO() {
		return RI_TEL_NO;
	}
	
	/**
	 * Sets the ri tel no.
	 *
	 * @param rI_TEL_NO the new ri tel no
	 */
	public void setRI_TEL_NO(String rI_TEL_NO) {
		RI_TEL_NO = rI_TEL_NO;
	}
	
	/**
	 * Gets the ri mobile no.
	 *
	 * @return the ri mobile no
	 */
	public String getRI_MOBILE_NO() {
		return RI_MOBILE_NO;
	}
	
	/**
	 * Sets the ri mobile no.
	 *
	 * @param rI_MOBILE_NO the new ri mobile no
	 */
	public void setRI_MOBILE_NO(String rI_MOBILE_NO) {
		RI_MOBILE_NO = rI_MOBILE_NO;
	}
	
	/**
	 * Gets the ri fax no.
	 *
	 * @return the ri fax no
	 */
	public String getRI_FAX_NO() {
		return RI_FAX_NO;
	}
	
	/**
	 * Sets the ri fax no.
	 *
	 * @param rI_FAX_NO the new ri fax no
	 */
	public void setRI_FAX_NO(String rI_FAX_NO) {
		RI_FAX_NO = rI_FAX_NO;
	}
	
	/**
	 * Gets the ri email.
	 *
	 * @return the ri email
	 */
	public String getRI_EMAIL() {
		return RI_EMAIL;
	}
	
	/**
	 * Sets the ri email.
	 *
	 * @param rI_EMAIL the new ri email
	 */
	public void setRI_EMAIL(String rI_EMAIL) {
		RI_EMAIL = rI_EMAIL;
	}
	
	/**
	 * Gets the ri company name.
	 *
	 * @return the ri company name
	 */
	public String getRI_COMPANY_NAME() {
		return RI_COMPANY_NAME;
	}
	
	/**
	 * Sets the ri company name.
	 *
	 * @param rI_COMPANY_NAME the new ri company name
	 */
	public void setRI_COMPANY_NAME(String rI_COMPANY_NAME) {
		RI_COMPANY_NAME = rI_COMPANY_NAME;
	}
	
	/**
	 * Gets the ri company license no.
	 *
	 * @return the ri company license no
	 */
	public String getRI_COMPANY_LICENSE_NO() {
		return RI_COMPANY_LICENSE_NO;
	}
	
	/**
	 * Sets the ri company license no.
	 *
	 * @param rI_COMPANY_LICENSE_NO the new ri company license no
	 */
	public void setRI_COMPANY_LICENSE_NO(String rI_COMPANY_LICENSE_NO) {
		RI_COMPANY_LICENSE_NO = rI_COMPANY_LICENSE_NO;
	}
	
	/**
	 * Gets the ri expiry date.
	 *
	 * @return the ri expiry date
	 */
	public Calendar getRI_EXPIRY_DATE() {
		return RI_EXPIRY_DATE;
	}
	
	/**
	 * Sets the ri expiry date.
	 *
	 * @param rI_EXPIRY_DATE the new ri expiry date
	 */
	public void setRI_EXPIRY_DATE(Calendar rI_EXPIRY_DATE) {
		RI_EXPIRY_DATE = rI_EXPIRY_DATE;
	}
	
	/**
	 * Gets the ri occupation.
	 *
	 * @return the ri occupation
	 */
	public String getRI_OCCUPATION() {
		return RI_OCCUPATION;
	}
	
	/**
	 * Sets the ri occupation.
	 *
	 * @param rI_OCCUPATION the new ri occupation
	 */
	public void setRI_OCCUPATION(String rI_OCCUPATION) {
		RI_OCCUPATION = rI_OCCUPATION;
	}
	
	/**
	 * Gets the ri nationality.
	 *
	 * @return the ri nationality
	 */
	public String getRI_NATIONALITY() {
		return RI_NATIONALITY;
	}
	
	/**
	 * Sets the ri nationality.
	 *
	 * @param rI_NATIONALITY the new ri nationality
	 */
	public void setRI_NATIONALITY(String rI_NATIONALITY) {
		RI_NATIONALITY = rI_NATIONALITY;
	}
	
	/**
	 * Gets the ri dob.
	 *
	 * @return the ri dob
	 */
	public Calendar getRI_DOB() {
		return RI_DOB;
	}
	
	/**
	 * Sets the ri dob.
	 *
	 * @param rI_DOB the new ri dob
	 */
	public void setRI_DOB(Calendar rI_DOB) {
		RI_DOB = rI_DOB;
	}
	
	/**
	 * Gets the ri gender.
	 *
	 * @return the ri gender
	 */
	public String getRI_GENDER() {
		return RI_GENDER;
	}
	
	/**
	 * Sets the ri gender.
	 *
	 * @param rI_GENDER the new ri gender
	 */
	public void setRI_GENDER(String rI_GENDER) {
		RI_GENDER = rI_GENDER;
	}
	
	/**
	 * Gets the ri joint authorisation.
	 *
	 * @return the ri joint authorisation
	 */
	public String getRI_JOINT_AUTHORISATION() {
		return RI_JOINT_AUTHORISATION;
	}
	
	/**
	 * Sets the ri joint authorisation.
	 *
	 * @param rI_JOINT_AUTHORISATION the new ri joint authorisation
	 */
	public void setRI_JOINT_AUTHORISATION(String rI_JOINT_AUTHORISATION) {
		RI_JOINT_AUTHORISATION = rI_JOINT_AUTHORISATION;
	}
	
	/**
	 * Gets the ri status.
	 *
	 * @return the ri status
	 */
	public String getRI_STATUS() {
		return RI_STATUS;
	}
	
	/**
	 * Sets the ri status.
	 *
	 * @param rI_STATUS the new ri status
	 */
	public void setRI_STATUS(String rI_STATUS) {
		RI_STATUS = rI_STATUS;
	}
	
	/**
	 * Gets the svc uid.
	 *
	 * @return the svc uid
	 */
	public String getSVC_UID() {
		return SVC_UID;
	}
	
	/**
	 * Sets the svc uid.
	 *
	 * @param sVC_UID the new svc uid
	 */
	public void setSVC_UID(String sVC_UID) {
		SVC_UID = sVC_UID;
	}
	
	/**
	 * Gets the ri industry.
	 *
	 * @return the ri industry
	 */
	public String getRI_INDUSTRY() {
		return RI_INDUSTRY;
	}
	
	/**
	 * Sets the ri industry.
	 *
	 * @param rI_INDUSTRY the new ri industry
	 */
	public void setRI_INDUSTRY(String rI_INDUSTRY) {
		RI_INDUSTRY = rI_INDUSTRY;
	}
	
	/**
	 * Gets the ri remarks.
	 *
	 * @return the ri remarks
	 */
	public String getRI_REMARKS() {
		return RI_REMARKS;
	}
	
	/**
	 * Sets the ri remarks.
	 *
	 * @param rI_REMARKS the new ri remarks
	 */
	public void setRI_REMARKS(String rI_REMARKS) {
		RI_REMARKS = rI_REMARKS;
	}
	
	/**
	 * Gets the ri marital status.
	 *
	 * @return the ri marital status
	 */
	public String getRI_MARITAL_STATUS() {
		return RI_MARITAL_STATUS;
	}
	
	/**
	 * Sets the ri marital status.
	 *
	 * @param rI_MARITAL_STATUS the new ri marital status
	 */
	public void setRI_MARITAL_STATUS(String rI_MARITAL_STATUS) {
		RI_MARITAL_STATUS = rI_MARITAL_STATUS;
	}
	
	/**
	 * Gets the ri marital dependents.
	 *
	 * @return the ri marital dependents
	 */
	public String getRI_MARITAL_DEPENDENTS() {
		return RI_MARITAL_DEPENDENTS;
	}
	
	/**
	 * Sets the ri marital dependents.
	 *
	 * @param rI_MARITAL_DEPENDENTS the new ri marital dependents
	 */
	public void setRI_MARITAL_DEPENDENTS(String rI_MARITAL_DEPENDENTS) {
		RI_MARITAL_DEPENDENTS = rI_MARITAL_DEPENDENTS;
	}
	
	/**
	 * Gets the ri national idenity.
	 *
	 * @return the ri national idenity
	 */
	public String getRI_NATIONAL_IDENITY() {
		return RI_NATIONAL_IDENITY;
	}
	
	/**
	 * Sets the ri national idenity.
	 *
	 * @param rI_NATIONAL_IDENITY the new ri national idenity
	 */
	public void setRI_NATIONAL_IDENITY(String rI_NATIONAL_IDENITY) {
		RI_NATIONAL_IDENITY = rI_NATIONAL_IDENITY;
	}
	
	/**
	 * Gets the ri passport iplace.
	 *
	 * @return the ri passport iplace
	 */
	public String getRI_PASSPORT_IPLACE() {
		return RI_PASSPORT_IPLACE;
	}
	
	/**
	 * Sets the ri passport iplace.
	 *
	 * @param rI_PASSPORT_IPLACE the new ri passport iplace
	 */
	public void setRI_PASSPORT_IPLACE(String rI_PASSPORT_IPLACE) {
		RI_PASSPORT_IPLACE = rI_PASSPORT_IPLACE;
	}
	
	/**
	 * Gets the ri passport idate.
	 *
	 * @return the ri passport idate
	 */
	public Calendar getRI_PASSPORT_IDATE() {
		return RI_PASSPORT_IDATE;
	}
	
	/**
	 * Sets the ri passport idate.
	 *
	 * @param rI_PASSPORT_IDATE the new ri passport idate
	 */
	public void setRI_PASSPORT_IDATE(Calendar rI_PASSPORT_IDATE) {
		RI_PASSPORT_IDATE = rI_PASSPORT_IDATE;
	}
	
	/**
	 * Gets the ri passport id.
	 *
	 * @return the ri passport id
	 */
	public String getRI_PASSPORT_ID() {
		return RI_PASSPORT_ID;
	}
	
	/**
	 * Sets the ri passport id.
	 *
	 * @param rI_PASSPORT_ID the new ri passport id
	 */
	public void setRI_PASSPORT_ID(String rI_PASSPORT_ID) {
		RI_PASSPORT_ID = rI_PASSPORT_ID;
	}
	
	/**
	 * Gets the ri investor name2.
	 *
	 * @return the ri investor name2
	 */
	public String getRI_INVESTOR_NAME2() {
		return RI_INVESTOR_NAME2;
	}
	
	/**
	 * Sets the ri investor name2.
	 *
	 * @param rI_INVESTOR_NAME2 the new ri investor name2
	 */
	public void setRI_INVESTOR_NAME2(String rI_INVESTOR_NAME2) {
		RI_INVESTOR_NAME2 = rI_INVESTOR_NAME2;
	}
	
	/**
	 * Gets the ri company name2.
	 *
	 * @return the ri company name2
	 */
	public String getRI_COMPANY_NAME2() {
		return RI_COMPANY_NAME2;
	}
	
	/**
	 * Sets the ri company name2.
	 *
	 * @param rI_COMPANY_NAME2 the new ri company name2
	 */
	public void setRI_COMPANY_NAME2(String rI_COMPANY_NAME2) {
		RI_COMPANY_NAME2 = rI_COMPANY_NAME2;
	}
	
	/**
	 * Gets the ri occupation2.
	 *
	 * @return the ri occupation2
	 */
	public String getRI_OCCUPATION2() {
		return RI_OCCUPATION2;
	}
	
	/**
	 * Sets the ri occupation2.
	 *
	 * @param rI_OCCUPATION2 the new ri occupation2
	 */
	public void setRI_OCCUPATION2(String rI_OCCUPATION2) {
		RI_OCCUPATION2 = rI_OCCUPATION2;
	}
	
	/**
	 * Gets the ri nationality2.
	 *
	 * @return the ri nationality2
	 */
	public String getRI_NATIONALITY2() {
		return RI_NATIONALITY2;
	}
	
	/**
	 * Sets the ri nationality2.
	 *
	 * @param rI_NATIONALITY2 the new ri nationality2
	 */
	public void setRI_NATIONALITY2(String rI_NATIONALITY2) {
		RI_NATIONALITY2 = rI_NATIONALITY2;
	}
	
	/**
	 * Gets the ri dob2.
	 *
	 * @return the ri dob2
	 */
	public Calendar getRI_DOB2() {
		return RI_DOB2;
	}
	
	/**
	 * Sets the ri dob2.
	 *
	 * @param rI_DOB2 the new ri dob2
	 */
	public void setRI_DOB2(Calendar rI_DOB2) {
		RI_DOB2 = rI_DOB2;
	}
	
	/**
	 * Gets the ri gender2.
	 *
	 * @return the ri gender2
	 */
	public String getRI_GENDER2() {
		return RI_GENDER2;
	}
	
	/**
	 * Sets the ri gender2.
	 *
	 * @param rI_GENDER2 the new ri gender2
	 */
	public void setRI_GENDER2(String rI_GENDER2) {
		RI_GENDER2 = rI_GENDER2;
	}
	
	/**
	 * Gets the ri marital status2.
	 *
	 * @return the ri marital status2
	 */
	public String getRI_MARITAL_STATUS2() {
		return RI_MARITAL_STATUS2;
	}
	
	/**
	 * Sets the ri marital status2.
	 *
	 * @param rI_MARITAL_STATUS2 the new ri marital status2
	 */
	public void setRI_MARITAL_STATUS2(String rI_MARITAL_STATUS2) {
		RI_MARITAL_STATUS2 = rI_MARITAL_STATUS2;
	}
	
	/**
	 * Gets the ri marital dependents2.
	 *
	 * @return the ri marital dependents2
	 */
	public String getRI_MARITAL_DEPENDENTS2() {
		return RI_MARITAL_DEPENDENTS2;
	}
	
	/**
	 * Sets the ri marital dependents2.
	 *
	 * @param rI_MARITAL_DEPENDENTS2 the new ri marital dependents2
	 */
	public void setRI_MARITAL_DEPENDENTS2(String rI_MARITAL_DEPENDENTS2) {
		RI_MARITAL_DEPENDENTS2 = rI_MARITAL_DEPENDENTS2;
	}
	
	/**
	 * Gets the ri passport iplace2.
	 *
	 * @return the ri passport iplace2
	 */
	public String getRI_PASSPORT_IPLACE2() {
		return RI_PASSPORT_IPLACE2;
	}
	
	/**
	 * Sets the ri passport iplace2.
	 *
	 * @param rI_PASSPORT_IPLACE2 the new ri passport iplace2
	 */
	public void setRI_PASSPORT_IPLACE2(String rI_PASSPORT_IPLACE2) {
		RI_PASSPORT_IPLACE2 = rI_PASSPORT_IPLACE2;
	}
	
	/**
	 * Gets the ri passport idate2.
	 *
	 * @return the ri passport idate2
	 */
	public Calendar getRI_PASSPORT_IDATE2() {
		return RI_PASSPORT_IDATE2;
	}
	
	/**
	 * Sets the ri passport idate2.
	 *
	 * @param rI_PASSPORT_IDATE2 the new ri passport idate2
	 */
	public void setRI_PASSPORT_IDATE2(Calendar rI_PASSPORT_IDATE2) {
		RI_PASSPORT_IDATE2 = rI_PASSPORT_IDATE2;
	}
	
	/**
	 * Gets the ri national idenity2.
	 *
	 * @return the ri national idenity2
	 */
	public String getRI_NATIONAL_IDENITY2() {
		return RI_NATIONAL_IDENITY2;
	}
	
	/**
	 * Sets the ri national idenity2.
	 *
	 * @param rI_NATIONAL_IDENITY2 the new ri national idenity2
	 */
	public void setRI_NATIONAL_IDENITY2(String rI_NATIONAL_IDENITY2) {
		RI_NATIONAL_IDENITY2 = rI_NATIONAL_IDENITY2;
	}
	
	/**
	 * Gets the ri passport id2.
	 *
	 * @return the ri passport id2
	 */
	public String getRI_PASSPORT_ID2() {
		return RI_PASSPORT_ID2;
	}
	
	/**
	 * Sets the ri passport id2.
	 *
	 * @param rI_PASSPORT_ID2 the new ri passport id2
	 */
	public void setRI_PASSPORT_ID2(String rI_PASSPORT_ID2) {
		RI_PASSPORT_ID2 = rI_PASSPORT_ID2;
	}
	
	/**
	 * Gets the ri primary contact name.
	 *
	 * @return the ri primary contact name
	 */
	public String getRI_PRIMARY_CONTACT_NAME() {
		return RI_PRIMARY_CONTACT_NAME;
	}
	
	/**
	 * Sets the ri primary contact name.
	 *
	 * @param rI_PRIMARY_CONTACT_NAME the new ri primary contact name
	 */
	public void setRI_PRIMARY_CONTACT_NAME(String rI_PRIMARY_CONTACT_NAME) {
		RI_PRIMARY_CONTACT_NAME = rI_PRIMARY_CONTACT_NAME;
	}
	
	/**
	 * Gets the ri office apart no.
	 *
	 * @return the ri office apart no
	 */
	public String getRI_OFFICE_APART_NO() {
		return RI_OFFICE_APART_NO;
	}
	
	/**
	 * Sets the ri office apart no.
	 *
	 * @param rI_OFFICE_APART_NO the new ri office apart no
	 */
	public void setRI_OFFICE_APART_NO(String rI_OFFICE_APART_NO) {
		RI_OFFICE_APART_NO = rI_OFFICE_APART_NO;
	}
	
	/**
	 * Gets the ri street.
	 *
	 * @return the ri street
	 */
	public String getRI_STREET() {
		return RI_STREET;
	}
	
	/**
	 * Sets the ri street.
	 *
	 * @param rI_STREET the new ri street
	 */
	public void setRI_STREET(String rI_STREET) {
		RI_STREET = rI_STREET;
	}
	
	/**
	 * Gets the ri area.
	 *
	 * @return the ri area
	 */
	public String getRI_AREA() {
		return RI_AREA;
	}
	
	/**
	 * Sets the ri area.
	 *
	 * @param rI_AREA the new ri area
	 */
	public void setRI_AREA(String rI_AREA) {
		RI_AREA = rI_AREA;
	}
	
	/**
	 * Gets the ri tel no home.
	 *
	 * @return the ri tel no home
	 */
	public String getRI_TEL_NO_HOME() {
		return RI_TEL_NO_HOME;
	}
	
	/**
	 * Sets the ri tel no home.
	 *
	 * @param rI_TEL_NO_HOME the new ri tel no home
	 */
	public void setRI_TEL_NO_HOME(String rI_TEL_NO_HOME) {
		RI_TEL_NO_HOME = rI_TEL_NO_HOME;
	}
	
	/**
	 * Gets the ri primary contact name2.
	 *
	 * @return the ri primary contact name2
	 */
	public String getRI_PRIMARY_CONTACT_NAME2() {
		return RI_PRIMARY_CONTACT_NAME2;
	}
	
	/**
	 * Sets the ri primary contact name2.
	 *
	 * @param rI_PRIMARY_CONTACT_NAME2 the new ri primary contact name2
	 */
	public void setRI_PRIMARY_CONTACT_NAME2(String rI_PRIMARY_CONTACT_NAME2) {
		RI_PRIMARY_CONTACT_NAME2 = rI_PRIMARY_CONTACT_NAME2;
	}
	
	/**
	 * Gets the ri address 12.
	 *
	 * @return the ri address 12
	 */
	public String getRI_ADDRESS_12() {
		return RI_ADDRESS_12;
	}
	
	/**
	 * Sets the ri address 12.
	 *
	 * @param rI_ADDRESS_12 the new ri address 12
	 */
	public void setRI_ADDRESS_12(String rI_ADDRESS_12) {
		RI_ADDRESS_12 = rI_ADDRESS_12;
	}
	
	/**
	 * Gets the ri office apart no2.
	 *
	 * @return the ri office apart no2
	 */
	public String getRI_OFFICE_APART_NO2() {
		return RI_OFFICE_APART_NO2;
	}
	
	/**
	 * Sets the ri office apart no2.
	 *
	 * @param rI_OFFICE_APART_NO2 the new ri office apart no2
	 */
	public void setRI_OFFICE_APART_NO2(String rI_OFFICE_APART_NO2) {
		RI_OFFICE_APART_NO2 = rI_OFFICE_APART_NO2;
	}
	
	/**
	 * Gets the ri street2.
	 *
	 * @return the ri street2
	 */
	public String getRI_STREET2() {
		return RI_STREET2;
	}
	
	/**
	 * Sets the ri street2.
	 *
	 * @param rI_STREET2 the new ri street2
	 */
	public void setRI_STREET2(String rI_STREET2) {
		RI_STREET2 = rI_STREET2;
	}
	
	/**
	 * Gets the ri area2.
	 *
	 * @return the ri area2
	 */
	public String getRI_AREA2() {
		return RI_AREA2;
	}
	
	/**
	 * Sets the ri area2.
	 *
	 * @param rI_AREA2 the new ri area2
	 */
	public void setRI_AREA2(String rI_AREA2) {
		RI_AREA2 = rI_AREA2;
	}
	
	/**
	 * Gets the ri post box2.
	 *
	 * @return the ri post box2
	 */
	public String getRI_POST_BOX2() {
		return RI_POST_BOX2;
	}
	
	/**
	 * Sets the ri post box2.
	 *
	 * @param rI_POST_BOX2 the new ri post box2
	 */
	public void setRI_POST_BOX2(String rI_POST_BOX2) {
		RI_POST_BOX2 = rI_POST_BOX2;
	}
	
	/**
	 * Gets the ri city2.
	 *
	 * @return the ri city2
	 */
	public String getRI_CITY2() {
		return RI_CITY2;
	}
	
	/**
	 * Sets the ri city2.
	 *
	 * @param rI_CITY2 the new ri city2
	 */
	public void setRI_CITY2(String rI_CITY2) {
		RI_CITY2 = rI_CITY2;
	}
	
	/**
	 * Gets the ri country2.
	 *
	 * @return the ri country2
	 */
	public String getRI_COUNTRY2() {
		return RI_COUNTRY2;
	}
	
	/**
	 * Sets the ri country2.
	 *
	 * @param rI_COUNTRY2 the new ri country2
	 */
	public void setRI_COUNTRY2(String rI_COUNTRY2) {
		RI_COUNTRY2 = rI_COUNTRY2;
	}
	
	/**
	 * Gets the ri post code2.
	 *
	 * @return the ri post code2
	 */
	public String getRI_POST_CODE2() {
		return RI_POST_CODE2;
	}
	
	/**
	 * Sets the ri post code2.
	 *
	 * @param rI_POST_CODE2 the new ri post code2
	 */
	public void setRI_POST_CODE2(String rI_POST_CODE2) {
		RI_POST_CODE2 = rI_POST_CODE2;
	}
	
	/**
	 * Gets the ri tel no2.
	 *
	 * @return the ri tel no2
	 */
	public String getRI_TEL_NO2() {
		return RI_TEL_NO2;
	}
	
	/**
	 * Sets the ri tel no2.
	 *
	 * @param rI_TEL_NO2 the new ri tel no2
	 */
	public void setRI_TEL_NO2(String rI_TEL_NO2) {
		RI_TEL_NO2 = rI_TEL_NO2;
	}
	
	/**
	 * Gets the ri tel no home2.
	 *
	 * @return the ri tel no home2
	 */
	public String getRI_TEL_NO_HOME2() {
		return RI_TEL_NO_HOME2;
	}
	
	/**
	 * Sets the ri tel no home2.
	 *
	 * @param rI_TEL_NO_HOME2 the new ri tel no home2
	 */
	public void setRI_TEL_NO_HOME2(String rI_TEL_NO_HOME2) {
		RI_TEL_NO_HOME2 = rI_TEL_NO_HOME2;
	}
	
	/**
	 * Gets the ri mobile no2.
	 *
	 * @return the ri mobile no2
	 */
	public String getRI_MOBILE_NO2() {
		return RI_MOBILE_NO2;
	}
	
	/**
	 * Sets the ri mobile no2.
	 *
	 * @param rI_MOBILE_NO2 the new ri mobile no2
	 */
	public void setRI_MOBILE_NO2(String rI_MOBILE_NO2) {
		RI_MOBILE_NO2 = rI_MOBILE_NO2;
	}
	
	/**
	 * Gets the ri fax no2.
	 *
	 * @return the ri fax no2
	 */
	public String getRI_FAX_NO2() {
		return RI_FAX_NO2;
	}
	
	/**
	 * Sets the ri fax no2.
	 *
	 * @param rI_FAX_NO2 the new ri fax no2
	 */
	public void setRI_FAX_NO2(String rI_FAX_NO2) {
		RI_FAX_NO2 = rI_FAX_NO2;
	}
	
	/**
	 * Gets the ri email2.
	 *
	 * @return the ri email2
	 */
	public String getRI_EMAIL2() {
		return RI_EMAIL2;
	}
	
	/**
	 * Sets the ri email2.
	 *
	 * @param rI_EMAIL2 the new ri email2
	 */
	public void setRI_EMAIL2(String rI_EMAIL2) {
		RI_EMAIL2 = rI_EMAIL2;
	}
	
	/**
	 * Gets the ri expiry date2.
	 *
	 * @return the ri expiry date2
	 */
	public Calendar getRI_EXPIRY_DATE2() {
		return RI_EXPIRY_DATE2;
	}
	
	/**
	 * Sets the ri expiry date2.
	 *
	 * @param rI_EXPIRY_DATE2 the new ri expiry date2
	 */
	public void setRI_EXPIRY_DATE2(Calendar rI_EXPIRY_DATE2) {
		RI_EXPIRY_DATE2 = rI_EXPIRY_DATE2;
	}
	
	/**
	 * Gets the ri bank aname.
	 *
	 * @return the ri bank aname
	 */
	public String getRI_BANK_ANAME() {
		return RI_BANK_ANAME;
	}
	
	/**
	 * Sets the ri bank aname.
	 *
	 * @param rI_BANK_ANAME the new ri bank aname
	 */
	public void setRI_BANK_ANAME(String rI_BANK_ANAME) {
		RI_BANK_ANAME = rI_BANK_ANAME;
	}
	
	/**
	 * Gets the ri bank anumber.
	 *
	 * @return the ri bank anumber
	 */
	public String getRI_BANK_ANUMBER() {
		return RI_BANK_ANUMBER;
	}
	
	/**
	 * Sets the ri bank anumber.
	 *
	 * @param rI_BANK_ANUMBER the new ri bank anumber
	 */
	public void setRI_BANK_ANUMBER(String rI_BANK_ANUMBER) {
		RI_BANK_ANUMBER = rI_BANK_ANUMBER;
	}
	
	/**
	 * Gets the ri bank bank name.
	 *
	 * @return the ri bank bank name
	 */
	public String getRI_BANK_BANK_NAME() {
		return RI_BANK_BANK_NAME;
	}
	
	/**
	 * Sets the ri bank bank name.
	 *
	 * @param rI_BANK_BANK_NAME the new ri bank bank name
	 */
	public void setRI_BANK_BANK_NAME(String rI_BANK_BANK_NAME) {
		RI_BANK_BANK_NAME = rI_BANK_BANK_NAME;
	}
	
	/**
	 * Gets the ri bank currency.
	 *
	 * @return the ri bank currency
	 */
	public String getRI_BANK_CURRENCY() {
		return RI_BANK_CURRENCY;
	}
	
	/**
	 * Sets the ri bank currency.
	 *
	 * @param rI_BANK_CURRENCY the new ri bank currency
	 */
	public void setRI_BANK_CURRENCY(String rI_BANK_CURRENCY) {
		RI_BANK_CURRENCY = rI_BANK_CURRENCY;
	}
	
	/**
	 * Gets the ri bank branch.
	 *
	 * @return the ri bank branch
	 */
	public String getRI_BANK_BRANCH() {
		return RI_BANK_BRANCH;
	}
	
	/**
	 * Sets the ri bank branch.
	 *
	 * @param rI_BANK_BRANCH the new ri bank branch
	 */
	public void setRI_BANK_BRANCH(String rI_BANK_BRANCH) {
		RI_BANK_BRANCH = rI_BANK_BRANCH;
	}
	
	/**
	 * Gets the ri bank country.
	 *
	 * @return the ri bank country
	 */
	public String getRI_BANK_COUNTRY() {
		return RI_BANK_COUNTRY;
	}
	
	/**
	 * Sets the ri bank country.
	 *
	 * @param rI_BANK_COUNTRY the new ri bank country
	 */
	public void setRI_BANK_COUNTRY(String rI_BANK_COUNTRY) {
		RI_BANK_COUNTRY = rI_BANK_COUNTRY;
	}
	
	/**
	 * Gets the ri bank city.
	 *
	 * @return the ri bank city
	 */
	public String getRI_BANK_CITY() {
		return RI_BANK_CITY;
	}
	
	/**
	 * Sets the ri bank city.
	 *
	 * @param rI_BANK_CITY the new ri bank city
	 */
	public void setRI_BANK_CITY(String rI_BANK_CITY) {
		RI_BANK_CITY = rI_BANK_CITY;
	}
	
	/**
	 * Gets the ri noti fax.
	 *
	 * @return the ri noti fax
	 */
	public String getRI_NOTI_FAX() {
		return RI_NOTI_FAX;
	}
	
	/**
	 * Sets the ri noti fax.
	 *
	 * @param rI_NOTI_FAX the new ri noti fax
	 */
	public void setRI_NOTI_FAX(String rI_NOTI_FAX) {
		RI_NOTI_FAX = rI_NOTI_FAX;
	}
	
	/**
	 * Gets the ri noti sms.
	 *
	 * @return the ri noti sms
	 */
	public String getRI_NOTI_SMS() {
		return RI_NOTI_SMS;
	}
	
	/**
	 * Sets the ri noti sms.
	 *
	 * @param rI_NOTI_SMS the new ri noti sms
	 */
	public void setRI_NOTI_SMS(String rI_NOTI_SMS) {
		RI_NOTI_SMS = rI_NOTI_SMS;
	}
	
	/**
	 * Gets the ri noti mail.
	 *
	 * @return the ri noti mail
	 */
	public String getRI_NOTI_MAIL() {
		return RI_NOTI_MAIL;
	}
	
	/**
	 * Sets the ri noti mail.
	 *
	 * @param rI_NOTI_MAIL the new ri noti mail
	 */
	public void setRI_NOTI_MAIL(String rI_NOTI_MAIL) {
		RI_NOTI_MAIL = rI_NOTI_MAIL;
	}
	
	/**
	 * Gets the ri noti email.
	 *
	 * @return the ri noti email
	 */
	public String getRI_NOTI_EMAIL() {
		return RI_NOTI_EMAIL;
	}
	
	/**
	 * Sets the ri noti email.
	 *
	 * @param rI_NOTI_EMAIL the new ri noti email
	 */
	public void setRI_NOTI_EMAIL(String rI_NOTI_EMAIL) {
		RI_NOTI_EMAIL = rI_NOTI_EMAIL;
	}
	
	/**
	 * Gets the ri margin type.
	 *
	 * @return the ri margin type
	 */
	public Long getRI_MARGIN_TYPE() {
		return RI_MARGIN_TYPE;
	}
	
	/**
	 * Sets the ri margin type.
	 *
	 * @param rI_MARGIN_TYPE the new ri margin type
	 */
	public void setRI_MARGIN_TYPE(Long rI_MARGIN_TYPE) {
		RI_MARGIN_TYPE = rI_MARGIN_TYPE;
	}
	
	/**
	 * Gets the ri interest type.
	 *
	 * @return the ri interest type
	 */
	public Long getRI_INTEREST_TYPE() {
		return RI_INTEREST_TYPE;
	}
	
	/**
	 * Sets the ri interest type.
	 *
	 * @param rI_INTEREST_TYPE the new ri interest type
	 */
	public void setRI_INTEREST_TYPE(Long rI_INTEREST_TYPE) {
		RI_INTEREST_TYPE = rI_INTEREST_TYPE;
	}
	
	/**
	 * Gets the ri relation manager.
	 *
	 * @return the ri relation manager
	 */
	public Long getRI_RELATION_MANAGER() {
		return RI_RELATION_MANAGER;
	}
	
	/**
	 * Sets the ri relation manager.
	 *
	 * @param rI_RELATION_MANAGER the new ri relation manager
	 */
	public void setRI_RELATION_MANAGER(Long rI_RELATION_MANAGER) {
		RI_RELATION_MANAGER = rI_RELATION_MANAGER;
	}
	
	/**
	 * Gets the ri corporate name.
	 *
	 * @return the ri corporate name
	 */
	public String getRI_CORPORATE_NAME() {
		return RI_CORPORATE_NAME;
	}
	
	/**
	 * Sets the ri corporate name.
	 *
	 * @param rI_CORPORATE_NAME the new ri corporate name
	 */
	public void setRI_CORPORATE_NAME(String rI_CORPORATE_NAME) {
		RI_CORPORATE_NAME = rI_CORPORATE_NAME;
	}
	
	/**
	 * Gets the ri business activity.
	 *
	 * @return the ri business activity
	 */
	public String getRI_BUSINESS_ACTIVITY() {
		return RI_BUSINESS_ACTIVITY;
	}
	
	/**
	 * Sets the ri business activity.
	 *
	 * @param rI_BUSINESS_ACTIVITY the new ri business activity
	 */
	public void setRI_BUSINESS_ACTIVITY(String rI_BUSINESS_ACTIVITY) {
		RI_BUSINESS_ACTIVITY = rI_BUSINESS_ACTIVITY;
	}
	
	/**
	 * Gets the ri country esta.
	 *
	 * @return the ri country esta
	 */
	public String getRI_COUNTRY_ESTA() {
		return RI_COUNTRY_ESTA;
	}
	
	/**
	 * Sets the ri country esta.
	 *
	 * @param rI_COUNTRY_ESTA the new ri country esta
	 */
	public void setRI_COUNTRY_ESTA(String rI_COUNTRY_ESTA) {
		RI_COUNTRY_ESTA = rI_COUNTRY_ESTA;
	}
	
	/**
	 * Gets the ri commercial regis no.
	 *
	 * @return the ri commercial regis no
	 */
	public String getRI_COMMERCIAL_REGIS_NO() {
		return RI_COMMERCIAL_REGIS_NO;
	}
	
	/**
	 * Sets the ri commercial regis no.
	 *
	 * @param rI_COMMERCIAL_REGIS_NO the new ri commercial regis no
	 */
	public void setRI_COMMERCIAL_REGIS_NO(String rI_COMMERCIAL_REGIS_NO) {
		RI_COMMERCIAL_REGIS_NO = rI_COMMERCIAL_REGIS_NO;
	}
	
	/**
	 * Gets the ri trade license no.
	 *
	 * @return the ri trade license no
	 */
	public String getRI_TRADE_LICENSE_NO() {
		return RI_TRADE_LICENSE_NO;
	}
	
	/**
	 * Sets the ri trade license no.
	 *
	 * @param rI_TRADE_LICENSE_NO the new ri trade license no
	 */
	public void setRI_TRADE_LICENSE_NO(String rI_TRADE_LICENSE_NO) {
		RI_TRADE_LICENSE_NO = rI_TRADE_LICENSE_NO;
	}
	
	/**
	 * Gets the ri trade license idate.
	 *
	 * @return the ri trade license idate
	 */
	public Calendar getRI_TRADE_LICENSE_IDATE() {
		return RI_TRADE_LICENSE_IDATE;
	}
	
	/**
	 * Sets the ri trade license idate.
	 *
	 * @param rI_TRADE_LICENSE_IDATE the new ri trade license idate
	 */
	public void setRI_TRADE_LICENSE_IDATE(Calendar rI_TRADE_LICENSE_IDATE) {
		RI_TRADE_LICENSE_IDATE = rI_TRADE_LICENSE_IDATE;
	}
	
	/**
	 * Gets the ri trade license edate.
	 *
	 * @return the ri trade license edate
	 */
	public Calendar getRI_TRADE_LICENSE_EDATE() {
		return RI_TRADE_LICENSE_EDATE;
	}
	
	/**
	 * Sets the ri trade license edate.
	 *
	 * @param rI_TRADE_LICENSE_EDATE the new ri trade license edate
	 */
	public void setRI_TRADE_LICENSE_EDATE(Calendar rI_TRADE_LICENSE_EDATE) {
		RI_TRADE_LICENSE_EDATE = rI_TRADE_LICENSE_EDATE;
	}
	
	/**
	 * Gets the ri authorized name.
	 *
	 * @return the ri authorized name
	 */
	public String getRI_AUTHORIZED_NAME() {
		return RI_AUTHORIZED_NAME;
	}
	
	/**
	 * Sets the ri authorized name.
	 *
	 * @param rI_AUTHORIZED_NAME the new ri authorized name
	 */
	public void setRI_AUTHORIZED_NAME(String rI_AUTHORIZED_NAME) {
		RI_AUTHORIZED_NAME = rI_AUTHORIZED_NAME;
	}
	
	/**
	 * Gets the ri capacity.
	 *
	 * @return the ri capacity
	 */
	public String getRI_CAPACITY() {
		return RI_CAPACITY;
	}
	
	/**
	 * Sets the ri capacity.
	 *
	 * @param rI_CAPACITY the new ri capacity
	 */
	public void setRI_CAPACITY(String rI_CAPACITY) {
		RI_CAPACITY = rI_CAPACITY;
	}
	
	/**
	 * Gets the ri margin client flag.
	 *
	 * @return the ri margin client flag
	 */
	public String getRI_MARGIN_CLIENT_FLAG() {
		return RI_MARGIN_CLIENT_FLAG;
	}
	
	/**
	 * Sets the ri margin client flag.
	 *
	 * @param rI_MARGIN_CLIENT_FLAG the new ri margin client flag
	 */
	public void setRI_MARGIN_CLIENT_FLAG(String rI_MARGIN_CLIENT_FLAG) {
		RI_MARGIN_CLIENT_FLAG = rI_MARGIN_CLIENT_FLAG;
	}
	
	/**
	 * Gets the ri margin report currency.
	 *
	 * @return the ri margin report currency
	 */
	public Long getRI_MARGIN_REPORT_CURRENCY() {
		return RI_MARGIN_REPORT_CURRENCY;
	}
	
	/**
	 * Sets the ri margin report currency.
	 *
	 * @param rI_MARGIN_REPORT_CURRENCY the new ri margin report currency
	 */
	public void setRI_MARGIN_REPORT_CURRENCY(Long rI_MARGIN_REPORT_CURRENCY) {
		RI_MARGIN_REPORT_CURRENCY = rI_MARGIN_REPORT_CURRENCY;
	}
	
	/**
	 * Gets the ri margin aggdate.
	 *
	 * @return the ri margin aggdate
	 */
	public Calendar getRI_MARGIN_AGGDATE() {
		return RI_MARGIN_AGGDATE;
	}
	
	/**
	 * Sets the ri margin aggdate.
	 *
	 * @param rI_MARGIN_AGGDATE the new ri margin aggdate
	 */
	public void setRI_MARGIN_AGGDATE(Calendar rI_MARGIN_AGGDATE) {
		RI_MARGIN_AGGDATE = rI_MARGIN_AGGDATE;
	}
	
	/**
	 * Gets the ri relation almal.
	 *
	 * @return the ri relation almal
	 */
	public String getRI_RELATION_ALMAL() {
		return RI_RELATION_ALMAL;
	}
	
	/**
	 * Sets the ri relation almal.
	 *
	 * @param rI_RELATION_ALMAL the new ri relation almal
	 */
	public void setRI_RELATION_ALMAL(String rI_RELATION_ALMAL) {
		RI_RELATION_ALMAL = rI_RELATION_ALMAL;
	}
	
	/**
	 * Gets the ri reffer by.
	 *
	 * @return the ri reffer by
	 */
	public String getRI_REFFER_BY() {
		return RI_REFFER_BY;
	}
	
	/**
	 * Sets the ri reffer by.
	 *
	 * @param rI_REFFER_BY the new ri reffer by
	 */
	public void setRI_REFFER_BY(String rI_REFFER_BY) {
		RI_REFFER_BY = rI_REFFER_BY;
	}
	
	/**
	 * Gets the ri pmr day.
	 *
	 * @return the ri pmr day
	 */
	public String getRI_PMR_DAY() {
		return RI_PMR_DAY;
	}
	
	/**
	 * Sets the ri pmr day.
	 *
	 * @param rI_PMR_DAY the new ri pmr day
	 */
	public void setRI_PMR_DAY(String rI_PMR_DAY) {
		RI_PMR_DAY = rI_PMR_DAY;
	}
	
	/**
	 * Gets the ri pmr weekly.
	 *
	 * @return the ri pmr weekly
	 */
	public String getRI_PMR_WEEKLY() {
		return RI_PMR_WEEKLY;
	}
	
	/**
	 * Sets the ri pmr weekly.
	 *
	 * @param rI_PMR_WEEKLY the new ri pmr weekly
	 */
	public void setRI_PMR_WEEKLY(String rI_PMR_WEEKLY) {
		RI_PMR_WEEKLY = rI_PMR_WEEKLY;
	}
	
	/**
	 * Gets the ri pmr month.
	 *
	 * @return the ri pmr month
	 */
	public String getRI_PMR_MONTH() {
		return RI_PMR_MONTH;
	}
	
	/**
	 * Sets the ri pmr month.
	 *
	 * @param rI_PMR_MONTH the new ri pmr month
	 */
	public void setRI_PMR_MONTH(String rI_PMR_MONTH) {
		RI_PMR_MONTH = rI_PMR_MONTH;
	}
	
	/**
	 * Gets the ri market amt.
	 *
	 * @return the ri market amt
	 */
	public Double getRI_MARKET_AMT() {
		return RI_MARKET_AMT;
	}
	
	/**
	 * Sets the ri market amt.
	 *
	 * @param rI_MARKET_AMT the new ri market amt
	 */
	public void setRI_MARKET_AMT(Double rI_MARKET_AMT) {
		RI_MARKET_AMT = rI_MARKET_AMT;
	}
	
	/**
	 * Gets the ri margin sub type.
	 *
	 * @return the ri margin sub type
	 */
	public Long getRI_MARGIN_SUB_TYPE() {
		return RI_MARGIN_SUB_TYPE;
	}
	
	/**
	 * Sets the ri margin sub type.
	 *
	 * @param rI_MARGIN_SUB_TYPE the new ri margin sub type
	 */
	public void setRI_MARGIN_SUB_TYPE(Long rI_MARGIN_SUB_TYPE) {
		RI_MARGIN_SUB_TYPE = rI_MARGIN_SUB_TYPE;
	}
	
	/**
	 * Gets the ri own or client.
	 *
	 * @return the ri own or client
	 */
	public String getRI_OWN_OR_CLIENT() {
		return RI_OWN_OR_CLIENT;
	}
	
	/**
	 * Sets the ri own or client.
	 *
	 * @param rI_OWN_OR_CLIENT the new ri own or client
	 */
	public void setRI_OWN_OR_CLIENT(String rI_OWN_OR_CLIENT) {
		RI_OWN_OR_CLIENT = rI_OWN_OR_CLIENT;
	}
	
	/**
	 * Gets the ri month end rep flag.
	 *
	 * @return the ri month end rep flag
	 */
	public String getRI_MONTH_END_REP_FLAG() {
		return RI_MONTH_END_REP_FLAG;
	}
	
	/**
	 * Sets the ri month end rep flag.
	 *
	 * @param rI_MONTH_END_REP_FLAG the new ri month end rep flag
	 */
	public void setRI_MONTH_END_REP_FLAG(String rI_MONTH_END_REP_FLAG) {
		RI_MONTH_END_REP_FLAG = rI_MONTH_END_REP_FLAG;
	}
	
	/**
	 * Gets the ri interest post.
	 *
	 * @return the ri interest post
	 */
	public String getRI_INTEREST_POST() {
		return RI_INTEREST_POST;
	}
	
	/**
	 * Sets the ri interest post.
	 *
	 * @param rI_INTEREST_POST the new ri interest post
	 */
	public void setRI_INTEREST_POST(String rI_INTEREST_POST) {
		RI_INTEREST_POST = rI_INTEREST_POST;
	}
	
	/**
	 * Gets the ri interest remarks.
	 *
	 * @return the ri interest remarks
	 */
	public String getRI_INTEREST_REMARKS() {
		return RI_INTEREST_REMARKS;
	}
	
	/**
	 * Sets the ri interest remarks.
	 *
	 * @param rI_INTEREST_REMARKS the new ri interest remarks
	 */
	public void setRI_INTEREST_REMARKS(String rI_INTEREST_REMARKS) {
		RI_INTEREST_REMARKS = rI_INTEREST_REMARKS;
	}
	
	/**
	 * Gets the ri account close.
	 *
	 * @return the ri account close
	 */
	public String getRI_ACCOUNT_CLOSE() {
		return RI_ACCOUNT_CLOSE;
	}
	
	/**
	 * Sets the ri account close.
	 *
	 * @param rI_ACCOUNT_CLOSE the new ri account close
	 */
	public void setRI_ACCOUNT_CLOSE(String rI_ACCOUNT_CLOSE) {
		RI_ACCOUNT_CLOSE = rI_ACCOUNT_CLOSE;
	}
	
	/**
	 * Gets the ri account close remarks.
	 *
	 * @return the ri account close remarks
	 */
	public String getRI_ACCOUNT_CLOSE_REMARKS() {
		return RI_ACCOUNT_CLOSE_REMARKS;
	}
	
	/**
	 * Sets the ri account close remarks.
	 *
	 * @param rI_ACCOUNT_CLOSE_REMARKS the new ri account close remarks
	 */
	public void setRI_ACCOUNT_CLOSE_REMARKS(String rI_ACCOUNT_CLOSE_REMARKS) {
		RI_ACCOUNT_CLOSE_REMARKS = rI_ACCOUNT_CLOSE_REMARKS;
	}
	
	/**
	 * Gets the ri salvation.
	 *
	 * @return the ri salvation
	 */
	public String getRI_SALVATION() {
		return RI_SALVATION;
	}
	
	/**
	 * Sets the ri salvation.
	 *
	 * @param rI_SALVATION the new ri salvation
	 */
	public void setRI_SALVATION(String rI_SALVATION) {
		RI_SALVATION = rI_SALVATION;
	}
	
	/**
	 * Gets the ri first name.
	 *
	 * @return the ri first name
	 */
	public String getRI_FIRST_NAME() {
		return RI_FIRST_NAME;
	}
	
	/**
	 * Sets the ri first name.
	 *
	 * @param rI_FIRST_NAME the new ri first name
	 */
	public void setRI_FIRST_NAME(String rI_FIRST_NAME) {
		RI_FIRST_NAME = rI_FIRST_NAME;
	}
	
	/**
	 * Gets the ri last name.
	 *
	 * @return the ri last name
	 */
	public String getRI_LAST_NAME() {
		return RI_LAST_NAME;
	}
	
	/**
	 * Sets the ri last name.
	 *
	 * @param rI_LAST_NAME the new ri last name
	 */
	public void setRI_LAST_NAME(String rI_LAST_NAME) {
		RI_LAST_NAME = rI_LAST_NAME;
	}
	
	/**
	 * Gets the ri middle name.
	 *
	 * @return the ri middle name
	 */
	public String getRI_MIDDLE_NAME() {
		return RI_MIDDLE_NAME;
	}
	
	/**
	 * Sets the ri middle name.
	 *
	 * @param rI_MIDDLE_NAME the new ri middle name
	 */
	public void setRI_MIDDLE_NAME(String rI_MIDDLE_NAME) {
		RI_MIDDLE_NAME = rI_MIDDLE_NAME;
	}
	
	/**
	 * Gets the ri suffix.
	 *
	 * @return the ri suffix
	 */
	public String getRI_SUFFIX() {
		return RI_SUFFIX;
	}
	
	/**
	 * Sets the ri suffix.
	 *
	 * @param rI_SUFFIX the new ri suffix
	 */
	public void setRI_SUFFIX(String rI_SUFFIX) {
		RI_SUFFIX = rI_SUFFIX;
	}
	
	/**
	 * Gets the ri joint salvation.
	 *
	 * @return the ri joint salvation
	 */
	public String getRI_JOINT_SALVATION() {
		return RI_JOINT_SALVATION;
	}
	
	/**
	 * Sets the ri joint salvation.
	 *
	 * @param rI_JOINT_SALVATION the new ri joint salvation
	 */
	public void setRI_JOINT_SALVATION(String rI_JOINT_SALVATION) {
		RI_JOINT_SALVATION = rI_JOINT_SALVATION;
	}
	
	/**
	 * Gets the ri joint first name.
	 *
	 * @return the ri joint first name
	 */
	public String getRI_JOINT_FIRST_NAME() {
		return RI_JOINT_FIRST_NAME;
	}
	
	/**
	 * Sets the ri joint first name.
	 *
	 * @param rI_JOINT_FIRST_NAME the new ri joint first name
	 */
	public void setRI_JOINT_FIRST_NAME(String rI_JOINT_FIRST_NAME) {
		RI_JOINT_FIRST_NAME = rI_JOINT_FIRST_NAME;
	}
	
	/**
	 * Gets the ri joint last name.
	 *
	 * @return the ri joint last name
	 */
	public String getRI_JOINT_LAST_NAME() {
		return RI_JOINT_LAST_NAME;
	}
	
	/**
	 * Sets the ri joint last name.
	 *
	 * @param rI_JOINT_LAST_NAME the new ri joint last name
	 */
	public void setRI_JOINT_LAST_NAME(String rI_JOINT_LAST_NAME) {
		RI_JOINT_LAST_NAME = rI_JOINT_LAST_NAME;
	}
	
	/**
	 * Gets the ri joint middle name.
	 *
	 * @return the ri joint middle name
	 */
	public String getRI_JOINT_MIDDLE_NAME() {
		return RI_JOINT_MIDDLE_NAME;
	}
	
	/**
	 * Sets the ri joint middle name.
	 *
	 * @param rI_JOINT_MIDDLE_NAME the new ri joint middle name
	 */
	public void setRI_JOINT_MIDDLE_NAME(String rI_JOINT_MIDDLE_NAME) {
		RI_JOINT_MIDDLE_NAME = rI_JOINT_MIDDLE_NAME;
	}
	
	/**
	 * Gets the ri joint suffix.
	 *
	 * @return the ri joint suffix
	 */
	public String getRI_JOINT_SUFFIX() {
		return RI_JOINT_SUFFIX;
	}
	
	/**
	 * Sets the ri joint suffix.
	 *
	 * @param rI_JOINT_SUFFIX the new ri joint suffix
	 */
	public void setRI_JOINT_SUFFIX(String rI_JOINT_SUFFIX) {
		RI_JOINT_SUFFIX = rI_JOINT_SUFFIX;
	}
	
	/**
	 * Gets the ri corp cd salvation.
	 *
	 * @return the ri corp cd salvation
	 */
	public String getRI_CORP_CD_SALVATION() {
		return RI_CORP_CD_SALVATION;
	}
	
	/**
	 * Sets the ri corp cd salvation.
	 *
	 * @param rI_CORP_CD_SALVATION the new ri corp cd salvation
	 */
	public void setRI_CORP_CD_SALVATION(String rI_CORP_CD_SALVATION) {
		RI_CORP_CD_SALVATION = rI_CORP_CD_SALVATION;
	}
	
	/**
	 * Gets the ri corp cd first name.
	 *
	 * @return the ri corp cd first name
	 */
	public String getRI_CORP_CD_FIRST_NAME() {
		return RI_CORP_CD_FIRST_NAME;
	}
	
	/**
	 * Sets the ri corp cd first name.
	 *
	 * @param rI_CORP_CD_FIRST_NAME the new ri corp cd first name
	 */
	public void setRI_CORP_CD_FIRST_NAME(String rI_CORP_CD_FIRST_NAME) {
		RI_CORP_CD_FIRST_NAME = rI_CORP_CD_FIRST_NAME;
	}
	
	/**
	 * Gets the ri corp cd last name.
	 *
	 * @return the ri corp cd last name
	 */
	public String getRI_CORP_CD_LAST_NAME() {
		return RI_CORP_CD_LAST_NAME;
	}
	
	/**
	 * Sets the ri corp cd last name.
	 *
	 * @param rI_CORP_CD_LAST_NAME the new ri corp cd last name
	 */
	public void setRI_CORP_CD_LAST_NAME(String rI_CORP_CD_LAST_NAME) {
		RI_CORP_CD_LAST_NAME = rI_CORP_CD_LAST_NAME;
	}
	
	/**
	 * Gets the ri corp cd middle name.
	 *
	 * @return the ri corp cd middle name
	 */
	public String getRI_CORP_CD_MIDDLE_NAME() {
		return RI_CORP_CD_MIDDLE_NAME;
	}
	
	/**
	 * Sets the ri corp cd middle name.
	 *
	 * @param rI_CORP_CD_MIDDLE_NAME the new ri corp cd middle name
	 */
	public void setRI_CORP_CD_MIDDLE_NAME(String rI_CORP_CD_MIDDLE_NAME) {
		RI_CORP_CD_MIDDLE_NAME = rI_CORP_CD_MIDDLE_NAME;
	}
	
	/**
	 * Gets the ri corp cd suffix.
	 *
	 * @return the ri corp cd suffix
	 */
	public String getRI_CORP_CD_SUFFIX() {
		return RI_CORP_CD_SUFFIX;
	}
	
	/**
	 * Sets the ri corp cd suffix.
	 *
	 * @param rI_CORP_CD_SUFFIX the new ri corp cd suffix
	 */
	public void setRI_CORP_CD_SUFFIX(String rI_CORP_CD_SUFFIX) {
		RI_CORP_CD_SUFFIX = rI_CORP_CD_SUFFIX;
	}
	
	/**
	 * Gets the ri account close date.
	 *
	 * @return the ri account close date
	 */
	public Calendar getRI_ACCOUNT_CLOSE_DATE() {
		return RI_ACCOUNT_CLOSE_DATE;
	}
	
	/**
	 * Sets the ri account close date.
	 *
	 * @param rI_ACCOUNT_CLOSE_DATE the new ri account close date
	 */
	public void setRI_ACCOUNT_CLOSE_DATE(Calendar rI_ACCOUNT_CLOSE_DATE) {
		RI_ACCOUNT_CLOSE_DATE = rI_ACCOUNT_CLOSE_DATE;
	}
	
	/**
	 * Gets the ri crate.
	 *
	 * @return the ri crate
	 */
	public Double getRI_CRATE() {
		return RI_CRATE;
	}
	
	/**
	 * Sets the ri crate.
	 *
	 * @param rI_CRATE the new ri crate
	 */
	public void setRI_CRATE(Double rI_CRATE) {
		RI_CRATE = rI_CRATE;
	}
	
	/**
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	
	/**
	 * Sets the comments.
	 *
	 * @param comments the new comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Gets the enteredby.
	 *
	 * @return the enteredby
	 */
	public String getEnteredby() {
		return enteredby;
	}
	
	/**
	 * Sets the enteredby.
	 *
	 * @param enteredby the new enteredby
	 */
	public void setEnteredby(String enteredby) {
		this.enteredby = enteredby;
	}
	
	/**
	 * Gets the enteredbyuserid.
	 *
	 * @return the enteredbyuserid
	 */
	public String getEnteredbyuserid() {
		return enteredbyuserid;
	}
	
	/**
	 * Sets the enteredbyuserid.
	 *
	 * @param enteredbyuserid the new enteredbyuserid
	 */
	public void setEnteredbyuserid(String enteredbyuserid) {
		this.enteredbyuserid = enteredbyuserid;
	}
	
	/**
	 * Gets the enteredbyuuid.
	 *
	 * @return the enteredbyuuid
	 */
	public String getEnteredbyuuid() {
		return enteredbyuuid;
	}
	
	/**
	 * Sets the enteredbyuuid.
	 *
	 * @param enteredbyuuid the new enteredbyuuid
	 */
	public void setEnteredbyuuid(String enteredbyuuid) {
		this.enteredbyuuid = enteredbyuuid;
	}
	
	/**
	 * Gets the entereddate.
	 *
	 * @return the entereddate
	 */
	public Calendar getEntereddate() {
		return entereddate;
	}
	
	/**
	 * Sets the entereddate.
	 *
	 * @param entereddate the new entereddate
	 */
	public void setEntereddate(Calendar entereddate) {
		this.entereddate = entereddate;
	}
	
	/**
	 * Gets the approvedby.
	 *
	 * @return the approvedby
	 */
	public String getApprovedby() {
		return approvedby;
	}
	
	/**
	 * Sets the approvedby.
	 *
	 * @param approvedby the new approvedby
	 */
	public void setApprovedby(String approvedby) {
		this.approvedby = approvedby;
	}
	
	/**
	 * Gets the approvedbyuserid.
	 *
	 * @return the approvedbyuserid
	 */
	public String getApprovedbyuserid() {
		return approvedbyuserid;
	}
	
	/**
	 * Sets the approvedbyuserid.
	 *
	 * @param approvedbyuserid the new approvedbyuserid
	 */
	public void setApprovedbyuserid(String approvedbyuserid) {
		this.approvedbyuserid = approvedbyuserid;
	}
	
	/**
	 * Gets the approvedbyuuid.
	 *
	 * @return the approvedbyuuid
	 */
	public String getApprovedbyuuid() {
		return approvedbyuuid;
	}
	
	/**
	 * Sets the approvedbyuuid.
	 *
	 * @param approvedbyuuid the new approvedbyuuid
	 */
	public void setApprovedbyuuid(String approvedbyuuid) {
		this.approvedbyuuid = approvedbyuuid;
	}
	
	/**
	 * Gets the approveddate.
	 *
	 * @return the approveddate
	 */
	public Calendar getApproveddate() {
		return approveddate;
	}
	
	/**
	 * Sets the approveddate.
	 *
	 * @param approveddate the new approveddate
	 */
	public void setApproveddate(Calendar approveddate) {
		this.approveddate = approveddate;
	}
	
	/**
	 * Gets the modifiedby.
	 *
	 * @return the modifiedby
	 */
	public String getModifiedby() {
		return modifiedby;
	}
	
	/**
	 * Sets the modifiedby.
	 *
	 * @param modifiedby the new modifiedby
	 */
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	
	/**
	 * Gets the modifiedbyuserid.
	 *
	 * @return the modifiedbyuserid
	 */
	public String getModifiedbyuserid() {
		return modifiedbyuserid;
	}
	
	/**
	 * Sets the modifiedbyuserid.
	 *
	 * @param modifiedbyuserid the new modifiedbyuserid
	 */
	public void setModifiedbyuserid(String modifiedbyuserid) {
		this.modifiedbyuserid = modifiedbyuserid;
	}
	
	/**
	 * Gets the modifiedbyuuid.
	 *
	 * @return the modifiedbyuuid
	 */
	public String getModifiedbyuuid() {
		return modifiedbyuuid;
	}
	
	/**
	 * Sets the modifiedbyuuid.
	 *
	 * @param modifiedbyuuid the new modifiedbyuuid
	 */
	public void setModifiedbyuuid(String modifiedbyuuid) {
		this.modifiedbyuuid = modifiedbyuuid;
	}
	
	/**
	 * Gets the modifieddate.
	 *
	 * @return the modifieddate
	 */
	public Calendar getModifieddate() {
		return modifieddate;
	}
	
	/**
	 * Sets the modifieddate.
	 *
	 * @param modifieddate the new modifieddate
	 */
	public void setModifieddate(Calendar modifieddate) {
		this.modifieddate = modifieddate;
	}
	
	/**
	 * Gets the msg.
	 *
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	
	/**
	 * Sets the msg.
	 *
	 * @param msg the new msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	/**
	 * Gets the ri country name.
	 *
	 * @return the ri country name
	 */
	public String getRI_COUNTRY_NAME() {
		return RI_COUNTRY_NAME;
	}


	/**
	 * Sets the ri country name.
	 *
	 * @param rI_COUNTRY_NAME the new ri country name
	 */
	public void setRI_COUNTRY_NAME(String rI_COUNTRY_NAME) {
		RI_COUNTRY_NAME = rI_COUNTRY_NAME;
	}


	/**
	 * Gets the ri country2 name.
	 *
	 * @return the ri country2 name
	 */
	public String getRI_COUNTRY2_NAME() {
		return RI_COUNTRY2_NAME;
	}


	/**
	 * Sets the ri country2 name.
	 *
	 * @param rI_COUNTRY2_NAME the new ri country2 name
	 */
	public void setRI_COUNTRY2_NAME(String rI_COUNTRY2_NAME) {
		RI_COUNTRY2_NAME = rI_COUNTRY2_NAME;
	}


	/**
	 * Gets the ri bank country name.
	 *
	 * @return the ri bank country name
	 */
	public String getRI_BANK_COUNTRY_NAME() {
		return RI_BANK_COUNTRY_NAME;
	}


	/**
	 * Sets the ri bank country name.
	 *
	 * @param rI_BANK_COUNTRY_NAME the new ri bank country name
	 */
	public void setRI_BANK_COUNTRY_NAME(String rI_BANK_COUNTRY_NAME) {
		RI_BANK_COUNTRY_NAME = rI_BANK_COUNTRY_NAME;
	}


	/**
	 * Gets the ri margin report currency name.
	 *
	 * @return the ri margin report currency name
	 */
	public String getRI_MARGIN_REPORT_CURRENCY_NAME() {
		return RI_MARGIN_REPORT_CURRENCY_NAME;
	}


	/**
	 * Sets the ri margin report currency name.
	 *
	 * @param rI_MARGIN_REPORT_CURRENCY_NAME the new ri margin report currency name
	 */
	public void setRI_MARGIN_REPORT_CURRENCY_NAME(String rI_MARGIN_REPORT_CURRENCY_NAME) {
		RI_MARGIN_REPORT_CURRENCY_NAME = rI_MARGIN_REPORT_CURRENCY_NAME;
	}


	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(InvestorBean o) {
		if(RI_INVESTOR_NAME.compareTo(o.getRI_INVESTOR_NAME())>0) {
			return 1;
		}else if(RI_INVESTOR_NAME.compareTo(o.getRI_INVESTOR_NAME())<0)
		{
			return -1;
		}else {
			return 0;
		}
	}


	/**
	 * Gets the ri margin type name.
	 *
	 * @return the ri margin type name
	 */
	public String getRI_MARGIN_TYPE_NAME() {
		return RI_MARGIN_TYPE_NAME;
	}


	/**
	 * Sets the ri margin type name.
	 *
	 * @param rI_MARGIN_TYPE_NAME the new ri margin type name
	 */
	public void setRI_MARGIN_TYPE_NAME(String rI_MARGIN_TYPE_NAME) {
		RI_MARGIN_TYPE_NAME = rI_MARGIN_TYPE_NAME;
	}


	/**
	 * Gets the ri bank currency name.
	 *
	 * @return the ri bank currency name
	 */
	public String getRI_BANK_CURRENCY_NAME() {
		return RI_BANK_CURRENCY_NAME;
	}


	/**
	 * Sets the ri bank currency name.
	 *
	 * @param rI_BANK_CURRENCY_NAME the new ri bank currency name
	 */
	public void setRI_BANK_CURRENCY_NAME(String rI_BANK_CURRENCY_NAME) {
		RI_BANK_CURRENCY_NAME = rI_BANK_CURRENCY_NAME;
	}
	
}
