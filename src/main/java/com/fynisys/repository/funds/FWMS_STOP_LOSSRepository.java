package com.fynisys.repository.funds;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.funds.FWMS_STOP_LOSS;
@Repository
public interface FWMS_STOP_LOSSRepository extends PagingAndSortingRepository<FWMS_STOP_LOSS, Long>{

	/*
	 * Without paging
	 */
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where T.WMS_STATUS like 'Approved' ")
	public List<FWMS_STOP_LOSS> findAllAPPROVEDFWMS_STOP_LOSS();
	
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where T.WMS_STATUS like 'Not Approved'")
	public List<FWMS_STOP_LOSS> findAllUNAPPROVEDFWMS_STOP_LOSS();
	
	/*
	 * With paging
	 */
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where T.WMS_STATUS like 'Approved'")
	public Page<FWMS_STOP_LOSS> findAllAPPROVEDFWMS_STOP_LOSS(Pageable page);
	
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_STOP_LOSS> findAllUNAPPROVEDFWMS_STOP_LOSS(Pageable page);

	/*
	 * 
	 * Search With Out LEVEL
	 */
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_FUND IN (:fundids)")
	public Page<FWMS_STOP_LOSS> findAllFWMS_STOP_LOSS_SEARCH(@Param("fundids")List<Long> fundis,Pageable page);
			
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_FUND IN (:fundids) AND T.WMS_STATUS like 'Approved'")
	public Page<FWMS_STOP_LOSS> findAllAPPROVEDFWMS_STOP_LOSS_SEARCH(@Param("fundids")List<Long> fundis,Pageable page);
	
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_FUND IN (:fundids) AND  T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_STOP_LOSS> findAllUNAPPROVEDFWMS_STOP_LOSS_SEARCH(@Param("fundids")List<Long> fundis,Pageable page);
	
	/*
	 *Filter On the base of Type
	 */
	/*
	 * Level
	 */
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where T.FMD_LEVEL = :FMD_LEVEL ")
	public Page<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_LEVEL(@Param("FMD_LEVEL") String FMD_LEVEL,Pageable page);
	
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where T.FMD_LEVEL = :FMD_LEVEL  AND T.WMS_STATUS like 'Approved'")
	public Page<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_LEVEL_Approved(@Param("FMD_LEVEL") String FMD_LEVEL,Pageable page);
	
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where T.FMD_LEVEL = :FMD_LEVEL  AND T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_LEVEL_UNApproved(@Param("FMD_LEVEL") String FMD_LEVEL,Pageable page);
	/*
	 * FMD_SL_LEVEL
	 */
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where T.FMD_SL_LEVEL = :FMD_SL_LEVEL ")
	public Page<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_SL_LEVEL(@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,Pageable page);
	
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where T.FMD_SL_LEVEL = :FMD_SL_LEVEL  AND T.WMS_STATUS like 'Approved'")
	public Page<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_SL_LEVEL_Approved(@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,Pageable page);
	
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where T.FMD_SL_LEVEL = :FMD_SL_LEVEL  AND T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_SL_LEVEL_UNApproved(@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,Pageable page);
	
	/*
	 * FMD_SL_LEVEL on Both
	 */
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_LEVEL = :FMD_LEVEL AND T.FMD_SL_LEVEL = :FMD_SL_LEVEL ")
	public Page<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,Pageable page);
	
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_LEVEL = :FMD_LEVEL AND  T.FMD_SL_LEVEL = :FMD_SL_LEVEL  AND T.WMS_STATUS like 'Approved'")
	public Page<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL_Approved(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,Pageable page);
	
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_LEVEL = :FMD_LEVEL AND  T.FMD_SL_LEVEL = :FMD_SL_LEVEL  AND T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL_UNApproved(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,Pageable page);
	
	/*
	 *  With Search with LEVEL
	 */
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_LEVEL = :FMD_LEVEL AND  T.FMD_FUND IN (:fundids) ")
	public Page<FWMS_STOP_LOSS> findAllFWMS_STOP_LOSS_LEVEL_SEARCH(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("fundids")List<Long> fundis,Pageable page);
			
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_LEVEL = :FMD_LEVEL AND  T.FMD_FUND IN (:fundids) AND T.WMS_STATUS like 'Approved'")
	public Page<FWMS_STOP_LOSS> findAllAPPROVEDFWMS_STOP_LOSS_LEVEL_SEARCH(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("fundids")List<Long> fundis,Pageable page);
	
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where   T.FMD_LEVEL = :FMD_LEVEL AND T.FMD_FUND IN (:fundids) AND  T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_STOP_LOSS> findAllUNAPPROVEDFWMS_STOP_LOSS_LEVEL_SEARCH(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("fundids")List<Long> fundis,Pageable page);
	
	
	/*
	 *  With Search with SL LEVEL
	 */
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_SL_LEVEL = :FMD_SL_LEVEL AND  T.FMD_FUND IN (:fundids) ")
	public Page<FWMS_STOP_LOSS> findAllFWMS_STOP_LOSS_SL_LEVEL_SEARCH(@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,@Param("fundids")List<Long> fundis,Pageable page);
			
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_SL_LEVEL = :FMD_SL_LEVEL AND  T.FMD_FUND IN (:fundids) AND T.WMS_STATUS like 'Approved'")
	public Page<FWMS_STOP_LOSS> findAllAPPROVEDFWMS_STOP_LOSS_SL_LEVEL_SEARCH(@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,@Param("fundids")List<Long> fundis,Pageable page);
	
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where   T.FMD_SL_LEVEL = :FMD_SL_LEVEL AND T.FMD_FUND IN (:fundids) AND  T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_STOP_LOSS> findAllUNAPPROVEDFWMS_STOP_LOSS_SL_LEVEL_SEARCH(@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,@Param("fundids")List<Long> fundis,Pageable page);
	
	/*
	 *  With Search with SL LEVEL WITH LEVEL
	 */
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_LEVEL = :FMD_LEVEL AND  T.FMD_SL_LEVEL = :FMD_SL_LEVEL AND  T.FMD_FUND IN (:fundids)")
	public Page<FWMS_STOP_LOSS> findAllFWMS_STOP_LOSS_LEVEL_SL_LEVEL_SEARCH(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,@Param("fundids")List<Long> fundis,Pageable page);
			
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_LEVEL = :FMD_LEVEL AND  T.FMD_SL_LEVEL = :FMD_SL_LEVEL AND  T.FMD_FUND IN (:fundids) AND T.WMS_STATUS like 'Approved'")
	public Page<FWMS_STOP_LOSS> findAllAPPROVEDFWMS_STOP_LOSS_LEVEL_SL_LEVEL_SEARCH(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,@Param("fundids")List<Long> fundis,Pageable page);
	
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_LEVEL = :FMD_LEVEL AND  T.FMD_SL_LEVEL = :FMD_SL_LEVEL AND T.FMD_FUND IN (:fundids) AND  T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_STOP_LOSS> findAllUNAPPROVEDFWMS_STOP_LOSS_LEVEL_SL_LEVEL_SEARCH(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,@Param("fundids")List<Long> fundis,Pageable page);
	
	@Query("SELECT COUNT(T) FROM FWMS_STOP_LOSS T "
			+ "WHERE T.FMD_FUND =:FMD_FUND AND T.FMD_SL_LEVEL =:FMD_SL_LEVEL AND T.FMD_SID=:FMD_SID  AND T.FMD_DATE =:FMD_DATE AND T.FMD_LEVEL =:FMD_LEVEL")
	public long fundCheckFundLevelDuplicate(
			@Param("FMD_FUND") Long FMD_FUND,
			@Param("FMD_DATE") Calendar FMD_DATE,
			@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,
			@Param("FMD_LEVEL") String FMD_LEVEL,
			@Param("FMD_SID") Long FMD_SID
			
			);
	
	@Query("SELECT COUNT(T) FROM FWMS_STOP_LOSS T "
			+ "WHERE T.FMD_FUND =:FMD_FUND AND T.FMD_SL_LEVEL =:FMD_SL_LEVEL AND T.FMD_SID=:FMD_SID  AND T.FMD_DATE =:FMD_DATE AND T.FMD_LEVEL =:FMD_LEVEL AND T.FMD_CLIENT=:FMD_CLIENT ")
	public long fundCheckClentDuplicate(
			@Param("FMD_FUND") Long FMD_FUND,
			@Param("FMD_DATE") Calendar FMD_DATE,
			@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,
			@Param("FMD_LEVEL") String FMD_LEVEL,
			@Param("FMD_SID") Long FMD_SID,
			@Param("FMD_CLIENT") Long FMD_CLIENT
			);
	
	@Query("SELECT COUNT(T) FROM FWMS_STOP_LOSS T "
			+ "WHERE T.FMD_FUND =:FMD_FUND AND T.FMD_SL_LEVEL =:FMD_SL_LEVEL AND T.FMD_SID=:FMD_SID  AND T.FMD_DATE =:FMD_DATE AND T.FMD_LEVEL =:FMD_LEVEL AND T.FMD_C_TYPE=:FMD_C_TYPE ")
	public long fundCheckClientTypeDuplicate(
			@Param("FMD_FUND") Long FMD_FUND,
			@Param("FMD_DATE") Calendar FMD_DATE,
			@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,
			@Param("FMD_LEVEL") String FMD_LEVEL,
			@Param("FMD_SID") Long FMD_SID,
			@Param("FMD_C_TYPE") Long FMD_C_TYPE
			);
	/*
	 * 
	 * With List	
	 */
	@Query("SELECT T FROM FWMS_STOP_LOSS T")
	public List<FWMS_STOP_LOSS> findAllStopLoss();
	/*
	 * 
	 * Search With Out LEVEL
	 */
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_FUND IN (:fundids)")
	public List<FWMS_STOP_LOSS> findAllFWMS_STOP_LOSS_SEARCH(@Param("fundids")List<Long> fundis);
			
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_FUND IN (:fundids) AND T.WMS_STATUS like 'Approved'")
	public List<FWMS_STOP_LOSS> findAllAPPROVEDFWMS_STOP_LOSS_SEARCH(@Param("fundids")List<Long> fundis);
	
	@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_FUND IN (:fundids) AND  T.WMS_STATUS like 'Not Approved'")
	public List<FWMS_STOP_LOSS> findAllUNAPPROVEDFWMS_STOP_LOSS_SEARCH(@Param("fundids")List<Long> fundis);
	
		/*
		 * Level
		 */
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where T.FMD_LEVEL = :FMD_LEVEL ")
		public List<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_LEVEL(@Param("FMD_LEVEL") String FMD_LEVEL);
		
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where T.FMD_LEVEL = :FMD_LEVEL  AND T.WMS_STATUS like 'Approved'")
		public List<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_LEVEL_Approved(@Param("FMD_LEVEL") String FMD_LEVEL);
		
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where T.FMD_LEVEL = :FMD_LEVEL  AND T.WMS_STATUS like 'Not Approved'")
		public List<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_LEVEL_UNApproved(@Param("FMD_LEVEL") String FMD_LEVEL);
		/*
		 * FMD_SL_LEVEL
		 */
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where T.FMD_SL_LEVEL = :FMD_SL_LEVEL ")
		public List<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_SL_LEVEL(@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL);
		
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where T.FMD_SL_LEVEL = :FMD_SL_LEVEL  AND T.WMS_STATUS like 'Approved'")
		public List<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_SL_LEVEL_Approved(@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL);
		
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where T.FMD_SL_LEVEL = :FMD_SL_LEVEL  AND T.WMS_STATUS like 'Not Approved'")
		public List<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_SL_LEVEL_UNApproved(@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL);
		
		/*
		 * FMD_SL_LEVEL on Both
		 */
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_LEVEL = :FMD_LEVEL AND T.FMD_SL_LEVEL = :FMD_SL_LEVEL ")
		public List<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL);
		
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_LEVEL = :FMD_LEVEL AND  T.FMD_SL_LEVEL = :FMD_SL_LEVEL  AND T.WMS_STATUS like 'Approved'")
		public List<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL_Approved(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL);
		
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_LEVEL = :FMD_LEVEL AND  T.FMD_SL_LEVEL = :FMD_SL_LEVEL  AND T.WMS_STATUS like 'Not Approved'")
		public List<FWMS_STOP_LOSS> findAll_STOP_LOSS_ON_LEVEL_SL_LEVEL_UNApproved(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL);
		
		/*
		 *  With Search with LEVEL
		 */
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_LEVEL = :FMD_LEVEL AND  T.FMD_FUND IN (:fundids) ")
		public List<FWMS_STOP_LOSS> findAllFWMS_STOP_LOSS_LEVEL_SEARCH(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("fundids")List<Long> fundis);
				
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_LEVEL = :FMD_LEVEL AND  T.FMD_FUND IN (:fundids) AND T.WMS_STATUS like 'Approved'")
		public List<FWMS_STOP_LOSS> findAllAPPROVEDFWMS_STOP_LOSS_LEVEL_SEARCH(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("fundids")List<Long> fundis);
		
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where   T.FMD_LEVEL = :FMD_LEVEL AND T.FMD_FUND IN (:fundids) AND  T.WMS_STATUS like 'Not Approved'")
		public List<FWMS_STOP_LOSS> findAllUNAPPROVEDFWMS_STOP_LOSS_LEVEL_SEARCH(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("fundids")List<Long> fundis);
		
		
		/*
		 *  With Search with SL LEVEL
		 */
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_SL_LEVEL = :FMD_SL_LEVEL AND  T.FMD_FUND IN (:fundids) ")
		public List<FWMS_STOP_LOSS> findAllFWMS_STOP_LOSS_SL_LEVEL_SEARCH(@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,@Param("fundids")List<Long> fundis);
				
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_SL_LEVEL = :FMD_SL_LEVEL AND  T.FMD_FUND IN (:fundids) AND T.WMS_STATUS like 'Approved'")
		public List<FWMS_STOP_LOSS> findAllAPPROVEDFWMS_STOP_LOSS_SL_LEVEL_SEARCH(@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,@Param("fundids")List<Long> fundis);
		
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where   T.FMD_SL_LEVEL = :FMD_SL_LEVEL AND T.FMD_FUND IN (:fundids) AND  T.WMS_STATUS like 'Not Approved'")
		public List<FWMS_STOP_LOSS> findAllUNAPPROVEDFWMS_STOP_LOSS_SL_LEVEL_SEARCH(@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,@Param("fundids")List<Long> fundis);
		
		/*
		 *  With Search with SL LEVEL WITH LEVEL
		 */
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_LEVEL = :FMD_LEVEL AND  T.FMD_SL_LEVEL = :FMD_SL_LEVEL AND  T.FMD_FUND IN (:fundids)")
		public List<FWMS_STOP_LOSS> findAllFWMS_STOP_LOSS_LEVEL_SL_LEVEL_SEARCH(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,@Param("fundids")List<Long> fundis);
				
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_LEVEL = :FMD_LEVEL AND  T.FMD_SL_LEVEL = :FMD_SL_LEVEL AND  T.FMD_FUND IN (:fundids) AND T.WMS_STATUS like 'Approved'")
		public List<FWMS_STOP_LOSS> findAllAPPROVEDFWMS_STOP_LOSS_LEVEL_SL_LEVEL_SEARCH(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,@Param("fundids")List<Long> fundis);
		
		@Query("SELECT T FROM FWMS_STOP_LOSS T  where  T.FMD_LEVEL = :FMD_LEVEL AND  T.FMD_SL_LEVEL = :FMD_SL_LEVEL AND T.FMD_FUND IN (:fundids) AND  T.WMS_STATUS like 'Not Approved'")
		public List<FWMS_STOP_LOSS> findAllUNAPPROVEDFWMS_STOP_LOSS_LEVEL_SL_LEVEL_SEARCH(@Param("FMD_LEVEL") String FMD_LEVEL,@Param("FMD_SL_LEVEL") String FMD_SL_LEVEL,@Param("fundids")List<Long> fundis);

}