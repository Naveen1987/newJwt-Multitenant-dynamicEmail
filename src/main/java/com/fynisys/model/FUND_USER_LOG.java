package com.fynisys.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity(name="FUND_USER_LOG")
public class FUND_USER_LOG  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 private int SVL_CODE;
	 @Column(length=80)
	 private String SVC_UID;
	 @Column(length=30)
	 private String SVL_USERID;
	 @Column(length=3000)
	 private String SVL_DESC;
	 @Column(length=30)
	 private String SVL_SCREEN;  
	 @Column(length=20)
	 private String SVL_TTYPE;
	 private Calendar SVL_DATE;
	 //for Item info
	 private String SNO;
	 
	 public String getSNO() {
		return SNO;
	}
	public void setSNO(String sNO) {
		SNO = sNO;
	}
	
	public int getSVL_CODE() {
		return SVL_CODE;
	}
	public void setSVL_CODE(int sVL_CODE) {
		SVL_CODE = sVL_CODE;
	}
	public String getSVC_UID() {
		return SVC_UID;
	 }
	 public void setSVC_UID(String sVC_UID) {
		SVC_UID = sVC_UID;
	 }
	 public String getSVL_USERID() {
		return SVL_USERID;
	 }
	 public void setSVL_USERID(String sVL_USERID) {
		SVL_USERID = sVL_USERID;
	 }
	 public String getSVL_DESC() {
		return SVL_DESC;
	 }
	 public void setSVL_DESC(String sVL_DESC) {
		SVL_DESC = sVL_DESC;
	 }
	 public String getSVL_SCREEN() {
		return SVL_SCREEN;
	 }
	 public void setSVL_SCREEN(String sVL_SCREEN) {
		SVL_SCREEN = sVL_SCREEN;
	 }
	 public String getSVL_TTYPE() {
		return SVL_TTYPE;
	 }
	 public void setSVL_TTYPE(String sVL_TTYPE) {
		SVL_TTYPE = sVL_TTYPE;
	 }
	 public Calendar getSVL_DATE() {
		return SVL_DATE;
	 }
	 public void setSVL_DATE(Calendar sVL_DATE) {
		SVL_DATE = sVL_DATE;
	 }
	@Override
	public String toString() {
		return "FUND_USER_LOG [SVC_UID=" + SVC_UID + ", SVL_USERID=" + SVL_USERID + ", SVL_DESC=" + SVL_DESC
				+ ", SVL_SCREEN=" + SVL_SCREEN + ", SVL_TTYPE=" + SVL_TTYPE + ", SVL_DATE=" + SVL_DATE + "]";
	} 
	
}
