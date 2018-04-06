package com.fynisys.utilities;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.fynisys.model.FUND_USERS;
import com.fynisys.repository.FUND_USERSRepository;


@Repository
public class FundUserValidate {
	@Autowired
	FUND_USERSRepository fund_UserRepository;

	
	public FUND_USERS isValid(String userid)
	{
		FUND_USERS fuser= fund_UserRepository.findByUSER_NAME(userid);
		if(fuser!=null)
		{
			return fuser;
		}
		else
		{
		
		return null;
		}
		
	}
	
	public FUND_USERS getUserName(String userid)
	{
		FUND_USERS fuser= fund_UserRepository.findOne(userid);
		if(fuser!=null)
		{
			return fuser;
		}
		else
		{
		
		return null;
		}
		
	}
	public List<String> getUserIds(Object user){
		if(user!=null) {
		List<String> list=fund_UserRepository.findByNAMEList(user.toString());
		return list;
		}else {
			return null;
		}
		
	}
}
