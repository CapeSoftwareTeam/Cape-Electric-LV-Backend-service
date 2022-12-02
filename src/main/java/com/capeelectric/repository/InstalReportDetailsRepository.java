
package com.capeelectric.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capeelectric.model.ReportDetails;

/**
 **
  * @author capeelectricsoftware
 *
 */
@Repository
public interface InstalReportDetailsRepository extends CrudRepository<ReportDetails, Integer> {
	ReportDetails findByUserNameAndSiteId(String userName, Integer siteId);

	Optional<ReportDetails> findBySiteId(Integer siteId);
}
