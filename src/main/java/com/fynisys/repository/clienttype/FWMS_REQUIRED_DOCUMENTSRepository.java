package com.fynisys.repository.clienttype;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fynisys.model.clienttype.FWMS_REQUIRED_DOCUMENTS;

@Repository
public interface FWMS_REQUIRED_DOCUMENTSRepository extends PagingAndSortingRepository<FWMS_REQUIRED_DOCUMENTS, Long>{
	/*
	 * Without paging
	 */
	@Query("SELECT T FROM FWMS_REQUIRED_DOCUMENTS T  where T.WMS_STATUS like 'Approved' ")
	public List<FWMS_REQUIRED_DOCUMENTS> findAllAPPROVEDFWMS_REQUIRED_DOCUMENTS();
	
	@Query("SELECT T FROM FWMS_REQUIRED_DOCUMENTS T  where T.WMS_STATUS like 'Not Approved'")
	public List<FWMS_REQUIRED_DOCUMENTS> findAllUNAPPROVEDFWMS_REQUIRED_DOCUMENTS();
	
	/*
	 * With paging
	 */
	@Query("SELECT T FROM FWMS_REQUIRED_DOCUMENTS T  where T.WMS_STATUS like 'Approved'")
	public Page<FWMS_REQUIRED_DOCUMENTS> findAllAPPROVEDFWMS_REQUIRED_DOCUMENTS(Pageable page);
	
	@Query("SELECT T FROM FWMS_REQUIRED_DOCUMENTS T  where T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_REQUIRED_DOCUMENTS> findAllUNAPPROVEDFWMS_REQUIRED_DOCUMENTS(Pageable page);

	/*
	 * 
	 * Search With Out LEVEL
	 */
	
	
	@Query("SELECT T FROM FWMS_REQUIRED_DOCUMENTS T  where  (T.WMS_CLIENT_TYPE IN (:clientids)) OR "
			+ "( T.WMS_DOCUMENT_TYPE IN (:doctypeids)) OR"
			+ "( T.WMS_ENTER_UID IN (:userids))")
	public Page<FWMS_REQUIRED_DOCUMENTS> findAllFWMS_REQUIRED_DOCUMENTS_SEARCH(
			@Param("clientids")List<Long> clientids,
			@Param("doctypeids")List<Long> doctypeids,
			@Param("userids")List<String> userids,
			Pageable page);
	
	@Query("SELECT T FROM FWMS_REQUIRED_DOCUMENTS T  where ( (T.WMS_CLIENT_TYPE IN (:clientids)) OR "
			+ "( T.WMS_DOCUMENT_TYPE IN (:doctypeids)) OR"
			+ "( T.WMS_ENTER_UID IN (:userids)) ) AND  T.WMS_STATUS like 'Approved'")
	public Page<FWMS_REQUIRED_DOCUMENTS> findAllFWMS_REQUIRED_DOCUMENTS_SEARCHAPPROVED(
			@Param("clientids")List<Long> clientids,
			@Param("doctypeids")List<Long> doctypeids,
			@Param("userids")List<String> userids,
			Pageable page);
	
	@Query("SELECT T FROM FWMS_REQUIRED_DOCUMENTS T  where ( (T.WMS_CLIENT_TYPE IN (:clientids)) OR "
			+ "( T.WMS_DOCUMENT_TYPE IN (:doctypeids)) OR"
			+ "( T.WMS_ENTER_UID IN (:userids))) AND T.WMS_STATUS like 'Not Approved'")
	public Page<FWMS_REQUIRED_DOCUMENTS> findAllFWMS_REQUIRED_DOCUMENTS_SEARCHUNAPPROVED(
			@Param("clientids")List<Long> clientids,
			@Param("doctypeids")List<Long> doctypeids,
			@Param("userids")List<String> userids,
			Pageable page);
	
	
////	
////	@Query("SELECT T FROM FWMS_REQUIRED_DOCUMENTS T  where  (T.WMS_FUND_ID IN (:ids) AND T.FMD_LEVEL LIKE :level) OR "
////			+ "( T.FMD_CLIENT IN (:ids) AND T.FMD_LEVEL LIKE :level) OR"
////			+ "( T.FMD_CLIENT_TYPE IN (:ids) AND T.FMD_LEVEL LIKE :level)")
////	public Page<FWMS_REQUIRED_DOCUMENTS> findAllFWMS_REQUIRED_DOCUMENTS_SEARCH(@Param("ids")List<Long> fundis,@Param("level") String level,Pageable page);
////	
//	@Query("SELECT T FROM FWMS_REQUIRED_DOCUMENTS T  where (T.WMS_FUND_ID IN (:ids)) OR "
//			+ "( T.FMD_CLIENT IN (:ids)) OR"
//			+ "( T.FMD_CLIENT_TYPE IN (:ids))")
//	public Page<FWMS_REQUIRED_DOCUMENTS> findAllFWMS_REQUIRED_DOCUMENTS_SEARCH(@Param("ids")List<Long> fundis,Pageable page);
//	
//	
//	@Query("SELECT T FROM FWMS_REQUIRED_DOCUMENTS T  where T.FMD_LEVEL LIKE :level")
//	public Page<FWMS_REQUIRED_DOCUMENTS> findAllFWMS_REQUIRED_DOCUMENTS_SEARCH(@Param("level") String level,Pageable page);
//	
//	
//	@Query("SELECT T FROM FWMS_REQUIRED_DOCUMENTS T  where ((T.WMS_FUND_ID IN (:ids) AND T.FMD_LEVEL LIKE :level) OR "
//			+ "( T.FMD_CLIENT IN (:ids) AND T.FMD_LEVEL LIKE :level) OR"
//			+ "( T.FMD_CLIENT_TYPE IN (:ids) AND T.FMD_LEVEL LIKE :level)) AND  T.WMS_STATUS like 'Approved'")
//	public Page<FWMS_REQUIRED_DOCUMENTS> findAllFWMS_REQUIRED_DOCUMENTS_SEARCHAPPROVED(@Param("ids")List<Long> fundis,@Param("level") String level,Pageable page);
//	
//	@Query("SELECT T FROM FWMS_REQUIRED_DOCUMENTS T  where ((T.WMS_FUND_ID IN (:ids)) OR "
//			+ "( T.FMD_CLIENT IN (:ids)) OR"
//			+ "( T.FMD_CLIENT_TYPE IN (:ids))) AND  T.WMS_STATUS like 'Approved'")
//	public Page<FWMS_REQUIRED_DOCUMENTS> findAllFWMS_REQUIRED_DOCUMENTS_SEARCHAPPROVED(@Param("ids")List<Long> fundis,Pageable page);
//	
//	
//	@Query("SELECT T FROM FWMS_REQUIRED_DOCUMENTS T  where T.FMD_LEVEL LIKE :level AND  T.WMS_STATUS like 'Approved'")
//	public Page<FWMS_REQUIRED_DOCUMENTS> findAllFWMS_REQUIRED_DOCUMENTS_SEARCHAPPROVED(@Param("level") String level,Pageable page);
//	
//	
//	@Query("SELECT T FROM FWMS_REQUIRED_DOCUMENTS T  where ((T.WMS_FUND_ID IN (:ids) AND T.FMD_LEVEL LIKE :level) OR "
//			+ "( T.FMD_CLIENT IN (:ids) AND T.FMD_LEVEL LIKE :level) OR"
//			+ "( T.FMD_CLIENT_TYPE IN (:ids) AND T.FMD_LEVEL LIKE :level)) AND  T.WMS_STATUS like 'Not Approved'")
//	public Page<FWMS_REQUIRED_DOCUMENTS> findAllFWMS_REQUIRED_DOCUMENTS_SEARCHUNAPPROVED(@Param("ids")List<Long> fundis,@Param("level") String level,Pageable page);
//	
//	@Query("SELECT T FROM FWMS_REQUIRED_DOCUMENTS T  where ((T.WMS_FUND_ID IN (:ids)) OR "
//			+ "( T.FMD_CLIENT IN (:ids)) OR"
//			+ "( T.FMD_CLIENT_TYPE IN (:ids))) AND  T.WMS_STATUS like 'Not Approved'")
//	public Page<FWMS_REQUIRED_DOCUMENTS> findAllFWMS_REQUIRED_DOCUMENTS_SEARCHUNAPPROVED(@Param("ids")List<Long> fundis,Pageable page);
//	
//	
//	@Query("SELECT T FROM FWMS_REQUIRED_DOCUMENTS T  where T.FMD_LEVEL LIKE :level AND  T.WMS_STATUS like 'Not Approved'")
//	public Page<FWMS_REQUIRED_DOCUMENTS> findAllFWMS_REQUIRED_DOCUMENTS_SEARCHUNAPPROVED(@Param("level") String level,Pageable page);
//	
//		
	
} 