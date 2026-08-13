package com.autocare.autocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.autocare.autocare.entity.Invoice;
import com.autocare.autocare.service.InvoiceService;
import com.autocare.autocare.service.ServiceBookingService;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final ServiceBookingService serviceBookingService;

    public InvoiceController(
            InvoiceService invoiceService,
            ServiceBookingService serviceBookingService) {

        this.invoiceService = invoiceService;
        this.serviceBookingService = serviceBookingService;
    }

    // Show all invoices
    @GetMapping
    public String showInvoices(Model model) {

        model.addAttribute(
                "invoices",
                invoiceService.getAllInvoices()
        );

        return "invoices/list";
    }

    // Show add invoice form
    @GetMapping("/new")
    public String showAddInvoiceForm(Model model) {

        model.addAttribute(
                "invoice",
                new Invoice()
        );

        model.addAttribute(
                "bookings",
                serviceBookingService.getAllBookings()
        );

        return "invoices/form";
    }

    // Show edit invoice form
    @GetMapping("/edit/{id}")
    public String showEditInvoiceForm(
            @PathVariable Long id,
            Model model) {

        Invoice invoice =
                invoiceService.getInvoiceById(id);

        if (invoice == null) {
            return "redirect:/invoices";
        }

        model.addAttribute(
                "invoice",
                invoice
        );

        model.addAttribute(
                "bookings",
                serviceBookingService.getAllBookings()
        );

        return "invoices/form";
    }

    // Save new or edited invoice
    @PostMapping("/save")
    public String saveInvoice(
            @ModelAttribute("invoice") Invoice invoice) {

        invoiceService.saveInvoice(invoice);

        return "redirect:/invoices";
    }

    // Delete invoice
    @PostMapping("/delete/{id}")
    public String deleteInvoice(
            @PathVariable Long id) {

        invoiceService.deleteInvoice(id);

        return "redirect:/invoices";
    }
}