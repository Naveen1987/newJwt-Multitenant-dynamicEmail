package com.fynisys.controller.funds.beans;
public class StopLossBean implements Comparable<StopLossBean>{   
    String effectdate;
    
    String stop_loss_level;
    
    String type;
    String client_id;
    String client_name;
    String client_type_id;
    String client_type_name;
    
    String stop_loss;
    
    String level;
    String fund_id;
    String fund_name;
    
    String status;
    String enteredby;
    String entereddate;
    String approvedby;
    String approveddate;
    String sno;
    String modifiedby;
    String modifieddate;
   
    String comments;
    String approvedbyuserid;
    String enteredbyuuid;
    String enteredbyuserid;
    String approvedbyuuid;
    String  modifiedbyuserid;
    String modifiedbyuuid;
    String msg;
   

	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}



	public String getEffectdate() {
		return effectdate;
	}



	public void setEffectdate(String effectdate) {
		this.effectdate = effectdate;
	}



	public String getStop_loss_level() {
		return stop_loss_level;
	}



	public void setStop_loss_level(String stop_loss_level) {
		this.stop_loss_level = stop_loss_level;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
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



	public String getStop_loss() {
		return stop_loss;
	}



	public void setStop_loss(String stop_loss) {
		this.stop_loss = stop_loss;
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



	public String getApproveddate() {
		return approveddate;
	}



	public void setApproveddate(String approveddate) {
		this.approveddate = approveddate;
	}



	public String getSno() {
		return sno;
	}



	public void setSno(String sno) {
		this.sno = sno;
	}



	public String getModifiedby() {
		return modifiedby;
	}



	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}



	public String getModifieddate() {
		return modifieddate;
	}



	public void setModifieddate(String modifieddate) {
		this.modifieddate = modifieddate;
	}



	public String getComments() {
		return comments;
	}



	public void setComments(String comments) {
		this.comments = comments;
	}



	public String getApprovedbyuserid() {
		return approvedbyuserid;
	}



	public void setApprovedbyuserid(String approvedbyuserid) {
		this.approvedbyuserid = approvedbyuserid;
	}



	public String getEnteredbyuuid() {
		return enteredbyuuid;
	}



	public void setEnteredbyuuid(String enteredbyuuid) {
		this.enteredbyuuid = enteredbyuuid;
	}



	public String getEnteredbyuserid() {
		return enteredbyuserid;
	}



	public void setEnteredbyuserid(String enteredbyuserid) {
		this.enteredbyuserid = enteredbyuserid;
	}



	public String getApprovedbyuuid() {
		return approvedbyuuid;
	}



	public void setApprovedbyuuid(String approvedbyuuid) {
		this.approvedbyuuid = approvedbyuuid;
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



	@Override
	public int compareTo(StopLossBean o) {
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