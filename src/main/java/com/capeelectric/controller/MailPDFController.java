package com.capeelectric.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capeelectric.exception.InspectionException;
import com.capeelectric.exception.InstalReportException;
import com.capeelectric.exception.PeriodicTestingException;
import com.capeelectric.exception.SummaryException;
import com.capeelectric.exception.SupplyCharacteristicsException;
import com.capeelectric.service.AwsEmailService;

@RestController
@RequestMapping("api/v2")

public class MailPDFController {

	private static final Logger logger = LoggerFactory.getLogger(MailPDFController.class);
	
	@Autowired
	private AwsEmailService awsEmailService;

	@GetMapping("/sendPDFinMail/{userName}/{siteId}/{siteName}")
	public ResponseEntity<byte[]> sendFinalPDF(@PathVariable String userName, @PathVariable Integer siteId, @PathVariable String siteName)
			throws InstalReportException, SupplyCharacteristicsException, InspectionException, PeriodicTestingException,
			SummaryException, Exception {
		logger.info("called sendFinalPDF function userName: {},siteId : {}, siteName : {}", userName,siteId,siteName);

		awsEmailService.sendEmailPDF(userName, siteId, siteId, siteName);

		return new ResponseEntity<byte[]>(HttpStatus.OK);
	}
}
