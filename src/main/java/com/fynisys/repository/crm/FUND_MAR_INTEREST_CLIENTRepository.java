package com.fynisys.repository.crm;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fynisys.model.crm.FUND_MAR_INTEREST_CLIENT;

@Repository
public interface FUND_MAR_INTEREST_CLIENTRepository extends PagingAndSortingRepository<FUND_MAR_INTEREST_CLIENT, Long> {
	
}
