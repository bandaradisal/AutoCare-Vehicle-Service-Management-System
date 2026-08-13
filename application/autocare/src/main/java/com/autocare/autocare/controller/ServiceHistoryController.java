package com.autocare.autocare.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.autocare.autocare.document.ServiceHistory;
import com.autocare.autocare.entity.ServiceBooking;
import com.autocare.autocare.service.ServiceBookingService;
import com.autocare.autocare.service.ServiceHistoryService;

@Controller
@RequestMapping("/service-history")
public class ServiceHistoryController {

    private final ServiceHistoryService serviceHistoryService;
    private final ServiceBookingService serviceBookingService;

    public ServiceHistoryController(
            ServiceHistoryService serviceHistoryService,
            ServiceBookingService serviceBookingService) {

        this.serviceHistoryService = serviceHistoryService;
        this.serviceBookingService = serviceBookingService;
    }

    // Show all MongoDB service history records
    @GetMapping
    public String showServiceHistory(Model model) {

        model.addAttribute(
                "serviceHistoryList",
                serviceHistoryService.getAllServiceHistory()
        );

        return "service-history/list";
    }

    // Show Add Service History form
    @GetMapping("/new")
    public String showAddServiceHistoryForm(Model model) {

        model.addAttribute(
                "serviceHistory",
                new ServiceHistory()
        );

        // Oracle service bookings
        model.addAttribute(
                "bookings",
                serviceBookingService.getAllBookings()
        );

        return "service-history/form";
    }

    // Show Edit Service History form
    @GetMapping("/edit/{historyId}")
    public String showEditServiceHistoryForm(
            @PathVariable String historyId,
            Model model) {

        ServiceHistory serviceHistory =
                serviceHistoryService
                        .getServiceHistoryByHistoryId(historyId);

        if (serviceHistory == null) {
            return "redirect:/service-history";
        }

        model.addAttribute(
                "serviceHistory",
                serviceHistory
        );

        // Oracle service bookings
        model.addAttribute(
                "bookings",
                serviceBookingService.getAllBookings()
        );

        return "service-history/form";
    }

    // Save new or edited Service History
    @PostMapping("/save")
    public String saveServiceHistory(
            @ModelAttribute("serviceHistory")
            ServiceHistory serviceHistory) {

        /*
         * Validate selected Oracle booking.
         */
        if (serviceHistory.getBookingId() == null) {
            return "redirect:/service-history";
        }

        ServiceBooking booking =
                serviceBookingService.getBookingById(
                        serviceHistory.getBookingId()
                );

        if (booking == null) {
            return "redirect:/service-history";
        }

        /*
         * Booking must have a vehicle.
         */
        if (booking.getVehicle() == null) {
            return "redirect:/service-history";
        }

        /*
         * Automatically copy Vehicle ID
         * from Oracle booking.
         */
        serviceHistory.setVehicleId(
                booking.getVehicle().getVehicleId()
        );

        /*
         * Automatically copy Service Type
         * from Oracle booking.
         */
        serviceHistory.setServiceType(
                booking.getServiceType()
        );

        /*
         * Automatically copy Service Date
         * from Oracle booking.
         *
         * Oracle ServiceBooking uses LocalDate.
         * MongoDB ServiceHistory uses LocalDateTime.
         */
        if (booking.getServiceDate() != null) {

            LocalDateTime serviceDate =
                    booking.getServiceDate()
                            .atStartOfDay();

            serviceHistory.setServiceDate(
                    serviceDate
            );
        }

        /*
         * If mileage was not entered,
         * try to use current vehicle mileage.
         */
        if (serviceHistory.getMileage() == null
                && booking.getVehicle().getCurrentMileage() != null) {

            serviceHistory.setMileage(
                    booking.getVehicle().getCurrentMileage()
            );
        }

        /*
         * Save document in MongoDB.
         */
        serviceHistoryService.saveServiceHistory(
                serviceHistory
        );

        return "redirect:/service-history";
    }

    // Delete using custom History ID
    @PostMapping("/delete/{historyId}")
    public String deleteServiceHistory(
            @PathVariable String historyId) {

        serviceHistoryService
                .deleteServiceHistoryByHistoryId(historyId);

        return "redirect:/service-history";
    }
}