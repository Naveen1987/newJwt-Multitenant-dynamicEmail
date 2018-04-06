package com.fynisys.model.stock;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/*
 * Coupan Table
 * 
 * FBI_DATE	effect date
 * FBI_INT		Int Rate
 * FBI_BOND is link between fund_share_copmpany_mstr (SVC_CODE) and Fund_BOND_INT_RATE (FBI_BOND)
 * 
 */

@Entity
@Table(name="FUND_BOND_INT_RATE")
public class FUND_BOND_INT_RATE implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue //made it auto generated
	private long FBI_BOND;	//NUMBER(10),
	private Calendar FBI_DATE;	//DATE
	@Column(precision=15,scale=6)
	private double FBI_INT;		//NUMBER(15,6));
	@ManyToOne
	@JoinColumn(name="SVC_CODE")
	@JsonBackReference
    private FUND_SHARE_COMPANY_MSTR SVC_CODE;
	public long getFBI_BOND() {
		return FBI_BOND;
	}
	public void setFBI_BOND(long fBI_BOND) {
		FBI_BOND = fBI_BOND;
	}
	public Calendar getFBI_DATE() {
		return FBI_DATE;
	}
	public void setFBI_DATE(Calendar fBI_DATE) {
		FBI_DATE = fBI_DATE;
	}
	public double getFBI_INT() {
		return FBI_INT;
	}
	public void setFBI_INT(double fBI_INT) {
		FBI_INT = fBI_INT;
	}
	public FUND_SHARE_COMPANY_MSTR getSVC_CODE() {
		return SVC_CODE;
	}
	public void setSVC_CODE(FUND_SHARE_COMPANY_MSTR sVC_CODE) {
		SVC_CODE = sVC_CODE;
	}
	@Override
	public String toString() {
		return "FUND_BOND_INT_RATE [FBI_BOND=" + FBI_BOND + ", FBI_DATE=" + FBI_DATE + ", FBI_INT=" + FBI_INT
				+ ", SVC_CODE=" + SVC_CODE + "]";
	}
    
}
