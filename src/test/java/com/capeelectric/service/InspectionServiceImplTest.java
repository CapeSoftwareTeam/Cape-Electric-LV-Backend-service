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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.capeelectric.exception.CompanyDetailsException;
import com.capeelectric.exception.InspectionException;
import com.capeelectric.model.Circuit;
import com.capeelectric.model.ConsumerUnit;
import com.capeelectric.model.IpaoInspection;
import com.capeelectric.model.IsolationCurrent;
import com.capeelectric.model.PeriodicInspection;
import com.capeelectric.model.PeriodicInspectionComment;
import com.capeelectric.model.Register;
import com.capeelectric.model.Site;
import com.capeelectric.model.SitePersons;
import com.capeelectric.repository.InspectionConsumerUnitRepository;
import com.capeelectric.repository.InspectionRepository;
import com.capeelectric.repository.SiteRepository;
import com.capeelectric.service.impl.InspectionServiceImpl;
import com.capeelectric.util.FindNonRemovedObject;
import com.capeelectric.util.SiteDetails;
import com.capeelectric.util.UserFullName;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class InspectionServiceImplTest {

	@InjectMocks
	private InspectionServiceImpl inspectionServiceImpl;

	@MockBean
	private InspectionRepository inspectionRepository;

	@MockBean
	private InspectionException inspectionException;
	
	@MockBean
	private UserFullName userFullName;

	private PeriodicInspection periodicInspection;
	
	private PeriodicInspectionComment periodicInspectionComment;

	@MockBean
	private SiteRepository siteRepository;
	
	@MockBean
	private SiteDetails siteDetails;
	
	@MockBean
	private FindNonRemovedObject findNonRemovedObject;
	
	@MockBean
	private InspectionConsumerUnitRepository inspectionConsumerUnitRepository;
	
	private ArrayList<PeriodicInspectionComment> listOfComments;

    private Site site;
	
	private Register register;

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
		periodicInspection = new PeriodicInspection();
		periodicInspection.setUserName("cape");
		periodicInspection.setSiteId(1);
		
		periodicInspectionComment = new PeriodicInspectionComment();
		periodicInspectionComment.setViewerDate(LocalDateTime.now());
		periodicInspectionComment = new PeriodicInspectionComment();
		periodicInspectionComment.setViewerComment("question");
		periodicInspectionComment.setViewerFlag("1");
		periodicInspection.setUserName("Inspector@gmail.com");
		periodicInspection.setSiteId(1);
		
		listOfComments = new ArrayList<PeriodicInspectionComment>();
	    listOfComments.add(periodicInspectionComment);
	    periodicInspection.setPeriodicInspectorComment(listOfComments);
	    
	    ConsumerUnit consumerUnit = new ConsumerUnit();
		consumerUnit.setConsumerStatus("R");
		ArrayList<ConsumerUnit> consumerUnitList = new ArrayList<ConsumerUnit>();
		consumerUnitList.add(consumerUnit);
	    IpaoInspection ipaoInspection = new IpaoInspection();
		ipaoInspection.setInspectionFlag("a");
		ipaoInspection.setConsumerUnit(consumerUnitList);
		List<IpaoInspection> arrayList = new ArrayList<IpaoInspection>();
		arrayList.add(ipaoInspection);
		periodicInspection.setIpaoInspection(arrayList);
		
	}

	@Test
	public void testAddInspectionDetails_Success_Flow() throws InspectionException, CompanyDetailsException {
		List<IpaoInspection> listofIpaoInspection = new ArrayList<IpaoInspection>();
		listofIpaoInspection.add(new IpaoInspection());
		ArrayList<ConsumerUnit> consumerUnitList = new ArrayList<ConsumerUnit>();
		ConsumerUnit consumerUnit = new ConsumerUnit();
		consumerUnit.setLocation("BuildingOne");
		consumerUnitList.add(consumerUnit);
		List<Circuit> listofCircuit = new ArrayList<Circuit>();
		listofCircuit.add(new Circuit());
		List<IsolationCurrent> listofIsolationCurrent = new ArrayList<IsolationCurrent>();
		listofIsolationCurrent.add(new IsolationCurrent());
		
		IpaoInspection ipaoInspection = listofIpaoInspection.get(0);
		ipaoInspection.setConsumerUnit(consumerUnitList);
		ipaoInspection.setCircuit(listofCircuit);
		ipaoInspection.setIsolationCurrent(listofIsolationCurrent);
		periodicInspection.setIpaoInspection(listofIpaoInspection);
		Optional<PeriodicInspection> ipaolist = Optional.of(periodicInspection);

		when(inspectionConsumerUnitRepository.findByLocation("BuildingTwo")).thenReturn(consumerUnit);
		
		inspectionServiceImpl.addInspectionDetails(periodicInspection);
		
		ipaoInspection.setConsumerUnit(null);
		periodicInspection.setIpaoInspection(listofIpaoInspection);
		InspectionException assertThrows_1 = Assertions.assertThrows(InspectionException.class,
				() -> inspectionServiceImpl.addInspectionDetails(periodicInspection));
		assertEquals(assertThrows_1.getMessage(), "Please fill all the fields before clicking next button");
		
		
		when(inspectionRepository.findBySiteId(periodicInspection.getSiteId())).thenReturn(ipaolist);
		InspectionException assertThrows = Assertions.assertThrows(InspectionException.class,
				() -> inspectionServiceImpl.addInspectionDetails(periodicInspection));
		equals(assertThrows.getMessage());

	}

	@Test
	public void testAddInspectionDetails_Invalid_Inputs() throws InspectionException {
		periodicInspection.setUserName(null);
		inspectionRepository.save(periodicInspection);

		InspectionException assertThrows = Assertions.assertThrows(InspectionException.class,
				() -> inspectionServiceImpl.addInspectionDetails(periodicInspection));
		equals(assertThrows.getMessage());
	}

	@Test
	public void testAddInspectionDetails_Site_Id_Already_Present() throws InspectionException {

		Optional<PeriodicInspection> ipaolist;
		ipaolist = Optional.of(periodicInspection);

		when(inspectionRepository.findBySiteId(periodicInspection.getSiteId())).thenReturn(ipaolist);
		inspectionRepository.save(periodicInspection);
		InspectionException assertThrows = Assertions.assertThrows(InspectionException.class,
				() -> inspectionServiceImpl.addInspectionDetails(periodicInspection));
		equals(assertThrows.getMessage());
	}
	
	@Test
	public void testUpdateInspectionDetails() throws InspectionException, CompanyDetailsException {
		periodicInspection.setUserName("LVsystem@gmail.com");
		periodicInspection.setPeriodicInspectionId(1);
		when(inspectionRepository.findById(1)).thenReturn(Optional.of(periodicInspection));
		inspectionServiceImpl.updateInspectionDetails(periodicInspection);

		PeriodicInspection periodicInspection_1 = new PeriodicInspection();
		periodicInspection_1.setSiteId(12);
		periodicInspection_1.setUserName("cape");
		periodicInspection_1.setPeriodicInspectionId(12);

		when(inspectionRepository.findById(4)).thenReturn(Optional.of(periodicInspection));
		InspectionException assertThrows = Assertions.assertThrows(InspectionException.class,
				() -> inspectionServiceImpl.updateInspectionDetails(periodicInspection_1));
		assertEquals(assertThrows.getMessage(), "Given SiteId and ReportId is Invalid");

		periodicInspection.setSiteId(null);
		when(inspectionRepository.findById(2)).thenReturn(Optional.of(periodicInspection));
		InspectionException assertThrows_1 = Assertions.assertThrows(InspectionException.class,
				() -> inspectionServiceImpl.updateInspectionDetails(periodicInspection));
		assertEquals(assertThrows_1.getMessage(), "Invalid inputs");
	}
	
	@Test
	public void testRetrieveInspectionDetails_Success_Flow() throws InspectionException {

		List<PeriodicInspection> ipaolist = new ArrayList<>();
		ipaolist.add(periodicInspection);

		when(inspectionRepository.findByUserNameAndSiteId(periodicInspection.getUserName(),
				periodicInspection.getSiteId())).thenReturn(periodicInspection);
		inspectionServiceImpl.retrieveInspectionDetails("Inspector@gmail.com", 1);

		InspectionException assertThrows_1 = Assertions.assertThrows(InspectionException.class,
				() -> inspectionServiceImpl.retrieveInspectionDetails("cape", 2));
		assertEquals(assertThrows_1.getMessage(), "Given UserName & Site doesn't exist Inspection");

		InspectionException assertThrows_2 = Assertions.assertThrows(InspectionException.class,
				() -> inspectionServiceImpl.retrieveInspectionDetails(null, 1));
		assertEquals(assertThrows_2.getMessage(), "Invalid Inputs");
	}
	
	@Test
	public void testReplyComments() throws InspectionException {
		periodicInspectionComment.setCommentsId(1);
		listOfComments.add(periodicInspectionComment);
		periodicInspection.setPeriodicInspectorComment(listOfComments);
		when(inspectionRepository.findBySiteId(1)).thenReturn(Optional.of(periodicInspection));
		when(siteRepository.findById(1)).thenReturn(Optional.of(site));
		inspectionServiceImpl.replyComments("Inspector@gmail.com", 1, periodicInspectionComment);

		InspectionException assertThrows = Assertions.assertThrows(InspectionException.class,
				() -> inspectionServiceImpl.replyComments("Viewer@gmail.com", 1, periodicInspectionComment));

		assertEquals(assertThrows.getMessage(), "Given userName not allowing for REPLY comment");
	}
	
	@Test
	public void testApproveComments() throws InspectionException {
		
		periodicInspectionComment.setCommentsId(1);
		listOfComments.add(periodicInspectionComment);
		periodicInspection.setPeriodicInspectorComment(listOfComments);
		when(inspectionRepository.findBySiteId(1)).thenReturn(Optional.of(periodicInspection));
		when(siteRepository.findById(1)).thenReturn(Optional.of(site));
		inspectionServiceImpl.approveComments("Viewer@gmail.com", 1, periodicInspectionComment);
		
		InspectionException assertThrows = Assertions.assertThrows(InspectionException.class,
				() -> inspectionServiceImpl.approveComments("Inspector@gmail.com", 1, periodicInspectionComment));
		
		assertEquals(assertThrows.getMessage(), "Given userName not allowing for APPROVED comment");
	}
	
	@Test
	public void testComments_Exception() throws InspectionException {

		periodicInspection.setUserName(null);
		when(inspectionRepository.findBySiteId(1)).thenReturn(Optional.of(periodicInspection));
		when(siteRepository.findById(1)).thenReturn(Optional.of(site));

		InspectionException assertThrows_1 = Assertions.assertThrows(InspectionException.class,
				() -> inspectionServiceImpl.sendComments("Viewer@gmail.com", 1, periodicInspectionComment));

		assertEquals(assertThrows_1.getMessage(), "Given username not have access for comments");

		periodicInspection.setPeriodicInspectorComment(null);
		InspectionException assertThrows_2 = Assertions.assertThrows(InspectionException.class,
				() -> inspectionServiceImpl.sendComments("Viewer@gmail.com", 2, periodicInspectionComment));
		assertEquals(assertThrows_2.getMessage(), "Siteinformation doesn't exist, try with different Site-Id");

		InspectionException assertThrows_3 = Assertions.assertThrows(InspectionException.class,
				() -> inspectionServiceImpl.sendComments("Viewer@gmail.com", null, periodicInspectionComment));
		assertEquals(assertThrows_3.getMessage(), "Invalid Inputs");

	}
}