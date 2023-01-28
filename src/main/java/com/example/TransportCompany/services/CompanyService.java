package com.example.TransportCompany.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.TransportCompany.dtos.CompanyRegisterDto;
import com.example.TransportCompany.entities.*;
import com.example.TransportCompany.repositories.CompanyRepository;

@Service
public class CompanyService {

	private final CompanyRepository companyRepository;

	@Autowired
	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

    public CompanyEntity register(CompanyRegisterDto companyRegisterDto) {

		String name = companyRegisterDto.getName();

		CompanyEntity company = new CompanyEntity();
		company.setName(name);

		this.companyRepository.save(company);

		return null;
	}

	public CompanyEntity getCompanyByName(String name) {
		final CompanyEntity company = this.companyRepository.findByName(name);
		return company;
	}

	public void updateCompany(CompanyRegisterDto companyRegisterDto, String name) {
		List<CompanyEntity> companyList = companyRepository.findAll().stream()
                .collect(Collectors.toUnmodifiableList());
		
		String newName = companyRegisterDto.getName();	

		for (CompanyEntity company : companyList) {
			if (company.getName().equals(name)) {
				company.setName(newName);
				this.companyRepository.save(company);
				return;
			}
		}
	}

}
