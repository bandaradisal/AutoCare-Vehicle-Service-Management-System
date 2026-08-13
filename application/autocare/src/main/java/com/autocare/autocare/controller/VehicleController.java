package com.autocare.autocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.autocare.autocare.entity.Vehicle;
import com.autocare.autocare.service.CustomerService;
import com.autocare.autocare.service.VehicleService;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final CustomerService customerService;

    public VehicleController(
            VehicleService vehicleService,
            CustomerService customerService) {

        this.vehicleService = vehicleService;
        this.customerService = customerService;
    }

    // Show all vehicles
    @GetMapping
    public String showVehicles(Model model) {

        model.addAttribute(
                "vehicles",
                vehicleService.getAllVehicles()
        );

        return "vehicles/list";
    }

    // Show add vehicle form
    @GetMapping("/new")
    public String showAddVehicleForm(Model model) {

        model.addAttribute(
                "vehicle",
                new Vehicle()
        );

        model.addAttribute(
                "customers",
                customerService.getAllCustomers()
        );

        return "vehicles/form";
    }

    // Show edit vehicle form
    @GetMapping("/edit/{id}")
    public String showEditVehicleForm(
            @PathVariable Long id,
            Model model) {

        Vehicle vehicle =
                vehicleService.getVehicleById(id);

        if (vehicle == null) {
            return "redirect:/vehicles";
        }

        model.addAttribute(
                "vehicle",
                vehicle
        );

        model.addAttribute(
                "customers",
                customerService.getAllCustomers()
        );

        return "vehicles/form";
    }

    // Save new or edited vehicle
    @PostMapping("/save")
    public String saveVehicle(
            @ModelAttribute("vehicle") Vehicle vehicle) {

        vehicleService.saveVehicle(vehicle);

        return "redirect:/vehicles";
    }

    // Delete vehicle
    @PostMapping("/delete/{id}")
    public String deleteVehicle(
            @PathVariable Long id) {

        vehicleService.deleteVehicle(id);

        return "redirect:/vehicles";
    }
}