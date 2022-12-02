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
import com.capeelectric.exception.DecimalConversionException;
import com.capeelectric.exception.SupplyCharacteristicsException;
import com.capeelectric.model.Site;
import com.capeelectric.model.SitePersons;
import com.capeelectric.model.SupplyCharacteristicComment;
import com.capeelectric.model.SupplyCharacteristics;
import com.capeelectric.model.SupplyParameters;
import com.capeelectric.repository.SiteRepository;
import com.capeelectric.repository.SupplyCharacteristicsRepository;
import com.capeelectric.service.SupplyCharacteristicsService;
import com.capeelectric.util.Constants;
import com.capeelectric.util.DecimalConversion;
import com.capeelectric.util.FindNonRemovedObject;
import com.capeelectric.util.SiteDetails;
import com.capeelectric.util.UserFullName;

/**
 **
 * This SupplyCharacteristicsServiceImpl service class doing save and retrieve operation related to SupplyCharacteristics
 * @author capeelectricsoftware
 *
 */
@Service
public class SupplyCharacteristicsServiceImpl implements SupplyCharacteristicsService {
	
	private static final Logger logger = LoggerFactory.getLogger(SupplyCharacteristicsServiceImpl.class);

	@Autowired
	private SupplyCharacteristicsRepository supplyCharacteristicsRepository;
	
	@Autowired
	private SiteRepository siteRepository;

	@Autowired
	private UserFullName userFullName;
	
	private SupplyCharacteristicComment supplyCharacteristicComment;

	private List<SupplyCharacteristicComment> listOfComments;
	
	private String viewerName;
	
	@Autowired
	private SiteDetails siteDetails;
	
	@Autowired
	private FindNonRemovedObject findNonRemovedObject;
	
	/**
	 * @param SupplyCharacteristics
	 * addCharacteristics method to first formating the main and alternative_supply (NominalFrequency,NominalVoltage,LoopImpedance and NominalCurrent)
	 * then save SupplyCharacteristics model and its child model also will be saved
	 * @throws DecimalConversionException 
	 * @throws CompanyDetailsException 
	*/	
	@Transactional
	@Override
	public void addCharacteristics(SupplyCharacteristics supplyCharacteristics)
			throws SupplyCharacteristicsException, DecimalConversionException, CompanyDetailsException {
		listOfComments = new ArrayList<SupplyCharacteristicComment>();
		if (supplyCharacteristics != null && supplyCharacteristics.getUserName() != null
				&& !supplyCharacteristics.getUserName().isEmpty() && supplyCharacteristics.getSiteId() != null
				&& supplyCharacteristics.getSiteId() != 0 && supplyCharacteristics.getAlternativeSupply() != null) {
			if (supplyCharacteristics.getAlternativeSupply().equalsIgnoreCase("No")) {
				logger.debug(
						"supplyCharacteristics AlternativeSupply --> " + supplyCharacteristics.getAlternativeSupply());
				saveSupplyCharacteristics(supplyCharacteristics);
			} else if (supplyCharacteristics.getAlternativeSupply().equalsIgnoreCase("Yes")
					&& supplyCharacteristics.getSupplyParameters() != null
					&& supplyCharacteristics.getCircuitBreaker() != null
					&& supplyCharacteristics.getSupplyParameters().size() > 0
					&& supplyCharacteristics.getCircuitBreaker().size() > 0) {
				logger.debug(
						"supplyCharacteristics AlternativeSupply --> " + supplyCharacteristics.getAlternativeSupply());
				saveSupplyCharacteristics(supplyCharacteristics);
			} else {
				logger.debug("Please fill all the fields before clicking next button");
				throw new SupplyCharacteristicsException("Please fill all the fields before clicking next button");
			}
		} else {
			logger.debug("Invalid Inputs");
			throw new SupplyCharacteristicsException("Invalid Inputs");
		}
	}

	private void saveSupplyCharacteristics(SupplyCharacteristics supplyCharacteristics)
			throws DecimalConversionException, CompanyDetailsException, SupplyCharacteristicsException {
		Optional<SupplyCharacteristics> siteId = supplyCharacteristicsRepository
				.findBySiteId(supplyCharacteristics.getSiteId());
		if (!siteId.isPresent() || !siteId.get().getSiteId().equals(supplyCharacteristics.getSiteId())) {
			decimalConversion(supplyCharacteristics);
			supplyCharacteristicComment = new SupplyCharacteristicComment();
			supplyCharacteristicComment.setInspectorFlag(Constants.INTIAL_FLAG_VALUE);
			supplyCharacteristicComment.setViewerFlag(Constants.INTIAL_FLAG_VALUE);
			supplyCharacteristicComment.setNoOfComment(1);
			supplyCharacteristicComment.setViewerDate(LocalDateTime.now());
			supplyCharacteristicComment.setSupplyCharacteristics(supplyCharacteristics);
			listOfComments.add(supplyCharacteristicComment);
			supplyCharacteristics.setSupplyCharacteristicComment(listOfComments);
			supplyCharacteristics.setCreatedDate(LocalDateTime.now());
			supplyCharacteristics.setUpdatedDate(LocalDateTime.now());
			supplyCharacteristics.setCreatedBy(userFullName.findByUserName(supplyCharacteristics.getUserName()));
			supplyCharacteristics.setUpdatedBy(userFullName.findByUserName(supplyCharacteristics.getUserName()));

			supplyCharacteristicsRepository.save(supplyCharacteristics);
			logger.debug("supplyCharacteristics Details Successfully Saved in DB");
			siteDetails.updateSite(supplyCharacteristics.getSiteId(), supplyCharacteristics.getUserName(),"Step2 completed");
			logger.debug("Updated successfully site updatedUsername", supplyCharacteristics.getUserName());
		} else {
			logger.error("Site-Id Already Available");
			throw new SupplyCharacteristicsException("Site-Id Already Available");
		}
	}

	/**
	 * @param userName,siteId
	 * retrieveCharacteristics method to retrieve list of supplyCharacteristic objects based on userName and siteId
	 * @return List<SupplyCharacteristics>
	 * 	
	*/
	@Override 
	public SupplyCharacteristics retrieveCharacteristics(String userName, Integer siteId)
			throws SupplyCharacteristicsException {

		if (userName != null && !userName.isEmpty() && siteId != null) {
			SupplyCharacteristics supplyCharacteristicsRepo = supplyCharacteristicsRepository
					.findByUserNameAndSiteId(userName, siteId);
			if (supplyCharacteristicsRepo != null) {

				supplyCharacteristicsRepo.setInstalLocationReport(
						findNonRemovedObject.findNonRemovedInstallLocation(supplyCharacteristicsRepo));
				supplyCharacteristicsRepo.setBoundingLocationReport(
						findNonRemovedObject.findNonRemovedBondingLocation(supplyCharacteristicsRepo));
				supplyCharacteristicsRepo.setEarthingLocationReport(
						findNonRemovedObject.findNonRemovedEarthingLocation(supplyCharacteristicsRepo));
				supplyCharacteristicsRepo.setCircuitBreaker(findNonRemovedObject
						.findNonRemovedCircuitBreaker(supplyCharacteristicsRepo.getCircuitBreaker()));
				supplyCharacteristicsRepo.setSupplyParameters(findNonRemovedObject
						.findNonRemovedSupplyParameters(supplyCharacteristicsRepo.getSupplyParameters()));
				supplyCharacteristicsRepo.setSupplyOuterObservation(findNonRemovedObject
						.findNonRemovedSupplyOuterObservation(supplyCharacteristicsRepo.getSupplyOuterObservation()));
				sortingDateTime(supplyCharacteristicsRepo.getSupplyCharacteristicComment());

				return supplyCharacteristicsRepo;
			} else {
				logger.error("Given UserName & Site doesn't exist Inspection");
				throw new SupplyCharacteristicsException("Given UserName & Site doesn't exist Inspection");
			}
		} else {
			logger.error("Invalid Inputs");
			throw new SupplyCharacteristicsException("Invalid Inputs");
		}
	}
	
	@Override 
	public SupplyCharacteristics retrieveCharacteristics(Integer siteId)
			throws SupplyCharacteristicsException {

		if (siteId != null) {
			Optional<SupplyCharacteristics> supplyCharacteristicsRepoData = supplyCharacteristicsRepository
					.findBySiteId(siteId);
			SupplyCharacteristics supplyCharacteristicsRepo = supplyCharacteristicsRepoData.get();
			if (supplyCharacteristicsRepo != null) {

				supplyCharacteristicsRepo.setInstalLocationReport(
						findNonRemovedObject.findNonRemovedInstallLocation(supplyCharacteristicsRepo));
				supplyCharacteristicsRepo.setBoundingLocationReport(
						findNonRemovedObject.findNonRemovedBondingLocation(supplyCharacteristicsRepo));
				supplyCharacteristicsRepo.setEarthingLocationReport(
						findNonRemovedObject.findNonRemovedEarthingLocation(supplyCharacteristicsRepo));
				supplyCharacteristicsRepo.setCircuitBreaker(findNonRemovedObject
						.findNonRemovedCircuitBreaker(supplyCharacteristicsRepo.getCircuitBreaker()));
				supplyCharacteristicsRepo.setSupplyParameters(findNonRemovedObject
						.findNonRemovedSupplyParameters(supplyCharacteristicsRepo.getSupplyParameters()));
				supplyCharacteristicsRepo.setSupplyOuterObservation(findNonRemovedObject
						.findNonRemovedSupplyOuterObservation(supplyCharacteristicsRepo.getSupplyOuterObservation()));
				sortingDateTime(supplyCharacteristicsRepo.getSupplyCharacteristicComment());

				return supplyCharacteristicsRepo;
			} else {
				logger.error("Given UserName & Site doesn't exist Inspection");
				throw new SupplyCharacteristicsException("Given UserName & Site doesn't exist Inspection");
			}
		} else {
			logger.error("Invalid Inputs");
			throw new SupplyCharacteristicsException("Invalid Inputs");
		}
	}

	
	
	/**
	 * @reportId,siteId must required
	 * @param SupplyCharacteristics Object
	 * updateCharacteristics method to finding the given SupplyCharacteristicsId is available or not in DB,
	 * if available only allowed for updating 
	 * @throws DecimalConversionException 
	 * @throws CompanyDetailsException 
	 * 
	*/
	@Transactional
	@Override
	public void updateCharacteristics(SupplyCharacteristics supplyCharacteristics)
			throws SupplyCharacteristicsException, DecimalConversionException, CompanyDetailsException {
		if (supplyCharacteristics != null && supplyCharacteristics.getSupplyCharacteristicsId() != null
				&& supplyCharacteristics.getSupplyCharacteristicsId() != 0 && supplyCharacteristics.getSiteId() != null
				&& supplyCharacteristics.getSiteId() != 0) {
			Optional<SupplyCharacteristics> supplyCharacteristicsRepo = supplyCharacteristicsRepository
					.findById(supplyCharacteristics.getSupplyCharacteristicsId());
			if (supplyCharacteristicsRepo.isPresent()
					&& supplyCharacteristicsRepo.get().getSiteId().equals(supplyCharacteristics.getSiteId())) {
				supplyCharacteristics.setUpdatedDate(LocalDateTime.now());
				supplyCharacteristics.setUpdatedBy(userFullName.findByUserName(supplyCharacteristics.getUserName()));
				decimalConversion(supplyCharacteristics);
				supplyCharacteristicsRepository.save(supplyCharacteristics);
				logger.debug("supplyCharacteristics Details Successfully Updated in DB");
				siteDetails.updateSite(supplyCharacteristics.getSiteId(), supplyCharacteristics.getUserName(),"Step2 completed");
				logger.debug("Updated successfully site updatedUsername", supplyCharacteristics.getUserName());
			} else {
				logger.error("Given SiteId and ReportId is Invalid");
				throw new SupplyCharacteristicsException("Given SiteId and ReportId is Invalid");
			}

		} else {
			logger.error("Invalid Inputs");
			throw new SupplyCharacteristicsException("Invalid inputs");
		}

	}
	
	@Override
	public void sendComments(String userName, Integer siteId,
			SupplyCharacteristicComment supplyCharacteristicComment) throws SupplyCharacteristicsException {

		SupplyCharacteristics supplyCharacteristics = verifyCommentsInfo(userName, siteId, supplyCharacteristicComment,
				Constants.SEND_COMMENT);
		if (supplyCharacteristics != null) {
			supplyCharacteristicsRepository.save(supplyCharacteristics);
		} else {
			logger.error("SupplyCharacteristics-Information doesn't exist for given Site-Id");
			throw new SupplyCharacteristicsException(
					"SupplyCharacteristics-Information doesn't exist for given Site-Id");
		}
	}

	@Override
	public String replyComments(String inspectorUserName, Integer siteId,
			SupplyCharacteristicComment supplyCharacteristicComment) throws SupplyCharacteristicsException {

		SupplyCharacteristics supplyCharacteristics = verifyCommentsInfo(inspectorUserName, siteId,
				supplyCharacteristicComment, Constants.REPLY_COMMENT);
		if (supplyCharacteristics != null) {
			supplyCharacteristicsRepository.save(supplyCharacteristics);
			logger.debug("ReplyComments successfully into DB");
			return viewerName;
		} else {
			logger.error("SupplyCharacteristics-Information doesn't exist for given Site-Id");
			throw new SupplyCharacteristicsException(
					"SupplyCharacteristics-Information doesn't exist for given Site-Id");
		}
	}
	
	@Override
	public void approveComments(String userName, Integer siteId,
			SupplyCharacteristicComment supplyCharacteristicComment) throws SupplyCharacteristicsException {

		SupplyCharacteristics supplyCharacteristics = verifyCommentsInfo(userName, siteId, supplyCharacteristicComment,
				Constants.APPROVE_REJECT_COMMENT);
		if (supplyCharacteristics != null) {
			supplyCharacteristicsRepository.save(supplyCharacteristics);
			logger.error("ApproveComments successfully into DB");
		} else {
			logger.error("SupplyCharacteristics-Information doesn't exist for given Site-Id");
			throw new SupplyCharacteristicsException(
					"SupplyCharacteristics-Information doesn't exist for given Site-Id");
		}

	}
	
	private SupplyCharacteristics verifyCommentsInfo(String userName, Integer siteId,
			SupplyCharacteristicComment supplyCharacteristicComment, String process)
			throws SupplyCharacteristicsException {

		Boolean flagInspectionComment = true;
		if (userName != null && siteId != null && supplyCharacteristicComment != null) {
			Optional<Site> siteRepo = siteRepository.findById(siteId);
			if (siteRepo.isPresent() && siteRepo.get().getSiteId().equals(siteId)
					&& siteRepo.get().getAssignedTo() != null && siteRepo.get().getUserName() != null
					&& siteRepo.get().getSite() != null) {
				Optional<SupplyCharacteristics> supplyCharacteristicsRepo = supplyCharacteristicsRepository
						.findBySiteId(siteId);

				if (supplyCharacteristicsRepo.isPresent() && supplyCharacteristicsRepo.get() != null
						&& checkInspectorViewer(userName, process, siteRepo, supplyCharacteristicsRepo)) {
					SupplyCharacteristics supplyCharacteristics = supplyCharacteristicsRepo.get();
					supplyCharacteristics.setUpdatedDate(LocalDateTime.now());
					supplyCharacteristics.setUpdatedBy(userName);
					List<SupplyCharacteristicComment> supplyCharacteristicCommentRepo = supplyCharacteristics
							.getSupplyCharacteristicComment();

					for (SupplyCharacteristicComment supplyCharacteristicCommentItr : supplyCharacteristicCommentRepo) {
						if (supplyCharacteristicCommentItr != null
								&& supplyCharacteristicCommentItr.getCommentsId() != null
								&& supplyCharacteristicCommentItr.getCommentsId()
										.equals(supplyCharacteristicComment.getCommentsId())) {
							flagInspectionComment = false;

							supplyCharacteristicCommentItr.setSupplyCharacteristics(supplyCharacteristics);

							if (process.equalsIgnoreCase(Constants.SEND_COMMENT)) {
								supplyCharacteristicCommentItr.setSiteName(siteRepo.get().getSite());
								supplyCharacteristicCommentItr.setInspectorEmail(siteRepo.get().getUserName());
								supplyCharacteristicCommentItr.setViewerUserEmail(siteRepo.get().getAssignedTo());
								supplyCharacteristicCommentItr.setViewerDate(LocalDateTime.now());
								supplyCharacteristicCommentItr.setViewerUserName(userFullName.findByUserName(userName));
								supplyCharacteristicCommentItr
										.setViewerComment(supplyCharacteristicComment.getViewerComment());
								supplyCharacteristicCommentItr.setViewerFlag(Constants.INCREASED_FLAG_VALUE);
								supplyCharacteristicCommentRepo.add(supplyCharacteristicCommentItr);
								supplyCharacteristics.setSupplyCharacteristicComment(supplyCharacteristicCommentRepo);
								return supplyCharacteristics;
							}
							if (process.equalsIgnoreCase(Constants.REPLY_COMMENT)) {
								supplyCharacteristicCommentItr.setInspectorDate(LocalDateTime.now());
								supplyCharacteristicCommentItr.setInspectorUserName(userFullName.findByUserName(userName));
								supplyCharacteristicCommentItr
										.setInspectorComment(supplyCharacteristicComment.getInspectorComment());
								supplyCharacteristicCommentItr.setInspectorFlag(Constants.INCREASED_FLAG_VALUE);
								supplyCharacteristicCommentRepo.add(supplyCharacteristicCommentItr);
								supplyCharacteristics.setSupplyCharacteristicComment(supplyCharacteristicCommentRepo);
								return supplyCharacteristics;
							}
							if (process.equalsIgnoreCase(Constants.APPROVE_REJECT_COMMENT)) {
								supplyCharacteristicCommentItr.setViewerUserName(userFullName.findByUserName(userName));
								supplyCharacteristicCommentItr
										.setApproveOrReject(supplyCharacteristicComment.getApproveOrReject());
								supplyCharacteristicCommentRepo.add(supplyCharacteristicCommentItr);
								supplyCharacteristics.setSupplyCharacteristicComment(supplyCharacteristicCommentRepo);
								return supplyCharacteristics;
							}
						}
					}
					if (flagInspectionComment) {

						if (process.equalsIgnoreCase(Constants.SEND_COMMENT)) {
							supplyCharacteristicComment.setNoOfComment(
									checkNoOfComments(supplyCharacteristics.getSupplyCharacteristicComment()));
							supplyCharacteristicComment.setSupplyCharacteristics(supplyCharacteristics);
							supplyCharacteristicComment.setSiteName(siteRepo.get().getSite());
							supplyCharacteristicComment.setInspectorEmail(siteRepo.get().getUserName());
							supplyCharacteristicComment.setViewerUserEmail(siteRepo.get().getAssignedTo());
							supplyCharacteristicComment.setViewerDate(LocalDateTime.now());
							supplyCharacteristicComment.setViewerUserName(userFullName.findByUserName(userName));
							supplyCharacteristicComment.setViewerFlag(Constants.INCREASED_FLAG_VALUE);
							supplyCharacteristicComment.setInspectorFlag(Constants.INTIAL_FLAG_VALUE);
							supplyCharacteristicCommentRepo.add(supplyCharacteristicComment);
							supplyCharacteristics.setSupplyCharacteristicComment(supplyCharacteristicCommentRepo);
							return supplyCharacteristics;
						}
					}
				} else {
					logger.error("Given username not have access for comments");
					throw new SupplyCharacteristicsException("Given username not have access for comments");
				}

			} else {
				logger.error("Siteinformation doesn't exist, try with different Site-Id");
				throw new SupplyCharacteristicsException("Siteinformation doesn't exist, try with different Site-Id");
			}

		} else {
			logger.error("Invalid inputs");
			throw new SupplyCharacteristicsException("Invalid inputs");
		}
		return null;
	}
	
	private void sortingDateTime(List<SupplyCharacteristicComment> listOfComments) {
		if(listOfComments.size()>1) {
			Collections.sort(listOfComments, (o1, o2) -> o1.getViewerDate().compareTo(o2.getViewerDate()));
		}
		
	}
	
	private Integer checkNoOfComments(List<SupplyCharacteristicComment> listOfComments) {
		Integer maxNum = 0;
		String approveRejectedFlag = "";
		for (SupplyCharacteristicComment supplyCharacteristicCommentItr : listOfComments) {
			if (supplyCharacteristicCommentItr != null && supplyCharacteristicCommentItr.getNoOfComment() != null
					&& maxNum <= supplyCharacteristicCommentItr.getNoOfComment()) {
				maxNum = supplyCharacteristicCommentItr.getNoOfComment();
				approveRejectedFlag = supplyCharacteristicCommentItr.getApproveOrReject();
			}
		}
		if (approveRejectedFlag != null && approveRejectedFlag.equalsIgnoreCase(Constants.APPROVE_REJECT_COMMENT)) {
			return maxNum + 1;
		} else {
			return maxNum + 1;
		}
	}

	private Boolean checkInspectorViewer(String userName, String process, Optional<Site> siteRepo,
			Optional<SupplyCharacteristics> supplyCharacteristicsRepo) throws SupplyCharacteristicsException {
		Boolean flag = false;
		if (process.equalsIgnoreCase(Constants.REPLY_COMMENT)) {
			if (siteRepo.get().getUserName().equalsIgnoreCase(userName)
					&& supplyCharacteristicsRepo.get().getUserName() != null
					&& siteRepo.get().getUserName().equalsIgnoreCase(supplyCharacteristicsRepo.get().getUserName())) {
				Set<SitePersons> sitePersons = siteRepo.get().getSitePersons();
				for (SitePersons sitePersonsItr : sitePersons) {
					if (sitePersonsItr != null && sitePersonsItr.getPersonInchargeEmail() != null) {
						viewerName = sitePersonsItr.getPersonInchargeEmail();
						return flag = true;
					}
				}
			} else {
				logger.error("Given userName not allowing for " + process + " comment");
				throw new SupplyCharacteristicsException("Given userName not allowing for " + process + " comment");
			}

		} else if (process.equalsIgnoreCase(Constants.SEND_COMMENT) || process.equalsIgnoreCase(Constants.APPROVE_REJECT_COMMENT)) {

			Set<SitePersons> sitePersons = siteRepo.get().getSitePersons();
			for (SitePersons sitePersonsItr : sitePersons) {
				if (sitePersonsItr != null && sitePersonsItr.getPersonInchargeEmail() != null
						&& sitePersonsItr.getPersonInchargeEmail().equalsIgnoreCase(userName)) {
					return flag = true;
				} else {
					logger.error("Given userName not allowing for " + process + " comment");
					throw new SupplyCharacteristicsException("Given userName not allowing for " + process + " comment");
				}
			}
		}
		return flag;
	}
	
	/**
	 * THis logic does the conversion of values from request to appropriate decimal converted values.
	 * @param supplyCharacteristics
	 * @throws DecimalConversionException
	 */
	private void decimalConversion(SupplyCharacteristics supplyCharacteristics) throws DecimalConversionException {
		if (supplyCharacteristics.getLiveConductorAC() != null && supplyCharacteristics.getMainNominalCurrent() != null
		// && supplyCharacteristics.getMainNominalFrequency() != null
				&& supplyCharacteristics.getMainNominalVoltage() != null
				&& supplyCharacteristics.getMainLoopImpedance() != null) {
			logger.info("decimal formating corrections started for Main supply");

			if (!supplyCharacteristics.getMainNominalCurrent().isEmpty()) {
				supplyCharacteristics.setMainNominalCurrent(DecimalConversion.convertToDecimal(
						supplyCharacteristics.getMainNominalCurrent(), Constants.supply_MainNominal_Current));
			}

			// supplyCharacteristics.setMainNominalFrequency(DecimalConversion.convertToDecimal(
//					supplyCharacteristics.getMainNominalFrequency(), Constants.supply_MainNominal_Frequency));
			if (!supplyCharacteristics.getMainNominalVoltage().isEmpty()) {
				supplyCharacteristics.setMainNominalVoltage(DecimalConversion.convertToDecimal(
						supplyCharacteristics.getMainNominalVoltage(), Constants.supply_MainNominal_Voltage));
			}
			if (!supplyCharacteristics.getMainLoopImpedance().isEmpty()) {
				supplyCharacteristics.setMainLoopImpedance(DecimalConversion.convertToDecimal(
						supplyCharacteristics.getMainLoopImpedance(), Constants.supply_MainLoop_Impedance));
			}
			logger.info("decimal formating corrections ended for Main supply");
		}
		if (supplyCharacteristics.getSupplyParameters() != null) {
			List<SupplyParameters> supplyParameters = supplyCharacteristics.getSupplyParameters();
			for (SupplyParameters supplyParametersItr : supplyParameters) {
				if (supplyParametersItr.getaLLiveConductorAC() != null
						&& !supplyParametersItr.getaLLiveConductorAC().isEmpty()
						// && supplyParametersItr.getNominalFrequency() != null
						&& supplyParametersItr.getNominalVoltage() != null
						&& supplyParametersItr.getFaultCurrent() != null
						&& supplyParametersItr.getLoopImpedance() != null) {
					logger.info("decimal formating corrections started for alternative supply");
//					supplyParametersItr.setNominalFrequency(DecimalConversion.convertToDecimal(
//							supplyParametersItr.getNominalFrequency(), Constants.supply_Nominal_Frequency));
					if (!supplyParametersItr.getNominalVoltage().isEmpty()) {
						supplyParametersItr.setNominalVoltage(DecimalConversion.convertToDecimal(
								supplyParametersItr.getNominalVoltage(), Constants.supply_Nominal_Voltage));
					}
					if (!supplyParametersItr.getFaultCurrent().isEmpty()) {
						supplyParametersItr.setFaultCurrent(DecimalConversion.convertToDecimal(
								supplyParametersItr.getFaultCurrent(), Constants.supply_Fault_Current));
					}
					if (!supplyParametersItr.getLoopImpedance().isEmpty()) {
						supplyParametersItr.setLoopImpedance(DecimalConversion.convertToDecimal(
								supplyParametersItr.getLoopImpedance(), Constants.supply_LoopImpedance));
					}
					logger.info("decimal formating corrections ended for alternative supply");
				}
			}
		}
	}

}
