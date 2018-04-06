package com.fynisys.repository.funds;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fynisys.model.funds.FUND_MSTR;


@Repository
public interface FUND_MSTRRepository extends PagingAndSortingRepository<FUND_MSTR, Long>{
	
	@Query("SELECT T FROM FUND_MSTR T ORDER BY T.SVC_NAME ASC ")
	public Page<FUND_MSTR> findAllFUND_MSTR_ASC(Pageable page);
	
	@Query("SELECT T FROM FUND_MSTR T ORDER BY T.SVC_NAME DESC ")
	public Page<FUND_MSTR> findAllFUND_MSTR_DESC(Pageable page);
	
	@Query("SELECT T FROM FUND_MSTR T ORDER BY T.SVC_NAME ASC ")
	public List<FUND_MSTR> findAllFUND_MSTR_ASC();
	
	@Query("SELECT T FROM FUND_MSTR T ORDER BY T.SVC_NAME DESC ")
	public List<FUND_MSTR> findAllFUND_MSTR_DESC();
	
	
	@Query("SELECT T FROM FUND_MSTR T WHERE lower(T.SVC_NAME) LIKE lower(:cname) ")
	public List<FUND_MSTR> findAllFUND_MSTR_NAME(@Param("cname")String cname);
	
	@Query("SELECT T FROM FUND_MSTR T WHERE lower(T.SVC_SHORT_NAME) LIKE lower(:cname) ")
	public List<FUND_MSTR> findAllFUND_MSTR_SHORTNAME(@Param("cname")String cname);
	
	
	/*
	 * Without paging
	 */
	@Query("SELECT T FROM FUND_MSTR T  where T.WMS_STATUS like 'Approved'  ORDER BY T.SVC_NAME ASC  ")
	public List<FUND_MSTR> findAllAPPROVEDFUND_MSTR_ASC();
	
	@Query("SELECT T FROM FUND_MSTR T  where T.WMS_STATUS like 'Not Approved'  ORDER BY T.SVC_NAME ASC  ")
	public List<FUND_MSTR> findAllUNAPPROVEDFUND_MSTR_ASC();
	
	/*
	 * With paging
	 */
	@Query("SELECT T FROM FUND_MSTR T  where T.WMS_STATUS like 'Approved'  ORDER BY T.SVC_NAME ASC  ")
	public Page<FUND_MSTR> findAllAPPROVEDFUND_MSTR_ASC(Pageable page);
	
	@Query("SELECT T FROM FUND_MSTR T  where T.WMS_STATUS like 'Not Approved'  ORDER BY T.SVC_NAME ASC  ")
	public Page<FUND_MSTR> findAllUNAPPROVEDFUND_MSTR_ASC(Pageable page);
	
	
	/*
	 * Search
	 */
	
	@Query("SELECT T FROM FUND_MSTR T WHERE lower(T.SVC_NAME) LIKE lower(concat('%',:cname,'%'))")
	public List<FUND_MSTR> findAllFUND_MSTR_LIST(@Param("cname")String cname);
	
	@Query("SELECT T FROM FUND_MSTR T WHERE lower(T.SVC_NAME) LIKE lower(concat('%',:cname,'%'))")
	public Page<FUND_MSTR> findAllFUND_MSTR_ASC_SEARCH(@Param("cname")String cname,Pageable page);
	
	@Query("SELECT T FROM FUND_MSTR T WHERE  lower(T.SVC_NAME) LIKE lower(concat('%',:cname,'%')) AND T.WMS_STATUS like 'Approved'  ORDER BY T.SVC_NAME ASC  ")
	public Page<FUND_MSTR> findAllAPPROVEDFUND_MSTR_ASC_SEARCH(@Param("cname")String cname,Pageable page);
	
	@Query("SELECT T FROM FUND_MSTR T WHERE  lower(T.SVC_NAME) LIKE lower(concat('%',:cname,'%')) AND T.WMS_STATUS like 'Not Approved'  ORDER BY T.SVC_NAME ASC  ")
	public Page<FUND_MSTR> findAllUNAPPROVEDFUND_MSTR_ASC_SEARCH(@Param("cname")String cname,Pageable page);
	
	
	
	@Query("SELECT T FROM FUND_MSTR T WHERE lower(T.SVC_NAME) LIKE lower(concat('%',:cname,'%')) OR T.IV_ENTER_UID IN (:users)")
	public List<FUND_MSTR> findAllFUND_MSTR_LIST(@Param("users")List<String> usres,@Param("cname")String cname);
	
	@Query("SELECT T FROM FUND_MSTR T WHERE lower(T.SVC_NAME) LIKE lower(concat('%',:cname,'%'))  OR T.IV_ENTER_UID IN (:users)")
	public Page<FUND_MSTR> findAllFUND_MSTR_ASC_SEARCH(@Param("users")List<String> users,@Param("cname")String cname,Pageable page);
	
	@Query("SELECT T FROM FUND_MSTR T WHERE  lower(T.SVC_NAME) LIKE lower(concat('%',:cname,'%'))  OR T.IV_ENTER_UID IN (:users) AND T.WMS_STATUS like 'Approved'  ORDER BY T.SVC_NAME ASC  ")
	public Page<FUND_MSTR> findAllAPPROVEDFUND_MSTR_ASC_SEARCH(@Param("users")List<String> users,@Param("cname")String cname,Pageable page);
	
	@Query("SELECT T FROM FUND_MSTR T WHERE  lower(T.SVC_NAME) LIKE lower(concat('%',:cname,'%')) OR T.IV_ENTER_UID IN (:users) AND T.WMS_STATUS like 'Not Approved'  ORDER BY T.SVC_NAME ASC  ")
	public Page<FUND_MSTR> findAllUNAPPROVEDFUND_MSTR_ASC_SEARCH(@Param("users")List<String> users,@Param("cname")String cname,Pageable page);
	
	/*
	 * Page search
	 */
	
	@Query("SELECT T.SVC_CODE FROM FUND_MSTR T WHERE lower(T.SVC_NAME) LIKE lower(concat('%',:cname,'%')) ORDER BY T.SVC_CODE ASC ")
	public List<Long> find_ids_FUND_MSTR_LIST(@Param("cname")String cname);

 /*
  * Get Only Ids
  * 
  */
	
	@Query("SELECT T.SVC_CODE FROM FUND_MSTR T WHERE lower(T.SVC_NAME) LIKE lower(concat('%',:cname,'%'))")
	public List<Long> fund_ids_Search(@Param("cname")String cname);


}

