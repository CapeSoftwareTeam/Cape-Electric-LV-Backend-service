package com.capeelectric.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capeelectric.exception.CompanyDetailsException;
import com.capeelectric.exception.InspectionException;
import com.capeelectric.exception.InstalReportException;
import com.capeelectric.exception.ObservationException;
import com.capeelectric.exception.PdfException;
import com.capeelectric.exception.PeriodicTestingException;
import com.capeelectric.exception.RegistrationException;
import com.capeelectric.exception.SummaryException;
import com.capeelectric.exception.SupplyCharacteristicsException;
import com.capeelectric.model.Summary;
import com.capeelectric.model.SummaryComment;
import com.capeelectric.service.SummaryService;
import com.capeelectric.util.SendReplyComments;

/**
 * 
 * @author capeelectricsoftware
 *
 */
@RestController()
@RequestMapping("/api/v2")
public class SummaryController {
	
	private static final Logger logger = LoggerFactory.getLogger(SummaryController.class);

	@Autowired
	private SummaryService summaryService;
	
	@Autowired
	private SendReplyComments sendReplyComments;

	@PostMapping("/addSummary")
	public ResponseEntity<String> addSummary(@RequestBody Summary summary) throws SummaryException, CompanyDetailsException, InstalReportException, SupplyCharacteristicsException, InspectionException, PeriodicTestingException, Exception, ObservationException, PdfException {
		logger.debug("Started addSummary function userName: {},siteId : {}", summary.getUserName(),summary.getSiteId());
		summaryService.addSummary(summary);
		logger.debug("Ended addSummary function");
		return new ResponseEntity<String>("Summary Details Successfully Saved", HttpStatus.CREATED);

	}
	
	@GetMapping("/retrieveSummary/{userName}/{siteId}")
	public ResponseEntity<List<Summary>> retrieveSummary(@PathVariable String userName,@PathVariable Integer siteId) throws SummaryException {
		logger.debug("called retrieveSummary function userName: {},siteId : {}", userName,siteId);
		return new ResponseEntity<List<Summary>>(summaryService.retrieveSummary(userName,siteId), HttpStatus.OK);
	}
	
	@GetMapping("/retrieveSummary/{siteId}")
	public ResponseEntity<Summary> retrieveSummaryForSiteId(@PathVariable Integer siteId) throws SummaryException {
		logger.debug("called retrieveSummary function siteId : {}",siteId);
		return new ResponseEntity<Summary>(summaryService.retrieveSummary(siteId), HttpStatus.OK);
	}
	
	@PutMapping("/updateSummary/{superAdminFlag}")
	public ResponseEntity<String> updateSummary(@RequestBody Summary summary,@PathVariable Boolean superAdminFlag) throws SummaryException, CompanyDetailsException, InstalReportException, 
	      SupplyCharacteristicsException, InspectionException, PeriodicTestingException, Exception, ObservationException, PdfException {
		logger.debug("called updateSummary function UserName : {},SiteId : {},SummaryId : {}", summary.getUserName(),
				summary.getSiteId(), summary.getSummaryId());
		summaryService.updateSummary(summary,superAdminFlag);
		logger.debug("Ended updateSummary function");
		return new ResponseEntity<String>("Summary successfully "
				+ (superAdminFlag?"Submitted":"Updated"), HttpStatus.OK);
	}


	@PostMapping("/sendSummaryComments/{userName}/{siteId}")
	public ResponseEntity<Void> sendComments(@PathVariable String userName, @PathVariable Integer siteId,
			@RequestBody SummaryComment summaryComment) throws SummaryException, RegistrationException, Exception {
		logger.debug("called sendComments function UserName : {},SiteId : {}", userName, siteId);
		summaryService.sendComments(userName, siteId, summaryComment);
		logger.debug("Ended sendComments function");
		sendReplyComments.sendComments(userName);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping("/replySummaryComments/{inspectorUserName}{siteId}")
	public ResponseEntity<Void> replyComments(@PathVariable String inspectorUserName, @PathVariable Integer siteId,
			@RequestBody SummaryComment summaryComment) throws SummaryException, RegistrationException, Exception {
		logger.debug("called replyComments function inspectorUserName : {},SiteId : {}", inspectorUserName, siteId);
		String viewerUserName = summaryService.replyComments(inspectorUserName, siteId, summaryComment);
		logger.debug("Ended replyComments function");
		if (viewerUserName != null) {
			sendReplyComments.replyComments(inspectorUserName, viewerUserName);	
			logger.debug("Ended sendReplyComments function");
		} else {
			logger.error("No viewer userName avilable");
			throw new SummaryException("No viewer userName avilable");
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping("/approveSummaryComments/{userName}/{siteId}")
	public ResponseEntity<Void> approveComments(@PathVariable String userName, @PathVariable Integer siteId,
			@RequestBody SummaryComment summaryComment) throws SummaryException, RegistrationException, Exception {
		logger.debug("called approveComments function UserName : {},SiteId : {}", userName, siteId);
		summaryService.approveComments(userName, siteId, summaryComment);
		logger.debug("Ended approveComments function");
		sendReplyComments.approveComments(userName, summaryComment.getApproveOrReject());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
