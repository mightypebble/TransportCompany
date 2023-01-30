package com.example.TransportCompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TransportCompany.entities.VehicleEntity;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    List<VehicleEntity> findAll();

    VehicleEntity findByRegistrationNumber(String registrationNumber);

    boolean existsByRegistrationNumber(String phone);

}
