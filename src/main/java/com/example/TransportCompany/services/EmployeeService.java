package com.example.TransportCompany.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TransportCompany.dtos.EmployeeRegisterDto;
import com.example.TransportCompany.entities.CompanyEntity;
import com.example.TransportCompany.entities.EmployeeEntity;
import com.example.TransportCompany.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
    private final CompanyService companyService;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository, CompanyService companyService) {
		this.employeeRepository = employeeRepository;
        this.companyService = companyService;
	}

    public EmployeeEntity register(EmployeeRegisterDto employeeRegisterDto) {

        String name = employeeRegisterDto.getName();
        String phone = employeeRegisterDto.getPhone();
        String UCN = employeeRegisterDto.getUCN();
        CompanyEntity company = (CompanyEntity) companyService.getCompanyByName(employeeRegisterDto.getCompanyName());

        EmployeeEntity employee = new EmployeeEntity();
        employee.setName(name);
        employee.setPhone(phone);
        employee.setUCN(UCN);
        employee.setCompany(company);

        employeeRepository.save(employee);

        return null;
    }

    public void updateEmployee(EmployeeRegisterDto employeeRegisterDto, String name) {
		List<EmployeeEntity> employeeList = employeeRepository.findAll().stream()
                .collect(Collectors.toUnmodifiableList());
		
		String newName = employeeRegisterDto.getName();
        String phone = employeeRegisterDto.getPhone();
        String UCN = employeeRegisterDto.getUCN();

		for (EmployeeEntity employee : employeeList) {
			if (employee.getName().equals(name)) {
				employee.setName(newName);
                employee.setPhone(phone);
                employee.setUCN(UCN);
				this.employeeRepository.save(employee);
				return;
			}
		}
	}

    public void deleteEmployee(String name) {
        this.employeeRepository.delete(employeeRepository.findByName(name));
    }

    public List<EmployeeEntity> getEmployees() {
        List<EmployeeEntity> employee = employeeRepository.findAll().stream().collect(Collectors.toUnmodifiableList());

        return employee;
    }

}