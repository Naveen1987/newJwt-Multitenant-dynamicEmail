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
FMD_SNO	NUMBER (10)	PRIMARY KEY	NOT NULL	Serial no (Auto generated)
FMD_DATE	DATE			effect date
FMD_C_TYPE	NUMBER (15)			cient type
FMD_CID	NUMBER (15)			Stock id or asset id
FMD_INITIAL	NUMBER (156)			
FMD_MAINTENANCE	NUMBER (156)			
FMD_LIQUIDATION	NUMBER (156)			
FMD_UID	VARCHAR2 (15)			
FMD_IU_DATE	DATE			
FMD_CONCENTRATION	NUMBER (156)			Margin %
IV_ENTER_UID	VARCHAR2 (119)			
IV_ENTER_DATE	DATE			
IV_APPROVE_UID	VARCHAR2 (119)			
IV_APPROVE_DATE	DATE			
IV_LAST_UPDATE_UID	VARCHAR2 (119)			
IV_LAST_UPDATE_DATE	DATE			
FMD_MARGIN_LEVEL	VARCHAR2(30)			margen level (Stock Name or Asset)
FMD_LEVEL	VARCHAR2(30)			fund level(Client Type Client Broker Custodian Fund )
FMD_BROKER	NUMBER(10)			broker name
FMD_CUSTODIAN	NUMBER(10)			CUSTODIAN name
FMD_FUND	NUMBER(10)			Fund name
FMD_CLIENT	NUMBER(10)			client name
WMS_COMMENTS	VARCHAR2(300)			comment
WMS_STATUS	VARCHAR2(20)			status
 */
/*
 * Unique Constraint
 * Fund name,Margin level, Asset/stock, level, client name, Client type, broker, custodian 
 * "FMD_LEVEL","FMD_CID","FMD_MARGIN_LEVEL","FMD_FUND","FMD_C_TYPE","FMD_BROKER","FMD_CUSTODIAN","FMD_CLIENT"
 */
@Entity(name="FUND_MAR_MARGIN_MSTR_SECU_LEV")
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames= {"FMD_LEVEL","FMD_CID","FMD_MARGIN_LEVEL","FMD_FUND","FMD_C_TYPE","FMD_BROKER","FMD_CUSTODIAN","FMD_CLIENT"},
				name="FUND_MAR_MARGIN_MSTR_SECU_LEV_DUPLICATE_VALUE")
})
public class FUND_MAR_MARGIN_MSTR_SECU_LEV implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long FMD_SNO;                                         //NUMBER (10)
	private Calendar FMD_DATE;                                         //DATE
	private long FMD_C_TYPE;                                         //NUMBER (15)
	private long FMD_CID;                                         //NUMBER (15)
	private long FMD_INITIAL;                                         //NUMBER (156)
	private long FMD_MAINTENANCE;                                         //NUMBER (156)
	private long FMD_LIQUIDATION;                                         //NUMBER (156)
	@Column(length=15)
	private String FMD_UID;                                         //VARCHAR2 (15)
	private Calendar FMD_IU_DATE;                                         //DATE
	@Column(precision=25,scale=7)
	private double FMD_CONCENTRATION;                                         //NUMBER (156)
	@Column(length=119)
	private String IV_ENTER_UID;                                         //VARCHAR2 (119)
	private Calendar IV_ENTER_DATE;                                         //DATE
	@Column(length=119)
	private String IV_APPROVE_UID;                                         //VARCHAR2 (119)
	private Calendar IV_APPROVE_DATE;                                         //DATE
	@Column(length=119)
	private String IV_LAST_UPDATE_UID;                                         //VARCHAR2 (119)
	private Calendar IV_LAST_UPDATE_DATE;                                         //DATE
	@Column(length=30)
	private String FMD_MARGIN_LEVEL;                                         //VARCHAR2(30)
	@Column(length=30)
	private String FMD_LEVEL;                                         //VARCHAR2(30)
	private long FMD_BROKER;                                         //NUMBER(10)
	private long FMD_CUSTODIAN;                                         //NUMBER(10)
	private long FMD_FUND;                                         //NUMBER(10)
	private long FMD_CLIENT;                                         //NUMBER(10)
	@Column(length=300)
	private String WMS_COMMENTS;                                         //VARCHAR2(300)
	@Column(length=20)
	private String WMS_STATUS;                                         //VARCHAR2(20)
	public long getFMD_SNO() {
		return FMD_SNO;
	}
	public void setFMD_SNO(long fMD_SNO) {
		FMD_SNO = fMD_SNO;
	}
	public Calendar getFMD_DATE() {
		return FMD_DATE;
	}
	public void setFMD_DATE(Calendar fMD_DATE) {
		FMD_DATE = fMD_DATE;
	}
	public long getFMD_C_TYPE() {
		return FMD_C_TYPE;
	}
	public void setFMD_C_TYPE(long fMD_C_TYPE) {
		FMD_C_TYPE = fMD_C_TYPE;
	}
	public long getFMD_CID() {
		return FMD_CID;
	}
	public void setFMD_CID(long fMD_CID) {
		FMD_CID = fMD_CID;
	}
	public long getFMD_INITIAL() {
		return FMD_INITIAL;
	}
	public void setFMD_INITIAL(long fMD_INITIAL) {
		FMD_INITIAL = fMD_INITIAL;
	}
	public long getFMD_MAINTENANCE() {
		return FMD_MAINTENANCE;
	}
	public void setFMD_MAINTENANCE(long fMD_MAINTENANCE) {
		FMD_MAINTENANCE = fMD_MAINTENANCE;
	}
	public long getFMD_LIQUIDATION() {
		return FMD_LIQUIDATION;
	}
	public void setFMD_LIQUIDATION(long fMD_LIQUIDATION) {
		FMD_LIQUIDATION = fMD_LIQUIDATION;
	}
	public String getFMD_UID() {
		return FMD_UID;
	}
	public void setFMD_UID(String fMD_UID) {
		FMD_UID = fMD_UID;
	}
	public Calendar getFMD_IU_DATE() {
		return FMD_IU_DATE;
	}
	public void setFMD_IU_DATE(Calendar fMD_IU_DATE) {
		FMD_IU_DATE = fMD_IU_DATE;
	}
	public double getFMD_CONCENTRATION() {
		return FMD_CONCENTRATION;
	}
	public void setFMD_CONCENTRATION(double fMD_CONCENTRATION) {
		FMD_CONCENTRATION = fMD_CONCENTRATION;
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
	public String getFMD_MARGIN_LEVEL() {
		return FMD_MARGIN_LEVEL;
	}
	public void setFMD_MARGIN_LEVEL(String fMD_MARGIN_LEVEL) {
		FMD_MARGIN_LEVEL = fMD_MARGIN_LEVEL;
	}
	public String getFMD_LEVEL() {
		return FMD_LEVEL;
	}
	public void setFMD_LEVEL(String fMD_LEVEL) {
		FMD_LEVEL = fMD_LEVEL;
	}
	public long getFMD_BROKER() {
		return FMD_BROKER;
	}
	public void setFMD_BROKER(long fMD_BROKER) {
		FMD_BROKER = fMD_BROKER;
	}
	public long getFMD_CUSTODIAN() {
		return FMD_CUSTODIAN;
	}
	public void setFMD_CUSTODIAN(long fMD_CUSTODIAN) {
		FMD_CUSTODIAN = fMD_CUSTODIAN;
	}
	public long getFMD_FUND() {
		return FMD_FUND;
	}
	public void setFMD_FUND(long fMD_FUND) {
		FMD_FUND = fMD_FUND;
	}
	public long getFMD_CLIENT() {
		return FMD_CLIENT;
	}
	public void setFMD_CLIENT(long fMD_CLIENT) {
		FMD_CLIENT = fMD_CLIENT;
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
		return "FUND_MAR_MARGIN_MSTR_SECU_LEV [FMD_SNO=" + FMD_SNO + ", FMD_DATE=" + FMD_DATE + ", FMD_C_TYPE="
				+ FMD_C_TYPE + ", FMD_CID=" + FMD_CID + ", FMD_INITIAL=" + FMD_INITIAL + ", FMD_MAINTENANCE="
				+ FMD_MAINTENANCE + ", FMD_LIQUIDATION=" + FMD_LIQUIDATION + ", FMD_UID=" + FMD_UID + ", FMD_IU_DATE="
				+ FMD_IU_DATE + ", FMD_CONCENTRATION=" + FMD_CONCENTRATION + ", IV_ENTER_UID=" + IV_ENTER_UID
				+ ", IV_ENTER_DATE=" + IV_ENTER_DATE + ", IV_APPROVE_UID=" + IV_APPROVE_UID + ", IV_APPROVE_DATE="
				+ IV_APPROVE_DATE + ", IV_LAST_UPDATE_UID=" + IV_LAST_UPDATE_UID + ", IV_LAST_UPDATE_DATE="
				+ IV_LAST_UPDATE_DATE + ", FMD_MARGIN_LEVEL=" + FMD_MARGIN_LEVEL + ", FMD_LEVEL=" + FMD_LEVEL
				+ ", FMD_BROKER=" + FMD_BROKER + ", FMD_CUSTODIAN=" + FMD_CUSTODIAN + ", FMD_FUND=" + FMD_FUND
				+ ", FMD_CLIENT=" + FMD_CLIENT + ", WMS_COMMENTS=" + WMS_COMMENTS + ", WMS_STATUS=" + WMS_STATUS + "]";
	}

}
