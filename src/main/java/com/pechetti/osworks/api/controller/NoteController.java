package com.pechetti.osworks.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pechetti.osworks.api.model.NoteInput;
import com.pechetti.osworks.api.model.NoteModel;
import com.pechetti.osworks.domain.exception.EntityNotFoundMException;
import com.pechetti.osworks.domain.model.Note;
import com.pechetti.osworks.domain.model.OrderService;
import com.pechetti.osworks.domain.repository.OrderServiceRepository;
import com.pechetti.osworks.domain.service.CreateNoteService;

@RestController
@RequestMapping("/service-orders/{orderServiceId}/notes")
public class NoteController {
	
	@Autowired
	private CreateNoteService createNoteService;
	
	@Autowired
	private OrderServiceRepository orderServiceRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public NoteModel create(@PathVariable Long orderServiceId, @Valid @RequestBody NoteInput noteInput) {
		Note note = createNoteService.create(orderServiceId, noteInput.getDescription());
		
		return toModel(note);
	}
	
	private NoteModel toModel(Note note) {
		return modelMapper.map(note, NoteModel.class);
	}
	
	@GetMapping
	public List<NoteModel> show(@PathVariable Long orderServiceId) {
		OrderService orderService = orderServiceRepository.findById(orderServiceId)
				.orElseThrow(() -> new EntityNotFoundMException("Order service not found"));
		
		return toCollectionModel(orderService.getNotes());
	}
	
	private List<NoteModel> toCollectionModel(List<Note> notes) {
		return notes.stream().map(note -> toModel(note)).collect(Collectors.toList());
	}

}
