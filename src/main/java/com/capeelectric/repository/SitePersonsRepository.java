package com.capeelectric.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capeelectric.model.SitePersons;

@Repository
public interface SitePersonsRepository extends CrudRepository<SitePersons, Integer> {

	Optional<SitePersons> findBySiteNameAndPersonInchargeEmail(String siteName,String email);
	
}
