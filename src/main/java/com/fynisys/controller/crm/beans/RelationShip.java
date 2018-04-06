package com.fynisys.controller.crm.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class RelationShip.
 */
public class RelationShip {
	
	/** The rcl no. */
	private Long RCL_NO;
	
	/** The rcl cdate. */
	private Calendar RCL_CDATE;
	
	/** The rcl efect date. */
	private Calendar RCL_EFECT_DATE;
	
	/** The wms comments. */
	private String WMS_COMMENTS;
	
	/** The wms status. */
	private String WMS_STATUS;
	
	/** The enteredby. */
	private String enteredby;
	
	/** The enteredbyuserid. */
	private String enteredbyuserid;
	
	/** The enteredbyuuid. */
	private String enteredbyuuid;
	
	/** The entereddate. */
	private Calendar entereddate;
	
	/** The approvedby. */
	private String approvedby;
	
	/** The approvedbyuserid. */
	private String approvedbyuserid;
	
	/** The approvedbyuuid. */
	private String approvedbyuuid;
	
	/** The approveddate. */
	private Calendar approveddate;
	
	/** The modifiedby. */
	private String modifiedby;
	
	/** The modifiedbyuserid. */
	private String modifiedbyuserid;
	
	/** The modifiedbyuuid. */
	private String modifiedbyuuid;
	
	/** The modifieddate. */
	private Calendar modifieddate;
	
	/** The msg. */
	private String msg;
	
	/** The relationlink. */
	List<RelationLink> relationlink=new ArrayList<>();
	
	/**
	 * Gets the rcl no.
	 *
	 * @return the rcl no
	 */
	public Long getRCL_NO() {
		return RCL_NO;
	}
	
	/**
	 * Sets the rcl no.
	 *
	 * @param rCL_NO the new rcl no
	 */
	public void setRCL_NO(Long rCL_NO) {
		RCL_NO = rCL_NO;
	}
	
	/**
	 * Gets the rcl cdate.
	 *
	 * @return the rcl cdate
	 */
	public Calendar getRCL_CDATE() {
		return RCL_CDATE;
	}
	
	/**
	 * Sets the rcl cdate.
	 *
	 * @param rCL_CDATE the new rcl cdate
	 */
	public void setRCL_CDATE(Calendar rCL_CDATE) {
		RCL_CDATE = rCL_CDATE;
	}
	
	/**
	 * Gets the rcl efect date.
	 *
	 * @return the rcl efect date
	 */
	public Calendar getRCL_EFECT_DATE() {
		return RCL_EFECT_DATE;
	}
	
	/**
	 * Sets the rcl efect date.
	 *
	 * @param rCL_EFECT_DATE the new rcl efect date
	 */
	public void setRCL_EFECT_DATE(Calendar rCL_EFECT_DATE) {
		RCL_EFECT_DATE = rCL_EFECT_DATE;
	}
	
	/**
	 * Gets the wms comments.
	 *
	 * @return the wms comments
	 */
	public String getWMS_COMMENTS() {
		return WMS_COMMENTS;
	}
	
	/**
	 * Sets the wms comments.
	 *
	 * @param wMS_COMMENTS the new wms comments
	 */
	public void setWMS_COMMENTS(String wMS_COMMENTS) {
		WMS_COMMENTS = wMS_COMMENTS;
	}
	
	/**
	 * Gets the wms status.
	 *
	 * @return the wms status
	 */
	public String getWMS_STATUS() {
		return WMS_STATUS;
	}
	
	/**
	 * Sets the wms status.
	 *
	 * @param wMS_STATUS the new wms status
	 */
	public void setWMS_STATUS(String wMS_STATUS) {
		WMS_STATUS = wMS_STATUS;
	}
	
	/**
	 * Gets the enteredby.
	 *
	 * @return the enteredby
	 */
	public String getEnteredby() {
		return enteredby;
	}
	
	/**
	 * Sets the enteredby.
	 *
	 * @param enteredby the new enteredby
	 */
	public void setEnteredby(String enteredby) {
		this.enteredby = enteredby;
	}
	
	/**
	 * Gets the enteredbyuserid.
	 *
	 * @return the enteredbyuserid
	 */
	public String getEnteredbyuserid() {
		return enteredbyuserid;
	}
	
	/**
	 * Sets the enteredbyuserid.
	 *
	 * @param enteredbyuserid the new enteredbyuserid
	 */
	public void setEnteredbyuserid(String enteredbyuserid) {
		this.enteredbyuserid = enteredbyuserid;
	}
	
	/**
	 * Gets the enteredbyuuid.
	 *
	 * @return the enteredbyuuid
	 */
	public String getEnteredbyuuid() {
		return enteredbyuuid;
	}
	
	/**
	 * Sets the enteredbyuuid.
	 *
	 * @param enteredbyuuid the new enteredbyuuid
	 */
	public void setEnteredbyuuid(String enteredbyuuid) {
		this.enteredbyuuid = enteredbyuuid;
	}
	
	/**
	 * Gets the entereddate.
	 *
	 * @return the entereddate
	 */
	public Calendar getEntereddate() {
		return entereddate;
	}
	
	/**
	 * Sets the entereddate.
	 *
	 * @param entereddate the new entereddate
	 */
	public void setEntereddate(Calendar entereddate) {
		this.entereddate = entereddate;
	}
	
	/**
	 * Gets the approvedby.
	 *
	 * @return the approvedby
	 */
	public String getApprovedby() {
		return approvedby;
	}
	
	/**
	 * Sets the approvedby.
	 *
	 * @param approvedby the new approvedby
	 */
	public void setApprovedby(String approvedby) {
		this.approvedby = approvedby;
	}
	
	/**
	 * Gets the approvedbyuserid.
	 *
	 * @return the approvedbyuserid
	 */
	public String getApprovedbyuserid() {
		return approvedbyuserid;
	}
	
	/**
	 * Sets the approvedbyuserid.
	 *
	 * @param approvedbyuserid the new approvedbyuserid
	 */
	public void setApprovedbyuserid(String approvedbyuserid) {
		this.approvedbyuserid = approvedbyuserid;
	}
	
	/**
	 * Gets the approvedbyuuid.
	 *
	 * @return the approvedbyuuid
	 */
	public String getApprovedbyuuid() {
		return approvedbyuuid;
	}
	
	/**
	 * Sets the approvedbyuuid.
	 *
	 * @param approvedbyuuid the new approvedbyuuid
	 */
	public void setApprovedbyuuid(String approvedbyuuid) {
		this.approvedbyuuid = approvedbyuuid;
	}
	
	/**
	 * Gets the approveddate.
	 *
	 * @return the approveddate
	 */
	public Calendar getApproveddate() {
		return approveddate;
	}
	
	/**
	 * Sets the approveddate.
	 *
	 * @param approveddate the new approveddate
	 */
	public void setApproveddate(Calendar approveddate) {
		this.approveddate = approveddate;
	}
	
	/**
	 * Gets the modifiedby.
	 *
	 * @return the modifiedby
	 */
	public String getModifiedby() {
		return modifiedby;
	}
	
	/**
	 * Sets the modifiedby.
	 *
	 * @param modifiedby the new modifiedby
	 */
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	
	/**
	 * Gets the modifiedbyuserid.
	 *
	 * @return the modifiedbyuserid
	 */
	public String getModifiedbyuserid() {
		return modifiedbyuserid;
	}
	
	/**
	 * Sets the modifiedbyuserid.
	 *
	 * @param modifiedbyuserid the new modifiedbyuserid
	 */
	public void setModifiedbyuserid(String modifiedbyuserid) {
		this.modifiedbyuserid = modifiedbyuserid;
	}
	
	/**
	 * Gets the modifiedbyuuid.
	 *
	 * @return the modifiedbyuuid
	 */
	public String getModifiedbyuuid() {
		return modifiedbyuuid;
	}
	
	/**
	 * Sets the modifiedbyuuid.
	 *
	 * @param modifiedbyuuid the new modifiedbyuuid
	 */
	public void setModifiedbyuuid(String modifiedbyuuid) {
		this.modifiedbyuuid = modifiedbyuuid;
	}
	
	/**
	 * Gets the modifieddate.
	 *
	 * @return the modifieddate
	 */
	public Calendar getModifieddate() {
		return modifieddate;
	}
	
	/**
	 * Sets the modifieddate.
	 *
	 * @param modifieddate the new modifieddate
	 */
	public void setModifieddate(Calendar modifieddate) {
		this.modifieddate = modifieddate;
	}
	
	/**
	 * Gets the msg.
	 *
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	
	/**
	 * Sets the msg.
	 *
	 * @param msg the new msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	/**
	 * Gets the relationlink.
	 *
	 * @return the relationlink
	 */
	public List<RelationLink> getRelationlink() {
		return relationlink;
	}
	
	/**
	 * Sets the relationlink.
	 *
	 * @param relationlink the new relationlink
	 */
	public void setRelationlink(List<RelationLink> relationlink) {
		this.relationlink = relationlink;
	}
	
}
