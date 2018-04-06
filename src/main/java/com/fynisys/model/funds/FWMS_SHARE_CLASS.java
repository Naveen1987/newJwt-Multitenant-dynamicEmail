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
WMS_SCLASS_ID	NUMBER (25) 	PRIMARY KEY	NOT NULL	Serial No
WMS_SCLASS_DESC	VARCHAR2 (75)	UNIQUE		Share Class Name
WMS_COMMENTS	VARCHAR2 (300)			Comments
WMS_STATUS	VARCHAR2 (20)			Status
WMS_ENTER_UID	VARCHAR2 (20)			
WMS_ENTER_FPC	VARCHAR2 (30)			
WMS_ENTER_DATE	DATE			
WMS_LAST_UPDATE_UID	VARCHAR2 (20)			
WMS_LAST_FPC	VARCHAR2 (30)			
WMS_LAST_UPDATE_DATE	DATE			
WMS_APPROVE_UID	VARCHAR2 (20)			
WMS_APPROVE_FPC	VARCHAR(30)	
WMS_APPROVE_DATE DATE		
 */
@Entity(name="FWMS_SHARE_CLASS")
@Table(uniqueConstraints= {
@UniqueConstraint(columnNames="WMS_SCLASS_DESC",name="UK_WMS_SCLASS_DESC")
})
public class FWMS_SHARE_CLASS implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long WMS_SCLASS_ID;                                         //NUMBER (25)
	@Column(length=75)
	private String WMS_SCLASS_DESC;                                         //VARCHAR2 (75)
	@Column(length=300)
	private String WMS_COMMENTS;                                         //VARCHAR2 (300)
	@Column(length=20)
	private String WMS_STATUS;                                         //VARCHAR2 (20)
	@Column(length=50)
	private String WMS_ENTER_UID;                                         //VARCHAR2 (20)
	@Column(length=30)
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
	private String WMS_APPROVE_FPC;                                         //VARCHAR(30)
	private Calendar WMS_APPROVE_DATE;
	public long getWMS_SCLASS_ID() {
		return WMS_SCLASS_ID;
	}
	public void setWMS_SCLASS_ID(long wMS_SCLASS_ID) {
		WMS_SCLASS_ID = wMS_SCLASS_ID;
	}
	public String getWMS_SCLASS_DESC() {
		return WMS_SCLASS_DESC;
	}
	public void setWMS_SCLASS_DESC(String wMS_SCLASS_DESC) {
		WMS_SCLASS_DESC = wMS_SCLASS_DESC;
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
	@Override
	public String toString() {
		return "FWMS_SHARE_CLASS [WMS_SCLASS_ID=" + WMS_SCLASS_ID + ", WMS_SCLASS_DESC=" + WMS_SCLASS_DESC
				+ ", WMS_COMMENTS=" + WMS_COMMENTS + ", WMS_STATUS=" + WMS_STATUS + ", WMS_ENTER_UID=" + WMS_ENTER_UID
				+ ", WMS_ENTER_FPC=" + WMS_ENTER_FPC + ", WMS_ENTER_DATE=" + WMS_ENTER_DATE + ", WMS_LAST_UPDATE_UID="
				+ WMS_LAST_UPDATE_UID + ", WMS_LAST_FPC=" + WMS_LAST_FPC + ", WMS_LAST_UPDATE_DATE="
				+ WMS_LAST_UPDATE_DATE + ", WMS_APPROVE_UID=" + WMS_APPROVE_UID + ", WMS_APPROVE_FPC=" + WMS_APPROVE_FPC
				+ "]";
	}

}
