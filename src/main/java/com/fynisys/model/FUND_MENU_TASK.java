package com.fynisys.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity(name="FUND_MENU_TASK")
public class FUND_MENU_TASK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long FMT_MENUTASKID;
	@Column(length=255)
	private String FMT_MENUNAME;
	@Column(length=45)
	private String FMT_CREATEDBY;
	private Calendar FMT_DATE;
	@Column(length=45)
	private String FMT_MODIFIEDBY;
	private Calendar FMT_LAST_CHANGE;
	
	/*
	 * Relationship with fund_menu_group as Parent
	 */
	@ManyToOne
	@JoinColumn(name="FMG_GROUPID",foreignKey=@ForeignKey(name="fk_fund_menu_group"))
	@JsonBackReference
	private FUND_MENU_GROUP fund_menu_group;
	
	public FUND_MENU_GROUP getFund_menu_group() {
		return fund_menu_group;
	}
	public void setFund_menu_group(FUND_MENU_GROUP fund_menu_group) {
		this.fund_menu_group = fund_menu_group;
	}

	
	/*
	 * Relationship with fund_role_task as Parent
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="fund_menu_task",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonManagedReference
	private List<FUND_ROLE_TASK> fund_role_task=new ArrayList<FUND_ROLE_TASK>();
	
	public List<FUND_ROLE_TASK> getFund_role_task() {
		return fund_role_task;
	}
	public void setFund_role_task(List<FUND_ROLE_TASK> fund_role_task) {
		this.fund_role_task = fund_role_task;
	}
	
	/*
	 * Relationship with fund_menu_sub_task as Parent
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="fun_menu_task",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonManagedReference
	private List<FUND_MENU_SUB_TASK> fund_menu_sub_task=new ArrayList<FUND_MENU_SUB_TASK>();
	
	public List<FUND_MENU_SUB_TASK> getFund_menu_sub_task() {
		return fund_menu_sub_task;
	}
	public void setFund_menu_sub_task(List<FUND_MENU_SUB_TASK> fund_menu_sub_task) {
		this.fund_menu_sub_task = fund_menu_sub_task;
	}
	
	public long getFMT_MENUTASKID() {
		return FMT_MENUTASKID;
	}
	public void setFMT_MENUTASKID(long fMT_MENUTASKID) {
		FMT_MENUTASKID = fMT_MENUTASKID;
	}
	
	
	
	public String getFMT_MENUNAME() {
		return FMT_MENUNAME;
	}
	public void setFMT_MENUNAME(String fMT_MENUNAME) {
		FMT_MENUNAME = fMT_MENUNAME;
	}
	public String getFMT_CREATEDBY() {
		return FMT_CREATEDBY;
	}
	public void setFMT_CREATEDBY(String fMT_CREATEDBY) {
		FMT_CREATEDBY = fMT_CREATEDBY;
	}
	public Calendar getFMT_DATE() {
		return FMT_DATE;
	}
	public void setFMT_DATE(Calendar fMT_DATE) {
		FMT_DATE = fMT_DATE;
	}
	public String getFMT_MODIFIEDBY() {
		return FMT_MODIFIEDBY;
	}
	public void setFMT_MODIFIEDBY(String fMT_MODIFIEDBY) {
		FMT_MODIFIEDBY = fMT_MODIFIEDBY;
	}
	public Calendar getFMT_LAST_CHANGE() {
		return FMT_LAST_CHANGE;
	}
	public void setFMT_LAST_CHANGE(Calendar fMT_LAST_CHANGE) {
		FMT_LAST_CHANGE = fMT_LAST_CHANGE;
	}
	
	@Override
	public String toString() {
		return "FUND_MENU_TASK [FMT_MENUTASKID=" + FMT_MENUTASKID + ", FMT_MENUNAME=" + FMT_MENUNAME
				+ ", FMT_CREATEDBY=" + FMT_CREATEDBY + ", FMT_DATE=" + FMT_DATE + ", FMT_MODIFIEDBY=" + FMT_MODIFIEDBY
				+ ", FMT_LAST_CHANGE=" + FMT_LAST_CHANGE + "]";
	}

	
}
