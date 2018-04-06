package com.fynisys.model.crm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name="RE_CLIENT_RM_LINK_HEAD")
public class RE_CLIENT_RM_LINK_HEAD {
	@Id
	@GeneratedValue
private Long RCL_NO;
private Calendar RCL_CDATE;
private Calendar RCL_EFECT_DATE;
@Column(length=50)
private String WMS_STATUS;
@Column(length=100)
private String WMS_COMMENTS;
@Column(length=119)
private String IV_ENTER_UID;
private Calendar IV_ENTER_DATE; 
@Column(length=119)
private String IV_APPROVE_UID;
private Calendar IV_APPROVE_DATE;
@Column(length=119)
private String IV_LAST_UPDATE_UID; 
private Calendar  IV_LAST_UPDATE_DATE;
/*
 * Connection with investor
 */
@ManyToOne
@JoinColumn(name="RCL_CLIENT")
@JsonBackReference
private RE_INVESTOR rE_INVESTOR;
/*
 * Relaton with client
 */
@OneToMany(mappedBy="rE_CLIENT_RM_LINK_HEAD",fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)
@JsonManagedReference
private List<RE_CLIENT_RM_LINK> rE_CLIENT_RM_LINK=new ArrayList<>();

@PrePersist
public void setCreationDate() {
	setRCL_CDATE(Calendar.getInstance());
	setIV_ENTER_DATE(Calendar.getInstance());
}

@PreUpdate
public void modifiedDate()
{
	setIV_LAST_UPDATE_DATE(Calendar.getInstance());
}
public Long getRCL_NO() {
	return RCL_NO;
}
public void setRCL_NO(Long rCL_NO) {
	RCL_NO = rCL_NO;
}
public Calendar getRCL_CDATE() {
	return RCL_CDATE;
}
public void setRCL_CDATE(Calendar rCL_CDATE) {
	RCL_CDATE = rCL_CDATE;
}
public Calendar getRCL_EFECT_DATE() {
	return RCL_EFECT_DATE;
}
public void setRCL_EFECT_DATE(Calendar rCL_EFECT_DATE) {
	RCL_EFECT_DATE = rCL_EFECT_DATE;
}
public String getWMS_STATUS() {
	return WMS_STATUS;
}
public void setWMS_STATUS(String wMS_STATUS) {
	WMS_STATUS = wMS_STATUS;
}
public String getWMS_COMMENTS() {
	return WMS_COMMENTS;
}
public void setWMS_COMMENTS(String wMS_COMMENTS) {
	WMS_COMMENTS = wMS_COMMENTS;
}
public String getIV_ENTER_UID() {
	return IV_ENTER_UID;
}
public void setIV_ENTER_UID(String iV_ENTER_UID) {
	IV_ENTER_UID = iV_ENTER_UID;
}
public Calendar getIV_ENTER_DATE() {
	return IV_ENTER_DATE;
}
public void setIV_ENTER_DATE(Calendar iV_ENTER_DATE) {
	IV_ENTER_DATE = iV_ENTER_DATE;
}
public String getIV_APPROVE_UID() {
	return IV_APPROVE_UID;
}
public void setIV_APPROVE_UID(String iV_APPROVE_UID) {
	IV_APPROVE_UID = iV_APPROVE_UID;
}
public Calendar getIV_APPROVE_DATE() {
	return IV_APPROVE_DATE;
}
public void setIV_APPROVE_DATE(Calendar iV_APPROVE_DATE) {
	IV_APPROVE_DATE = iV_APPROVE_DATE;
}
public String getIV_LAST_UPDATE_UID() {
	return IV_LAST_UPDATE_UID;
}
public void setIV_LAST_UPDATE_UID(String iV_LAST_UPDATE_UID) {
	IV_LAST_UPDATE_UID = iV_LAST_UPDATE_UID;
}
public Calendar getIV_LAST_UPDATE_DATE() {
	return IV_LAST_UPDATE_DATE;
}
public void setIV_LAST_UPDATE_DATE(Calendar iV_LAST_UPDATE_DATE) {
	IV_LAST_UPDATE_DATE = iV_LAST_UPDATE_DATE;
}
public RE_INVESTOR getrE_INVESTOR() {
	return rE_INVESTOR;
}
public void setrE_INVESTOR(RE_INVESTOR rE_INVESTOR) {
	this.rE_INVESTOR = rE_INVESTOR;
}
public List<RE_CLIENT_RM_LINK> getrE_CLIENT_RM_LINK() {
	return rE_CLIENT_RM_LINK;
}
public void setrE_CLIENT_RM_LINK(List<RE_CLIENT_RM_LINK> rE_CLIENT_RM_LINK) {
	this.rE_CLIENT_RM_LINK = rE_CLIENT_RM_LINK;
}

}
