package com.fynisys.model.crm;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name="RE_CRMWORKFLOWSTATUS")
public class RE_CRMWORKFLOWSTATUS {

	
	@Id
	@GeneratedValue
	private Long RE_CRMWORKFLOWSTATUS_ID;
	private Integer STATUS;
	private Calendar APPROVED_DATE;	
	private Calendar APPROVAL_DATE;
	private String ROLEID;
	private String APPROVEDID;
	private Calendar LAST_MODIFIED_DATE;
	/*
	 * 
	 */
	@ManyToOne
	@JoinColumn(name="rE_CRMWORKFLOW_ID",foreignKey=@ForeignKey(name="fk_RE_CRMWORKFLOWSTATUS"))
	@JsonBackReference
	private RE_CRMWORKFLOW rE_CRMWORKFLOW;

	public RE_CRMWORKFLOW getrE_CRMWORKFLOW() {
		return rE_CRMWORKFLOW;
	}

	public void setrE_CRMWORKFLOW(RE_CRMWORKFLOW rE_CRMWORKFLOW) {
		this.rE_CRMWORKFLOW = rE_CRMWORKFLOW;
	}

	@PrePersist
	@PreUpdate
	public void setModified() {
		setLAST_MODIFIED_DATE(Calendar.getInstance());
	}
	
	public Long getRE_CRMWORKFLOWSTATUS_ID() {
		return RE_CRMWORKFLOWSTATUS_ID;
	}

	public void setRE_CRMWORKFLOWSTATUS_ID(Long rE_CRMWORKFLOWSTATUS_ID) {
		RE_CRMWORKFLOWSTATUS_ID = rE_CRMWORKFLOWSTATUS_ID;
	}

	public Integer getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(Integer sTATUS) {
		STATUS = sTATUS;
	}

	public Calendar getAPPROVED_DATE() {
		return APPROVED_DATE;
	}

	public void setAPPROVED_DATE(Calendar aPPROVED_DATE) {
		APPROVED_DATE = aPPROVED_DATE;
	}

	public Calendar getAPPROVAL_DATE() {
		return APPROVAL_DATE;
	}

	public void setAPPROVAL_DATE(Calendar aPPROVAL_DATE) {
		APPROVAL_DATE = aPPROVAL_DATE;
	}

	public String getROLEID() {
		return ROLEID;
	}

	public void setROLEID(String rOLEID) {
		ROLEID = rOLEID;
	}

	public String getAPPROVEDID() {
		return APPROVEDID;
	}

	public void setAPPROVEDID(String aPPROVEDID) {
		APPROVEDID = aPPROVEDID;
	}

	public Calendar getLAST_MODIFIED_DATE() {
		return LAST_MODIFIED_DATE;
	}

	public void setLAST_MODIFIED_DATE(Calendar lAST_MODIFIED_DATE) {
		LAST_MODIFIED_DATE = lAST_MODIFIED_DATE;
	}
}
