package com.fynisys.country.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.fynisys.model.FUND_COUNTRIES;


public interface FUND_COUNTRIESRepository extends PagingAndSortingRepository<FUND_COUNTRIES, Long> {
	
	@Query("select t from FUND_COUNTRIES t where t.SVC_STATUS like '1' order by t.SVC_NAME asc ")
	public List<FUND_COUNTRIES> findAllCountries();
	
	@Query("select t from FUND_COUNTRIES t where t.SVC_FLOW like '1' and  t.SVC_STATUS like '1' order by t.SVC_NAME asc ")
	public List<FUND_COUNTRIES> findAllAPPROVED();
	
	@Query("select t from FUND_COUNTRIES t where t.SVC_FLOW like '0' and  t.SVC_STATUS like '1'  order by t.SVC_NAME asc")
	public List<FUND_COUNTRIES> findAllUNAPPROVED();
	
	@Query("select t from FUND_COUNTRIES t where lower(t.SVC_NAME) like lower(:countryname) or lower(t.SVC_NAME) like upper(:countryname)")
	public List<FUND_COUNTRIES> getCountryCheck(@Param("countryname")String countryname);
	
	
	/*
	 * With Paging
	 * 
	 */
	
	@Query("select t from FUND_COUNTRIES t where t.SVC_STATUS like '1' order by t.SVC_NAME asc ")
	public Page<FUND_COUNTRIES> findAllCountries(Pageable page);
	
	@Query("select t from FUND_COUNTRIES t where t.SVC_FLOW like '1' and  t.SVC_STATUS like '1' order by t.SVC_NAME asc ")
	public Page<FUND_COUNTRIES> findAllAPPROVED(Pageable page);
	
	@Query("select t from FUND_COUNTRIES t where t.SVC_FLOW like '0' and  t.SVC_STATUS like '1'  order by t.SVC_NAME asc")
	public Page<FUND_COUNTRIES> findAllUNAPPROVED(Pageable page);
	
	/*
	 * All Conuntries
	 */
	@Query("select t from FUND_COUNTRIES t where lower(t.SVC_NAME) like lower(concat('%',:countryname,'%'))   order by t.SVC_NAME asc")
	public List<FUND_COUNTRIES> findAllCountries(@Param("countryname")String countryname);
	/*
	 * Ids
	 */
	
	@Query("select t.SVC_ID from FUND_COUNTRIES t where lower(t.SVC_NAME) like lower(concat('%',:countryname,'%'))")
	public List<Long> findAllIds(@Param("countryname")String countryname);
}
