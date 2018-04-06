package com.fynisys.repository.funds;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.funds.FUND_MAR_MARGIN_MSTR_SECU_LEV;

@Repository
public interface FUND_MAR_MARGIN_MSTR_SECU_LEVRepository extends PagingAndSortingRepository<FUND_MAR_MARGIN_MSTR_SECU_LEV, Long>{

	/*
	 * Without paging
	 */
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where T.WMS_STATUS like 'Approved' ")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findAllAPPROVEDFUND_MAR_MARGIN_MSTR_SECU_LEV();
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where T.WMS_STATUS like 'Not Approved'")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findAllUNAPPROVEDFUND_MAR_MARGIN_MSTR_SECU_LEV();
	
	/*
	 * With paging
	 */
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where T.WMS_STATUS like 'Approved'")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findAllAPPROVEDFUND_MAR_MARGIN_MSTR_SECU_LEV(Pageable page);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where T.WMS_STATUS like 'Not Approved'")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findAllUNAPPROVEDFUND_MAR_MARGIN_MSTR_SECU_LEV(Pageable page);

	/*
	 * 
	 * Search With Out LEVEL
	 */
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL(
			@Param("level") String level,
			Pageable page);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(LOWER(T.FMD_MARGIN_LEVEL)) LIKE LOWER(:margin_level))")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL(
			@Param("margin_level") String margin_level,
			Pageable page);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(T.FMD_LEVEL )LIKE LOWER(LOWER(:level )) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(LOWER(:margin_level)))")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL(
			@Param("level") String level,
			@Param("margin_level") String margin_level,
			Pageable page);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(T.FMD_LEVEL )LIKE LOWER(:level )) AND  T.WMS_STATUS like 'Approved' ")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_APPROVED(
			@Param("level") String level,
			Pageable page);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level)) AND T.WMS_STATUS like 'Approved'")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL_APPROVED(
			@Param("margin_level") String margin_level,
			Pageable page);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level)) AND  T.WMS_STATUS like 'Approved'")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL_APPROVED(
			@Param("level") String level,
			@Param("margin_level") String margin_level,
			Pageable page);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(T.FMD_LEVEL )LIKE LOWER(:level )) AND T.WMS_STATUS like 'Not Approved' ")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_UNAPPROVED(
			@Param("level") String level,
			Pageable page);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level)) AND T.WMS_STATUS like 'Not Approved' ")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL_UNAPPROVED(
			@Param("margin_level") String margin_level,
			Pageable page);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))  AND T.WMS_STATUS like 'Not Approved'")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL_UNAPPROVED(
			@Param("level") String level,
			@Param("margin_level") String margin_level,
			Pageable page);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (T.FMD_FUND IN (:fundis) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_FUND IN (:fundis) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_CLIENT IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_C_TYPE IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_BROKER IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_CUSTODIAN IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL(
			@Param("fundis")List<Long> fundis,
			@Param("levelids")List<Long> levelids,
			@Param("level") String level,
			Pageable page);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (T.FMD_CID IN (:marginlevelids) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL(
			@Param("marginlevelids")List<Long> marginlevelids,
			@Param("margin_level") String margin_level,
			Pageable page);
	
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where ((T.FMD_FUND IN (:fundis) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_FUND IN (:fundis) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_CLIENT IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_C_TYPE IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_BROKER IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_CUSTODIAN IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))) AND T.WMS_STATUS like 'Approved'")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_APPROVED(
			@Param("fundis")List<Long> fundis,
			@Param("levelids")List<Long> levelids,
			@Param("level") String level,
			Pageable page);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (T.FMD_CID IN (:marginlevelids) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level)) AND T.WMS_STATUS like 'Approved'")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL_APPROVED(
			@Param("marginlevelids")List<Long> marginlevelids,
			@Param("margin_level") String margin_level,
			Pageable page);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where ((T.FMD_FUND IN (:fundis) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_FUND IN (:fundis) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_CLIENT IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_C_TYPE IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_BROKER IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_CUSTODIAN IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level )))  AND T.WMS_STATUS like 'Not Approved'")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_UNAPPROVED(
			@Param("fundis")List<Long> fundis,
			@Param("levelids")List<Long> levelids,
			@Param("level") String level,
			Pageable page);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (T.FMD_CID IN (:marginlevelids) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level)) AND T.WMS_STATUS like 'Not Approved'")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL_UNAPPROVED(
			@Param("marginlevelids")List<Long> marginlevelids,
			@Param("margin_level") String margin_level,
			Pageable page);
	
	/*
	 * 
	 */
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where "
			+"((T.FMD_FUND IN (:fundis) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
			
			+ " OR  ((T.FMD_CLIENT IN (:levelids) OR T.FMD_CID IN (:marginlevelids))  AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR  ((T.FMD_C_TYPE IN (:levelids) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level )  AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR  ((T.FMD_BROKER IN (:levelids) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR ((T.FMD_CUSTODIAN IN (:levelids) OR T.FMD_CID IN (:marginlevelids))  AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))")
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL(
			@Param("fundis")List<Long> fundis,
			@Param("levelids")List<Long> levelids,
			@Param("marginlevelids")List<Long> marginlevelids,
			@Param("level") String level,
			@Param("margin_level") String margin_level,
			Pageable page);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where "
			+"(((T.FMD_FUND IN (:fundis) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
			
			+ " OR  ((T.FMD_CLIENT IN (:levelids) OR T.FMD_CID IN (:marginlevelids))  AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR  ((T.FMD_C_TYPE IN (:levelids) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level )  AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR  ((T.FMD_BROKER IN (:levelids) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR ((T.FMD_CUSTODIAN IN (:levelids) OR T.FMD_CID IN (:marginlevelids))  AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level)) ) AND T.WMS_STATUS like 'Approved'")
	
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL_APPROVED(
			@Param("fundis")List<Long> fundis,
			@Param("levelids")List<Long> levelids,
			@Param("level") String level,
			@Param("marginlevelids")List<Long> marginlevelids,
			@Param("margin_level") String margin_level,
			Pageable page);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where "
			+"(((T.FMD_FUND IN (:fundis) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
			
			+ " OR  ((T.FMD_CLIENT IN (:levelids) OR T.FMD_CID IN (:marginlevelids))  AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR  ((T.FMD_C_TYPE IN (:levelids) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level )  AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR  ((T.FMD_BROKER IN (:levelids) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR ((T.FMD_CUSTODIAN IN (:levelids) OR T.FMD_CID IN (:marginlevelids))  AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level)) ) AND T.WMS_STATUS like 'Not Approved'")
	
	public Page<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL_UNAPPROVED(
			@Param("fundis")List<Long> fundis,
			@Param("levelids")List<Long> levelids,
			@Param("level") String level,
			@Param("marginlevelids")List<Long> marginlevelids,
			@Param("margin_level") String margin_level,
			Pageable page);
	
/*
 * With list	
 */

		@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL(
			@Param("level") String level);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(LOWER(T.FMD_MARGIN_LEVEL)) LIKE LOWER(:margin_level))")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL(
			@Param("margin_level") String margin_level);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(T.FMD_LEVEL )LIKE LOWER(LOWER(:level )) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(LOWER(:margin_level)))")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL(
			@Param("level") String level,
			@Param("margin_level") String margin_level);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(T.FMD_LEVEL )LIKE LOWER(:level )) AND  T.WMS_STATUS like 'Approved' ")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_APPROVED(
			@Param("level") String level);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level)) AND T.WMS_STATUS like 'Approved'")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL_APPROVED(
			@Param("margin_level") String margin_level);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level)) AND  T.WMS_STATUS like 'Approved'")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL_APPROVED(
			@Param("level") String level,
			@Param("margin_level") String margin_level);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(T.FMD_LEVEL )LIKE LOWER(:level )) AND T.WMS_STATUS like 'Not Approved' ")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_UNAPPROVED(
			@Param("level") String level);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level)) AND T.WMS_STATUS like 'Not Approved' ")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL_UNAPPROVED(
			@Param("margin_level") String margin_level);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))  AND T.WMS_STATUS like 'Not Approved'")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL_UNAPPROVED(
			@Param("level") String level,
			@Param("margin_level") String margin_level);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (T.FMD_FUND IN (:fundis) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_FUND IN (:fundis) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_CLIENT IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_C_TYPE IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_BROKER IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_CUSTODIAN IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL(
			@Param("fundis")List<Long> fundis,
			@Param("levelids")List<Long> levelids,
			@Param("level") String level);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (T.FMD_CID IN (:marginlevelids) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL(
			@Param("marginlevelids")List<Long> marginlevelids,
			@Param("margin_level") String margin_level);
	
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where ((T.FMD_FUND IN (:fundis) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_FUND IN (:fundis) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_CLIENT IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_C_TYPE IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_BROKER IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_CUSTODIAN IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))) AND T.WMS_STATUS like 'Approved'")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_APPROVED(
			@Param("fundis")List<Long> fundis,
			@Param("levelids")List<Long> levelids,
			@Param("level") String level);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (T.FMD_CID IN (:marginlevelids) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level)) AND T.WMS_STATUS like 'Approved'")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL_APPROVED(
			@Param("marginlevelids")List<Long> marginlevelids,
			@Param("margin_level") String margin_level);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where ((T.FMD_FUND IN (:fundis) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_FUND IN (:fundis) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_CLIENT IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_C_TYPE IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_BROKER IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ))"
			+ " OR  (T.FMD_CUSTODIAN IN (:levelids) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level )))  AND T.WMS_STATUS like 'Not Approved'")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_UNAPPROVED(
			@Param("fundis")List<Long> fundis,
			@Param("levelids")List<Long> levelids,
			@Param("level") String level);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where (T.FMD_CID IN (:marginlevelids) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level)) AND T.WMS_STATUS like 'Not Approved'")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_MARGINLEVEL_UNAPPROVED(
			@Param("marginlevelids")List<Long> marginlevelids,
			@Param("margin_level") String margin_level);
	
	
	 
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where "
			+"((T.FMD_FUND IN (:fundis) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
			
			+ " OR  ((T.FMD_CLIENT IN (:levelids) OR T.FMD_CID IN (:marginlevelids))  AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR  ((T.FMD_C_TYPE IN (:levelids) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level )  AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR  ((T.FMD_BROKER IN (:levelids) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR ((T.FMD_CUSTODIAN IN (:levelids) OR T.FMD_CID IN (:marginlevelids))  AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))")
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL(
			@Param("fundis")List<Long> fundis,
			@Param("levelids")List<Long> levelids,
			@Param("marginlevelids")List<Long> marginlevelids,
			@Param("level") String level,
			@Param("margin_level") String margin_level);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where "
			+"(((T.FMD_FUND IN (:fundis) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
			
			+ " OR  ((T.FMD_CLIENT IN (:levelids) OR T.FMD_CID IN (:marginlevelids))  AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR  ((T.FMD_C_TYPE IN (:levelids) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level )  AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR  ((T.FMD_BROKER IN (:levelids) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR ((T.FMD_CUSTODIAN IN (:levelids) OR T.FMD_CID IN (:marginlevelids))  AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level)) ) AND T.WMS_STATUS like 'Approved'")
	
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL_APPROVED(
			@Param("fundis")List<Long> fundis,
			@Param("levelids")List<Long> levelids,
			@Param("level") String level,
			@Param("marginlevelids")List<Long> marginlevelids,
			@Param("margin_level") String margin_level);
	
	@Query("SELECT T FROM FUND_MAR_MARGIN_MSTR_SECU_LEV T  where "
			+"(((T.FMD_FUND IN (:fundis) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
			
			+ " OR  ((T.FMD_CLIENT IN (:levelids) OR T.FMD_CID IN (:marginlevelids))  AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR  ((T.FMD_C_TYPE IN (:levelids) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level )  AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR  ((T.FMD_BROKER IN (:levelids) OR T.FMD_CID IN (:marginlevelids)) AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level))"
						
			+ " OR ((T.FMD_CUSTODIAN IN (:levelids) OR T.FMD_CID IN (:marginlevelids))  AND LOWER(T.FMD_LEVEL )LIKE LOWER(:level ) AND LOWER(T.FMD_MARGIN_LEVEL) LIKE LOWER(:margin_level)) ) AND T.WMS_STATUS like 'Not Approved'")
	
	public List<FUND_MAR_MARGIN_MSTR_SECU_LEV> findFUND_MARGIN_SEARCH_WITH_LEVEL_AND_MARGINLEVEL_UNAPPROVED(
			@Param("fundis")List<Long> fundis,
			@Param("levelids")List<Long> levelids,
			@Param("level") String level,
			@Param("marginlevelids")List<Long> marginlevelids,
			@Param("margin_level") String margin_level);
}