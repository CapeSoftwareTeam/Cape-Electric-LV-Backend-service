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

import com.capeelectric.exception.ObservationException;
import com.capeelectric.model.AllComponentObservation;
import com.capeelectric.model.ObservationComponent;
import com.capeelectric.service.ObservationService;

@RestController
@RequestMapping("api/v2")
public class ObservationController {

	private static final Logger logger = LoggerFactory.getLogger(ObservationController.class);

	@Autowired
	private ObservationService observationService;

	@PostMapping("/saveObservation")
	public ResponseEntity<String> saveObservation(@RequestBody ObservationComponent observationComponent)
			throws ObservationException {
		logger.info("started saveObservation function ");
		observationService.addObservation(observationComponent);
		logger.info("ended saveObservation function");
		return new ResponseEntity<String>("Observation Successfully Saved", HttpStatus.CREATED);

	}

	@PutMapping("/updateObservation")
	public ResponseEntity<String> updateObservation(@RequestBody ObservationComponent observationComponent)
			throws ObservationException {
		logger.info("called updateSite function ");
		observationService.updateObservation(observationComponent);
		return new ResponseEntity<String>("Observation Successfully Updated", HttpStatus.OK);
	}

	@GetMapping("/retrieveObservation/{userName}/{siteId}/{observationComponent}")
	public ResponseEntity<ObservationComponent> retrieveObservation(@PathVariable String userName,
			@PathVariable Integer siteId, @PathVariable String observationComponent) throws ObservationException {
		logger.info("called retrieveObservation function");
		return new ResponseEntity<ObservationComponent>(
				observationService.retrieveObservation(userName, siteId, observationComponent), HttpStatus.OK);
	}

	@GetMapping("/retrieveObservationsInSummary/{userName}/{siteId}")
	public ResponseEntity<AllComponentObservation> retrieveObservationsInSummary(@PathVariable String userName,
			@PathVariable Integer siteId) throws ObservationException {
		logger.info("called retrieveObservation function");
		return new ResponseEntity<AllComponentObservation>(
				observationService.retrieveObservationsInSummary(userName, siteId), HttpStatus.OK);
	}

}
