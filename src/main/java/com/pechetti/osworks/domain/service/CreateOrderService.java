package com.pechetti.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pechetti.osworks.domain.exception.DomainException;
import com.pechetti.osworks.domain.model.Client;
import com.pechetti.osworks.domain.model.OrderService;
import com.pechetti.osworks.domain.model.OrderServiceStatus;
import com.pechetti.osworks.domain.repository.ClientRepository;
import com.pechetti.osworks.domain.repository.OrderServiceRepository;

@Service
public class CreateOrderService {
	
	@Autowired
	private OrderServiceRepository orderServiceRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	public OrderService create(OrderService orderService) {
		Client client = clientRepository.findById(orderService.getClient().getId())
				.orElseThrow(() -> new DomainException("Client not found"));
		
		orderService.setClient(client);;
		orderService.setStatus(OrderServiceStatus.OPEN);
		orderService.setOpenDate(OffsetDateTime.now());
		
		return orderServiceRepository.save(orderService);
	}

}
