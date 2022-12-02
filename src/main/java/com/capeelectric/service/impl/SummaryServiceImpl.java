package com.capeelectric.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capeelectric.exception.CompanyDetailsException;
import com.capeelectric.exception.InspectionException;
import com.capeelectric.exception.InstalReportException;
import com.capeelectric.exception.ObservationException;
import com.capeelectric.exception.PdfException;
import com.capeelectric.exception.PeriodicTestingException;
import com.capeelectric.exception.SummaryException;
import com.capeelectric.exception.SupplyCharacteristicsException;
import com.capeelectric.model.AllComponentObservation;
import com.capeelectric.model.InspectionOuterObservation;
import com.capeelectric.model.IpaoInspection;
import com.capeelectric.model.PeriodicInspection;
import com.capeelectric.model.ReportDetails;
import com.capeelectric.model.Site;
import com.capeelectric.model.SitePersons;
import com.capeelectric.model.Summary;
import com.capeelectric.model.SummaryComment;
import com.capeelectric.model.SummaryInnerObservation;
import com.capeelectric.model.SummaryObservation;
import com.capeelectric.model.SupplyCharacteristics;
import com.capeelectric.model.TestingReport;
import com.capeelectric.repository.InspectionRepository;
import com.capeelectric.repository.InstalReportDetailsRepository;
import com.capeelectric.repository.SiteRepository;
import com.capeelectric.repository.SummaryInnerObservationRepo;
import com.capeelectric.repository.SummaryObservationRepo;
import com.capeelectric.repository.SummaryRepository;
import com.capeelectric.repository.SupplyCharacteristicsRepository;
import com.capeelectric.repository.TestingReportRepository;
import com.capeelectric.service.InspectionServicePDF;
import com.capeelectric.service.InstalReportPDFService;
import com.capeelectric.service.PrintFinalPDFService;
import com.capeelectric.service.PrintService;
import com.capeelectric.service.PrintSupplyService;
import com.capeelectric.service.PrintTestingService;
import com.capeelectric.service.SummaryService;
import com.capeelectric.util.Constants;
import com.capeelectric.util.FindNonRemovedObject;
import com.capeelectric.util.SiteDetails;
import com.capeelectric.util.UserFullName;

/**
 * This SummaryServiceImpl service class doing add and retrieve operation related to Summary_model(SummaryObervation,SummaryDeclaration)
 * @author capeelectricsoftware
 *
 */
@Service
public class SummaryServiceImpl implements SummaryService {
	
	private static final Logger logger = LoggerFactory.getLogger(SummaryServiceImpl.class);

	@Autowired
	private InstalReportPDFService instalReportService;
	
	@Autowired
	private PrintSupplyService printSupplyService ;
	
	@Autowired
	private InspectionServicePDF inspectionServicePDF;
	
	@Autowired
	private PrintTestingService printTestingService;
	
	@Autowired
	private PrintService printService ;
	
	@Autowired
	private PrintFinalPDFService printFinalPDFService;
	
	@Autowired
	private SummaryRepository summaryRepository;
	
	@Autowired
	private InstalReportDetailsRepository installationReportRepository;
	
	@Autowired
	private SupplyCharacteristicsRepository supplyCharacteristicsRepository;
	
	@Autowired
	private InspectionRepository inspectionRepository;
	
	@Autowired
	private TestingReportRepository testingReportRepository;
	
	@Autowired
	private UserFullName userFullName;
	
	@Autowired
	private SiteRepository siteRepository;
	
	private SummaryComment summaryComment;
	
	private List<SummaryComment> listOfComments;
	
	private Site site;
	
	private Optional<Site> siteRepo;
	
	private String viewerName;
	
	@Autowired
	private SiteDetails siteDetails;
	
	@Autowired
	private FindNonRemovedObject findNonRemovedObject;
	
	@Autowired
	private SummaryObservationRepo summaryObservationRepo;
	
	@Autowired
	private SummaryInnerObservationRepo summaryInnerObservationRepo;
	
	/**
	 * @ siteId unique for summary object
	 * 
	 * @param Summary object addSummary method to find summary object based on input
	 *                summary_siteId if not available summary object will be saved
	 * @throws CompanyDetailsException
	 * @throws InstalReportException
	 * @throws SupplyCharacteristicsException
	 * @throws InspectionException
	 * @throws PeriodicTestingException
	 * @throws Exception
	 * @throws ObservationException
	 * @throws PdfException
	 * 
	 */
	@Transactional
	@Override
	public void addSummary(Summary summary) throws SummaryException, CompanyDetailsException, InstalReportException, SupplyCharacteristicsException, InspectionException, PeriodicTestingException, Exception, ObservationException, PdfException {
		listOfComments = new ArrayList<SummaryComment>();
		if (summary != null && summary.getUserName() != null && !summary.getUserName().isEmpty()
				&& summary.getSiteId() != null && summary.getSiteId() != 0) {
			Optional<Summary> summaryRepo = summaryRepository.findBySiteId(summary.getSiteId());
			Optional<SupplyCharacteristics> supplyCharacteristics = supplyCharacteristicsRepository
					.findBySiteId(summary.getSiteId());
			Optional<TestingReport> testingRepo = testingReportRepository.findBySiteId(summary.getSiteId());
			Optional<PeriodicInspection> periodicInspection = inspectionRepository.findBySiteId(summary.getSiteId());
			Optional<ReportDetails> reportDetailsRepo = installationReportRepository.findBySiteId(summary.getSiteId());
			if (!reportDetailsRepo.isPresent() && !supplyCharacteristics.isPresent() && !periodicInspection.isPresent()
					&& !testingRepo.isPresent()) {
				logger.error("Please enter details for all previous steps to proceed further");
				throw new SummaryException("Please enter details for all previous steps to proceed further");
			} else if (!reportDetailsRepo.isPresent()) {
				logger.error("Please enter Basic Information step to proceed further");
				throw new SummaryException("Please enter Basic Information step to proceed further");
			} else if (!supplyCharacteristics.isPresent()) {
				logger.error("Please enter Supply Characteristics step to proceed further");
				throw new SummaryException("Please enter Supply Characteristics step to proceed further");
			} else if (!periodicInspection.isPresent()) {
				logger.error("Please enter Inspection step to proceed further");
				throw new SummaryException("Please enter Inspection step to proceed further");
			} else if (!testingRepo.isPresent()) {
				logger.error("Please enter Testing step to proceed further");
				throw new SummaryException("Please enter Testing step to proceed further");
			} else {
				if (summary.getSummaryDeclaration() != null && summary.getSummaryDeclaration().size() > 0) {

					if (!summaryRepo.isPresent() || !summaryRepo.get().getSiteId().equals(summary.getSiteId())) {
						summaryComment = new SummaryComment();
						summaryComment.setInspectorFlag(Constants.INTIAL_FLAG_VALUE);
						summaryComment.setViewerFlag(Constants.INTIAL_FLAG_VALUE);
						summaryComment.setNoOfComment(1);
						summaryComment.setViewerDate(LocalDateTime.now());
						summaryComment.setSummary(summary);
						listOfComments.add(summaryComment);
						summary.setSummaryComment(listOfComments);
						summary.setCreatedDate(LocalDateTime.now());
						summary.setUpdatedDate(LocalDateTime.now());
						summary.setCreatedBy(userFullName.findByUserName(summary.getUserName()));
						summary.setUpdatedBy(userFullName.findByUserName(summary.getUserName()));
						summaryRepository.save(summary);
						logger.debug("Summary Details Successfully Saved in DB");
						siteDetails.updateSite(summary.getSiteId(),
								summary.getUserName(),"Step5 completed");
						logger.debug("Site Successfully updated in DB");
//						siteRepo = siteRepository.findById(summary.getSiteId());
//						if (siteRepo.isPresent() && siteRepo.get().getSiteId().equals(summary.getSiteId())) {
//							site = siteRepo.get();
//							site.setAllStepsCompleted("AllStepCompleted");
//							siteRepository.save(site);
//							logger.debug("AllStepCompleted information saved site table in DB"+summary.getUserName());
//						} else {
//							logger.error("Site-Id Information not Available in site_Table");
//							throw new SummaryException("Site-Id Information not Available in site_Table");
//						}

					} else {
						logger.error("Site-Id Already Available");
						throw new SummaryException("Site-Id Already Available");
					}
					
				} else {
					logger.error("Please fill all the fields before clicking next button");
					throw new SummaryException("Please fill all the fields before clicking next button");
				}
			}
		} else {
			logger.error("Invalid Inputs");
			throw new SummaryException("Invalid Inputs");
		}

	}

	
	private void printPDfDatas(Summary summary, Optional<ReportDetails> reportDetailsRepo, Optional<SupplyCharacteristics> supplyCharacteristics, Optional<PeriodicInspection> periodicInspection, Optional<TestingReport> testingRepo) throws InstalReportException, PdfException, SupplyCharacteristicsException,
			InspectionException, PeriodicTestingException, SummaryException, ObservationException, Exception {

		try {
			instalReportService.printBasicInfromation(summary.getUserName(), summary.getSiteId(), reportDetailsRepo);
			logger.debug("PDF printBasicInfromation() function called successfully");

			printSupplyService.printSupply(summary.getUserName(), summary.getSiteId(), supplyCharacteristics);
			logger.debug("PDF printSupply() function called successfully");

			inspectionServicePDF.printInspectionDetails(summary.getUserName(), summary.getSiteId(), periodicInspection);
			logger.debug("PDF printInspectionDetails() function called successfully");

			printTestingService.printTesting(summary.getUserName(), summary.getSiteId(), testingRepo);
			logger.debug("PDF printTesting() function called successfully");

			printService.printSummary(summary.getUserName(), summary.getSiteId());
			logger.debug("PDF printSummary() function called successfully");

			printFinalPDFService.printFinalPDF(summary.getUserName(), summary.getSiteId(), site.getSite());
			logger.debug("PDF printFinalPDF() function called successfully");
		} catch (Exception e) {
			throw new PdfException("PDF Creation failed");
		}
	}

	/**
	 * @param userName,siteId
	 * retrieveSummary method to retrieve the summary object based on userName and SiteId
	 * @return summary object
	*/
	@Override
	public List<Summary> retrieveSummary(String userName, Integer siteId) throws SummaryException {
		if (userName != null && !userName.isEmpty() && siteId != null && siteId != 0) {
			List<Summary> summaryRepo = summaryRepository.findByUserNameAndSiteId(userName, siteId);
			if (summaryRepo != null) {
				for (Summary summary : summaryRepo) {
					summary.setAllComponentObservation(allComponentObservation(siteId));
					logger.debug("AllComponentObservation details added in summary model");
					sortingDateTime(summary.getSummaryComment());
				}
				return summaryRepo;
			} else {
				logger.error("Given UserName & Site doesn't exist Inspection");
				throw new SummaryException("Given UserName & Site doesn't exist Inspection");
			}
		} else {
			logger.error("Invalid Inputs");
			throw new SummaryException("Invalid Inputs");

		}
	}

	/**
	 * @reportId,siteId must required
	 * @param Summary Object
	 * updateSummary method to finding the given SummaryId is available or not in DB,
	 * if available only allowed for updating 
	 * @throws CompanyDetailsException 
	 * 
	*/
	@Transactional
	@Override
	public void updateSummary(Summary summary,Boolean superAdminFlag) throws SummaryException, CompanyDetailsException, InstalReportException, SupplyCharacteristicsException, InspectionException, 
	PeriodicTestingException, Exception, ObservationException, PdfException{
		
		if (summary != null && summary.getSummaryId() != null && summary.getSummaryId() != 0
				&& summary.getSiteId() != null && summary.getSiteId() != 0) {
			Optional<Summary> summaryRepo = summaryRepository.findById(summary.getSummaryId());
			if (summaryRepo.isPresent() && summaryRepo.get().getSiteId().equals(summary.getSiteId())) {
				List<SummaryObservation> summaryObservationData = summaryRepo.get().getSummaryObservation();
				if(summaryObservationData != null) {
					for(SummaryObservation summaryObservationItr : summaryObservationData) {
						List<SummaryInnerObservation> summaryInnerObservationData = summaryObservationItr.getSummaryInnerObservation();
						for(SummaryInnerObservation summaryInnerObservationItr : summaryInnerObservationData) {
							summaryInnerObservationRepo.deleteInnerObservRecordsById(summaryInnerObservationItr.getInnerObservationsId());
						}
						summaryObservationRepo.deleteObservRecordsById(summaryObservationItr.getObservationsId());
					}	
				}
				
				summary.setUpdatedDate(LocalDateTime.now());
				summary.setUpdatedBy(userFullName.findByUserName(summary.getUserName()));
				if(summary.getSummaryComment() == null) {
					summary.setSummaryComment(summaryRepo.get().getSummaryComment());
				}
				summaryRepository.save(summary);
				logger.debug("Summary Details Successfully updated in DB");
				
				if(!superAdminFlag) {
					siteDetails.updateSite(summary.getSiteId(), summary.getUserName(),"Step5 completed");
					logger.debug("Updated successfully site updatedUsername",summary.getUserName());
				}
				else {
					Optional<SupplyCharacteristics> supplyCharacteristics = supplyCharacteristicsRepository
							.findBySiteId(summary.getSiteId());
					Optional<TestingReport> testingRepo = testingReportRepository.findBySiteId(summary.getSiteId());
					Optional<PeriodicInspection> periodicInspection = inspectionRepository.findBySiteId(summary.getSiteId());
					Optional<ReportDetails> reportDetailsRepo = installationReportRepository.findBySiteId(summary.getSiteId());
					siteRepo = siteRepository.findById(summary.getSiteId());
					if(reportDetailsRepo.isPresent() && supplyCharacteristics.isPresent() && periodicInspection.isPresent()
							 && testingRepo.isPresent() && summaryRepo.isPresent()) {
						 
		    			if (siteRepo.isPresent() && siteRepo.get().getSiteId().equals(summary.getSiteId())) {
		    				site = siteRepo.get();
		    				printPDfDatas(summaryRepo.get(),reportDetailsRepo,supplyCharacteristics,periodicInspection,testingRepo);
		    				site.setAllStepsCompleted("AllStepCompleted");
		    				siteRepository.save(site);
		    				logger.debug("AllStepCompleted information saved site table in DB"+summary.getUserName());
		    			} else {
		    				logger.error("Site-Id Information not Available in site_Table");
		    				throw new SummaryException("Site-Id Information not Available in site_Table");
		    			}
					}
					else {
						logger.error("Please fill all the fields before clicking submit button");
						throw new SummaryException("Please fill all the fields before clicking submit button");
					}
					
				}
				
			} else {
				logger.error("Given SiteId and ReportId is Invalid");
				throw new SummaryException("Given SiteId and ReportId is Invalid");
			}

		} else {
			logger.error("Invalid Inputs");
			throw new SummaryException("Invalid inputs");
		}	
	}
	
	@Override
	public void sendComments(String userName, Integer siteId, SummaryComment summaryComment) throws SummaryException {
		Summary summary = verifyCommentsInfo(userName, siteId, summaryComment, Constants.SEND_COMMENT);
		if (summary != null) {
			summaryRepository.save(summary);
			logger.debug("sendComments successfully into DB");
		} else {
			logger.error("Testing-Information doesn't exist for given Site-Id");
			throw new SummaryException("Testing-Information doesn't exist for given Site-Id");
		}
	}

	@Override
	public String replyComments(String inspectorUserName, Integer siteId, SummaryComment summaryComment)
			throws SummaryException {
		Summary summary = verifyCommentsInfo(inspectorUserName, siteId, summaryComment, Constants.REPLY_COMMENT);
		if (summary != null) {
			summaryRepository.save(summary);
			logger.debug("ReplyComments successfully into DB");
			return viewerName;
		} else {
			logger.error("Testing-Information doesn't exist for given Site-Id");
			throw new SummaryException("Testing-Information doesn't exist for given Site-Id");
		}
	}
	
	@Override
	public void approveComments(String userName, Integer siteId, SummaryComment summaryComment)
			throws SummaryException {
		Summary summary = verifyCommentsInfo(userName, siteId, summaryComment, Constants.APPROVE_REJECT_COMMENT);
		if (summary != null) {
			summaryRepository.save(summary);
			logger.error("ApproveComments successfully into DB");
		} else {
			logger.error("Testing-Information doesn't exist for given Site-Id");
			throw new SummaryException("Testing-Information doesn't exist for given Site-Id");
		}
	}

	private Summary verifyCommentsInfo(String userName, Integer siteId,
			SummaryComment summaryComment, String process) throws SummaryException {

		Boolean flagInspectionComment = true;
		if (userName != null && siteId != null && summaryComment != null) {
			Optional<Site> siteRepo = siteRepository.findById(siteId);
			if (siteRepo.isPresent() && siteRepo.get().getSiteId().equals(siteId)
					&& siteRepo.get().getAssignedTo() != null && siteRepo.get().getUserName() != null
					&& siteRepo.get().getSite() != null) {
				Optional<Summary> summaryRepo = summaryRepository.findBySiteId(siteId);

				if (summaryRepo.isPresent() && summaryRepo.get() != null
						&& checkInspectorViewer(userName, process, siteRepo, summaryRepo)) {

					Summary summary = summaryRepo.get();
					summary.setUpdatedDate(LocalDateTime.now());
					summary.setUpdatedBy(userName);
					List<SummaryComment> summaryCommentRepo = summary.getSummaryComment();

					for (SummaryComment summaryCommentItr : summaryCommentRepo) {
						if (summaryCommentRepo != null && summaryCommentItr.getCommentsId() != null
								&& summaryCommentItr.getCommentsId().equals(summaryComment.getCommentsId())) {
							flagInspectionComment = false;

							summaryCommentItr.setSummary(summary);

							if (process.equalsIgnoreCase(Constants.SEND_COMMENT)) {
								summaryCommentItr.setSiteName(siteRepo.get().getSite());
								summaryCommentItr.setInspectorEmail(siteRepo.get().getUserName());
								summaryCommentItr.setViewerUserEmail(siteRepo.get().getAssignedTo());
								summaryCommentItr.setViewerDate(LocalDateTime.now());
								summaryCommentItr.setViewerUserName(userFullName.findByUserName(userName));
								summaryCommentItr.setViewerComment(summaryComment.getViewerComment());
								summaryCommentItr.setViewerFlag(Constants.INCREASED_FLAG_VALUE);
								summaryCommentRepo.add(summaryCommentItr);
								summary.setSummaryComment(summaryCommentRepo);
								return summary;
							}
							if (process.equalsIgnoreCase(Constants.REPLY_COMMENT)) {
								summaryCommentItr.setInspectorDate(LocalDateTime.now());
								summaryCommentItr.setInspectorUserName(userFullName.findByUserName(userName));
								summaryCommentItr.setInspectorComment(summaryComment.getInspectorComment());
								summaryCommentItr.setInspectorFlag(Constants.INCREASED_FLAG_VALUE);
								summaryCommentRepo.add(summaryCommentItr);
								summary.setSummaryComment(summaryCommentRepo);
								return summary;
							}
							if (process.equalsIgnoreCase(Constants.APPROVE_REJECT_COMMENT)) {
								summaryCommentItr.setViewerUserName(userFullName.findByUserName(userName));
								summaryCommentItr.setApproveOrReject(summaryComment.getApproveOrReject());
								summaryCommentRepo.add(summaryCommentItr);
								summary.setSummaryComment(summaryCommentRepo);
								return summary;
							}
						}
					}
					if (flagInspectionComment) {
						if (process.equalsIgnoreCase(Constants.SEND_COMMENT)) {
							summaryComment.setNoOfComment(checkNoOfComments(summary.getSummaryComment()));
							summaryComment.setSummary(summary);
							summaryComment.setSiteName(siteRepo.get().getSite());
							summaryComment.setInspectorEmail(siteRepo.get().getUserName());
							summaryComment.setViewerUserEmail(siteRepo.get().getAssignedTo());
							summaryComment.setViewerDate(LocalDateTime.now());
							summaryComment.setViewerUserName(userFullName.findByUserName(userName));
							summaryComment.setViewerFlag(Constants.INCREASED_FLAG_VALUE);
							summaryComment.setInspectorFlag(Constants.INTIAL_FLAG_VALUE);
							summaryCommentRepo.add(summaryComment);
							summary.setSummaryComment(summaryCommentRepo);
							return summary;
						} else {
							throw new SummaryException("Sending viewer comments faild");
						}
					}
				} else {
					throw new SummaryException("Given username not have access for comments");
				}

			} else {
				throw new SummaryException("Siteinformation doesn't exist, try with different Site-Id");
			}

		} else {
			throw new SummaryException("Invalid inputs");
		}
		return null;
	}
	
	private void sortingDateTime(List<SummaryComment> listOfComments) {
		if(listOfComments.size()>1) {
			Collections.sort(listOfComments, (o1, o2) -> o1.getViewerDate().compareTo(o2.getViewerDate()));
		}
	}
	
	private Integer checkNoOfComments(List<SummaryComment> listOfComments) {
		Integer maxNum = 0;
		String approveRejectedFlag = "";
		for (SummaryComment SummaryCommentItr : listOfComments) {
			if (SummaryCommentItr != null && SummaryCommentItr.getNoOfComment() != null
					&& maxNum <= SummaryCommentItr.getNoOfComment()) {
				maxNum = SummaryCommentItr.getNoOfComment();
				approveRejectedFlag = SummaryCommentItr.getApproveOrReject();
			}
		}
		if (approveRejectedFlag != null && approveRejectedFlag.equalsIgnoreCase(Constants.APPROVE_REJECT_COMMENT)) {
			return maxNum + 1;
		} else {
			return maxNum + 1;
		}
	}
	
	private Boolean checkInspectorViewer(String userName, String process, Optional<Site> siteRepo,
			Optional<Summary> summaryRepo) throws SummaryException {
		Boolean flag = false;
		if (process.equalsIgnoreCase(Constants.REPLY_COMMENT)) {
			if (siteRepo.get().getUserName().equalsIgnoreCase(userName)
					&& summaryRepo.get().getUserName() != null
					&& siteRepo.get().getUserName().equalsIgnoreCase(summaryRepo.get().getUserName())) {
				Set<SitePersons> sitePersons = siteRepo.get().getSitePersons();
				for (SitePersons sitePersonsItr : sitePersons) {
					if (sitePersonsItr != null && sitePersonsItr.getPersonInchargeEmail() != null) {
						viewerName = sitePersonsItr.getPersonInchargeEmail();
						return flag = true;
					}
				}
			} else {
				logger.error("Given userName not allowing for " + process + " comment");
				throw new SummaryException("Given userName not allowing for " + process + " comment");
			}

		} else if (process.equalsIgnoreCase(Constants.SEND_COMMENT) || process.equalsIgnoreCase(Constants.APPROVE_REJECT_COMMENT)) {

			Set<SitePersons> sitePersons = siteRepo.get().getSitePersons();
			for (SitePersons sitePersonsItr : sitePersons) {
				if (sitePersonsItr != null && sitePersonsItr.getPersonInchargeEmail() != null
						&& sitePersonsItr.getPersonInchargeEmail().equalsIgnoreCase(userName)) {
					return flag = true;
				} else {
					logger.error("Given userName not allowing for " + process + " comment");
					throw new SummaryException("Given userName not allowing for " + process + " comment");
				}
			}
		}
		return flag;
	}
	
	private AllComponentObservation allComponentObservation(Integer siteId) {
		AllComponentObservation allComponentObservation = new AllComponentObservation();
		Optional<SupplyCharacteristics> supplyCharacteristics = supplyCharacteristicsRepository.findBySiteId(siteId);
		Optional<PeriodicInspection> periodicInspection = inspectionRepository.findBySiteId(siteId);
		Optional<TestingReport> testingReport = testingReportRepository.findBySiteId(siteId);
		if (supplyCharacteristics.isPresent() && supplyCharacteristics.get().getSupplyOuterObservation() != null) {
			allComponentObservation.setSupplyOuterObservation(findNonRemovedObject
					.findNonRemovedSupplyOuterObservation(supplyCharacteristics.get().getSupplyOuterObservation()));
		}
		if (periodicInspection.isPresent() && periodicInspection.get().getIpaoInspection() != null) {

			List<IpaoInspection> nonRemovedInspectionLocation = findNonRemovedObject
					.findNonRemovedInspectionLocation(periodicInspection.get());
			ArrayList<InspectionOuterObservation> outerObservationyList = new ArrayList<InspectionOuterObservation>();
			for (IpaoInspection ipaoInspection : nonRemovedInspectionLocation) {
				if (ipaoInspection.getInspectionOuterObervation() != null) {
					for (InspectionOuterObservation inspectionOuterObservation : ipaoInspection.getInspectionOuterObervation()) {
						outerObservationyList.add(inspectionOuterObservation);
					}
				}
			}

			allComponentObservation.setInspectionOuterObservation(outerObservationyList);
		}
		if (testingReport.isPresent()) {
			allComponentObservation.setTestingInnerObservation(
					findNonRemovedObject.findNonRemoveTestingInnerObservationByReport(testingReport));
		}
		return allComponentObservation;
	}
	@Override
	public Summary retrieveSummary(Integer siteId) throws SummaryException {
		if (siteId != null && siteId != 0) {
			Optional<Summary> summaryRepoData = summaryRepository.findBySiteId(siteId);
			Summary summaryRepo = summaryRepoData.get();

			if (summaryRepo != null) {
				summaryRepo.setAllComponentObservation(allComponentObservation(siteId));
					logger.debug("AllComponentObservation details added in summary model");
					sortingDateTime(summaryRepo.getSummaryComment());
				return summaryRepo;
			} else {
				logger.error("Given UserName & Site doesn't exist Inspection");
				throw new SummaryException("Given UserName & Site doesn't exist Inspection");
			}
		} else {
			logger.error("Invalid Inputs");
			throw new SummaryException("Invalid Inputs");

		}
	}

}
