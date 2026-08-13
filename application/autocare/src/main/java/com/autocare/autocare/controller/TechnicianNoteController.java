package com.autocare.autocare.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.autocare.autocare.document.JobCard;
import com.autocare.autocare.document.TechnicianNote;
import com.autocare.autocare.entity.Technician;
import com.autocare.autocare.service.JobCardService;
import com.autocare.autocare.service.TechnicianNoteService;
import com.autocare.autocare.service.TechnicianService;

@Controller
@RequestMapping("/technician-notes")
public class TechnicianNoteController {

    private final TechnicianNoteService technicianNoteService;
    private final JobCardService jobCardService;
    private final TechnicianService technicianService;

    public TechnicianNoteController(
            TechnicianNoteService technicianNoteService,
            JobCardService jobCardService,
            TechnicianService technicianService) {

        this.technicianNoteService = technicianNoteService;
        this.jobCardService = jobCardService;
        this.technicianService = technicianService;
    }

    // Show all technician notes
    @GetMapping
    public String showTechnicianNotes(Model model) {

        model.addAttribute(
                "technicianNotes",
                technicianNoteService.getAllTechnicianNotes()
        );

        return "technician-notes/list";
    }

    // Show Add Technician Note form
    @GetMapping("/new")
    public String showAddTechnicianNoteForm(Model model) {

        model.addAttribute(
                "technicianNote",
                new TechnicianNote()
        );

        // MongoDB Job Cards
        model.addAttribute(
                "jobCards",
                jobCardService.getAllJobCards()
        );

        // Oracle Technicians
        model.addAttribute(
                "technicians",
                technicianService.getAllTechnicians()
        );

        model.addAttribute(
                "noteText",
                ""
        );

        return "technician-notes/form";
    }

    // Show Edit Technician Note form
    @GetMapping("/edit/{noteId}")
    public String showEditTechnicianNoteForm(
            @PathVariable String noteId,
            Model model) {

        TechnicianNote technicianNote =
                technicianNoteService
                        .getTechnicianNoteByNoteId(noteId);

        if (technicianNote == null) {
            return "redirect:/technician-notes";
        }

        model.addAttribute(
                "technicianNote",
                technicianNote
        );

        // MongoDB Job Cards
        model.addAttribute(
                "jobCards",
                jobCardService.getAllJobCards()
        );

        // Oracle Technicians
        model.addAttribute(
                "technicians",
                technicianService.getAllTechnicians()
        );

        /*
         * Convert the MongoDB notes array into
         * text that can be shown inside a textarea.
         *
         * Each note will appear on a new line.
         */
        String noteText = "";

        if (technicianNote.getNotes() != null
                && !technicianNote.getNotes().isEmpty()) {

            noteText = String.join(
                    System.lineSeparator(),
                    technicianNote.getNotes()
            );
        }

        model.addAttribute(
                "noteText",
                noteText
        );

        return "technician-notes/form";
    }

    // Save new or edited technician note
    @PostMapping("/save")
    public String saveTechnicianNote(
            @ModelAttribute("technicianNote")
            TechnicianNote technicianNote,

            @RequestParam(
                    value = "noteText",
                    required = false
            )
            String noteText) {

        /*
         * Validate MongoDB Job Card.
         */
        if (technicianNote.getJobCardId() == null
                || technicianNote.getJobCardId().isBlank()) {

            return "redirect:/technician-notes";
        }

        JobCard jobCard =
                jobCardService.getJobCardByJobCardId(
                        technicianNote.getJobCardId()
                );

        if (jobCard == null) {
            return "redirect:/technician-notes";
        }

        /*
         * Validate Oracle Technician.
         */
        if (technicianNote.getTechnicianId() == null) {
            return "redirect:/technician-notes";
        }

        Technician technician =
                technicianService.getTechnicianById(
                        technicianNote.getTechnicianId()
                );

        if (technician == null) {
            return "redirect:/technician-notes";
        }

        /*
         * Convert textarea lines into a MongoDB array.
         *
         * Example:
         *
         * Checked engine oil
         * Replaced oil filter
         *
         * becomes:
         *
         * [
         *   "Checked engine oil",
         *   "Replaced oil filter"
         * ]
         */
        if (noteText != null
                && !noteText.isBlank()) {

            List<String> notes =
                    Arrays.stream(
                            noteText.split("\\R")
                    )
                    .map(String::trim)
                    .filter(line -> !line.isBlank())
                    .toList();

            technicianNote.setNotes(notes);
        }

        technicianNoteService.saveTechnicianNote(
                technicianNote
        );

        return "redirect:/technician-notes";
    }

    // Delete using custom Note ID
    @PostMapping("/delete/{noteId}")
    public String deleteTechnicianNote(
            @PathVariable String noteId) {

        technicianNoteService
                .deleteTechnicianNoteByNoteId(noteId);

        return "redirect:/technician-notes";
    }
}