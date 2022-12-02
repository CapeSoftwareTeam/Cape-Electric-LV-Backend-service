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
@Table(name = "earthing_location_reports_table")
public class EarthingLocationReport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EARTHING_LOCATION_REPORT_ID")
	private Integer locationReportId;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "JOINT_NO")
	private String jointNo;

	@Column(name = "JOINT_REFERENCE")
	private String jointReference;

	@Column(name = "JOINT_RESISTANCE")
	private String jointResistance;

	@Column(name = "INSTALLOCATIONREPORT_STATUS")
	private String instalLocationReportStatus;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "SUPPLY_CHARACTERISTICS_ID")
	private SupplyCharacteristics supplyCharacteristics;

	public Integer getLocationReportId() {
		return locationReportId;
	}

	public void setLocationReportId(Integer locationReportId) {
		this.locationReportId = locationReportId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getJointNo() {
		return jointNo;
	}

	public void setJointNo(String jointNo) {
		this.jointNo = jointNo;
	}

	public String getJointResistance() {
		return jointResistance;
	}

	public void setJointResistance(String jointResistance) throws DecimalConversionException {
		if (jointResistance != null && !jointResistance.isEmpty()) {
			this.jointResistance = DecimalConversion.convertToDecimal(jointResistance,
					Constants.supply_Earth_JointResistance);
		}
		else {
			this.jointResistance = jointResistance;
		}
	}

	public String getJointReference() {
		return jointReference;
	}

	public void setJointReference(String jointReference) {
		this.jointReference = jointReference;
	}

	public SupplyCharacteristics getSupplyCharacteristics() {
		return supplyCharacteristics;
	}

	public void setSupplyCharacteristics(SupplyCharacteristics supplyCharacteristics) {
		this.supplyCharacteristics = supplyCharacteristics;
	}

	public String getInstalLocationReportStatus() {
		return instalLocationReportStatus;
	}

	public void setInstalLocationReportStatus(String instalLocationReportStatus) {
		this.instalLocationReportStatus = instalLocationReportStatus;
	}

}