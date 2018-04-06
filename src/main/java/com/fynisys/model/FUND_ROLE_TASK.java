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


@Entity(name="FUND_ROLE_TASK")
public class FUND_ROLE_TASK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long FRT_FRTASKID;
	/*@Column(length=15)
	private String FRT_TASKNAME;*/
	@Column(length=45)
	private String FRT_CREATEDBY;
	private Calendar FRT_DATE;
	@Column(length=45)
	private String FRT_MODIFIEDBY;
	private Calendar FRT_LAST_CHANGE;
	private boolean FRT_ACCESS;
	private boolean FRT_INSERT;
	private boolean FRT_UPDATE;
	private boolean FRT_DELETE;
	private boolean FRT_APPROVE;
	
	/*
	 * Relationship with FUND_ROLES Table  as Parent
	 */
	
	@ManyToOne
	@JoinColumn(name="FRL_ROLEID",foreignKey=@ForeignKey(name="fk_fund_roles_task"))
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
	@JoinColumn(name="FMT_MENUTASKID",foreignKey=@ForeignKey(name="fk_fund_menu_task"))
	@JsonBackReference
	private FUND_MENU_TASK fund_menu_task;
	
	public FUND_MENU_TASK getFund_menu_task() {
		return fund_menu_task;
	}
	public void setFund_menu_task(FUND_MENU_TASK fund_menu_task) {
		this.fund_menu_task = fund_menu_task;
	}
	
	
	public long getFRT_FRTASKID() {
		return FRT_FRTASKID;
	}
	public void setFRT_FRTASKID(long fRT_FRTASKID) {
		FRT_FRTASKID = fRT_FRTASKID;
	}
	/*public String getFRT_TASKNAME() {
		return FRT_TASKNAME;
	}
	public void setFRT_TASKNAME(String fRT_TASKNAME) {
		FRT_TASKNAME = fRT_TASKNAME;
	}*/
	
	public String getFRT_CREATEDBY() {
		return FRT_CREATEDBY;
	}
	public void setFRT_CREATEDBY(String fRT_CREATEDBY) {
		FRT_CREATEDBY = fRT_CREATEDBY;
	}
	public Calendar getFRT_DATE() {
		return FRT_DATE;
	}
	public void setFRT_DATE(Calendar fRT_DATE) {
		FRT_DATE = fRT_DATE;
	}
	public String getFRT_MODIFIEDBY() {
		return FRT_MODIFIEDBY;
	}
	public void setFRT_MODIFIEDBY(String fRT_MODIFIEDBY) {
		FRT_MODIFIEDBY = fRT_MODIFIEDBY;
	}
	public Calendar getFRT_LAST_CHANGE() {
		return FRT_LAST_CHANGE;
	}
	public void setFRT_LAST_CHANGE(Calendar fRT_LAST_CHANGE) {
		FRT_LAST_CHANGE = fRT_LAST_CHANGE;
	}
	public boolean isFRT_ACCESS() {
		return FRT_ACCESS;
	}
	public void setFRT_ACCESS(boolean fRT_ACCESS) {
		FRT_ACCESS = fRT_ACCESS;
	}
	public boolean isFRT_INSERT() {
		return FRT_INSERT;
	}
	public void setFRT_INSERT(boolean fRT_INSERT) {
		FRT_INSERT = fRT_INSERT;
	}
	public boolean isFRT_UPDATE() {
		return FRT_UPDATE;
	}
	public void setFRT_UPDATE(boolean fRT_UPDATE) {
		FRT_UPDATE = fRT_UPDATE;
	}
	public boolean isFRT_DELETE() {
		return FRT_DELETE;
	}
	public void setFRT_DELETE(boolean fRT_DELETE) {
		FRT_DELETE = fRT_DELETE;
	}
	public boolean isFRT_APPROVE() {
		return FRT_APPROVE;
	}
	public void setFRT_APPROVE(boolean fRT_APPROVE) {
		FRT_APPROVE = fRT_APPROVE;
	}
	@Override
	public String toString() {
		return "FUND_ROLE_TASK [FRT_FRTASKID=" + FRT_FRTASKID + ", FRT_CREATEDBY=" + FRT_CREATEDBY + ", FRT_DATE=" + FRT_DATE + ", FRT_MODIFIEDBY="
				+ FRT_MODIFIEDBY + ", FRT_LAST_CHANGE=" + FRT_LAST_CHANGE + ", FRT_ACCESS=" + FRT_ACCESS
				+ ", FRT_INSEFRT=" + FRT_INSERT + ", FRT_UPDATE=" + FRT_UPDATE + ", FRT_DELETE=" + FRT_DELETE
				+ ", FRT_APPROVE=" + FRT_APPROVE + "]";
	}

	
}
