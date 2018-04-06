package com.fynisys.repository.crm;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fynisys.model.crm.FUND_CLIENT_DOCUMENTS;

@Repository
public interface FUND_CLIENT_DOCUMENTSRepository extends PagingAndSortingRepository<FUND_CLIENT_DOCUMENTS, Long> {
	
}
