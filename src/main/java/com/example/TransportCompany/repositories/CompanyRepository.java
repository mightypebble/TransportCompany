package com.example.TransportCompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.TransportCompany.entities.CompanyEntity;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    List<CompanyEntity> findAll();

    CompanyEntity findByName(String name);

    boolean existsByName(String name);

}
