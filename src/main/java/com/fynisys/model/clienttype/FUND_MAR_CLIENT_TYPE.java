package com.fynisys.model.clienttype;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name="FUND_MAR_CLIENT_TYPE")
@Table(uniqueConstraints= {
		
	@UniqueConstraint(columnNames="FCT_NAME",name="UK_FCT_NAME"),
	@UniqueConstraint(columnNames="FCT_SHORT_NAME",name="UK_FCT_SHORT_NAME"),
})
public class FUND_MAR_CLIENT_TYPE implements Serializable{
	  
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  @Id
	  @GeneratedValue
	  private long FCT_ID;              // NUMBER(15),
	  private Calendar FCT_DATE;          //   DATE,
	  @Column(length=55)
	  private String FCT_NAME;            // VARCHAR2(55 BYTE),
	  @Column(length=15)
	  private String FCT_SHORT_NAME;
	  @Column(precision=25,scale=6)
	  private double FCT_MARGIN_LIMIT;    // NUMBER(25,6),
	  @Column(length=15)
	  private String FCT_UID;             // VARCHAR2(15 BYTE),
	  private String FCT_IU_DATE;         // DATE,
	  @Column(precision=10,scale=3)
	  private double FCT_CUSTODY_FEE;     // NUMBER(10,3),
	  /*
	   * Standard parameter
	   */
	  @Column(length=219)
	  private String IV_ENTER_UID; 
	  private Calendar IV_ENTER_DATE;
	  @Column(length=219)
	  private String IV_APPROVE_UID; 
	  private Calendar IV_APPROVE_DATE;
	  @Column(length=219)
	  private String IV_LAST_UPDATE_UID;
	  private Calendar IV_LAST_UPDATE_DATE;
	  @Column(length=300)
	  private String WMS_COMMENTS;
	  @Column(length=20)
	  private String WMS_STATUS;
	  
	public long getFCT_ID() {
		return FCT_ID;
	}
	public void setFCT_ID(long fCT_ID) {
		FCT_ID = fCT_ID;
	}
	public Calendar getFCT_DATE() {
		return FCT_DATE;
	}
	public void setFCT_DATE(Calendar fCT_DATE) {
		FCT_DATE = fCT_DATE;
	}
	public String getFCT_NAME() {
		return FCT_NAME;
	}
	public void setFCT_NAME(String fCT_NAME) {
		FCT_NAME = fCT_NAME;
	}
	public String getFCT_SHORT_NAME() {
		return FCT_SHORT_NAME;
	}
	public void setFCT_SHORT_NAME(String fCT_SHORT_NAME) {
		FCT_SHORT_NAME = fCT_SHORT_NAME;
	}
	public double getFCT_MARGIN_LIMIT() {
		return FCT_MARGIN_LIMIT;
	}
	public void setFCT_MARGIN_LIMIT(double fCT_MARGIN_LIMIT) {
		FCT_MARGIN_LIMIT = fCT_MARGIN_LIMIT;
	}
	public String getFCT_UID() {
		return FCT_UID;
	}
	public void setFCT_UID(String fCT_UID) {
		FCT_UID = fCT_UID;
	}
	public String getFCT_IU_DATE() {
		return FCT_IU_DATE;
	}
	public void setFCT_IU_DATE(String fCT_IU_DATE) {
		FCT_IU_DATE = fCT_IU_DATE;
	}
	public double getFCT_CUSTODY_FEE() {
		return FCT_CUSTODY_FEE;
	}
	public void setFCT_CUSTODY_FEE(double fCT_CUSTODY_FEE) {
		FCT_CUSTODY_FEE = fCT_CUSTODY_FEE;
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
		return "FUND_MAR_CLIENT_TYPE [FCT_ID=" + FCT_ID + ", FCT_DATE=" + FCT_DATE + ", FCT_NAME=" + FCT_NAME
				+ ", FCT_MARGIN_LIMIT=" + FCT_MARGIN_LIMIT + ", FCT_UID=" + FCT_UID + ", FCT_IU_DATE=" + FCT_IU_DATE
				+ ", FCT_CUSTODY_FEE=" + FCT_CUSTODY_FEE + ", IV_ENTER_UID=" + IV_ENTER_UID + ", IV_ENTER_DATE="
				+ IV_ENTER_DATE + ", IV_APPROVE_UID=" + IV_APPROVE_UID + ", IV_APPROVE_DATE=" + IV_APPROVE_DATE
				+ ", IV_LAST_UPDATE_UID=" + IV_LAST_UPDATE_UID + ", IV_LAST_UPDATE_DATE=" + IV_LAST_UPDATE_DATE
				+ ", WMS_COMMENTS=" + WMS_COMMENTS + ", WMS_STATUS=" + WMS_STATUS + "]";
	}
	
}
