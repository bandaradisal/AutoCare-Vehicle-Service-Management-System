package com.autocare.autocare.mongo_repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.autocare.autocare.document.TechnicianNote;

public interface TechnicianNoteRepository
        extends MongoRepository<TechnicianNote, String> {

    // Find one note using custom note ID
    Optional<TechnicianNote> findByNoteId(String noteId);

    // Find all notes for one job card
    List<TechnicianNote> findByJobCardId(String jobCardId);

    // Find all notes written by one technician
    List<TechnicianNote> findByTechnicianId(Long technicianId);
}