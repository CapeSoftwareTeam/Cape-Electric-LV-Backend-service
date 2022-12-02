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
import com.capeelectric.exception.DecimalConversionException;
import com.capeelectric.exception.RegistrationException;
import com.capeelectric.exception.SupplyCharacteristicsException;
import com.capeelectric.model.SupplyCharacteristicComment;
import com.capeelectric.model.SupplyCharacteristics;
import com.capeelectric.service.SupplyCharacteristicsService;
import com.capeelectric.util.SendReplyComments;

/**
 *
 * @author capeelectricsoftware
 *
 */
@RestController()
@RequestMapping("/api/v2")
public class SupplyCharacteristicsController {

	private static final Logger logger = LoggerFactory.getLogger(SupplyCharacteristicsController.class);
	
	@Autowired
	private SupplyCharacteristicsService supplyCharacteristicsService;
	
	@Autowired
	private SendReplyComments sendReplyComments;

	@PostMapping("/addCharacteristics")
	public ResponseEntity<String> addCharacteristics(@RequestBody SupplyCharacteristics supplyCharacteristics)
			throws SupplyCharacteristicsException, DecimalConversionException, CompanyDetailsException {
		logger.debug("called addCharacteristics function UserName : {}, SiteId : {}",
				supplyCharacteristics.getUserName(), supplyCharacteristics.getSiteId());
		supplyCharacteristicsService.addCharacteristics(supplyCharacteristics);
		logger.debug("Ended addCharacteristics function");
		return new ResponseEntity<String>("SupplyCharacteristics and Earthing Properties Sucessfully Saved",
				HttpStatus.CREATED);
	}

	@GetMapping("/retrieveCharacteristics/{userName}/{siteId}")
	public ResponseEntity<SupplyCharacteristics> retrieveCharacteristics(@PathVariable String userName,
			@PathVariable Integer siteId) throws SupplyCharacteristicsException {
		logger.debug("started retrieveCharacteristics function UserName : {}, SiteId : {}", userName, siteId);
		return new ResponseEntity<SupplyCharacteristics>(
				supplyCharacteristicsService.retrieveCharacteristics(userName, siteId), HttpStatus.OK);
	}
	
	@GetMapping("/retrieveCharacteristics/{siteId}")
	public ResponseEntity<SupplyCharacteristics> retrieveCharacteristicsForSiteId(@PathVariable Integer siteId) throws SupplyCharacteristicsException {
		logger.debug("started retrieveCharacteristics function SiteId : {}", siteId);
		return new ResponseEntity<SupplyCharacteristics>(
				supplyCharacteristicsService.retrieveCharacteristics(siteId), HttpStatus.OK);
	}

	@PutMapping("/updateCharacteristics")
	public ResponseEntity<String> updateCharacteristics(@RequestBody SupplyCharacteristics supplyCharacteristics)
			throws SupplyCharacteristicsException, DecimalConversionException, CompanyDetailsException {
		logger.debug("called updateCharacteristics function UserName : {},SiteId : {},SupplyCharacteristicsId : {}",
				supplyCharacteristics.getUserName(), supplyCharacteristics.getSiteId(),
				supplyCharacteristics.getSupplyCharacteristicsId());
		supplyCharacteristicsService.updateCharacteristics(supplyCharacteristics);
		logger.debug("Ended addCharacteristics function");
		return new ResponseEntity<String>("SupplyCharacteristics Data successfully Updated", HttpStatus.OK);
	}

	@PostMapping("/sendCharacteristicsComments/{userName}/{siteId}")
	public ResponseEntity<Void> sendComments(@PathVariable String userName, @PathVariable Integer siteId,
			@RequestBody SupplyCharacteristicComment supplyCharacteristicComment)
			throws SupplyCharacteristicsException, RegistrationException, Exception {
		logger.debug("called sendcomments function UserName : {},SiteId : {}", userName, siteId);
		supplyCharacteristicsService.sendComments(userName, siteId, supplyCharacteristicComment);
		logger.debug("Ended sendcomments function");
		sendReplyComments.sendComments(userName);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping("/replyCharacteristicsComments/{inspectorUserName}/{siteId}")
	public ResponseEntity<Void> replyComments(@PathVariable String inspectorUserName, @PathVariable Integer siteId,
			@RequestBody SupplyCharacteristicComment supplyCharacteristicComment)
			throws SupplyCharacteristicsException, RegistrationException, Exception {
		logger.debug("called replyComments function inspectorUserName : {},SiteId : {}", inspectorUserName, siteId);
		String viewerUserName = supplyCharacteristicsService.replyComments(inspectorUserName, siteId,
				supplyCharacteristicComment);
		logger.debug("Ended replyComments function");
		if (viewerUserName != null) {
			sendReplyComments.replyComments(inspectorUserName, viewerUserName);
			logger.debug("Ended sendReplyComments function");
		} else {
			logger.error("No viewer userName avilable");
			throw new SupplyCharacteristicsException("No viewer userName avilable");
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping("/approveCharacteristicsComments/{userName}/{siteId}")
	public ResponseEntity<Void> approveComments(@PathVariable String userName, @PathVariable Integer siteId,
			@RequestBody SupplyCharacteristicComment supplyCharacteristicComment)
			throws SupplyCharacteristicsException, RegistrationException, Exception {
		logger.debug("called approveComments function UserName : {},SiteId : {}", userName, siteId);
		supplyCharacteristicsService.approveComments(userName, siteId, supplyCharacteristicComment);
		logger.debug("Ended approveComments function");
		sendReplyComments.approveComments(userName, supplyCharacteristicComment.getApproveOrReject());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
