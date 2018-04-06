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

@Entity(name="FUND_CLIENT_DOCUMENTS")
public class FUND_CLIENT_DOCUMENTS {
	@Id
	@GeneratedValue
	@Column(length=15)
	 private Long SCD_CLIENT;
	private Calendar SCD_EFFECT_DATE;
	@Column(length=15)
	 private Long SCD_TYPE;
	@Column(length=100)
	 private String SCD_NAME;
	@Column(length=15)
	 private Long SCD_DOC_TYPE;
	private Calendar SCD_DOC_IDATE;
	private Calendar SCD_DOC_EDATE;
	@Column(length=50)
	 private String SCD_DOC_IDNO;
	@Column(length=50)
	 private String SCD_STATUS;
	@Column(length=100)
	 private String SCD_COMMENTS;
	@Column(length=119)
	 private String IV_ENTER_UID;
	private Calendar IV_ENTER_DATE;
	@Column(length=119)
	 private String IV_APPROVE_UID;
	private Calendar IV_APPROVE_DATE;
	@Column(length=119)
	 private String IV_LAST_UPDATE_UID;
	private Calendar IV_LAST_UPDATE_DATE;
	private Calendar SCD_UAE_RED_EDATE;
	@Column(length=10)
	 private Long CRM_FUND_NO;
	@Column(length=25)
	 private String CRM_FUND_REF_NO;
	
	@ManyToOne
	@JoinColumn(name="RI_WMS_CODE",foreignKey=@ForeignKey(name="fk_CLIENT_DOCUMENTS_INVESTOR"))
	@JsonBackReference
	private RE_INVESTOR rE_INVESTOR;
	
	public RE_INVESTOR getrE_INVESTOR() {
		return rE_INVESTOR;
	}
	public void setrE_INVESTOR(RE_INVESTOR rE_INVESTOR) {
		this.rE_INVESTOR = rE_INVESTOR;
	}
	
	
	public Long getSCD_CLIENT() {
		return SCD_CLIENT;
	}
	public void setSCD_CLIENT(Long sCD_CLIENT) {
		SCD_CLIENT = sCD_CLIENT;
	}
	public Calendar getSCD_EFFECT_DATE() {
		return SCD_EFFECT_DATE;
	}
	public void setSCD_EFFECT_DATE(Calendar sCD_EFFECT_DATE) {
		SCD_EFFECT_DATE = sCD_EFFECT_DATE;
	}
	public Long getSCD_TYPE() {
		return SCD_TYPE;
	}
	public void setSCD_TYPE(Long sCD_TYPE) {
		SCD_TYPE = sCD_TYPE;
	}
	public String getSCD_NAME() {
		return SCD_NAME;
	}
	public void setSCD_NAME(String sCD_NAME) {
		SCD_NAME = sCD_NAME;
	}
	public Long getSCD_DOC_TYPE() {
		return SCD_DOC_TYPE;
	}
	public void setSCD_DOC_TYPE(Long sCD_DOC_TYPE) {
		SCD_DOC_TYPE = sCD_DOC_TYPE;
	}
	public Calendar getSCD_DOC_IDATE() {
		return SCD_DOC_IDATE;
	}
	public void setSCD_DOC_IDATE(Calendar sCD_DOC_IDATE) {
		SCD_DOC_IDATE = sCD_DOC_IDATE;
	}
	public Calendar getSCD_DOC_EDATE() {
		return SCD_DOC_EDATE;
	}
	public void setSCD_DOC_EDATE(Calendar sCD_DOC_EDATE) {
		SCD_DOC_EDATE = sCD_DOC_EDATE;
	}
	public String getSCD_DOC_IDNO() {
		return SCD_DOC_IDNO;
	}
	public void setSCD_DOC_IDNO(String sCD_DOC_IDNO) {
		SCD_DOC_IDNO = sCD_DOC_IDNO;
	}
	public String getSCD_STATUS() {
		return SCD_STATUS;
	}
	public void setSCD_STATUS(String sCD_STATUS) {
		SCD_STATUS = sCD_STATUS;
	}
	public String getSCD_COMMENTS() {
		return SCD_COMMENTS;
	}
	public void setSCD_COMMENTS(String sCD_COMMENTS) {
		SCD_COMMENTS = sCD_COMMENTS;
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
	public Calendar getSCD_UAE_RED_EDATE() {
		return SCD_UAE_RED_EDATE;
	}
	public void setSCD_UAE_RED_EDATE(Calendar sCD_UAE_RED_EDATE) {
		SCD_UAE_RED_EDATE = sCD_UAE_RED_EDATE;
	}
	public Long getCRM_FUND_NO() {
		return CRM_FUND_NO;
	}
	public void setCRM_FUND_NO(Long cRM_FUND_NO) {
		CRM_FUND_NO = cRM_FUND_NO;
	}
	public String getCRM_FUND_REF_NO() {
		return CRM_FUND_REF_NO;
	}
	public void setCRM_FUND_REF_NO(String cRM_FUND_REF_NO) {
		CRM_FUND_REF_NO = cRM_FUND_REF_NO;
	}
}
