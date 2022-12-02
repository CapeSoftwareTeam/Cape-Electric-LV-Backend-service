package com.capeelectric.service;

import java.util.List;
import java.util.Optional;

import com.capeelectric.exception.InspectionException;
import com.capeelectric.exception.PdfException;
import com.capeelectric.model.PeriodicInspection;

public interface InspectionServicePDF {

	public List<PeriodicInspection> printInspectionDetails(String userName, Integer siteId,
			Optional<PeriodicInspection> periodicInspection) throws InspectionException, PdfException;
}