package com.fynisys.model.clienttype;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
/*
WMS_SNO	NUMBER (4)	PRIMARY KEY	NOT NULL	Serial No
WMS_CLIENT_TYPE	NUMBER (4)			Document Type
WMS_DOCUMENT_TYPE	NUMBER (4)			
WMS_STATUS	VARCHAR2 (20)			Status
WMS_COMMENTS	VARCHAR2 (300)			Commnets
WMS_ENTER_UID	VARCHAR2 (20)			
WMS_ENTER_FPC	VARCHAR2 (30)			
WMS_ENTER_DATE	DATE			
WMS_LAST_UPDATE_UID	VARCHAR2 (20)			
WMS_LAST_FPC	VARCHAR2 (30)			
WMS_LAST_UPDATE_DATE	DATE			
WMS_APPROVE_UID	VARCHAR2 (20)			
WMS_APPROVE_FPC	VARCHAR2 (30)			
WMS_APPROVE_DATE	DATE			
 */
@Entity(name="FWMS_REQUIRED_DOCUMENTS")
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames= {"WMS_CLIENT_TYPE","WMS_DOCUMENT_TYPE"},name="FWMS_REQUIRED_DOCUMENTS_column_dup")
		})
public class FWMS_REQUIRED_DOCUMENTS implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long WMS_SNO;                                         //NUMBER (4)
	private long WMS_CLIENT_TYPE;                                         //NUMBER (4)
	private long WMS_DOCUMENT_TYPE;                                         //NUMBER (4)
	@Column(length=20)
	private String WMS_STATUS;                                         //VARCHAR2 (20)
	@Column(length=300)
	private String WMS_COMMENTS;                                         //VARCHAR2 (300)
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
	private String WMS_APPROVE_FPC;                                         //VARCHAR2 (30)
	private Calendar WMS_APPROVE_DATE;                                         //DATE
	public long getWMS_SNO() {
		return WMS_SNO;
	}
	public void setWMS_SNO(long wMS_SNO) {
		WMS_SNO = wMS_SNO;
	}
	public long getWMS_CLIENT_TYPE() {
		return WMS_CLIENT_TYPE;
	}
	public void setWMS_CLIENT_TYPE(long wMS_CLIENT_TYPE) {
		WMS_CLIENT_TYPE = wMS_CLIENT_TYPE;
	}
	public long getWMS_DOCUMENT_TYPE() {
		return WMS_DOCUMENT_TYPE;
	}
	public void setWMS_DOCUMENT_TYPE(long wMS_DOCUMENT_TYPE) {
		WMS_DOCUMENT_TYPE = wMS_DOCUMENT_TYPE;
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
	@Override
	public String toString() {
		return "FWMS_REQUIRED_DOCUMENTS [WMS_SNO=" + WMS_SNO + ", WMS_CLIENT_TYPE=" + WMS_CLIENT_TYPE
				+ ", WMS_DOCUMENT_TYPE=" + WMS_DOCUMENT_TYPE + ", WMS_STATUS=" + WMS_STATUS + ", WMS_COMMENTS="
				+ WMS_COMMENTS + ", WMS_ENTER_UID=" + WMS_ENTER_UID + ", WMS_ENTER_FPC=" + WMS_ENTER_FPC
				+ ", WMS_ENTER_DATE=" + WMS_ENTER_DATE + ", WMS_LAST_UPDATE_UID=" + WMS_LAST_UPDATE_UID
				+ ", WMS_LAST_FPC=" + WMS_LAST_FPC + ", WMS_LAST_UPDATE_DATE=" + WMS_LAST_UPDATE_DATE
				+ ", WMS_APPROVE_UID=" + WMS_APPROVE_UID + ", WMS_APPROVE_FPC=" + WMS_APPROVE_FPC
				+ ", WMS_APPROVE_DATE=" + WMS_APPROVE_DATE + "]";
	}

}
