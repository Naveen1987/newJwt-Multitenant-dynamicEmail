package com.fynisys.repository.funds;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fynisys.model.funds.FWMS_MUTUAL_FUNDS_FEES;

@Repository
public interface FWMS_MUTUAL_FUNDS_FEESRepository extends PagingAndSortingRepository<FWMS_MUTUAL_FUNDS_FEES, Long>
{
	
	/*
	 * Without paging
	 */
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where T.WMS_STATUS like 'Approved' ")
	public List<FWMS_MUTUAL_FUNDS_FEES> findAllAPPROVEDFWMS_MUTUAL_FUNDS_FEES();
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where T.WMS_STATUS like 'Not Approved'")
	public List<FWMS_MUTUAL_FUNDS_FEES> findAllUNAPPROVEDFWMS_MUTUAL_FUNDS_FEES();
	
	/*
	 * With paging
	 */
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where T.WMS_STATUS like 'Approved'")
	public Page<FWMS_MUTUAL_FUNDS_FEES> findAllAPPROVEDFWMS_MUTUAL_FUNDS_FEES(Pageable page);
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_MUTUAL_FUNDS_FEES> findAllUNAPPROVEDFWMS_MUTUAL_FUNDS_FEES(Pageable page);

	/*
	 * 
	 * Search With Out LEVEL
	 */
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where  "
			+ "(T.WMS_FUND_ID IN (:fundids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level)) OR "
			+ "( T.FMD_CLIENT IN (:levelids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level)) OR"
			+ "( T.FMD_CLIENT_TYPE IN (:levelids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level))")
	public Page<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCH(
			@Param("fundids")List<Long> fundids,
			@Param("levelids")List<Long> levelids,
			@Param("level") String level,Pageable page);
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where "
			+ "(T.WMS_FUND_ID IN (:fundids)) OR "
			+ "( T.FMD_CLIENT IN (:levelids)) OR"
			+ "( T.FMD_CLIENT_TYPE IN (:levelids))")
	public Page<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCH(
			@Param("fundids")List<Long> fundids,
			@Param("levelids")List<Long> levelids,
			Pageable page);
	
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where LOWER(T.FMD_LEVEL) LIKE LOWER(:level)")
	public Page<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCH(@Param("level") String level,
			Pageable page);
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where  "
			+ "((T.WMS_FUND_ID IN (:fundids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level)) OR "
			+ "( T.FMD_CLIENT IN (:levelids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level)) OR"
			+ "( T.FMD_CLIENT_TYPE IN (:levelids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level))) AND  T.WMS_STATUS like 'Approved'")
	public Page<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHAPPROVED(
			@Param("fundids")List<Long> fundids,
			@Param("levelids")List<Long> levelids,
			@Param("level") String level,Pageable page);
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where "
			+ "((T.WMS_FUND_ID IN (:fundids)) OR "
			+ "( T.FMD_CLIENT IN (:levelids)) OR"
			+ "( T.FMD_CLIENT_TYPE IN (:levelids))) AND  T.WMS_STATUS like 'Approved'")
	public Page<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHAPPROVED(
			@Param("fundids")List<Long> fundids,
			@Param("levelids")List<Long> levelids,
			Pageable page);
	
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where LOWER(T.FMD_LEVEL) LIKE LOWER(:level) AND  T.WMS_STATUS like 'Approved'")
	public Page<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHAPPROVED(@Param("level") String level,Pageable page);
	
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where  "
			+ "((T.WMS_FUND_ID IN (:fundids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level)) OR "
			+ "( T.FMD_CLIENT IN (:levelids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level)) OR"
			+ "( T.FMD_CLIENT_TYPE IN (:levelids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level))) AND  T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHUNAPPROVED(
			@Param("fundids")List<Long> fundids,
			@Param("levelids")List<Long> levelids,
			@Param("level") String level,Pageable page);
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where ((T.WMS_FUND_ID IN (:ids)) OR "
			+ "( T.FMD_CLIENT IN (:fundids)) OR"
			+ "( T.FMD_CLIENT_TYPE IN (:levelids))) AND  T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHUNAPPROVED(
			@Param("fundids")List<Long> fundids,
			@Param("levelids")List<Long> levelids,
			Pageable page);
	
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where LOWER(T.FMD_LEVEL) LIKE LOWER(:level) AND  T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHUNAPPROVED(@Param("level") String level,Pageable page);
	
	/*
	 * list
	 */
	
	/*
	 * 
	 * Search With Out LEVEL
	 */
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where  "
			+ "(T.WMS_FUND_ID IN (:fundids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level)) OR "
			+ "( T.FMD_CLIENT IN (:levelids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level)) OR"
			+ "( T.FMD_CLIENT_TYPE IN (:levelids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level))")
	public List<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCH(
			@Param("fundids")List<Long> fundids,
			@Param("levelids")List<Long> levelids,
			@Param("level") String level);
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where "
			+ "(T.WMS_FUND_ID IN (:fundids)) OR "
			+ "( T.FMD_CLIENT IN (:levelids)) OR"
			+ "( T.FMD_CLIENT_TYPE IN (:levelids))")
	public List<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCH(
			@Param("fundids")List<Long> fundids,
			@Param("levelids")List<Long> levelids);
	
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where LOWER(T.FMD_LEVEL) LIKE LOWER(:level)")
	public List<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCH(@Param("level") String level);
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where  "
			+ "((T.WMS_FUND_ID IN (:fundids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level)) OR "
			+ "( T.FMD_CLIENT IN (:levelids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level)) OR"
			+ "( T.FMD_CLIENT_TYPE IN (:levelids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level))) AND  T.WMS_STATUS like 'Approved'")
	public List<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHAPPROVED(
			@Param("fundids")List<Long> fundids,
			@Param("levelids")List<Long> levelids,
			@Param("level") String level);
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where "
			+ "((T.WMS_FUND_ID IN (:fundids)) OR "
			+ "( T.FMD_CLIENT IN (:levelids)) OR"
			+ "( T.FMD_CLIENT_TYPE IN (:levelids))) AND  T.WMS_STATUS like 'Approved'")
	public List<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHAPPROVED(
			@Param("fundids")List<Long> fundids,
			@Param("levelids")List<Long> levelids);
	
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where LOWER(T.FMD_LEVEL) LIKE LOWER(:level) AND  T.WMS_STATUS like 'Approved'")
	public List<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHAPPROVED(@Param("level") String level);
	
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where  "
			+ "((T.WMS_FUND_ID IN (:fundids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level)) OR "
			+ "( T.FMD_CLIENT IN (:levelids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level)) OR"
			+ "( T.FMD_CLIENT_TYPE IN (:levelids) AND LOWER(T.FMD_LEVEL) LIKE LOWER(:level))) AND  T.WMS_STATUS like 'Not Approved'")
	public List<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHUNAPPROVED(
			@Param("fundids")List<Long> fundids,
			@Param("levelids")List<Long> levelids,
			@Param("level") String level);
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where ((T.WMS_FUND_ID IN (:ids)) OR "
			+ "( T.FMD_CLIENT IN (:fundids)) OR"
			+ "( T.FMD_CLIENT_TYPE IN (:levelids))) AND  T.WMS_STATUS like 'Not Approved'")
	public List<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHUNAPPROVED(
			@Param("fundids")List<Long> fundids,
			@Param("levelids")List<Long> levelids);
	
	
	@Query("SELECT T FROM FWMS_MUTUAL_FUNDS_FEES T  where LOWER(T.FMD_LEVEL) LIKE LOWER(:level) AND  T.WMS_STATUS like 'Not Approved'")
	public List<FWMS_MUTUAL_FUNDS_FEES> findAllFWMS_MUTUAL_FUNDS_FEES_SEARCHUNAPPROVED(@Param("level") String level);
	
		
}