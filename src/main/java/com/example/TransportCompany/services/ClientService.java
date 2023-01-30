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
import com.example.TransportCompany.repositories.DeliveryRepository;

@Service
public class ClientService {
    public static final String CLIENT_ALREADY_EXISTS = "Client already exists!";
    public static final String CLIENT_HAS_DELIVERIES = "Client still has deliveries that need to be deleted!";

	private final ClientRepository clientRepository;
    private final CompanyService companyService;
    private final DeliveryRepository deliveryRepository;

	@Autowired
	public ClientService(ClientRepository clientRepository, CompanyService companyService, DeliveryRepository deliveryRepository) {
		this.clientRepository = clientRepository;
        this.companyService = companyService;
        this.deliveryRepository = deliveryRepository;
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
        if(deliveryRepository.getByClient(clientRepository.getReferenceById(id)) != null) {
            throw new BadRequestException(CLIENT_HAS_DELIVERIES);
        }
        this.clientRepository.deleteById(id);
    }

    public List<ClientEntity> getClients() {
        List<ClientEntity> clients = clientRepository.findAll().stream().collect(Collectors.toUnmodifiableList());

        return clients;
    }

    public ClientEntity getClientById(Long id) {
        ClientEntity client = clientRepository.getReferenceById(id);

        return client;
    }

}    
