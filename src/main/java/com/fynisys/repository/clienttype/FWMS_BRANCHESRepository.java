package com.fynisys.repository.clienttype;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fynisys.model.clienttype.FWMS_BRANCHES;

@Repository
public interface FWMS_BRANCHESRepository extends PagingAndSortingRepository<FWMS_BRANCHES, Long>{
	/*
	 * Without paging
	 */
	
	@Query("select t from FWMS_BRANCHES t order by t.WMS_BRAN_DESC asc ")
	public List<FWMS_BRANCHES> findAllFWMS_BRANCHES();
	
	@Query("select t from FWMS_BRANCHES t where t.WMS_STATUS like 'Approved'  order by t.WMS_BRAN_DESC asc ")
	public List<FWMS_BRANCHES> findAllAPPROVEDFWMS_BRANCHES();
	
	@Query("select t from FWMS_BRANCHES t where t.WMS_STATUS like 'Not Approved'  order by t.WMS_BRAN_DESC asc ")
	public List<FWMS_BRANCHES> findAllUNAPPROVEDFWMS_BRANCHES();
	
	/*
	 * With paging
	 */
	
	@Query("select t from FWMS_BRANCHES t order by t.WMS_BRAN_DESC asc ")
	public Page<FWMS_BRANCHES> findAllFWMS_BRANCHES(Pageable page);
	
	@Query("select t from FWMS_BRANCHES t where t.WMS_STATUS like 'Approved'  order by t.WMS_BRAN_DESC asc ")
	public Page<FWMS_BRANCHES> findAllAPPROVEDFWMS_BRANCHES(Pageable page);
	
	@Query("select t from FWMS_BRANCHES t where t.WMS_STATUS like 'Not Approved'  order by t.WMS_BRAN_DESC asc ")
	public Page<FWMS_BRANCHES> findAllUNAPPROVEDFWMS_BRANCHES(Pageable page);
	
	/*
	 * Searching
	 */
	@Query("select t from FWMS_BRANCHES t WHERE lower(t.WMS_BRAN_DESC) like lower(concat('%',:cname,'%'))  order by t.WMS_BRAN_DESC asc ")
	public List<FWMS_BRANCHES> findAllFWMS_BRANCHESSearching(@Param("cname")String cname);

	@Query("select t from FWMS_BRANCHES t WHERE  lower(t.WMS_BRAN_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.WMS_BRAN_DESC asc ")
	public List<FWMS_BRANCHES> findAllAPPROVEDFWMS_BRANCHESSearching(@Param("cname")String cname);
	
	@Query("select t from FWMS_BRANCHES t WHERE  lower(t.WMS_BRAN_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.WMS_BRAN_DESC asc ")
	public List<FWMS_BRANCHES> findAllUNAPPROVEDFWMS_BRANCHESSearching(@Param("cname")String cname);
	
	/*
	 * 
	 * Searching with page
	 */
	
	@Query("select t from FWMS_BRANCHES t WHERE lower(t.WMS_BRAN_DESC) like lower(concat('%',:cname,'%'))  order by t.WMS_BRAN_DESC asc ")
	public Page<FWMS_BRANCHES> findAllFWMS_BRANCHESSearching(@Param("cname")String cname,Pageable page);

	@Query("select t from FWMS_BRANCHES t WHERE  lower(t.WMS_BRAN_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.WMS_BRAN_DESC asc ")
	public Page<FWMS_BRANCHES> findAllAPPROVEDFWMS_BRANCHESSearching(@Param("cname")String cname,Pageable page);
	
	@Query("select t from FWMS_BRANCHES t WHERE  lower(t.WMS_BRAN_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.WMS_BRAN_DESC asc ")
	public Page<FWMS_BRANCHES> findAllUNAPPROVEDFWMS_BRANCHESSearching(@Param("cname")String cname,Pageable page);
	
}