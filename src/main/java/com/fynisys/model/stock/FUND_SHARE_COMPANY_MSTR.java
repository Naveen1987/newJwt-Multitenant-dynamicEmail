package com.fynisys.model.stock;

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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/*
 * STOCK TABLE
 *
  Table  FUND_SHARE_COMPANY_MSTR
  SVC_DATE                   Record Creation Date - Auto
  SVC_CODE                   Auto Serial Number
  SVC_NAME                   Stock Name
  SVC_EXEC_CODE		     Ticker
  SVC_CUSIP                  Cusip
  SVC_RETUER_CODE            Retuer Code
  SVC_BLOM_CODE              Bloomberg code
  SVC_ISIN_CODE              ISIN Code
  SVC_SHARES_ISSUE            SEDOL
  SVC_CUST_COUNTRY	  Country
  SVC_CURR_CODE		  Currency
  SVC_EXCHANGE              Exchange
  SVC_INDUSTRY_TYPE	  Sector
  SVC_CUST_SECURITY 	  Asset
  SVC_PUT_CALL_EDATE	Expiry Date
  SVC_PUT_CALL_LOTSIZE    Lot Size
  SVC_BOND_ISSUE_NO         Issue No
  SVC_BOND_DATE           Issue Date
  SVC_BOND_MATURITY_DT   Maturity Date
  SVC_BOND_DURATION	  Unit Price / Rate/Frequency 
  SVC_BOND_RATE           Coupon Rate %
  SVC_DIVISOR_DAYS        Divisor Days
  SVC_CLOSE_ALTERNATE_PRICE	Close/Alternate price,
  SVC_PRICE_CALCULATE		Price calculated flag,
  SVC_MARGIN_MAX			Calculate Value,

 */
@Entity(name="FUND_SHARE_COMPANY_MSTR")
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames="SVC_NAME",name="UK_SVC_NAME"),
	/*	@UniqueConstraint(columnNames="SVC_BLOM_CODE",name="UK_BLOM_CODE"),
		@UniqueConstraint(columnNames="SVC_ISIN_CODE",name="UK_ISIN_CODE"),
		@UniqueConstraint(columnNames="SVC_RETUER_CODE",name="UK_RETUER_CODE"),
		@UniqueConstraint(columnNames="SVC_EXEC_CODE",name="UK_TICKER"),
		@UniqueConstraint(columnNames="SVC_CUSIP",name="UK_CUSIP"),
		@UniqueConstraint(columnNames="SVC_SHARES_ISSUE",name="UK_SEDOL")*/
})

public class FUND_SHARE_COMPANY_MSTR implements Serializable{
	
	 /**
	 * 
	 */
	  private static final long serialVersionUID = 1L;
	  @Id
	  @GeneratedValue
	  private long SVC_CODE ;  //On Amit discussion 2/1/2018
	  private Calendar SVC_DATE;
	  @Column(length=50)
	  private String SVC_NAME;
	  @Column(length=50)
	  private String SVC_ANAME;
	  @Column(length=20)
	  private String SVC_CUSIP;
	  @Column(length=15)
	  private String SVC_EXEC_CODE;
	  @Column(length=15)
	  private String SVC_EXCHANGE; 
	  @Column(length=15)
	  private String SVC_MARKET_TYPE;
	  @Column(length=15)
	  private String SVC_INDUSTRY_TYPE;
	  @Column(length=15)
	  private String SVC_TYPE_DESC;
	  @Column(length=15)
	  private String SVC_CURR_CODE;
	  @Column(length=15)
	  private String SVC_PER_VALUE;
	  @Column(length=15)
	  private String SVC_PAIDUP_VALUE;
	  private long SVC_UNIT_SHARE;
	  private long SVC_ISSUED_SHARES;
	  @Column(precision=20, scale=3)
	  private double SVC_ISSUED_AMOUNT;
	  private Calendar SVC_ISSUED_DATE;
	  private Calendar SVC_TRADEDED_DATE;
	  @Column(length=25)
	  private String SVC_AGM_DATE;
	  @Column(length=20)
	  private String SVC_BOND_ISSUE_NO;
	  private Calendar  SVC_BOND_DATE;
	  @Column(length=20)
	  private String SVC_BOND_DURATION;
	  private Calendar SVC_BOND_MATURITY_DT;
	  private Calendar SVC_BOND_FIRST_INT_DT;
	  private Calendar SVC_BOND_SECOND_INT_DT;
	  @Column(precision=15, scale=6)
	  private double SVC_BOND_RATE;
	  @Column(length=50)
	  private String SVC_REMARKS;
	  @Column(length=15)
	  private String SVC_RISK;
	  private long SVC_SHARES_ISSUE;
	  @Column(length=15)
	  private String SVC_TYPE;
	  @Column(length=15)
	  private String SVC_TYPE2;
	  @Column(length=15)
	  private String SVC_MSM_TYPE; 
	  private int SVC_TRANSACTION_FUND;
	  private int SVC_TRANSACTION_PM;
	  private int SVC_SHARES_REGISTER;
	  @Column(length=50)
	  private String SVC_UID;   //UID is 50 char long
	  private Calendar SVC_BOND_THIRD_INT_DT;
	  private Calendar SVC_BOND_FOURTH_INT_DT;
	  private int SVC_DIVISOR_DAYS;
	  @Column(precision=15, scale=6)
	  private double SVC_MARGIN_MIN;
	  @Column(precision=15, scale=6)
	  private double SVC_MARGIN_MAX;
	  private int SVC_CUST_COUNTRY; 
	  private int SVC_CUST_SECURITY; //ASSEST
	  @Column(precision=15, scale=6)
	  private double SVC_MARGIN_LIQUIDATION;
	  @Column(precision=15, scale=6)
	  private double SVC_MARGIN_CONCENTRATION;
	  private long SVC_MARGIN_TYPE;
	  @Column(length=20)
	  private String SVC_RETUER_CODE;
	  @Column(length=40)
	  private String SVC_BLOM_CODE;
	  @Column(length=40)
	  private String SVC_ISIN_CODE;
	  @Column(length=12)
	  private String SVC_PUT_CALL;
	  private Calendar SVC_PUT_CALL_EDATE;
	  private long SVC_PUT_CALL_LOTSIZE;  
	  @Column(precision=25, scale=8)
	  private double SVC_PUT_CALL_SPRICE; 
	  @Column(length=10)
	  private String SVC_CLOSE_ALTERNATE_PRICE;
	  @Column(length=30)
	  private String SVC_PRICE_CALCULATE;
	  @Column(length=40)
	  private String SVC_OTHER_CODE;
	  /*
	   * Standard parameter
	   */
	  @Column(length=219)
	  private String IV_ENTER_UID; 
	  private Calendar IV_ENTER_DATE;
	  @Column(length=219)
	  private String IV_APPROVE_UID; 
	  private Calendar IV_APPROVE_DATE;
	  @Column(length=219)
	  private String IV_LAST_UPDATE_UID;
	  private Calendar IV_LAST_UPDATE_DATE;
	  @Column(length=300)
	  private String WMS_COMMENTS;
	  @Column(length=20)
	  private String WMS_STATUS;
	  @OneToMany(mappedBy="SVC_CODE",fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)
	  private List<FUND_BOND_INT_RATE> fUND_BOND_INT_RATE=new ArrayList<FUND_BOND_INT_RATE>();
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
	public String getSVC_CUSIP() {
		return SVC_CUSIP;
	}
	public void setSVC_CUSIP(String sVC_CUSIP) {
		SVC_CUSIP = sVC_CUSIP;
	}
	public String getSVC_EXEC_CODE() {
		return SVC_EXEC_CODE;
	}
	public void setSVC_EXEC_CODE(String sVC_EXEC_CODE) {
		SVC_EXEC_CODE = sVC_EXEC_CODE;
	}
	public String getSVC_EXCHANGE() {
		return SVC_EXCHANGE;
	}
	public void setSVC_EXCHANGE(String sVC_EXCHANGE) {
		SVC_EXCHANGE = sVC_EXCHANGE;
	}
	public String getSVC_MARKET_TYPE() {
		return SVC_MARKET_TYPE;
	}
	public void setSVC_MARKET_TYPE(String sVC_MARKET_TYPE) {
		SVC_MARKET_TYPE = sVC_MARKET_TYPE;
	}
	public String getSVC_INDUSTRY_TYPE() {
		return SVC_INDUSTRY_TYPE;
	}
	public void setSVC_INDUSTRY_TYPE(String sVC_INDUSTRY_TYPE) {
		SVC_INDUSTRY_TYPE = sVC_INDUSTRY_TYPE;
	}
	public String getSVC_TYPE_DESC() {
		return SVC_TYPE_DESC;
	}
	public void setSVC_TYPE_DESC(String sVC_TYPE_DESC) {
		SVC_TYPE_DESC = sVC_TYPE_DESC;
	}
	public String getSVC_CURR_CODE() {
		return SVC_CURR_CODE;
	}
	public void setSVC_CURR_CODE(String sVC_CURR_CODE) {
		SVC_CURR_CODE = sVC_CURR_CODE;
	}
	public String getSVC_PER_VALUE() {
		return SVC_PER_VALUE;
	}
	public void setSVC_PER_VALUE(String sVC_PER_VALUE) {
		SVC_PER_VALUE = sVC_PER_VALUE;
	}
	public String getSVC_PAIDUP_VALUE() {
		return SVC_PAIDUP_VALUE;
	}
	public void setSVC_PAIDUP_VALUE(String sVC_PAIDUP_VALUE) {
		SVC_PAIDUP_VALUE = sVC_PAIDUP_VALUE;
	}
	public long getSVC_UNIT_SHARE() {
		return SVC_UNIT_SHARE;
	}
	public void setSVC_UNIT_SHARE(long sVC_UNIT_SHARE) {
		SVC_UNIT_SHARE = sVC_UNIT_SHARE;
	}
	public long getSVC_ISSUED_SHARES() {
		return SVC_ISSUED_SHARES;
	}
	public void setSVC_ISSUED_SHARES(long sVC_ISSUED_SHARES) {
		SVC_ISSUED_SHARES = sVC_ISSUED_SHARES;
	}
	public double getSVC_ISSUED_AMOUNT() {
		return SVC_ISSUED_AMOUNT;
	}
	public void setSVC_ISSUED_AMOUNT(double sVC_ISSUED_AMOUNT) {
		SVC_ISSUED_AMOUNT = sVC_ISSUED_AMOUNT;
	}
	public Calendar getSVC_ISSUED_DATE() {
		return SVC_ISSUED_DATE;
	}
	public void setSVC_ISSUED_DATE(Calendar sVC_ISSUED_DATE) {
		SVC_ISSUED_DATE = sVC_ISSUED_DATE;
	}
	public Calendar getSVC_TRADEDED_DATE() {
		return SVC_TRADEDED_DATE;
	}
	public void setSVC_TRADEDED_DATE(Calendar sVC_TRADEDED_DATE) {
		SVC_TRADEDED_DATE = sVC_TRADEDED_DATE;
	}
	public String getSVC_AGM_DATE() {
		return SVC_AGM_DATE;
	}
	public void setSVC_AGM_DATE(String sVC_AGM_DATE) {
		SVC_AGM_DATE = sVC_AGM_DATE;
	}
	public String getSVC_BOND_ISSUE_NO() {
		return SVC_BOND_ISSUE_NO;
	}
	public void setSVC_BOND_ISSUE_NO(String sVC_BOND_ISSUE_NO) {
		SVC_BOND_ISSUE_NO = sVC_BOND_ISSUE_NO;
	}
	public Calendar getSVC_BOND_DATE() {
		return SVC_BOND_DATE;
	}
	public void setSVC_BOND_DATE(Calendar sVC_BOND_DATE) {
		SVC_BOND_DATE = sVC_BOND_DATE;
	}
	public String getSVC_BOND_DURATION() {
		return SVC_BOND_DURATION;
	}
	public void setSVC_BOND_DURATION(String sVC_BOND_DURATION) {
		SVC_BOND_DURATION = sVC_BOND_DURATION;
	}
	public Calendar getSVC_BOND_MATURITY_DT() {
		return SVC_BOND_MATURITY_DT;
	}
	public void setSVC_BOND_MATURITY_DT(Calendar sVC_BOND_MATURITY_DT) {
		SVC_BOND_MATURITY_DT = sVC_BOND_MATURITY_DT;
	}
	public Calendar getSVC_BOND_FIRST_INT_DT() {
		return SVC_BOND_FIRST_INT_DT;
	}
	public void setSVC_BOND_FIRST_INT_DT(Calendar sVC_BOND_FIRST_INT_DT) {
		SVC_BOND_FIRST_INT_DT = sVC_BOND_FIRST_INT_DT;
	}
	public Calendar getSVC_BOND_SECOND_INT_DT() {
		return SVC_BOND_SECOND_INT_DT;
	}
	public void setSVC_BOND_SECOND_INT_DT(Calendar sVC_BOND_SECOND_INT_DT) {
		SVC_BOND_SECOND_INT_DT = sVC_BOND_SECOND_INT_DT;
	}
	public double getSVC_BOND_RATE() {
		return SVC_BOND_RATE;
	}
	public void setSVC_BOND_RATE(double sVC_BOND_RATE) {
		SVC_BOND_RATE = sVC_BOND_RATE;
	}
	public String getSVC_REMARKS() {
		return SVC_REMARKS;
	}
	public void setSVC_REMARKS(String sVC_REMARKS) {
		SVC_REMARKS = sVC_REMARKS;
	}
	public String getSVC_RISK() {
		return SVC_RISK;
	}
	public void setSVC_RISK(String sVC_RISK) {
		SVC_RISK = sVC_RISK;
	}
	public long getSVC_SHARES_ISSUE() {
		return SVC_SHARES_ISSUE;
	}
	public void setSVC_SHARES_ISSUE(long sVC_SHARES_ISSUE) {
		SVC_SHARES_ISSUE = sVC_SHARES_ISSUE;
	}
	public String getSVC_TYPE() {
		return SVC_TYPE;
	}
	public void setSVC_TYPE(String sVC_TYPE) {
		SVC_TYPE = sVC_TYPE;
	}
	public String getSVC_TYPE2() {
		return SVC_TYPE2;
	}
	public void setSVC_TYPE2(String sVC_TYPE2) {
		SVC_TYPE2 = sVC_TYPE2;
	}
	public String getSVC_MSM_TYPE() {
		return SVC_MSM_TYPE;
	}
	public void setSVC_MSM_TYPE(String sVC_MSM_TYPE) {
		SVC_MSM_TYPE = sVC_MSM_TYPE;
	}
	public int getSVC_TRANSACTION_FUND() {
		return SVC_TRANSACTION_FUND;
	}
	public void setSVC_TRANSACTION_FUND(int sVC_TRANSACTION_FUND) {
		SVC_TRANSACTION_FUND = sVC_TRANSACTION_FUND;
	}
	public int getSVC_TRANSACTION_PM() {
		return SVC_TRANSACTION_PM;
	}
	public void setSVC_TRANSACTION_PM(int sVC_TRANSACTION_PM) {
		SVC_TRANSACTION_PM = sVC_TRANSACTION_PM;
	}
	public int getSVC_SHARES_REGISTER() {
		return SVC_SHARES_REGISTER;
	}
	public void setSVC_SHARES_REGISTER(int sVC_SHARES_REGISTER) {
		SVC_SHARES_REGISTER = sVC_SHARES_REGISTER;
	}
	public String getSVC_UID() {
		return SVC_UID;
	}
	public void setSVC_UID(String sVC_UID) {
		SVC_UID = sVC_UID;
	}
	public Calendar getSVC_BOND_THIRD_INT_DT() {
		return SVC_BOND_THIRD_INT_DT;
	}
	public void setSVC_BOND_THIRD_INT_DT(Calendar sVC_BOND_THIRD_INT_DT) {
		SVC_BOND_THIRD_INT_DT = sVC_BOND_THIRD_INT_DT;
	}
	public Calendar getSVC_BOND_FOURTH_INT_DT() {
		return SVC_BOND_FOURTH_INT_DT;
	}
	public void setSVC_BOND_FOURTH_INT_DT(Calendar sVC_BOND_FOURTH_INT_DT) {
		SVC_BOND_FOURTH_INT_DT = sVC_BOND_FOURTH_INT_DT;
	}
	public int getSVC_DIVISOR_DAYS() {
		return SVC_DIVISOR_DAYS;
	}
	public void setSVC_DIVISOR_DAYS(int sVC_DIVISOR_DAYS) {
		SVC_DIVISOR_DAYS = sVC_DIVISOR_DAYS;
	}
	public double getSVC_MARGIN_MIN() {
		return SVC_MARGIN_MIN;
	}
	public void setSVC_MARGIN_MIN(double sVC_MARGIN_MIN) {
		SVC_MARGIN_MIN = sVC_MARGIN_MIN;
	}
	public double getSVC_MARGIN_MAX() {
		return SVC_MARGIN_MAX;
	}
	public void setSVC_MARGIN_MAX(double sVC_MARGIN_MAX) {
		SVC_MARGIN_MAX = sVC_MARGIN_MAX;
	}
	public int getSVC_CUST_COUNTRY() {
		return SVC_CUST_COUNTRY;
	}
	public void setSVC_CUST_COUNTRY(int sVC_CUST_COUNTRY) {
		SVC_CUST_COUNTRY = sVC_CUST_COUNTRY;
	}
	public int getSVC_CUST_SECURITY() {
		return SVC_CUST_SECURITY;
	}
	public void setSVC_CUST_SECURITY(int sVC_CUST_SECURITY) {
		SVC_CUST_SECURITY = sVC_CUST_SECURITY;
	}
	public double getSVC_MARGIN_LIQUIDATION() {
		return SVC_MARGIN_LIQUIDATION;
	}
	public void setSVC_MARGIN_LIQUIDATION(double sVC_MARGIN_LIQUIDATION) {
		SVC_MARGIN_LIQUIDATION = sVC_MARGIN_LIQUIDATION;
	}
	public double getSVC_MARGIN_CONCENTRATION() {
		return SVC_MARGIN_CONCENTRATION;
	}
	public void setSVC_MARGIN_CONCENTRATION(double sVC_MARGIN_CONCENTRATION) {
		SVC_MARGIN_CONCENTRATION = sVC_MARGIN_CONCENTRATION;
	}
	public long getSVC_MARGIN_TYPE() {
		return SVC_MARGIN_TYPE;
	}
	public void setSVC_MARGIN_TYPE(long sVC_MARGIN_TYPE) {
		SVC_MARGIN_TYPE = sVC_MARGIN_TYPE;
	}
	public String getSVC_RETUER_CODE() {
		return SVC_RETUER_CODE;
	}
	public void setSVC_RETUER_CODE(String sVC_RETUER_CODE) {
		SVC_RETUER_CODE = sVC_RETUER_CODE;
	}
	public String getSVC_BLOM_CODE() {
		return SVC_BLOM_CODE;
	}
	public void setSVC_BLOM_CODE(String sVC_BLOM_CODE) {
		SVC_BLOM_CODE = sVC_BLOM_CODE;
	}
	public String getSVC_ISIN_CODE() {
		return SVC_ISIN_CODE;
	}
	public void setSVC_ISIN_CODE(String sVC_ISIN_CODE) {
		SVC_ISIN_CODE = sVC_ISIN_CODE;
	}
	public String getSVC_PUT_CALL() {
		return SVC_PUT_CALL;
	}
	public void setSVC_PUT_CALL(String sVC_PUT_CALL) {
		SVC_PUT_CALL = sVC_PUT_CALL;
	}
	public Calendar getSVC_PUT_CALL_EDATE() {
		return SVC_PUT_CALL_EDATE;
	}
	public void setSVC_PUT_CALL_EDATE(Calendar sVC_PUT_CALL_EDATE) {
		SVC_PUT_CALL_EDATE = sVC_PUT_CALL_EDATE;
	}
	public long getSVC_PUT_CALL_LOTSIZE() {
		return SVC_PUT_CALL_LOTSIZE;
	}
	public void setSVC_PUT_CALL_LOTSIZE(long sVC_PUT_CALL_LOTSIZE) {
		SVC_PUT_CALL_LOTSIZE = sVC_PUT_CALL_LOTSIZE;
	}
	public double getSVC_PUT_CALL_SPRICE() {
		return SVC_PUT_CALL_SPRICE;
	}
	public void setSVC_PUT_CALL_SPRICE(double sVC_PUT_CALL_SPRICE) {
		SVC_PUT_CALL_SPRICE = sVC_PUT_CALL_SPRICE;
	}
	public String getSVC_CLOSE_ALTERNATE_PRICE() {
		return SVC_CLOSE_ALTERNATE_PRICE;
	}
	public void setSVC_CLOSE_ALTERNATE_PRICE(String sVC_CLOSE_ALTERNATE_PRICE) {
		SVC_CLOSE_ALTERNATE_PRICE = sVC_CLOSE_ALTERNATE_PRICE;
	}
	public String getSVC_PRICE_CALCULATE() {
		return SVC_PRICE_CALCULATE;
	}
	public void setSVC_PRICE_CALCULATE(String sVC_PRICE_CALCULATE) {
		SVC_PRICE_CALCULATE = sVC_PRICE_CALCULATE;
	}
	public String getSVC_OTHER_CODE() {
		return SVC_OTHER_CODE;
	}
	public void setSVC_OTHER_CODE(String sVC_OTHER_CODE) {
		SVC_OTHER_CODE = sVC_OTHER_CODE;
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
	public List<FUND_BOND_INT_RATE> getfUND_BOND_INT_RATE() {
		return fUND_BOND_INT_RATE;
	}
	public void setfUND_BOND_INT_RATE(List<FUND_BOND_INT_RATE> fUND_BOND_INT_RATE) {
		this.fUND_BOND_INT_RATE = fUND_BOND_INT_RATE;
	}
	@Override
	public String toString() {
		return "FUND_SHARE_COMPANY_MSTR [SVC_CODE=" + SVC_CODE + ", SVC_DATE=" + SVC_DATE + ", SVC_NAME=" + SVC_NAME
				+ ", SVC_ANAME=" + SVC_ANAME + ", SVC_CUSIP=" + SVC_CUSIP + ", SVC_EXEC_CODE=" + SVC_EXEC_CODE
				+ ", SVC_EXCHANGE=" + SVC_EXCHANGE + ", SVC_MARKET_TYPE=" + SVC_MARKET_TYPE + ", SVC_INDUSTRY_TYPE="
				+ SVC_INDUSTRY_TYPE + ", SVC_TYPE_DESC=" + SVC_TYPE_DESC + ", SVC_CURR_CODE=" + SVC_CURR_CODE
				+ ", SVC_PER_VALUE=" + SVC_PER_VALUE + ", SVC_PAIDUP_VALUE=" + SVC_PAIDUP_VALUE + ", SVC_UNIT_SHARE="
				+ SVC_UNIT_SHARE + ", SVC_ISSUED_SHARES=" + SVC_ISSUED_SHARES + ", SVC_ISSUED_AMOUNT="
				+ SVC_ISSUED_AMOUNT + ", SVC_ISSUED_DATE=" + SVC_ISSUED_DATE + ", SVC_TRADEDED_DATE="
				+ SVC_TRADEDED_DATE + ", SVC_AGM_DATE=" + SVC_AGM_DATE + ", SVC_BOND_ISSUE_NO=" + SVC_BOND_ISSUE_NO
				+ ", SVC_BOND_DATE=" + SVC_BOND_DATE + ", SVC_BOND_DURATION=" + SVC_BOND_DURATION
				+ ", SVC_BOND_MATURITY_DT=" + SVC_BOND_MATURITY_DT + ", SVC_BOND_FIRST_INT_DT=" + SVC_BOND_FIRST_INT_DT
				+ ", SVC_BOND_SECOND_INT_DT=" + SVC_BOND_SECOND_INT_DT + ", SVC_BOND_RATE=" + SVC_BOND_RATE
				+ ", SVC_REMARKS=" + SVC_REMARKS + ", SVC_RISK=" + SVC_RISK + ", SVC_SHARES_ISSUE=" + SVC_SHARES_ISSUE
				+ ", SVC_TYPE=" + SVC_TYPE + ", SVC_TYPE2=" + SVC_TYPE2 + ", SVC_MSM_TYPE=" + SVC_MSM_TYPE
				+ ", SVC_TRANSACTION_FUND=" + SVC_TRANSACTION_FUND + ", SVC_TRANSACTION_PM=" + SVC_TRANSACTION_PM
				+ ", SVC_SHARES_REGISTER=" + SVC_SHARES_REGISTER + ", SVC_UID=" + SVC_UID + ", SVC_BOND_THIRD_INT_DT="
				+ SVC_BOND_THIRD_INT_DT + ", SVC_BOND_FOURTH_INT_DT=" + SVC_BOND_FOURTH_INT_DT + ", SVC_DIVISOR_DAYS="
				+ SVC_DIVISOR_DAYS + ", SVC_MARGIN_MIN=" + SVC_MARGIN_MIN + ", SVC_MARGIN_MAX=" + SVC_MARGIN_MAX
				+ ", SVC_CUST_COUNTRY=" + SVC_CUST_COUNTRY + ", SVC_CUST_SECURITY=" + SVC_CUST_SECURITY
				+ ", SVC_MARGIN_LIQUIDATION=" + SVC_MARGIN_LIQUIDATION + ", SVC_MARGIN_CONCENTRATION="
				+ SVC_MARGIN_CONCENTRATION + ", SVC_MARGIN_TYPE=" + SVC_MARGIN_TYPE + ", SVC_RETUER_CODE="
				+ SVC_RETUER_CODE + ", SVC_BLOM_CODE=" + SVC_BLOM_CODE + ", SVC_ISIN_CODE=" + SVC_ISIN_CODE
				+ ", SVC_PUT_CALL=" + SVC_PUT_CALL + ", SVC_PUT_CALL_EDATE=" + SVC_PUT_CALL_EDATE
				+ ", SVC_PUT_CALL_LOTSIZE=" + SVC_PUT_CALL_LOTSIZE + ", SVC_PUT_CALL_SPRICE=" + SVC_PUT_CALL_SPRICE
				+ ", SVC_CLOSE_ALTERNATE_PRICE=" + SVC_CLOSE_ALTERNATE_PRICE + ", SVC_PRICE_CALCULATE="
				+ SVC_PRICE_CALCULATE + ", SVC_OTHER_CODE=" + SVC_OTHER_CODE + ", IV_ENTER_UID=" + IV_ENTER_UID
				+ ", IV_ENTER_DATE=" + IV_ENTER_DATE + ", IV_APPROVE_UID=" + IV_APPROVE_UID + ", IV_APPROVE_DATE="
				+ IV_APPROVE_DATE + ", IV_LAST_UPDATE_UID=" + IV_LAST_UPDATE_UID + ", IV_LAST_UPDATE_DATE="
				+ IV_LAST_UPDATE_DATE + ", WMS_COMMENTS=" + WMS_COMMENTS + ", WMS_STATUS=" + WMS_STATUS
				+ ", fUND_BOND_INT_RATE=" + fUND_BOND_INT_RATE + "]";
	}
	
	  
}
