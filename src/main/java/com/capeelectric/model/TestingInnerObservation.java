package com.capeelectric.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "testing_inner_observations_table")
public class TestingInnerObservation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TESTING_INNER_OBSERVATIONS_ID")
	private Integer testingInnerObervationsId;

	@Column(name = "OBSERVATION_COMPONENT_DETAILS")
	private String observationComponentDetails;

	@Column(name = "OBSERVATION_DESCRIPTION")
	private String observationDescription;
	
	@Column(name = "TESTING_INNER_OBSERVATION_STATUS")
	private String testingInnerObservationStatus;  

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "TEST_DIST_RECORD_ID")
	private TestDistRecords testDistRecords;

	public Integer getTestingInnerObervationsId() {
		return testingInnerObervationsId;
	}

	public void setTestingInnerObervationsId(Integer testingInnerObervationsId) {
		this.testingInnerObervationsId = testingInnerObervationsId;
	}

	public String getObservationComponentDetails() {
		return observationComponentDetails;
	}

	public void setObservationComponentDetails(String observationComponentDetails) {
		this.observationComponentDetails = observationComponentDetails;
	}

	public String getObservationDescription() {
		return observationDescription;
	}

	public void setObservationDescription(String observationDescription) {
		this.observationDescription = observationDescription;
	}

	public String getTestingInnerObservationStatus() {
		return testingInnerObservationStatus;
	}

	public TestDistRecords getTestDistRecords() {
		return testDistRecords;
	}

	public void setTestDistRecords(TestDistRecords testDistRecords) {
		this.testDistRecords = testDistRecords;
	}

	public void setTestingInnerObservationStatus(String testingInnerObservationStatus) {
		this.testingInnerObservationStatus = testingInnerObservationStatus;
	}

}