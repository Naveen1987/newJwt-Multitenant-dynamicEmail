package com.fynisys.repository.parameters;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.fynisys.model.parameters.FUND_INDUSTRY_MSTR;

@Repository
public interface FUND_INDUSTRY_MSTRRepositroy extends PagingAndSortingRepository<FUND_INDUSTRY_MSTR, Integer> {
	
	/*
	 * With out paging
	 */
	@Query("select t from FUND_INDUSTRY_MSTR t order by t.SVI_NAME asc ")
	public List<FUND_INDUSTRY_MSTR> findAllSectors();
	
	@Query("select t from FUND_INDUSTRY_MSTR t where t.WMS_STATUS like 'Approved'  order by t.SVI_NAME asc ")
	public List<FUND_INDUSTRY_MSTR> findAllAPPROVEDSectors();
	
	@Query("select t from FUND_INDUSTRY_MSTR t where t.WMS_STATUS like 'Not Approved'  order by t.SVI_NAME asc ")
	public List<FUND_INDUSTRY_MSTR> findAllUNAPPROVEDSectors();
	
	/*
	 * With paging
	 */
	
	@Query("select t from FUND_INDUSTRY_MSTR t order by t.SVI_NAME asc ")
	public Page<FUND_INDUSTRY_MSTR> findAllSectors(Pageable page);
	
	@Query("select t from FUND_INDUSTRY_MSTR t where t.WMS_STATUS like 'Approved'  order by t.SVI_NAME asc ")
	public Page<FUND_INDUSTRY_MSTR> findAllAPPROVEDSectors(Pageable page);
	
	@Query("select t from FUND_INDUSTRY_MSTR t where t.WMS_STATUS like 'Not Approved'  order by t.SVI_NAME asc ")
	public Page<FUND_INDUSTRY_MSTR> findAllUNAPPROVEDSectors(Pageable page);
	
//	@Query("select t from FUND_INDUSTRY_MSTR t where lower(t.SVI_NAME) like lower(:sectorname) or lower(t.SVI_NAME) like upper(:sectorname)")
//	public List<FUND_INDUSTRY_MSTR> getFUND_INDUSTRY_MSTR_NameUnique(@Param("sectorname") String sectorname);
//	
//	@Query("select t from FUND_INDUSTRY_MSTR t where lower(t.SVI_SHORT_NAME) like lower(:sectorname) or lower(t.SVI_SHORT_NAME) like upper(:sectorname)")
//	public List<FUND_INDUSTRY_MSTR> getFUND_INDUSTRY_MSTR_Shortname(@Param("sectorshortname") String sectorname);
}
