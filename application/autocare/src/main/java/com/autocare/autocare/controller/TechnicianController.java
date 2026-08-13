package com.autocare.autocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.autocare.autocare.entity.Technician;
import com.autocare.autocare.service.TechnicianService;

@Controller
@RequestMapping("/technicians")
public class TechnicianController {

    private final TechnicianService technicianService;

    public TechnicianController(
            TechnicianService technicianService) {

        this.technicianService = technicianService;
    }

    // Show all technicians
    @GetMapping
    public String showTechnicians(Model model) {

        model.addAttribute(
                "technicians",
                technicianService.getAllTechnicians()
        );

        return "technicians/list";
    }

    // Show add technician form
    @GetMapping("/new")
    public String showAddTechnicianForm(Model model) {

        model.addAttribute(
                "technician",
                new Technician()
        );

        return "technicians/form";
    }

    // Show edit technician form
    @GetMapping("/edit/{id}")
    public String showEditTechnicianForm(
            @PathVariable Long id,
            Model model) {

        Technician technician =
                technicianService.getTechnicianById(id);

        if (technician == null) {
            return "redirect:/technicians";
        }

        model.addAttribute(
                "technician",
                technician
        );

        return "technicians/form";
    }

    // Save new or edited technician
    @PostMapping("/save")
    public String saveTechnician(
            @ModelAttribute("technician") Technician technician) {

        technicianService.saveTechnician(technician);

        return "redirect:/technicians";
    }

    // Delete technician
    @PostMapping("/delete/{id}")
    public String deleteTechnician(
            @PathVariable Long id) {

        technicianService.deleteTechnician(id);

        return "redirect:/technicians";
    }
}