package com.fynisys.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name="FUND_PASSWORD_HISTORY")
public class FUND_PASSWORD_HISTORY implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long FPH_ID;
	private String FPH_PASSWORD;
	private long FPH_HISTORY;
	private Calendar FPH_DATE;
	
	/*
	 * Relationship with FUND_USER Table
	 */

	@ManyToOne
	@JoinColumn(name="SVC_UID",foreignKey=@ForeignKey(name="fk_fund_users"))
	@JsonBackReference
	private FUND_USERS  fund_users;
	public FUND_USERS getFund_users() {
		return fund_users;
	}
	public void setFund_users(FUND_USERS fund_users) {
		this.fund_users = fund_users;
	}

	
	public long getFPH_ID() {
		return FPH_ID;
	}
	public void setFPH_ID(long fPH_ID) {
		FPH_ID = fPH_ID;
	}
	public String getFPH_PASSWORD() {
		return FPH_PASSWORD;
	}
	public void setFPH_PASSWORD(String fPH_PASSWORD) {
		FPH_PASSWORD = fPH_PASSWORD;
	}
	public long getFPH_HISTORY() {
		return FPH_HISTORY;
	}
	public void setFPH_HISTORY(long fPH_HISTORY) {
		FPH_HISTORY = fPH_HISTORY;
	}
	public Calendar getFPH_DATE() {
		return FPH_DATE;
	}
	public void setFPH_DATE(Calendar fPH_DATE) {
		FPH_DATE = fPH_DATE;
	}
	
	@Override
	public String toString() {
		return "FUND_PASSWORD_HISTORY [FPH_ID=" + FPH_ID + ", FPH_PASSWORD=" + FPH_PASSWORD + ", FPH_HISTORY="
				+ FPH_HISTORY + ", FPH_DATE=" + FPH_DATE + "]";
	}
	

}
