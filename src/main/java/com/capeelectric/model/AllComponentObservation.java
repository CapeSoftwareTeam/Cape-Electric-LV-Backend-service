package com.capeelectric.model;

import java.util.List;

/**
 *
 * @author capeelectricsoftware
 *
 */
public class AllComponentObservation {

	private List<SupplyOuterObservation> supplyOuterObservation;

	private List<InspectionOuterObservation> InspectionOuterObservation;

	private List<TestingInnerObservation> testingInnerObservation;

	public List<SupplyOuterObservation> getSupplyOuterObservation() {
		return supplyOuterObservation;
	}

	public void setSupplyOuterObservation(List<SupplyOuterObservation> supplyOuterObservation) {
		this.supplyOuterObservation = supplyOuterObservation;
	}

	public List<InspectionOuterObservation> getInspectionOuterObservation() {
		return InspectionOuterObservation;
	}

	public void setInspectionOuterObservation(List<InspectionOuterObservation> inspectionOuterObservation) {
		InspectionOuterObservation = inspectionOuterObservation;
	}

	public List<TestingInnerObservation> getTestingInnerObservation() {
		return testingInnerObservation;
	}

	public void setTestingInnerObservation(List<TestingInnerObservation> testingInnerObservation) {
		this.testingInnerObservation = testingInnerObservation;
	}

}
