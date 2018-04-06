package com.fynisys.repository.people;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.people.FUND_BROKER_MSTR;


@Repository
public interface FUND_BROKER_MSTRRepository extends PagingAndSortingRepository<FUND_BROKER_MSTR, Long>{

	/*
	 * Without paging
	 */
	
	@Query("select t from FUND_BROKER_MSTR t order by t.SVB_NAME asc ")
	public List<FUND_BROKER_MSTR> findAllBroker();
	
	@Query("select t from FUND_BROKER_MSTR t where t.WMS_STATUS like 'Approved'  order by t.SVB_NAME asc ")
	public List<FUND_BROKER_MSTR> findAllAPPROVEDBroker();
	
	@Query("select t from FUND_BROKER_MSTR t where t.WMS_STATUS like 'Not Approved'  order by t.SVB_NAME asc ")
	public List<FUND_BROKER_MSTR> findAllUNAPPROVEDBroker();
	
	/*
	 * With paging
	 */
	
	@Query("select t from FUND_BROKER_MSTR t order by t.SVB_NAME asc ")
	public Page<FUND_BROKER_MSTR> findAllBroker(Pageable page);
	
	@Query("select t from FUND_BROKER_MSTR t where t.WMS_STATUS like 'Approved'  order by t.SVB_NAME asc ")
	public Page<FUND_BROKER_MSTR> findAllAPPROVEDBroker(Pageable page);
	
	@Query("select t from FUND_BROKER_MSTR t where t.WMS_STATUS like 'Not Approved'  order by t.SVB_NAME asc ")
	public Page<FUND_BROKER_MSTR> findAllUNAPPROVEDBroker(Pageable page);
	
	/*
	 * Searching
	 */
	@Query("select t from FUND_BROKER_MSTR t WHERE lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  order by t.SVB_NAME asc ")
	public List<FUND_BROKER_MSTR> findAllBrokerSearching(@Param("cname")String cname);

	@Query("select t from FUND_BROKER_MSTR t WHERE  lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.SVB_NAME asc ")
	public List<FUND_BROKER_MSTR> findAllAPPROVEDBrokerSearching(@Param("cname")String cname);
	
	@Query("select t from FUND_BROKER_MSTR t WHERE  lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.SVB_NAME asc ")
	public List<FUND_BROKER_MSTR> findAllUNAPPROVEDBrokerSearching(@Param("cname")String cname);
	
	
	@Query("select t from FUND_BROKER_MSTR t WHERE lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  order by t.SVB_NAME asc ")
	public Page<FUND_BROKER_MSTR> findAllBrokerSearchingPage(@Param("cname")String cname,Pageable page);

	@Query("select t from FUND_BROKER_MSTR t WHERE  lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.SVB_NAME asc ")
	public Page<FUND_BROKER_MSTR> findAllAPPROVEDBrokerSearchingPage(@Param("cname")String cname,Pageable page);
	
	@Query("select t from FUND_BROKER_MSTR t WHERE  lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.SVB_NAME asc ")
	public Page<FUND_BROKER_MSTR> findAllUNAPPROVEDBrokerSearchingPage(@Param("cname")String cname,Pageable page);
	
	/*
	 * Search IDs
	 */
	@Query("select t.SVB_CODE from FUND_BROKER_MSTR t WHERE lower(t.SVB_NAME) like lower(concat('%',:cname,'%')) order by t.SVB_CODE")
	public List<Long> find_Ids_BrokerSearching(@Param("cname")String cname);

}
