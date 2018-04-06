package com.fynisys.repository.people;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.people.FUND_ACCOUNT_MSTR;

@Repository
public interface FUND_ACCOUNT_MSTRRepository extends PagingAndSortingRepository<FUND_ACCOUNT_MSTR, Long> {

	/*
	 * Without paging
	 */
	
	@Query("select t from  FUND_ACCOUNT_MSTR t order by t.SVA_NAME asc ")
	public List< FUND_ACCOUNT_MSTR> findAllFundAccount();
	
	@Query("select t from  FUND_ACCOUNT_MSTR t where t.WMS_STATUS like 'Approved'  order by t.SVA_NAME asc ")
	public List< FUND_ACCOUNT_MSTR> findAllAPPROVEDFundAccount();
	
	@Query("select t from  FUND_ACCOUNT_MSTR t where t.WMS_STATUS like 'Not Approved'  order by t.SVA_NAME asc ")
	public List< FUND_ACCOUNT_MSTR> findAllUNAPPROVEDFundAccount();
	
	/*
	 * With paging
	 */
	
	@Query("select t from  FUND_ACCOUNT_MSTR t order by t.SVA_NAME asc ")
	public Page< FUND_ACCOUNT_MSTR> findAllFundAccount(Pageable page);
	
	@Query("select t from  FUND_ACCOUNT_MSTR t where t.WMS_STATUS like 'Approved'  order by t.SVA_NAME asc ")
	public Page< FUND_ACCOUNT_MSTR> findAllAPPROVEDFundAccount(Pageable page);
	
	@Query("select t from  FUND_ACCOUNT_MSTR t where t.WMS_STATUS like 'Not Approved'  order by t.SVA_NAME asc ")
	public Page< FUND_ACCOUNT_MSTR> findAllUNAPPROVEDFundAccount(Pageable page);
	
	/*
	 * Searching
	 */
	@Query("select t from  FUND_ACCOUNT_MSTR t WHERE lower(t.SVA_NAME) like lower(concat('%',:cname,'%'))  order by t.SVA_NAME asc ")
	public List< FUND_ACCOUNT_MSTR> findAllFundAccountSearching(@Param("cname")String cname);

	@Query("select t from  FUND_ACCOUNT_MSTR t WHERE  lower(t.SVA_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.SVA_NAME asc ")
	public List< FUND_ACCOUNT_MSTR> findAllAPPROVEDFundAccountSearching(@Param("cname")String cname);
	
	@Query("select t from  FUND_ACCOUNT_MSTR t WHERE  lower(t.SVA_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.SVA_NAME asc ")
	public List< FUND_ACCOUNT_MSTR> findAllUNAPPROVEDFundAccountSearching(@Param("cname")String cname);
	
	
	@Query("select t from  FUND_ACCOUNT_MSTR t WHERE lower(t.SVA_NAME) like lower(concat('%',:cname,'%'))  order by t.SVA_NAME asc ")
	public Page< FUND_ACCOUNT_MSTR> findAllFundAccountSearchingPage(@Param("cname")String cname,Pageable page);

	@Query("select t from  FUND_ACCOUNT_MSTR t WHERE  lower(t.SVA_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.SVA_NAME asc ")
	public Page< FUND_ACCOUNT_MSTR> findAllAPPROVEDFundAccountSearchingPage(@Param("cname")String cname,Pageable page);
	
	@Query("select t from  FUND_ACCOUNT_MSTR t WHERE  lower(t.SVA_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.SVA_NAME asc ")
	public Page< FUND_ACCOUNT_MSTR> findAllUNAPPROVEDFundAccountSearchingPage(@Param("cname")String cname,Pageable page);
	
	/*
	 * Searching with parameter
	 */

	@Query("select t.SVA_CODE from  FUND_ACCOUNT_MSTR t WHERE lower(t.SVA_NAME) like lower(concat('%',:cname,'%'))")
	public List<Long> find_Ids_FundAccountSearching(@Param("cname")String cname);
}
