package com.autocare.autocare.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.autocare.autocare.document.DiagnosticSummary;
import com.autocare.autocare.document.JobCard;
import com.autocare.autocare.entity.Vehicle;
import com.autocare.autocare.service.DiagnosticSummaryService;
import com.autocare.autocare.service.JobCardService;
import com.autocare.autocare.service.VehicleService;

@Controller
@RequestMapping("/diagnostic-summaries")
public class DiagnosticSummaryController {

    private final DiagnosticSummaryService diagnosticSummaryService;
    private final VehicleService vehicleService;
    private final JobCardService jobCardService;

    public DiagnosticSummaryController(
            DiagnosticSummaryService diagnosticSummaryService,
            VehicleService vehicleService,
            JobCardService jobCardService) {

        this.diagnosticSummaryService = diagnosticSummaryService;
        this.vehicleService = vehicleService;
        this.jobCardService = jobCardService;
    }

    // Show all diagnostic summaries
    @GetMapping
    public String showDiagnosticSummaries(Model model) {

        model.addAttribute(
                "diagnosticSummaries",
                diagnosticSummaryService.getAllDiagnosticSummaries()
        );

        return "diagnostic-summaries/list";
    }

    // Show Add form
    @GetMapping("/new")
    public String showAddDiagnosticSummaryForm(Model model) {

        model.addAttribute(
                "diagnosticSummary",
                new DiagnosticSummary()
        );

        // Oracle vehicles
        model.addAttribute(
                "vehicles",
                vehicleService.getAllVehicles()
        );

        // MongoDB job cards
        model.addAttribute(
                "jobCards",
                jobCardService.getAllJobCards()
        );

        model.addAttribute(
                "errorCodeText",
                ""
        );

        return "diagnostic-summaries/form";
    }

    // Show Edit form
    @GetMapping("/edit/{diagnosticId}")
    public String showEditDiagnosticSummaryForm(
            @PathVariable String diagnosticId,
            Model model) {

        DiagnosticSummary diagnosticSummary =
                diagnosticSummaryService
                        .getDiagnosticSummaryByDiagnosticId(
                                diagnosticId
                        );

        if (diagnosticSummary == null) {
            return "redirect:/diagnostic-summaries";
        }

        model.addAttribute(
                "diagnosticSummary",
                diagnosticSummary
        );

        // Oracle vehicles
        model.addAttribute(
                "vehicles",
                vehicleService.getAllVehicles()
        );

        // MongoDB job cards
        model.addAttribute(
                "jobCards",
                jobCardService.getAllJobCards()
        );

        /*
         * Convert MongoDB error-code array
         * into one code per line for textarea.
         */
        String errorCodeText = "";

        if (diagnosticSummary.getErrorCodes() != null
                && !diagnosticSummary.getErrorCodes().isEmpty()) {

            errorCodeText =
                    String.join(
                            System.lineSeparator(),
                            diagnosticSummary.getErrorCodes()
                    );
        }

        model.addAttribute(
                "errorCodeText",
                errorCodeText
        );

        return "diagnostic-summaries/form";
    }

    // Save new or edited Diagnostic Summary
    @PostMapping("/save")
    public String saveDiagnosticSummary(
            @ModelAttribute("diagnosticSummary")
            DiagnosticSummary diagnosticSummary,

            @RequestParam(
                    value = "errorCodeText",
                    required = false
            )
            String errorCodeText) {

        /*
         * Validate Oracle Vehicle.
         */
        if (diagnosticSummary.getVehicleId() == null) {
            return "redirect:/diagnostic-summaries";
        }

        Vehicle vehicle =
                vehicleService.getVehicleById(
                        diagnosticSummary.getVehicleId()
                );

        if (vehicle == null) {
            return "redirect:/diagnostic-summaries";
        }

        /*
         * Job Card is optional.
         *
         * If a Job Card is selected,
         * it must exist in MongoDB.
         */
        if (diagnosticSummary.getJobCardId() != null
                && !diagnosticSummary.getJobCardId().isBlank()) {

            JobCard jobCard =
                    jobCardService.getJobCardByJobCardId(
                            diagnosticSummary.getJobCardId()
                    );

            if (jobCard == null) {
                return "redirect:/diagnostic-summaries";
            }

            /*
             * Make sure selected Job Card belongs
             * to the same vehicle.
             */
            if (jobCard.getVehicleId() != null
                    && !jobCard.getVehicleId()
                    .equals(diagnosticSummary.getVehicleId())) {

                return "redirect:/diagnostic-summaries";
            }
        }

        /*
         * If mileage is empty,
         * use current Oracle vehicle mileage.
         */
        if (diagnosticSummary.getMileage() == null
                && vehicle.getCurrentMileage() != null) {

            diagnosticSummary.setMileage(
                    vehicle.getCurrentMileage()
            );
        }

        /*
         * Convert textarea lines into
         * MongoDB error_codes array.
         *
         * Example:
         * P0300
         * P0420
         *
         * becomes:
         * ["P0300", "P0420"]
         */
        if (errorCodeText != null
                && !errorCodeText.isBlank()) {

            List<String> errorCodes =
                    Arrays.stream(
                            errorCodeText.split("\\R")
                    )
                    .map(String::trim)
                    .filter(code -> !code.isBlank())
                    .toList();

            diagnosticSummary.setErrorCodes(
                    errorCodes
            );
        }

        diagnosticSummaryService.saveDiagnosticSummary(
                diagnosticSummary
        );

        return "redirect:/diagnostic-summaries";
    }

    // Delete using custom Diagnostic ID
    @PostMapping("/delete/{diagnosticId}")
    public String deleteDiagnosticSummary(
            @PathVariable String diagnosticId) {

        diagnosticSummaryService
                .deleteDiagnosticSummaryByDiagnosticId(
                        diagnosticId
                );

        return "redirect:/diagnostic-summaries";
    }
}