
package com.capeelectric.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capeelectric.exception.FinalReportException;
import com.capeelectric.model.FinalReport;
import com.capeelectric.model.Site;
import com.capeelectric.service.impl.FinalReportServiceImpl;

/**
 * 
 * @author capeelectricsoftware
 *
 */
@RestController
@RequestMapping("/api/v2")
public class FinalReportController {

	private static final Logger logger = LoggerFactory.getLogger(FinalReportController.class);

	@Autowired
	FinalReportServiceImpl finalReportServiceImpl;

	@GetMapping("/retrieveListOfSite/{userName}")
	public ResponseEntity<List<Site>> retrieveListOfSite(@PathVariable String userName) throws FinalReportException {
		logger.info("FinalReportAPI_started retrieveSiteDetails function UserName: {}", userName);
		
		return new ResponseEntity<List<Site>>(finalReportServiceImpl.retrieveListOfSite(userName),
				HttpStatus.OK);

	}

	@GetMapping("/retrieveReport/{userName}/{siteId}")
	public ResponseEntity<Optional<FinalReport>> retrieveReports(@PathVariable String userName,
			@PathVariable Integer siteId) throws FinalReportException {
		logger.info("FinalReportAPI_started retrieveFinalReport function userName: {},siteId : {}", userName, siteId);
		
		return new ResponseEntity<Optional<FinalReport>>(finalReportServiceImpl.retrieveFinalReport(userName, siteId),
				HttpStatus.OK);

	}
	
	@GetMapping("/retrieveReport/{siteId}")
	public ResponseEntity<Optional<FinalReport>> retrieveReportsForSiteId(@PathVariable Integer siteId) throws FinalReportException {
		logger.info("FinalReportAPI_started retrieveFinalReport function siteId : {}", siteId);
		
		return new ResponseEntity<Optional<FinalReport>>(finalReportServiceImpl.retrieveFinalReport(siteId),
				HttpStatus.OK);

	}
	
	@GetMapping("/retrieveAllSites")
	public ResponseEntity<List<Site>> retrieveAllSites() throws FinalReportException{
		logger.info("FinalReportAPI_started retrieveAllSites");
			
		return new ResponseEntity<List<Site>>(finalReportServiceImpl.retrieveAllSites(),
						HttpStatus.OK);
	}

}
