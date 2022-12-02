package com.capeelectric.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * 
 * @author capeelectricsoftware
 *
 */
@Entity
@Table(name = "test_dist_records_table")
public class TestDistRecords implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TEST_DIST_RECORD_ID")
	private Integer testDistRecordId;
	
	@Column(name = "TEST_DIST_RECORDS_STATUS")
	private String testDistRecordStatus;
	
	@Column(name = "LOCATION_COUNT")
	private Integer locationCount;
 
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "TESTING_ID")
	private Testing testing;

	@JsonManagedReference
	@OneToMany(mappedBy = "testDistRecords", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TestDistribution> testDistribution;

	@JsonManagedReference
	@OneToMany(mappedBy = "testDistRecords", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TestingRecords> testingRecords;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "testDistRecords", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TestingInnerObservation> testingInnerObservation;

	public Integer getTestDistRecordId() {
		return testDistRecordId;
	}

	public void setTestDistRecordId(Integer testDistRecordId) {
		this.testDistRecordId = testDistRecordId;
	}

	public Testing getTesting() {
		return testing;
	}

	public void setTesting(Testing testing) {
		this.testing = testing;
	}

	public List<TestDistribution> getTestDistribution() {
		return testDistribution;
	}

	public void setTestDistribution(List<TestDistribution> testDistribution) {
		this.testDistribution = testDistribution;
	}

	public List<TestingRecords> getTestingRecords() {
		return testingRecords;
	}

	public void setTestingRecords(List<TestingRecords> testingRecords) {
		this.testingRecords = testingRecords;
	}

	public String getTestDistRecordStatus() {
		return testDistRecordStatus;
	}

	public void setTestDistRecordStatus(String testDistRecordStatus) {
		this.testDistRecordStatus = testDistRecordStatus;
	}

	public Integer getLocationCount() {
		return locationCount;
	}

	public void setLocationCount(Integer locationCount) {
		this.locationCount = locationCount;
	}

	public List<TestingInnerObservation> getTestingInnerObservation() {
		return testingInnerObservation;
	}

	public void setTestingInnerObservation(List<TestingInnerObservation> testingInnerObservation) {
		this.testingInnerObservation = testingInnerObservation;
	}

}
