
package com.capeelectric.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
 **
  * @author capeelectricsoftware
 *
 */
@Entity
@Table(name = "sign_details_table")
public class SignatorDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SIGNATOR_ID")
	private Integer signatorId;

	@Column(name = "SIGNATOR_ROLE")
	private String signatorRole;

	@Column(name = "PERSON_NAME")
	private String personName;

	@Column(name = "PERSON_CONTACT_NO")
	private String personContactNo;

	@Column(name = "PERSON_MAIL_ID")
	private String personMailID;

	@Column(name = "MANAGER_NAME")
	private String managerName;

	@Column(name = "MANAGER_CONTACT_NO")
	private String managerContactNo;

	@Column(name = "MANAGER_MAIL_ID")
	private String managerMailID;

	@Column(name = "COMPANY_NAME")
	private String companyName;

	@Column(name = "ADDRESS_LINE1")
	private String addressLine1;

	@Column(name = "ADDRESS_LINE2")
	private String addressLine2;

	@Column(name = "LAND_MARK")
	private String landMark;

	@Column(name = "STATE")
	private String state;

	@Column(name = "PIN_CODE")
	private String pinCode;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "DECLARATION_DATE")
	private String declarationDate;

	@Column(name = "DECLARATION_NAME")
	private String declarationName;
	
	@Column(name = "DECLARATION_SIGNATURE")
	private String declarationSignature;
	
	@Column(name = "SIGNATOR_STATUS")
	private String signatorStatus;

	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "REPORT_ID")
	private ReportDetails reportDetails;

	public Integer getSignatorId() {
		return signatorId;
	}

	public void setSignatorId(Integer signatorId) {
		this.signatorId = signatorId;
	}

	public String getSignatorRole() {
		return signatorRole;
	}

	public void setSignatorRole(String signatorRole) {
		this.signatorRole = signatorRole;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonContactNo() {
		return personContactNo;
	}

	public void setPersonContactNo(String personContactNo) {
		this.personContactNo = personContactNo;
	}

	public String getPersonMailID() {
		return personMailID;
	}

	public void setPersonMailID(String personMailID) {
		this.personMailID = personMailID;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerContactNo() {
		return managerContactNo;
	}

	public void setManagerContactNo(String managerContactNo) {
		this.managerContactNo = managerContactNo;
	}

	public String getManagerMailID() {
		return managerMailID;
	}

	public void setManagerMailID(String managerMailID) {
		this.managerMailID = managerMailID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDeclarationDate() {
		return declarationDate;
	}

	public void setDeclarationDate(String declarationDate) {
		this.declarationDate = declarationDate;
	}

	public String getDeclarationName() {
		return declarationName;
	}

	public void setDeclarationName(String declarationName) {
		this.declarationName = declarationName;
	}
	
	public String getDeclarationSignature() {
		return declarationSignature;
	}

	public void setDeclarationSignature(String declarationSignature) {
		this.declarationSignature = declarationSignature;
	}

	public ReportDetails getReportDetails() {
		return reportDetails;
	}

	public void setReportDetails(ReportDetails reportDetails) {
		this.reportDetails = reportDetails;
	}

	public String getSignatorStatus() {
		return signatorStatus;
	}

	public void setSignatorStatus(String signatorStatus) {
		this.signatorStatus = signatorStatus;
	}

 
}