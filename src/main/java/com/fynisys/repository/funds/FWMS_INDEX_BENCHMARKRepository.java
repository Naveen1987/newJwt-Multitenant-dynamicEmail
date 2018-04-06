package com.fynisys.repository.funds;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.funds.FWMS_INDEX_BENCHMARK;
@Repository
public interface FWMS_INDEX_BENCHMARKRepository extends PagingAndSortingRepository<FWMS_INDEX_BENCHMARK, Long>{
	/*
	 * Without paging
	 */
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where T.WMS_STATUS like 'Approved' ")
	public List<FWMS_INDEX_BENCHMARK> findAllAPPROVEDFWMS_INDEX_BENCHMARK();
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where T.WMS_STATUS like 'Not Approved'")
	public List<FWMS_INDEX_BENCHMARK> findAllUNAPPROVEDFWMS_INDEX_BENCHMARK();
	
	/*
	 * With paging
	 */
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where T.WMS_STATUS like 'Approved'")
	public Page<FWMS_INDEX_BENCHMARK> findAllAPPROVEDFWMS_INDEX_BENCHMARK(Pageable page);
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_INDEX_BENCHMARK> findAllUNAPPROVEDFWMS_INDEX_BENCHMARK(Pageable page);

	/*
	 * 
	 * Search With Out LEVEL
	 */
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where  (T.FMD_FUND IN (:ids) AND T.FMD_LEVEL LIKE :level) OR "
			+ "( T.FMD_CLIENT IN (:ids) AND T.FMD_LEVEL LIKE :level) OR"
			+ "( T.FMD_C_TYPE IN (:ids) AND T.FMD_LEVEL LIKE :level)")
	public Page<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCH(@Param("ids")List<Long> fundis,@Param("level") String level,Pageable page);
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where (T.FMD_FUND IN (:ids)) OR "
			+ "( T.FMD_CLIENT IN (:ids)) OR"
			+ "( T.FMD_C_TYPE IN (:ids))")
	public Page<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCH(@Param("ids")List<Long> fundis,Pageable page);
	
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where T.FMD_LEVEL LIKE :level")
	public Page<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCH(@Param("level") String level,Pageable page);
	
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where ((T.FMD_FUND IN (:ids) AND T.FMD_LEVEL LIKE :level) OR "
			+ "( T.FMD_CLIENT IN (:ids) AND T.FMD_LEVEL LIKE :level) OR"
			+ "( T.FMD_C_TYPE IN (:ids) AND T.FMD_LEVEL LIKE :level)) AND  T.WMS_STATUS like 'Approved'")
	public Page<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCHAPPROVED(@Param("ids")List<Long> fundis,@Param("level") String level,Pageable page);
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where ((T.FMD_FUND IN (:ids)) OR "
			+ "( T.FMD_CLIENT IN (:ids)) OR"
			+ "( T.FMD_C_TYPE IN (:ids))) AND  T.WMS_STATUS like 'Approved'")
	public Page<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCHAPPROVED(@Param("ids")List<Long> fundis,Pageable page);
	
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where T.FMD_LEVEL LIKE :level AND  T.WMS_STATUS like 'Approved'")
	public Page<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCHAPPROVED(@Param("level") String level,Pageable page);
	
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where ((T.FMD_FUND IN (:ids) AND T.FMD_LEVEL LIKE :level) OR "
			+ "( T.FMD_CLIENT IN (:ids) AND T.FMD_LEVEL LIKE :level) OR"
			+ "( T.FMD_C_TYPE IN (:ids) AND T.FMD_LEVEL LIKE :level)) AND  T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCHUNAPPROVED(@Param("ids")List<Long> fundis,@Param("level") String level,Pageable page);
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where ((T.FMD_FUND IN (:ids)) OR "
			+ "( T.FMD_CLIENT IN (:ids)) OR"
			+ "( T.FMD_C_TYPE IN (:ids))) AND  T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCHUNAPPROVED(@Param("ids")List<Long> fundis,Pageable page);
	
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where T.FMD_LEVEL LIKE :level AND  T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCHUNAPPROVED(@Param("level") String level,Pageable page);
	
		/*
		 * List
		 */
	
	/*
	 * 
	 * Search With Out LEVEL
	 */
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where  (T.FMD_FUND IN (:ids) AND T.FMD_LEVEL LIKE :level) OR "
			+ "( T.FMD_CLIENT IN (:ids) AND T.FMD_LEVEL LIKE :level) OR"
			+ "( T.FMD_C_TYPE IN (:ids) AND T.FMD_LEVEL LIKE :level)")
	public List<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCH(@Param("ids")List<Long> fundis,@Param("level") String level);
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where (T.FMD_FUND IN (:ids)) OR "
			+ "( T.FMD_CLIENT IN (:ids)) OR"
			+ "( T.FMD_C_TYPE IN (:ids))")
	public List<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCH(@Param("ids")List<Long> fundis);
	
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where T.FMD_LEVEL LIKE :level")
	public List<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCH(@Param("level") String level);
	
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where ((T.FMD_FUND IN (:ids) AND T.FMD_LEVEL LIKE :level) OR "
			+ "( T.FMD_CLIENT IN (:ids) AND T.FMD_LEVEL LIKE :level) OR"
			+ "( T.FMD_C_TYPE IN (:ids) AND T.FMD_LEVEL LIKE :level)) AND  T.WMS_STATUS like 'Approved'")
	public List<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCHAPPROVED(@Param("ids")List<Long> fundis,@Param("level") String level);
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where ((T.FMD_FUND IN (:ids)) OR "
			+ "( T.FMD_CLIENT IN (:ids)) OR"
			+ "( T.FMD_C_TYPE IN (:ids))) AND  T.WMS_STATUS like 'Approved'")
	public List<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCHAPPROVED(@Param("ids")List<Long> fundis);
	
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where T.FMD_LEVEL LIKE :level AND  T.WMS_STATUS like 'Approved'")
	public List<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCHAPPROVED(@Param("level") String level);
	
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where ((T.FMD_FUND IN (:ids) AND T.FMD_LEVEL LIKE :level) OR "
			+ "( T.FMD_CLIENT IN (:ids) AND T.FMD_LEVEL LIKE :level) OR"
			+ "( T.FMD_C_TYPE IN (:ids) AND T.FMD_LEVEL LIKE :level)) AND  T.WMS_STATUS like 'Not Approved'")
	public List<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCHUNAPPROVED(@Param("ids")List<Long> fundis,@Param("level") String level);
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where ((T.FMD_FUND IN (:ids)) OR "
			+ "( T.FMD_CLIENT IN (:ids)) OR"
			+ "( T.FMD_C_TYPE IN (:ids))) AND  T.WMS_STATUS like 'Not Approved'")
	public List<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCHUNAPPROVED(@Param("ids")List<Long> fundis);
	
	
	@Query("SELECT T FROM FWMS_INDEX_BENCHMARK T  where T.FMD_LEVEL LIKE :level AND  T.WMS_STATUS like 'Not Approved'")
	public List<FWMS_INDEX_BENCHMARK> findAllFWMS_INDEX_BENCHMARK_SEARCHUNAPPROVED(@Param("level") String level);
	
		
}
