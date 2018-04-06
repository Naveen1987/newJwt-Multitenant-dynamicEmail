package com.fynisys.menutask.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.FUND_ROLE_SUB_TASK;



@Repository
public interface FUND_ROLE_SUB_TASKRepository extends PagingAndSortingRepository<FUND_ROLE_SUB_TASK, Long> {

	@Query("select t from FUND_ROLE_SUB_TASK t where t.fund_roles.FRL_ROLEID=:roleid")
	public List<FUND_ROLE_SUB_TASK> getSubMenuOnRole(@Param("roleid") Long roleid);
	
	@Query("select t from FUND_ROLE_SUB_TASK t where t.fund_roles.FRL_ROLEID=:roleid and t.fund_menu_sub_task.FMST_MENUSUBTASKID=:submenuid")
	public FUND_ROLE_SUB_TASK getSubMenuOnRole(@Param("roleid") Long roleid,@Param("submenuid") Long submenuid);
	
	@Query("select t from FUND_ROLE_SUB_TASK t where t.fund_roles.FRL_ROLEID=:roleid and t.FMT_MENUTASKID=:menuid")
	public List<FUND_ROLE_SUB_TASK> getSubMenuOnRoleAndMenuid(@Param("roleid") Long roleid,@Param("menuid") Long menuid);
}
