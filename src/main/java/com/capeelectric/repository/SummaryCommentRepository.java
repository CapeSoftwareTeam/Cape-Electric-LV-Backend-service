package com.capeelectric.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capeelectric.model.SummaryComment;

@Repository
public interface SummaryCommentRepository extends CrudRepository<SummaryComment, Integer> {

	public List<SummaryComment> findByInspectorEmail(String userName);

	public List<SummaryComment> findByViewerUserEmail(String userName);

}