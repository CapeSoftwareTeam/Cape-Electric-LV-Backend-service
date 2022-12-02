package com.capeelectric.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capeelectric.exception.NotificationException;
import com.capeelectric.model.NotificationCommentList;
import com.capeelectric.model.PeriodicInspectionComment;
import com.capeelectric.model.Register;
import com.capeelectric.model.ReportDetailsComment;
import com.capeelectric.model.SummaryComment;
import com.capeelectric.model.SupplyCharacteristicComment;
import com.capeelectric.model.TestingReportComment;
import com.capeelectric.repository.PeriodicInspectionCommentRepository;
import com.capeelectric.repository.RegistrationRepository;
import com.capeelectric.repository.ReportDetailsCommentRepository;
import com.capeelectric.repository.SummaryCommentRepository;
import com.capeelectric.repository.SupplyCharacteristicCommentRepository;
import com.capeelectric.repository.TestingReportCommentRepository;
import com.capeelectric.service.NotificationService;
import com.capeelectric.util.Constants;

/**
 * 
 * @author capeelectricsoftware
 *
 */
@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private RegistrationRepository registrationRepository;

	@Autowired
	private ReportDetailsCommentRepository reportDetailsCommentRepository;

	@Autowired
	private SupplyCharacteristicCommentRepository supplyCharacteristicCommentRepository;

	@Autowired
	private PeriodicInspectionCommentRepository periodicInspectionCommentRepository;

	@Autowired
	private TestingReportCommentRepository testingReportCommentRepository;

	@Autowired
	private SummaryCommentRepository summaryCommentRepository;

	private List<ReportDetailsComment> reportDetailsComment;

	private List<SupplyCharacteristicComment> supplyCharacteristicComment;

	private List<PeriodicInspectionComment> periodicInspectionComment;

	private List<TestingReportComment> testingReportComment;

	private List<SummaryComment> summaryComment;

	private NotificationCommentList notificationCommentList;

	@Override
	public NotificationCommentList retrieveCommentsForUser(String userName) throws NotificationException {

		if (userName != null) {

			if (isInspector(userName)) {
				reportDetailsComment = reportDetailsCommentRepository.findByInspectorEmail(userName);
				supplyCharacteristicComment = supplyCharacteristicCommentRepository.findByInspectorEmail(userName);
				periodicInspectionComment = periodicInspectionCommentRepository.findByInspectorEmail(userName);
				testingReportComment = testingReportCommentRepository.findByInspectorEmail(userName);
				summaryComment = summaryCommentRepository.findByInspectorEmail(userName);
				return addAllComments();

			} else {
				reportDetailsComment = reportDetailsCommentRepository.findByViewerUserEmail(userName);
				supplyCharacteristicComment = supplyCharacteristicCommentRepository.findByViewerUserEmail(userName);
				periodicInspectionComment = periodicInspectionCommentRepository.findByViewerUserEmail(userName);
				testingReportComment = testingReportCommentRepository.findByViewerUserEmail(userName);
				summaryComment = summaryCommentRepository.findByViewerUserEmail(userName);
				return addAllComments();
			}
		} else {
			throw new NotificationException();
		}
	}

	private Boolean isInspector(String userName) {
		Optional<Register> isInspector = registrationRepository.findByUsername(userName);
		if (isInspector.isPresent() && isInspector.get() != null && isInspector.get().getAssignedBy() == null) {
			return true;
		} else {
			return false;
		}
	}

	private NotificationCommentList addAllComments() {
		notificationCommentList = new NotificationCommentList();
		if (reportDetailsComment.size() != Constants.LIST_ZERO) {
			notificationCommentList.setReportDetailsComment(reportDetailsComment);
		}
		if (supplyCharacteristicComment.size() != Constants.LIST_ZERO) {
			notificationCommentList.setSupplyCharacteristicComment(supplyCharacteristicComment);
		}
		if (periodicInspectionComment.size() != Constants.LIST_ZERO) {
			notificationCommentList.setPeriodicInspectionComment(periodicInspectionComment);
		}
		if (testingReportComment.size() != Constants.LIST_ZERO) {
			notificationCommentList.setTestingReportComment(testingReportComment);
		}
		if (summaryComment.size() != Constants.LIST_ZERO) {
			notificationCommentList.setSummaryComment(summaryComment);
		}
		return notificationCommentList;
	}
}
