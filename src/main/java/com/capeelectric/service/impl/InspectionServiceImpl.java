package com.capeelectric.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capeelectric.exception.CompanyDetailsException;
import com.capeelectric.exception.InspectionException;
import com.capeelectric.model.ConsumerUnit;
import com.capeelectric.model.IpaoInspection;
import com.capeelectric.model.PeriodicInspection;
import com.capeelectric.model.PeriodicInspectionComment;
import com.capeelectric.model.Site;
import com.capeelectric.model.SitePersons;
import com.capeelectric.model.TestDistRecords;
import com.capeelectric.model.Testing;
import com.capeelectric.repository.InspectionConsumerUnitRepository;
import com.capeelectric.repository.InspectionRepository;
import com.capeelectric.repository.SiteRepository;
import com.capeelectric.repository.TestDistRecordsRepository;
import com.capeelectric.repository.TestInfoRepository;
import com.capeelectric.service.InspectionService;
import com.capeelectric.util.Constants;
import com.capeelectric.util.FindNonRemovedObject;
import com.capeelectric.util.SiteDetails;
import com.capeelectric.util.UserFullName;
/**
 * This InspectionServiceImpl class to add and retrieve the IpaoInspection object
 * @author capeelectricsoftware
 *
 */
@Service
public class InspectionServiceImpl implements InspectionService {
	
	private static final Logger logger = LoggerFactory.getLogger(InspectionServiceImpl.class);

	@Autowired
	private InspectionRepository inspectionRepository;
	
	@Autowired
	private UserFullName userFullName;
	
	@Autowired
	private SiteRepository siteRepository;
	
	private PeriodicInspectionComment periodicInspectionComment;
	
	private List<PeriodicInspectionComment> listOfComments;
	
	private String viewerName;
	
	@Autowired
	private SiteDetails siteDetails;
	
	@Autowired
	private TestInfoRepository testInfoRepository;
	
	@Autowired
	private FindNonRemovedObject findNonRemovedObject;
	
	@Autowired
	private InspectionConsumerUnitRepository inspectionConsumerUnitRepository;
	
	@Autowired
	private TestDistRecordsRepository testDistRecordsRepository;

	/**
	 * @param IpaoInspection object 
	 * addInspectionDetails method to save IpaoInspection object into table
	 * @throws CompanyDetailsException 
	 * 
	*/
	@Transactional
	@Override
	public void addInspectionDetails(PeriodicInspection periodicInspection) throws InspectionException, CompanyDetailsException {
		listOfComments = new ArrayList<PeriodicInspectionComment>();
        int i=0;
		if (periodicInspection.getUserName() != null && periodicInspection.getSiteId() != null
				&& periodicInspection.getIpaoInspection() != null) {
			List<IpaoInspection> ipaoInspection = periodicInspection.getIpaoInspection();
			Optional<PeriodicInspection> siteId = inspectionRepository
					.findBySiteId(periodicInspection.getSiteId());
			if (!siteId.isPresent() || !siteId.get().getSiteId().equals(periodicInspection.getSiteId())) {
				if (ipaoInspection != null && ipaoInspection.size() > 0) {
					for (IpaoInspection ipaoInspectionItr : ipaoInspection) {
						ipaoInspectionItr.setLocationCount(new Random().nextInt(999999999));
						if (ipaoInspectionItr !=null && ipaoInspectionItr.getConsumerUnit() != null && ipaoInspectionItr.getCircuit() != null
								&& ipaoInspectionItr.getIsolationCurrent() != null
								&& ipaoInspectionItr.getConsumerUnit().size() > 0
								&& ipaoInspectionItr.getCircuit().size() > 0
								&& ipaoInspectionItr.getIsolationCurrent().size() > 0) {
							ipaoInspectionItr.setConsumerUnit(addLocationCountInConsumerUnit(ipaoInspectionItr.getConsumerUnit()));
							//findConsumerUnitLocation(ipaoInspectionItr.getConsumerUnit());
							i++;
							if (i == ipaoInspection.size()) {
								periodicInspectionComment = new PeriodicInspectionComment();
								periodicInspectionComment.setInspectorFlag(Constants.INTIAL_FLAG_VALUE);
								periodicInspectionComment.setViewerFlag(Constants.INTIAL_FLAG_VALUE);
								periodicInspectionComment.setNoOfComment(1);
								periodicInspectionComment.setViewerDate(LocalDateTime.now());
								periodicInspectionComment.setPeriodicInspection(periodicInspection);
								listOfComments.add(periodicInspectionComment);
								periodicInspection.setPeriodicInspectorComment(listOfComments);
								periodicInspection.setCreatedDate(LocalDateTime.now());
								periodicInspection.setUpdatedDate(LocalDateTime.now());
								periodicInspection
										.setCreatedBy(userFullName.findByUserName(periodicInspection.getUserName()));
								periodicInspection
										.setUpdatedBy(userFullName.findByUserName(periodicInspection.getUserName()));
								try {
									inspectionRepository.save(periodicInspection);
									logger.debug("InspectionDetails Successfully Saved in DB");
								}catch(Exception e) {
									logger.error("Not able to save Inspection data "+e.getMessage());
									throw new InspectionException("Not able to save Inspection data "+e.getMessage());
								}
								
								siteDetails.updateSite(periodicInspection.getSiteId(),
										periodicInspection.getUserName(),"Step3 completed");
								logger.debug("Site Successfully Saved in DB");
							}

						} else {
							logger.error("Please fill all the fields before clicking next button");
							throw new InspectionException("Please fill all the fields before clicking next button");
						}
					}
				} else {
					logger.error("SiteId already present");
					throw new InspectionException("SiteId already present");
				}

			} else {
				logger.error("Inspection data contains duplicate Object");
				throw new InspectionException("Inspection data contains duplicate Object");
			}

		} else {
			logger.error("Invalid input");
			throw new InspectionException("Invalid input");
		}
	}

	/**
	 * @param userName,siteId
	 * retrieveInspectionDetails method to retrieve data based on userName,siteId
	 * @return List<IpaoInspection> object 
	*/
	@Override
	public PeriodicInspection retrieveInspectionDetails(String userName, Integer siteId)
			throws InspectionException {
		if (userName != null && !userName.isEmpty() && siteId != null) {
			PeriodicInspection inspectionRepo = inspectionRepository.findByUserNameAndSiteId(userName, siteId);
			if (inspectionRepo != null) {
//				inspectionRepo.setIpaoInspection(isNullLocationCount(inspectionRepo.getIpaoInspection()));
				inspectionRepo.setIpaoInspection(findNonRemovedObject.findNonRemovedInspectionLocation(inspectionRepo));
				sortingDateTime(inspectionRepo.getPeriodicInspectorComment());
				return inspectionRepo;
			} else {
				logger.error("Given UserName & Site doesn't exist Inspection");
				throw new InspectionException("Given UserName & Site doesn't exist Inspection");
			}

		} else {
			logger.error("Invalid Inputs");
			throw new InspectionException("Invalid Inputs");
		}
	}
	
	@Override
	public PeriodicInspection retrieveInspectionDetails(Integer siteId)
			throws InspectionException {
		if (siteId != null) {
			Optional<PeriodicInspection> inspectionRepoData = inspectionRepository.findBySiteId(siteId);
			PeriodicInspection inspectionRepo = inspectionRepoData.get();

			if (inspectionRepo != null) {
//				inspectionRepo.setIpaoInspection(isNullLocationCount(inspectionRepo.getIpaoInspection()));
				inspectionRepo.setIpaoInspection(findNonRemovedObject.findNonRemovedInspectionLocation(inspectionRepo));
				sortingDateTime(inspectionRepo.getPeriodicInspectorComment());
				return inspectionRepo;
			} else {
				logger.error("Given UserName & Site doesn't exist Inspection");
				throw new InspectionException("Given UserName & Site doesn't exist Inspection");
			}

		} else {
			logger.error("Invalid Inputs");
			throw new InspectionException("Invalid Inputs");
		}
	}
	

	/**
	 * @reportId,siteId must required
	 * @param PeriodicInspection Object
	 * updateInspectionDetails method to finding the given PeriodicInspectionId is available or not in DB,
	 * if available only allowed for updating 
	 * @throws CompanyDetailsException 
	 * 
	*/
	@Transactional
	@Override
	public void updateInspectionDetails(PeriodicInspection periodicInspection)
			throws InspectionException, CompanyDetailsException {
		if (periodicInspection != null && periodicInspection.getPeriodicInspectionId() != null
				&& periodicInspection.getPeriodicInspectionId() != 0 && periodicInspection.getSiteId() != null
				&& periodicInspection.getSiteId() != 0) {
			Optional<PeriodicInspection> periodicInspectionRepo = inspectionRepository
					.findById(periodicInspection.getPeriodicInspectionId());
			if (periodicInspectionRepo.isPresent()
					&& periodicInspectionRepo.get().getSiteId().equals(periodicInspection.getSiteId())) {
				addRemoveStatusInTesting(periodicInspection.getIpaoInspection());
				addRemoveStatusInTestDistRecords(periodicInspection.getIpaoInspection());
				List<IpaoInspection> ipaoInspection = periodicInspection.getIpaoInspection();
				
				for (IpaoInspection ipaoInspectionItr : ipaoInspection) {
					logger.debug("locationcount value adding for new location");
					// locationcount value adding for new location
					if (ipaoInspectionItr != null && ipaoInspectionItr.getLocationCount() == null) {
						ipaoInspectionItr.setLocationCount(new Random().nextInt(999999999));
						ipaoInspectionItr
								.setConsumerUnit(addLocationCountInConsumerUnit(ipaoInspectionItr.getConsumerUnit()));
					} else {
						for (ConsumerUnit consumerUnit : ipaoInspectionItr.getConsumerUnit()) {
							// locationcount value adding for new consumerUnit
							if (consumerUnit != null && consumerUnit.getConsumerId() == null) {
								consumerUnit.setLocationCount(new Random().nextInt(999999999));
								logger.debug("locationcount value adding for new consumerUnit");
							}
						}
					}
				}
				periodicInspection.setUpdatedDate(LocalDateTime.now());
				periodicInspection.setUpdatedBy(userFullName.findByUserName(periodicInspection.getUserName()));
				inspectionRepository.save(periodicInspection);
				logger.debug("Inspection successfully updated into DB");
				siteDetails.updateSite(periodicInspection.getSiteId(), periodicInspection.getUserName(),"Step3 completed");
				logger.debug("Updated successfully site updatedUsername",periodicInspection.getUserName());
			} else {
				logger.error("Given SiteId and ReportId is Invalid");
				throw new InspectionException("Given SiteId and ReportId is Invalid");
			}

		} else {
			logger.error("Invalid Inputs");
			throw new InspectionException("Invalid inputs");
		}

	}

	@Override
	public void sendComments(String userName, Integer siteId, PeriodicInspectionComment periodicInspectionComment)
			throws InspectionException {
		PeriodicInspection periodicInspection = verifyCommentsInfo(userName, siteId, periodicInspectionComment, Constants.SEND_COMMENT);
		if (periodicInspection != null) {
			inspectionRepository.save(periodicInspection);
			logger.debug("sendComments successfully into DB");
		} else {
			logger.error("Invalid Inputs");
			throw new InspectionException("Periodic-Inspection information doesn't exist for given Site-Id");
		}
	}

	@Override
	public String replyComments(String inspectorUserName, Integer siteId,
			PeriodicInspectionComment periodicInspectionComment) throws InspectionException {
		PeriodicInspection periodicInspection = verifyCommentsInfo(inspectorUserName, siteId, periodicInspectionComment,
				Constants.REPLY_COMMENT);
		if (periodicInspection != null) {
			inspectionRepository.save(periodicInspection);
			logger.debug("ReplyComments successfully into DB");
			return viewerName;
		} else {
			logger.error("Periodic-Inspection information doesn't exist for given Site-Id");
			throw new InspectionException("Periodic-Inspection information doesn't exist for given Site-Id");
		}
	}

	@Override
	public void approveComments(String userName, Integer siteId, PeriodicInspectionComment periodicInspectionComment)
			throws InspectionException {
		PeriodicInspection periodicInspection = verifyCommentsInfo(userName, siteId, periodicInspectionComment,
				Constants.APPROVE_REJECT_COMMENT);
		if (periodicInspection != null) {
			inspectionRepository.save(periodicInspection);
			logger.debug("ReplyComments successfully into DB");
		} else {
			logger.error("Periodic-Inspection information doesn't exist for given Site-Id");
			throw new InspectionException("Periodic-Inspection information doesn't exist for given Site-Id");
		}
	}

	private PeriodicInspection verifyCommentsInfo(String userName, Integer siteId,
			PeriodicInspectionComment periodicInspectionComment, String process) throws InspectionException {

		Boolean flagInspectionComment = true;
		if (userName != null && siteId != null && periodicInspectionComment != null) {
			Optional<Site> siteRepo = siteRepository.findById(siteId);
			if (siteRepo.isPresent() && siteRepo.get().getSiteId().equals(siteId)
					&& siteRepo.get().getAssignedTo() != null && siteRepo.get().getUserName() != null
					&& siteRepo.get().getSite() != null) {
				Optional<PeriodicInspection> periodicInspectionRepo = inspectionRepository.findBySiteId(siteId);
				if (periodicInspectionRepo.isPresent() && periodicInspectionRepo.get() != null
						&& periodicInspectionRepo.get().getUserName() != null
						&& checkInspectorViewer(userName, process, siteRepo, periodicInspectionRepo)) {
					PeriodicInspection periodicInspection = periodicInspectionRepo.get();
					periodicInspection.setUpdatedDate(LocalDateTime.now());
					periodicInspection.setUpdatedBy(userName);
					List<PeriodicInspectionComment> periodicInspectorCommentRepo = periodicInspection
							.getPeriodicInspectorComment();

					for (PeriodicInspectionComment periodicInspectionCommentItr : periodicInspectorCommentRepo) {
						if (periodicInspectionCommentItr.getCommentsId()
								.equals(periodicInspectionComment.getCommentsId())) {
							flagInspectionComment = false;

							periodicInspectionCommentItr.setPeriodicInspection(periodicInspection);

							if (process.equalsIgnoreCase(Constants.SEND_COMMENT)) {
								periodicInspectionCommentItr.setSiteName(siteRepo.get().getSite());
								periodicInspectionCommentItr.setInspectorEmail(siteRepo.get().getUserName());
								periodicInspectionCommentItr.setViewerUserEmail(siteRepo.get().getAssignedTo());
								periodicInspectionCommentItr.setViewerDate(LocalDateTime.now());
								periodicInspectionCommentItr
										.setViewerComment(periodicInspectionComment.getViewerComment());
								periodicInspectionCommentItr.setViewerFlag(Constants.INCREASED_FLAG_VALUE);
								periodicInspectionCommentItr.setViewerUserName(userFullName.findByUserName(userName));
								periodicInspectorCommentRepo.add(periodicInspectionCommentItr);
								periodicInspection.setPeriodicInspectorComment(periodicInspectorCommentRepo);
								return periodicInspection;
							}
							if (process.equalsIgnoreCase(Constants.REPLY_COMMENT)) {
								periodicInspectionCommentItr.setInspectorDate(LocalDateTime.now());
								periodicInspectionCommentItr
										.setInspectorUserName(userFullName.findByUserName(userName));
								periodicInspectionCommentItr
										.setInspectorComment(periodicInspectionComment.getInspectorComment());
								periodicInspectionCommentItr.setInspectorFlag(Constants.INCREASED_FLAG_VALUE);
								periodicInspectorCommentRepo.add(periodicInspectionCommentItr);
								periodicInspection.setPeriodicInspectorComment(periodicInspectorCommentRepo);
								return periodicInspection;
							}
							if (process.equalsIgnoreCase(Constants.APPROVE_REJECT_COMMENT)) {
								periodicInspectionCommentItr.setViewerUserName(userFullName.findByUserName(userName));
								periodicInspectionCommentItr
										.setApproveOrReject(periodicInspectionComment.getApproveOrReject());
								periodicInspectorCommentRepo.add(periodicInspectionCommentItr);
								periodicInspection.setPeriodicInspectorComment(periodicInspectorCommentRepo);
								return periodicInspection;
							}
						}
					}
					if (flagInspectionComment) {

						if (process.equalsIgnoreCase(Constants.SEND_COMMENT)) {
							periodicInspectionComment.setNoOfComment(
									checkNoOfComments(periodicInspection.getPeriodicInspectorComment()));
							periodicInspectionComment.setPeriodicInspection(periodicInspection);
							periodicInspectionComment.setSiteName(siteRepo.get().getSite());
							periodicInspectionComment.setInspectorEmail(siteRepo.get().getUserName());
							periodicInspectionComment.setViewerUserEmail(siteRepo.get().getAssignedTo());
							periodicInspectionComment.setViewerDate(LocalDateTime.now());
							periodicInspectionComment.setViewerFlag(Constants.INCREASED_FLAG_VALUE);
							periodicInspectionComment.setInspectorFlag(Constants.INTIAL_FLAG_VALUE);
							periodicInspectionComment.setViewerUserName(userFullName.findByUserName(userName));
							periodicInspectorCommentRepo.add(periodicInspectionComment);
							periodicInspection.setPeriodicInspectorComment(periodicInspectorCommentRepo);
							return periodicInspection;
						} else {
							logger.error("Sending viewer comments faild");
							throw new InspectionException("Sending viewer comments faild");
						}
					}
				} else {
					logger.error("Given username not have access for comments");
					throw new InspectionException("Given username not have access for comments");
				}

			} else {
				logger.error("Siteinformation doesn't exist, try with different Site-Id");
				throw new InspectionException("Siteinformation doesn't exist, try with different Site-Id");
			}

		} else {
			logger.error("Invalid Inputs");
			throw new InspectionException("Invalid Inputs");
		}
		return null;
	}
	
	private void sortingDateTime(List<PeriodicInspectionComment> listOfComments) {
		if (listOfComments.size() > 1) {
			Collections.sort(listOfComments, (o1, o2) -> o1.getViewerDate().compareTo(o2.getViewerDate()));
		}
	}
	
	private Integer checkNoOfComments(List<PeriodicInspectionComment> listOfComments) {
		Integer maxNum = 0;
		String approveRejectedFlag = "";
		for (PeriodicInspectionComment periodicInspectionComment : listOfComments) {
			if (periodicInspectionComment != null && maxNum <= periodicInspectionComment.getNoOfComment()) {
				maxNum = periodicInspectionComment.getNoOfComment();
				approveRejectedFlag = periodicInspectionComment.getApproveOrReject();
			}
		}
		if (approveRejectedFlag != null && approveRejectedFlag.equalsIgnoreCase(Constants.APPROVE_REJECT_COMMENT)) {
			return maxNum + 1;
		} else {
			return maxNum;
		}
	}
	
	private Boolean checkInspectorViewer(String userName, String process, Optional<Site> siteRepo,
			Optional<PeriodicInspection> periodicInspectionRepo) throws InspectionException {
		Boolean flag = false;
		if (process.equalsIgnoreCase(Constants.REPLY_COMMENT)) {
			if (siteRepo.get().getUserName().equalsIgnoreCase(userName)
					&& periodicInspectionRepo.get().getUserName() != null
					&& siteRepo.get().getUserName().equalsIgnoreCase(periodicInspectionRepo.get().getUserName())) {
				Set<SitePersons> sitePersons = siteRepo.get().getSitePersons();
				for (SitePersons sitePersonsItr : sitePersons) {
					if (sitePersonsItr != null && sitePersonsItr.getPersonInchargeEmail() != null) {
						viewerName = sitePersonsItr.getPersonInchargeEmail();
						return flag = true;
					}
				}
			} else {
				logger.error("Given userName not allowing for " + process + " comment");
				throw new InspectionException("Given userName not allowing for " + process + " comment");
			}

		} else if (process.equalsIgnoreCase(Constants.SEND_COMMENT) || process.equalsIgnoreCase(Constants.APPROVE_REJECT_COMMENT)) {

			Set<SitePersons> sitePersons = siteRepo.get().getSitePersons();
			for (SitePersons sitePersonsItr : sitePersons) {
				if (sitePersonsItr != null && sitePersonsItr.getPersonInchargeEmail() != null
						&& sitePersonsItr.getPersonInchargeEmail().equalsIgnoreCase(userName)) {
					return flag = true;
				} else {
					logger.debug("Given userName not allowing for " + process + " comment");
					throw new InspectionException("Given userName not allowing for " + process + " comment");
				}
			}
		}
		return flag;
	}
	
	private void addRemoveStatusInTesting(List<IpaoInspection> listIpaoInspection)
			throws InspectionException {

		for (IpaoInspection ipaoInspectionItr : listIpaoInspection) {
			if (ipaoInspectionItr != null && ipaoInspectionItr.getLocationCount() != null
					&& ipaoInspectionItr.getInspectionFlag().equalsIgnoreCase("R")) {
				try {
					Testing testingRepo = testInfoRepository.findByLocationCount(ipaoInspectionItr.getLocationCount());
					if (testingRepo != null
							&& testingRepo.getLocationCount().equals(ipaoInspectionItr.getLocationCount())) {
						testingRepo.setTestingStatus("R");
						testInfoRepository.save(testingRepo);
					}
				} catch (Exception e) {
					logger.debug("Please check removed Inspection Location data not available in PeriodicTesting"
							+ e.getMessage());
					throw new InspectionException(
							"Please check removed Inspection Location data not available in PeriodicTesting"
									+ e.getMessage());
				}
			}
		}
	}


//	private List<IpaoInspection> isNullLocationCount(List<IpaoInspection> ipaoInspection) {
//		List<IpaoInspection> ipaoInspectionList = new ArrayList<IpaoInspection>();
//		 for (IpaoInspection ipaoInspectionItr : ipaoInspection) {
//			if (ipaoInspectionItr !=null && ipaoInspectionItr.getLocationCount() == null) {
//				ipaoInspectionItr.setLocationCount(new Random().nextInt(999999999));
//				ipaoInspectionList.add(ipaoInspectionItr);
//			}
//			ipaoInspectionList.add(ipaoInspectionItr);
//		}
//		return ipaoInspectionList;
//	}

//	private void findConsumerUnitLocation(List<ConsumerUnit> consumerUnitList) throws InspectionException {
//		for (ConsumerUnit consumerUnit : consumerUnitList) {
//			if (consumerUnit != null && consumerUnit.getLocation() != null) {
//				ConsumerUnit consumerLocation = inspectionConsumerUnitRepository
//						.findByLocation(consumerUnit.getLocation());
//				if (consumerLocation == null) {
//
//				} else {
//					logger.error("Given LocationName already present in ConsumerUnit,please try new LocationName");
//					throw new InspectionException(
//							"Given LocationName already present in ConsumerUnit,please try new LocationName");
//				}
//
//			} else {
//				logger.error("Please check Location Information in ConsumerUnit");
//				throw new InspectionException("Please check Location Information in ConsumerUnit");
//			}
//		}
//
//	}

	/**
	 * addRemoveStatusInTestDistRecords function first finding consumer R status then search corresponding 
	 *  testdistrecords it will set R status
	 * @throws InspectionException 
	 * */
	private void addRemoveStatusInTestDistRecords(List<IpaoInspection> ipaoInspectionList) throws InspectionException {

		for (IpaoInspection ipaoInspection : ipaoInspectionList) {
			List<ConsumerUnit> consumerUnitList = ipaoInspection.getConsumerUnit();
			for (ConsumerUnit consumerUnit : consumerUnitList) {
				if (consumerUnit != null && consumerUnit.getConsumerStatus() != null
						&& consumerUnit.getConsumerStatus().equalsIgnoreCase("R")
						&& consumerUnit.getLocationCount() != null) {
					try {

						TestDistRecords testDistRecords = testDistRecordsRepository
								.findByLocationCount(consumerUnit.getLocationCount());
						if (testDistRecords != null) {
							testDistRecords.setTestDistRecordStatus("R");
							testDistRecordsRepository.save(testDistRecords);
						}
					} catch (Exception e) {
						logger.error(
								"Please verify Removed consumerUnit records,Removed data not available in TestingDistrubtionRecords"
										+ e.getMessage());
						throw new InspectionException(
								"Please verify Removed consumerUnit records,Removed data not available in TestingDistrubtionRecords"
										+ e.getMessage());
					}

				}
			}

		}

	}
	
	@Override
	public String retrieveLocation(String distributionDetails, String reference, String location) {
		// TODO Auto-generated method stub
		ConsumerUnit fetchLocationDetails = inspectionConsumerUnitRepository.findByDistributionBoardDetailsAndReferanceAndLocation(distributionDetails, reference, location);
		return fetchLocationDetails != null ? fetchLocationDetails.getLocation(): "";
	}
	
	/**
	 * addLocationCountInConsumerUnit function  finding cosumerunit then randomly added some digts number in locationcount
	 * @throws InspectionException 
	 * */	
	private List<ConsumerUnit> addLocationCountInConsumerUnit(List<ConsumerUnit> consumerUnitList) {
		List<ConsumerUnit> locationCountList = new ArrayList<ConsumerUnit>();
		 for (ConsumerUnit consumerUnit : consumerUnitList) {
			 consumerUnit.setLocationCount(new Random().nextInt(999999999));
			 locationCountList.add(consumerUnit);
		}
		return locationCountList;
	}

	
}
