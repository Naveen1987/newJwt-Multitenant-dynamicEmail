package com.fynisys.repository.parameters;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fynisys.model.parameters.FWMS_INDEX;

@Repository
public interface FWMS_INDEXRepository extends PagingAndSortingRepository<FWMS_INDEX, Integer> {

	/*
	 * With out paging
	 */
	
	@Query("select t from FWMS_INDEX t order by t.WMS_INDEX_DESC asc ")
	public List<FWMS_INDEX> findAllIndex();
	
	@Query("select t from FWMS_INDEX t where t.WMS_STATUS like 'Approved'  order by t.WMS_INDEX_DESC asc ")
	public List<FWMS_INDEX> findAllAPPROVEDIndex();
	
	@Query("select t from FWMS_INDEX t where t.WMS_STATUS like 'Not Approved'  order by t.WMS_INDEX_DESC asc ")
	public List<FWMS_INDEX> findAllUNAPPROVEDIndex();
	
	/*
	 * With paging
	 */
	
	@Query("select t from FWMS_INDEX t order by t.WMS_INDEX_DESC asc ")
	public Page<FWMS_INDEX> findAllIndex(Pageable page);
	
	@Query("select t from FWMS_INDEX t where t.WMS_STATUS like 'Approved'  order by t.WMS_INDEX_DESC asc ")
	public Page<FWMS_INDEX> findAllAPPROVEDIndex(Pageable page);
	
	@Query("select t from FWMS_INDEX t where t.WMS_STATUS like 'Not Approved'  order by t.WMS_INDEX_DESC asc ")
	public Page<FWMS_INDEX> findAllUNAPPROVEDIndex(Pageable page); 
	
}
