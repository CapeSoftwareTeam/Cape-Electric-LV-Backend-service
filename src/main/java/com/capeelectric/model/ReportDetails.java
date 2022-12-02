
package com.capeelectric.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 *
 * @author capeelectricsoftware
 *
 */
@Entity
@Table(name = "REPORT_DETAILS_TABLE")
@NamedQueries(value = {
		@NamedQuery(name = "InstalReportDetailsRepository.findByUserNameAndSiteId", query = "select r from ReportDetails r where r.userName=:userName and r.siteId=:siteId"),
		@NamedQuery(name = "InstalReportDetailsRepository.findBySiteId", query = "select r from ReportDetails r where r.siteId=:siteId")

})
public class ReportDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REPORT_ID")
	private Integer reportId;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "SITE_ID")
	private Integer siteId;
	
	@Column(name = "DESCRIPTION_REPORT")
	private String descriptionReport;

	@Column(name = "REASON_REPORT")
	private String reasonOfReport;

	@Column(name = "INSTALLATION_TYPE")
	private String installationType;

	@Column(name = "DESCRIPTION_PREMISE")
	private String descriptionPremise;

	@Column(name = "ESTIMATED_WIRE_AGE")
	private String estimatedWireAge;

	@Column(name = "EVIDANCE_ADDITION")
	private String evidanceAddition;
	
	@Column(name = "EVIDANCE_WIRE_AGE")
	private String evidanceWireAge;

	@Column(name = "PREVIOUS_RECORDS")
	private String previousRecords;

	@Column(name = "LAST_INSPECTION")
	private String lastInspection;

	@Column(name = "NEXT_INSPECTION")
	private String nextInspection;

	@Column(name = "EXTENT_INSTALLATION")
	private String extentInstallation;

	@Column(name = "CLIENT_DETAILS")
	private String clientDetails;

	@Column(name = "INSTALLATION_DETAILS")
	private String installationDetails;

	@Column(name = "VERIFICATION_DATE")
	private String verificationDate;

	@Column(name = "VERIFIED_ENGINEER")
	private String verifiedEngineer;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "COMPANY")
	private String company;
	
	@Column(name = "INSPECTOR_DESIGNATION")
	private String inspectorDesignation;
	
	@Column(name = "INSPECTOR_COMPANYNAME")
	private String inspectorCompanyName;
	
	@Column(name = "LIMITATIONS")
	private String limitations;
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;
		
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "UPDATED_DATE")
	private LocalDateTime updatedDate;

	@JsonManagedReference
	@OneToMany(mappedBy = "reportDetails", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<SignatorDetails> signatorDetails;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "reportDetails", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ReportDetailsComment> reportDetailsComment;

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

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

	public String getDescriptionReport() {
		return descriptionReport;
	}

	public void setDescriptionReport(String descriptionReport) {
		this.descriptionReport = descriptionReport;
	}

	public String getReasonOfReport() {
		return reasonOfReport;
	}

	public void setReasonOfReport(String reasonOfReport) {
		this.reasonOfReport = reasonOfReport;
	}

	public String getInstallationType() {
		return installationType;
	}

	public void setInstallationType(String installationType) {
		this.installationType = installationType;
	}

	public String getDescriptionPremise() {
		return descriptionPremise;
	}

	public void setDescriptionPremise(String descriptionPremise) {
		this.descriptionPremise = descriptionPremise;
	}

	public String getEstimatedWireAge() {
		return estimatedWireAge;
	}

	public void setEstimatedWireAge(String estimatedWireAge) {
		this.estimatedWireAge = estimatedWireAge;
	}

	public String getEvidanceAddition() {
		return evidanceAddition;
	}

	public void setEvidanceAddition(String evidanceAddition) {
		this.evidanceAddition = evidanceAddition;
	}

	public String getEvidanceWireAge() {
		return evidanceWireAge;
	}

	public void setEvidanceWireAge(String evidanceWireAge) {
		this.evidanceWireAge = evidanceWireAge;
	}

	public String getPreviousRecords() {
		return previousRecords;
	}

	public void setPreviousRecords(String previousRecords) {
		this.previousRecords = previousRecords;
	}

	public String getLastInspection() {
		return lastInspection;
	}

	public void setLastInspection(String lastInspection) {
		this.lastInspection = lastInspection;
	}

	public String getNextInspection() {
		return nextInspection;
	}

	public void setNextInspection(String nextInspection) {
		this.nextInspection = nextInspection;
	}

	public String getExtentInstallation() {
		return extentInstallation;
	}

	public void setExtentInstallation(String extentInstallation) {
		this.extentInstallation = extentInstallation;
	}

	public String getClientDetails() {
		return clientDetails;
	}

	public void setClientDetails(String clientDetails) {
		this.clientDetails = clientDetails;
	}

	public String getInstallationDetails() {
		return installationDetails;
	}

	public void setInstallationDetails(String installationDetails) {
		this.installationDetails = installationDetails;
	}

	public String getVerificationDate() {
		return verificationDate;
	}

	public void setVerificationDate(String verificationDate) {
		this.verificationDate = verificationDate;
	}

	public String getVerifiedEngineer() {
		return verifiedEngineer;
	}

	public void setVerifiedEngineer(String verifiedEngineer) {
		this.verifiedEngineer = verifiedEngineer;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getInspectorDesignation() {
		return inspectorDesignation;
	}

	public void setInspectorDesignation(String inspectorDesignation) {
		this.inspectorDesignation = inspectorDesignation;
	}

	public String getInspectorCompanyName() {
		return inspectorCompanyName;
	}

	public void setInspectorCompanyName(String inspectorCompanyName) {
		this.inspectorCompanyName = inspectorCompanyName;
	}

	public String getLimitations() {
		return limitations;
	}

	public void setLimitations(String limitations) {
		this.limitations = limitations;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Set<SignatorDetails> getSignatorDetails() {
		return signatorDetails;
	}

	public void setSignatorDetails(Set<SignatorDetails> signatorDetails) {
		this.signatorDetails = signatorDetails;
	}

	public List<ReportDetailsComment> getReportDetailsComment() {
		return reportDetailsComment;
	}

	public void setReportDetailsComment(List<ReportDetailsComment> reportDetailsComment) {
		this.reportDetailsComment = reportDetailsComment;
	}

}
