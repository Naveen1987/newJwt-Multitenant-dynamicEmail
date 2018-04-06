package com.fynisys.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity(name="FUND_ROLE_SUB_TASK")
public class FUND_ROLE_SUB_TASK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long FRST_FRTASKID;
	@Column(length=45)
	private String FRST_CREATEDBY;
	private Calendar FRST_DATE;
	@Column(length=45)
	private String FRST_MODIFIEDBY;
	private Calendar FRST_LAST_CHANGE;
	private boolean FRST_ACCESS;
	private boolean FRST_INSERT;
	private boolean FRST_UPDATE;
	private boolean FRST_DELETE;
	private boolean FRST_APPROVE;
	
	/*
	 * Storing MenuTaskId
	 * */
	
	private long FMT_MENUTASKID;
	
	public long getFMT_MENUTASKID() {
		return FMT_MENUTASKID;
	}
	public void setFMT_MENUTASKID(long fMT_MENUTASKID) {
		FMT_MENUTASKID = fMT_MENUTASKID;
	}
	
	/*
	 * Relationship with FUND_ROLES Table  as Parent
	 */

	@ManyToOne
	@JoinColumn(name="FRL_ROLEID",foreignKey=@ForeignKey(name="fk_fund_roles_sub_task"))
	@JsonBackReference
	private FUND_ROLES fund_roles;
		
	public FUND_ROLES getFund_roles() {
		return fund_roles;
	}
	public void setFund_roles(FUND_ROLES fund_roles) {
		this.fund_roles = fund_roles;
	}
	
	/*
	 * Relationship with fund_menu_task Table  as Parent
	 */
	@ManyToOne
	@JoinColumn(name="FMST_MENUSUBTASKID",foreignKey=@ForeignKey(name="fk_fund_menu_sub_task"))
	@JsonBackReference
	private FUND_MENU_SUB_TASK fund_menu_sub_task;

	public FUND_MENU_SUB_TASK getFund_menu_sub_task() {
		return fund_menu_sub_task;
	}
	public void setFund_menu_sub_task(FUND_MENU_SUB_TASK fund_menu_sub_task) {
		this.fund_menu_sub_task = fund_menu_sub_task;
	}
	
	
	public long getFRST_FRTASKID() {
		return FRST_FRTASKID;
	}
	public void setFRST_FRTASKID(long fRST_FRTASKID) {
		FRST_FRTASKID = fRST_FRTASKID;
	}
	public String getFRST_CREATEDBY() {
		return FRST_CREATEDBY;
	}
	public void setFRST_CREATEDBY(String fRST_CREATEDBY) {
		FRST_CREATEDBY = fRST_CREATEDBY;
	}
	public Calendar getFRST_DATE() {
		return FRST_DATE;
	}
	public void setFRST_DATE(Calendar fRST_DATE) {
		FRST_DATE = fRST_DATE;
	}
	public String getFRST_MODIFIEDBY() {
		return FRST_MODIFIEDBY;
	}
	public void setFRST_MODIFIEDBY(String fRST_MODIFIEDBY) {
		FRST_MODIFIEDBY = fRST_MODIFIEDBY;
	}
	public Calendar getFRST_LAST_CHANGE() {
		return FRST_LAST_CHANGE;
	}
	public void setFRST_LAST_CHANGE(Calendar fRST_LAST_CHANGE) {
		FRST_LAST_CHANGE = fRST_LAST_CHANGE;
	}
	public boolean isFRST_ACCESS() {
		return FRST_ACCESS;
	}
	public void setFRST_ACCESS(boolean fRST_ACCESS) {
		FRST_ACCESS = fRST_ACCESS;
	}
	public boolean isFRST_INSERT() {
		return FRST_INSERT;
	}
	public void setFRST_INSERT(boolean fRST_INSERT) {
		FRST_INSERT = fRST_INSERT;
	}
	public boolean isFRST_UPDATE() {
		return FRST_UPDATE;
	}
	public void setFRST_UPDATE(boolean fRST_UPDATE) {
		FRST_UPDATE = fRST_UPDATE;
	}
	public boolean isFRST_DELETE() {
		return FRST_DELETE;
	}
	public void setFRST_DELETE(boolean fRST_DELETE) {
		FRST_DELETE = fRST_DELETE;
	}
	public boolean isFRST_APPROVE() {
		return FRST_APPROVE;
	}
	public void setFRST_APPROVE(boolean fRST_APPROVE) {
		FRST_APPROVE = fRST_APPROVE;
	}
	@Override
	public String toString() {
		return "FUND_ROLE_SUB_TASK [FRST_FRTASKID=" + FRST_FRTASKID + ", FRST_CREATEDBY=" + FRST_CREATEDBY
				+ ", FRST_DATE=" + FRST_DATE + ", FRST_MODIFIEDBY=" + FRST_MODIFIEDBY + ", FRST_LAST_CHANGE="
				+ FRST_LAST_CHANGE + ", FRST_ACCESS=" + FRST_ACCESS + ", FRST_INSEFRT=" + FRST_INSERT
				+ ", FRST_UPDATE=" + FRST_UPDATE + ", FRST_DELETE=" + FRST_DELETE + ", FRST_APPROVE=" + FRST_APPROVE
				+ ", fund_roles=" + fund_roles + ", fund_menu_sub_task=" + fund_menu_sub_task + "]";
	}
	
}
