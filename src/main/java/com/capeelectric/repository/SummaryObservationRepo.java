/**
 * 
 */
package com.capeelectric.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.capeelectric.model.SummaryObservation;

/**
 * @author CAPE-SOFTWARE
 *
 */
public interface SummaryObservationRepo extends CrudRepository<SummaryObservation, Integer>{

	@Modifying
	@Query("DELETE FROM SummaryObservation WHERE observationsId = :observationsId")
	void deleteObservRecordsById(Integer observationsId);
}
