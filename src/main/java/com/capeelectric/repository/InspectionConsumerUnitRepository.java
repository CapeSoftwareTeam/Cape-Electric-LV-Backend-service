package com.capeelectric.repository;

import org.springframework.data.repository.CrudRepository;

import com.capeelectric.model.ConsumerUnit;
/**
 * 
 * @author capeelectricsoftware
 *
 */
public interface InspectionConsumerUnitRepository extends CrudRepository<ConsumerUnit, Integer> {

	public ConsumerUnit findByLocation(String location);
	
	public ConsumerUnit findByDistributionBoardDetailsAndReferanceAndLocation(String distributionDetails, String referance, String location);

}
