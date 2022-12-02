package com.capeelectric.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
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

import com.capeelectric.exception.CompanyDetailsException;
import com.capeelectric.exception.PeriodicTestingException;
import com.capeelectric.exception.RegistrationException;
import com.capeelectric.model.TestingReport;
import com.capeelectric.model.TestingReportComment;
import com.capeelectric.service.impl.PeriodicTestingServiceImpl;
import com.capeelectric.util.SendReplyComments;

/**
 * 
 * @author capeelectricsoftware
 *
 */
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class PeriodicTestingControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(PeriodicTestingControllerTest.class);

	@InjectMocks
	private PeriodicTestingController periodicTestingController;

	@MockBean
	private PeriodicTestingServiceImpl periodicTestingServiceImpl;

	@MockBean
	private PeriodicTestingException periodicTestingException;
	
	@MockBean
	private SendReplyComments sendReplyComments;

	private TestingReport testingReport;
	
	private TestingReportComment testingReportComment;

	{
		testingReport = new TestingReport();
		testingReport.setSiteId(1);
		testingReport.setUserName("LVsystem@gmail.com");
		
		testingReportComment = new TestingReportComment();
		testingReportComment.setViewerComment("question");
		testingReportComment.setViewerFlag("1");
		testingReport.setUserName("Inspector@gmail.com");
		testingReport.setSiteId(1);
	}

	@Test
	public void testSavePeriodicTesting() throws PeriodicTestingException, CompanyDetailsException {
		logger.info("testSavePeriodicTesting Function Started");

		doNothing().when(periodicTestingServiceImpl).addTestingReport(testingReport);
		ResponseEntity<String> savePeriodicTesting = periodicTestingController.savePeriodicTesting(testingReport);
		assertEquals(savePeriodicTesting.getBody(), "Testing Report Successfully Saved");

		logger.info("testSavePeriodicTesting Function Ended");
	}

	@Test
	public void testRetrievePeriodicTesting() throws PeriodicTestingException {
		List<TestingReport> arrayList = new ArrayList<>();
		arrayList.add(testingReport);

		logger.info("testRetrievePeriodicTesting Function Started");

		when(periodicTestingServiceImpl.retrieveTestingReport("LVsystem@gmail.com", 12)).thenReturn(testingReport);
		ResponseEntity<TestingReport> retrieveTestingReport = periodicTestingController
				.retrievePeriodicTesting("LVsystem@gmail.com", 12);
		assertEquals(HttpStatus.OK, retrieveTestingReport.getStatusCode());

		logger.info("testRetrievePeriodicTesting Function Ended");

	}
	
	@Test
	public void testUpdatePeriodicTesting() throws PeriodicTestingException, CompanyDetailsException {
		logger.info("testUpdatePeriodicTesting Function Started");
		ResponseEntity<String> expectedResponseEntity = new ResponseEntity<String>(HttpStatus.OK);
		ResponseEntity<String> actualResponseEntity = periodicTestingController
				.updatePeriodicTesting(testingReport);
		assertEquals(actualResponseEntity.getStatusCode(), expectedResponseEntity.getStatusCode());
		logger.info("testUpdatePeriodicTesting Function Ended");
	}
	
	@Test
	public void testSendComments() throws RegistrationException, PeriodicTestingException, Exception {
		doNothing().when(periodicTestingServiceImpl).sendComments("Viewer@gmail.com", 1, testingReportComment());
		ResponseEntity<Void> sendComments = periodicTestingController.sendComments("Viewer@gmail.com", 1,
				testingReportComment());
		assertEquals(sendComments.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testReplyComments() throws RegistrationException, PeriodicTestingException, Exception {

		when(periodicTestingServiceImpl.replyComments("Inspector@gmail.com", 1, testingReportComment))
				.thenReturn("Viewer@gmail.com");

		ResponseEntity<Void> sendComments = periodicTestingController.replyComments("Inspector@gmail.com", 1,
				testingReportComment);

		assertEquals(sendComments.getStatusCode(), HttpStatus.OK);

		when(periodicTestingServiceImpl.replyComments("Inspector@gmail.com", 1, testingReportComment)).thenReturn(null);
		PeriodicTestingException exception = Assertions.assertThrows(PeriodicTestingException.class,
				() -> periodicTestingController.replyComments("Inspector@gmail.com", 1, testingReportComment));

		assertEquals(exception.getMessage(), "No viewer userName avilable");

	}

	@Test
	public void testApproveComments() throws PeriodicTestingException, RegistrationException, Exception {

		periodicTestingController.approveComments("Inspector@gmail.com", 1, testingReportComment());

	}

	private TestingReportComment testingReportComment() {
		TestingReportComment testingReportComment = new TestingReportComment();
		testingReportComment.setViewerComment("question");
		testingReportComment.setViewerFlag("1");
		testingReport.setUserName("Inspector@gmail.com");
		testingReport.setSiteId(1);
		return testingReportComment;
	}
}
