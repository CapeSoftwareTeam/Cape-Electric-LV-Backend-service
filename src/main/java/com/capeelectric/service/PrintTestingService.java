package com.capeelectric.service;

import java.util.Optional;

import com.capeelectric.exception.PdfException;
import com.capeelectric.exception.PeriodicTestingException;
import com.capeelectric.model.TestingReport;

public interface PrintTestingService {

	public void printTesting(String userName, Integer siteId, Optional<TestingReport> testingRepo)
			throws PeriodicTestingException, PdfException;
}
