package com.capeelectric.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capeelectric.model.PeriodicInspectionComment;

@Repository
public interface PeriodicInspectionCommentRepository extends CrudRepository<PeriodicInspectionComment, Integer> {

	public List<PeriodicInspectionComment> findByInspectorEmail(String userName);

	public List<PeriodicInspectionComment> findByViewerUserEmail(String userName);

}