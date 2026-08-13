package com.autocare.autocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.autocare.autocare.entity.ServiceBooking;
import com.autocare.autocare.service.ServiceBookingService;
import com.autocare.autocare.service.VehicleService;

@Controller
@RequestMapping("/bookings")
public class ServiceBookingController {

    private final ServiceBookingService serviceBookingService;
    private final VehicleService vehicleService;

    public ServiceBookingController(
            ServiceBookingService serviceBookingService,
            VehicleService vehicleService) {

        this.serviceBookingService = serviceBookingService;
        this.vehicleService = vehicleService;
    }

    // Show all bookings
    @GetMapping
    public String showBookings(Model model) {

        model.addAttribute(
                "bookings",
                serviceBookingService.getAllBookings()
        );

        return "bookings/list";
    }

    // Show add booking form
    @GetMapping("/new")
    public String showAddBookingForm(Model model) {

        model.addAttribute(
                "booking",
                new ServiceBooking()
        );

        model.addAttribute(
                "vehicles",
                vehicleService.getAllVehicles()
        );

        return "bookings/form";
    }

    // Show edit booking form
    @GetMapping("/edit/{id}")
    public String showEditBookingForm(
            @PathVariable Long id,
            Model model) {

        ServiceBooking booking =
                serviceBookingService.getBookingById(id);

        if (booking == null) {
            return "redirect:/bookings";
        }

        model.addAttribute(
                "booking",
                booking
        );

        model.addAttribute(
                "vehicles",
                vehicleService.getAllVehicles()
        );

        return "bookings/form";
    }

    // Save new or edited booking
    @PostMapping("/save")
    public String saveBooking(
            @ModelAttribute("booking") ServiceBooking booking) {

        serviceBookingService.saveBooking(booking);

        return "redirect:/bookings";
    }

    // Delete booking
    @PostMapping("/delete/{id}")
    public String deleteBooking(
            @PathVariable Long id) {

        serviceBookingService.deleteBooking(id);

        return "redirect:/bookings";
    }
}