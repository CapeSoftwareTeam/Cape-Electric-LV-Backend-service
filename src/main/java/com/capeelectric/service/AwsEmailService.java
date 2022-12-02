package com.capeelectric.service;


import java.net.URISyntaxException;

import org.springframework.stereotype.Service;



/**
 * 
 * @author capeelectricsoftware
 *
 */
public interface AwsEmailService {

	
	public void sendEmail(String email, String content);
	
	public void sendEmailToAdmin(String content) throws URISyntaxException;
	
	public void sendEmailForComments(String toEmail, String ccEmail, String content) throws URISyntaxException;
	
	public void sendEmailPDF(String userName, Integer siteId, int count, String keyname);
	
	public void sendEmailForOTPGeneration(String email, String content) throws URISyntaxException;

}
