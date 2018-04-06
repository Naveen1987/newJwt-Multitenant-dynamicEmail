package com.fynisys.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

// TODO: Auto-generated Javadoc
/**
 * The Class FUND_COUNTRIES.
 *
 * @author Daffodil
 */
@Entity
@Table(name="FUND_COUNTRIES",uniqueConstraints= {
		@UniqueConstraint(columnNames= {"SVC_NAME"},name="countryname_unique"),
		@UniqueConstraint(columnNames= {"SVC_CODE"},name="countryiso_unique")
})
public class FUND_COUNTRIES implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	
	/** The svc id. */
	@Id
	@GeneratedValue
	private long SVC_ID;

	/** The svc date. */
	private Calendar SVC_DATE;
	
	/** The svc code. */
	@Column(length=15,nullable=false)
	 private String SVC_CODE;
	
	/** The svc name. */
	@Column(length=50)
	private String SVC_NAME;
	
	/** The svc short name. */
	@Column(length=5)
	private String SVC_SHORT_NAME;
	
	/** The svc aname. */
	@Column(length=40)
	private String SVC_ANAME;
	
	/** The svc uid. */
	@Column(length=45)
	 private String SVC_UID;
	
	/** The iv enter uid. */
	@Column(length=119)
	 private String IV_ENTER_UID;
	
	/** The iv enter date. */
	private Calendar IV_ENTER_DATE;
	
	/** The iv approve uid. */
	@Column(length=119)
	private String IV_APPROVE_UID;
	
	/** The iv approve date. */
	private Calendar IV_APPROVE_DATE;
	
	/** The iv last update uid. */
	@Column(length=119)
	private String IV_LAST_UPDATE_UID;
	
	/** The iv last update date. */
	private Calendar IV_LAST_UPDATE_DATE;
	
	/** The svc sno. */
	@Column(length=10)
	private String SVC_SNO;
	
	
	
	/** The svc comments. */
	@Column(length=255)
	private String SVC_COMMENTS;

/** The svc status. */
/* active=1 and deactive=0 country
 * */
@Column(length=1)
private  String SVC_STATUS;

/** The svc flow. */
/*for approved=1 and unapproved=0*/
@Column(length=1)
private String SVC_FLOW;

/**
 * Gets the svc id.
 *
 * @return the svc id
 */
public long getSVC_ID() {
	return SVC_ID;
}

/**
 * Sets the svc id.
 *
 * @param sVC_ID the new svc id
 */
public void setSVC_ID(long sVC_ID) {
	SVC_ID = sVC_ID;
}

/**
 * Gets the svc date.
 *
 * @return the svc date
 */
public Calendar getSVC_DATE() {
	return SVC_DATE;
}

/**
 * Sets the svc date.
 *
 * @param sVC_DATE the new svc date
 */
public void setSVC_DATE(Calendar sVC_DATE) {
	SVC_DATE = sVC_DATE;
}

/**
 * Gets the svc code.
 *
 * @return the svc code
 */
public String getSVC_CODE() {
	return SVC_CODE;
}

/**
 * Sets the svc code.
 *
 * @param sVC_CODE the new svc code
 */
public void setSVC_CODE(String sVC_CODE) {
	SVC_CODE = sVC_CODE;
}

/**
 * Gets the svc name.
 *
 * @return the svc name
 */
public String getSVC_NAME() {
	return SVC_NAME;
}

/**
 * Sets the svc name.
 *
 * @param sVC_NAME the new svc name
 */
public void setSVC_NAME(String sVC_NAME) {
	SVC_NAME = sVC_NAME;
}

/**
 * Gets the svc short name.
 *
 * @return the svc short name
 */
public String getSVC_SHORT_NAME() {
	return SVC_SHORT_NAME;
}

/**
 * Sets the svc short name.
 *
 * @param sVC_SHORT_NAME the new svc short name
 */
public void setSVC_SHORT_NAME(String sVC_SHORT_NAME) {
	SVC_SHORT_NAME = sVC_SHORT_NAME;
}

/**
 * Gets the svc aname.
 *
 * @return the svc aname
 */
public String getSVC_ANAME() {
	return SVC_ANAME;
}

/**
 * Sets the svc aname.
 *
 * @param sVC_ANAME the new svc aname
 */
public void setSVC_ANAME(String sVC_ANAME) {
	SVC_ANAME = sVC_ANAME;
}

/**
 * Gets the svc uid.
 *
 * @return the svc uid
 */
public String getSVC_UID() {
	return SVC_UID;
}

/**
 * Sets the svc uid.
 *
 * @param sVC_UID the new svc uid
 */
public void setSVC_UID(String sVC_UID) {
	SVC_UID = sVC_UID;
}

/**
 * Gets the iv enter uid.
 *
 * @return the iv enter uid
 */
public String getIV_ENTER_UID() {
	return IV_ENTER_UID;
}

/**
 * Sets the iv enter uid.
 *
 * @param iV_ENTER_UID the new iv enter uid
 */
public void setIV_ENTER_UID(String iV_ENTER_UID) {
	IV_ENTER_UID = iV_ENTER_UID;
}

/**
 * Gets the iv enter date.
 *
 * @return the iv enter date
 */
public Calendar getIV_ENTER_DATE() {
	return IV_ENTER_DATE;
}

/**
 * Sets the iv enter date.
 *
 * @param iV_ENTER_DATE the new iv enter date
 */
public void setIV_ENTER_DATE(Calendar iV_ENTER_DATE) {
	IV_ENTER_DATE = iV_ENTER_DATE;
}

/**
 * Gets the iv approve uid.
 *
 * @return the iv approve uid
 */
public String getIV_APPROVE_UID() {
	return IV_APPROVE_UID;
}

/**
 * Sets the iv approve uid.
 *
 * @param iV_APPROVE_UID the new iv approve uid
 */
public void setIV_APPROVE_UID(String iV_APPROVE_UID) {
	IV_APPROVE_UID = iV_APPROVE_UID;
}

/**
 * Gets the iv approve date.
 *
 * @return the iv approve date
 */
public Calendar getIV_APPROVE_DATE() {
	return IV_APPROVE_DATE;
}

/**
 * Sets the iv approve date.
 *
 * @param iV_APPROVE_DATE the new iv approve date
 */
public void setIV_APPROVE_DATE(Calendar iV_APPROVE_DATE) {
	IV_APPROVE_DATE = iV_APPROVE_DATE;
}

/**
 * Gets the iv last update uid.
 *
 * @return the iv last update uid
 */
public String getIV_LAST_UPDATE_UID() {
	return IV_LAST_UPDATE_UID;
}

/**
 * Sets the iv last update uid.
 *
 * @param iV_LAST_UPDATE_UID the new iv last update uid
 */
public void setIV_LAST_UPDATE_UID(String iV_LAST_UPDATE_UID) {
	IV_LAST_UPDATE_UID = iV_LAST_UPDATE_UID;
}

/**
 * Gets the iv last update date.
 *
 * @return the iv last update date
 */
public Calendar getIV_LAST_UPDATE_DATE() {
	return IV_LAST_UPDATE_DATE;
}

/**
 * Sets the iv last update date.
 *
 * @param iV_LAST_UPDATE_DATE the new iv last update date
 */
public void setIV_LAST_UPDATE_DATE(Calendar iV_LAST_UPDATE_DATE) {
	IV_LAST_UPDATE_DATE = iV_LAST_UPDATE_DATE;
}

/**
 * Gets the svc sno.
 *
 * @return the svc sno
 */
public String getSVC_SNO() {
	return SVC_SNO;
}

/**
 * Sets the svc sno.
 *
 * @param sVC_SNO the new svc sno
 */
public void setSVC_SNO(String sVC_SNO) {
	SVC_SNO = sVC_SNO;
}

/**
 * Gets the svc comments.
 *
 * @return the svc comments
 */
public String getSVC_COMMENTS() {
	return SVC_COMMENTS;
}

/**
 * Sets the svc comments.
 *
 * @param sVC_COMMENTS the new svc comments
 */
public void setSVC_COMMENTS(String sVC_COMMENTS) {
	SVC_COMMENTS = sVC_COMMENTS;
}

/**
 * Gets the svc status.
 *
 * @return the svc status
 */
public String getSVC_STATUS() {
	return SVC_STATUS;
}

/**
 * Sets the svc status.
 *
 * @param sVC_STATUS the new svc status
 */
public void setSVC_STATUS(String sVC_STATUS) {
	SVC_STATUS = sVC_STATUS;
}

/**
 * Gets the svc flow.
 *
 * @return the svc flow
 */
public String getSVC_FLOW() {
	return SVC_FLOW;
}

/**
 * Sets the svc flow.
 *
 * @param sVC_FLOW the new svc flow
 */
public void setSVC_FLOW(String sVC_FLOW) {
	SVC_FLOW = sVC_FLOW;
}

/**
 * Gets the serialversionuid.
 *
 * @return the serialversionuid
 */
public static long getSerialversionuid() {
	return serialVersionUID;
}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "FUND_COUNTRIES [SVC_ID=" + SVC_ID + ", SVC_DATE=" + SVC_DATE + ", SVC_CODE=" + SVC_CODE + ", SVC_NAME="
			+ SVC_NAME + ", SVC_SHORT_NAME=" + SVC_SHORT_NAME + ", SVC_ANAME=" + SVC_ANAME + ", SVC_UID=" + SVC_UID
			+ ", IV_ENTER_UID=" + IV_ENTER_UID + ", IV_ENTER_DATE=" + IV_ENTER_DATE + ", IV_APPROVE_UID="
			+ IV_APPROVE_UID + ", IV_APPROVE_DATE=" + IV_APPROVE_DATE + ", IV_LAST_UPDATE_UID=" + IV_LAST_UPDATE_UID
			+ ", IV_LAST_UPDATE_DATE=" + IV_LAST_UPDATE_DATE + ", SVC_SNO=" + SVC_SNO + ", SVC_COMMENTS=" + SVC_COMMENTS
			+ ", SVC_STATUS=" + SVC_STATUS + ", SVC_FLOW=" + SVC_FLOW + "]";
}





}
