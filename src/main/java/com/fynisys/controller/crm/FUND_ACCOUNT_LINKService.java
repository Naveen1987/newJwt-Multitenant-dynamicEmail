package com.fynisys.controller.crm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fynisys.controller.crm.beans.AccountLink;
import com.fynisys.model.crm.FUND_ACCOUNT_LINK;
import com.fynisys.model.crm.RE_INVESTOR;
import com.fynisys.repository.crm.FUND_ACCOUNT_LINKRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class FUND_ACCOUNT_LINKService.
 */
@Service
public class FUND_ACCOUNT_LINKService {
	
	/** The UN D ACCOUN T LINK repository. */
	@Autowired
	FUND_ACCOUNT_LINKRepository fUND_ACCOUNT_LINKRepository;
	
	/** The Constant logger. */
	private static final Logger logger=LoggerFactory.getLogger("FUND ACCOUNT LINK Service");
 
 /**
  * Save.
  *
  * @param AccountLink the account link
  * @param fc the fc
  */
 public void save(List<Map<String,Object>> AccountLink,RE_INVESTOR fc) {
		if(AccountLink!=null) {
			if(AccountLink.isEmpty()==false) {
				AccountLink.forEach(link->{
					FUND_ACCOUNT_LINK fal=null;
					Object RIL_ACCOUNT_CODE=link.get("RIL_ACCOUNT_CODE");
					if(RIL_ACCOUNT_CODE!=null) {
						fal=new FUND_ACCOUNT_LINK();
						fal.setRIL_ACCOUNT_CODE(new Long(RIL_ACCOUNT_CODE.toString()));
					}else {
						fal=new FUND_ACCOUNT_LINK();
					}
					if(fal!=null) {
						Object RIL_MODULE=link.get("RIL_MODULE");
						Object RIL_LINK_MCODE=link.get("RIL_LINK_MCODE");
						Object RIL_LINK_SCODE=link.get("RIL_LINK_SCODE");
						Object RIL_REMARKS=link.get("RIL_REMARKS");
						fal.setRIL_LINK_MCODE(RIL_LINK_MCODE.toString());
						fal.setRIL_LINK_SCODE(RIL_LINK_SCODE.toString());
						fal.setRIL_MODULE(RIL_MODULE.toString());
						fal.setRIL_REMARKS(RIL_REMARKS.toString());
						fal.setRI_WMS_CODE(fc);
						fal=fUND_ACCOUNT_LINKRepository.save(fal);
						if(fal!=null) {
							logger.info("Account link info Update:"+fal.getRIL_ACCOUNT_CODE());
						}
					}
				});
			}
		}
	}
	
 /**
  * Account links.
  *
  * @param RI_WMS_CODE the ri wms code
  * @return the list
  */
 public List<AccountLink> accountLinks(Long RI_WMS_CODE) {
	 List<AccountLink> accounts=new ArrayList<>();
	 fUND_ACCOUNT_LINKRepository.getAccountLinks(RI_WMS_CODE).forEach(link->{
		 AccountLink ac=new AccountLink();
		 ac.setRIL_ACCOUNT_CODE(link.getRIL_ACCOUNT_CODE());
		 ac.setRIL_LINK_MCODE(link.getRIL_LINK_MCODE());
		 ac.setRIL_LINK_SCODE(link.getRIL_LINK_SCODE());
		 ac.setRIL_MODULE(link.getRIL_MODULE());
		 ac.setRIL_REMARKS(link.getRIL_REMARKS());
		 accounts.add(ac);
	 });
	 return accounts;
}
	 
}
