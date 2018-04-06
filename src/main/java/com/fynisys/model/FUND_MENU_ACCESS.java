package com.fynisys.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="FUND_MENU_ACCESS")
public class FUND_MENU_ACCESS  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(length=45)
	private String FMA_USERID;
	@Column(length=10)
	private String FMA_MENU;
	@Column(length=5)
	private long FMA_SNODE;
	@Column(length=5)
	private long FMA_MNODE;
	@Column(length=100)
	private String FMA_DESC;
	@Column(length=1)
	private String FMA_FLAG;
	@Column(length=20)
	private String FMA_FILE;
	@Column(length=1)
	private String FMA_VIEW;
	@Column(length=1)
	private String FMA_INSERT; 
	@Column(length=1)
	private String FMA_DELETE; 
	@Column(length=1)
	private String FMA_UPDATE;
	@Column(length=15)
	private String SVC_UID;
	@Column(length=30)
	private String FMA_IMPLEMENT;
	@Column(length=30)
	private String FMA_MENUSUB;
	@Column(length=100)
	private String FMA_ARABIC_DESC;
	public String getFMA_USERID() {
		return FMA_USERID;
	}
	public void setFMA_USERID(String fMA_USERID) {
		FMA_USERID = fMA_USERID;
	}
	public String getFMA_MENU() {
		return FMA_MENU;
	}
	public void setFMA_MENU(String fMA_MENU) {
		FMA_MENU = fMA_MENU;
	}
	public long getFMA_SNODE() {
		return FMA_SNODE;
	}
	public void setFMA_SNODE(long fMA_SNODE) {
		FMA_SNODE = fMA_SNODE;
	}
	public long getFMA_MNODE() {
		return FMA_MNODE;
	}
	public void setFMA_MNODE(long fMA_MNODE) {
		FMA_MNODE = fMA_MNODE;
	}
	public String getFMA_DESC() {
		return FMA_DESC;
	}
	public void setFMA_DESC(String fMA_DESC) {
		FMA_DESC = fMA_DESC;
	}
	public String getFMA_FLAG() {
		return FMA_FLAG;
	}
	public void setFMA_FLAG(String fMA_FLAG) {
		FMA_FLAG = fMA_FLAG;
	}
	public String getFMA_FILE() {
		return FMA_FILE;
	}
	public void setFMA_FILE(String fMA_FILE) {
		FMA_FILE = fMA_FILE;
	}
	public String getFMA_VIEW() {
		return FMA_VIEW;
	}
	public void setFMA_VIEW(String fMA_VIEW) {
		FMA_VIEW = fMA_VIEW;
	}
	public String getFMA_INSERT() {
		return FMA_INSERT;
	}
	public void setFMA_INSERT(String fMA_INSERT) {
		FMA_INSERT = fMA_INSERT;
	}
	public String getFMA_DELETE() {
		return FMA_DELETE;
	}
	public void setFMA_DELETE(String fMA_DELETE) {
		FMA_DELETE = fMA_DELETE;
	}
	public String getFMA_UPDATE() {
		return FMA_UPDATE;
	}
	public void setFMA_UPDATE(String fMA_UPDATE) {
		FMA_UPDATE = fMA_UPDATE;
	}
	public String getSVC_UID() {
		return SVC_UID;
	}
	public void setSVC_UID(String sVC_UID) {
		SVC_UID = sVC_UID;
	}
	public String getFMA_IMPLEMENT() {
		return FMA_IMPLEMENT;
	}
	public void setFMA_IMPLEMENT(String fMA_IMPLEMENT) {
		FMA_IMPLEMENT = fMA_IMPLEMENT;
	}
	public String getFMA_MENUSUB() {
		return FMA_MENUSUB;
	}
	public void setFMA_MENUSUB(String fMA_MENUSUB) {
		FMA_MENUSUB = fMA_MENUSUB;
	}
	public String getFMA_ARABIC_DESC() {
		return FMA_ARABIC_DESC;
	}
	public void setFMA_ARABIC_DESC(String fMA_ARABIC_DESC) {
		FMA_ARABIC_DESC = fMA_ARABIC_DESC;
	}
	@Override
	public String toString() {
		return "FUND_MENU_ACCESS [FMA_USERID=" + FMA_USERID + ", FMA_MENU=" + FMA_MENU + ", FMA_SNODE=" + FMA_SNODE
				+ ", FMA_MNODE=" + FMA_MNODE + ", FMA_DESC=" + FMA_DESC + ", FMA_FLAG=" + FMA_FLAG + ", FMA_FILE="
				+ FMA_FILE + ", FMA_VIEW=" + FMA_VIEW + ", FMA_INSERT=" + FMA_INSERT + ", FMA_DELETE=" + FMA_DELETE
				+ ", FMA_UPDATE=" + FMA_UPDATE + ", SVC_UID=" + SVC_UID + ", FMA_IMPLEMENT=" + FMA_IMPLEMENT
				+ ", FMA_MENUSUB=" + FMA_MENUSUB + ", FMA_ARABIC_DESC=" + FMA_ARABIC_DESC + "]";
	}
	
}
