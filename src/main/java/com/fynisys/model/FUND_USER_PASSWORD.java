package com.fynisys.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name="FUND_USER_PASSWORD")
public class FUND_USER_PASSWORD  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(length=45)
	private String FID;
	private Calendar FCDATE;
	@Column(length=15)
	 private String FOPD; 
	
	@Column(length=45)
	private String SVC_UID;
	
		
	
	public String getFID() {
		return FID;
	}
	public void setFID(String fID) {
		FID = fID;
	}
	public Calendar getFCDATE() {
		return FCDATE;
	}
	public void setFCDATE(Calendar fCDATE) {
		FCDATE = fCDATE;
	}
	public String getFOPD() {
		return FOPD;
	}
	public void setFOPD(String fOPD) {
		FOPD = fOPD;
	}
	
	public String getSVC_UID() {
		return SVC_UID;
	}
	public void setSVC_UID(String sVC_UID) {
		SVC_UID = sVC_UID;
	}
	@Override
	public String toString() {
		return "FUND_USER_PASSWORD [FID=" + FID + ", FCDATE=" + FCDATE + ", FOPD=" + FOPD + ", SVC_UID=" + SVC_UID
				+ "]";
	}
	
	
}
