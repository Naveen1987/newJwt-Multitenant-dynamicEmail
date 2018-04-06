package com.fynisys.menutask.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fynisys.model.FUND_MENU_TASK;

@Repository
public interface FUND_MENU_TASKRepository extends PagingAndSortingRepository<FUND_MENU_TASK, Long> {
	
	@Query("select t from FUND_MENU_TASK t where t.FMT_MENUNAME like :menuname and t.fund_menu_group.FMG_GROUPID=:groupid")
	public FUND_MENU_TASK getMenu(@Param("menuname")String menuname,@Param("groupid") Long groupid);	
}
