package com.fynisys.repository.funds;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.funds.FWMS_SHARE_CLASS;

@Repository
public interface FWMS_SHARE_CLASSRepository extends PagingAndSortingRepository<FWMS_SHARE_CLASS, Long>{
	
	/*
	 * Without paging
	 */
	
	@Query("select t from FWMS_SHARE_CLASS t order by t.WMS_SCLASS_DESC asc ")
	public List<FWMS_SHARE_CLASS> findAllFWMS_SHARE_CLASS();
	
	@Query("select t from FWMS_SHARE_CLASS t where t.WMS_STATUS like 'Approved'  order by t.WMS_SCLASS_DESC asc ")
	public List<FWMS_SHARE_CLASS> findAllAPPROVEDFWMS_SHARE_CLASS();
	
	@Query("select t from FWMS_SHARE_CLASS t where t.WMS_STATUS like 'Not Approved'  order by t.WMS_SCLASS_DESC asc ")
	public List<FWMS_SHARE_CLASS> findAllUNAPPROVEDFWMS_SHARE_CLASS();
	
	/*
	 * With paging
	 */
	
	@Query("select t from FWMS_SHARE_CLASS t order by t.WMS_SCLASS_DESC asc ")
	public Page<FWMS_SHARE_CLASS> findAllFWMS_SHARE_CLASS(Pageable page);
	
	@Query("select t from FWMS_SHARE_CLASS t where t.WMS_STATUS like 'Approved'  order by t.WMS_SCLASS_DESC asc ")
	public Page<FWMS_SHARE_CLASS> findAllAPPROVEDFWMS_SHARE_CLASS(Pageable page);
	
	@Query("select t from FWMS_SHARE_CLASS t where t.WMS_STATUS like 'Not Approved'  order by t.WMS_SCLASS_DESC asc ")
	public Page<FWMS_SHARE_CLASS> findAllUNAPPROVEDFWMS_SHARE_CLASS(Pageable page);
	
	/*
	 * Searching
	 */
	@Query("select t from FWMS_SHARE_CLASS t WHERE lower(t.WMS_SCLASS_DESC) like lower(concat('%',:cname,'%'))  order by t.WMS_SCLASS_DESC asc ")
	public List<FWMS_SHARE_CLASS> findAllFWMS_SHARE_CLASSSearching(@Param("cname")String cname);

	@Query("select t from FWMS_SHARE_CLASS t WHERE  lower(t.WMS_SCLASS_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.WMS_SCLASS_DESC asc ")
	public List<FWMS_SHARE_CLASS> findAllAPPROVEDFWMS_SHARE_CLASSSearching(@Param("cname")String cname);
	
	@Query("select t from FWMS_SHARE_CLASS t WHERE  lower(t.WMS_SCLASS_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.WMS_SCLASS_DESC asc ")
	public List<FWMS_SHARE_CLASS> findAllUNAPPROVEDFWMS_SHARE_CLASSSearching(@Param("cname")String cname);
	
	/*
	 * 
	 * Searching with page
	 */
	
	@Query("select t from FWMS_SHARE_CLASS t WHERE lower(t.WMS_SCLASS_DESC) like lower(concat('%',:cname,'%'))  order by t.WMS_SCLASS_DESC asc ")
	public Page<FWMS_SHARE_CLASS> findAllFWMS_SHARE_CLASSSearching(@Param("cname")String cname,Pageable page);

	@Query("select t from FWMS_SHARE_CLASS t WHERE  lower(t.WMS_SCLASS_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.WMS_SCLASS_DESC asc ")
	public Page<FWMS_SHARE_CLASS> findAllAPPROVEDFWMS_SHARE_CLASSSearching(@Param("cname")String cname,Pageable page);
	
	@Query("select t from FWMS_SHARE_CLASS t WHERE  lower(t.WMS_SCLASS_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.WMS_SCLASS_DESC asc ")
	public Page<FWMS_SHARE_CLASS> findAllUNAPPROVEDFWMS_SHARE_CLASSSearching(@Param("cname")String cname,Pageable page);
}