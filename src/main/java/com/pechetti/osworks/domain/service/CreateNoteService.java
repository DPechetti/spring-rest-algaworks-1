package com.pechetti.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pechetti.osworks.domain.exception.EntityNotFoundMException;
import com.pechetti.osworks.domain.model.Note;
import com.pechetti.osworks.domain.model.OrderService;
import com.pechetti.osworks.domain.repository.NoteRepository;
import com.pechetti.osworks.domain.repository.OrderServiceRepository;

@Service
public class CreateNoteService {
	
	@Autowired
	private OrderServiceRepository orderServiceRepository;
	
	@Autowired
	private NoteRepository noteRepository;

	public Note create(Long orderServiceId, String description) {
		OrderService orderService = orderServiceRepository.findById(orderServiceId)
				.orElseThrow(() -> new EntityNotFoundMException("Service order not found"));
		
		Note note = new Note();
		
		note.setSendDate(OffsetDateTime.now());
		note.setDescription(description);
		note.setOrderService(orderService);
		
		return noteRepository.save(note);
	}
	
}
