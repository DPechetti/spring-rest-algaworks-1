package com.pechetti.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pechetti.osworks.api.model.OrderServiceInput;
import com.pechetti.osworks.api.model.OrderServiceRepresentationModel;
import com.pechetti.osworks.domain.model.OrderService;
import com.pechetti.osworks.domain.repository.OrderServiceRepository;
import com.pechetti.osworks.domain.service.CreateOrderService;
import com.pechetti.osworks.domain.service.UpdateOrderService;

@RestController
@RequestMapping("service-orders")
public class OrderServiceController {
	
	@Autowired
	private CreateOrderService createOrderService;
	
	@Autowired
	private UpdateOrderService updateOrderService;
	
	@Autowired
	private OrderServiceRepository orderServiceRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderServiceRepresentationModel create( @Valid @RequestBody OrderServiceInput orderServiceInput) {
		OrderService orderService = toEntity(orderServiceInput);
		return toModel(createOrderService.create(orderService));
	}
	
	@GetMapping
	public List<OrderServiceRepresentationModel> show(){
		return toCollectionModel(orderServiceRepository.findAll());
	}
	
	@GetMapping("/{orderServiceId}")
	public ResponseEntity<OrderServiceRepresentationModel> index (@PathVariable Long orderServiceId) {
		Optional<OrderService> orderService = orderServiceRepository.findById(orderServiceId);
		
		if (orderService.isPresent()) {
			OrderServiceRepresentationModel orderServiceRepresentationModel = toModel(orderService.get()); 
			return ResponseEntity.ok(orderServiceRepresentationModel);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{orderServiceId}/finalization")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalize(@PathVariable Long orderServiceId) {
		updateOrderService.endService(orderServiceId);
	}
	
	@PutMapping("/{orderServiceId}/cancelation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancel(@PathVariable Long orderServiceId) {
		updateOrderService.cancelService(orderServiceId);
	}
	
	private OrderServiceRepresentationModel toModel(OrderService orderService) {
		return modelMapper.map(orderService, OrderServiceRepresentationModel.class);
	}
	
	private List<OrderServiceRepresentationModel> toCollectionModel(List<OrderService> ordersService) {
		return ordersService.stream().map(orderService -> toModel(orderService)).collect(Collectors.toList());
	}
	
	private OrderService toEntity(OrderServiceInput orderServiceInput) {
		return modelMapper.map(orderServiceInput, OrderService.class);
	}
}
