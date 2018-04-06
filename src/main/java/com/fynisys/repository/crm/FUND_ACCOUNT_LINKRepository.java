package com.fynisys.repository.crm;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.crm.FUND_ACCOUNT_LINK;


@Repository
public interface FUND_ACCOUNT_LINKRepository  extends PagingAndSortingRepository<FUND_ACCOUNT_LINK, Long> {

	@Query("SELECT T FROM FUND_ACCOUNT_LINK T WHERE T.rE_INVESTOR.RI_WMS_CODE=:RI_WMS_CODE")
	public List<FUND_ACCOUNT_LINK> getAccountLinks(@Param("RI_WMS_CODE")Long RI_WMS_CODE);
}
