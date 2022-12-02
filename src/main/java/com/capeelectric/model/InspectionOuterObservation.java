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
@Table(name = "inspection_outer_observation_table")
public class InspectionOuterObservation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INSPECTION_OUTER_OBSERVATION_ID")
	private Integer inspectionOuterObservationId;

	@Column(name = "OBSERVATION_COMPONENT_DETAILS")
	private String observationComponentDetails;

	@Column(name = "OBSERVATION_DESCRIPTION")
	private String observationDescription;
	
	@Column(name = "INSPECTION_OUTER_OBSERVATION_STATUS")
	private String inspectionOuterObservationStatus;

	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "IPAO_INSPECTION_ID")
	private IpaoInspection ipaoInspection;

	@JsonManagedReference
	@OneToMany(mappedBy = "inspectionOuterObservation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<InspectionInnerObservations> inspectionInnerObservations;

	public Integer getInspectionOuterObservationId() {
		return inspectionOuterObservationId;
	}

	public void setInspectionOuterObservationId(Integer inspectionOuterObservationId) {
		this.inspectionOuterObservationId = inspectionOuterObservationId;
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

	public IpaoInspection getIpaoInspection() {
		return ipaoInspection;
	}

	public void setIpaoInspection(IpaoInspection ipaoInspection) {
		this.ipaoInspection = ipaoInspection;
	}

	public String getInspectionOuterObservationStatus() {
		return inspectionOuterObservationStatus;
	}

	public void setInspectionOuterObservationStatus(String inspectionOuterObservationStatus) {
		this.inspectionOuterObservationStatus = inspectionOuterObservationStatus;
	}

	public List<InspectionInnerObservations> getInspectionInnerObservations() {
		return inspectionInnerObservations;
	}

	public void setInspectionInnerObservations(List<InspectionInnerObservations> inspectionInnerObservations) {
		this.inspectionInnerObservations = inspectionInnerObservations;
	}

}
