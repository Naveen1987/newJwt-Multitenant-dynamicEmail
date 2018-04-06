package com.fynisys.controller.funds.beans;

public class BenchMarkBean implements Comparable<BenchMarkBean> {
	private String sno;
	private String effectdate;
	private String level;
	private String fund_id;
	private String fund_name;
	private String client_id;
	private String client_name;
	private String client_type_id;
	private String client_type_name;
	private String indexid;
	private String indexname;
	private String comments;
	private String status;
	private String enteredby;
	private String enteredbyuserid;
	private String enteredbyuuid;
	private String entereddate;
	private String approvedby;
	private String approvedbyuserid;
	private String approvedbyuuid;
	private String approveddate;
	private String modifiedby;
	private String modifiedbyuserid;
	private String modifiedbyuuid;
	private String modifieddate;
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getEffectdate() {
		return effectdate;
	}
	public void setEffectdate(String effectdate) {
		this.effectdate = effectdate;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getFund_id() {
		return fund_id;
	}
	public void setFund_id(String fund_id) {
		this.fund_id = fund_id;
	}
	public String getFund_name() {
		return fund_name;
	}
	public void setFund_name(String fund_name) {
		this.fund_name = fund_name;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getClient_type_id() {
		return client_type_id;
	}
	public void setClient_type_id(String client_type_id) {
		this.client_type_id = client_type_id;
	}
	public String getClient_type_name() {
		return client_type_name;
	}
	public void setClient_type_name(String client_type_name) {
		this.client_type_name = client_type_name;
	}
	public String getIndexid() {
		return indexid;
	}
	public void setIndexid(String indexid) {
		this.indexid = indexid;
	}
	public String getIndexname() {
		return indexname;
	}
	public void setIndexname(String indexname) {
		this.indexname = indexname;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEnteredby() {
		return enteredby;
	}
	public void setEnteredby(String enteredby) {
		this.enteredby = enteredby;
	}
	public String getEnteredbyuserid() {
		return enteredbyuserid;
	}
	public void setEnteredbyuserid(String enteredbyuserid) {
		this.enteredbyuserid = enteredbyuserid;
	}
	public String getEnteredbyuuid() {
		return enteredbyuuid;
	}
	public void setEnteredbyuuid(String enteredbyuuid) {
		this.enteredbyuuid = enteredbyuuid;
	}
	public String getEntereddate() {
		return entereddate;
	}
	public void setEntereddate(String entereddate) {
		this.entereddate = entereddate;
	}
	public String getApprovedby() {
		return approvedby;
	}
	public void setApprovedby(String approvedby) {
		this.approvedby = approvedby;
	}
	public String getApprovedbyuserid() {
		return approvedbyuserid;
	}
	public void setApprovedbyuserid(String approvedbyuserid) {
		this.approvedbyuserid = approvedbyuserid;
	}
	public String getApprovedbyuuid() {
		return approvedbyuuid;
	}
	public void setApprovedbyuuid(String approvedbyuuid) {
		this.approvedbyuuid = approvedbyuuid;
	}
	public String getApproveddate() {
		return approveddate;
	}
	public void setApproveddate(String approveddate) {
		this.approveddate = approveddate;
	}
	public String getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	public String getModifiedbyuserid() {
		return modifiedbyuserid;
	}
	public void setModifiedbyuserid(String modifiedbyuserid) {
		this.modifiedbyuserid = modifiedbyuserid;
	}
	public String getModifiedbyuuid() {
		return modifiedbyuuid;
	}
	public void setModifiedbyuuid(String modifiedbyuuid) {
		this.modifiedbyuuid = modifiedbyuuid;
	}
	public String getModifieddate() {
		return modifieddate;
	}
	public void setModifieddate(String modifieddate) {
		this.modifieddate = modifieddate;
	}
	@Override
	public int compareTo(BenchMarkBean o) {
		if(fund_name.compareTo(o.getFund_name())>0) {
			return 1;
		}else if(fund_name.compareTo(o.getFund_name())<0)
		{
			return -1;
		}else {
			return 0;
		}
	}
}
