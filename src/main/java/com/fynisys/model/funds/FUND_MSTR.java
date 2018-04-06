package com.fynisys.model.funds;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
/*
 * 
  SVC_DATE                       Record Creation Date 
  SVC_CODE                       Auto Number
  SVC_MODULE                     Type of Fund List box (Fund Managment,Investment Banking,Portfolio Management,Private Equity,Proprietary Investments, ) 
  SVC_NAME                       Fund Name
  SVC_SHORT_NAME                 Fund Short Name 
  SVC_SHARE_PRICE	             NAV 
  SVC_CURRECNY                   Fund Currency

  SVC_FUND_MANAGER               Contact Person
  SVC_PHONE                      Phone
  SVC_FAX                        Fax
  SVC_EMAIL                      Email
  SVC_FUND_IPO			         Mobile no

  SVC_PB                         Post box
  SVC_PC                         Post code
  SVC_STATE                      State
  SVC_COUNTRY                    Country
  SVC_REMARKS			         Address
 
  SVC_FUND_TYPE 		         FLAG	
  SVC_TERM                       Duration
  SVC_START_DATE                 Issue Date 
  SVC_END_DATE                   End DATE
  SVC_MIN_INVESTMENT             Min Investment
  SVC_MAX_INVESTMENT             Max Investment
 * 
 */
@Entity(name="FUND_MSTR")
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames="SVC_NAME",name="UK_FUND_MSTR_SVC_NAME"),
		@UniqueConstraint(columnNames="SVC_SHORT_NAME",name="UK_SVC_SHORT_NAME")
})
public class FUND_MSTR implements Serializable{

	private static final long serialVersionUID = 1L;
	  @Id
	  @GeneratedValue
	  private long SVC_CODE;
	  private Calendar SVC_DATE; 
	  @Column(length=25)
	  private String SVC_FUND_TYPE; 
	  @Column(length=50)
	  private String SVC_NAME; 
	  @Column(length=50)
	  private String SVC_ANAME;
	  @Column(length=10)
	  private String SVC_SHORT_NAME;
	  @Column(length=25)
	  private String SVC_PB;
	  @Column(length=25)
	  private String SVC_PC;
	  @Column(length=50)
	  private String SVC_STATE;
	  @Column(length=50)
	  private String SVC_COUNTRY; 
	  @Column(length=25)
	  private String SVC_PHONE; 
	  @Column(length=25)
	  private String SVC_FAX; 
	  @Column(length=50)
	  private String SVC_EMAIL; 
	  @Column(length=10)
	  private String SVC_CURRECNY; 
	  @Column(length=25)
	  private String SVC_EXCHANGE; 
	  @Column(precision=25,scale=0)
	  private double SVC_NO_OF_UNITS; 
	  @Column(precision=20,scale=3)
	  private double SVC_TOTAL_VALUE; 
	  @Column(precision=10,scale=3)
	  private double SVC_SHARE_PRICE; 
	  @Column(length=50)
	  private String SVC_REMARKS; 
	  private Calendar SVC_LAUNCH_DATE; 
	  private Calendar SVC_START_DATE; 
	  private Calendar SVC_END_DATE; 
	  private int SVC_TERM; 
	  @Column(precision=20,scale=3)
	  private double SVC_MIN_INVESTMENT; 
	  @Column(precision=10,scale=3)
	  private double SVC_PROFIT_RATE; 
	  private int SVC_PROFIT_PERIOD; 
	  private int SVC_TERM_EXT; 
	  @Column(length=1)
	  private String SVC_CAPITAL_REDEMPTION_FLAG; 
	  private long SVC_CAPITAL_REDEMPTION_PERIOD; 
	  private Calendar SVC_ALLOTMENT_DATE;
	  @Column(length=50)
	  private String SVC_FUND_MANAGER; 
	  @Column(length=25)
	  private String SVC_FUND_IPO; 
	  private int SVC_MODULE; 
	  @Column(length=2)
	  private String SVC_STATUS; 
	  @Column(length=50)
	  private String SVC_UID ; 
	  @Column(precision=20,scale=3)
	  private double SVC_MAX_INVESTMENT; 
	  @Column(length=3)
	  private String SVC_BANCS_CODE ;
	  
	  @Column(length=20)
	  private String WMS_ENTER_UID;
	  @Column(length=30)
	  private String WMS_ENTER_FPC;
	  private Calendar WMS_ENTER_DATE;
	  
	  @Column(length=20)
	  private String WMS_LAST_UPDATE_UID;
	  @Column(length=30)
	  private String WMS_LAST_FPC;
	  private Calendar WMS_LAST_UPDATE_DATE;
	  
	  @Column(length=20)
	  private String WMS_APPROVE_UID;
	  @Column(length=30)
	  private String WMS_APPROVE_FPC;
	  private Calendar WMS_APPROVE_DATE;
	  	 /*
		  * Standard Fields
		  */
		  @Column(length=119)
		  private String IV_ENTER_UID;         	//VARCHAR2 (119), 
		  private Calendar IV_ENTER_DATE;       //DATE, 
		  @Column(length=119)
		  private String IV_APPROVE_UID;       	//VARCHAR2 (119), 
		  private Calendar IV_APPROVE_DATE;     //DATE,
		  @Column(length=119)
		  private String IV_LAST_UPDATE_UID;   	//VARCHAR2 (119),
		  private Calendar IV_LAST_UPDATE_DATE; //DATE	  
		  @Column(length=300)
		  private String WMS_COMMENTS;
		  @Column(length=20)
		  private String WMS_STATUS;
		public long getSVC_CODE() {
			return SVC_CODE;
		}
		public void setSVC_CODE(long sVC_CODE) {
			SVC_CODE = sVC_CODE;
		}
		public Calendar getSVC_DATE() {
			return SVC_DATE;
		}
		public void setSVC_DATE(Calendar sVC_DATE) {
			SVC_DATE = sVC_DATE;
		}
		public String getSVC_FUND_TYPE() {
			return SVC_FUND_TYPE;
		}
		public void setSVC_FUND_TYPE(String sVC_FUND_TYPE) {
			SVC_FUND_TYPE = sVC_FUND_TYPE;
		}
		public String getSVC_NAME() {
			return SVC_NAME;
		}
		public void setSVC_NAME(String sVC_NAME) {
			SVC_NAME = sVC_NAME;
		}
		public String getSVC_ANAME() {
			return SVC_ANAME;
		}
		public void setSVC_ANAME(String sVC_ANAME) {
			SVC_ANAME = sVC_ANAME;
		}
		public String getSVC_SHORT_NAME() {
			return SVC_SHORT_NAME;
		}
		public void setSVC_SHORT_NAME(String sVC_SHORT_NAME) {
			SVC_SHORT_NAME = sVC_SHORT_NAME;
		}
		public String getSVC_PB() {
			return SVC_PB;
		}
		public void setSVC_PB(String sVC_PB) {
			SVC_PB = sVC_PB;
		}
		public String getSVC_PC() {
			return SVC_PC;
		}
		public void setSVC_PC(String sVC_PC) {
			SVC_PC = sVC_PC;
		}
		public String getSVC_STATE() {
			return SVC_STATE;
		}
		public void setSVC_STATE(String sVC_STATE) {
			SVC_STATE = sVC_STATE;
		}
		public String getSVC_COUNTRY() {
			return SVC_COUNTRY;
		}
		public void setSVC_COUNTRY(String sVC_COUNTRY) {
			SVC_COUNTRY = sVC_COUNTRY;
		}
		public String getSVC_PHONE() {
			return SVC_PHONE;
		}
		public void setSVC_PHONE(String sVC_PHONE) {
			SVC_PHONE = sVC_PHONE;
		}
		public String getSVC_FAX() {
			return SVC_FAX;
		}
		public void setSVC_FAX(String sVC_FAX) {
			SVC_FAX = sVC_FAX;
		}
		public String getSVC_EMAIL() {
			return SVC_EMAIL;
		}
		public void setSVC_EMAIL(String sVC_EMAIL) {
			SVC_EMAIL = sVC_EMAIL;
		}
		public String getSVC_CURRECNY() {
			return SVC_CURRECNY;
		}
		public void setSVC_CURRECNY(String sVC_CURRECNY) {
			SVC_CURRECNY = sVC_CURRECNY;
		}
		public String getSVC_EXCHANGE() {
			return SVC_EXCHANGE;
		}
		public void setSVC_EXCHANGE(String sVC_EXCHANGE) {
			SVC_EXCHANGE = sVC_EXCHANGE;
		}
		public double getSVC_NO_OF_UNITS() {
			return SVC_NO_OF_UNITS;
		}
		public void setSVC_NO_OF_UNITS(double sVC_NO_OF_UNITS) {
			SVC_NO_OF_UNITS = sVC_NO_OF_UNITS;
		}
		public double getSVC_TOTAL_VALUE() {
			return SVC_TOTAL_VALUE;
		}
		public void setSVC_TOTAL_VALUE(double sVC_TOTAL_VALUE) {
			SVC_TOTAL_VALUE = sVC_TOTAL_VALUE;
		}
		public double getSVC_SHARE_PRICE() {
			return SVC_SHARE_PRICE;
		}
		public void setSVC_SHARE_PRICE(double sVC_SHARE_PRICE) {
			SVC_SHARE_PRICE = sVC_SHARE_PRICE;
		}
		public String getSVC_REMARKS() {
			return SVC_REMARKS;
		}
		public void setSVC_REMARKS(String sVC_REMARKS) {
			SVC_REMARKS = sVC_REMARKS;
		}
		public Calendar getSVC_LAUNCH_DATE() {
			return SVC_LAUNCH_DATE;
		}
		public void setSVC_LAUNCH_DATE(Calendar sVC_LAUNCH_DATE) {
			SVC_LAUNCH_DATE = sVC_LAUNCH_DATE;
		}
		public Calendar getSVC_START_DATE() {
			return SVC_START_DATE;
		}
		public void setSVC_START_DATE(Calendar sVC_START_DATE) {
			SVC_START_DATE = sVC_START_DATE;
		}
		public Calendar getSVC_END_DATE() {
			return SVC_END_DATE;
		}
		public void setSVC_END_DATE(Calendar sVC_END_DATE) {
			SVC_END_DATE = sVC_END_DATE;
		}
		public int getSVC_TERM() {
			return SVC_TERM;
		}
		public void setSVC_TERM(int sVC_TERM) {
			SVC_TERM = sVC_TERM;
		}
		public double getSVC_MIN_INVESTMENT() {
			return SVC_MIN_INVESTMENT;
		}
		public void setSVC_MIN_INVESTMENT(double sVC_MIN_INVESTMENT) {
			SVC_MIN_INVESTMENT = sVC_MIN_INVESTMENT;
		}
		public double getSVC_PROFIT_RATE() {
			return SVC_PROFIT_RATE;
		}
		public void setSVC_PROFIT_RATE(double sVC_PROFIT_RATE) {
			SVC_PROFIT_RATE = sVC_PROFIT_RATE;
		}
		public int getSVC_PROFIT_PERIOD() {
			return SVC_PROFIT_PERIOD;
		}
		public void setSVC_PROFIT_PERIOD(int sVC_PROFIT_PERIOD) {
			SVC_PROFIT_PERIOD = sVC_PROFIT_PERIOD;
		}
		public int getSVC_TERM_EXT() {
			return SVC_TERM_EXT;
		}
		public void setSVC_TERM_EXT(int sVC_TERM_EXT) {
			SVC_TERM_EXT = sVC_TERM_EXT;
		}
		public String getSVC_CAPITAL_REDEMPTION_FLAG() {
			return SVC_CAPITAL_REDEMPTION_FLAG;
		}
		public void setSVC_CAPITAL_REDEMPTION_FLAG(String sVC_CAPITAL_REDEMPTION_FLAG) {
			SVC_CAPITAL_REDEMPTION_FLAG = sVC_CAPITAL_REDEMPTION_FLAG;
		}
		public long getSVC_CAPITAL_REDEMPTION_PERIOD() {
			return SVC_CAPITAL_REDEMPTION_PERIOD;
		}
		public void setSVC_CAPITAL_REDEMPTION_PERIOD(long sVC_CAPITAL_REDEMPTION_PERIOD) {
			SVC_CAPITAL_REDEMPTION_PERIOD = sVC_CAPITAL_REDEMPTION_PERIOD;
		}
		public Calendar getSVC_ALLOTMENT_DATE() {
			return SVC_ALLOTMENT_DATE;
		}
		public void setSVC_ALLOTMENT_DATE(Calendar sVC_ALLOTMENT_DATE) {
			SVC_ALLOTMENT_DATE = sVC_ALLOTMENT_DATE;
		}
		public String getSVC_FUND_MANAGER() {
			return SVC_FUND_MANAGER;
		}
		public void setSVC_FUND_MANAGER(String sVC_FUND_MANAGER) {
			SVC_FUND_MANAGER = sVC_FUND_MANAGER;
		}
		public String getSVC_FUND_IPO() {
			return SVC_FUND_IPO;
		}
		public void setSVC_FUND_IPO(String sVC_FUND_IPO) {
			SVC_FUND_IPO = sVC_FUND_IPO;
		}
		public int getSVC_MODULE() {
			return SVC_MODULE;
		}
		public void setSVC_MODULE(int sVC_MODULE) {
			SVC_MODULE = sVC_MODULE;
		}
		public String getSVC_STATUS() {
			return SVC_STATUS;
		}
		public void setSVC_STATUS(String sVC_STATUS) {
			SVC_STATUS = sVC_STATUS;
		}
		public String getSVC_UID() {
			return SVC_UID;
		}
		public void setSVC_UID(String sVC_UID) {
			SVC_UID = sVC_UID;
		}
		public double getSVC_MAX_INVESTMENT() {
			return SVC_MAX_INVESTMENT;
		}
		public void setSVC_MAX_INVESTMENT(double sVC_MAX_INVESTMENT) {
			SVC_MAX_INVESTMENT = sVC_MAX_INVESTMENT;
		}
		public String getSVC_BANCS_CODE() {
			return SVC_BANCS_CODE;
		}
		public void setSVC_BANCS_CODE(String sVC_BANCS_CODE) {
			SVC_BANCS_CODE = sVC_BANCS_CODE;
		}
		public String getWMS_ENTER_UID() {
			return WMS_ENTER_UID;
		}
		public void setWMS_ENTER_UID(String wMS_ENTER_UID) {
			WMS_ENTER_UID = wMS_ENTER_UID;
		}
		public String getWMS_ENTER_FPC() {
			return WMS_ENTER_FPC;
		}
		public void setWMS_ENTER_FPC(String wMS_ENTER_FPC) {
			WMS_ENTER_FPC = wMS_ENTER_FPC;
		}
		public Calendar getWMS_ENTER_DATE() {
			return WMS_ENTER_DATE;
		}
		public void setWMS_ENTER_DATE(Calendar wMS_ENTER_DATE) {
			WMS_ENTER_DATE = wMS_ENTER_DATE;
		}
		public String getWMS_LAST_UPDATE_UID() {
			return WMS_LAST_UPDATE_UID;
		}
		public void setWMS_LAST_UPDATE_UID(String wMS_LAST_UPDATE_UID) {
			WMS_LAST_UPDATE_UID = wMS_LAST_UPDATE_UID;
		}
		public String getWMS_LAST_FPC() {
			return WMS_LAST_FPC;
		}
		public void setWMS_LAST_FPC(String wMS_LAST_FPC) {
			WMS_LAST_FPC = wMS_LAST_FPC;
		}
		public Calendar getWMS_LAST_UPDATE_DATE() {
			return WMS_LAST_UPDATE_DATE;
		}
		public void setWMS_LAST_UPDATE_DATE(Calendar wMS_LAST_UPDATE_DATE) {
			WMS_LAST_UPDATE_DATE = wMS_LAST_UPDATE_DATE;
		}
		public String getWMS_APPROVE_UID() {
			return WMS_APPROVE_UID;
		}
		public void setWMS_APPROVE_UID(String wMS_APPROVE_UID) {
			WMS_APPROVE_UID = wMS_APPROVE_UID;
		}
		public String getWMS_APPROVE_FPC() {
			return WMS_APPROVE_FPC;
		}
		public void setWMS_APPROVE_FPC(String wMS_APPROVE_FPC) {
			WMS_APPROVE_FPC = wMS_APPROVE_FPC;
		}
		public Calendar getWMS_APPROVE_DATE() {
			return WMS_APPROVE_DATE;
		}
		public void setWMS_APPROVE_DATE(Calendar wMS_APPROVE_DATE) {
			WMS_APPROVE_DATE = wMS_APPROVE_DATE;
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
		public String getWMS_STATUS() {
			return WMS_STATUS;
		}
		public void setWMS_STATUS(String wMS_STATUS) {
			WMS_STATUS = wMS_STATUS;
		}
		@Override
		public String toString() {
			return "FUND_MSTR [SVC_CODE=" + SVC_CODE + ", SVC_DATE=" + SVC_DATE + ", SVC_FUND_TYPE=" + SVC_FUND_TYPE
					+ ", SVC_NAME=" + SVC_NAME + ", SVC_ANAME=" + SVC_ANAME + ", SVC_SHORT_NAME=" + SVC_SHORT_NAME
					+ ", SVC_PB=" + SVC_PB + ", SVC_PC=" + SVC_PC + ", SVC_STATE=" + SVC_STATE + ", SVC_COUNTRY="
					+ SVC_COUNTRY + ", SVC_PHONE=" + SVC_PHONE + ", SVC_FAX=" + SVC_FAX + ", SVC_EMAIL=" + SVC_EMAIL
					+ ", SVC_CURRECNY=" + SVC_CURRECNY + ", SVC_EXCHANGE=" + SVC_EXCHANGE + ", SVC_NO_OF_UNITS="
					+ SVC_NO_OF_UNITS + ", SVC_TOTAL_VALUE=" + SVC_TOTAL_VALUE + ", SVC_SHARE_PRICE=" + SVC_SHARE_PRICE
					+ ", SVC_REMARKS=" + SVC_REMARKS + ", SVC_LAUNCH_DATE=" + SVC_LAUNCH_DATE + ", SVC_START_DATE="
					+ SVC_START_DATE + ", SVC_END_DATE=" + SVC_END_DATE + ", SVC_TERM=" + SVC_TERM
					+ ", SVC_MIN_INVESTMENT=" + SVC_MIN_INVESTMENT + ", SVC_PROFIT_RATE=" + SVC_PROFIT_RATE
					+ ", SVC_PROFIT_PERIOD=" + SVC_PROFIT_PERIOD + ", SVC_TERM_EXT=" + SVC_TERM_EXT
					+ ", SVC_CAPITAL_REDEMPTION_FLAG=" + SVC_CAPITAL_REDEMPTION_FLAG
					+ ", SVC_CAPITAL_REDEMPTION_PERIOD=" + SVC_CAPITAL_REDEMPTION_PERIOD + ", SVC_ALLOTMENT_DATE="
					+ SVC_ALLOTMENT_DATE + ", SVC_FUND_MANAGER=" + SVC_FUND_MANAGER + ", SVC_FUND_IPO=" + SVC_FUND_IPO
					+ ", SVC_MODULE=" + SVC_MODULE + ", SVC_STATUS=" + SVC_STATUS + ", SVC_UID=" + SVC_UID
					+ ", SVC_MAX_INVESTMENT=" + SVC_MAX_INVESTMENT + ", SVC_BANCS_CODE=" + SVC_BANCS_CODE
					+ ", WMS_ENTER_UID=" + WMS_ENTER_UID + ", WMS_ENTER_FPC=" + WMS_ENTER_FPC + ", WMS_ENTER_DATE="
					+ WMS_ENTER_DATE + ", WMS_LAST_UPDATE_UID=" + WMS_LAST_UPDATE_UID + ", WMS_LAST_FPC=" + WMS_LAST_FPC
					+ ", WMS_LAST_UPDATE_DATE=" + WMS_LAST_UPDATE_DATE + ", WMS_APPROVE_UID=" + WMS_APPROVE_UID
					+ ", WMS_APPROVE_FPC=" + WMS_APPROVE_FPC + ", WMS_APPROVE_DATE=" + WMS_APPROVE_DATE
					+ ", IV_ENTER_UID=" + IV_ENTER_UID + ", IV_ENTER_DATE=" + IV_ENTER_DATE + ", IV_APPROVE_UID="
					+ IV_APPROVE_UID + ", IV_APPROVE_DATE=" + IV_APPROVE_DATE + ", IV_LAST_UPDATE_UID="
					+ IV_LAST_UPDATE_UID + ", IV_LAST_UPDATE_DATE=" + IV_LAST_UPDATE_DATE + ", WMS_COMMENTS="
					+ WMS_COMMENTS + ", WMS_STATUS=" + WMS_STATUS + "]";
		}
		
		
	
}
