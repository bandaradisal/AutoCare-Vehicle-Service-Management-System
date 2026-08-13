package com.autocare.autocare.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.autocare.autocare.entity.Technician;
import com.autocare.autocare.repository.TechnicianRepository;

@Service
public class TechnicianService {

    private final TechnicianRepository technicianRepository;

    public TechnicianService(
            TechnicianRepository technicianRepository) {

        this.technicianRepository = technicianRepository;
    }

    // Get all technicians
    public List<Technician> getAllTechnicians() {
        return technicianRepository.findAll();
    }

    // Get one technician by ID
    public Technician getTechnicianById(Long id) {
        return technicianRepository.findById(id).orElse(null);
    }

    // Add or update technician
    public Technician saveTechnician(Technician technician) {

        if (technician.getTechnicianId() != null) {

            Technician existingTechnician =
                    technicianRepository.findById(
                            technician.getTechnicianId()
                    ).orElse(null);

            if (existingTechnician != null) {
                technician.setCreatedDate(
                        existingTechnician.getCreatedDate()
                );
            }

        } else {

            technician.setCreatedDate(LocalDateTime.now());

            if (technician.getStatus() == null
                    || technician.getStatus().isBlank()) {

                technician.setStatus("Available");
            }

            if (technician.getExperienceYears() == null) {
                technician.setExperienceYears(0);
            }
        }

        return technicianRepository.save(technician);
    }

    // Delete technician
    public void deleteTechnician(Long id) {
        technicianRepository.deleteById(id);
    }
}