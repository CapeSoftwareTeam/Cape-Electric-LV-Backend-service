package com.capeelectric.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capeelectric.model.SignatorDetails;

@Repository
public interface InstalSignDetailsRepository extends CrudRepository<SignatorDetails, Integer> {

}
