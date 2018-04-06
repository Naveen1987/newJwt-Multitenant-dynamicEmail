package com.fynisys.repository.orders;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fynisys.model.orders.FWMS_ORDER_BOOK;

@Repository
public interface FWMS_ORDER_BOOKRepository extends PagingAndSortingRepository<FWMS_ORDER_BOOK, Long> {
	}
