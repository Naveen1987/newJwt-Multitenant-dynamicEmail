package com.fynisys.repository.clienttype;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fynisys.model.clienttype.FWMS_NATIONALITY;

@Repository
public interface FWMS_NATIONALITYRepository extends PagingAndSortingRepository<FWMS_NATIONALITY, Long>{
	/*
	 * Without paging
	 */
	
	@Query("select t from FWMS_NATIONALITY t order by t.WMS_NATIONALITY_DESC asc ")
	public List<FWMS_NATIONALITY> findAllFWMS_NATIONALITY();
	
	@Query("select t from FWMS_NATIONALITY t WHERE lower(t.WMS_NATIONALITY_DESC) like lower(:cname)")
	public List<FWMS_NATIONALITY> findFWMS_NATIONALITYByName(@Param("cname")String cname);
	
	@Query("select t from FWMS_NATIONALITY t WHERE lower(t.WMS_NATIONALITY_DESC) like lower(:cname) and t.WMS_NATIONALITY_ID=:id")
	public List<FWMS_NATIONALITY> findFWMS_NATIONALITYByName(@Param("cname")String cname,@Param("id")Long id);
	
	@Query("select t from FWMS_NATIONALITY t WHERE lower(t.WMS_SHORT_DESC) like lower(:cname)")
	public List<FWMS_NATIONALITY> findFWMS_NATIONALITYByWMS_SHORT_DESC(@Param("cname")String cname);
	
	@Query("select t from FWMS_NATIONALITY t WHERE lower(t.WMS_SHORT_DESC) like lower(:cname) and t.WMS_NATIONALITY_ID=:id")
	public List<FWMS_NATIONALITY> findFWMS_NATIONALITYByWMS_SHORT_DESC(@Param("cname")String cname,@Param("id")Long id);
	
	
	@Query("select t from FWMS_NATIONALITY t where t.WMS_STATUS like 'Approved' order by t.WMS_NATIONALITY_DESC asc ")
	public List<FWMS_NATIONALITY> findAllAPPROVEDFWMS_NATIONALITY();
	
	@Query("select t from FWMS_NATIONALITY t where t.WMS_STATUS like 'Not Approved' order by t.WMS_NATIONALITY_DESC asc ")
	public List<FWMS_NATIONALITY> findAllUNAPPROVEDFWMS_NATIONALITY();
	
	/*
	 * With paging
	 */
	
	@Query("select t from FWMS_NATIONALITY t ORDER BY t.WMS_NATIONALITY_DESC ")
	public Page<FWMS_NATIONALITY> findAllFWMS_NATIONALITY(Pageable page);
	
	@Query("select t from FWMS_NATIONALITY t where t.WMS_STATUS like 'Approved' ORDER BY t.WMS_NATIONALITY_DESC ")
	public Page<FWMS_NATIONALITY> findAllAPPROVEDFWMS_NATIONALITY(Pageable page);
	
	@Query("select t from FWMS_NATIONALITY t where t.WMS_STATUS like 'Not Approved' ORDER BY t.WMS_NATIONALITY_DESC ")
	public Page<FWMS_NATIONALITY> findAllUNAPPROVEDFWMS_NATIONALITY(Pageable page);
	
	/*
	 * Searching
	 */
	@Query("select t from FWMS_NATIONALITY t WHERE lower(t.WMS_NATIONALITY_DESC) like lower(concat('%',:cname,'%')) ORDER BY t.WMS_NATIONALITY_DESC")
	public List<FWMS_NATIONALITY> findAllFWMS_NATIONALITYSearching(@Param("cname")String cname);

	@Query("select t from FWMS_NATIONALITY t WHERE  lower(t.WMS_NATIONALITY_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved' ORDER BY t.WMS_NATIONALITY_DESC")
	public List<FWMS_NATIONALITY> findAllAPPROVEDFWMS_NATIONALITYSearching(@Param("cname")String cname);
	
	@Query("select t from FWMS_NATIONALITY t WHERE  lower(t.WMS_NATIONALITY_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved' ORDER BY t.WMS_NATIONALITY_DESC")
	public List<FWMS_NATIONALITY> findAllUNAPPROVEDFWMS_NATIONALITYSearching(@Param("cname")String cname);
	
	/*
	 * 
	 * Searching with page
	 */
	
	@Query("select t from FWMS_NATIONALITY t WHERE lower(t.WMS_NATIONALITY_DESC) like lower(concat('%',:cname,'%')) ORDER BY t.WMS_NATIONALITY_DESC ")
	public Page<FWMS_NATIONALITY> findAllFWMS_NATIONALITYSearching(@Param("cname")String cname,Pageable page);

	@Query("select t from FWMS_NATIONALITY t WHERE  lower(t.WMS_NATIONALITY_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved' ORDER BY t.WMS_NATIONALITY_DESC ")
	public Page<FWMS_NATIONALITY> findAllAPPROVEDFWMS_NATIONALITYSearching(@Param("cname")String cname,Pageable page);
	
	@Query("select t from FWMS_NATIONALITY t WHERE  lower(t.WMS_NATIONALITY_DESC) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved' ORDER BY t.WMS_NATIONALITY_DESC ")
	public Page<FWMS_NATIONALITY> findAllUNAPPROVEDFWMS_NATIONALITYSearching(@Param("cname")String cname,Pageable page);
}
