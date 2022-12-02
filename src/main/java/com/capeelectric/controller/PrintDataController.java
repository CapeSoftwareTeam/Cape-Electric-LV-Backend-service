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
//import com.capeelectric.exception.SummaryException;
//import com.capeelectric.model.Summary;
//import com.capeelectric.service.PrintService;
//
//@RestController()
//@RequestMapping("/api/v1")
//public class PrintDataController {
//	private static final Logger logger = LoggerFactory.getLogger(PrintDataController.class);
//	
//	
//	@Autowired
//	private PrintService printService ;
//	
//	@GetMapping("/printSummary/{userName}/{siteId}")
//	public ResponseEntity<List<Summary>> retrieveSummary(@PathVariable String userName,@PathVariable Integer siteId) throws SummaryException {
//		logger.info("called printSummary function userName: {},siteId : {}", userName,siteId);
//		printService.printSummary(userName,siteId);
//		return new ResponseEntity<List<Summary>>( HttpStatus.OK);
//	}
//	
//}
