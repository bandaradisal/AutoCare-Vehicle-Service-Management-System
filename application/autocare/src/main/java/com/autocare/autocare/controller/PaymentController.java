package com.autocare.autocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.autocare.autocare.entity.Payment;
import com.autocare.autocare.service.InvoiceService;
import com.autocare.autocare.service.PaymentService;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final InvoiceService invoiceService;

    public PaymentController(
            PaymentService paymentService,
            InvoiceService invoiceService) {

        this.paymentService = paymentService;
        this.invoiceService = invoiceService;
    }

    // Show all payments
    @GetMapping
    public String showPayments(Model model) {

        model.addAttribute(
                "payments",
                paymentService.getAllPayments()
        );

        return "payments/list";
    }

    // Show add payment form
    @GetMapping("/new")
    public String showAddPaymentForm(Model model) {

        model.addAttribute(
                "payment",
                new Payment()
        );

        model.addAttribute(
                "invoices",
                invoiceService.getAllInvoices()
        );

        return "payments/form";
    }

    // Show edit payment form
    @GetMapping("/edit/{id}")
    public String showEditPaymentForm(
            @PathVariable Long id,
            Model model) {

        Payment payment =
                paymentService.getPaymentById(id);

        if (payment == null) {
            return "redirect:/payments";
        }

        model.addAttribute(
                "payment",
                payment
        );

        model.addAttribute(
                "invoices",
                invoiceService.getAllInvoices()
        );

        return "payments/form";
    }

    // Save new or edited payment
    @PostMapping("/save")
    public String savePayment(
            @ModelAttribute("payment") Payment payment) {

        paymentService.savePayment(payment);

        return "redirect:/payments";
    }

    // Delete payment
    @PostMapping("/delete/{id}")
    public String deletePayment(
            @PathVariable Long id) {

        paymentService.deletePayment(id);

        return "redirect:/payments";
    }
}