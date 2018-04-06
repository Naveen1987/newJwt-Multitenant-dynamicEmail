package com.fynisys.model.parameters;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="FUND_EXCHANGE_HOLIDAY")
public class FUND_EXCHANGE_HOLIDAY implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int FEH_ETYPE;
	private int FEH_D1;
	private int FEH_D2;
	private int FEH_D3;
	private int FEH_D4;
	private int FEH_D5;
	private int FEH_D6;
	private int FEH_D7;
	
	@ManyToOne
	@JoinColumn(name="FEH_EXCHANGE")
	/*
	 * Not giving foreign key name because of conflict
	 */
	@JsonBackReference
	private FUND_EXCHANGE_MSTR FEH_EXCHANGE;
	
	public FUND_EXCHANGE_MSTR getFEH_EXCHANGE() {
		return FEH_EXCHANGE;
	}
	public void setFEH_EXCHANGE(FUND_EXCHANGE_MSTR fEH_EXCHANGE) {
		FEH_EXCHANGE = fEH_EXCHANGE;
	}
	
	
	public int getFEH_ETYPE() {
		return FEH_ETYPE;
	}
	public void setFEH_ETYPE(int fEH_ETYPE) {
		FEH_ETYPE = fEH_ETYPE;
	}
	public int getFEH_D1() {
		return FEH_D1;
	}
	public void setFEH_D1(int fEH_D1) {
		FEH_D1 = fEH_D1;
	}
	public int getFEH_D2() {
		return FEH_D2;
	}
	public void setFEH_D2(int fEH_D2) {
		FEH_D2 = fEH_D2;
	}
	public int getFEH_D3() {
		return FEH_D3;
	}
	public void setFEH_D3(int fEH_D3) {
		FEH_D3 = fEH_D3;
	}
	public int getFEH_D4() {
		return FEH_D4;
	}
	public void setFEH_D4(int fEH_D4) {
		FEH_D4 = fEH_D4;
	}
	public int getFEH_D5() {
		return FEH_D5;
	}
	public void setFEH_D5(int fEH_D5) {
		FEH_D5 = fEH_D5;
	}
	public int getFEH_D6() {
		return FEH_D6;
	}
	public void setFEH_D6(int fEH_D6) {
		FEH_D6 = fEH_D6;
	}
	public int getFEH_D7() {
		return FEH_D7;
	}
	public void setFEH_D7(int fEH_D7) {
		FEH_D7 = fEH_D7;
	}
	@Override
	public String toString() {
		return "FUND_EXCHANGE_HOLIDAY [FEH_EXCHANGE=" + FEH_EXCHANGE + ", FEH_ETYPE=" + FEH_ETYPE + ", FEH_D1=" + FEH_D1
				+ ", FEH_D2=" + FEH_D2 + ", FEH_D3=" + FEH_D3 + ", FEH_D4=" + FEH_D4 + ", FEH_D5=" + FEH_D5
				+ ", FEH_D6=" + FEH_D6 + ", FEH_D7=" + FEH_D7 + "]";
	}
	
	
	
}
