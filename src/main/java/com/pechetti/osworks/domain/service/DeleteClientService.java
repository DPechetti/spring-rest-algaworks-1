package com.pechetti.osworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pechetti.osworks.domain.repository.ClientRepository;

@Service
public class DeleteClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	public void remove(Long clientId) {
		clientRepository.deleteById(clientId);
	}

}
