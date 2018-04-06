package com.fynisys.model.orders;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;



@Entity(name="FWMS_ORDER_BOOK")
public class FWMS_ORDER_BOOK {
	@Id
	@GeneratedValue
	private Long FOB_NO;  
	private Calendar FOB_DATE;  
	@Column(length=20)
	private String FOB_TIME;
	@Column(length=10)
	private String FOB_B_S;
	@Column(length=10)
	private String FOB_L_S;
	@Column(length=25)
	private String FOB_SHARE_CLASS;
	private Long FOB_FUND;
	private Long FOB_BROKER;
	private Long FOB_CLIENT;
	private Long FOB_STOCK; 
	@Column(length=100)
	private String FOB_STOCK_NAME;
	@Column(length=30)
	private String FOB_TICKER;
	@Column(length=15)
	private String FOB_L_M;
	@Column(precision=20,scale=3)
	private Double FOB_QTY;  
	@Column(precision=20,scale=3)
	private Double FOB_PRICE;    
	@Column(precision=20,scale=3)
	private Double FOB_VALUE;    
	@Column(precision=20,scale=3)
	private Double FOB_MPRICE;   
	@Column(precision=20,scale=3)
	private Double FOB_BQTY;     
	@Column(precision=20,scale=3)
	private Double FOB_BPRICE;   
	@Column(precision=20,scale=3)
	private Double FOB_AQTY;     
	@Column(precision=20,scale=3)
	private Double FOB_APRICE;   
	@Column(precision=20,scale=3)
	private Double FOB_HIGH;     
	@Column(precision=20,scale=3)
	private Double FOB_LOW;      
	@Column(precision=20,scale=3)
	private Double FOB_VOLUME;   
	@Column(precision=20,scale=3)
	private Double FOB_BROKER_QTY;
	@Column(precision=20,scale=3)
	private Double FOB_BROKER_CASH;  
	@Column(precision=20,scale=3)
	private Double FOB_MARGIN_AMT;
	@Column(length=25)
	private String FOB_OSTATUS;
	@Column(precision=20,scale=3)
	private Double FOB_EQTY;     
	@Column(precision=20,scale=3)
	private Double FOB_EPRICE;   
	@Column(length=50)
	private String WMS_ENTER_UID;
	@Column(length=50)
	private String WMS_ENTER_FPC; 
	private Calendar WMS_ENTER_DATE;
	private String WMS_LAST_UPDATE_UID;
	@Column(length=50)
	private String WMS_LAST_FPC;
	@Column(length=50)
	private Calendar WMS_LAST_UPDATE_DATE;
	@Column(length=50)
	private String WMS_APPROVE_UID; 
	@Column(length=50)
	private String WMS_APPROVE_FPC; 
	private Calendar WMS_APPROVE_DATE; 
	private Long FOB_OTYPE;            
	@Column(length=2)
	private String FOB_DGF_TYPE;
	private Long FOB_SEQNO; 
	private Long FOB_L_SEQNO;
	private Calendar FOB_EDATE;
	
	
	@PrePersist
	public void setCreation() {
		setWMS_ENTER_DATE(Calendar.getInstance());
	}
	
	@PreUpdate
	public void setUpdation() {
		setWMS_LAST_UPDATE_DATE(Calendar.getInstance());
	}
	public Long getFOB_NO() {
		return FOB_NO;
	}
	public void setFOB_NO(Long fOB_NO) {
		FOB_NO = fOB_NO;
	}
	public Calendar getFOB_DATE() {
		return FOB_DATE;
	}
	public void setFOB_DATE(Calendar fOB_DATE) {
		FOB_DATE = fOB_DATE;
	}
	public String getFOB_TIME() {
		return FOB_TIME;
	}
	public void setFOB_TIME(String fOB_TIME) {
		FOB_TIME = fOB_TIME;
	}
	public String getFOB_B_S() {
		return FOB_B_S;
	}
	public void setFOB_B_S(String fOB_B_S) {
		FOB_B_S = fOB_B_S;
	}
	public String getFOB_L_S() {
		return FOB_L_S;
	}
	public void setFOB_L_S(String fOB_L_S) {
		FOB_L_S = fOB_L_S;
	}
	public String getFOB_SHARE_CLASS() {
		return FOB_SHARE_CLASS;
	}
	public void setFOB_SHARE_CLASS(String fOB_SHARE_CLASS) {
		FOB_SHARE_CLASS = fOB_SHARE_CLASS;
	}
	public Long getFOB_FUND() {
		return FOB_FUND;
	}
	public void setFOB_FUND(Long fOB_FUND) {
		FOB_FUND = fOB_FUND;
	}
	public Long getFOB_BROKER() {
		return FOB_BROKER;
	}
	public void setFOB_BROKER(Long fOB_BROKER) {
		FOB_BROKER = fOB_BROKER;
	}
	public Long getFOB_CLIENT() {
		return FOB_CLIENT;
	}
	public void setFOB_CLIENT(Long fOB_CLIENT) {
		FOB_CLIENT = fOB_CLIENT;
	}
	public Long getFOB_STOCK() {
		return FOB_STOCK;
	}
	public void setFOB_STOCK(Long fOB_STOCK) {
		FOB_STOCK = fOB_STOCK;
	}
	public String getFOB_STOCK_NAME() {
		return FOB_STOCK_NAME;
	}
	public void setFOB_STOCK_NAME(String fOB_STOCK_NAME) {
		FOB_STOCK_NAME = fOB_STOCK_NAME;
	}
	public String getFOB_TICKER() {
		return FOB_TICKER;
	}
	public void setFOB_TICKER(String fOB_TICKER) {
		FOB_TICKER = fOB_TICKER;
	}
	public String getFOB_L_M() {
		return FOB_L_M;
	}
	public void setFOB_L_M(String fOB_L_M) {
		FOB_L_M = fOB_L_M;
	}
	public Double getFOB_QTY() {
		return FOB_QTY;
	}
	public void setFOB_QTY(Double fOB_QTY) {
		FOB_QTY = fOB_QTY;
	}
	public Double getFOB_PRICE() {
		return FOB_PRICE;
	}
	public void setFOB_PRICE(Double fOB_PRICE) {
		FOB_PRICE = fOB_PRICE;
	}
	public Double getFOB_VALUE() {
		return FOB_VALUE;
	}
	public void setFOB_VALUE(Double fOB_VALUE) {
		FOB_VALUE = fOB_VALUE;
	}
	public Double getFOB_MPRICE() {
		return FOB_MPRICE;
	}
	public void setFOB_MPRICE(Double fOB_MPRICE) {
		FOB_MPRICE = fOB_MPRICE;
	}
	public Double getFOB_BQTY() {
		return FOB_BQTY;
	}
	public void setFOB_BQTY(Double fOB_BQTY) {
		FOB_BQTY = fOB_BQTY;
	}
	public Double getFOB_BPRICE() {
		return FOB_BPRICE;
	}
	public void setFOB_BPRICE(Double fOB_BPRICE) {
		FOB_BPRICE = fOB_BPRICE;
	}
	public Double getFOB_AQTY() {
		return FOB_AQTY;
	}
	public void setFOB_AQTY(Double fOB_AQTY) {
		FOB_AQTY = fOB_AQTY;
	}
	public Double getFOB_APRICE() {
		return FOB_APRICE;
	}
	public void setFOB_APRICE(Double fOB_APRICE) {
		FOB_APRICE = fOB_APRICE;
	}
	public Double getFOB_HIGH() {
		return FOB_HIGH;
	}
	public void setFOB_HIGH(Double fOB_HIGH) {
		FOB_HIGH = fOB_HIGH;
	}
	public Double getFOB_LOW() {
		return FOB_LOW;
	}
	public void setFOB_LOW(Double fOB_LOW) {
		FOB_LOW = fOB_LOW;
	}
	public Double getFOB_VOLUME() {
		return FOB_VOLUME;
	}
	public void setFOB_VOLUME(Double fOB_VOLUME) {
		FOB_VOLUME = fOB_VOLUME;
	}
	public Double getFOB_BROKER_QTY() {
		return FOB_BROKER_QTY;
	}
	public void setFOB_BROKER_QTY(Double fOB_BROKER_QTY) {
		FOB_BROKER_QTY = fOB_BROKER_QTY;
	}
	public Double getFOB_BROKER_CASH() {
		return FOB_BROKER_CASH;
	}
	public void setFOB_BROKER_CASH(Double fOB_BROKER_CASH) {
		FOB_BROKER_CASH = fOB_BROKER_CASH;
	}
	public Double getFOB_MARGIN_AMT() {
		return FOB_MARGIN_AMT;
	}
	public void setFOB_MARGIN_AMT(Double fOB_MARGIN_AMT) {
		FOB_MARGIN_AMT = fOB_MARGIN_AMT;
	}
	public String getFOB_OSTATUS() {
		return FOB_OSTATUS;
	}
	public void setFOB_OSTATUS(String fOB_OSTATUS) {
		FOB_OSTATUS = fOB_OSTATUS;
	}
	public Double getFOB_EQTY() {
		return FOB_EQTY;
	}
	public void setFOB_EQTY(Double fOB_EQTY) {
		FOB_EQTY = fOB_EQTY;
	}
	public Double getFOB_EPRICE() {
		return FOB_EPRICE;
	}
	public void setFOB_EPRICE(Double fOB_EPRICE) {
		FOB_EPRICE = fOB_EPRICE;
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
	public Long getFOB_OTYPE() {
		return FOB_OTYPE;
	}
	public void setFOB_OTYPE(Long fOB_OTYPE) {
		FOB_OTYPE = fOB_OTYPE;
	}
	public String getFOB_DGF_TYPE() {
		return FOB_DGF_TYPE;
	}
	public void setFOB_DGF_TYPE(String fOB_DGF_TYPE) {
		FOB_DGF_TYPE = fOB_DGF_TYPE;
	}
	public Long getFOB_SEQNO() {
		return FOB_SEQNO;
	}
	public void setFOB_SEQNO(Long fOB_SEQNO) {
		FOB_SEQNO = fOB_SEQNO;
	}
	public Long getFOB_L_SEQNO() {
		return FOB_L_SEQNO;
	}
	public void setFOB_L_SEQNO(Long fOB_L_SEQNO) {
		FOB_L_SEQNO = fOB_L_SEQNO;
	}
	public Calendar getFOB_EDATE() {
		return FOB_EDATE;
	}
	public void setFOB_EDATE(Calendar fOB_EDATE) {
		FOB_EDATE = fOB_EDATE;
	}

}
