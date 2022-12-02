package com.capeelectric.controller;

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
import com.capeelectric.exception.RegistrationException;
import com.capeelectric.model.ReportDetails;
import com.capeelectric.model.ReportDetailsComment;
import com.capeelectric.service.InstalReportService;
import com.capeelectric.util.SendReplyComments;

/**
 * @author capeelectricsoftware
 *
 */
@RestController
@RequestMapping("/api/v2")
public class InstallReportController {

	private static final Logger logger = LoggerFactory.getLogger(InstallReportController.class);

	@Autowired
	private InstalReportService instalReportService;
	
	@Autowired
	private SendReplyComments sendReplyComments;

	@PostMapping("/addInstalReport")
	public ResponseEntity<String> addInstallationReport(@RequestBody ReportDetails reportDetails)
			throws InstalReportException, CompanyDetailsException {
		logger.info("called addInstallationReport function UserName : {}, SiteId", reportDetails.getUserName(),
				reportDetails.getSiteId());
		instalReportService.addInstallationReport(reportDetails);
		return new ResponseEntity<String>("Basic Information Successfully Saved", HttpStatus.CREATED);
	}

	@GetMapping("/retrieveInstalReport/{userName}/{siteId}")
	public ResponseEntity<ReportDetails> retrieveInstallationReport(@PathVariable String userName,
			@PathVariable Integer siteId)
			throws InstalReportException, InspectionException {
		logger.info("called retrieveInstallationReport function UserName: {}, SiteId : {}", userName, siteId);
		return new ResponseEntity<ReportDetails>(instalReportService.retrieveInstallationReport(userName,siteId),
				HttpStatus.OK);
	}
	
	@GetMapping("/retrieveInstalReport/{siteId}")
	public ResponseEntity<ReportDetails> retrieveInstallationReportForSiteId(@PathVariable Integer siteId)
			throws InstalReportException, InspectionException {
		logger.info("called retrieveInstallationReportForSiteId function  SiteId : {}", siteId);
		return new ResponseEntity<ReportDetails>(instalReportService.retrieveInstallationReport(siteId),
				HttpStatus.OK);
	}
	
	@PutMapping("/updateInstalReport")
	public ResponseEntity<String> updateInstallationReport(@RequestBody ReportDetails reportDetails)
			throws InstalReportException, CompanyDetailsException {
		logger.info("called updateInstallationReport function UserName : {},SiteId : {},ReportId : {}", reportDetails.getUserName(),
				reportDetails.getSiteId(),reportDetails.getReportId());
		instalReportService.updateInstallationReport(reportDetails);
		return new ResponseEntity<String>("Report successfully Updated", HttpStatus.OK);
	}
	
	@PostMapping("/sendBasicInfoComments/{userName}/{siteId}")
	public ResponseEntity<Void> sendComments(@PathVariable String userName, @PathVariable Integer siteId,
			@RequestBody ReportDetailsComment reportDetailsComment)
			throws InstalReportException, RegistrationException, Exception {
		logger.info("called sendComments function UserName : {},SiteId : {}", userName, siteId);
		instalReportService.sendComments(userName, siteId, reportDetailsComment);
		sendReplyComments.sendComments(userName);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping("/replyBasicInfoComments/{inspectorUserName}/{siteId}")
	public ResponseEntity<Void> replyComments(@PathVariable String inspectorUserName, @PathVariable Integer siteId,
			@RequestBody ReportDetailsComment reportDetailsComment)
			throws InstalReportException, RegistrationException, Exception {
		logger.info("called replyComments function inspectorUserName : {},SiteId : {}", inspectorUserName, siteId);
		String viewerUserName = instalReportService.replyComments(inspectorUserName, siteId, reportDetailsComment);
		if (viewerUserName != null) {
			sendReplyComments.replyComments(inspectorUserName, viewerUserName);
		} else {
			throw new InstalReportException("No viewer userName avilable");
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping("/approveBasicInfoComments/{userName}/{siteId}")
	public ResponseEntity<Void> approveComments(@PathVariable String userName, @PathVariable Integer siteId,
			@RequestBody ReportDetailsComment reportDetailsComment)
			throws InstalReportException, RegistrationException, Exception {
		logger.info("called approveComments function UserName : {},SiteId : {}", userName, siteId);
		instalReportService.approveComments(userName, siteId, reportDetailsComment);
		sendReplyComments.approveComments(userName,reportDetailsComment.getApproveOrReject());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
