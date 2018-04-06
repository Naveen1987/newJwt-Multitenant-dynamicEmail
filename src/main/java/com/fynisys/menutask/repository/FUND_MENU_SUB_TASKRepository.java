package com.fynisys.menutask.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fynisys.model.FUND_MENU_SUB_TASK;

@Repository
public interface FUND_MENU_SUB_TASKRepository extends PagingAndSortingRepository<FUND_MENU_SUB_TASK, Long> {
	@Query("select t from FUND_MENU_SUB_TASK t where t.FMST_MENUSUBNAME like :submenu and t.fun_menu_task.FMT_MENUTASKID=:menuid")
	public FUND_MENU_SUB_TASK findMenuSubTaskByMenuId(@Param("submenu") String submenu,@Param("menuid")Long menuid);
}
