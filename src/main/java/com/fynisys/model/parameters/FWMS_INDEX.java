package com.fynisys.model.parameters;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="FWMS_INDEX",uniqueConstraints=
{
@UniqueConstraint(columnNames="WMS_INDEX_DESC",name="INDEXNAME_UNIQUE"),
@UniqueConstraint(columnNames="WMS_SHORT_DESC",name="INDEXSHORTNAME_UNIQUE")
})
public class FWMS_INDEX implements Serializable {
	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	/*
	 * Auto Number - User will not enter this field 
	 */
	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  private int WMS_INDEX_ID; 
	  
	  @Column(length=75)
	  private String WMS_INDEX_DESC;
	  @Column(length=75)
	  private String WMS_SHORT_DESC; 
	  @Column(length=300)
	  private String WMS_COMMENTS; 
	  @Column(length=20)
	  private String WMS_STATUS; 
	  @Column(length=50)
	  private String WMS_ENTER_UID;
	  @Column(length=50)
	  private String WMS_ENTER_FPC; 
	
	  
	  private Calendar WMS_ENTER_DATE; 
	  @Column(length=50)
	  private String WMS_LAST_UPDATE_UID;
	  @Column(length=50)
	  private String WMS_LAST_FPC; 
	  
	  private Calendar WMS_LAST_UPDATE_DATE; 
	  @Column(length=50)
	  private String WMS_APPROVE_UID; 
	  @Column(length=50)
	  private String WMS_APPROVE_FPC; 
	  
	  private Calendar WMS_APPROVE_DATE;

	public int getWMS_INDEX_ID() {
		return WMS_INDEX_ID;
	}

	public void setWMS_INDEX_ID(int wMS_INDEX_ID) {
		WMS_INDEX_ID = wMS_INDEX_ID;
	}

	public String getWMS_INDEX_DESC() {
		return WMS_INDEX_DESC;
	}

	public void setWMS_INDEX_DESC(String wMS_INDEX_DESC) {
		WMS_INDEX_DESC = wMS_INDEX_DESC;
	}

	public String getWMS_SHORT_DESC() {
		return WMS_SHORT_DESC;
	}

	public void setWMS_SHORT_DESC(String wMS_SHORT_DESC) {
		WMS_SHORT_DESC = wMS_SHORT_DESC;
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
		return "FWMS_INDEX [WMS_INDEX_ID=" + WMS_INDEX_ID + ", WMS_INDEX_DESC=" + WMS_INDEX_DESC + ", WMS_SHORT_DESC="
				+ WMS_SHORT_DESC + ", WMS_COMMENTS=" + WMS_COMMENTS + ", WMS_STATUS=" + WMS_STATUS + ", WMS_ENTER_UID="
				+ WMS_ENTER_UID + ", WMS_ENTER_FPC=" + WMS_ENTER_FPC + ", WMS_ENTER_DATE=" + WMS_ENTER_DATE
				+ ", WMS_LAST_UPDATE_UID=" + WMS_LAST_UPDATE_UID + ", WMS_LAST_FPC=" + WMS_LAST_FPC
				+ ", WMS_LAST_UPDATE_DATE=" + WMS_LAST_UPDATE_DATE + ", WMS_APPROVE_UID=" + WMS_APPROVE_UID
				+ ", WMS_APPROVE_FPC=" + WMS_APPROVE_FPC + ", WMS_APPROVE_DATE=" + WMS_APPROVE_DATE + "]";
	}
	  
	
}
