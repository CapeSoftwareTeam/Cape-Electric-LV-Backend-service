
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
import com.capeelectric.service.impl.SummaryServiceImpl;
import com.capeelectric.util.SendReplyComments;

/**
 * 
 * @author capeelectricsoftware
 *
 */
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class SummaryControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(SummaryControllerTest.class);

	@InjectMocks
	private SummaryController summaryController;

	@MockBean
	private SummaryServiceImpl summaryServiceImpl;

	@MockBean
	private SummaryException summaryException;
	
	@MockBean
	private SendReplyComments sendReplyComments;

	private Summary summary;
	
	private SummaryComment summaryComment;

	{
		summary = new Summary();
		summary.setUserName("LVsystem@gmail.com");
		summary.setSiteId(12);
		
		summaryComment = new SummaryComment();
		summaryComment.setViewerComment("question");
		summaryComment.setViewerFlag("1");
	}

	@Test
	public void testAddSummary() throws SummaryException, CompanyDetailsException, InstalReportException, SupplyCharacteristicsException, InspectionException, PeriodicTestingException, Exception, ObservationException, PdfException {
		logger.info("testAddSummary Function Started");

		doNothing().when(summaryServiceImpl).addSummary(summary);
		ResponseEntity<String> addSummary = summaryController.addSummary(summary);
		assertEquals(addSummary.getBody(), "Summary Details Successfully Saved");

		logger.info("testAddSummary Function Ended");
	}

	@Test
	public void testRetrieveSummary() throws SummaryException {
		List<Summary> arrayList = new ArrayList<Summary>();
		arrayList.add(summary);
		
		logger.info("testRetrieveSummary Function Started");

		when(summaryServiceImpl.retrieveSummary("LVsystem@gmail.com", 12)).thenReturn(arrayList);
		ResponseEntity<List<Summary>> retrieveSummary = summaryController.retrieveSummary("LVsystem@gmail.com", 12);
		assertEquals( HttpStatus.OK, retrieveSummary.getStatusCode());

		logger.info("testRetrieveSummary Function Ended");

	}
	
	@Test
	public void testUpdateSummary() throws SummaryException, CompanyDetailsException, InstalReportException, 
    SupplyCharacteristicsException, InspectionException, PeriodicTestingException, Exception, ObservationException, PdfException{
		
		logger.info("testUpdateSummary Function Started");
		ResponseEntity<String> expectedResponseEntity = new ResponseEntity<String>(HttpStatus.OK);
		ResponseEntity<String> actualResponseEntity = summaryController
				.updateSummary(summary,true);
		assertEquals(actualResponseEntity.getStatusCode(), expectedResponseEntity.getStatusCode());
		logger.info("testUpdateSummary Function Ended");
	}
	
	@Test
	public void testSendComments() throws RegistrationException, Exception, SummaryException {
		doNothing().when(summaryServiceImpl).sendComments("Viewer@gmail.com", 1, summaryComment());
		ResponseEntity<Void> sendComments = summaryController.sendComments("Viewer@gmail.com", 1,
				summaryComment());
		assertEquals(sendComments.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testReplyComments() throws RegistrationException, Exception, SummaryException {

		when(summaryServiceImpl.replyComments("Inspector@gmail.com", 1, summaryComment))
				.thenReturn("Viewer@gmail.com");

		ResponseEntity<Void> sendComments = summaryController.replyComments("Inspector@gmail.com", 1,
				summaryComment);

		assertEquals(sendComments.getStatusCode(), HttpStatus.OK);

		when(summaryServiceImpl.replyComments("Inspector@gmail.com", 1, summaryComment)).thenReturn(null);
		SummaryException exception = Assertions.assertThrows(SummaryException.class,
				() -> summaryController.replyComments("Inspector@gmail.com", 1, summaryComment));

		assertEquals(exception.getMessage(), "No viewer userName avilable");

	}

	@Test
	public void testApproveComments() throws RegistrationException, Exception, SummaryException {

		summaryController.approveComments("Inspector@gmail.com", 1, summaryComment());

	}

	private SummaryComment summaryComment() {
		SummaryComment summaryComment = new SummaryComment();
		summaryComment.setViewerComment("question");
		summaryComment.setViewerFlag("1");
		summary.setUserName("Inspector@gmail.com");
		summary.setSiteId(1);
		return summaryComment;
	}

}
