package com.autocare.autocare.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.autocare.autocare.entity.Invoice;
import com.autocare.autocare.repository.InvoiceRepository;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    // Get all invoices
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    // Get one invoice by ID
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    // Add or update invoice
    public Invoice saveInvoice(Invoice invoice) {

        if (invoice.getInvoiceId() != null) {

            Invoice existingInvoice =
                    invoiceRepository.findById(invoice.getInvoiceId())
                            .orElse(null);

            if (existingInvoice != null) {

                // Keep original invoice date
                invoice.setInvoiceDate(
                        existingInvoice.getInvoiceDate()
                );

                // Payment module will control this later
                invoice.setInvoiceStatus(
                        existingInvoice.getInvoiceStatus()
                );
            }

        } else {

            // New invoice
            invoice.setInvoiceDate(LocalDateTime.now());
            invoice.setInvoiceStatus("Unpaid");
        }

        // Handle empty monetary fields
        BigDecimal serviceCharge =
                valueOrZero(invoice.getServiceCharge());

        BigDecimal partsTotal =
                valueOrZero(invoice.getPartsTotal());

        BigDecimal taxAmount =
                valueOrZero(invoice.getTaxAmount());

        BigDecimal discount =
                valueOrZero(invoice.getDiscount());

        invoice.setServiceCharge(serviceCharge);
        invoice.setPartsTotal(partsTotal);
        invoice.setTaxAmount(taxAmount);
        invoice.setDiscount(discount);

        // Calculate invoice total
        BigDecimal total =
                serviceCharge
                        .add(partsTotal)
                        .add(taxAmount)
                        .subtract(discount);

        // Total should not become negative
        if (total.compareTo(BigDecimal.ZERO) < 0) {
            total = BigDecimal.ZERO;
        }

        invoice.setTotalAmount(total);

        return invoiceRepository.save(invoice);
    }

    // Delete invoice
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    // Convert null amount to zero
    private BigDecimal valueOrZero(BigDecimal value) {

        if (value == null) {
            return BigDecimal.ZERO;
        }

        return value;
    }
}