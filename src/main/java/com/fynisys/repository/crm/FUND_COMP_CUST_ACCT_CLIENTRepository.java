package com.fynisys.repository.crm;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fynisys.model.crm.FUND_COMP_CUST_ACCT_CLIENT;

@Repository
public interface FUND_COMP_CUST_ACCT_CLIENTRepository extends PagingAndSortingRepository<FUND_COMP_CUST_ACCT_CLIENT, Long> {

}
