package com.fynisys.model.crm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name="FUND_ACCOUNT_LINK")
public class FUND_ACCOUNT_LINK {
	@Id
	@GeneratedValue
private Long RIL_ACCOUNT_CODE;
	@Column(length=100)
private String RIL_MODULE;
	@Column(length=100)
private String RIL_LINK_MCODE;
    @Column(length=100)
private String RIL_LINK_SCODE;
    @Column(length=100)
private String RIL_REMARKS;

    @ManyToOne
    @JoinColumn(name="RI_WMS_CODE",foreignKey=@ForeignKey(name="fk_FUND_RE_INVESTOR"))
    @JsonBackReference
private RE_INVESTOR rE_INVESTOR;
public Long getRIL_ACCOUNT_CODE() {
	return RIL_ACCOUNT_CODE;
}
public void setRIL_ACCOUNT_CODE(Long rIL_ACCOUNT_CODE) {
	RIL_ACCOUNT_CODE = rIL_ACCOUNT_CODE;
}
public String getRIL_MODULE() {
	return RIL_MODULE;
}
public void setRIL_MODULE(String rIL_MODULE) {
	RIL_MODULE = rIL_MODULE;
}
public String getRIL_LINK_MCODE() {
	return RIL_LINK_MCODE;
}
public void setRIL_LINK_MCODE(String rIL_LINK_MCODE) {
	RIL_LINK_MCODE = rIL_LINK_MCODE;
}
public String getRIL_LINK_SCODE() {
	return RIL_LINK_SCODE;
}
public void setRIL_LINK_SCODE(String rIL_LINK_SCODE) {
	RIL_LINK_SCODE = rIL_LINK_SCODE;
}
public String getRIL_REMARKS() {
	return RIL_REMARKS;
}
public void setRIL_REMARKS(String rIL_REMARKS) {
	RIL_REMARKS = rIL_REMARKS;
}
public RE_INVESTOR getRI_WMS_CODE() {
	return rE_INVESTOR;
}
public void setRI_WMS_CODE(RE_INVESTOR rE_INVESTOR) {
	this.rE_INVESTOR = rE_INVESTOR;
}

}
