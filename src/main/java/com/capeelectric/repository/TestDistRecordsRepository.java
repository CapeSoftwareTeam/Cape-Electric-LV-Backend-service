package com.capeelectric.repository;

import org.springframework.data.repository.CrudRepository;

import com.capeelectric.model.TestDistRecords;
/**
 * 
 * @author capeelectricsoftware
 *
 */
public interface TestDistRecordsRepository extends CrudRepository<TestDistRecords, Integer> {

	public TestDistRecords findByLocationCount(Integer locationCount);

}
