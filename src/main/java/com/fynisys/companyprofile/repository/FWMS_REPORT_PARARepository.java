package com.fynisys.companyprofile.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.companyprofile.model.FWMS_REPORT_PARA;

@Repository
public interface FWMS_REPORT_PARARepository extends PagingAndSortingRepository<FWMS_REPORT_PARA, Long> {
	@Query("SELECT T FROM FWMS_REPORT_PARA T WHERE T.WMS_COMPANY_NAME LIKE :COMPNAME")
	public FWMS_REPORT_PARA getInfo(@Param("COMPNAME") String FWMS_REPORT_PARA);
}
