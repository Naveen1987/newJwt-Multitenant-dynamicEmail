package com.fynisys.model.funds;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
 * ALTER TABLE FWMS_STOP_LOSS ADD FMD_SL_LEVEL VARCHAR2(30);
-- Margin Level   Stock Name or Sector 

ALTER TABLE FWMS_STOP_LOSS ADD FMD_LEVEL VARCHAR2(30);
--  Level   Client Type ,Client , Fund 

 */
@Entity(name="FWMS_STOP_LOSS")
public class FWMS_STOP_LOSS implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	  @Id
	  @GeneratedValue
	  private long FMD_SNO;             		// NUMBER (10), 
	  private Calendar FMD_DATE;       		//  DATE, 
	  @Column(length=30)
	  private String FMD_SL_LEVEL;
	  @Column(length=30)
	  private String FMD_LEVEL;
	  private long FMD_FUND;
	  private long FMD_CLIENT;
	  private long FMD_C_TYPE;         		//  NUMBER (15), 
	  private int FMD_INDEX_BM;       		//  NUMBER (6),
	  private long FMD_SID;//              NUMBER (15),//Stock or Asset 
	  
	  @Column(precision=15,scale=6)
	  private double FMD_STOP_LOSS;//       NUMBER (15,6),    //Stop/Loss%
	
	  @Column(length=119)
	  private String IV_ENTER_UID;      	//  VARCHAR2 (119), 
	  private Calendar IV_ENTER_DATE;   	//     DATE, 
	  @Column(length=119)
	  private String IV_APPROVE_UID;    	//   VARCHAR2 (119), 
	  private Calendar IV_APPROVE_DATE; 	//     DATE, 
	  @Column(length=119)
	  private String IV_LAST_UPDATE_UID;   	//  VARCHAR2 (119), 
	  private Calendar IV_LAST_UPDATE_DATE;	//  DATE;
	  
	 
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
	public String getFMD_SL_LEVEL() {
		return FMD_SL_LEVEL;
	}
	public void setFMD_SL_LEVEL(String fMD_SL_LEVEL) {
		FMD_SL_LEVEL = fMD_SL_LEVEL;
	}
	public String getFMD_LEVEL() {
		return FMD_LEVEL;
	}
	public void setFMD_LEVEL(String fMD_LEVEL) {
		FMD_LEVEL = fMD_LEVEL;
	}
	public long getFMD_FUND() {
		return FMD_FUND;
	}
	public void setFMD_FUND(long fMD_FUND) {
		FMD_FUND = fMD_FUND;
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
	public long getFMD_SID() {
		return FMD_SID;
	}
	public void setFMD_SID(long fMD_SID) {
		FMD_SID = fMD_SID;
	}
	public double getFMD_STOP_LOSS() {
		return FMD_STOP_LOSS;
	}
	public void setFMD_STOP_LOSS(double fMD_STOP_LOSS) {
		FMD_STOP_LOSS = fMD_STOP_LOSS;
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
		return "FWMS_STOP_LOSS [FMD_SNO=" + FMD_SNO + ", FMD_DATE=" + FMD_DATE + ", FMD_SL_LEVEL=" + FMD_SL_LEVEL
				+ ", FMD_LEVEL=" + FMD_LEVEL + ", FMD_FUND=" + FMD_FUND + ", FMD_CLIENT=" + FMD_CLIENT + ", FMD_C_TYPE="
				+ FMD_C_TYPE + ", FMD_INDEX_BM=" + FMD_INDEX_BM + ", FMD_SID=" + FMD_SID + ", FMD_STOP_LOSS="
				+ FMD_STOP_LOSS + ", IV_ENTER_UID=" + IV_ENTER_UID + ", IV_ENTER_DATE=" + IV_ENTER_DATE
				+ ", IV_APPROVE_UID=" + IV_APPROVE_UID + ", IV_APPROVE_DATE=" + IV_APPROVE_DATE
				+ ", IV_LAST_UPDATE_UID=" + IV_LAST_UPDATE_UID + ", IV_LAST_UPDATE_DATE=" + IV_LAST_UPDATE_DATE
				+ ", WMS_COMMENTS=" + WMS_COMMENTS + ", WMS_STATUS=" + WMS_STATUS + "]";
	}
	  
	
}
