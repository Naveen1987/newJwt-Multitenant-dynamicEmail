package com.fynisys.model.clienttype;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/*
 * 
CREATE TABLE FUND_CLIENT_DOCUMENTS_TYPE2 ( 
SCD_TYPE  NUMBER (15), 						| Serail No
SCD_DESC  VARCHAR2 (50));						| Name

ALTER TABLE FUND_CLIENT_DOCUMENTS_TYPE2 ADD WMS_COMMENTS VARCHAR2(300);	| Comments
ALTER TABLE FUND_CLIENT_DOCUMENTS_TYPE2 ADD WMS_STATUS VARCHAR2(20);	| Status

ALTER TABLE FUND_CLIENT_DOCUMENTS_TYPE2 ADD WMS_ENTER_UID          VARCHAR2 (20);
ALTER TABLE FUND_CLIENT_DOCUMENTS_TYPE2 ADD   WMS_ENTER_FPC          VARCHAR2 (30); 
ALTER TABLE FUND_CLIENT_DOCUMENTS_TYPE2 ADD   WMS_ENTER_DATE         DATE; 
ALTER TABLE FUND_CLIENT_DOCUMENTS_TYPE2 ADD  WMS_LAST_UPDATE_UID    VARCHAR2 (20); 
ALTER TABLE FUND_CLIENT_DOCUMENTS_TYPE2 ADD   WMS_LAST_FPC           VARCHAR2 (30);
ALTER TABLE FUND_CLIENT_DOCUMENTS_TYPE2 ADD   WMS_LAST_UPDATE_DATE   DATE; 
ALTER TABLE FUND_CLIENT_DOCUMENTS_TYPE2 ADD   WMS_APPROVE_UID        VARCHAR2 (20);
ALTER TABLE FUND_CLIENT_DOCUMENTS_TYPE2 ADD   WMS_APPROVE_FPC        VARCHAR2 (30);
ALTER TABLE FUND_CLIENT_DOCUMENTS_TYPE2 ADD   WMS_APPROVE_DATE       DATE;
 */
@Entity(name="FUND_CLIENT_DOCUMENTS_TYPE2")
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames="SCD_DESC",name="UK_SCD_DESC"),
		@UniqueConstraint(columnNames="SCD_SHORT_DESC",name="UK_SCD_SHORT_DESC"),
	})

public class FUND_CLIENT_DOCUMENTS_TYPE2 implements Serializable {

	
	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		 @Id
		 @GeneratedValue
		 private long SCD_TYPE;//    NUMBER (25)   NOT NULL, | Serial No
		 @Column(length=75)
		 private String SCD_DESC;//  VARCHAR2 (75), 			| Name
		 @Column(length=15)
		 private String SCD_SHORT_DESC;
		 @Column(length=300)
		 private String  WMS_COMMENTS;//         VARCHAR2 (300), 		| Commnets
		 @Column(length=20)
		 private String  WMS_STATUS;    //        VARCHAR2 (20), 			| Status
		 @Column(length=50)
		 private String   WMS_ENTER_UID; //         VARCHAR2 (20), 
		 @Column(length=50)
		 private String   WMS_ENTER_FPC; //         VARCHAR2 (30), 
		 private Calendar   WMS_ENTER_DATE; //        DATE, 
		 @Column(length=50)
		 private String    WMS_LAST_UPDATE_UID; //   VARCHAR2 (20),
		 @Column(length=50)
		 private String   WMS_LAST_FPC; //          VARCHAR2 (30), 
		 private Calendar   WMS_LAST_UPDATE_DATE;//   DATE,
		 @Column(length=50)
		 private String   WMS_APPROVE_UID; //       VARCHAR2 (20),
		 @Column(length=50)
		 private String   WMS_APPROVE_FPC; //       VARCHAR2 (30), 
		 private Calendar   WMS_APPROVE_DATE; //      DATE
		public long getSCD_TYPE() {
			return SCD_TYPE;
		}
		public void setSCD_TYPE(long sCD_TYPE) {
			SCD_TYPE = sCD_TYPE;
		}
		public String getSCD_DESC() {
			return SCD_DESC;
		}
		public void setSCD_DESC(String sCD_DESC) {
			SCD_DESC = sCD_DESC;
		}
		public String getSCD_SHORT_DESC() {
			return SCD_SHORT_DESC;
		}
		public void setSCD_SHORT_DESC(String sCD_SHORT_DESC) {
			SCD_SHORT_DESC = sCD_SHORT_DESC;
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
		public String getWMS_ENTER_UID() {
			return WMS_ENTER_UID;
		}
		public void setWMS_ENTER_UID(String wMS_ENTER_UID) {
			WMS_ENTER_UID = wMS_ENTER_UID;
		}
		public String getWMS_ENTER_FPC() {
			return WMS_ENTER_FPC;
		}
		public void setWMS_ENTER_FPC(String wMS_ENTER_FPC) {
			WMS_ENTER_FPC = wMS_ENTER_FPC;
		}
		public Calendar getWMS_ENTER_DATE() {
			return WMS_ENTER_DATE;
		}
		public void setWMS_ENTER_DATE(Calendar wMS_ENTER_DATE) {
			WMS_ENTER_DATE = wMS_ENTER_DATE;
		}
		public String getWMS_LAST_UPDATE_UID() {
			return WMS_LAST_UPDATE_UID;
		}
		public void setWMS_LAST_UPDATE_UID(String wMS_LAST_UPDATE_UID) {
			WMS_LAST_UPDATE_UID = wMS_LAST_UPDATE_UID;
		}
		public String getWMS_LAST_FPC() {
			return WMS_LAST_FPC;
		}
		public void setWMS_LAST_FPC(String wMS_LAST_FPC) {
			WMS_LAST_FPC = wMS_LAST_FPC;
		}
		public Calendar getWMS_LAST_UPDATE_DATE() {
			return WMS_LAST_UPDATE_DATE;
		}
		public void setWMS_LAST_UPDATE_DATE(Calendar wMS_LAST_UPDATE_DATE) {
			WMS_LAST_UPDATE_DATE = wMS_LAST_UPDATE_DATE;
		}
		public String getWMS_APPROVE_UID() {
			return WMS_APPROVE_UID;
		}
		public void setWMS_APPROVE_UID(String wMS_APPROVE_UID) {
			WMS_APPROVE_UID = wMS_APPROVE_UID;
		}
		public String getWMS_APPROVE_FPC() {
			return WMS_APPROVE_FPC;
		}
		public void setWMS_APPROVE_FPC(String wMS_APPROVE_FPC) {
			WMS_APPROVE_FPC = wMS_APPROVE_FPC;
		}
		public Calendar getWMS_APPROVE_DATE() {
			return WMS_APPROVE_DATE;
		}
		public void setWMS_APPROVE_DATE(Calendar wMS_APPROVE_DATE) {
			WMS_APPROVE_DATE = wMS_APPROVE_DATE;
		}
}
