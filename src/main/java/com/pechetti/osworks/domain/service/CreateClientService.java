package com.pechetti.osworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pechetti.osworks.domain.exception.DomainException;
import com.pechetti.osworks.domain.model.Client;
import com.pechetti.osworks.domain.repository.ClientRepository;

@Service
public class CreateClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	public Client save(Client client) {
		Client clientExists = clientRepository.findByEmail(client.getEmail());
		
		if (clientExists != null && !clientExists.equals(client)) {
			throw new DomainException("Email is already being used");
		}
		
		return clientRepository.save(client);
	}

}
