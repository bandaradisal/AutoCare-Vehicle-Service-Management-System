package com.autocare.autocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.autocare.autocare.document.JobCard;
import com.autocare.autocare.entity.ServiceBooking;
import com.autocare.autocare.entity.Technician;
import com.autocare.autocare.service.JobCardService;
import com.autocare.autocare.service.ServiceBookingService;
import com.autocare.autocare.service.TechnicianService;

@Controller
@RequestMapping("/job-cards")
public class JobCardController {

    private final JobCardService jobCardService;
    private final ServiceBookingService serviceBookingService;
    private final TechnicianService technicianService;

    public JobCardController(
            JobCardService jobCardService,
            ServiceBookingService serviceBookingService,
            TechnicianService technicianService) {

        this.jobCardService = jobCardService;
        this.serviceBookingService = serviceBookingService;
        this.technicianService = technicianService;
    }

    // Show all MongoDB job cards
    @GetMapping
    public String showJobCards(Model model) {

        model.addAttribute(
                "jobCards",
                jobCardService.getAllJobCards()
        );

        return "job-cards/list";
    }

    // Show Add Job Card form
    @GetMapping("/new")
    public String showAddJobCardForm(Model model) {

        model.addAttribute(
                "jobCard",
                new JobCard()
        );

        model.addAttribute(
                "bookings",
                serviceBookingService.getAllBookings()
        );

        model.addAttribute(
                "technicians",
                technicianService.getAllTechnicians()
        );

        return "job-cards/form";
    }

    // Show Edit Job Card form
    // Uses our Job Card ID such as JC001 or JC_TEST_01
    @GetMapping("/edit/{jobCardId}")
    public String showEditJobCardForm(
            @PathVariable String jobCardId,
            Model model) {

        JobCard jobCard =
                jobCardService.getJobCardByJobCardId(jobCardId);

        if (jobCard == null) {
            return "redirect:/job-cards";
        }

        model.addAttribute(
                "jobCard",
                jobCard
        );

        model.addAttribute(
                "bookings",
                serviceBookingService.getAllBookings()
        );

        model.addAttribute(
                "technicians",
                technicianService.getAllTechnicians()
        );

        return "job-cards/form";
    }

    // Save new or edited MongoDB Job Card
    @PostMapping("/save")
    public String saveJobCard(
            @ModelAttribute("jobCard") JobCard jobCard) {

        /*
         * Check that an Oracle booking was selected.
         */
        if (jobCard.getBookingId() == null) {
            return "redirect:/job-cards";
        }

        ServiceBooking booking =
                serviceBookingService.getBookingById(
                        jobCard.getBookingId()
                );

        /*
         * Booking must exist in Oracle.
         */
        if (booking == null) {
            return "redirect:/job-cards";
        }

        /*
         * Automatically obtain Vehicle ID
         * from the selected Oracle booking.
         */
        if (booking.getVehicle() == null) {
            return "redirect:/job-cards";
        }

        jobCard.setVehicleId(
                booking.getVehicle().getVehicleId()
        );

        /*
         * Check that an Oracle technician was selected.
         */
        if (jobCard.getTechnicianId() == null) {
            return "redirect:/job-cards";
        }

        Technician technician =
                technicianService.getTechnicianById(
                        jobCard.getTechnicianId()
                );

        /*
         * Technician must exist in Oracle.
         */
        if (technician == null) {
            return "redirect:/job-cards";
        }

        /*
         * Save the document in MongoDB.
         */
        jobCardService.saveJobCard(jobCard);

        return "redirect:/job-cards";
    }

    // Delete MongoDB Job Card
    // Uses our Job Card ID such as JC_TEST_01
    @PostMapping("/delete/{jobCardId}")
    public String deleteJobCard(
            @PathVariable String jobCardId) {

        jobCardService.deleteJobCardByJobCardId(jobCardId);

        return "redirect:/job-cards";
    }
}