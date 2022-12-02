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
@Table(name = "TESTING_INCOMING_DISTRIBUTION_TABLE")
public class TestIncomingDistribution implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INCOMING_DISTRIBUTION_ID")
	private Integer incomingDistributionId;

	@Column(name = "INCOMING_VOLTAGE")
	private String incomingVoltage;

	@Column(name = "INCOMING_LOOP_IMPEDANCE")
	private String incomingLoopImpedance;

	@Column(name = "INCOMING_FAULTCURRENT")
	private String incomingFaultCurrent;

	@Column(name = "INCOMING_ACTUALLOAD")
	private String incomingActualLoad;

	@Column(name = "SOURCE_FROM_SUPPLY")
	private String sourceFromSupply;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "TESTING_REPORT_ID")
	private TestingReport testingReport;

	public Integer getIncomingDistributionId() {
		return incomingDistributionId;
	}

	public void setIncomingDistributionId(Integer incomingDistributionId) {
		this.incomingDistributionId = incomingDistributionId;
	}

	public String getIncomingVoltage() {
		return incomingVoltage;
	}

	public void setIncomingVoltage(String incomingVoltage) {
		this.incomingVoltage = incomingVoltage;
	}

	public String getIncomingFaultCurrent() {
		return incomingFaultCurrent;
	}

	public void setIncomingFaultCurrent(String incomingFaultCurrent) {
		this.incomingFaultCurrent = incomingFaultCurrent;
	}

	public String getSourceFromSupply() {
		return sourceFromSupply;
	}

	public void setSourceFromSupply(String sourceFromSupply) {
		this.sourceFromSupply = sourceFromSupply;
	}

	public TestingReport getTestingReport() {
		return testingReport;
	}

	public void setTestingReport(TestingReport testingReport) {
		this.testingReport = testingReport;
	}

	public String getIncomingActualLoad() {
		return incomingActualLoad;
	}

	public void setIncomingActualLoad(String incomingActualLoad) {
		this.incomingActualLoad = incomingActualLoad;
	}

	public String getIncomingLoopImpedance() {
		return incomingLoopImpedance;
	}

	public void setIncomingLoopImpedance(String incomingLoopImpedance) {
		this.incomingLoopImpedance = incomingLoopImpedance;
	}

}
