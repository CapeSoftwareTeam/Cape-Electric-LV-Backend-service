package com.capeelectric.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * 
 * @author capeelectricsoftware
 *
 */
@Entity
@Table(name = "reportdetails_comments_table")
public class ReportDetailsComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7288035826161063750L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "COMMENTS_ID")
	private Integer commentsId;

	@Column(name = "VIEWER_USERNAME")
	private String viewerUserName;
	
	@Column(name = "INSPECTOR_USERNAME")
	private String inspectorUserName;
	
	@Column(name = "VIEWER_EMAIL")
	private String viewerUserEmail;
	
	@Column(name = "INSPECTOR_EMAIL")
	private String inspectorEmail;
	
	@Column(name = "SITE_NAME")
	private String siteName;
	
	@Column(name = "VIEWER_COMMENT")
	private String viewerComment;
	
	@Column(name = "INSPECTOR_COMMENT")
	private String inspectorComment;

	@Column(name = "VIEWER_UPDATEDATE")
	private LocalDateTime viewerDate;

	@Column(name = "INSPECTOR_UPDATEDATE")
	private LocalDateTime inspectorDate;

	@Column(name = "COMMENT_APPROVE_OR_REJECT")
	private String approveOrReject;
	
	@Column(name = "VIEWER_FLAG")
	private String viewerFlag;
	
	@Column(name = "INSPECTOR_FLAG")
	private String inspectorFlag;
	
	@Column(name = "NO_OF_COMMENT")
	private Integer noOfComment;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "REPORT_ID")
	private ReportDetails reportDetails;

	public Integer getCommentsId() {
		return commentsId;
	}

	public void setCommentsId(Integer commentsId) {
		this.commentsId = commentsId;
	}

	public String getViewerUserName() {
		return viewerUserName;
	}

	public void setViewerUserName(String viewerUserName) {
		this.viewerUserName = viewerUserName;
	}

	public String getInspectorUserName() {
		return inspectorUserName;
	}

	public void setInspectorUserName(String inspectorUserName) {
		this.inspectorUserName = inspectorUserName;
	}

	public String getViewerUserEmail() {
		return viewerUserEmail;
	}

	public void setViewerUserEmail(String viewerUserEmail) {
		this.viewerUserEmail = viewerUserEmail;
	}

	public String getInspectorEmail() {
		return inspectorEmail;
	}

	public void setInspectorEmail(String inspectorEmail) {
		this.inspectorEmail = inspectorEmail;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getViewerComment() {
		return viewerComment;
	}

	public void setViewerComment(String viewerComment) {
		this.viewerComment = viewerComment;
	}

	public String getInspectorComment() {
		return inspectorComment;
	}

	public void setInspectorComment(String inspectorComment) {
		this.inspectorComment = inspectorComment;
	}

	public LocalDateTime getViewerDate() {
		return viewerDate;
	}

	public void setViewerDate(LocalDateTime viewerDate) {
		this.viewerDate = viewerDate;
	}

	public LocalDateTime getInspectorDate() {
		return inspectorDate;
	}

	public void setInspectorDate(LocalDateTime inspectorDate) {
		this.inspectorDate = inspectorDate;
	}

	public String getApproveOrReject() {
		return approveOrReject;
	}

	public void setApproveOrReject(String approveOrReject) {
		this.approveOrReject = approveOrReject;
	}

	public ReportDetails getReportDetails() {
		return reportDetails;
	}

	public void setReportDetails(ReportDetails reportDetails) {
		this.reportDetails = reportDetails;
	}

	public String getViewerFlag() {
		return viewerFlag;
	}

	public void setViewerFlag(String viewerFlag) {
		this.viewerFlag = viewerFlag;
	}

	public String getInspectorFlag() {
		return inspectorFlag;
	}

	public void setInspectorFlag(String inspectorFlag) {
		this.inspectorFlag = inspectorFlag;
	}

	public Integer getNoOfComment() {
		return noOfComment;
	}

	public void setNoOfComment(Integer noOfComment) {
		this.noOfComment = noOfComment;
	}

}
