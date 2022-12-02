package com.capeelectric.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.capeelectric.exception.CompanyDetailsException;
import com.capeelectric.exception.InstalReportException;
import com.capeelectric.model.ReportDetails;
import com.capeelectric.model.ReportDetailsComment;
import com.capeelectric.model.SignatorDetails;
import com.capeelectric.model.Site;
import com.capeelectric.model.SitePersons;
import com.capeelectric.repository.InstalReportDetailsRepository;
import com.capeelectric.repository.RegistrationRepository;
import com.capeelectric.repository.SiteRepository;
import com.capeelectric.service.impl.InstalReportServiceImpl;
import com.capeelectric.util.FindNonRemovedObject;
import com.capeelectric.util.SiteDetails;
import com.capeelectric.util.UserFullName;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class InstalReportServiceImplTest {

	@InjectMocks
	InstalReportServiceImpl instalReportServiceImpl;

	@MockBean
	private InstalReportDetailsRepository installationReportRepository;

	@MockBean
	private RegistrationRepository registrationRepository;
	
	@MockBean
	private UserFullName userFullName;

	private ReportDetails reportDetails;
	
	@MockBean
	private SiteRepository siteRepository;
	
	@MockBean
	private FindNonRemovedObject findNonRemovedObject;
	
	private ArrayList<ReportDetailsComment> listOfComments;
	
	private ReportDetailsComment reportDetailsComment;
	
    private Site site;
    
	@MockBean
	private SiteDetails siteDetails;
	
	{
		Set<SignatorDetails> set = new HashSet<SignatorDetails>();

		reportDetails = new ReportDetails();
		reportDetails.setCompany("capeelectric");
		reportDetails.setCreatedBy("software@capeindia.com");
		reportDetails.setDescriptionPremise("");
		reportDetails.setDescriptionReport(
				"IEC 60364 gives the rules for the design, erection, and verification of electrical installations up to 1000 V AC and 1500 V DC. These rules are adopted/followed worldwide as wiring rules. Some of the adopted national versions of this standard are BS7671 in UK, VDE0100 in Germany … etc. The NFPA 70 (NEC) of USA closely follows the fundamental safety measures recommended in IEC 60364.");
		reportDetails.setDesignation("chennai");
		reportDetails.setEstimatedWireAge("25");
		reportDetails.setEvidanceAddition("");
		reportDetails.setExtentInstallation("");
		reportDetails.setInstallationDetails(
				"IEC 60364 gives the rules for the design, erection, and verification of electrical installations up to 1000 V AC and 1500 V DC. These rules are adopted/followed worldwide as wiring rules. Some of the adopted national versions of this standard are BS7671 in UK, VDE0100 in Germany … etc. The NFPA 70 (NEC) of USA closely follows the fundamental safety measures recommended in IEC 60364.");
		reportDetails.setInstallationType("new installation");
		reportDetails.setLastInspection("march 20th");
		reportDetails.setNextInspection("may 20th");
		reportDetails.setPreviousRecords("no");
		reportDetails.setReasonOfReport(
				"IEC 60364 gives the rules for the design, erection, and verification of electrical installations up to 1000 V AC and 1500 V DC. These rules are adopted/followed worldwide as wiring rules. Some of the adopted national versions of this standard are BS7671 in UK, VDE0100 in Germany … etc. The NFPA 70 (NEC) of USA closely follows the fundamental safety measures recommended in IEC 60364.");
		reportDetails.setReportId(12);
		reportDetails.setSignatorDetails(set);
		reportDetails.setUserName("software@capeindia.com");
		reportDetails.setVerificationDate("20-03-2023");
		reportDetails.setVerifiedEngineer("cape");
		reportDetails.setClientDetails("");
		reportDetails.setSiteId(12);
	}
	
	{
		site = new Site();
		site.setUserName("Inspector@gmail.com");
		site.setSiteId(1);
		site.setSite("user");
		site.setAssignedTo("Viewer@gmail.com");

		SitePersons sitePersons1 =new SitePersons();
		sitePersons1.setPersonId(1);
		sitePersons1.setSiteName("user");
		sitePersons1.setPersonInchargeEmail("Viewer@gmail.com");
		sitePersons1.setInActive(true);
		 
		HashSet<SitePersons> sitePersonsSet = new HashSet<SitePersons>();
		sitePersonsSet.add(sitePersons1);
		site.setSitePersons(sitePersonsSet);
		
		reportDetailsComment = new ReportDetailsComment();
		reportDetailsComment.setViewerComment("question");
		reportDetailsComment.setViewerFlag("1");
		reportDetails.setUserName("Inspector@gmail.com");
		reportDetails.setSiteId(1);
	}

	@Test
	public void testAddInstallationReport() throws InstalReportException, CompanyDetailsException {
		reportDetails.setUserName("software@capeindia.com");

		InstalReportException exception_reqiredValue = Assertions.assertThrows(InstalReportException.class,
				() -> instalReportServiceImpl.addInstallationReport(reportDetails));
		assertEquals(exception_reqiredValue.getMessage(), "Please fill all the fields before clicking next button");
		
		Set<SignatorDetails> listReportDetails = new HashSet<SignatorDetails>();
		listReportDetails.add(new SignatorDetails());
		reportDetails.setSignatorDetails(listReportDetails);
		
		when(installationReportRepository.save(reportDetails)).thenReturn(reportDetails);
		instalReportServiceImpl.addInstallationReport(reportDetails);

		when(installationReportRepository.findBySiteId(1)).thenReturn(Optional.of(reportDetails));
		InstalReportException exception_SiteId = Assertions.assertThrows(InstalReportException.class,
				() -> instalReportServiceImpl.addInstallationReport(reportDetails));
		assertEquals(exception_SiteId.getMessage(), "Site-Id Details Already Available,Create New Site-Id");
				
		InstalReportException exception = Assertions.assertThrows(InstalReportException.class,
				() -> instalReportServiceImpl.addInstallationReport(null));
		assertEquals(exception.getMessage(), "Invalid Inputs");

	}
	
	@Test
	public void testUpdateInstallationReport() throws InstalReportException, CompanyDetailsException {
		reportDetails.setUserName("LVsystem@gmail.com");
		reportDetails.setReportId(1);
		when(installationReportRepository.findById(1)).thenReturn(Optional.of(reportDetails));
		instalReportServiceImpl.updateInstallationReport(reportDetails);

		ReportDetails reportDetails_1 = new ReportDetails();
		reportDetails_1.setSiteId(12);
		reportDetails_1.setUserName("cape");
		reportDetails_1.setReportId(12);

		when(installationReportRepository.findById(4)).thenReturn(Optional.of(reportDetails));
		InstalReportException assertThrows = Assertions.assertThrows(InstalReportException.class,
				() -> instalReportServiceImpl.updateInstallationReport(reportDetails_1));
		assertEquals(assertThrows.getMessage(), "Given SiteId and ReportId is Invalid");

		reportDetails.setSiteId(null);
		when(installationReportRepository.findById(2)).thenReturn(Optional.of(reportDetails));
		InstalReportException assertThrows_1 = Assertions.assertThrows(InstalReportException.class,
				() -> instalReportServiceImpl.updateInstallationReport(reportDetails));
		assertEquals(assertThrows_1.getMessage(), "Invalid inputs");
	}
	
	@Test
	public void testSendComments() throws InstalReportException {
		listOfComments = new ArrayList<ReportDetailsComment>();
		listOfComments.add(reportDetailsComment);
		reportDetails.setReportDetailsComment(listOfComments);
		when(installationReportRepository.findBySiteId(1)).thenReturn(Optional.of(reportDetails));
		when(siteRepository.findById(1)).thenReturn(Optional.of(site));

		instalReportServiceImpl.sendComments("Viewer@gmail.com", 1, reportDetailsComment);
 
		reportDetailsComment.setCommentsId(1);
		listOfComments.add(reportDetailsComment);
		reportDetails.setReportDetailsComment(listOfComments);
		when(installationReportRepository.findBySiteId(1)).thenReturn(Optional.of(reportDetails));
		instalReportServiceImpl.sendComments("Viewer@gmail.com", 1, reportDetailsComment);

		listOfComments.remove(reportDetailsComment);
		reportDetails.setReportDetailsComment(listOfComments);
		instalReportServiceImpl.sendComments("Viewer@gmail.com", 1, reportDetailsComment);

		InstalReportException assertThrows = Assertions.assertThrows(InstalReportException.class,
				() -> instalReportServiceImpl.sendComments("Inspector@gmail.com", 1, reportDetailsComment));

		assertEquals(assertThrows.getMessage(), "Given userName not allowing for SEND comment");
	}
	
	@Test
	public void testReplyComments() throws InstalReportException {
		listOfComments = new ArrayList<>();
		reportDetailsComment.setCommentsId(1);
		listOfComments.add(reportDetailsComment);
		reportDetails.setReportDetailsComment(listOfComments);
		when(installationReportRepository.findBySiteId(1)).thenReturn(Optional.of(reportDetails));
		when(siteRepository.findById(1)).thenReturn(Optional.of(site));
		instalReportServiceImpl.replyComments("Inspector@gmail.com", 1, reportDetailsComment);

		InstalReportException assertThrows = Assertions.assertThrows(InstalReportException.class,
				() -> instalReportServiceImpl.replyComments("Viewer@gmail.com", 1, reportDetailsComment));

		assertEquals(assertThrows.getMessage(), "Given userName not allowing for REPLY comment");
	}
	
	@Test
	public void testApproveComments() throws InstalReportException {
		listOfComments = new ArrayList<ReportDetailsComment>();
		reportDetailsComment.setCommentsId(1);
		listOfComments.add(reportDetailsComment);
		reportDetails.setReportDetailsComment(listOfComments);
		when(installationReportRepository.findBySiteId(1)).thenReturn(Optional.of(reportDetails));
		when(siteRepository.findById(1)).thenReturn(Optional.of(site));
		instalReportServiceImpl.approveComments("Viewer@gmail.com", 1, reportDetailsComment);
		
		InstalReportException assertThrows = Assertions.assertThrows(InstalReportException.class,
				() -> instalReportServiceImpl.approveComments("Inspector@gmail.com", 1, reportDetailsComment));
		
		assertEquals(assertThrows.getMessage(), "Given userName not allowing for APPROVED comment");
	}
	
	@Test
	public void testComments_Exception() throws InstalReportException {

		reportDetails.setUserName(null);
		when(installationReportRepository.findBySiteId(1)).thenReturn(Optional.of(reportDetails));
		when(siteRepository.findById(1)).thenReturn(Optional.of(site));

		InstalReportException assertThrows_1 = Assertions.assertThrows(InstalReportException.class,
				() -> instalReportServiceImpl.sendComments("Viewer@gmail.com", 1, reportDetailsComment));

		assertEquals(assertThrows_1.getMessage(), "Given username not have access for comments");

		reportDetails.setReportDetailsComment(null);
		InstalReportException assertThrows_2 = Assertions.assertThrows(InstalReportException.class,
				() -> instalReportServiceImpl.sendComments("Viewer@gmail.com", 2, reportDetailsComment));
		assertEquals(assertThrows_2.getMessage(), "Siteinformation doesn't exist, try with different Site-Id");

		InstalReportException assertThrows_3 = Assertions.assertThrows(InstalReportException.class,
				() -> instalReportServiceImpl.sendComments("Viewer@gmail.com", null, reportDetailsComment));
		assertEquals(assertThrows_3.getMessage(), "Invalid Inputs");

	}
	
	@Test
	public void testRetrieveInstalReportDetails_Success_Flow() throws InstalReportException {

		List<ReportDetails> ipaolist = new ArrayList<ReportDetails>();
		ipaolist.add(reportDetails);
		listOfComments = new ArrayList<ReportDetailsComment>();
		listOfComments.add(reportDetailsComment);
		reportDetails.setReportDetailsComment(listOfComments);

		when(installationReportRepository.findByUserNameAndSiteId(reportDetails.getUserName(),
				reportDetails.getSiteId())).thenReturn(reportDetails);
		instalReportServiceImpl.retrieveInstallationReport("Inspector@gmail.com", 1);

		InstalReportException assertThrows_1 = Assertions.assertThrows(InstalReportException.class,
				() -> instalReportServiceImpl.retrieveInstallationReport("cape", 2));
		assertEquals(assertThrows_1.getMessage(), "Given UserName & Site doesn't exist Basic-information");

		InstalReportException assertThrows_2 = Assertions.assertThrows(InstalReportException.class,
				() -> instalReportServiceImpl.retrieveInstallationReport(null, 1));
		assertEquals(assertThrows_2.getMessage(), "Invalid Inputs");
	}
}
