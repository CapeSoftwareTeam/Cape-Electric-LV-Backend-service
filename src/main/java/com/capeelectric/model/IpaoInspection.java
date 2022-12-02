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
@Table(name = "ipao_inspection_table")
public class IpaoInspection implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IPAO_INSPECTION_ID")
	private Integer ipaoInspectionId;

	@Column(name = "LOCATION_COUNT")
	private Integer locationCount;
	
	@Column(name = "LOCATION_NUMBER")
	private Integer locationNumber;

	@Column(name = "LOCATION_NAME")
	private String locationName;

	@Column(name = "I_SERVICE_CABLE")
	private String serviceCable;

	@Column(name = "I_SERVICE_FUSE")
	private String serviceFuse;
	
	@Column(name = "ELECTRIC_SHOCK")
	private String electricShock;
	
	@Column(name = "BASIC_PROTECTION")
	private String basicProtection;
	
	@Column(name = "FAULT_PROTECTION")
	private String faultProtection;
	
	@Column(name = "LIMITED_VALUE")
	private String limitedValue;
	
	@Column(name = "PROTECTIVE_MEASURE")
	private String protectiveMeasure;
	
	@Column(name = "ADDITIONAL_MEASURE")
	private String additionalMeasure;
	
	@Column(name = "ADDITIONAL_PROTECTION")
	private String additionalProtection;
	
	@Column(name = "FAULT_PROTECTION_DESC")
	private String faultProtectionDesc;
	
	@Column(name = "I_METER_DISTRIBUTOR")
	private String meterDistributor;

	@Column(name = "I_METER_CONSUMER")
	private String meterConsumer;

	@Column(name = "I_METER_EQU")
	private String meterEqu;

	@Column(name = "I_TOV_MEASURES_LV_HV")
	private String tovMeasuresLVHV;

	@Column(name = "I_ISOLATOR")
	private String isolator;

	@Column(name = "P_EARTHING_ARRANGEMENT")
	private String earthingArrangement;

	@Column(name = "P_ADEQUATE_ARRANGEMENT")
	private String adequateArrangement;

	@Column(name = "P_CONNECTION_GENERATOR")
	private String connectionGenerator;

	@Column(name = "P_COMPATIBILITY_CHARACTERISTICS")
	private String compatibilityCharacteristics;

	@Column(name = "P_AUTOMATIC_DISCONNECT_GENERATOR")
	private String automaticDisconnectGenerator;

	@Column(name = "P_PREVENT_CONNECT_GENERATOR")
	private String preventConnectGenerator;

	@Column(name = "P_ISOLATE_GENERATOR")
	private String isolateGenerator;

	@Column(name = "A_MAIN_EARTHING")
	private String mainEarting;

	@Column(name = "A_EARTH_ELECTORDE_ARRANGEMENT")
	private String earthElectordeArrangement;

	@Column(name = "A_EARTH_CONDUCTOR_CONNECTION")
	private String earthConductorConnection;

	@Column(name = "A_ACCESSIBILITY")
	private String accessibility;

	@Column(name = "A_MAIN_PROTECT_BONDING")
	private String aainProtectBonding;

	@Column(name = "A_ALL_PROTECT_BONDING")
	private String allProtectBonding;

	@Column(name = "A_ALL_APPROPRIATE_LOCATION")
	private String allAppropriateLocation;

	@Column(name = "A_FELV_REQUIREMENT")
	private String felvRequirement;

	@Column(name = "O_SELV_SYSTEM")
	private String selvSystem;

	@Column(name = "O_PELV_SYSTEM")
	private String pelvSystem;

	@Column(name = "O_DOUBLE_INSULATION")
	private String doubleInsulation;

	@Column(name = "O_REINFORCED_INSULATION")
	private String reinforcedInsulation;

	@Column(name = "O_BASIC_ELECTRICAL_SEPARTION")
	private String basicElectricalSepartion;

	@Column(name = "O_ISOLATE_PUBLIC_SUPPLY")
	private String isolatePublicSupply;

	@Column(name = "O_INSULATION_LIVE_PARTS")
	private String insulationLiveParts;

	@Column(name = "O_BARRIERS_ENCLOSERS")
	private String barriersEnclosers;

	@Column(name = "O_OBSTACLES")
	private String obstacles;

	@Column(name = "O_PLACING_OUT_REACH")
	private String placingOutReach;

	@Column(name = "O_FAULT_NON_CONDUCT_LOCATION")
	private String faultNonConductLocation;

	@Column(name = "O_FAULT_ELECTRICAL_SEPARTION")
	private String faultElectricalSepartion;

	@Column(name = "O_OPERATING_CURRENT")
	private String operatingCurrent;

	@Column(name = "O_SUPPLEMENTARY_BONDING")
	private String supplementaryBonding;
	
	@Column(name = "O_SPECIFIC_INSPECTION_RE")
	private String specificInspectionRe;

	@Column(name = "INSPECTION_FLAG")
	private String inspectionFlag;

	@JsonManagedReference
	@OneToMany(mappedBy = "ipaoInspection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ConsumerUnit> consumerUnit;

	@JsonManagedReference
	@OneToMany(mappedBy = "ipaoInspection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Circuit> circuit;

	@JsonManagedReference
	@OneToMany(mappedBy = "ipaoInspection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<IsolationCurrent> isolationCurrent;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "PERIODIC_INSPECTION_ID")
	private PeriodicInspection periodicInspection;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "ipaoInspection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<InspectionOuterObservation> inspectionOuterObervation;

	public Integer getIpaoInspectionId() {
		return ipaoInspectionId;
	}

	public String getInspectionFlag() {
		return inspectionFlag;
	}

	public void setInspectionFlag(String inspectionFlag) {
		this.inspectionFlag = inspectionFlag;
	}
	
	public String getTovMeasuresLVHV() {
		return tovMeasuresLVHV;
	}

	public void setTovMeasuresLVHV(String tovMeasuresLVHV) {
		this.tovMeasuresLVHV = tovMeasuresLVHV;
	}

	public void setIpaoInspectionId(Integer ipaoInspectionId) {
		this.ipaoInspectionId = ipaoInspectionId;
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

	public String getServiceCable() {
		return serviceCable;
	}

	public void setServiceCable(String serviceCable) {
		this.serviceCable = serviceCable;
	}

	public String getServiceFuse() {
		return serviceFuse;
	}

	public void setServiceFuse(String serviceFuse) {
		this.serviceFuse = serviceFuse;
	}

	
	
	public String getElectricShock() {
		return electricShock;
	}

	public void setElectricShock(String electricShock) {
		this.electricShock = electricShock;
	}

	public String getBasicProtection() {
		return basicProtection;
	}

	public void setBasicProtection(String basicProtection) {
		this.basicProtection = basicProtection;
	}

	public String getFaultProtection() {
		return faultProtection;
	}

	public void setFaultProtection(String faultProtection) {
		this.faultProtection = faultProtection;
	}

	public String getLimitedValue() {
		return limitedValue;
	}

	public void setLimitedValue(String limitedValue) {
		this.limitedValue = limitedValue;
	}

	public String getProtectiveMeasure() {
		return protectiveMeasure;
	}

	public void setProtectiveMeasure(String protectiveMeasure) {
		this.protectiveMeasure = protectiveMeasure;
	}

	

	public String getAdditionalMeasure() {
		return additionalMeasure;
	}

	public void setAdditionalMeasure(String additionalMeasure) {
		this.additionalMeasure = additionalMeasure;
	}

	public String getAdditionalProtection() {
		return additionalProtection;
	}

	public void setAdditionalProtection(String additionalProtection) {
		this.additionalProtection = additionalProtection;
	}

	public String getFaultProtectionDesc() {
		return faultProtectionDesc;
	}

	public void setFaultProtectionDesc(String faultProtectionDesc) {
		this.faultProtectionDesc = faultProtectionDesc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMeterDistributor() {
		return meterDistributor;
	}

	public void setMeterDistributor(String meterDistributor) {
		this.meterDistributor = meterDistributor;
	}

	public String getMeterConsumer() {
		return meterConsumer;
	}

	public void setMeterConsumer(String meterConsumer) {
		this.meterConsumer = meterConsumer;
	}

	public String getMeterEqu() {
		return meterEqu;
	}

	public void setMeterEqu(String meterEqu) {
		this.meterEqu = meterEqu;
	}

	public String getIsolator() {
		return isolator;
	}

	public void setIsolator(String isolator) {
		this.isolator = isolator;
	}

	public String getEarthingArrangement() {
		return earthingArrangement;
	}

	public void setEarthingArrangement(String earthingArrangement) {
		this.earthingArrangement = earthingArrangement;
	}

	public String getAdequateArrangement() {
		return adequateArrangement;
	}

	public void setAdequateArrangement(String adequateArrangement) {
		this.adequateArrangement = adequateArrangement;
	}

	public String getConnectionGenerator() {
		return connectionGenerator;
	}

	public void setConnectionGenerator(String connectionGenerator) {
		this.connectionGenerator = connectionGenerator;
	}

	public String getCompatibilityCharacteristics() {
		return compatibilityCharacteristics;
	}

	public void setCompatibilityCharacteristics(String compatibilityCharacteristics) {
		this.compatibilityCharacteristics = compatibilityCharacteristics;
	}

	public String getAutomaticDisconnectGenerator() {
		return automaticDisconnectGenerator;
	}

	public void setAutomaticDisconnectGenerator(String automaticDisconnectGenerator) {
		this.automaticDisconnectGenerator = automaticDisconnectGenerator;
	}

	public String getPreventConnectGenerator() {
		return preventConnectGenerator;
	}

	public void setPreventConnectGenerator(String preventConnectGenerator) {
		this.preventConnectGenerator = preventConnectGenerator;
	}

	public String getIsolateGenerator() {
		return isolateGenerator;
	}

	public void setIsolateGenerator(String isolateGenerator) {
		this.isolateGenerator = isolateGenerator;
	}

	public String getMainEarting() {
		return mainEarting;
	}

	public void setMainEarting(String mainEarting) {
		this.mainEarting = mainEarting;
	}

	public String getEarthElectordeArrangement() {
		return earthElectordeArrangement;
	}

	public void setEarthElectordeArrangement(String earthElectordeArrangement) {
		this.earthElectordeArrangement = earthElectordeArrangement;
	}

	public String getEarthConductorConnection() {
		return earthConductorConnection;
	}

	public void setEarthConductorConnection(String earthConductorConnection) {
		this.earthConductorConnection = earthConductorConnection;
	}

	public String getAccessibility() {
		return accessibility;
	}

	public void setAccessibility(String accessibility) {
		this.accessibility = accessibility;
	}

	public String getAainProtectBonding() {
		return aainProtectBonding;
	}

	public void setAainProtectBonding(String aainProtectBonding) {
		this.aainProtectBonding = aainProtectBonding;
	}

	public String getAllProtectBonding() {
		return allProtectBonding;
	}

	public void setAllProtectBonding(String allProtectBonding) {
		this.allProtectBonding = allProtectBonding;
	}

	public String getAllAppropriateLocation() {
		return allAppropriateLocation;
	}

	public void setAllAppropriateLocation(String allAppropriateLocation) {
		this.allAppropriateLocation = allAppropriateLocation;
	}

	public String getFelvRequirement() {
		return felvRequirement;
	}

	public void setFelvRequirement(String felvRequirement) {
		this.felvRequirement = felvRequirement;
	}

	public String getSelvSystem() {
		return selvSystem;
	}

	public void setSelvSystem(String selvSystem) {
		this.selvSystem = selvSystem;
	}

	public String getPelvSystem() {
		return pelvSystem;
	}

	public void setPelvSystem(String pelvSystem) {
		this.pelvSystem = pelvSystem;
	}

	public String getDoubleInsulation() {
		return doubleInsulation;
	}

	public void setDoubleInsulation(String doubleInsulation) {
		this.doubleInsulation = doubleInsulation;
	}

	public String getReinforcedInsulation() {
		return reinforcedInsulation;
	}

	public void setReinforcedInsulation(String reinforcedInsulation) {
		this.reinforcedInsulation = reinforcedInsulation;
	}

	public String getBasicElectricalSepartion() {
		return basicElectricalSepartion;
	}

	public void setBasicElectricalSepartion(String basicElectricalSepartion) {
		this.basicElectricalSepartion = basicElectricalSepartion;
	}

	public String getIsolatePublicSupply() {
		return isolatePublicSupply;
	}

	public void setIsolatePublicSupply(String isolatePublicSupply) {
		this.isolatePublicSupply = isolatePublicSupply;
	}

	public String getInsulationLiveParts() {
		return insulationLiveParts;
	}

	public void setInsulationLiveParts(String insulationLiveParts) {
		this.insulationLiveParts = insulationLiveParts;
	}

	public String getBarriersEnclosers() {
		return barriersEnclosers;
	}

	public void setBarriersEnclosers(String barriersEnclosers) {
		this.barriersEnclosers = barriersEnclosers;
	}

	public String getObstacles() {
		return obstacles;
	}

	public void setObstacles(String obstacles) {
		this.obstacles = obstacles;
	}

	public String getPlacingOutReach() {
		return placingOutReach;
	}

	public void setPlacingOutReach(String placingOutReach) {
		this.placingOutReach = placingOutReach;
	}

	public String getFaultElectricalSepartion() {
		return faultElectricalSepartion;
	}

	public void setFaultElectricalSepartion(String faultElectricalSepartion) {
		this.faultElectricalSepartion = faultElectricalSepartion;
	}

	public String getFaultNonConductLocation() {
		return faultNonConductLocation;
	}

	public void setFaultNonConductLocation(String faultNonConductLocation) {
		this.faultNonConductLocation = faultNonConductLocation;
	}

	public String getOperatingCurrent() {
		return operatingCurrent;
	}

	public void setOperatingCurrent(String operatingCurrent) {
		this.operatingCurrent = operatingCurrent;
	}

	public String getSupplementaryBonding() {
		return supplementaryBonding;
	}

	public void setSupplementaryBonding(String supplementaryBonding) {
		this.supplementaryBonding = supplementaryBonding;
	}

	public List<ConsumerUnit> getConsumerUnit() {
		return consumerUnit;
	}

	public void setConsumerUnit(List<ConsumerUnit> consumerUnit) {
		this.consumerUnit = consumerUnit;
	}

	public List<Circuit> getCircuit() {
		return circuit;
	}

	public void setCircuit(List<Circuit> circuit) {
		this.circuit = circuit;
	}

	public List<IsolationCurrent> getIsolationCurrent() {
		return isolationCurrent;
	}

	public void setIsolationCurrent(List<IsolationCurrent> isolationCurrent) {
		this.isolationCurrent = isolationCurrent;
	}

	public PeriodicInspection getPeriodicInspection() {
		return periodicInspection;
	}

	public void setPeriodicInspection(PeriodicInspection periodicInspection) {
		this.periodicInspection = periodicInspection;
	}

	public Integer getLocationCount() {
		return locationCount;
	}

	public void setLocationCount(Integer locationCount) {
		this.locationCount = locationCount;
	}

	public String getSpecificInspectionRe() {
		return specificInspectionRe;
	}

	public void setSpecificInspectionRe(String specificInspectionRe) {
		this.specificInspectionRe = specificInspectionRe;
	}

	public List<InspectionOuterObservation> getInspectionOuterObervation() {
		return inspectionOuterObervation;
	}

	public void setInspectionOuterObervation(List<InspectionOuterObservation> inspectionOuterObervation) {
		this.inspectionOuterObervation = inspectionOuterObervation;
	}

}
