package com.example.TransportCompany.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.TransportCompany.dtos.CompanyRegisterDto;
import com.example.TransportCompany.entities.*;
import com.example.TransportCompany.repositories.CompanyRepository;
import com.example.TransportCompany.exceptions.BadRequestException;

@Service
public class CompanyService {
	public static final String COMPANY_ALREADY_EXISTS = "Company with that name already exists!";

	private final CompanyRepository companyRepository;

	@Autowired
	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

    public CompanyEntity register(CompanyRegisterDto companyRegisterDto) {

		String name = companyRegisterDto.getName();

		if(this.companyRepository.existsByName(name)) throw new BadRequestException(COMPANY_ALREADY_EXISTS);

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
		if(this.companyRepository.existsByName(name)) throw new BadRequestException(COMPANY_ALREADY_EXISTS);

		for (CompanyEntity company : companyList) {
			if (company.getName().equals(name)) {
				company.setName(newName);
				this.companyRepository.save(company);
				return;
			}
		}
	}

	public void deleteCompany(String name) {
		this.companyRepository.delete(companyRepository.findByName(name));
	}

}
