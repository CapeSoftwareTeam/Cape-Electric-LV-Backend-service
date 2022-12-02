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
@Table(name = "declaration_table")
public class SummaryDeclaration implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DECLARATION_ID")
	private Integer declarationId;
	
	@Column(name = "DECLARATION_ROLE")
	private String declarationRole;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "SIGNATURE")
	private String signature;
	
	@Column(name = "COMPANY")
	private String company;
	
	@Column(name = "POSITION")
	private String position;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "DATE")
	private String date;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "SUMMARY_ID")
	private Summary summary;

	public Integer getDeclarationId() {
		return declarationId;
	}

	public void setDeclarationId(Integer declarationId) {
		this.declarationId = declarationId;
	}

	public String getDeclarationRole() {
		return declarationRole;
	}

	public void setDeclarationRole(String declarationRole) {
		this.declarationRole = declarationRole;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Summary getSummary() {
		return summary;
	}

	public void setSummary(Summary summary) {
		this.summary = summary;
	}

}
