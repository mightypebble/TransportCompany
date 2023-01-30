package com.example.TransportCompany.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TransportCompany.dtos.ClientRegisterDto;
import com.example.TransportCompany.dtos.CompanyRegisterDto;
import com.example.TransportCompany.entities.ClientEntity;
import com.example.TransportCompany.entities.CompanyEntity;
import com.example.TransportCompany.exceptions.BadRequestException;
import com.example.TransportCompany.repositories.ClientRepository;

@Service
public class ClientService {
    public static final String CLIENT_ALREADY_EXISTS = "Client already exists!";

	private final ClientRepository clientRepository;
    private final CompanyService companyService;

	@Autowired
	public ClientService(ClientRepository clientRepository, CompanyService companyService) {
		this.clientRepository = clientRepository;
        this.companyService = companyService;
	}

    public ClientEntity register(ClientRegisterDto clientRegisterDto) {

        String name = clientRegisterDto.getName();
        String phone = clientRegisterDto.getPhone();
        String UCN = clientRegisterDto.getUCN();
        if(this.clientRepository.existsByPhoneOrUCN(phone, UCN)) throw new BadRequestException(CLIENT_ALREADY_EXISTS);
        CompanyEntity company = (CompanyEntity) companyService.getCompanyByName(clientRegisterDto.getCompanyName());

        ClientEntity client = new ClientEntity();
        client.setName(name);
        client.setPhone(phone);
        client.setUCN(UCN);
        client.setCompany(company);

        clientRepository.save(client);

        return null;
    }

    public ClientEntity getClientByUCN(String UCN) {
		final ClientEntity client = this.clientRepository.findByUCN(UCN);
		return client;
	}

    public void updateClient(ClientRegisterDto clientRegisterDto, Long id) {
		List<ClientEntity> clientList = clientRepository.findAll().stream()
                .collect(Collectors.toUnmodifiableList());
		
		String newName = clientRegisterDto.getName();
        String phone = clientRegisterDto.getPhone();
        String UCN = clientRegisterDto.getUCN();
        if(this.clientRepository.existsByPhoneOrUCN(phone, UCN)) throw new BadRequestException(CLIENT_ALREADY_EXISTS);

		for (ClientEntity client : clientList) {
			if (client.getId() == id) {
				client.setName(newName);
                client.setPhone(phone);
                client.setUCN(UCN);
				this.clientRepository.save(client);
				return;
			}
		}
	}

    public void deleteClient(Long id) {
        this.clientRepository.deleteById(id);
    }

    public List<ClientEntity> getClients() {
        List<ClientEntity> clients = clientRepository.findAll().stream().collect(Collectors.toUnmodifiableList());

        return clients;
    }

}    
