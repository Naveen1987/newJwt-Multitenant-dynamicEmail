package com.fynisys.repository.parameters;



import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fynisys.model.parameters.FUND_CURRENCY_MSTR;


@Repository
public interface FUND_CURRENCY_MSTRRepositroy extends PagingAndSortingRepository<FUND_CURRENCY_MSTR, Integer> {

	/*
	 * Without paging
	 */
	
	@Query("select t from FUND_CURRENCY_MSTR t order by t.SVC_NAME asc ")
	public List<FUND_CURRENCY_MSTR> findAllCurrency();
	
	@Query("select t from FUND_CURRENCY_MSTR t where t.WMS_STATUS like 'Approved'  order by t.SVC_NAME asc ")
	public List<FUND_CURRENCY_MSTR> findAllAPPROVEDCurrency();
	
	@Query("select t from FUND_CURRENCY_MSTR t where t.WMS_STATUS like 'Not Approved'  order by t.SVC_NAME asc ")
	public List<FUND_CURRENCY_MSTR> findAllUNAPPROVEDCurrency();
	
	/*
	 * With paging
	 */
	
	@Query("select t from FUND_CURRENCY_MSTR t order by t.SVC_NAME asc ")
	public Page<FUND_CURRENCY_MSTR> findAllCurrency(Pageable page);
	
	@Query("select t from FUND_CURRENCY_MSTR t where t.WMS_STATUS like 'Approved'  order by t.SVC_NAME asc ")
	public Page<FUND_CURRENCY_MSTR> findAllAPPROVEDCurrency(Pageable page);
	
	@Query("select t from FUND_CURRENCY_MSTR t where t.WMS_STATUS like 'Not Approved'  order by t.SVC_NAME asc ")
	public Page<FUND_CURRENCY_MSTR> findAllUNAPPROVEDCurrency(Pageable page);
	
	/*
	 * Searching
	 */
	@Query("select t from FUND_CURRENCY_MSTR t WHERE lower(t.SVC_NAME) like lower(concat('%',:cname,'%'))  order by t.SVC_NAME asc ")
	public List<FUND_CURRENCY_MSTR> findAllCurrencySearching(@Param("cname")String cname);

	@Query("select t from FUND_CURRENCY_MSTR t WHERE  lower(t.SVC_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.SVC_NAME asc ")
	public List<FUND_CURRENCY_MSTR> findAllAPPROVEDCurrencySearching(@Param("cname")String cname);
	
	@Query("select t from FUND_CURRENCY_MSTR t WHERE  lower(t.SVC_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.SVC_NAME asc ")
	public List<FUND_CURRENCY_MSTR> findAllUNAPPROVEDCurrencySearching(@Param("cname")String cname);
	
	
	/*
	 * All Currencies
	 */
	
	@Query("select t.SVC_CODE from FUND_CURRENCY_MSTR t WHERE  lower(t.SVC_NAME) like lower(concat('%',:cname,'%'))  order by t.SVC_NAME asc ")
	public List<FUND_CURRENCY_MSTR> findAllCurrencies(@Param("cname")String cname);
	
	/*
	 * Ids
	 */
	
	@Query("select t.SVC_CODE from FUND_CURRENCY_MSTR t WHERE  lower(t.SVC_NAME) like lower(concat('%',:cname,'%'))  order by t.SVC_CODE asc ")
	public List<Integer> findAllIds(@Param("cname")String cname);
	
}
