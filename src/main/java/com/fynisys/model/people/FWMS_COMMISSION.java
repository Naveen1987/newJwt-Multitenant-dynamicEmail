package com.fynisys.model.people;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/*
 * ********??????????????????? per order column missing

CREATE TABLE FWMS_COMMISSION  (
 WMS_SNO                                                        NUMBER(4),       - auto number
 WMS_EDATE                                                      DATE, - effect date
 WMS_LEAVEL							VARCHAR2(25), - level 
 WMS_FUND							NUMBER(10), - fund
 WMS_CLIENT_TYPE                                                NUMBER(15), -  client type
 WMS_CLIENT_ID                                                  NUMBER(15), - client 
 WMS_BROKER                                                     NUMBER(15), - broker
 WMS_STOCK_EXCHANGE_ID                                          NUMBER(4), - stock exchange
 WMS_ASSET_TYPE_ID                                              NUMBER(4), - assest type
 WMS_SHARES_AMOUNT_CAL                                          VARCHAR2(15), - flag
 WMS_TRAN_PROC_FLAG                                             VARCHAR2(25), -perorder /per full tranaction 
 WMS_TRAN_PROC_FEE                                              NUMBER(25,8), - transaction procedure fee
 WMS_COMMISSION_FEE                                             NUMBER(25,8), - commission % 
 WMS_MIN_COMMISSION_FEE                                         NUMBER(25,8), - min commission fee
 WMS_SERVICE_TAX                                                NUMBER(15,8), - service tax
 WMS_STT                                                        NUMBER(15,8), - STT
 WMS_TURNOVER_CGS                                               NUMBER(15,8), - turnover
 WMS_RMS_CGS                                                    NUMBER(15,8), - RMS
 WMS_SEBI_CGS                                                   NUMBER(15,8), - SEBI
 WMS_STRU_CGS                                                   NUMBER(15,8), - STRU
 WMS_STAMP_DUTY                                                 NUMBER(15,8), - Duty
 WMS_VAT							NUMBER(15,8), - vat
 WMS_STATUS                                                     VARCHAR2(20), - status
 WMS_COMMENTS                                                   VARCHAR2(300), - comments
 WMS_ENTER_UID                                                  VARCHAR2(20),
 WMS_ENTER_FPC                                                  VARCHAR2(30),
 WMS_ENTER_DATE                                                 DATE,
 WMS_LAST_UPDATE_UID                                            VARCHAR2(20),
 WMS_LAST_FPC                                                   VARCHAR2(30),
 WMS_LAST_UPDATE_DATE                                           DATE,
 WMS_APPROVE_UID                                                VARCHAR2(20),
 WMS_APPROVE_FPC                                                VARCHAR2(30),
 WMS_APPROVE_DATE                                               DATE);
 
 */
@Entity(name="FWMS_COMMISSION")
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames= {"WMS_EDATE",
		"WMS_LEAVEL",
		"WMS_FUND",
		"WMS_CLIENT_TYPE",
		"WMS_CLIENT_ID",
		"WMS_BROKER",
		"WMS_STOCK_EXCHANGE_ID",
		"WMS_ASSET_TYPE_ID"	
		},name="FWMS_COMMISSION_DUPLICATE_VALUE")
})

public class FWMS_COMMISSION {
	@Id
	@GeneratedValue
	private long WMS_SNO;                                         //NUMBER(4)
	private Calendar WMS_EDATE;                                         //DATE
	@Column(length=25)
	private String WMS_LEAVEL;                                         //VARCHAR2(25)
	private long WMS_FUND;                                         //NUMBER(10)
	private long WMS_CLIENT_TYPE;                                         //NUMBER(15)
	private long WMS_CLIENT_ID;                                         //NUMBER(15)
	private long WMS_BROKER;                                         //NUMBER(15)
	
	private long WMS_STOCK_EXCHANGE_ID;                                         //NUMBER(4)
	private long WMS_ASSET_TYPE_ID;                                         //NUMBER(4)
	@Column(length=15)
	private String WMS_SHARES_AMOUNT_CAL;                                         //VARCHAR2(15)
	@Column(length=25)
	private String WMS_TRAN_PROC_FLAG;                                         //VARCHAR2(25)
	@Column(precision=25,scale=8)
	private double WMS_TRAN_PROC_FEE;                                         //NUMBER(25,8)
	@Column(precision=25,scale=8)
	private double WMS_COMMISSION_FEE;                                         //NUMBER(25,8)
	@Column(precision=25,scale=8)
	private double WMS_MIN_COMMISSION_FEE;                                         //NUMBER(25,8)
	@Column(precision=15,scale=8)
	private double WMS_SERVICE_TAX;                                         //NUMBER(15,8)
	@Column(precision=15,scale=8)
	private double WMS_STT;                                         //NUMBER(15,8)
	@Column(precision=15,scale=8)
	private double WMS_TURNOVER_CGS;                                         //NUMBER(15,8)
	@Column(precision=15,scale=8)
	private double WMS_RMS_CGS;                                         //NUMBER(15,8)
	@Column(precision=15,scale=8)
	private double WMS_SEBI_CGS;                                         //NUMBER(15,8)
	@Column(precision=15,scale=8)
	private double WMS_STRU_CGS;                                         //NUMBER(15,8)
	@Column(precision=15,scale=8)
	private double WMS_STAMP_DUTY;                                         //NUMBER(15,8)
	@Column(precision=15,scale=8)
	private double WMS_VAT;        
	@Column(length=20)
	private String WMS_STATUS;                                         //VARCHAR2(20)
	@Column(length=300)
	private String WMS_COMMENTS;                                         //VARCHAR2(300)
	@Column(length=50)
	private String WMS_ENTER_UID;                                         //VARCHAR2(20)
	@Column(length=50)
	private String WMS_ENTER_FPC;                                         //VARCHAR2(30)
	private Calendar WMS_ENTER_DATE;                                         //DATE
	@Column(length=50)
	private String WMS_LAST_UPDATE_UID;                                         //VARCHAR2(20)
	@Column(length=50)
	private String WMS_LAST_FPC;                                         //VARCHAR2(30)
	private Calendar WMS_LAST_UPDATE_DATE;                                         //DATE
	@Column(length=50)
	private String WMS_APPROVE_UID;                                         //VARCHAR2(20)
	@Column(length=50)
	private String WMS_APPROVE_FPC;                                         //VARCHAR2(30)
	private Calendar WMS_APPROVE_DATE;                                         //DATE
	public long getWMS_SNO() {
		return WMS_SNO;
	}
	public void setWMS_SNO(long wMS_SNO) {
		WMS_SNO = wMS_SNO;
	}
	public Calendar getWMS_EDATE() {
		return WMS_EDATE;
	}
	public void setWMS_EDATE(Calendar wMS_EDATE) {
		WMS_EDATE = wMS_EDATE;
	}
	public String getWMS_LEAVEL() {
		return WMS_LEAVEL;
	}
	public void setWMS_LEAVEL(String wMS_LEAVEL) {
		WMS_LEAVEL = wMS_LEAVEL;
	}
	public long getWMS_FUND() {
		return WMS_FUND;
	}
	public void setWMS_FUND(long wMS_FUND) {
		WMS_FUND = wMS_FUND;
	}
	public long getWMS_CLIENT_TYPE() {
		return WMS_CLIENT_TYPE;
	}
	public void setWMS_CLIENT_TYPE(long wMS_CLIENT_TYPE) {
		WMS_CLIENT_TYPE = wMS_CLIENT_TYPE;
	}
	public long getWMS_CLIENT_ID() {
		return WMS_CLIENT_ID;
	}
	public void setWMS_CLIENT_ID(long wMS_CLIENT_ID) {
		WMS_CLIENT_ID = wMS_CLIENT_ID;
	}
	public long getWMS_BROKER() {
		return WMS_BROKER;
	}
	public void setWMS_BROKER(long wMS_BROKER) {
		WMS_BROKER = wMS_BROKER;
	}
	public long getWMS_STOCK_EXCHANGE_ID() {
		return WMS_STOCK_EXCHANGE_ID;
	}
	public void setWMS_STOCK_EXCHANGE_ID(long wMS_STOCK_EXCHANGE_ID) {
		WMS_STOCK_EXCHANGE_ID = wMS_STOCK_EXCHANGE_ID;
	}
	public long getWMS_ASSET_TYPE_ID() {
		return WMS_ASSET_TYPE_ID;
	}
	public void setWMS_ASSET_TYPE_ID(long wMS_ASSET_TYPE_ID) {
		WMS_ASSET_TYPE_ID = wMS_ASSET_TYPE_ID;
	}
	public String getWMS_SHARES_AMOUNT_CAL() {
		return WMS_SHARES_AMOUNT_CAL;
	}
	public void setWMS_SHARES_AMOUNT_CAL(String wMS_SHARES_AMOUNT_CAL) {
		WMS_SHARES_AMOUNT_CAL = wMS_SHARES_AMOUNT_CAL;
	}
	public String getWMS_TRAN_PROC_FLAG() {
		return WMS_TRAN_PROC_FLAG;
	}
	public void setWMS_TRAN_PROC_FLAG(String wMS_TRAN_PROC_FLAG) {
		WMS_TRAN_PROC_FLAG = wMS_TRAN_PROC_FLAG;
	}
	public double getWMS_TRAN_PROC_FEE() {
		return WMS_TRAN_PROC_FEE;
	}
	public void setWMS_TRAN_PROC_FEE(double wMS_TRAN_PROC_FEE) {
		WMS_TRAN_PROC_FEE = wMS_TRAN_PROC_FEE;
	}
	public double getWMS_COMMISSION_FEE() {
		return WMS_COMMISSION_FEE;
	}
	public void setWMS_COMMISSION_FEE(double wMS_COMMISSION_FEE) {
		WMS_COMMISSION_FEE = wMS_COMMISSION_FEE;
	}
	public double getWMS_MIN_COMMISSION_FEE() {
		return WMS_MIN_COMMISSION_FEE;
	}
	public void setWMS_MIN_COMMISSION_FEE(double wMS_MIN_COMMISSION_FEE) {
		WMS_MIN_COMMISSION_FEE = wMS_MIN_COMMISSION_FEE;
	}
	public double getWMS_SERVICE_TAX() {
		return WMS_SERVICE_TAX;
	}
	public void setWMS_SERVICE_TAX(double wMS_SERVICE_TAX) {
		WMS_SERVICE_TAX = wMS_SERVICE_TAX;
	}
	public double getWMS_STT() {
		return WMS_STT;
	}
	public void setWMS_STT(double wMS_STT) {
		WMS_STT = wMS_STT;
	}
	public double getWMS_TURNOVER_CGS() {
		return WMS_TURNOVER_CGS;
	}
	public void setWMS_TURNOVER_CGS(double wMS_TURNOVER_CGS) {
		WMS_TURNOVER_CGS = wMS_TURNOVER_CGS;
	}
	public double getWMS_RMS_CGS() {
		return WMS_RMS_CGS;
	}
	public void setWMS_RMS_CGS(double wMS_RMS_CGS) {
		WMS_RMS_CGS = wMS_RMS_CGS;
	}
	public double getWMS_SEBI_CGS() {
		return WMS_SEBI_CGS;
	}
	public void setWMS_SEBI_CGS(double wMS_SEBI_CGS) {
		WMS_SEBI_CGS = wMS_SEBI_CGS;
	}
	public double getWMS_STRU_CGS() {
		return WMS_STRU_CGS;
	}
	public void setWMS_STRU_CGS(double wMS_STRU_CGS) {
		WMS_STRU_CGS = wMS_STRU_CGS;
	}
	public double getWMS_STAMP_DUTY() {
		return WMS_STAMP_DUTY;
	}
	public void setWMS_STAMP_DUTY(double wMS_STAMP_DUTY) {
		WMS_STAMP_DUTY = wMS_STAMP_DUTY;
	}
	public double getWMS_VAT() {
		return WMS_VAT;
	}
	public void setWMS_VAT(double wMS_VAT) {
		WMS_VAT = wMS_VAT;
	}
	public String getWMS_STATUS() {
		return WMS_STATUS;
	}
	public void setWMS_STATUS(String wMS_STATUS) {
		WMS_STATUS = wMS_STATUS;
	}
	public String getWMS_COMMENTS() {
		return WMS_COMMENTS;
	}
	public void setWMS_COMMENTS(String wMS_COMMENTS) {
		WMS_COMMENTS = wMS_COMMENTS;
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

}
