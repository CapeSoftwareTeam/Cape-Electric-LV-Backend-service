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

@Entity
@Table(name = "supply_outer_observation_table")
public class SupplyOuterObservation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SUPPLY_OUTER_OBSERVATION_ID")
	private Integer supplyOuterObservationId;

	@Column(name = "OBSERVATION_COMPONENT_DETAILS")
	private String observationComponentDetails;

	@Column(name = "OBSERVATION_DESCRIPTION")
	private String observationDescription;
	
	@Column(name = "SUPPLY_OUTER_OBSERVATION_STATUS")
	private String supplyOuterObservationStatus;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "SUPPLY_CHARACTERISTICS_ID")
	private SupplyCharacteristics supplyCharacteristics;

	@JsonManagedReference
	@OneToMany(mappedBy = "supplyOuterObservation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AlternativeInnerObservation> alternativeInnerObservation;

	public Integer getSupplyOuterObservationId() {
		return supplyOuterObservationId;
	}

	public void setSupplyOuterObservationId(Integer supplyOuterObservationId) {
		this.supplyOuterObservationId = supplyOuterObservationId;
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

	public SupplyCharacteristics getSupplyCharacteristics() {
		return supplyCharacteristics;
	}

	public void setSupplyCharacteristics(SupplyCharacteristics supplyCharacteristics) {
		this.supplyCharacteristics = supplyCharacteristics;
	}

	public List<AlternativeInnerObservation> getAlternativeInnerObservation() {
		return alternativeInnerObservation;
	}

	public void setAlternativeInnerObservation(List<AlternativeInnerObservation> alternativeInnerObservation) {
		this.alternativeInnerObservation = alternativeInnerObservation;
	}

	public String getSupplyOuterObservationStatus() {
		return supplyOuterObservationStatus;
	}

	public void setSupplyOuterObservationStatus(String supplyOuterObservationStatus) {
		this.supplyOuterObservationStatus = supplyOuterObservationStatus;
	}
}
