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
@Table(name = "supply_parameters_table")
public class SupplyParameters implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SUPPLY_PARAMETERS_ID")
	private Integer supplyparametersId;

	@Column(name = "AL_SYSTEM_EARTING")
	private String aLSystemEarthing;

	@Column(name = "AL_SUPPLY_NO")
	private String aLSupplyNo;

	@Column(name = "AL_SUPPLY_STNAME")
	private String aLSupplyShortName;

	@Column(name = "AL_SYSTEM_EARTING_BNOTE")
	private String aLSystemEarthingBNote;

	@Column(name = "AL_LIVECONDUCTOR_TYPE")
	private String aLLiveConductorType;

	@Column(name = "AL_LIVECONDUCTOR_AC")
	private String aLLiveConductorAC;

	@Column(name = "AL_LIVECONDUCTOR_DC")
	private String aLLiveConductorDC;

	@Column(name = "AL_LIVECONDUCTOR_BNOTE")
	private String aLLiveConductorBNote;

	@Column(name = "AL_NOMINAL_VOLTAGE")
	private String nominalVoltage;

	@Column(name = "AL_NOMINAL_FREQUENCY")
	private String nominalFrequency;

	@Column(name = "AL_FAULT_CURRENT")
	private String faultCurrent;

	@Column(name = "AL_LOOP_IMPEDANCE")
	private String loopImpedance;

	@Column(name = "AL_INSTALLED_CAPACITY")
	private String installedCapacity;

	@Column(name = "AL_ACTUAL_LOAD")
	private String actualLoad;

	@Column(name = "AL_PROTECTIVE_DEVICE")
	private String protectiveDevice;
	
	@Column(name = "AL_RATED_CURRNT")
	private String ratedCurrent;

	@Column(name = "AL_CURRENT_DISCONNECTION")
	private String currentDissconnection;
	
	@Column(name = "SUPPLY_PARAMETER_STATUS")
	private String supplyParameterStatus;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "SUPPLY_CHARACTERISTICS_ID")
	private SupplyCharacteristics supplyCharacteristics;

	public Integer getSupplyparametersId() {
		return supplyparametersId;
	}

	public void setSupplyparametersId(Integer supplyparametersId) {
		this.supplyparametersId = supplyparametersId;
	}

	public String getaLSystemEarthing() {
		return aLSystemEarthing;
	}

	public void setaLSystemEarthing(String aLSystemEarthing) {
		this.aLSystemEarthing = aLSystemEarthing;
	}

	public String getaLSupplyNo() {
		return aLSupplyNo;
	}

	public void setaLSupplyNo(String aLSupplyNo) {
		this.aLSupplyNo = aLSupplyNo;
	}

	public String getaLSupplyShortName() {
		return aLSupplyShortName;
	}

	public void setaLSupplyShortName(String aLSupplyShortName) {
		this.aLSupplyShortName = aLSupplyShortName;
	}

	public String getaLSystemEarthingBNote() {
		return aLSystemEarthingBNote;
	}

	public void setaLSystemEarthingBNote(String aLSystemEarthingBNote) {
		this.aLSystemEarthingBNote = aLSystemEarthingBNote;
	}

	public String getaLLiveConductorType() {
		return aLLiveConductorType;
	}

	public void setaLLiveConductorType(String aLLiveConductorType) {
		this.aLLiveConductorType = aLLiveConductorType;
	}

	public String getaLLiveConductorAC() {
		return aLLiveConductorAC;
	}

	public void setaLLiveConductorAC(String aLLiveConductorAC) {
		this.aLLiveConductorAC = aLLiveConductorAC;
	}

	public String getaLLiveConductorDC() {
		return aLLiveConductorDC;
	}

	public void setaLLiveConductorDC(String aLLiveConductorDC) {
		this.aLLiveConductorDC = aLLiveConductorDC;
	}

	public String getaLLiveConductorBNote() {
		return aLLiveConductorBNote;
	}

	public void setaLLiveConductorBNote(String aLLiveConductorBNote) {
		this.aLLiveConductorBNote = aLLiveConductorBNote;
	}

	public String getNominalVoltage() {
		return nominalVoltage;
	}

	public void setNominalVoltage(String nominalVoltage) {
		this.nominalVoltage = nominalVoltage;
	}

	public String getNominalFrequency() {
		return nominalFrequency;
	}

	public void setNominalFrequency(String nominalFrequency) {
		this.nominalFrequency = nominalFrequency;
	}

	public String getFaultCurrent() {
		return faultCurrent;
	}

	public void setFaultCurrent(String faultCurrent) {
		this.faultCurrent = faultCurrent;
	}

	public String getLoopImpedance() {
		return loopImpedance;
	}

	public void setLoopImpedance(String loopImpedance) {
		this.loopImpedance = loopImpedance;
	}

	public String getInstalledCapacity() {
		return installedCapacity;
	}

	public void setInstalledCapacity(String installedCapacity) {
		this.installedCapacity = installedCapacity;
	}

	public String getActualLoad() {
		return actualLoad;
	}

	public void setActualLoad(String actualLoad) {
		this.actualLoad = actualLoad;
	}

	public String getProtectiveDevice() {
		return protectiveDevice;
	}

	public void setProtectiveDevice(String protectiveDevice) {
		this.protectiveDevice = protectiveDevice;
	}

	public String getRatedCurrent() {
		return ratedCurrent;
	}

	public void setRatedCurrent(String ratedCurrent) {
		this.ratedCurrent = ratedCurrent;
	}

	public String getCurrentDissconnection() {
		return currentDissconnection;
	}

	public void setCurrentDissconnection(String currentDissconnection) {
		this.currentDissconnection = currentDissconnection;
	}

	public SupplyCharacteristics getSupplyCharacteristics() {
		return supplyCharacteristics;
	}

	public void setSupplyCharacteristics(SupplyCharacteristics supplyCharacteristics) {
		this.supplyCharacteristics = supplyCharacteristics;
	}

	public String getSupplyParameterStatus() {
		return supplyParameterStatus;
	}

	public void setSupplyParameterStatus(String supplyParameterStatus) {
		this.supplyParameterStatus = supplyParameterStatus;
	}
	
}
