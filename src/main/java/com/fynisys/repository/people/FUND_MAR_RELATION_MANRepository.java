package com.fynisys.repository.people;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.people.FUND_MAR_RELATION_MAN;


@Repository
public interface FUND_MAR_RELATION_MANRepository  extends PagingAndSortingRepository<FUND_MAR_RELATION_MAN, Long> {
	
	/*
	 * Without paging
	 */
	
	@Query("select t from FUND_MAR_RELATION_MAN t order by t.SVB_NAME asc ")
	public List<FUND_MAR_RELATION_MAN> findAllRm();
	
	@Query("select t from FUND_MAR_RELATION_MAN t where t.SVB_STATUS like 'Approved'  order by t.SVB_NAME asc ")
	public List<FUND_MAR_RELATION_MAN> findAllAPPROVEDRm();
	
	@Query("select t from FUND_MAR_RELATION_MAN t where t.SVB_STATUS like 'Not Approved'  order by t.SVB_NAME asc ")
	public List<FUND_MAR_RELATION_MAN> findAllUNAPPROVEDRm();
	
	/*
	 * With paging
	 */
	
	@Query("select t from FUND_MAR_RELATION_MAN t order by t.SVB_NAME asc ")
	public Page<FUND_MAR_RELATION_MAN> findAllRm(Pageable page);
	
	@Query("select t from FUND_MAR_RELATION_MAN t where t.SVB_STATUS like 'Approved'  order by t.SVB_NAME asc ")
	public Page<FUND_MAR_RELATION_MAN> findAllAPPROVEDRm(Pageable page);
	
	@Query("select t from FUND_MAR_RELATION_MAN t where t.SVB_STATUS like 'Not Approved'  order by t.SVB_NAME asc ")
	public Page<FUND_MAR_RELATION_MAN> findAllUNAPPROVEDRm(Pageable page);
	
	/*
	 * Searching
	 */
	@Query("select t from FUND_MAR_RELATION_MAN t WHERE lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  order by t.SVB_NAME asc ")
	public List<FUND_MAR_RELATION_MAN> findAllRmSearching(@Param("cname")String cname);

	@Query("select t from FUND_MAR_RELATION_MAN t WHERE  lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  and t.SVB_STATUS like 'Approved'  order by t.SVB_NAME asc ")
	public List<FUND_MAR_RELATION_MAN> findAllAPPROVEDRmSearching(@Param("cname")String cname);
	
	@Query("select t from FUND_MAR_RELATION_MAN t WHERE  lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  and t.SVB_STATUS like 'Not Approved'  order by t.SVB_NAME asc ")
	public List<FUND_MAR_RELATION_MAN> findAllUNAPPROVEDRmSearching(@Param("cname")String cname);
	
	
	@Query("select t from FUND_MAR_RELATION_MAN t WHERE lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  order by t.SVB_NAME asc ")
	public Page<FUND_MAR_RELATION_MAN> findAllRmSearchingPage(@Param("cname")String cname,Pageable page);

	@Query("select t from FUND_MAR_RELATION_MAN t WHERE  lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  and t.SVB_STATUS like 'Approved'  order by t.SVB_NAME asc ")
	public Page<FUND_MAR_RELATION_MAN> findAllAPPROVEDRmSearchingPage(@Param("cname")String cname,Pageable page);
	
	@Query("select t from FUND_MAR_RELATION_MAN t WHERE  lower(t.SVB_NAME) like lower(concat('%',:cname,'%'))  and t.SVB_STATUS like 'Not Approved'  order by t.SVB_NAME asc ")
	public Page<FUND_MAR_RELATION_MAN> findAllUNAPPROVEDRmSearchingPage(@Param("cname")String cname,Pageable page);
	

}
