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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name="FUND_MENU_GROUP")
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames="FMG_GROUPNAME",name="unique_FMG_GROUPNAME")
})
public class FUND_MENU_GROUP implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long FMG_GROUPID;
	@Column(length=255,nullable=false)
	private String FMG_GROUPNAME;
	@Column(length=45)
	private String FMG_CREATEDBY;
	private Calendar FMG_DATE;
	@Column(length=45)
	private String FMG_MODIFIEDBY;
	private Calendar FMG_LAST_CHANGE;
	/*
	 * Relationship with FUND_MENU_TASK as Child Table
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="fund_menu_group",cascade=CascadeType.ALL,orphanRemoval=true)
    @JsonManagedReference
	private List<FUND_MENU_TASK> fund_menu_task=new ArrayList<FUND_MENU_TASK>();
	
	public List<FUND_MENU_TASK> getFund_menu_task() {
		return fund_menu_task;
	}
	public void setFund_menu_task(List<FUND_MENU_TASK> fund_menu_task) {
		this.fund_menu_task = fund_menu_task;
	}
	
	
	public long getFMG_GROUPID() {
		return FMG_GROUPID;
	}
	public void setFMG_GROUPID(long fMG_GROUPID) {
		FMG_GROUPID = fMG_GROUPID;
	}
	public String getFMG_GROUPNAME() {
		return FMG_GROUPNAME;
	}
	public void setFMG_GROUPNAME(String fMG_GROUPNAME) {
		FMG_GROUPNAME = fMG_GROUPNAME;
	}
	public String getFMG_CREATEDBY() {
		return FMG_CREATEDBY;
	}
	public void setFMG_CREATEDBY(String fMG_CREATEDBY) {
		FMG_CREATEDBY = fMG_CREATEDBY;
	}
	public Calendar getFMG_DATE() {
		return FMG_DATE;
	}
	public void setFMG_DATE(Calendar fMG_DATE) {
		FMG_DATE = fMG_DATE;
	}
	public String getFMG_MODIFIEDBY() {
		return FMG_MODIFIEDBY;
	}
	public void setFMG_MODIFIEDBY(String fMG_MODIFIEDBY) {
		FMG_MODIFIEDBY = fMG_MODIFIEDBY;
	}
	public Calendar getFMG_LAST_CHANGE() {
		return FMG_LAST_CHANGE;
	}
	public void setFMG_LAST_CHANGE(Calendar fMG_LAST_CHANGE) {
		FMG_LAST_CHANGE = fMG_LAST_CHANGE;
	}
	
	@Override
	public String toString() {
		return "FUND_MENU_GROUP [FMG_GROUPID=" + FMG_GROUPID + ", FMG_GROUPNAME=" + FMG_GROUPNAME + ", FMG_CREATEDBY="
				+ FMG_CREATEDBY + ", FMG_DATE=" + FMG_DATE + ", FMG_MODIFIEDBY=" + FMG_MODIFIEDBY + ", FMG_LAST_CHANGE="
				+ FMG_LAST_CHANGE + "]";
	}

	
}
