package com.capeelectric.service;

import java.util.List;

import com.capeelectric.exception.CompanyDetailsException;
import com.capeelectric.exception.InspectionException;
import com.capeelectric.exception.InstalReportException;
import com.capeelectric.exception.ObservationException;
import com.capeelectric.exception.PdfException;
import com.capeelectric.exception.PeriodicTestingException;
import com.capeelectric.exception.SummaryException;
import com.capeelectric.exception.SupplyCharacteristicsException;
import com.capeelectric.model.Summary;
import com.capeelectric.model.SummaryComment;

/**
 * 
 * @author capeelectricsoftware
 *
 */
public interface SummaryService {

	public void addSummary(Summary summary) throws SummaryException, CompanyDetailsException, InstalReportException, SupplyCharacteristicsException, InspectionException, PeriodicTestingException, Exception, ObservationException,PdfException;

	public List<Summary> retrieveSummary(String userName, Integer siteId) throws SummaryException;
	
	public Summary retrieveSummary(Integer siteId) throws SummaryException;

	public void updateSummary(Summary summary,Boolean superAdminFlag) throws SummaryException, CompanyDetailsException, InstalReportException, SupplyCharacteristicsException,
	 	InspectionException, PeriodicTestingException, Exception, ObservationException, PdfException;

	public void sendComments(String userName, Integer siteId, SummaryComment summaryComment) throws SummaryException;

	public String replyComments(String userName, Integer siteId, SummaryComment summaryComment) throws SummaryException;

	public void approveComments(String userName, Integer siteId, SummaryComment summaryComment) throws SummaryException;

}
