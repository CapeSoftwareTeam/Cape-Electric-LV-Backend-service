package com.capeelectric.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capeelectric.exception.CompanyDetailsException;
import com.capeelectric.model.Site;
import com.capeelectric.service.SiteService;

@RestController()
@RequestMapping("/api/v2")
public class SiteController {        
	private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

	@Autowired
	private SiteService siteService;

	@PostMapping("/addSite")
	public ResponseEntity<String> addSite(@RequestBody Site site) throws CompanyDetailsException {
		logger.debug("called addSite function UserName : {}, Site : {}", site.getSite());
		siteService.addSite(site);
		logger.debug("Ended addSite function");
		return new ResponseEntity<String>("Site Successfully Saved", HttpStatus.CREATED);

	}

	@PutMapping("/updateSite")
	public ResponseEntity<String> updateSite(@RequestBody Site site) throws CompanyDetailsException {
		logger.debug("called updateSite function UserName: {},Site : {}", site.getUserName(), site.getSite());
		siteService.updateSite(site);
		logger.debug("Ended updateSite function");
		return new ResponseEntity<String>("Site Successfully Updated", HttpStatus.OK);
	}
	
	@PutMapping("/updateSiteStatus")
	public ResponseEntity<String> updateSiteStatus(@RequestBody Site site) throws CompanyDetailsException {
		logger.debug("called updateSiteStatus function UserName: {},Site : {}", site.getUserName(), site.getSite());
		siteService.updateSiteStatus(site);
		logger.debug("Ended updateSiteStatus function");
		return new ResponseEntity<String>("Site has been successfully deleted", HttpStatus.OK);
	}

	@DeleteMapping("/deleteSite/{siteId}")
	public ResponseEntity<String> deleteSite(@PathVariable Integer siteId) throws CompanyDetailsException {
		logger.debug("called deleteSite function siteId: {}", siteId);
		siteService.deleteSite(siteId);
		logger.debug("Ended deleteSite function");
		return new ResponseEntity<String>("Site Succesfully Deleted", HttpStatus.OK);
	}

	
	@GetMapping("/retriveSite/{userName}")
	public ResponseEntity<List<Site>> retriveSite(@PathVariable String userName) throws CompanyDetailsException {
		logger.debug("called retriveSite function UserName: {}", userName);
		return new ResponseEntity<List<Site>>(siteService.retriveSite(userName), HttpStatus.OK);
	}
	
	@GetMapping("/retrieveSiteByName/{companyName}/{department}/{siteName}")
	public ResponseEntity<Site> retrieveSiteByName(@PathVariable String companyName, 
			@PathVariable String department, @PathVariable String siteName) throws CompanyDetailsException {
		logger.debug("called retriveSiteByName function Company Name: {}, Department: {}, Site Name: {}", companyName,
				department, siteName);
		return new ResponseEntity<Site>(siteService.retrieveSiteByName(companyName, department, siteName),
				HttpStatus.OK);
	}
	
	@GetMapping("/retrieveSiteName/{companyName}/{department}/{siteName}")
	public String retrieveSiteName(@PathVariable String companyName, @PathVariable String department, @PathVariable String siteName) throws CompanyDetailsException {
		logger.debug("called retriveSiteByName function Company Name: {}, Department: {}, Site Name: {}", companyName,
				department, siteName);
		return siteService.retrieveByCompanyNameDepartmentSiteName(companyName, department, siteName);
	}

}
