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
//import com.capeelectric.exception.SupplyCharacteristicsException;
//import com.capeelectric.model.SupplyCharacteristics;
//import com.capeelectric.service.PrintSupplyService;
//@RestController()
//@RequestMapping("/api/v1")
//public class PrintSupplyController {
//	private static final Logger logger = LoggerFactory.getLogger(PrintSupplyController.class);
//	
//	
//	@Autowired
//	private PrintSupplyService printSupplyService ;
//	
//	
//	@GetMapping("/printSupply/{userName}/{siteId}")
//	public ResponseEntity<List<SupplyCharacteristics>> printSupply(@PathVariable String userName,@PathVariable Integer siteId) throws  SupplyCharacteristicsException {
//		logger.info("called printSummary function userName: {},siteId : {}", userName,siteId);
//		printSupplyService.printSupply(userName,siteId);
//		return new ResponseEntity<List<SupplyCharacteristics>>( HttpStatus.OK);
//	}	
//	
//}
