package com.fynisys.utilities;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fynisys.country.repository.FUND_COUNTRIESRepository;
import com.fynisys.model.FUND_COUNTRIES;
import com.fynisys.model.clienttype.FWMS_NATIONALITY;
import com.fynisys.model.parameters.FUND_CURRENCY_MSTR;
import com.fynisys.model.parameters.FUND_EXCHANGE_MSTR;
import com.fynisys.model.parameters.FUND_INDUSTRY_MSTR;
import com.fynisys.model.parameters.FUND_INSTRUMENT_MSTR;
import com.fynisys.model.parameters.FWMS_INDEX;
import com.fynisys.repository.clienttype.FWMS_NATIONALITYRepository;
import com.fynisys.repository.parameters.FUND_CURRENCY_MSTRRepositroy;
import com.fynisys.repository.parameters.FUND_EXCHANGE_MSTRRepositroy;
import com.fynisys.repository.parameters.FUND_INDUSTRY_MSTRRepositroy;
import com.fynisys.repository.parameters.FUND_INSTRUMENT_MSTRRepositroy;
import com.fynisys.repository.parameters.FWMS_INDEXRepository;

@Component
public class StockParameters {
	@Autowired
	FUND_EXCHANGE_MSTRRepositroy fUND_EXCHANGE_MSTRRepositroy;
	@Autowired
	FUND_INDUSTRY_MSTRRepositroy fUND_INDUSTRY_MSTRRepositroy;
	@Autowired
	FUND_INSTRUMENT_MSTRRepositroy fUND_INSTRUMENT_MSTRRepositroy;
	@Autowired
	FWMS_INDEXRepository fWMS_INDEXRepository;
	@Autowired
	FUND_COUNTRIESRepository fUND_COUNTRIESRepository;
	@Autowired
	FUND_CURRENCY_MSTRRepositroy fUND_CURRENCY_MSTRRepositroy;
	@Autowired
	FWMS_NATIONALITYRepository fWMS_NATIONALITYRepository;
	
	private static final Logger logger=LoggerFactory.getLogger("Stock Parameter");
	
	public FUND_CURRENCY_MSTR getFUND_CURRENCY_MSTR(Object id) {
		try {
			FUND_CURRENCY_MSTR fem=fUND_CURRENCY_MSTRRepositroy.findOne(new Integer(id.toString().trim()));
		return fem;
		}catch (Exception e) {
			logger.error("CURRENCY="+e.getMessage());
		}
		return null;
	}
	
	public List<FUND_CURRENCY_MSTR> getFUND_CURRENCY_MSTR_Searching(Object id) {
		try {
			if(id!=null) {
			List<FUND_CURRENCY_MSTR> fem=fUND_CURRENCY_MSTRRepositroy.findAllCurrencySearching(id.toString().trim());
			return fem;
			}
		}catch (Exception e) {
			logger.error("CURRENCY="+e.getMessage());
		}
		return null;
	}
	
	public List<FUND_CURRENCY_MSTR> getFUND_CURRENCY_MSTR_APPROVED_Searching(Object id) {
		try {
			if(id!=null) {
			List<FUND_CURRENCY_MSTR> fem=fUND_CURRENCY_MSTRRepositroy.findAllAPPROVEDCurrencySearching(id.toString().trim());
			return fem;
			}
		}catch (Exception e) {
			logger.error("CURRENCY="+e.getMessage());
		}
		return null;
	}
	
	public List<FUND_CURRENCY_MSTR> getFUND_CURRENCY_MSTR_UNAPPROVED_Searching(Object id) {
		try {
			if(id!=null) {
			List<FUND_CURRENCY_MSTR> fem=fUND_CURRENCY_MSTRRepositroy.findAllUNAPPROVEDCurrencySearching(id.toString().trim());
			return fem;
			}
		}catch (Exception e) {
			logger.error("CURRENCY="+e.getMessage());
		}
		return null;
	}
	
	
	public FUND_EXCHANGE_MSTR getFUND_EXCHANGE_MSTR(Object id) {
		try {
		FUND_EXCHANGE_MSTR fem=fUND_EXCHANGE_MSTRRepositroy.findOne(new Integer(id.toString().trim()));
		return fem;
		}catch (Exception e) {
			logger.error("Exchange="+e.getMessage());
		}
		return null;
	}
	
	public FUND_INDUSTRY_MSTR getFUND_INDUSTRY_MSTR(Object id) {
		try {
			FUND_INDUSTRY_MSTR fem=fUND_INDUSTRY_MSTRRepositroy.findOne(new Integer(id.toString().trim()));
			return fem;
			}catch (Exception e) {
				logger.error("Sector="+e.getMessage());
			}
			return null;
	}

	public FUND_INSTRUMENT_MSTR getFUND_INSTRUMENT_MSTR(Object id) {
		try {
			FUND_INSTRUMENT_MSTR fem=fUND_INSTRUMENT_MSTRRepositroy.findOne(new Integer(id.toString().trim()));
		
			return fem;
			}catch (Exception e) {
				logger.error("Asset="+e.getMessage());
			}
			return null;
	}

	public FWMS_INDEX getFWMS_INDEX(Object id) {
		try {
			FWMS_INDEX fem=fWMS_INDEXRepository.findOne(new Integer(id.toString().trim()));
			return fem;
			}catch (Exception e) {
				logger.error("Index="+e.getMessage());
			}
			return null;
	}

	public FUND_COUNTRIES getFUND_COUNTRIES(Object id) {
		try {
			FUND_COUNTRIES fem=fUND_COUNTRIESRepository.findOne(new Long(id.toString().trim()));
			return fem;
			}catch (Exception e) {
				logger.error("Countries="+e.getMessage());
			}
			return null;
	}
	
	public FWMS_NATIONALITY getNationality(Object id) {
		try {
			FWMS_NATIONALITY fem=fWMS_NATIONALITYRepository.findOne(new Long(id.toString().trim()));
			return fem;
			}catch (Exception e) {
				logger.error("Nationality="+e.getMessage());
			}
			return null;
	}

	public List<Integer> getAllAssetsId(Object id) {
		try {
			if(id!=null) {
				List<Integer>fem=fUND_INSTRUMENT_MSTRRepositroy.findAllAssets(id.toString());
				if(fem.size()==0) {
					fem.add(new Integer(-1));
				}
				return fem;	
			}else {
				return null;
			}
			}catch (Exception e) {
				logger.error("Asset="+e.getMessage());
			}
			return null;
	}
	

	public List<Integer> getAllCurrencyId(Object id) {
		try {
			if(id!=null) {
				List<Integer>fem=fUND_CURRENCY_MSTRRepositroy.findAllIds(id.toString());
				if(fem.size()==0) {
					fem.add(new Integer(-1));
				}
				return fem;	
			}else {
				return null;
			}
			}catch (Exception e) {
				logger.error("Currency="+e.getMessage());
			}
			return null;
	}
	
	public List<Long> getAllCountriesId(Object id) {
		try {
			if(id!=null) {
				List<Long>fem=fUND_COUNTRIESRepository.findAllIds(id.toString());
				if(fem.size()==0) {
					fem.add(new Long(-1));
				}
				return fem;	
			}else {
				return null;
			}
			}catch (Exception e) {
				logger.error("Country="+e.getMessage());
			}
			return null;
	}

	
	public List<Integer> getAllExchangeId(Object id) {
		try {
			if(id!=null) {
				List<Integer>fem=fUND_EXCHANGE_MSTRRepositroy.findAllids(id.toString());
				if(fem.size()==0) {
					fem.add(new Integer(-1));
				}
				return fem;	
			}else {
				return null;
			}
			}catch (Exception e) {
				logger.error("Country="+e.getMessage());
			}
			return null;
	}

}
