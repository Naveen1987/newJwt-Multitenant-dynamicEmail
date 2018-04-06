package com.fynisys.repository.parameters;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.parameters.FUND_EXCHANGE_MSTR;

@Repository
public interface FUND_EXCHANGE_MSTRRepositroy extends PagingAndSortingRepository<FUND_EXCHANGE_MSTR, Integer> {
  
	/*
	 * With out paging
	 */
	@Query("select t from FUND_EXCHANGE_MSTR t order by t.SVE_NAME asc ")
	public List<FUND_EXCHANGE_MSTR> findAllExchange();
	
	@Query("select t from FUND_EXCHANGE_MSTR t where t.WMS_STATUS like 'Approved'  order by t.SVE_NAME asc ")
	public List<FUND_EXCHANGE_MSTR> findAllAPPROVEDExchange();
	
	@Query("select t from FUND_EXCHANGE_MSTR t where t.WMS_STATUS like 'Not Approved'  order by t.SVE_NAME asc ")
	public List<FUND_EXCHANGE_MSTR> findAllUNAPPROVEDExchange();
	
	/*
	 * With paging
	 */
	@Query("select t from FUND_EXCHANGE_MSTR t order by t.SVE_NAME asc ")
	public Page<FUND_EXCHANGE_MSTR> findAllExchange(Pageable page);
	
	@Query("select t from FUND_EXCHANGE_MSTR t where t.WMS_STATUS like 'Approved'  order by t.SVE_NAME asc ")
	public Page<FUND_EXCHANGE_MSTR> findAllAPPROVEDExchange(Pageable page);
	
	@Query("select t from FUND_EXCHANGE_MSTR t where t.WMS_STATUS like 'Not Approved'  order by t.SVE_NAME asc ")
	public Page<FUND_EXCHANGE_MSTR> findAllUNAPPROVEDExchange(Pageable page);
	/*
	 * All Exchanges
	 */
	@Query("select t from FUND_EXCHANGE_MSTR t where lower(t.SVE_NAME) like lower(concat('%',:cname,'%'))) order by t.SVE_NAME asc")
	public List<FUND_EXCHANGE_MSTR> findAllExchanges(@Param("cname")String countryname);
	/*
	 * All Exchanges Ids
	 */
	@Query("select t.SVE_CODE from FUND_EXCHANGE_MSTR t where lower(t.SVE_NAME) like lower(concat('%',:cname,'%'))) ")
	public List<Integer> findAllids(@Param("cname")String countryname);
	
}
