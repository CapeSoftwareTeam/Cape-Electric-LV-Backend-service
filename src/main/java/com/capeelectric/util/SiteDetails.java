package com.capeelectric.util;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.capeelectric.exception.CompanyDetailsException;
import com.capeelectric.model.Site;
import com.capeelectric.repository.SiteRepository;

/**
 * @author capeelectricsoftware
 *
 */
@Configuration
public class SiteDetails {
	
	private static final Logger logger = LoggerFactory.getLogger(SiteDetails.class);
	
	@Autowired
	private SiteRepository siteRepository;
	
	@Autowired
	private UserFullName userName;

	/**
	 * 	updateSite function to updating site updatetime and username updating
	*/
	public void updateSite(Integer siteId, String userName,String stepLV) throws CompanyDetailsException {

		try {
			logger.debug("SitedID " + siteId);
			Optional<Site> siteRepo = siteRepository.findById(siteId);
			logger.debug("Sitedetails time updating started");
			Site site = siteRepo.get();
			site.setUpdatedDate(LocalDateTime.now());
			site.setUpdatedBy(this.userName.findByUserName(userName));
			site.setAllStepsCompleted(stepLV);
			siteRepository.save(site);
			logger.debug("Sitedetails time updating ended");
		} catch (Exception e) {
			logger.debug("Sitedetails time updating failed");
			throw new CompanyDetailsException("Sitedetails time updating failed");
		}
	}

}
