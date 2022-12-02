package com.capeelectric.service;

import com.capeelectric.exception.CompanyDetailsException;
import com.capeelectric.exception.InspectionException;
import com.capeelectric.model.PeriodicInspection;
import com.capeelectric.model.PeriodicInspectionComment;

/**
 * 
 * @author capeelectricsoftware
 *
 */
public interface InspectionService {

	public void addInspectionDetails(PeriodicInspection periodicInspection) throws InspectionException, CompanyDetailsException;;

	public PeriodicInspection retrieveInspectionDetails(String userName, Integer siteId)
			throws InspectionException;
	
	public PeriodicInspection retrieveInspectionDetails(Integer siteId)
			throws InspectionException;

	public void updateInspectionDetails(PeriodicInspection periodicInspection) throws InspectionException, CompanyDetailsException;

	public void sendComments(String userName, Integer siteId, PeriodicInspectionComment periodicInspectionComment)
			throws InspectionException;

	public String replyComments(String userName, Integer siteId, PeriodicInspectionComment periodicInspectionComment)
			throws InspectionException;

	public void approveComments(String userName, Integer siteId, PeriodicInspectionComment periodicInspectionComment)
			throws InspectionException;
	
	public String retrieveLocation(String distributionDetails, String reference, String location);

}
