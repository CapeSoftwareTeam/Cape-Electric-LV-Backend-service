/**
 * 
 */
package com.capeelectric.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.capeelectric.model.SummaryInnerObservation;

/**
 * @author CAPE-SOFTWARE
 *
 */
public interface SummaryInnerObservationRepo extends CrudRepository<SummaryInnerObservation, Integer>{
	
	@Modifying
	@Query("DELETE FROM SummaryInnerObservation WHERE innerObservationsId = :innerObservationsId")
	void deleteInnerObservRecordsById(Integer innerObservationsId);

}
