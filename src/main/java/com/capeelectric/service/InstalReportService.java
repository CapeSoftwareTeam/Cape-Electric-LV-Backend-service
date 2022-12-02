package com.capeelectric.service;

import com.capeelectric.exception.CompanyDetailsException;
import com.capeelectric.exception.InspectionException;
import com.capeelectric.exception.InstalReportException;
import com.capeelectric.model.ReportDetails;
import com.capeelectric.model.ReportDetailsComment;

/**
 **
  * @author capeelectricsoftware
 *
 */
public interface InstalReportService {

	public void addInstallationReport(ReportDetails reportDetails) throws InstalReportException, CompanyDetailsException;;

	public ReportDetails retrieveInstallationReport(String userName, Integer SiteId) throws InstalReportException, InspectionException;
	
	public ReportDetails retrieveInstallationReport(Integer SiteId) throws InstalReportException, InspectionException;

	public void updateInstallationReport(ReportDetails reportDetails) throws InstalReportException, CompanyDetailsException;

	public void sendComments(String userName, Integer siteId, ReportDetailsComment reportDetailsComment)
			throws InstalReportException;

	public String replyComments(String userName, Integer siteId, ReportDetailsComment reportDetailsComment)
			throws InstalReportException;

	public void approveComments(String userName, Integer siteId, ReportDetailsComment reportDetailsComment)
			throws InstalReportException;

}
