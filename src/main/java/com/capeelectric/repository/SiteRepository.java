package com.capeelectric.repository;

 
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.capeelectric.model.Site;

public interface SiteRepository extends CrudRepository<Site, Integer> {
	Optional<Site> findByUserNameAndSite(String userName, String site);

	List<Site> findByUserName(String userName);
	
	List<Site> findBysiteId(Integer siteId);
	
	Site findByCompanyNameAndDepartmentNameAndSite(String companyName, String departmentName, String site);

}
