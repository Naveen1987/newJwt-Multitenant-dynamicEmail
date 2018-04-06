package com.fynisys.activitylogs;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fynisys.model.FUND_USERS;
import com.fynisys.model.FUND_USER_LOG;
import com.fynisys.repository.FUND_USER_LOGRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class ActivityLogger.
 */
@Component
public class ActivityLogger {
	
	/** The UN D USE R LOG repository. */
	@Autowired
	FUND_USER_LOGRepository fUND_USER_LOGRepository;
	
	/** The Constant logger. */
	private static final Logger logger=LoggerFactory.getLogger("Activity Logs");
	
	/**
	 * Write log.
	 *
	 * @param fuser the fuser
	 * @param SNO the sno
	 * @param SVL_SCREEN the svl screen
	 * @param SVL_DESC the svl desc
	 * @param action the action
	 * @return true, if successful
	 */
	public boolean writeLog(FUND_USERS fuser,Object SNO,Object SVL_SCREEN,Object SVL_DESC, String action) {
		FUND_USER_LOG ful=new FUND_USER_LOG();
		ful.setSNO((SNO!=null)?SNO.toString().trim():"");
		ful.setSVC_UID(fuser.getSVC_UID());
		ful.setSVL_USERID(fuser.getSVU_NAME());
		ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
		ful.setSVL_TTYPE(action);
		ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
		ful.setSVL_DATE(Calendar.getInstance());
		try {
		ful=fUND_USER_LOGRepository.save(ful);
		return true;
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}
	
	/**
	 * Write log.
	 *
	 * @param fuser the fuser
	 * @param SVL_SCREEN the svl screen
	 * @param SVL_DESC the svl desc
	 * @param action the action
	 * @return true, if successful
	 */
	public boolean writeLog(FUND_USERS fuser,Object SVL_SCREEN,Object SVL_DESC, String action) {
		FUND_USER_LOG ful=new FUND_USER_LOG();
		ful.setSVC_UID(fuser.getSVC_UID());
		ful.setSVL_USERID(fuser.getSVU_NAME());
		ful.setSVL_DESC((SVL_DESC!=null)?SVL_DESC.toString().trim():"");
		ful.setSVL_TTYPE(action);
		ful.setSVL_SCREEN((SVL_SCREEN!=null)?SVL_SCREEN.toString().trim():"");
		ful.setSVL_DATE(Calendar.getInstance());
		try {
		ful=fUND_USER_LOGRepository.save(ful);
		return true;
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}
}
