package com.capeelectric.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capeelectric.model.TestingReportComment;

@Repository
public interface TestingReportCommentRepository extends CrudRepository<TestingReportComment, Integer> {

	public List<TestingReportComment> findByInspectorEmail(String userName);
	
	public List<TestingReportComment> findByViewerUserEmail(String userName);

}