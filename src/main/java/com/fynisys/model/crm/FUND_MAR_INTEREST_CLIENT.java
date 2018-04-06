package com.fynisys.model.crm;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name="FUND_MAR_INTEREST_CLIENT")
public class FUND_MAR_INTEREST_CLIENT {
	@Id
	@GeneratedValue
	@Column(length=10)
	 private Long FMD_SNO;
	private Calendar FID_DATE;
	@Column(length=15)
	 private Long FID_CURRENCY;
	@Column(length=15)
	 private String FID_LEVEL;
	@Column(length=15)
	 private Long FID_CLIENT_TYPE;
	@Column(length=15)
	 private Long FID_CLIENT;
	@Column(length=15)
	 private String FID_BANK;
	
	@Column(precision=25,scale=3)
	 private Double FID_F_SLAB;
	@Column(precision=25,scale=3)
	 private Double FID_T_SLAB;
	@Column(precision=15,scale=6)
	 private Double FID_DR_INT;
	@Column(precision=15,scale=6)
	 private Double FID_CR_INT;
	@Column(precision=15,scale=6)
	 private Double FID_PDR_INT;
	@Column(precision=15,scale=6)
	 private Double FID_PCR_INT;
	@Column(length=15)
	 private String FID_UID;
	private Calendar FID_IU_DATE;
	@Column(length=119)
	 private String IV_ENTER_UID;
	private Calendar IV_ENTER_DATE;
	@Column(length=119)
	 private String IV_APPROVE_UID;
	private Calendar IV_APPROVE_DATE;
	@Column(length=119)
	 private String IV_LAST_UPDATE_UID;
	private Calendar IV_LAST_UPDATE_DATE;
	@Column(length=300)
	private String WMS_COMMENTS;
	@Column(length=20)
	private String WMS_STATUS;
	
	
	@ManyToOne
	@JoinColumn(name="RI_WMS_CODE",foreignKey=@ForeignKey(name="fk_MAR_INTEREST_CLIENT_INVESTOR"))
	@JsonBackReference
	private RE_INVESTOR rE_INVESTOR;
	
	public RE_INVESTOR getrE_INVESTOR() {
		return rE_INVESTOR;
	}
	public void setrE_INVESTOR(RE_INVESTOR rE_INVESTOR) {
		this.rE_INVESTOR = rE_INVESTOR;
	}
	
	public Long getFMD_SNO() {
		return FMD_SNO;
	}
	public void setFMD_SNO(Long fMD_SNO) {
		FMD_SNO = fMD_SNO;
	}
	public Calendar getFID_DATE() {
		return FID_DATE;
	}
	public void setFID_DATE(Calendar fID_DATE) {
		FID_DATE = fID_DATE;
	}
	public Long getFID_CURRENCY() {
		return FID_CURRENCY;
	}
	public void setFID_CURRENCY(Long fID_CURRENCY) {
		FID_CURRENCY = fID_CURRENCY;
	}
	public Long getFID_CLIENT() {
		return FID_CLIENT;
	}
	public void setFID_CLIENT(Long fID_CLIENT) {
		FID_CLIENT = fID_CLIENT;
	}
	public Double getFID_F_SLAB() {
		return FID_F_SLAB;
	}
	public void setFID_F_SLAB(Double fID_F_SLAB) {
		FID_F_SLAB = fID_F_SLAB;
	}
	public Double getFID_T_SLAB() {
		return FID_T_SLAB;
	}
	public void setFID_T_SLAB(Double fID_T_SLAB) {
		FID_T_SLAB = fID_T_SLAB;
	}
	public Double getFID_DR_INT() {
		return FID_DR_INT;
	}
	public void setFID_DR_INT(Double fID_DR_INT) {
		FID_DR_INT = fID_DR_INT;
	}
	public Double getFID_CR_INT() {
		return FID_CR_INT;
	}
	public void setFID_CR_INT(Double fID_CR_INT) {
		FID_CR_INT = fID_CR_INT;
	}
	public Double getFID_PDR_INT() {
		return FID_PDR_INT;
	}
	public void setFID_PDR_INT(Double fID_PDR_INT) {
		FID_PDR_INT = fID_PDR_INT;
	}
	public Double getFID_PCR_INT() {
		return FID_PCR_INT;
	}
	public void setFID_PCR_INT(Double fID_PCR_INT) {
		FID_PCR_INT = fID_PCR_INT;
	}
	public String getFID_UID() {
		return FID_UID;
	}
	public void setFID_UID(String fID_UID) {
		FID_UID = fID_UID;
	}
	public Calendar getFID_IU_DATE() {
		return FID_IU_DATE;
	}
	public void setFID_IU_DATE(Calendar fID_IU_DATE) {
		FID_IU_DATE = fID_IU_DATE;
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
	public String getFID_LEVEL() {
		return FID_LEVEL;
	}
	public void setFID_LEVEL(String fID_LEVEL) {
		FID_LEVEL = fID_LEVEL;
	}
	public Long getFID_CLIENT_TYPE() {
		return FID_CLIENT_TYPE;
	}
	public void setFID_CLIENT_TYPE(Long fID_CLIENT_TYPE) {
		FID_CLIENT_TYPE = fID_CLIENT_TYPE;
	}
	public String getFID_BANK() {
		return FID_BANK;
	}
	public void setFID_BANK(String fID_BANK) {
		FID_BANK = fID_BANK;
	}
	public String getWMS_COMMENTS() {
		return WMS_COMMENTS;
	}
	public void setWMS_COMMENTS(String wMS_COMMENTS) {
		WMS_COMMENTS = wMS_COMMENTS;
	}
	public String getWMS_STATUS() {
		return WMS_STATUS;
	}
	public void setWMS_STATUS(String wMS_STATUS) {
		WMS_STATUS = wMS_STATUS;
	}
}
