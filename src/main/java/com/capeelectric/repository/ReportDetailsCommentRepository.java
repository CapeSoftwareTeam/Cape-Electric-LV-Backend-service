package com.capeelectric.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capeelectric.model.ReportDetailsComment;

@Repository
public interface ReportDetailsCommentRepository extends CrudRepository<ReportDetailsComment, Integer> {

	public List<ReportDetailsComment> findByInspectorEmail(String userName);
	
	public List<ReportDetailsComment> findByViewerUserEmail(String userName);
}