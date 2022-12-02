package com.capeelectric.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.capeelectric.exception.CompanyDetailsException;
import com.capeelectric.exception.InspectionException;
import com.capeelectric.exception.InstalReportException;
import com.capeelectric.exception.RegistrationException;
import com.capeelectric.model.ReportDetails;
import com.capeelectric.model.ReportDetailsComment;
import com.capeelectric.model.SignatorDetails;
import com.capeelectric.service.InstalReportService;
import com.capeelectric.util.SendReplyComments;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class InstallReportControllerTest {

	@InjectMocks
	private InstallReportController instalReportController;
	
	@MockBean
	private InstalReportService instalReportService;
	
	@MockBean
	private SendReplyComments sendReplyComments;
	
	ReportDetails reportDetails;
	
	private  ReportDetailsComment reportDetailsComment;
		
	{
		Set<SignatorDetails> set = new HashSet<SignatorDetails>();
		
		reportDetails=new ReportDetails();
		reportDetails.setCompany("capeelectric");
		reportDetails.setCreatedBy("software@capeindia.com");
		reportDetails.setDescriptionPremise("");
		reportDetails.setDescriptionReport("IEC 60364 gives the rules for the design, erection, and verification of electrical installations up to 1000 V AC and 1500 V DC. These rules are adopted/followed worldwide as wiring rules. Some of the adopted national versions of this standard are BS7671 in UK, VDE0100 in Germany … etc. The NFPA 70 (NEC) of USA closely follows the fundamental safety measures recommended in IEC 60364.");
		reportDetails.setDesignation("chennai");
		reportDetails.setEstimatedWireAge("25");
		reportDetails.setEvidanceAddition("");
		reportDetails.setExtentInstallation("");
		reportDetails.setInstallationDetails("IEC 60364 gives the rules for the design, erection, and verification of electrical installations up to 1000 V AC and 1500 V DC. These rules are adopted/followed worldwide as wiring rules. Some of the adopted national versions of this standard are BS7671 in UK, VDE0100 in Germany … etc. The NFPA 70 (NEC) of USA closely follows the fundamental safety measures recommended in IEC 60364.");
		reportDetails.setInstallationType("new installation");
		reportDetails.setLastInspection("march 20th");
		reportDetails.setNextInspection("may 20th");
		reportDetails.setPreviousRecords("no");
		reportDetails.setReasonOfReport("IEC 60364 gives the rules for the design, erection, and verification of electrical installations up to 1000 V AC and 1500 V DC. These rules are adopted/followed worldwide as wiring rules. Some of the adopted national versions of this standard are BS7671 in UK, VDE0100 in Germany … etc. The NFPA 70 (NEC) of USA closely follows the fundamental safety measures recommended in IEC 60364.");
		reportDetails.setReportId(12);
		reportDetails.setSignatorDetails(set);
		reportDetails.setUserName("software@capeindia.com");
		reportDetails.setVerificationDate("20-03-2023");
		reportDetails.setVerifiedEngineer("cape");
		reportDetails.setClientDetails(""); 
		
		reportDetailsComment = new ReportDetailsComment();
		reportDetailsComment.setViewerComment("question");
		reportDetailsComment.setViewerFlag("1");
		reportDetails.setUserName("Inspector@gmail.com");
		reportDetails.setSiteId(1);
	}
	
	
	@Test
	public void testAddInstallationReport() throws InstalReportException, CompanyDetailsException {
		
		ResponseEntity<String> response = instalReportController.addInstallationReport(reportDetails);
		assertEquals(response.getBody(), "Basic Information Successfully Saved");
		
	}
	
	@Test
	public void testRetrieveInstallationReport() throws InstalReportException, InspectionException { 

		ResponseEntity<ReportDetails> report = instalReportController.retrieveInstallationReport(reportDetails.getUserName(),reportDetails.getSiteId());
		assertEquals(report.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void testUpdateInstallationReport() throws InstalReportException, CompanyDetailsException {
		ResponseEntity<String> expectedResponseEntity = new ResponseEntity<String>(HttpStatus.OK);
		ResponseEntity<String> actualResponseEntity = instalReportController
				.updateInstallationReport(reportDetails);
		assertEquals(actualResponseEntity.getStatusCode(), expectedResponseEntity.getStatusCode());
	}
	
	@Test
	public void testSendComments() throws RegistrationException, Exception, InstalReportException {
		doNothing().when(instalReportService).sendComments("Viewer@gmail.com", 1, reportDetailsComment());
		ResponseEntity<Void> sendComments = instalReportController.sendComments("Viewer@gmail.com", 1,
				reportDetailsComment());
		assertEquals(sendComments.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testReplyComments() throws RegistrationException, Exception, InstalReportException {

		when(instalReportService.replyComments("Inspector@gmail.com", 1, reportDetailsComment))
				.thenReturn("Viewer@gmail.com");

		ResponseEntity<Void> sendComments = instalReportController.replyComments("Inspector@gmail.com", 1,
				reportDetailsComment);

		assertEquals(sendComments.getStatusCode(), HttpStatus.OK);

		when(instalReportService.replyComments("Inspector@gmail.com", 1, reportDetailsComment)).thenReturn(null);
		InstalReportException exception = Assertions.assertThrows(InstalReportException.class,
				() -> instalReportController.replyComments("Inspector@gmail.com", 1, reportDetailsComment));

		assertEquals(exception.getMessage(), "No viewer userName avilable");

	}

	@Test
	public void testApproveComments() throws InstalReportException, RegistrationException, Exception{

		instalReportController.approveComments("Inspector@gmail.com", 1, reportDetailsComment);

	}

	private ReportDetailsComment reportDetailsComment() {
		reportDetailsComment = new ReportDetailsComment();
		reportDetailsComment.setViewerComment("question");
		reportDetailsComment.setViewerFlag("1");
		reportDetails.setUserName("Inspector@gmail.com");
		reportDetails.setSiteId(1);
		return reportDetailsComment;
	}

}
