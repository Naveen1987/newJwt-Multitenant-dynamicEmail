package com.fynisys.repository.crm;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.crm.RE_INVESTOR;
@Repository
public interface RE_INVESTORRepository extends PagingAndSortingRepository<RE_INVESTOR, Long>{
	
@Query("SELECT T.RI_INVESTOR_CODE FROM RE_INVESTOR T WHERE LOWER(T.RI_INVESTOR_NAME) LIKE LOWER(CONCAT('%',:cname,'%')) ORDER BY T.RI_INVESTOR_CODE")
public List<Long> find_ids_Client(@Param("cname")String cname);

@Query("SELECT T FROM RE_INVESTOR T WHERE LOWER(T.RI_INVESTOR_NAME) LIKE LOWER(CONCAT('%',:cname,'%'))   OR LOWER(T.RI_CORPORATE_NAME) LIKE LOWER(CONCAT('%',:cname,'%'))   OR LOWER(T.RI_INVESTOR_TYPE) LIKE LOWER(CONCAT('%',:cname,'%')) OR LOWER(T.WMS_STATUS) LIKE LOWER(CONCAT('%',:cname,'%')) ORDER BY T.RI_INVESTOR_NAME, T.RI_CORPORATE_NAME")
public Page<RE_INVESTOR> findAllInvestor_Serach(@Param("cname")String cname,Pageable page);

@Query("SELECT T FROM RE_INVESTOR T ORDER BY T.RI_INVESTOR_NAME, T.RI_CORPORATE_NAME")
public Page<RE_INVESTOR> findAllInvestor(Pageable page);

@Query("SELECT T FROM RE_INVESTOR T WHERE T.RI_INVESTOR_CODE IS NOT NULL ORDER BY T.RI_INVESTOR_NAME, T.RI_CORPORATE_NAME")
public List<RE_INVESTOR> findAllInvestorWithCode();

}
