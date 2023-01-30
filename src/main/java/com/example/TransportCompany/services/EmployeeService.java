package com.example.TransportCompany.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TransportCompany.dtos.EmployeeRegisterDto;
import com.example.TransportCompany.entities.CompanyEntity;
import com.example.TransportCompany.entities.EmployeeEntity;
import com.example.TransportCompany.exceptions.BadRequestException;
import com.example.TransportCompany.repositories.EmployeeRepository;

@Service
public class EmployeeService {
    public static final String EMPLOYEE_ALREADY_EXISTS = "Employee already exists!";

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
        if(this.employeeRepository.existsByPhoneOrUCN(phone, UCN)) throw new BadRequestException(EMPLOYEE_ALREADY_EXISTS);
        String vehicleCategory = employeeRegisterDto.getVehicleCategory();
        CompanyEntity company = (CompanyEntity) companyService.getCompanyByName(employeeRegisterDto.getCompanyName());

        EmployeeEntity employee = new EmployeeEntity();
        employee.setName(name);
        employee.setPhone(phone);
        employee.setUCN(UCN);
        employee.setVehicleCategory(vehicleCategory);
        employee.setCompany(company);
        employeeRepository.save(employee);

        return null;
    }

    public void updateEmployee(EmployeeRegisterDto employeeRegisterDto, Long id) {
		List<EmployeeEntity> employeeList = employeeRepository.findAll().stream()
                .collect(Collectors.toUnmodifiableList());
		
		String newName = employeeRegisterDto.getName();
        String phone = employeeRegisterDto.getPhone();
        String UCN = employeeRegisterDto.getUCN();
        String vehicleCategory = employeeRegisterDto.getVehicleCategory();
        if(this.employeeRepository.existsByPhoneOrUCN(phone, UCN)) throw new BadRequestException(EMPLOYEE_ALREADY_EXISTS);

		for (EmployeeEntity employee : employeeList) {
			if (employee.getId() == id) {
				employee.setName(newName);
                employee.setPhone(phone);
                employee.setUCN(UCN);
                employee.setVehicleCategory(vehicleCategory);
				this.employeeRepository.save(employee);
				return;
			}
		}
	}

    public void deleteEmployee(Long id) {
        this.employeeRepository.deleteById(id);
    }

    public List<EmployeeEntity> getEmployees() {
        List<EmployeeEntity> employee = employeeRepository.findAll().stream().collect(Collectors.toUnmodifiableList());

        return employee;
    }

}