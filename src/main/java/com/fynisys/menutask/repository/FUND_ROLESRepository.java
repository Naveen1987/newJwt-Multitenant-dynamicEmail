package com.fynisys.menutask.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.FUND_ROLES;



@Repository
public interface FUND_ROLESRepository extends PagingAndSortingRepository<FUND_ROLES, Long> {

	@Query("select t from FUND_ROLES t where t.FRL_ROLENAME like :rolename")
	public FUND_ROLES findByRoleName(@Param("rolename")String rolename);
	
	@Query("select t from FUND_ROLES t where lower(t.FRL_ROLENAME) like lower(concat(:rolename,'%'))")
	public Page<FUND_ROLES> findByRoleNameSearch(@Param("rolename")String rolename,Pageable page);
	
	@Query("select t from FUND_ROLES t where lower(t.FRL_ROLENAME) like lower(concat(:rolename,'%'))")
	public List<FUND_ROLES> findByRoleNameSearch(@Param("rolename")String rolename);
	
	@Query("select t from FUND_ROLES t order by t.FRL_ROLENAME asc")
	public Iterable<FUND_ROLES> ordeByFRL_ROLENAME();
	
	@Query("select t from FUND_ROLES t where lower(t.FRL_ROLENAME) like lower(:rolename) or lower(t.FRL_ROLENAME) like upper(:rolename)")
	public List<FUND_ROLES> getRoleCheck(@Param("rolename")String rolename);
	
	@Query("select t from FUND_ROLES t order by t.FRL_ROLENAME asc")
	public Page<FUND_ROLES> ordeByFRL_ROLENAME(Pageable page);
}
