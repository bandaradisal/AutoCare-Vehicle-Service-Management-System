package com.autocare.autocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.autocare.autocare.entity.SparePart;
import com.autocare.autocare.service.SparePartService;

@Controller
@RequestMapping("/spare-parts")
public class SparePartController {

    private final SparePartService sparePartService;

    public SparePartController(
            SparePartService sparePartService) {

        this.sparePartService = sparePartService;
    }

    // Show all spare parts
    @GetMapping
    public String showSpareParts(Model model) {

        model.addAttribute(
                "spareParts",
                sparePartService.getAllSpareParts()
        );

        return "spare-parts/list";
    }

    // Show add spare part form
    @GetMapping("/new")
    public String showAddSparePartForm(Model model) {

        model.addAttribute(
                "sparePart",
                new SparePart()
        );

        return "spare-parts/form";
    }

    // Show edit spare part form
    @GetMapping("/edit/{id}")
    public String showEditSparePartForm(
            @PathVariable Long id,
            Model model) {

        SparePart sparePart =
                sparePartService.getSparePartById(id);

        if (sparePart == null) {
            return "redirect:/spare-parts";
        }

        model.addAttribute(
                "sparePart",
                sparePart
        );

        return "spare-parts/form";
    }

    // Save new or edited spare part
    @PostMapping("/save")
    public String saveSparePart(
            @ModelAttribute("sparePart") SparePart sparePart) {

        sparePartService.saveSparePart(sparePart);

        return "redirect:/spare-parts";
    }

    // Delete spare part
    @PostMapping("/delete/{id}")
    public String deleteSparePart(
            @PathVariable Long id) {

        sparePartService.deleteSparePart(id);

        return "redirect:/spare-parts";
    }
}