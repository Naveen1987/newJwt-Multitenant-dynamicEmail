package com.fynisys.repository.clienttype;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.clienttype.FUND_CLIENT_DOCUMENTS_TYPE2;


@Repository
public interface FUND_CLIENT_DOCUMENTS_TYPE2Repository extends PagingAndSortingRepository<FUND_CLIENT_DOCUMENTS_TYPE2, Long>{
	
	/*
	 * Without paging
	 */
	
	@Query("select t from FUND_CLIENT_DOCUMENTS_TYPE2 t order by t.SCD_DESC asc ")
	public List<FUND_CLIENT_DOCUMENTS_TYPE2> findAllFUND_CLIENT_DOCUMENTS_TYPE2();
	
	@Query("select t from FUND_CLIENT_DOCUMENTS_TYPE2 t where t.WMS_STATUS like 'Approved'  order by t.SCD_DESC asc ")
	public List<FUND_CLIENT_DOCUMENTS_TYPE2> findAllAPPROVEDFUND_CLIENT_DOCUMENTS_TYPE2();
	
	@Query("select t from FUND_CLIENT_DOCUMENTS_TYPE2 t where t.WMS_STATUS like 'Not Approved'  order by t.SCD_DESC asc ")
	public List<FUND_CLIENT_DOCUMENTS_TYPE2> findAllUNAPPROVEDFUND_CLIENT_DOCUMENTS_TYPE2();
	
	/*
	 * With paging
	 */
	
	@Query("select t from FUND_CLIENT_DOCUMENTS_TYPE2 t order by t.SCD_DESC asc ")
	public Page<FUND_CLIENT_DOCUMENTS_TYPE2> findAllFUND_CLIENT_DOCUMENTS_TYPE2(Pageable page);
	
	@Query("select t from FUND_CLIENT_DOCUMENTS_TYPE2 t where t.WMS_STATUS like 'Approved'  order by t.SCD_DESC asc ")
	public Page<FUND_CLIENT_DOCUMENTS_TYPE2> findAllAPPROVEDFUND_CLIENT_DOCUMENTS_TYPE2(Pageable page);
	
	@Query("select t from FUND_CLIENT_DOCUMENTS_TYPE2 t where t.WMS_STATUS like 'Not Approved'  order by t.SCD_DESC asc ")
	public Page<FUND_CLIENT_DOCUMENTS_TYPE2> findAllUNAPPROVEDFUND_CLIENT_DOCUMENTS_TYPE2(Pageable page);
	
	/*
	 * Searching
	 */
	@Query("select t from FUND_CLIENT_DOCUMENTS_TYPE2 t WHERE lower(t.SCD_DESC) like lower(concat('%',:cname,'%'))  order by t.SCD_DESC asc ")
	public List<FUND_CLIENT_DOCUMENTS_TYPE2> findAllFUND_CLIENT_DOCUMENTS_TYPE2Searching(@Param("cname")String cname);

	@Query("select t from FUND_CLIENT_DOCUMENTS_TYPE2 t WHERE  lower(t.SCD_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.SCD_DESC asc ")
	public List<FUND_CLIENT_DOCUMENTS_TYPE2> findAllAPPROVEDFUND_CLIENT_DOCUMENTS_TYPE2Searching(@Param("cname")String cname);
	
	@Query("select t from FUND_CLIENT_DOCUMENTS_TYPE2 t WHERE  lower(t.SCD_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.SCD_DESC asc ")
	public List<FUND_CLIENT_DOCUMENTS_TYPE2> findAllUNAPPROVEDFUND_CLIENT_DOCUMENTS_TYPE2Searching(@Param("cname")String cname);
	
	/*
	 * 
	 * Searching with page
	 */
	
	@Query("select t from FUND_CLIENT_DOCUMENTS_TYPE2 t WHERE lower(t.SCD_DESC) like lower(concat('%',:cname,'%'))  order by t.SCD_DESC asc ")
	public Page<FUND_CLIENT_DOCUMENTS_TYPE2> findAllFUND_CLIENT_DOCUMENTS_TYPE2Searching(@Param("cname")String cname,Pageable page);

	@Query("select t from FUND_CLIENT_DOCUMENTS_TYPE2 t WHERE  lower(t.SCD_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.SCD_DESC asc ")
	public Page<FUND_CLIENT_DOCUMENTS_TYPE2> findAllAPPROVEDFUND_CLIENT_DOCUMENTS_TYPE2Searching(@Param("cname")String cname,Pageable page);
	
	@Query("select t from FUND_CLIENT_DOCUMENTS_TYPE2 t WHERE  lower(t.SCD_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.SCD_DESC asc ")
	public Page<FUND_CLIENT_DOCUMENTS_TYPE2> findAllUNAPPROVEDFUND_CLIENT_DOCUMENTS_TYPE2Searching(@Param("cname")String cname,Pageable page);
	
	/*
	 *Other filtres 
	 */
	@Query("select t.SCD_TYPE from FUND_CLIENT_DOCUMENTS_TYPE2 t WHERE lower(t.SCD_DESC) like lower(concat('%',:cname,'%'))  order by t.SCD_TYPE asc ")
	public List<Long> find_Ids_FUND_CLIENT_DOCUMENTS_TYPE2(@Param("cname")String cname);

}
