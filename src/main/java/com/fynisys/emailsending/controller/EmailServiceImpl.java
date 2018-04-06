package com.fynisys.emailsending.controller;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService{

 
    private  JavaMailSenderImpl mailSender;
       
    private final Logger logger=LoggerFactory.getLogger("Email Service");
    
    public void sendSimpleMsg( String to, String subject, String textHtml,
    	 String host,
    	 Integer port,
    	 String userName,
    	 String password) throws MessagingException {
    	 mailSender = new JavaMailSenderImpl();
    	 mailSender.setHost(host);
         mailSender.setPort(port);
         mailSender.setUsername(userName);
         mailSender.setPassword(password);
         Properties javaMailProperties = new Properties();
         javaMailProperties.put("mail.smtp.starttls.enable", "true");
         javaMailProperties.put("mail.smtp.auth", "true");
         javaMailProperties.put("mail.transport.protocol", "smtp");
         // javaMailProperties.put("mail.debug", "true");
         mailSender.setJavaMailProperties(javaMailProperties);
    	 sendSimpleMessage(to,subject,textHtml);
    }
    
    public void sendSimpleMessage(
    	 String to, String subject, String textHtml) throws MessagingException {
    	 MimeMessage message = mailSender.createMimeMessage();
    	 MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
         message.setContent(textHtml, "text/html");
         helper.setTo(to);
         helper.setSubject(subject);
         logger.info("Msg Templete [To: {"+to+"} ,Subject:{"+subject+"}, body:{"+textHtml+"}, status {Sending.....}");
         mailSender.send(message); 
         logger.info("Msg Templete [To: {"+to+"} ,Subject:{"+subject+"}, body:{"+textHtml+"}, status{Sent}");
  }
    
    /*
     * Old Code Reading from properties file
     * (non-Javadoc)
     * @see com.fynisys.emailsending.controller.EmailService#sendSimpleMessage(java.lang.String, java.lang.String, java.lang.String)
     */
//  @Autowired
//  public JavaMailSender emailSender;
//    public void sendSimpleMessage(
//       	 String to, String subject, String textHtml) throws MessagingException {
//       	 MimeMessage message = emailSender.createMimeMessage();
//       	 MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
//            message.setContent(textHtml, "text/html");
//            helper.setTo(to);
//            helper.setSubject(subject);
//            logger.info("Msg Templete [To: {"+to+"} ,Subject:{"+subject+"}, body:{"+textHtml+"}, status {Sending.....}");
//            emailSender.send(message); 
//            logger.info("Msg Templete [To: {"+to+"} ,Subject:{"+subject+"}, body:{"+textHtml+"}, status{Sent}");
//     }
    //Basic msg
    /* public void sendSimpleMessage(
       String to, String subject, String text) {
         SimpleMailMessage message = new SimpleMailMessage(); 
         message.setTo(to); 
         message.setSubject(subject); 
         message.setText(text);
         emailSender.send(message);
     
     }*/
    
   /* public void sendMessageWithAttachment(
      String to, String subject, String text, String pathToAttachment) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        FileSystemResource file 
          = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("Invoice", file);
     	emailSender.send(message);
      
    }
    */
}