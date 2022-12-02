package com.capeelectric.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capeelectric.exception.ObservationException;
import com.capeelectric.model.AllComponentObservation;
import com.capeelectric.model.InspectionOuterObservation;
import com.capeelectric.model.IpaoInspection;
import com.capeelectric.model.ObservationComponent;
import com.capeelectric.model.PeriodicInspection;
import com.capeelectric.model.SupplyCharacteristics;
import com.capeelectric.model.TestingReport;
import com.capeelectric.repository.InspectionRepository;
import com.capeelectric.repository.ObservationRepository;
import com.capeelectric.repository.SupplyCharacteristicsRepository;
import com.capeelectric.repository.TestingReportRepository;
import com.capeelectric.service.ObservationService;
import com.capeelectric.util.FindNonRemovedObject;
import com.capeelectric.util.UserFullName;

/**
 * 
 * @author capeelectricsoftware
 *
 */
@Service
public class ObservationServiceImpl implements ObservationService {

	@Autowired
	private ObservationRepository observationRepository;

	@Autowired
	private UserFullName userFullName;
	
	@Autowired
	private SupplyCharacteristicsRepository supplyCharacteristicsRepository;

	@Autowired
	private InspectionRepository inspectionRepository;

	@Autowired
	private TestingReportRepository testingReportRepository;
	
	@Autowired
	private FindNonRemovedObject findNonRemovedObject;
	
	@Override
	public void addObservation(ObservationComponent observationComponent) throws ObservationException {
		if (observationComponent != null && observationComponent.getUserName() != null) {
			Optional<ObservationComponent> observationRepo = observationRepository
					.findByUserNameAndSiteIdAndObservationComponent(observationComponent.getUserName(),
							observationComponent.getSiteId(), observationComponent.getObservationComponent());
			if (!observationRepo.isPresent()) {
				observationComponent.setCreatedDate(LocalDateTime.now());
				observationComponent.setCreatedBy(userFullName.findByUserName(observationComponent.getUserName()));
				observationRepository.save(observationComponent);
			}

			else {

				throw new ObservationException("UserName&SiteId&ObservationComponent already exists");
			}

		} else {
			throw new ObservationException("Invalid Inputs");
		}
	}

	@Override
	public void updateObservation(ObservationComponent observationComponent) throws ObservationException {
		if (observationComponent != null && observationComponent.getObservationId() != null
				&& observationComponent.getObservationId() != 0 && observationComponent.getSiteId() != null
				&& observationComponent.getSiteId() != 0) {
			Optional<ObservationComponent> observationRepo = observationRepository
					.findByUserNameAndSiteIdAndObservationComponent(observationComponent.getUserName(),
							observationComponent.getSiteId(), observationComponent.getObservationComponent());
			if (observationRepo.isPresent()
					&& observationRepo.get().getSiteId().equals(observationComponent.getSiteId())) {
				observationComponent.setUpdatedDate(LocalDateTime.now());
				observationComponent.setUpdatedBy(userFullName.findByUserName(observationComponent.getUserName()));
				observationRepository.save(observationComponent);

			} else {
				throw new ObservationException("Given SiteId and ObservationId is Invalid");
			}

		} else {
			throw new ObservationException("Invalid inputs");
		}

	}

	@Override
	public ObservationComponent retrieveObservation(String userName, Integer siteId, String observationComponent)
			throws ObservationException {
		if (userName != null && !userName.isEmpty() && siteId != null && siteId != 0) {
			Optional<ObservationComponent> observationRepo = observationRepository
					.findByUserNameAndSiteIdAndObservationComponent(userName, siteId, observationComponent);
			if (observationRepo.isPresent() && observationRepo.get() != null) {
				return observationRepo.get();
			} else {
				throw new ObservationException("Given UserName & SiteId doesn't exist Observation");
			}
		} else {
			throw new ObservationException("Invalid Inputs");

		}
	}

	@Override
	public AllComponentObservation retrieveObservationsInSummary(String userName, Integer siteId)
			throws ObservationException {
		AllComponentObservation allComponentObservation = new AllComponentObservation();
		if (userName != null && !userName.isEmpty() && siteId != null && siteId != 0) {

			Optional<SupplyCharacteristics> supplyCharacteristics = supplyCharacteristicsRepository
					.findBySiteId(siteId);
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
		} else {
			throw new ObservationException("Invalid Inputs");

		}
		return allComponentObservation;
	}
}
