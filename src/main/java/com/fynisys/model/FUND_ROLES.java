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

@Entity(name="FUND_ROLES")
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames="FRL_ROLENAME",name="unique_FRL_ROLENAME")
})
public class FUND_ROLES implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long FRL_ROLEID;
	 @Column(length=100,nullable=false)
	private String FRL_ROLENAME;
	 @Column(length=100)
	private String FRL_ROLETYPE;
	 @Column(length=45)
	private String FRL_CREATEDBY;
	private Calendar FRL_DATE;
	@Column(length=45)
	private String FRL_MODIFIEDBY;
	private Calendar FRL_LAST_CHANGE;
	
	/*
	 * Here is relationship FUND_USERS as Child
	 * */
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="fund_roles",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonManagedReference
	private List<FUND_USERS> fund_users=new ArrayList<FUND_USERS>();
	
	public List<FUND_USERS> getFund_users() {
		return fund_users;
	}
	public void setFund_users(List<FUND_USERS> fund_users) {
		this.fund_users = fund_users;
	}
		
	/*
	 * Here is relationship FUND_ROLE_TASK as Child
	 * */
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="fund_roles",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonManagedReference
	private List<FUND_ROLE_TASK> fund_role_task=new ArrayList<FUND_ROLE_TASK>();
	
	public List<FUND_ROLE_TASK> getFund_role_task() {
		return fund_role_task;
	}
	public void setFund_role_task(List<FUND_ROLE_TASK> fund_role_task) {
		this.fund_role_task = fund_role_task;
	}
	
	
	/*
	 * Here is relationship FUND_ROLE_SUB_TASK as Child
	 * */
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="fund_roles",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonManagedReference
	private List<FUND_ROLE_SUB_TASK> fund_role_sub_task=new ArrayList<FUND_ROLE_SUB_TASK>();
		
	public List<FUND_ROLE_SUB_TASK> getFund_role_sub_task() {
		return fund_role_sub_task;
	}
	public void setFund_role_sub_task(List<FUND_ROLE_SUB_TASK> fund_role_sub_task) {
		this.fund_role_sub_task = fund_role_sub_task;
	}
	
	
	
	public long getFRL_ROLEID() {
		return FRL_ROLEID;
	}
	public void setFRL_ROLEID(long fRL_ROLEID) {
		FRL_ROLEID = fRL_ROLEID;
	}
	public String getFRL_ROLENAME() {
		return FRL_ROLENAME;
	}
	public void setFRL_ROLENAME(String fRL_ROLENAME) {
		FRL_ROLENAME = fRL_ROLENAME;
	}
	public String getFRL_ROLETYPE() {
		return FRL_ROLETYPE;
	}
	public void setFRL_ROLETYPE(String fRL_ROLETYPE) {
		FRL_ROLETYPE = fRL_ROLETYPE;
	}
	public String getFRL_CREATEDBY() {
		return FRL_CREATEDBY;
	}
	public void setFRL_CREATEDBY(String fRL_CREATEDBY) {
		FRL_CREATEDBY = fRL_CREATEDBY;
	}
	public Calendar getFRL_DATE() {
		return FRL_DATE;
	}
	public void setFRL_DATE(Calendar fRL_DATE) {
		FRL_DATE = fRL_DATE;
	}
	public String getFRL_MODIFIEDBY() {
		return FRL_MODIFIEDBY;
	}
	public void setFRL_MODIFIEDBY(String fRL_MODIFIEDBY) {
		FRL_MODIFIEDBY = fRL_MODIFIEDBY;
	}
	public Calendar getFRL_LAST_CHANGE() {
		return FRL_LAST_CHANGE;
	}
	public void setFRL_LAST_CHANGE(Calendar fRL_LAST_CHANGE) {
		FRL_LAST_CHANGE = fRL_LAST_CHANGE;
	}
	
	@Override
	public String toString() {
		return "FUND_ROLES [FRL_ROLEID=" + FRL_ROLEID + ", FRL_ROLENAME=" + FRL_ROLENAME + ", FRL_ROLETYPE="
				+ FRL_ROLETYPE + ", FRL_CREATEDBY=" + FRL_CREATEDBY + ", FRL_DATE=" + FRL_DATE + ", FRL_MODIFIEDBY="
				+ FRL_MODIFIEDBY + ", FRL_LAST_CHANGE=" + FRL_LAST_CHANGE + "]";
	}
 
	
}
