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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name="FUND_USERS")
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames="SVU_USER_NAME",name="unique_SVU_USER_NAME"),
		@UniqueConstraint(columnNames="SVU_EMAIL",name="unique_SVU_EMAIL")
})
public class FUND_USERS  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * On the base of Schema Observation
	 */
	  @Id
	  @GenericGenerator(name="key_gen",strategy="uuid")
	  @GeneratedValue(generator="key_gen")
	  @Column(length=45)
	  private String SVC_UID;
	  
	  /*
	   * This is representing number of Users
	   */
	  private long SVU_USER_NO; 
	  private Calendar SVU_DATE; 
	  @Column(length=50)
	  private String SVU_NAME;
	  @Column(length=15,nullable=false)
	  private String SVU_USER_NAME;
	  @Column(length=15)
	  private String SVU_USER_PASSWORD; 
	  @Column(length=3)
	  private int SVU_EXPIRY_DAYS;
	  private Calendar SVU_EXPIRY_DATE;
	  private Calendar SVU_LAST_CHANGE; 
	  @Column(length=25)
	  private String SVU_REMARKS;
	  @Column(length=25)
	  private String SVU_FUND;
	  @Column(length=1)
	  private String SVU_LOG_FLAG;
	  @Column(length=1)
	  private String SVU_USER_FLAG; 
	  @Column(length=1)
	  private String SVU_BLOCK; 
	  @Column(length=10)
	  private long SVU_GROUP_SNO; 
	  @Column(length=40)
	  private String SVU_RM_BROKER; 
	  @Column(length=10)
	  private long SVU_RM_BROKER_T;
	  @Column(length=30)
	  private String SVU_USER_TYPE;
	  @Column(length=250)
	  private String SVU_EMAIL;
	  
	  /*
	 * Validate to temporary password
	 * */
	  
	  private int SVU_FLAG;
	
	 /*
	 * SOME Attribute added for Permission allocation
	 * */
	  
	  private Calendar SVU_USER_LASTSEEN;
	  @Column(length=15)
	  private String SVU_USER_CREATEDBY;
	  @Column(length=15)
	  private String SVU_USER_MODIFIEDBY;
	  
	  /*
	   * Here is relationship with FUND_ROLES Table  in back reference
	   * */
	  
	  @Column(length=200)
	  private String SVU_REUTERS_CODE;
	
	  
	  public String getSVU_REUTERS_CODE() {
		return SVU_REUTERS_CODE;
	  }
	  public void setSVU_REUTERS_CODE(String sVU_REUTERS_CODE) {
		SVU_REUTERS_CODE = sVU_REUTERS_CODE;
	  }

	@ManyToOne
	@JoinColumn(name="FRL_ROLEID",foreignKey=@ForeignKey(name="fk_fund_roles"))
	@JsonBackReference
	private FUND_ROLES fund_roles;
	public FUND_ROLES getFund_roles() {
		return fund_roles;
	}
	public void setFund_roles(FUND_ROLES fund_roles) {
		this.fund_roles = fund_roles;
	}
	
	/*
	 * Here relationship with FUND_PASSWORD_HISTORY
	 */
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="fund_users",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonManagedReference
	private List<FUND_PASSWORD_HISTORY> fund_password_history=new ArrayList<FUND_PASSWORD_HISTORY>();
	public List<FUND_PASSWORD_HISTORY> getFund_password_history() {
		return fund_password_history;
	}
	public void setFund_password_history(List<FUND_PASSWORD_HISTORY> fund_password_history) {
		this.fund_password_history = fund_password_history;
	}
	
	/*
	 * Company Default SCHEMA Related Info
	 */
	@Column(nullable=true)
	private Long FCD_DEFAULT_FCODE;
	
	public Long getFCD_DEFAULT_FCODE() {
		return FCD_DEFAULT_FCODE;
	}
	
	public void setFCD_DEFAULT_FCODE(Long fCD_DEFAULT_FCODE) {
		FCD_DEFAULT_FCODE = fCD_DEFAULT_FCODE;
	}
	
	public long getSVU_USER_NO() {
		return SVU_USER_NO;
	}
	public void setSVU_USER_NO(long sVU_USER_NO) {
		SVU_USER_NO = sVU_USER_NO;
	}
	public Calendar getSVU_DATE() {
		return SVU_DATE;
	}
	public void setSVU_DATE(Calendar sVU_DATE) {
		SVU_DATE = sVU_DATE;
	}
	public String getSVU_NAME() {
		return SVU_NAME;
	}
	public void setSVU_NAME(String sVU_NAME) {
		SVU_NAME = sVU_NAME;
	}
	public String getSVU_USER_NAME() {
		return SVU_USER_NAME;
	}
	public void setSVU_USER_NAME(String sVU_USER_NAME) {
		SVU_USER_NAME = sVU_USER_NAME;
	}
	public String getSVU_USER_PASSWORD() {
		return SVU_USER_PASSWORD;
	}
	public void setSVU_USER_PASSWORD(String sVU_USER_PASSWORD) {
		SVU_USER_PASSWORD = sVU_USER_PASSWORD;
	}
	public int getSVU_EXPIRY_DAYS() {
		return SVU_EXPIRY_DAYS;
	}
	public void setSVU_EXPIRY_DAYS(int sVU_EXPIRY_DAYS) {
		SVU_EXPIRY_DAYS = sVU_EXPIRY_DAYS;
	}
	public Calendar getSVU_EXPIRY_DATE() {
		return SVU_EXPIRY_DATE;
	}
	public void setSVU_EXPIRY_DATE(Calendar sVU_EXPIRY_DATE) {
		SVU_EXPIRY_DATE = sVU_EXPIRY_DATE;
	}
	public Calendar getSVU_LAST_CHANGE() {
		return SVU_LAST_CHANGE;
	}
	public void setSVU_LAST_CHANGE(Calendar sVU_LAST_CHANGE) {
		SVU_LAST_CHANGE = sVU_LAST_CHANGE;
	}
	public String getSVU_REMARKS() {
		return SVU_REMARKS;
	}
	public void setSVU_REMARKS(String sVU_REMARKS) {
		SVU_REMARKS = sVU_REMARKS;
	}
	public String getSVU_FUND() {
		return SVU_FUND;
	}
	public void setSVU_FUND(String sVU_FUND) {
		SVU_FUND = sVU_FUND;
	}
	public String getSVU_LOG_FLAG() {
		return SVU_LOG_FLAG;
	}
	public void setSVU_LOG_FLAG(String sVU_LOG_FLAG) {
		SVU_LOG_FLAG = sVU_LOG_FLAG;
	}
	public String getSVU_USER_FLAG() {
		return SVU_USER_FLAG;
	}
	public void setSVU_USER_FLAG(String sVU_USER_FLAG) {
		SVU_USER_FLAG = sVU_USER_FLAG;
	}
	public String getSVC_UID() {
		return SVC_UID;
	}
	public void setSVC_UID(String sVC_UID) {
		SVC_UID = sVC_UID;
	}
	public String getSVU_BLOCK() {
		return SVU_BLOCK;
	}
	public void setSVU_BLOCK(String sVU_BLOCK) {
		SVU_BLOCK = sVU_BLOCK;
	}
	public long getSVU_GROUP_SNO() {
		return SVU_GROUP_SNO;
	}
	public void setSVU_GROUP_SNO(long sVU_GROUP_SNO) {
		SVU_GROUP_SNO = sVU_GROUP_SNO;
	}
	public String getSVU_RM_BROKER() {
		return SVU_RM_BROKER;
	}
	public void setSVU_RM_BROKER(String sVU_RM_BROKER) {
		SVU_RM_BROKER = sVU_RM_BROKER;
	}
	public long getSVU_RM_BROKER_T() {
		return SVU_RM_BROKER_T;
	}
	public void setSVU_RM_BROKER_T(long sVU_RM_BROKER_T) {
		SVU_RM_BROKER_T = sVU_RM_BROKER_T;
	}
	public String getSVU_USER_TYPE() {
		return SVU_USER_TYPE;
	}
	public void setSVU_USER_TYPE(String sVU_USER_TYPE) {
		SVU_USER_TYPE = sVU_USER_TYPE;
	}
	public String getSVU_EMAIL() {
		return SVU_EMAIL;
	}
	public void setSVU_EMAIL(String sVU_EMAIL) {
		SVU_EMAIL = sVU_EMAIL;
	}
	public int getSVU_FLAG() {
		return SVU_FLAG;
	}
	public void setSVU_FLAG(int sVU_FLAG) {
		SVU_FLAG = sVU_FLAG;
	}
	public Calendar getSVU_USER_LASTSEEN() {
		return SVU_USER_LASTSEEN;
	}
	public void setSVU_USER_LASTSEEN(Calendar sVU_USER_LASTSEEN) {
		SVU_USER_LASTSEEN = sVU_USER_LASTSEEN;
	}
	public String getSVU_USER_CREATEDBY() {
		return SVU_USER_CREATEDBY;
	}
	public void setSVU_USER_CREATEDBY(String sVU_USER_CREATEDBY) {
		SVU_USER_CREATEDBY = sVU_USER_CREATEDBY;
	}
	public String getSVU_USER_MODIFIEDBY() {
		return SVU_USER_MODIFIEDBY;
	}
	public void setSVU_USER_MODIFIEDBY(String sVU_USER_MODIFIEDBY) {
		SVU_USER_MODIFIEDBY = sVU_USER_MODIFIEDBY;
	}
	@Override
	public String toString() {
		return "FUND_USERS [SVU_USER_NO=" + SVU_USER_NO + ", SVU_DATE=" + SVU_DATE + ", SVU_NAME=" + SVU_NAME
				+ ", SVU_USER_NAME=" + SVU_USER_NAME + ", SVU_USER_PASSWORD=" + SVU_USER_PASSWORD + ", SVU_EXPIRY_DAYS="
				+ SVU_EXPIRY_DAYS + ", SVU_LAST_CHANGE=" + SVU_LAST_CHANGE + ", SVU_REMARKS=" + SVU_REMARKS
				+ ", SVU_FUND=" + SVU_FUND + ", SVU_LOG_FLAG=" + SVU_LOG_FLAG + ", SVU_USER_FLAG=" + SVU_USER_FLAG
				+ ", SVC_UID=" + SVC_UID + ", SVU_BLOCK=" + SVU_BLOCK + ", SVU_GROUP_SNO=" + SVU_GROUP_SNO
				+ ", SVU_RM_BROKER=" + SVU_RM_BROKER + ", SVU_RM_BROKER_T=" + SVU_RM_BROKER_T + ", SVU_USER_TYPE="
				+ SVU_USER_TYPE + ", SVU_EMAIL=" + SVU_EMAIL + ", SVU_FLAG=" + SVU_FLAG + ", SVU_USER_LASTSEEN="
				+ SVU_USER_LASTSEEN + ", SVU_USER_CREATEDBY=" + SVU_USER_CREATEDBY + ", SVU_USER_MODIFIEDBY="
				+ SVU_USER_MODIFIEDBY + "]";
	}
	
}
