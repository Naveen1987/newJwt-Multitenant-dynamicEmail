package com.fynisys.model.parameters;
/*
 * Asset Parameter
 */
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
@Table(name="FUND_INSTRUMENT_MSTR",uniqueConstraints=
{
@UniqueConstraint(columnNames="SVI_NAME",name="ASSETNAME_UNIQUE"),
@UniqueConstraint(columnNames="SVI_SHORT_NAME",name="ASSETSHORTNAME_UNIQUE")
})
public class FUND_INSTRUMENT_MSTR implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Auto Number - User will not enter this field        
	 */
	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 private int SVI_CODE;
	 /*
	  *  USER WILL enter Asset Name
	  */
	 @Column(length=50)
	 private String SVI_NAME;
	 
	 @Column(length=50)
	 private String SVI_ANAME;
	 /*
	  * User will enter asset short name
	  */
	 @Column(length=10)
	 private String SVI_SHORT_NAME;
	 
	 private long SVI_GROUP;
	 /*
	  * IT will store record creation date       - User will not enter this field 
	  */
	 private Calendar SVI_DATE;
	 
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

	public int getSVI_CODE() {
		return SVI_CODE;
	}

	public void setSVI_CODE(int sVI_CODE) {
		SVI_CODE = sVI_CODE;
	}

	public String getSVI_NAME() {
		return SVI_NAME;
	}

	public void setSVI_NAME(String sVI_NAME) {
		SVI_NAME = sVI_NAME;
	}

	public String getSVI_ANAME() {
		return SVI_ANAME;
	}

	public void setSVI_ANAME(String sVI_ANAME) {
		SVI_ANAME = sVI_ANAME;
	}

	public String getSVI_SHORT_NAME() {
		return SVI_SHORT_NAME;
	}

	public void setSVI_SHORT_NAME(String sVI_SHORT_NAME) {
		SVI_SHORT_NAME = sVI_SHORT_NAME;
	}

	public long getSVI_GROUP() {
		return SVI_GROUP;
	}

	public void setSVI_GROUP(long sVI_GROUP) {
		SVI_GROUP = sVI_GROUP;
	}

	public Calendar getSVI_DATE() {
		return SVI_DATE;
	}

	public void setSVI_DATE(Calendar sVI_DATE) {
		SVI_DATE = sVI_DATE;
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
		return "FUND_INSTRUMENT_MSTR [SVI_CODE=" + SVI_CODE + ", SVI_NAME=" + SVI_NAME + ", SVI_ANAME=" + SVI_ANAME
				+ ", SVI_SHORT_NAME=" + SVI_SHORT_NAME + ", SVI_GROUP=" + SVI_GROUP + ", SVI_DATE=" + SVI_DATE
				+ ", SVC_UID=" + SVC_UID + ", IV_ENTER_UID=" + IV_ENTER_UID + ", IV_ENTER_DATE=" + IV_ENTER_DATE
				+ ", IV_APPROVE_UID=" + IV_APPROVE_UID + ", IV_APPROVE_DATE=" + IV_APPROVE_DATE
				+ ", IV_LAST_UPDATE_UID=" + IV_LAST_UPDATE_UID + ", IV_LAST_UPDATE_DATE=" + IV_LAST_UPDATE_DATE
				+ ", WMS_COMMENTS=" + WMS_COMMENTS + ", WMS_STATUS=" + WMS_STATUS + "]";
	}
	 
}
