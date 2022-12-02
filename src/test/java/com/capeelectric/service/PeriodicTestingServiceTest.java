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
import com.capeelectric.exception.PeriodicTestingException;
import com.capeelectric.model.Register;
import com.capeelectric.model.Site;
import com.capeelectric.model.SitePersons;
import com.capeelectric.model.TestDistRecords;
import com.capeelectric.model.TestDistribution;
import com.capeelectric.model.TestIncomingDistribution;
import com.capeelectric.model.Testing;
import com.capeelectric.model.TestingRecords;
import com.capeelectric.model.TestingReport;
import com.capeelectric.model.TestingReportComment;
import com.capeelectric.repository.SiteRepository;
import com.capeelectric.repository.TestingReportRepository;
import com.capeelectric.service.impl.PeriodicTestingServiceImpl;
import com.capeelectric.util.FindNonRemovedObject;
import com.capeelectric.util.SiteDetails;
import com.capeelectric.util.UserFullName;

/**
 * 
 * @author capeelectricsoftware
 * @param <E>
 *
 */
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class PeriodicTestingServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(PeriodicTestingServiceTest.class);

	@MockBean
	private TestingReportRepository testingReportRepository;

	@InjectMocks
	private PeriodicTestingServiceImpl periodicTestingServiceImpl;
	
	@MockBean
	private UserFullName userFullName;
	
	@MockBean
	private SiteRepository siteRepository;
	
	@MockBean
	private FindNonRemovedObject findNonRemovedObject;

	private TestingReport testingReport;
	
	private TestingReportComment testingReportComment;
	
	private Testing testing;
	
	private ArrayList<TestingReportComment> listOfComments;
	
    private Site site;
	
	private Register register;
	
	@MockBean
	private SiteDetails siteDetails;

	{
		register =new Register();
		register.setUsername("cape");
		
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
	}
	{
		testingReport = new TestingReport();
		testingReport.setSiteId(1);
		testingReport.setUserName("LVsystem@gmail.com");

		testingReportComment = new TestingReportComment();
		testingReportComment.setViewerDate(LocalDateTime.now());
		testingReportComment.setViewerComment("question");
		testingReportComment.setViewerFlag("1");
		testingReport.setUserName("Inspector@gmail.com");
		testingReport.setSiteId(1);
		
		ArrayList<TestingReportComment> listOfComment = new ArrayList<TestingReportComment>();
		listOfComment.add(testingReportComment);
		testingReport.setTestingComment(listOfComment);
		List<Testing> testingList = new ArrayList<Testing>();
		List<TestingRecords> testingRecordsList = new ArrayList<TestingRecords>();
        Testing testing = new Testing();
        testing.setTestingStatus("a");
        TestDistRecords testDistRecords = new TestDistRecords();
		TestingRecords testingRecords = new TestingRecords();
		testingRecords.setTestingRecordStatus("a");
		testingRecordsList.add(testingRecords);
		testDistRecords.setTestingRecords(testingRecordsList);
		List<TestDistRecords> testDistRecordsList = new ArrayList<TestDistRecords>();
		testDistRecordsList.add(testDistRecords);
		testing.setTestDistRecords(testDistRecordsList);
        testingList.add(testing);
        testingReport.setTesting(testingList);
	}
	
	@Test
	public void testAddTestingReport() throws PeriodicTestingException, CompanyDetailsException {
		 
		when(testingReportRepository.findBySiteId(1)).thenReturn(Optional.of(testingReport));

		logger.info("SiteId already Present_flow");
		List<Testing> listofTesting = new ArrayList<Testing>();
		listofTesting.add(new Testing());
		 
		List<TestDistribution> listofTestDistribution = new ArrayList<TestDistribution>();
		listofTestDistribution.add(new TestDistribution());
		List<TestIncomingDistribution> listofTestIncomingDistribution = new ArrayList<TestIncomingDistribution>();
		listofTestIncomingDistribution.add(new TestIncomingDistribution());
		List<TestingRecords> listofTestingRecords = new ArrayList<TestingRecords>();
		listofTestingRecords.add(new TestingRecords());
		
		Testing testing = listofTesting.get(0);
		TestDistRecords testDistRecords = new TestDistRecords();
		testDistRecords.setTestDistribution(listofTestDistribution);
		testDistRecords.setTestingRecords(listofTestingRecords);
		List<TestDistRecords> testDistRecordsList = new ArrayList<TestDistRecords>();
		testDistRecordsList.add(testDistRecords);
		testing.setTestDistRecords(testDistRecordsList);
		testingReport.setTesting(listofTesting);
		
		
		PeriodicTestingException periodicTestingException = Assertions.assertThrows(PeriodicTestingException.class,
				() -> periodicTestingServiceImpl.addTestingReport(testingReport));
		assertEquals(periodicTestingException.getMessage(), "Site-Id Already Present");

		testingReport.setTestIncomingDistribution(listofTestIncomingDistribution);
		PeriodicTestingException periodicTestingException_1 = Assertions.assertThrows(PeriodicTestingException.class,
				() -> periodicTestingServiceImpl.addTestingReport(testingReport));
		assertEquals(periodicTestingException_1.getMessage(), "Site-Id Already Present");

		when(testingReportRepository.findBySiteId(1)).thenReturn(Optional.of(testingReport));
		logger.info("Successfully added Summary_Object flow");
		testingReport.setSiteId(2);
		periodicTestingServiceImpl.addTestingReport(testingReport);

		logger.info("Invalid Present_flow");
		testingReport.setUserName(null);
		PeriodicTestingException periodicTestingException_2 = Assertions.assertThrows(PeriodicTestingException.class,
				() -> periodicTestingServiceImpl.addTestingReport(testingReport));
		assertEquals(periodicTestingException_2.getMessage(), "Invalid Inputs");

	}

	@Test
	public void testRetrieveTestingReport() throws PeriodicTestingException {
		
		List<TestingReport> arrayList = new ArrayList<TestingReport>();
		arrayList.add(testingReport);
		when(testingReportRepository.findByUserNameAndSiteId("LVsystem@gmail.com", 12)).thenReturn(testingReport);

		logger.info("SuccessFlow of Retrieve Summary Obeject");
		periodicTestingServiceImpl.retrieveTestingReport("LVsystem@gmail.com", 12);

		logger.info("Invalid Input flow");
		PeriodicTestingException periodicTestingException = Assertions.assertThrows(PeriodicTestingException.class,
				() -> periodicTestingServiceImpl.retrieveTestingReport(null, 12));
		assertEquals(periodicTestingException.getMessage(), "Invalid Inputs");

	}

	@Test
	public void testTesting_NA_Value() throws DecimalConversionException, PeriodicTestingException, CompanyDetailsException {
		logger.info("'NA'value checking processes started");

		testing = new Testing();
		ArrayList<Testing> testingList = new ArrayList<Testing>();

		List<TestDistribution> listofTestDistribution = new ArrayList<TestDistribution>();
		listofTestDistribution.add(new TestDistribution());
		List<TestIncomingDistribution> listofTestIncomingDistribution = new ArrayList<TestIncomingDistribution>();
		listofTestIncomingDistribution.add(new TestIncomingDistribution());

		testingList.add(utill_withDemcimal_Records());
		Testing testing = testingList.get(0);
		
		TestDistRecords testDistRecords = new TestDistRecords();
		testDistRecords.setTestDistribution(listofTestDistribution);
		
		List<TestingRecords> testingRecordsList = new ArrayList<TestingRecords>();
		testingRecordsList.add(new TestingRecords());
		testDistRecords.setTestingRecords(testingRecordsList);
		ArrayList<TestDistRecords> testDistRecordsList = new ArrayList<TestDistRecords>();
		testDistRecordsList.add(testDistRecords);
		testing.setTestDistRecords(testDistRecordsList);
		testingReport.setTestIncomingDistribution(listofTestIncomingDistribution);
		testingList.add(testing);
		testingReport.setTesting(testingList);

		when(testingReportRepository.findBySiteId(1)).thenReturn(Optional.of(testingReport));
		logger.info("Successfully added Summary_Object flow");
		testingReport.setSiteId(2);
		periodicTestingServiceImpl.addTestingReport(testingReport);

		//utill_withOutDemcimal_Records();

		logger.info("'NA'value checking processes started");

	}

	private Testing utill_withDemcimal_Records() throws DecimalConversionException {
		logger.info("Demcimal_Conversion success case");
		List<TestingRecords> testingRecordsList = new ArrayList<TestingRecords>();

		TestingRecords records = new TestingRecords();
		records.setTestVoltage("212,na,21,534,212");
		records.setTestLoopImpedance("na,12423,413,na");
		records.setTestFaultCurrent("312,122,na,234");

		testingRecordsList.add(records);
		TestDistRecords testDistRecords = new TestDistRecords();
		testDistRecords.setTestingRecords(testingRecordsList);

		return testing;
	}

	private Testing utill_withOutDemcimal_Records() throws DecimalConversionException {
		logger.info("Demcimal_Conversion faild case");
		TestingRecords testingRecords = new TestingRecords();

		DecimalConversionException decimalConversionException = Assertions
				.assertThrows(DecimalConversionException.class, () -> testingRecords.setTestVoltage(""));

		assertEquals(decimalConversionException.getMessage(), "invalid input of value for DecimalConversion");
		return null;
	}
	
	@Test
	public void testUpdateTesting() throws DecimalConversionException, PeriodicTestingException, CompanyDetailsException {
		testingReport.setUserName("LVsystem@gmail.com");
		testingReport.setTestingReportId(1);
		when(testingReportRepository.findById(1)).thenReturn(Optional.of(testingReport));
		periodicTestingServiceImpl.updatePeriodicTesting(testingReport);

		TestingReport testingReport_1 = new TestingReport();
		testingReport_1.setSiteId(12);
		testingReport_1.setUserName("cape");
		testingReport_1.setTestingReportId(12);

		when(testingReportRepository.findById(4)).thenReturn(Optional.of(testingReport));
		PeriodicTestingException assertThrows = Assertions.assertThrows(PeriodicTestingException.class,
				() -> periodicTestingServiceImpl.updatePeriodicTesting(testingReport_1));
		assertEquals(assertThrows.getMessage(), "Given SiteId and ReportId is Invalid");

		testingReport.setSiteId(null);
		when(testingReportRepository.findById(2)).thenReturn(Optional.of(testingReport));
		PeriodicTestingException assertThrows_1 = Assertions.assertThrows(PeriodicTestingException.class,
				() -> periodicTestingServiceImpl.updatePeriodicTesting(testingReport));
		assertEquals(assertThrows_1.getMessage(), "Invalid inputs");
	}
	
	@Test
	public void testSendComments() throws PeriodicTestingException {
		listOfComments = new ArrayList<TestingReportComment>();
		when(testingReportRepository.findBySiteId(1)).thenReturn(Optional.of(testingReport));
		when(siteRepository.findById(1)).thenReturn(Optional.of(site));
		periodicTestingServiceImpl.sendComments("Viewer@gmail.com", 1, testingReportComment);
 
		testingReportComment.setCommentsId(1);
		listOfComments.add(testingReportComment);
		testingReport.setTestingComment(listOfComments);
		when(testingReportRepository.findBySiteId(1)).thenReturn(Optional.of(testingReport));
		periodicTestingServiceImpl.sendComments("Viewer@gmail.com", 1, testingReportComment);

		listOfComments.remove(testingReportComment);
		testingReport.setTestingComment(listOfComments);
		periodicTestingServiceImpl.sendComments("Viewer@gmail.com", 1, testingReportComment);

		PeriodicTestingException assertThrows = Assertions.assertThrows(PeriodicTestingException.class,
				() -> periodicTestingServiceImpl.sendComments("Inspector@gmail.com", 1, testingReportComment));

		assertEquals(assertThrows.getMessage(), "Given userName not allowing for SEND comment");
	}
	
	@Test
	public void testReplyComments() throws PeriodicTestingException {
		listOfComments = new ArrayList<TestingReportComment>();
		testingReportComment.setCommentsId(1);
		listOfComments.add(testingReportComment);
		testingReport.setTestingComment(listOfComments);
		when(testingReportRepository.findBySiteId(1)).thenReturn(Optional.of(testingReport));
		when(siteRepository.findById(1)).thenReturn(Optional.of(site));
		periodicTestingServiceImpl.replyComments("Inspector@gmail.com", 1, testingReportComment);

		PeriodicTestingException assertThrows = Assertions.assertThrows(PeriodicTestingException.class,
				() -> periodicTestingServiceImpl.replyComments("Viewer@gmail.com", 1, testingReportComment));

		assertEquals(assertThrows.getMessage(), "Given userName not allowing for REPLY comment");
	}
	
	@Test
	public void testApproveComments() throws PeriodicTestingException {
		listOfComments = new ArrayList<TestingReportComment>();
		testingReportComment.setCommentsId(1);
		listOfComments.add(testingReportComment);
		testingReport.setTestingComment(listOfComments);
		when(testingReportRepository.findBySiteId(1)).thenReturn(Optional.of(testingReport));
		when(siteRepository.findById(1)).thenReturn(Optional.of(site));
		periodicTestingServiceImpl.approveComments("Viewer@gmail.com", 1, testingReportComment);
		
		PeriodicTestingException assertThrows = Assertions.assertThrows(PeriodicTestingException.class,
				() -> periodicTestingServiceImpl.approveComments("Inspector@gmail.com", 1, testingReportComment));
		
		assertEquals(assertThrows.getMessage(), "Given userName not allowing for APPROVED comment");
	}
	
	@Test
	public void testComments_Exception() throws PeriodicTestingException {

		testingReport.setUserName(null);
		when(testingReportRepository.findBySiteId(2)).thenReturn(Optional.of(testingReport));
		when(siteRepository.findById(1)).thenReturn(Optional.of(site));

		PeriodicTestingException assertThrows_1 = Assertions.assertThrows(PeriodicTestingException.class,
				() -> periodicTestingServiceImpl.sendComments("Viewer@gmail.com", 1, testingReportComment));

		assertEquals(assertThrows_1.getMessage(), "Given username not have access for comments");

		testingReport.setTestingComment(null);
		PeriodicTestingException assertThrows_2 = Assertions.assertThrows(PeriodicTestingException.class,
				() -> periodicTestingServiceImpl.sendComments("Viewer@gmail.com", 2, testingReportComment));
		assertEquals(assertThrows_2.getMessage(), "Siteinformation doesn't exist, try with different Site-Id");

		PeriodicTestingException assertThrows_3 = Assertions.assertThrows(PeriodicTestingException.class,
				() -> periodicTestingServiceImpl.sendComments("Viewer@gmail.com", null, testingReportComment));
		assertEquals(assertThrows_3.getMessage(), "Invalid Inputs");

	}

}
