package com.fynisys.model.crm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;




@Entity(name="RE_INVESTOR")
public class RE_INVESTOR implements Serializable{
	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	  @Id
	  @GeneratedValue
	  @Column(name="RI_WMS_CODE")
	  private Long  RI_WMS_CODE;
	  
	  @Column(precision=25,scale=0)
	  private Long RI_INVESTOR_CODE;          ;//NUMBER (25)   NOT NULL,	
	  
	  @Column(length=30)
	  private String RI_INVESTOR_TYPE;           ;//VARCHAR2 (30), 
	  
	  private Calendar RI_CREATE_DATE;          ; //DATE,
	  
	  @Column(length=100)
	  private String RI_INVESTOR_NAME;           ;//VARCHAR2 (100),
	  
	  @Column(length=90)
	  private String RI_ADDRESS_1;               ;//VARCHAR2 (90),
	  
	  @Column(length=25)
	  private String RI_POST_BOX;                ;//VARCHAR2 (25),
	  
	  @Column(length=25)
	  private String RI_POST_CODE;               ;//VARCHAR2 (25),
	  
	  @Column(length=25)
	  private String RI_CITY;                   ;//VARCHAR2 (25),
	  
	  @Column(length=30)
	  private String RI_COUNTRY;                ;//VARCHAR2 (30),
	  
	  @Column(length=25)
	  private String RI_TEL_NO;                  ;//VARCHAR2 (25),
	  
	  @Column(length=25)
	  private String RI_MOBILE_NO;               ;//VARCHAR2 (25),
	  
	  @Column(length=25)
	  private String RI_FAX_NO;                  ;//VARCHAR2 (25),
	  
	  @Column(length=200)
	  private String RI_EMAIL;                  ;// VARCHAR2 (200),
	  
	  @Column(length=100)
	  private String RI_COMPANY_NAME;            ;//VARCHAR2 (100),
	  
	  @Column(length=40)
	  private String RI_COMPANY_LICENSE_NO;      ;//VARCHAR2 (40),
	  
	  private Calendar RI_EXPIRY_DATE;             ;//DATE
	  
	  @Column(length=30)
	  private String RI_OCCUPATION;              ;//VARCHAR2 (30),
	  
	  @Column(length=25)
	  private String RI_NATIONALITY;             ;//VARCHAR2 (25), 
	  
	  private Calendar RI_DOB;                     ;//DATE, 
	  
	  @Column(length=6)
	  private String RI_GENDER;                  ;//VARCHAR2 (6),
	  
	  @Column(length=25)
	  private String RI_JOINT_AUTHORISATION;     ;//VARCHAR2 (25),
	  
	  @Column(length=35)
	  private String RI_STATUS;                  ;//VARCHAR2 (35),
	  
	  @Column(length=15)
	  private String SVC_UID;                    ;//VARCHAR2 (15),
	  
	  @Column(length=40)
	  private String RI_INDUSTRY;                ;//VARCHAR2 (40),
	  
	  @Column(length=100)
	  private String RI_REMARKS;                 ;//VARCHAR2 (100),
	  
	  @Column(length=25)
	  private String RI_MARITAL_STATUS;          ;//VARCHAR2 (25),
	  
	  @Column(length=55)
	  private String RI_MARITAL_DEPENDENTS;      ;//VARCHAR2 (55),
	  
	  @Column(length=55)
	  private String RI_NATIONAL_IDENITY;        ;//VARCHAR2 (55),
	  
	  @Column(length=55)
	  private String RI_PASSPORT_IPLACE;         ;//VARCHAR2 (55),
	  
	  private Calendar RI_PASSPORT_IDATE;          ;//DATE,
	  
	  @Column(length=55)
	  private String RI_PASSPORT_ID;            ;// VARCHAR2 (55),
	  
	  @Column(length=100)
	  private String RI_INVESTOR_NAME2;          ;//VARCHAR2 (100), 
	  
	  @Column(length=100)
	  private String RI_COMPANY_NAME2;           ;//VARCHAR2 (100),
	  
	  @Column(length=30)
	  private String RI_OCCUPATION2;             ;//VARCHAR2 (30),
	  
	  @Column(length=25)
	  private String RI_NATIONALITY2;            ;//VARCHAR2 (25), 
	  
	  private Calendar RI_DOB2                    ;//DATE,
	  
	  @Column(length=6)
	  private String RI_GENDER2;                 ;//VARCHAR2 (6),
	  
	  @Column(length=25)
	  private String RI_MARITAL_STATUS2;         ;//VARCHAR2 (25),
	  
	  @Column(length=55)
	  private String RI_MARITAL_DEPENDENTS2;     ;//VARCHAR2 (55),
	  
	  @Column(length=55)
	  private String RI_PASSPORT_IPLACE2;        ;//VARCHAR2 (55), 
	  
	  private Calendar RI_PASSPORT_IDATE2;         ;//DATE,
	  
	  @Column(length=55)
	  private String RI_NATIONAL_IDENITY2;       ;//VARCHAR2 (55),
	  
	  @Column(length=55)
	  private String RI_PASSPORT_ID2;            ;//VARCHAR2 (55),
	  
	  @Column(length=90)
	  private String RI_PRIMARY_CONTACT_NAME;    ;//VARCHAR2 (90),
	  
	  @Column(length=25)
	  private String RI_OFFICE_APART_NO;         ;//VARCHAR2 (25),
	  
	  @Column(length=25)
	  private String RI_STREET;                  ;//VARCHAR2 (25),
	  
	  @Column(length=25)
	  private String RI_AREA;                    ;//VARCHAR2 (25),
	  
	  @Column(length=25)
	  private String RI_TEL_NO_HOME;             ;//VARCHAR2 (25), 
	  
	  @Column(length=90)
	  private String RI_PRIMARY_CONTACT_NAME2   ;//VARCHAR2 (90),
	  
	  @Column(length=90)
	  private String RI_ADDRESS_12              ;//VARCHAR2 (90),
	  
	  @Column(length=25)
	  private String RI_OFFICE_APART_NO2        ;//VARCHAR2 (25),
	  
	  @Column(length=25)
	  private String RI_STREET2                 ;//VARCHAR2 (25),
	  
	  @Column(length=25)
	  private String RI_AREA2                   ;//VARCHAR2 (25),
	  
	  @Column(length=25)
	  private String RI_POST_BOX2               ;//VARCHAR2 (25),
	  
	  @Column(length=25)
	  private String RI_CITY2                   ;//VARCHAR2 (25),
	  
	  @Column(length=30)
	  private String RI_COUNTRY2                ;//VARCHAR2 (30),
	  
	  @Column(length=25)
	  private String RI_POST_CODE2              ;//VARCHAR2 (25),
	  
	  @Column(length=25)
	  private String RI_TEL_NO2                 ;//VARCHAR2 (25),
	  
	  @Column(length=25)
	  private String RI_TEL_NO_HOME2            ;//VARCHAR2 (25),
	  
	  @Column(length=25)
	  private String RI_MOBILE_NO2              ;//VARCHAR2 (25),
	  
	  @Column(length=25)
	  private String RI_FAX_NO2                 ;//VARCHAR2 (25),
	  
	  @Column(length=40)
	  private String RI_EMAIL2                  ;//VARCHAR2 (40),
	  
	  private Calendar RI_EXPIRY_DATE2            ;//DATE, 
	  
	  @Column(length=40)
	  private String RI_BANK_ANAME              ;//VARCHAR2 (40),
	  
	  @Column(length=40)
	  private String RI_BANK_ANUMBER            ;//VARCHAR2 (40),
	  
	  @Column(length=40)
	  private String RI_BANK_BANK_NAME          ;//VARCHAR2 (40),
	  
	  @Column(length=40)
	  private String RI_BANK_CURRENCY           ;//VARCHAR2 (40),
	  
	  @Column(length=40)
	  private String RI_BANK_BRANCH             ;//VARCHAR2 (40),
	  
	  @Column(length=40)
	  private String RI_BANK_COUNTRY            ;//VARCHAR2 (40),
	  
	  @Column(length=40)
	  private String RI_BANK_CITY               ;//VARCHAR2 (40),
	  
	  @Column(length=1)
	  private String RI_NOTI_FAX                ;//VARCHAR2 (1), 
	  
	  @Column(length=1)
	  private String RI_NOTI_SMS                ;//VARCHAR2 (1),
	  
	  @Column(length=1)
	  private String RI_NOTI_MAIL               ;//VARCHAR2 (1),
	  
	  @Column(length=1)
	  private String RI_NOTI_EMAIL              ;//VARCHAR2 (1), 
	  
	  private Long RI_MARGIN_TYPE             ;//NUMBER (10), 
	  
	  @Column(nullable=true)
	  private Long RI_INTEREST_TYPE           ;//NUMBER (10), 
	  
	  private Long RI_RELATION_MANAGER        ;//NUMBER (10), 
	  
	  @Column(length=90)
	  private String RI_CORPORATE_NAME          ;//VARCHAR2 (90),
	  
	  @Column(length=90)
	  private String RI_BUSINESS_ACTIVITY       ;//VARCHAR2 (90),
	  
	  @Column(length=90)
	  private String RI_COUNTRY_ESTA            ;//VARCHAR2 (90),
	  
	  @Column(length=90)
	  private String RI_COMMERCIAL_REGIS_NO     ;//VARCHAR2 (90),
	  
	  @Column(length=90)
	  private String RI_TRADE_LICENSE_NO        ;//VARCHAR2 (90),
	  
	  
	  private Calendar RI_TRADE_LICENSE_IDATE     ;//DATE, 
	  
	  private Calendar RI_TRADE_LICENSE_EDATE     ;//DATE,
	  
	  @Column(length=90)
	  private String RI_AUTHORIZED_NAME         ;//VARCHAR2 (90), 
	  
	  @Column(length=90)
	  private String RI_CAPACITY                ;//VARCHAR2 (90),
	  
	  @Column(length=3)
	  private String RI_MARGIN_CLIENT_FLAG      ;//VARCHAR2 (3),
	  
	  private Long RI_MARGIN_REPORT_CURRENCY  ;//NUMBER (10),
	  
	  private Calendar RI_MARGIN_AGGDATE          ;//DATE, 
	  
	  @Column(length=100)
	  private String RI_RELATION_ALMAL          ;//VARCHAR2 (100), 
	  
	  @Column(length=100)
	  private String RI_REFFER_BY               ;//VARCHAR2 (100),
	  
	  @Column(length=3)
	  private String RI_PMR_DAY                 ;//VARCHAR2 (3),
	  
	  @Column(length=3)
	  private String RI_PMR_WEEKLY              ;//VARCHAR2 (3),
	  
	  @Column(length=3)
	  private String RI_PMR_MONTH               ;//VARCHAR2 (3),
	  
	  @Column(precision=25,scale=3)
	  private double RI_MARKET_AMT              ;//NUMBER (25,3),
	  
	  @Column(length=119)
	  private String IV_ENTER_UID               ;//VARCHAR2 (119), 
	  
	  private Calendar IV_ENTER_DATE              ;//DATE,
	  
	  @Column(length=119)
	  private String IV_APPROVE_UID             ;//VARCHAR2 (119),
	  
	  private Calendar IV_APPROVE_DATE            ;//DATE, 
	  
	  @Column(length=119)
	  private String IV_LAST_UPDATE_UID         ;//VARCHAR2 (119), 
	  
	  private Calendar IV_LAST_UPDATE_DATE        ;//DATE, 
	  
	  private Long RI_MARGIN_SUB_TYPE         ;//NUMBER (10), 
	  
	  @Column(length=40)
	  private String RI_OWN_OR_CLIENT           ;//VARCHAR2 (40),
	  
	  @Column(length=40)
	  private String RI_MONTH_END_REP_FLAG      ;//VARCHAR2 (40),
	  
	  @Column(length=40)
	  private String RI_INTEREST_POST           ;//VARCHAR2 (40),
	  
	  @Column(length=240)
	  private String RI_INTEREST_REMARKS        ;//VARCHAR2 (240),
	  
	  @Column(length=40)
	  private String RI_ACCOUNT_CLOSE           ;//VARCHAR2 (40),
	  
	  @Column(length=240)
	  private String RI_ACCOUNT_CLOSE_REMARKS   ;//VARCHAR2 (240),
	  
	  @Column(length=100)
	  private String RI_SALVATION               ;//VARCHAR2 (100),
	  
	  @Column(length=100)
	  private String RI_FIRST_NAME              ;//VARCHAR2 (100),
	  
	  @Column(length=100)
	  private String RI_LAST_NAME               ;//VARCHAR2 (100),
	  
	  @Column(length=100)
	  private String RI_MIDDLE_NAME             ;//VARCHAR2 (100),
	  
	  @Column(length=100)
	  private String RI_SUFFIX                  ;//VARCHAR2 (100),
	  
	  @Column(length=100)
	  private String RI_JOINT_SALVATION         ;//VARCHAR2 (100),
	  
	  @Column(length=100)
	  private String RI_JOINT_FIRST_NAME        ;//VARCHAR2 (100),
	  
	  @Column(length=100)
	  private String RI_JOINT_LAST_NAME         ;//VARCHAR2 (100),
	  
	  @Column(length=100)
	  private String RI_JOINT_MIDDLE_NAME       ;//VARCHAR2 (100),
	  
	  @Column(length=100)
	  private String RI_JOINT_SUFFIX            ;//VARCHAR2 (100),
	  
	  @Column(length=100)
	  private String RI_CORP_CD_SALVATION       ;//VARCHAR2 (100),
	  
	  @Column(length=100)
	  private String RI_CORP_CD_FIRST_NAME      ;//VARCHAR2 (100),
	  
	  @Column(length=100)
	  private String RI_CORP_CD_LAST_NAME       ;//VARCHAR2 (100),
	  
	  @Column(length=100)
	  private String RI_CORP_CD_MIDDLE_NAME     ;//VARCHAR2 (100),
	  
	  @Column(length=100)
	  private String RI_CORP_CD_SUFFIX          ;//VARCHAR2 (100),
	  
	  
	  private Calendar RI_ACCOUNT_CLOSE_DATE      ;//DATE,
	  
	  @Column(precision=10,scale=3)
	  private double RI_CRATE                   ;//NUMBER (10,3)
	  public Long getRI_WMS_CODE() {
		return RI_WMS_CODE;
	}
	public void setRI_WMS_CODE(Long rI_WMS_CODE) {
		RI_WMS_CODE = rI_WMS_CODE;
	}
	/*
	   * Standard parameter
	   */
	  @Column(length=300)
	  private String WMS_COMMENTS;
	  @Column(length=20)
	  private String WMS_STATUS;
	  
	  /*
	   * Relationship here
	   */ 
	  
	  /*
	   * With RM documents
	   */
	  @OneToOne(fetch=FetchType.LAZY,mappedBy="rE_INVESTOR",cascade=CascadeType.ALL,orphanRemoval=true)
		private RE_CLIENT_RM_LINK_HEAD rE_CLIENT_RM_LINK_HEAD;
	  	public RE_CLIENT_RM_LINK_HEAD getrE_CLIENT_RM_LINK_HEAD() {
	  		return rE_CLIENT_RM_LINK_HEAD;
	  		}
	  	public void setrE_CLIENT_RM_LINK_HEAD(RE_CLIENT_RM_LINK_HEAD rE_CLIENT_RM_LINK_HEAD) {
	  		this.rE_CLIENT_RM_LINK_HEAD = rE_CLIENT_RM_LINK_HEAD;
	  		}
	/*
	   * Approval Work flow
	   */
	@OneToOne(fetch=FetchType.LAZY,mappedBy="rE_INVESTOR",cascade=CascadeType.ALL,orphanRemoval=true)
	private RE_CRMWORKFLOW rE_CRMWORKFLOW;
	
	
	public RE_CRMWORKFLOW getrE_CRMWORKFLOW() {
		return rE_CRMWORKFLOW;
	}
	public void setrE_CRMWORKFLOW(RE_CRMWORKFLOW rE_CRMWORKFLOW) {
		this.rE_CRMWORKFLOW = rE_CRMWORKFLOW;
	}
	/*
	 * Fund Account Link
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="rE_INVESTOR",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<FUND_ACCOUNT_LINK> fUND_ACCOUNT_LINK=new ArrayList<>();

	public List<FUND_ACCOUNT_LINK> getfUND_ACCOUNT_LINK() {
		return fUND_ACCOUNT_LINK;
	}
	public void setfUND_ACCOUNT_LINK(List<FUND_ACCOUNT_LINK> fUND_ACCOUNT_LINK) {
		this.fUND_ACCOUNT_LINK = fUND_ACCOUNT_LINK;
	}
/*
 * Client documents
 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="rE_INVESTOR",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<FUND_CLIENT_DOCUMENTS> fUND_CLIENT_DOCUMENTS=new ArrayList<>();
	public List<FUND_CLIENT_DOCUMENTS> getfUND_CLIENT_DOCUMENTS() {
		return fUND_CLIENT_DOCUMENTS;
	}
	public void setfUND_CLIENT_DOCUMENTS(List<FUND_CLIENT_DOCUMENTS> fUND_CLIENT_DOCUMENTS) {
		this.fUND_CLIENT_DOCUMENTS = fUND_CLIENT_DOCUMENTS;
	}
	/*
	 * Commission
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="rE_INVESTOR",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<FUND_COMP_CUST_ACCT_CLIENT> fUND_COMP_CUST_ACCT_CLIENT=new ArrayList<>();

	public List<FUND_COMP_CUST_ACCT_CLIENT> getfUND_COMP_CUST_ACCT_CLIENT() {
		return fUND_COMP_CUST_ACCT_CLIENT;
	}
	public void setfUND_COMP_CUST_ACCT_CLIENT(List<FUND_COMP_CUST_ACCT_CLIENT> fUND_COMP_CUST_ACCT_CLIENT) {
		this.fUND_COMP_CUST_ACCT_CLIENT = fUND_COMP_CUST_ACCT_CLIENT;
	}
	/*
	 * Interest
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="rE_INVESTOR",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<FUND_MAR_INTEREST_CLIENT> fUND_MAR_INTEREST_CLIENT=new ArrayList<>();
	
	public List<FUND_MAR_INTEREST_CLIENT> getfUND_MAR_INTEREST_CLIENT() {
		return fUND_MAR_INTEREST_CLIENT;
	}
	public void setfUND_MAR_INTEREST_CLIENT(List<FUND_MAR_INTEREST_CLIENT> fUND_MAR_INTEREST_CLIENT) {
		this.fUND_MAR_INTEREST_CLIENT = fUND_MAR_INTEREST_CLIENT;
	}
	
	
	public Long getRI_INVESTOR_CODE() {
		return RI_INVESTOR_CODE;
	}
	public void setRI_INVESTOR_CODE(Long rI_INVESTOR_CODE) {
		RI_INVESTOR_CODE = rI_INVESTOR_CODE;
	}
	public String getRI_INVESTOR_TYPE() {
		return RI_INVESTOR_TYPE;
	}
	public void setRI_INVESTOR_TYPE(String rI_INVESTOR_TYPE) {
		RI_INVESTOR_TYPE = rI_INVESTOR_TYPE;
	}
	public Calendar getRI_CREATE_DATE() {
		return RI_CREATE_DATE;
	}
	public void setRI_CREATE_DATE(Calendar rI_CREATE_DATE) {
		RI_CREATE_DATE = rI_CREATE_DATE;
	}
	public String getRI_INVESTOR_NAME() {
		return RI_INVESTOR_NAME;
	}
	public void setRI_INVESTOR_NAME(String rI_INVESTOR_NAME) {
		RI_INVESTOR_NAME = rI_INVESTOR_NAME;
	}
	public String getRI_ADDRESS_1() {
		return RI_ADDRESS_1;
	}
	public void setRI_ADDRESS_1(String rI_ADDRESS_1) {
		RI_ADDRESS_1 = rI_ADDRESS_1;
	}
	public String getRI_POST_BOX() {
		return RI_POST_BOX;
	}
	public void setRI_POST_BOX(String rI_POST_BOX) {
		RI_POST_BOX = rI_POST_BOX;
	}
	public String getRI_POST_CODE() {
		return RI_POST_CODE;
	}
	public void setRI_POST_CODE(String rI_POST_CODE) {
		RI_POST_CODE = rI_POST_CODE;
	}
	public String getRI_CITY() {
		return RI_CITY;
	}
	public void setRI_CITY(String rI_CITY) {
		RI_CITY = rI_CITY;
	}
	public String getRI_COUNTRY() {
		return RI_COUNTRY;
	}
	public void setRI_COUNTRY(String rI_COUNTRY) {
		RI_COUNTRY = rI_COUNTRY;
	}
	public String getRI_TEL_NO() {
		return RI_TEL_NO;
	}
	public void setRI_TEL_NO(String rI_TEL_NO) {
		RI_TEL_NO = rI_TEL_NO;
	}
	public String getRI_MOBILE_NO() {
		return RI_MOBILE_NO;
	}
	public void setRI_MOBILE_NO(String rI_MOBILE_NO) {
		RI_MOBILE_NO = rI_MOBILE_NO;
	}
	public String getRI_FAX_NO() {
		return RI_FAX_NO;
	}
	public void setRI_FAX_NO(String rI_FAX_NO) {
		RI_FAX_NO = rI_FAX_NO;
	}
	public String getRI_EMAIL() {
		return RI_EMAIL;
	}
	public void setRI_EMAIL(String rI_EMAIL) {
		RI_EMAIL = rI_EMAIL;
	}
	public String getRI_COMPANY_NAME() {
		return RI_COMPANY_NAME;
	}
	public void setRI_COMPANY_NAME(String rI_COMPANY_NAME) {
		RI_COMPANY_NAME = rI_COMPANY_NAME;
	}
	public String getRI_COMPANY_LICENSE_NO() {
		return RI_COMPANY_LICENSE_NO;
	}
	public void setRI_COMPANY_LICENSE_NO(String rI_COMPANY_LICENSE_NO) {
		RI_COMPANY_LICENSE_NO = rI_COMPANY_LICENSE_NO;
	}
	public Calendar getRI_EXPIRY_DATE() {
		return RI_EXPIRY_DATE;
	}
	public void setRI_EXPIRY_DATE(Calendar rI_EXPIRY_DATE) {
		RI_EXPIRY_DATE = rI_EXPIRY_DATE;
	}
	public String getRI_OCCUPATION() {
		return RI_OCCUPATION;
	}
	public void setRI_OCCUPATION(String rI_OCCUPATION) {
		RI_OCCUPATION = rI_OCCUPATION;
	}
	public String getRI_NATIONALITY() {
		return RI_NATIONALITY;
	}
	public void setRI_NATIONALITY(String rI_NATIONALITY) {
		RI_NATIONALITY = rI_NATIONALITY;
	}
	public Calendar getRI_DOB() {
		return RI_DOB;
	}
	public void setRI_DOB(Calendar rI_DOB) {
		RI_DOB = rI_DOB;
	}
	public String getRI_GENDER() {
		return RI_GENDER;
	}
	public void setRI_GENDER(String rI_GENDER) {
		RI_GENDER = rI_GENDER;
	}
	public String getRI_JOINT_AUTHORISATION() {
		return RI_JOINT_AUTHORISATION;
	}
	public void setRI_JOINT_AUTHORISATION(String rI_JOINT_AUTHORISATION) {
		RI_JOINT_AUTHORISATION = rI_JOINT_AUTHORISATION;
	}
	public String getRI_STATUS() {
		return RI_STATUS;
	}
	public void setRI_STATUS(String rI_STATUS) {
		RI_STATUS = rI_STATUS;
	}
	public String getSVC_UID() {
		return SVC_UID;
	}
	public void setSVC_UID(String sVC_UID) {
		SVC_UID = sVC_UID;
	}
	public String getRI_INDUSTRY() {
		return RI_INDUSTRY;
	}
	public void setRI_INDUSTRY(String rI_INDUSTRY) {
		RI_INDUSTRY = rI_INDUSTRY;
	}
	public String getRI_REMARKS() {
		return RI_REMARKS;
	}
	public void setRI_REMARKS(String rI_REMARKS) {
		RI_REMARKS = rI_REMARKS;
	}
	public String getRI_MARITAL_STATUS() {
		return RI_MARITAL_STATUS;
	}
	public void setRI_MARITAL_STATUS(String rI_MARITAL_STATUS) {
		RI_MARITAL_STATUS = rI_MARITAL_STATUS;
	}
	public String getRI_MARITAL_DEPENDENTS() {
		return RI_MARITAL_DEPENDENTS;
	}
	public void setRI_MARITAL_DEPENDENTS(String rI_MARITAL_DEPENDENTS) {
		RI_MARITAL_DEPENDENTS = rI_MARITAL_DEPENDENTS;
	}
	public String getRI_NATIONAL_IDENITY() {
		return RI_NATIONAL_IDENITY;
	}
	public void setRI_NATIONAL_IDENITY(String rI_NATIONAL_IDENITY) {
		RI_NATIONAL_IDENITY = rI_NATIONAL_IDENITY;
	}
	public String getRI_PASSPORT_IPLACE() {
		return RI_PASSPORT_IPLACE;
	}
	public void setRI_PASSPORT_IPLACE(String rI_PASSPORT_IPLACE) {
		RI_PASSPORT_IPLACE = rI_PASSPORT_IPLACE;
	}
	public Calendar getRI_PASSPORT_IDATE() {
		return RI_PASSPORT_IDATE;
	}
	public void setRI_PASSPORT_IDATE(Calendar rI_PASSPORT_IDATE) {
		RI_PASSPORT_IDATE = rI_PASSPORT_IDATE;
	}
	public String getRI_PASSPORT_ID() {
		return RI_PASSPORT_ID;
	}
	public void setRI_PASSPORT_ID(String rI_PASSPORT_ID) {
		RI_PASSPORT_ID = rI_PASSPORT_ID;
	}
	public String getRI_INVESTOR_NAME2() {
		return RI_INVESTOR_NAME2;
	}
	public void setRI_INVESTOR_NAME2(String rI_INVESTOR_NAME2) {
		RI_INVESTOR_NAME2 = rI_INVESTOR_NAME2;
	}
	public String getRI_COMPANY_NAME2() {
		return RI_COMPANY_NAME2;
	}
	public void setRI_COMPANY_NAME2(String rI_COMPANY_NAME2) {
		RI_COMPANY_NAME2 = rI_COMPANY_NAME2;
	}
	public String getRI_OCCUPATION2() {
		return RI_OCCUPATION2;
	}
	public void setRI_OCCUPATION2(String rI_OCCUPATION2) {
		RI_OCCUPATION2 = rI_OCCUPATION2;
	}
	public String getRI_NATIONALITY2() {
		return RI_NATIONALITY2;
	}
	public void setRI_NATIONALITY2(String rI_NATIONALITY2) {
		RI_NATIONALITY2 = rI_NATIONALITY2;
	}
	public Calendar getRI_DOB2() {
		return RI_DOB2;
	}
	public void setRI_DOB2(Calendar rI_DOB2) {
		RI_DOB2 = rI_DOB2;
	}
	public String getRI_GENDER2() {
		return RI_GENDER2;
	}
	public void setRI_GENDER2(String rI_GENDER2) {
		RI_GENDER2 = rI_GENDER2;
	}
	public String getRI_MARITAL_STATUS2() {
		return RI_MARITAL_STATUS2;
	}
	public void setRI_MARITAL_STATUS2(String rI_MARITAL_STATUS2) {
		RI_MARITAL_STATUS2 = rI_MARITAL_STATUS2;
	}
	public String getRI_MARITAL_DEPENDENTS2() {
		return RI_MARITAL_DEPENDENTS2;
	}
	public void setRI_MARITAL_DEPENDENTS2(String rI_MARITAL_DEPENDENTS2) {
		RI_MARITAL_DEPENDENTS2 = rI_MARITAL_DEPENDENTS2;
	}
	public String getRI_PASSPORT_IPLACE2() {
		return RI_PASSPORT_IPLACE2;
	}
	public void setRI_PASSPORT_IPLACE2(String rI_PASSPORT_IPLACE2) {
		RI_PASSPORT_IPLACE2 = rI_PASSPORT_IPLACE2;
	}
	public Calendar getRI_PASSPORT_IDATE2() {
		return RI_PASSPORT_IDATE2;
	}
	public void setRI_PASSPORT_IDATE2(Calendar rI_PASSPORT_IDATE2) {
		RI_PASSPORT_IDATE2 = rI_PASSPORT_IDATE2;
	}
	public String getRI_NATIONAL_IDENITY2() {
		return RI_NATIONAL_IDENITY2;
	}
	public void setRI_NATIONAL_IDENITY2(String rI_NATIONAL_IDENITY2) {
		RI_NATIONAL_IDENITY2 = rI_NATIONAL_IDENITY2;
	}
	public String getRI_PASSPORT_ID2() {
		return RI_PASSPORT_ID2;
	}
	public void setRI_PASSPORT_ID2(String rI_PASSPORT_ID2) {
		RI_PASSPORT_ID2 = rI_PASSPORT_ID2;
	}
	public String getRI_PRIMARY_CONTACT_NAME() {
		return RI_PRIMARY_CONTACT_NAME;
	}
	public void setRI_PRIMARY_CONTACT_NAME(String rI_PRIMARY_CONTACT_NAME) {
		RI_PRIMARY_CONTACT_NAME = rI_PRIMARY_CONTACT_NAME;
	}
	public String getRI_OFFICE_APART_NO() {
		return RI_OFFICE_APART_NO;
	}
	public void setRI_OFFICE_APART_NO(String rI_OFFICE_APART_NO) {
		RI_OFFICE_APART_NO = rI_OFFICE_APART_NO;
	}
	public String getRI_STREET() {
		return RI_STREET;
	}
	public void setRI_STREET(String rI_STREET) {
		RI_STREET = rI_STREET;
	}
	public String getRI_AREA() {
		return RI_AREA;
	}
	public void setRI_AREA(String rI_AREA) {
		RI_AREA = rI_AREA;
	}
	public String getRI_TEL_NO_HOME() {
		return RI_TEL_NO_HOME;
	}
	public void setRI_TEL_NO_HOME(String rI_TEL_NO_HOME) {
		RI_TEL_NO_HOME = rI_TEL_NO_HOME;
	}
	public String getRI_PRIMARY_CONTACT_NAME2() {
		return RI_PRIMARY_CONTACT_NAME2;
	}
	public void setRI_PRIMARY_CONTACT_NAME2(String rI_PRIMARY_CONTACT_NAME2) {
		RI_PRIMARY_CONTACT_NAME2 = rI_PRIMARY_CONTACT_NAME2;
	}
	public String getRI_ADDRESS_12() {
		return RI_ADDRESS_12;
	}
	public void setRI_ADDRESS_12(String rI_ADDRESS_12) {
		RI_ADDRESS_12 = rI_ADDRESS_12;
	}
	public String getRI_OFFICE_APART_NO2() {
		return RI_OFFICE_APART_NO2;
	}
	public void setRI_OFFICE_APART_NO2(String rI_OFFICE_APART_NO2) {
		RI_OFFICE_APART_NO2 = rI_OFFICE_APART_NO2;
	}
	public String getRI_STREET2() {
		return RI_STREET2;
	}
	public void setRI_STREET2(String rI_STREET2) {
		RI_STREET2 = rI_STREET2;
	}
	public String getRI_AREA2() {
		return RI_AREA2;
	}
	public void setRI_AREA2(String rI_AREA2) {
		RI_AREA2 = rI_AREA2;
	}
	public String getRI_POST_BOX2() {
		return RI_POST_BOX2;
	}
	public void setRI_POST_BOX2(String rI_POST_BOX2) {
		RI_POST_BOX2 = rI_POST_BOX2;
	}
	public String getRI_CITY2() {
		return RI_CITY2;
	}
	public void setRI_CITY2(String rI_CITY2) {
		RI_CITY2 = rI_CITY2;
	}
	public String getRI_COUNTRY2() {
		return RI_COUNTRY2;
	}
	public void setRI_COUNTRY2(String rI_COUNTRY2) {
		RI_COUNTRY2 = rI_COUNTRY2;
	}
	public String getRI_POST_CODE2() {
		return RI_POST_CODE2;
	}
	public void setRI_POST_CODE2(String rI_POST_CODE2) {
		RI_POST_CODE2 = rI_POST_CODE2;
	}
	public String getRI_TEL_NO2() {
		return RI_TEL_NO2;
	}
	public void setRI_TEL_NO2(String rI_TEL_NO2) {
		RI_TEL_NO2 = rI_TEL_NO2;
	}
	public String getRI_TEL_NO_HOME2() {
		return RI_TEL_NO_HOME2;
	}
	public void setRI_TEL_NO_HOME2(String rI_TEL_NO_HOME2) {
		RI_TEL_NO_HOME2 = rI_TEL_NO_HOME2;
	}
	public String getRI_MOBILE_NO2() {
		return RI_MOBILE_NO2;
	}
	public void setRI_MOBILE_NO2(String rI_MOBILE_NO2) {
		RI_MOBILE_NO2 = rI_MOBILE_NO2;
	}
	public String getRI_FAX_NO2() {
		return RI_FAX_NO2;
	}
	public void setRI_FAX_NO2(String rI_FAX_NO2) {
		RI_FAX_NO2 = rI_FAX_NO2;
	}
	public String getRI_EMAIL2() {
		return RI_EMAIL2;
	}
	public void setRI_EMAIL2(String rI_EMAIL2) {
		RI_EMAIL2 = rI_EMAIL2;
	}
	public Calendar getRI_EXPIRY_DATE2() {
		return RI_EXPIRY_DATE2;
	}
	public void setRI_EXPIRY_DATE2(Calendar rI_EXPIRY_DATE2) {
		RI_EXPIRY_DATE2 = rI_EXPIRY_DATE2;
	}
	public String getRI_BANK_ANAME() {
		return RI_BANK_ANAME;
	}
	public void setRI_BANK_ANAME(String rI_BANK_ANAME) {
		RI_BANK_ANAME = rI_BANK_ANAME;
	}
	public String getRI_BANK_ANUMBER() {
		return RI_BANK_ANUMBER;
	}
	public void setRI_BANK_ANUMBER(String rI_BANK_ANUMBER) {
		RI_BANK_ANUMBER = rI_BANK_ANUMBER;
	}
	public String getRI_BANK_BANK_NAME() {
		return RI_BANK_BANK_NAME;
	}
	public void setRI_BANK_BANK_NAME(String rI_BANK_BANK_NAME) {
		RI_BANK_BANK_NAME = rI_BANK_BANK_NAME;
	}
	public String getRI_BANK_CURRENCY() {
		return RI_BANK_CURRENCY;
	}
	public void setRI_BANK_CURRENCY(String rI_BANK_CURRENCY) {
		RI_BANK_CURRENCY = rI_BANK_CURRENCY;
	}
	public String getRI_BANK_BRANCH() {
		return RI_BANK_BRANCH;
	}
	public void setRI_BANK_BRANCH(String rI_BANK_BRANCH) {
		RI_BANK_BRANCH = rI_BANK_BRANCH;
	}
	public String getRI_BANK_COUNTRY() {
		return RI_BANK_COUNTRY;
	}
	public void setRI_BANK_COUNTRY(String rI_BANK_COUNTRY) {
		RI_BANK_COUNTRY = rI_BANK_COUNTRY;
	}
	public String getRI_BANK_CITY() {
		return RI_BANK_CITY;
	}
	public void setRI_BANK_CITY(String rI_BANK_CITY) {
		RI_BANK_CITY = rI_BANK_CITY;
	}
	public String getRI_NOTI_FAX() {
		return RI_NOTI_FAX;
	}
	public void setRI_NOTI_FAX(String rI_NOTI_FAX) {
		RI_NOTI_FAX = rI_NOTI_FAX;
	}
	public String getRI_NOTI_SMS() {
		return RI_NOTI_SMS;
	}
	public void setRI_NOTI_SMS(String rI_NOTI_SMS) {
		RI_NOTI_SMS = rI_NOTI_SMS;
	}
	public String getRI_NOTI_MAIL() {
		return RI_NOTI_MAIL;
	}
	public void setRI_NOTI_MAIL(String rI_NOTI_MAIL) {
		RI_NOTI_MAIL = rI_NOTI_MAIL;
	}
	public String getRI_NOTI_EMAIL() {
		return RI_NOTI_EMAIL;
	}
	public void setRI_NOTI_EMAIL(String rI_NOTI_EMAIL) {
		RI_NOTI_EMAIL = rI_NOTI_EMAIL;
	}
	public Long getRI_MARGIN_TYPE() {
		return RI_MARGIN_TYPE;
	}
	public void setRI_MARGIN_TYPE(Long rI_MARGIN_TYPE) {
		RI_MARGIN_TYPE = rI_MARGIN_TYPE;
	}
	public Long getRI_INTEREST_TYPE() {
		return RI_INTEREST_TYPE;
	}
	public void setRI_INTEREST_TYPE(Long rI_INTEREST_TYPE) {
		RI_INTEREST_TYPE = rI_INTEREST_TYPE;
	}
	public Long getRI_RELATION_MANAGER() {
		return RI_RELATION_MANAGER;
	}
	public void setRI_RELATION_MANAGER(Long rI_RELATION_MANAGER) {
		RI_RELATION_MANAGER = rI_RELATION_MANAGER;
	}
	public String getRI_CORPORATE_NAME() {
		return RI_CORPORATE_NAME;
	}
	public void setRI_CORPORATE_NAME(String rI_CORPORATE_NAME) {
		RI_CORPORATE_NAME = rI_CORPORATE_NAME;
	}
	public String getRI_BUSINESS_ACTIVITY() {
		return RI_BUSINESS_ACTIVITY;
	}
	public void setRI_BUSINESS_ACTIVITY(String rI_BUSINESS_ACTIVITY) {
		RI_BUSINESS_ACTIVITY = rI_BUSINESS_ACTIVITY;
	}
	public String getRI_COUNTRY_ESTA() {
		return RI_COUNTRY_ESTA;
	}
	public void setRI_COUNTRY_ESTA(String rI_COUNTRY_ESTA) {
		RI_COUNTRY_ESTA = rI_COUNTRY_ESTA;
	}
	public String getRI_COMMERCIAL_REGIS_NO() {
		return RI_COMMERCIAL_REGIS_NO;
	}
	public void setRI_COMMERCIAL_REGIS_NO(String rI_COMMERCIAL_REGIS_NO) {
		RI_COMMERCIAL_REGIS_NO = rI_COMMERCIAL_REGIS_NO;
	}
	public String getRI_TRADE_LICENSE_NO() {
		return RI_TRADE_LICENSE_NO;
	}
	public void setRI_TRADE_LICENSE_NO(String rI_TRADE_LICENSE_NO) {
		RI_TRADE_LICENSE_NO = rI_TRADE_LICENSE_NO;
	}
	public Calendar getRI_TRADE_LICENSE_IDATE() {
		return RI_TRADE_LICENSE_IDATE;
	}
	public void setRI_TRADE_LICENSE_IDATE(Calendar rI_TRADE_LICENSE_IDATE) {
		RI_TRADE_LICENSE_IDATE = rI_TRADE_LICENSE_IDATE;
	}
	public Calendar getRI_TRADE_LICENSE_EDATE() {
		return RI_TRADE_LICENSE_EDATE;
	}
	public void setRI_TRADE_LICENSE_EDATE(Calendar rI_TRADE_LICENSE_EDATE) {
		RI_TRADE_LICENSE_EDATE = rI_TRADE_LICENSE_EDATE;
	}
	public String getRI_AUTHORIZED_NAME() {
		return RI_AUTHORIZED_NAME;
	}
	public void setRI_AUTHORIZED_NAME(String rI_AUTHORIZED_NAME) {
		RI_AUTHORIZED_NAME = rI_AUTHORIZED_NAME;
	}
	public String getRI_CAPACITY() {
		return RI_CAPACITY;
	}
	public void setRI_CAPACITY(String rI_CAPACITY) {
		RI_CAPACITY = rI_CAPACITY;
	}
	public String getRI_MARGIN_CLIENT_FLAG() {
		return RI_MARGIN_CLIENT_FLAG;
	}
	public void setRI_MARGIN_CLIENT_FLAG(String rI_MARGIN_CLIENT_FLAG) {
		RI_MARGIN_CLIENT_FLAG = rI_MARGIN_CLIENT_FLAG;
	}
	public Long getRI_MARGIN_REPORT_CURRENCY() {
		return RI_MARGIN_REPORT_CURRENCY;
	}
	public void setRI_MARGIN_REPORT_CURRENCY(Long rI_MARGIN_REPORT_CURRENCY) {
		RI_MARGIN_REPORT_CURRENCY = rI_MARGIN_REPORT_CURRENCY;
	}
	public Calendar getRI_MARGIN_AGGDATE() {
		return RI_MARGIN_AGGDATE;
	}
	public void setRI_MARGIN_AGGDATE(Calendar rI_MARGIN_AGGDATE) {
		RI_MARGIN_AGGDATE = rI_MARGIN_AGGDATE;
	}
	public String getRI_RELATION_ALMAL() {
		return RI_RELATION_ALMAL;
	}
	public void setRI_RELATION_ALMAL(String rI_RELATION_ALMAL) {
		RI_RELATION_ALMAL = rI_RELATION_ALMAL;
	}
	public String getRI_REFFER_BY() {
		return RI_REFFER_BY;
	}
	public void setRI_REFFER_BY(String rI_REFFER_BY) {
		RI_REFFER_BY = rI_REFFER_BY;
	}
	public String getRI_PMR_DAY() {
		return RI_PMR_DAY;
	}
	public void setRI_PMR_DAY(String rI_PMR_DAY) {
		RI_PMR_DAY = rI_PMR_DAY;
	}
	public String getRI_PMR_WEEKLY() {
		return RI_PMR_WEEKLY;
	}
	public void setRI_PMR_WEEKLY(String rI_PMR_WEEKLY) {
		RI_PMR_WEEKLY = rI_PMR_WEEKLY;
	}
	public String getRI_PMR_MONTH() {
		return RI_PMR_MONTH;
	}
	public void setRI_PMR_MONTH(String rI_PMR_MONTH) {
		RI_PMR_MONTH = rI_PMR_MONTH;
	}
	public double getRI_MARKET_AMT() {
		return RI_MARKET_AMT;
	}
	public void setRI_MARKET_AMT(double rI_MARKET_AMT) {
		RI_MARKET_AMT = rI_MARKET_AMT;
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
	public Long getRI_MARGIN_SUB_TYPE() {
		return RI_MARGIN_SUB_TYPE;
	}
	public void setRI_MARGIN_SUB_TYPE(Long rI_MARGIN_SUB_TYPE) {
		RI_MARGIN_SUB_TYPE = rI_MARGIN_SUB_TYPE;
	}
	public String getRI_OWN_OR_CLIENT() {
		return RI_OWN_OR_CLIENT;
	}
	public void setRI_OWN_OR_CLIENT(String rI_OWN_OR_CLIENT) {
		RI_OWN_OR_CLIENT = rI_OWN_OR_CLIENT;
	}
	public String getRI_MONTH_END_REP_FLAG() {
		return RI_MONTH_END_REP_FLAG;
	}
	public void setRI_MONTH_END_REP_FLAG(String rI_MONTH_END_REP_FLAG) {
		RI_MONTH_END_REP_FLAG = rI_MONTH_END_REP_FLAG;
	}
	public String getRI_INTEREST_POST() {
		return RI_INTEREST_POST;
	}
	public void setRI_INTEREST_POST(String rI_INTEREST_POST) {
		RI_INTEREST_POST = rI_INTEREST_POST;
	}
	public String getRI_INTEREST_REMARKS() {
		return RI_INTEREST_REMARKS;
	}
	public void setRI_INTEREST_REMARKS(String rI_INTEREST_REMARKS) {
		RI_INTEREST_REMARKS = rI_INTEREST_REMARKS;
	}
	public String getRI_ACCOUNT_CLOSE() {
		return RI_ACCOUNT_CLOSE;
	}
	public void setRI_ACCOUNT_CLOSE(String rI_ACCOUNT_CLOSE) {
		RI_ACCOUNT_CLOSE = rI_ACCOUNT_CLOSE;
	}
	public String getRI_ACCOUNT_CLOSE_REMARKS() {
		return RI_ACCOUNT_CLOSE_REMARKS;
	}
	public void setRI_ACCOUNT_CLOSE_REMARKS(String rI_ACCOUNT_CLOSE_REMARKS) {
		RI_ACCOUNT_CLOSE_REMARKS = rI_ACCOUNT_CLOSE_REMARKS;
	}
	public String getRI_SALVATION() {
		return RI_SALVATION;
	}
	public void setRI_SALVATION(String rI_SALVATION) {
		RI_SALVATION = rI_SALVATION;
	}
	public String getRI_FIRST_NAME() {
		return RI_FIRST_NAME;
	}
	public void setRI_FIRST_NAME(String rI_FIRST_NAME) {
		RI_FIRST_NAME = rI_FIRST_NAME;
	}
	public String getRI_LAST_NAME() {
		return RI_LAST_NAME;
	}
	public void setRI_LAST_NAME(String rI_LAST_NAME) {
		RI_LAST_NAME = rI_LAST_NAME;
	}
	public String getRI_MIDDLE_NAME() {
		return RI_MIDDLE_NAME;
	}
	public void setRI_MIDDLE_NAME(String rI_MIDDLE_NAME) {
		RI_MIDDLE_NAME = rI_MIDDLE_NAME;
	}
	public String getRI_SUFFIX() {
		return RI_SUFFIX;
	}
	public void setRI_SUFFIX(String rI_SUFFIX) {
		RI_SUFFIX = rI_SUFFIX;
	}
	public String getRI_JOINT_SALVATION() {
		return RI_JOINT_SALVATION;
	}
	public void setRI_JOINT_SALVATION(String rI_JOINT_SALVATION) {
		RI_JOINT_SALVATION = rI_JOINT_SALVATION;
	}
	public String getRI_JOINT_FIRST_NAME() {
		return RI_JOINT_FIRST_NAME;
	}
	public void setRI_JOINT_FIRST_NAME(String rI_JOINT_FIRST_NAME) {
		RI_JOINT_FIRST_NAME = rI_JOINT_FIRST_NAME;
	}
	public String getRI_JOINT_LAST_NAME() {
		return RI_JOINT_LAST_NAME;
	}
	public void setRI_JOINT_LAST_NAME(String rI_JOINT_LAST_NAME) {
		RI_JOINT_LAST_NAME = rI_JOINT_LAST_NAME;
	}
	public String getRI_JOINT_MIDDLE_NAME() {
		return RI_JOINT_MIDDLE_NAME;
	}
	public void setRI_JOINT_MIDDLE_NAME(String rI_JOINT_MIDDLE_NAME) {
		RI_JOINT_MIDDLE_NAME = rI_JOINT_MIDDLE_NAME;
	}
	public String getRI_JOINT_SUFFIX() {
		return RI_JOINT_SUFFIX;
	}
	public void setRI_JOINT_SUFFIX(String rI_JOINT_SUFFIX) {
		RI_JOINT_SUFFIX = rI_JOINT_SUFFIX;
	}
	public String getRI_CORP_CD_SALVATION() {
		return RI_CORP_CD_SALVATION;
	}
	public void setRI_CORP_CD_SALVATION(String rI_CORP_CD_SALVATION) {
		RI_CORP_CD_SALVATION = rI_CORP_CD_SALVATION;
	}
	public String getRI_CORP_CD_FIRST_NAME() {
		return RI_CORP_CD_FIRST_NAME;
	}
	public void setRI_CORP_CD_FIRST_NAME(String rI_CORP_CD_FIRST_NAME) {
		RI_CORP_CD_FIRST_NAME = rI_CORP_CD_FIRST_NAME;
	}
	public String getRI_CORP_CD_LAST_NAME() {
		return RI_CORP_CD_LAST_NAME;
	}
	public void setRI_CORP_CD_LAST_NAME(String rI_CORP_CD_LAST_NAME) {
		RI_CORP_CD_LAST_NAME = rI_CORP_CD_LAST_NAME;
	}
	public String getRI_CORP_CD_MIDDLE_NAME() {
		return RI_CORP_CD_MIDDLE_NAME;
	}
	public void setRI_CORP_CD_MIDDLE_NAME(String rI_CORP_CD_MIDDLE_NAME) {
		RI_CORP_CD_MIDDLE_NAME = rI_CORP_CD_MIDDLE_NAME;
	}
	public String getRI_CORP_CD_SUFFIX() {
		return RI_CORP_CD_SUFFIX;
	}
	public void setRI_CORP_CD_SUFFIX(String rI_CORP_CD_SUFFIX) {
		RI_CORP_CD_SUFFIX = rI_CORP_CD_SUFFIX;
	}
	public Calendar getRI_ACCOUNT_CLOSE_DATE() {
		return RI_ACCOUNT_CLOSE_DATE;
	}
	public void setRI_ACCOUNT_CLOSE_DATE(Calendar rI_ACCOUNT_CLOSE_DATE) {
		RI_ACCOUNT_CLOSE_DATE = rI_ACCOUNT_CLOSE_DATE;
	}
	public double getRI_CRATE() {
		return RI_CRATE;
	}
	public void setRI_CRATE(double rI_CRATE) {
		RI_CRATE = rI_CRATE;
	}
	public String getWMS_COMMENTS() {
		return WMS_COMMENTS;
	}
	public void setWMS_COMMENTS(String wMS_COMMENTS) {
		WMS_COMMENTS = wMS_COMMENTS;
	}
	public String getWMS_STATUS() {
		return WMS_STATUS;
	}
	public void setWMS_STATUS(String wMS_STATUS) {
		WMS_STATUS = wMS_STATUS;
	}
	public static Long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
