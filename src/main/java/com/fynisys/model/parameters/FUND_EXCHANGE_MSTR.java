package com.fynisys.model.parameters;

/*
 * Exchange Parameter
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="FUND_EXCHANGE_MSTR"
,uniqueConstraints=
{
@UniqueConstraint(columnNames="SVE_NAME",name="EXCHANGENAME_UNIQUE"),
@UniqueConstraint(columnNames="SVE_SHORT_NAME",name="EXCHANGECODE_UNIQUE")
})
public class FUND_EXCHANGE_MSTR implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Auto Number
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int SVE_CODE;
	
	/*
	 * User will enter Exchange Name
	 */
	@Column(length=50)
	private String  SVE_NAME;
	@Column(length=50)
	private String  SVE_ANAME;
	
	/*
	 * User Will enter short name
	 */
	@Column(length=10)
	private String SVE_SHORT_NAME;
	/*
	 * Record Creation Date          
	 */
	private Calendar SVE_DATE;
	
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

	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="FEH_EXCHANGE",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonManagedReference
	private List<FUND_EXCHANGE_HOLIDAY>  fUND_EXCHANGE_HOLIDAY=new ArrayList<FUND_EXCHANGE_HOLIDAY>();
	
	public List<FUND_EXCHANGE_HOLIDAY> getfUND_EXCHANGE_HOLIDAY() {
		return fUND_EXCHANGE_HOLIDAY;
	}

	public void setfUND_EXCHANGE_HOLIDAY(List<FUND_EXCHANGE_HOLIDAY> fUND_EXCHANGE_HOLIDAY) {
		this.fUND_EXCHANGE_HOLIDAY = fUND_EXCHANGE_HOLIDAY;
	}

	
	public int getSVE_CODE() {
		return SVE_CODE;
	}

	public void setSVE_CODE(int sVE_CODE) {
		SVE_CODE = sVE_CODE;
	}

	public String getSVE_NAME() {
		return SVE_NAME;
	}

	public void setSVE_NAME(String sVE_NAME) {
		SVE_NAME = sVE_NAME;
	}

	public String getSVE_ANAME() {
		return SVE_ANAME;
	}

	public void setSVE_ANAME(String sVE_ANAME) {
		SVE_ANAME = sVE_ANAME;
	}

	public String getSVE_SHORT_NAME() {
		return SVE_SHORT_NAME;
	}

	public void setSVE_SHORT_NAME(String sVE_SHORT_NAME) {
		SVE_SHORT_NAME = sVE_SHORT_NAME;
	}

	public Calendar getSVE_DATE() {
		return SVE_DATE;
	}

	public void setSVE_DATE(Calendar sVE_DATE) {
		SVE_DATE = sVE_DATE;
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
		return "FUND_EXCHANGE_MSTR [SVE_CODE=" + SVE_CODE + ", SVE_NAME=" + SVE_NAME + ", SVE_ANAME=" + SVE_ANAME
				+ ", SVE_SHORT_NAME=" + SVE_SHORT_NAME + ", SVE_DATE=" + SVE_DATE + ", SVC_UID=" + SVC_UID
				+ ", IV_ENTER_UID=" + IV_ENTER_UID + ", IV_ENTER_DATE=" + IV_ENTER_DATE + ", IV_APPROVE_UID="
				+ IV_APPROVE_UID + ", IV_APPROVE_DATE=" + IV_APPROVE_DATE + ", IV_LAST_UPDATE_UID=" + IV_LAST_UPDATE_UID
				+ ", IV_LAST_UPDATE_DATE=" + IV_LAST_UPDATE_DATE + ", WMS_COMMENTS=" + WMS_COMMENTS + ", WMS_STATUS="
				+ WMS_STATUS + "]";
	}
	
	  
}
