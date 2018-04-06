package com.fynisys.companyprofile.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;


// TODO: Auto-generated Javadoc
/**
 * The Class FWMS_REPORT_PARA.
 */
@Entity(name="FWMS_REPORT_PARA")
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames="WMS_COMPANY_NAME",name="UK_WMS_COMPANY_NAME")
		
})
public class FWMS_REPORT_PARA {
	
	/** The wms fund. */
	@Id
	@GeneratedValue
	private long WMS_FUND;//                                           NUMBER(10),
	
	/** The wms company name. */
	@Column(length=100)
	private String WMS_COMPANY_NAME;//                                   VARCHAR2(100),
	
	/** The wms fline 1. */
	@Column(length=200)
	private String WMS_FLINE_1;  //                                      VARCHAR2(200),
	
	/** The wms fline 2. */
	@Column(length=200)
	private String WMS_FLINE_2;  //                                      VARCHAR2(200),
	
	/** The wms image name. */
	@Column(length=200)
	private String WMS_IMAGE_NAME;//                                     VARCHAR2(100),
    
    /** The wms image. */
    @Lob
	private byte[] WMS_IMAGE; //                                         BLOB
	
    /** The wms company default. */
    @OneToOne
    @JoinColumn(name="COMPANY_DEFAULT",foreignKey=@ForeignKey(name="fk_FWMS_COMPANY_DEFAULT"))
	@JsonBackReference
    FWMS_COMPANY_DEFAULT fWMS_COMPANY_DEFAULT;
    
	/**
	 * Gets the f WM S COMPAN Y DEFAULT.
	 *
	 * @return the f WM S COMPAN Y DEFAULT
	 */
	public FWMS_COMPANY_DEFAULT getfWMS_COMPANY_DEFAULT() {
		return fWMS_COMPANY_DEFAULT;
	}
	
	/**
	 * Sets the f WM S COMPAN Y DEFAULT.
	 *
	 * @param fWMS_COMPANY_DEFAULT the new f WM S COMPAN Y DEFAULT
	 */
	public void setfWMS_COMPANY_DEFAULT(FWMS_COMPANY_DEFAULT fWMS_COMPANY_DEFAULT) {
		this.fWMS_COMPANY_DEFAULT = fWMS_COMPANY_DEFAULT;
	}
	
	/**
	 * Gets the wms fund.
	 *
	 * @return the wms fund
	 */
	public long getWMS_FUND() {
		return WMS_FUND;
	}
	
	/**
	 * Sets the wms fund.
	 *
	 * @param wMS_FUND the new wms fund
	 */
	public void setWMS_FUND(long wMS_FUND) {
		WMS_FUND = wMS_FUND;
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
	 * Gets the wms fline 1.
	 *
	 * @return the wms fline 1
	 */
	public String getWMS_FLINE_1() {
		return WMS_FLINE_1;
	}
	
	/**
	 * Sets the wms fline 1.
	 *
	 * @param wMS_FLINE_1 the new wms fline 1
	 */
	public void setWMS_FLINE_1(String wMS_FLINE_1) {
		WMS_FLINE_1 = wMS_FLINE_1;
	}
	
	/**
	 * Gets the wms fline 2.
	 *
	 * @return the wms fline 2
	 */
	public String getWMS_FLINE_2() {
		return WMS_FLINE_2;
	}
	
	/**
	 * Sets the wms fline 2.
	 *
	 * @param wMS_FLINE_2 the new wms fline 2
	 */
	public void setWMS_FLINE_2(String wMS_FLINE_2) {
		WMS_FLINE_2 = wMS_FLINE_2;
	}
	
	/**
	 * Gets the wms image name.
	 *
	 * @return the wms image name
	 */
	public String getWMS_IMAGE_NAME() {
		return WMS_IMAGE_NAME;
	}
	
	/**
	 * Sets the wms image name.
	 *
	 * @param wMS_IMAGE_NAME the new wms image name
	 */
	public void setWMS_IMAGE_NAME(String wMS_IMAGE_NAME) {
		WMS_IMAGE_NAME = wMS_IMAGE_NAME;
	}
	
	/**
	 * Gets the wms image.
	 *
	 * @return the wms image
	 */
	public byte[] getWMS_IMAGE() {
		return WMS_IMAGE;
	}
	
	/**
	 * Sets the wms image.
	 *
	 * @param wMS_IMAGE the new wms image
	 */
	public void setWMS_IMAGE(byte[] wMS_IMAGE) {
		WMS_IMAGE = wMS_IMAGE;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FWMS_REPORT_PARA [WMS_FUND=" + WMS_FUND + ", WMS_COMPANY_NAME=" + WMS_COMPANY_NAME + ", WMS_FLINE_1="
				+ WMS_FLINE_1 + ", WMS_FLINE_2=" + WMS_FLINE_2 + ", WMS_IMAGE_NAME=" + WMS_IMAGE_NAME + ", WMS_IMAGE="
				+ Arrays.toString(WMS_IMAGE) + "]";
	}
	
	 
}
