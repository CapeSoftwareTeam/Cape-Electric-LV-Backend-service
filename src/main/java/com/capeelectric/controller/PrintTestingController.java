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
//import com.capeelectric.exception.PeriodicTestingException;
//import com.capeelectric.model.TestingReport;
//import com.capeelectric.service.PrintTestingService;
//
//@RestController()
//@RequestMapping("/api/v1")
//public class PrintTestingController {
//	private static final Logger logger = LoggerFactory.getLogger(PrintTestingController.class);
//
//	@Autowired
//	private PrintTestingService printTestingService;
//
//	@GetMapping("/printTesting/{userName}/{siteId}")
//	public ResponseEntity<List<TestingReport>> printTesting(@PathVariable String userName, @PathVariable Integer siteId)
//			throws PeriodicTestingException {
//		logger.info("Started printTesting function userName: {},siteId : {}", userName, siteId);
//		printTestingService.printTesting(userName, siteId);
//		logger.info("ended printTesting function");
//		return new ResponseEntity<List<TestingReport>>(HttpStatus.OK);
//	}
//
//}
