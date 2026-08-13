package com.autocare.autocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.autocare.autocare.document.ComplaintFeedback;
import com.autocare.autocare.entity.Vehicle;
import com.autocare.autocare.service.ComplaintFeedbackService;
import com.autocare.autocare.service.VehicleService;

@Controller
@RequestMapping("/complaints-feedback")
public class ComplaintFeedbackController {

    private final ComplaintFeedbackService complaintFeedbackService;
    private final VehicleService vehicleService;

    public ComplaintFeedbackController(
            ComplaintFeedbackService complaintFeedbackService,
            VehicleService vehicleService) {

        this.complaintFeedbackService = complaintFeedbackService;
        this.vehicleService = vehicleService;
    }

    // Show all complaints and feedback
    @GetMapping
    public String showComplaintFeedback(Model model) {

        model.addAttribute(
                "complaintsFeedback",
                complaintFeedbackService.getAllComplaintFeedback()
        );

        return "complaints-feedback/list";
    }

    // Show Add form
    @GetMapping("/new")
    public String showAddComplaintFeedbackForm(Model model) {

        model.addAttribute(
                "complaintFeedback",
                new ComplaintFeedback()
        );

        // Oracle vehicles
        model.addAttribute(
                "vehicles",
                vehicleService.getAllVehicles()
        );

        return "complaints-feedback/form";
    }

    // Show Edit form
    @GetMapping("/edit/{complaintId}")
    public String showEditComplaintFeedbackForm(
            @PathVariable String complaintId,
            Model model) {

        ComplaintFeedback complaintFeedback =
                complaintFeedbackService
                        .getComplaintFeedbackByComplaintId(
                                complaintId
                        );

        if (complaintFeedback == null) {
            return "redirect:/complaints-feedback";
        }

        model.addAttribute(
                "complaintFeedback",
                complaintFeedback
        );

        // Oracle vehicles
        model.addAttribute(
                "vehicles",
                vehicleService.getAllVehicles()
        );

        return "complaints-feedback/form";
    }

    // Save new or edited complaint/feedback
    @PostMapping("/save")
    public String saveComplaintFeedback(
            @ModelAttribute("complaintFeedback")
            ComplaintFeedback complaintFeedback) {

        /*
         * Validate selected Oracle vehicle.
         */
        if (complaintFeedback.getVehicleId() == null) {
            return "redirect:/complaints-feedback";
        }

        Vehicle vehicle =
                vehicleService.getVehicleById(
                        complaintFeedback.getVehicleId()
                );

        if (vehicle == null) {
            return "redirect:/complaints-feedback";
        }

        /*
         * Vehicle must belong to a customer.
         */
        if (vehicle.getCustomer() == null) {
            return "redirect:/complaints-feedback";
        }

        /*
         * Automatically get Customer ID
         * from the selected Oracle vehicle.
         */
        complaintFeedback.setCustomerId(
                vehicle.getCustomer().getCustomerId()
        );

        /*
         * Validate type.
         */
        if (complaintFeedback.getType() == null
                || complaintFeedback.getType().isBlank()) {

            return "redirect:/complaints-feedback";
        }

        if (!complaintFeedback.getType().equals("Complaint")
                && !complaintFeedback.getType().equals("Feedback")) {

            return "redirect:/complaints-feedback";
        }

        /*
         * Rating is mainly used for Feedback.
         * If Complaint is selected, remove rating.
         */
        if (complaintFeedback.getType().equals("Complaint")) {

            complaintFeedback.setRating(null);
        }

        /*
         * Save MongoDB document.
         */
        complaintFeedbackService.saveComplaintFeedback(
                complaintFeedback
        );

        return "redirect:/complaints-feedback";
    }

    // Delete using custom Complaint ID
    @PostMapping("/delete/{complaintId}")
    public String deleteComplaintFeedback(
            @PathVariable String complaintId) {

        complaintFeedbackService
                .deleteComplaintFeedbackByComplaintId(
                        complaintId
                );

        return "redirect:/complaints-feedback";
    }
}