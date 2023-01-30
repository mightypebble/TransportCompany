package com.example.TransportCompany.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TransportCompany.dtos.ClientRegisterDto;
import com.example.TransportCompany.dtos.CompanyRegisterDto;
import com.example.TransportCompany.dtos.VehicleRegisterDto;
import com.example.TransportCompany.entities.ClientEntity;
import com.example.TransportCompany.entities.CompanyEntity;
import com.example.TransportCompany.entities.VehicleEntity;
import com.example.TransportCompany.exceptions.BadRequestException;
import com.example.TransportCompany.repositories.ClientRepository;
import com.example.TransportCompany.repositories.VehicleRepository;

@Service
public class VehicleService {
    public static final String VEHICLE_ALREADY_EXISTS = "Vehicle with that registration number already exists!"; 

	private final VehicleRepository vehicleRepository;
    private final CompanyService companyService;

	@Autowired
	public VehicleService(VehicleRepository vehicleRepository, CompanyService companyService) {
		this.vehicleRepository = vehicleRepository;
        this.companyService = companyService;
	}

    public VehicleEntity register(VehicleRegisterDto vehicleRegisterDto) {

        String brand = vehicleRegisterDto.getBrand();
        String category = vehicleRegisterDto.getCategory();
        String registration = vehicleRegisterDto.getRegistrationNumber();
        if(this.vehicleRepository.existsByRegistrationNumber(registration)) throw new BadRequestException(VEHICLE_ALREADY_EXISTS);
        CompanyEntity company = (CompanyEntity) companyService.getCompanyByName(vehicleRegisterDto.getCompanyName());

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setBrand(brand);
        vehicle.setCategory(category);
        vehicle.setRegistrationNumber(registration);
        vehicle.setCompany(company);

        vehicleRepository.save(vehicle);

        return null;
    }

    public void updateVehicle(VehicleRegisterDto vehicleRegisterDto, String registration) {
		List<VehicleEntity> vehicleList = vehicleRepository.findAll().stream()
                .collect(Collectors.toUnmodifiableList());
		
		String brand = vehicleRegisterDto.getBrand();
        String category = vehicleRegisterDto.getCategory();
        String newRegistration = vehicleRegisterDto.getRegistrationNumber();
        if(this.vehicleRepository.existsByRegistrationNumber(registration)) throw new BadRequestException(VEHICLE_ALREADY_EXISTS);

		for (VehicleEntity vehicle : vehicleList) {
			if (vehicle.getRegistrationNumber().equals(registration)) {
				vehicle.setBrand(brand);
                vehicle.setCategory(category);
                vehicle.setRegistrationNumber(newRegistration);
				this.vehicleRepository.save(vehicle);
				return;
			}
		}
	}

    public void deleteVehicle(String registration) {
        this.vehicleRepository.delete(vehicleRepository.findByRegistrationNumber(registration));
    }

    public List<VehicleEntity> getVehicles() {
        List<VehicleEntity> vehicles = vehicleRepository.findAll().stream().collect(Collectors.toUnmodifiableList());

        return vehicles;
    }

}