package com.fynisys.repository.clienttype;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.clienttype.FWMS_RELATIONSHIP;
@Repository
public interface FWMS_RELATIONSHIPRepository extends PagingAndSortingRepository<FWMS_RELATIONSHIP, Long>{
	
	/*
	 * Without paging
	 */
	
	@Query("select t from FWMS_RELATIONSHIP t order by t.WMS_RELATIONSHIP_DESC asc ")
	public List<FWMS_RELATIONSHIP> findAllFWMS_RELATIONSHIP();
	
	@Query("select t from FWMS_RELATIONSHIP t where t.WMS_STATUS like 'Approved'  order by t.WMS_RELATIONSHIP_DESC asc ")
	public List<FWMS_RELATIONSHIP> findAllAPPROVEDFWMS_RELATIONSHIP();
	
	@Query("select t from FWMS_RELATIONSHIP t where t.WMS_STATUS like 'Not Approved'  order by t.WMS_RELATIONSHIP_DESC asc ")
	public List<FWMS_RELATIONSHIP> findAllUNAPPROVEDFWMS_RELATIONSHIP();
	
	/*
	 * With paging
	 */
	
	@Query("select t from FWMS_RELATIONSHIP t order by t.WMS_RELATIONSHIP_DESC asc ")
	public Page<FWMS_RELATIONSHIP> findAllFWMS_RELATIONSHIP(Pageable page);
	
	@Query("select t from FWMS_RELATIONSHIP t where t.WMS_STATUS like 'Approved'  order by t.WMS_RELATIONSHIP_DESC asc ")
	public Page<FWMS_RELATIONSHIP> findAllAPPROVEDFWMS_RELATIONSHIP(Pageable page);
	
	@Query("select t from FWMS_RELATIONSHIP t where t.WMS_STATUS like 'Not Approved'  order by t.WMS_RELATIONSHIP_DESC asc ")
	public Page<FWMS_RELATIONSHIP> findAllUNAPPROVEDFWMS_RELATIONSHIP(Pageable page);
	
	/*
	 * Searching
	 */
	@Query("select t from FWMS_RELATIONSHIP t WHERE lower(t.WMS_RELATIONSHIP_DESC) like lower(concat('%',:cname,'%'))  order by t.WMS_RELATIONSHIP_DESC asc ")
	public List<FWMS_RELATIONSHIP> findAllFWMS_RELATIONSHIPSearching(@Param("cname")String cname);

	@Query("select t from FWMS_RELATIONSHIP t WHERE  lower(t.WMS_RELATIONSHIP_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.WMS_RELATIONSHIP_DESC asc ")
	public List<FWMS_RELATIONSHIP> findAllAPPROVEDFWMS_RELATIONSHIPSearching(@Param("cname")String cname);
	
	@Query("select t from FWMS_RELATIONSHIP t WHERE  lower(t.WMS_RELATIONSHIP_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.WMS_RELATIONSHIP_DESC asc ")
	public List<FWMS_RELATIONSHIP> findAllUNAPPROVEDFWMS_RELATIONSHIPSearching(@Param("cname")String cname);
	
	/*
	 * 
	 * Searching with page
	 */
	
	@Query("select t from FWMS_RELATIONSHIP t WHERE lower(t.WMS_RELATIONSHIP_DESC) like lower(concat('%',:cname,'%'))  order by t.WMS_RELATIONSHIP_DESC asc ")
	public Page<FWMS_RELATIONSHIP> findAllFWMS_RELATIONSHIPSearching(@Param("cname")String cname,Pageable page);

	@Query("select t from FWMS_RELATIONSHIP t WHERE  lower(t.WMS_RELATIONSHIP_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.WMS_RELATIONSHIP_DESC asc ")
	public Page<FWMS_RELATIONSHIP> findAllAPPROVEDFWMS_RELATIONSHIPSearching(@Param("cname")String cname,Pageable page);
	
	@Query("select t from FWMS_RELATIONSHIP t WHERE  lower(t.WMS_RELATIONSHIP_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.WMS_RELATIONSHIP_DESC asc ")
	public Page<FWMS_RELATIONSHIP> findAllUNAPPROVEDFWMS_RELATIONSHIPSearching(@Param("cname")String cname,Pageable page);
}