package com.autocare.autocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.autocare.autocare.service.ComplaintFeedbackService;
import com.autocare.autocare.service.CustomerService;
import com.autocare.autocare.service.DiagnosticSummaryService;
import com.autocare.autocare.service.InvoiceItemService;
import com.autocare.autocare.service.InvoiceService;
import com.autocare.autocare.service.JobCardService;
import com.autocare.autocare.service.PaymentService;
import com.autocare.autocare.service.ServiceBookingService;
import com.autocare.autocare.service.ServiceHistoryService;
import com.autocare.autocare.service.SparePartService;
import com.autocare.autocare.service.TechnicianNoteService;
import com.autocare.autocare.service.TechnicianService;
import com.autocare.autocare.service.VehicleService;

@Controller
public class DashboardController {

    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final ServiceBookingService serviceBookingService;
    private final TechnicianService technicianService;
    private final SparePartService sparePartService;
    private final InvoiceService invoiceService;
    private final PaymentService paymentService;
    private final InvoiceItemService invoiceItemService;

    private final JobCardService jobCardService;
    private final TechnicianNoteService technicianNoteService;
    private final ServiceHistoryService serviceHistoryService;
    private final ComplaintFeedbackService complaintFeedbackService;
    private final DiagnosticSummaryService diagnosticSummaryService;

    public DashboardController(
            CustomerService customerService,
            VehicleService vehicleService,
            ServiceBookingService serviceBookingService,
            TechnicianService technicianService,
            SparePartService sparePartService,
            InvoiceService invoiceService,
            PaymentService paymentService,
            InvoiceItemService invoiceItemService,
            JobCardService jobCardService,
            TechnicianNoteService technicianNoteService,
            ServiceHistoryService serviceHistoryService,
            ComplaintFeedbackService complaintFeedbackService,
            DiagnosticSummaryService diagnosticSummaryService) {

        this.customerService = customerService;
        this.vehicleService = vehicleService;
        this.serviceBookingService = serviceBookingService;
        this.technicianService = technicianService;
        this.sparePartService = sparePartService;
        this.invoiceService = invoiceService;
        this.paymentService = paymentService;
        this.invoiceItemService = invoiceItemService;

        this.jobCardService = jobCardService;
        this.technicianNoteService = technicianNoteService;
        this.serviceHistoryService = serviceHistoryService;
        this.complaintFeedbackService = complaintFeedbackService;
        this.diagnosticSummaryService = diagnosticSummaryService;
    }

    // Main dashboard
    @GetMapping("/")
    public String showDashboard(Model model) {

        /*
         * ==============================
         * ORACLE DATABASE STATISTICS
         * ==============================
         */

        model.addAttribute(
                "customerCount",
                customerService.getAllCustomers().size()
        );

        model.addAttribute(
                "vehicleCount",
                vehicleService.getAllVehicles().size()
        );

        model.addAttribute(
                "bookingCount",
                serviceBookingService.getAllBookings().size()
        );

        model.addAttribute(
                "technicianCount",
                technicianService.getAllTechnicians().size()
        );

        model.addAttribute(
                "sparePartCount",
                sparePartService.getAllSpareParts().size()
        );

        model.addAttribute(
                "invoiceCount",
                invoiceService.getAllInvoices().size()
        );

        model.addAttribute(
                "paymentCount",
                paymentService.getAllPayments().size()
        );

        model.addAttribute(
                "invoiceItemCount",
                invoiceItemService.getAllInvoiceItems().size()
        );


        /*
         * Pending Service Bookings
         */
        long pendingBookings =
                serviceBookingService
                        .getAllBookings()
                        .stream()
                        .filter(booking ->
                                booking.getBookingStatus() != null
                                &&
                                booking.getBookingStatus()
                                        .equalsIgnoreCase("Pending"))
                        .count();

        model.addAttribute(
                "pendingBookings",
                pendingBookings
        );


        /*
         * Low Stock Spare Parts
         */
        long lowStockParts =
                sparePartService
                        .getAllSpareParts()
                        .stream()
                        .filter(part ->
                                part.getQuantityInStock() != null
                                &&
                                part.getReorderLevel() != null
                                &&
                                part.getQuantityInStock()
                                        <= part.getReorderLevel())
                        .count();

        model.addAttribute(
                "lowStockParts",
                lowStockParts
        );


        /*
         * Unpaid Invoices
         */
        long unpaidInvoices =
                invoiceService
                        .getAllInvoices()
                        .stream()
                        .filter(invoice ->
                                invoice.getInvoiceStatus() != null
                                &&
                                invoice.getInvoiceStatus()
                                        .equalsIgnoreCase("Unpaid"))
                        .count();

        model.addAttribute(
                "unpaidInvoices",
                unpaidInvoices
        );


        /*
         * ==============================
         * MONGODB STATISTICS
         * ==============================
         */

        model.addAttribute(
                "jobCardCount",
                jobCardService.getAllJobCards().size()
        );

        model.addAttribute(
                "technicianNoteCount",
                technicianNoteService
                        .getAllTechnicianNotes()
                        .size()
        );

        model.addAttribute(
                "serviceHistoryCount",
                serviceHistoryService
                        .getAllServiceHistory()
                        .size()
        );

        model.addAttribute(
                "complaintFeedbackCount",
                complaintFeedbackService
                        .getAllComplaintFeedback()
                        .size()
        );

        model.addAttribute(
                "diagnosticCount",
                diagnosticSummaryService
                        .getAllDiagnosticSummaries()
                        .size()
        );


        /*
         * Count only Complaint records
         */
        long complaintCount =
                complaintFeedbackService
                        .getAllComplaintFeedback()
                        .stream()
                        .filter(record ->
                                record.getType() != null
                                &&
                                record.getType()
                                        .equalsIgnoreCase("Complaint"))
                        .count();

        model.addAttribute(
                "complaintCount",
                complaintCount
        );


        /*
         * Count Open Job Cards
         */
        long openJobCards =
                jobCardService
                        .getAllJobCards()
                        .stream()
                        .filter(jobCard ->
                                jobCard.getStatus() != null
                                &&
                                (
                                    jobCard.getStatus()
                                            .equalsIgnoreCase("Open")
                                    ||
                                    jobCard.getStatus()
                                            .equalsIgnoreCase("In Progress")
                                ))
                        .count();

        model.addAttribute(
                "openJobCards",
                openJobCards
        );


        return "dashboard";
    }
}