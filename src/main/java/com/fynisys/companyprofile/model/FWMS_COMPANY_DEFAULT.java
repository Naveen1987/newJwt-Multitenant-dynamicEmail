package com.fynisys.companyprofile.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name="FWMS_COMPANY_DEFAULT")
@Table(uniqueConstraints= {
		@UniqueConstraint(name="UK_WMS_COMPANY_NAME",columnNames="WMS_COMPANY_NAME")
		
})
public class FWMS_COMPANY_DEFAULT {
	
	@Id
	@GeneratedValue
	private Long FCD_DEFAULT_FCODE;
	

	@Column(length=100)
	private String WMS_COMPANY_NAME;// 
	

	@Column(length=100)
	private String WMS_SCHEMA_NAME;//
	

	@Column(length=100)
	private String WMS_SCHEMA_USER;//
	

	@Column(length=100)
	private String WMS_SCHEMA_PASSWORD;//
	
	@Column(length=100)
	private String WMS_SCHEMA_STRING;//
	
	@Column(length=100)
	private String SMTP_SERVER;
	
	@Column(length=100)
	private String SMTP_PORT;
	
	@Column(length=100)
	private String SMTP_EMAIL_ADDRESS;
	
	@Column(length=100)
	private String SMTP_YOUR_NAME;
	
	@Column(length=100)
	private String SMTP_REQUIRE_RECIEPT;
	
	@Column(length=100)
	private String SMTP_IS_REQUIRE_AUTH;
	
	@Column(length=100)
	private String SMTP_USER_NAME;
	
	@Column(length=100)
	private String SMTP_PASSWORD;

	@OneToOne(mappedBy="fWMS_COMPANY_DEFAULT")
	FWMS_REPORT_PARA fWMS_REPORT_PARA;
	
	/**
	 * Gets the f WM S REPOR T PARA.
	 *
	 * @return the f WM S REPOR T PARA
	 */
	public FWMS_REPORT_PARA getfWMS_REPORT_PARA() {
		return fWMS_REPORT_PARA;
	}
	
	/**
	 * Sets the f WM S REPOR T PARA.
	 *
	 * @param fWMS_REPORT_PARA the new f WM S REPOR T PARA
	 */
	public void setfWMS_REPORT_PARA(FWMS_REPORT_PARA fWMS_REPORT_PARA) {
		this.fWMS_REPORT_PARA = fWMS_REPORT_PARA;
	}
	
	/**
	 * Gets the fcd default fcode.
	 *
	 * @return the fcd default fcode
	 */
	public long getFCD_DEFAULT_FCODE() {
		return FCD_DEFAULT_FCODE;
	}
	
	/**
	 * Sets the fcd default fcode.
	 *
	 * @param fCD_DEFAULT_FCODE the new fcd default fcode
	 */
	public void setFCD_DEFAULT_FCODE(long fCD_DEFAULT_FCODE) {
		FCD_DEFAULT_FCODE = fCD_DEFAULT_FCODE;
	}
	
	/**
	 * Gets the wms company name.
	 *
	 * @return the wms company name
	 */
	public String getWMS_COMPANY_NAME() {
		return WMS_COMPANY_NAME;
	}
	
	/**
	 * Sets the wms company name.
	 *
	 * @param wMS_COMPANY_NAME the new wms company name
	 */
	public void setWMS_COMPANY_NAME(String wMS_COMPANY_NAME) {
		WMS_COMPANY_NAME = wMS_COMPANY_NAME;
	}
	
	/**
	 * Gets the wms schema name.
	 *
	 * @return the wms schema name
	 */
	public String getWMS_SCHEMA_NAME() {
		return WMS_SCHEMA_NAME;
	}
	
	/**
	 * Sets the wms schema name.
	 *
	 * @param wMS_SCHEMA_NAME the new wms schema name
	 */
	public void setWMS_SCHEMA_NAME(String wMS_SCHEMA_NAME) {
		WMS_SCHEMA_NAME = wMS_SCHEMA_NAME;
	}
	
	/**
	 * Gets the wms schema user.
	 *
	 * @return the wms schema user
	 */
	public String getWMS_SCHEMA_USER() {
		return WMS_SCHEMA_USER;
	}
	
	/**
	 * Sets the wms schema user.
	 *
	 * @param wMS_SCHEMA_USER the new wms schema user
	 */
	public void setWMS_SCHEMA_USER(String wMS_SCHEMA_USER) {
		WMS_SCHEMA_USER = wMS_SCHEMA_USER;
	}
	
	/**
	 * Gets the wms schema password.
	 *
	 * @return the wms schema password
	 */
	public String getWMS_SCHEMA_PASSWORD() {
		return WMS_SCHEMA_PASSWORD;
	}
	
	/**
	 * Sets the wms schema password.
	 *
	 * @param wMS_SCHEMA_PASSWORD the new wms schema password
	 */
	public void setWMS_SCHEMA_PASSWORD(String wMS_SCHEMA_PASSWORD) {
		WMS_SCHEMA_PASSWORD = wMS_SCHEMA_PASSWORD;
	}
	
	/**
	 * Gets the wms schema string.
	 *
	 * @return the wms schema string
	 */
	public String getWMS_SCHEMA_STRING() {
		return WMS_SCHEMA_STRING;
	}
	
	/**
	 * Sets the wms schema string.
	 *
	 * @param wMS_SCHEMA_STRING the new wms schema string
	 */
	public void setWMS_SCHEMA_STRING(String wMS_SCHEMA_STRING) {
		WMS_SCHEMA_STRING = wMS_SCHEMA_STRING;
	}
	
	/**
	 * Gets the smtp server.
	 *
	 * @return the smtp server
	 */
	public String getSMTP_SERVER() {
		return SMTP_SERVER;
	}
	
	/**
	 * Sets the smtp server.
	 *
	 * @param sMTP_SERVER the new smtp server
	 */
	public void setSMTP_SERVER(String sMTP_SERVER) {
		SMTP_SERVER = sMTP_SERVER;
	}
	
	/**
	 * Gets the smtp port.
	 *
	 * @return the smtp port
	 */
	public String getSMTP_PORT() {
		return SMTP_PORT;
	}
	
	/**
	 * Sets the smtp port.
	 *
	 * @param sMTP_PORT the new smtp port
	 */
	public void setSMTP_PORT(String sMTP_PORT) {
		SMTP_PORT = sMTP_PORT;
	}
	
	/**
	 * Gets the smtp email address.
	 *
	 * @return the smtp email address
	 */
	public String getSMTP_EMAIL_ADDRESS() {
		return SMTP_EMAIL_ADDRESS;
	}
	
	/**
	 * Sets the smtp email address.
	 *
	 * @param sMTP_EMAIL_ADDRESS the new smtp email address
	 */
	public void setSMTP_EMAIL_ADDRESS(String sMTP_EMAIL_ADDRESS) {
		SMTP_EMAIL_ADDRESS = sMTP_EMAIL_ADDRESS;
	}
	
	/**
	 * Gets the smtp your name.
	 *
	 * @return the smtp your name
	 */
	public String getSMTP_YOUR_NAME() {
		return SMTP_YOUR_NAME;
	}
	
	/**
	 * Sets the smtp your name.
	 *
	 * @param sMTP_YOUR_NAME the new smtp your name
	 */
	public void setSMTP_YOUR_NAME(String sMTP_YOUR_NAME) {
		SMTP_YOUR_NAME = sMTP_YOUR_NAME;
	}
	
	/**
	 * Gets the smtp require reciept.
	 *
	 * @return the smtp require reciept
	 */
	public String getSMTP_REQUIRE_RECIEPT() {
		return SMTP_REQUIRE_RECIEPT;
	}
	
	/**
	 * Sets the smtp require reciept.
	 *
	 * @param sMTP_REQUIRE_RECIEPT the new smtp require reciept
	 */
	public void setSMTP_REQUIRE_RECIEPT(String sMTP_REQUIRE_RECIEPT) {
		SMTP_REQUIRE_RECIEPT = sMTP_REQUIRE_RECIEPT;
	}
	
	/**
	 * Gets the smtp is require auth.
	 *
	 * @return the smtp is require auth
	 */
	public String getSMTP_IS_REQUIRE_AUTH() {
		return SMTP_IS_REQUIRE_AUTH;
	}
	
	/**
	 * Sets the smtp is require auth.
	 *
	 * @param sMTP_IS_REQUIRE_AUTH the new smtp is require auth
	 */
	public void setSMTP_IS_REQUIRE_AUTH(String sMTP_IS_REQUIRE_AUTH) {
		SMTP_IS_REQUIRE_AUTH = sMTP_IS_REQUIRE_AUTH;
	}
	
	/**
	 * Gets the smtp user name.
	 *
	 * @return the smtp user name
	 */
	public String getSMTP_USER_NAME() {
		return SMTP_USER_NAME;
	}
	
	/**
	 * Sets the smtp user name.
	 *
	 * @param sMTP_USER_NAME the new smtp user name
	 */
	public void setSMTP_USER_NAME(String sMTP_USER_NAME) {
		SMTP_USER_NAME = sMTP_USER_NAME;
	}
	
	/**
	 * Gets the smtp password.
	 *
	 * @return the smtp password
	 */
	public String getSMTP_PASSWORD() {
		return SMTP_PASSWORD;
	}
	
	/**
	 * Sets the smtp password.
	 *
	 * @param sMTP_PASSWORD the new smtp password
	 */
	public void setSMTP_PASSWORD(String sMTP_PASSWORD) {
		SMTP_PASSWORD = sMTP_PASSWORD;
	}
	
	/**
	 * Sets the fcd default fcode.
	 *
	 * @param fCD_DEFAULT_FCODE the new fcd default fcode
	 */
	public void setFCD_DEFAULT_FCODE(Long fCD_DEFAULT_FCODE) {
		FCD_DEFAULT_FCODE = fCD_DEFAULT_FCODE;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FWMS_COMPANY_DEFAULT [FCD_DEFAULT_FCODE=" + FCD_DEFAULT_FCODE + ", WMS_COMPANY_NAME=" + WMS_COMPANY_NAME
				+ ", WMS_SCHEMA_NAME=" + WMS_SCHEMA_NAME + ", WMS_SCHEMA_USER=" + WMS_SCHEMA_USER
				+ ", WMS_SCHEMA_PASSWORD=" + WMS_SCHEMA_PASSWORD + ", WMS_SCHEMA_STRING=" + WMS_SCHEMA_STRING + "]";
	}
	
}
