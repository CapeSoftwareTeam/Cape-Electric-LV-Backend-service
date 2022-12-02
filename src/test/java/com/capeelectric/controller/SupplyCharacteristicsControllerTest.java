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
import com.capeelectric.exception.DecimalConversionException;
import com.capeelectric.exception.RegistrationException;
import com.capeelectric.exception.SupplyCharacteristicsException;
import com.capeelectric.model.SupplyCharacteristicComment;
import com.capeelectric.model.SupplyCharacteristics;
import com.capeelectric.service.impl.SupplyCharacteristicsServiceImpl;
import com.capeelectric.util.SendReplyComments;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class SupplyCharacteristicsControllerTest {

	@MockBean
	private SupplyCharacteristicsServiceImpl supplyCharacteristicsServiceImpl;

	@InjectMocks
	private SupplyCharacteristicsController supplyCharacteristicsController;

	@MockBean
	private SupplyCharacteristicsException supplyCharacteristicsException;
	
	@MockBean
	private SendReplyComments sendReplyComments;

	private SupplyCharacteristics supplyCharacteristics;
	
	private SupplyCharacteristicComment supplyCharacteristicComment;

	{
		supplyCharacteristics = new SupplyCharacteristics();
		supplyCharacteristics.setSupplyCharacteristicsId(1);
		supplyCharacteristics.setUserName("cape");
		supplyCharacteristics.setSiteId(1);
		
		supplyCharacteristicComment = new SupplyCharacteristicComment();
		supplyCharacteristicComment.setViewerComment("question");
		supplyCharacteristicComment.setViewerFlag("1");
	}

	@Test
	public void testAddCharacteristics() throws SupplyCharacteristicsException, DecimalConversionException, CompanyDetailsException {
		ResponseEntity<String> expectedResponseEntity = new ResponseEntity<String>(HttpStatus.CREATED);
		ResponseEntity<String> actualResponseEntity = supplyCharacteristicsController
				.addCharacteristics(supplyCharacteristics);
		assertEquals(actualResponseEntity.getStatusCode(), expectedResponseEntity.getStatusCode());
	}

	@Test
	public void testRetrieveCharacteristics() throws SupplyCharacteristicsException {
		ResponseEntity<List<SupplyCharacteristics>> expectedResponseEntity = new ResponseEntity<List<SupplyCharacteristics>>(
				HttpStatus.OK);
		ResponseEntity<SupplyCharacteristics> actualResponseEntity = supplyCharacteristicsController
				.retrieveCharacteristics(supplyCharacteristics.getUserName(), supplyCharacteristics.getSiteId());
		assertEquals(actualResponseEntity.getStatusCode(), expectedResponseEntity.getStatusCode());
	}
	
	@Test
	public void testUpdateCharacteristics() throws SupplyCharacteristicsException, DecimalConversionException, CompanyDetailsException {
		ResponseEntity<String> expectedResponseEntity = new ResponseEntity<String>("SupplyCharacteristics Data successfully Updated",HttpStatus.OK);
		ResponseEntity<String> actualResponseEntity = supplyCharacteristicsController
				.updateCharacteristics(supplyCharacteristics);
		assertEquals(actualResponseEntity.getStatusCode(), expectedResponseEntity.getStatusCode());
	}
	
	@Test
	public void testSendComments() throws RegistrationException, Exception, SupplyCharacteristicsException {
		doNothing().when(supplyCharacteristicsServiceImpl).sendComments("Viewer@gmail.com", 1, supplyCharacteristicComment());
		ResponseEntity<Void> sendComments = supplyCharacteristicsController.sendComments("Viewer@gmail.com", 1,
				supplyCharacteristicComment());
		assertEquals(sendComments.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testReplyComments() throws RegistrationException, Exception, SupplyCharacteristicsException {

		when(supplyCharacteristicsServiceImpl.replyComments("Inspector@gmail.com", 1, supplyCharacteristicComment))
				.thenReturn("Viewer@gmail.com");

		ResponseEntity<Void> sendComments = supplyCharacteristicsController.replyComments("Inspector@gmail.com", 1,
				supplyCharacteristicComment);

		assertEquals(sendComments.getStatusCode(), HttpStatus.OK);

		when(supplyCharacteristicsServiceImpl.replyComments("Inspector@gmail.com", 1, supplyCharacteristicComment)).thenReturn(null);
		SupplyCharacteristicsException exception = Assertions.assertThrows(SupplyCharacteristicsException.class,
				() -> supplyCharacteristicsController.replyComments("Inspector@gmail.com", 1, supplyCharacteristicComment));

		assertEquals(exception.getMessage(), "No viewer userName avilable");

	}

	@Test
	public void testApproveComments() throws RegistrationException, Exception, SupplyCharacteristicsException {

		supplyCharacteristicsController.approveComments("Inspector@gmail.com", 1, supplyCharacteristicComment());

	}

	private SupplyCharacteristicComment supplyCharacteristicComment() {
		SupplyCharacteristicComment supplyCharacteristicComment = new SupplyCharacteristicComment();
		supplyCharacteristicComment.setViewerComment("question");
		supplyCharacteristicComment.setViewerFlag("1");
		supplyCharacteristics.setUserName("Inspector@gmail.com");
		supplyCharacteristics.setSiteId(1);
		return supplyCharacteristicComment;
	}

}
