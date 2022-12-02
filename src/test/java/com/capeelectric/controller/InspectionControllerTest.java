package com.capeelectric.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

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
import com.capeelectric.exception.RegistrationException;
import com.capeelectric.model.PeriodicInspection;
import com.capeelectric.model.PeriodicInspectionComment;
import com.capeelectric.service.impl.InspectionServiceImpl;
import com.capeelectric.util.SendReplyComments;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class InspectionControllerTest {

	@MockBean
	private InspectionServiceImpl inspectionServiceImpl;

	@InjectMocks
	private InspectionController inspectionController;

	@MockBean
	private InspectionException inspectionException;
	
	@MockBean
	private SendReplyComments replyComments;
	
	private  PeriodicInspectionComment periodicInspectionComment;

	private PeriodicInspection periodicInspection;

	{
		periodicInspection = new PeriodicInspection();
		periodicInspection.setUserName("cape");
		periodicInspection.setSiteId(1);
		
		periodicInspectionComment = new PeriodicInspectionComment();
		periodicInspectionComment.setViewerComment("question");
		periodicInspectionComment.setViewerFlag("1");
		periodicInspection.setUserName("Inspector@gmail.com");
		periodicInspection.setSiteId(1);
	}

	@Test
	public void testAddInspectionDetails() throws InspectionException, CompanyDetailsException {
		ResponseEntity<String> expectedResponseEntity = new ResponseEntity<String>(HttpStatus.CREATED);
		ResponseEntity<String> actualResponseEntity = inspectionController.addInspectionDetails(periodicInspection);
		assertEquals(actualResponseEntity.getStatusCode(), expectedResponseEntity.getStatusCode());
	}

	@Test
	public void testRetrieveInspectionDetails() throws InspectionException {
		ResponseEntity<List<PeriodicInspection>> expectedResponseEntity = new ResponseEntity<List<PeriodicInspection>>(HttpStatus.OK);
		ResponseEntity<PeriodicInspection> actualResponseEntity = inspectionController
				.retrieveInspectionDetails(periodicInspection.getUserName(), periodicInspection.getSiteId());
		assertEquals(actualResponseEntity.getStatusCode(), expectedResponseEntity.getStatusCode());
	}
	
	@Test
	public void testUpdateInspectionDetails() throws InspectionException, CompanyDetailsException{
		ResponseEntity<String> expectedResponseEntity = new ResponseEntity<String>(HttpStatus.OK);
		ResponseEntity<String> actualResponseEntity = inspectionController
				.updateInspectionDetails(periodicInspection);
		assertEquals(actualResponseEntity.getStatusCode(), expectedResponseEntity.getStatusCode());
	}
	
	@Test
	public void testSendComments() throws InspectionException, RegistrationException, Exception {
		doNothing().when(inspectionServiceImpl).sendComments("Viewer@gmail.com", 1, periodicInspectionComment());
		ResponseEntity<Void> sendComments = inspectionController.sendComments("Viewer@gmail.com", 1,
				periodicInspectionComment());
		assertEquals(sendComments.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testReplyComments() throws RegistrationException, Exception, InspectionException {
		
		when(inspectionServiceImpl.replyComments("Inspector@gmail.com", 1,periodicInspectionComment)).thenReturn("Viewer@gmail.com");
		
		ResponseEntity<Void> sendComments = inspectionController.replyComments("Inspector@gmail.com", 1,
				periodicInspectionComment);

		assertEquals(sendComments.getStatusCode(), HttpStatus.OK);
	
		when(inspectionServiceImpl.replyComments("Inspector@gmail.com", 1,periodicInspectionComment)).thenReturn(null);
		InspectionException exception = Assertions.assertThrows(InspectionException.class,
				() -> inspectionController.replyComments("Inspector@gmail.com", 1, periodicInspectionComment));

		assertEquals(exception.getMessage(), "No viewer userName avilable");
	
	}
	@Test
	public void testApproveComments() throws RegistrationException, Exception, InspectionException {

		inspectionController.approveComments("Inspector@gmail.com", 1, periodicInspectionComment);

	}
	
	private PeriodicInspectionComment periodicInspectionComment() {
		periodicInspectionComment = new PeriodicInspectionComment();
		periodicInspectionComment.setViewerComment("question");
		periodicInspectionComment.setViewerFlag("1");
		periodicInspection.setUserName("Inspector@gmail.com");
		periodicInspection.setSiteId(1);
 		return periodicInspectionComment;
	}
}