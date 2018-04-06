package com.fynisys.companyprofile.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.fynisys.companyprofile.model.FWMS_COMPANY_DEFAULT;

public interface FWMS_COMPANY_DEFAULTRepository extends PagingAndSortingRepository<FWMS_COMPANY_DEFAULT, Long> {

	@Query("SELECT T FROM FWMS_COMPANY_DEFAULT T WHERE T.WMS_COMPANY_NAME LIKE :COMPNAME")
	public FWMS_COMPANY_DEFAULT getInfo(@Param("COMPNAME") String FWMS_REPORT_PARA);
	
}
