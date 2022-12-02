package com.capeelectric.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.annotation.Configuration;

import com.capeelectric.model.AlternativeInnerObservation;
import com.capeelectric.model.BoundingLocationReport;
import com.capeelectric.model.Circuit;
import com.capeelectric.model.CircuitBreaker;
import com.capeelectric.model.ConsumerUnit;
import com.capeelectric.model.EarthingLocationReport;
import com.capeelectric.model.InspectionInnerObservations;
import com.capeelectric.model.InspectionOuterObservation;
import com.capeelectric.model.InstalLocationReport;
import com.capeelectric.model.IpaoInspection;
import com.capeelectric.model.PeriodicInspection;
import com.capeelectric.model.SignatorDetails;
import com.capeelectric.model.SupplyCharacteristics;
import com.capeelectric.model.SupplyOuterObservation;
import com.capeelectric.model.SupplyParameters;
import com.capeelectric.model.TestDistRecords;
import com.capeelectric.model.Testing;
import com.capeelectric.model.TestingEquipment;
import com.capeelectric.model.TestingInnerObservation;
import com.capeelectric.model.TestingRecords;
import com.capeelectric.model.TestingReport;

/**
 * This FindNonRemovedObject Util class finding non Removed object for all_steps
 * 
 * @author capeelectricsoftware
 *
 */
@Configuration
public class FindNonRemovedObject {

	public List<IpaoInspection> findNonRemovedInspectionLocation(PeriodicInspection inspectionRepo) {

		ArrayList<IpaoInspection> inspectionReport = new ArrayList<IpaoInspection>();
		List<IpaoInspection> findNonRemoveLocation = inspectionRepo.getIpaoInspection();
		for (IpaoInspection inspectionLocationReport : findNonRemoveLocation) {
			if (inspectionLocationReport.getInspectionFlag()==null || !inspectionLocationReport.getInspectionFlag().equalsIgnoreCase("R")) {
				if(inspectionLocationReport.getInspectionFlag()==null) {
					inspectionLocationReport.setInspectionFlag("N");
				}
				inspectionLocationReport.setCircuit(findNonRemovedInspectionCircuit(inspectionLocationReport.getCircuit()));
				inspectionLocationReport.setConsumerUnit(findNonRemovedInspectionConsumerUnit(inspectionLocationReport.getConsumerUnit()));
				inspectionLocationReport.setInspectionOuterObervation(findNonRemovedInspectionOuterObervation(inspectionLocationReport.getInspectionOuterObervation()));
				inspectionReport.add(inspectionLocationReport);
				 
			}
		}
		return inspectionReport;
	}
	
	public List<Circuit> findNonRemovedInspectionCircuit(List<Circuit> listOfCircuit) {

		ArrayList<Circuit> unRemovedCircuit = new ArrayList<Circuit>();
		for (Circuit circuit : listOfCircuit) {
			if (circuit.getCircuitStatus() == null || !circuit.getCircuitStatus().equalsIgnoreCase("R")) {
				if (circuit.getCircuitStatus() == null) {
					circuit.setCircuitStatus("N");
				}
				unRemovedCircuit.add(circuit);
			}
		}
		return unRemovedCircuit;
	}
	
	public List<ConsumerUnit> findNonRemovedInspectionConsumerUnit(List<ConsumerUnit> ConsumerUnit) {

		ArrayList<ConsumerUnit> unRemovedConsumer = new ArrayList<ConsumerUnit>();
		for (ConsumerUnit consumerUnit : ConsumerUnit) {
			if (consumerUnit.getConsumerStatus() == null || !consumerUnit.getConsumerStatus().equalsIgnoreCase("R")) {
				if (consumerUnit.getConsumerStatus() == null) {
					consumerUnit.setConsumerStatus("N");
				}
				unRemovedConsumer.add(consumerUnit);
			}
		}
		return unRemovedConsumer;
	}

	public List<InstalLocationReport> findNonRemovedInstallLocation(SupplyCharacteristics supplyCharacteristicsRepo) {
		ArrayList<InstalLocationReport> locationReport = new ArrayList<InstalLocationReport>();
		List<InstalLocationReport> findNonRemoveLocation = supplyCharacteristicsRepo.getInstalLocationReport();
		for (InstalLocationReport instalLocationReport : findNonRemoveLocation) {
			if (instalLocationReport.getInstalLocationReportStatus()==null || !instalLocationReport.getInstalLocationReportStatus().equalsIgnoreCase("R")) {
				if(instalLocationReport.getInstalLocationReportStatus()==null) {
					instalLocationReport.setInstalLocationReportStatus("N");
				}
				locationReport.add(instalLocationReport);
			}
		}
		return locationReport;
	}

	public List<BoundingLocationReport> findNonRemovedBondingLocation(
			SupplyCharacteristics supplyCharacteristicsRepo) {
		ArrayList<BoundingLocationReport> locationReport = new ArrayList<BoundingLocationReport>();
		List<BoundingLocationReport> findNonRemoveLocation = supplyCharacteristicsRepo.getBoundingLocationReport();
		for (BoundingLocationReport bondingLocationReport : findNonRemoveLocation) {
			if (bondingLocationReport.getInstalLocationReportStatus()==null || !bondingLocationReport.getInstalLocationReportStatus().equalsIgnoreCase("R")) {
				if(bondingLocationReport.getInstalLocationReportStatus()==null) {
					bondingLocationReport.setInstalLocationReportStatus("N");
				}
				locationReport.add(bondingLocationReport);
			}
		}
		return locationReport;
	}

	public List<EarthingLocationReport> findNonRemovedEarthingLocation(
			SupplyCharacteristics supplyCharacteristicsRepo) {
		ArrayList<EarthingLocationReport> locationReport = new ArrayList<EarthingLocationReport>();
		List<EarthingLocationReport> findNonRemoveLocation = supplyCharacteristicsRepo.getEarthingLocationReport();
		for (EarthingLocationReport earthingLocationReport : findNonRemoveLocation) {
			if (earthingLocationReport.getInstalLocationReportStatus() ==null || !earthingLocationReport.getInstalLocationReportStatus().equalsIgnoreCase("R")) {
				if(earthingLocationReport.getInstalLocationReportStatus()==null) {
					earthingLocationReport.setInstalLocationReportStatus("N");
				}
				locationReport.add(earthingLocationReport);
			}
		}
		return locationReport;
	}

	public List<Testing> findNonRemoveTesting(List<Testing> listOfTesting) {
		ArrayList<Testing> nonRemoveTesting = new ArrayList<Testing>();
		for (Testing testing : listOfTesting) {
			if (testing != null && testing.getTestingStatus() != null
					&& !testing.getTestingStatus().equalsIgnoreCase("R")) {

				if (testing.getTestingStatus() == null) {
					testing.setTestingStatus("N");
				}
				testing.setTestingEquipment(findNonRemoveTestingEquipment(testing.getTestingEquipment()));
				List<TestDistRecords> NonRemoveTestingRecords = new ArrayList<TestDistRecords>();
				List<TestDistRecords> testDistRecords = testing.getTestDistRecords();
				for (TestDistRecords testDistRecord : testDistRecords) {

					if (testDistRecord.getTestDistRecordStatus() == null) {
						testDistRecord.setTestDistRecordStatus("N");
					} else if (testDistRecord.getTestDistRecordStatus() != null
							&& !testDistRecord.getTestDistRecordStatus().equalsIgnoreCase("R")) {
						testDistRecord
								.setTestingRecords(findNonRemoveTestingRecord(testDistRecord.getTestingRecords()));
						testDistRecord.setTestingInnerObservation(
								findNonRemoveTestingInnerObservation(testDistRecord.getTestingInnerObservation()));
						NonRemoveTestingRecords.add(testDistRecord);
					}
				}
				testing.setTestDistRecords(NonRemoveTestingRecords);
				nonRemoveTesting.add(testing);
			}

		}
		return nonRemoveTesting;
	}

	private List<TestingEquipment> findNonRemoveTestingEquipment(List<TestingEquipment> testingEquipment) {
		List<TestingEquipment> listNonRemovedTestingEquipment = new ArrayList<TestingEquipment>();
		for (TestingEquipment testingEquipmentItr : testingEquipment) {
			if (testingEquipmentItr.getTestingEquipmentStatus() == null || !testingEquipmentItr.getTestingEquipmentStatus().equalsIgnoreCase("R")) {
				if(testingEquipmentItr.getTestingEquipmentStatus() ==null) {
					testingEquipmentItr.setTestingEquipmentStatus("N");
				}
				listNonRemovedTestingEquipment.add(testingEquipmentItr);
			}
		}
		return listNonRemovedTestingEquipment;
	}

	public List<TestingRecords> findNonRemoveTestingRecord(List<TestingRecords> listOfTestingRecords) {
		List<TestingRecords> listNonRemovedTestingRecord = new ArrayList<TestingRecords>();
		for (TestingRecords testingRecords : listOfTestingRecords) {
			if (testingRecords.getTestingRecordStatus() ==null || !testingRecords.getTestingRecordStatus().equalsIgnoreCase("R")) {
				if(testingRecords.getTestingRecordStatus() ==null) {
					testingRecords.setTestingRecordStatus("N");
				}
				listNonRemovedTestingRecord.add(testingRecords);
			}
		}
		return listNonRemovedTestingRecord;
	}

	public Set<SignatorDetails> findNonRemovedReport(Set<SignatorDetails> signatorDetails) {
		Set<SignatorDetails> signatorDetail = new HashSet<SignatorDetails>();
		for (SignatorDetails signatorDetailItr : signatorDetails) {
			if (signatorDetailItr.getSignatorStatus() == null
					|| !signatorDetailItr.getSignatorStatus().equalsIgnoreCase("R")) {
				if(signatorDetailItr.getSignatorStatus() == null) {
					signatorDetailItr.setSignatorStatus("N");
				}
				signatorDetail.add(signatorDetailItr);
			}
		}
		return signatorDetail;
	}

//	public List<SummaryObervation> findNonRemoveObservation(List<SummaryObervation> summaryObervation) {
//		List<SummaryObervation> obervationList = new ArrayList<SummaryObervation>();
//		for (SummaryObervation obervation : summaryObervation) {
//			if (obervation.getObervationStatus() == null
//					|| !obervation.getObervationStatus().equalsIgnoreCase("R")) {
//				if(obervation.getObervationStatus() == null) {
//					obervation.setObervationStatus("N");
//				}
//				obervationList.add(obervation);
//			}
//		}
//		return obervationList;
//	}

	public List<CircuitBreaker> findNonRemovedCircuitBreaker(List<CircuitBreaker> circuitBreaker) {
		List<CircuitBreaker> circuitBreakerList = new ArrayList<CircuitBreaker>();
		for (CircuitBreaker circuitBreakerItr : circuitBreaker) {
			if (circuitBreakerItr.getCircuitStatus() == null
					|| !circuitBreakerItr.getCircuitStatus().equalsIgnoreCase("R")) {
				if(circuitBreakerItr.getCircuitStatus() == null) {
					circuitBreakerItr.setCircuitStatus("N");
				}
				circuitBreakerList.add(circuitBreakerItr);
			}
		}
		return circuitBreakerList;
	}

	public List<SupplyParameters> findNonRemovedSupplyParameters(List<SupplyParameters> supplyParameters) {
		List<SupplyParameters> supplyParametersList = new ArrayList<SupplyParameters>();
		for (SupplyParameters supplyParametersItr : supplyParameters) {
			if (supplyParametersItr.getSupplyParameterStatus() == null
					|| !supplyParametersItr.getSupplyParameterStatus().equalsIgnoreCase("R")) {
				if(supplyParametersItr.getSupplyParameterStatus() == null) {
					supplyParametersItr.setSupplyParameterStatus("N");
				}
				supplyParametersList.add(supplyParametersItr);
			}
		}
		return supplyParametersList;
	}

	public List<SupplyOuterObservation> findNonRemovedSupplyOuterObservation(
			List<SupplyOuterObservation> supplyOuterObservation) {
		List<SupplyOuterObservation> supplyOuterObservationList = new ArrayList<SupplyOuterObservation>();
		for (SupplyOuterObservation supplyOuterObservationItr : supplyOuterObservation) {
			if (supplyOuterObservationItr.getSupplyOuterObservationStatus() != null
					&& !supplyOuterObservationItr.getSupplyOuterObservationStatus().equalsIgnoreCase("R")) {
				List<AlternativeInnerObservation> alternativeInnerObservationList = new ArrayList<AlternativeInnerObservation>();
				if (supplyOuterObservationItr.getAlternativeInnerObservation() != null 
						&& supplyOuterObservationItr.getObservationComponentDetails().equalsIgnoreCase("alternate")) {
					for (AlternativeInnerObservation alternativeInnerObservation : supplyOuterObservationItr
							.getAlternativeInnerObservation()) {
						if (alternativeInnerObservation.getAlternativeInnerObservationStatus() != null
								&& !alternativeInnerObservation.getAlternativeInnerObservationStatus()
										.equalsIgnoreCase("R")) {
							alternativeInnerObservationList.add(alternativeInnerObservation);
						}
					}
					
				}
				supplyOuterObservationItr.setAlternativeInnerObservation(alternativeInnerObservationList);
				supplyOuterObservationList.add(supplyOuterObservationItr);
			}
		}
		return supplyOuterObservationList;
	}


	private List<InspectionOuterObservation> findNonRemovedInspectionOuterObervation(
			List<InspectionOuterObservation> inspectionOuterObervation) {

		List<InspectionOuterObservation> outerObservationList = new ArrayList<InspectionOuterObservation>();
		for (InspectionOuterObservation outerObservation : inspectionOuterObervation) {
			if (outerObservation.getInspectionOuterObservationStatus() != null
					&& !outerObservation.getInspectionOuterObservationStatus().equalsIgnoreCase("R")) {
				List<InspectionInnerObservations> innerObservationsList = new ArrayList<InspectionInnerObservations>();
				if (outerObservation.getInspectionInnerObservations() != null) {
					for (InspectionInnerObservations innerObservations : outerObservation
							.getInspectionInnerObservations()) {
						if (innerObservations.getInspectionInnerObservationStatus() != null
								&& !innerObservations.getInspectionInnerObservationStatus().equalsIgnoreCase("R")) {
							innerObservationsList.add(innerObservations);
						}
					}
				}
				outerObservation.setInspectionInnerObservations(innerObservationsList);
				outerObservationList.add(outerObservation);
			}
		}
		return outerObservationList;
	}

	public List<TestingInnerObservation> findNonRemoveTestingInnerObservationByReport(
			Optional<TestingReport> testingReport) {

		List<TestingInnerObservation> innerObservationList = new ArrayList<TestingInnerObservation>();
		List<Testing> testingList = testingReport.get().getTesting();

		for (Testing testing : testingList) {
			if (testing.getTestingStatus() != null && !testing.getTestingStatus().equalsIgnoreCase("R")) {
				List<TestDistRecords> testDistRecords = testing.getTestDistRecords();
				for (TestDistRecords testDistRecordItr : testDistRecords) {
					if (testDistRecordItr.getTestDistRecordStatus() != null
							&& !testDistRecordItr.getTestDistRecordStatus().equalsIgnoreCase("R")) {
						innerObservationList.addAll(
								findNonRemoveTestingInnerObservation(testDistRecordItr.getTestingInnerObservation()));

					}
				}
			}

		}
		return innerObservationList;

	}
	
	
	public List<TestingInnerObservation> findNonRemoveTestingInnerObservation(
			List<TestingInnerObservation> testingInnerObservation) {
		List<TestingInnerObservation> innerObservationList = new ArrayList<TestingInnerObservation>();

		for (TestingInnerObservation innerObservation : testingInnerObservation) {
			if (innerObservation.getTestingInnerObservationStatus() != null
					&& !innerObservation.getTestingInnerObservationStatus().equalsIgnoreCase("R")) {
				innerObservationList.add(innerObservation);
			}
		}
		return innerObservationList;
	}
}
