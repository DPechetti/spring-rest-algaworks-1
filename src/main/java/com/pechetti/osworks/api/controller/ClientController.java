package com.pechetti.osworks.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pechetti.osworks.domain.model.Client;
import com.pechetti.osworks.domain.repository.ClientRepository;
import com.pechetti.osworks.domain.service.CreateClientService;
import com.pechetti.osworks.domain.service.DeleteClientService;

@RestController
@RequestMapping("clients")
public class ClientController {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CreateClientService createClient;
	
	@Autowired
	private DeleteClientService deleteClient;
	
	@GetMapping
	public List<Client> show() {
		return clientRepository.findAll();
	}
	
	@GetMapping("/{clientId}")
	public ResponseEntity<Client>index(@PathVariable Long clientId) {
		Optional<Client> client = clientRepository.findById(clientId);
		
		if (client.isPresent()) {
			return ResponseEntity.ok(client.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Client create(@Valid @RequestBody Client client) {
		return createClient.save(client);
	}
	
	@PutMapping("/{clientId}")
	public ResponseEntity<Client> update(@Valid @PathVariable Long clientId, @RequestBody Client client) {
		if (!clientRepository.existsById(clientId)) {
			return ResponseEntity.notFound().build();
		}
		
		client.setId(clientId);
		client = createClient.save(client);
		
		return ResponseEntity.ok(client);
	}
	
	@DeleteMapping("/{clientId}")
	public ResponseEntity<Void> delete(@PathVariable Long clientId) {
		if (!clientRepository.existsById(clientId)) {
			return ResponseEntity.notFound().build();
		}
		
		deleteClient.remove(clientId);
		return ResponseEntity.noContent().build();
	}
}
