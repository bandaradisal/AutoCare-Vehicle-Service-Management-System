package com.autocare.autocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.autocare.autocare.entity.InvoiceItem;
import com.autocare.autocare.service.InvoiceItemService;
import com.autocare.autocare.service.InvoiceService;
import com.autocare.autocare.service.SparePartService;

@Controller
@RequestMapping("/invoice-items")
public class InvoiceItemController {

    private final InvoiceItemService invoiceItemService;
    private final InvoiceService invoiceService;
    private final SparePartService sparePartService;

    public InvoiceItemController(
            InvoiceItemService invoiceItemService,
            InvoiceService invoiceService,
            SparePartService sparePartService) {

        this.invoiceItemService = invoiceItemService;
        this.invoiceService = invoiceService;
        this.sparePartService = sparePartService;
    }

    // Show all invoice items
    @GetMapping
    public String showInvoiceItems(Model model) {

        model.addAttribute(
                "invoiceItems",
                invoiceItemService.getAllInvoiceItems()
        );

        return "invoice-items/list";
    }

    // Show add invoice item form
    @GetMapping("/new")
    public String showAddInvoiceItemForm(Model model) {

        model.addAttribute(
                "invoiceItem",
                new InvoiceItem()
        );

        model.addAttribute(
                "invoices",
                invoiceService.getAllInvoices()
        );

        model.addAttribute(
                "spareParts",
                sparePartService.getAllSpareParts()
        );

        return "invoice-items/form";
    }

    // Show edit invoice item form
    @GetMapping("/edit/{id}")
    public String showEditInvoiceItemForm(
            @PathVariable Long id,
            Model model) {

        InvoiceItem invoiceItem =
                invoiceItemService.getInvoiceItemById(id);

        if (invoiceItem == null) {
            return "redirect:/invoice-items";
        }

        model.addAttribute(
                "invoiceItem",
                invoiceItem
        );

        model.addAttribute(
                "invoices",
                invoiceService.getAllInvoices()
        );

        model.addAttribute(
                "spareParts",
                sparePartService.getAllSpareParts()
        );

        return "invoice-items/form";
    }

    // Save invoice item
    @PostMapping("/save")
    public String saveInvoiceItem(
            @ModelAttribute("invoiceItem") InvoiceItem invoiceItem) {

        invoiceItemService.saveInvoiceItem(invoiceItem);

        return "redirect:/invoice-items";
    }

    // Delete invoice item
    @PostMapping("/delete/{id}")
    public String deleteInvoiceItem(
            @PathVariable Long id) {

        invoiceItemService.deleteInvoiceItem(id);

        return "redirect:/invoice-items";
    }
}