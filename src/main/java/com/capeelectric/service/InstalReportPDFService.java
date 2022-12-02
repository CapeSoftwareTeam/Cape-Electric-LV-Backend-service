package com.capeelectric.service;

import java.util.List;
import java.util.Optional;

import com.capeelectric.exception.InstalReportException;
import com.capeelectric.exception.PdfException;
import com.capeelectric.model.ReportDetails;

public interface InstalReportPDFService {

	public List<ReportDetails> printBasicInfromation(String userName, Integer SiteId,
			Optional<ReportDetails> reportDetailsRepo) throws InstalReportException, PdfException;


}