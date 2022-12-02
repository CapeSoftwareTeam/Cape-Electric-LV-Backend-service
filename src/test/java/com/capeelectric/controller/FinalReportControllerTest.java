package com.capeelectric.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.capeelectric.exception.FinalReportException;
import com.capeelectric.model.FinalReport;
import com.capeelectric.model.PeriodicInspection;
import com.capeelectric.model.ReportDetails;
import com.capeelectric.model.Site;
import com.capeelectric.model.Summary;
import com.capeelectric.model.SupplyCharacteristics;
import com.capeelectric.model.TestingReport;
import com.capeelectric.service.impl.FinalReportServiceImpl;

/**
 * 
 * @author capeelectricsoftware
 *
 */
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class FinalReportControllerTest {
	
	private static final Logger logger = LoggerFactory.getLogger(FinalReportControllerTest.class);

	@InjectMocks
	private FinalReportController finalReportController;

	@MockBean
	private FinalReportServiceImpl finalReportServiceImpl;

	private Site site;
	
	private FinalReport finalReport;

	{
		site = new Site();
		site.setUserName("LVsystem@gmail.com");
	}

	{
		finalReport = new FinalReport();
		finalReport.setSiteId(1);
		finalReport.setUserName("LVsystem@gmail.com");
		finalReport.setPeriodicInspection(new PeriodicInspection());
		finalReport.setReportDetails(new ReportDetails());
		finalReport.setSummary(new Summary());
		finalReport.setTestingReport(new TestingReport());
		finalReport.setSupplyCharacteristics(new SupplyCharacteristics());
	}
	@Test
	public void testRetrieveListOfSite() throws FinalReportException {
		logger.info("FinalReportControllerTest testRetrieveListOfSite Function Started");
		List<Site> arrayList = new ArrayList<>();
		arrayList.add(site);
		when(finalReportServiceImpl.retrieveListOfSite("LVsystem@gmail.com")).thenReturn(arrayList);

		ResponseEntity<List<Site>> listOfSite = finalReportController.retrieveListOfSite("LVsystem@gmail.com");
		assertEquals(listOfSite.getStatusCode() , HttpStatus.OK);
		logger.info("FinalReportControllerTest testRetrieveListOfSite Function Started");
	}
	
	@Test
	public void retrieveFinalReport() throws FinalReportException {
		logger.info("FinalReportControllerTest retrieveFinalReport Function Started");
		List<Site> arrayList = new ArrayList<>();
		when(finalReportServiceImpl.retrieveListOfSite("LVsystem@gmail.com")).thenReturn(arrayList);

		 ResponseEntity<Optional<FinalReport>> finalReport = finalReportController.retrieveReports("LVsystem@gmail.com", 1);
		assertEquals(finalReport.getStatusCode() , HttpStatus.OK);
		logger.info("FinalReportControllerTest retrieveFinalReport Function Started");
	}
	
	
}