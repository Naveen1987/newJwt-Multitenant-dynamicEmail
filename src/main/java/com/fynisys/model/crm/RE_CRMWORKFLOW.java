package com.fynisys.model.crm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/*
RE_APPROVAL_ID      Long                          - Primary Key
IN_APPROVAL_ID      Varchar(100)                  - Uuid of User(initiator)
IN_APPROVAL_DATE    DATE                          - Current Date 
CO_APPROVAL_ID      varchar(100)                  - Uuid of User
CO_APPROVAL_STATUS  Boolean                       -(0 not approved, 1 Approved)
CO_APPROVAL_DATE    DATE						  - Current Date (When it's come for approved)
CO_APPROVED_DATE    DATE						  - Current Date (When it's Approved)
FM_APPROVAL_ID      Varchar(100)                  - Uuid of User
FM_APPROVAL_STATUS  Boolean					      -(0 not approved, 1 Approved)								
FM_APPROVAL_DATE    DATE                          - Current Date
FM_APPROVED_DATE    DATE						  - Current Date (When it's Approved)
OP_APPROVAL_ID      Varchar(100)                  - Uuid of User
OP_APPROVAL_STATUS  Boolean					      -(0 not approved, 1 Approved)
OP_APPROVAL_DATE    DATE                          - Current Date 
OP_APPROVED_DATE    DATE                          - Current Date (When it's Approved)
RI_WMS_CODE      
 */
@Entity(name="RE_CRMWORKFLOW")
public class RE_CRMWORKFLOW {
	@Id
	@GeneratedValue
	private Long RE_CRMWORKFLOW_ID;
	private String INITIATOR;
	private String CO;
	private String FM;
	private String OP;
	private Integer CURRENT_STATUS;
	@OneToOne
	@JoinColumn(name="RI_WMS_CODE",foreignKey=@ForeignKey(name="FK_RE_CRMWORKFLOW"))
    @JsonBackReference
	private RE_INVESTOR  rE_INVESTOR;
	
	/*
	 * Relationship with table
	 */

	@OneToMany(fetch=FetchType.LAZY,mappedBy="rE_CRMWORKFLOW",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonManagedReference
	private List<RE_CRMWORKFLOWSTATUS> rE_CRMWORKFLOWSTATUS=new ArrayList<>();
	public List<RE_CRMWORKFLOWSTATUS> getrE_CRMWORKFLOWSTATUS() {
		return rE_CRMWORKFLOWSTATUS;
	}
	public void setrE_CRMWORKFLOWSTATUS(List<RE_CRMWORKFLOWSTATUS> rE_CRMWORKFLOWSTATUS) {
		this.rE_CRMWORKFLOWSTATUS = rE_CRMWORKFLOWSTATUS;
	}
	
	public Long getRE_CRMWORKFLOW_ID() {
		return RE_CRMWORKFLOW_ID;
	}
	public void setRE_CRMWORKFLOW_ID(Long rE_CRMWORKFLOW_ID) {
		RE_CRMWORKFLOW_ID = rE_CRMWORKFLOW_ID;
	}
	public String getINITIATOR() {
		return INITIATOR;
	}
	public void setINITIATOR(String iNITIATOR) {
		INITIATOR = iNITIATOR;
	}
	public String getCO() {
		return CO;
	}
	public void setCO(String cO) {
		CO = cO;
	}
	public String getFM() {
		return FM;
	}
	public void setFM(String fM) {
		FM = fM;
	}
	public String getOP() {
		return OP;
	}
	public void setOP(String oP) {
		OP = oP;
	}
	
	public RE_INVESTOR getrE_INVESTOR() {
		return rE_INVESTOR;
	}
	public void setrE_INVESTOR(RE_INVESTOR rE_INVESTOR) {
		this.rE_INVESTOR = rE_INVESTOR;
	}
	public Integer getCURRENT_STATUS() {
		return CURRENT_STATUS;
	}
	public void setCURRENT_STATUS(Integer cURRENT_STATUS) {
		CURRENT_STATUS = cURRENT_STATUS;
	}
	
}
