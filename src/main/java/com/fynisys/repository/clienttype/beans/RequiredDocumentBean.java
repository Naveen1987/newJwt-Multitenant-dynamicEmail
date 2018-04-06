package com.fynisys.repository.clienttype.beans;

import java.util.Calendar;

public class RequiredDocumentBean implements Comparable<RequiredDocumentBean>{
	private String docreqid;
	private String clienttypeid;
	private String clienttypename;
	private String docid;
	private String doctypename;
	private String comment;
	private String status;
	private String enteredby;
	private String enteredbyuserid;
	private String enteredbyuuid;
	private Calendar entereddate;
	private String approvedby;
	private String approvedbyuserid;
	private String approvedbyuuid;
	private Calendar approveddate;
	private String modifiedby;
	private String modifiedbyuserid;
	private String modifiedbyuuid;
	private Calendar modifieddate;
	private String msg;
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDocreqid() {
		return docreqid;
	}

	public void setDocreqid(String docreqid) {
		this.docreqid = docreqid;
	}

	public String getClienttypeid() {
		return clienttypeid;
	}

	public void setClienttypeid(String clienttypeid) {
		this.clienttypeid = clienttypeid;
	}

	public String getClienttypename() {
		return clienttypename;
	}

	public void setClienttypename(String clienttypename) {
		this.clienttypename = clienttypename;
	}

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getDoctypename() {
		return doctypename;
	}

	public void setDoctypename(String doctypename) {
		this.doctypename = doctypename;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public Calendar getEntereddate() {
		return entereddate;
	}

	public void setEntereddate(Calendar entereddate) {
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

	public Calendar getApproveddate() {
		return approveddate;
	}

	public void setApproveddate(Calendar approveddate) {
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

	public Calendar getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(Calendar modifieddate) {
		this.modifieddate = modifieddate;
	}

	@Override
	public int compareTo(RequiredDocumentBean o) {
		// TODO Auto-generated method stub
		return clienttypename.compareTo(o.clienttypename);//getClienttypename().compareTo(o.getClienttypename());
	}

	@Override
	public String toString() {
		return "RequiredDocumentBean [docreqid=" + docreqid + ", clienttypeid=" + clienttypeid + ", clienttypename="
				+ clienttypename + ", docid=" + docid + ", doctypename=" + doctypename + ", comment=" + comment
				+ ", status=" + status + ", enteredby=" + enteredby + ", enteredbyuserid=" + enteredbyuserid
				+ ", enteredbyuuid=" + enteredbyuuid + ", entereddate=" + entereddate + ", approvedby=" + approvedby
				+ ", approvedbyuserid=" + approvedbyuserid + ", approvedbyuuid=" + approvedbyuuid + ", approveddate="
				+ approveddate + ", modifiedby=" + modifiedby + ", modifiedbyuserid=" + modifiedbyuserid
				+ ", modifiedbyuuid=" + modifiedbyuuid + ", modifieddate=" + modifieddate + ", msg=" + msg + "]";
	}

}
