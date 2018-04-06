package com.fynisys.menutask.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.FUND_MENU_GROUP;

@Repository
public interface FUND_MENU_GROUPRepository extends PagingAndSortingRepository<FUND_MENU_GROUP, Long> {

	@Query("select t from FUND_MENU_GROUP t where t.FMG_GROUPNAME like :groupname")
	public FUND_MENU_GROUP getGroup(@Param("groupname")String groupname);
}
