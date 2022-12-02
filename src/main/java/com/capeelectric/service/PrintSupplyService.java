package com.capeelectric.service;

import java.util.Optional;

import com.capeelectric.exception.PdfException;
import com.capeelectric.exception.SupplyCharacteristicsException;
import com.capeelectric.model.SupplyCharacteristics;

public interface PrintSupplyService {
	public void printSupply(String userName, Integer siteId, Optional<SupplyCharacteristics> supplyCharacteristics)
			throws SupplyCharacteristicsException, PdfException;
}
