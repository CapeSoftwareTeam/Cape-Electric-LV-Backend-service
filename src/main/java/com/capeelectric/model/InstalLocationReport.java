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
@Table(name = "installation_location_table")
public class InstalLocationReport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INSTALLATION_LOCATION_REPORT_ID")
	private Integer locationReportId;

	@Column(name = "LOCATION_NO")
	private String locationNo;

	@Column(name = "LOCATION_NAME")
	private String locationName;

	@Column(name = "MEANS_EARTHING_REMARK")
	private String meansEarthingRemark;

	@Column(name = "ELECTRODE_EARTH_TYPE")
	private String electrodeEarthType;

	@Column(name = "ELECTRODE_EARTH_MATERIAL")
	private String electrodeEarthMaterial;

	@Column(name = "ELECTRODE_EARTH_DEPTH")
	private String electrodeEarthDepth;

	@Column(name = "ELECTRODE_EARTH_SIZE")
	private String electrodeEarthSize;

	@Column(name = "ELECTORDE_RESISTANCE_EARTH")
	private String electrodeResistanceEarth;

	@Column(name = "ELECTORDE_RESISTANCE_GRID")
	private String electrodeResistanceGird;

	@Column(name = "INSTALLOCATIONREPORT_STATUS")
	private String instalLocationReportStatus;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "SUPPLY_CHARACTERISTICS_ID")
	private SupplyCharacteristics supplyCharacteristics;

	public Integer getLocationReportId() {
		return locationReportId;
	}

	public String getMeansEarthingRemark() {
		return meansEarthingRemark;
	}

	public void setMeansEarthingRemark(String meansEarthingRemark) {
		this.meansEarthingRemark = meansEarthingRemark;
	}

	public void setLocationReportId(Integer locationReportId) {
		this.locationReportId = locationReportId;
	}

	public String getLocationNo() {
		return locationNo;
	}

	public void setLocationNo(String locationNo) {
		this.locationNo = locationNo;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getElectrodeResistanceEarth() {
		return electrodeResistanceEarth;
	}

	public void setElectrodeResistanceEarth(String electrodeResistanceEarth) throws DecimalConversionException {
		if (electrodeResistanceEarth != null && !electrodeResistanceEarth.isEmpty()) {
			this.electrodeResistanceEarth = DecimalConversion.convertToDecimal(electrodeResistanceEarth,
					Constants.supply_Earth_Resistance);
		} else {
			this.electrodeResistanceEarth = electrodeResistanceEarth;
		}
	}

	public String getElectrodeEarthType() {
		return electrodeEarthType;
	}

	public void setElectrodeEarthType(String electrodeEarthType) {
		this.electrodeEarthType = electrodeEarthType;
	}

	public String getElectrodeEarthMaterial() {
		return electrodeEarthMaterial;
	}

	public void setElectrodeEarthMaterial(String electrodeEarthMaterial) {
		this.electrodeEarthMaterial = electrodeEarthMaterial;
	}

	public String getElectrodeEarthDepth() {
		return electrodeEarthDepth;
	}

	public void setElectrodeEarthDepth(String electrodeEarthDepth) throws DecimalConversionException {
		if (electrodeEarthDepth != null && !electrodeEarthDepth.isEmpty()) {
			this.electrodeEarthDepth = DecimalConversion.convertToDecimal(electrodeEarthDepth,
					Constants.supply_Earth_Depth);
		} else {
			this.electrodeEarthDepth = electrodeEarthDepth;
		}
	}

	public String getElectrodeEarthSize() {
		return electrodeEarthSize;
	}

	public void setElectrodeEarthSize(String electrodeEarthSize) throws DecimalConversionException {
		if (electrodeEarthSize != null && !electrodeEarthSize.isEmpty()) {
			this.electrodeEarthSize = DecimalConversion.convertToDecimal(electrodeEarthSize,
					Constants.supply_Earth_Electrode_Size);
		} else {
			this.electrodeEarthSize = electrodeEarthSize;
		}
	}

	public String getElectrodeResistanceGird() {
		return electrodeResistanceGird;
	}

	public void setElectrodeResistanceGird(String electrodeResistanceGird) throws DecimalConversionException {
		if (electrodeResistanceGird != null && !electrodeResistanceGird.isEmpty()) {
			this.electrodeResistanceGird = DecimalConversion.convertToDecimal(electrodeResistanceGird,
					Constants.supply_Grid_Resistance);
		} else {
			this.electrodeResistanceGird = electrodeResistanceGird;
		}
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