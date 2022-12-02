package com.capeelectric.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * The persistent class for the site_table database table.
 * 
 */
@Entity
@Table(name = "site_table")
@NamedQueries(value = {
		@NamedQuery(name = "SiteRepository.findByUserNameAndSite", query = "select s from Site s where s.userName=:userName and s.site=:site"),
		@NamedQuery(name = "SiteRepository.findByCompanyNameAndDepartmentNameAndSite",
		            query = "select s from Site s where s.companyName=:companyName and s.departmentName=:departmentName and s.site=:site") })
public class Site implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "SITE_ID")
	private Integer siteId;

	@Column(name = "SITE_CODE")
	private String siteCd;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "SITE")
	private String site;
	
	@Column(name = "COMPANY_NAME")
	private String companyName;
	
	@Column(name = "DEPARTMENT_NAME")
	private String departmentName;

	@Column(name = "ADDRESSLINE_1")
	private String addressLine_1;

	@Column(name = "ADDRESSLINE_2")
	private String addressLine_2;

	@Column(name = "LAND_MARK")
	private String landMark;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "ZIP_CODE")
	private String zipCode;
	
	@Column(name = "ALL_STEPS_COMPLETED")
	private String allStepsCompleted;
	
	@Column(name = "SITE_ASSIGNED_TO")
	private String assignedTo;
	
	@Column(name = "STATUS")
	private String status;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATED_DATE")
	private LocalDateTime updatedDate;
	
	@OneToMany(mappedBy="site",fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
	private Set<SitePersons> sitePersons;
	
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getAddressLine_1() {
		return addressLine_1;
	}

	public void setAddressLine_1(String addressLine_1) {
		this.addressLine_1 = addressLine_1;
	}

	public String getAddressLine_2() {
		return addressLine_2;
	}

	public void setAddressLine_2(String addressLine_2) {
		this.addressLine_2 = addressLine_2;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAllStepsCompleted() {
		return allStepsCompleted;
	}

	public void setAllStepsCompleted(String allStepsCompleted) {
		this.allStepsCompleted = allStepsCompleted;
	}
	
	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getSiteCd() {
		return siteCd;
	}

	public void setSiteCd(String siteCd) {
		this.siteCd = siteCd;
	}

	@JsonManagedReference
	public Set<SitePersons> getSitePersons() {
		return sitePersons;
	}

	public void setSitePersons(Set<SitePersons> sitePersons) {
		this.sitePersons = sitePersons;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}