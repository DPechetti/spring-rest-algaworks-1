package com.pechetti.osworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pechetti.osworks.domain.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

}
