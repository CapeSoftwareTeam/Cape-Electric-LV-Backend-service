package com.capeelectric.repository;

import org.springframework.data.repository.CrudRepository;

import com.capeelectric.model.Testing;

/**
 * 
 * @author capeelectricsoftware
 *
 */
public interface TestInfoRepository extends CrudRepository<Testing, Integer> {

	public Testing findByLocationCount(Integer integer);

}
