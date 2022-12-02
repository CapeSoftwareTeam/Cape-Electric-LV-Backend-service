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
import com.capeelectric.model.Register;
import com.capeelectric.model.Site;
import com.capeelectric.model.SitePersons;
import com.capeelectric.model.User;
import com.capeelectric.repository.RegistrationRepository;
import com.capeelectric.repository.SitePersonsRepository;
import com.capeelectric.repository.SiteRepository;
import com.capeelectric.service.impl.SiteServiceImpl;
import com.capeelectric.util.UserFullName;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class SiteDetailsServiceTest {

	@MockBean
	private SiteRepository siteRepository;

	@InjectMocks
	private SiteServiceImpl siteServiceImpl;

	@MockBean
	private RegistrationRepository registrationRepository;

	@MockBean
	private CompanyDetailsException companyDetailsException;

	@MockBean
	private SitePersonsRepository sitePersonsRepository;
	
	@MockBean
	private UserFullName userName;

	private SitePersons sitePersons1 = new SitePersons();

	private SitePersons sitePersons2 = new SitePersons();

	private SitePersons sitePersons3 = new SitePersons();

	private Set<SitePersons> sitePersonsSet;

	private Site site;
	
	private Register register;

	{
		register =new Register();
		register.setUsername("hasan");
		
		site = new Site();
		site.setUserName("hasan");
		site.setSiteId(1);
		site.setSite("user");
		site.setCompanyName("Cape");
		site.setDepartmentName("electric");

		sitePersons1.setPersonId(1);
		sitePersons1.setSiteName("user");
		sitePersons1.setPersonInchargeEmail("LVsystem@gmail.com");
		sitePersons1.setInActive(true);
		sitePersons2.setPersonId(2);
		sitePersons2.setSiteName("user");
		sitePersons2.setPersonInchargeEmail("Cape@gmail.com");
		sitePersons2.setInActive(true);
		sitePersonsSet = new HashSet<SitePersons>();

	}

	@Test
	public void testupdateSite_Success_Flow() throws CompanyDetailsException {
		test();
		sitePersonsSet.add(sitePersons1);
		site.setSitePersons(sitePersonsSet);

		siteServiceImpl.updateSite(site);

	}

	@Test
	public void testupdateSiteRemovedInactivePerson() throws CompanyDetailsException {
		test();
		sitePersons1.setInActive(false);
		sitePersonsSet.add(sitePersons1);
		site.setSitePersons(sitePersonsSet);
		sitePersonsSet.add(sitePersons2);
		sitePersonsSet.add(sitePersons2);
		site.setSitePersons(sitePersonsSet);
		when(siteRepository.save(site)).thenReturn(site);

		sitePersons3.setPersonInchargeEmail("Cape1@gmail.com");
		sitePersons3.setSiteName("user");
		sitePersons3.setInActive(true);
		sitePersonsSet.add(sitePersons3);
		site.setSitePersons(sitePersonsSet);
		siteServiceImpl.updateSite(site);
	}

	@Test
	public void testupdateSite_SiteNotPresentException() throws CompanyDetailsException {
		test();
		Site site2 = new Site();
		site2.setUserName("hasan");
		site2.setSite("user");

		site2.setSitePersons(sitePersonsSet);
		site2.setSiteId(2);
		CompanyDetailsException assertThrows = Assertions.assertThrows(CompanyDetailsException.class,
				() -> siteServiceImpl.updateSite(site2));
		assertEquals(assertThrows.getMessage(), "user site not present");
	}

	@Test
	public void testupdateSite_PersonInchargEmailalreadypresentException() throws CompanyDetailsException {
		test();
		sitePersons3.setPersonInchargeEmail("Cape@gmail.com");
		sitePersons3.setInActive(true);
		sitePersonsSet.add(sitePersons2);
		sitePersonsSet.add(sitePersons3);
		site.setSitePersons(sitePersonsSet);
		CompanyDetailsException assertThrows = Assertions.assertThrows(CompanyDetailsException.class,
				() -> siteServiceImpl.updateSite(site));
		assertEquals(assertThrows.getMessage(), "PersonInchargEmail already present");

	}

	@Test
	public void testupdateSite_InvalidInputsException() throws CompanyDetailsException {
		site.setUserName(null);
		CompanyDetailsException assertThrows = Assertions.assertThrows(CompanyDetailsException.class,
				() -> siteServiceImpl.updateSite(site));
		assertEquals(assertThrows.getMessage(), "Invalid Inputs");

	}

//	@Test
//	public void testaddSite_Success_Flow() throws CompanyDetailsException {
//
//		User user = new User();
//		user.setFirstname("firstName");
//		user.setLastname("lastName");
//		sitePersonsSet.add(sitePersons1);
//		site.setSitePersons(sitePersonsSet);
//		siteServiceImpl.addSite(site);
//	}

	@Test
	public void testaddSite_Exception() throws CompanyDetailsException {

		ArrayList<Site> list = new ArrayList<Site>();
		list.add(site);
		when(siteRepository.findByUserName(site.getUserName())).thenReturn(list);
		when(sitePersonsRepository.findBySiteNameAndPersonInchargeEmail("user1","Cape@gmail.com"))
				.thenReturn(Optional.of(sitePersons2));
		when(siteRepository.findByCompanyNameAndDepartmentNameAndSite("Cape", "electric","user")).thenReturn(site);
	 
		Site site2 = new Site();
		site2.setSite("user1");
		site2.setUserName("hasan");
		sitePersons3.setSiteName("user1");
		sitePersons3.setPersonInchargeEmail("Cape@gmail.com");
		sitePersons3.setInActive(true);
		sitePersonsSet.add(sitePersons3);
		site2.setSitePersons(sitePersonsSet);
		CompanyDetailsException assertThrows5 = Assertions.assertThrows(CompanyDetailsException.class,
				() -> siteServiceImpl.addSite(site2));
		assertEquals(assertThrows5.getMessage(), "PersonInchargEmail already present");

		when(siteRepository.findByUserNameAndSite("hasan", "user")).thenReturn(Optional.of(site));
		CompanyDetailsException assertThrows1 = Assertions.assertThrows(CompanyDetailsException.class,
				() -> siteServiceImpl.addSite(site));
		assertEquals(assertThrows1.getMessage(), "user: site already present");

		site.setUserName(null);
		CompanyDetailsException assertThrows4 = Assertions.assertThrows(CompanyDetailsException.class,
				() -> siteServiceImpl.addSite(site));
		assertEquals(assertThrows4.getMessage(), "Invalid Inputs");

	}

	@Test
	public void testdeleteSite_Success_Flow() throws CompanyDetailsException {

		when(siteRepository.findById(site.getSiteId())).thenReturn(Optional.of(site));
		siteServiceImpl.deleteSite(1);

	}

	@Test
	public void testdeleteSite_Exception() throws CompanyDetailsException {

		when(siteRepository.findById(site.getSiteId())).thenReturn(Optional.of(site));
		CompanyDetailsException assertThrows1 = Assertions.assertThrows(CompanyDetailsException.class,
				() -> siteServiceImpl.deleteSite(2));
		assertEquals(assertThrows1.getMessage(), "2 : this siteId not present");

		site.setSiteId(null);
		CompanyDetailsException assertThrows2 = Assertions.assertThrows(CompanyDetailsException.class,
				() -> siteServiceImpl.deleteSite(site.getSiteId()));
		assertEquals(assertThrows2.getMessage(), "Invalid Inputs");

	}

	@Test
	public void testretriveSite_Success_Flow() throws CompanyDetailsException {
		List<Site> siteList = new ArrayList<>();
		siteList.add(site);

		when(siteRepository.findByUserName(site.getUserName())).thenReturn(siteList);

		siteServiceImpl.retriveSite("nissan");

		CompanyDetailsException assertThrows = Assertions.assertThrows(CompanyDetailsException.class,
				() -> siteServiceImpl.retriveSite(null));
		assertEquals(assertThrows.getMessage(), "Invalid Inputs");

	}

	public void test() {
		when(userName.findByUserName("hasan")).thenReturn("hasan");
		List<Site> deptlist = new ArrayList<>();
		deptlist.add(site);

		when(siteRepository.findByUserName(site.getUserName())).thenReturn(deptlist);
		when(siteRepository.findByUserNameAndSite(site.getUserName(), site.getSite())).thenReturn(Optional.of(site));
		when(sitePersonsRepository.findBySiteNameAndPersonInchargeEmail(site.getSite(),sitePersons1.getPersonInchargeEmail()))
				.thenReturn(Optional.of(sitePersons1));
		when(sitePersonsRepository.findBySiteNameAndPersonInchargeEmail(site.getSite(),sitePersons2.getPersonInchargeEmail()))
				.thenReturn(Optional.of(sitePersons2));
	}

}
