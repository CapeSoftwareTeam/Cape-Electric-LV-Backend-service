package com.capeelectric.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * 
 * @author capeelectricsoftware
 *
 */
@Entity
@Table(name = "SUMMARY_TABLE")
@NamedQueries(value = {
		@NamedQuery(name = "SummaryRepository.findBySiteId", query = "select s from Summary s where s.siteId=:siteId"),
		@NamedQuery(name = "SummaryRepository.findByUserNameAndSiteId", query = "select s from Summary s where s.userName=:userName and s.siteId=:siteId") })
public class Summary implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SUMMARY_ID")
	private Integer summaryId;

	@Column(name = "SITE_ID")
	private Integer siteId;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "LIMITATIONS_INSPECTION")
	private String limitationsInspection;
	
	@Column(name = "EXTENT_INSTALLATION")
	private String extentInstallation;

	@Column(name = "AGREED_LIMITATIONS")
	private String agreedLimitations;

	@Column(name = "AGREED_WITH")
	private String agreedWith;

	@Column(name = "OPERATIONAL_LIMITATIONS")
	private String operationalLimitations;

	@Column(name = "INSPECTION_TESTING_DETAILED")
	private String inspectionTestingDetailed;
	
	@Column(name = "INSPECTION_REPORT_TESTRESULT")
	private String inspectionReportTestResult;

	@Column(name = "RECOMMENDATIONS_DATE")
	private String recommendationsDate;

	@Column(name = "GENERAL_CONDITION_INSTALLATION")
	private String generalConditionInstallation;

	@Column(name = "OVERALL_ASSESSMENT_INSTALLATION")
	private String overallAssessmentInstallation;
	
	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "UPDATED_DATE")
	private LocalDateTime updatedDate;

	@JsonManagedReference
	@OneToMany(mappedBy = "summary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<SummaryDeclaration> summaryDeclaration;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "summary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<SummaryComment> summaryComment;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "summary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<SummaryObservation> summaryObservation;
	
	@Transient
	private AllComponentObservation allComponentObservation;

	public Integer getSummaryId() {
		return summaryId;
	}

	public void setSummaryId(Integer summaryId) {
		this.summaryId = summaryId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLimitationsInspection() {
		return limitationsInspection;
	}

	public void setLimitationsInspection(String limitationsInspection) {
		this.limitationsInspection = limitationsInspection;
	}

	public String getExtentInstallation() {
		return extentInstallation;
	}

	public void setExtentInstallation(String extentInstallation) {
		this.extentInstallation = extentInstallation;
	}

	public String getAgreedLimitations() {
		return agreedLimitations;
	}

	public void setAgreedLimitations(String agreedLimitations) {
		this.agreedLimitations = agreedLimitations;
	}

	public String getAgreedWith() {
		return agreedWith;
	}

	public void setAgreedWith(String agreedWith) {
		this.agreedWith = agreedWith;
	}

	public String getOperationalLimitations() {
		return operationalLimitations;
	}

	public void setOperationalLimitations(String operationalLimitations) {
		this.operationalLimitations = operationalLimitations;
	}

	public String getInspectionTestingDetailed() {
		return inspectionTestingDetailed;
	}

	public void setInspectionTestingDetailed(String inspectionTestingDetailed) {
		this.inspectionTestingDetailed = inspectionTestingDetailed;
	}

	public String getInspectionReportTestResult() {
		return inspectionReportTestResult;
	}

	public void setInspectionReportTestResult(String inspectionReportTestResult) {
		this.inspectionReportTestResult = inspectionReportTestResult;
	}

	public String getRecommendationsDate() {
		return recommendationsDate;
	}

	public void setRecommendationsDate(String recommendationsDate) {
		this.recommendationsDate = recommendationsDate;
	}

	public String getGeneralConditionInstallation() {
		return generalConditionInstallation;
	}

	public void setGeneralConditionInstallation(String generalConditionInstallation) {
		this.generalConditionInstallation = generalConditionInstallation;
	}

	public String getOverallAssessmentInstallation() {
		return overallAssessmentInstallation;
	}

	public void setOverallAssessmentInstallation(String overallAssessmentInstallation) {
		this.overallAssessmentInstallation = overallAssessmentInstallation;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public List<SummaryDeclaration> getSummaryDeclaration() {
		return summaryDeclaration;
	}

	public void setSummaryDeclaration(List<SummaryDeclaration> summaryDeclaration) {
		this.summaryDeclaration = summaryDeclaration;
	}

	public List<SummaryComment> getSummaryComment() {
		return summaryComment;
	}

	public void setSummaryComment(List<SummaryComment> summaryComment) {
		this.summaryComment = summaryComment;
	}

	public AllComponentObservation getAllComponentObservation() {
		return allComponentObservation;
	}

	public void setAllComponentObservation(AllComponentObservation allComponentObservation) {
		this.allComponentObservation = allComponentObservation;
	}

	public List<SummaryObservation> getSummaryObservation() {
		return summaryObservation;
	}

	public void setSummaryObservation(List<SummaryObservation> summaryObservation) {
		this.summaryObservation = summaryObservation;
	}
	
}
