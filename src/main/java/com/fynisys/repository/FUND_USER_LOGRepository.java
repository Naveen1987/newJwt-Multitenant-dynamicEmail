package com.fynisys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.FUND_USER_LOG;


@Repository
public interface FUND_USER_LOGRepository extends PagingAndSortingRepository<FUND_USER_LOG, String> {
	@Query("SELECT T FROM FUND_USER_LOG T where T.SVL_SCREEN like :screenid order by T.SVL_DATE DESC")
	public List<FUND_USER_LOG> getLogs(@Param("screenid")String screenid);
	
//	@Query("SELECT T FROM FUND_USER_LOG T where T.SVL_SCREEN like :screenid and T.SVL_TTYPE like :transactiontype order by T.SVL_DATE DESC")
//	public List<FUND_USER_LOG> getLogs(@Param("screenid")String screenid,@Param("transactiontype") String tt);

	
	@Query("SELECT T FROM FUND_USER_LOG T where T.SNO like :SNO AND  T.SVL_SCREEN like :screenid order by T.SVL_DATE DESC")
	public List<FUND_USER_LOG> getLogs(@Param("SNO")String SNO,@Param("screenid")String screenid);
		
}
