package com.capeelectric.service;

import org.springframework.stereotype.Service;

import com.capeelectric.exception.CompanyDetailsException;
import com.capeelectric.model.Site;

@Service
public interface SitePersonsService {

	public void addSitePerson(Site site)throws CompanyDetailsException;

	public void updateSitePerson(Site site)throws CompanyDetailsException;
}
