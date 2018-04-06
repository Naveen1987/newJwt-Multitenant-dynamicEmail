package com.fynisys.repository.crm;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fynisys.model.crm.RE_CLIENT_RM_LINK_HEAD;

@Repository
public interface RE_CLIENT_RM_LINK_HEADRepository  extends PagingAndSortingRepository<RE_CLIENT_RM_LINK_HEAD, Long>{

}
