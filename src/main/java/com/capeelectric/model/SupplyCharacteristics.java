package com.capeelectric.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 *
 * @author capeelectricsoftware
 *
 */
@Entity
@Table(name = "SUPPLY_CHARACTERISTICS_TABLE")
@NamedQueries(value = {
		@NamedQuery(name = "SupplyCharacteristicsRepository.findByUserNameAndSiteId", query = "select s from SupplyCharacteristics s where s.userName=:userName and s.siteId=:siteId"),
		@NamedQuery(name = "SupplyCharacteristicsRepository.findBySiteId", query = "select s from SupplyCharacteristics s where s.siteId=:siteId")

})

public class SupplyCharacteristics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SUPPLY_CHARACTERISTICS_ID")
	private Integer supplyCharacteristicsId;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "SITE_ID")
	private Integer siteId;

	@Column(name = "SHORT_NAME")
	private String shortName;

	@Column(name = "MAIN_SYSTEM_EARTING")
	private String mainSystemEarthing;

	@Column(name = "SYSTEM_EARTH_BNOTE")
	private String systemEarthingBNote;

	@Column(name = "LIVE_CONDUCTOR_TYPE")
	private String liveConductorType;

	@Column(name = "LIVE_CONDUCTOR_AC")
	private String liveConductorAC;

	@Column(name = "LIVE_CONDUCTOR_DC")
	private String liveConductorDC;

	@Column(name = "LIVE_CONDUCTOR_BNOTE")
	private String liveConductorBNote;

	@Column(name = "MAIN_NOMINAL_VOLTAGE")
	private String mainNominalVoltage;

	@Column(name = "MAIN_NOMINAL_CAPACITY")
	private String mainNominalCapacity;

	@Column(name = "MAIN_NOMINAL_FREQUENCY")
	private String mainNominalFrequency;

	@Column(name = "MAIN_ACTUALLOAD")
	private String mainActualLoad;

	@Column(name = "MAIN_FAULT_CURRENT")
	private String mainNominalCurrent;

	@Column(name = "MAIN_LOOP_IMPEDANCE")
	private String mainLoopImpedance;

	@Column(name = "MAIN_CURRENT_PROTECTIVE_DEVICE")
	private String mainNominalProtectiveDevice;

	@Column(name = "MAIN_RATED_CURRENT")
	private String mainRatedCurrent;

	@Column(name = "MAIN_CURRENT_DISCONNECTION")
	private String mainCurrentDisconnection;

	@Column(name = "AL_AVILABLE_SUPPLY")
	private String alternativeSupply;

	@Column(name = "AL_NUM_SUPPLY")
	private String supplyNumber;

	@Column(name = "MAXIMUM_DEMAND")
	private String maximumDemand;

	@Column(name = "MAXIMUM_LOAD")
	private String maximumLoad;

	@Column(name = "MEANS_EARTHING")
	private String meansEarthing;

	@Column(name = "EARTHING_REMARKS")
	private String meansEarthingRemark;

	@Column(name = "ELECTORDE_TYPE")
	private String electrodeType;

	@Column(name = "ELECTORDE_MATERIAL")
	private String electrodeMaterial;

	@Column(name = "NUM_LOCATION")
	private Integer noOfLocation;

	@Column(name = "INSTALLOCATION_REPORT_OB")
	private Integer instalLocationReportOb;

	@Column(name = "CONDUCTOR_SIZE")
	private String conductorSize;

	@Column(name = "CONDUCTOR_MATERIAL")
	private String conductormaterial;

	@Column(name = "CONDUCTOR_VERIFY")
	private String conductorVerify;

	@Column(name = "BONDING_CONDUCTOR_SIZE")
	private String bondingConductorSize;

	@Column(name = "BONDING_CONDUCTOR_MATERIAL")
	private String bondingConductorMaterial;

	@Column(name = "BONDING_CONDUCTOR_VERIFY")
	private String bondingConductorVerify;

	@Column(name = "BONDING_JOINTS_TYPE")
	private String bondingJointsType;

	@Column(name = "BONDING_NO_OF_JOINTS")
	private Integer bondingNoOfJoints;

	@Column(name = "BONDING_NO_OF_JOINTS_OB")
	private Integer bondingNoOfJointsOb;

	@Column(name = "EARTHING_CONDUCTOR_SIZE")
	private String earthingConductorSize;

	@Column(name = "EARTHING_CONDUCTOR_MATERIAL")
	private String earthingConductorMaterial;

	@Column(name = "EARTHING_CONDUCTOR_VERIFY")
	private String earthingConductorVerify;

	@Column(name = "EARTHING_JOINTS_TYPE")
	private String earthingJointsType;

	@Column(name = "EARTHING_NO_OF_JOINTS")
	private Integer earthingNoOfJoints;

	@Column(name = "EARTHING_NO_OF_JOINTS_OB")
	private Integer earthingNoOfJointsOb;
	
	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATED_DATE")
	private LocalDateTime updatedDate;

	@JsonManagedReference
	@OneToMany(mappedBy = "supplyCharacteristics", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SupplyParameters> supplyParameters;

	@JsonManagedReference
	@OneToMany(mappedBy = "supplyCharacteristics", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CircuitBreaker> circuitBreaker;

	@JsonManagedReference
	@OneToMany(mappedBy = "supplyCharacteristics", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<InstalLocationReport> instalLocationReport;

	@JsonManagedReference
	@OneToMany(mappedBy = "supplyCharacteristics", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<BoundingLocationReport> boundingLocationReport;

	@JsonManagedReference
	@OneToMany(mappedBy = "supplyCharacteristics", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<EarthingLocationReport> earthingLocationReport;

	@JsonManagedReference
	@OneToMany(mappedBy = "supplyCharacteristics", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SupplyCharacteristicComment> supplyCharacteristicComment;

	@JsonManagedReference
	@OneToMany(mappedBy = "supplyCharacteristics", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SupplyOuterObservation> supplyOuterObservation;

	public Integer getSupplyCharacteristicsId() {
		return supplyCharacteristicsId;
	}

	public void setSupplyCharacteristicsId(Integer supplyCharacteristicsId) {
		this.supplyCharacteristicsId = supplyCharacteristicsId;
	}
	
	public String getUserName() {
		return userName;
	}

	public String getMainNominalCapacity() {
		return mainNominalCapacity;
	}

	public void setMainNominalCapacity(String mainNominalCapacity) {
		this.mainNominalCapacity = mainNominalCapacity;
	}

	public String getMainNominalFrequency() {
		return mainNominalFrequency;
	}

	public void setMainNominalFrequency(String mainNominalFrequency) {
		this.mainNominalFrequency = mainNominalFrequency;
	}

	public String getMainActualLoad() {
		return mainActualLoad;
	}

	public void setMainActualLoad(String mainActualLoad) {
		this.mainActualLoad = mainActualLoad;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getMainSystemEarthing() {
		return mainSystemEarthing;
	}

	public void setMainSystemEarthing(String mainSystemEarthing) {
		this.mainSystemEarthing = mainSystemEarthing;
	}

	public String getSystemEarthingBNote() {
		return systemEarthingBNote;
	}

	public void setSystemEarthingBNote(String systemEarthingBNote) {
		this.systemEarthingBNote = systemEarthingBNote;
	}

	public String getLiveConductorType() {
		return liveConductorType;
	}

	public void setLiveConductorType(String liveConductorType) {
		this.liveConductorType = liveConductorType;
	}

	public String getLiveConductorAC() {
		return liveConductorAC;
	}

	public void setLiveConductorAC(String liveConductorAC) {
		this.liveConductorAC = liveConductorAC;
	}

	public String getLiveConductorDC() {
		return liveConductorDC;
	}

	public void setLiveConductorDC(String liveConductorDC) {
		this.liveConductorDC = liveConductorDC;
	}

	public String getLiveConductorBNote() {
		return liveConductorBNote;
	}

	public void setLiveConductorBNote(String liveConductorBNote) {
		this.liveConductorBNote = liveConductorBNote;
	}

	public String getMainNominalVoltage() {
		return mainNominalVoltage;
	}

	public void setMainNominalVoltage(String mainNominalVoltage) {
		this.mainNominalVoltage = mainNominalVoltage;
	}

	public String getMainNominalCurrent() {
		return mainNominalCurrent;
	}

	public void setMainNominalCurrent(String mainNominalCurrent) {
		this.mainNominalCurrent = mainNominalCurrent;
	}

	public String getMainLoopImpedance() {
		return mainLoopImpedance;
	}

	public void setMainLoopImpedance(String mainLoopImpedance) {
		this.mainLoopImpedance = mainLoopImpedance;
	}

	public String getMainNominalProtectiveDevice() {
		return mainNominalProtectiveDevice;
	}

	public void setMainNominalProtectiveDevice(String mainNominalProtectiveDevice) {
		this.mainNominalProtectiveDevice = mainNominalProtectiveDevice;
	}

	public String getMainRatedCurrent() {
		return mainRatedCurrent;
	}

	public void setMainRatedCurrent(String mainRatedCurrent) {
		this.mainRatedCurrent = mainRatedCurrent;
	}

	public String getMainCurrentDisconnection() {
		return mainCurrentDisconnection;
	}

	public void setMainCurrentDisconnection(String mainCurrentDisconnection) {
		this.mainCurrentDisconnection = mainCurrentDisconnection;
	}

	public String getAlternativeSupply() {
		return alternativeSupply;
	}

	public void setAlternativeSupply(String alternativeSupply) {
		this.alternativeSupply = alternativeSupply;
	}

	public String getSupplyNumber() {
		return supplyNumber;
	}

	public void setSupplyNumber(String supplyNumber) {
		this.supplyNumber = supplyNumber;
	}

	public String getMaximumDemand() {
		return maximumDemand;
	}

	public void setMaximumDemand(String maximumDemand) {
		this.maximumDemand = maximumDemand;
	}

	public String getMaximumLoad() {
		return maximumLoad;
	}

	public void setMaximumLoad(String maximumLoad) {
		this.maximumLoad = maximumLoad;
	}

	public String getMeansEarthing() {
		return meansEarthing;
	}

	public void setMeansEarthing(String meansEarthing) {
		this.meansEarthing = meansEarthing;
	}

	public String getElectrodeType() {
		return electrodeType;
	}

	public void setElectrodeType(String electrodeType) {
		this.electrodeType = electrodeType;
	}

	public String getElectrodeMaterial() {
		return electrodeMaterial;
	}

	public void setElectrodeMaterial(String electrodeMaterial) {
		this.electrodeMaterial = electrodeMaterial;
	}

	public Integer getNoOfLocation() {
		return noOfLocation;
	}

	public void setNoOfLocation(Integer noOfLocation) {
		this.noOfLocation = noOfLocation;
	}

	public String getConductorSize() {
		return conductorSize;
	}

	public void setConductorSize(String conductorSize) {
		this.conductorSize = conductorSize;
	}

	public String getConductormaterial() {
		return conductormaterial;
	}

	public void setConductormaterial(String conductormaterial) {
		this.conductormaterial = conductormaterial;
	}

	public String getConductorVerify() {
		return conductorVerify;
	}

	public void setConductorVerify(String conductorVerify) {
		this.conductorVerify = conductorVerify;
	}

	public String getBondingConductorSize() {
		return bondingConductorSize;
	}

	public void setBondingConductorSize(String bondingConductorSize) {
		this.bondingConductorSize = bondingConductorSize;
	}

	public String getBondingConductorMaterial() {
		return bondingConductorMaterial;
	}

	public void setBondingConductorMaterial(String bondingConductorMaterial) {
		this.bondingConductorMaterial = bondingConductorMaterial;
	}

	public String getBondingConductorVerify() {
		return bondingConductorVerify;
	}

	public void setBondingConductorVerify(String bondingConductorVerify) {
		this.bondingConductorVerify = bondingConductorVerify;
	}

	public String getBondingJointsType() {
		return bondingJointsType;
	}

	public void setBondingJointsType(String bondingJointsType) {
		this.bondingJointsType = bondingJointsType;
	}

	public Integer getBondingNoOfJoints() {
		return bondingNoOfJoints;
	}

	public void setBondingNoOfJoints(Integer bondingNoOfJoints) {
		this.bondingNoOfJoints = bondingNoOfJoints;
	}

	public String getEarthingConductorSize() {
		return earthingConductorSize;
	}

	public void setEarthingConductorSize(String earthingConductorSize) {
		this.earthingConductorSize = earthingConductorSize;
	}

	public String getEarthingConductorMaterial() {
		return earthingConductorMaterial;
	}

	public void setEarthingConductorMaterial(String earthingConductorMaterial) {
		this.earthingConductorMaterial = earthingConductorMaterial;
	}

	public String getEarthingConductorVerify() {
		return earthingConductorVerify;
	}

	public void setEarthingConductorVerify(String earthingConductorVerify) {
		this.earthingConductorVerify = earthingConductorVerify;
	}

	public String getEarthingJointsType() {
		return earthingJointsType;
	}

	public void setEarthingJointsType(String earthingJointsType) {
		this.earthingJointsType = earthingJointsType;
	}

	public Integer getEarthingNoOfJoints() {
		return earthingNoOfJoints;
	}

	public void setEarthingNoOfJoints(Integer earthingNoOfJoints) {
		this.earthingNoOfJoints = earthingNoOfJoints;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<SupplyParameters> getSupplyParameters() {
		return supplyParameters;
	}

	public void setSupplyParameters(List<SupplyParameters> supplyParameters) {
		this.supplyParameters = supplyParameters;
	}

	public List<CircuitBreaker> getCircuitBreaker() {
		return circuitBreaker;
	}

	public void setCircuitBreaker(List<CircuitBreaker> circuitBreaker) {
		this.circuitBreaker = circuitBreaker;
	}

	public List<InstalLocationReport> getInstalLocationReport() {
		return instalLocationReport;
	}

	public void setInstalLocationReport(List<InstalLocationReport> instalLocationReport) {
		this.instalLocationReport = instalLocationReport;
	}

	public List<BoundingLocationReport> getBoundingLocationReport() {
		return boundingLocationReport;
	}

	public void setBoundingLocationReport(List<BoundingLocationReport> boundingLocationReport) {
		this.boundingLocationReport = boundingLocationReport;
	}

	public List<EarthingLocationReport> getEarthingLocationReport() {
		return earthingLocationReport;
	}

	public void setEarthingLocationReport(List<EarthingLocationReport> earthingLocationReport) {
		this.earthingLocationReport = earthingLocationReport;
	}

	public List<SupplyCharacteristicComment> getSupplyCharacteristicComment() {
		return supplyCharacteristicComment;
	}

	public void setSupplyCharacteristicComment(List<SupplyCharacteristicComment> supplyCharacteristicComment) {
		this.supplyCharacteristicComment = supplyCharacteristicComment;
	}

	public Integer getInstalLocationReportOb() {
		return instalLocationReportOb;
	}

	public void setInstalLocationReportOb(Integer instalLocationReportOb) {
		this.instalLocationReportOb = instalLocationReportOb;
	}

	public Integer getBondingNoOfJointsOb() {
		return bondingNoOfJointsOb;
	}

	public void setBondingNoOfJointsOb(Integer bondingNoOfJointsOb) {
		this.bondingNoOfJointsOb = bondingNoOfJointsOb;
	}

	public Integer getEarthingNoOfJointsOb() {
		return earthingNoOfJointsOb;
	}

	public void setEarthingNoOfJointsOb(Integer earthingNoOfJointsOb) {
		this.earthingNoOfJointsOb = earthingNoOfJointsOb;
	}

	public List<SupplyOuterObservation> getSupplyOuterObservation() {
		return supplyOuterObservation;
	}

	public void setSupplyOuterObservation(List<SupplyOuterObservation> supplyOuterObservation) {
		this.supplyOuterObservation = supplyOuterObservation;
	}

	public String getMeansEarthingRemark() {
		return meansEarthingRemark;
	}

	public void setMeansEarthingRemark(String meansEarthingRemark) {
		this.meansEarthingRemark = meansEarthingRemark;
	}

}
