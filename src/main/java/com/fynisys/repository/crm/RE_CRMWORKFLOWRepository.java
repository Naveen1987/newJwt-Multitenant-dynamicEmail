package com.fynisys.repository.crm;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.crm.RE_CRMWORKFLOW;
@Repository
public interface RE_CRMWORKFLOWRepository  extends PagingAndSortingRepository<RE_CRMWORKFLOW, Long> { 

	@Query("select T from RE_CRMWORKFLOW T where T.rE_INVESTOR.RI_WMS_CODE=:code")
	public RE_CRMWORKFLOW getExistingInvestor(@Param("code")Long code);
}
