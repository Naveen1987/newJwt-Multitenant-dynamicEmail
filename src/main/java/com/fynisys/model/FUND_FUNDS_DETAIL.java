package com.fynisys.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name="FUND_FUNDS_DETAIL")
public class FUND_FUNDS_DETAIL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="key_gen",strategy="guid")
	@GeneratedValue(generator="key_gen")
	@Column(length=45)
	private String FID;
	 @Column(length=250)
	private String FFUNDS;
	 @Column(length=250)
	private String FDETAILS;
	 @Column(length=45)
	private String FCREATEDBY;
	private Calendar FDATE;
	@Column(length=45)
	private String FMODIFIEDBY;
	private Calendar FLAST_CHANGE;
	
	public String getFID() {
		return FID;
	}
	public void setFID(String fID) {
		FID = fID;
	}

	/**
	 * 
	 * Relationship with fund_user_funds
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="FFID",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonManagedReference
	private List<FUND_USERS_FUNDS> fund_users_funds=new ArrayList<FUND_USERS_FUNDS>();
	
	public List<FUND_USERS_FUNDS> getFund_users_funds() {
		return fund_users_funds;
	}
	public void setFund_users_funds(List<FUND_USERS_FUNDS> fund_users_funds) {
		this.fund_users_funds = fund_users_funds;
	}
	
	
	public String getFFUNDS() {
		return FFUNDS;
	}
	public void setFFUNDS(String fFUNDS) {
		FFUNDS = fFUNDS;
	}
	public String getFDETAILS() {
		return FDETAILS;
	}
	public void setFDETAILS(String fDETAILS) {
		FDETAILS = fDETAILS;
	}
	public String getFCREATEDBY() {
		return FCREATEDBY;
	}
	public void setFCREATEDBY(String fCREATEDBY) {
		FCREATEDBY = fCREATEDBY;
	}
	public Calendar getFDATE() {
		return FDATE;
	}
	public void setFDATE(Calendar fDATE) {
		FDATE = fDATE;
	}
	public String getFMODIFIEDBY() {
		return FMODIFIEDBY;
	}
	public void setFMODIFIEDBY(String fMODIFIEDBY) {
		FMODIFIEDBY = fMODIFIEDBY;
	}
	public Calendar getFLAST_CHANGE() {
		return FLAST_CHANGE;
	}
	public void setFLAST_CHANGE(Calendar fLAST_CHANGE) {
		FLAST_CHANGE = fLAST_CHANGE;
	}
	
	@Override
	public String toString() {
		return "FUND_FUNDS_DETAIL [FID=" + FID + ", FFUNDS=" + FFUNDS + ", FDETAILS=" + FDETAILS + ", FCREATEDBY="
				+ FCREATEDBY + ", FDATE=" + FDATE + ", FMODIFIEDBY=" + FMODIFIEDBY + ", FLAST_CHANGE=" + FLAST_CHANGE
				+ "]";
	}

	
}
