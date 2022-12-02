package com.capeelectric.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.capeelectric.exception.CompanyDetailsException;
import com.capeelectric.exception.DecimalConversionException;
import com.capeelectric.exception.SupplyCharacteristicsException;
import com.capeelectric.model.BoundingLocationReport;
import com.capeelectric.model.CircuitBreaker;
import com.capeelectric.model.EarthingLocationReport;
import com.capeelectric.model.InstalLocationReport;
import com.capeelectric.model.Register;
import com.capeelectric.model.Site;
import com.capeelectric.model.SitePersons;
import com.capeelectric.model.SupplyCharacteristicComment;
import com.capeelectric.model.SupplyCharacteristics;
import com.capeelectric.model.SupplyParameters;
import com.capeelectric.repository.SiteRepository;
import com.capeelectric.repository.SupplyCharacteristicsRepository;
import com.capeelectric.service.impl.SupplyCharacteristicsServiceImpl;
import com.capeelectric.util.FindNonRemovedObject;
import com.capeelectric.util.SiteDetails;
import com.capeelectric.util.UserFullName;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class SupplyCharacteristicsServiceTest {

	@InjectMocks
	private SupplyCharacteristicsServiceImpl supplyCharacteristicsServiceImpl;

	@MockBean
	private SupplyCharacteristicsRepository supplyCharacteristicsRepository;

	@MockBean
	private SupplyCharacteristicsException supplyCharacteristicsException;

	private SupplyParameters supplyParameters;

	private SupplyCharacteristics supplyCharacteristics;
	
	private SupplyCharacteristicComment supplyCharacteristicComment;
	
	@MockBean
	private UserFullName userFullName;
	
	@MockBean
	private SiteRepository siteRepository;
	
	@MockBean
	private FindNonRemovedObject findNonRemovedObject;
	
	private ArrayList<SupplyCharacteristicComment> listOfComments;
	
	@MockBean
	private SiteDetails siteDetails;
	
    private Site site;
	
	private Register register;

	{
		supplyCharacteristics = new SupplyCharacteristics();
		supplyCharacteristics.setSupplyCharacteristicsId(1);
		supplyCharacteristics.setUserName("cape");
		supplyCharacteristics.setSiteId(1);
		supplyCharacteristics.setMainNominalCurrent("1.2012,na,455,566");
		supplyCharacteristics.setMainNominalFrequency("566");
		supplyCharacteristics.setMainNominalVoltage("3.00,152.1212,455.051,56.9459");
		supplyCharacteristics.setMainLoopImpedance("4.000,12.12245,455.21265,56.766456");
		supplyCharacteristics.setLiveConductorAC("3");
		
		supplyCharacteristicComment = new SupplyCharacteristicComment();
		supplyCharacteristicComment.setViewerDate(LocalDateTime.now());
		supplyCharacteristicComment.setViewerComment("question");
		supplyCharacteristicComment.setViewerFlag("1");
		supplyCharacteristics.setUserName("Inspector@gmail.com");
		supplyCharacteristics.setSiteId(1);
		supplyCharacteristics.setAlternativeSupply("Yes");
		ArrayList<SupplyCharacteristicComment> listOfComments = new ArrayList<SupplyCharacteristicComment>();
	    listOfComments.add(supplyCharacteristicComment);
	    supplyCharacteristics.setSupplyCharacteristicComment(listOfComments);
		
	}
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

	@Test
	public void testAddCharacteristics_Succes_Flow() throws SupplyCharacteristicsException, DecimalConversionException, CompanyDetailsException {
		
		SupplyCharacteristicsException exception_reqiredValue = Assertions.assertThrows(SupplyCharacteristicsException.class,
				() -> supplyCharacteristicsServiceImpl.addCharacteristics(supplyCharacteristics));
		assertEquals(exception_reqiredValue.getMessage(), "Please fill all the fields before clicking next button");
		
		List<SupplyParameters> listofSupplyParameters= new ArrayList<SupplyParameters>();
		listofSupplyParameters.add(new SupplyParameters());
		List<CircuitBreaker> listofCircuitBreaker= new ArrayList<CircuitBreaker>();
		listofCircuitBreaker.add(new CircuitBreaker());
		List<InstalLocationReport> listofInstalLocationReport= new ArrayList<InstalLocationReport>();
		listofInstalLocationReport.add(new InstalLocationReport());
		List<BoundingLocationReport> listofBoundingLocationReport= new ArrayList<BoundingLocationReport>();
		listofBoundingLocationReport.add(new BoundingLocationReport());
		List<EarthingLocationReport> listofEarthingLocationReport= new ArrayList<EarthingLocationReport>();
		listofEarthingLocationReport.add(new EarthingLocationReport());
	 
		supplyCharacteristics.setBoundingLocationReport(listofBoundingLocationReport);
		supplyCharacteristics.setEarthingLocationReport(listofEarthingLocationReport);
		supplyCharacteristics.setInstalLocationReport(listofInstalLocationReport);
		supplyCharacteristics.setCircuitBreaker(listofCircuitBreaker);
		supplyCharacteristics.setSupplyParameters(listofSupplyParameters);
		
		supplyCharacteristicsServiceImpl.addCharacteristics(supplyCharacteristics);
		when(supplyCharacteristicsRepository.save(supplyCharacteristics)).thenReturn(supplyCharacteristics);
		assertNotNull(supplyCharacteristics);
	}

	@Test
	public void testAddCharacteristics_Supply_Parameters_Succes_Flow() throws SupplyCharacteristicsException, DecimalConversionException, CompanyDetailsException {

		List<SupplyParameters> listofSupplyParameters= new ArrayList<SupplyParameters>();
		listofSupplyParameters.add(new SupplyParameters());
		List<CircuitBreaker> listofCircuitBreaker= new ArrayList<CircuitBreaker>();
		listofCircuitBreaker.add(new CircuitBreaker());
		List<InstalLocationReport> listofInstalLocationReport= new ArrayList<InstalLocationReport>();
		listofInstalLocationReport.add(new InstalLocationReport());
		List<BoundingLocationReport> listofBoundingLocationReport= new ArrayList<BoundingLocationReport>();
		listofBoundingLocationReport.add(new BoundingLocationReport());
		List<EarthingLocationReport> listofEarthingLocationReport= new ArrayList<EarthingLocationReport>();
		listofEarthingLocationReport.add(new EarthingLocationReport());
	 
		supplyCharacteristics.setBoundingLocationReport(listofBoundingLocationReport);
		supplyCharacteristics.setEarthingLocationReport(listofEarthingLocationReport);
		supplyCharacteristics.setInstalLocationReport(listofInstalLocationReport);
		supplyCharacteristics.setCircuitBreaker(listofCircuitBreaker);
		supplyCharacteristics.setSupplyParameters(listofSupplyParameters);
		
		supplyCharacteristics.setMainNominalCurrent("1.2012,12.1212,455,566");
		supplyCharacteristics.setMainNominalFrequency("2.00");
		supplyCharacteristics.setMainNominalVoltage("3.00,152.1212,455.051,56.9459");
		supplyCharacteristics.setMainLoopImpedance("4.000,12.12245,455.21265,56.766456");
		supplyParameters = new SupplyParameters();
		supplyParameters.setNominalFrequency("1.2450");
		supplyParameters.setNominalVoltage("2.050,12.1452,45545,5546");
		supplyParameters.setFaultCurrent("3.005,12.1542,44555.01,56.99");
		supplyParameters.setLoopImpedance("4.000,12.12254,455.21245,56.766");
		supplyParameters.setaLLiveConductorAC("3");

		List<SupplyParameters> list = new ArrayList<>();
		list.add(supplyParameters);
		supplyCharacteristics.setSupplyParameters(list);

		when(supplyCharacteristicsRepository.save(supplyCharacteristics)).thenReturn(supplyCharacteristics);
		supplyCharacteristicsServiceImpl.addCharacteristics(supplyCharacteristics);
		assertNotNull(supplyParameters);
	}

	@Test
	public void testAddCharacteristics_Invalid_Inputs() throws SupplyCharacteristicsException {
		supplyCharacteristics = null;

		when(supplyCharacteristicsRepository.save(supplyCharacteristics)).thenReturn(supplyCharacteristics);
		SupplyCharacteristicsException assertThrows = Assertions.assertThrows(SupplyCharacteristicsException.class,
				() -> supplyCharacteristicsServiceImpl.addCharacteristics(supplyCharacteristics));
		assertEquals(assertThrows.getMessage(),"Invalid Inputs");
	}

	@Test
	public void testAddCharacteristics_Site_Alredy_Present() throws SupplyCharacteristicsException {
		List<SupplyParameters> listofSupplyParameters= new ArrayList<SupplyParameters>();
		listofSupplyParameters.add(new SupplyParameters());
		List<CircuitBreaker> listofCircuitBreaker= new ArrayList<CircuitBreaker>();
		listofCircuitBreaker.add(new CircuitBreaker());
		List<InstalLocationReport> listofInstalLocationReport= new ArrayList<InstalLocationReport>();
		listofInstalLocationReport.add(new InstalLocationReport());
		List<BoundingLocationReport> listofBoundingLocationReport= new ArrayList<BoundingLocationReport>();
		listofBoundingLocationReport.add(new BoundingLocationReport());
		List<EarthingLocationReport> listofEarthingLocationReport= new ArrayList<EarthingLocationReport>();
		listofEarthingLocationReport.add(new EarthingLocationReport());
		supplyCharacteristics.setBoundingLocationReport(listofBoundingLocationReport);
		supplyCharacteristics.setEarthingLocationReport(listofEarthingLocationReport);
		supplyCharacteristics.setInstalLocationReport(listofInstalLocationReport);
		supplyCharacteristics.setCircuitBreaker(listofCircuitBreaker);
		supplyCharacteristics.setSupplyParameters(listofSupplyParameters);
		
		Optional<SupplyCharacteristics> supplylist;
		supplylist = Optional.of(supplyCharacteristics);

		when(supplyCharacteristicsRepository.findBySiteId(supplyCharacteristics.getSiteId())).thenReturn(supplylist);
		SupplyCharacteristicsException assertThrows = Assertions.assertThrows(SupplyCharacteristicsException.class,
				() -> supplyCharacteristicsServiceImpl.addCharacteristics(supplyCharacteristics));
		assertEquals(assertThrows.getMessage(),"Site-Id Already Available");
	}

	@Test
	public void testRetrieveCharacteristics_Invalid_Inputs() throws SupplyCharacteristicsException {

		List<SupplyCharacteristics> supplylist = new ArrayList<>();
		supplyCharacteristics.setUserName(null);
		supplylist.add(supplyCharacteristics);

		when(supplyCharacteristicsRepository.findByUserNameAndSiteId(supplyCharacteristics.getUserName(),
				supplyCharacteristics.getSiteId())).thenReturn(supplyCharacteristics);
		SupplyCharacteristicsException assertThrows = Assertions.assertThrows(SupplyCharacteristicsException.class,
				() -> supplyCharacteristicsServiceImpl.retrieveCharacteristics(null, 1));
		assertEquals(assertThrows.getMessage(), "Invalid Inputs");
	}
	
	@Test
	public void testAddCharacteristics_With_NA_Value() throws SupplyCharacteristicsException, DecimalConversionException, CompanyDetailsException {
		List<SupplyParameters> listofSupplyParameters= new ArrayList<SupplyParameters>();
		listofSupplyParameters.add(new SupplyParameters());
		List<CircuitBreaker> listofCircuitBreaker= new ArrayList<CircuitBreaker>();
		listofCircuitBreaker.add(new CircuitBreaker());
		List<InstalLocationReport> listofInstalLocationReport= new ArrayList<InstalLocationReport>();
		listofInstalLocationReport.add(new InstalLocationReport());
		List<BoundingLocationReport> listofBoundingLocationReport= new ArrayList<BoundingLocationReport>();
		listofBoundingLocationReport.add(new BoundingLocationReport());
		List<EarthingLocationReport> listofEarthingLocationReport= new ArrayList<EarthingLocationReport>();
		listofEarthingLocationReport.add(new EarthingLocationReport());
	 
		supplyCharacteristics.setBoundingLocationReport(listofBoundingLocationReport);
		supplyCharacteristics.setEarthingLocationReport(listofEarthingLocationReport);
		supplyCharacteristics.setInstalLocationReport(listofInstalLocationReport);
		supplyCharacteristics.setCircuitBreaker(listofCircuitBreaker);
		supplyCharacteristics.setSupplyParameters(listofSupplyParameters);
		
		supplyCharacteristics.setMainNominalCurrent("1.2012,na,455,566");
		supplyCharacteristics.setMainNominalFrequency("NA");
		
		when(supplyCharacteristicsRepository.save(supplyCharacteristics)).thenReturn(supplyCharacteristics);
		supplyCharacteristicsServiceImpl.addCharacteristics(supplyCharacteristics);
	}
	 
	@Test
	public void testUpdateCharacteristics() throws SupplyCharacteristicsException, DecimalConversionException, CompanyDetailsException {
		
		when(supplyCharacteristicsRepository.findById(1)).thenReturn(Optional.of(supplyCharacteristics));
		supplyCharacteristicsServiceImpl.updateCharacteristics(supplyCharacteristics);
		
		SupplyCharacteristics supplyCharacteristics_2 = new SupplyCharacteristics();
		supplyCharacteristics_2.setSiteId(12);
		supplyCharacteristics_2.setUserName("cape");
		supplyCharacteristics_2.setSupplyCharacteristicsId(1);
		
		when(supplyCharacteristicsRepository.findById(2)).thenReturn(Optional.of(supplyCharacteristics));
		SupplyCharacteristicsException assertThrows = Assertions.assertThrows(SupplyCharacteristicsException.class,
				() -> supplyCharacteristicsServiceImpl.updateCharacteristics(supplyCharacteristics_2));
		
		assertEquals(assertThrows.getMessage(),"Given SiteId and ReportId is Invalid");
		
		supplyCharacteristics_2.setSiteId(null);
		when(supplyCharacteristicsRepository.findById(2)).thenReturn(Optional.of(supplyCharacteristics));
		SupplyCharacteristicsException assertThrows_1 = Assertions.assertThrows(SupplyCharacteristicsException.class,
				() -> supplyCharacteristicsServiceImpl.updateCharacteristics(supplyCharacteristics_2));
		
		assertEquals(assertThrows_1.getMessage(),"Invalid inputs");
	}
	
	@Test
	public void testretrieveCharacteristics_Success_Flow() throws SupplyCharacteristicsException {

		List<SupplyCharacteristics> ipaolist = new ArrayList<>();
		ipaolist.add(supplyCharacteristics);
		listOfComments = new ArrayList<SupplyCharacteristicComment>();
		listOfComments.add(supplyCharacteristicComment);
		supplyCharacteristics.setSupplyCharacteristicComment(listOfComments);
		
		when(supplyCharacteristicsRepository.findByUserNameAndSiteId(supplyCharacteristics.getUserName(),
				supplyCharacteristics.getSiteId())).thenReturn(supplyCharacteristics);
		supplyCharacteristicsServiceImpl.retrieveCharacteristics("Inspector@gmail.com", 1);

		SupplyCharacteristicsException assertThrows_1 = Assertions.assertThrows(SupplyCharacteristicsException.class,
				() -> supplyCharacteristicsServiceImpl.retrieveCharacteristics("cape", 2));
		assertEquals(assertThrows_1.getMessage(), "Given UserName & Site doesn't exist Inspection");

		SupplyCharacteristicsException assertThrows_2 = Assertions.assertThrows(SupplyCharacteristicsException.class,
				() -> supplyCharacteristicsServiceImpl.retrieveCharacteristics(null, 1));
		assertEquals(assertThrows_2.getMessage(), "Invalid Inputs");
	}
	
	@Test
	public void testSendComments() throws SupplyCharacteristicsException {
		listOfComments = new ArrayList<SupplyCharacteristicComment>();
		when(supplyCharacteristicsRepository.findBySiteId(1)).thenReturn(Optional.of(supplyCharacteristics));
		when(siteRepository.findById(1)).thenReturn(Optional.of(site));

		supplyCharacteristicsServiceImpl.sendComments("Viewer@gmail.com", 1, supplyCharacteristicComment);
 
		supplyCharacteristicComment.setCommentsId(1);
		listOfComments.add(supplyCharacteristicComment);
		supplyCharacteristics.setSupplyCharacteristicComment(listOfComments);
		when(supplyCharacteristicsRepository.findBySiteId(1)).thenReturn(Optional.of(supplyCharacteristics));
		supplyCharacteristicsServiceImpl.sendComments("Viewer@gmail.com", 1, supplyCharacteristicComment);

		listOfComments.remove(supplyCharacteristicComment);
		supplyCharacteristics.setSupplyCharacteristicComment(listOfComments);
		supplyCharacteristicsServiceImpl.sendComments("Viewer@gmail.com", 1, supplyCharacteristicComment);

		SupplyCharacteristicsException assertThrows = Assertions.assertThrows(SupplyCharacteristicsException.class,
				() -> supplyCharacteristicsServiceImpl.sendComments("Inspector@gmail.com", 1, supplyCharacteristicComment));

		assertEquals(assertThrows.getMessage(), "Given userName not allowing for SEND comment");
	}
	
	@Test
	public void testReplyComments() throws SupplyCharacteristicsException {
		listOfComments = new ArrayList<SupplyCharacteristicComment>();
		supplyCharacteristicComment.setCommentsId(1);
		listOfComments.add(supplyCharacteristicComment);
		supplyCharacteristics.setSupplyCharacteristicComment(listOfComments);
		when(supplyCharacteristicsRepository.findBySiteId(1)).thenReturn(Optional.of(supplyCharacteristics));
		when(siteRepository.findById(1)).thenReturn(Optional.of(site));
		supplyCharacteristicsServiceImpl.replyComments("Inspector@gmail.com", 1, supplyCharacteristicComment);

		SupplyCharacteristicsException assertThrows = Assertions.assertThrows(SupplyCharacteristicsException.class,
				() -> supplyCharacteristicsServiceImpl.replyComments("Viewer@gmail.com", 1, supplyCharacteristicComment));

		assertEquals(assertThrows.getMessage(), "Given userName not allowing for REPLY comment");
	}
	
	@Test
	public void testApproveComments() throws SupplyCharacteristicsException {
		listOfComments = new ArrayList<SupplyCharacteristicComment>();
		supplyCharacteristicComment.setCommentsId(1);
		listOfComments.add(supplyCharacteristicComment);
		supplyCharacteristics.setSupplyCharacteristicComment(listOfComments);
		when(supplyCharacteristicsRepository.findBySiteId(1)).thenReturn(Optional.of(supplyCharacteristics));
		when(siteRepository.findById(1)).thenReturn(Optional.of(site));
		supplyCharacteristicsServiceImpl.approveComments("Viewer@gmail.com", 1, supplyCharacteristicComment);
		
		SupplyCharacteristicsException assertThrows = Assertions.assertThrows(SupplyCharacteristicsException.class,
				() -> supplyCharacteristicsServiceImpl.approveComments("Inspector@gmail.com", 1, supplyCharacteristicComment));
		
		assertEquals(assertThrows.getMessage(), "Given userName not allowing for APPROVED comment");
	}
	
	@Test
	public void testComments_Exception() throws SupplyCharacteristicsException {
		/*
		 * 
		 * supplyCharacteristics.setUserName(null);
		 * when(supplyCharacteristicsRepository.findBySiteId(1)).thenReturn(Optional.of(
		 * supplyCharacteristics));
		 * when(siteRepository.findById(1)).thenReturn(Optional.of(site));
		 * 
		 * SupplyCharacteristicsException assertThrows_1 =
		 * Assertions.assertThrows(SupplyCharacteristicsException.class, () ->
		 * supplyCharacteristicsServiceImpl.sendComments("Viewer@gmail.com", 2,
		 * supplyCharacteristicComment));
		 * 
		 * assertEquals(assertThrows_1.getMessage(),
		 * "Given username not have access for comments");
		 * 
		 * supplyCharacteristics.setSupplyCharacteristicComment(null);
		 * SupplyCharacteristicsException assertThrows_2 =
		 * Assertions.assertThrows(SupplyCharacteristicsException.class, () ->
		 * supplyCharacteristicsServiceImpl.sendComments("Viewer@gmail.com", 2,
		 * supplyCharacteristicComment)); assertEquals(assertThrows_2.getMessage(),
		 * "Siteinformation doesn't exist, try with different Site-Id");
		 * 
		 * SupplyCharacteristicsException assertThrows_3 =
		 * Assertions.assertThrows(SupplyCharacteristicsException.class, () ->
		 * supplyCharacteristicsServiceImpl.sendComments("Viewer@gmail.com", null,
		 * supplyCharacteristicComment)); assertEquals(assertThrows_3.getMessage(),
		 * "Invalid Inputs");
		 * 
		 */}
}