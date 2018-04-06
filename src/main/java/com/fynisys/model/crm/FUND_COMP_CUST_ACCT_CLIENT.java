package com.fynisys.model.crm;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity(name="FUND_COMP_CUST_ACCT_CLIENT")
public class FUND_COMP_CUST_ACCT_CLIENT {
	@Id
	@GeneratedValue
	@Column(length=15)
	 private Long SAC_FUND_CODE;
	@Column(length=15)
	 private Long SAC_CLIENT_NO;
	@Column(length=10)
	 private String SAC_SECURITY_CLASS;
	@Column(precision=16,scale=3)
	 private Double SAC_SAFE_RATE;
	@Column(precision=16,scale=3)
	 private Double SAC_SAFE_MINIMUM;
	@Column(length=10)
	 private Long SAC_SAFE_PERIOD;
	@Column(precision=16,scale=3)
	 private Double SAC_BLOCKING;
	@Column(precision=16,scale=3)
	 private Double SAC_UNBLOCKING;
	@Column(precision=16,scale=3)
	 private Double SAC_OTHER_CHARGES;
	@Column(length=50)
	 private String SAC_OTHER_REMARKS;
	@Column(precision=16,scale=3)
	 private Double SAC_OTHER_CHARGES1;
	@Column(length=50)
	 private String SAC_OTHER_REMARKS1;
	@Column(precision=16,scale=3)
	 private Double SAC_REBATE_CHARGES;
	@Column(precision=25,scale=3)
	 private Double SAC_ANNUAL_CHAREGES;
	@Column(length=30)
	 private String SAC_COUNTRY;
	@Column(length=10)
	 private String SAC_CURR;
	@Column(length=10)
	 private String SAC_SAFE_DIVISOR_DAYS;
	@Column(length=10)
	 private String SAC_SAFE_MONTH_DAYS;
	@Column(length=50)
	 private String SAC_REBATE_REMARKS4;
	@Column(precision=16,scale=3)
	 private Double SAC_TRAN_OTHER_FLAT_RATE;
	@Column(precision=16,scale=3)
	 private Double SAC_BROKERAGE_FEE;
	@Column(precision=25,scale=3)
	 private Double SAC_BROKERAGE_MIN;
	@Column(precision=16,scale=3)
	 private Double SAC_TRAN_OTHER_FLAT_RATE2;
	@Column(length=15)
	 private Long FMD_C_TYPE;
	private Calendar SVC_EDATE;
	@Column(length=119)
	 private String IV_ENTER_UID;
	private Calendar IV_ENTER_DATE;
	@Column(length=119)
	 private String IV_APPROVE_UID;
	private Calendar IV_APPROVE_DATE;
	@Column(length=119)
	 private String IV_LAST_UPDATE_UID;
	private Calendar IV_LAST_UPDATE_DATE;
	
	@ManyToOne
	@JoinColumn(name="RI_WMS_CODE",foreignKey=@ForeignKey(name="fk_COMP_CUST_ACCT_CLIENT_INVESTOR"))
	@JsonBackReference
	private RE_INVESTOR rE_INVESTOR;
	
	public RE_INVESTOR getrE_INVESTOR() {
		return rE_INVESTOR;
	}
	public void setrE_INVESTOR(RE_INVESTOR rE_INVESTOR) {
		this.rE_INVESTOR = rE_INVESTOR;
	}
	
	
	public Long getSAC_FUND_CODE() {
		return SAC_FUND_CODE;
	}
	public void setSAC_FUND_CODE(Long sAC_FUND_CODE) {
		SAC_FUND_CODE = sAC_FUND_CODE;
	}
	public Long getSAC_CLIENT_NO() {
		return SAC_CLIENT_NO;
	}
	public void setSAC_CLIENT_NO(Long sAC_CLIENT_NO) {
		SAC_CLIENT_NO = sAC_CLIENT_NO;
	}
	public String getSAC_SECURITY_CLASS() {
		return SAC_SECURITY_CLASS;
	}
	public void setSAC_SECURITY_CLASS(String sAC_SECURITY_CLASS) {
		SAC_SECURITY_CLASS = sAC_SECURITY_CLASS;
	}
	public Double getSAC_SAFE_RATE() {
		return SAC_SAFE_RATE;
	}
	public void setSAC_SAFE_RATE(Double sAC_SAFE_RATE) {
		SAC_SAFE_RATE = sAC_SAFE_RATE;
	}
	public Double getSAC_SAFE_MINIMUM() {
		return SAC_SAFE_MINIMUM;
	}
	public void setSAC_SAFE_MINIMUM(Double sAC_SAFE_MINIMUM) {
		SAC_SAFE_MINIMUM = sAC_SAFE_MINIMUM;
	}
	public Long getSAC_SAFE_PERIOD() {
		return SAC_SAFE_PERIOD;
	}
	public void setSAC_SAFE_PERIOD(Long sAC_SAFE_PERIOD) {
		SAC_SAFE_PERIOD = sAC_SAFE_PERIOD;
	}
	public Double getSAC_BLOCKING() {
		return SAC_BLOCKING;
	}
	public void setSAC_BLOCKING(Double sAC_BLOCKING) {
		SAC_BLOCKING = sAC_BLOCKING;
	}
	public Double getSAC_UNBLOCKING() {
		return SAC_UNBLOCKING;
	}
	public void setSAC_UNBLOCKING(Double sAC_UNBLOCKING) {
		SAC_UNBLOCKING = sAC_UNBLOCKING;
	}
	public Double getSAC_OTHER_CHARGES() {
		return SAC_OTHER_CHARGES;
	}
	public void setSAC_OTHER_CHARGES(Double sAC_OTHER_CHARGES) {
		SAC_OTHER_CHARGES = sAC_OTHER_CHARGES;
	}
	public String getSAC_OTHER_REMARKS() {
		return SAC_OTHER_REMARKS;
	}
	public void setSAC_OTHER_REMARKS(String sAC_OTHER_REMARKS) {
		SAC_OTHER_REMARKS = sAC_OTHER_REMARKS;
	}
	public Double getSAC_OTHER_CHARGES1() {
		return SAC_OTHER_CHARGES1;
	}
	public void setSAC_OTHER_CHARGES1(Double sAC_OTHER_CHARGES1) {
		SAC_OTHER_CHARGES1 = sAC_OTHER_CHARGES1;
	}
	public String getSAC_OTHER_REMARKS1() {
		return SAC_OTHER_REMARKS1;
	}
	public void setSAC_OTHER_REMARKS1(String sAC_OTHER_REMARKS1) {
		SAC_OTHER_REMARKS1 = sAC_OTHER_REMARKS1;
	}
	public Double getSAC_REBATE_CHARGES() {
		return SAC_REBATE_CHARGES;
	}
	public void setSAC_REBATE_CHARGES(Double sAC_REBATE_CHARGES) {
		SAC_REBATE_CHARGES = sAC_REBATE_CHARGES;
	}
	public Double getSAC_ANNUAL_CHAREGES() {
		return SAC_ANNUAL_CHAREGES;
	}
	public void setSAC_ANNUAL_CHAREGES(Double sAC_ANNUAL_CHAREGES) {
		SAC_ANNUAL_CHAREGES = sAC_ANNUAL_CHAREGES;
	}
	public String getSAC_COUNTRY() {
		return SAC_COUNTRY;
	}
	public void setSAC_COUNTRY(String sAC_COUNTRY) {
		SAC_COUNTRY = sAC_COUNTRY;
	}
	public String getSAC_CURR() {
		return SAC_CURR;
	}
	public void setSAC_CURR(String sAC_CURR) {
		SAC_CURR = sAC_CURR;
	}
	public String getSAC_SAFE_DIVISOR_DAYS() {
		return SAC_SAFE_DIVISOR_DAYS;
	}
	public void setSAC_SAFE_DIVISOR_DAYS(String sAC_SAFE_DIVISOR_DAYS) {
		SAC_SAFE_DIVISOR_DAYS = sAC_SAFE_DIVISOR_DAYS;
	}
	public String getSAC_SAFE_MONTH_DAYS() {
		return SAC_SAFE_MONTH_DAYS;
	}
	public void setSAC_SAFE_MONTH_DAYS(String sAC_SAFE_MONTH_DAYS) {
		SAC_SAFE_MONTH_DAYS = sAC_SAFE_MONTH_DAYS;
	}
	public String getSAC_REBATE_REMARKS4() {
		return SAC_REBATE_REMARKS4;
	}
	public void setSAC_REBATE_REMARKS4(String sAC_REBATE_REMARKS4) {
		SAC_REBATE_REMARKS4 = sAC_REBATE_REMARKS4;
	}
	public Double getSAC_TRAN_OTHER_FLAT_RATE() {
		return SAC_TRAN_OTHER_FLAT_RATE;
	}
	public void setSAC_TRAN_OTHER_FLAT_RATE(Double sAC_TRAN_OTHER_FLAT_RATE) {
		SAC_TRAN_OTHER_FLAT_RATE = sAC_TRAN_OTHER_FLAT_RATE;
	}
	public Double getSAC_BROKERAGE_FEE() {
		return SAC_BROKERAGE_FEE;
	}
	public void setSAC_BROKERAGE_FEE(Double sAC_BROKERAGE_FEE) {
		SAC_BROKERAGE_FEE = sAC_BROKERAGE_FEE;
	}
	public Double getSAC_BROKERAGE_MIN() {
		return SAC_BROKERAGE_MIN;
	}
	public void setSAC_BROKERAGE_MIN(Double sAC_BROKERAGE_MIN) {
		SAC_BROKERAGE_MIN = sAC_BROKERAGE_MIN;
	}
	public Double getSAC_TRAN_OTHER_FLAT_RATE2() {
		return SAC_TRAN_OTHER_FLAT_RATE2;
	}
	public void setSAC_TRAN_OTHER_FLAT_RATE2(Double sAC_TRAN_OTHER_FLAT_RATE2) {
		SAC_TRAN_OTHER_FLAT_RATE2 = sAC_TRAN_OTHER_FLAT_RATE2;
	}
	public Long getFMD_C_TYPE() {
		return FMD_C_TYPE;
	}
	public void setFMD_C_TYPE(Long fMD_C_TYPE) {
		FMD_C_TYPE = fMD_C_TYPE;
	}
	public Calendar getSVC_EDATE() {
		return SVC_EDATE;
	}
	public void setSVC_EDATE(Calendar sVC_EDATE) {
		SVC_EDATE = sVC_EDATE;
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

}
