package com.fynisys.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.FUND_USERS_FUNDS;

@Repository
public interface FUND_USERS_FUNDSRepository extends PagingAndSortingRepository<FUND_USERS_FUNDS, String> {

	@Query("select t from FUND_USERS_FUNDS t where t.FFID.FID like :fid and t.SVC_UID like :uuid")
	public FUND_USERS_FUNDS getFund(@Param("fid")String fid,@Param("uuid")String uuid); 
}
