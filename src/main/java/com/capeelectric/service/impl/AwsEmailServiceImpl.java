package com.capeelectric.service.impl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.capeelectric.config.AWSLVConfig;
import com.capeelectric.model.EmailContent;
import com.capeelectric.service.AwsEmailService;
import com.capeelectric.util.Utility;


/**
 * 
 * @author capeelectricsoftware
 *
 */
@Service
public class AwsEmailServiceImpl implements AwsEmailService {
	
	private static final Logger logger = LoggerFactory.getLogger(AwsEmailServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AWSLVConfig awsConfiguration;
	
	@Value("${app.web.domain}")
	private String webUrl;

	@Override
	public void sendEmail(String email, String content) {
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		restTemplate.exchange(awsConfiguration.getSendEmail() + email + "/" +content,
				HttpMethod.GET, null, String.class);

		logger.debug("Cape-Electric-AWS-Email service Response was successful");
		
	}

	@Override
	public void sendEmailToAdmin(String content) throws URISyntaxException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		URI uri = new URI(awsConfiguration.getSendEmailToAdmin());
		EmailContent emailContent = new EmailContent();
		emailContent.setContentDetails(content);
		RequestEntity<EmailContent> requestEntity = new RequestEntity<>(emailContent, headers, HttpMethod.PUT, uri);
		ParameterizedTypeReference<EmailContent> typeRef = new ParameterizedTypeReference<EmailContent>() {};

		restTemplate.exchange(requestEntity, typeRef);
		logger.debug("Cape-Electric-AWS-Email service Response was successful");
		
	}

	@Override
	public void sendEmailForComments(String toEmail, String ccEmail, String content) throws URISyntaxException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		URI uri = new URI(awsConfiguration.getSendEmailForComments() + toEmail + "/"+ ccEmail);
		EmailContent emailContent = new EmailContent();
		emailContent.setContentDetails(content);
		RequestEntity<EmailContent> requestEntity = new RequestEntity<>(emailContent, headers, HttpMethod.PUT, uri);
		ParameterizedTypeReference<EmailContent> typeRef = new ParameterizedTypeReference<EmailContent>() {};

		ResponseEntity<EmailContent> responseEntity = restTemplate.exchange(requestEntity, typeRef);
		logger.debug("Cape-Electric-AWS-Email service Response was successful"+responseEntity.getStatusCode());

	}

	@Override
	public void sendEmailPDF(String userName, Integer id, int count, String keyname) {
		String type = "LV";
		restTemplate.exchange(awsConfiguration.getSendEmailWithPDF() + userName + "/"+type+"/"+ id +"/"+ keyname,
				HttpMethod.GET, null, String.class);

		logger.debug("Cape-Electric-AWS-Email service Response was successful");
		
	}
	
	@Override
	public void sendEmailForOTPGeneration(String email, String content) throws URISyntaxException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		URI uri = new URI(awsConfiguration.getSendEmailForApproval() + email );
		EmailContent emailContent = new EmailContent();
		emailContent.setContentDetails(content);
		RequestEntity<EmailContent> requestEntity = new RequestEntity<>(emailContent, headers, HttpMethod.PUT, uri);
		ParameterizedTypeReference<EmailContent> typeRef = new ParameterizedTypeReference<EmailContent>() {};

		ResponseEntity<EmailContent> responseEntity = restTemplate.exchange(requestEntity, typeRef);
		logger.debug("Cape-Electric-AWS-Email service Response was successful"+responseEntity.getStatusCode());
	}
	
	private void sendMailToAdmin(String inspectorName, Integer registerId) throws MessagingException, MalformedURLException, URISyntaxException {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(registerId).toUri();
		String resetUrl = Utility.getSiteURL(uri.toURL());
		sendEmailToAdmin("The " + inspectorName
				+ " has modified or updated his application type access, please approve or reject by logging to SOLVE admin portal"
				+ ". You can login to admin Portal with this link " + "\n"
				+ (resetUrl.contains("localhost:5000")
						? resetUrl.replace("http://localhost:5000", "http://localhost:4200")
						: "https://admin."+webUrl)
				+ "/admin");
		logger.debug("AwsEmailService call Successfully Ended");

	}
}
