package com.fynisys.repository.parameters;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fynisys.model.parameters.FUND_INSTRUMENT_MSTR;

@Repository
public interface FUND_INSTRUMENT_MSTRRepositroy extends PagingAndSortingRepository<FUND_INSTRUMENT_MSTR, Integer> {
	/*
	 * With out paging
	 */
	@Query("select t from FUND_INSTRUMENT_MSTR t order by t.SVI_NAME asc ")
	public List<FUND_INSTRUMENT_MSTR> findAllAssets();
	
	@Query("select t from FUND_INSTRUMENT_MSTR t where t.WMS_STATUS like 'Approved'  order by t.SVI_NAME asc ")
	public List<FUND_INSTRUMENT_MSTR> findAllAPPROVEDAssets();
	
	@Query("select t from FUND_INSTRUMENT_MSTR t where t.WMS_STATUS like 'Not Approved'  order by t.SVI_NAME asc ")
	public List<FUND_INSTRUMENT_MSTR> findAllUNAPPROVEDAssets();
	
	/*
	 * With paging
	 */
	
	@Query("select t from FUND_INSTRUMENT_MSTR t order by t.SVI_NAME asc ")
	public Page<FUND_INSTRUMENT_MSTR> findAllAssets(Pageable page);
	
	@Query("select t from FUND_INSTRUMENT_MSTR t where t.WMS_STATUS like 'Approved'  order by t.SVI_NAME asc ")
	public Page<FUND_INSTRUMENT_MSTR> findAllAPPROVEDAssets(Pageable page);
	
	@Query("select t from FUND_INSTRUMENT_MSTR t where t.WMS_STATUS like 'Not Approved'  order by t.SVI_NAME asc ")
	public Page<FUND_INSTRUMENT_MSTR> findAllUNAPPROVEDAssets(Pageable page); 
	
	/*
	 * all Assets
	 */
	@Query("select t from FUND_INSTRUMENT_MSTR t WHERE lower(t.SVI_NAME) like lower(concat('%',:cname,'%')) order by t.SVI_NAME asc")
	public List<FUND_INSTRUMENT_MSTR> findAllAsset(@Param("cname")String cname);
	/*
	 * Ids of all Assets
	 */
	@Query("select t.SVI_CODE from FUND_INSTRUMENT_MSTR t WHERE lower(t.SVI_NAME) like lower(concat('%',:cname,'%')) order by t.SVI_NAME asc")
	public List<Integer> findAllAssets(@Param("cname")String cname);
}
