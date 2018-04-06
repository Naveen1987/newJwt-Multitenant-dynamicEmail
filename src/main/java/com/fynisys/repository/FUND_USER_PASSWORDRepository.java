package com.fynisys.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.FUND_USER_PASSWORD;
@Repository
public interface FUND_USER_PASSWORDRepository extends PagingAndSortingRepository<FUND_USER_PASSWORD, String> {

	
	@Query("select t from FUND_USER_PASSWORD t where t.FOPD =:otp and SVC_UID =:uid")
	public FUND_USER_PASSWORD findOptValid(@Param("otp")String otp,@Param("uid") String uid);
	
}
