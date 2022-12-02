package com.capeelectric.model;

/**
 *
 * @author capeelectricsoftware
 *
 */

public class FinalReport {

	private String userName;

	private Integer siteId;
	
	private String allStepsCompleted;

	private ReportDetails reportDetails;

	private SupplyCharacteristics supplyCharacteristics;

	private PeriodicInspection periodicInspection;

	private TestingReport testingReport;

	private Summary summary;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public ReportDetails getReportDetails() {
		return reportDetails;
	}

	public void setReportDetails(ReportDetails reportDetails) {
		this.reportDetails = reportDetails;
	}

	public SupplyCharacteristics getSupplyCharacteristics() {
		return supplyCharacteristics;
	}

	public void setSupplyCharacteristics(SupplyCharacteristics supplyCharacteristics) {
		this.supplyCharacteristics = supplyCharacteristics;
	}

	public PeriodicInspection getPeriodicInspection() {
		return periodicInspection;
	}

	public void setPeriodicInspection(PeriodicInspection periodicInspection) {
		this.periodicInspection = periodicInspection;
	}

	public TestingReport getTestingReport() {
		return testingReport;
	}

	public void setTestingReport(TestingReport testingReport) {
		this.testingReport = testingReport;
	}

	public Summary getSummary() {
		return summary;
	}

	public void setSummary(Summary summary) {
		this.summary = summary;
	}

	public String getAllStepsCompleted() {
		return allStepsCompleted;
	}

	public void setAllStepsCompleted(String allStepsCompleted) {
		this.allStepsCompleted = allStepsCompleted;
	}

}
