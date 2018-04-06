package com.fynisys.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.FUND_PASSWORD_HISTORY;

@Repository
public interface FUND_PASSWORD_HISTORYRepository extends PagingAndSortingRepository<FUND_PASSWORD_HISTORY, Long> {
	@Query("select t from FUND_PASSWORD_HISTORY t where t.fund_users.SVC_UID =:uid and t.FPH_PASSWORD=:newpass")
	public FUND_PASSWORD_HISTORY getPass(@Param("uid") String uid,@Param("newpass")String newpass);
	
	@Query("select count(*) from FUND_PASSWORD_HISTORY where SVC_UID=:uid ")
	public int maxCount(@Param("uid")String uid);
	
	@Query("Select FPH_ID from FUND_PASSWORD_HISTORY where FPH_DATE in (select min(FPH_DATE) from FUND_PASSWORD_HISTORY where  SVC_UID=:uid) and SVC_UID=:uid  ")
	public long maxTimestamp(@Param("uid")String uid);
}
