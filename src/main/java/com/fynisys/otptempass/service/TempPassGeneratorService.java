package com.fynisys.otptempass.service;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fynisys.companyprofile.model.FWMS_COMPANY_DEFAULT;
import com.fynisys.companyprofile.repository.FWMS_COMPANY_DEFAULTRepository;
//import com.fynisys.emailsending.controller.EmailService;
import com.fynisys.emailsending.controller.EmailServiceImpl;
import com.fynisys.model.FUND_USERS;
import com.fynisys.repository.FUND_USERSRepository;

@Service
public class TempPassGeneratorService {
	
	@Value("${temppass.length}")
	private int length;
	
//	@Autowired
//	EmailService emailService;
	
	@Autowired
	EmailServiceImpl emailServiceImpl;
	
	@Autowired
	FWMS_COMPANY_DEFAULTRepository fWMS_COMPANY_DEFAULTRepository;
	
	@Autowired
	FUND_USERSRepository fUND_USERSRepository;	
	
	 	public  final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	    public  final String lower = upper.toLowerCase(Locale.ROOT);

	    public  final String digits = "0123456789";

	    public  final String alphanum = upper + lower + digits;

	    private final Random random=new SecureRandom();
	
	private static final Logger logger = LoggerFactory.getLogger("Temp Password Generator Service");
	 /**
     * Generate a random string.
     */
    public String tempPassword() {
    	 char[] symbols=alphanum.toCharArray();
    	 char[] buf=new char[length];
        for (int idx = 0; idx < buf.length; ++idx) {
        	buf[idx] = symbols[random.nextInt(symbols.length)];
        	}
        logger.info("Temporary Password:"+String.valueOf(buf));
        return String.valueOf(buf);
    }

    public String randomNumber() {
   	 char[] symbols=alphanum.toCharArray();
   	 char[] buf=new char[15];
       for (int idx = 0; idx < buf.length; ++idx) {
       	buf[idx] = symbols[random.nextInt(symbols.length)];
       	}
       logger.info("Random Id:"+String.valueOf(buf));
       return String.valueOf(buf);
   }
    
    public boolean sendTempPass(FUND_USERS user) throws MessagingException {
    	String temppass=tempPassword();
    	if(user!=null){
			user.setSVU_FLAG(0);
			user.setSVU_USER_PASSWORD(temppass);
			user.setSVU_LAST_CHANGE(Calendar.getInstance());
			try{
				user=fUND_USERSRepository.save(user);
				if(user!=null){
					logger.info("Temporary Password preparing...");
					String htmlMsg = "<body>"
			                +"<p>Dear: "+user.getSVU_NAME()+"<br/>Temporary Paasword : <b>"+temppass+"</b></p>" 
			                + "<br/><p style='color:red;font-weight:bold'>Note - Please use this password for Reset Password."+
			                      "Password is confidential, do not share this  with anyone. Valid only for 10 minutes</p></body>";
					
					/*
					 * Neew COde
					 */
					FWMS_COMPANY_DEFAULT company=fWMS_COMPANY_DEFAULTRepository.findOne(user.getFCD_DEFAULT_FCODE());
					emailServiceImpl.sendSimpleMsg(user.getSVU_EMAIL(), "Temporary Password", htmlMsg, company.getSMTP_SERVER(), new Integer(company.getSMTP_PORT()), company.getSMTP_USER_NAME(), company.getSMTP_PASSWORD());
					/*
					 * Old code
					 */
//					emailService.sendSimpleMessage(user.getSVU_EMAIL(), "Temporary Password", htmlMsg);
					logger.info("Temporary Password: Sent.");
				}
			}
			catch (Exception e) {
				logger.error(e.getMessage());
				return false;
			}
			
    	}
		return true;
	}
    
    public boolean invalidPassword(Calendar cCal){   	
    	long diff=Calendar.getInstance().getTimeInMillis()-cCal.getTimeInMillis();
    	long diffHours = (diff / (24 * 60 * 60 * 1000));
    	logger.info("Time Difference:"+diffHours+" Days");
    	return ((diffHours>0)?true:false);
    }
}

/*First Way*/
/*
@Service
public class TempPassGeneratorService {
	
	@Value("${temppass.length}")
	private int len;
	
	
	private static final Logger logger = LoggerFactory.getLogger("Temp Password Generator Service");
	
	//first Method
	
	public String tempPassword()
	    {
	        
	 
	        
	        A strong password has Cap_chars, Lower_chars,
	        Numeric value and symbols. So we are using all of
	        Them to generate our password
	        
	        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
	        String numbers = "0123456789";
	        
	         * You can use these two line when you want to add symbols in temp password 
	        String symbols = "!@#$%^&*_=+-/.?<>)";
	        String values = Capital_chars + Small_chars + numbers + symbols;
	         * 
	       
	        String values = Capital_chars + Small_chars + numbers;
	 
	        // Using random method
	        Random rndm_method = new Random();
	 
	        char[] password = new char[len];
	 
	        for (int i = 0; i < len; i++)
	        {
	            // Use of charAt() method : to get character value
	            // Use of nextInt() as it is scanning the value as int
	            password[i] =
	              values.charAt(rndm_method.nextInt(values.length()));
	            
	        }
	        logger.info("Temp password:-"+String.valueOf(password));
	        return String.valueOf(password);
	    }
	}
*/