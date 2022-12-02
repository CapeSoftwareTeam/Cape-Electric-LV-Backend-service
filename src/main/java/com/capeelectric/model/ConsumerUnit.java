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
@Table(name = "consumer_table")
public class ConsumerUnit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CONSUMER_ID")
	private Integer consumerId;
	
	@Column(name = "DISTRIBUTIONBOARD_DETAILS")
	private String distributionBoardDetails;
	
	@Column(name = "REFERANCE")
	private String referance;
	
	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "ACCESS_AND_WORKING")
	private String accessWorking;
	
	@Column(name = "SECURITY_OF_FIXING")
	private String securityFixing;
	
	@Column(name = "LIVE_PARTS_NOT_DAMAGE")
	private String livePartsDamage;
	
	@Column(name = "SECURITY_OF_BARRIERS")
	private String securityBarriers;
	
	@Column(name = "SUITABILITY_OF_ENCLOSURE")
	private String suitabilityEnclosure;
	
	@Column(name = "ENCLOSURE_NOT_DAMAGED")
	private String enclosureDamaged;
	
	@Column(name = "PRESENCE_OF_OBSTACLES")
	private String presenceObstacles;
	
	@Column(name = "PLACING_OUT_OF_CONSUMER")
	private String placingOutOfConsumer;
	
	@Column(name = "PRESENCE_MAIN_SWITCHES")
	private String presenceMainSwitches;
	
	@Column(name = "OPERATION_MAIN_SWITCHES")
	private String operationMainSwitches;
	
	@Column(name = "MANUAL_CIRCUIT_BREAKERS")
	private String manualCircuitBreakers;
	
	@Column(name = "SWITCH_CAUSES_RCD")
	private String switchCausesRcd;
	
	@Column(name = "RCD_FAULT_PROTECTION")
	private String rcdFaultProtection;
	
	@Column(name = "RCD_ADDITIONAL_PROTECTION")
	private String rcdAdditionalProtection;
	
	@Column(name = "OVER_VOLTAGE_PROTECTION")
	private String overVoltageProtection;
	
	@Column(name = "INDICATION_OF_SPD")
	private String indicationOfSpd;
	
	@Column(name = "RCD_QUARTERLY_TEST")
	private String rcdQuarterlyTest;
	
	@Column(name = "DIAGRAMS_CHARTS")
	private String diagramsCharts;
	
	@Column(name = "NONSTANDARD_CABLE_COLOUR")
	private String nonstandardCableColour;
	
	@Column(name = "AL_SUPPLY_OF_ORIGN")
	private String alSupplyOfOrign;
	
	@Column(name = "AL_SUPPLY_OF_METER")
	private String alSupplyOfMeter;
	
	@Column(name = "AL_SUPPLY_DISTRIBUTION")
	private String alSupplyDistribution;
	
	@Column(name = "ALL_POINTS_ISOLATION")
	private String allPointsIsolation;
	
	@Column(name = "NEXT_INSPECTION")
	private String nextInspection;
	
	@Column(name = "OTHER_REQUIRED_LABELLING")
	private String otherRequiredLabelling;
	
	@Column(name = "BASES_CORRECT_TYPE")
	private String basesCorrectType;
	
	@Column(name = "SINGLE_POLE")
	private String singlePole;
	
	@Column(name = "MECHANICAL_DAMAGE")
	private String mechanicalDamage;
	
	@Column(name = "ELECTROMAGNETIC")
	private String electromagnetic;
	
	@Column(name = "ALL_CONDUCTOR_CON")
	private String allConductorCon;
	
	@Column(name = "CONSUMER_STATUS")
	private String consumerStatus;
	
	@Column(name = "LOCATION_COUNT")
	private Integer locationCount;
	
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "IPAO_INSPECTION_ID")
	private IpaoInspection ipaoInspection;

	public Integer getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(Integer consumerId) {
		this.consumerId = consumerId;
	}

	public String getAccessWorking() {
		return accessWorking;
	}

	public void setAccessWorking(String accessWorking) {
		this.accessWorking = accessWorking;
	}

	public String getSecurityFixing() {
		return securityFixing;
	}

	public void setSecurityFixing(String securityFixing) {
		this.securityFixing = securityFixing;
	}

	public String getLivePartsDamage() {
		return livePartsDamage;
	}

	public void setLivePartsDamage(String livePartsDamage) {
		this.livePartsDamage = livePartsDamage;
	}

	public String getSecurityBarriers() {
		return securityBarriers;
	}

	public void setSecurityBarriers(String securityBarriers) {
		this.securityBarriers = securityBarriers;
	}

	public String getSuitabilityEnclosure() {
		return suitabilityEnclosure;
	}

	public void setSuitabilityEnclosure(String suitabilityEnclosure) {
		this.suitabilityEnclosure = suitabilityEnclosure;
	}

	public String getEnclosureDamaged() {
		return enclosureDamaged;
	}

	public void setEnclosureDamaged(String enclosureDamaged) {
		this.enclosureDamaged = enclosureDamaged;
	}

	public String getPresenceObstacles() {
		return presenceObstacles;
	}

	public void setPresenceObstacles(String presenceObstacles) {
		this.presenceObstacles = presenceObstacles;
	}

	public String getPlacingOutOfConsumer() {
		return placingOutOfConsumer;
	}

	public void setPlacingOutOfConsumer(String placingOutOfConsumer) {
		this.placingOutOfConsumer = placingOutOfConsumer;
	}

	public String getPresenceMainSwitches() {
		return presenceMainSwitches;
	}

	public void setPresenceMainSwitches(String presenceMainSwitches) {
		this.presenceMainSwitches = presenceMainSwitches;
	}

	public String getOperationMainSwitches() {
		return operationMainSwitches;
	}

	public void setOperationMainSwitches(String operationMainSwitches) {
		this.operationMainSwitches = operationMainSwitches;
	}

	public String getManualCircuitBreakers() {
		return manualCircuitBreakers;
	}

	public void setManualCircuitBreakers(String manualCircuitBreakers) {
		this.manualCircuitBreakers = manualCircuitBreakers;
	}

	public String getSwitchCausesRcd() {
		return switchCausesRcd;
	}

	public void setSwitchCausesRcd(String switchCausesRcd) {
		this.switchCausesRcd = switchCausesRcd;
	}

	public String getRcdFaultProtection() {
		return rcdFaultProtection;
	}

	public void setRcdFaultProtection(String rcdFaultProtection) {
		this.rcdFaultProtection = rcdFaultProtection;
	}

	public String getRcdAdditionalProtection() {
		return rcdAdditionalProtection;
	}

	public void setRcdAdditionalProtection(String rcdAdditionalProtection) {
		this.rcdAdditionalProtection = rcdAdditionalProtection;
	}

	public String getOverVoltageProtection() {
		return overVoltageProtection;
	}

	public void setOverVoltageProtection(String overVoltageProtection) {
		this.overVoltageProtection = overVoltageProtection;
	}

	public String getIndicationOfSpd() {
		return indicationOfSpd;
	}

	public void setIndicationOfSpd(String indicationOfSpd) {
		this.indicationOfSpd = indicationOfSpd;
	}

	public String getRcdQuarterlyTest() {
		return rcdQuarterlyTest;
	}

	public void setRcdQuarterlyTest(String rcdQuarterlyTest) {
		this.rcdQuarterlyTest = rcdQuarterlyTest;
	}

	public String getDiagramsCharts() {
		return diagramsCharts;
	}

	public void setDiagramsCharts(String diagramsCharts) {
		this.diagramsCharts = diagramsCharts;
	}

	public String getNonstandardCableColour() {
		return nonstandardCableColour;
	}

	public void setNonstandardCableColour(String nonstandardCableColour) {
		this.nonstandardCableColour = nonstandardCableColour;
	}

	public String getAlSupplyOfOrign() {
		return alSupplyOfOrign;
	}

	public void setAlSupplyOfOrign(String alSupplyOfOrign) {
		this.alSupplyOfOrign = alSupplyOfOrign;
	}

	public String getAlSupplyOfMeter() {
		return alSupplyOfMeter;
	}

	public void setAlSupplyOfMeter(String alSupplyOfMeter) {
		this.alSupplyOfMeter = alSupplyOfMeter;
	}

	public String getAlSupplyDistribution() {
		return alSupplyDistribution;
	}

	public void setAlSupplyDistribution(String alSupplyDistribution) {
		this.alSupplyDistribution = alSupplyDistribution;
	}

	public String getAllPointsIsolation() {
		return allPointsIsolation;
	}

	public void setAllPointsIsolation(String allPointsIsolation) {
		this.allPointsIsolation = allPointsIsolation;
	}

	public String getNextInspection() {
		return nextInspection;
	}

	public void setNextInspection(String nextInspection) {
		this.nextInspection = nextInspection;
	}

	public String getOtherRequiredLabelling() {
		return otherRequiredLabelling;
	}

	public void setOtherRequiredLabelling(String otherRequiredLabelling) {
		this.otherRequiredLabelling = otherRequiredLabelling;
	}

	public String getBasesCorrectType() {
		return basesCorrectType;
	}

	public void setBasesCorrectType(String basesCorrectType) {
		this.basesCorrectType = basesCorrectType;
	}

	public String getSinglePole() {
		return singlePole;
	}

	public void setSinglePole(String singlePole) {
		this.singlePole = singlePole;
	}

	public String getMechanicalDamage() {
		return mechanicalDamage;
	}

	public void setMechanicalDamage(String mechanicalDamage) {
		this.mechanicalDamage = mechanicalDamage;
	}

	public String getElectromagnetic() {
		return electromagnetic;
	}

	public void setElectromagnetic(String electromagnetic) {
		this.electromagnetic = electromagnetic;
	}

	public String getAllConductorCon() {
		return allConductorCon;
	}

	public void setAllConductorCon(String allConductorCon) {
		this.allConductorCon = allConductorCon;
	}

	public IpaoInspection getIpaoInspection() {
		return ipaoInspection;
	}

	public void setIpaoInspection(IpaoInspection ipaoInspection) {
		this.ipaoInspection = ipaoInspection;
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

	public String getConsumerStatus() {
		return consumerStatus;
	}

	public void setConsumerStatus(String consumerStatus) {
		this.consumerStatus = consumerStatus;
	}

	public Integer getLocationCount() {
		return locationCount;
	}

	public void setLocationCount(Integer locationCount) {
		this.locationCount = locationCount;
	}
}
