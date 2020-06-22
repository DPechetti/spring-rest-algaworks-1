package com.pechetti.osworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pechetti.osworks.domain.exception.EntityNotFoundMException;
import com.pechetti.osworks.domain.model.OrderService;
import com.pechetti.osworks.domain.repository.OrderServiceRepository;

@Service
public class UpdateOrderService {
	
	@Autowired
	private OrderServiceRepository orderServiceRepository;

	public void endService(Long orderServiceId) {
		OrderService orderService = orderServiceRepository.findById(orderServiceId)
				.orElseThrow(() -> new EntityNotFoundMException("Service order not found"));
		
		orderService.finish();
		
		orderServiceRepository.save(orderService);		
	}
	
	public void cancelService(Long orderServiceId) {
		OrderService orderService = orderServiceRepository.findById(orderServiceId)
				.orElseThrow(() -> new EntityNotFoundMException("Service order not found"));
		
		orderService.cancel();
		
		orderServiceRepository.save(orderService);		
	}
	
}
