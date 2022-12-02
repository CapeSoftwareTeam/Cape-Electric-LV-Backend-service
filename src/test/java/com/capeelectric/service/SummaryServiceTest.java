
package com.capeelectric.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.capeelectric.exception.CompanyDetailsException;
import com.capeelectric.exception.DecimalConversionException;
import com.capeelectric.exception.InspectionException;
import com.capeelectric.exception.InstalReportException;
import com.capeelectric.exception.ObservationException;
import com.capeelectric.exception.PdfException;
import com.capeelectric.exception.PeriodicTestingException;
import com.capeelectric.exception.SummaryException;
import com.capeelectric.exception.SupplyCharacteristicsException;
import com.capeelectric.model.PeriodicInspection;
import com.capeelectric.model.Register;
import com.capeelectric.model.ReportDetails;
import com.capeelectric.model.Site;
import com.capeelectric.model.SitePersons;
import com.capeelectric.model.Summary;
import com.capeelectric.model.SummaryComment;
import com.capeelectric.model.SummaryDeclaration;
import com.capeelectric.model.SupplyCharacteristics;
import com.capeelectric.model.TestingReport;
import com.capeelectric.repository.InspectionRepository;
import com.capeelectric.repository.InstalReportDetailsRepository;
import com.capeelectric.repository.SiteRepository;
import com.capeelectric.repository.SummaryRepository;
import com.capeelectric.repository.SupplyCharacteristicsRepository;
import com.capeelectric.repository.TestingReportRepository;
import com.capeelectric.service.impl.SummaryServiceImpl;
import com.capeelectric.util.FindNonRemovedObject;
import com.capeelectric.util.SiteDetails;
import com.capeelectric.util.UserFullName;

@ExtendWith(SpringExtension.class)

@ExtendWith(MockitoExtension.class)
public class SummaryServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(SummaryServiceTest.class);

	@MockBean
	private InstalReportPDFService instalReportService;
	
	@MockBean
	private PrintSupplyService printSupplyService ;
	
	@MockBean
	private InspectionServicePDF inspectionServicePDF;
	
	@MockBean
	private PrintTestingService printTestingService;
	
	@MockBean
	private PrintService printService ;
	
	@MockBean
	private PrintFinalPDFService printFinalPDFService;
	
	@MockBean
	private SummaryRepository summaryRepository;
	
	@MockBean
	private InstalReportDetailsRepository installationReportRepository;
	
	@MockBean
	private InspectionRepository inspectionRepository;
	
	@MockBean
	private TestingReportRepository testingReportRepository;
	
	@MockBean
	private SupplyCharacteristicsRepository supplyCharacteristicsRepository;

	@InjectMocks
	private SummaryServiceImpl summaryServiceImpl;
	
	@MockBean
	private UserFullName userFullName;
	
	@MockBean
	private FindNonRemovedObject findNonRemovedObject;

	private Summary summary;
	
	private SummaryComment summaryComment;
	
	@MockBean
	private SiteDetails siteDetails;
	
	@MockBean
	private SiteRepository siteRepository;
	
	private ArrayList<SummaryComment> listOfComments;
	
    private Site site;
	
	private Register register;

	{
		summary = new Summary();
		summary.setUserName("LVsystem@gmail.com");
		summary.setSiteId(6);
		summary.setSummaryId(10);
		
		summaryComment = new SummaryComment();
		summaryComment.setViewerDate(LocalDateTime.now());
		ArrayList<SummaryComment> listComment = new ArrayList<SummaryComment>();
		listComment.add(summaryComment);
		summary.setSummaryComment(listComment);
		
//		ArrayList<SummaryObervation> obervationList = new ArrayList<SummaryObervation>();
//		SummaryObervation obervation = new SummaryObervation();
//		obervation.setObervationStatus("a");
//		obervationList.add(obervation);
		
		//summary.setSummaryObervation(obervationList);
	}
	
	{
		register =new Register();
		register.setUsername("cape");
		
		site = new Site();
		site.setUserName("Inspector@gmail.com");
		site.setSiteId(6);
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
	}

	@Test
	public void testAddSummary() throws SummaryException, CompanyDetailsException, InstalReportException, SupplyCharacteristicsException, InspectionException, PeriodicTestingException, Exception, ObservationException, PdfException {

		SupplyCharacteristics supplyCharacteristics = new SupplyCharacteristics();
		supplyCharacteristics.setSiteId(6);
		Optional<SupplyCharacteristics> supply = Optional.of(supplyCharacteristics);

		TestingReport testingReport = new TestingReport();
		testingReport.setSiteId(6);
		Optional<TestingReport> testreport = Optional.of(testingReport);

		PeriodicInspection periodicInspection = new PeriodicInspection();
		periodicInspection.setSiteId(6);
		Optional<PeriodicInspection> periodicreport = Optional.of(periodicInspection);

		ReportDetails reportDetails = new ReportDetails();
		reportDetails.setSiteId(6);
		Optional<ReportDetails> reportdet = Optional.of(reportDetails);
		
		when(siteRepository.findById(6)).thenReturn(Optional.of(site));
		when(summaryRepository.findBySiteId(10)).thenReturn(Optional.of(summary));
		when(installationReportRepository.findBySiteId(6)).thenReturn(reportdet);
		when(supplyCharacteristicsRepository.findBySiteId(6)).thenReturn(supply);
		when(inspectionRepository.findBySiteId(6)).thenReturn(periodicreport);
		when(testingReportRepository.findBySiteId(6)).thenReturn(testreport);

		SummaryException exception_reqiredValue = Assertions.assertThrows(SummaryException.class,
				() -> summaryServiceImpl.addSummary(summary));
		assertEquals(exception_reqiredValue.getMessage(), "Please fill all the fields before clicking next button");
		
		List<SummaryDeclaration> listofSummaryDeclaration= new ArrayList<SummaryDeclaration>();
		listofSummaryDeclaration.add(new SummaryDeclaration());
//		List<SummaryObervation> listofSummaryObervation= new ArrayList<SummaryObervation>();
//		listofSummaryObervation.add(new SummaryObervation());
		summary.setSummaryDeclaration(listofSummaryDeclaration);
//		summary.setSummaryObervation(listofSummaryObervation);
		
		instalReportService.printBasicInfromation(summary.getUserName(),summary.getSiteId(),reportdet);
		
		printSupplyService.printSupply(summary.getUserName(),summary.getSiteId(),supply);
		
		inspectionServicePDF.printInspectionDetails(summary.getUserName(),summary.getSiteId(), periodicreport);
		
		printTestingService.printTesting(summary.getUserName(),summary.getSiteId(),testreport);
		
		printService.printSummary(summary.getUserName(),summary.getSiteId());
		
		printFinalPDFService.printFinalPDF(summary.getUserName(),summary.getSiteId(), site.getSite());
		
		
		
		summaryServiceImpl.addSummary(summary);
		
		when(summaryRepository.findBySiteId(6)).thenReturn(Optional.of(summary));
		SummaryException summaryException_2 = Assertions.assertThrows(SummaryException.class,
				() -> summaryServiceImpl.addSummary(summary));
		assertEquals(summaryException_2.getMessage(), "Site-Id Already Available");

		summary.setUserName(null);
		SummaryException summaryException_3 = Assertions.assertThrows(SummaryException.class,
				() -> summaryServiceImpl.addSummary(summary));
		assertEquals(summaryException_3.getMessage(), "Invalid Inputs");

//		when(siteRepository.findById(1)).thenReturn(Optional.of(site));
//		when(summaryRepository.findBySiteId(6)).thenReturn(Optional.of(summary));
//		SummaryException summaryException_1 = Assertions.assertThrows(SummaryException.class,
//				() -> summaryServiceImpl.addSummary(summary));
//		assertEquals(summaryException_1.getMessage(), "Site-Id Information not Available in site_Table");
		
//		summary.setSiteId(1);
//		SummaryException summaryException_2 = Assertions.assertThrows(SummaryException.class,
//				() -> summaryServiceImpl.addSummary(summary));
//		assertEquals(summaryException_2.getMessage(), "Site-Id Information not Available in site_Table");
//		
//		when(siteRepository.findById(1)).thenReturn(Optional.of(site));
//		summaryServiceImpl.addSummary(summary);
//
//		
	}
	@Test
	public void testRetrieveSummary() throws SummaryException {
		
		List<Summary> arrayList = new ArrayList<Summary>();
		arrayList.add(summary);
		when(summaryRepository.findByUserNameAndSiteId("LVsystem@gmail.com", 12)).thenReturn(arrayList);

		logger.info("SuccessFlow of Retrieve Summary Obeject");
		summaryServiceImpl.retrieveSummary("LVsystem@gmail.com", 12);

		logger.info("Invalid Input flow");
		SummaryException summaryException = Assertions.assertThrows(SummaryException.class,
				() -> summaryServiceImpl.retrieveSummary(null, 12));
		assertEquals(summaryException.getMessage(), "Invalid Inputs");

	}

	
	@Test
	public void testUpdateSummary() throws SummaryException, CompanyDetailsException, InstalReportException, 
    SupplyCharacteristicsException, InspectionException, PeriodicTestingException, Exception, ObservationException, PdfException {
		SupplyCharacteristics supplyCharacteristics = new SupplyCharacteristics();
		supplyCharacteristics.setSiteId(6);
		Optional<SupplyCharacteristics> supply = Optional.of(supplyCharacteristics);

		TestingReport testingReport = new TestingReport();
		testingReport.setSiteId(6);
		Optional<TestingReport> testreport = Optional.of(testingReport);

		PeriodicInspection periodicInspection = new PeriodicInspection();
		periodicInspection.setSiteId(6);
		Optional<PeriodicInspection> periodicreport = Optional.of(periodicInspection);

		ReportDetails reportDetails = new ReportDetails();
		reportDetails.setSiteId(6);
		Optional<ReportDetails> reportdet = Optional.of(reportDetails);
		
		when(siteRepository.findById(6)).thenReturn(Optional.of(site));
		when(summaryRepository.findBySiteId(10)).thenReturn(Optional.of(summary));
		when(installationReportRepository.findBySiteId(6)).thenReturn(reportdet);
		when(supplyCharacteristicsRepository.findBySiteId(6)).thenReturn(supply);
		when(inspectionRepository.findBySiteId(6)).thenReturn(periodicreport);
		when(testingReportRepository.findBySiteId(6)).thenReturn(testreport);
		
		summary.setUserName("LVsystem@gmail.com");
		when(summaryRepository.findById(10)).thenReturn(Optional.of(summary));
		summaryServiceImpl.updateSummary(summary,true);
		
		Summary summary_1 = new Summary();
		summary_1.setSiteId(12);
		summary_1.setUserName("cape");
		summary_1.setSummaryId(12);
		
		when(summaryRepository.findById(4)).thenReturn(Optional.of(summary));
		SummaryException assertThrows = Assertions.assertThrows(SummaryException.class,
				() -> summaryServiceImpl.updateSummary(summary_1,true));
		
		assertEquals(assertThrows.getMessage(),"Given SiteId and ReportId is Invalid");
		
		summary.setSiteId(null);
		when(summaryRepository.findById(2)).thenReturn(Optional.of(summary));
		SummaryException assertThrows_1 = Assertions.assertThrows(SummaryException.class,
				() -> summaryServiceImpl.updateSummary(summary,false));
		
		assertEquals(assertThrows_1.getMessage(),"Invalid inputs");
	}
	
	

	
	@Test
	public void testSendComments() throws SummaryException {
		listOfComments = new ArrayList<SummaryComment>();
		when(summaryRepository.findBySiteId(6)).thenReturn(Optional.of(summary));
		when(siteRepository.findById(6)).thenReturn(Optional.of(site));
		summaryServiceImpl.sendComments("Viewer@gmail.com", 6, summaryComment);
 
		summaryComment.setCommentsId(1);
		listOfComments.add(summaryComment);
		summary.setSummaryComment(listOfComments);
		when(summaryRepository.findBySiteId(1)).thenReturn(Optional.of(summary));
		summaryServiceImpl.sendComments("Viewer@gmail.com", 6, summaryComment);

		listOfComments.remove(summaryComment);
		summary.setSummaryComment(listOfComments);
		summaryServiceImpl.sendComments("Viewer@gmail.com", 6, summaryComment);

		SummaryException assertThrows = Assertions.assertThrows(SummaryException.class,
				() -> summaryServiceImpl.sendComments("Inspector@gmail.com", 6, summaryComment));

		assertEquals(assertThrows.getMessage(), "Given userName not allowing for SEND comment");
	}
	
	@Test
	public void testReplyComments() throws SummaryException {
		listOfComments = new ArrayList<SummaryComment>();
		summaryComment.setCommentsId(1);
		listOfComments.add(summaryComment);
		summary.setUserName("Inspector@gmail.com");
		summary.setSummaryComment(listOfComments);
//		site.setSiteId(1);
//		summary.setSummaryId(1);
		when(summaryRepository.findBySiteId(6)).thenReturn(Optional.of(summary));
		when(siteRepository.findById(6)).thenReturn(Optional.of(site));
		summaryServiceImpl.replyComments("Inspector@gmail.com", 6, summaryComment);

		SummaryException assertThrows = Assertions.assertThrows(SummaryException.class,
				() -> summaryServiceImpl.replyComments("Viewer@gmail.com", 6, summaryComment));

		assertEquals(assertThrows.getMessage(), "Given userName not allowing for REPLY comment");
	}
	
	@Test
	public void testApproveComments() throws SummaryException {
		listOfComments = new ArrayList<SummaryComment>();
		summaryComment.setCommentsId(1);
		listOfComments.add(summaryComment);
		summary.setSummaryComment(listOfComments);
//		site.setSiteId(1);
//		summary.setSummaryId(1);
		when(summaryRepository.findBySiteId(6)).thenReturn(Optional.of(summary));
		when(siteRepository.findById(6)).thenReturn(Optional.of(site));
		summaryServiceImpl.approveComments("Viewer@gmail.com", 6, summaryComment);
		
		SummaryException assertThrows = Assertions.assertThrows(SummaryException.class,
				() -> summaryServiceImpl.approveComments("Inspector@gmail.com", 6, summaryComment));
		
		assertEquals(assertThrows.getMessage(), "Given userName not allowing for APPROVED comment");
	}
	
	@Test
	public void testComments_Exception() throws SummaryException {

		summary.setUserName(null); 
		site.setSiteId(1);
		summary.setSummaryId(1);
		when(summaryRepository.findBySiteId(2)).thenReturn(Optional.of(summary));
		when(siteRepository.findById(1)).thenReturn(Optional.of(site));

		SummaryException assertThrows_1 = Assertions.assertThrows(SummaryException.class,
				() -> summaryServiceImpl.sendComments("Viewer@gmail.com", 1, summaryComment));
		assertEquals(assertThrows_1.getMessage(), "Given username not have access for comments");

		summary.setSummaryComment(null);
		SummaryException assertThrows_2 = Assertions.assertThrows(SummaryException.class,
				() -> summaryServiceImpl.sendComments("Viewer@gmail.com", 2, summaryComment));
		assertEquals(assertThrows_2.getMessage(), "Siteinformation doesn't exist, try with different Site-Id");

		SummaryException assertThrows_3 = Assertions.assertThrows(SummaryException.class,
				() -> summaryServiceImpl.sendComments("Viewer@gmail.com", null, summaryComment));
	//	assertEquals(assertThrows_3.getMessage(), "Invalid Inputs");

	}

}
