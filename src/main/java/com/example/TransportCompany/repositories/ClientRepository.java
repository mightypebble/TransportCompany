package com.example.TransportCompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TransportCompany.entities.ClientEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    List<ClientEntity> findAll();

    ClientEntity findByName(String name);

    boolean existsByPhoneOrUCN(String phone, String UCN);

    ClientEntity findByUCN(String UCN);

}
