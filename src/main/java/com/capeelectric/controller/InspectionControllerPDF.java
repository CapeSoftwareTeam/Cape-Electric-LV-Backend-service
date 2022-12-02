//package com.capeelectric.controller;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.capeelectric.exception.InspectionException;
//import com.capeelectric.model.PeriodicInspection;
//import com.capeelectric.service.InspectionServicePDF;
//
//@RestController
//@RequestMapping("/api/v1")
//public class InspectionControllerPDF {
//	
//	private static final Logger logger = LoggerFactory.getLogger(InspectionControllerPDF.class);
//
//	@Autowired
//	private InspectionServicePDF inspectionServicePDF;
//	
//	@GetMapping("/PrintInspectionDetails/{userName}/{siteId}")
//	public ResponseEntity<List<PeriodicInspection>> retrieveInspectionDetails(@PathVariable String userName,
//			@PathVariable Integer siteId) throws InspectionException {
//		logger.info("called addInspectionDetails function UserName : {},SiteId : {}", userName, siteId);
//		return new ResponseEntity<List<PeriodicInspection>>(inspectionServicePDF.printInspectionDetails(userName, siteId),
//				HttpStatus.OK);
//	}
//}