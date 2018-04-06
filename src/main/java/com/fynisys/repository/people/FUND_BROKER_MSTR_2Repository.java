package com.fynisys.repository.people;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.people.FUND_BROKER_MSTR_2;
@Repository
public interface FUND_BROKER_MSTR_2Repository extends PagingAndSortingRepository<FUND_BROKER_MSTR_2, Long> {


	/*
	 * Without paging
	 */
	
	@Query("select t from FUND_BROKER_MSTR_2 t order by t.SVB_NAME asc ")
	public List<FUND_BROKER_MSTR_2> findAllCustodian();
	
	@Query("select t from FUND_BROKER_MSTR_2 t where t.WMS_STATUS like 'Approved'  order by t.SVB_NAME asc ")
	public List<FUND_BROKER_MSTR_2> findAllAPPROVEDCustodian();
	
	@Query("select t from FUND_BROKER_MSTR_2 t where t.WMS_STATUS like 'Not Approved'  order by t.SVB_NAME asc ")
	public List<FUND_BROKER_MSTR_2> findAllUNAPPROVEDCustodian();
	
	/*
	 * With paging
	 */
	
	@Query("select t from FUND_BROKER_MSTR_2 t order by t.SVB_NAME asc ")
	public Page<FUND_BROKER_MSTR_2> findAllCustodian(Pageable page);
	
	@Query("select t from FUND_BROKER_MSTR_2 t where t.WMS_STATUS like 'Approved'  order by t.SVB_NAME asc ")
	public Page<FUND_BROKER_MSTR_2> findAllAPPROVEDCustodian(Pageable page);
	
	@Query("select t from FUND_BROKER_MSTR_2 t where t.WMS_STATUS like 'Not Approved'  order by t.SVB_NAME asc ")
	public Page<FUND_BROKER_MSTR_2> findAllUNAPPROVEDCustodian(Pageable page);
	
	/*
	 * Searching
	 */
	@Query("select t from FUND_BROKER_MSTR_2 t WHERE lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  order by t.SVB_NAME asc ")
	public List<FUND_BROKER_MSTR_2> findAllCustodianSearching(@Param("cname")String cname);

	@Query("select t from FUND_BROKER_MSTR_2 t WHERE  lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.SVB_NAME asc ")
	public List<FUND_BROKER_MSTR_2> findAllAPPROVEDCustodianSearching(@Param("cname")String cname);
	
	@Query("select t from FUND_BROKER_MSTR_2 t WHERE  lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.SVB_NAME asc ")
	public List<FUND_BROKER_MSTR_2> findAllUNAPPROVEDCustodianSearching(@Param("cname")String cname);
	
	
	@Query("select t from FUND_BROKER_MSTR_2 t WHERE lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  order by t.SVB_NAME asc ")
	public Page<FUND_BROKER_MSTR_2> findAllCustodianSearchingPage(@Param("cname")String cname,Pageable page);

	@Query("select t from FUND_BROKER_MSTR_2 t WHERE  lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.SVB_NAME asc ")
	public Page<FUND_BROKER_MSTR_2> findAllAPPROVEDCustodianSearchingPage(@Param("cname")String cname,Pageable page);
	
	@Query("select t from FUND_BROKER_MSTR_2 t WHERE  lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.SVB_NAME asc ")
	public Page<FUND_BROKER_MSTR_2> findAllUNAPPROVEDCustodianSearchingPage(@Param("cname")String cname,Pageable page);
	
/*
 * Sercahing IDs
 */
	@Query("select t.SVB_CODE from FUND_BROKER_MSTR_2 t WHERE lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))")
	public List<Long> find_Ids_CustodianSearching(@Param("cname")String cname);

}
