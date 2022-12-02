package com.capeelectric.service;

import com.capeelectric.exception.CompanyDetailsException;
import com.capeelectric.exception.PeriodicTestingException;
import com.capeelectric.model.TestingReport;
import com.capeelectric.model.TestingReportComment;

/**
 * 
 * @author capeelectricsoftware
 *
 */
public interface PeriodicTestingService {

	public void addTestingReport(TestingReport testing) throws PeriodicTestingException, CompanyDetailsException;

	public TestingReport retrieveTestingReport(String userName, Integer siteId) throws PeriodicTestingException;
	
	public TestingReport retrieveTestingReport(Integer siteId) throws PeriodicTestingException;

	public void updatePeriodicTesting(TestingReport testingReport) throws PeriodicTestingException, CompanyDetailsException;

	public void sendComments(String userName, Integer siteId, TestingReportComment testingReportComment)
			throws PeriodicTestingException;

	public String replyComments(String userName, Integer siteId, TestingReportComment testingReportComment)
			throws PeriodicTestingException;

	public void approveComments(String userName, Integer siteId, TestingReportComment testingReportComment)
			throws PeriodicTestingException;

}
