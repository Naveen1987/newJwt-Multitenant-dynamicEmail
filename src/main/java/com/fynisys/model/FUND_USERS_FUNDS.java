package com.fynisys.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name="FUND_USERS_FUNDS")
public class FUND_USERS_FUNDS  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name="key_gen",strategy="guid")
	@GeneratedValue(generator="key_gen")
	@Column(length=45)
	private String FID; 
	@Column(length=10)
	private String   FFUNDS;
	@Column(length=45)
	private String SVC_UID;
	/*
	 * Reslation ship with FUND_FUNDS_DEATILS
	 * 
	 */
	@ManyToOne
	@JoinColumn(name="FFID",foreignKey=@ForeignKey(name="fk_fund_funds_details"))
	@JsonBackReference
	private FUND_FUNDS_DETAIL FFID;

	public FUND_FUNDS_DETAIL getFFID() {
		return FFID;
	}
	public void setFFID(FUND_FUNDS_DETAIL fFID) {
		FFID = fFID;
	}

	public String getFID() {
		return FID;
	}
	public void setFID(String fID) {
		FID = fID;
	}
	public String getFFUNDS() {
		return FFUNDS;
	}
	public void setFFUNDS(String fFUNDS) {
		FFUNDS = fFUNDS;
	}
	public String getSVC_UID() {
		return SVC_UID;
	}
	public void setSVC_UID(String sVC_UID) {
		SVC_UID = sVC_UID;
	}
	
	@Override
	public String toString() {
		return "FUND_USERS_FUNDS [FID=" + FID + ", FFUNDS=" + FFUNDS + ", SVC_UID=" + SVC_UID + "]";
	}
	
}
