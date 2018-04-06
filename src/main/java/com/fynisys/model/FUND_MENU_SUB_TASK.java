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


@Entity(name="FUND_MENU_SUB_TASK")
public class FUND_MENU_SUB_TASK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long FMST_MENUSUBTASKID;
	@Column(length=255)
	private String FMST_MENUSUBNAME;
	@Column(length=45)
	private String FMST_CREATEDBY;
	private Calendar FMST_DATE;
	@Column(length=45)
	private String FMST_MODIFIEDBY;
	private Calendar FMST_LAST_CHANGE;
	
	/*
	 * Relationship with fund_menu_group as Parent
	 */
	@ManyToOne
	@JoinColumn(name="FMT_MENUTASKID",foreignKey=@ForeignKey(name="fk_fund_menu_task_1"))
	@JsonBackReference
	private FUND_MENU_TASK fun_menu_task;
	
	public FUND_MENU_TASK getFun_menu_task() {
		return fun_menu_task;
	}

	public void setFun_menu_task(FUND_MENU_TASK fun_menu_task) {
		this.fun_menu_task = fun_menu_task;
	}
	
	/*
	 * Relationship with fund_menu_sub_task as Parent
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="fund_menu_sub_task",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonManagedReference
	private List<FUND_ROLE_SUB_TASK> fund_role_sub_task=new ArrayList<FUND_ROLE_SUB_TASK>();

	public List<FUND_ROLE_SUB_TASK> getFund_role_sub_task() {
		return fund_role_sub_task;
	}

	public void setFund_role_sub_task(List<FUND_ROLE_SUB_TASK> fund_role_sub_task) {
		this.fund_role_sub_task = fund_role_sub_task;
	}
	
	
	
	public long getFMST_MENUSUBTASKID() {
		return FMST_MENUSUBTASKID;
	}

	public void setFMST_MENUSUBTASKID(long fMST_MENUSUBTASKID) {
		FMST_MENUSUBTASKID = fMST_MENUSUBTASKID;
	}

	
	public String getFMST_MENUSUBNAME() {
		return FMST_MENUSUBNAME;
	}

	public void setFMST_MENUSUBNAME(String fMST_MENUSUBNAME) {
		FMST_MENUSUBNAME = fMST_MENUSUBNAME;
	}

	public String getFMST_CREATEDBY() {
		return FMST_CREATEDBY;
	}

	public void setFMST_CREATEDBY(String fMST_CREATEDBY) {
		FMST_CREATEDBY = fMST_CREATEDBY;
	}

	public Calendar getFMST_DATE() {
		return FMST_DATE;
	}

	public void setFMST_DATE(Calendar fMT_DATE) {
		FMST_DATE = fMT_DATE;
	}

	public String getFMST_MODIFIEDBY() {
		return FMST_MODIFIEDBY;
	}

	public void setFMST_MODIFIEDBY(String fMST_MODIFIEDBY) {
		FMST_MODIFIEDBY = fMST_MODIFIEDBY;
	}

	public Calendar getFMST_LAST_CHANGE() {
		return FMST_LAST_CHANGE;
	}

	public void setFMST_LAST_CHANGE(Calendar fMST_LAST_CHANGE) {
		FMST_LAST_CHANGE = fMST_LAST_CHANGE;
	}

	@Override
	public String toString() {
		return "FUND_MENU_SUB_TASK [FMST_MENUSUBTASKID=" + FMST_MENUSUBTASKID + ", FMST_MENUSUBNAME=" + FMST_MENUSUBNAME
				+ ", FMST_CREATEDBY=" + FMST_CREATEDBY + ", FMT_DATE=" + FMST_DATE + ", FMST_MODIFIEDBY="
				+ FMST_MODIFIEDBY + ", FMST_LAST_CHANGE=" + FMST_LAST_CHANGE + ", fun_menu_task=" + fun_menu_task
				+ ", fund_role_sub_task=" + fund_role_sub_task + "]";
	}
	
	
	
}
