package com.capeelectric.model;

import java.util.List;

public class NotificationCommentList {

	private List<ReportDetailsComment> reportDetailsComment;

	private List<SupplyCharacteristicComment> supplyCharacteristicComment;

	private List<PeriodicInspectionComment> periodicInspectionComment;

	private List<TestingReportComment> testingReportComment;

	private List<SummaryComment> summaryComment;

	public List<ReportDetailsComment> getReportDetailsComment() {
		return reportDetailsComment;
	}

	public void setReportDetailsComment(List<ReportDetailsComment> reportDetailsComment) {
		this.reportDetailsComment = reportDetailsComment;
	}

	public List<SupplyCharacteristicComment> getSupplyCharacteristicComment() {
		return supplyCharacteristicComment;
	}

	public void setSupplyCharacteristicComment(List<SupplyCharacteristicComment> supplyCharacteristicComment) {
		this.supplyCharacteristicComment = supplyCharacteristicComment;
	}

	public List<PeriodicInspectionComment> getPeriodicInspectionComment() {
		return periodicInspectionComment;
	}

	public void setPeriodicInspectionComment(List<PeriodicInspectionComment> periodicInspectionComment) {
		this.periodicInspectionComment = periodicInspectionComment;
	}

	public List<TestingReportComment> getTestingReportComment() {
		return testingReportComment;
	}

	public void setTestingReportComment(List<TestingReportComment> testingReportComment) {
		this.testingReportComment = testingReportComment;
	}

	public List<SummaryComment> getSummaryComment() {
		return summaryComment;
	}

	public void setSummaryComment(List<SummaryComment> summaryComment) {
		this.summaryComment = summaryComment;
	}

}
