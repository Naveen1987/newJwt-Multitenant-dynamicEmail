package com.fynisys.repository.clienttype;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.clienttype.FUND_MAR_CLIENT_TYPE;

@Repository
public interface FUND_MAR_CLIENT_TYPERespository extends PagingAndSortingRepository< FUND_MAR_CLIENT_TYPE, Long>{
	/*
	 * Without paging
	 */
	
	@Query("select t from FUND_MAR_CLIENT_TYPE t order by t.FCT_NAME asc ")
	public List<FUND_MAR_CLIENT_TYPE> findAllFUND_MAR_CLIENT_TYPE();
	
	@Query("select t from FUND_MAR_CLIENT_TYPE t WHERE lower(t.FCT_NAME) like lower(:cname)")
	public List<FUND_MAR_CLIENT_TYPE> findByFUND_MAR_CLIENT_TYPE_Name(@Param("cname")String cname);
	
	
	@Query("select t from FUND_MAR_CLIENT_TYPE t where t.WMS_STATUS like 'Approved'  order by t.FCT_NAME asc ")
	public List<FUND_MAR_CLIENT_TYPE> findAllAPPROVEDFUND_MAR_CLIENT_TYPE();
	
	@Query("select t from FUND_MAR_CLIENT_TYPE t where t.WMS_STATUS like 'Not Approved'  order by t.FCT_NAME asc ")
	public List<FUND_MAR_CLIENT_TYPE> findAllUNAPPROVEDFUND_MAR_CLIENT_TYPE();
	
	/*
	 * With paging
	 */
	
	@Query("select t from FUND_MAR_CLIENT_TYPE t order by t.FCT_NAME asc ")
	public Page<FUND_MAR_CLIENT_TYPE> findAllFUND_MAR_CLIENT_TYPE(Pageable page);
	
	@Query("select t from FUND_MAR_CLIENT_TYPE t where t.WMS_STATUS like 'Approved'  order by t.FCT_NAME asc ")
	public Page<FUND_MAR_CLIENT_TYPE> findAllAPPROVEDFUND_MAR_CLIENT_TYPE(Pageable page);
	
	@Query("select t from FUND_MAR_CLIENT_TYPE t where t.WMS_STATUS like 'Not Approved'  order by t.FCT_NAME asc ")
	public Page<FUND_MAR_CLIENT_TYPE> findAllUNAPPROVEDFUND_MAR_CLIENT_TYPE(Pageable page);
	
	/*
	 * Searching
	 */
	@Query("select t from FUND_MAR_CLIENT_TYPE t WHERE lower(t.FCT_NAME) like lower(concat('%',:cname,'%'))  order by t.FCT_NAME asc ")
	public List<FUND_MAR_CLIENT_TYPE> findAllFUND_MAR_CLIENT_TYPESearching(@Param("cname")String cname);

	@Query("select t from FUND_MAR_CLIENT_TYPE t WHERE  lower(t.FCT_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.FCT_NAME asc ")
	public List<FUND_MAR_CLIENT_TYPE> findAllAPPROVEDFUND_MAR_CLIENT_TYPESearching(@Param("cname")String cname);
	
	@Query("select t from FUND_MAR_CLIENT_TYPE t WHERE  lower(t.FCT_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.FCT_NAME asc ")
	public List<FUND_MAR_CLIENT_TYPE> findAllUNAPPROVEDFUND_MAR_CLIENT_TYPESearching(@Param("cname")String cname);
	
	/*
	 * 
	 * Searching with page
	 */
	
	@Query("select t from FUND_MAR_CLIENT_TYPE t WHERE lower(t.FCT_NAME) like lower(concat('%',:cname,'%'))  order by t.FCT_NAME asc ")
	public Page<FUND_MAR_CLIENT_TYPE> findAllFUND_MAR_CLIENT_TYPESearching(@Param("cname")String cname,Pageable page);

	@Query("select t from FUND_MAR_CLIENT_TYPE t WHERE  lower(t.FCT_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Approved'  order by t.FCT_NAME asc ")
	public Page<FUND_MAR_CLIENT_TYPE> findAllAPPROVEDFUND_MAR_CLIENT_TYPESearching(@Param("cname")String cname,Pageable page);
	
	@Query("select t from FUND_MAR_CLIENT_TYPE t WHERE  lower(t.FCT_NAME) like lower(concat('%',:cname,'%'))  and t.WMS_STATUS like 'Not Approved'  order by t.FCT_NAME asc ")
	public Page<FUND_MAR_CLIENT_TYPE> findAllUNAPPROVEDFUND_MAR_CLIENT_TYPESearching(@Param("cname")String cname,Pageable page);

	/*
	 * Search ID
	 */
		@Query("select t.FCT_ID from FUND_MAR_CLIENT_TYPE t WHERE lower(t.FCT_NAME) like lower(concat('%',:cname,'%'))  order by t.FCT_ID asc ")
		public List<Long> find_ids_FUND_MAR_CLIENT_TYPE(@Param("cname")String cname);

}
