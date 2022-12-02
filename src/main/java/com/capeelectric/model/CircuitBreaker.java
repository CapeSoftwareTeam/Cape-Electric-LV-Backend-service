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

import com.capeelectric.exception.DecimalConversionException;
import com.capeelectric.util.Constants;
import com.capeelectric.util.DecimalConversion;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 *
 * @author capeelectricsoftware
 *
 */
@Entity
@Table(name = "circuit_breaker_table")
public class CircuitBreaker implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CIRCUIT_BREAKER_ID")
	private Integer circuitBreakerId;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "SOURCE_NAME")
	private String sourceName;

	@Column(name = "MAKE")
	private String make;

	@Column(name = "CURRENT_CURVE")
	private String currentCurve;

	@Column(name = "POLES_NO")
	private String noPoles;

	@Column(name = "CURRRENT")
	private String current;

	@Column(name = "VOLTAGE")
	private String voltage;

	@Column(name = "FUSE")
	private String fuse;

	@Column(name = "TYPE_OF_RESIDUALCURRENT")
	private String typeOfResidualCurrent;

	@Column(name = "RESIDUAL_CURRENT")
	private String residualCurrent;

	@Column(name = "RESIDUAL_TIME")
	private String residualTime;
	
	@Column(name = "CIRCUIT_STATUS")
	private String circuitStatus;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "SUPPLY_CHARACTERISTICS_ID")
	private SupplyCharacteristics supplyCharacteristics;

	public Integer getCircuitBreakerId() {
		return circuitBreakerId;
	}

	public void setCircuitBreakerId(Integer circuitBreakerId) {
		this.circuitBreakerId = circuitBreakerId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getCurrentCurve() {
		return currentCurve;
	}

	public void setCurrentCurve(String currentCurve) {
		this.currentCurve = currentCurve;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNoPoles() {
		return noPoles;
	}

	public void setNoPoles(String noPoles) {
		this.noPoles = noPoles;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}

	public String getFuse() {
		return fuse;
	}

	public void setFuse(String fuse) {
		this.fuse = fuse;
	}

	public String getResidualCurrent() {
		return residualCurrent;
	}

	public void setResidualCurrent(String residualCurrent) {
		this.residualCurrent = residualCurrent;
	}

	public String getTypeOfResidualCurrent() {
		return typeOfResidualCurrent;
	}

	public void setTypeOfResidualCurrent(String typeOfResidualCurrent) {
		this.typeOfResidualCurrent = typeOfResidualCurrent;
	}

	public String getResidualTime() {
		return residualTime;
	}

	public void setResidualTime(String residualTime) throws DecimalConversionException {
		if (residualTime != null && !residualTime.isEmpty()) {
			this.residualTime = DecimalConversion.convertToDecimal(residualTime, Constants.supply_ResidualTime);
		} else {
			this.residualTime = residualTime;
		}
	}

	public SupplyCharacteristics getSupplyCharacteristics() {
		return supplyCharacteristics;
	}

	public void setSupplyCharacteristics(SupplyCharacteristics supplyCharacteristics) {
		this.supplyCharacteristics = supplyCharacteristics;
	}

	public String getCircuitStatus() {
		return circuitStatus;
	}

	public void setCircuitStatus(String circuitStatus) {
		this.circuitStatus = circuitStatus;
	}

}
