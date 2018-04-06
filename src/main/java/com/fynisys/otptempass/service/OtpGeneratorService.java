package com.fynisys.otptempass.service;

import java.util.Calendar;
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
import com.fynisys.model.FUND_USER_PASSWORD;
import com.fynisys.repository.FUND_USER_PASSWORDRepository;

@Service
public class OtpGeneratorService
{
	@Value("${opt.length}")
	private int len;
    
	@Autowired
	FUND_USER_PASSWORDRepository fUND_USER_PASSWORDRepository;
	
	@Autowired
	TempPassGeneratorService tempPassGeneratorService;
	
	@Autowired
	FWMS_COMPANY_DEFAULTRepository fWMS_COMPANY_DEFAULTRepository;
	
//	@Autowired
//	EmailService emailService;
	
	@Autowired
	EmailServiceImpl emailServiceImpl;
	
	private static final Logger logger = LoggerFactory.getLogger("OTP Generator Service ");
	
	public String OTP()
    {
        // Using numeric values
        String numbers = "0123456789";
 
        // Using random method
        Random rndm_method = new Random();
 
        char[] otp = new char[len];
 
        for (int i = 0; i < len; i++)
        {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otp[i] =
             numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        logger.info("OTP generated:"+String.valueOf(otp));
        return String.valueOf(otp);
    }
	
	public boolean sendOTP(String to,String subject,FUND_USERS user) throws MessagingException {
		logger.info("OTP: Otp preparing...");
		String Otp=OTP();
		FUND_USER_PASSWORD fundPass=new FUND_USER_PASSWORD();
		/*String newId=tempPassGeneratorService.randomNumber();
		FUND_USER_PASSWORD fundPassexit=fUND_USER_PASSWORDRepository.findOne(newId);
		while(true){
				if(fundPassexit==null){
					fundPass.setFID(newId);
					break;
			}
			newId=tempPassGeneratorService.randomNumber();
			fundPassexit=fUND_USER_PASSWORDRepository.findOne(newId);
		}*/
		fundPass.setFID(Calendar.getInstance().getTimeInMillis()+"");
		fundPass.setFCDATE(Calendar.getInstance());
		fundPass.setFOPD(Otp);
		fundPass.setSVC_UID(user.getSVC_UID());
		fUND_USER_PASSWORDRepository.save(fundPass);
		String htmlMsg = "<body>"
				+ "<div style='display:table; vertical-align:middle'>"
				+ "<div style='display:table-cell;width:600px;height:400px;background:#fff;box-shadow:0 1px 4px 0 rgba(0, 0, 0, 0.19);'>"
				+ "<div style='background: #FF9F00;color:#fff;text-align: center; padding: 15px;font-size: 16px;'>"
				+ "<p style='text-align: center;'>Fynisys Verification Code	</p>"
				+ "</div><div style='padding:10px;font-size:14px;'>"
				+ "Dear Fynisys User,"
				+ "<br><br/>This email address is being used to recover a Fynisys Account. "
				+ "If you initiated the recovery process, it is asking you to enter the numeric verification code that appears below."
				+ "<br/>If you did not initiate an account recovery process and have a Fynisys Account associated with this email address, "
				+ "it is possible that someone else is trying to access your account. "
				+ "Do not forward or give this code to anyone. "
				+ "Please visit your account's sign-in to ensure that your account is safe."
				+ "	<div><br/><br/><label style='border-radius: 4px;background-color: #348EDA;color:#fff;font-weight: bold;padding: 10px;text-align: center;'>"
				+ "OTP&nbsp;:&nbsp;"+Otp+"</label></div>"
						+ "</div><br/><br/><b>Yours sincerely</b>,"
						+ "<br/>The Fynisys Team</div></div></body>";		
		
	/*
	 *New COde 
	 */
		FWMS_COMPANY_DEFAULT company=fWMS_COMPANY_DEFAULTRepository.findOne(user.getFCD_DEFAULT_FCODE());
		emailServiceImpl.sendSimpleMsg(to, subject, htmlMsg, company.getSMTP_SERVER(), new Integer(company.getSMTP_PORT()), company.getSMTP_USER_NAME(), company.getSMTP_PASSWORD());
		/*
		 * Old Code
		 */
//		emailService.sendSimpleMessage(to, subject, htmlMsg);
		logger.info("OTP:"+Otp+" Otp Sent.");
		return true;
	}
   
	public boolean validOTP(Calendar genDate){
		logger.info(genDate.get(Calendar.HOUR_OF_DAY)+":"+genDate.get(Calendar.MINUTE)+":"+genDate.get(Calendar.SECOND));
		Calendar currentime=Calendar.getInstance();
		int     t1;
	    int     t2;
	    t1 = (int) (currentime.getTimeInMillis() % (24*60*60*1000L));
	    t2 = (int) (genDate.getTimeInMillis() % (24*60*60*1000L));
	    logger.info(currentime.get(Calendar.HOUR_OF_DAY)+":"+currentime.get(Calendar.MINUTE)+":"+currentime.get(Calendar.SECOND));
	    long diff=((t1-t2)/(60*1000L));
	    logger.info("Time Different:"+diff);
	    return (diff>10||diff<0?false:true);   
	}
	
}