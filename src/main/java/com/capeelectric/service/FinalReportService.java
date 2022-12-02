package com.capeelectric.service;

import java.util.List;
import java.util.Optional;

import com.capeelectric.exception.FinalReportException;
import com.capeelectric.model.FinalReport;
import com.capeelectric.model.Site;

/**
 * 
 * @author capeelectricsoftware
 *
 */
public interface FinalReportService {
	List<Site> retrieveListOfSite(String userName) throws FinalReportException;

	Optional<FinalReport> retrieveFinalReport(String userName, Integer siteId) throws FinalReportException;
	
	List<Site> retrieveAllSites() throws FinalReportException;
}
