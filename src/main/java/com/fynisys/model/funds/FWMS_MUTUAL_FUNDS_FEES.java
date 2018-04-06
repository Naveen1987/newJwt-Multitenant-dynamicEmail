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
WMS_EDATE	DATE			Effective Date
WMS_SNO	NUMBER (10)	PRIMARY KEY	NOT NULL	Sno
WMS_FUND_ID	NUMBER (4)			Fund Name (Should show fund name and store fund id in thebackend)
WMS_SHARE_CLASS	VARCHAR2 (20)			Share Class
WMS_FEE_PER_FLAG	VARCHAR2 (30)			Fee Falg (Flat Amount or Percentage)
WMS_CAL_FREQUENCY	VARCHAR2 (20)			Frequency
WMS_DIVISORY_DAY	NUMBER (4)			Divisory Days
WMS_CAL_FUND_SC_LEVEL	VARCHAR2 (15)			Calcualte on
WMS_WEEK_DAY	VARCHAR2 (20)			Day (List box show monday.... Sunday in the list al the days)
WMS_FEE_FLAG	VARCHAR2 (35)			Fees Flag ( List Box mANAGMENT Feeadmin etc)
WMS_FEE_PER	NUMBER (266)			fee % (Amount)
WMS_STATUS	VARCHAR2 (20)			Status
WMS_COMMENTS	VARCHAR2 (300)			Comment
WMS_ENTER_UID	VARCHAR2 (20)			
WMS_ENTER_FPC	VARCHAR2 (30)			
WMS_ENTER_DATE	DATE			
WMS_LAST_UPDATE_UID	VARCHAR2 (20)			
WMS_LAST_FPC	VARCHAR2 (30)			
WMS_LAST_UPDATE_DATE	DATE			
WMS_APPROVE_UID	VARCHAR2 (20)			
WMS_APPROVE_FPC	VARCHAR2 (30)			
WMS_APPROVE_DATE	DATE			
FMD_CLIENT_TYPE	NUMBER(15)			Client Type
FMD_LEVEL	VARCHAR2(30)			Level (Client Type Client   Fund )
FMD_CLIENT 	VARCHAR2(10)			Client Name ( You should show client name and in the backend u should store client id)
 */
/*
 * Unique COntraint
 * Effect date, Fund name, Shae class and Fee type
 */
@Entity(name="FWMS_MUTUAL_FUNDS_FEES")
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames= {"WMS_EDATE","WMS_SHARE_CLASS","WMS_FUND_ID","WMS_FEE_PER_FLAG"},
				name="FWMS_MUTUAL_FUNDS_FEES_duplicate_value")
})

public class FWMS_MUTUAL_FUNDS_FEES implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long WMS_SNO;                                         //NUMBER (10)
	private Calendar WMS_EDATE;                                         //DATE
	private long WMS_FUND_ID;                                         //NUMBER (4)
	@Column(length=20)
	private String WMS_SHARE_CLASS;                                         //VARCHAR2 (20)
	@Column(length=30)
	private String WMS_FEE_PER_FLAG;                                         //VARCHAR2 (30)
	@Column(length=20)
	private String WMS_CAL_FREQUENCY;                                         //VARCHAR2 (20)
	private int WMS_DIVISORY_DAY;                                         //NUMBER (4)
	@Column(length=15)
	private String WMS_CAL_FUND_SC_LEVEL;                                         //VARCHAR2 (15)
	@Column(length=20)
	private String WMS_WEEK_DAY;                                         //VARCHAR2 (20)
	@Column(length=35)
	private String WMS_FEE_FLAG;                                         //VARCHAR2 (35)
	@Column(precision=25,scale=6)
	private double WMS_FEE_PER;                                         //NUMBER (266)
	@Column(length=20)
	private String WMS_STATUS;                                         //VARCHAR2 (20)
	@Column(length=300)
	private String WMS_COMMENTS;                                         //VARCHAR2 (300)
	@Column(length=50)
	private String WMS_ENTER_UID;                                         //VARCHAR2 (20)
	@Column(length=50)
	private String WMS_ENTER_FPC;                                         //VARCHAR2 (30)
	private Calendar WMS_ENTER_DATE;                                         //DATE
	@Column(length=50)
	private String WMS_LAST_UPDATE_UID;                                         //VARCHAR2 (20)
	@Column(length=30)
	private String WMS_LAST_FPC;                                         //VARCHAR2 (30)
	private Calendar WMS_LAST_UPDATE_DATE;                                         //DATE
	@Column(length=50)
	private String WMS_APPROVE_UID;                                         //VARCHAR2 (20)
	@Column(length=30)
	private String WMS_APPROVE_FPC;                                         //VARCHAR2 (30)
	private Calendar WMS_APPROVE_DATE;                                         //DATE
	private long FMD_CLIENT_TYPE;                                         //NUMBER(15)
	@Column(length=30)
	private String FMD_LEVEL;                                         //VARCHAR2(30)
	@Column(length=10)
	private long FMD_CLIENT ;                                         //VARCHAR2(10)
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
	public long getWMS_FUND_ID() {
		return WMS_FUND_ID;
	}
	public void setWMS_FUND_ID(long wMS_FUND_ID) {
		WMS_FUND_ID = wMS_FUND_ID;
	}
	public String getWMS_SHARE_CLASS() {
		return WMS_SHARE_CLASS;
	}
	public void setWMS_SHARE_CLASS(String wMS_SHARE_CLASS) {
		WMS_SHARE_CLASS = wMS_SHARE_CLASS;
	}
	public String getWMS_FEE_PER_FLAG() {
		return WMS_FEE_PER_FLAG;
	}
	public void setWMS_FEE_PER_FLAG(String wMS_FEE_PER_FLAG) {
		WMS_FEE_PER_FLAG = wMS_FEE_PER_FLAG;
	}
	public String getWMS_CAL_FREQUENCY() {
		return WMS_CAL_FREQUENCY;
	}
	public void setWMS_CAL_FREQUENCY(String wMS_CAL_FREQUENCY) {
		WMS_CAL_FREQUENCY = wMS_CAL_FREQUENCY;
	}
	public int getWMS_DIVISORY_DAY() {
		return WMS_DIVISORY_DAY;
	}
	public void setWMS_DIVISORY_DAY(int wMS_DIVISORY_DAY) {
		WMS_DIVISORY_DAY = wMS_DIVISORY_DAY;
	}
	public String getWMS_CAL_FUND_SC_LEVEL() {
		return WMS_CAL_FUND_SC_LEVEL;
	}
	public void setWMS_CAL_FUND_SC_LEVEL(String wMS_CAL_FUND_SC_LEVEL) {
		WMS_CAL_FUND_SC_LEVEL = wMS_CAL_FUND_SC_LEVEL;
	}
	public String getWMS_WEEK_DAY() {
		return WMS_WEEK_DAY;
	}
	public void setWMS_WEEK_DAY(String wMS_WEEK_DAY) {
		WMS_WEEK_DAY = wMS_WEEK_DAY;
	}
	public String getWMS_FEE_FLAG() {
		return WMS_FEE_FLAG;
	}
	public void setWMS_FEE_FLAG(String wMS_FEE_FLAG) {
		WMS_FEE_FLAG = wMS_FEE_FLAG;
	}
	public double getWMS_FEE_PER() {
		return WMS_FEE_PER;
	}
	public void setWMS_FEE_PER(double wMS_FEE_PER) {
		WMS_FEE_PER = wMS_FEE_PER;
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
	public long getFMD_CLIENT_TYPE() {
		return FMD_CLIENT_TYPE;
	}
	public void setFMD_CLIENT_TYPE(long fMD_CLIENT_TYPE) {
		FMD_CLIENT_TYPE = fMD_CLIENT_TYPE;
	}
	public String getFMD_LEVEL() {
		return FMD_LEVEL;
	}
	public void setFMD_LEVEL(String fMD_LEVEL) {
		FMD_LEVEL = fMD_LEVEL;
	}
	public Long getFMD_CLIENT() {
		return FMD_CLIENT;
	}
	public void setFMD_CLIENT(Long fMD_CLIENT) {
		FMD_CLIENT = fMD_CLIENT;
	}
	@Override
	public String toString() {
		return "FWMS_MUTUAL_FUNDS_FEES [WMS_SNO=" + WMS_SNO + ", WMS_EDATE=" + WMS_EDATE + ", WMS_FUND_ID="
				+ WMS_FUND_ID + ", WMS_SHARE_CLASS=" + WMS_SHARE_CLASS + ", WMS_FEE_PER_FLAG=" + WMS_FEE_PER_FLAG
				+ ", WMS_CAL_FREQUENCY=" + WMS_CAL_FREQUENCY + ", WMS_DIVISORY_DAY=" + WMS_DIVISORY_DAY
				+ ", WMS_CAL_FUND_SC_LEVEL=" + WMS_CAL_FUND_SC_LEVEL + ", WMS_WEEK_DAY=" + WMS_WEEK_DAY
				+ ", WMS_FEE_FLAG=" + WMS_FEE_FLAG + ", WMS_FEE_PER=" + WMS_FEE_PER + ", WMS_STATUS=" + WMS_STATUS
				+ ", WMS_COMMENTS=" + WMS_COMMENTS + ", WMS_ENTER_UID=" + WMS_ENTER_UID + ", WMS_ENTER_FPC="
				+ WMS_ENTER_FPC + ", WMS_ENTER_DATE=" + WMS_ENTER_DATE + ", WMS_LAST_UPDATE_UID=" + WMS_LAST_UPDATE_UID
				+ ", WMS_LAST_FPC=" + WMS_LAST_FPC + ", WMS_LAST_UPDATE_DATE=" + WMS_LAST_UPDATE_DATE
				+ ", WMS_APPROVE_UID=" + WMS_APPROVE_UID + ", WMS_APPROVE_FPC=" + WMS_APPROVE_FPC
				+ ", WMS_APPROVE_DATE=" + WMS_APPROVE_DATE + ", FMD_CLIENT_TYPE=" + FMD_CLIENT_TYPE + ", FMD_LEVEL="
				+ FMD_LEVEL + ", FMD_CLIENT=" + FMD_CLIENT + "]";
	}
	
}
