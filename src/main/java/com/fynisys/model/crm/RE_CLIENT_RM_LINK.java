package com.fynisys.model.crm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name="RE_CLIENT_RM_LINK")
public class RE_CLIENT_RM_LINK {
	@Id
	@GeneratedValue
	private Long RCL_RM_CLIENT_ID;
	@Column(length=30)
	private String RCL_FLAG;
	@Column(precision=10,scale=2)
	private Double RCL_FEE_PER;
	private Long RCL_RM_ID;
	/*
	 * 
	 */
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="RCL_NO")
	private RE_CLIENT_RM_LINK_HEAD rE_CLIENT_RM_LINK_HEAD ;
	public Long getRCL_RM_CLIENT_ID() {
		return RCL_RM_CLIENT_ID;
	}
	public void setRCL_RM_CLIENT_ID(Long rCL_RM_CLIENT_ID) {
		RCL_RM_CLIENT_ID = rCL_RM_CLIENT_ID;
	}
	public String getRCL_FLAG() {
		return RCL_FLAG;
	}
	public void setRCL_FLAG(String rCL_FLAG) {
		RCL_FLAG = rCL_FLAG;
	}
	public Double getRCL_FEE_PER() {
		return RCL_FEE_PER;
	}
	public void setRCL_FEE_PER(Double rCL_FEE_PER) {
		RCL_FEE_PER = rCL_FEE_PER;
	}
	public Long getRCL_RM_ID() {
		return RCL_RM_ID;
	}
	public void setRCL_RM_ID(Long rCL_RM_ID) {
		RCL_RM_ID = rCL_RM_ID;
	}
	public RE_CLIENT_RM_LINK_HEAD getrE_CLIENT_RM_LINK_HEAD() {
		return rE_CLIENT_RM_LINK_HEAD;
	}
	public void setrE_CLIENT_RM_LINK_HEAD(RE_CLIENT_RM_LINK_HEAD rE_CLIENT_RM_LINK_HEAD) {
		this.rE_CLIENT_RM_LINK_HEAD = rE_CLIENT_RM_LINK_HEAD;
	}
	
}
