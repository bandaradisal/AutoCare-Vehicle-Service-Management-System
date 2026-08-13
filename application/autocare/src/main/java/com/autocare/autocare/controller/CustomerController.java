package com.autocare.autocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.autocare.autocare.entity.Customer;
import com.autocare.autocare.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Show all customers
    @GetMapping
    public String showCustomers(Model model) {

        model.addAttribute(
                "customers",
                customerService.getAllCustomers()
        );

        return "customers/list";
    }

    // Show add customer form
    @GetMapping("/new")
    public String showAddCustomerForm(Model model) {

        model.addAttribute(
                "customer",
                new Customer()
        );

        return "customers/form";
    }

    // Show edit customer form
    @GetMapping("/edit/{id}")
    public String showEditCustomerForm(
            @PathVariable Long id,
            Model model) {

        Customer customer =
                customerService.getCustomerById(id);

        if (customer == null) {
            return "redirect:/customers";
        }

        model.addAttribute(
                "customer",
                customer
        );

        return "customers/form";
    }

    // Save new or edited customer
    @PostMapping("/save")
    public String saveCustomer(
            @ModelAttribute("customer") Customer customer) {

        customerService.saveCustomer(customer);

        return "redirect:/customers";
    }

    // Delete customer
    @PostMapping("/delete/{id}")
    public String deleteCustomer(
            @PathVariable Long id) {

        customerService.deleteCustomer(id);

        return "redirect:/customers";
    }
}