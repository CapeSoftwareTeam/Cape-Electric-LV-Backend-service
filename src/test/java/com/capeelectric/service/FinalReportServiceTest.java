package com.capeelectric.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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

import com.capeelectric.exception.FinalReportException;
import com.capeelectric.model.BoundingLocationReport;
import com.capeelectric.model.EarthingLocationReport;
import com.capeelectric.model.FinalReport;
import com.capeelectric.model.InstalLocationReport;
import com.capeelectric.model.IpaoInspection;
import com.capeelectric.model.PeriodicInspection;
import com.capeelectric.model.ReportDetails;
import com.capeelectric.model.SignatorDetails;
import com.capeelectric.model.Site;
import com.capeelectric.model.Summary;
import com.capeelectric.model.SupplyCharacteristics;
import com.capeelectric.model.TestDistRecords;
import com.capeelectric.model.Testing;
import com.capeelectric.model.TestingRecords;
import com.capeelectric.model.TestingReport;
import com.capeelectric.repository.InspectionRepository;
import com.capeelectric.repository.InstalReportDetailsRepository;
import com.capeelectric.repository.SiteRepository;
import com.capeelectric.repository.SummaryRepository;
import com.capeelectric.repository.SupplyCharacteristicsRepository;
import com.capeelectric.repository.TestingReportRepository;
import com.capeelectric.service.impl.FinalReportServiceImpl;
import com.capeelectric.util.FindNonRemovedObject;

/**
 * @author capeelectricsoftware
 * @param <E>
 *
 */
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class FinalReportServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(FinalReportServiceTest.class);

	@InjectMocks
	private FinalReportServiceImpl finalReportServiceImpl;

	@MockBean
	private SiteRepository siteRepository;

	@MockBean
	private InstalReportDetailsRepository instalReportDetailsRepository;

	@MockBean
	private SupplyCharacteristicsRepository supplyCharacteristicsRepository;

	@MockBean
	private InspectionRepository inspectionRepository;

	@MockBean
	private TestingReportRepository testingReportRepository;

	@MockBean
	private SummaryRepository summaryRepository;
	
	@MockBean
	private FindNonRemovedObject findNonRemovedObject;

	private Site site;

	private FinalReport finalReport;

	{
		finalReport = new FinalReport();
		finalReport.setUserName("LVsystem@gmail.com");
		finalReport.setSiteId(1);

		finalReport.setReportDetails(retrieveReportDetails());
		finalReport.setSupplyCharacteristics(retrieveSupplyCharacteristics());
		finalReport.setPeriodicInspection(retrievePeriodicInspection());
		finalReport.setTestingReport(retrieveTestingReport());
		finalReport.setSummary(retrieveSummary());

	}

	{
		site = new Site();
		site.setUserName("LVsystem@gmail.com");
		site.setSiteId(1);
	}

	@Test
	public void testRetriveListOfSite() throws FinalReportException {
		logger.info("testRetriveListOfSite method started");
		ArrayList<Site> sites = new ArrayList<Site>();
		sites.add(site);
		when(siteRepository.findByUserName("LVsystem@gmail.com")).thenReturn(sites);
		List<Site> retrieveListOfSite = finalReportServiceImpl.retrieveListOfSite("LVsystem@gmail.com");
		assertTrue(retrieveListOfSite.contains(site));

		FinalReportException finalReportException = Assertions.assertThrows(FinalReportException.class,
				() -> finalReportServiceImpl.retrieveListOfSite(null));
		assertEquals(finalReportException.getMessage(), "Invaild Input");
		logger.info("testRetriveListOfSite method ended");

	}

	@Test
	public void testRetriveFinalReport() throws FinalReportException {

		logger.info("testRetriveListOfSite method started");

		when(instalReportDetailsRepository.findBySiteId(1)).thenReturn(Optional.of(retrieveReportDetails()));
		when(supplyCharacteristicsRepository.findBySiteId(1)).thenReturn(Optional.of(retrieveSupplyCharacteristics()));
		when(inspectionRepository.findBySiteId(1)).thenReturn(Optional.of(retrievePeriodicInspection()));
		when(testingReportRepository.findBySiteId(1)).thenReturn(Optional.of(retrieveTestingReport()));
		when(summaryRepository.findBySiteId(1)).thenReturn(Optional.of(retrieveSummary()));

		Optional<FinalReport> retrieveFinalReport = finalReportServiceImpl.retrieveFinalReport("LVsystem@gmail.com", 1);
		assertNotNull(retrieveFinalReport);

		FinalReportException finalReportException = Assertions.assertThrows(FinalReportException.class,
				() -> finalReportServiceImpl.retrieveFinalReport(null, 1));
		assertEquals(finalReportException.getMessage(), "Invalid Input");
		logger.info("testRetriveListOfSite method ended");

	}
	
	@Test
	public void testRetriveFinalReport_NotHaveingAllStepData() throws FinalReportException {

		logger.info("testRetriveFinalReport_NotHaveingAllStepData method started");

		when(instalReportDetailsRepository.findBySiteId(1)).thenReturn(Optional.of(retrieveReportDetails()));
		when(supplyCharacteristicsRepository.findBySiteId(1)).thenReturn(Optional.of(retrieveSupplyCharacteristics()));

		Optional<FinalReport> retrieveFinalReport = finalReportServiceImpl.retrieveFinalReport("LVsystem@gmail.com", 1);
		assertNotNull(retrieveFinalReport);

	}

	private ReportDetails retrieveReportDetails() {
		ReportDetails reportDetails = new ReportDetails();
		
		SignatorDetails signatorDetails = new SignatorDetails();
		signatorDetails.setSignatorStatus("a");
		HashSet<SignatorDetails> signatorDetailsList = new HashSet<SignatorDetails>();
		reportDetails.setSignatorDetails(signatorDetailsList);
		reportDetails.setUserName("LVsystem@gmail.com");
		reportDetails.setSiteId(1);
		return reportDetails;
	}

	private SupplyCharacteristics retrieveSupplyCharacteristics() {
		SupplyCharacteristics supplyCharacteristics = new SupplyCharacteristics();
		supplyCharacteristics.setUserName("LVsystem@gmail.com");
		supplyCharacteristics.setSiteId(1);
		
		List<InstalLocationReport> installList = new ArrayList<InstalLocationReport>();
		InstalLocationReport instalLocationReport = new InstalLocationReport();
		instalLocationReport.setInstalLocationReportStatus("A");
		installList.add(instalLocationReport);

		List<BoundingLocationReport> boundingList = new ArrayList<BoundingLocationReport>();
		BoundingLocationReport boundingLocationReport = new BoundingLocationReport();
		boundingLocationReport.setInstalLocationReportStatus("u");
		boundingList.add(boundingLocationReport);

		List<EarthingLocationReport> earthingList = new ArrayList<EarthingLocationReport>();
		EarthingLocationReport earthingLocationReport = new EarthingLocationReport();
		earthingLocationReport.setInstalLocationReportStatus("n");
		earthingList.add(earthingLocationReport);

		supplyCharacteristics.setInstalLocationReport(installList);
		supplyCharacteristics.setBoundingLocationReport(boundingList);
		supplyCharacteristics.setEarthingLocationReport(earthingList);
		return supplyCharacteristics;
	}

	private PeriodicInspection retrievePeriodicInspection() {
		PeriodicInspection periodicInspection = new PeriodicInspection();
		IpaoInspection ipaoInspection = new IpaoInspection();
		ipaoInspection.setInspectionFlag("a");
		List<IpaoInspection> arrayList = new ArrayList<IpaoInspection>();
		arrayList.add(ipaoInspection);
		periodicInspection.setIpaoInspection(arrayList);
		periodicInspection.setUserName("LVsystem@gmail.com");
		periodicInspection.setSiteId(1);
		
		return periodicInspection;
	}

	private TestingReport retrieveTestingReport() {
		TestingReport testingReport = new TestingReport();
		testingReport.setUserName("LVsystem@gmail.com");
		testingReport.setSiteId(1);
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
		return testingReport;
	}

	private Summary retrieveSummary() {
		Summary summary = new Summary();
		
//		SummaryObervation obervation = new SummaryObervation();
//		obervation.setObervationStatus("a");
//		List<SummaryObervation> obervationList = new ArrayList<SummaryObervation>();
//		obervationList.add(obervation);
//		summary.setSummaryObervation(obervationList);
		summary.setUserName("LVsystem@gmail.com");
		summary.setSiteId(1);
		return summary;
	}
}
