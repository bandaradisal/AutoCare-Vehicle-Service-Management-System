package com.autocare.autocare.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.autocare.autocare.document.TechnicianNote;
import com.autocare.autocare.mongo_repository.TechnicianNoteRepository;

@Service
public class TechnicianNoteService {

    private final TechnicianNoteRepository technicianNoteRepository;

    public TechnicianNoteService(
            TechnicianNoteRepository technicianNoteRepository) {

        this.technicianNoteRepository = technicianNoteRepository;
    }

    // Get all technician notes
    public List<TechnicianNote> getAllTechnicianNotes() {
        return technicianNoteRepository.findAll();
    }

    // Get note using MongoDB internal ID
    public TechnicianNote getTechnicianNoteById(String id) {
        return technicianNoteRepository.findById(id)
                .orElse(null);
    }

    // Get note using custom Note ID
    // Example: TN001
    public TechnicianNote getTechnicianNoteByNoteId(String noteId) {
        return technicianNoteRepository.findByNoteId(noteId)
                .orElse(null);
    }

    // Save new or edited technician note
    public TechnicianNote saveTechnicianNote(
            TechnicianNote technicianNote) {

        TechnicianNote existingNote =
                technicianNoteRepository
                        .findByNoteId(
                                technicianNote.getNoteId()
                        )
                        .orElse(null);

        /*
         * EDIT EXISTING NOTE
         */
        if (existingNote != null) {

            // Keep MongoDB internal _id
            technicianNote.setId(
                    existingNote.getId()
            );

            // Keep original created date
            technicianNote.setCreatedAt(
                    existingNote.getCreatedAt()
            );

            /*
             * If the form sends no notes,
             * keep the existing note list.
             */
            if (technicianNote.getNotes() == null
                    || technicianNote.getNotes().isEmpty()) {

                technicianNote.setNotes(
                        existingNote.getNotes()
                );
            }

        } else {

            /*
             * NEW NOTE
             */
            technicianNote.setCreatedAt(
                    LocalDateTime.now()
            );

            if (technicianNote.getNotes() == null) {
                technicianNote.setNotes(
                        new ArrayList<>()
                );
            }
        }

        return technicianNoteRepository.save(
                technicianNote
        );
    }

    // Delete using MongoDB internal ID
    public void deleteTechnicianNote(String id) {

        if (id != null && !id.isBlank()) {
            technicianNoteRepository.deleteById(id);
        }
    }

    // Delete using custom Note ID
    public void deleteTechnicianNoteByNoteId(
            String noteId) {

        TechnicianNote technicianNote =
                technicianNoteRepository
                        .findByNoteId(noteId)
                        .orElse(null);

        if (technicianNote != null) {
            technicianNoteRepository.delete(
                    technicianNote
            );
        }
    }

    // Find notes for one Job Card
    public List<TechnicianNote> getNotesByJobCard(
            String jobCardId) {

        return technicianNoteRepository
                .findByJobCardId(jobCardId);
    }

    // Find notes for one Technician
    public List<TechnicianNote> getNotesByTechnician(
            Long technicianId) {

        return technicianNoteRepository
                .findByTechnicianId(technicianId);
    }
}