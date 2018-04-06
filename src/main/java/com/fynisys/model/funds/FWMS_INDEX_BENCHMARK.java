package com.fynisys.model.funds;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/*
ALTER TABLE FWMS_INDEX_BENCHMARK ADD FMD_LEVEL VARCHAR2(30);
--  Level   Client Type ,Client , Fund 
FMD_SNO Serial No Auto 
FMD_DATE Effect Date 
FMD_LEVEL	Client Type, Client , Fund (Default Value will be Fund) 
FMD_FUND	FUND Name 
FMD_CLIENT	Client Name 
FMD_C_TYPE Client Type 
FMD_INDEX_BM Index Name Below fields are standard 
WMS_COMMENTS 
WMS_STATUS 
IV_ENTER_UID 
IV_ENTER_DATE 
IV_APPROVE_UID 
IV_APPROVE_DATE 
IV_LAST_UPDATE_UID 
IV_LAST_UPDATE_DATE 
 */
/*
 * Duplicate value
 * Effect Date ,Fund Name,Level,Index List ,Client Name ,Client Type 
 * "FMD_DATE","FMD_FUND","FMD_LEVEL","FMD_CLIENT","FMD_C_TYPE","FMD_INDEX_BM"
 * 
 */
@Entity(name="FWMS_INDEX_BENCHMARK")
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames= {"FMD_DATE","FMD_FUND","FMD_LEVEL","FMD_CLIENT","FMD_C_TYPE","FMD_INDEX_BM"},
				name="FWMS_INDEX_BENCHMARK_DUPLICATE_VALUE")
})
public class FWMS_INDEX_BENCHMARK implements Serializable{
	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  @Id
	  @GeneratedValue
	  private long FMD_SNO;               	//NUMBER (10), 
	  private Calendar FMD_DATE;          	//DATE, 
	  private long FMD_FUND;
	  @Column(length=30)
	  private String FMD_LEVEL;
	  private long FMD_CLIENT;
	  private long FMD_C_TYPE;           	//NUMBER (15), 
	  private int FMD_INDEX_BM;
	 /*
	  * Standard Fields
	  */
	  @Column(length=119)
	  private String IV_ENTER_UID;         	//VARCHAR2 (119), 
	  private Calendar IV_ENTER_DATE;       //DATE, 
	  @Column(length=119)
	  private String IV_APPROVE_UID;       	//VARCHAR2 (119), 
	  private Calendar IV_APPROVE_DATE;     //DATE,
	  @Column(length=119)
	  private String IV_LAST_UPDATE_UID;   	//VARCHAR2 (119),
	  private Calendar IV_LAST_UPDATE_DATE; //DATE	  
	  @Column(length=300)
	  private String WMS_COMMENTS;
	  @Column(length=20)
	  private String WMS_STATUS;
	public long getFMD_SNO() {
		return FMD_SNO;
	}
	public void setFMD_SNO(long fMD_SNO) {
		FMD_SNO = fMD_SNO;
	}
	public Calendar getFMD_DATE() {
		return FMD_DATE;
	}
	public void setFMD_DATE(Calendar fMD_DATE) {
		FMD_DATE = fMD_DATE;
	}
	public long getFMD_FUND() {
		return FMD_FUND;
	}
	public void setFMD_FUND(long fMD_FUND) {
		FMD_FUND = fMD_FUND;
	}
	public String getFMD_LEVEL() {
		return FMD_LEVEL;
	}
	public void setFMD_LEVEL(String fMD_LEVEL) {
		FMD_LEVEL = fMD_LEVEL;
	}
	public long getFMD_CLIENT() {
		return FMD_CLIENT;
	}
	public void setFMD_CLIENT(long fMD_CLIENT) {
		FMD_CLIENT = fMD_CLIENT;
	}
	public long getFMD_C_TYPE() {
		return FMD_C_TYPE;
	}
	public void setFMD_C_TYPE(long fMD_C_TYPE) {
		FMD_C_TYPE = fMD_C_TYPE;
	}
	public int getFMD_INDEX_BM() {
		return FMD_INDEX_BM;
	}
	public void setFMD_INDEX_BM(int fMD_INDEX_BM) {
		FMD_INDEX_BM = fMD_INDEX_BM;
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
	@Override
	public String toString() {
		return "FWMS_INDEX_BENCHMARK [FMD_SNO=" + FMD_SNO + ", FMD_DATE=" + FMD_DATE + ", FMD_FUND=" + FMD_FUND
				+ ", FMD_LEVEL=" + FMD_LEVEL + ", FMD_CLIENT=" + FMD_CLIENT + ", FMD_C_TYPE=" + FMD_C_TYPE
				+ ", FMD_INDEX_BM=" + FMD_INDEX_BM + ", IV_ENTER_UID=" + IV_ENTER_UID + ", IV_ENTER_DATE="
				+ IV_ENTER_DATE + ", IV_APPROVE_UID=" + IV_APPROVE_UID + ", IV_APPROVE_DATE=" + IV_APPROVE_DATE
				+ ", IV_LAST_UPDATE_UID=" + IV_LAST_UPDATE_UID + ", IV_LAST_UPDATE_DATE=" + IV_LAST_UPDATE_DATE
				+ ", WMS_COMMENTS=" + WMS_COMMENTS + ", WMS_STATUS=" + WMS_STATUS + "]";
	}

}
