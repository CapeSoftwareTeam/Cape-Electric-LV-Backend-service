package com.capeelectric.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capeelectric.model.SupplyCharacteristicComment;

@Repository
public interface SupplyCharacteristicCommentRepository extends CrudRepository<SupplyCharacteristicComment, Integer> {

	public List<SupplyCharacteristicComment> findByInspectorEmail(String userName);

	public List<SupplyCharacteristicComment> findByViewerUserEmail(String userName);

}