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
@Table(name="FUND_CURRENCY_MSTR",uniqueConstraints=
{
@UniqueConstraint(columnNames="SVC_NAME",name="CURRENCYNAME_UNIQUE"),
@UniqueConstraint(columnNames="SVC_SHORT_NAME",name="CURRENCYSHORTNAME_UNIQUE")
})
public class FUND_CURRENCY_MSTR implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * Auto Number - User will not enter this field 
	 */
	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  private int SVC_CODE;
	  /*
	   * User will enter currency name
	   */
	  @Column(length=50)
	  private String SVC_NAME;
	  /*
	   * user will enter currency short name 
	   */
	  @Column(length=5)
	  private String SVC_SHORT_NAME;
	  @Column(length=40)
	  private String SVC_ANAME;
	  /*
	   * Record Creation Date - User will not enter this field
	   */
	  private Calendar SVC_DATE;
	  
	  @Column(length=50)
	  private String SVC_UID;
		 
	  @Column(length=119)
	  private String IV_ENTER_UID;
		 
	  private Calendar IV_ENTER_DATE;
		 
	  @Column(length=119)
	  private String IV_APPROVE_UID;
		 
	  private Calendar IV_APPROVE_DATE;
		 
	  @Column(length=119)
	  private String IV_LAST_UPDATE_UID;
		 
	  private Calendar IV_LAST_UPDATE_DATE;
		 
	  @Column(length=300)
	  private String WMS_COMMENTS;
		 
	  @Column(length=20)
	  private String WMS_STATUS;

	public int getSVC_CODE() {
		return SVC_CODE;
	}

	public void setSVC_CODE(int sVC_CODE) {
		SVC_CODE = sVC_CODE;
	}

	public String getSVC_NAME() {
		return SVC_NAME;
	}

	public void setSVC_NAME(String sVC_NAME) {
		SVC_NAME = sVC_NAME;
	}

	public String getSVC_SHORT_NAME() {
		return SVC_SHORT_NAME;
	}

	public void setSVC_SHORT_NAME(String sVC_SHORT_NAME) {
		SVC_SHORT_NAME = sVC_SHORT_NAME;
	}

	public String getSVC_ANAME() {
		return SVC_ANAME;
	}

	public void setSVC_ANAME(String sVC_ANAME) {
		SVC_ANAME = sVC_ANAME;
	}

	public Calendar getSVC_DATE() {
		return SVC_DATE;
	}

	public void setSVC_DATE(Calendar sVC_DATE) {
		SVC_DATE = sVC_DATE;
	}

	public String getSVC_UID() {
		return SVC_UID;
	}

	public void setSVC_UID(String sVC_UID) {
		SVC_UID = sVC_UID;
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
		return "FUND_CURRENCY_MSTR [SVC_CODE=" + SVC_CODE + ", SVC_NAME=" + SVC_NAME + ", SVC_SHORT_NAME="
				+ SVC_SHORT_NAME + ", SVC_ANAME=" + SVC_ANAME + ", SVC_DATE=" + SVC_DATE + ", SVC_UID=" + SVC_UID
				+ ", IV_ENTER_UID=" + IV_ENTER_UID + ", IV_ENTER_DATE=" + IV_ENTER_DATE + ", IV_APPROVE_UID="
				+ IV_APPROVE_UID + ", IV_APPROVE_DATE=" + IV_APPROVE_DATE + ", IV_LAST_UPDATE_UID=" + IV_LAST_UPDATE_UID
				+ ", IV_LAST_UPDATE_DATE=" + IV_LAST_UPDATE_DATE + ", WMS_COMMENTS=" + WMS_COMMENTS + ", WMS_STATUS="
				+ WMS_STATUS + "]";
	}
	
	  
}
