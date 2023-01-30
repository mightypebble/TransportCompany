package com.example.TransportCompany.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TransportCompany.dtos.ClientRegisterDto;
import com.example.TransportCompany.dtos.CompanyRegisterDto;
import com.example.TransportCompany.dtos.DeliveryDto;
import com.example.TransportCompany.entities.ClientEntity;
import com.example.TransportCompany.entities.CompanyEntity;
import com.example.TransportCompany.entities.Delivery;
import com.example.TransportCompany.exceptions.BadRequestException;
import com.example.TransportCompany.repositories.ClientRepository;
import com.example.TransportCompany.repositories.DeliveryRepository;

@Service
public class DeliveryService {

	private final DeliveryRepository deliveryRepository;
    private final CompanyService companyService;
    private final ClientService clientService;

	@Autowired
	public DeliveryService(DeliveryRepository deliveryRepository, CompanyService companyService, ClientService clientService) {
		this.deliveryRepository = deliveryRepository;
        this.companyService = companyService;
        this.clientService = clientService;
	}

    public Delivery register(DeliveryDto deliveryDto) {

        String type = deliveryDto.getType();
        BigDecimal weight = deliveryDto.getWeight();
        String origin = deliveryDto.getOrigin();
        String destination = deliveryDto.getDestination();
        String startDate = deliveryDto.getStartDate();
        String finishDate = deliveryDto.getFinishDate();
        BigDecimal price = deliveryDto.getPrice();
        ClientEntity client = (ClientEntity) clientService.getClientByUCN(deliveryDto.getClientUCN());
        CompanyEntity company = (CompanyEntity) companyService.getCompanyByName(deliveryDto.getCompanyName());

        Delivery delivery = new Delivery();
        delivery.setType(type);
        delivery.setWeight(weight);
        delivery.setOrigin(origin);
        delivery.setDestination(destination);
        delivery.setStartDate(startDate);
        delivery.setFinishDate(finishDate);
        delivery.setClient(client);
        delivery.setCompany(company);
        delivery.setPrice(price);
        delivery.setPaid(false);
        delivery.setDelivered(false);

        deliveryRepository.save(delivery);

        return null;
    }

    public void updateDelivery(DeliveryDto deliveryDto, Long id) {
		List<Delivery> deliveryList = deliveryRepository.findAll().stream()
                .collect(Collectors.toUnmodifiableList());
		
        String type = deliveryDto.getType();
        BigDecimal weight = deliveryDto.getWeight();
        String origin = deliveryDto.getOrigin();
        String destination = deliveryDto.getDestination();
        String startDate = deliveryDto.getStartDate();
        String finishDate = deliveryDto.getFinishDate();
        BigDecimal price = deliveryDto.getPrice();
        boolean isPaid = Boolean.parseBoolean(deliveryDto.getIsPaid());
        boolean isDelivered = Boolean.parseBoolean(deliveryDto.getIsDelivered());

		for (Delivery delivery : deliveryList) {
			if (delivery.getId() == id) {
                delivery.setType(type);
                delivery.setWeight(weight);
                delivery.setOrigin(origin);
                delivery.setDestination(destination);
                delivery.setStartDate(startDate);
                delivery.setFinishDate(finishDate);
                delivery.setPrice(price);
                delivery.setPaid(isPaid);
                delivery.setDelivered(isDelivered);
				this.deliveryRepository.save(delivery);
				return;
			}
		}
	}

    public void deleteDelivery(Long id) {
        this.deliveryRepository.deleteById(id);
    }

    public List<Delivery> getDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAll().stream().collect(Collectors.toUnmodifiableList());

        return deliveries;
    }

}