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
@Table(name = "isolation_current_table")
public class IsolationCurrent implements Serializable {

	 
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ISOLATION_CURRENT_ID")
	private Integer isolationCurrentId;
	
	@Column(name = "I_PRESENCE_DEVICES")
	private String presenceDevices;
	
	@Column(name = "I_CONDITION_DEVICES")
	private String conditionDevices;
	
	@Column(name = "I_LOCATION_DEVICES")
	private String locationDevices;
	
	@Column(name = "I_CAPABLE_SECURED")
	private String capableSecured;
	
	@Column(name = "I_OPERATION_VERIFY")
	private String operationVerify;
	
	@Column(name = "I_INSTALL_CIRCUIT")
	private String installCircuit;
	
	@Column(name = "I_WARNING_LABEL")
	private String warningLabel;
	
	@Column(name = "SW_PRESENCE_DEVICES")
	private String swPresenceDevices;
	
	@Column(name = "SW_CONDITION_DEVICES")
	private String swConditionDevices;
	
	@Column(name = "SW_ACCEPTABLE_LOCATION")
	private String swAcceptableLocation;
	
	@Column(name = "SW_CAPABLE_OFF_POSITION")
	private String swCapableOffPosition;
	
	@Column(name = "SW_CORRECT_OPERATION")
	private String swCorrectOperation;
	
	@Column(name = "SW_CIRCUIT")
	private String swCircuit;
	
	@Column(name = "SW_WARNING_LABEL")
	private String swWarningLabel;
	
	@Column(name = "EM_SWIT_PRESENCE_DEVICES")
	private String emSwitPresenceDevices;
	
	@Column(name = "EM_SWIT_CONDITION_DEVICES")
	private String emSwitConditionDevices;
	
	@Column(name = "EM_SWIT_LOCATION_DEVICES")
	private String emSwitLocationDevices;
	
	@Column(name = "EM_SWIT_OPERATION_VERIFY")
	private String emSwitOperationVerify;
	
	@Column(name = "EM_SWIT_INSTALL_CIRCUIT")
	private String emSwitInstallCircuit;
	
	@Column(name = "FU_SWIT_PRESENCE_DEVICES")
	private String fuSwitPresenceDevices;
	
	@Column(name = "FU_SWIT_LOCATION_DEVICES")
	private String fuSwitLocationDevices;
	
	@Column(name = "FU_SWIT_OPERATION_VERIFY")
	private String fuSwitOperationVerify;
	
	@Column(name = "C_SUITABILITY_EQUIPMENT")
	private String suitabilityEquipment;
	
	@Column(name = "C_ENCLOSURE_NOT_DAMAGED")
	private String enclosureNotDamaged;
	
	@Column(name = "C_SUITABILITY_ENVIRONMENT")
	private String suitabilityEnvironment;
	
	@Column(name = "C_SECURITY_FIXING")
	private String securityFixing;
	
	@Column(name = "C_CABLE_ENTRY_HOLES")
	private String cableEntryHoles;
	
	@Column(name = "C_PROVISION_VOLTAGE")
	private String provisionVoltage;
	
	@Column(name = "C_PROVISION_OVERLOAD")
	private String provisionOverload;
	
	@Column(name = "C_CORRECT_TYPE_LAMPS")
	private String correctTypeLamps;
	
	@Column(name = "C_INSULA_DISPLACEMENT_BOX")
	private String insulaDisplacementBox;
	
	@Column(name = "C_OVERHEAT_SURROUNDING")
	private String overheatSurrounding;
	
	@Column(name = "C_OVERHEAT_CONDUCTORS")
	private String overheatConductors;
	
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "IPAO_INSPECTION_ID")
	private IpaoInspection ipaoInspection;

	public Integer getIsolationCurrentId() {
		return isolationCurrentId;
	}

	public void setIsolationCurrentId(Integer isolationCurrentId) {
		this.isolationCurrentId = isolationCurrentId;
	}

	public String getPresenceDevices() {
		return presenceDevices;
	}

	public void setPresenceDevices(String presenceDevices) {
		this.presenceDevices = presenceDevices;
	}

	public String getConditionDevices() {
		return conditionDevices;
	}

	public void setConditionDevices(String conditionDevices) {
		this.conditionDevices = conditionDevices;
	}

	public String getLocationDevices() {
		return locationDevices;
	}

	public void setLocationDevices(String locationDevices) {
		this.locationDevices = locationDevices;
	}

	public String getCapableSecured() {
		return capableSecured;
	}

	public void setCapableSecured(String capableSecured) {
		this.capableSecured = capableSecured;
	}

	public String getOperationVerify() {
		return operationVerify;
	}

	public void setOperationVerify(String operationVerify) {
		this.operationVerify = operationVerify;
	}

	public String getInstallCircuit() {
		return installCircuit;
	}

	public void setInstallCircuit(String installCircuit) {
		this.installCircuit = installCircuit;
	}

	public String getWarningLabel() {
		return warningLabel;
	}

	public void setWarningLabel(String warningLabel) {
		this.warningLabel = warningLabel;
	}

	public String getSwPresenceDevices() {
		return swPresenceDevices;
	}

	public void setSwPresenceDevices(String swPresenceDevices) {
		this.swPresenceDevices = swPresenceDevices;
	}

	public String getSwConditionDevices() {
		return swConditionDevices;
	}

	public void setSwConditionDevices(String swConditionDevices) {
		this.swConditionDevices = swConditionDevices;
	}

	public String getSwAcceptableLocation() {
		return swAcceptableLocation;
	}

	public void setSwAcceptableLocation(String swAcceptableLocation) {
		this.swAcceptableLocation = swAcceptableLocation;
	}

	public String getSwCapableOffPosition() {
		return swCapableOffPosition;
	}

	public void setSwCapableOffPosition(String swCapableOffPosition) {
		this.swCapableOffPosition = swCapableOffPosition;
	}

	public String getSwCorrectOperation() {
		return swCorrectOperation;
	}

	public void setSwCorrectOperation(String swCorrectOperation) {
		this.swCorrectOperation = swCorrectOperation;
	}

	public String getSwCircuit() {
		return swCircuit;
	}

	public void setSwCircuit(String swCircuit) {
		this.swCircuit = swCircuit;
	}

	public String getSwWarningLabel() {
		return swWarningLabel;
	}

	public void setSwWarningLabel(String swWarningLabel) {
		this.swWarningLabel = swWarningLabel;
	}

	public String getEmSwitPresenceDevices() {
		return emSwitPresenceDevices;
	}

	public void setEmSwitPresenceDevices(String emSwitPresenceDevices) {
		this.emSwitPresenceDevices = emSwitPresenceDevices;
	}

	public String getEmSwitConditionDevices() {
		return emSwitConditionDevices;
	}

	public void setEmSwitConditionDevices(String emSwitConditionDevices) {
		this.emSwitConditionDevices = emSwitConditionDevices;
	}

	public String getEmSwitLocationDevices() {
		return emSwitLocationDevices;
	}

	public void setEmSwitLocationDevices(String emSwitLocationDevices) {
		this.emSwitLocationDevices = emSwitLocationDevices;
	}

	public String getEmSwitOperationVerify() {
		return emSwitOperationVerify;
	}

	public void setEmSwitOperationVerify(String emSwitOperationVerify) {
		this.emSwitOperationVerify = emSwitOperationVerify;
	}

	public String getEmSwitInstallCircuit() {
		return emSwitInstallCircuit;
	}

	public void setEmSwitInstallCircuit(String emSwitInstallCircuit) {
		this.emSwitInstallCircuit = emSwitInstallCircuit;
	}

	public String getFuSwitPresenceDevices() {
		return fuSwitPresenceDevices;
	}

	public void setFuSwitPresenceDevices(String fuSwitPresenceDevices) {
		this.fuSwitPresenceDevices = fuSwitPresenceDevices;
	}

	public String getFuSwitLocationDevices() {
		return fuSwitLocationDevices;
	}

	public void setFuSwitLocationDevices(String fuSwitLocationDevices) {
		this.fuSwitLocationDevices = fuSwitLocationDevices;
	}

	public String getFuSwitOperationVerify() {
		return fuSwitOperationVerify;
	}

	public void setFuSwitOperationVerify(String fuSwitOperationVerify) {
		this.fuSwitOperationVerify = fuSwitOperationVerify;
	}

	public String getSuitabilityEquipment() {
		return suitabilityEquipment;
	}

	public void setSuitabilityEquipment(String suitabilityEquipment) {
		this.suitabilityEquipment = suitabilityEquipment;
	}

	public String getEnclosureNotDamaged() {
		return enclosureNotDamaged;
	}

	public void setEnclosureNotDamaged(String enclosureNotDamaged) {
		this.enclosureNotDamaged = enclosureNotDamaged;
	}

	public String getSuitabilityEnvironment() {
		return suitabilityEnvironment;
	}

	public void setSuitabilityEnvironment(String suitabilityEnvironment) {
		this.suitabilityEnvironment = suitabilityEnvironment;
	}

	public String getSecurityFixing() {
		return securityFixing;
	}

	public void setSecurityFixing(String securityFixing) {
		this.securityFixing = securityFixing;
	}

	public String getCableEntryHoles() {
		return cableEntryHoles;
	}

	public void setCableEntryHoles(String cableEntryHoles) {
		this.cableEntryHoles = cableEntryHoles;
	}

	public String getProvisionVoltage() {
		return provisionVoltage;
	}

	public void setProvisionVoltage(String provisionVoltage) {
		this.provisionVoltage = provisionVoltage;
	}

	public String getProvisionOverload() {
		return provisionOverload;
	}

	public void setProvisionOverload(String provisionOverload) {
		this.provisionOverload = provisionOverload;
	}

	public String getCorrectTypeLamps() {
		return correctTypeLamps;
	}

	public void setCorrectTypeLamps(String correctTypeLamps) {
		this.correctTypeLamps = correctTypeLamps;
	}

	public String getInsulaDisplacementBox() {
		return insulaDisplacementBox;
	}

	public void setInsulaDisplacementBox(String insulaDisplacementBox) {
		this.insulaDisplacementBox = insulaDisplacementBox;
	}

	public String getOverheatSurrounding() {
		return overheatSurrounding;
	}

	public void setOverheatSurrounding(String overheatSurrounding) {
		this.overheatSurrounding = overheatSurrounding;
	}

	public String getOverheatConductors() {
		return overheatConductors;
	}

	public void setOverheatConductors(String overheatConductors) {
		this.overheatConductors = overheatConductors;
	}

	public IpaoInspection getIpaoInspection() {
		return ipaoInspection;
	}

	public void setIpaoInspection(IpaoInspection ipaoInspection) {
		this.ipaoInspection = ipaoInspection;
	}

}
