package com.fynisys.repository.stock;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.stock.FUND_SHARE_COMPANY_MSTR;

@Repository
public interface FUND_SHARE_COMPANY_MSTRRepository extends PagingAndSortingRepository<FUND_SHARE_COMPANY_MSTR, Long>{
	
	@Query("SELECT T FROM FUND_SHARE_COMPANY_MSTR T ORDER BY T.SVC_NAME ASC ")
	public Page<FUND_SHARE_COMPANY_MSTR> findAllFUND_SHARE_COMPANY_MSTR_ASC(Pageable page);
	
	@Query("SELECT T FROM FUND_SHARE_COMPANY_MSTR T ORDER BY T.SVC_NAME DESC ")
	public Page<FUND_SHARE_COMPANY_MSTR> findAllFUND_SHARE_COMPANY_MSTR_DESC(Pageable page);
	
	@Query("SELECT T FROM FUND_SHARE_COMPANY_MSTR T ORDER BY T.SVC_NAME ASC ")
	public List<FUND_SHARE_COMPANY_MSTR> findAllFUND_SHARE_COMPANY_MSTR_ASC();
	
	@Query("SELECT T FROM FUND_SHARE_COMPANY_MSTR T ORDER BY T.SVC_NAME DESC ")
	public List<FUND_SHARE_COMPANY_MSTR> findAllFUND_SHARE_COMPANY_MSTR_DESC();
	
	/*
	 * Without paging
	 */
	
	
	
	@Query("SELECT T FROM FUND_SHARE_COMPANY_MSTR T  where T.WMS_STATUS like 'Approved'  ORDER BY T.SVC_NAME ASC  ")
	public List<FUND_SHARE_COMPANY_MSTR> findAllAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC();
	
	@Query("SELECT T FROM FUND_SHARE_COMPANY_MSTR T  where T.WMS_STATUS like 'Not Approved'  ORDER BY T.SVC_NAME ASC  ")
	public List<FUND_SHARE_COMPANY_MSTR> findAllUNAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC();
	
	/*
	 * With paging
	 */
	
	
	
	@Query("SELECT T FROM FUND_SHARE_COMPANY_MSTR T  where T.WMS_STATUS like 'Approved'  ORDER BY T.SVC_NAME ASC  ")
	public Page<FUND_SHARE_COMPANY_MSTR> findAllAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC(Pageable page);
	
	@Query("SELECT T FROM FUND_SHARE_COMPANY_MSTR T  where T.WMS_STATUS like 'Not Approved'  ORDER BY T.SVC_NAME ASC  ")
	public Page<FUND_SHARE_COMPANY_MSTR> findAllUNAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC(Pageable page);
	
	/*
	 * Search
	 * 
	 */
	
//	@Query("SELECT T FROM FUND_SHARE_COMPANY_MSTR T WHERE  lower(T.SVC_NAME) like lower(concat('%',:param,'%'))   ORDER BY T.SVC_NAME ASC")
//	public Page<FUND_SHARE_COMPANY_MSTR> findAllFUND_SHARE_COMPANY_MSTR_ASC_SEARCH(@Param("param") String param,Pageable page);
//	
//	@Query("SELECT T FROM FUND_SHARE_COMPANY_MSTR T  WHERE  lower(T.SVC_NAME) like lower(concat('%',:param,'%'))   AND T.WMS_STATUS like 'Not Approved'  ORDER BY T.SVC_NAME ASC")
//	public Page<FUND_SHARE_COMPANY_MSTR> findAllUNAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC_SEARCH(@Param("param") String param,Pageable page);
//	
//
//	@Query("SELECT T FROM FUND_SHARE_COMPANY_MSTR T  WHERE  lower(T.SVC_NAME) like lower(concat('%',:param,'%'))   AND T.WMS_STATUS like ' Approved'  ORDER BY T.SVC_NAME ASC")
//	public Page<FUND_SHARE_COMPANY_MSTR> findAllAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC_SEARCH(@Param("param") String param,Pageable page);

	
	@Query("SELECT T FROM FUND_SHARE_COMPANY_MSTR T WHERE"
			+ " lower(T.SVC_NAME) like lower(concat('%',:param,'%')) "
			+ " OR T.SVC_CURR_CODE IN (:curlist)"
			+ " OR T.SVC_EXCHANGE IN (:exclist)"
			+ " OR T.SVC_CUST_COUNTRY IN (:custlist)"
			+ "  ORDER BY T.SVC_NAME ASC")
	public Page<FUND_SHARE_COMPANY_MSTR> findAllFUND_SHARE_COMPANY_MSTR_ASC_SEARCH(@Param("param") String param,
			@Param("curlist")  List<String>  curlist,
			@Param("custlist") List<Integer> custlist,
			@Param("exclist") List<String> exclist,
			Pageable page);
	
	@Query("SELECT T FROM FUND_SHARE_COMPANY_MSTR T  WHERE ( lower(T.SVC_NAME) like lower(concat('%',:param,'%'))   "
			+ " OR T.SVC_CURR_CODE IN (:curlist)"
			+ " OR T.SVC_EXCHANGE IN (:exclist)"
			+ " OR T.SVC_CUST_COUNTRY IN (:custlist)"
			+ ") AND T.WMS_STATUS like 'Not Approved'  ORDER BY T.SVC_NAME ASC")
	public Page<FUND_SHARE_COMPANY_MSTR> findAllUNAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC_SEARCH(@Param("param") String param,
			@Param("curlist") List<String>  curlist,
			@Param("custlist") List<Integer> custlist,
			@Param("exclist") List<String> exclist,
			Pageable page);
	

	@Query("SELECT T FROM FUND_SHARE_COMPANY_MSTR T  WHERE ( lower(T.SVC_NAME) like lower(concat('%',:param,'%'))  "
			+ " OR T.SVC_CURR_CODE IN (:curlist)"
			+ " OR T.SVC_EXCHANGE IN (:exclist)"
			+ " OR T.SVC_CUST_COUNTRY IN (:custlist)"
			+ ") AND T.WMS_STATUS like ' Approved'  ORDER BY T.SVC_NAME ASC")
	public Page<FUND_SHARE_COMPANY_MSTR> findAllAPPROVEDFUND_SHARE_COMPANY_MSTR_ASC_SEARCH(@Param("param") String param,
			@Param("curlist")  List<String>  curlist,
			@Param("custlist") List<Integer> custlist,
			@Param("exclist") List<String> exclist,
			Pageable page);

	
	/*
	 * Duplicate Checking
	 * 
	 */
	@Query("SELECT COUNT(T) FROM FUND_SHARE_COMPANY_MSTR T  where lower(T.SVC_BLOM_CODE) like lower(concat(:UK_BLOM_CODE,'%')) ")
	public Long find_UK_BLOM_CODE(@Param("UK_BLOM_CODE") String UK_BLOM_CODE);
	
	@Query("SELECT COUNT(T) FROM FUND_SHARE_COMPANY_MSTR T  where lower(T.SVC_CUSIP) like lower(concat(:UK_CUSIP,'%'))")
	public Long find_UK_CUSIP(@Param("UK_CUSIP") String UK_CUSIP);
	
	@Query("SELECT COUNT(T) FROM FUND_SHARE_COMPANY_MSTR T  where lower(T.SVC_ISIN_CODE) like lower(concat(:UK_ISIN_CODE,'%'))")
	public Long find_UK_ISIN_CODE(@Param("UK_ISIN_CODE") String UK_ISIN_CODE);
	
	@Query("SELECT COUNT(T) FROM FUND_SHARE_COMPANY_MSTR T  where lower(T.SVC_RETUER_CODE) like lower(concat(:UK_RETUER_CODE,'%'))")
	public Long find_UK_RETUER_CODE(@Param("UK_RETUER_CODE") String UK_RETUER_CODE);
	
	@Query("SELECT COUNT(T) FROM FUND_SHARE_COMPANY_MSTR T  where lower(T.SVC_EXEC_CODE) like lower(concat(:UK_TICKER,'%'))")
	public Long find_UK_TICKER(@Param("UK_TICKER") String UK_TICKER);
	
	@Query("SELECT COUNT(T) FROM FUND_SHARE_COMPANY_MSTR T  where T.SVC_SHARES_ISSUE = :UK_SEDOL ")
	public Long find_UK_SEDOL(@Param("UK_SEDOL") Long UK_SEDOL);
	
	
	/*
	 * Duplicate Checking
	 * 
	 */
	@Query("SELECT COUNT(T) FROM FUND_SHARE_COMPANY_MSTR T  where lower(T.SVC_BLOM_CODE) like lower(concat(:UK_BLOM_CODE,'%')) AND T.SVC_CODE!=:SVC_CODE")
	public Long find_UK_BLOM_CODE(@Param("UK_BLOM_CODE") String UK_BLOM_CODE,@Param("SVC_CODE") Long SVC_CODE);
	
	@Query("SELECT COUNT(T) FROM FUND_SHARE_COMPANY_MSTR T  where lower(T.SVC_CUSIP) like lower(concat(:UK_CUSIP,'%'))  AND T.SVC_CODE!=:SVC_CODE")
	public Long find_UK_CUSIP(@Param("UK_CUSIP") String UK_CUSIP,@Param("SVC_CODE") Long SVC_CODE);
	
	@Query("SELECT COUNT(T) FROM FUND_SHARE_COMPANY_MSTR T  where lower(T.SVC_ISIN_CODE) like lower(concat(:UK_ISIN_CODE,'%'))  AND T.SVC_CODE!=:SVC_CODE")
	public Long find_UK_ISIN_CODE(@Param("UK_ISIN_CODE") String UK_ISIN_CODE,@Param("SVC_CODE") Long SVC_CODE);
	
	@Query("SELECT COUNT(T) FROM FUND_SHARE_COMPANY_MSTR T  where lower(T.SVC_RETUER_CODE) like lower(concat(:UK_RETUER_CODE,'%'))  AND T.SVC_CODE!=:SVC_CODE")
	public Long find_UK_RETUER_CODE(@Param("UK_RETUER_CODE") String UK_RETUER_CODE,@Param("SVC_CODE") Long SVC_CODE);
	
	@Query("SELECT COUNT(T) FROM FUND_SHARE_COMPANY_MSTR T  where lower(T.SVC_EXEC_CODE) like lower(concat(:UK_TICKER,'%')) AND T.SVC_CODE!=:SVC_CODE")
	public Long find_UK_TICKER(@Param("UK_TICKER") String UK_TICKER,@Param("SVC_CODE") Long SVC_CODE);
	
	@Query("SELECT COUNT(T) FROM FUND_SHARE_COMPANY_MSTR T  where T.SVC_SHARES_ISSUE=:UK_SEDOL  AND T.SVC_CODE!=:SVC_CODE")
	public Long find_UK_SEDOL(@Param("UK_SEDOL") Long UK_SEDOL,@Param("SVC_CODE") Long SVC_CODE);
	
	@Query("SELECT T.SVC_CODE FROM FUND_SHARE_COMPANY_MSTR T WHERE LOWER(T.SVC_NAME) LIKE LOWER(CONCAT('%',:cname,'%')) ORDER BY T.SVC_NAME DESC ")
	public List<Long> findAllFUND_SHARE_COMPANY_MSTR_DESC(@Param("cname")String cname);

	/*
	 * List of all Search
	 */
	
	@Query("SELECT T.SVC_CODE FROM FUND_SHARE_COMPANY_MSTR T WHERE  lower(T.SVC_NAME) like lower(concat('%',:param,'%'))")
	public List<Long> find_Ids_FUND_SHARE_COMPANY_MSTR_ASC_SEARCH(@Param("param") String param);
	
}
