package com.pechetti.osworks.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pechetti.osworks.domain.model.Client;

@RestController
public class ClientController {
	
	@GetMapping("/clients")
	public List<Client> show() {
		
		var client1 = new Client();
		
		client1.setId(1L);
		client1.setName("Joãozinho");
		client1.setEmail("joao@teste.com");
		client1.setPhone("51 99999-8888");
		
		var client2 = new Client();
		
		client2.setId(2L);
		client2.setName("Maria");
		client2.setEmail("maria@teste.com");
		client2.setPhone("51 99999-7777");
		
		return Arrays.asList(client1, client2);
		
	}
	
}
