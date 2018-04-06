package com.fynisys.model.people;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="FUND_ACCOUNT_MSTR")
public class FUND_ACCOUNT_MSTR {
	@Id
	@GeneratedValue
	private long SVA_CODE;
	@Column(length=50)
	private String SVA_NAME;
	@Column(length=119)
	private String IV_ENTER_UID;                                         //VARCHAR2(119 BYTE)
	private Calendar IV_ENTER_DATE;                                         //DATE
	@Column(length=119)
	private String IV_APPROVE_UID;                                         //VARCHAR2(119 BYTE)
	private Calendar IV_APPROVE_DATE;                                         //DATE
	@Column(length=119)
	private String IV_LAST_UPDATE_UID;                                         //VARCHAR2(119 BYTE)
	private Calendar IV_LAST_UPDATE_DATE;                                         //DATE
	@Column(length=300)
	private String WMS_COMMENTS;                                         //VARCHAR2(300)
	@Column(length=20)
	private String WMS_STATUS;                                         //VARCHAR2(20)
	public long getSVA_CODE() {
		return SVA_CODE;
	}
	public void setSVA_CODE(long sVA_CODE) {
		SVA_CODE = sVA_CODE;
	}
	public String getSVA_NAME() {
		return SVA_NAME;
	}
	public void setSVA_NAME(String sVA_NAME) {
		SVA_NAME = sVA_NAME;
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
}
