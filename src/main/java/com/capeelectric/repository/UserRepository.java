package com.capeelectric.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.capeelectric.model.User;

/**
 * 
 * @author capeelectricsoftware
 *
 */
public interface UserRepository extends CrudRepository<User, Integer> {
	Optional<User> findByUsername(String userName);
}
