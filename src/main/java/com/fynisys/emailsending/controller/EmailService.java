package com.fynisys.emailsending.controller;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
	 public void sendSimpleMessage(String to, String subject, String text) throws MessagingException;
}
