package com.fynisys.controller.funds.beans;

import java.io.Serializable;
import java.util.Calendar;

public class FundsFeesBean implements Serializable,Comparable<FundsFeesBean>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sno;
	private Calendar effectdate;
	private String share_id;
	private String share_name;
	private String level;
	private String fund_id;
	private String fund_name;
	private String client_id;
	private String client_name;
	private String client_type_id;
	private String client_type_name;
	private String Fee_flag;
	private String Divisory_Days;
	private String Calcualte_on;
	private String Day;
	private String Fee_type;
	private String Fee_Amount;		  
	private String comments;
	private String status;
	private String Frequency;
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
		public String getFrequency() {
			return Frequency;
		}
		public void setFrequency(String frequency) {
			Frequency = frequency;
		}
		private String msg;
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public String getSno() {
			return sno;
		}
		public void setSno(String sno) {
			this.sno = sno;
		}
		public Calendar getEffectdate() {
			return effectdate;
		}
		public void setEffectdate(Calendar effectdate) {
			this.effectdate = effectdate;
		}
		public String getShare_id() {
			return share_id;
		}
		public void setShare_id(String share_id) {
			this.share_id = share_id;
		}
		public String getShare_name() {
			return share_name;
		}
		public void setShare_name(String share_name) {
			this.share_name = share_name;
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
		public String getFee_Type() {
			return Fee_type;
		}
		public void setFee_Type(String flat_Amount) {
			Fee_type = flat_Amount;
		}
		public String getDivisory_Days() {
			return Divisory_Days;
		}
		public void setDivisory_Days(String divisory_Days) {
			Divisory_Days = divisory_Days;
		}
		public String getCalcualte_on() {
			return Calcualte_on;
		}
		public void setCalcualte_on(String calcualte_on) {
			Calcualte_on = calcualte_on;
		}
		public String getDay() {
			return Day;
		}
		public void setDay(String day) {
			Day = day;
		}
		public String getFee_Flag() {
			return Fee_flag;
		}
		public void setFee_Flag(String fee_Flag) {
			Fee_flag = fee_Flag;
		}
		public String getFee_Amount() {
			return Fee_Amount;
		}
		public void setFee_Amount(String fee_Amount) {
			Fee_Amount = fee_Amount;
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
		public int compareTo(FundsFeesBean o) {
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
