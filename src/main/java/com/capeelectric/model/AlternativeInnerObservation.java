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

@Entity
@Table(name = "supply_alternative_inner_observations_table")
public class AlternativeInnerObservation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SUPPLY_INNER_OBSERVATIONS_ID")
	private Integer supplyInnerObervationsId;

	@Column(name = "OBSERVATION_COMPONENT_DETAILS")
	private String observationComponentDetails;

	@Column(name = "OBSERVATION_DESCRIPTION")
	private String observationDescription;
	
	@Column(name = "ALTERNATIVE_INNER_OBSERVATION_STATUS")
	private String alternativeInnerObservationStatus;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "SUPPLY_OUTER_OBSERVATION_ID")
	private SupplyOuterObservation supplyOuterObservation;

	public Integer getSupplyInnerObervationsId() {
		return supplyInnerObervationsId;
	}

	public void setSupplyInnerObervationsId(Integer supplyInnerObervationsId) {
		this.supplyInnerObervationsId = supplyInnerObervationsId;
	}

	public String getObservationComponentDetails() {
		return observationComponentDetails;
	}

	public void setObservationComponentDetails(String observationComponentDetails) {
		this.observationComponentDetails = observationComponentDetails;
	}

	public String getObservationDescription() {
		return observationDescription;
	}

	public void setObservationDescription(String observationDescription) {
		this.observationDescription = observationDescription;
	}

	public SupplyOuterObservation getSupplyOuterObservation() {
		return supplyOuterObservation;
	}

	public void setSupplyOuterObservation(SupplyOuterObservation supplyOuterObservation) {
		this.supplyOuterObservation = supplyOuterObservation;
	}

	public String getAlternativeInnerObservationStatus() {
		return alternativeInnerObservationStatus;
	}

	public void setAlternativeInnerObservationStatus(String alternativeInnerObservationStatus) {
		this.alternativeInnerObservationStatus = alternativeInnerObservationStatus;
	}

}
