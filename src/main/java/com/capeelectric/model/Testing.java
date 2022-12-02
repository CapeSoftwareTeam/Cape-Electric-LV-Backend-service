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
@Table(name = "TESTING_TABLE")
public class Testing implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TESTING_ID")
	private Integer testingId;
	
	@Column(name = "LOCATION_NUMBER")
	private Integer locationNumber;
	
	@Column(name = "LOCATION_NAME")
	private String locationName;

	@Column(name = "TEST_ENGINEER_NAME")
	private String testEngineerName;
	
	@Column(name = "DESIGNATION")
	private String designation;
	
	@Column(name = "COMPANY_NAME")
	private String companyName;

	@Column(name = "DATE")
	private String date;
	
	@Column(name = "TESTING_STATUS")
	private String testingStatus;
	
	@Column(name = "LOCATION_COUNT")
	private Integer locationCount;

	@JsonManagedReference
	@OneToMany(mappedBy = "testing", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TestDistRecords> testDistRecords;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "testingReport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TestingEquipment> testingEquipment;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "TESTING_REPORT_ID")
	private TestingReport testingReport;

	public Integer getTestingId() {
		return testingId;
	}

	public void setTestingId(Integer testingId) {
		this.testingId = testingId;
	}

 	public Integer getLocationNumber() {
		return locationNumber;
	}

	public void setLocationNumber(Integer locationNumber) {
		this.locationNumber = locationNumber;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getTestEngineerName() {
		return testEngineerName;
	}

	public void setTestEngineerName(String testEngineerName) {
		this.testEngineerName = testEngineerName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<TestDistRecords> getTestDistRecords() {
		return testDistRecords;
	}

	public void setTestDistRecords(List<TestDistRecords> testDistRecords) {
		this.testDistRecords = testDistRecords;
	}

	public TestingReport getTestingReport() {
		return testingReport;
	}

	public void setTestingReport(TestingReport testingReport) {
		this.testingReport = testingReport;
	}

	public List<TestingEquipment> getTestingEquipment() {
		return testingEquipment;
	}

	public void setTestingEquipment(List<TestingEquipment> testingEquipment) {
		this.testingEquipment = testingEquipment;
	}


	public String getTestingStatus() {
		return testingStatus;
	}

	public void setTestingStatus(String testingStatus) {
		this.testingStatus = testingStatus;
	}

	public Integer getLocationCount() {
		return locationCount;
	}

	public void setLocationCount(Integer locationCount) {
		this.locationCount = locationCount;
  }
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
