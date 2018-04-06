
package com.fynisys.controller.funds.beans;

import java.io.Serializable;
import java.util.Calendar;

public class MarginBean implements Serializable,Comparable<MarginBean>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sno;
	private Calendar FMD_DATE;
	private String margin_level;
	private String assetid;
	private String assetname;
	private String stockid;
	private String stockname;
	
	private String level;
	private String fund_id;
	private String fund_name;
	private String client_id;
	private String client_name;
	private String client_type_id;
	private String client_type_name;
	private String broker_Id;
	private String broker_name;
	private String custodian_Id;
	private String custodian_name;
	
	private String fmd_initial;
	private String fmd_maintenance;
	private String fmd_liquiation;
	private String fmd_uid;
	private Calendar fmd_iu_date;
	private String margin_per;
	private String comments;
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
		public String getAssetid() {
			return assetid;
		}
		public void setAssetid(String assetid) {
			this.assetid = assetid;
		}
		public String getAssetname() {
			return assetname;
		}
		public void setAssetname(String assetname) {
			this.assetname = assetname;
		}
		public String getStockid() {
			return stockid;
		}
		public void setStockid(String stockid) {
			this.stockid = stockid;
		}
		public String getStockname() {
			return stockname;
		}
		public void setStockname(String stockname) {
			this.stockname = stockname;
		}
		public String getSno() {
			return sno;
		}
		public void setSno(String sno) {
			this.sno = sno;
		}
		public String getMargin_level() {
			return margin_level;
		}
		public void setMargin_level(String margin_level) {
			this.margin_level = margin_level;
		}
		
		public Calendar getFMD_DATE() {
			return FMD_DATE;
		}
		public void setFMD_DATE(Calendar fMD_DATE) {
			FMD_DATE = fMD_DATE;
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
		public String getBroker_Id() {
			return broker_Id;
		}
		public void setBroker_Id(String broker_Id) {
			this.broker_Id = broker_Id;
		}
		public String getBroker_name() {
			return broker_name;
		}
		public void setBroker_name(String broker_name) {
			this.broker_name = broker_name;
		}
		public String getCustodian_Id() {
			return custodian_Id;
		}
		public void setCustodian_Id(String custodian_Id) {
			this.custodian_Id = custodian_Id;
		}
		public String getCustodian_name() {
			return custodian_name;
		}
		public void setCustodian_name(String custodian_name) {
			this.custodian_name = custodian_name;
		}
		public String getFmd_initial() {
			return fmd_initial;
		}
		public void setFmd_initial(String fmd_initial) {
			this.fmd_initial = fmd_initial;
		}
		public String getFmd_maintenance() {
			return fmd_maintenance;
		}
		public void setFmd_maintenance(String fmd_maintenance) {
			this.fmd_maintenance = fmd_maintenance;
		}
		public String getFmd_liquiation() {
			return fmd_liquiation;
		}
		public void setFmd_liquiation(String fmd_liquiation) {
			this.fmd_liquiation = fmd_liquiation;
		}
		public String getFmd_uid() {
			return fmd_uid;
		}
		public void setFmd_uid(String fmd_uid) {
			this.fmd_uid = fmd_uid;
		}
		public Calendar getFmd_iu_date() {
			return fmd_iu_date;
		}
		public void setFmd_iu_date(Calendar fmd_iu_date) {
			this.fmd_iu_date = fmd_iu_date;
		}
		public String getMargin_per() {
			return margin_per;
		}
		public void setMargin_per(String margin_per) {
			this.margin_per = margin_per;
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
		public int compareTo(MarginBean o) {
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
