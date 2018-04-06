package com.fynisys.utilities;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fynisys.model.clienttype.FUND_CLIENT_DOCUMENTS_TYPE2;
import com.fynisys.model.clienttype.FUND_MAR_CLIENT_TYPE;
import com.fynisys.model.crm.RE_INVESTOR;
import com.fynisys.model.people.FUND_ACCOUNT_MSTR;
import com.fynisys.model.people.FUND_BROKER_MSTR;
import com.fynisys.model.people.FUND_BROKER_MSTR_2;
import com.fynisys.repository.clienttype.FUND_CLIENT_DOCUMENTS_TYPE2Repository;
import com.fynisys.repository.clienttype.FUND_MAR_CLIENT_TYPERespository;
import com.fynisys.repository.crm.RE_INVESTORRepository;
import com.fynisys.repository.people.FUND_ACCOUNT_MSTRRepository;
import com.fynisys.repository.people.FUND_BROKER_MSTRRepository;
import com.fynisys.repository.people.FUND_BROKER_MSTR_2Repository;

@Component
public class LevelParameterValidator {

	@Autowired
	FUND_BROKER_MSTR_2Repository fUND_BROKER_MSTR_2Repository; //custodian
	@Autowired
	FUND_ACCOUNT_MSTRRepository fUND_ACCOUNT_MSTRRepository;   //Fund_Account
	@Autowired
	FUND_BROKER_MSTRRepository fUND_BROKER_MSTRRepository;     //Broker
	@Autowired
	FUND_MAR_CLIENT_TYPERespository fUND_MAR_CLIENT_TYPERespository;                 //Client Type
	@Autowired
	RE_INVESTORRepository rE_INVESTORRepository;                                   //Client
	@Autowired
	FUND_CLIENT_DOCUMENTS_TYPE2Repository fUND_CLIENT_DOCUMENTS_TYPE2Repository;
	
	private Logger logger=LoggerFactory.getLogger("Level Validator");
	
	
	
	public FUND_CLIENT_DOCUMENTS_TYPE2 getDocumentType(Long code) {
		if(code!=null) {
			FUND_CLIENT_DOCUMENTS_TYPE2 doctype=fUND_CLIENT_DOCUMENTS_TYPE2Repository.findOne(code);
			return doctype;
		}else {
			return null;
		}
		
	}
	
	
	public boolean IsCustodianApproved(Long SVB_CODE) {
		if(SVB_CODE!=null) {
			FUND_BROKER_MSTR_2 mstr=fUND_BROKER_MSTR_2Repository.findOne(SVB_CODE);
			if(mstr!=null) {
				if(mstr.getIV_APPROVE_UID()!=null) {
					logger.info("Custodian approved");
					return true;
				}
				else {
					logger.info("Custodian Not approved");
					return false;
				}
			}else {
				logger.info("Custodian Not found");
				return false;
			}
		}
		else {
			logger.info("SVB_CODE of custodian is null");
			return false;
		}
	}
	public boolean IsBrokerApproved(Long SVB_CODE) {
		if(SVB_CODE!=null) {
			FUND_BROKER_MSTR mstr=fUND_BROKER_MSTRRepository.findOne(SVB_CODE);
			if(mstr!=null) {
				if(mstr.getIV_APPROVE_UID()!=null) {
					logger.info("Broker approved");
					return true;
				}
				else {
					logger.info("Broker Not approved");
					return false;
				}
			}else {
				logger.info("Broker Not found");
				return false;
			}
		}
		else {
			logger.info("SVB_CODE of Broker is null");
			return false;
		}
	}
	
	public boolean IsFundAccunt_GLCodeApproved(Long SVB_CODE) {
		if(SVB_CODE!=null) {
			FUND_ACCOUNT_MSTR mstr=fUND_ACCOUNT_MSTRRepository.findOne(SVB_CODE);
			if(mstr!=null) {
				if(mstr.getIV_APPROVE_UID()!=null) {
					logger.info("FUND Account approved");
					return true;
				}
				else {
					logger.info("FUND Account  Not approved");
					return false;
				}
			}else {
				logger.info("FUND Account  Not found");
				return false;
			}
		}
		else {
			logger.info("SVB_CODE of FUND Account is null");
			return false;
		}
	}
	
	public boolean IsCLIENT_TYPEApproved(Long SVB_CODE) {
		if(SVB_CODE!=null) {
			FUND_MAR_CLIENT_TYPE mstr=fUND_MAR_CLIENT_TYPERespository.findOne(SVB_CODE);
			if(mstr!=null) {
				if(mstr.getIV_APPROVE_UID()!=null) {
					logger.info("Client Type approved");
					return true;
				}
				else {
					logger.info("Client Type Not approved");
					return false;
				}
			}else {
				logger.info("Client Type Not found");
				return false;
			}
		}
		else {
			logger.info("ID of Client Type is null");
			return false;
		}
	}

	public boolean IsCLIENTApproved(Long SVB_CODE) {
		if(SVB_CODE!=null) {
			RE_INVESTOR mstr=rE_INVESTORRepository.findOne(SVB_CODE);
			if(mstr!=null) {
				if(mstr.getIV_APPROVE_UID()!=null) {
					logger.info("Client approved");
					return true;
				}
				else {
					logger.info("Client Not approved");
					return false;
				}
			}else {
				logger.info("Client Not found");
				return false;
			}
		}
		else {
			logger.info("ID of Client is null");
			return false;
		}
	}

	public FUND_BROKER_MSTR_2 getCustodianApproved(Long SVB_CODE) {
		if(SVB_CODE!=null) {
			FUND_BROKER_MSTR_2 mstr=fUND_BROKER_MSTR_2Repository.findOne(SVB_CODE);
			if(mstr!=null) {
				logger.info("Custodian found");
				return mstr;
			}else {
				logger.info("Custodian Not found");
				return null;
			}
		}
		else {
			logger.info("SVB_CODE of custodian is null");
			return null;
		}
	}
	public FUND_BROKER_MSTR getBrokerApproved(Long SVB_CODE) {
		if(SVB_CODE!=null) {
			FUND_BROKER_MSTR mstr=fUND_BROKER_MSTRRepository.findOne(SVB_CODE);
			if(mstr!=null) {
				logger.info("Broker found");
				return mstr;
			}else {
				logger.info("Broker Not found");
				return null;
			}
		}
		else {
			logger.info("SVB_CODE of Broker is null");
			return null;
		}
	}
	
	public FUND_ACCOUNT_MSTR getFundAccunt_GLCodeApproved(Long SVB_CODE) {
		if(SVB_CODE!=null) {
			FUND_ACCOUNT_MSTR mstr=fUND_ACCOUNT_MSTRRepository.findOne(SVB_CODE);
			if(mstr!=null) {
				logger.info("FUND Account");
				return mstr;
				
			}else {
				logger.info("FUND Account  Not found");
				return null;
			}
		}
		else {
			logger.info("SVB_CODE of FUND Account is null");
			return null;
		}
	}
	
	public FUND_MAR_CLIENT_TYPE getCLIENT_TYPEApproved(Long SVB_CODE) {
		if(SVB_CODE!=null) {
			FUND_MAR_CLIENT_TYPE mstr=fUND_MAR_CLIENT_TYPERespository.findOne(SVB_CODE);
			return mstr;
		}
		else {
			logger.info("ID of Client Type is null");
			return null;
		}
	}

	public RE_INVESTOR getCLIENTApproved(Long SVB_CODE) {
		if(SVB_CODE!=null) {
			RE_INVESTOR mstr=rE_INVESTORRepository.findOne(SVB_CODE);
			if(mstr!=null) {
				logger.info("Client Found");
				return mstr;
			}else {
				logger.info("Client Not found");
				return null;
			}
		}
		else {
			logger.info("ID of Client is null");
			return null;
		}
	}

	
	/*
	 * Return List of All
	 * 
	 */
	
	public List<Long> get_ids_clients(String param){
		List<Long> ids=rE_INVESTORRepository.find_ids_Client(param);
		if(ids.size()==0) {
			ids.add(new Long(-1));
		}
		return ids;
	}
	
	public List<Long> get_ids_clientType(String param){
		List<Long> ids=fUND_MAR_CLIENT_TYPERespository.find_ids_FUND_MAR_CLIENT_TYPE(param);
		if(ids.size()==0) {
			ids.add(new Long(-1));
		}
		return ids;
	}
	
	public List<Long> get_ids_Brokers(String param){
		List<Long> ids=fUND_BROKER_MSTRRepository.find_Ids_BrokerSearching(param);
		if(ids.size()==0) {
			ids.add(new Long(-1));
		}
		return ids;
	}
	
	public List<Long> get_ids_Custodian(String param){
		List<Long> ids=fUND_BROKER_MSTR_2Repository.find_Ids_CustodianSearching(param);
		if(ids.size()==0) {
			ids.add(new Long(-1));
		}
		return ids;
	}
	
	public List<Long> get_ids_FundAccunt_GLCode(String param){
		List<Long> ids=fUND_ACCOUNT_MSTRRepository.find_Ids_FundAccountSearching(param);
		if(ids.size()==0) {
			ids.add(new Long(-1));
		}
		return ids;
	}
	
}
