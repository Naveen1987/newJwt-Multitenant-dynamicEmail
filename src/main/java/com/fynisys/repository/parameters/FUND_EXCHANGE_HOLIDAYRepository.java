package com.fynisys.repository.parameters;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fynisys.model.parameters.FUND_EXCHANGE_HOLIDAY;
@Repository
public interface FUND_EXCHANGE_HOLIDAYRepository extends PagingAndSortingRepository<FUND_EXCHANGE_HOLIDAY, Integer> {

}
