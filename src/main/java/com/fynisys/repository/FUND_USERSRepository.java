package com.fynisys.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.FUND_USERS;

@Repository
public interface FUND_USERSRepository extends PagingAndSortingRepository<FUND_USERS, String> {

	
	@Query("select t from FUND_USERS t where t.SVU_USER_NAME like :user and t.SVU_USER_PASSWORD like :password")
	public FUND_USERS getUser(@Param("user")String user,@Param("password")String password);
	
	@Query("select t from FUND_USERS t where lower(t.SVU_USER_NAME) like lower(:user) or lower(t.SVU_USER_NAME) like upper(:user)")
	public FUND_USERS getUserCheck(@Param("user")String user);
	
	@Query("select t from FUND_USERS t where t.SVU_USER_NAME like :user")
	public FUND_USERS getLogin(@Param("user")String user);
	
	@Query("select t from FUND_USERS t where t.SVU_USER_NAME like :user")
	public FUND_USERS findByUSER_NAME(@Param("user")String user);
	
	@Query("select t from FUND_USERS t where t.SVU_USER_NAME like :user and t.SVU_USER_PASSWORD like :password and t.SVU_FLAG=:flag")
	public FUND_USERS getUserReset(@Param("user")String user,@Param("password")String password,@Param("flag")Integer flag);
	
	@Query("select t from FUND_USERS t order by  t.SVU_NAME asc")
	public Iterable<FUND_USERS> orderBySVU_NAME();
	
	@Query("select t from FUND_USERS t order by  t.SVU_NAME asc")
	public Page<FUND_USERS> orderBySVU_NAME(Pageable page);
	
	@Query("select t from FUND_USERS t where lower(t.SVU_NAME) like lower(concat('%',:user,'%'))")
	public Iterable<FUND_USERS> findByNAME(@Param("user")String user);
	
	@Query("select t.SVC_UID from FUND_USERS t where lower(t.SVU_NAME) like lower(concat('%',:user,'%'))")
	public List<String> findByNAMEList(@Param("user")String user);
	
	@Query("select t from FUND_USERS t where t.SVU_USER_NAME like :user")
	public FUND_USERS findByCompany(@Param("user")String user);
	
	
}
