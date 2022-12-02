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

/**
 * 
 * @author capeelectricsoftware
 *
 */
@Entity
@Table(name = "testing_distribution_table")
public class TestDistribution implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DISTRIBUTION_ID")
	private Integer distributionId;

	@Column(name = "DISTRIBUTION_BOARD_DETAILS")
	private String distributionBoardDetails;

	@Column(name = "REFERANCE")
	private String referance;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "CORRECT_SUPPLY_POLARITY")
	private String correctSupplyPolarity;

	@Column(name = "NUM_OUTPUT_CIRCUITS_USE")
	private String numOutputCircuitsUse;

	@Column(name = "RATINGS_AMPS")
	private String ratingsAmps;

	@Column(name = "NUM_OUTPUT_CIRCUITS_SPARE")
	private String numOutputCircuitsSpare;

	@Column(name = "INSTALLED_EQUIPMENT_VULNARABLE")
	private String installedEquipmentVulnarable;
	
	@Column(name = "SOURCE_FROM_SUPPLY")
	private String sourceFromSupply;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "TEST_DIST_RECORD_ID")
	private TestDistRecords testDistRecords;
	
	public String getSourceFromSupply() {
		return sourceFromSupply;
	}

	public void setSourceFromSupply(String sourceFromSupply) {
		this.sourceFromSupply = sourceFromSupply;
	}

	public Integer getDistributionId() {
		return distributionId;
	}

	public void setDistributionId(Integer distributionId) {
		this.distributionId = distributionId;
	}

	public String getDistributionBoardDetails() {
		return distributionBoardDetails;
	}

	public void setDistributionBoardDetails(String distributionBoardDetails) {
		this.distributionBoardDetails = distributionBoardDetails;
	}

	public String getReferance() {
		return referance;
	}

	public void setReferance(String referance) {
		this.referance = referance;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCorrectSupplyPolarity() {
		return correctSupplyPolarity;
	}

	public void setCorrectSupplyPolarity(String correctSupplyPolarity) {
		this.correctSupplyPolarity = correctSupplyPolarity;
	}

	public String getNumOutputCircuitsUse() {
		return numOutputCircuitsUse;
	}

	public void setNumOutputCircuitsUse(String numOutputCircuitsUse) {
		this.numOutputCircuitsUse = numOutputCircuitsUse;
	}

	public String getRatingsAmps() {
		return ratingsAmps;
	}

	public void setRatingsAmps(String ratingsAmps) {
		this.ratingsAmps = ratingsAmps;
	}

	public String getNumOutputCircuitsSpare() {
		return numOutputCircuitsSpare;
	}

	public void setNumOutputCircuitsSpare(String numOutputCircuitsSpare) {
		this.numOutputCircuitsSpare = numOutputCircuitsSpare;
	}

	public String getInstalledEquipmentVulnarable() {
		return installedEquipmentVulnarable;
	}

	public void setInstalledEquipmentVulnarable(String installedEquipmentVulnarable) {
		this.installedEquipmentVulnarable = installedEquipmentVulnarable;
	}

	public TestDistRecords getTestDistRecords() {
		return testDistRecords;
	}

	public void setTestDistRecords(TestDistRecords testDistRecords) {
		this.testDistRecords = testDistRecords;
	}

}
