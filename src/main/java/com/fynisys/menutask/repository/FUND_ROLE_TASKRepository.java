package com.fynisys.menutask.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.FUND_ROLE_TASK;

@Repository
public interface FUND_ROLE_TASKRepository extends PagingAndSortingRepository<FUND_ROLE_TASK, Long> {

	@Query("select t from FUND_ROLE_TASK t where t.fund_roles.FRL_ROLEID=:roleid")
	public List<FUND_ROLE_TASK> getMenuOnRole(@Param("roleid") Long roleid);
	
	@Query("select t from FUND_ROLE_TASK t where t.fund_roles.FRL_ROLEID=:roleid and t.fund_menu_task.FMT_MENUTASKID=:menuid")
	public FUND_ROLE_TASK getMenuOnRole(@Param("roleid") Long roleid,@Param("menuid") Long menuid);
	
	
	
}
